package br.com.core.escalonador;

import java.awt.Color;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Resultado {  
	  
    JFrame jResultado;  
    Calcular calc;  
    int linhas;  
  
    public Resultado() {  
    	
        calc = new Calcular();  
        
        jResultado = new JFrame();  
        jResultado.setLayout(null);  
        jResultado.setTitle("Resultado");  
        jResultado.setSize(500, 750);  
        jResultado.setLocationRelativeTo(null);  
        
        calc.getFIFO();  
        calc.getPrioridade();  

        DefaultTableModel modelFIFO = new DefaultTableModel() {  
            @Override  
            public boolean isCellEditable(int row, int col) {  
                return false;  
            }  
        };
        
        DefaultTableModel modelSJF = new DefaultTableModel() {  
            @Override  
            public boolean isCellEditable(int row, int col) {  
                return false;  
            }  
        };
        
        DefaultTableModel modelPrio = new DefaultTableModel() {  
            @Override  
            public boolean isCellEditable(int row, int col) {  
                return false;  
            }  
        };
        
        DefaultTableModel modelRR = new DefaultTableModel() {  
            @Override  
            public boolean isCellEditable(int row, int col) {  
                return false;  
            }  
        };  

        modelFIFO.addColumn("Processo");  
        modelFIFO.addColumn("Tempo de Espera");  
        modelFIFO.addColumn("Tempo de Turnaround");  
  
        modelSJF.addColumn("Processo");  
        modelSJF.addColumn("Tempo de Espera");  
        modelSJF.addColumn("Tempo de Turnaround");  
  
        modelPrio.addColumn("Processo");  
        modelPrio.addColumn("Tempo de Espera");  
        modelPrio.addColumn("Tempo de Turnaround");  
  
        modelRR.addColumn("Processo");  
        modelRR.addColumn("Tempo de Espera");  
        modelRR.addColumn("Tempo de Turnaround");  
  
        linhas = Principal.table.getRowCount();  
  
        int contLinha = 0;  
        while (contLinha < linhas) {  
  
            modelFIFO.addRow(new Object[]{"P" + (contLinha + 1), Calcular.tEspFIFO[contLinha], Calcular.tTurFIFO[contLinha]});  
            modelPrio.addRow(new Object[]{"P" + (contLinha + 1), Calcular.tEspPrio[contLinha], Calcular.tTurPrio[contLinha]});  
            contLinha++;  
        }  

        JTable tableFIFO = new JTable(modelFIFO);  
        JTable tablePrio = new JTable(modelPrio);  
  
        JScrollPane stableFIFO = new JScrollPane(tableFIFO);  
        stableFIFO.setHorizontalScrollBar(new JScrollBar(0));  
        JScrollPane stablePrio = new JScrollPane(tablePrio);  
        stablePrio.setHorizontalScrollBar(new JScrollBar(0));  

        stableFIFO.setBounds(50, 50, 400, 250);  
        stablePrio.setBounds(50, 400, 400, 250);  

        JTextField temFIFO = new JTextField();  
        JTextField ttmFIFO = new JTextField();  
        JTextField temSJF = new JTextField();  
        JTextField ttmSJF = new JTextField();  
        JTextField temPrio = new JTextField();  
        JTextField ttmPrio = new JTextField();  
        JTextField temRR = new JTextField();  
        JTextField ttmRR = new JTextField();  
  
        temFIFO.setEditable(false);  
        ttmFIFO.setEditable(false);  
        temSJF.setEditable(false);  
        ttmSJF.setEditable(false);  
        temPrio.setEditable(false);  
        ttmPrio.setEditable(false);  
        temRR.setEditable(false);  
        ttmRR.setEditable(false);  
  
        temFIFO.setBounds(150, 315, 100, 20);  
        ttmFIFO.setBounds(370, 315, 100, 20);  
        temSJF.setBounds(650, 315, 100, 20);  
        ttmSJF.setBounds(870, 315, 100, 20);  
        temPrio.setBounds(150, 665, 100, 20);  
        ttmPrio.setBounds(370, 665, 100, 20);  
        temRR.setBounds(650, 665, 100, 20);  
        ttmRR.setBounds(870, 665, 100, 20);  
  
        temFIFO.setText("" + NumberFormat.getNumberInstance().format(calc.getEsperaFIFO()));  
        ttmFIFO.setText("" + NumberFormat.getNumberInstance().format(calc.getTurnaroundFIFO()));  
        temPrio.setText("" + NumberFormat.getNumberInstance().format(calc.getEsperaPrioridade()));  
        ttmPrio.setText("" + NumberFormat.getNumberInstance().format(calc.getTurnaroundPrioridade()));  
  
        JLabel ltemFIFO = new JLabel("T.Espera Médio");  
        JLabel lttmFIFO = new JLabel("T.Turnaround Médio");  
        JLabel ltemPrio = new JLabel("T.Espera Médio");  
        JLabel lttmPrio = new JLabel("T.Turnaround Médio");  
  
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
        jResultado.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
} 