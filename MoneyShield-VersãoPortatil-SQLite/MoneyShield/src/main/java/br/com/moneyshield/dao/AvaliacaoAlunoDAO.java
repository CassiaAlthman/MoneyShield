package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvaliacaoAlunoDAO {

    public ResultadoTrilha buscarResultadoAtual(
            int numeroTrilha)
            throws SQLException {

        validarAlunoLogado();

        return buscarResultado(
                SessaoUsuario.getIdPerfil(),
                numeroTrilha
        );
    }

    public ResultadoTrilha buscarResultado(
            String idAluno,
            int numeroTrilha)
            throws SQLException {

        validarIdAluno(idAluno);
        validarNumeroTrilha(numeroTrilha);

        DadosResultado pre =
                DadosResultado.naoRealizado();

        DadosResultado pos =
                DadosResultado.naoRealizado();

        String sql =
                """
                SELECT
                    a.nom_tipo_aval,
                    r.qtd_acer,
                    r.qtd_tota,
                    r.vlr_perc
                FROM dbo.tbl_trilha t
                INNER JOIN dbo.tbl_avaliacao a
                    ON a.cod_idef_tril = t.cod_idef_tril
                LEFT JOIN dbo.tbl_resultado_avaliacao r
                    ON r.cod_idef_aval = a.cod_idef_aval
                    AND r.cod_idef_stud = ?
                WHERE
                    t.num_ord_exib = ?
                    AND t.ind_tril_ativ = 1
                    AND a.ind_aval_ativ = 1
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
                    idAluno
            );

            comando.setInt(
                    2,
                    numeroTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    int acertos =
                            resultado.getInt(
                                    "qtd_acer"
                            );

                    if (resultado.wasNull()) {
                        continue;
                    }

                    DadosResultado dados =
                            new DadosResultado(
                                    true,
                                    acertos,
                                    resultado.getInt(
                                            "qtd_tota"
                                    ),
                                    resultado.getBigDecimal(
                                            "vlr_perc"
                                    ).doubleValue()
                            );

                    String tipo =
                            resultado.getString(
                                    "nom_tipo_aval"
                            );

                    if ("PRE".equals(tipo)) {
                        pre = dados;
                    } else if ("POS".equals(tipo)) {
                        pos = dados;
                    }
                }
            }
        }

        return new ResultadoTrilha(
                pre,
                pos
        );
    }

    public DadosResultado registrarAvaliacaoAtual(
            int numeroTrilha,
            boolean preTeste,
            ArrayList<Integer> respostasSelecionadas)
            throws SQLException {

        validarAlunoLogado();

        return registrarAvaliacao(
                SessaoUsuario.getIdPerfil(),
                numeroTrilha,
                preTeste,
                respostasSelecionadas
        );
    }

    public DadosResultado registrarAvaliacao(
            String idAluno,
            int numeroTrilha,
            boolean preTeste,
            ArrayList<Integer> respostasSelecionadas)
            throws SQLException {

        validarIdAluno(idAluno);
        validarNumeroTrilha(numeroTrilha);
        validarRespostas(respostasSelecionadas);

        String tipo =
                preTeste
                        ? "PRE"
                        : "POS";

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                DadosAvaliacao avaliacao =
                        buscarAvaliacao(
                                conexao,
                                numeroTrilha,
                                tipo
                        );

                if (avaliacao == null) {

                    throw new SQLException(
                            "Avaliação "
                                    + tipo
                                    + " não encontrada para a Trilha "
                                    + numeroTrilha
                                    + "."
                    );
                }

                if (!preTeste
                        && !preTesteRealizado(
                        conexao,
                        idAluno,
                        avaliacao.idTrilha
                )) {

                    throw new IllegalStateException(
                            "O pré-teste precisa ser realizado antes do pós-teste."
                    );
                }

                ArrayList<DadosResposta> respostas =
                        buscarRespostasSelecionadas(
                                conexao,
                                avaliacao.idAvaliacao,
                                respostasSelecionadas
                        );

                if (respostas.size()
                        != respostasSelecionadas.size()) {

                    throw new SQLException(
                            "Não foi possível localizar todas as respostas da avaliação no banco."
                    );
                }

                int acertos = 0;

                for (DadosResposta resposta
                        : respostas) {

                    if (resposta.correta) {
                        acertos++;
                    }
                }

                int total =
                        respostas.size();

                BigDecimal percentual =
                        BigDecimal.valueOf(
                                ((double) acertos / total)
                                        * 100.0
                        ).setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

                String idResultado =
                        salvarResultado(
                                conexao,
                                idAluno,
                                avaliacao.idAvaliacao,
                                acertos,
                                total,
                                percentual
                        );

                salvarRespostas(
                        conexao,
                        idResultado,
                        respostas
                );

                atualizarStatusTrilha(
                        conexao,
                        idAluno,
                        avaliacao.idTrilha,
                        preTeste
                );

                conexao.commit();

                return new DadosResultado(
                        true,
                        acertos,
                        total,
                        percentual.doubleValue()
                );

            } catch (SQLException
                     | RuntimeException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }
        }
    }

    private DadosAvaliacao buscarAvaliacao(
            Connection conexao,
            int numeroTrilha,
            String tipo)
            throws SQLException {

        String sql =
                """
                SELECT
                    a.cod_idef_aval,
                    t.cod_idef_tril
                FROM dbo.tbl_trilha t
                INNER JOIN dbo.tbl_avaliacao a
                    ON a.cod_idef_tril = t.cod_idef_tril
                WHERE
                    t.num_ord_exib = ?
                    AND t.ind_tril_ativ = 1
                    AND a.nom_tipo_aval = ?
                    AND a.ind_aval_ativ = 1
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setInt(
                    1,
                    numeroTrilha
            );

            comando.setString(
                    2,
                    tipo
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return new DadosAvaliacao(
                            resultado.getString(
                                    "cod_idef_aval"
                            ),
                            resultado.getString(
                                    "cod_idef_tril"
                            )
                    );
                }
            }
        }

        return null;
    }

    private boolean preTesteRealizado(
            Connection conexao,
            String idAluno,
            String idTrilha)
            throws SQLException {

        String sql =
                """
                SELECT 1
                FROM dbo.tbl_resultado_avaliacao r
                INNER JOIN dbo.tbl_avaliacao a
                    ON a.cod_idef_aval = r.cod_idef_aval
                WHERE
                    r.cod_idef_stud = ?
                    AND a.cod_idef_tril = ?
                    AND a.nom_tipo_aval = 'PRE'
                    AND a.ind_aval_ativ = 1
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                return resultado.next();
            }
        }
    }

    private ArrayList<DadosResposta> buscarRespostasSelecionadas(
            Connection conexao,
            String idAvaliacao,
            ArrayList<Integer> selecionadas)
            throws SQLException {

        String sql =
                """
                SELECT
                    q.cod_idef_ques,
                    q.num_ord_ques,
                    a.cod_idef_alte,
                    a.num_ord_alte,
                    a.ind_alte_corr
                FROM dbo.tbl_questao_avaliacao q
                INNER JOIN dbo.tbl_alternativa_avaliacao a
                    ON a.cod_idef_ques = q.cod_idef_ques
                WHERE
                    q.cod_idef_aval = ?
                    AND q.ind_ques_ativ = 1
                ORDER BY
                    q.num_ord_ques,
                    a.num_ord_alte
                """;

        ArrayList<DadosResposta> respostas =
                new ArrayList<>();

        String idQuestaoAtual =
                null;

        int indiceQuestao =
                -1;

        int quantidadeQuestoes =
                0;

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

                while (resultado.next()) {

                    String idQuestao =
                            resultado.getString(
                                    "cod_idef_ques"
                            );

                    if (!idQuestao.equals(
                            idQuestaoAtual
                    )) {

                        idQuestaoAtual =
                                idQuestao;

                        indiceQuestao++;
                        quantidadeQuestoes++;
                    }

                    if (indiceQuestao < 0
                            || indiceQuestao >= selecionadas.size()) {

                        continue;
                    }

                    int alternativaSelecionada =
                            selecionadas.get(
                                    indiceQuestao
                            ) + 1;

                    int alternativaBanco =
                            resultado.getInt(
                                    "num_ord_alte"
                            );

                    if (alternativaSelecionada
                            == alternativaBanco) {

                        respostas.add(
                                new DadosResposta(
                                        idQuestao,
                                        resultado.getString(
                                                "cod_idef_alte"
                                        ),
                                        resultado.getBoolean(
                                                "ind_alte_corr"
                                        )
                                )
                        );
                    }
                }
            }
        }

        if (quantidadeQuestoes == 0) {

            throw new SQLException(
                    "A avaliação não possui perguntas ativas."
            );
        }

        if (selecionadas.size()
                != quantidadeQuestoes
                || respostas.size()
                != quantidadeQuestoes) {

            throw new IllegalArgumentException(
                    "As respostas enviadas não correspondem às perguntas atuais da avaliação."
            );
        }

        return respostas;
    }

    private String salvarResultado(
            Connection conexao,
            String idAluno,
            String idAvaliacao,
            int acertos,
            int total,
            BigDecimal percentual)
            throws SQLException {

        String sqlBuscar =
                """
                SELECT cod_idef_resu_aval
                FROM dbo.tbl_resultado_avaliacao
                WITH (UPDLOCK, HOLDLOCK)
                WHERE
                    cod_idef_stud = ?
                    AND cod_idef_aval = ?
                """;

        String idResultado =
                null;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sqlBuscar
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idAvaliacao
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    idResultado =
                            resultado.getString(
                                    "cod_idef_resu_aval"
                            );
                }
            }
        }

        if (idResultado == null) {

            String sqlInserir =
                    """
                    INSERT INTO dbo.tbl_resultado_avaliacao (
                        cod_idef_stud,
                        cod_idef_aval,
                        qtd_acer,
                        qtd_tota,
                        vlr_perc
                    )
                    OUTPUT INSERTED.cod_idef_resu_aval
                    VALUES (?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement comando =
                         conexao.prepareStatement(
                                 sqlInserir
                         )) {

                comando.setString(
                        1,
                        idAluno
                );

                comando.setString(
                        2,
                        idAvaliacao
                );

                comando.setInt(
                        3,
                        acertos
                );

                comando.setInt(
                        4,
                        total
                );

                comando.setBigDecimal(
                        5,
                        percentual
                );

                try (ResultSet resultado =
                             comando.executeQuery()) {

                    if (!resultado.next()) {

                        throw new SQLException(
                                "Não foi possível criar o resultado da avaliação."
                        );
                    }

                    idResultado =
                            resultado.getString(
                                    1
                            );
                }
            }

        } else {

            try (PreparedStatement comando =
                         conexao.prepareStatement(
                                 "DELETE FROM dbo.tbl_resposta_avaliacao WHERE cod_idef_resu_aval = ?"
                         )) {

                comando.setString(
                        1,
                        idResultado
                );

                comando.executeUpdate();
            }

            String sqlAtualizar =
                    """
                    UPDATE dbo.tbl_resultado_avaliacao
                    SET
                        qtd_acer = ?,
                        qtd_tota = ?,
                        vlr_perc = ?,
                        dat_hor_real = SYSUTCDATETIME(),
                        dat_hor_ulti_atui = SYSUTCDATETIME(),
                        anomesdia = CONVERT(
                            INT,
                            CONVERT(
                                CHAR(8),
                                SYSUTCDATETIME(),
                                112
                            )
                        )
                    WHERE cod_idef_resu_aval = ?
                    """;

            try (PreparedStatement comando =
                         conexao.prepareStatement(
                                 sqlAtualizar
                         )) {

                comando.setInt(
                        1,
                        acertos
                );

                comando.setInt(
                        2,
                        total
                );

                comando.setBigDecimal(
                        3,
                        percentual
                );

                comando.setString(
                        4,
                        idResultado
                );

                comando.executeUpdate();
            }
        }

        return idResultado;
    }

    private void salvarRespostas(
            Connection conexao,
            String idResultado,
            ArrayList<DadosResposta> respostas)
            throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_resposta_avaliacao (
                    cod_idef_resu_aval,
                    cod_idef_ques,
                    cod_idef_alte,
                    ind_resp_corr
                )
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            for (DadosResposta resposta
                    : respostas) {

                comando.setString(
                        1,
                        idResultado
                );

                comando.setString(
                        2,
                        resposta.idQuestao
                );

                comando.setString(
                        3,
                        resposta.idAlternativa
                );

                comando.setBoolean(
                        4,
                        resposta.correta
                );

                comando.addBatch();
            }

            comando.executeBatch();
        }
    }

    private void atualizarStatusTrilha(
            Connection conexao,
            String idAluno,
            String idTrilha,
            boolean preTeste)
            throws SQLException {

        String sqlBuscar =
                """
                SELECT nom_stat_tril
                FROM dbo.tbl_aluno_trilha
                WITH (UPDLOCK, HOLDLOCK)
                WHERE
                    cod_idef_stud = ?
                    AND cod_idef_tril = ?
                """;

        String statusAtual =
                null;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sqlBuscar
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    statusAtual =
                            resultado.getString(
                                    "nom_stat_tril"
                            );
                }
            }
        }

        String novoStatus =
                preTeste
                        ? "PRE_REALIZADO"
                        : "POS_REALIZADO";

        if (statusAtual == null) {

            String sqlInserir =
                    """
                    INSERT INTO dbo.tbl_aluno_trilha (
                        cod_idef_stud,
                        cod_idef_tril,
                        nom_stat_tril,
                        dat_hor_inic
                    )
                    VALUES (?, ?, ?, SYSUTCDATETIME())
                    """;

            try (PreparedStatement comando =
                         conexao.prepareStatement(
                                 sqlInserir
                         )) {

                comando.setString(
                        1,
                        idAluno
                );

                comando.setString(
                        2,
                        idTrilha
                );

                comando.setString(
                        3,
                        novoStatus
                );

                comando.executeUpdate();
            }

            return;
        }

        if ("CONCLUIDA".equals(statusAtual)) {
            return;
        }

        String sqlAtualizar =
                """
                UPDATE dbo.tbl_aluno_trilha
                SET
                    nom_stat_tril = ?,
                    dat_hor_inic = COALESCE(
                        dat_hor_inic,
                        SYSUTCDATETIME()
                    ),
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
                    cod_idef_stud = ?
                    AND cod_idef_tril = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sqlAtualizar
                     )) {

            comando.setString(
                    1,
                    novoStatus
            );

            comando.setString(
                    2,
                    idAluno
            );

            comando.setString(
                    3,
                    idTrilha
            );

            comando.executeUpdate();
        }
    }

    private void validarAlunoLogado() {

        if (!SessaoUsuario.possuiUsuarioLogado()
                || !SessaoUsuario.isAluno()) {

            throw new IllegalStateException(
                    "Nenhum aluno está autenticado."
            );
        }
    }

    private void validarIdAluno(
            String idAluno) {

        if (idAluno == null
                || idAluno.isBlank()) {

            throw new IllegalArgumentException(
                    "ID do aluno inválido."
            );
        }
    }

    private void validarNumeroTrilha(
            int numeroTrilha) {

        if (numeroTrilha <= 0) {

            throw new IllegalArgumentException(
                    "Número da trilha inválido."
            );
        }
    }

    private void validarRespostas(
            ArrayList<Integer> respostas) {

        if (respostas == null
                || respostas.isEmpty()) {

            throw new IllegalArgumentException(
                    "A avaliação precisa possuir respostas."
            );
        }

        for (Integer resposta
                : respostas) {

            if (resposta == null
                    || resposta < 0) {

                throw new IllegalArgumentException(
                        "Existe uma resposta inválida na avaliação."
                );
            }
        }
    }

    private static final class DadosAvaliacao {

        private final String idAvaliacao;
        private final String idTrilha;

        private DadosAvaliacao(
                String idAvaliacao,
                String idTrilha) {

            this.idAvaliacao =
                    idAvaliacao;

            this.idTrilha =
                    idTrilha;
        }
    }

    private static final class DadosResposta {

        private final String idQuestao;
        private final String idAlternativa;
        private final boolean correta;

        private DadosResposta(
                String idQuestao,
                String idAlternativa,
                boolean correta) {

            this.idQuestao =
                    idQuestao;

            this.idAlternativa =
                    idAlternativa;

            this.correta =
                    correta;
        }
    }

    public static final class DadosResultado {

        private final boolean realizado;
        private final int acertos;
        private final int total;
        private final double percentual;

        private DadosResultado(
                boolean realizado,
                int acertos,
                int total,
                double percentual) {

            this.realizado =
                    realizado;

            this.acertos =
                    acertos;

            this.total =
                    total;

            this.percentual =
                    percentual;
        }

        private static DadosResultado naoRealizado() {

            return new DadosResultado(
                    false,
                    -1,
                    0,
                    0
            );
        }

        public boolean isRealizado() {
            return realizado;
        }

        public int getAcertos() {
            return acertos;
        }

        public int getTotal() {
            return total;
        }

        public double getPercentual() {
            return percentual;
        }
    }

    public static final class ResultadoTrilha {

        private final DadosResultado pre;
        private final DadosResultado pos;

        private ResultadoTrilha(
                DadosResultado pre,
                DadosResultado pos) {

            this.pre = pre;
            this.pos = pos;
        }

        public DadosResultado getPre() {
            return pre;
        }

        public DadosResultado getPos() {
            return pos;
        }
    }
}
