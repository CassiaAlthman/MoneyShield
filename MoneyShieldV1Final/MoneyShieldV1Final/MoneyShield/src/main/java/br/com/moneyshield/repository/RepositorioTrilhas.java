package br.com.moneyshield.repository;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class RepositorioTrilhas {

    private static final String SQL_LISTAR =
            """
            SELECT
                cod_idef_tril,
                nom_tril,
                num_ord_exib
            FROM dbo.tbl_trilha
            WHERE ind_tril_ativ = 1
            ORDER BY num_ord_exib
            """;

    public static ArrayList<String> getTrilhas() {

        ArrayList<String> trilhas =
                new ArrayList<>();

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_LISTAR
                        );

                ResultSet resultado =
                        comando.executeQuery()
        ) {

            while (resultado.next()) {

                trilhas.add(
                        resultado.getString(
                                "nom_tril"
                        )
                );
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao carregar trilhas:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return trilhas;
    }

    public static ArrayList<String> listar() {
        return getTrilhas();
    }

    public static String get(
            int indice) {

        ArrayList<String> trilhas =
                getTrilhas();

        if (indice < 0
                || indice >= trilhas.size()) {

            return null;
        }

        return trilhas.get(
                indice
        );
    }

    public static int getNumeroOrdem(
            int indice) {

        if (indice < 0) {
            return -1;
        }

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_LISTAR
                        );

                ResultSet resultado =
                        comando.executeQuery()
        ) {

            int atual = 0;

            while (resultado.next()) {

                if (atual == indice) {

                    return resultado.getInt(
                            "num_ord_exib"
                    );
                }

                atual++;
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao localizar a ordem da trilha:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return -1;
    }

    public static void adicionar(
            String nome) {

        adicionarTrilha(
                nome
        );
    }

    public static void adicionarTrilha(
            String nome) {

        if (nome == null
                || nome.isBlank()) {

            return;
        }

        String sql =
                """
                INSERT INTO dbo.tbl_trilha (
                    nom_tril,
                    des_tril,
                    txt_cont_tril,
                    num_ord_exib,
                    qtd_xp_reco,
                    cod_idef_admi_cria
                )
                SELECT
                    ?,
                    NULL,
                    NULL,
                    ISNULL(MAX(num_ord_exib), 0) + 1,
                    150,
                    ?
                FROM dbo.tbl_trilha
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
                    nome.trim()
            );

            configurarAdmin(
                    comando,
                    2
            );

            comando.executeUpdate();

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao adicionar trilha:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }
    }

    public static boolean editar(
            int indice,
            String nome) {

        return editarTrilha(
                indice,
                nome
        );
    }

    public static boolean editarTrilha(
            int indice,
            String nome) {

        if (nome == null
                || nome.isBlank()) {

            return false;
        }

        String id =
                buscarIdPorIndice(
                        indice
                );

        if (id == null) {
            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_trilha
                SET
                    nom_tril = ?,
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
                    cod_idef_tril = ?
                    AND ind_tril_ativ = 1
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
                    nome.trim()
            );

            comando.setString(
                    2,
                    id
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao editar trilha:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean excluir(
            int indice) {

        return excluirTrilha(
                indice
        );
    }

    public static boolean excluirTrilha(
            int indice) {

        String id =
                buscarIdPorIndice(
                        indice
                );

        if (id == null) {
            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_trilha
                SET
                    ind_tril_ativ = 0,
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
                    cod_idef_tril = ?
                    AND ind_tril_ativ = 1
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
                    id
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao excluir trilha:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static int quantidade() {
        return getTrilhas().size();
    }

    private static String buscarIdPorIndice(
            int indice) {

        if (indice < 0) {
            return null;
        }

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_LISTAR
                        );

                ResultSet resultado =
                        comando.executeQuery()
        ) {

            int atual = 0;

            while (resultado.next()) {

                if (atual == indice) {

                    return resultado.getString(
                            "cod_idef_tril"
                    );
                }

                atual++;
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao localizar trilha:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return null;
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
