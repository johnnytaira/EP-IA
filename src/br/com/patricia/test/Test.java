package br.com.patricia.test;

import java.util.TreeSet;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.DataSourceFactory;
import br.com.patricia.data.Reader;


/**
 * Classe para realização de testes.
 * @author Johnny Taira
 *
 */
public class Test {

	public static void main(String[] args) {
		DataSource dataSource = new DataSource(); 
		Reader reader = new Reader(dataSource);
		reader.readCSV("/Users/Marcio/Downloads/Desktop/dummy.csv");
//		
//		DataSource teste = DataSourceFactory.getTrainingSet(datasource);
//		System.out.println(datasource.getSize());
//		System.out.println(teste.getSentiment().keySet());
//		System.out.println(teste.getSize());
		

		DataSourceFactory data = new DataSourceFactory(dataSource);
		DataSource testing = data.getTestingSet();
		DataSource training = data.getTrainingSet();
		System.out.println(dataSource.getSize());
		System.out.println(testing.getSize());
		System.out.println(training.getSize());
		
		System.out.println(new TreeSet<Integer>(testing.getSentiment().keySet()));
		System.out.println(new TreeSet<Integer>(training.getSentiment().keySet()));
		
//		System.out.println(dataSource.getSentiment().size());
//		TextManipulator manipulator = new TextManipulator(dataSource);
//		
//		manipulator.wordCounter();
//		System.out.println("Negative: " +manipulator.getWordNegativeOccurrences().get("wompppp"));
//	
//		System.out.println("Positive: " +manipulator.getWordPositiveOccurrences().get("wompppp"));
//		
//		System.out.println(manipulator.getTotalOccurrences());
//		Probabilidades p = new Probabilidades(dataSource, manipulator );
//		System.out.println(p.priori(0) + p.priori(1));
//		
//		p.calcularProbabilidades();
//		
//		System.out.println(p.getProbabilitiesPositive().get("happy"));
		
	}
	
}
