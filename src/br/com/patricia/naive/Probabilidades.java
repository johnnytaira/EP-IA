package br.com.patricia.naive;

import java.util.Collections;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.TextManipulator;



/**
 * Classe que possui funcionalidades referentes ao cálculo das probabilidades
 * @author Johnny Taira
 *
 */
public class Probabilidades {

	private DataSource dataSource;
	private TextManipulator textManipulator;
	
	public Probabilidades(DataSource dataSource, TextManipulator textManipulator){
		this.dataSource = dataSource;
		this.textManipulator = textManipulator;
	}
	
	
	/**
	 * Calcula a priori, dado um sentimento
	 * @param sentiment
	 * @return 
	 */
	public double priori(int sentiment){
		
		return (double) Collections.frequency(dataSource.getSentiment().values(), sentiment)/ dataSource.getSentiment().size();
		
	}
}
