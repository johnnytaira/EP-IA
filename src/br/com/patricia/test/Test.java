package br.com.patricia.test;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.Reader;
import br.com.patricia.data.TextManipulator;
import br.com.patricia.naive.Probabilidades;

/**
 * Classe para realização de testes.
 * @author Johnny Taira
 *
 */
public class Test {

	public static void main(String[] args) {
		DataSource datasource = new DataSource();
		Reader reader = new Reader(datasource);
		reader.readCSV("/Users/Marcio/Downloads/Desktop/dummy.csv");
		
		System.out.println(datasource.getSentiment().size());
		TextManipulator manipulator = new TextManipulator(datasource);
		
		manipulator.wordCounter();
		System.out.println("Negative: " +manipulator.getWordNegativeOccurrences().get("wompppp"));
	
		System.out.println("Positive: " +manipulator.getWordPositiveOccurrences().get("wompppp"));
		
		
		Probabilidades p = new Probabilidades(datasource, manipulator );
		System.out.println(p.priori(0) + p.priori(1));
		
	}
	
}
