package br.com.patricia.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * Classe respons�vel por realizar a leitura do arquivo .csv que servir� como exemplos j�
 * rotulados para an�lise de sentimentos. Ao menos a princ�pio, todos os dados s�o armazenados
 * em {@link List} ({@code List} de inteiros, {@code List} de sentimentos e {@code List} de
 * textos a serem analisados).
 * @author Johnny Taira
 *
 */
public class Reader {
	
	private DataSource dataSource;
	
	public Reader(DataSource datasource){
		this.dataSource = datasource;
	}
	
	
	/**
	 * L� um csv, dado um caminho para leitura do arquivo. Os dados s�o armazenados em um
	 * {@link DataSource}
	 * @param documentPath
	 */
	public void readCSV(String documentPath){
		int size = 0;
		try {
			Scanner sc = new Scanner(new FileReader(documentPath));
			sc.nextLine();
			
			while(sc.hasNext()){
				String linha = sc.nextLine();
				stringManipulator(linha);
				size ++;
			}
		
			dataSource.setSize(size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	/**
	 * M�todo de manipula��o de String, feito especialmente para o escopo do trabalho de IA.
	 
	 * @param test representa uma linha do CSV a ser lido.
	 */
	/*FIXME se voc� procura reaproveitamento de c�digo e boas
	 * pr�ticas de programa��o, este N�O � o lugar certo. Pas e lus.
	 */
	private void stringManipulator(String test){ 
		int count = 0;
		Integer id = 0;
		Integer sentiment = 0;
		
		
		StringBuilder text = new StringBuilder();
		
		
		for(int i = 0; i < test.length(); i++){
			char c = test.charAt(i);
			if (count == 0){
				StringBuilder idString = new StringBuilder();
				while(c!= ','){
					idString.append(c);
					i++;
					c = test.charAt(i);
				}
				
				id = Integer.parseInt(idString.toString());
				
				if(c == ',')
					count++;
			}else if (count == 1 && c != ','){
				sentiment = Character.getNumericValue(c);
				//count++;
			}else if (c == ',' && count < 3){
				count++;
				continue;
			}else if (count == 2){
				while(c != ','){
					i++;
					c = test.charAt(i);
				}
				
				if (c == ',')
					count++;
			}else{
				text.append(c);
			}
			
		}
		
		dataSource.getSentiment().put(id, sentiment);
		dataSource.getText().put(id, text.toString());
		
	}
	
	
}
