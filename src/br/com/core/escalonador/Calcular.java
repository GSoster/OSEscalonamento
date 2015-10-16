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
	double[][] mSJF;
	double[][] mPrioridade;
	double[][] mRR;
	static double[] tEspPrio;
	static double[] tTurPrio;
	static double[] tEspFIFO;
	static double[] tTurFIFO;
	static double[] tEspSJF;
	static double[] tTurSJF;
	static double[] tEspRR;
	static double[] tTurRR;
	double tmeFIFO = 0;
	double tmeSJF = 0;
	double tmePrio = 0;
	double tmeRR = 0;
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
		mSJF = new double[linhas][3];
		mPrioridade = new double[linhas][3];
		mRR = new double[linhas][3];

		while (i < linhas) {
			mD[i][0] = Double.parseDouble((String) Principal.table.getValueAt(i, 1));
			mFIFO[i][0] = Double.parseDouble((String) Principal.table.getValueAt(i,
					1));
			mSJF[i][0] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 1));
			mPrioridade[i][0] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 1));
			mRR[i][0] = Double
					.parseDouble((String) Principal.table.getValueAt(i, 1));
			mD[i][1] = Double.parseDouble((String) Principal.table.getValueAt(i, 2));
			mFIFO[i][1] = Double.parseDouble((String) Principal.table.getValueAt(i,
					2));
			mSJF[i][1] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 2));
			mPrioridade[i][1] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 2));
			mRR[i][1] = Double
					.parseDouble((String) Principal.table.getValueAt(i, 2));
			mD[i][2] = Double.parseDouble((String) Principal.table.getValueAt(i, 3));
			mFIFO[i][2] = Double.parseDouble((String) Principal.table.getValueAt(i,
					3));
			mSJF[i][2] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 3));
			mPrioridade[i][2] = Double.parseDouble((String) Principal.table
					.getValueAt(i, 3));
			mRR[i][2] = Double
					.parseDouble((String) Principal.table.getValueAt(i, 3));
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

	public void getSJF() {
		int i = 0;
		int pmt = 0;
		int contSJF = 0;
		int[] escSJF = new int[(int) tempoTotal];
		while (contSJF < tempoTotal) {
			int minTime = 9999;
			for (i = 0; i < linhas; i++) {
				if (mSJF[i][1] < minTime && mSJF[i][1] > 0
						&& mSJF[i][0] <= contSJF) {
					minTime = (int) mSJF[i][1];
					pmt = i;
				}
			}
			mSJF[pmt][1] = mSJF[pmt][1] - 1;
			escSJF[contSJF] = pmt;
			contSJF++;
		}

		contSJF = 0;
		i = 0;
		tEspSJF = new double[(int) linhas];
		tTurSJF = new double[(int) linhas];
		int contProcesso = 0;

		while (contProcesso < linhas) {
			if (escSJF[i] == contProcesso) {
				tEspSJF[contProcesso] = i - mD[contProcesso][0];
				tTurSJF[contProcesso] = tEspSJF[contProcesso]
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

	public void getRR() {
		int i = 0;
		int cQ = 1;
		int contRR = 0;
		int[] escRR = new int[(int) tempoTotal];
		while (contRR < tempoTotal) {
			if (mRR[i][1] > 0 && mRR[i][0] <= contRR) {
				mRR[i][1] = mRR[i][1] - 1;
				escRR[contRR] = i;
				contRR++;
				cQ++;
			} else {
				cQ = 1;
				if (i + 1 > linhas) {
					i = 0;
				} else {
					i++;
				}
			}
			if (cQ > quantum) {
				cQ = 1;
				if (i + 1 > linhas) {
					i = 0;
				} else {
					i++;
				}
			}
			if (i + 1 > linhas) {
				i = 0;
			}

		}

		contRR = 0;
		i = 0;
		tEspRR = new double[(int) linhas];
		tTurRR = new double[(int) linhas];
		int contProcesso = 0;
		while (contProcesso < linhas) {
			if (escRR[i] == contProcesso) {
				tEspRR[contProcesso] = i - mD[contProcesso][0];
				tTurRR[contProcesso] = tEspRR[contProcesso]
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
		if (tmeFIFO < tmeSJF && tmeFIFO < tmePrio && tmeFIFO < tmeRR) {
			c = 1;
		}
		if (tmeSJF < tmeFIFO && tmeSJF < tmePrio && tmeSJF < tmeRR) {
			c = 2;
		}
		if (tmePrio < tmeFIFO && tmePrio < tmeSJF && tmePrio < tmeRR) {
			c = 3;
		}
		if (tmeRR < tmeFIFO && tmeRR < tmeSJF && tmeRR < tmePrio) {
			c = 4;
		}
		return c;

	}

	public double getEsperaFIFO() {

		for (int i = 0; i < tEspFIFO.length; i++) {
			tmeFIFO = tEspFIFO[i] + tmeFIFO;
		}
		return tmeFIFO / linhas;
	}

	public double getEsperaSJF() {

		for (int i = 0; i < tEspSJF.length; i++) {
			tmeSJF = tEspSJF[i] + tmeSJF;
		}
		return tmeSJF / linhas;
	}

	public double getEsperaPrioridade() {

		for (int i = 0; i < tEspPrio.length; i++) {
			tmePrio = tEspPrio[i] + tmePrio;
		}
		return tmePrio / linhas;
	}

	public double getEsperaRR() {
		for (int i = 0; i < tEspRR.length; i++) {
			tmeRR = tEspRR[i] + tmeRR;
		}
		return tmeRR / linhas;
	}

	public double getTurnaroundFIFO() {
		return (tmeFIFO + tempoTotal) / linhas;
	}

	public double getTurnaroundSJF() {
		return (tmeSJF + tempoTotal) / linhas;
	}

	public double getTurnaroundPrioridade() {
		return (tmePrio + tempoTotal) / linhas;
	}

	public double getTurnaroundRR() {
		return (tmeRR + tempoTotal) / linhas;
	}

}
