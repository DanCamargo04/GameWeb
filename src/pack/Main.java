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
	
	public static Node nodes[];

	public static TGrafoND gerarGrafoAPartirDeArquivo(int n) {
		
	    TGrafoND g = new TGrafoND(n);
	    nodes = new Node[n];

	    try (BufferedReader br = new BufferedReader(new FileReader("src/pack/grafo.txt"))) {
	    	
	        String linha;

	        // lê o tipo do grafo
	        linha = br.readLine();

	        // lê a quantidade de vértices
	        linha = br.readLine();
	        int qtdVertices = Integer.parseInt(linha);

	        // lê os dados dos vértices e preenche o vetor de Node
	        for (int i = 0; i < qtdVertices; i++) {
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

	                g.insereAGND(v1, v2, peso);
	            }
	        }
	    } 
	    catch (IOException | NumberFormatException e) {
	        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
	    }

	    return g;
	}


	public static void menu() {
		System.out.print("\nMenu:\n\n" + "1 - Ler dados do arquivo grafo.txt\n" +
						"2 - Gravar dados no arquivo grafo.txt\n" + 
				"3 - Inserir vértice\n" + 
				"4 - Inserir aresta" + "5 - Remover vértice\n" + 
				"6 - Remover aresta\n"+
				"7 - Mostrar conteúdo do arquivo\n" +
				"8 - Mostrar Grafo\n"+
				"9 - Apresentar a conexidade do Grafo e o Reduzido\n" + 
				"10 - Encerrar aplicação\n\n"+
				"Escolha sua opção: ");
	}

	public static void main(String[] args) {

		final int qtdJogos = 61;
		int tipoConexidade = 0;
		boolean foiCarregado = false;

		TGrafoND grafoJogos = null;

		int opcao = -1;
		Scanner sc = new Scanner(System.in);

		while (opcao != 10) {

			menu();
			opcao = sc.nextInt();
			
			if(!foiCarregado && opcao > 1){
				System.out.println("\nGrafo NÃO carregado!");
				continue;
			}

			switch (opcao) {
			case 1:
				// geração do Grafo
				grafoJogos = gerarGrafoAPartirDeArquivo(qtdJogos);
				foiCarregado = true;
				tipoConexidade = grafoJogos.tipoConexidade();
				System.out.println("\nGrafo carregado com sucesso!");
				break;
			case 2:
				// GRAVAR DADOS NO ARQUIVO
				break;
			case 3:
				// INSERIR VÉRTICE
				break;
			case 4:
				grafoJogos.insereAGND(100, 100, 100); // MEXER
				break;
			case 5:
				grafoJogos.removeVerticeGND(100); // MEXER
				break;
			case 6:
				grafoJogos.removeAGND(100, 100); // MEXER
				break;
			case 7:
				// MOSTRAR CONTEUDO ARQUIVO
				break;
			case 8:		
				// argumento true mostra só os pesos de cada aresta / argumento false mostra os índices junto com os pesos
				grafoJogos.showGND(true);	
				break;
			case 9:
				System.out.println("\nConexidade: " + grafoJogos.tipoConexidade());
				// GRAFO REDUZIDO
				break;
			case 10:
				break; // finalizar
			default:
				System.out.println("Opção inválida!");
				break;
			}
		}

		System.out.println("\nPrograma finalizado!");
	}

}