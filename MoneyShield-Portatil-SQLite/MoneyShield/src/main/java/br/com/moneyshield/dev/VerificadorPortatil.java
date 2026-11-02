package br.com.moneyshield.dev;

import br.com.moneyshield.dao.LoginDAO;
import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.UsuarioAutenticado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class VerificadorPortatil {

    private VerificadorPortatil() {
    }

    public static void main(String[] args) {
        try (
                Connection conexao = ConexaoBanco.abrir();
                PreparedStatement comando = conexao.prepareStatement(
                        "SELECT COUNT(*) FROM tbl_pessoa_login WHERE nom_usua IN ('Aluno 123', 'ADM123')"
                );
                ResultSet resultado = comando.executeQuery()
        ) {
            if (!resultado.next() || resultado.getInt(1) < 2) {
                System.err.println("Usuários de demonstração não foram preparados corretamente.");
                System.exit(2);
            }

            LoginDAO loginDAO = new LoginDAO();

            UsuarioAutenticado aluno = loginDAO.autenticar(
                    "Aluno 123",
                    "Exemplo123"
            );

            if (aluno == null || !aluno.isAluno()) {
                System.err.println("Falha no teste de autenticação do aluno portátil.");
                System.exit(3);
            }

            UsuarioAutenticado administrador = loginDAO.autenticar(
                    "ADM123",
                    "Exemplo123"
            );

            if (administrador == null || !administrador.isAdministrador()) {
                System.err.println("Falha no teste de autenticação do administrador portátil.");
                System.exit(4);
            }

            System.out.println("Banco SQLite portátil e autenticação validados com sucesso.");
            System.exit(0);

        } catch (Exception erro) {
            System.err.println("Falha ao validar o banco portátil:");
            System.err.println(erro.getMessage());
            erro.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
