package br.com.moneyshield.model;

public class Trilha {

    private final String id;
    private final String nome;
    private final String descricao;
    private final String conteudo;
    private final int ordem;

    public Trilha(
            String id,
            String nome,
            String descricao,
            String conteudo,
            int ordem) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.conteudo = conteudo;
        this.ordem = ordem;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public int getOrdem() {
        return ordem;
    }
}