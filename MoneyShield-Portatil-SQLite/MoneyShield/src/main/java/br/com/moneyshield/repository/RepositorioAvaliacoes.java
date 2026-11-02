package br.com.moneyshield.repository;

import br.com.moneyshield.model.AvaliacaoTrilha;
import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.model.PerguntaAvaliacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepositorioAvaliacoes {

    public static AvaliacaoTrilha getAvaliacao(
            int numeroTrilha) {

        AvaliacaoTrilha avaliacao =
                new AvaliacaoTrilha(
                        numeroTrilha
                );

        if (numeroTrilha <= 0) {
            return avaliacao;
        }

        String sql =
                """
                SELECT
                    a.nom_tipo_aval,
                    q.cod_idef_ques,
                    q.txt_enun,
                    q.num_ord_ques,
                    alt.txt_alte,
                    alt.num_ord_alte,
                    alt.ind_alte_corr
                FROM dbo.tbl_trilha t
                INNER JOIN dbo.tbl_avaliacao a
                    ON a.cod_idef_tril = t.cod_idef_tril
                INNER JOIN dbo.tbl_questao_avaliacao q
                    ON q.cod_idef_aval = a.cod_idef_aval
                INNER JOIN dbo.tbl_alternativa_avaliacao alt
                    ON alt.cod_idef_ques = q.cod_idef_ques
                WHERE
                    t.num_ord_exib = ?
                    AND t.ind_tril_ativ = 1
                    AND a.ind_aval_ativ = 1
                    AND q.ind_ques_ativ = 1
                ORDER BY
                    CASE a.nom_tipo_aval
                        WHEN 'PRE' THEN 1
                        ELSE 2
                    END,
                    q.num_ord_ques,
                    alt.num_ord_alte
                """;

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                sql
                        )
        ) {

            comando.setInt(
                    1,
                    numeroTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                String idQuestaoAtual =
                        null;

                String tipoAtual =
                        null;

                String enunciadoAtual =
                        null;

                ArrayList<String> alternativas =
                        new ArrayList<>();

                int correta = -1;

                while (resultado.next()) {

                    String idQuestao =
                            resultado.getString(
                                    "cod_idef_ques"
                            );

                    String tipo =
                            resultado.getString(
                                    "nom_tipo_aval"
                            );

                    if (idQuestaoAtual != null
                            && !idQuestaoAtual.equals(
                            idQuestao
                    )) {

                        adicionarPerguntaCarregada(
                                avaliacao,
                                numeroTrilha,
                                tipoAtual,
                                idQuestaoAtual,
                                enunciadoAtual,
                                alternativas,
                                correta
                        );

                        alternativas =
                                new ArrayList<>();

                        correta = -1;
                    }

                    idQuestaoAtual =
                            idQuestao;

                    tipoAtual =
                            tipo;

                    enunciadoAtual =
                            resultado.getString(
                                    "txt_enun"
                            );

                    alternativas.add(
                            resultado.getString(
                                    "txt_alte"
                            )
                    );

                    if (resultado.getBoolean(
                            "ind_alte_corr"
                    )) {

                        correta =
                                resultado.getInt(
                                        "num_ord_alte"
                                ) - 1;
                    }
                }

                if (idQuestaoAtual != null) {

                    adicionarPerguntaCarregada(
                            avaliacao,
                            numeroTrilha,
                            tipoAtual,
                            idQuestaoAtual,
                            enunciadoAtual,
                            alternativas,
                            correta
                    );
                }
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao carregar avaliações:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return avaliacao;
    }

    public static boolean possuiAvaliacao(
            int numeroTrilha) {

        AvaliacaoTrilha avaliacao =
                getAvaliacao(
                        numeroTrilha
                );

        return avaliacao
                .possuiAvaliacaoCompleta();
    }

    public static boolean adicionarPergunta(
            int numeroTrilha,
            String tipo,
            PerguntaAvaliacao pergunta) {

        if (numeroTrilha <= 0
                || pergunta == null
                || !tipoValido(tipo)
                || pergunta.getAlternativas().size() != 4
                || pergunta.getRespostaCorreta() < 0
                || pergunta.getRespostaCorreta() > 3) {

            return false;
        }

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                String idTrilha =
                        buscarIdTrilha(
                                conexao,
                                numeroTrilha
                        );

                if (idTrilha == null) {

                    throw new SQLException(
                            "Trilha não encontrada: "
                                    + numeroTrilha
                    );
                }

                String idAvaliacao =
                        buscarOuCriarAvaliacao(
                                conexao,
                                idTrilha,
                                numeroTrilha,
                                tipo
                        );

                int ordem =
                        buscarProximaOrdemQuestao(
                                conexao,
                                idAvaliacao
                        );

                String idQuestao =
                        inserirQuestao(
                                conexao,
                                idAvaliacao,
                                pergunta.getPergunta(),
                                ordem
                        );

                inserirAlternativas(
                        conexao,
                        idQuestao,
                        pergunta
                );

                conexao.commit();

                pergunta.configurarPersistencia(
                        idQuestao,
                        numeroTrilha,
                        tipo
                );

                return true;

            } catch (SQLException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao adicionar pergunta:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean atualizarPergunta(
            PerguntaAvaliacao pergunta) {

        if (pergunta == null
                || pergunta.getIdBanco() == null
                || pergunta.getAlternativas().size() != 4
                || pergunta.getRespostaCorreta() < 0
                || pergunta.getRespostaCorreta() > 3) {

            return false;
        }

        String sqlQuestao =
                """
                UPDATE dbo.tbl_questao_avaliacao
                SET
                    txt_enun = ?,
                    dat_hor_ulti_atui = SYSUTCDATETIME(),
                    anomesdia = CONVERT(
                        INT,
                        CONVERT(
                            CHAR(8),
                            SYSUTCDATETIME(),
                            112
                        )
                    )
                WHERE
                    cod_idef_ques = ?
                    AND ind_ques_ativ = 1
                """;

        String sqlAlternativa =
                """
                UPDATE dbo.tbl_alternativa_avaliacao
                SET
                    txt_alte = ?,
                    ind_alte_corr = ?,
                    dat_hor_ulti_atui = SYSUTCDATETIME(),
                    anomesdia = CONVERT(
                        INT,
                        CONVERT(
                            CHAR(8),
                            SYSUTCDATETIME(),
                            112
                        )
                    )
                WHERE
                    cod_idef_ques = ?
                    AND num_ord_alte = ?
                """;

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     sqlQuestao
                             )) {

                    comando.setString(
                            1,
                            pergunta.getPergunta()
                    );

                    comando.setString(
                            2,
                            pergunta.getIdBanco()
                    );

                    if (comando.executeUpdate() == 0) {

                        throw new SQLException(
                                "Pergunta não encontrada."
                        );
                    }
                }

                ArrayList<String> alternativas =
                        pergunta.getAlternativas();

                for (int i = 0;
                     i < alternativas.size();
                     i++) {

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         sqlAlternativa
                                 )) {

                        comando.setString(
                                1,
                                alternativas.get(i)
                        );

                        comando.setBoolean(
                                2,
                                pergunta.getRespostaCorreta()
                                        == i
                        );

                        comando.setString(
                                3,
                                pergunta.getIdBanco()
                        );

                        comando.setInt(
                                4,
                                i + 1
                        );

                        comando.executeUpdate();
                    }
                }

                conexao.commit();
                return true;

            } catch (SQLException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao atualizar pergunta:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean removerPergunta(
            PerguntaAvaliacao pergunta) {

        if (pergunta == null
                || pergunta.getIdBanco() == null) {

            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_questao_avaliacao
                SET
                    ind_ques_ativ = 0,
                    dat_hor_ulti_atui = SYSUTCDATETIME(),
                    anomesdia = CONVERT(
                        INT,
                        CONVERT(
                            CHAR(8),
                            SYSUTCDATETIME(),
                            112
                        )
                    )
                WHERE
                    cod_idef_ques = ?
                    AND ind_ques_ativ = 1
                """;

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                sql
                        )
        ) {

            comando.setString(
                    1,
                    pergunta.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao remover pergunta:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    private static void adicionarPerguntaCarregada(
            AvaliacaoTrilha avaliacao,
            int numeroTrilha,
            String tipo,
            String idQuestao,
            String enunciado,
            ArrayList<String> alternativas,
            int correta) {

        if (alternativas.size() != 4
                || correta < 0) {

            return;
        }

        PerguntaAvaliacao pergunta =
                new PerguntaAvaliacao(
                        enunciado,
                        alternativas,
                        correta
                );

        pergunta.configurarPersistencia(
                idQuestao,
                numeroTrilha,
                tipo
        );

        if ("PRE".equals(tipo)) {

            avaliacao.adicionarPerguntaPreCarregada(
                    pergunta
            );

        } else if ("POS".equals(tipo)) {

            avaliacao.adicionarPerguntaPosCarregada(
                    pergunta
            );
        }
    }

    private static String buscarIdTrilha(
            Connection conexao,
            int numeroTrilha)
            throws SQLException {

        String sql =
                """
                SELECT cod_idef_tril
                FROM dbo.tbl_trilha
                WHERE
                    num_ord_exib = ?
                    AND ind_tril_ativ = 1
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setInt(
                    1,
                    numeroTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            1
                    );
                }
            }
        }

        return null;
    }

    private static String buscarOuCriarAvaliacao(
            Connection conexao,
            String idTrilha,
            int numeroTrilha,
            String tipo)
            throws SQLException {

        String sqlBuscar =
                """
                SELECT cod_idef_aval
                FROM dbo.tbl_avaliacao
                WHERE
                    cod_idef_tril = ?
                    AND nom_tipo_aval = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sqlBuscar
                     )) {

            comando.setString(
                    1,
                    idTrilha
            );

            comando.setString(
                    2,
                    tipo
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    String id =
                            resultado.getString(
                                    1
                            );

                    String sqlAtivar =
                            """
                            UPDATE dbo.tbl_avaliacao
                            SET
                                ind_aval_ativ = 1,
                                dat_hor_ulti_atui = SYSUTCDATETIME()
                            WHERE cod_idef_aval = ?
                            """;

                    try (PreparedStatement ativar =
                                 conexao.prepareStatement(
                                         sqlAtivar
                                 )) {

                        ativar.setString(
                                1,
                                id
                        );

                        ativar.executeUpdate();
                    }

                    return id;
                }
            }
        }

        String sqlInserir =
                """
                INSERT INTO dbo.tbl_avaliacao (
                    cod_idef_tril,
                    nom_tipo_aval,
                    nom_aval
                )
                OUTPUT INSERTED.cod_idef_aval
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sqlInserir
                     )) {

            comando.setString(
                    1,
                    idTrilha
            );

            comando.setString(
                    2,
                    tipo
            );

            comando.setString(
                    3,
                    ("PRE".equals(tipo)
                            ? "Pré-teste da Trilha "
                            : "Pós-teste da Trilha ")
                            + numeroTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {

                    throw new SQLException(
                            "Não foi possível criar a avaliação."
                    );
                }

                return resultado.getString(
                        1
                );
            }
        }
    }

    private static int buscarProximaOrdemQuestao(
            Connection conexao,
            String idAvaliacao)
            throws SQLException {

        String sql =
                """
                SELECT ISNULL(MAX(num_ord_ques), 0) + 1
                FROM dbo.tbl_questao_avaliacao
                WHERE cod_idef_aval = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(
                    1,
                    idAvaliacao
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                resultado.next();

                return resultado.getInt(
                        1
                );
            }
        }
    }

    private static String inserirQuestao(
            Connection conexao,
            String idAvaliacao,
            String enunciado,
            int ordem)
            throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_questao_avaliacao (
                    cod_idef_aval,
                    txt_enun,
                    num_ord_ques
                )
                OUTPUT INSERTED.cod_idef_ques
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(
                    1,
                    idAvaliacao
            );

            comando.setString(
                    2,
                    enunciado
            );

            comando.setInt(
                    3,
                    ordem
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {

                    throw new SQLException(
                            "Não foi possível criar a pergunta."
                    );
                }

                return resultado.getString(
                        1
                );
            }
        }
    }

    private static void inserirAlternativas(
            Connection conexao,
            String idQuestao,
            PerguntaAvaliacao pergunta)
            throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_alternativa_avaliacao (
                    cod_idef_ques,
                    txt_alte,
                    num_ord_alte,
                    ind_alte_corr
                )
                VALUES (?, ?, ?, ?)
                """;

        ArrayList<String> alternativas =
                pergunta.getAlternativas();

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            for (int i = 0;
                 i < alternativas.size();
                 i++) {

                comando.setString(
                        1,
                        idQuestao
                );

                comando.setString(
                        2,
                        alternativas.get(i)
                );

                comando.setInt(
                        3,
                        i + 1
                );

                comando.setBoolean(
                        4,
                        pergunta.getRespostaCorreta()
                                == i
                );

                comando.addBatch();
            }

            comando.executeBatch();
        }
    }

    private static boolean tipoValido(
            String tipo) {

        return "PRE".equals(tipo)
                || "POS".equals(tipo);
    }
}
