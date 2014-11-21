package br.com.patricia.data;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 * Classe designada para tarefas de manipulação das palavras dos tweets. Contém
 * métodos que realizam a contagem das palavras para sentimentos positivos e negativos.
 * @author Johnny Taira
 *
 */
public class TextManipulator {
	
	private DataSource dataSource;
	private Map<String, Integer> wordPositiveOccurrences;
	private Map<String, Integer> wordNegativeOccurrences;
	private long totalOccurrences;
	
	public TextManipulator(DataSource dataSource){
		this.dataSource = dataSource;
		this.wordPositiveOccurrences = new TreeMap<String, Integer>();
		this.wordNegativeOccurrences = new TreeMap<String, Integer>();
		this.totalOccurrences = 0;
	}
	
	public Map<String, Integer> getWordPositiveOccurrences(){
		return this.wordPositiveOccurrences;
	}
	
	public long getTotalOccurrences(){
		return this.totalOccurrences;
	}
	
	public Map<String, Integer> getWordNegativeOccurrences(){
		return this.wordNegativeOccurrences;
	}
	
	
	/**
	 * Conta o número de ocorrências de cada palavra para sentimentos positivos e negativos.
	 * As operações são realizadas em dois mapas, que podem ser retornados através dos getters
	 * and setters acima.
	 */
	public void wordCounter(){
		
		
		for (Map.Entry<Integer, String> entry : dataSource.getText().entrySet()){
			Scanner sc = new Scanner(entry.getValue());
			
			while(sc.hasNext()){
				String word = sc.next();
				if(dataSource.getSentiment().get(entry.getKey()).equals(0)){
					if(wordNegativeOccurrences.containsKey(word)){
						wordNegativeOccurrences.put(word, wordNegativeOccurrences.get(word) + 1);
					}else{
						wordNegativeOccurrences.put(word, 1);
					}
					
					if(!wordPositiveOccurrences.containsKey(word))
						wordPositiveOccurrences.put(word, 0);
					
				}else{
					if (wordPositiveOccurrences.containsKey(word)){
						wordPositiveOccurrences.put(word, wordPositiveOccurrences.get(word) + 1);
					}else{
						wordPositiveOccurrences.put(word, 1);
					}
					if(!wordNegativeOccurrences.containsKey(word))
						wordNegativeOccurrences.put(word, 0);
				}
				
				totalOccurrences++;
			}
			
			sc.close();
		}
		
	}
	
	
	//FIXME Não usar, por enquanto
	private String removeInitialDigit(String s){
		return s.replaceAll("\\d","");
		
	}
	
	
	//FIXME Não usar, por enquanto
	private String removeSpecialCharacters(String s){
		String result = s.replaceAll("[^\\w\\s]"," ").toLowerCase();
		
		return result;
	}
	

}
