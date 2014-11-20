package br.com.patricia.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que contem os informações essenciais sobre os dados de entrada.
 * @author Johnny Taira
 *
 */

public class DataSource {
	
	private Map<Integer, Integer> sentiment;
	private Map<Integer, String> text;
	
	public DataSource(){
		this.sentiment = new HashMap<Integer, Integer>();
		this.text = new HashMap<Integer, String>();
	}

	public Map<Integer, Integer> getSentiment() {
		return sentiment;
	}

	public void setSentiment(Map<Integer, Integer> sentiment) {
		this.sentiment = sentiment;
	}

	public Map<Integer, String> getText() {
		return text;
	}

	public void setText(Map<Integer, String> text) {
		this.text = text;
	}
	
	

}
