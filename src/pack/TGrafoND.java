package pack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

// GRAFO NÃO DIRIGIDO(ORIENTADO) COM PESO NAS ARESTAS

public class TGrafoND {

	private int n; // quantidade de vértices
	private int m; // quantidade de arestas
	private int adj[][]; // matriz de adjacência

	// Métodos Públicos

	public int[][] getAdj() {
		return adj;
	}

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

	// função auxiliar para verificar se o vértice é válido
	private boolean indiceValido(int v) {
		return v >= 0 && v < n;
	}
	
	public int contarArestas() {
	    int count = 0;
	    
	    for (int i = 0; i < this.n; i++) {
	        for (int j = i + 1; j < this.n; j++) { // Apenas metade superior da matriz para evitar contagens duplas
	            if (this.adj[i][j] != 0) {
	                count++;
	            }
	        }
	    }

	    return count;
	}

	public void gravarDados() {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("grafo.txt"))) {
	        writer.write(n + "\n"); // Salva o número de vértices
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                writer.write(adj[i][j] + " ");
	            }
	            writer.newLine();
	        }
	        System.out.println("Grafo salvo com sucesso!");
	    } catch (IOException e) {
	        System.out.println("Erro ao salvar o grafo: " + e.getMessage());
	    }
	}

	public void carregarDados() {
	    try (BufferedReader reader = new BufferedReader(new FileReader("grafo.txt"))) {
	        n = Integer.parseInt(reader.readLine().trim());
	        adj = new int[n][n]; // Recria a matriz de adjacência
	        for (int i = 0; i < n; i++) {
	            String[] linha = reader.readLine().split(" ");
	            for (int j = 0; j < n; j++) {
	                adj[i][j] = Integer.parseInt(linha[j]);
	            }
	        }
	        System.out.println("Grafo carregado do arquivo!");
	    } catch (IOException e) {
	        System.out.println("Arquivo de grafo não encontrado, iniciando novo grafo.");
	    }
	}


	// insere uma aresta no Grafo tal que v é adjacente a w e vice versa / com peso
	// calculado
	public void insereAGND(int v, int w, int peso, boolean mostraMensagem) {
		if (!indiceValido(v) || !indiceValido(w))
			return;
		// testa se nao temos a aresta
		if (adj[v][w] == 0) {
			adj[v][w] = peso;
			adj[w][v] = peso;
			m++; // atualiza qtd arestas
			if (mostraMensagem)
				System.out.println("Aresta inserida com sucesso!\n");
		} else {
			System.out.println("Já existe essa aresta!\n");
		}
	}

	// remove uma aresta v->w e w->v do Grafo
	public void removeAGND(int v, int w) {
		if (!indiceValido(v) || !indiceValido(w))
			return;
		// testa se temos a aresta
		if (adj[v][w] != 0) {
			adj[v][w] = 0;
			adj[w][v] = 0;
			m--; // atualiza qtd arestas
			System.out.println("Remoção de aresta concluída!\n");
		} else {
			System.out.println("Não existe essa aresta!\n");
		}
	}

	// apresenta o Grafo contendo número de vértices, arestas e a matriz de
	// adjacência obtida
	public void showGND(boolean mostrarSimples) {
	    System.out.print("\n     "); // Espaço para alinhar as colunas
	    for (int i = 1; i <= n; i++) {
	        System.out.print(String.format("%2d ", i));  // Cabeçalho das colunas com duas casas
	    }
	    System.out.println(); // Nova linha após o cabeçalho

	    // Imprime uma linha de separação
	    System.out.print("     ");
	    for (int i = 0; i < n; i++) {
	        System.out.print("__ "); // Separadores de colunas
	    }
	    System.out.println(); // Nova linha após os separadores

	    // Imprime as linhas do grafo com o número da linha na frente
	    for (int i = 0; i < n; i++) {
	        System.out.print(String.format("%2d | ", i + 1));  // Número da linha com duas casas
	        for (int j = 0; j < n; j++) {
	            if (mostrarSimples) {
	                if (adj[i][j] != 0)
	                    System.out.print(String.format("%2d ", adj[i][j]));  // Formato com duas casas
	                else
	                    System.out.print("00 "); // Formato de zero com duas casas
	            } else {
	                if (adj[i][j] != 0)
	                    System.out.print(String.format("Adj[%2d,%2d]=%2d ", i + 1, j + 1, adj[i][j]));  // Formato com duas casas
	                else
	                    System.out.print(String.format("Adj[%2d,%2d]= 0 ", i + 1, j + 1));  // Formato de zero com duas casas
	            }
	        }
	        System.out.println();  // Nova linha após cada linha do grafo
	    }
	    System.out.println("\nFim da impressao do Grafo!\n");
	}



	// MÉTODOS NOVOS
	// |------------------------------------------------------------------------------------------|

	// verifica o grau do grafo
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

	public void insereVerticeGND() {
		int novoN = n + 1;
		int[][] novaAdj = new int[novoN][novoN];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				novaAdj[i][j] = adj[i][j];
			}
		}

		this.n = novoN;
		this.adj = novaAdj;
		System.out.println("Novo vértice adicionado com sucesso ao grafo!\n");
	}

	// remove vértice do Grafo e reorganiza o Grafo
	public void removeVerticeGND(int v) {
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

	// retorna o tipo de conexidade do Grafo (1 -> Conexo / 0 -> Desconexo)
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

	//public TGrafoND gerarGrafoReduzido() {
		// IMPLEMENTAR
	//}

}