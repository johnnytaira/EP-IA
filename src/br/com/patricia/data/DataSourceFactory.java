package br.com.patricia.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.ListUtils;

/**
 * Classe respons�vel pela cria��o das dataSources de treinamento e teste, tanto no m�todo holdout
 * quanto no m�todo r-fold crossvalidation.
 * <p> Usa m�todos da biblioteca {@link org.apache.commons}
 * @author Johnny Taira
 *
 */
public class DataSourceFactory {
	
	private static List<DataSource> dataSources;
	
	
	/**
	 * Construtor para a cria��o das parti��es usando o m�todo holdout
	 * @param dataSource conjunto de dados original
	 */
	public DataSourceFactory(DataSource dataSource){
		dataSources = new ArrayList<DataSource>();
		particiona(dataSource);
	}
	
	/**
	 * Construtor para a cria��o das parti��es usando o m�todo crossvalidation
	 * @param dataSource conjunto de dados original
	 * @param folds n�mero de parti��es
	 */
	public DataSourceFactory(DataSource dataSource, int folds){
		dataSources = new ArrayList<DataSource>();
		particiona(dataSource, folds);
	}
	
	public List<DataSource> getDataSources(){
		return dataSources;
	}
	
	/**
	 * Particionamento no m�todo holdout. S�o divididos em tr�s parti��es, sendo que 
	 * posteriormente ocorrer� o merge necess�rio.
	 * @param dataSource
	 */
	private static void particiona(DataSource dataSource){
		particiona(dataSource, 3);
	}
	/**
	 * Particionamento no m�todo cross-validation.
	 * @param dataSource
	 * @param folds
	 */
	private static void particiona(DataSource dataSource, int folds){
		Set<Integer> dataSourceIds = dataSource.getText().keySet();
		List<Integer> dataSourceIdsList = new ArrayList<Integer>(dataSourceIds);
		Collections.shuffle(dataSourceIdsList);
		
		List<List<Integer>> partitions = new LinkedList<List<Integer>>(ListUtils.partition(dataSourceIdsList, dataSourceIdsList.size()/(folds)));
		
		List<Integer> union = ListUtils.union(partitions.get(0), partitions.get(partitions.size() - 1));
		partitions.remove(partitions.get(0));
		partitions.remove(partitions.size() - 1);
		partitions.add(union);
		for(List<Integer> partition : partitions){
			DataSource data = new DataSource();
			Map<Integer, Integer> sentiments = new HashMap<Integer, Integer>();
			Map<Integer, String> texts = new HashMap<Integer, String>();
			for(int id : partition){
				sentiments.put(id, dataSource.getSentiment().get(id));
				texts.put(id, dataSource.getText().get(id));
			}
			
			data.getSentiment().putAll(sentiments);
			data.getText().putAll(texts);
			data.setSize(partition.size());
			dataSources.add(data);
		}
		
		
	}
	
	/**
	 * M�todo para unir um conjunto de {@link DataSource}. 
	 * @param dataSources o conjunto de {@link DataSource} a ser mergeado
	 * @return as dataSources mergeadas
	 */
	public DataSource mergeDataSources(List<DataSource> dataSources){
		DataSource dataSource = new DataSource();
		
		for(DataSource data : dataSources){
			dataSource.getSentiment().putAll(data.getSentiment());
			dataSource.getText().putAll(data.getText());
			dataSource.setSize(dataSource.getSize()+ data.getSize());
		}
		
		return dataSource;
			
	}
	
}
