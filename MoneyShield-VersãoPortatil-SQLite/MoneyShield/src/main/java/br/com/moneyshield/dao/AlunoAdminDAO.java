package br.com.moneyshield.dao;

import br.com.moneyshield.infrastructure.database.ConexaoBanco;
import br.com.moneyshield.session.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoAdminDAO {

    public ArrayList<DadosAluno> listarAlunos()
            throws SQLException {

        validarAdministrador();

        ArrayList<DadosAluno> alunos =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    a.cod_idef_stud,
                    p.nom_pess,
                    l.nom_usua,
                    l.txt_emai,
                    a.ind_usua_ativ,
                    pr.qtd_xp,
                    pr.qtd_tril_conc,
                    pr.qtd_miss_conc,
                    pr.qtd_simu_real,
                    n.num_nivl,
                    n.nom_nivl,
                    n.qtd_xp_min,
                    n.qtd_xp_max
                FROM dbo.tbl_pessoa_aluno a
                INNER JOIN dbo.tbl_conta_pessoa p
                    ON p.cod_idef_pess = a.cod_idef_pess
                INNER JOIN dbo.tbl_pessoa_login l
                    ON l.cod_idef_pess = p.cod_idef_pess
                INNER JOIN dbo.tbl_progresso_aluno pr
                    ON pr.cod_idef_stud = a.cod_idef_stud
                INNER JOIN dbo.tbl_nivel n
                    ON n.cod_idef_nivl = pr.cod_idef_nivl
                WHERE
                    p.ind_cont_ativ = 1
                    AND l.ind_login_ativ = 1
                ORDER BY
                    p.nom_pess,
                    l.nom_usua
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

            while (resultado.next()) {

                alunos.add(
                        criarDadosAluno(
                                resultado
                        )
                );
            }
        }

        return alunos;
    }

    public DadosAluno buscarAluno(
            String idAluno)
            throws SQLException {

        validarAdministrador();
        validarIdAluno(idAluno);

        String sql =
                """
                SELECT
                    a.cod_idef_stud,
                    p.nom_pess,
                    l.nom_usua,
                    l.txt_emai,
                    a.ind_usua_ativ,
                    pr.qtd_xp,
                    pr.qtd_tril_conc,
                    pr.qtd_miss_conc,
                    pr.qtd_simu_real,
                    n.num_nivl,
                    n.nom_nivl,
                    n.qtd_xp_min,
                    n.qtd_xp_max
                FROM dbo.tbl_pessoa_aluno a
                INNER JOIN dbo.tbl_conta_pessoa p
                    ON p.cod_idef_pess = a.cod_idef_pess
                INNER JOIN dbo.tbl_pessoa_login l
                    ON l.cod_idef_pess = p.cod_idef_pess
                INNER JOIN dbo.tbl_progresso_aluno pr
                    ON pr.cod_idef_stud = a.cod_idef_stud
                INNER JOIN dbo.tbl_nivel n
                    ON n.cod_idef_nivl = pr.cod_idef_nivl
                WHERE a.cod_idef_stud = ?
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

                if (resultado.next()) {

                    return criarDadosAluno(
                            resultado
                    );
                }
            }
        }

        return null;
    }

    public ResultadoAvaliacoes buscarAvaliacoesTrilha1(
            String idAluno)
            throws SQLException {

        validarAdministrador();
        validarIdAluno(idAluno);

        String sql =
                """
                SELECT
                    a.nom_tipo_aval,
                    r.qtd_acer,
                    r.qtd_tota,
                    r.vlr_perc
                FROM dbo.tbl_avaliacao a
                INNER JOIN dbo.tbl_trilha t
                    ON t.cod_idef_tril = a.cod_idef_tril
                LEFT JOIN dbo.tbl_resultado_avaliacao r
                    ON r.cod_idef_aval = a.cod_idef_aval
                    AND r.cod_idef_stud = ?
                WHERE
                    t.num_ord_exib = 1
                    AND a.ind_aval_ativ = 1
                """;

        Integer pre = null;
        Integer pos = null;

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

                    int acertos =
                            resultado.getInt(
                                    "qtd_acer"
                            );

                    if (resultado.wasNull()) {
                        continue;
                    }

                    int total =
                            resultado.getInt(
                                    "qtd_tota"
                            );

                    int percentual =
                            total <= 0
                                    ? 0
                                    : (int) Math.round(
                                    ((double) acertos / total)
                                            * 100
                            );

                    if ("PRE".equals(
                            resultado.getString(
                                    "nom_tipo_aval"
                            )
                    )) {

                        pre = percentual;

                    } else {

                        pos = percentual;
                    }
                }
            }
        }

        return new ResultadoAvaliacoes(
                pre,
                pos
        );
    }

    public ArrayList<String> buscarAtividadesRecentes(
            String idAluno)
            throws SQLException {

        validarAdministrador();
        validarIdAluno(idAluno);

        ArrayList<String> atividades =
                new ArrayList<>();

        String sql =
                """
                SELECT TOP 5 descricao
                FROM (
                    SELECT
                        N'Trilha concluída: ' + t.nom_tril AS descricao,
                        at.dat_hor_conc AS data_evento
                    FROM dbo.tbl_aluno_trilha at
                    INNER JOIN dbo.tbl_trilha t
                        ON t.cod_idef_tril = at.cod_idef_tril
                    WHERE
                        at.cod_idef_stud = ?
                        AND at.nom_stat_tril = 'CONCLUIDA'

                    UNION ALL

                    SELECT
                        N'Missão concluída: ' + m.nom_miss AS descricao,
                        am.dat_hor_conc AS data_evento
                    FROM dbo.tbl_aluno_missao am
                    INNER JOIN dbo.tbl_missao m
                        ON m.cod_idef_miss = am.cod_idef_miss
                    WHERE
                        am.cod_idef_stud = ?
                        AND am.ind_miss_conc = 1

                    UNION ALL

                    SELECT
                        CASE
                            WHEN s.nom_tipo_simu = 'CENARIO'
                                THEN N'Cenário simulado: ' + ISNULL(c.nom_cena, N'Cenário')
                            WHEN s.nom_tipo_simu = 'JUROS'
                                THEN N'Simulação realizada: Juros'
                            WHEN s.nom_tipo_simu = 'FATURA'
                                THEN N'Simulação realizada: Fatura'
                            ELSE N'Simulação realizada'
                        END AS descricao,
                        s.dat_hor_real AS data_evento
                    FROM dbo.tbl_simulacao_aluno s
                    LEFT JOIN dbo.tbl_cenario_financeiro c
                        ON c.cod_idef_cena = s.cod_idef_cena
                    WHERE s.cod_idef_stud = ?
                ) eventos
                WHERE data_evento IS NOT NULL
                ORDER BY data_evento DESC
                """;

        try (
                Connection conexao =
                        ConexaoBanco.abrir();

                PreparedStatement comando =
                        conexao.prepareStatement(
                                sql
                        )
        ) {

            comando.setString(1, idAluno);
            comando.setString(2, idAluno);
            comando.setString(3, idAluno);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {

                    atividades.add(
                            resultado.getString(
                                    "descricao"
                            )
                    );
                }
            }
        }

        return atividades;
    }

    public ArrayList<String> buscarConquistas(
            DadosAluno aluno) {

        ArrayList<String> conquistas =
                new ArrayList<>();

        if (aluno == null) {
            return conquistas;
        }

        if (aluno.getXp() >= 300) {
            conquistas.add("Medalha do Planejador");
        }

        if (aluno.getXp() >= 700) {
            conquistas.add("Troféu do Estrategista");
        }

        if (aluno.getXp() >= 1200) {
            conquistas.add("Insígnia do Mestre Financeiro");
        }

        return conquistas;
    }

    private DadosAluno criarDadosAluno(
            ResultSet resultado)
            throws SQLException {

        int xpMaxBanco =
                resultado.getInt(
                        "qtd_xp_max"
                );

        Integer xpMax =
                resultado.wasNull()
                        ? null
                        : xpMaxBanco;

        return new DadosAluno(
                resultado.getString(
                        "cod_idef_stud"
                ),
                resultado.getString(
                        "nom_pess"
                ),
                resultado.getString(
                        "nom_usua"
                ),
                resultado.getString(
                        "txt_emai"
                ),
                resultado.getBoolean(
                        "ind_usua_ativ"
                ),
                resultado.getInt(
                        "qtd_xp"
                ),
                resultado.getInt(
                        "num_nivl"
                ),
                resultado.getString(
                        "nom_nivl"
                ),
                resultado.getInt(
                        "qtd_xp_min"
                ),
                xpMax,
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

    private void validarAdministrador() {

        if (!SessaoUsuario.possuiUsuarioLogado()
                || !SessaoUsuario.isAdministrador()) {

            throw new IllegalStateException(
                    "Apenas administradores podem consultar os alunos."
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

    public static final class DadosAluno {

        private final String idAluno;
        private final String nome;
        private final String usuario;
        private final String email;
        private final boolean ativo;
        private final int xp;
        private final int nivel;
        private final String nomeNivel;
        private final int xpMinNivel;
        private final Integer xpMaxNivel;
        private final int trilhas;
        private final int missoes;
        private final int simulacoes;

        private DadosAluno(
                String idAluno,
                String nome,
                String usuario,
                String email,
                boolean ativo,
                int xp,
                int nivel,
                String nomeNivel,
                int xpMinNivel,
                Integer xpMaxNivel,
                int trilhas,
                int missoes,
                int simulacoes) {

            this.idAluno = idAluno;
            this.nome = nome;
            this.usuario = usuario;
            this.email = email;
            this.ativo = ativo;
            this.xp = xp;
            this.nivel = nivel;
            this.nomeNivel = nomeNivel;
            this.xpMinNivel = xpMinNivel;
            this.xpMaxNivel = xpMaxNivel;
            this.trilhas = trilhas;
            this.missoes = missoes;
            this.simulacoes = simulacoes;
        }

        public String getIdAluno() {
            return idAluno;
        }

        public String getNome() {
            return nome;
        }

        public String getUsuario() {
            return usuario;
        }

        public String getEmail() {
            return email;
        }

        public boolean isAtivo() {
            return ativo;
        }

        public int getXp() {
            return xp;
        }

        public int getNivel() {
            return nivel;
        }

        public String getNomeNivel() {
            return nomeNivel;
        }

        public int getTrilhas() {
            return trilhas;
        }

        public int getMissoes() {
            return missoes;
        }

        public int getSimulacoes() {
            return simulacoes;
        }

        public int getXpDentroNivel() {

            if (xpMaxNivel == null) {
                return 0;
            }

            return Math.max(
                    0,
                    xp - xpMinNivel
            );
        }

        public int getXpNecessarioNivel() {

            if (xpMaxNivel == null) {
                return 0;
            }

            return xpMaxNivel
                    - xpMinNivel
                    + 1;
        }

        public int getProgressoNivel() {

            if (xpMaxNivel == null) {
                return 100;
            }

            int necessario =
                    getXpNecessarioNivel();

            if (necessario <= 0) {
                return 0;
            }

            int percentual =
                    (int) Math.round(
                            ((double) getXpDentroNivel()
                                    / necessario)
                                    * 100
                    );

            return Math.max(
                    0,
                    Math.min(
                            100,
                            percentual
                    )
            );
        }

        @Override
        public String toString() {
            return nome;
        }
    }

    public static final class ResultadoAvaliacoes {

        private final Integer pre;
        private final Integer pos;

        private ResultadoAvaliacoes(
                Integer pre,
                Integer pos) {

            this.pre = pre;
            this.pos = pos;
        }

        public Integer getPre() {
            return pre;
        }

        public Integer getPos() {
            return pos;
        }

        public Integer getEvolucao() {

            if (pre == null
                    || pos == null) {

                return null;
            }

            return pos - pre;
        }
    }
}
