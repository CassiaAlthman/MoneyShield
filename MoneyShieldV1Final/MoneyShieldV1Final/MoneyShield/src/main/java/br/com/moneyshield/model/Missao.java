package br.com.moneyshield.model;

public class Missao {

    private final int id;
    private final String idBanco;
    private String titulo;
    private String descricao;
    private String tipo;
    private Integer meta;
    private int xpRecompensa;

    public Missao(
            int id,
            String titulo,
            String descricao) {

        this(
                id,
                null,
                titulo,
                descricao,
                "ADMINISTRATIVA",
                1,
                100
        );
    }

    public Missao(
            int id,
            String idBanco,
            String titulo,
            String descricao,
            String tipo,
            Integer meta,
            int xpRecompensa) {

        this.id = id;
        this.idBanco = idBanco;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.meta = meta;
        this.xpRecompensa = xpRecompensa;
    }

    public int getId() {
        return id;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(
            String titulo) {

        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(
            String descricao) {

        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(
            String tipo) {

        this.tipo = tipo;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(
            Integer meta) {

        this.meta = meta;
    }

    public int getXpRecompensa() {
        return xpRecompensa;
    }

    public void setXpRecompensa(
            int xpRecompensa) {

        this.xpRecompensa = xpRecompensa;
    }
}
