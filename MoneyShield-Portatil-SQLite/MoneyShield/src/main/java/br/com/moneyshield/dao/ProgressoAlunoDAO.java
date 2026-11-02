package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressoAlunoDAO {

    public static final class DadosProgresso {

        private final String idAluno;
        private final String idNivel;
        private final int numeroNivel;
        private final String nomeNivel;
        private final int xp;
        private final int xpMinimoNivel;
        private final Integer xpMaximoNivel;
        private final int trilhasConcluidas;
        private final int missoesConcluidas;
        private final int simulacoesRealizadas;

        public DadosProgresso(
                String idAluno,
                String idNivel,
                int numeroNivel,
                String nomeNivel,
                int xp,
                int xpMinimoNivel,
                Integer xpMaximoNivel,
                int trilhasConcluidas,
                int missoesConcluidas,
                int simulacoesRealizadas) {

            this.idAluno = idAluno;
            this.idNivel = idNivel;
            this.numeroNivel = numeroNivel;
            this.nomeNivel = nomeNivel;
            this.xp = xp;
            this.xpMinimoNivel = xpMinimoNivel;
            this.xpMaximoNivel = xpMaximoNivel;
            this.trilhasConcluidas = trilhasConcluidas;
            this.missoesConcluidas = missoesConcluidas;
            this.simulacoesRealizadas = simulacoesRealizadas;
        }

        public String getIdAluno() {
            return idAluno;
        }

        public String getIdNivel() {
            return idNivel;
        }

        public int getNumeroNivel() {
            return numeroNivel;
        }

        public String getNomeNivel() {
            return nomeNivel;
        }

        public int getXp() {
            return xp;
        }

        public int getXpMinimoNivel() {
            return xpMinimoNivel;
        }

        public Integer getXpMaximoNivel() {
            return xpMaximoNivel;
        }

        public int getTrilhasConcluidas() {
            return trilhasConcluidas;
        }

        public int getMissoesConcluidas() {
            return missoesConcluidas;
        }

        public int getSimulacoesRealizadas() {
            return simulacoesRealizadas;
        }
    }

    private static final String SQL_BUSCAR_PROGRESSO =
            """
            SELECT
                p.cod_idef_stud,
                p.cod_idef_nivl,
                p.qtd_xp,
                p.qtd_tril_conc,
                p.qtd_miss_conc,
                p.qtd_simu_real,
                n.num_nivl,
                n.nom_nivl,
                n.qtd_xp_min,
                n.qtd_xp_max
            FROM dbo.tbl_progresso_aluno p
            INNER JOIN dbo.tbl_nivel n
                ON n.cod_idef_nivl = p.cod_idef_nivl
            WHERE
                p.cod_idef_stud = ?
                AND n.ind_nivl_ativ = 1
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

    public DadosProgresso buscarProgressoAtual()
            throws SQLException {

        if (!SessaoUsuario.isAluno()) {

            throw new IllegalStateException(
                    "O usuário autenticado não é um aluno."
            );
        }

        return buscarProgresso(
                SessaoUsuario.getIdPerfil()
        );
    }

    public DadosProgresso buscarProgresso(
            String idAluno) throws SQLException {

        validarIdAluno(
                idAluno
        );

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_BUSCAR_PROGRESSO
                        )
        ) {

            comando.setString(
                    1,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                int xpMaximo =
                        resultado.getInt(
                                "qtd_xp_max"
                        );

                Integer xpMaximoNivel =
                        resultado.wasNull()
                                ? null
                                : xpMaximo;

                return new DadosProgresso(
                        resultado.getString(
                                "cod_idef_stud"
                        ),
                        resultado.getString(
                                "cod_idef_nivl"
                        ),
                        resultado.getInt(
                                "num_nivl"
                        ),
                        resultado.getString(
                                "nom_nivl"
                        ),
                        resultado.getInt(
                                "qtd_xp"
                        ),
                        resultado.getInt(
                                "qtd_xp_min"
                        ),
                        xpMaximoNivel,
                        resultado.getInt(
                                "qtd_tril_conc"
                        ),
                        resultado.getInt(
                                "qtd_miss_conc"
                        ),
                        resultado.getInt(
                                "qtd_simu_real"
                        )
                );
            }
        }
    }

    public void salvarProgressoAtual(
            int xp,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        if (!SessaoUsuario.isAluno()) {

            throw new IllegalStateException(
                    "O usuário autenticado não é um aluno."
            );
        }

        salvarProgresso(
                SessaoUsuario.getIdPerfil(),
                xp,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );
    }

    public void salvarProgresso(
            String idAluno,
            int xp,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarValores(
                xp,
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
                            xp
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

                    int quantidadeAlterada =
                            comando.executeUpdate();

                    if (quantidadeAlterada == 0) {

                        throw new SQLException(
                                "Progresso do aluno não encontrado."
                        );
                    }
                }

                conexao.commit();

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

    private String buscarIdNivel(
            Connection conexao,
            int nivel) throws SQLException {

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

    private void validarIdAluno(
            String idAluno) {

        if (idAluno == null
                || idAluno.isBlank()) {

            throw new IllegalArgumentException(
                    "ID do aluno inválido."
            );
        }
    }

    private void validarValores(
            int xp,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas) {

        if (xp < 0) {

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
}