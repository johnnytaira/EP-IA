package br.com.patricia.data;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe que contem os informações essenciais sobre os dados de entrada.
 * @author Johnny Taira
 *
 */

public class DataSource {
	
	private List<Integer> sentiment;
	private List<Integer> id;
	private List<String> source;
	private List<String> text;
	
	public DataSource(){
		this.sentiment = new LinkedList<Integer>();
		this.id = new LinkedList<Integer>();
		this.source = new LinkedList<String>();
		this.text = new LinkedList<String>();
	}

	public List<Integer> getSentiment() {
		return sentiment;
	}

	public void setSentiment(List<Integer> sentiment) {
		this.sentiment = sentiment;
	}

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	public List<String> getSource() {
		return source;
	}

	public void setSource(List<String> source) {
		this.source = source;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	
	

}
