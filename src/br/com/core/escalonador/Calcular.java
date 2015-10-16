/*Calcula FIFO,SJF,PRIORIDADE e RR 
 * cria matrizes a partir da table e faz escalonamentos 
 */

//turnaround que é o tempo transcorrido desde o momento em que o software entra e o instante em que termina sua execução;

package br.com.core.escalonador;

public class Calcular {
	int quantum = 0;
	double tempoTotal = 0;
	double[][] mD;
	double[][] mFIFO;
	double[][] mPrioridade;
	static double[] tEspPrio;
	static double[] tTurPrio;
	static double[] tEspFIFO;
	static double[] tTurFIFO;
	double tmeFIFO = 0;
	double tmePrio = 0;
	int linhas;

	public Calcular() {

		linhas = Principal.table.getRowCount();

		double ttemp;
		int clinha = 0;
		int i = 0;

		while (clinha < linhas) {
			ttemp = Double.parseDouble((String) Principal.table
					.getValueAt(clinha, 2));
			tempoTotal = tempoTotal + ttemp;

			clinha++;
		}

		mFIFO = new double[linhas][3];
		mD = new double[linhas][3];
		mPrioridade = new double[linhas][3];

		while (i < linhas) {
			mD[i][0] = Double.parseDouble((String) Principal.table.getValueAt(i, 1));
			mFIFO[i][0] = Double.parseDouble((String) Principal.table.getValueAt(i,
					1));
			mD[i][1] = Double.parseDouble((String) Principal.table.getValueAt(i, 2));
			mFIFO[i][1] = Double.parseDouble((String) Principal.table.getValueAt(i,
					2));
			mPrioridade[i][1] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 2));
			mD[i][2] = Double.parseDouble((String) Principal.table.getValueAt(i, 3));
			mFIFO[i][2] = Double.parseDouble((String) Principal.table.getValueAt(i,
					3));
			mPrioridade[i][2] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 3));
			i++;
		}
		quantum = Principal.quantum;

	}

	public void getFIFO() {
		int contFIFO = 0;
		int i = 0;

		int[] escFIFO = new int[(int) tempoTotal];
		while (contFIFO < tempoTotal) {
			while (mFIFO[i][1] > 0) {
				mFIFO[i][1] = mFIFO[i][1] - 1;
				escFIFO[contFIFO] = i;
				contFIFO++;
			}
			i++;
		}

		contFIFO = 0;
		i = 0;
		tEspFIFO = new double[(int) linhas];
		tTurFIFO = new double[(int) linhas];
		int contProcesso = 0;

		while (contProcesso < linhas) {
			if (escFIFO[i] == contProcesso) {
				tEspFIFO[contProcesso] = i - mD[contProcesso][0];
				tTurFIFO[contProcesso] = tEspFIFO[contProcesso]
						+ mD[contProcesso][1];
				contProcesso++;
			}
			
			if (i + 1 >= tempoTotal) {
				i = 0;
			} else {
				i++;
			}
		}

	}

	public void getPrioridade() {
		int i = 0;

		int pmp = 0;
		int contPrio = 0;
		int[] escPrio = new int[(int) tempoTotal];

		while (contPrio < tempoTotal) {
			int minPrio = 9999;
			for (i = 0; i < linhas; i++) {
				if (mPrioridade[i][2] < minPrio && mPrioridade[i][1] > 0
						&& mPrioridade[i][0] <= contPrio) {
					minPrio = (int) mPrioridade[i][2];
					pmp = i;
				}
			}

			mPrioridade[pmp][1] = mPrioridade[pmp][1] - 1;
			escPrio[contPrio] = pmp;
			contPrio++;
		}

		contPrio = 0;
		i = 0;
		tEspPrio = new double[(int) linhas];
		tTurPrio = new double[(int) linhas];
		int contProcesso = 0;
		while (contProcesso < linhas) {
			if (escPrio[i] == contProcesso) {
				tEspPrio[contProcesso] = i - mD[contProcesso][0];
				tTurPrio[contProcesso] = tEspPrio[contProcesso]
						+ mD[contProcesso][1];
				contProcesso++;
			}

			if (i + 1 >= tempoTotal) {
				i = 0;
			} else {
				i++;
			}
		}

	}

	public int getMelhor() {
		
		int c = 0;
		if (tmeFIFO < tmePrio) {
			c = 1;
		}
		
		if (tmePrio < tmeFIFO) {
			c = 2;
		}
		
		return c;
	}

	public double getEsperaFIFO() {

		for (int i = 0; i < tEspFIFO.length; i++) {
			tmeFIFO = tEspFIFO[i] + tmeFIFO;
		}
		return tmeFIFO / linhas;
	}

	public double getEsperaPrioridade() {

		for (int i = 0; i < tEspPrio.length; i++) {
			tmePrio = tEspPrio[i] + tmePrio;
		}
		return tmePrio / linhas;
	}

	public double getTurnaroundFIFO() {
		return (tmeFIFO + tempoTotal) / linhas;
	}

	public double getTurnaroundPrioridade() {
		return (tmePrio + tempoTotal) / linhas;
	}
}
