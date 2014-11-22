package br.com.patricia.test;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.DataSourceFactory;
import br.com.patricia.data.Reader;
import br.com.patricia.naive.BayesianModel;
import br.com.patricia.naive.Probabilidades;


/**
 * Classe para realização de testes.
 * @author Johnny Taira
 *
 */
public class Test {

	public static void main(String[] args) {
		DataSource dataSource = new DataSource(); 
		Reader reader = new Reader(dataSource);
		reader.readCSV("/Users/Johnny Taira/Desktop/dummy.csv");
		

		DataSourceFactory data = new DataSourceFactory(dataSource);
		DataSource testing = data.getTestingSet();
		DataSource training = data.getTrainingSet();
		Probabilidades ney = new Probabilidades();
		ney.calcularPosteriori(dataSource);
		BayesianModel negative = ney.getNegativeModel();
		BayesianModel positive = ney.getPositiveModel();
		
		System.out.println("Negative: "+ negative.getPriori());
		System.out.println(negative.getTamanhoVocabulario());
		System.out.println("Positive: " +positive.getPriori());
		System.out.println(positive.getTamanhoVocabulario()); 
		

		
	}
	
}
