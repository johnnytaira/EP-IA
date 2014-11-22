package br.com.patricia.naive;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.TextManipulator;



/**
 * Classe que possui funcionalidades referentes ao cálculo das probabilidades. Os resultados são 
 * armazenados em um {@link BayesianModel}, diferenciado pela sua classe (positiva ou negativa)
 * @author Johnny Taira
 *
 */
public class Probabilidades {

	private int negativeFrequency;
	private int positiveFrequency;
	private BayesianModel positiveModel;
	private BayesianModel negativeModel;
	
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

	public int getNegativeFrequency(){
		return this.negativeFrequency;
	}
	
	public void setNegativeFrequency(int negativeFrequency){
		this.negativeFrequency = negativeFrequency;
	}
	
	public int getPositiveFrequency(){
		return this.positiveFrequency;
	}
	
	public void setPositiveFrequency(int positiveFrequency){
		this.positiveFrequency=positiveFrequency;
	}
	/**
	 * Calcula a priori, dado um sentimento. A priori é calculada pela frequência 
	 * de cada classe no conjunto de dados.
	 * @param sentiment classe do sentimento
	 * @return a frequência da classe passada por parâmetro.
	 */
	public double priori(int sentiment, DataSource dataSource){
		
		return (double) Collections.frequency(dataSource.getSentiment().values(), sentiment)/ dataSource.getSentiment().size();
		
	}
	
	
	private  Map<String, Integer> positiveOccurrences;
	private  Map<String, Integer> negativeOccurrences;
	private TextManipulator manipulator;
	
	
	/**
	 * Método privado para inicializar alguns atributos referentes ao modelo bayesiano.
	 */
	private void modelFactory(){
		positiveOccurrences = manipulator.getWordPositiveOccurrences();
		negativeOccurrences = manipulator.getWordNegativeOccurrences();
		
		positiveModel = new BayesianModel(1);
		positiveModel.setTamanhoVocabulario(manipulator.getTotalPositiveOccurrences());
		negativeModel = new BayesianModel(0);
		negativeModel.setTamanhoVocabulario(manipulator.getTotalNegativeOccurrences());
	}
	
	/**
	 * Calcula a posteriori, dado um conjunto de dados para treinamento. A posteriori é 
	 * calculada para cada palavra do vocabulário. 
	 * @param dataSource
	 */
	public void calcularPosteriori(DataSource dataSource){
		manipulator = new TextManipulator(dataSource);
		manipulator.wordCounter();
		modelFactory();
		positiveModel.setPriori(priori(1, dataSource));
		negativeModel.setPriori(priori(0, dataSource));
		Set<String> palavras = positiveOccurrences.keySet();
		for(String palavra: palavras){
			calcularProbabilidadePorPalavra(palavra);
		}
		
	}
	
	
	/**
	 * Sem fazer o Laplace, por enquanto
	 * @param palavra
	 *
	 */
	//FIXME: Caio, mudar aqui!.
	private void calcularProbabilidadePorPalavra(String palavra){
		
		//Para não dar exception em divisão por zero.
		if(manipulator.getTotalPositiveOccurrences() == 0){
			negativeModel.getProbabilidadeDadaClasse().put(palavra, 1.0);
			return;
		}else if( manipulator.getTotalNegativeOccurrences() == 0){
			positiveModel.getProbabilidadeDadaClasse().put(palavra, 1.0);
			return;
		}else{
			double probabilidadePositivo = positiveOccurrences.get(palavra)/manipulator.getTotalPositiveOccurrences();
			positiveModel.getProbabilidadeDadaClasse().put(palavra, probabilidadePositivo);
			
			double probabilidadeNegativo = negativeOccurrences.get(palavra)/manipulator.getTotalNegativeOccurrences();
			negativeModel.getProbabilidadeDadaClasse().put(palavra, probabilidadeNegativo);
		}
		
	}
	
	//FIXME implementar aqui, se necessário.
	private int estimativaLaplace(Map<String, Integer> frequencias){
		return Collections.frequency(frequencias.values(), 0);
	}
}
