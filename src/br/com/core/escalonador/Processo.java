package br.com.core.escalonador;

/**
 * @author Guilherme
 * 
 */
public class Processo implements Comparable<Processo> {

	private String nome;
	private int tempoChegada;
	private int tempoExecucao;
	private int tempoEspera;

	public Processo(String n, int tc, int te) {
		nome = n;
		tempoChegada = tc;
		tempoExecucao = te;
		tempoEspera = 0;
	}

	public Processo(String n, int tc, int te, int tesp) {
		nome = n;
		tempoChegada = tc;
		tempoExecucao = te;
		tempoEspera = tesp;
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

	public int getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	@Override
	public int compareTo(Processo paraComparar) {

		int compararTurnAround = ((Processo) paraComparar).getTempoExecucao();

		// ordem ascendente
		return this.tempoExecucao - compararTurnAround;
	}

}
