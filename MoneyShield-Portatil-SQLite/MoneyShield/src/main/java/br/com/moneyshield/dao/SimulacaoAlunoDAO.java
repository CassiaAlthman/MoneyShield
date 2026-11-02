package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SimulacaoAlunoDAO {

    private static final String TIPO_CENARIO =
            "CENARIO";

    private static final String TIPO_JUROS =
            "JUROS";

    private static final String TIPO_FATURA =
            "FATURA";

    private static final String SQL_BUSCAR_CENARIO =
            """
            SELECT
                cod_idef_cena
            FROM dbo.tbl_cenario_financeiro
            WHERE
                nom_cena = ?
                AND ind_cena_ativ = 1
                AND ind_cena_publ = 1
            """;

    private static final String SQL_BUSCAR_OPCAO =
            """
            SELECT
                cod_idef_opca
            FROM dbo.tbl_opcao_cenario
            WHERE
                cod_idef_cena = ?
                AND nom_opca = ?
                AND ind_opca_ativ = 1
            """;

    private static final String SQL_JA_RECEBEU_XP_CENARIO =
            """
            SELECT TOP 1 1
            FROM dbo.tbl_simulacao_aluno
            WITH (UPDLOCK, HOLDLOCK)
            WHERE
                cod_idef_stud = ?
                AND nom_tipo_simu = 'CENARIO'
                AND cod_idef_cena = ?
                AND ind_xp_conc = 1
            """;

    private static final String SQL_JA_RECEBEU_XP_TIPO =
            """
            SELECT TOP 1 1
            FROM dbo.tbl_simulacao_aluno
            WITH (UPDLOCK, HOLDLOCK)
            WHERE
                cod_idef_stud = ?
                AND nom_tipo_simu = ?
                AND ind_xp_conc = 1
            """;

    private static final String SQL_INSERIR_CENARIO =
            """
            INSERT INTO dbo.tbl_simulacao_aluno (
                cod_idef_stud,
                nom_tipo_simu,
                cod_idef_cena,
                cod_idef_opca,
                txt_entr_simu,
                txt_resu_simu,
                ind_xp_conc
            )
            VALUES (
                ?,
                'CENARIO',
                ?,
                ?,
                ?,
                ?,
                ?
            )
            """;

    private static final String SQL_INSERIR_SIMPLES =
            """
            INSERT INTO dbo.tbl_simulacao_aluno (
                cod_idef_stud,
                nom_tipo_simu,
                cod_idef_cena,
                cod_idef_opca,
                txt_entr_simu,
                txt_resu_simu,
                ind_xp_conc
            )
            VALUES (
                ?,
                ?,
                NULL,
                NULL,
                ?,
                ?,
                ?
            )
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

    private static final String SQL_LISTAR_REALIZADAS =
            """
            SELECT DISTINCT
                CASE
                    WHEN s.nom_tipo_simu = 'CENARIO'
                        THEN c.nom_cena
                    WHEN s.nom_tipo_simu = 'JUROS'
                        THEN N'Simulador de Juros'
                    WHEN s.nom_tipo_simu = 'FATURA'
                        THEN N'Simulador de Fatura'
                END AS nom_simu
            FROM dbo.tbl_simulacao_aluno s
            LEFT JOIN dbo.tbl_cenario_financeiro c
                ON c.cod_idef_cena = s.cod_idef_cena
            WHERE
                s.cod_idef_stud = ?
                AND s.ind_xp_conc = 1
            """;

    public ArrayList<String> buscarSimulacoesRealizadasAtual()
            throws SQLException {

        validarAlunoLogado();

        return buscarSimulacoesRealizadas(
                SessaoUsuario.getIdPerfil()
        );
    }

    public ArrayList<String> buscarSimulacoesRealizadas(
            String idAluno)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        ArrayList<String> simulacoes =
                new ArrayList<>();

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_LISTAR_REALIZADAS
                        )
        ) {

            comando.setString(
                    1,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    String nome =
                            resultado.getString(
                                    "nom_simu"
                            );

                    if (nome != null
                            && !nome.isBlank()) {

                        simulacoes.add(
                                nome
                        );
                    }
                }
            }
        }

        return simulacoes;
    }

    public boolean registrarJurosAtual(
            String entrada,
            String resultado,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarAlunoLogado();

        return registrarSimulacaoSimples(
                SessaoUsuario.getIdPerfil(),
                TIPO_JUROS,
                entrada,
                resultado,
                xpTotal,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );
    }

    public boolean registrarFaturaAtual(
            String entrada,
            String resultado,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarAlunoLogado();

        return registrarSimulacaoSimples(
                SessaoUsuario.getIdPerfil(),
                TIPO_FATURA,
                entrada,
                resultado,
                xpTotal,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );
    }

    public boolean registrarCenarioAtual(
            String nomeCenario,
            String nomeOpcao,
            String entrada,
            String resultado,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarAlunoLogado();

        return registrarCenario(
                SessaoUsuario.getIdPerfil(),
                nomeCenario,
                nomeOpcao,
                entrada,
                resultado,
                xpTotal,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );
    }

    public boolean registrarCenario(
            String idAluno,
            String nomeCenario,
            String nomeOpcao,
            String entrada,
            String resultado,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarTextoObrigatorio(
                nomeCenario,
                "Nome do cenário inválido."
        );

        validarTextoObrigatorio(
                nomeOpcao,
                "Nome da opção inválido."
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

                String idCenario =
                        buscarIdCenario(
                                conexao,
                                nomeCenario
                        );

                if (idCenario == null) {

                    throw new SQLException(
                            "Cenário não encontrado no banco: "
                                    + nomeCenario
                    );
                }

                String idOpcao =
                        buscarIdOpcao(
                                conexao,
                                idCenario,
                                nomeOpcao
                        );

                if (idOpcao == null) {

                    throw new SQLException(
                            "Opção não encontrada no cenário: "
                                    + nomeOpcao
                    );
                }

                boolean jaRecebeuXp =
                        jaRecebeuXpCenario(
                                conexao,
                                idAluno,
                                idCenario
                        );

                boolean concederXp =
                        !jaRecebeuXp;

                inserirCenario(
                        conexao,
                        idAluno,
                        idCenario,
                        idOpcao,
                        entrada,
                        resultado,
                        concederXp
                );

                if (concederXp) {

                    atualizarProgresso(
                            conexao,
                            idAluno,
                            xpTotal,
                            nivel,
                            trilhasConcluidas,
                            missoesConcluidas,
                            simulacoesRealizadas
                    );
                }

                conexao.commit();

                return concederXp;

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

    private boolean registrarSimulacaoSimples(
            String idAluno,
            String tipo,
            String entrada,
            String resultado,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarTipoSimples(
                tipo
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

                boolean jaRecebeuXp =
                        jaRecebeuXpTipo(
                                conexao,
                                idAluno,
                                tipo
                        );

                boolean concederXp =
                        !jaRecebeuXp;

                inserirSimulacaoSimples(
                        conexao,
                        idAluno,
                        tipo,
                        entrada,
                        resultado,
                        concederXp
                );

                if (concederXp) {

                    atualizarProgresso(
                            conexao,
                            idAluno,
                            xpTotal,
                            nivel,
                            trilhasConcluidas,
                            missoesConcluidas,
                            simulacoesRealizadas
                    );
                }

                conexao.commit();

                return concederXp;

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

    private String buscarIdCenario(
            Connection conexao,
            String nomeCenario)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_BUSCAR_CENARIO
                     )) {

            comando.setString(
                    1,
                    nomeCenario
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            "cod_idef_cena"
                    );
                }
            }
        }

        return null;
    }

    private String buscarIdOpcao(
            Connection conexao,
            String idCenario,
            String nomeOpcao)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_BUSCAR_OPCAO
                     )) {

            comando.setString(
                    1,
                    idCenario
            );

            comando.setString(
                    2,
                    nomeOpcao
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            "cod_idef_opca"
                    );
                }
            }
        }

        return null;
    }

    private boolean jaRecebeuXpCenario(
            Connection conexao,
            String idAluno,
            String idCenario)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_JA_RECEBEU_XP_CENARIO
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idCenario
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                return resultado.next();
            }
        }
    }

    private boolean jaRecebeuXpTipo(
            Connection conexao,
            String idAluno,
            String tipo)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_JA_RECEBEU_XP_TIPO
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    tipo
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                return resultado.next();
            }
        }
    }

    private void inserirCenario(
            Connection conexao,
            String idAluno,
            String idCenario,
            String idOpcao,
            String entrada,
            String resultado,
            boolean concederXp)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_INSERIR_CENARIO
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idCenario
            );

            comando.setString(
                    3,
                    idOpcao
            );

            comando.setString(
                    4,
                    normalizarTexto(
                            entrada
                    )
            );

            comando.setString(
                    5,
                    normalizarTexto(
                            resultado
                    )
            );

            comando.setBoolean(
                    6,
                    concederXp
            );

            comando.executeUpdate();
        }
    }

    private void inserirSimulacaoSimples(
            Connection conexao,
            String idAluno,
            String tipo,
            String entrada,
            String resultado,
            boolean concederXp)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_INSERIR_SIMPLES
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    tipo
            );

            comando.setString(
                    3,
                    normalizarTexto(
                            entrada
                    )
            );

            comando.setString(
                    4,
                    normalizarTexto(
                            resultado
                    )
            );

            comando.setBoolean(
                    5,
                    concederXp
            );

            comando.executeUpdate();
        }
    }

    private void atualizarProgresso(
            Connection conexao,
            String idAluno,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

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

    private void validarTipoSimples(
            String tipo) {

        if (!TIPO_JUROS.equals(
                tipo
        ) && !TIPO_FATURA.equals(
                tipo
        )) {

            throw new IllegalArgumentException(
                    "Tipo de simulação inválido."
            );
        }
    }

    private void validarTextoObrigatorio(
            String texto,
            String mensagem) {

        if (texto == null
                || texto.isBlank()) {

            throw new IllegalArgumentException(
                    mensagem
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

    private String normalizarTexto(
            String texto) {

        if (texto == null
                || texto.isBlank()) {

            return null;
        }

        return texto.trim();
    }
}