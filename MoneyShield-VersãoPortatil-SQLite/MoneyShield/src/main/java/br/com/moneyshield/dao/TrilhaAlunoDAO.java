package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrilhaAlunoDAO {

    private static final String SQL_BUSCAR_TRILHA =
            """
            SELECT
                cod_idef_tril,
                qtd_xp_reco
            FROM dbo.tbl_trilha
            WHERE
                nom_tril = ?
                AND ind_tril_ativ = 1
            """;

    private static final String SQL_BUSCAR_CONCLUSAO =
            """
            SELECT
                nom_stat_tril,
                ind_xp_conc
            FROM dbo.tbl_aluno_trilha
            WITH (UPDLOCK, HOLDLOCK)
            WHERE
                cod_idef_stud = ?
                AND cod_idef_tril = ?
            """;

    private static final String SQL_INSERIR_CONCLUSAO =
            """
            INSERT INTO dbo.tbl_aluno_trilha (
                cod_idef_stud,
                cod_idef_tril,
                nom_stat_tril,
                ind_xp_conc,
                dat_hor_inic,
                dat_hor_conc
            )
            VALUES (
                ?,
                ?,
                'CONCLUIDA',
                1,
                SYSUTCDATETIME(),
                SYSUTCDATETIME()
            )
            """;

    private static final String SQL_ATUALIZAR_CONCLUSAO =
            """
            UPDATE dbo.tbl_aluno_trilha
            SET
                nom_stat_tril = 'CONCLUIDA',
                ind_xp_conc = 1,
                dat_hor_inic = COALESCE(
                    dat_hor_inic,
                    SYSUTCDATETIME()
                ),
                dat_hor_conc = SYSUTCDATETIME(),
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

    private static final String SQL_LISTAR_CONCLUIDAS =
            """
            SELECT
                t.nom_tril
            FROM dbo.tbl_aluno_trilha at
            INNER JOIN dbo.tbl_trilha t
                ON t.cod_idef_tril = at.cod_idef_tril
            WHERE
                at.cod_idef_stud = ?
                AND at.nom_stat_tril = 'CONCLUIDA'
            ORDER BY at.dat_hor_conc
            """;

    private static final String SQL_BUSCAR_NIVEL =
            """
            SELECT
                cod_idef_nivl
            FROM dbo.tbl_nivel
            WHERE
                num_nivl = ?
                AND ind_nivl_ativ = 1
            """;

    private static final String SQL_ATUALIZAR_PROGRESSO =
            """
            UPDATE dbo.tbl_progresso_aluno
            SET
                cod_idef_nivl = ?,
                qtd_xp = ?,
                qtd_tril_conc = ?,
                qtd_miss_conc = ?,
                qtd_simu_real = ?,
                dat_hor_ulti_atui = SYSUTCDATETIME(),
                anomesdia = CONVERT(
                    INT,
                    CONVERT(
                        CHAR(8),
                        SYSUTCDATETIME(),
                        112
                    )
                )
            WHERE cod_idef_stud = ?
            """;

    public ArrayList<String> buscarTrilhasConcluidasAtual()
            throws SQLException {

        validarAlunoLogado();

        return buscarTrilhasConcluidas(
                SessaoUsuario.getIdPerfil()
        );
    }

    public ArrayList<String> buscarTrilhasConcluidas(
            String idAluno)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        ArrayList<String> trilhas =
                new ArrayList<>();

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_LISTAR_CONCLUIDAS
                        )
        ) {

            comando.setString(
                    1,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    trilhas.add(
                            resultado.getString(
                                    "nom_tril"
                            )
                    );
                }
            }
        }

        return trilhas;
    }

    public boolean trilhaConcluidaAtual(
            String nomeTrilha)
            throws SQLException {

        validarAlunoLogado();

        return trilhaConcluida(
                SessaoUsuario.getIdPerfil(),
                nomeTrilha
        );
    }

    public boolean trilhaConcluida(
            String idAluno,
            String nomeTrilha)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarNomeTrilha(
                nomeTrilha
        );

        String sql =
                """
                SELECT 1
                FROM dbo.tbl_aluno_trilha at
                INNER JOIN dbo.tbl_trilha t
                    ON t.cod_idef_tril = at.cod_idef_tril
                WHERE
                    at.cod_idef_stud = ?
                    AND t.nom_tril = ?
                    AND at.nom_stat_tril = 'CONCLUIDA'
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

            comando.setString(
                    2,
                    nomeTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                return resultado.next();
            }
        }
    }

    public boolean concluirTrilhaAtual(
            String nomeTrilha,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarAlunoLogado();

        return concluirTrilha(
                SessaoUsuario.getIdPerfil(),
                nomeTrilha,
                xpTotal,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );
    }

    public boolean concluirTrilha(
            String idAluno,
            String nomeTrilha,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarNomeTrilha(
                nomeTrilha
        );

        validarProgresso(
                xpTotal,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(
                    false
            );

            try {

                DadosTrilha trilha =
                        buscarTrilha(
                                conexao,
                                nomeTrilha
                        );

                if (trilha == null) {

                    throw new SQLException(
                            "Trilha não encontrada no banco: "
                                    + nomeTrilha
                    );
                }

                boolean existeRegistro =
                        false;

                boolean jaConcluida =
                        false;

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     SQL_BUSCAR_CONCLUSAO
                             )) {

                    comando.setString(
                            1,
                            idAluno
                    );

                    comando.setString(
                            2,
                            trilha.id
                    );

                    try (ResultSet resultado =
                                 comando.executeQuery()) {

                        if (resultado.next()) {

                            existeRegistro =
                                    true;

                            String status =
                                    resultado.getString(
                                            "nom_stat_tril"
                                    );

                            boolean xpConcedido =
                                    resultado.getBoolean(
                                            "ind_xp_conc"
                                    );

                            jaConcluida =
                                    "CONCLUIDA".equals(
                                            status
                                    )
                                            || xpConcedido;
                        }
                    }
                }

                if (jaConcluida) {

                    conexao.rollback();

                    return false;
                }

                if (existeRegistro) {

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         SQL_ATUALIZAR_CONCLUSAO
                                 )) {

                        comando.setString(
                                1,
                                idAluno
                        );

                        comando.setString(
                                2,
                                trilha.id
                        );

                        comando.executeUpdate();
                    }

                } else {

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         SQL_INSERIR_CONCLUSAO
                                 )) {

                        comando.setString(
                                1,
                                idAluno
                        );

                        comando.setString(
                                2,
                                trilha.id
                        );

                        comando.executeUpdate();
                    }
                }

                String idNivel =
                        buscarIdNivel(
                                conexao,
                                nivel
                        );

                if (idNivel == null) {

                    throw new SQLException(
                            "Nível "
                                    + nivel
                                    + " não encontrado no banco."
                    );
                }

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     SQL_ATUALIZAR_PROGRESSO
                             )) {

                    comando.setString(
                            1,
                            idNivel
                    );

                    comando.setInt(
                            2,
                            xpTotal
                    );

                    comando.setInt(
                            3,
                            trilhasConcluidas
                    );

                    comando.setInt(
                            4,
                            missoesConcluidas
                    );

                    comando.setInt(
                            5,
                            simulacoesRealizadas
                    );

                    comando.setString(
                            6,
                            idAluno
                    );

                    int alterados =
                            comando.executeUpdate();

                    if (alterados == 0) {

                        throw new SQLException(
                                "Progresso do aluno não encontrado."
                        );
                    }
                }

                conexao.commit();

                return true;

            } catch (SQLException erro) {

                conexao.rollback();

                throw erro;

            } finally {

                conexao.setAutoCommit(
                        true
                );
            }
        }
    }

    public int buscarXpTrilhaAtual(
            String nomeTrilha)
            throws SQLException {

        validarAlunoLogado();

        validarNomeTrilha(
                nomeTrilha
        );

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            DadosTrilha trilha =
                    buscarTrilha(
                            conexao,
                            nomeTrilha
                    );

            if (trilha == null) {

                throw new SQLException(
                        "Trilha não encontrada no banco: "
                                + nomeTrilha
                );
            }

            return trilha.xp;
        }
    }

    private DadosTrilha buscarTrilha(
            Connection conexao,
            String nomeTrilha)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_BUSCAR_TRILHA
                     )) {

            comando.setString(
                    1,
                    nomeTrilha
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {

                    return null;
                }

                return new DadosTrilha(
                        resultado.getString(
                                "cod_idef_tril"
                        ),
                        resultado.getInt(
                                "qtd_xp_reco"
                        )
                );
            }
        }
    }

    private String buscarIdNivel(
            Connection conexao,
            int nivel)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_BUSCAR_NIVEL
                     )) {

            comando.setInt(
                    1,
                    nivel
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            "cod_idef_nivl"
                    );
                }
            }
        }

        return null;
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

    private void validarNomeTrilha(
            String nomeTrilha) {

        if (nomeTrilha == null
                || nomeTrilha.isBlank()) {

            throw new IllegalArgumentException(
                    "Nome da trilha inválido."
            );
        }
    }

    private void validarProgresso(
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas) {

        if (xpTotal < 0) {

            throw new IllegalArgumentException(
                    "XP não pode ser negativo."
            );
        }

        if (nivel < 1
                || nivel > 4) {

            throw new IllegalArgumentException(
                    "Nível inválido."
            );
        }

        if (trilhasConcluidas < 0
                || missoesConcluidas < 0
                || simulacoesRealizadas < 0) {

            throw new IllegalArgumentException(
                    "Os totais de progresso não podem ser negativos."
            );
        }
    }

    private static final class DadosTrilha {

        private final String id;
        private final int xp;

        private DadosTrilha(
                String id,
                int xp) {

            this.id =
                    id;

            this.xp =
                    xp;
        }
    }
}