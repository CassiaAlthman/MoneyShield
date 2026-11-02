package br.com.moneyshield.repository;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.model.Missao;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class RepositorioMissoes {

    public static ArrayList<Missao> getMissoes() {

        ArrayList<Missao> missoes =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    cod_idef_miss,
                    nom_miss,
                    des_miss,
                    nom_tipo_miss,
                    qtd_meta_miss,
                    qtd_xp_reco
                FROM dbo.tbl_missao
                WHERE ind_miss_ativ = 1
                ORDER BY
                    dat_hor_cria,
                    cod_idef_miss
                """;

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                sql
                        );

                ResultSet resultado =
                        comando.executeQuery()
        ) {

            int idVisual = 1;

            while (resultado.next()) {

                int metaBanco =
                        resultado.getInt(
                                "qtd_meta_miss"
                        );

                Integer meta =
                        resultado.wasNull()
                                ? null
                                : metaBanco;

                missoes.add(
                        new Missao(
                                idVisual++,
                                resultado.getString(
                                        "cod_idef_miss"
                                ),
                                resultado.getString(
                                        "nom_miss"
                                ),
                                resultado.getString(
                                        "des_miss"
                                ),
                                resultado.getString(
                                        "nom_tipo_miss"
                                ),
                                meta,
                                resultado.getInt(
                                        "qtd_xp_reco"
                                )
                        )
                );
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao carregar missões:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return missoes;
    }

    public static Missao adicionarMissao(
            String titulo,
            String descricao) {

        return adicionarMissao(
                titulo,
                descricao,
                "ADMINISTRATIVA",
                1
        );
    }

    public static Missao adicionarMissao(
            String titulo,
            String descricao,
            String tipo,
            Integer meta) {

        if (titulo == null
                || titulo.isBlank()
                || descricao == null
                || descricao.isBlank()) {

            return null;
        }

        String tipoFinal =
                tipo == null
                        || tipo.isBlank()
                        ? "ADMINISTRATIVA"
                        : tipo.trim();

        Integer metaFinal =
                meta == null
                        || meta <= 0
                        ? 1
                        : meta;

        String sql =
                """
                INSERT INTO dbo.tbl_missao (
                    nom_miss,
                    des_miss,
                    nom_tipo_miss,
                    qtd_meta_miss,
                    qtd_xp_reco,
                    cod_idef_admi_cria
                )
                OUTPUT INSERTED.cod_idef_miss
                VALUES (?, ?, ?, ?, 100, ?)
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

            comando.setString(
                    3,
                    tipoFinal
            );

            comando.setInt(
                    4,
                    metaFinal
            );

            configurarAdmin(
                    comando,
                    5
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

                ArrayList<Missao> missoes =
                        getMissoes();

                for (Missao missao
                        : missoes) {

                    if (idBanco.equals(
                            missao.getIdBanco()
                    )) {

                        return missao;
                    }
                }
            }

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao adicionar missão:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return null;
    }

    public static Missao buscarPorId(
            int id) {

        for (Missao missao
                : getMissoes()) {

            if (missao.getId()
                    == id) {

                return missao;
            }
        }

        return null;
    }

    public static boolean editarMissao(
            int id,
            String titulo,
            String descricao) {

        Missao atual =
                buscarPorId(
                        id
                );

        if (atual == null) {
            return false;
        }

        return editarMissao(
                id,
                titulo,
                descricao,
                atual.getTipo(),
                atual.getMeta()
        );
    }

    public static boolean editarMissao(
            int id,
            String titulo,
            String descricao,
            String tipo,
            Integer meta) {

        Missao missao =
                buscarPorId(
                        id
                );

        if (missao == null
                || titulo == null
                || titulo.isBlank()
                || descricao == null
                || descricao.isBlank()) {

            return false;
        }

        String tipoFinal =
                tipo == null
                        || tipo.isBlank()
                        ? "ADMINISTRATIVA"
                        : tipo.trim();

        int metaFinal =
                meta == null
                        || meta <= 0
                        ? 1
                        : meta;

        String sql =
                """
                UPDATE dbo.tbl_missao
                SET
                    nom_miss = ?,
                    des_miss = ?,
                    nom_tipo_miss = ?,
                    qtd_meta_miss = ?,
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
                    cod_idef_miss = ?
                    AND ind_miss_ativ = 1
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

            comando.setString(
                    3,
                    tipoFinal
            );

            comando.setInt(
                    4,
                    metaFinal
            );

            comando.setString(
                    5,
                    missao.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao editar missão:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean excluirMissao(
            int id) {

        Missao missao =
                buscarPorId(
                        id
                );

        if (missao == null) {
            return false;
        }

        String sql =
                """
                UPDATE dbo.tbl_missao
                SET
                    ind_miss_ativ = 0,
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
                    cod_idef_miss = ?
                    AND ind_miss_ativ = 1
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
                    missao.getIdBanco()
            );

            return comando.executeUpdate()
                    > 0;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao excluir missão:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static int quantidade() {
        return getMissoes().size();
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
