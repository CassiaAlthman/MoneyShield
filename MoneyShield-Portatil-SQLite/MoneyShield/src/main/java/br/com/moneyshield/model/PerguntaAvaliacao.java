package br.com.moneyshield.model;

import br.com.moneyshield.repository.RepositorioAvaliacoes;
import java.util.ArrayList;

public class PerguntaAvaliacao {

    private String pergunta;
    private final ArrayList<String> alternativas;
    private int respostaCorreta;

    private String idBanco;
    private int numeroTrilha;
    private String tipoAvaliacao;
    private boolean persistente;

    public PerguntaAvaliacao(
            String pergunta,
            ArrayList<String> alternativas,
            int respostaCorreta) {

        this.pergunta = pergunta;
        this.alternativas = new ArrayList<>(alternativas);
        this.respostaCorreta = respostaCorreta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(
            String pergunta) {

        this.pergunta = pergunta;
        persistir();
    }

    public ArrayList<String> getAlternativas() {
        return new ArrayList<>(alternativas);
    }

    public void setAlternativa(
            int indice,
            String texto) {

        alternativas.set(
                indice,
                texto
        );

        persistir();
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(
            int respostaCorreta) {

        this.respostaCorreta = respostaCorreta;
        persistir();
    }

    public boolean respostaCorreta(
            int resposta) {

        return resposta == respostaCorreta;
    }

    public String getIdBanco() {
        return idBanco;
    }

    int getNumeroTrilha() {
        return numeroTrilha;
    }

    String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void configurarPersistencia(
            String idBanco,
            int numeroTrilha,
            String tipoAvaliacao) {

        this.idBanco = idBanco;
        this.numeroTrilha = numeroTrilha;
        this.tipoAvaliacao = tipoAvaliacao;
        this.persistente = idBanco != null;
    }

    private void persistir() {

        if (!persistente
                || idBanco == null) {

            return;
        }

        RepositorioAvaliacoes.atualizarPergunta(
                this
        );
    }

    @Override
    public String toString() {
        return pergunta;
    }
}
