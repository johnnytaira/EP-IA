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
 * Classe responsável pela criação das dataSources de treinamento e teste, tanto no método holdout
 * quanto no método r-fold crossvalidation.
 * <p> Usa métodos da biblioteca {@link org.apache.commons}
 * @author Johnny Taira
 *
 */
public class DataSourceFactory {
	
	private static List<DataSource> dataSources;
	
	
	/**
	 * Construtor para a criação das partições usando o método holdout
	 * @param dataSource conjunto de dados original
	 */
	public DataSourceFactory(DataSource dataSource){
		dataSources = new ArrayList<DataSource>();
		particiona(dataSource);
	}
	
	/**
	 * Construtor para a criação das partições usando o método crossvalidation
	 * @param dataSource conjunto de dados original
	 * @param folds número de partições
	 */
	public DataSourceFactory(DataSource dataSource, int folds){
		dataSources = new ArrayList<DataSource>();
		particiona(dataSource, folds);
	}
	
	public List<DataSource> getDataSources(){
		return dataSources;
	}
	
	/**
	 * Particionamento no método holdout. São divididos em três partições, sendo que 
	 * posteriormente ocorrerá o merge necessário.
	 * @param dataSource
	 */
	private static void particiona(DataSource dataSource){
		particiona(dataSource, 3);
	}
	/**
	 * Particionamento no método cross-validation.
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
	 * Método para unir um conjunto de {@link DataSource}. 
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
