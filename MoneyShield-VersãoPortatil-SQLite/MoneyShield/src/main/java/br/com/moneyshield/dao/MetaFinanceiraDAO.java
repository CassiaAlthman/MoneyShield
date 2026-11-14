package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MetaFinanceiraDAO {

    private static final String SQL_LISTAR =
            """
            SELECT
                cod_idef_meta,
                nom_meta,
                des_meta,
                vlr_objt,
                vlr_atua,
                dat_prev_conc,
                ind_meta_conc
            FROM dbo.tbl_meta_financeira
            WHERE cod_idef_stud = ?
            ORDER BY
                ind_meta_conc ASC,
                dat_hor_cria DESC
            """;

    private static final String SQL_BUSCAR =
            """
            SELECT
                cod_idef_meta,
                nom_meta,
                des_meta,
                vlr_objt,
                vlr_atua,
                dat_prev_conc,
                ind_meta_conc
            FROM dbo.tbl_meta_financeira
            WHERE
                cod_idef_meta = ?
                AND cod_idef_stud = ?
            """;

    private static final String SQL_CRIAR =
            """
            INSERT INTO dbo.tbl_meta_financeira (
                cod_idef_stud,
                nom_meta,
                des_meta,
                vlr_objt,
                vlr_atua,
                dat_prev_conc,
                ind_meta_conc
            )
            OUTPUT
                INSERTED.cod_idef_meta,
                INSERTED.nom_meta,
                INSERTED.des_meta,
                INSERTED.vlr_objt,
                INSERTED.vlr_atua,
                INSERTED.dat_prev_conc,
                INSERTED.ind_meta_conc
            VALUES (
                ?,
                ?,
                ?,
                ?,
                ?,
                ?,
                ?
            )
            """;

    private static final String SQL_ADICIONAR_VALOR =
            """
            UPDATE dbo.tbl_meta_financeira
            SET
                vlr_atua = vlr_atua + ?,
                ind_meta_conc =
                    CASE
                        WHEN vlr_atua + ? >= vlr_objt
                            THEN 1
                        ELSE 0
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
                cod_idef_meta = ?
                AND cod_idef_stud = ?
            """;

    private static final String SQL_EXCLUIR =
            """
            DELETE FROM dbo.tbl_meta_financeira
            WHERE
                cod_idef_meta = ?
                AND cod_idef_stud = ?
            """;

    public ArrayList<DadosMeta> listarMetasAtual()
            throws SQLException {

        validarAlunoLogado();

        return listarMetas(
                SessaoUsuario.getIdPerfil()
        );
    }

    public ArrayList<DadosMeta> listarMetas(
            String idAluno)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        ArrayList<DadosMeta> metas =
                new ArrayList<>();

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_LISTAR
                        )
        ) {

            comando.setString(
                    1,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    metas.add(
                            criarDadosMeta(
                                    resultado
                            )
                    );
                }
            }
        }

        return metas;
    }

    public DadosMeta buscarMetaAtual(
            String idMeta)
            throws SQLException {

        validarAlunoLogado();

        return buscarMeta(
                SessaoUsuario.getIdPerfil(),
                idMeta
        );
    }

    public DadosMeta buscarMeta(
            String idAluno,
            String idMeta)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarIdMeta(
                idMeta
        );

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_BUSCAR
                        )
        ) {

            comando.setString(
                    1,
                    idMeta
            );

            comando.setString(
                    2,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return criarDadosMeta(
                            resultado
                    );
                }
            }
        }

        return null;
    }

    public DadosMeta criarMetaAtual(
            String nome,
            double valorObjetivo)
            throws SQLException {

        validarAlunoLogado();

        return criarMeta(
                SessaoUsuario.getIdPerfil(),
                nome,
                null,
                valorObjetivo,
                0,
                null
        );
    }

    public DadosMeta criarMetaAtual(
            String nome,
            String descricao,
            double valorObjetivo,
            double valorAtual,
            LocalDate previsaoConclusao)
            throws SQLException {

        validarAlunoLogado();

        return criarMeta(
                SessaoUsuario.getIdPerfil(),
                nome,
                descricao,
                valorObjetivo,
                valorAtual,
                previsaoConclusao
        );
    }

    public DadosMeta criarMeta(
            String idAluno,
            String nome,
            String descricao,
            double valorObjetivo,
            double valorAtual,
            LocalDate previsaoConclusao)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarNome(
                nome
        );

        validarValores(
                valorObjetivo,
                valorAtual
        );

        boolean concluida =
                valorAtual >= valorObjetivo;

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_CRIAR
                        )
        ) {

            comando.setString(
                    1,
                    idAluno
            );

            comando.setString(
                    2,
                    nome.trim()
            );

            if (descricao == null
                    || descricao.isBlank()) {

                comando.setNull(
                        3,
                        java.sql.Types.NVARCHAR
                );

            } else {

                comando.setString(
                        3,
                        descricao.trim()
                );
            }

            comando.setBigDecimal(
                    4,
                    BigDecimal.valueOf(
                            valorObjetivo
                    )
            );

            comando.setBigDecimal(
                    5,
                    BigDecimal.valueOf(
                            valorAtual
                    )
            );

            if (previsaoConclusao == null) {

                comando.setNull(
                        6,
                        java.sql.Types.DATE
                );

            } else {

                comando.setDate(
                        6,
                        Date.valueOf(
                                previsaoConclusao
                        )
                );
            }

            comando.setBoolean(
                    7,
                    concluida
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return criarDadosMeta(
                            resultado
                    );
                }
            }
        }

        throw new SQLException(
                "Não foi possível criar a meta financeira."
        );
    }

    public DadosMeta adicionarValorAtual(
            String idMeta,
            double valor)
            throws SQLException {

        validarAlunoLogado();

        return adicionarValor(
                SessaoUsuario.getIdPerfil(),
                idMeta,
                valor
        );
    }

    public DadosMeta adicionarValor(
            String idAluno,
            String idMeta,
            double valor)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarIdMeta(
                idMeta
        );

        if (valor <= 0) {

            throw new IllegalArgumentException(
                    "O valor adicionado deve ser maior que zero."
            );
        }

        try (Connection conexao =
                     ConexaoBanco.abrir()) {

            conexao.setAutoCommit(
                    false
            );

            try {

                BigDecimal valorBanco =
                        BigDecimal.valueOf(
                                valor
                        );

                try (PreparedStatement comando =
                             conexao.prepareStatement(
                                     SQL_ADICIONAR_VALOR
                             )) {

                    comando.setBigDecimal(
                            1,
                            valorBanco
                    );

                    comando.setBigDecimal(
                            2,
                            valorBanco
                    );

                    comando.setString(
                            3,
                            idMeta
                    );

                    comando.setString(
                            4,
                            idAluno
                    );

                    int alterados =
                            comando.executeUpdate();

                    if (alterados == 0) {

                        throw new SQLException(
                                "Meta financeira não encontrada."
                        );
                    }
                }

                DadosMeta metaAtualizada =
                        buscarMeta(
                                conexao,
                                idAluno,
                                idMeta
                        );

                conexao.commit();

                return metaAtualizada;

            } catch (SQLException erro) {

                conexao.rollback();

                throw erro;

            } finally {

                conexao.setAutoCommit(
                        true
                );
            }
        }
    }

    public boolean excluirMetaAtual(
            String idMeta)
            throws SQLException {

        validarAlunoLogado();

        return excluirMeta(
                SessaoUsuario.getIdPerfil(),
                idMeta
        );
    }

    public boolean excluirMeta(
            String idAluno,
            String idMeta)
            throws SQLException {

        validarIdAluno(
                idAluno
        );

        validarIdMeta(
                idMeta
        );

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                SQL_EXCLUIR
                        )
        ) {

            comando.setString(
                    1,
                    idMeta
            );

            comando.setString(
                    2,
                    idAluno
            );

            return comando.executeUpdate()
                    > 0;
        }
    }

    private DadosMeta buscarMeta(
            Connection conexao,
            String idAluno,
            String idMeta)
            throws SQLException {

        try (PreparedStatement comando =
                     conexao.prepareStatement(
                             SQL_BUSCAR
                     )) {

            comando.setString(
                    1,
                    idMeta
            );

            comando.setString(
                    2,
                    idAluno
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {

                    return criarDadosMeta(
                            resultado
                    );
                }
            }
        }

        return null;
    }

    private DadosMeta criarDadosMeta(
            ResultSet resultado)
            throws SQLException {

        Date dataPrevisao =
                resultado.getDate(
                        "dat_prev_conc"
                );

        LocalDate previsao =
                dataPrevisao == null
                        ? null
                        : dataPrevisao.toLocalDate();

        return new DadosMeta(
                resultado.getString(
                        "cod_idef_meta"
                ),
                resultado.getString(
                        "nom_meta"
                ),
                resultado.getString(
                        "des_meta"
                ),
                resultado.getBigDecimal(
                        "vlr_objt"
                ).doubleValue(),
                resultado.getBigDecimal(
                        "vlr_atua"
                ).doubleValue(),
                previsao,
                resultado.getBoolean(
                        "ind_meta_conc"
                )
        );
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

    private void validarIdMeta(
            String idMeta) {

        if (idMeta == null
                || idMeta.isBlank()) {

            throw new IllegalArgumentException(
                    "ID da meta inválido."
            );
        }
    }

    private void validarNome(
            String nome) {

        if (nome == null
                || nome.isBlank()) {

            throw new IllegalArgumentException(
                    "Nome da meta inválido."
            );
        }

        if (nome.trim().length() > 180) {

            throw new IllegalArgumentException(
                    "O nome da meta deve ter no máximo 180 caracteres."
            );
        }
    }

    private void validarValores(
            double valorObjetivo,
            double valorAtual) {

        if (valorObjetivo <= 0) {

            throw new IllegalArgumentException(
                    "O valor objetivo deve ser maior que zero."
            );
        }

        if (valorAtual < 0) {

            throw new IllegalArgumentException(
                    "O valor atual não pode ser negativo."
            );
        }
    }

    public static final class DadosMeta {

        private final String id;
        private final String nome;
        private final String descricao;
        private final double valorObjetivo;
        private final double valorAtual;
        private final LocalDate previsaoConclusao;
        private final boolean concluida;

        public DadosMeta(
                String id,
                String nome,
                String descricao,
                double valorObjetivo,
                double valorAtual,
                LocalDate previsaoConclusao,
                boolean concluida) {

            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.valorObjetivo = valorObjetivo;
            this.valorAtual = valorAtual;
            this.previsaoConclusao =
                    previsaoConclusao;
            this.concluida = concluida;
        }

        public String getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public double getValorObjetivo() {
            return valorObjetivo;
        }

        public double getValorAtual() {
            return valorAtual;
        }

        public LocalDate getPrevisaoConclusao() {
            return previsaoConclusao;
        }

        public boolean isConcluida() {
            return concluida;
        }

        public double getValorRestante() {

            return Math.max(
                    valorObjetivo
                            - valorAtual,
                    0
            );
        }

        public int getProgressoPercentual() {

            if (valorObjetivo <= 0) {
                return 0;
            }

            int percentual =
                    (int) (
                            (
                                    valorAtual
                                            / valorObjetivo
                            ) * 100
                    );

            return Math.min(
                    percentual,
                    100
            );
        }
    }
}