package br.com.patricia.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.collections4.ListUtils;

import br.com.patricia.data.DataSource;
import br.com.patricia.data.DataSourceFactory;
import br.com.patricia.data.Reader;
import br.com.patricia.data.StopWordReader;
import br.com.patricia.data.TextManipulator;
import br.com.patricia.naive.BayesianModel;
import br.com.patricia.naive.Probabilidades;

/**
 * Classe para realização de testes.
 * 
 * @author Johnny Taira
 *
 */
public class Test {

	// FIXME: mudar para receber via argumento
	public static void main(String[] args) {
		// String path = args[0];
		String path = "/Users/Johnny Taira/Desktop/Sentiment Analysis Dataset.csv";
		Scanner sc = new Scanner(System.in);

		System.out
				.println("Digite a opção que você deseja: " + "\n1:Holdout " + "\n2:CrossValidation");
		int opcao = sc.nextInt();

		sc.close();

		DataSource dataSource = new DataSource();
		Reader reader = new StopWordReader(dataSource);
		reader.readCSV(path);
		DataSourceFactory data = null;
		// System.out.println(dataSource.getText());
		if (opcao == 1) {
			data = new DataSourceFactory(dataSource);
			holdout(data, null);
		} else if (opcao == 2) {
			data = new DataSourceFactory(dataSource, 10);
			crossValidation(data);
		} else {
			System.out.println("fuck");
		}

	}

	private static void crossValidation(DataSourceFactory factory) {
		List<DataSource> dataSources = factory.getDataSources();
		List<Double> arrayStats = new ArrayList<Double>();
		for (int i = 0; i < dataSources.size(); i++) {
			DataSource testing = dataSources.get(i);
			List<DataSource> temp = new ArrayList<DataSource>();
			temp.add(testing);
			List<DataSource> listaTreinamento = ListUtils.subtract(
					factory.getDataSources(), temp);
			DataSource training = factory.mergeDataSources(listaTreinamento);
			TextManipulator manipulator = new TextManipulator(training);
			Probabilidades p = new Probabilidades(manipulator);
			p.criarModeloBayesiano(training);
			p.calculaPosteriori(testing);
			arrayStats.add(p.calculoAcuracia(testing));
			// System.out.println(p.calculoAcuracia(testing));
			temp.remove(testing);
		}

		System.out.println(arrayStats);

	}

	private static void holdout(DataSourceFactory factory, String path) {

		List<DataSource> dataSources = factory.getDataSources();
		DataSource testing = dataSources.get(0);
		dataSources.remove(testing);
		DataSource training = factory.mergeDataSources(dataSources);
		TextManipulator manipulator = new TextManipulator(training);
		Probabilidades ney = new Probabilidades(manipulator);
		ney.criarModeloBayesiano(training);

		ney.calculaPosteriori(testing);

		System.out.println(ney.calculoAcuracia(testing));
		// saveToFile(ney.calculoAcuracia(testing), path, ney);

	}

	private static void saveToFile(double stats, String path, Probabilidades p) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(path);
			PrintWriter pw = new PrintWriter(fw);
			BayesianModel positive = p.getPositiveModel();
			BayesianModel negative = p.getNegativeModel();
			pw.println("Accurracy: " + Math.round(stats * 100) + "%");
			pw.println("P(h=positive): " + positive.getPriori());
			pw.println("Size(h=positive): " + positive.getTamanhoVocabulario());
			pw.println("P(h=negative): " + negative.getPriori());
			pw.println("Size(h=negative): " + negative.getTamanhoVocabulario());

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
