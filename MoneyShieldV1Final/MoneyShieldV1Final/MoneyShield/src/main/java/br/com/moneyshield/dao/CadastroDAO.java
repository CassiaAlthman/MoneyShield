package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

public class CadastroDAO {

    public enum ResultadoCadastro {
        SUCESSO,
        NOME_INVALIDO,
        USUARIO_INVALIDO,
        EMAIL_INVALIDO,
        SENHA_INVALIDA,
        USUARIO_EXISTENTE,
        EMAIL_EXISTENTE,
        NIVEL_INICIAL_NAO_ENCONTRADO
    }

    private static final Pattern PADRAO_EMAIL =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            );

    public ResultadoCadastro cadastrarAluno(
            String nome,
            String usuario,
            String email,
            String senha) throws SQLException {

        nome = limpar(nome);
        usuario = limpar(usuario);
        email = limpar(email);

        ResultadoCadastro validacao =
                validarDados(
                        nome,
                        usuario,
                        email,
                        senha
                );

        if (validacao != ResultadoCadastro.SUCESSO) {
            return validacao;
        }

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                if (usuarioExiste(
                        conexao,
                        usuario
                )) {

                    conexao.rollback();

                    return ResultadoCadastro.USUARIO_EXISTENTE;
                }

                if (emailExiste(
                        conexao,
                        email
                )) {

                    conexao.rollback();

                    return ResultadoCadastro.EMAIL_EXISTENTE;
                }

                String idNivelInicial =
                        buscarNivelInicial(
                                conexao
                        );

                if (idNivelInicial == null) {

                    conexao.rollback();

                    return ResultadoCadastro.NIVEL_INICIAL_NAO_ENCONTRADO;
                }

                String idPessoa =
                        UUID.randomUUID()
                                .toString();

                String idAluno =
                        UUID.randomUUID()
                                .toString();

                inserirPessoa(
                        conexao,
                        idPessoa,
                        nome
                );

                inserirLogin(
                        conexao,
                        idPessoa,
                        usuario,
                        email,
                        senha
                );

                inserirAluno(
                        conexao,
                        idAluno,
                        idPessoa
                );

                inserirProgressoInicial(
                        conexao,
                        idAluno,
                        idNivelInicial
                );

                conexao.commit();

                return ResultadoCadastro.SUCESSO;

            } catch (SQLException erro) {

                conexao.rollback();

                if (erro.getErrorCode() == 2601
                        || erro.getErrorCode() == 2627) {

                    if (usuarioExiste(
                            conexao,
                            usuario
                    )) {

                        return ResultadoCadastro.USUARIO_EXISTENTE;
                    }

                    if (emailExiste(
                            conexao,
                            email
                    )) {

                        return ResultadoCadastro.EMAIL_EXISTENTE;
                    }
                }

                throw erro;

            } finally {

                conexao.setAutoCommit(
                        true
                );
            }
        }
    }

    private ResultadoCadastro validarDados(
            String nome,
            String usuario,
            String email,
            String senha) {

        if (nome == null
                || nome.length() < 2
                || nome.length() > 150) {

            return ResultadoCadastro.NOME_INVALIDO;
        }

        if (usuario == null
                || usuario.length() < 3
                || usuario.length() > 60) {

            return ResultadoCadastro.USUARIO_INVALIDO;
        }

        if (email == null
                || email.length() > 254
                || !PADRAO_EMAIL
                .matcher(email)
                .matches()) {

            return ResultadoCadastro.EMAIL_INVALIDO;
        }

        if (senha == null
                || senha.length() < 8
                || senha.length() > 72) {

            return ResultadoCadastro.SENHA_INVALIDA;
        }

        return ResultadoCadastro.SUCESSO;
    }

    private void inserirPessoa(
            Connection conexao,
            String idPessoa,
            String nome) throws SQLException {

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

            comando.setString(
                    1,
                    idPessoa
            );

            comando.setString(
                    2,
                    nome
            );

            comando.executeUpdate();
        }
    }

    private void inserirLogin(
            Connection conexao,
            String idPessoa,
            String usuario,
            String email,
            String senha) throws SQLException {

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

        String hashSenha =
                gerarHashSenha(
                        senha
                );

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    idPessoa
            );

            comando.setString(
                    2,
                    usuario
            );

            comando.setString(
                    3,
                    email
            );

            comando.setString(
                    4,
                    hashSenha
            );

            comando.executeUpdate();
        }
    }

    private void inserirAluno(
            Connection conexao,
            String idAluno,
            String idPessoa) throws SQLException {

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

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idPessoa
            );

            comando.executeUpdate();
        }
    }

    private void inserirProgressoInicial(
            Connection conexao,
            String idAluno,
            String idNivel) throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_progresso_aluno (
                    cod_idef_stud,
                    cod_idef_nivl,
                    qtd_xp,
                    qtd_tril_conc,
                    qtd_miss_conc,
                    qtd_simu_real
                )
                VALUES (?, ?, 0, 0, 0, 0)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idNivel
            );

            comando.executeUpdate();
        }
    }

    private boolean usuarioExiste(
            Connection conexao,
            String usuario) throws SQLException {

        String sql =
                """
                SELECT 1
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

                return resultado.next();
            }
        }
    }

    private boolean emailExiste(
            Connection conexao,
            String email) throws SQLException {

        String sql =
                """
                SELECT 1
                FROM dbo.tbl_pessoa_login
                WHERE txt_emai = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    email
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                return resultado.next();
            }
        }
    }

    private String buscarNivelInicial(
            Connection conexao) throws SQLException {

        String sql =
                """
                SELECT TOP 1
                    cod_idef_nivl
                FROM dbo.tbl_nivel
                WHERE
                    num_nivl = 1
                    AND ind_nivl_ativ = 1
                ORDER BY qtd_xp_min
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql);

             ResultSet resultado =
                     comando.executeQuery()) {

            if (resultado.next()) {

                return resultado.getString(
                        "cod_idef_nivl"
                );
            }
        }

        return null;
    }

    private String gerarHashSenha(
            String senha) {

        return BCrypt
                .withDefaults()
                .hashToString(
                        12,
                        senha.toCharArray()
                );
    }

    private String limpar(
            String valor) {

        if (valor == null) {
            return null;
        }

        return valor.trim();
    }
}