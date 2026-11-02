package br.com.moneyshield.model;

import java.time.LocalDate;

public class MetaFinanceira {

    private final String id;
    private String nome;
    private String descricao;
    private double valorObjetivo;
    private double valorAtual;
    private LocalDate previsaoConclusao;
    private boolean concluida;

    public MetaFinanceira(
            String id,
            String nome,
            double valorObjetivo,
            double valorAtual) {

        this(
                id,
                nome,
                null,
                valorObjetivo,
                valorAtual,
                null,
                valorAtual >= valorObjetivo
        );
    }

    public MetaFinanceira(
            String id,
            String nome,
            String descricao,
            double valorObjetivo,
            double valorAtual,
            LocalDate previsaoConclusao,
            boolean concluida) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorObjetivo = valorObjetivo;
        this.valorAtual = valorAtual;
        this.previsaoConclusao = previsaoConclusao;
        this.concluida = concluida;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(
            String nome) {

        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(
            String descricao) {

        this.descricao = descricao;
    }

    public double getValorObjetivo() {
        return valorObjetivo;
    }

    public void setValorObjetivo(
            double valorObjetivo) {

        this.valorObjetivo =
                valorObjetivo;

        atualizarConclusao();
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(
            double valorAtual) {

        this.valorAtual =
                valorAtual;

        atualizarConclusao();
    }

    public LocalDate getPrevisaoConclusao() {
        return previsaoConclusao;
    }

    public void setPrevisaoConclusao(
            LocalDate previsaoConclusao) {

        this.previsaoConclusao =
                previsaoConclusao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(
            boolean concluida) {

        this.concluida =
                concluida;
    }

    public void adicionarValor(
            double valor) {

        if (valor <= 0) {
            return;
        }

        valorAtual += valor;

        atualizarConclusao();
    }

    public double getValorRestante() {

        double restante =
                valorObjetivo
                        - valorAtual;

        return Math.max(
                restante,
                0
        );
    }

    public int getProgressoPercentual() {

        if (valorObjetivo <= 0) {
            return 0;
        }

        int percentual =
                (int) (
                        (
                                valorAtual
                                        / valorObjetivo
                        ) * 100
                );

        return Math.min(
                percentual,
                100
        );
    }

    public boolean metaAtingida() {

        return concluida
                || valorAtual >= valorObjetivo;
    }

    private void atualizarConclusao() {

        concluida =
                valorObjetivo > 0
                        && valorAtual >= valorObjetivo;
    }
}