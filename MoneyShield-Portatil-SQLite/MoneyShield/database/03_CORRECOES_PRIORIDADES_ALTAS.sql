USE db_moneyshield;
GO

IF OBJECT_ID('dbo.tbl_atividade_missao', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.tbl_atividade_missao (
        cod_idef_ativ_miss VARCHAR(36) NOT NULL
            CONSTRAINT DF_tbl_atividade_missao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
        cod_idef_stud VARCHAR(36) NOT NULL,
        cod_idef_miss VARCHAR(36) NOT NULL,
        nom_tipo_ativ VARCHAR(30) NOT NULL,
        dat_ref DATE NULL,
        vlr_prim DECIMAL(18,2) NULL,
        vlr_segu DECIMAL(18,2) NULL,
        vlr_terc DECIMAL(18,2) NULL,
        vlr_quar DECIMAL(18,2) NULL,
        txt_dado NVARCHAR(500) NULL,
        dat_hor_cria DATETIME2(0) NOT NULL
            CONSTRAINT DF_tbl_atividade_missao_cria DEFAULT SYSUTCDATETIME(),
        dat_hor_ulti_atui DATETIME2(0) NOT NULL
            CONSTRAINT DF_tbl_atividade_missao_atui DEFAULT SYSUTCDATETIME(),
        anomesdia INT NOT NULL
            CONSTRAINT DF_tbl_atividade_missao_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
        CONSTRAINT PK_tbl_atividade_missao PRIMARY KEY CLUSTERED (cod_idef_ativ_miss),
        CONSTRAINT FK_tbl_atividade_missao_aluno FOREIGN KEY (cod_idef_stud)
            REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
        CONSTRAINT FK_tbl_atividade_missao_missao FOREIGN KEY (cod_idef_miss)
            REFERENCES dbo.tbl_missao(cod_idef_miss),
        CONSTRAINT CHK_tbl_atividade_missao_valores CHECK (
            (vlr_prim IS NULL OR vlr_prim >= 0)
            AND (vlr_segu IS NULL OR vlr_segu >= 0)
            AND (vlr_terc IS NULL OR vlr_terc >= 0)
            AND (vlr_quar IS NULL OR vlr_quar >= 0)
        )
    );

    ;WITH xp_missao_antigo AS (
        SELECT
            am.cod_idef_stud,
            SUM(CASE WHEN am.ind_xp_conc = 1 THEN m.qtd_xp_reco ELSE 0 END) AS qtd_xp_revogar
        FROM dbo.tbl_aluno_missao am
        INNER JOIN dbo.tbl_missao m
            ON m.cod_idef_miss = am.cod_idef_miss
        GROUP BY am.cod_idef_stud
    )
    UPDATE p
    SET
        p.qtd_xp = CASE
            WHEN p.qtd_xp >= x.qtd_xp_revogar THEN p.qtd_xp - x.qtd_xp_revogar
            ELSE 0
        END,
        p.qtd_miss_conc = 0,
        p.dat_hor_ulti_atui = SYSUTCDATETIME(),
        p.anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112))
    FROM dbo.tbl_progresso_aluno p
    INNER JOIN xp_missao_antigo x
        ON x.cod_idef_stud = p.cod_idef_stud;

    UPDATE dbo.tbl_aluno_missao
    SET
        qtd_prog = 0,
        ind_miss_conc = 0,
        ind_xp_conc = 0,
        dat_hor_conc = NULL,
        dat_hor_ulti_atui = SYSUTCDATETIME(),
        anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112));

    UPDATE p
    SET p.qtd_miss_conc = 0
    FROM dbo.tbl_progresso_aluno p
    WHERE p.qtd_miss_conc <> 0;

    UPDATE p
    SET p.cod_idef_nivl = n.cod_idef_nivl
    FROM dbo.tbl_progresso_aluno p
    CROSS APPLY (
        SELECT TOP 1 niv.cod_idef_nivl
        FROM dbo.tbl_nivel niv
        WHERE
            niv.ind_nivl_ativ = 1
            AND niv.qtd_xp_min <= p.qtd_xp
            AND (
                niv.qtd_xp_max IS NULL
                OR niv.qtd_xp_max >= p.qtd_xp
            )
        ORDER BY niv.num_nivl DESC
    ) n;
END;
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.indexes
    WHERE
        name = 'IX_tbl_atividade_missao_stud_miss'
        AND object_id = OBJECT_ID('dbo.tbl_atividade_missao')
)
BEGIN
    CREATE INDEX IX_tbl_atividade_missao_stud_miss
    ON dbo.tbl_atividade_missao(cod_idef_stud, cod_idef_miss, dat_hor_cria DESC);
END;
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.indexes
    WHERE
        name = 'UX_tbl_atividade_missao_gasto_dia'
        AND object_id = OBJECT_ID('dbo.tbl_atividade_missao')
)
BEGIN
    CREATE UNIQUE INDEX UX_tbl_atividade_missao_gasto_dia
    ON dbo.tbl_atividade_missao(cod_idef_stud, cod_idef_miss, dat_ref)
    WHERE nom_tipo_ativ = 'REGISTRO_GASTOS' AND dat_ref IS NOT NULL;
END;
GO

UPDATE dbo.tbl_missao
SET
    nom_tipo_miss = 'REGISTRO_GASTOS',
    qtd_meta_miss = 7,
    dat_hor_ulti_atui = SYSUTCDATETIME(),
    anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112))
WHERE cod_idef_miss = 'MISSAO-01';

UPDATE dbo.tbl_missao
SET
    nom_tipo_miss = 'PLANEJAMENTO',
    qtd_meta_miss = 1,
    dat_hor_ulti_atui = SYSUTCDATETIME(),
    anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112))
WHERE cod_idef_miss = 'MISSAO-02';

UPDATE dbo.tbl_missao
SET
    nom_tipo_miss = 'CONTEUDOS',
    qtd_meta_miss = 10,
    dat_hor_ulti_atui = SYSUTCDATETIME(),
    anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112))
WHERE cod_idef_miss = 'MISSAO-03';

UPDATE dbo.tbl_missao
SET
    nom_tipo_miss = 'SIMULACAO_FATURA',
    qtd_meta_miss = 1,
    dat_hor_ulti_atui = SYSUTCDATETIME(),
    anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112))
WHERE cod_idef_miss = 'MISSAO-04';

UPDATE dbo.tbl_missao
SET
    nom_tipo_miss = 'RESERVA',
    qtd_meta_miss = 1,
    dat_hor_ulti_atui = SYSUTCDATETIME(),
    anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112))
WHERE cod_idef_miss = 'MISSAO-05';
GO
