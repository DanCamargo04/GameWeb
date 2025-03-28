/*
 * Cláudio Dias Alves (101403569) 
 * Daniel Rubio Camargo (10408823)
 * Guillermo Kuznietz (10410134)
 */

package pack;

public class Main {

	public static TGrafoND gerarGrafoAPartirDeArquivo(int n) {
		
		TGrafoND g = new TGrafoND(n);
		
		// LOGICA DE PASSAR O TXT PRA UM GRAFO
		
		return g;
		
	}
	
	public static void main(String[] args) {
		
		final int qtdJogos = 61;
		
		TGrafoND grafoJogos = gerarGrafoAPartirDeArquivo(qtdJogos);
		
		grafoJogos.showGND();
		
	}

}
