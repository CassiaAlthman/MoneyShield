package br.com.moneyshield.model;

import br.com.moneyshield.dao.AvaliacaoAlunoDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultadoAvaliacao {

    private final int numeroTrilha;

    private int acertosPre = -1;
    private int totalPre = 0;

    private int acertosPos = -1;
    private int totalPos = 0;

    private static final AvaliacaoAlunoDAO avaliacaoDAO =
            new AvaliacaoAlunoDAO();

    public ResultadoAvaliacao() {
        this(0);
    }

    public ResultadoAvaliacao(
            int numeroTrilha) {

        this.numeroTrilha =
                numeroTrilha;
    }

    public boolean realizouPre() {
        return acertosPre >= 0;
    }

    public boolean realizouPos() {
        return acertosPos >= 0;
    }

    public void registrarPre(
            int acertos,
            int total) {

        this.acertosPre = acertos;
        this.totalPre = total;
    }

    public void registrarPos(
            int acertos,
            int total) {

        this.acertosPos = acertos;
        this.totalPos = total;
    }

    public boolean registrarPre(
            ArrayList<Integer> respostas) {

        return registrarNoBanco(
                true,
                respostas
        );
    }

    public boolean registrarPos(
            ArrayList<Integer> respostas) {

        return registrarNoBanco(
                false,
                respostas
        );
    }

    public void carregarPre(
            int acertos,
            int total) {

        this.acertosPre = acertos;
        this.totalPre = total;
    }

    public void carregarPos(
            int acertos,
            int total) {

        this.acertosPos = acertos;
        this.totalPos = total;
    }

    public int getAcertosPre() {
        return acertosPre;
    }

    public int getTotalPre() {
        return totalPre;
    }

    public int getAcertosPos() {
        return acertosPos;
    }

    public int getTotalPos() {
        return totalPos;
    }

    public int getPercentualPre() {

        if (totalPre == 0) {
            return 0;
        }

        return (int) Math.round(
                ((double) acertosPre / totalPre)
                        * 100
        );
    }

    public int getPercentualPos() {

        if (totalPos == 0) {
            return 0;
        }

        return (int) Math.round(
                ((double) acertosPos / totalPos)
                        * 100
        );
    }

    public int getEvolucao() {

        if (!realizouPre()
                || !realizouPos()) {

            return 0;
        }

        return getPercentualPos()
                - getPercentualPre();
    }

    private boolean registrarNoBanco(
            boolean preTeste,
            ArrayList<Integer> respostas) {

        if (numeroTrilha <= 0) {
            return false;
        }

        try {

            AvaliacaoAlunoDAO.DadosResultado dados =
                    avaliacaoDAO.registrarAvaliacaoAtual(
                            numeroTrilha,
                            preTeste,
                            respostas
                    );

            if (preTeste) {

                carregarPre(
                        dados.getAcertos(),
                        dados.getTotal()
                );

            } else {

                carregarPos(
                        dados.getAcertos(),
                        dados.getTotal()
                );
            }

            return true;

        } catch (SQLException
                 | RuntimeException erro) {

            System.err.println(
                    "Erro ao registrar avaliação:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }
}
