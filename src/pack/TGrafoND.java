package pack;

import java.util.Arrays;

public class TGrafoND {

	private int n;
	private int m;
	private int adj[][];

	public int[][] getAdj() {
		return adj;
	}

	public TGrafoND(int n) {
		this.n = n;
		this.m = 0;
		this.adj = new int[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				this.adj[i][j] = 0;
	}

	private boolean indiceValido(int v) {
		return v >= 0 && v < n;
	}

	public int contarArestas() {
	    int count = 0;
	    for (int i = 0; i < this.n; i++) {
	        for (int j = i + 1; j < this.n; j++) {
	            if (this.adj[i][j] != 0) {
	                count++;
	            }
	        }
	    }
	    return count;
	}

	public int getNumeroArestas() {
		return this.m;
	}

	public boolean verificarPeso(int peso) {
		return peso >= 15;
	}

	public void insereVGND() {
		int novoN = n + 1;
		int[][] novaAdj = new int[novoN][novoN];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				novaAdj[i][j] = adj[i][j];
			}
		}

		this.n = novoN;
		this.adj = novaAdj;

		System.out.println("Novo vértice adicionado com sucesso ao grafo!");
		System.out.println("Número do novo vértice: " + (n - 1) + "\n");
	}

	public void insereAGND(int v1, int v2, int peso, boolean mostrar) {
	    if (adj[v1][v2] == 0 && adj[v2][v1] == 0) {
	        m++;
	    }
	    adj[v1][v2] = peso;
	    adj[v2][v1] = peso;
	    if (mostrar) {
	        System.out.println("Aresta inserida entre " + v1 + " e " + v2 + " com peso " + peso + "\n");
	    }
	}

	public void removeVGND(int v) {
		if (!indiceValido(v))
			return;

		int novaAdj[][] = new int[n - 1][n - 1];
		int novoI = 0, novoJ;
		for (int i = 0; i < n; i++) {
			if (i == v)
				continue;
			novoJ = 0;
			for (int j = 0; j < n; j++) {
				if (j == v)
					continue;
				novaAdj[novoI][novoJ] = adj[i][j];
				novoJ++;
			}
			novoI++;
		}
		adj = novaAdj;
		n--;
		System.out.println("Vértice " + v + " removido com sucesso!\n");
	}

	public void removeAGND(int v, int w) {
		if (!indiceValido(v) || !indiceValido(w))
			return;
		if (adj[v][w] != 0) {
			adj[v][w] = 0;
			adj[w][v] = 0;
			m--;
			System.out.println("Remoção de aresta concluída!\n");
		} else {
			System.out.println("Não existe essa aresta!\n");
		}
	}

	public void showGND(boolean mostrarSimples) {
	    System.out.print("     ");
	    for (int i = 0; i < n; i++) {
	        System.out.print(String.format("%2d ", i));
	    }
	    System.out.println();

	    System.out.print("     ");
	    for (int i = 0; i < n; i++) {
	        System.out.print("__ ");
	    }
	    System.out.println();

	    for (int i = 0; i < n; i++) {
	        System.out.print(String.format("%2d | ", i));
	        for (int j = 0; j < n; j++) {
	            if (mostrarSimples) {
	                if (adj[i][j] != 0)
	                    System.out.print(String.format("%2d ", adj[i][j]));
	                else
	                    System.out.print("00 ");
	            } else {
	                if (adj[i][j] != 0)
	                    System.out.print(String.format("Adj[%2d,%2d]=%2d ", i + 1, j + 1, adj[i][j]));
	                else
	                    System.out.print(String.format("Adj[%2d,%2d]= 0 ", i + 1, j + 1));
	            }
	        }
	        System.out.println();
	    }
	    System.out.println();
	}

	public String tipoGrafo(int tipo) {
		switch (tipo) {
		case 0:
			return "Grafo não orientado sem peso";
		case 1:
			return "Grafo não orientado com peso no vértice";
		case 2:
			return "Grafo não orientado com peso na aresta";
		case 3:
			return "Grafo não orientado com peso nos vértices e arestas";
		case 4:
			return "Grafo orientado sem peso";
		case 5:
			return "Grafo orientado com peso no vértice";
		case 6:
			return "Grafo orientado com peso na aresta";
		case 7:
			return "Grafo orientado com peso nos vértices e arestas";
		default:
			return "Não encontrado!\n";
		}
	}

	public int degreeGND(int v) {
		if (!indiceValido(v))
			return -1;
		int grau = 0;
		for (int i = 0; i < n; i++) {
			if (adj[v][i] != 0) {
				grau++;
			}
		}
		return grau;
	}
	
	public boolean caminhoEuleriano() {
	    int qtde = 0;
	    for (int i = 0; i < n; i++) {
	        int grau = 0;
	        for (int j = 0; j < n; j++) {
	            grau += adj[i][j];
	        }
	        if (grau % 2 == 1) {
	            qtde++;
	        }
	        if (qtde > 2) {
	            return false;
	        }
	    }
	    return true;
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

	public int tipoConexidade() {
		boolean[] visitado = new boolean[n];
		verificaVertice(0, visitado);
		for (boolean v : visitado) {
			if (!v)
				return 0;
		}
		return 1;
	}

	private void verificaVertice(int v, boolean[] visitado) {
		visitado[v] = true;
		for (int i = 0; i < n; i++) {
			if (adj[v][i] != 0 && !visitado[i]) {
				verificaVertice(i, visitado);
			}
		}
	}

	public boolean ehConexo() {
	    boolean[] visitado = new boolean[n];
	    dfs(0, visitado);
	    for (boolean v : visitado) {
	        if (!v) return false;
	    }
	    return true;
	}
	
	public void colorirVertices() {
	    int[] cores = new int[n];
	    Arrays.fill(cores, -1);

	    boolean[] disponivel = new boolean[n];

	    cores[0] = 0;

	    for (int u = 1; u < n; u++) {
	        Arrays.fill(disponivel, true);

	        for (int v = 0; v < n; v++) {
	            if (adj[u][v] != 0 && cores[v] != -1) {
	                disponivel[cores[v]] = false;
	            }
	        }

	        for (int cor = 0; cor < n; cor++) {
	            if (disponivel[cor]) {
	                cores[u] = cor;
	                break;
	            }
	        }
	    }

	    System.out.println("Coloração dos vértices:");
	    for (int i = 0; i < n; i++) {
	        System.out.println("Vértice " + i + " --> Cor " + cores[i]);
	    }
	}


	private void dfs(int v, boolean[] visitado) {
	    visitado[v] = true;
	    for (int i = 0; i < n; i++) {
	        if (adj[v][i] != 0 && !visitado[i]) {
	            dfs(i, visitado);
	        }
	    }
	}

	public int classificarGrafoDirecionado() {
	    boolean[] visitado = new boolean[n];
	    dfs(0, visitado);
	    boolean fracamenteConexo = true;
	    
	    for (boolean v : visitado) {
	        if (!v) {
	            fracamenteConexo = false;
	            break;
	        }
	    }
	    
	    if (fracamenteConexo) {
	        boolean[] visitadoReverso = new boolean[n];
	        TGrafoND grafoTransposto = this.getTransposto();
	        grafoTransposto.dfs(0, visitadoReverso);
	        
	        for (boolean v : visitadoReverso) {
	            if (!v) return 1; // C1 - Fracamente conexo
	        }
	        return 3; // C3 - Fortemente conexo
	    }
	    
	    return 0; // C0 - Desconexo
	}

	private TGrafoND getTransposto() {
	    TGrafoND transposto = new TGrafoND(n);
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if (adj[i][j] != 0) {
	                transposto.adj[j][i] = adj[i][j];
	            }
	        }
	    }
	    return transposto;
	}
	
	public int[] getRelatedGames(int v) {
	    int vetVal[] = {0, 0, 0};      // valores das 3 maiores conexões
	    int vetInd[] = {-1, -1, -1};   // índices dos vértices correspondentes
	    
	    for (int i = 0; i < n; i++) {
	        int val = adj[v][i];
	        
	        int minIndex = 0;
	        for (int j = 1; j < 3; j++) {
	            if (vetVal[j] < vetVal[minIndex]) {
	                minIndex = j;
	            }
	        }
	        
	        if (val > vetVal[minIndex]) {
	            vetVal[minIndex] = val;
	            vetInd[minIndex] = i;
	        }
	    }
	    
	    return vetInd;
	}


}