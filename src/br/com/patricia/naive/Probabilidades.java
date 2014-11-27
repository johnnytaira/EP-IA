package br.com.patricia.naive;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.TextManipulator;

/**
 * Classe que possui funcionalidades referentes ao cálculo das probabilidades.
 * Os resultados são armazenados em um {@link BayesianModel}, diferenciado pela
 * sua classe (positiva ou negativa)
 * 
 * @author Johnny Taira
 *
 */
public class Probabilidades {

	private BayesianModel positiveModel;
	private BayesianModel negativeModel;
	private Map<Integer, Integer> classificationMap;

	public Probabilidades(TextManipulator manipulator) {
		this.manipulator = manipulator;
	}

	public BayesianModel getPositiveModel() {
		return positiveModel;
	}

	public void setPositiveModel(BayesianModel positiveModel) {
		this.positiveModel = positiveModel;
	}

	public BayesianModel getNegativeModel() {
		return negativeModel;
	}

	public void setNegativeModel(BayesianModel negativeModel) {
		this.negativeModel = negativeModel;
	}

	public Map<Integer, Integer> getClassificationMap() {
		return classificationMap;
	}

	public void setClassificationMap(Map<Integer, Integer> classificationMap) {
		this.classificationMap = classificationMap;
	}

	/**
	 * Cria o modelo bayesiano, com as probabilidades de cada palavra, dada a
	 * classe, bem como o cálculo da priori e as ocorrências de palavras
	 * positivas e negativas.
	 * 
	 * @param dataSource
	 */
	public void criarModeloBayesiano(DataSource dataSource) {
		manipulator.wordCounter();
		modelFactory();
		positiveModel.setPriori(priori(1, dataSource));
		negativeModel.setPriori(priori(0, dataSource));
		Set<String> palavras = positiveOccurrences.keySet();
		for (String palavra : palavras) {
			calcularProbabilidadePorPalavra(palavra);
		}

	}

	/**
	 * Calcula a posteriori. Segundo a fórmula aprendida em aula, define-se a
	 * posteriori como:
	 * <p>
	 * <strong>P(H|d) = P(H) * P(d|H)</strong>.
	 * 
	 * @param dataSource
	 */
	public void calculaPosteriori(DataSource dataSource) {
		classificationMap = new HashMap<Integer, Integer>();
		for (Map.Entry<Integer, String> entry : dataSource.getText().entrySet()) {

			double pdhp = 0, pdhn = 0;
			double posteriorip = 0, posteriorin = 0;
			String tweet = entry.getValue();
			Scanner sc = new Scanner(tweet);
			while (sc.hasNext()) {
				String palavra = sc.next();
				// O cálculo do log previne o underflow
				if (positiveModel.getProbabilidadeDadaClasse().containsKey(
						palavra)) {
					pdhp = pdhp + Math.log10(positiveModel
							.getProbabilidadeDadaClasse().get(palavra));
				} else {
					calcularProbabilidadePorPalavra(palavra);
					pdhp = pdhp + Math.log10(positiveModel
							.getProbabilidadeDadaClasse().get(palavra));
				}

				if (negativeModel.getProbabilidadeDadaClasse().containsKey(
						palavra)) {
					pdhn = pdhn + Math.log10(negativeModel
							.getProbabilidadeDadaClasse().get(palavra));
				} else {
					calcularProbabilidadePorPalavra(palavra);
					pdhn = pdhn + Math.log10(negativeModel
							.getProbabilidadeDadaClasse().get(palavra));
				}

			}

			posteriorip = Math.log10(positiveModel.getPriori()) + pdhp;
			posteriorin = Math.log10(negativeModel.getPriori()) + pdhn;

			if (posteriorip > posteriorin) {
				classificationMap.put(entry.getKey(), 1);

			} else {
				classificationMap.put(entry.getKey(), 0);

			}
			sc.close();
		}
	}

	/**
	 * Método responsável por realizar o cálculo da acurácia do classificador
	 * implementado.
	 * 
	 * @param dataSource
	 *            o conjunto de dados
	 * @return a acurácia do classificador.
	 */
	public double calculoAcuracia(DataSource dataSource) {
		Map<Integer, Integer> sentiments = dataSource.getSentiment();
		int acertos = 0;
		for (Map.Entry<Integer, Integer> entry : sentiments.entrySet()) {
			if (entry.getValue() == classificationMap.get(entry.getKey())) {
				acertos++;
			}
		}

		return (double) acertos / sentiments.size();
	}

	/**
	 * Calcula a priori, dado um sentimento. A priori é calculada pela
	 * frequência de cada classe no conjunto de dados.
	 * 
	 * @param sentiment
	 *            classe do sentimento
	 * @return a frequência da classe passada por parâmetro.
	 */
	private double priori(int sentiment, DataSource dataSource) {

		return (double) Collections.frequency(dataSource.getSentiment()
				.values(), sentiment) / dataSource.getSentiment().size();

	}

	private Map<String, Integer> positiveOccurrences;
	private Map<String, Integer> negativeOccurrences;
	private TextManipulator manipulator;

	/**
	 * Método privado para inicializar alguns atributos referentes ao modelo
	 * bayesiano.
	 */
	private void modelFactory() {
		positiveOccurrences = manipulator.getWordPositiveOccurrences();
		negativeOccurrences = manipulator.getWordNegativeOccurrences();

		positiveModel = new BayesianModel(1);
		positiveModel.setTamanhoVocabulario(manipulator
				.getTotalPositiveOccurrences());
		negativeModel = new BayesianModel(0);
		negativeModel.setTamanhoVocabulario(manipulator
				.getTotalNegativeOccurrences());
	}

	/**
	 * Para cada palavra do vocabulário, calcula sua probabilidade, dado um
	 * sentimento. Equivale ao P(d|H), dentro da fórmula. Os dados são salvos em
	 * um {@link Map} para serem acessados na hora de calcular a posteriori.
	 * 
	 * @param palavra
	 *
	 */
	private void calcularProbabilidadePorPalavra(String palavra) {

		// Para não dar exception em divisão por zero.
		if (manipulator.getTotalPositiveOccurrences() == 0) {
			negativeModel.getProbabilidadeDadaClasse().put(palavra, 1.0);
			return;
		} else if (manipulator.getTotalNegativeOccurrences() == 0) {
			positiveModel.getProbabilidadeDadaClasse().put(palavra, 1.0);
			return;
		} else {
			double probabilidadePositivo, probabilidadeNegativo;
			if (positiveOccurrences.containsKey(palavra)) {
				probabilidadePositivo = (double) (positiveOccurrences
						.get(palavra) + 1) / (manipulator
						.getTotalPositiveOccurrences() + positiveOccurrences
						.size());

			} else {// Palavra nova no conjunto de testes
				probabilidadePositivo = (double) (1 + 1) / (manipulator
						.getTotalPositiveOccurrences() + positiveOccurrences
						.size());
			}
			positiveModel.getProbabilidadeDadaClasse().put(palavra,
					probabilidadePositivo);

			if (negativeOccurrences.containsKey(palavra)) {
				probabilidadeNegativo = (double) (negativeOccurrences
						.get(palavra) + 1) / (manipulator
						.getTotalNegativeOccurrences() + negativeOccurrences
						.size());

			} else {// Palavra nova no conjunto de testes
				probabilidadeNegativo = (double) (1 + 1) / (manipulator
						.getTotalNegativeOccurrences() + negativeOccurrences
						.size());
			}

			negativeModel.getProbabilidadeDadaClasse().put(palavra,
					probabilidadeNegativo);

		}

	}

}
