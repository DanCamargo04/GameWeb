package pack;

//definicao da classe de nós da lista
class TNo { // define uma struct (registro)
	public int w; // vértice que é adjacente ao elemento da lista
	public TNo prox;
}

//definição de uma classe para armezanar um grafo
public class TGrafoLista {
	// atributos privados
	private int n; // quantidade de vértices
	private int m; // quantidade de arestas
	private TNo adj[]; // um vetor onde cada entrada guarda o inicio de uma lista
	// métodos públicos
	// Construtor do grafo com a lista de
	// adjacência

	public TGrafoLista(int n) {
		// aloca a estrutura TGrafo
		this.n = n;
		this.m = 0;
		// aloca m vetor para guardar lista de adjacencias
		TNo adjac[] = new TNo[n];
		// Inicia o vetor com nullL
		for (int i = 0; i < n; i++)
			adjac[i] = null;
		this.adj = adjac;
	};

	/*
	 * Método que cria uma aresta v-w no grafo. O método supõe que v e w são
	 * distintos, positivos e menores que V. Se o grafo já tem a aresta v-w, o
	 * método não faz nada. O método também atualiza a quantidade de arestas no
	 * grafo.
	 */
	public void insereAL(int v, int w) {

		TNo novoNo;
		// anda na lista para chegar ao final
		TNo no = adj[v];
		TNo ant = null;
		// anda na lista enquanto no != NULL E w > no->w
		while (no != null && w >= no.w) {
			if (w == no.w)
				return;
			ant = no;
			no = no.prox;
		}
		// cria o novo No para guardar w
		novoNo = new TNo();
		novoNo.w = w;
		novoNo.prox = no;
		// atualiza a lista
		if (ant == null) {
			// insere no inicio
			adj[v] = novoNo;
		} else
			// insere no final
			ant.prox = novoNo;
		m++;
	}

	/*
	 * Método que remove do grafo a aresta que tem ponta inicial v e ponta final w.
	 * O método supõe que v e w são distintos, positivos e menores que V. Se não
	 * existe a aresta v-w, o método não faz nada. O método também atualiza a
	 * quantidade de arestas no grafo.
	 */
	public void removeAL(int v, int w) {
	    TNo no = adj[v];
	    TNo ant = null;

	    while (no != null && no.w != w) {
	        ant = no;
	        no = no.prox;
	    }

	    if (no != null) {
	        if (ant == null) {
	            adj[v] = no.prox;
	        } else {
	            ant.prox = no.prox;
	        }
	        m--;
	    }
	}

	/*
	 * Para cada vértice v do grafo, este método imprime, em uma linha, todos os
	 * vértices adjacentes ao vértice v (vizinhos ao vértice v).
	 */
	public void showL() {
		System.out.print("n: " + n);
		System.out.print("\nm: " + m + "\n");
		for (int i = 0; i < n; i++) {
			System.out.print("\n" + i + ": ");
			// Percorre a lista na posição i do vetor
			TNo no = adj[i];
			while (no != null) {
				System.out.print(no.w + " ");
				no = no.prox;
			}
		}
		System.out.print("\n\nfim da impressao do grafo.\n");
	}

	// MEUS MÉTODOS
	// |------------------------------------------------------------------------------------------|

	public int inDegreeL(int v) {
		int count = 0;
		for (int i = 0; i < n; i++) {
			TNo no = adj[i];
			while (no != null) {
				if (no.w == v) {
					count++;
					break;
				}
				no = no.prox;
			}
		}
		return count;
	}

	public int outDegreeL(int v) {
		int count = 0;
		TNo no = adj[v];
		while (no != null) {
			count++;
			no = no.prox;
		}
		return count;
	}

	public int degreeL(int v) {
		return inDegreeL(v) + outDegreeL(v);
	}

	public int eFonteL(int v) {
		if (inDegreeL(v) == 0 && outDegreeL(v) > 0) {
			return 1;
		}
		return 0;
	}

	public int eSorvedouroL(int v) {
		if (inDegreeL(v) > 0 && outDegreeL(v) == 0) {
			return 1;
		}
		return 0;
	}

	public int eSimetricoL() {
	    for (int i = 0; i < n; i++) {
	        TNo no = adj[i];
	        while (no != null) {
	            TNo noAdj = adj[no.w];
	            boolean simetrico = false;
	            while (noAdj != null) {
	                if (noAdj.w == i) {
	                    simetrico = true;
	                    break;
	                }
	                noAdj = noAdj.prox;
	            }
	            if (!simetrico) {
	                return 0;
	            }
	            no = no.prox;
	        }
	    }
	    return 1; 
	}
	
	public void removeVerticeNDL(int v) {
	    for (int i = 0; i < n; i++) {
	        if (i != v) {
	            removeAL(i, v);
	            removeAL(v, i);
	        }
	    }
	    adj[v] = null;
	    n--;
	}

	public void removeVerticeDL(int v) {
	    for (int i = 0; i < n; i++) {
	        if (i != v) {
	            removeAL(i, v);
	        }
	    }
	    adj[v] = null;
	    n--;
	}

	public boolean eCompleto() {
		for (int i = 0; i < n; i++) {
			TNo no = adj[i];
			int count = 0;

			while (no != null) {
				if (no.w == i)
					return false;
				count++;
				no = no.prox;
			}

			if (count < n - 1)
				return false;
		}
		return true;
	}
	
	public boolean igualGrafo(TGrafoLista outro) {
		if (this.n != outro.n || this.m != outro.m) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			TNo no1 = this.adj[i];
			TNo no2 = outro.adj[i];
			while (no1 != null && no2 != null) {
				if (no1.w != no2.w) {
					return false;
				}
				no1 = no1.prox;
				no2 = no2.prox;
			}
			if (no1 != null || no2 != null) {
				return false;
			}
		}
		return true;
	}
	
	public TGrafoLista converterMatrizParaLista(TGrafo matriz) {
		TGrafoLista lista = new TGrafoLista(matriz.getN());
		for (int i = 0; i < matriz.getN(); i++) {
			for (int j = 0; j < matriz.getN(); j++) {
				if (matriz.getAdj()[i][j] == 1) {
					lista.insereAL(i, j);
				}
			}
		}
		return lista;
	}
	
	public void inverterListasAdjacencia() {
		for (int i = 0; i < n; i++) {
			TNo prev = null;
			TNo atual = adj[i];
			while (atual != null) {
				TNo proximo = atual.prox;
				atual.prox = prev;
				prev = atual;
				atual = proximo;
			}
			adj[i] = prev;
		}
	}

}