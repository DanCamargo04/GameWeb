package pack;

// GRAFO NÃO DIRIGIDO

public class TGrafoND {

	private int n; // quantidade de vértices
	private int m; // quantidade de arestas
	private int adj[][]; // matriz de adjacência

	// Métodos Públicos
	public TGrafoND(int n) { // construtor
		this.n = n;
		// No início dos tempos não há arestas
		this.m = 0;
		// alocação da matriz do TGrafo
		this.adj = new int[n][n];

		// Inicia a matriz com zeros
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				this.adj[i][j] = 0;
	}

	// Insere uma aresta no Grafo tal que
	// v é adjacente a w e vice versa
	public void insereAGND(int v, int w) {
		// testa se nao temos a aresta
		if (adj[v][w] == 0) {
			adj[v][w] = 1;
			adj[w][v] = 1;
			m++; // atualiza qtd arestas
		}
	}

	// remove uma aresta v->w e w->v do Grafo
	public void removeAGND(int v, int w) {
		// testa se temos a aresta
		if (adj[v][w] == 1) {
			adj[v][w] = 0;
			adj[w][v] = 0;
			m--; // atualiza qtd arestas
		}
	}

	// Apresenta o Grafo contendo
	// número de vértices, arestas
	// e a matriz de adjacência obtida
	public void showGND() {
		System.out.println("n: " + n);
		System.out.println("m: " + m);
		for (int i = 0; i < n; i++) {
			System.out.print("\n");
			for (int w = 0; w < n; w++)
				if (adj[i][w] == 1)
					System.out.print("Adj[" + i + "," + w + "]= 1" + " ");
				else
					System.out.print("Adj[" + i + "," + w + "]= 0" + " ");
		}
		System.out.println("\n\nfim da impressao do grafo.\n");
	}
	
	// MEUS MÉTODOS |------------------------------------------------------------------------------------------|

	public int degreeGND(int v) {
		if (v > n || v < 0) {
			return -1;
		}
		int degree = 0;
		for (int i = 0; i < n; i++) {
			if (adj[i][v] == 1) {
				degree++;
			}
		}
		return degree;
	}

	public boolean eCompletoGND() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j && adj[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void removeVerticeGND(int v) {
	    if (v < 0 || v >= n) return;

	    int novaAdj[][] = new int[n - 1][n - 1];

	    int novoI = 0, novoJ;
	    for (int i = 0; i < n; i++) {
	        if (i == v) {
				continue;
			}
	        novoJ = 0;
	        for (int j = 0; j < n; j++) {
	            if (j == v) {
					continue;
				}
	            novaAdj[novoI][novoJ] = adj[i][j];
	            novoJ++;
	        }
	        novoI++;
	    }

	    adj = novaAdj;
	    n--;
	}
	
	public TGrafoND complementoGND() {
	    TGrafoND complemento = new TGrafoND(this.n);

	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            if (this.adj[i][j] == 0) {
	                complemento.insereAGND(i, j);
	            }
	        }
	    }
	    return complemento;
	}
	
	public int tipoConexidade() {
	    boolean[] visitado = new boolean[n];
	    verificaVertice(0, visitado);

	    for (boolean v : visitado) {
	        if (!v) return 1;
	    }
	    
	    return 0;
	}

	private void verificaVertice(int v, boolean[] visitado) {
	    visitado[v] = true;
	    for (int i = 0; i < n; i++) {
	        if (adj[v][i] == 1 && !visitado[i]) {
	            verificaVertice(i, visitado);
	        }
	    }
	}

}
