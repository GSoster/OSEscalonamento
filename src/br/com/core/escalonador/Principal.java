package br.com.core.escalonador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
//import javax.swing.JLabel; n utilizado
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import javax.swing.JTextField; n utilizado
import javax.swing.table.DefaultTableModel;

public class Principal {

	protected static int quantum = 1;
	protected static JFrame janelaPrinc;
	protected static JTable table;
	protected static int p = 0; // numero de processos
	protected static int openJa = 0;
	private static JMenu menuPrincipal;
	private static JMenuBar barraMenu;
	private static JMenuItem itemAdicionar, itemRemover, itemCalcular,
			itemSobre;
	// tornar Final ambas referencias abaixo
	private final String alerta = "Você deve inserir pelo menos 1 processo e/ou o quantum não pode ser vazio!";
	private final String sobre = "                                                                  ======= Escalonamento de Processos =======\n Prof. Marcela Santos\n Alunos: Antonio, Sandro, Guilherme, Maicon\n\nFonte original obtido em: JavaFree.org\nDisponÃ­vel em: <http://javafree.uol.com.br/topic-886249-Exemplo-de-Simulador-de-escalonamento-de-processos-de-SO.html>\nAcesso em 16 de outubro de 2015.";

	private static DefaultTableModel model;
	// private static JTextField tquantum;

	// trabalhando com processos
	//lista vai ordenar por menor tempo, fifo mantem ordem de chegada
	public static ArrayList<Processo> listaProcessos,fifoProcessos;// estatico p/acessar de
	// fora diretamente
	

	public Principal() {

		listaProcessos = new ArrayList<>();
		fifoProcessos = new ArrayList<>();
		janelaPrinc = new JFrame();
		janelaPrinc.setLayout(null);
		janelaPrinc.setTitle("Simulação de escalonador de processos");
		janelaPrinc.setSize(800, 600);
		inicializarMenu();
		configuraMenu();
		adicionarMenu();
		inicializarTabela();
		eventosMenu();
		janelaPrinc.setVisible(true);
		janelaPrinc.setLocationRelativeTo(null);
		janelaPrinc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("serial")
	private void inicializarTabela() {
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		model.addColumn("Processo");
		model.addColumn("Tempo de Chegada");
		model.addColumn("Tempo de Execução");
		model.addColumn("Prioridade");//(nao exibiremos ja q a prioridade e o tempo de chegada)

		table = new JTable(model);

		JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.setHorizontalScrollBar(new JScrollBar(0));
		scrollTable.setBounds(50, 20, 700, 500);
		janelaPrinc.add(scrollTable);
	}

	private void eventosMenu() {
		// ADICIONANDO PROCESSO
		itemAdicionar.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				if (openJa == 0) {
					JAdicionarProcesso ja = new JAdicionarProcesso();
					openJa = 1;
				}
			}
		});

		itemRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int colunas = table.getSelectedColumn();
				int linhas = table.getSelectedRow();
				if (linhas == -1 || colunas == -1) {// alterar para: (linhas !=
					// -1 || colunas != -1) ?
					// aÃ§Ã£o (model....);
				} else {
					model.removeRow(linhas);
					p--;
				}
			}
		});

		/*
		 * CALCULANDO! /alterar aqui... a classe RESULTADO apenas monta a
		 * informacao visualmente Calcular eh que eh deve ser alterada.
		 */
		itemCalcular.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				// if (tquantum != null && !tquantum.getText().trim().isEmpty()
				// && model.getRowCount() > 0) {
				if (model.getRowCount() > 0) {
					// quantum = Integer.parseInt(tquantum.getText());
					Resultado res = new Resultado();// lembrar de alterar o
					// construtor de Calcular.
				} else {
					JOptionPane.showMessageDialog(null, alerta, null,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		itemSobre.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, sobre, null,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public static void adicionaLinha(Object nl[]) {
		model.addRow(nl);
	}

	/**
	 * ordena os processos por prioridade (menor tempo de execucao TURNAROUND)
	 */
	public static void ordenarProcessos() {
		Collections.sort(listaProcessos);
		for (Processo p : Principal.listaProcessos) {
			System.out.println(p.getNome());
		}
	}

	public void atualizarGrid(){
		
	}

	
	/**
	 * ####################### DAQUI PRA BAIXO SO GRAFICO ##################3
	 * PODE IGNORAR (com excecao do main..)
	 */

	private void inicializarMenu() {
		barraMenu = new JMenuBar();
		menuPrincipal = new JMenu("Simulador");
		itemAdicionar = new JMenuItem("Adicionar Processo");
		itemCalcular = new JMenuItem("Calcular");
		itemRemover = new JMenuItem("Remover Processo");
		itemSobre = new JMenuItem("Sobre");
	}

	private void adicionarMenu() {
		janelaPrinc.setJMenuBar(barraMenu);
		barraMenu.add(menuPrincipal);
		menuPrincipal.add(itemAdicionar);
		menuPrincipal.add(itemRemover);
		menuPrincipal.add(itemCalcular);
		menuPrincipal.add(itemSobre);
	}

	private void configuraMenu() {
		menuPrincipal.setMnemonic('S');
		itemAdicionar.setMnemonic('A');
		itemCalcular.setMnemonic('C');
		itemRemover.setMnemonic('R');
		itemSobre.setMnemonic('S');
	}

	public static void main(String[] args) {
		new Principal();
	}
}
