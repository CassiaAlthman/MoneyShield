package br.com.moneyshield.model;

import br.com.moneyshield.repository.RepositorioCenarios;
import java.util.ArrayList;

public class CenarioFinanceiro {

    private final int id;
    private final String idBanco;
    private String titulo;
    private String descricao;
    private boolean publicado;

    private final ArrayList<OpcaoCenario> opcoes;

    private int proximoIdOpcao = 1;
    private final boolean persistente;

    public CenarioFinanceiro(
            int id,
            String titulo,
            String descricao,
            boolean publicado) {

        this(
                id,
                null,
                titulo,
                descricao,
                publicado,
                false
        );
    }

    public CenarioFinanceiro(
            int id,
            String idBanco,
            String titulo,
            String descricao,
            boolean publicado,
            boolean persistente) {

        this.id = id;
        this.idBanco = idBanco;
        this.titulo = titulo;
        this.descricao = descricao;
        this.publicado = publicado;
        this.persistente = persistente;
        this.opcoes = new ArrayList<>();
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

        persistir();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(
            String descricao) {

        this.descricao = descricao;

        persistir();
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(
            boolean publicado) {

        this.publicado = publicado;

        persistir();
    }

    public ArrayList<OpcaoCenario> getOpcoes() {
        return new ArrayList<>(opcoes);
    }

    public OpcaoCenario adicionarOpcao(
            String titulo,
            String consequencia) {

        if (persistente
                && idBanco != null) {

            OpcaoCenario opcao =
                    RepositorioCenarios.adicionarOpcao(
                            this,
                            titulo,
                            consequencia
                    );

            if (opcao != null) {

                opcoes.add(
                        opcao
                );

                proximoIdOpcao =
                        Math.max(
                                proximoIdOpcao,
                                opcao.getId() + 1
                        );
            }

            return opcao;
        }

        OpcaoCenario opcao =
                new OpcaoCenario(
                        proximoIdOpcao++,
                        titulo,
                        consequencia
                );

        opcoes.add(
                opcao
        );

        return opcao;
    }

    public void adicionarOpcaoCarregada(
            OpcaoCenario opcao) {

        if (opcao == null) {
            return;
        }

        opcoes.add(
                opcao
        );

        proximoIdOpcao =
                Math.max(
                        proximoIdOpcao,
                        opcao.getId() + 1
                );
    }

    public OpcaoCenario buscarOpcao(
            int id) {

        for (OpcaoCenario opcao : opcoes) {

            if (opcao.getId() == id) {
                return opcao;
            }
        }

        return null;
    }

    public boolean excluirOpcao(
            int id) {

        OpcaoCenario opcao =
                buscarOpcao(
                        id
                );

        if (opcao == null) {
            return false;
        }

        if (persistente
                && opcao.getIdBanco() != null) {

            if (!RepositorioCenarios.excluirOpcao(
                    opcao
            )) {

                return false;
            }
        }

        return opcoes.remove(
                opcao
        );
    }

    private void persistir() {

        if (!persistente
                || idBanco == null) {

            return;
        }

        RepositorioCenarios.atualizarCenario(
                this
        );
    }

    @Override
    public String toString() {
        return titulo;
    }
}
