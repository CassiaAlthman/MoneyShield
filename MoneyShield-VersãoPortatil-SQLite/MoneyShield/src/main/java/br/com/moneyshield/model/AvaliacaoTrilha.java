package br.com.moneyshield.model;

import br.com.moneyshield.repository.RepositorioAvaliacoes;
import java.util.ArrayList;

public class AvaliacaoTrilha {

    private final int numeroTrilha;

    private final ArrayList<PerguntaAvaliacao> perguntasPre =
            new ArrayList<>();

    private final ArrayList<PerguntaAvaliacao> perguntasPos =
            new ArrayList<>();

    public AvaliacaoTrilha(
            int numeroTrilha) {

        this.numeroTrilha =
                numeroTrilha;
    }

    public int getNumeroTrilha() {
        return numeroTrilha;
    }

    public ArrayList<PerguntaAvaliacao> getPerguntasPre() {
        return new ArrayList<>(perguntasPre);
    }

    public ArrayList<PerguntaAvaliacao> getPerguntasPos() {
        return new ArrayList<>(perguntasPos);
    }

    public void adicionarPerguntaPre(
            PerguntaAvaliacao pergunta) {

        if (pergunta == null) {
            return;
        }

        if (RepositorioAvaliacoes.adicionarPergunta(
                numeroTrilha,
                "PRE",
                pergunta
        )) {

            perguntasPre.add(
                    pergunta
            );
        }
    }

    public void adicionarPerguntaPos(
            PerguntaAvaliacao pergunta) {

        if (pergunta == null) {
            return;
        }

        if (RepositorioAvaliacoes.adicionarPergunta(
                numeroTrilha,
                "POS",
                pergunta
        )) {

            perguntasPos.add(
                    pergunta
            );
        }
    }

    public void adicionarPerguntaPreCarregada(
            PerguntaAvaliacao pergunta) {

        if (pergunta != null) {
            perguntasPre.add(pergunta);
        }
    }

    public void adicionarPerguntaPosCarregada(
            PerguntaAvaliacao pergunta) {

        if (pergunta != null) {
            perguntasPos.add(pergunta);
        }
    }

    public void removerPerguntaPre(
            PerguntaAvaliacao pergunta) {

        if (pergunta == null) {
            return;
        }

        if (RepositorioAvaliacoes.removerPergunta(
                pergunta
        )) {

            perguntasPre.remove(
                    pergunta
            );
        }
    }

    public void removerPerguntaPos(
            PerguntaAvaliacao pergunta) {

        if (pergunta == null) {
            return;
        }

        if (RepositorioAvaliacoes.removerPergunta(
                pergunta
        )) {

            perguntasPos.remove(
                    pergunta
            );
        }
    }

    public boolean possuiPreTeste() {
        return !perguntasPre.isEmpty();
    }

    public boolean possuiPosTeste() {
        return !perguntasPos.isEmpty();
    }

    public boolean possuiAvaliacaoCompleta() {

        return possuiPreTeste()
                && possuiPosTeste();
    }
}
