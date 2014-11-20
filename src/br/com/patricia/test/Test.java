package br.com.patricia.test;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.Reader;
import br.com.patricia.data.TextManipulator;

/**
 * Classe para realização de testes.
 * @author Johnny Taira
 *
 */
public class Test {

	public static void main(String[] args) {
		DataSource datasource = new DataSource();
		Reader reader = new Reader(datasource);
		reader.readCSV("/Users/Johnny Taira/Desktop/dummy.csv");
		
		TextManipulator manipulator = new TextManipulator(datasource);
		
		manipulator.wordCounter();
		
	}
	
}
