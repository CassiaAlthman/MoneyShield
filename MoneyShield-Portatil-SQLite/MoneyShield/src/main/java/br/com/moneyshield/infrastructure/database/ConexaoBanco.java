package br.com.moneyshield.infrastructure.database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexaoBanco {

    private static final String URL_SQL_SERVER =
            "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=db_moneyshield;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

    private static final String USUARIO_SQL_SERVER = "moneyshield_app";
    private static final String SENHA_SQL_SERVER = "MoneyShield_Local_2026!";

    private ConexaoBanco() {
    }

    public static Connection abrir() throws SQLException {
        if (usarSQLite()) {
            return abrirSQLite();
        }

        return DriverManager.getConnection(
                URL_SQL_SERVER,
                USUARIO_SQL_SERVER,
                SENHA_SQL_SERVER
        );
    }

    public static boolean usandoSQLite() {
        return usarSQLite();
    }

    private static boolean usarSQLite() {
        String modo = System.getProperty("moneyshield.database");

        if (modo == null || modo.isBlank()) {
            modo = System.getenv("MONEYSHIELD_DATABASE");
        }

        return modo != null
                && "sqlite".equalsIgnoreCase(modo.trim());
    }

    private static Connection abrirSQLite() throws SQLException {
        String caminhoConfigurado =
                System.getProperty(
                        "moneyshield.sqlite.path",
                        "portable/data/moneyshield.db"
                );

        Path caminho = Paths.get(caminhoConfigurado)
                .toAbsolutePath()
                .normalize();

        if (!Files.exists(caminho)) {
            throw new SQLException(
                    "Banco SQLite portátil não encontrado: " + caminho
            );
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException erro) {
            throw new SQLException(
                    "Driver SQLite não encontrado. Execute CRIAR_ATALHO.bat novamente.",
                    erro
            );
        }

        Connection conexao = DriverManager.getConnection(
                "jdbc:sqlite:" + caminho
        );

        try (Statement comando = conexao.createStatement()) {
            comando.execute("PRAGMA foreign_keys = ON");
            comando.execute("PRAGMA busy_timeout = 5000");
            comando.execute("PRAGMA journal_mode = WAL");
        }

        return SqliteSqlAdapter.adaptar(conexao);
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
        if (usarSQLite()) {
            System.out.println("Testando conexão com o banco SQLite portátil...");

            try (
                    Connection conexao = abrir();
                    Statement comando = conexao.createStatement();
                    ResultSet resultado = comando.executeQuery(
                            "SELECT COUNT(*) AS total FROM tbl_nivel"
                    )
            ) {
                if (resultado.next()) {
                    System.out.println("Conexão SQLite realizada com sucesso!");
                    System.out.println("Níveis cadastrados: " + resultado.getInt("total"));
                }
            } catch (SQLException erro) {
                System.err.println("Falha na conexão SQLite.");
                System.err.println("Mensagem: " + erro.getMessage());
            }

            return;
        }

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
