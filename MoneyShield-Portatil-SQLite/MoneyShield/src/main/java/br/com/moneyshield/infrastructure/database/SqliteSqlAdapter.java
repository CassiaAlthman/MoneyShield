package br.com.moneyshield.infrastructure.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class SqliteSqlAdapter {

    private static final Pattern DATA_YYYYMMDD = Pattern.compile(
            "CONVERT\\s*\\(\\s*INT\\s*,\\s*CONVERT\\s*\\(\\s*CHAR\\s*\\(\\s*8\\s*\\)\\s*,\\s*SYSUTCDATETIME\\s*\\(\\s*\\)\\s*,\\s*112\\s*\\)\\s*\\)",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    private static final Pattern TOP = Pattern.compile(
            "\\bSELECT\\s+TOP\\s+(\\d+)\\s+",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    private static final Pattern OUTPUT = Pattern.compile(
            "\\bOUTPUT\\s+(.+?)\\s+(?=VALUES\\s*\\()",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    private static final Pattern STRING_CONCAT = Pattern.compile(
            "('(?:''|[^'])*')\\s*\\+\\s*(?=(?:[A-Za-z_]\\w*\\.|COALESCE\\s*\\())",
            Pattern.CASE_INSENSITIVE
    );

    private SqliteSqlAdapter() {
    }

    static Connection adaptar(Connection conexao) {
        InvocationHandler handler = (proxy, metodo, args) -> {
            try {
                String nome = metodo.getName();

                if (("prepareStatement".equals(nome)
                        || "prepareCall".equals(nome)
                        || "nativeSQL".equals(nome))
                        && args != null
                        && args.length > 0
                        && args[0] instanceof String) {

                    Object[] ajustados = args.clone();
                    ajustados[0] = adaptarSql((String) args[0]);
                    return metodo.invoke(conexao, ajustados);
                }

                if ("createStatement".equals(nome)) {
                    Statement statement = (Statement) metodo.invoke(conexao, args);
                    return adaptarStatement(statement);
                }

                if ("unwrap".equals(nome)
                        && args != null
                        && args.length == 1
                        && args[0] instanceof Class<?> tipo
                        && tipo.isInstance(conexao)) {
                    return conexao;
                }

                if ("isWrapperFor".equals(nome)
                        && args != null
                        && args.length == 1
                        && args[0] instanceof Class<?> tipo) {
                    return tipo.isInstance(conexao);
                }

                return metodo.invoke(conexao, args);

            } catch (InvocationTargetException erro) {
                throw erro.getCause();
            }
        };

        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                handler
        );
    }

    private static Statement adaptarStatement(Statement statement) {
        InvocationHandler handler = (proxy, metodo, args) -> {
            try {
                if (args != null
                        && args.length > 0
                        && args[0] instanceof String
                        && usaSql(metodo)) {

                    Object[] ajustados = args.clone();
                    ajustados[0] = adaptarSql((String) args[0]);
                    return metodo.invoke(statement, ajustados);
                }

                return metodo.invoke(statement, args);

            } catch (InvocationTargetException erro) {
                throw erro.getCause();
            }
        };

        return (Statement) Proxy.newProxyInstance(
                Statement.class.getClassLoader(),
                new Class<?>[]{Statement.class},
                handler
        );
    }

    private static boolean usaSql(Method metodo) {
        String nome = metodo.getName();
        return "execute".equals(nome)
                || "executeQuery".equals(nome)
                || "executeUpdate".equals(nome)
                || "executeLargeUpdate".equals(nome)
                || "addBatch".equals(nome);
    }

    static String adaptarSql(String original) {
        if (original == null || original.isBlank()) {
            return original;
        }

        String sql = original;

        sql = DATA_YYYYMMDD.matcher(sql)
                .replaceAll("CAST(strftime('%Y%m%d','now') AS INTEGER)");

        sql = sql.replaceAll(
                "(?i)SYSUTCDATETIME\\s*\\(\\s*\\)",
                "CURRENT_TIMESTAMP"
        );

        sql = sql.replaceAll(
                "(?i)\\bdbo\\.",
                ""
        );

        sql = sql.replaceAll(
                "(?i)WITH\\s*\\(\\s*UPDLOCK\\s*,\\s*HOLDLOCK\\s*\\)",
                ""
        );

        sql = sql.replaceAll(
                "(?i)\\bISNULL\\s*\\(",
                "COALESCE("
        );

        sql = sql.replaceAll(
                "(?i)\\bN'",
                "'"
        );

        sql = STRING_CONCAT.matcher(sql)
                .replaceAll("$1 || ");

        String returning = null;
        Matcher output = OUTPUT.matcher(sql);

        if (output.find()) {
            returning = output.group(1)
                    .replaceAll("(?i)\\bINSERTED\\.", "")
                    .trim();

            sql = output.replaceFirst("");
        }

        Integer limite = null;
        Matcher top = TOP.matcher(sql);

        if (top.find()) {
            limite = Integer.parseInt(top.group(1));
            sql = top.replaceFirst("SELECT ");
        }

        sql = sql.trim();

        boolean tinhaPontoVirgula = sql.endsWith(";");
        if (tinhaPontoVirgula) {
            sql = sql.substring(0, sql.length() - 1).trim();
        }

        if (returning != null && !returning.isBlank()) {
            sql += " RETURNING " + returning;
        }

        if (limite != null
                && !sql.toUpperCase().matches("(?s).*\\bLIMIT\\s+\\d+\\s*$")) {
            sql += " LIMIT " + limite;
        }

        if (tinhaPontoVirgula) {
            sql += ";";
        }

        return sql;
    }
}
