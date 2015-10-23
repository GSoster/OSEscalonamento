package br.com.core.escalonador;

import java.awt.Color;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Resultado {

	JFrame jResultado;
	Calcular calc;
	int linhas;

	// tabelas
	DefaultTableModel modelFIFO;
	DefaultTableModel modelPrio;
	// texts
	JTextField temFIFO;// TEMPO ESPERA MEDIA FIFO
	JTextField ttmFIFO;// TEMPO EXECUCAO MEDIO FIFO
	JTextField temPrio;// TEMPO ESPERA MEDIA PRIORIDADE
	JTextField ttmPrio;// TEMPO EXECUCAO MEDIO PRIORIDADE

	public Resultado() {

		calc = new Calcular();

		jResultado = new JFrame();
		jResultado.setLayout(null);
		jResultado.setTitle("Resultado");
		jResultado.setSize(500, 750);
		jResultado.setLocationRelativeTo(null);

		calc.getFIFO();
		calc.getPrioridade();

		this.criarTabelas();
		this.montarTabelas();
		this.preencherTabelas();

		this.criarCamposMedios();

		this.definirTemposMedios();
		
		
		JTable tableFIFO = new JTable(modelFIFO);
		JTable tablePrio = new JTable(modelPrio);

		JScrollPane stableFIFO = new JScrollPane(tableFIFO);
		stableFIFO.setHorizontalScrollBar(new JScrollBar(0));
		JScrollPane stablePrio = new JScrollPane(tablePrio);
		stablePrio.setHorizontalScrollBar(new JScrollBar(0));

		stableFIFO.setBounds(50, 50, 400, 250);
		stablePrio.setBounds(50, 400, 400, 250);

		//MEDIOS AQUI

		JLabel ltemFIFO = new JLabel("T.Espera Médio");
		JLabel lttmFIFO = new JLabel("T.Execução Médio");// era turnAround
		JLabel ltemPrio = new JLabel("T.Espera Médio");
		JLabel lttmPrio = new JLabel("T.Execução Médio");// era turnAround

		JLabel lFIFO = new JLabel("FIFO");
		JLabel lPrio = new JLabel("Prioridade");

		ltemFIFO.setBounds(50, 315, 100, 20);
		lttmFIFO.setBounds(250, 315, 120, 20);
		ltemPrio.setBounds(50, 665, 100, 20);
		lttmPrio.setBounds(250, 665, 120, 20);
		lFIFO.setBounds(50, 30, 100, 20);
		lPrio.setBounds(50, 380, 100, 20);

		int posicao = calc.getMelhor();

		if (posicao == 1) {
			stableFIFO.setBorder(BorderFactory.createLineBorder(Color.RED));
		}

		if (posicao == 3) {
			stablePrio.setBorder(BorderFactory.createLineBorder(Color.RED));
		}

		jResultado.add(stableFIFO);
		jResultado.add(stablePrio);
		jResultado.add(temFIFO);
		jResultado.add(ttmFIFO);
		jResultado.add(temPrio);
		jResultado.add(ttmPrio);
		jResultado.add(ltemFIFO);
		jResultado.add(lttmFIFO);
		jResultado.add(ltemPrio);
		jResultado.add(lttmPrio);
		jResultado.add(lFIFO);
		jResultado.add(lPrio);
		jResultado.setVisible(true);
		// jResultado.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * calcula o tempo de espera de cada processo e preenche a tabela com as
	 * informacoes necessarias de cada processo
	 */
	private void preencherTabelas() {
		// ############# FIFO
		int tempoExecucaoAnterior = 0;
		int tempoEspera = 0;
		for (Processo p : Principal.fifoProcessos) {
			tempoEspera = p.getTempoChegada() - tempoExecucaoAnterior;
			if (tempoEspera < 0) {
				tempoEspera = 0;
			}
			modelFIFO.addRow(new Object[] { p.getNome(), tempoEspera,
					p.getTempoExecucao() });
			tempoExecucaoAnterior = p.getTempoExecucao();
		}
		// ############## FIM DO FIFO

		Principal.ordenarProcessos();
		for (Processo p : Principal.listaProcessos) {
			modelPrio.addRow(new Object[] { p.getNome(), "tempo espera",
					p.getTempoExecucao() });
		}

	}

	private void definirTemposMedios() {
		// FIFO
		// TEMPO EXECUCAO
		int tempoTotalExecucao = 0, tempoMedioExecucao;
		for (Processo p : Principal.fifoProcessos) {
			tempoTotalExecucao += p.getTempoExecucao();
		}
		tempoMedioExecucao = tempoTotalExecucao
				/ Principal.fifoProcessos.size();
		ttmFIFO.setText("" + tempoMedioExecucao);// tempo medio execucao

		// TEMPO ESPERA
		int tempoEsperaTotal = 0;
		int tempoEsperaCalculo = 0;
		Processo ultimoProcesso = null;
		for (Processo p : Principal.fifoProcessos) {
			if (ultimoProcesso == null) {
				ultimoProcesso = p;
				p.setTempoEspera(0);
			} else {
				tempoEsperaCalculo = (p.getTempoChegada() + ultimoProcesso
						.getTempoEspera()) - ultimoProcesso.getTempoExecucao();
				p.setTempoEspera(tempoEsperaCalculo);
			}
			tempoEsperaTotal += tempoEsperaCalculo;
		}
		temFIFO.setText(""
				+ (tempoEsperaTotal / Principal.fifoProcessos.size()));// tempo
																		// medio
																		// espera

		// PRIORIDADE
		tempoMedioExecucao = tempoTotalExecucao = 0;
		for (Processo p : Principal.listaProcessos) {
			tempoTotalExecucao += p.getTempoExecucao();
		}
		tempoMedioExecucao = tempoTotalExecucao
				/ Principal.fifoProcessos.size();		
		ttmPrio.setText("" + tempoMedioExecucao);// tempo medio execucao

		//TEMPO ESPERA
		for (Processo p : Principal.listaProcessos) {
			if (ultimoProcesso == null) {
				ultimoProcesso = p;
				p.setTempoEspera(0);
			} else {
				tempoEsperaCalculo = (p.getTempoChegada() + ultimoProcesso
						.getTempoEspera()) - ultimoProcesso.getTempoExecucao();
				p.setTempoEspera(tempoEsperaCalculo);
			}
			tempoEsperaTotal += tempoEsperaCalculo;
		}
		temPrio.setText(""+tempoEsperaTotal / Principal.listaProcessos.size());// tempo medio espera
		
		/*
		 * temFIFO.setText("" +
		 * NumberFormat.getNumberInstance().format(calc.getEsperaFIFO()));
		 * ttmFIFO.setText("" + NumberFormat.getNumberInstance().format(
		 * calc.getTurnaroundFIFO())); temPrio.setText("" +
		 * NumberFormat.getNumberInstance().format(
		 * calc.getEsperaPrioridade())); ttmPrio.setText("" +
		 * NumberFormat.getNumberInstance().format(
		 * calc.getTurnaroundPrioridade()));
		 */
	}

	/**
	 * Preenche as tabelas com as informacoes necessarias. versao antiga!!
	 * 
	 * @deprecated
	 */
	private void _preencherTabelas() {
		int contLinha = 0;
		while (contLinha < linhas) {

			modelFIFO
					.addRow(new Object[] { "P" + (contLinha + 1),
							Calcular.tEspFIFO[contLinha],
							Calcular.tTurFIFO[contLinha] });
			modelPrio
					.addRow(new Object[] { "P" + (contLinha + 1),
							Calcular.tEspPrio[contLinha],
							Calcular.tTurPrio[contLinha] });
			contLinha++;
		}
	}

	/**
	 * #######################################################################
	 * #######################################################################
	 * ####################### DAQUI PRA BAIXO SO GRAFICO ####################
	 * PODE IGNORAR (com excecao do main..)
	 */

	/**
	 * cria campos como Tempo medio, etc..
	 */
	private void criarCamposMedios() {
		temFIFO = new JTextField();// TEMPO ESPERA MEDIA FIFO
		ttmFIFO = new JTextField();// TEMPO EXECUCAO MEDIO FIFO
		temPrio = new JTextField();// TEMPO ESPERA MEDIA PRIORIDADE
		ttmPrio = new JTextField();// TEMPO EXECUCAO MEDIO PRIORIDADE

		temFIFO.setEditable(false);
		ttmFIFO.setEditable(false);
		temPrio.setEditable(false);
		ttmPrio.setEditable(false);

		temFIFO.setBounds(150, 315, 100, 20);
		ttmFIFO.setBounds(370, 315, 100, 20);
		temPrio.setBounds(150, 665, 100, 20);
		ttmPrio.setBounds(370, 665, 100, 20);
	}

	/**
	 * cria instancia das tabelas
	 */
	private void criarTabelas() {
		modelFIFO = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		/*
		 * modelSJF = new DefaultTableModel() {
		 * 
		 * @Override public boolean isCellEditable(int row, int col) { return
		 * false; } };
		 */

		modelPrio = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		/*
		 * modelRR = new DefaultTableModel() {
		 * 
		 * @Override public boolean isCellEditable(int row, int col) { return
		 * false; } };
		 */
	}

	/**
	 * Adiciona colunas as tabelas
	 */
	private void montarTabelas() {
		modelFIFO.addColumn("Processo");
		modelFIFO.addColumn("Tempo de Espera");
		modelFIFO.addColumn("Tempo de Execução");

		/*
		 * modelSJF.addColumn("Processo");
		 * modelSJF.addColumn("Tempo de Espera");
		 * modelSJF.addColumn("Tempo de Turnaround");
		 */

		modelPrio.addColumn("Processo");
		modelPrio.addColumn("Tempo de Espera");
		modelPrio.addColumn("Tempo de Execução");

		/*
		 * modelRR.addColumn("Processo"); modelRR.addColumn("Tempo de Espera");
		 * modelRR.addColumn("Tempo de Turnaround");
		 */

		linhas = Principal.table.getRowCount();
	}
}