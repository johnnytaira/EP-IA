package br.com.patricia.naive;

import java.util.HashMap;
import java.util.Map;


/**
 * Armazena todos os dados referentes ao modelo Naive-Bayes (Priori, tamanho do vocabulário,
 * probabilidade P(d|H)), representado pelo atributo {@code probabilidadeDadaClasse}, que associa
 * uma probabilidade a cada palavra, em {@link Map}. Todos os dados estatísticos para o cálculo da
 * Posteriori devem ser pegos nesta classe.
 * @author Johnny Taira
 *
 */
public class BayesianModel {
	
	private int sentiment;
	private double priori;
	private long tamanhoVocabulario;
	private Map<String, Double> probabilidadeDadaClasse;
	
	
	public BayesianModel(int sentiment){
		this.sentiment = sentiment;
		probabilidadeDadaClasse = new HashMap<String, Double>();
	}
	
	public int getSentiment() {
		return sentiment;
	}
	public void setSentiment(int sentiment) {
		this.sentiment = sentiment;
	}
	public double getPriori() {
		return priori;
	}
	public void setPriori(double priori) {
		this.priori = priori;
	}
	public long getTamanhoVocabulario() {
		return tamanhoVocabulario;
	}
	public void setTamanhoVocabulario(long tamanhoVocabulario) {
		this.tamanhoVocabulario = tamanhoVocabulario;
	}
	public Map<String, Double> getProbabilidadeDadaClasse() {
		return probabilidadeDadaClasse;
	}
	public void setProbabilidadeDadaClasse(
			Map<String, Double> probabilidadeDadaClasse) {
		this.probabilidadeDadaClasse = probabilidadeDadaClasse;
	}
	
	

}
