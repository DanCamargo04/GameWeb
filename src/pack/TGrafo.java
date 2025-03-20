package pack;

// GRAFO DIRIGIDO

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafo {
	// Atributos Privados
	private int n; // quantidade de vértices
	private int m; // quantidade de arestas
	private int adj[][]; // matriz de adjacência

	// Métodos Públicos
	public TGrafo(int n) { // construtor
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
	
	public int getN() {
		return this.n;
	}
	
	public int getM() {
		return this.m;
	}
	
	public int[][] getAdj() {
		return this.adj;
	}

	// Insere uma aresta no Grafo tal que
	// v é adjacente a w
	public void insereA(int v, int w) {
		// testa se nao temos a aresta
		if (adj[v][w] == 0) {
			adj[v][w] = 1;
			m++; // atualiza qtd arestas
		}
	}

	// remove uma aresta v->w do Grafo
	public void removeA(int v, int w) {
		// testa se temos a aresta
		if (adj[v][w] == 1) {
			adj[v][w] = 0;
			m--; // atualiza qtd arestas
		}
	}

	// Apresenta o Grafo contendo
	// número de vértices, arestas
	// e a matriz de adjacência obtida
	public void show() {
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

	// MEUS MÉTODOS
	// |------------------------------------------------------------------------------------------|

	public int inDegree(int v) {
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

	public int outDegree(int v) {
		if (v > n || v < 0) {
			return -1;
		}
		int degree = 0;
		for (int i = 0; i < n; i++) {
			if (adj[v][i] == 1) {
				degree++;
			}
		}
		return degree;
	}

	public int degree(int v) {
		return inDegree(v) + outDegree(v);
	}

	public int eFonte(int v) {
		if (inDegree(v) == 0 && outDegree(v) > 0) {
			return 1;
		}
		return 0;
	}

	public int eSorvedouro(int v) {
		if (inDegree(v) > 0 && outDegree(v) == 0) {
			return 1;
		}
		return 0;
	}

	public int eSimetrico() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (adj[i][j] != adj[j][i]) {
					return 0;
				}
			}
		}
		return 1;
	}

	public boolean eCompleto() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j && adj[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void removeVertice(int v) {
		if (v < 0 || v >= n)
			return;

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
	
	public TGrafo complemento() {
	    TGrafo complemento = new TGrafo(this.n);

	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if (i != j && this.adj[i][j] == 0) {
	                complemento.insereA(i, j);
	            }
	        }
	    }
	    return complemento;
	}
	
	private boolean verificaVertice(int v, boolean[] visitado) {
        visitado[v] = true;
        for (int i = 0; i < n; i++) {
            if (adj[v][i] == 1 && !visitado[i]) {
                verificaVertice(i, visitado);
            }
        }
        return true;
    }

    private boolean eConexo() {
        boolean[] visitado = new boolean[n];
        verificaVertice(0, visitado);
        for (boolean v : visitado) {
            if (!v) return false;
        }
        return true;
    }

    public int categoriaConexidade() {
        if (!eConexo()) return 0; // C0
        if (eConexo()) return 2; // C2
        if (eCompleto()) return 3; // C3
        return 1; // C1
    }
    
    // GRAFO REDUZIDO
    
    public TGrafo grafoReduzido() {
     
        int[] setor = new int[n];
        for (int i = 0; i < n; i++) {
            setor[i] = -1;
        }

        int idSetor = 0;
        for (int v = 0; v < n; v++) {
            if (setor[v] == -1) { 
                setor[v] = idSetor;

                for (int w = 0; w < n; w++) {
                    if (setor[w] == -1 && temIntersecao(v, w)) {
                        setor[w] = idSetor;
                    }
                }
                idSetor++;
            }
        }

        TGrafo reduzido = new TGrafo(idSetor);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adj[i][j] == 1 && setor[i] != setor[j]) {
                    reduzido.insereA(setor[i], setor[j]);
                }
            }
        }

        return reduzido;
    }

    private boolean temIntersecao(int v, int w) {
        boolean entradaV = false, saidaV = false;
        boolean entradaW = false, saidaW = false;

        for (int i = 0; i < n; i++) {
            if (adj[i][v] == 1) entradaV = true;
            if (adj[v][i] == 1) saidaV = true;
            if (adj[i][w] == 1) entradaW = true;
            if (adj[w][i] == 1) saidaW = true;
        }

        return (entradaV == entradaW) && (saidaV == saidaW);
    }    
}
