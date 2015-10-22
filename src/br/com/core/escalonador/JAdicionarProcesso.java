package br.com.core.escalonador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JAdicionarProcesso {

	static JFrame jAdicionar;
	static Object[] nl;
	JTextField tprocesso;
	JTextField ttc;
	JTextField tte;
	JTextField tprioridade;

	JLabel lprocesso;
	JLabel ltc;
	JLabel lte;
	JLabel lprioridade;

	JButton bok;
	JButton bcancel;

	public JAdicionarProcesso() {

		Principal.p++;

		this.criarElementos();
		this.posicionarElementos();
		this.adicionarElementos();

		ttc.requestFocus();

		bok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nl = new Object[] { tprocesso.getText(), ttc.getText(),
						tte.getText(), tprioridade.getText() };
				// trabalhando com processos
				Processo p = new Processo(tprocesso.getText(), Integer
						.parseInt(ttc.getText()), Integer.parseInt(tte
						.getText()));
				Principal.listaProcessos.add(p);
				Principal.fifoProcessos.add(p);
				Principal.ordenarProcessos();
				// fim
				Principal.adicionaLinha(nl);
				Principal.openJa = 0;
				jAdicionar.dispose();
			}
		});

		bcancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Principal.p--;
				Principal.openJa = 0;
				jAdicionar.dispose();

			}
		});

		jAdicionar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jAdicionar.setVisible(true);
	}

	/**
	 * ####################### DAQUI PRA BAIXO SO GRAFICO ##################3
	 * PODE IGNORAR
	 */

	/**
	 * Instancia elementos graficos
	 */
	private void criarElementos() {
		jAdicionar = new JFrame();
		jAdicionar.setTitle("Adicionar Processo");
		jAdicionar.setLayout(null);
		jAdicionar.setSize(400, 300);
		jAdicionar.setLocationRelativeTo(Principal.janelaPrinc);

		lprocesso = new JLabel("Processo");
		ltc = new JLabel("Tempo de Chegada");
		lte = new JLabel("Tempo de Execução");
		lprioridade = new JLabel("Prioridade");

		tprocesso = new JTextField();
		ttc = new JTextField();
		tte = new JTextField();
		tprioridade = new JTextField();

		bok = new JButton("OK");
		bcancel = new JButton("CANCELAR");
	}

	/**
	 * Define configuracoes graficas dos elementos.
	 */
	private void posicionarElementos() {
		lprocesso.setBounds(30, 30, 100, 20);
		ltc.setBounds(30, 70, 150, 20);
		lte.setBounds(30, 110, 150, 20);
		lprioridade.setBounds(30, 150, 100, 20);

		tprocesso.setBounds(170, 30, 80, 20);
		ttc.setBounds(170, 70, 80, 20);
		tte.setBounds(170, 110, 80, 20);
		tprioridade.setBounds(170, 150, 80, 20);

		bok.setBounds(160, 220, 100, 30);
		bcancel.setBounds(280, 220, 100, 30);

		tprocesso.setEditable(false);
		tprocesso.setText("Processo " + Principal.p);
	}

	/**
	 * Adiciona elementos ao Jframe
	 */
	private void adicionarElementos() {
		jAdicionar.add(lprocesso);
		jAdicionar.add(ltc);
		jAdicionar.add(lte);
		jAdicionar.add(lprioridade);
		jAdicionar.add(tprocesso);
		jAdicionar.add(ttc);
		jAdicionar.add(tte);
		jAdicionar.add(tprioridade);
		jAdicionar.add(bok);
		jAdicionar.add(bcancel);
	}

}
