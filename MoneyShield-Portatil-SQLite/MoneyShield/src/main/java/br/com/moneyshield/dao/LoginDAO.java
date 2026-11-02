package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.UsuarioAutenticado;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private static final String SQL_BUSCAR_USUARIO =
            """
            SELECT
                l.cod_idef_pess,
                l.nom_usua,
                l.txt_emai,
                l.txt_pass,
                l.nom_algo_pass,
                p.nom_pess,
                a.cod_idef_admi,
                s.cod_idef_stud
            FROM dbo.tbl_pessoa_login l
            INNER JOIN dbo.tbl_conta_pessoa p
                ON p.cod_idef_pess = l.cod_idef_pess
            LEFT JOIN dbo.tbl_pessoa_admin a
                ON a.cod_idef_pess = l.cod_idef_pess
                AND a.ind_admi_ativ = 1
            LEFT JOIN dbo.tbl_pessoa_aluno s
                ON s.cod_idef_pess = l.cod_idef_pess
                AND s.ind_usua_ativ = 1
            WHERE
                (l.nom_usua = ? OR l.txt_emai = ?)
                AND l.ind_login_ativ = 1
                AND p.ind_cont_ativ = 1
            """;

    private static final String SQL_ATUALIZAR_ULTIMO_LOGIN =
            """
            UPDATE dbo.tbl_pessoa_login
            SET
                dat_hor_ulti_login = SYSUTCDATETIME(),
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

    private static final String SQL_BUSCAR_USUARIO_SQLITE =
            """
            SELECT
                l.cod_idef_pess,
                l.nom_usua,
                l.txt_emai,
                l.txt_pass,
                l.nom_algo_pass,
                p.nom_pess,
                a.cod_idef_admi,
                s.cod_idef_stud
            FROM tbl_pessoa_login l
            INNER JOIN tbl_conta_pessoa p
                ON p.cod_idef_pess = l.cod_idef_pess
            LEFT JOIN tbl_pessoa_admin a
                ON a.cod_idef_pess = l.cod_idef_pess
                AND a.ind_admi_ativ = 1
            LEFT JOIN tbl_pessoa_aluno s
                ON s.cod_idef_pess = l.cod_idef_pess
                AND s.ind_usua_ativ = 1
            WHERE
                (LOWER(l.nom_usua) = LOWER(?) OR LOWER(l.txt_emai) = LOWER(?))
                AND l.ind_login_ativ = 1
                AND p.ind_cont_ativ = 1
            """;

    private static final String SQL_ATUALIZAR_ULTIMO_LOGIN_SQLITE =
            """
            UPDATE tbl_pessoa_login
            SET
                dat_hor_ulti_login = CURRENT_TIMESTAMP,
                dat_hor_ulti_atui = CURRENT_TIMESTAMP,
                anomesdia = CAST(strftime('%Y%m%d','now') AS INTEGER)
            WHERE cod_idef_pess = ?
            """;

    public UsuarioAutenticado autenticar(
            String identificador,
            String senha) throws SQLException {

        if (identificador == null
                || identificador.isBlank()
                || senha == null
                || senha.isBlank()) {

            return null;
        }

        String identificadorLimpo =
                identificador.trim();

        try (
                Connection conexao = ConexaoBanco.abrir();
                PreparedStatement comando =
                        conexao.prepareStatement(
                                ConexaoBanco.usandoSQLite()
                                        ? SQL_BUSCAR_USUARIO_SQLITE
                                        : SQL_BUSCAR_USUARIO
                        )
        ) {

            comando.setString(
                    1,
                    identificadorLimpo
            );

            comando.setString(
                    2,
                    identificadorLimpo
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                String hash =
                        resultado.getString(
                                "txt_pass"
                        );

                String algoritmo =
                        resultado.getString(
                                "nom_algo_pass"
                        );

                if (!senhaValida(
                        senha,
                        hash,
                        algoritmo
                )) {
                    return null;
                }

                String idPessoa =
                        resultado.getString(
                                "cod_idef_pess"
                        );

                String idAdministrador =
                        resultado.getString(
                                "cod_idef_admi"
                        );

                String idAluno =
                        resultado.getString(
                                "cod_idef_stud"
                        );

                UsuarioAutenticado usuario;

                if (idAdministrador != null) {

                    usuario =
                            new UsuarioAutenticado(
                                    idPessoa,
                                    idAdministrador,
                                    resultado.getString(
                                            "nom_pess"
                                    ),
                                    resultado.getString(
                                            "nom_usua"
                                    ),
                                    resultado.getString(
                                            "txt_emai"
                                    ),
                                    UsuarioAutenticado.TipoUsuario.ADMINISTRADOR
                            );

                } else if (idAluno != null) {

                    usuario =
                            new UsuarioAutenticado(
                                    idPessoa,
                                    idAluno,
                                    resultado.getString(
                                            "nom_pess"
                                    ),
                                    resultado.getString(
                                            "nom_usua"
                                    ),
                                    resultado.getString(
                                            "txt_emai"
                                    ),
                                    UsuarioAutenticado.TipoUsuario.ALUNO
                            );

                } else {
                    return null;
                }

                try {
                    atualizarUltimoLogin(
                            conexao,
                            idPessoa
                    );
                } catch (SQLException erroAtualizacao) {
                    if (!ConexaoBanco.usandoSQLite()) {
                        throw erroAtualizacao;
                    }

                    registrarDiagnostico(
                            "Falha ao atualizar o último login no SQLite: "
                                    + erroAtualizacao.getMessage()
                    );
                }

                registrarDiagnostico(
                        "Autenticação SQLite concluída para: "
                                + usuario.getUsuario()
                );

                return usuario;
            }
        }
    }

    private boolean senhaValida(
            String senha,
            String hash,
            String algoritmo) {

        if (hash == null
                || hash.isBlank()
                || algoritmo == null
                || !algoritmo.equalsIgnoreCase(
                "BCRYPT"
        )) {
            return false;
        }

        try {

            BCrypt.Result resultado =
                    BCrypt
                            .verifyer()
                            .verify(
                                    senha.toCharArray(),
                                    hash
                            );

            return resultado.verified;

        } catch (RuntimeException erro) {
            return false;
        }
    }

    private void atualizarUltimoLogin(
            Connection conexao,
            String idPessoa) throws SQLException {

        String sql = ConexaoBanco.usandoSQLite()
                ? SQL_ATUALIZAR_ULTIMO_LOGIN_SQLITE
                : SQL_ATUALIZAR_ULTIMO_LOGIN;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    idPessoa
            );

            comando.executeUpdate();
        }
    }

    private void registrarDiagnostico(String mensagem) {
        if (!ConexaoBanco.usandoSQLite()) {
            return;
        }

        try {
            java.nio.file.Path pasta = java.nio.file.Paths.get(
                    "portable",
                    "logs"
            );

            java.nio.file.Files.createDirectories(pasta);

            java.nio.file.Path arquivo = pasta.resolve(
                    "login-portatil.log"
            );

            String linha = java.time.LocalDateTime.now()
                    + " - "
                    + mensagem
                    + System.lineSeparator();

            java.nio.file.Files.writeString(
                    arquivo,
                    linha,
                    java.nio.charset.StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND
            );
        } catch (Exception ignorado) {
        }
    }

    public static void main(String[] args) {

        LoginDAO loginDAO =
                new LoginDAO();

        testarLogin(
                loginDAO,
                "Aluno 123",
                "Exemplo123"
        );

        System.out.println();

        testarLogin(
                loginDAO,
                "ADM123",
                "Exemplo123"
        );

        System.out.println();

        testarLogin(
                loginDAO,
                "Aluno 123",
                "SenhaErrada"
        );
    }

    private static void testarLogin(
            LoginDAO loginDAO,
            String usuario,
            String senha) {

        System.out.println(
                "Testando: " + usuario
        );

        try {

            UsuarioAutenticado autenticado =
                    loginDAO.autenticar(
                            usuario,
                            senha
                    );

            if (autenticado == null) {

                System.out.println(
                        "Login recusado."
                );

                return;
            }

            System.out.println(
                    "Login realizado com sucesso."
            );

            System.out.println(
                    "Nome: "
                            + autenticado.getNome()
            );

            System.out.println(
                    "Usuário: "
                            + autenticado.getUsuario()
            );

            System.out.println(
                    "Tipo: "
                            + autenticado.getTipo()
            );

            System.out.println(
                    "ID pessoa: "
                            + autenticado.getIdPessoa()
            );

            System.out.println(
                    "ID perfil: "
                            + autenticado.getIdPerfil()
            );

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao consultar o banco:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }
    }
}