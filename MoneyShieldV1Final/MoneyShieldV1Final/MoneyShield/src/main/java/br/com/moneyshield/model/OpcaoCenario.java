package br.com.moneyshield.model;

import br.com.moneyshield.repository.RepositorioCenarios;

public class OpcaoCenario {

    private final int id;
    private final String idBanco;
    private final String idCenarioBanco;
    private String titulo;
    private String consequencia;
    private final boolean persistente;

    public OpcaoCenario(
            int id,
            String titulo,
            String consequencia) {

        this(
                id,
                null,
                null,
                titulo,
                consequencia,
                false
        );
    }

    public OpcaoCenario(
            int id,
            String idBanco,
            String idCenarioBanco,
            String titulo,
            String consequencia,
            boolean persistente) {

        this.id = id;
        this.idBanco = idBanco;
        this.idCenarioBanco = idCenarioBanco;
        this.titulo = titulo;
        this.consequencia = consequencia;
        this.persistente = persistente;
    }

    public int getId() {
        return id;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public String getIdCenarioBanco() {
        return idCenarioBanco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(
            String titulo) {

        this.titulo = titulo;

        persistir();
    }

    public String getConsequencia() {
        return consequencia;
    }

    public void setConsequencia(
            String consequencia) {

        this.consequencia = consequencia;

        persistir();
    }

    private void persistir() {

        if (!persistente
                || idBanco == null) {

            return;
        }

        RepositorioCenarios.atualizarOpcao(
                this
        );
    }

    @Override
    public String toString() {
        return titulo;
    }
}
