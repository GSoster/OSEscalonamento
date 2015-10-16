package br.com.core.escalonador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Principal {

	protected static int quantum; 
	protected static JFrame janelaPrinc; 
	protected static JTable table; 
	protected static int p = 0; 
	protected static int openJa = 0;
	private static JMenu menuPrincipal;
	private static JMenuBar barraMenu;
	private static JMenuItem itemAdicionar, itemRemover, itemCalcular, itemSobre;
	private String alerta = "Você deve inserir pelo menos 1 processo e/ou o quantum não pode ser vazio!";
	private String sobre = "                                                                  ======= Escalonamento de Processos =======\n Prof. Marcela Santos\n Alunos: Antonio, Sandro, Guilherme, Maicon\n\nFonte original obtido em: JavaFree.org\nDisponível em: <http://javafree.uol.com.br/topic-886249-Exemplo-de-Simulador-de-escalonamento-de-processos-de-SO.html>\nAcesso em 16 de outubro de 2015.";

	private static DefaultTableModel model; 
	private static JTextField tquantum; 

	public Principal() {

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
		model.addColumn("Prioridade");

		table = new JTable(model);

		JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.setHorizontalScrollBar(new JScrollBar(0));
		scrollTable.setBounds(50, 50, 700, 450);
		janelaPrinc.add(scrollTable);
	}

	private void eventosMenu() {
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
				if (linhas == -1 || colunas == -1) {
				} else {
					model.removeRow(linhas);
					p--;
				}
			}
		});
		
		itemCalcular.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				if (tquantum != null && !tquantum.getText().trim().isEmpty() && model.getRowCount() > 0) {
					quantum = Integer.parseInt(tquantum.getText());
					Resultado res = new Resultado();
				} else {
					JOptionPane.showMessageDialog(null, alerta, null, JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		itemSobre.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, sobre, null, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public static void adicionaLinha(Object nl[]) {
		model.addRow(nl);
	}

	private void inicializarMenu() {

		barraMenu = new JMenuBar();
		menuPrincipal = new JMenu("Simulador");
		itemAdicionar = new JMenuItem("Adicionar Processo");
		itemCalcular = new JMenuItem("Calcular");
		itemRemover = new JMenuItem("Remover Processo");
		itemSobre = new JMenuItem("Sobre");
		JLabel lquantum = new JLabel("Quantum:");
		lquantum.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		tquantum = new JTextField();
		lquantum.setBounds(50, 0, 80, 50);
		tquantum.setBounds(125, 15, 60, 20);
		janelaPrinc.add(lquantum);
		janelaPrinc.add(tquantum);
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
