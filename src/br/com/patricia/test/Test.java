package br.com.patricia.test;

import java.util.Scanner;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.Reader;
import br.com.patricia.util.Numericals;

/**
 * Classe para realização de testes.
 * @author Johnny Taira
 *
 */
public class Test {

	public static void main(String[] args) {
		DataSource datasource = new DataSource();
		Reader reader = new Reader(datasource);
		reader.readCSV("/Users/Johnny Taira/Desktop/Sentiment Analysis Dataset.csv");
		
		System.out.println(datasource.getId().size());
		System.out.println(datasource.getText().size());
		
	}
	
}
