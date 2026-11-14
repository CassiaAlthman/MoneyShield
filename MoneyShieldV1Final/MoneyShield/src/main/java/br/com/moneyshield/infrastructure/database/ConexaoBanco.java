package br.com.moneyshield.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexaoBanco {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=db_moneyshield;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

    private static final String USUARIO = "moneyshield_app";

    private static final String SENHA = "MoneyShield_Local_2026!";

    private ConexaoBanco() {
    }

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                SENHA
        );
    }

    public static boolean testarConexao() {
        try (Connection conexao = abrir()) {
            return conexao != null && !conexao.isClosed();
        } catch (SQLException erro) {
            System.err.println("Erro ao conectar ao banco MoneyShield:");
            System.err.println("Mensagem: " + erro.getMessage());
            System.err.println("SQL State: " + erro.getSQLState());
            System.err.println("Código: " + erro.getErrorCode());

            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("Testando conexão com db_moneyshield em localhost:1433...");

        try (
                Connection conexao = abrir();
                Statement comando = conexao.createStatement();
                ResultSet resultado = comando.executeQuery(
                        "SELECT DB_NAME() AS banco, SUSER_SNAME() AS usuario"
                )
        ) {

            if (resultado.next()) {
                System.out.println("Conexão realizada com sucesso!");
                System.out.println("Banco: " + resultado.getString("banco"));
                System.out.println("Usuário SQL: " + resultado.getString("usuario"));
            }

        } catch (SQLException erro) {
            System.err.println("Falha na conexão.");
            System.err.println("Mensagem: " + erro.getMessage());
            System.err.println("SQL State: " + erro.getSQLState());
            System.err.println("Código: " + erro.getErrorCode());
        }
    }
}