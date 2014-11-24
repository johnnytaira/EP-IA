package br.com.patricia.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por realizar a leitura do arquivo .csv que servirá como
 * exemplos já rotulados para análise de sentimentos. Ao menos a princípio,
 * todos os dados são armazenados em {@link List} ({@code List} de inteiros,
 * {@code List} de sentimentos e {@code List} de textos a serem analisados).
 * 
 * @author Johnny Taira
 *
 */
public class Reader {

	protected DataSource dataSource;
	protected int id = 0;
	protected int sentiment = 0;
	protected StringBuilder text = new StringBuilder();

	public Reader(DataSource datasource) {
		this.dataSource = datasource;
	}

	/**
	 * Lê um csv, dado um caminho para leitura do arquivo. Os dados são
	 * armazenados em um {@link DataSource}
	 * 
	 * @param documentPath
	 */
	public void readCSV(String documentPath) {
		int size = 0;
		try {
			Scanner sc = new Scanner(new FileReader(documentPath));
			sc.nextLine();

			while (sc.hasNext()) {
				String linha = sc.nextLine();
				stringManipulator(linha);
				size++;
				addToDataSource(id, sentiment, text);
				text = new StringBuilder();
			}
			sc.close();
			dataSource.setSize(size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Adiciona todos os dados coletados do csv para o {@link DataSource}
	 * 
	 * @param id
	 * @param sentiment
	 * @param text
	 */
	protected void addToDataSource(int id, int sentiment, StringBuilder text) {
		dataSource.getSentiment().put(id, sentiment);
		dataSource.getText().put(id, text.toString());
	}

	/**
	 * Método de manipulação de String, feito especialmente para o escopo do
	 * trabalho de IA.
	 * 
	 * @param test
	 *            representa uma linha do CSV a ser lido.
	 */
	/*
	 * FIXME se você procura reaproveitamento de código e boas práticas de
	 * programação, este NÃO é o lugar certo. Pas e lus.
	 */
	private void stringManipulator(String test) {
		int count = 0;

		for (int i = 0; i < test.length(); i++) {
			char c = test.charAt(i);
			if (count == 0) {
				StringBuilder idString = new StringBuilder();
				while (c != ',') {
					idString.append(c);
					i++;
					c = test.charAt(i);
				}

				id = Integer.parseInt(idString.toString());

				if (c == ',')
					count++;
			} else if (count == 1 && c != ',') {
				sentiment = Character.getNumericValue(c);
			} else if (c == ',' && count < 3) {
				count++;
				continue;
			} else if (count == 2) {
				while (c != ',') {
					i++;
					c = test.charAt(i);
				}

				if (c == ',')
					count++;
			} else {
				text.append(c);
			}

		}

	}

}
