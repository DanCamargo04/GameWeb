package pack;

// GRAFO NÃO DIRIGIDO(ORIENTADO) COM PESO NAS ARESTAS

public class TGrafoND {

	private int n; // quantidade de vértices
	private int m; // quantidade de arestas
	private int adj[][]; // matriz de adjacência

	// Métodos Públicos
	
	public TGrafoND(int n) { // construtor
		this.n = n;
		// no início dos tempos não há arestas
		this.m = 0;
		// alocação da matriz do TGrafoND
		this.adj = new int[n][n];

		// inicia a matriz com zeros
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				this.adj[i][j] = 0;
	}

	// insere uma aresta no Grafo tal que v é adjacente a w e vice versa
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

	// apresenta o Grafo contendo número de vértices, arestas e a matriz de adjacência obtida
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
		System.out.println("\n\nFim da impressao do grafo!\n");
	}
	
	// MÉTODOS NOVOS |------------------------------------------------------------------------------------------|
	
	// função auxiliar para verificar se o vértice é válido
    private boolean indiceValido(int v) {
        return v >= 0 && v < n;
    }

    // verifica o grau do grafo
	public int degreeGND(int v) {		
		if (!indiceValido(v)) return -1;
        int grau = 0;    
        for (int i = 0; i < n; i++) {
            if (adj[v][i] != 0) {
                grau++;
            }
        }    
        return grau;
	}

	// verifica se o Grafo é completo
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

	// remove vértice do Grafo e reorganiza o Grafo
	public void removeVerticeGND(int v) {	
		if (!indiceValido(v)) return;	
	    int novaAdj[][] = new int[n - 1][n - 1];
	    int novoI = 0, novoJ;
	    for (int i = 0; i < n; i++) {
	        if (i == v) continue;
	        novoJ = 0;
	        for (int j = 0; j < n; j++) {
	            if (j == v) continue;
	            novaAdj[novoI][novoJ] = adj[i][j];
	            novoJ++;
	        }
	        novoI++;
	    }
	    adj = novaAdj;
	    n--;
	}
	
	// cria o complemento do Grafo
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
	
	// retorna o tipo de conexidade do Grafo (1 -> Conexo / 0 -> Desconexo)
	public int tipoConexidade() {
	    boolean[] visitado = new boolean[n];
	    verificaVertice(0, visitado);
	    for (boolean v : visitado) {
	        if (!v) return 0;
	    }
	    return 1;
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
