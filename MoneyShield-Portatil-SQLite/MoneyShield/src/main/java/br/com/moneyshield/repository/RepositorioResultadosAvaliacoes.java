package br.com.moneyshield.repository;

import br.com.moneyshield.dao.AvaliacaoAlunoDAO;
import br.com.moneyshield.model.ResultadoAvaliacao;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.SQLException;

public class RepositorioResultadosAvaliacoes {

    private static final AvaliacaoAlunoDAO avaliacaoDAO =
            new AvaliacaoAlunoDAO();

    public static ResultadoAvaliacao getResultado(
            int numeroTrilha) {

        ResultadoAvaliacao resultado =
                new ResultadoAvaliacao(
                        numeroTrilha
                );

        if (!SessaoUsuario.possuiUsuarioLogado()
                || !SessaoUsuario.isAluno()) {

            return resultado;
        }

        try {

            AvaliacaoAlunoDAO.ResultadoTrilha dados =
                    avaliacaoDAO.buscarResultadoAtual(
                            numeroTrilha
                    );

            if (dados.getPre().isRealizado()) {

                resultado.carregarPre(
                        dados.getPre().getAcertos(),
                        dados.getPre().getTotal()
                );
            }

            if (dados.getPos().isRealizado()) {

                resultado.carregarPos(
                        dados.getPos().getAcertos(),
                        dados.getPos().getTotal()
                );
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao carregar resultados das avaliações:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return resultado;
    }
}
