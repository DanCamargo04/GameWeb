package pack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static int n; // dimensão da matriz
	static int numTipoGrafo; // tipo do grafo
	public static List<Node> nodes = new ArrayList<>(); // lista dos jogos

	public static TGrafoND lerDadosDoArquivo() {
		
		TGrafoND g = null;
		
	    try (BufferedReader br = new BufferedReader(new FileReader("src/pack/grafo.txt"))) {
	    	
	        String linha;

	        linha = br.readLine();
	        numTipoGrafo = Integer.parseInt(linha);

	        linha = br.readLine();
	        n = Integer.parseInt(linha);
	        
	        g = new TGrafoND(n);
	        nodes.clear();

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

	            Tag[] tags = tagsList.toArray(new Tag[6]);
	            for (int k = tagsList.size(); k < 6; k++) {
	                tags[k] = Tag.NENHUMA;
	            }

	            nodes.add(new Node(indice, nome, publicadora, modo, tags));
	        }

	        linha = br.readLine();
	        int qtdArestas = Integer.parseInt(linha);

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
	        System.out.println("Grafo lido com sucesso!\n");
	    } 
	    catch (IOException | NumberFormatException e) {
	    	System.err.println("Erro ao ler o arquivo: " + e.getMessage() + "\n");
	    }

	    return g;
	}

	public static void gravarDadosNoArquivo(TGrafoND g) {
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/pack/grafo.txt"))) {
	        bw.write(numTipoGrafo + "\n");
	        
	        bw.write(n + "\n");

	        for (int i = 0; i < n; i++) {
	            Node node = nodes.get(i);
	            bw.write(node.getIndice() + ";" + node.getNome() + ";" + node.getPublicadora() + ";" + node.getModoDeJogo());
	            for (Tag tag : node.getTags()) {
	                bw.write(";" + tag);
	            }
	            bw.write("\n");
	        }
	        
	        int qtdArestas = g.contarArestas();
	        bw.write(qtdArestas + "\n");
	        
	        for (int i = 0; i < n; i++) {
	            for (int j = i + 1; j < n; j++) {
	                if (g.getAdj()[i][j] != 0) {
	                    bw.write(i + "-" + j + ";" + g.getAdj()[i][j] + "\n");
	                }
	            }
	        }
	        
	        System.out.println("Grafo salvo com sucesso!\n");
	    } 
	    catch (IOException e) {
	        System.err.println("Erro ao salvar o arquivo: " + e.getMessage() + "\n");
	    }
	}
	
	public static void mostrarArestas(TGrafoND g) {
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if ((i != j) && (i < j) && (g.getAdj()[i][j] != 0)) {
	                String iFormatado = String.format("%02d", i);
	                String jFormatado = String.format("%02d", j);
	                System.out.println("[" + iFormatado + "][" + jFormatado + "] -> " + 
	                    nodes.get(i).getNome() + " e " + nodes.get(j).getNome());
	            }
	        }
	    }
	}
	
	public static int calcularPeso(int v1, int v2) {
		
		int peso = 0;
		
		if(nodes.get(v1).getPublicadora() == nodes.get(v2).getPublicadora()) {
			peso+=2;
		}
		
		if(nodes.get(v1).getModoDeJogo() == nodes.get(v2).getModoDeJogo()) {
			peso++;
		}
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				if(nodes.get(v1).getTags()[i] == nodes.get(v2).getTags()[j] && nodes.get(v1).getTags()[i] != Tag.NENHUMA) {
					peso+=5;
					break;
				}
			}
		}
		
		return peso;
	}
	
	public static void carregarGraus(TGrafoND gnd) {
		for(int i = 0; i < n; i++) {
			nodes.get(i).setDegree(gnd.degreeGND(i));
		}
	}
	
	public static String retornarClassificacaoDoGrau(int degree) {
		if(degree < 5) {
			return "BAIXO";
		}
		else if(degree < 15) {
			return "MÉDIO";
		}
		return "ALTO";
	}
	
	public static boolean eVerticeValido(int v) {
		return v >= 0 && v < n;
	}

	public static void main(String[] args) {
		boolean foiCarregado = false;
		TGrafoND grafoJogos = null;
		int opcao = -1;
		Scanner sc = new Scanner(System.in);
		Scanner scv = new Scanner(System.in);
		Scanner scv2 = new Scanner(System.in);

		while (opcao != 11) {
			System.out.print("Menu:\n" +
					"01 - Ler dados do arquivo grafo.txt\n" +
					"02 - Gravar dados no arquivo grafo.txt\n" + 
					"03 - Inserir vértice\n" + 
					"04 - Inserir aresta\n" + 
					"05 - Remover vértice\n" + 
					"06 - Remover aresta\n"+
					"07 - Mostrar conteúdo do arquivo\n" +
					"08 - Mostrar Grafo\n"+
					"09 - Apresentar a conexidade do Grafo e o Reduzido\n" + 
					"10 - Mostrar a classificação do grau de conexões dos jogos\n" +
					"11 - Encerrar aplicação\n"+
					"Escolha sua opção: ");
			try {
				opcao = sc.nextInt();
			} catch(Exception e) {
				System.out.println("Erro ao informar opção!\n");
				break;
			}

			if(!foiCarregado && opcao > 1 && opcao < 11){
				System.out.println("Grafo não carregado!\n");
				continue;
			}

			switch (opcao) {
				case 1: // ler dados do arquivo
					grafoJogos = lerDadosDoArquivo();
					carregarGraus(grafoJogos);
					foiCarregado = true;
					break;

				case 2: // gravar dados no arquivo
					gravarDadosNoArquivo(grafoJogos);
					break;

				case 3: // adicionar novo jogo
					System.out.print("Digite o nome do jogo: ");
					String nome = scv.nextLine();

					System.out.print("Digite a publicadora: ");
					String pub = scv.nextLine();

					boolean error3 = false;
					int opcaoModo = -1;
					while(opcaoModo < 1 || opcaoModo > 4) {
						System.out.print("1) SINGLEPLAYER\n" +
								"2) MULTIPLAYER\n" +
								"3) MISTO\n");
						System.out.print("Digite a opção do modo de jogo: ");
						try {
							opcaoModo = sc.nextInt();
						} catch(Exception e) {
							System.out.println("\nErro ao informar opção!\n");
							error3 = true;
							break;
						}
					}

					if(error3) break;
					
					String[] modos = {"SINGLEPLAYER", "MULTIPLAYER", "MISTO"};
					ModoDeJogo modo = ModoDeJogo.valueOf(modos[opcaoModo-1]);

					List<Tag> novasTags = new ArrayList<>();
					System.out.println("Digite até 6 tags (ou 'fim' para parar): ");
					while (novasTags.size() < 6) {
						String tagStr = scv.nextLine();
						if (tagStr.equalsIgnoreCase("fim")) break;
						try {
							Tag tag = Tag.valueOf(tagStr.replace("-", "_").toUpperCase());
							novasTags.add(tag);
						} catch (IllegalArgumentException e) {
							System.out.println("Tag inválida ignorada.");
						}
					}

					while (novasTags.size() < 6) novasTags.add(Tag.NENHUMA);
					Tag[] tags = novasTags.toArray(new Tag[6]);

					nodes.add(new Node(nodes.size(), nome, pub, modo, tags));
					grafoJogos.insereVGND();
					n = nodes.size();
					break;

				case 4: // adicionar arestas
					System.out.print("Vértice a ser conectado: ");
					int iva1 = -1;
					try {
						iva1 = scv.nextInt();
					}
					catch(Exception e) {
						System.out.println("Valor inválido!\n");
						break;
					}
					if(!eVerticeValido(iva1)) {
						System.out.println("Índice não encontrado!\n");
						break;
					}
					for(int i = 0; i < n; i++) {
						int peso = calcularPeso(iva1, i);
						if(grafoJogos.verificarPeso(peso) && i != iva1) {
							grafoJogos.insereAGND(iva1, i, peso, false);
							nodes.get(iva1).setDegree(nodes.get(iva1).getDegree()+1);
							nodes.get(i).setDegree(nodes.get(i).getDegree()+1);
						}
					}
					System.out.println("Arestas conectadas no vértice " + iva1 + "!\n");
					break;

				case 5: // remover vértice
					System.out.print("Digite o vértice que deseja remover: ");
					int rv1 = -1;
					try {
						rv1 = scv.nextInt();
					}
					catch(Exception e) {
						System.out.println("Valor inválido!\n");
						break;
					}
					if(!eVerticeValido(rv1)) {
						System.out.println("Índice não encontrado!\n");
						break;
					}
					grafoJogos.removeVGND(rv1);
					nodes.remove(rv1);
					n = nodes.size();
					break;

				case 6: // remover aresta
					System.out.println("Qual aresta deseja remover?");
					System.out.print("Vértice 1: ");
					int ra1 = -1;
					try {
						ra1 = scv.nextInt();
					}
					catch(Exception e) {
						System.out.println("Valor inválido!\n");
						break;
					}
					if(!eVerticeValido(ra1)) {
						System.out.println("Índice não encontrado!\n");
						break;
					}
					System.out.print("Vértice 2: ");
					int ra2 = -1;
					try {
						ra2 = scv.nextInt();
					}
					catch(Exception e) {
						System.out.println("Valor inválido!\n");
						break;
					}
					if(!eVerticeValido(ra2)) {
						System.out.println("Índice não encontrado!\n");
						break;
					}
					grafoJogos.removeAGND(ra1, ra2);
					break;

				case 7: // mostrar dados do arquivo
					String tipoGrafo = grafoJogos.tipoGrafo(numTipoGrafo);
					System.out.println("Tipo do Grafo: " + tipoGrafo + "\n");
					System.out.println("Vértices:");
					for (int i = 0; i < n; i++) {
						String iFormatado = String.format("%02d", i);
						System.out.println(iFormatado + " - " + nodes.get(i).getNome());
					}
					System.out.println("Arestas:");
					mostrarArestas(grafoJogos);
					System.out.println("");
					break;

				case 8: // mostrar Grafo
					grafoJogos.showGND(true);
					break;

				case 9: // mostrar conexidade
					if (numTipoGrafo >= 0 && numTipoGrafo <= 3) {
						boolean conexo = grafoJogos.ehConexo();
						System.out.println("Conexidade: " + (conexo ? "CONEXO" : "DESCONEXO") + "\n");
					} 
					else {
						int categoria = grafoJogos.classificarGrafoDirecionado();
						String categoriaStr = switch (categoria) {
							case 3 -> "C3 - Fortemente conexo";
							case 2 -> "C2 - Unilateralmente conexo";
							case 1 -> "C1 - Fracamente conexo";
							default -> "C0 - Desconexo";
						};
						System.out.println("Conexidade: " + categoriaStr + "\n");
					}
					break;

				case 10: // mostrar a classificação do grau dos jogos
					System.out.println("Classificação dos graus de todos os jogos:");
					for(int i = 0; i < n; i++) {
						String classificacao = retornarClassificacaoDoGrau(nodes.get(i).getDegree());
						System.out.println(nodes.get(i).getNome() + ": " + classificacao + " (" + nodes.get(i).getDegree() + ")");
					}
					System.out.println();
					break;

				case 11: // finalizar programa
					break;

				default:
					System.out.println("Opção inválida!\n");
					break;
			}
		}

		sc.close();
		scv.close();
		scv2.close();
		
		System.out.println("\nPrograma finalizado!\n");
	}

}