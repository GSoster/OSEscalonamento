package br.com.core.escalonador;

public class Processo {

	private String nome;
	private int tempoChegada ;
	private int tempoExecucao;
	
	
	public Processo(String n,int tc, int te ){
		nome = n;
		tempoChegada = tc;
		tempoExecucao = te;
	}

	
	

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getTempoChegada() {
		return tempoChegada;
	}


	public void setTempoChegada(int tempoChegada) {
		this.tempoChegada = tempoChegada;
	}


	public int getTempoExecucao() {
		return tempoExecucao;
	}


	public void setTempoExecucao(int tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}
	
	
	
}
