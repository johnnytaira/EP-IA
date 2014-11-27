package br.com.patricia.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Implementação de {@link Reader} específica para a remoção de stop-words do conjunto de dados.
 * Sobrescreve o método {@link #addToDataSource(int, int, StringBuilder) addToDataSource(id, sentiment, text)
 * @author Johnny Taira
 *
 */
public class StopWordReader extends Reader {

	private String path;

	private StopWordReader(DataSource datasource) {
		super(datasource);
	}

	public StopWordReader(DataSource dataSource, String path) {
		this(dataSource);
		this.path = path;
		createAndReadStopWords();
	}

	@Override
	protected void addToDataSource(int id, int sentiment, StringBuilder text) {

		String stringedText = text.toString();
		Scanner sc = new Scanner(stringedText);
		List<String> fraseBuffer = new ArrayList<String>();
		while (sc.hasNext()) {
			String palavra = sc.next();
			if (!stopWords.contains(palavra.toLowerCase())) {
				fraseBuffer.add(palavra);
			}
		}
		sc.close();

		StringBuilder builder = new StringBuilder();
		for (String cu : fraseBuffer) {
			builder.append(cu + " ");
		}

		dataSource.getSentiment().put(id, sentiment);
		dataSource.getText().put(id, builder.toString());
	}

	private void createAndReadStopWords() {
		File file = new File(path);
		stopWords = new HashSet<String>();
		try {
			file.createNewFile();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				stopWords.add(line);
				line = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Set<String> stopWords;

}
