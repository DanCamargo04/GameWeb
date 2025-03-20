package pack;

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafoPonderadoFloat {
	// Atributos Privados
    private int n; // quantidade de vértices
    private int m; // quantidade de arestas
    private float adj[][]; // matriz de adjacência com valores float

    // Métodos Públicos
    public TGrafoPonderadoFloat(int n) { // construtor
        this.n = n;
        // No início dos tempos não há arestas
        this.m = 0;
        // alocação da matriz do TGrafo
        this.adj = new float[n][n];

        // Inicia a matriz com infinito (indicando ausência de arestas)
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.adj[i][j] = Float.POSITIVE_INFINITY;
    }

    public int getN() {
        return this.n;
    }

    public int getM() {
        return this.m;
    }

    public float[][] getAdj() {
        return this.adj;
    }

    // Insere uma aresta no Grafo tal que
    // v é adjacente a w com um valor específico
    public void insereA(int v, int w, float valor) {
        // testa se não temos a aresta
        if (adj[v][w] == Float.POSITIVE_INFINITY) {
            adj[v][w] = valor;
            m++; // atualiza qtd arestas
        }
    }

    // remove uma aresta v->w do Grafo
    public void removeA(int v, int w) {
        // testa se temos a aresta
        if (adj[v][w] != Float.POSITIVE_INFINITY) {
            adj[v][w] = Float.POSITIVE_INFINITY;
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
                if (adj[i][w] != Float.POSITIVE_INFINITY)
                    System.out.print("Adj[" + i + "," + w + "]= " + adj[i][w] + " ");
                else
                    System.out.print("Adj[" + i + "," + w + "]= INF ");
        }
        System.out.println("\n\nfim da impressao do grafo.\n");
    }

	// MEUS MÉTODOS
	// |------------------------------------------------------------------------------------------|

     public int inDegree(int v) {
         if (v >= n || v < 0) {
             return -1;
         }
         int degree = 0;
         for (int i = 0; i < n; i++) {
             if (adj[i][v] != Float.POSITIVE_INFINITY) {
                 degree++;
             }
         }
         return degree;
     }

     public int outDegree(int v) {
         if (v >= n || v < 0) {
             return -1;
         }
         int degree = 0;
         for (int i = 0; i < n; i++) {
             if (adj[v][i] != Float.POSITIVE_INFINITY) {
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
                 if (i != j && adj[i][j] == Float.POSITIVE_INFINITY) {
                     return false;
                 }
             }
         }
         return true;
     }

     public void removeVertice(int v) {
         if (v < 0 || v >= n)
             return;

         float novaAdj[][] = new float[n - 1][n - 1];

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

     private boolean verificaVertice(int v, boolean[] visitado) {
         visitado[v] = true;
         for (int i = 0; i < n; i++) {
             if (adj[v][i] != Float.POSITIVE_INFINITY && !visitado[i]) {
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
 	
}
