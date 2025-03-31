package pack;

public class Node {
	
	final private int NUMERO_DE_TAGS = 6;
	
	private int indice = -1; // índice do jogo na matriz de adjacência
	private String nome; // nome do jogo
	private String publicadora; // publicadora do jogo
	private ModoDeJogo modoDeJogo; // modo de jogo
	private Tag tags[] = new Tag[NUMERO_DE_TAGS]; // tags do jogo
	
	public Node(int _indice, String _nome, String _publicadora, ModoDeJogo _modoDeJogo, Tag _tags[]) {
	    this.indice = _indice;
	    this.nome = _nome;
	    this.publicadora = _publicadora;
	    this.modoDeJogo = _modoDeJogo;

	    // Garante que no máximo 6 tags sejam copiadas
	    for (int i = 0; i < NUMERO_DE_TAGS; i++) {
	        if (i < _tags.length) {
	            this.tags[i] = _tags[i];
	        } else {
	            this.tags[i] = Tag.NENHUMA;
	        }
	    }
	}
	
	public int getIndice() {
		return this.indice;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getPublicadora() {
		return this.publicadora;
	}
	
	public ModoDeJogo getModoDeJogo() {
		return this.modoDeJogo;
	}
	
	public Tag[] getTags() {
		return this.tags;
	}

}
