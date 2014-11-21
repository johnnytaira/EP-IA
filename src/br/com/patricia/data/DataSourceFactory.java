package br.com.patricia.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Classe responsável pela fabricação de duas DataSources:uma para treinamento e uma para testes, seguindo a documentação
 * do EP de IA.
 * <p> Usa métodos da biblioteca {@link org.apache.commons}
 * @author Johnny Taira
 *
 */
public class DataSourceFactory {
	
	private static DataSource trainingSet;
	private static DataSource testingSet;
	
	public DataSourceFactory(DataSource dataSource){
		trainingSet = new DataSource();
		testingSet = new DataSource();
		getTrainingSet(dataSource);
	}
	
	
	public DataSource getTrainingSet(){
		return trainingSet;
	}
	
	public DataSource getTestingSet(){
		return testingSet;
	}
	

	private static void getTrainingSet(DataSource dataSource){
		Map<Integer, String> texts = dataSource.getText();
		Map<Integer, Integer> sentiments = dataSource.getSentiment();
		
		Random random = new Random();
		
		Map<Integer, String> trainingTexts = new HashMap<Integer, String>();
		Map<Integer, Integer> trainingSentiments = new HashMap<Integer, Integer>();
		for (int i = 0; i < (2 * dataSource.getSize()) / 3; i++){
			int number = random.nextInt(dataSource.getSize());
			trainingTexts.put(number, texts.get(number));
			trainingSentiments.put(number, sentiments.get(number));
		}
		
		trainingSet.getSentiment().putAll(trainingSentiments);
		trainingSet.getText().putAll(trainingTexts);
		trainingSet.setSize((2 * dataSource.getSize()) / 3);
		
		Collection<Integer> set = CollectionUtils.subtract(sentiments.keySet(), trainingSentiments.keySet());
		
		
		Map<Integer, String> testingTexts = new HashMap<Integer, String>();
		Map<Integer, Integer> testingSentiments = new HashMap<Integer, Integer>();
		for (int i : set){
			testingTexts.put(i, texts.get(i));
			testingSentiments.put(i, sentiments.get(i));
		}
		
		testingSet.getSentiment().putAll(testingSentiments);
		testingSet.getText().putAll(trainingTexts);
		testingSet.setSize(dataSource.getSize() - trainingSet.getSize());
	}
	
}
