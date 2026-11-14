package br.com.moneyshield.repository;

import br.com.moneyshield.model.CenarioFinanceiro;
import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.model.OpcaoCenario;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class RepositorioCenarios {

    public static CenarioFinanceiro adicionarCenario(
            String titulo,
            String descricao,
            boolean publicado) {

        if (titulo == null
                || titulo.isBlank()
                || descricao == null
                || descricao.isBlank()) {

            return null;
        }

        String sql =
                """
                INSERT INTO dbo.tbl_cenario_financeiro (
                    nom_cena,
                    des_cena,
                    ind_cena_publ,
                    cod_idef_admi_cria
                )
                OUTPUT INSERTED.cod_idef_cena
                VALUES (?, ?, ?, ?)
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
                    titulo.trim()
            );

            comando.setString(
                    2,
                    descricao.trim()
            );

            comando.setBoolean(
                    3,
                    publicado
            );

            configurarAdmin(
                    comando,
                    4
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                String idBanco =
                        resultado.getString(
                                1
                        );

                ArrayList<CenarioFinanceiro> cenarios =
                        getCenarios();

                for (CenarioFinanceiro cenario
                        : cenarios) {

                    if (idBanco.equals(
                            cenario.getIdBanco()
                    )) {

                        return cenario;
                    }
                }
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao adicionar cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return null;
    }

    public static ArrayList<CenarioFinanceiro> getCenarios() {

        ArrayList<CenarioFinanceiro> cenarios =
                new ArrayList<>();

        String sqlCenarios =
                """
                SELECT
                    cod_idef_cena,
                    nom_cena,
                    des_cena,
                    ind_cena_publ
                FROM dbo.tbl_cenario_financeiro
                WHERE ind_cena_ativ = 1
                ORDER BY
                    dat_hor_cria,
                    cod_idef_cena
                """;

        String sqlOpcoes =
                """
                SELECT
                    cod_idef_opca,
                    nom_opca,
                    des_cons,
                    num_ord_opca
                FROM dbo.tbl_opcao_cenario
                WHERE
                    cod_idef_cena = ?
                    AND ind_opca_ativ = 1
                ORDER BY num_ord_opca
                """;

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            try (
                    PreparedStatement comandoCenarios =
                            conexao.prepareStatement(
                                    sqlCenarios
                            );

                    ResultSet resultadoCenarios =
                            comandoCenarios.executeQuery()
            ) {

                int idVisual = 1;

                while (resultadoCenarios.next()) {

                    String idBanco =
                            resultadoCenarios.getString(
                                    "cod_idef_cena"
                            );

                    CenarioFinanceiro cenario =
                            new CenarioFinanceiro(
                                    idVisual++,
                                    idBanco,
                                    resultadoCenarios.getString(
                                            "nom_cena"
                                    ),
                                    resultadoCenarios.getString(
                                            "des_cena"
                                    ),
                                    resultadoCenarios.getBoolean(
                                            "ind_cena_publ"
                                    ),
                                    true
                            );

                    try (PreparedStatement comandoOpcoes =
                                 conexao.prepareStatement(
                                         sqlOpcoes
                                 )) {

                        comandoOpcoes.setString(
                                1,
                                idBanco
                        );

                        try (ResultSet resultadoOpcoes =
                                     comandoOpcoes.executeQuery()) {

                            while (resultadoOpcoes.next()) {

                                cenario.adicionarOpcaoCarregada(
                                        new OpcaoCenario(
                                                resultadoOpcoes.getInt(
                                                        "num_ord_opca"
                                                ),
                                                resultadoOpcoes.getString(
                                                        "cod_idef_opca"
                                                ),
                                                idBanco,
                                                resultadoOpcoes.getString(
                                                        "nom_opca"
                                                ),
                                                resultadoOpcoes.getString(
                                                        "des_cons"
                                                ),
                                                true
                                        )
                                );
                            }
                        }
                    }

                    cenarios.add(
                            cenario
                    );
                }
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao carregar cenários:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return cenarios;
    }

    public static ArrayList<CenarioFinanceiro> getCenariosPublicados() {

        ArrayList<CenarioFinanceiro> publicados =
                new ArrayList<>();

        for (CenarioFinanceiro cenario
                : getCenarios()) {

            if (cenario.isPublicado()) {

                publicados.add(
                        cenario
                );
            }
        }

        return publicados;
    }

    public static CenarioFinanceiro buscarPorId(
            int id) {

        for (CenarioFinanceiro cenario
                : getCenarios()) {

            if (cenario.getId() == id) {
                return cenario;
            }
        }

        return null;
    }

    public static boolean excluirCenario(
            int id) {

        CenarioFinanceiro cenario =
                buscarPorId(
                        id
                );

        if (cenario == null) {
            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_cenario_financeiro
                SET
                    ind_cena_ativ = 0,
                    ind_cena_publ = 0,
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
                    cod_idef_cena = ?
                    AND ind_cena_ativ = 1
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
                    cenario.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao excluir cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static int quantidade() {
        return getCenarios().size();
    }

    public static int quantidadePublicados() {

        int quantidade = 0;

        for (CenarioFinanceiro cenario
                : getCenarios()) {

            if (cenario.isPublicado()) {
                quantidade++;
            }
        }

        return quantidade;
    }

    public static boolean atualizarCenario(
            CenarioFinanceiro cenario) {

        if (cenario == null
                || cenario.getIdBanco() == null) {

            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_cenario_financeiro
                SET
                    nom_cena = ?,
                    des_cena = ?,
                    ind_cena_publ = ?,
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
                    cod_idef_cena = ?
                    AND ind_cena_ativ = 1
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
                    cenario.getTitulo()
            );

            comando.setString(
                    2,
                    cenario.getDescricao()
            );

            comando.setBoolean(
                    3,
                    cenario.isPublicado()
            );

            comando.setString(
                    4,
                    cenario.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao atualizar cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static OpcaoCenario adicionarOpcao(
            CenarioFinanceiro cenario,
            String titulo,
            String consequencia) {

        if (cenario == null
                || cenario.getIdBanco() == null
                || titulo == null
                || titulo.isBlank()
                || consequencia == null
                || consequencia.isBlank()) {

            return null;
        }

        String sqlOrdem =
                """
                SELECT ISNULL(MAX(num_ord_opca), 0) + 1
                FROM dbo.tbl_opcao_cenario
                WHERE cod_idef_cena = ?
                """;

        String sqlInserir =
                """
                INSERT INTO dbo.tbl_opcao_cenario (
                    cod_idef_cena,
                    nom_opca,
                    des_cons,
                    num_ord_opca
                )
                OUTPUT INSERTED.cod_idef_opca
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                int ordem;

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     sqlOrdem
                             )) {

                    comando.setString(
                            1,
                            cenario.getIdBanco()
                    );

                    try (ResultSet resultado =
                                 comando.executeQuery()) {

                        resultado.next();

                        ordem =
                                resultado.getInt(
                                        1
                                );
                    }
                }

                String idBanco;

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     sqlInserir
                             )) {

                    comando.setString(
                            1,
                            cenario.getIdBanco()
                    );

                    comando.setString(
                            2,
                            titulo.trim()
                    );

                    comando.setString(
                            3,
                            consequencia.trim()
                    );

                    comando.setInt(
                            4,
                            ordem
                    );

                    try (ResultSet resultado =
                                 comando.executeQuery()) {

                        if (!resultado.next()) {

                            throw new SQLException(
                                    "Não foi possível criar a opção."
                            );
                        }

                        idBanco =
                                resultado.getString(
                                        1
                                );
                    }
                }

                conexao.commit();

                return new OpcaoCenario(
                        ordem,
                        idBanco,
                        cenario.getIdBanco(),
                        titulo.trim(),
                        consequencia.trim(),
                        true
                );

            } catch (SQLException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao adicionar opção ao cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return null;
        }
    }

    public static boolean atualizarOpcao(
            OpcaoCenario opcao) {

        if (opcao == null
                || opcao.getIdBanco() == null) {

            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_opcao_cenario
                SET
                    nom_opca = ?,
                    des_cons = ?,
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
                    cod_idef_opca = ?
                    AND ind_opca_ativ = 1
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
                    opcao.getTitulo()
            );

            comando.setString(
                    2,
                    opcao.getConsequencia()
            );

            comando.setString(
                    3,
                    opcao.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao atualizar opção do cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean excluirOpcao(
            OpcaoCenario opcao) {

        if (opcao == null
                || opcao.getIdBanco() == null) {

            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_opcao_cenario
                SET
                    ind_opca_ativ = 0,
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
                    cod_idef_opca = ?
                    AND ind_opca_ativ = 1
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
                    opcao.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao excluir opção do cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    private static void configurarAdmin(
            PreparedStatement comando,
            int indice)
            throws SQLException {

        if (SessaoUsuario.isAdministrador()) {

            comando.setString(
                    indice,
                    SessaoUsuario.getIdPerfil()
            );

        } else {

            comando.setNull(
                    indice,
                    Types.VARCHAR
            );
        }
    }
}
