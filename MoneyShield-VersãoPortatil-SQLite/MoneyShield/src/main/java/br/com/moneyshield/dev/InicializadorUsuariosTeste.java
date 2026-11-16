package br.com.moneyshield.dev;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class InicializadorUsuariosTeste {

    private static final String SENHA_TESTE = "Exemplo123";

    public static void main(String[] args) {

        try {
            prepararAluno();
            prepararAdministrador();

            System.out.println();
            System.out.println("Usuários de teste sincronizados com sucesso.");
            System.out.println();
            System.out.println("ALUNO");
            System.out.println("Usuário: Aluno 123");
            System.out.println("Senha: Exemplo123");
            System.out.println();
            System.out.println("ADMINISTRADOR");
            System.out.println("Usuário: ADM123");
            System.out.println("Senha: Exemplo123");

        } catch (SQLException erro) {
            System.err.println("Erro ao preparar usuários:");
            System.err.println(erro.getMessage());
            erro.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static void prepararAluno() throws SQLException {

        try (Connection conexao = ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                String idPessoa = buscarIdPessoa(
                        conexao,
                        "Aluno 123"
                );

                if (idPessoa == null) {

                    idPessoa = UUID.randomUUID().toString();

                    inserirPessoa(
                            conexao,
                            idPessoa,
                            "Aluno 123"
                    );

                    inserirLogin(
                            conexao,
                            idPessoa,
                            "Aluno 123",
                            "aluno123@moneyshield.local"
                    );

                } else {

                    atualizarLogin(
                            conexao,
                            idPessoa
                    );

                    ativarPessoa(
                            conexao,
                            idPessoa
                    );
                }

                String idAluno = buscarIdAluno(
                        conexao,
                        idPessoa
                );

                if (idAluno == null) {

                    idAluno = UUID.randomUUID().toString();

                    String sql =
                            """
                            INSERT INTO dbo.tbl_pessoa_aluno (
                                cod_idef_stud,
                                cod_idef_pess
                            )
                            VALUES (?, ?)
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(sql)) {

                        comando.setString(1, idAluno);
                        comando.setString(2, idPessoa);
                        comando.executeUpdate();
                    }

                } else {

                    String sql =
                            """
                            UPDATE dbo.tbl_pessoa_aluno
                            SET
                                ind_usua_ativ = 1,
                                dat_hor_ulti_atui = SYSUTCDATETIME()
                            WHERE cod_idef_stud = ?
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(sql)) {

                        comando.setString(1, idAluno);
                        comando.executeUpdate();
                    }
                }

                garantirProgresso(
                        conexao,
                        idAluno
                );

                conexao.commit();

                System.out.println(
                        "Aluno 123 preparado com sucesso."
                );

            } catch (SQLException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }
        }
    }

    private static void prepararAdministrador()
            throws SQLException {

        try (Connection conexao = ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                String idPessoa = buscarIdPessoa(
                        conexao,
                        "ADM123"
                );

                if (idPessoa == null) {

                    idPessoa = UUID.randomUUID().toString();

                    inserirPessoa(
                            conexao,
                            idPessoa,
                            "Administrador MoneyShield"
                    );

                    inserirLogin(
                            conexao,
                            idPessoa,
                            "ADM123",
                            "adm123@moneyshield.local"
                    );

                } else {

                    atualizarLogin(
                            conexao,
                            idPessoa
                    );

                    ativarPessoa(
                            conexao,
                            idPessoa
                    );
                }

                String idAdmin = buscarIdAdministrador(
                        conexao,
                        idPessoa
                );

                if (idAdmin == null) {

                    idAdmin = UUID.randomUUID().toString();

                    String sql =
                            """
                            INSERT INTO dbo.tbl_pessoa_admin (
                                cod_idef_admi,
                                cod_idef_pess,
                                nom_func
                            )
                            VALUES (?, ?, ?)
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(sql)) {

                        comando.setString(1, idAdmin);
                        comando.setString(2, idPessoa);
                        comando.setString(
                                3,
                                "Administrador"
                        );

                        comando.executeUpdate();
                    }

                } else {

                    String sql =
                            """
                            UPDATE dbo.tbl_pessoa_admin
                            SET
                                ind_admi_ativ = 1,
                                dat_hor_ulti_atui = SYSUTCDATETIME()
                            WHERE cod_idef_admi = ?
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(sql)) {

                        comando.setString(1, idAdmin);
                        comando.executeUpdate();
                    }
                }

                conexao.commit();

                System.out.println(
                        "ADM123 preparado com sucesso."
                );

            } catch (SQLException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }
        }
    }

    private static void inserirPessoa(
            Connection conexao,
            String idPessoa,
            String nome)
            throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_conta_pessoa (
                    cod_idef_pess,
                    nom_pess
                )
                VALUES (?, ?)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(1, idPessoa);
            comando.setString(2, nome);
            comando.executeUpdate();
        }
    }

    private static void inserirLogin(
            Connection conexao,
            String idPessoa,
            String usuario,
            String email)
            throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_pessoa_login (
                    cod_idef_pess,
                    nom_usua,
                    txt_emai,
                    txt_pass,
                    nom_algo_pass
                )
                VALUES (?, ?, ?, ?, 'BCRYPT')
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(1, idPessoa);
            comando.setString(2, usuario);
            comando.setString(3, email);
            comando.setString(
                    4,
                    gerarHash(SENHA_TESTE)
            );

            comando.executeUpdate();
        }
    }

    private static void atualizarLogin(
            Connection conexao,
            String idPessoa)
            throws SQLException {

        String sql =
                """
                UPDATE dbo.tbl_pessoa_login
                SET
                    txt_pass = ?,
                    nom_algo_pass = 'BCRYPT',
                    ind_login_ativ = 1,
                    dat_hor_ulti_atui = SYSUTCDATETIME(),
                    anomesdia = CONVERT(
                        INT,
                        CONVERT(
                            CHAR(8),
                            SYSUTCDATETIME(),
                            112
                        )
                    )
                WHERE cod_idef_pess = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    gerarHash(SENHA_TESTE)
            );

            comando.setString(
                    2,
                    idPessoa
            );

            comando.executeUpdate();
        }
    }

    private static void ativarPessoa(
            Connection conexao,
            String idPessoa)
            throws SQLException {

        String sql =
                """
                UPDATE dbo.tbl_conta_pessoa
                SET
                    ind_cont_ativ = 1,
                    dat_hor_ulti_atui = SYSUTCDATETIME()
                WHERE cod_idef_pess = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    idPessoa
            );

            comando.executeUpdate();
        }
    }

    private static String buscarIdPessoa(
            Connection conexao,
            String usuario)
            throws SQLException {

        String sql =
                """
                SELECT cod_idef_pess
                FROM dbo.tbl_pessoa_login
                WHERE nom_usua = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    usuario
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            "cod_idef_pess"
                    );
                }
            }
        }

        return null;
    }

    private static String buscarIdAluno(
            Connection conexao,
            String idPessoa)
            throws SQLException {

        String sql =
                """
                SELECT cod_idef_stud
                FROM dbo.tbl_pessoa_aluno
                WHERE cod_idef_pess = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    idPessoa
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            "cod_idef_stud"
                    );
                }
            }
        }

        return null;
    }

    private static String buscarIdAdministrador(
            Connection conexao,
            String idPessoa)
            throws SQLException {

        String sql =
                """
                SELECT cod_idef_admi
                FROM dbo.tbl_pessoa_admin
                WHERE cod_idef_pess = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    idPessoa
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return resultado.getString(
                            "cod_idef_admi"
                    );
                }
            }
        }

        return null;
    }

    private static void garantirProgresso(
            Connection conexao,
            String idAluno)
            throws SQLException {

        String sqlBusca =
                """
                SELECT 1
                FROM dbo.tbl_progresso_aluno
                WHERE cod_idef_stud = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sqlBusca)) {

            comando.setString(
                    1,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {
                    return;
                }
            }
        }

        String sqlInsert =
                """
                INSERT INTO dbo.tbl_progresso_aluno (
                    cod_idef_stud,
                    cod_idef_nivl,
                    qtd_xp
                )
                VALUES (?, 'NIVEL-1', 0)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sqlInsert)) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.executeUpdate();
        }
    }

    private static String gerarHash(
            String senha) {

        return BCrypt
                .withDefaults()
                .hashToString(
                        12,
                        senha.toCharArray()
                );
    }
}