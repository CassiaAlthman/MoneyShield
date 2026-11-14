package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class MissaoAlunoDAO {

    public ArrayList<String> buscarMissoesConcluidasAtual()
            throws SQLException {

        validarAlunoLogado();

        return buscarMissoesConcluidas(
                SessaoUsuario.getIdPerfil()
        );
    }

    public ArrayList<String> buscarMissoesConcluidas(
            String idAluno)
            throws SQLException {

        validarIdAluno(idAluno);

        ArrayList<String> missoes =
                new ArrayList<>();

        String sql =
                """
                SELECT m.nom_miss
                FROM dbo.tbl_aluno_missao am
                INNER JOIN dbo.tbl_missao m
                    ON m.cod_idef_miss = am.cod_idef_miss
                WHERE
                    am.cod_idef_stud = ?
                    AND am.ind_miss_conc = 1
                ORDER BY am.dat_hor_conc
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
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    missoes.add(
                            resultado.getString(
                                    "nom_miss"
                            )
                    );
                }
            }
        }

        return missoes;
    }

    public boolean missaoConcluidaAtual(
            String nomeMissao)
            throws SQLException {

        validarAlunoLogado();

        return missaoConcluida(
                SessaoUsuario.getIdPerfil(),
                nomeMissao
        );
    }

    public boolean missaoConcluida(
            String idAluno,
            String nomeMissao)
            throws SQLException {

        validarIdAluno(idAluno);
        validarNomeMissao(nomeMissao);

        String sql =
                """
                SELECT 1
                FROM dbo.tbl_aluno_missao am
                INNER JOIN dbo.tbl_missao m
                    ON m.cod_idef_miss = am.cod_idef_miss
                WHERE
                    am.cod_idef_stud = ?
                    AND m.nom_miss = ?
                    AND am.ind_miss_conc = 1
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
                    idAluno
            );

            comando.setString(
                    2,
                    nomeMissao
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                return resultado.next();
            }
        }
    }

    public ArrayList<DadosProgressoMissao> buscarProgressoMissoesAtual()
            throws SQLException {

        validarAlunoLogado();

        sincronizarMissoesAtual();

        return buscarProgressoMissoes(
                SessaoUsuario.getIdPerfil()
        );
    }

    public ArrayList<DadosProgressoMissao> buscarProgressoMissoes(
            String idAluno)
            throws SQLException {

        validarIdAluno(idAluno);

        ArrayList<DadosProgressoMissao> dados =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    m.nom_miss,
                    m.des_miss,
                    m.nom_tipo_miss,
                    m.qtd_meta_miss,
                    m.qtd_xp_reco,
                    ISNULL(am.qtd_prog, 0) AS qtd_prog,
                    ISNULL(am.ind_miss_conc, 0) AS ind_miss_conc
                FROM dbo.tbl_missao m
                LEFT JOIN dbo.tbl_aluno_missao am
                    ON am.cod_idef_miss = m.cod_idef_miss
                    AND am.cod_idef_stud = ?
                WHERE m.ind_miss_ativ = 1
                ORDER BY
                    m.dat_hor_cria,
                    m.cod_idef_miss
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
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    int metaBanco =
                            resultado.getInt(
                                    "qtd_meta_miss"
                            );

                    Integer meta =
                            resultado.wasNull()
                                    ? null
                                    : metaBanco;

                    dados.add(
                            new DadosProgressoMissao(
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
                                            "qtd_prog"
                                    ),
                                    resultado.getBoolean(
                                            "ind_miss_conc"
                                    ),
                                    resultado.getInt(
                                            "qtd_xp_reco"
                                    )
                            )
                    );
                }
            }
        }

        return dados;
    }

    public DadosProgressoMissao buscarProgressoMissaoAtual(
            String nomeMissao)
            throws SQLException {

        validarAlunoLogado();
        validarNomeMissao(nomeMissao);

        for (DadosProgressoMissao dados
                : buscarProgressoMissoesAtual()) {

            if (nomeMissao.equals(
                    dados.getNome()
            )) {

                return dados;
            }
        }

        return null;
    }

    public ArrayList<String> sincronizarMissoesAtual()
            throws SQLException {

        validarAlunoLogado();

        return sincronizarMissoes(
                SessaoUsuario.getIdPerfil()
        );
    }

    public ArrayList<String> sincronizarMissoes(
            String idAluno)
            throws SQLException {

        validarIdAluno(idAluno);

        ArrayList<String> novasConcluidas =
                new ArrayList<>();

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                DadosProgressoAluno progressoAluno =
                        buscarProgressoAluno(
                                conexao,
                                idAluno
                        );

                if (progressoAluno == null) {

                    throw new SQLException(
                            "Progresso do aluno não encontrado."
                    );
                }

                ArrayList<DadosMissaoBanco> missoes =
                        buscarMissoesAtivas(
                                conexao
                        );

                int xpAtual =
                        progressoAluno.xp;

                for (DadosMissaoBanco missao
                        : missoes) {

                    int progressoCalculado =
                            calcularProgresso(
                                    conexao,
                                    idAluno,
                                    missao
                            );

                    DadosRegistroMissao registro =
                            buscarRegistroMissao(
                                    conexao,
                                    idAluno,
                                    missao.id
                            );

                    int meta =
                            missao.meta == null
                                    ? 1
                                    : missao.meta;

                    boolean atingiuMeta =
                            progressoCalculado >= meta;

                    if (registro == null) {

                        inserirRegistroMissao(
                                conexao,
                                idAluno,
                                missao.id,
                                progressoCalculado,
                                atingiuMeta,
                                atingiuMeta
                        );

                        if (atingiuMeta) {

                            xpAtual +=
                                    missao.xp;

                            novasConcluidas.add(
                                    missao.nome
                            );
                        }

                    } else {

                        boolean completarAgora =
                                !registro.concluida
                                        && atingiuMeta;

                        boolean concederXpAgora =
                                atingiuMeta
                                        && !registro.xpConcedido;

                        boolean revogarXpAgora =
                                !atingiuMeta
                                        && registro.xpConcedido;

                        atualizarRegistroMissao(
                                conexao,
                                idAluno,
                                missao.id,
                                progressoCalculado,
                                atingiuMeta,
                                atingiuMeta,
                                completarAgora
                        );

                        if (concederXpAgora) {

                            xpAtual +=
                                    missao.xp;
                        }

                        if (revogarXpAgora) {

                            xpAtual = Math.max(
                                    0,
                                    xpAtual - missao.xp
                            );
                        }

                        if (completarAgora) {

                            novasConcluidas.add(
                                    missao.nome
                            );
                        }
                    }
                }

                int totalMissoesConcluidas =
                        contarMissoesConcluidas(
                                conexao,
                                idAluno
                        );

                String idNivel =
                        buscarNivelPorXp(
                                conexao,
                                xpAtual
                        );

                if (idNivel == null) {

                    throw new SQLException(
                            "Nível não encontrado para o XP atual."
                    );
                }

                atualizarProgressoAluno(
                        conexao,
                        idAluno,
                        idNivel,
                        xpAtual,
                        progressoAluno.trilhas,
                        totalMissoesConcluidas,
                        progressoAluno.simulacoes
                );

                conexao.commit();

                return novasConcluidas;

            } catch (SQLException
                     | RuntimeException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }
        }
    }

    public boolean registrarGastoDiaAtual(
            LocalDate data,
            BigDecimal totalGasto)
            throws SQLException {

        validarAlunoLogado();

        if (data == null
                || !data.equals(
                LocalDate.now()
        )) {

            throw new IllegalArgumentException(
                    "O registro de gastos só pode ser feito para o dia atual."
            );
        }

        if (totalGasto == null
                || totalGasto.compareTo(
                BigDecimal.ZERO
        ) < 0) {

            throw new IllegalArgumentException(
                    "O total gasto não pode ser negativo."
            );
        }

        String idAluno =
                SessaoUsuario.getIdPerfil();

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                DadosMissaoBanco missao =
                        buscarMissaoPorTipo(
                                conexao,
                                "REGISTRO_GASTOS"
                        );

                if (missao == null) {

                    throw new SQLException(
                            "Missão de registro de gastos não encontrada."
                    );
                }

                String sqlBuscar =
                        """
                        SELECT cod_idef_ativ_miss
                        FROM dbo.tbl_atividade_missao
                        WHERE
                            cod_idef_stud = ?
                            AND cod_idef_miss = ?
                            AND nom_tipo_ativ = 'REGISTRO_GASTOS'
                            AND dat_ref = ?
                        """;

                String idAtividade =
                        null;

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     sqlBuscar
                             )) {

                    comando.setString(
                            1,
                            idAluno
                    );

                    comando.setString(
                            2,
                            missao.id
                    );

                    comando.setDate(
                            3,
                            Date.valueOf(data)
                    );

                    try (ResultSet resultado =
                                 comando.executeQuery()) {

                        if (resultado.next()) {

                            idAtividade =
                                    resultado.getString(
                                            1
                                    );
                        }
                    }
                }

                if (idAtividade == null) {

                    String sqlInserir =
                            """
                            INSERT INTO dbo.tbl_atividade_missao (
                                cod_idef_stud,
                                cod_idef_miss,
                                nom_tipo_ativ,
                                dat_ref,
                                vlr_prim
                            )
                            VALUES (?, ?, 'REGISTRO_GASTOS', ?, ?)
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         sqlInserir
                                 )) {

                        comando.setString(
                                1,
                                idAluno
                        );

                        comando.setString(
                                2,
                                missao.id
                        );

                        comando.setDate(
                                3,
                                Date.valueOf(data)
                        );

                        comando.setBigDecimal(
                                4,
                                totalGasto.setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                )
                        );

                        comando.executeUpdate();
                    }

                } else {

                    String sqlAtualizar =
                            """
                            UPDATE dbo.tbl_atividade_missao
                            SET
                                vlr_prim = ?,
                                dat_hor_ulti_atui = SYSUTCDATETIME(),
                                anomesdia = CONVERT(
                                    INT,
                                    CONVERT(
                                        CHAR(8),
                                        SYSUTCDATETIME(),
                                        112
                                    )
                                )
                            WHERE cod_idef_ativ_miss = ?
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         sqlAtualizar
                                 )) {

                        comando.setBigDecimal(
                                1,
                                totalGasto.setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                )
                        );

                        comando.setString(
                                2,
                                idAtividade
                        );

                        comando.executeUpdate();
                    }
                }

                conexao.commit();

            } catch (SQLException
                     | RuntimeException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }
        }

        sincronizarMissoesAtual();

        return true;
    }

    public boolean registrarPlanejamento503020Atual(
            BigDecimal renda,
            BigDecimal necessidades,
            BigDecimal desejos,
            BigDecimal planejamento)
            throws SQLException {

        validarAlunoLogado();

        validarValorPositivo(
                renda,
                "A renda deve ser maior que zero."
        );

        validarValorNaoNegativo(
                necessidades,
                "O valor de necessidades é inválido."
        );

        validarValorNaoNegativo(
                desejos,
                "O valor de desejos é inválido."
        );

        validarValorNaoNegativo(
                planejamento,
                "O valor de planejamento é inválido."
        );

        BigDecimal soma =
                necessidades
                        .add(desejos)
                        .add(planejamento);

        if (soma.subtract(renda)
                .abs()
                .compareTo(
                        new BigDecimal("0.05")
                ) > 0) {

            throw new IllegalArgumentException(
                    "A soma das três categorias precisa ser igual à renda informada."
            );
        }

        double percentualNecessidades =
                necessidades
                        .divide(
                                renda,
                                6,
                                RoundingMode.HALF_UP
                        )
                        .doubleValue();

        double percentualDesejos =
                desejos
                        .divide(
                                renda,
                                6,
                                RoundingMode.HALF_UP
                        )
                        .doubleValue();

        double percentualPlanejamento =
                planejamento
                        .divide(
                                renda,
                                6,
                                RoundingMode.HALF_UP
                        )
                        .doubleValue();

        if (percentualNecessidades < 0.45
                || percentualNecessidades > 0.55
                || percentualDesejos < 0.25
                || percentualDesejos > 0.35
                || percentualPlanejamento < 0.15
                || percentualPlanejamento > 0.25) {

            throw new IllegalArgumentException(
                    "A divisão precisa ficar próxima da regra 50/30/20: 50% necessidades, 30% desejos e 20% planejamento."
            );
        }

        registrarAtividadeUnicaAtual(
                "PLANEJAMENTO",
                renda,
                necessidades,
                desejos,
                planejamento,
                "Planejamento 50/30/20 validado pelo sistema."
        );

        sincronizarMissoesAtual();

        return true;
    }

    public BigDecimal registrarReservaAtual(
            BigDecimal custoMensal,
            int meses)
            throws SQLException {

        validarAlunoLogado();

        validarValorPositivo(
                custoMensal,
                "O custo mensal deve ser maior que zero."
        );

        if (meses < 3
                || meses > 6) {

            throw new IllegalArgumentException(
                    "A reserva deve considerar entre 3 e 6 meses."
            );
        }

        BigDecimal totalReserva =
                custoMensal
                        .multiply(
                                BigDecimal.valueOf(
                                        meses
                                )
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        registrarAtividadeUnicaAtual(
                "RESERVA",
                custoMensal,
                BigDecimal.valueOf(meses),
                totalReserva,
                null,
                "Reserva de emergência calculada pelo sistema."
        );

        sincronizarMissoesAtual();

        return totalReserva;
    }

    public boolean concluirMissaoAtual(
            String nomeMissao,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarAlunoLogado();

        return concluirMissao(
                SessaoUsuario.getIdPerfil(),
                nomeMissao,
                xpTotal,
                nivel,
                trilhasConcluidas,
                missoesConcluidas,
                simulacoesRealizadas
        );
    }

    public boolean concluirMissao(
            String idAluno,
            String nomeMissao,
            int xpTotal,
            int nivel,
            int trilhasConcluidas,
            int missoesConcluidas,
            int simulacoesRealizadas)
            throws SQLException {

        validarIdAluno(idAluno);
        validarNomeMissao(nomeMissao);

        boolean antes =
                missaoConcluida(
                        idAluno,
                        nomeMissao
                );

        sincronizarMissoes(
                idAluno
        );

        boolean depois =
                missaoConcluida(
                        idAluno,
                        nomeMissao
                );

        return !antes
                && depois;
    }

    private void registrarAtividadeUnicaAtual(
            String tipo,
            BigDecimal valor1,
            BigDecimal valor2,
            BigDecimal valor3,
            BigDecimal valor4,
            String texto)
            throws SQLException {

        String idAluno =
                SessaoUsuario.getIdPerfil();

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(false);

            try {

                DadosMissaoBanco missao =
                        buscarMissaoPorTipo(
                                conexao,
                                tipo
                        );

                if (missao == null) {

                    throw new SQLException(
                            "Missão não encontrada para o tipo: "
                                    + tipo
                    );
                }

                String sqlBuscar =
                        """
                        SELECT TOP 1 cod_idef_ativ_miss
                        FROM dbo.tbl_atividade_missao
                        WHERE
                            cod_idef_stud = ?
                            AND cod_idef_miss = ?
                            AND nom_tipo_ativ = ?
                        ORDER BY dat_hor_cria
                        """;

                String idAtividade =
                        null;

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     sqlBuscar
                             )) {

                    comando.setString(
                            1,
                            idAluno
                    );

                    comando.setString(
                            2,
                            missao.id
                    );

                    comando.setString(
                            3,
                            tipo
                    );

                    try (ResultSet resultado =
                                 comando.executeQuery()) {

                        if (resultado.next()) {

                            idAtividade =
                                    resultado.getString(
                                            1
                                    );
                        }
                    }
                }

                if (idAtividade == null) {

                    String sqlInserir =
                            """
                            INSERT INTO dbo.tbl_atividade_missao (
                                cod_idef_stud,
                                cod_idef_miss,
                                nom_tipo_ativ,
                                vlr_prim,
                                vlr_segu,
                                vlr_terc,
                                vlr_quar,
                                txt_dado
                            )
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         sqlInserir
                                 )) {

                        comando.setString(1, idAluno);
                        comando.setString(2, missao.id);
                        comando.setString(3, tipo);
                        configurarDecimal(comando, 4, valor1);
                        configurarDecimal(comando, 5, valor2);
                        configurarDecimal(comando, 6, valor3);
                        configurarDecimal(comando, 7, valor4);
                        comando.setString(8, texto);
                        comando.executeUpdate();
                    }

                } else {

                    String sqlAtualizar =
                            """
                            UPDATE dbo.tbl_atividade_missao
                            SET
                                vlr_prim = ?,
                                vlr_segu = ?,
                                vlr_terc = ?,
                                vlr_quar = ?,
                                txt_dado = ?,
                                dat_hor_ulti_atui = SYSUTCDATETIME(),
                                anomesdia = CONVERT(
                                    INT,
                                    CONVERT(
                                        CHAR(8),
                                        SYSUTCDATETIME(),
                                        112
                                    )
                                )
                            WHERE cod_idef_ativ_miss = ?
                            """;

                    try (PreparedStatement comando =
                                 conexao.prepareStatement(
                                         sqlAtualizar
                                 )) {

                        configurarDecimal(comando, 1, valor1);
                        configurarDecimal(comando, 2, valor2);
                        configurarDecimal(comando, 3, valor3);
                        configurarDecimal(comando, 4, valor4);
                        comando.setString(5, texto);
                        comando.setString(6, idAtividade);
                        comando.executeUpdate();
                    }
                }

                conexao.commit();

            } catch (SQLException
                     | RuntimeException erro) {

                conexao.rollback();
                throw erro;

            } finally {

                conexao.setAutoCommit(true);
            }
        }
    }

    private ArrayList<DadosMissaoBanco> buscarMissoesAtivas(
            Connection conexao)
            throws SQLException {

        ArrayList<DadosMissaoBanco> missoes =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    cod_idef_miss,
                    nom_miss,
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
                PreparedStatement comando =
                        conexao.prepareStatement(
                                sql
                        );

                ResultSet resultado =
                        comando.executeQuery()
        ) {

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
                        new DadosMissaoBanco(
                                resultado.getString(
                                        "cod_idef_miss"
                                ),
                                resultado.getString(
                                        "nom_miss"
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
        }

        return missoes;
    }

    private DadosMissaoBanco buscarMissaoPorTipo(
            Connection conexao,
            String tipo)
            throws SQLException {

        String sql =
                """
                SELECT TOP 1
                    cod_idef_miss,
                    nom_miss,
                    nom_tipo_miss,
                    qtd_meta_miss,
                    qtd_xp_reco
                FROM dbo.tbl_missao
                WHERE
                    ind_miss_ativ = 1
                    AND nom_tipo_miss = ?
                ORDER BY
                    dat_hor_cria,
                    cod_idef_miss
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(
                    1,
                    tipo
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                int metaBanco =
                        resultado.getInt(
                                "qtd_meta_miss"
                        );

                Integer meta =
                        resultado.wasNull()
                                ? null
                                : metaBanco;

                return new DadosMissaoBanco(
                        resultado.getString(
                                "cod_idef_miss"
                        ),
                        resultado.getString(
                                "nom_miss"
                        ),
                        resultado.getString(
                                "nom_tipo_miss"
                        ),
                        meta,
                        resultado.getInt(
                                "qtd_xp_reco"
                        )
                );
            }
        }
    }

    private int calcularProgresso(
            Connection conexao,
            String idAluno,
            DadosMissaoBanco missao)
            throws SQLException {

        if ("REGISTRO_GASTOS".equals(
                missao.tipo
        )) {

            return contarAtividades(
                    conexao,
                    idAluno,
                    missao.id,
                    "REGISTRO_GASTOS"
            );
        }

        if ("PLANEJAMENTO".equals(
                missao.tipo
        )) {

            return Math.min(
                    contarAtividades(
                            conexao,
                            idAluno,
                            missao.id,
                            "PLANEJAMENTO"
                    ),
                    1
            );
        }

        if ("CONTEUDOS".equals(
                missao.tipo
        )) {

            String sql =
                    """
                    SELECT COUNT(*)
                    FROM dbo.tbl_aluno_trilha
                    WHERE
                        cod_idef_stud = ?
                        AND nom_stat_tril = 'CONCLUIDA'
                    """;

            try (PreparedStatement comando =
                         conexao.prepareStatement(
                                 sql
                         )) {

                comando.setString(
                        1,
                        idAluno
                );

                try (ResultSet resultado =
                             comando.executeQuery()) {

                    resultado.next();
                    return resultado.getInt(1);
                }
            }
        }

        if ("SIMULACAO_FATURA".equals(
                missao.tipo
        )) {

            String sql =
                    """
                    SELECT COUNT(*)
                    FROM dbo.tbl_simulacao_aluno
                    WHERE
                        cod_idef_stud = ?
                        AND nom_tipo_simu = 'FATURA'
                    """;

            try (PreparedStatement comando =
                         conexao.prepareStatement(
                                 sql
                         )) {

                comando.setString(
                        1,
                        idAluno
                );

                try (ResultSet resultado =
                             comando.executeQuery()) {

                    resultado.next();
                    return resultado.getInt(1);
                }
            }
        }

        if ("RESERVA".equals(
                missao.tipo
        )) {

            return Math.min(
                    contarAtividades(
                            conexao,
                            idAluno,
                            missao.id,
                            "RESERVA"
                    ),
                    1
            );
        }

        return 0;
    }

    private int contarAtividades(
            Connection conexao,
            String idAluno,
            String idMissao,
            String tipo)
            throws SQLException {

        String sql =
                """
                SELECT COUNT(*)
                FROM dbo.tbl_atividade_missao
                WHERE
                    cod_idef_stud = ?
                    AND cod_idef_miss = ?
                    AND nom_tipo_ativ = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    idMissao
            );

            comando.setString(
                    3,
                    tipo
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                resultado.next();
                return resultado.getInt(1);
            }
        }
    }

    private DadosRegistroMissao buscarRegistroMissao(
            Connection conexao,
            String idAluno,
            String idMissao)
            throws SQLException {

        String sql =
                """
                SELECT
                    qtd_prog,
                    ind_miss_conc,
                    ind_xp_conc
                FROM dbo.tbl_aluno_missao
                WITH (UPDLOCK, HOLDLOCK)
                WHERE
                    cod_idef_stud = ?
                    AND cod_idef_miss = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(1, idAluno);
            comando.setString(2, idMissao);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                return new DadosRegistroMissao(
                        resultado.getInt(
                                "qtd_prog"
                        ),
                        resultado.getBoolean(
                                "ind_miss_conc"
                        ),
                        resultado.getBoolean(
                                "ind_xp_conc"
                        )
                );
            }
        }
    }

    private void inserirRegistroMissao(
            Connection conexao,
            String idAluno,
            String idMissao,
            int progresso,
            boolean concluida,
            boolean xpConcedido)
            throws SQLException {

        String sql =
                """
                INSERT INTO dbo.tbl_aluno_missao (
                    cod_idef_stud,
                    cod_idef_miss,
                    qtd_prog,
                    ind_miss_conc,
                    ind_xp_conc,
                    dat_hor_conc
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(1, idAluno);
            comando.setString(2, idMissao);
            comando.setInt(3, progresso);
            comando.setBoolean(4, concluida);
            comando.setBoolean(5, xpConcedido);

            if (concluida) {

                comando.setTimestamp(
                        6,
                        java.sql.Timestamp.valueOf(
                                java.time.LocalDateTime.now()
                        )
                );

            } else {

                comando.setNull(
                        6,
                        Types.TIMESTAMP
                );
            }

            comando.executeUpdate();
        }
    }

    private void atualizarRegistroMissao(
            Connection conexao,
            String idAluno,
            String idMissao,
            int progresso,
            boolean concluida,
            boolean xpConcedido,
            boolean concluirAgora)
            throws SQLException {

        String sql =
                """
                UPDATE dbo.tbl_aluno_missao
                SET
                    qtd_prog = ?,
                    ind_miss_conc = ?,
                    ind_xp_conc = ?,
                    dat_hor_conc = CASE
                        WHEN ? = 1 THEN SYSUTCDATETIME()
                        WHEN ? = 0 THEN NULL
                        ELSE dat_hor_conc
                    END,
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
                    cod_idef_stud = ?
                    AND cod_idef_miss = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setInt(1, progresso);
            comando.setBoolean(2, concluida);
            comando.setBoolean(3, xpConcedido);
            comando.setBoolean(4, concluirAgora);
            comando.setBoolean(5, concluida);
            comando.setString(6, idAluno);
            comando.setString(7, idMissao);
            comando.executeUpdate();
        }
    }

    private DadosProgressoAluno buscarProgressoAluno(
            Connection conexao,
            String idAluno)
            throws SQLException {

        String sql =
                """
                SELECT
                    qtd_xp,
                    qtd_tril_conc,
                    qtd_miss_conc,
                    qtd_simu_real
                FROM dbo.tbl_progresso_aluno
                WITH (UPDLOCK, HOLDLOCK)
                WHERE cod_idef_stud = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(
                    1,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                return new DadosProgressoAluno(
                        resultado.getInt(
                                "qtd_xp"
                        ),
                        resultado.getInt(
                                "qtd_tril_conc"
                        ),
                        resultado.getInt(
                                "qtd_miss_conc"
                        ),
                        resultado.getInt(
                                "qtd_simu_real"
                        )
                );
            }
        }
    }

    private int contarMissoesConcluidas(
            Connection conexao,
            String idAluno)
            throws SQLException {

        String sql =
                """
                SELECT COUNT(*)
                FROM dbo.tbl_aluno_missao
                WHERE
                    cod_idef_stud = ?
                    AND ind_miss_conc = 1
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(1, idAluno);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                resultado.next();
                return resultado.getInt(1);
            }
        }
    }

    private String buscarNivelPorXp(
            Connection conexao,
            int xp)
            throws SQLException {

        String sql =
                """
                SELECT TOP 1 cod_idef_nivl
                FROM dbo.tbl_nivel
                WHERE
                    ind_nivl_ativ = 1
                    AND qtd_xp_min <= ?
                    AND (
                        qtd_xp_max IS NULL
                        OR qtd_xp_max >= ?
                    )
                ORDER BY num_nivl DESC
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setInt(1, xp);
            comando.setInt(2, xp);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString(1);
                }
            }
        }

        return null;
    }

    private void atualizarProgressoAluno(
            Connection conexao,
            String idAluno,
            String idNivel,
            int xp,
            int trilhas,
            int missoes,
            int simulacoes)
            throws SQLException {

        String sql =
                """
                UPDATE dbo.tbl_progresso_aluno
                SET
                    cod_idef_nivl = ?,
                    qtd_xp = ?,
                    qtd_tril_conc = ?,
                    qtd_miss_conc = ?,
                    qtd_simu_real = ?,
                    dat_hor_ulti_atui = SYSUTCDATETIME(),
                    anomesdia = CONVERT(
                        INT,
                        CONVERT(
                            CHAR(8),
                            SYSUTCDATETIME(),
                            112
                        )
                    )
                WHERE cod_idef_stud = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             sql
                     )) {

            comando.setString(1, idNivel);
            comando.setInt(2, xp);
            comando.setInt(3, trilhas);
            comando.setInt(4, missoes);
            comando.setInt(5, simulacoes);
            comando.setString(6, idAluno);

            if (comando.executeUpdate() == 0) {

                throw new SQLException(
                        "Progresso do aluno não encontrado."
                );
            }
        }
    }

    private void configurarDecimal(
            PreparedStatement comando,
            int indice,
            BigDecimal valor)
            throws SQLException {

        if (valor == null) {

            comando.setNull(
                    indice,
                    Types.DECIMAL
            );

        } else {

            comando.setBigDecimal(
                    indice,
                    valor.setScale(
                            2,
                            RoundingMode.HALF_UP
                    )
            );
        }
    }

    private void validarValorPositivo(
            BigDecimal valor,
            String mensagem) {

        if (valor == null
                || valor.compareTo(
                BigDecimal.ZERO
        ) <= 0) {

            throw new IllegalArgumentException(
                    mensagem
            );
        }
    }

    private void validarValorNaoNegativo(
            BigDecimal valor,
            String mensagem) {

        if (valor == null
                || valor.compareTo(
                BigDecimal.ZERO
        ) < 0) {

            throw new IllegalArgumentException(
                    mensagem
            );
        }
    }

    private void validarAlunoLogado() {

        if (!SessaoUsuario.possuiUsuarioLogado()
                || !SessaoUsuario.isAluno()) {

            throw new IllegalStateException(
                    "Nenhum aluno está autenticado."
            );
        }
    }

    private void validarIdAluno(
            String idAluno) {

        if (idAluno == null
                || idAluno.isBlank()) {

            throw new IllegalArgumentException(
                    "ID do aluno inválido."
            );
        }
    }

    private void validarNomeMissao(
            String nomeMissao) {

        if (nomeMissao == null
                || nomeMissao.isBlank()) {

            throw new IllegalArgumentException(
                    "Nome da missão inválido."
            );
        }
    }

    public static final class DadosProgressoMissao {

        private final String nome;
        private final String descricao;
        private final String tipo;
        private final Integer meta;
        private final int progresso;
        private final boolean concluida;
        private final int xp;

        private DadosProgressoMissao(
                String nome,
                String descricao,
                String tipo,
                Integer meta,
                int progresso,
                boolean concluida,
                int xp) {

            this.nome = nome;
            this.descricao = descricao;
            this.tipo = tipo;
            this.meta = meta;
            this.progresso = progresso;
            this.concluida = concluida;
            this.xp = xp;
        }

        public String getNome() {
            return nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getTipo() {
            return tipo;
        }

        public Integer getMeta() {
            return meta;
        }

        public int getProgresso() {
            return progresso;
        }

        public boolean isConcluida() {
            return concluida;
        }

        public int getXp() {
            return xp;
        }
    }

    private static final class DadosMissaoBanco {

        private final String id;
        private final String nome;
        private final String tipo;
        private final Integer meta;
        private final int xp;

        private DadosMissaoBanco(
                String id,
                String nome,
                String tipo,
                Integer meta,
                int xp) {

            this.id = id;
            this.nome = nome;
            this.tipo = tipo;
            this.meta = meta;
            this.xp = xp;
        }
    }

    private static final class DadosRegistroMissao {

        private final int progresso;
        private final boolean concluida;
        private final boolean xpConcedido;

        private DadosRegistroMissao(
                int progresso,
                boolean concluida,
                boolean xpConcedido) {

            this.progresso = progresso;
            this.concluida = concluida;
            this.xpConcedido = xpConcedido;
        }
    }

    private static final class DadosProgressoAluno {

        private final int xp;
        private final int trilhas;
        private final int missoes;
        private final int simulacoes;

        private DadosProgressoAluno(
                int xp,
                int trilhas,
                int missoes,
                int simulacoes) {

            this.xp = xp;
            this.trilhas = trilhas;
            this.missoes = missoes;
            this.simulacoes = simulacoes;
        }
    }
}
