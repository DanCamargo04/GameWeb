/*
 * Cláudio Dias Alves (101403569) 
 * Daniel Rubio Camargo (10408823)
 * Guillermo Kuznietz (10410134)
 */

package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static int n;
	static int numTipoGrafo;
	public static Node nodes[];

	// gera o grafo a partir do txt fornecido
	public static TGrafoND gerarGrafoAPartirDeArquivo() {
		
		TGrafoND g = null;
		
	    try (BufferedReader br = new BufferedReader(new FileReader("src/pack/grafo.txt"))) {
	    	
	        String linha;

	        // lê o tipo do grafo
	        linha = br.readLine();
	        numTipoGrafo = Integer.parseInt(linha);

	        // lê a quantidade de vértices
	        linha = br.readLine();
	        n = Integer.parseInt(linha);
	        
	        g = new TGrafoND(n);
		    nodes = new Node[n];

	        // lê os dados dos vértices e preenche o vetor de Node
	        for (int i = 0; i < n; i++) {
	            linha = br.readLine();
	            String[] partes = linha.split(";");

	            int indice = Integer.parseInt(partes[0]);
	            String nome = partes[1];
	            String publicadora = partes[2];
	            ModoDeJogo modo = ModoDeJogo.valueOf(partes[3]);
	            
	            List<Tag> tagsList = new ArrayList<>();

	            for (int j = 4; j < partes.length; j++) {
	                try {
	                    Tag tag = Tag.valueOf(partes[j].replace("-", "_").toUpperCase());
	                    tagsList.add(tag); 
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Tag inválida ignorada: " + partes[j]);
	                }
	            }

	            Tag[] tags = tagsList.toArray(new Tag[0]);

	            nodes[indice] = new Node(indice, nome, publicadora, modo, tags);
	        }

	        // lê a quantidade de arestas
	        linha = br.readLine();
	        int qtdArestas = Integer.parseInt(linha);

	        // lê e adiciona as arestas
	        for (int i = 0; i < qtdArestas; i++) {
	            linha = br.readLine().trim();
	            if (!linha.isEmpty()) {
	                String[] partes = linha.split(";");
	                String[] vertices = partes[0].split("-");
	                int v1 = Integer.parseInt(vertices[0]);
	                int v2 = Integer.parseInt(vertices[1]);
	                int peso = Integer.parseInt(partes[1]);

	                g.insereAGND(v1, v2, peso, false);
	            }
	        }
	    } 
	    catch (IOException | NumberFormatException e) {
	        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
	    }

	    return g;
	}

	// mostra o menu
	public static void menu() {
		System.out.print("\nMenu:\n\n" + "1 - Ler dados do arquivo grafo.txt\n" +
						"2 - Gravar dados no arquivo grafo.txt\n" + 
				"3 - Inserir vértice\n" + 
				"4 - Inserir aresta\n" + 
				"5 - Remover vértice\n" + 
				"6 - Remover aresta\n"+
				"7 - Mostrar conteúdo do arquivo\n" +
				"8 - Mostrar Grafo\n"+
				"9 - Apresentar a conexidade do Grafo e o Reduzido\n" + 
				"10 - Encerrar aplicação\n\n"+
				"Escolha sua opção: ");
	}
	
	// retorna o tipo do grafo
	public static String tipoGrafo(int tipo) {
		
		switch(tipo) {
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
	
	// mostra todas as arestas criadas
	public static void mostrarArestas(TGrafoND g) {
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if((i != j) && (i < j) && (g.getAdj()[i][j] != 0)) {
					System.out.println("Aresta [" + i + "][" + j + "] -> " +  nodes[i].getNome() + " e " + nodes[j].getNome());
				}
			}
		}
		
	}
	
	// calcula o peso da aresta
	public static int calcularPeso(int v1, int v2) {
		
		int peso = 0;
		
		if(nodes[v1].getPublicadora() == nodes[v2].getPublicadora()) {
			peso+=2;
		}
		
		if(nodes[v1].getModoDeJogo() == nodes[v2].getModoDeJogo()) {
			peso++;
		}
		
		// 6 é o máximo de tags possíveis
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				if(nodes[v1].getTags()[i] == nodes[v2].getTags()[j] && nodes[v1].getTags()[i] != Tag.NENHUMA) {
					peso+=5; // só calcula se for igual e não for repetido
					break;
				}
			}
		}
		
		return peso;
	}
	
	// verifica se é possível criar aresta a partir do peso
	public static boolean verificarPeso(int peso) {
		if(peso < 15) return false;
		return true;
	}

	public static void main(String[] args) {

		int tipoConexidade = 0;
		boolean foiCarregado = false;

		TGrafoND grafoJogos = null;

		int opcao = -1;
		Scanner sc = new Scanner(System.in);
		Scanner scv = new Scanner(System.in);
		Scanner scv2 = new Scanner(System.in);

		while (opcao != 10) {

			menu();
			opcao = sc.nextInt();
			
			if(!foiCarregado && opcao > 1){
				System.out.println("\nGrafo NÃO carregado!");
				continue;
			}

			switch (opcao) {
			case 1: // OK
				// geração do Grafo
				grafoJogos = gerarGrafoAPartirDeArquivo();
				foiCarregado = true;
				tipoConexidade = grafoJogos.tipoConexidade();
				System.out.println("\nGrafo carregado com sucesso!");
				break;
				
			case 2:
				// GRAVAR DADOS NO ARQUIVO
				break;
				
			case 3: // OK
				System.out.print("\nDigite o nome do jogo: ");
			    String nome = scv.nextLine();

			    System.out.print("Digite a publicadora: ");
			    String pub = scv.nextLine();

			    Scanner sm = new Scanner(System.in);
			    
			    int opcaoModo = -1;
			    while(opcaoModo < 1 || opcaoModo > 4) {
			    	System.out.print("\nDigite o modo de jogo:\n"
			    			+ "1 - SINGLEPLAYER\n"
			    			+ "2 - MULTIPLAYER\n"
			    			+ "3 - MISTO\n"
			    			+ "4 - SAIR\n");
			    	System.out.print("Opção: ");
			    	opcaoModo = sc.nextInt();
			    }
			    if(opcaoModo == 4) break;
			    
			    String[] modos = {"SINGLEPLAYER", "MULTIPLAYER", "MISTO"};
			    
			    ModoDeJogo modo = ModoDeJogo.valueOf(modos[opcaoModo-1]);
			    
			    System.out.println("\nModo de Jogo selecionado: " + modos[opcaoModo-1]);

			    List<Tag> novasTags = new ArrayList<>();
			    System.out.println("\nDigite até 6 tags (ou 'fim' para parar): ");
			    while (novasTags.size() < 6) {
			        String tagStr = scv.nextLine();
			        if (tagStr.equalsIgnoreCase("fim")) break;
			        try {
			            Tag tag = Tag.valueOf(tagStr.replace("-", "_").toUpperCase());
			            novasTags.add(tag);
			        } 
			        catch (IllegalArgumentException e) {
			            System.out.println("Tag inválida ignorada.");
			        }
			    }

			    // preenche com NENHUMA se tiver menos de 6
			    while (novasTags.size() < 6) novasTags.add(Tag.NENHUMA);
			    Tag[] tags = novasTags.toArray(new Tag[6]);

			    // redimensiona o array de nodes
			    Node[] novoNodes = new Node[n + 1];
			    for (int i = 0; i < n; i++) novoNodes[i] = nodes[i];
			    novoNodes[n] = new Node(n, nome, pub, modo, tags);
			    nodes = novoNodes;

			    grafoJogos.insereVerticeGND();

			    // calcula arestas para o novo vértice
			    for (int i = 0; i < n; i++) {
			        int peso = calcularPeso(i, n);
			        if (verificarPeso(peso)) {
			            grafoJogos.insereAGND(i, n, peso, false);
			        }
			    }
			    n++; // atualiza contador de nós
			    System.out.println("Novo vértice e arestas inseridas com sucesso!\n");
			    break;
				
			case 4: // OK
				System.out.println("Qual aresta deseja inserir?\n");
				System.out.print("Vértice 1: ");
				int iva1 = scv.nextInt();
				System.out.print("Vértice 2: ");
				int iva2 = scv2.nextInt();
				int peso = calcularPeso(iva1, iva2);
				//System.out.println("Peso: \n" + peso);
				if(!verificarPeso(peso)) {
					System.out.println("O peso calculado é menor do que o necessário para criar uma aresta!\n");
					break;
				}
				grafoJogos.insereAGND(iva1, iva2, peso, true);
				break;
				
			case 5: // OK
				System.out.print("Qual vértice deseja remover?: ");
				int rv1 = scv.nextInt();
				grafoJogos.removeVerticeGND(rv1); 
				break;
				
			case 6: // OK
				System.out.println("Qual aresta deseja remover?\n");
				System.out.print("Vértice 1: ");
				int ra1 = scv.nextInt();
				System.out.print("Vértice 2: ");
				int ra2 = scv2.nextInt();
				grafoJogos.removeAGND(ra1, ra2);
				break;
				
			case 7: // OK
				String tipoGrafo = tipoGrafo(numTipoGrafo);
				System.out.println("\nConteúdo do Grafo\n\nTipo do Grafo: " + tipoGrafo);
				System.out.println("\nVértices\n");
				for(int i = 0; i < n; i++) {
					System.out.println(i + " - " + nodes[i].getNome());
				}
				System.out.println("\nArestas\n");
				mostrarArestas(grafoJogos);
				break;
				
			case 8: // OK		
				// argumento true mostra só os pesos de cada aresta / argumento false mostra os índices junto com os pesos
				grafoJogos.showGND(true);
				break;
				
			case 9:
				if(grafoJogos.tipoConexidade() == 0) {
					System.out.println("\nConexidade: DESCONEXO\n");
				}
				else {
					System.out.println("\nConexidade: CONEXO\n");
				}		
				/*TGrafoND grafoReduzido = grafoJogos.gerarGrafoReduzido();
				System.out.println("Grafo Reduzido\n");
				grafoReduzido.showGND(true);
				*/
				break;
				
			case 10: // OK
				break; // finalizar
				
			default: // OK
				System.out.println("Opção inválida!");
				break;
			}
		}

		System.out.println("\nPrograma finalizado!");
	}

}