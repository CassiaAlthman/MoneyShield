IF DB_ID(N'db_moneyshield') IS NULL
BEGIN
    CREATE DATABASE db_moneyshield;
END
GO

USE db_moneyshield;
GO

DROP TABLE IF EXISTS dbo.tbl_aluno_plano_assinatura;
DROP TABLE IF EXISTS dbo.tbl_plano_assinatura;
DROP TABLE IF EXISTS dbo.tbl_notificacao_aluno;
DROP TABLE IF EXISTS dbo.tbl_historico_atividade;
DROP TABLE IF EXISTS dbo.tbl_meta_financeira;
DROP TABLE IF EXISTS dbo.tbl_simulacao_aluno;
DROP TABLE IF EXISTS dbo.tbl_opcao_cenario;
DROP TABLE IF EXISTS dbo.tbl_cenario_financeiro;
DROP TABLE IF EXISTS dbo.tbl_aluno_missao;
DROP TABLE IF EXISTS dbo.tbl_missao;
DROP TABLE IF EXISTS dbo.tbl_resposta_avaliacao;
DROP TABLE IF EXISTS dbo.tbl_resultado_avaliacao;
DROP TABLE IF EXISTS dbo.tbl_alternativa_avaliacao;
DROP TABLE IF EXISTS dbo.tbl_questao_avaliacao;
DROP TABLE IF EXISTS dbo.tbl_avaliacao;
DROP TABLE IF EXISTS dbo.tbl_aluno_trilha;
DROP TABLE IF EXISTS dbo.tbl_trilha;
DROP TABLE IF EXISTS dbo.tbl_aluno_badge;
DROP TABLE IF EXISTS dbo.tbl_progresso_aluno;
DROP TABLE IF EXISTS dbo.tbl_badge;
DROP TABLE IF EXISTS dbo.tbl_nivel;
DROP TABLE IF EXISTS dbo.tbl_pessoa_admin;
DROP TABLE IF EXISTS dbo.tbl_pessoa_aluno;
DROP TABLE IF EXISTS dbo.tbl_pessoa_login;
DROP TABLE IF EXISTS dbo.tbl_conta_pessoa;
GO

CREATE TABLE dbo.tbl_conta_pessoa (
    cod_idef_pess VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_conta_pessoa_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    nom_pess NVARCHAR(150) NOT NULL,
    num_cfcj VARCHAR(14) NULL,
    dat_nasc DATE NULL,
    ind_genr CHAR(2) NULL,
    nom_carg NVARCHAR(100) NULL,
    ind_cont_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_conta_pessoa_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_conta_pessoa_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_conta_pessoa_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_conta_pessoa_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_conta_pessoa PRIMARY KEY CLUSTERED (cod_idef_pess),
    CONSTRAINT CHK_tbl_conta_pessoa_genr CHECK (
        ind_genr IS NULL OR ind_genr IN ('MC','FC','HT','MT','NB','NR','OU')
    )
);
GO

CREATE UNIQUE INDEX UX_tbl_conta_pessoa_cfcj
ON dbo.tbl_conta_pessoa(num_cfcj)
WHERE num_cfcj IS NOT NULL;
GO

CREATE TABLE dbo.tbl_pessoa_login (
    cod_idef_pess VARCHAR(36) NOT NULL,
    nom_usua NVARCHAR(60) NOT NULL,
    txt_emai NVARCHAR(254) NOT NULL,
    txt_pass VARCHAR(255) NOT NULL,
    nom_algo_pass VARCHAR(20) NOT NULL
        CONSTRAINT DF_tbl_pessoa_login_algo DEFAULT 'BCRYPT',
    ind_login_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_pessoa_login_ativ DEFAULT 1,
    dat_hor_ulti_login DATETIME2(0) NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_pessoa_login_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_pessoa_login_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_pessoa_login_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_pessoa_login PRIMARY KEY CLUSTERED (cod_idef_pess),
    CONSTRAINT UQ_tbl_pessoa_login_usuario UNIQUE (nom_usua),
    CONSTRAINT UQ_tbl_pessoa_login_email UNIQUE (txt_emai),
    CONSTRAINT FK_tbl_pessoa_login_pessoa FOREIGN KEY (cod_idef_pess)
        REFERENCES dbo.tbl_conta_pessoa(cod_idef_pess)
);
GO

CREATE TABLE dbo.tbl_pessoa_aluno (
    cod_idef_stud VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_pessoa_aluno_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_pess VARCHAR(36) NOT NULL,
    ind_usua_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_pessoa_aluno_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_pessoa_aluno_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_pessoa_aluno_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_pessoa_aluno_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_pessoa_aluno PRIMARY KEY CLUSTERED (cod_idef_stud),
    CONSTRAINT UQ_tbl_pessoa_aluno_pessoa UNIQUE (cod_idef_pess),
    CONSTRAINT FK_tbl_pessoa_aluno_pessoa FOREIGN KEY (cod_idef_pess)
        REFERENCES dbo.tbl_conta_pessoa(cod_idef_pess)
);
GO

CREATE TABLE dbo.tbl_pessoa_admin (
    cod_idef_admi VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_pessoa_admin_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_pess VARCHAR(36) NOT NULL,
    nom_func NVARCHAR(100) NOT NULL,
    ind_admi_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_pessoa_admin_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_pessoa_admin_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_pessoa_admin_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_pessoa_admin_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_pessoa_admin PRIMARY KEY CLUSTERED (cod_idef_admi),
    CONSTRAINT UQ_tbl_pessoa_admin_pessoa UNIQUE (cod_idef_pess),
    CONSTRAINT FK_tbl_pessoa_admin_pessoa FOREIGN KEY (cod_idef_pess)
        REFERENCES dbo.tbl_conta_pessoa(cod_idef_pess)
);
GO

CREATE TABLE dbo.tbl_nivel (
    cod_idef_nivl VARCHAR(36) NOT NULL,
    num_nivl INT NOT NULL,
    nom_nivl NVARCHAR(80) NOT NULL,
    qtd_xp_min INT NOT NULL,
    qtd_xp_max INT NULL,
    ind_nivl_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_nivel_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_nivel_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_nivel_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_nivel_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_nivel PRIMARY KEY CLUSTERED (cod_idef_nivl),
    CONSTRAINT UQ_tbl_nivel_num UNIQUE (num_nivl),
    CONSTRAINT UQ_tbl_nivel_nome UNIQUE (nom_nivl),
    CONSTRAINT CHK_tbl_nivel_xp CHECK (
        qtd_xp_min >= 0 AND (qtd_xp_max IS NULL OR qtd_xp_max >= qtd_xp_min)
    )
);
GO

CREATE TABLE dbo.tbl_badge (
    cod_idef_badg VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_badge_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    nom_badg NVARCHAR(120) NOT NULL,
    des_badg NVARCHAR(500) NOT NULL,
    txt_url_badg NVARCHAR(500) NULL,
    qtd_pont_badg INT NOT NULL
        CONSTRAINT DF_tbl_badge_pont DEFAULT 0,
    ind_badg_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_badge_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_badge_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_badge_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_badge_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_badge PRIMARY KEY CLUSTERED (cod_idef_badg),
    CONSTRAINT UQ_tbl_badge_nome UNIQUE (nom_badg),
    CONSTRAINT CHK_tbl_badge_pont CHECK (qtd_pont_badg >= 0)
);
GO

CREATE TABLE dbo.tbl_progresso_aluno (
    cod_idef_stud VARCHAR(36) NOT NULL,
    cod_idef_nivl VARCHAR(36) NOT NULL,
    qtd_xp INT NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_xp DEFAULT 0,
    qtd_tril_conc INT NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_tril DEFAULT 0,
    qtd_miss_conc INT NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_miss DEFAULT 0,
    qtd_simu_real INT NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_simu DEFAULT 0,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_progresso_aluno_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_progresso_aluno PRIMARY KEY CLUSTERED (cod_idef_stud),
    CONSTRAINT FK_tbl_progresso_aluno_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_progresso_aluno_nivel FOREIGN KEY (cod_idef_nivl)
        REFERENCES dbo.tbl_nivel(cod_idef_nivl),
    CONSTRAINT CHK_tbl_progresso_aluno_valores CHECK (
        qtd_xp >= 0 AND qtd_tril_conc >= 0 AND qtd_miss_conc >= 0 AND qtd_simu_real >= 0
    )
);
GO

CREATE TABLE dbo.tbl_aluno_badge (
    cod_idef_emss_badg VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_aluno_badge_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    cod_idef_badg VARCHAR(36) NOT NULL,
    dat_hor_aqui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_badge_aqui DEFAULT SYSUTCDATETIME(),
    ind_visu_badg BIT NOT NULL
        CONSTRAINT DF_tbl_aluno_badge_visu DEFAULT 0,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_badge_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_badge_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_aluno_badge_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_aluno_badge PRIMARY KEY CLUSTERED (cod_idef_emss_badg),
    CONSTRAINT UQ_tbl_aluno_badge UNIQUE (cod_idef_stud, cod_idef_badg),
    CONSTRAINT FK_tbl_aluno_badge_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_badge_badge FOREIGN KEY (cod_idef_badg)
        REFERENCES dbo.tbl_badge(cod_idef_badg)
);
GO

CREATE TABLE dbo.tbl_trilha (
    cod_idef_tril VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_trilha_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    nom_tril NVARCHAR(220) NOT NULL,
    des_tril NVARCHAR(1000) NULL,
    txt_cont_tril NVARCHAR(MAX) NULL,
    num_ord_exib INT NOT NULL,
    qtd_xp_reco INT NOT NULL
        CONSTRAINT DF_tbl_trilha_xp DEFAULT 150,
    ind_tril_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_trilha_ativ DEFAULT 1,
    cod_idef_admi_cria VARCHAR(36) NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_trilha_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_trilha_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_trilha_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_trilha PRIMARY KEY CLUSTERED (cod_idef_tril),
    CONSTRAINT UQ_tbl_trilha_nome UNIQUE (nom_tril),
    CONSTRAINT UQ_tbl_trilha_ordem UNIQUE (num_ord_exib),
    CONSTRAINT FK_tbl_trilha_admin FOREIGN KEY (cod_idef_admi_cria)
        REFERENCES dbo.tbl_pessoa_admin(cod_idef_admi),
    CONSTRAINT CHK_tbl_trilha_valores CHECK (num_ord_exib > 0 AND qtd_xp_reco >= 0)
);
GO

CREATE TABLE dbo.tbl_aluno_trilha (
    cod_idef_alun_tril VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_aluno_trilha_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    cod_idef_tril VARCHAR(36) NOT NULL,
    nom_stat_tril VARCHAR(20) NOT NULL
        CONSTRAINT DF_tbl_aluno_trilha_stat DEFAULT 'DISPONIVEL',
    ind_xp_conc BIT NOT NULL
        CONSTRAINT DF_tbl_aluno_trilha_xp DEFAULT 0,
    dat_hor_inic DATETIME2(0) NULL,
    dat_hor_conc DATETIME2(0) NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_trilha_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_trilha_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_aluno_trilha_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_aluno_trilha PRIMARY KEY CLUSTERED (cod_idef_alun_tril),
    CONSTRAINT UQ_tbl_aluno_trilha UNIQUE (cod_idef_stud, cod_idef_tril),
    CONSTRAINT FK_tbl_aluno_trilha_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_trilha_trilha FOREIGN KEY (cod_idef_tril)
        REFERENCES dbo.tbl_trilha(cod_idef_tril),
    CONSTRAINT CHK_tbl_aluno_trilha_status CHECK (
        nom_stat_tril IN ('DISPONIVEL','PRE_REALIZADO','EM_ANDAMENTO','POS_REALIZADO','CONCLUIDA')
    )
);
GO

CREATE TABLE dbo.tbl_avaliacao (
    cod_idef_aval VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_avaliacao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_tril VARCHAR(36) NOT NULL,
    nom_tipo_aval VARCHAR(10) NOT NULL,
    nom_aval NVARCHAR(150) NOT NULL,
    ind_aval_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_avaliacao_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_avaliacao_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_avaliacao_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_avaliacao_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_avaliacao PRIMARY KEY CLUSTERED (cod_idef_aval),
    CONSTRAINT UQ_tbl_avaliacao_tipo UNIQUE (cod_idef_tril, nom_tipo_aval),
    CONSTRAINT FK_tbl_avaliacao_trilha FOREIGN KEY (cod_idef_tril)
        REFERENCES dbo.tbl_trilha(cod_idef_tril),
    CONSTRAINT CHK_tbl_avaliacao_tipo CHECK (nom_tipo_aval IN ('PRE','POS'))
);
GO

CREATE TABLE dbo.tbl_questao_avaliacao (
    cod_idef_ques VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_questao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_aval VARCHAR(36) NOT NULL,
    txt_enun NVARCHAR(1000) NOT NULL,
    num_ord_ques INT NOT NULL,
    ind_ques_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_questao_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_questao_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_questao_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_questao_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_questao_avaliacao PRIMARY KEY CLUSTERED (cod_idef_ques),
    CONSTRAINT UQ_tbl_questao_ordem UNIQUE (cod_idef_aval, num_ord_ques),
    CONSTRAINT FK_tbl_questao_avaliacao_avaliacao FOREIGN KEY (cod_idef_aval)
        REFERENCES dbo.tbl_avaliacao(cod_idef_aval),
    CONSTRAINT CHK_tbl_questao_ordem CHECK (num_ord_ques > 0)
);
GO

CREATE TABLE dbo.tbl_alternativa_avaliacao (
    cod_idef_alte VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_alternativa_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_ques VARCHAR(36) NOT NULL,
    txt_alte NVARCHAR(1000) NOT NULL,
    num_ord_alte INT NOT NULL,
    ind_alte_corr BIT NOT NULL
        CONSTRAINT DF_tbl_alternativa_corr DEFAULT 0,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_alternativa_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_alternativa_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_alternativa_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_alternativa_avaliacao PRIMARY KEY CLUSTERED (cod_idef_alte),
    CONSTRAINT UQ_tbl_alternativa_ordem UNIQUE (cod_idef_ques, num_ord_alte),
    CONSTRAINT FK_tbl_alternativa_avaliacao_questao FOREIGN KEY (cod_idef_ques)
        REFERENCES dbo.tbl_questao_avaliacao(cod_idef_ques),
    CONSTRAINT CHK_tbl_alternativa_ordem CHECK (num_ord_alte > 0)
);
GO

CREATE TABLE dbo.tbl_resultado_avaliacao (
    cod_idef_resu_aval VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_resultado_avaliacao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    cod_idef_aval VARCHAR(36) NOT NULL,
    qtd_acer INT NOT NULL,
    qtd_tota INT NOT NULL,
    vlr_perc DECIMAL(5,2) NOT NULL,
    dat_hor_real DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_resultado_avaliacao_real DEFAULT SYSUTCDATETIME(),
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_resultado_avaliacao_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_resultado_avaliacao_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_resultado_avaliacao_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_resultado_avaliacao PRIMARY KEY CLUSTERED (cod_idef_resu_aval),
    CONSTRAINT UQ_tbl_resultado_avaliacao UNIQUE (cod_idef_stud, cod_idef_aval),
    CONSTRAINT FK_tbl_resultado_avaliacao_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_resultado_avaliacao_avaliacao FOREIGN KEY (cod_idef_aval)
        REFERENCES dbo.tbl_avaliacao(cod_idef_aval),
    CONSTRAINT CHK_tbl_resultado_avaliacao_valores CHECK (
        qtd_acer >= 0 AND qtd_tota > 0 AND qtd_acer <= qtd_tota AND vlr_perc BETWEEN 0 AND 100
    )
);
GO

CREATE TABLE dbo.tbl_resposta_avaliacao (
    cod_idef_resp VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_resposta_avaliacao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_resu_aval VARCHAR(36) NOT NULL,
    cod_idef_ques VARCHAR(36) NOT NULL,
    cod_idef_alte VARCHAR(36) NOT NULL,
    ind_resp_corr BIT NOT NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_resposta_avaliacao_cria DEFAULT SYSUTCDATETIME(),
    CONSTRAINT PK_tbl_resposta_avaliacao PRIMARY KEY CLUSTERED (cod_idef_resp),
    CONSTRAINT UQ_tbl_resposta_avaliacao UNIQUE (cod_idef_resu_aval, cod_idef_ques),
    CONSTRAINT FK_tbl_resposta_avaliacao_resultado FOREIGN KEY (cod_idef_resu_aval)
        REFERENCES dbo.tbl_resultado_avaliacao(cod_idef_resu_aval),
    CONSTRAINT FK_tbl_resposta_avaliacao_questao FOREIGN KEY (cod_idef_ques)
        REFERENCES dbo.tbl_questao_avaliacao(cod_idef_ques),
    CONSTRAINT FK_tbl_resposta_avaliacao_alternativa FOREIGN KEY (cod_idef_alte)
        REFERENCES dbo.tbl_alternativa_avaliacao(cod_idef_alte)
);
GO

CREATE TABLE dbo.tbl_missao (
    cod_idef_miss VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_missao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    nom_miss NVARCHAR(180) NOT NULL,
    des_miss NVARCHAR(1000) NOT NULL,
    nom_tipo_miss VARCHAR(30) NULL,
    qtd_meta_miss INT NULL,
    qtd_xp_reco INT NOT NULL
        CONSTRAINT DF_tbl_missao_xp DEFAULT 100,
    ind_miss_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_missao_ativ DEFAULT 1,
    cod_idef_admi_cria VARCHAR(36) NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_missao_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_missao_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_missao_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_missao PRIMARY KEY CLUSTERED (cod_idef_miss),
    CONSTRAINT UQ_tbl_missao_nome UNIQUE (nom_miss),
    CONSTRAINT FK_tbl_missao_admin FOREIGN KEY (cod_idef_admi_cria)
        REFERENCES dbo.tbl_pessoa_admin(cod_idef_admi),
    CONSTRAINT CHK_tbl_missao_valores CHECK (
        (qtd_meta_miss IS NULL OR qtd_meta_miss > 0) AND qtd_xp_reco >= 0
    )
);
GO

CREATE TABLE dbo.tbl_aluno_missao (
    cod_idef_alun_miss VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    cod_idef_miss VARCHAR(36) NOT NULL,
    qtd_prog INT NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_prog DEFAULT 0,
    ind_miss_conc BIT NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_conc DEFAULT 0,
    ind_xp_conc BIT NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_xp DEFAULT 0,
    dat_hor_conc DATETIME2(0) NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_aluno_missao_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_aluno_missao PRIMARY KEY CLUSTERED (cod_idef_alun_miss),
    CONSTRAINT UQ_tbl_aluno_missao UNIQUE (cod_idef_stud, cod_idef_miss),
    CONSTRAINT FK_tbl_aluno_missao_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_missao_missao FOREIGN KEY (cod_idef_miss)
        REFERENCES dbo.tbl_missao(cod_idef_miss),
    CONSTRAINT CHK_tbl_aluno_missao_prog CHECK (qtd_prog >= 0)
);
GO

CREATE TABLE dbo.tbl_cenario_financeiro (
    cod_idef_cena VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_cenario_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    nom_cena NVARCHAR(180) NOT NULL,
    des_cena NVARCHAR(1500) NOT NULL,
    ind_cena_publ BIT NOT NULL
        CONSTRAINT DF_tbl_cenario_publ DEFAULT 0,
    ind_cena_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_cenario_ativ DEFAULT 1,
    cod_idef_admi_cria VARCHAR(36) NULL,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_cenario_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_cenario_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_cenario_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_cenario_financeiro PRIMARY KEY CLUSTERED (cod_idef_cena),
    CONSTRAINT UQ_tbl_cenario_nome UNIQUE (nom_cena),
    CONSTRAINT FK_tbl_cenario_admin FOREIGN KEY (cod_idef_admi_cria)
        REFERENCES dbo.tbl_pessoa_admin(cod_idef_admi)
);
GO

CREATE TABLE dbo.tbl_opcao_cenario (
    cod_idef_opca VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_opcao_cenario_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_cena VARCHAR(36) NOT NULL,
    nom_opca NVARCHAR(180) NOT NULL,
    des_cons NVARCHAR(MAX) NOT NULL,
    num_ord_opca INT NOT NULL,
    ind_opca_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_opcao_cenario_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_opcao_cenario_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_opcao_cenario_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_opcao_cenario_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_opcao_cenario PRIMARY KEY CLUSTERED (cod_idef_opca),
    CONSTRAINT UQ_tbl_opcao_cenario_ordem UNIQUE (cod_idef_cena, num_ord_opca),
    CONSTRAINT FK_tbl_opcao_cenario_cenario FOREIGN KEY (cod_idef_cena)
        REFERENCES dbo.tbl_cenario_financeiro(cod_idef_cena),
    CONSTRAINT CHK_tbl_opcao_cenario_ordem CHECK (num_ord_opca > 0)
);
GO

CREATE TABLE dbo.tbl_simulacao_aluno (
    cod_idef_simu VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_simulacao_aluno_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    nom_tipo_simu VARCHAR(20) NOT NULL,
    cod_idef_cena VARCHAR(36) NULL,
    cod_idef_opca VARCHAR(36) NULL,
    txt_entr_simu NVARCHAR(MAX) NULL,
    txt_resu_simu NVARCHAR(MAX) NULL,
    ind_xp_conc BIT NOT NULL
        CONSTRAINT DF_tbl_simulacao_aluno_xp DEFAULT 0,
    dat_hor_real DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_simulacao_aluno_real DEFAULT SYSUTCDATETIME(),
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_simulacao_aluno_cria DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_simulacao_aluno_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_simulacao_aluno PRIMARY KEY CLUSTERED (cod_idef_simu),
    CONSTRAINT FK_tbl_simulacao_aluno_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_simulacao_aluno_cenario FOREIGN KEY (cod_idef_cena)
        REFERENCES dbo.tbl_cenario_financeiro(cod_idef_cena),
    CONSTRAINT FK_tbl_simulacao_aluno_opcao FOREIGN KEY (cod_idef_opca)
        REFERENCES dbo.tbl_opcao_cenario(cod_idef_opca),
    CONSTRAINT CHK_tbl_simulacao_aluno_tipo CHECK (nom_tipo_simu IN ('CENARIO','JUROS','FATURA')),
    CONSTRAINT CHK_tbl_simulacao_aluno_cenario CHECK (
        (nom_tipo_simu = 'CENARIO' AND cod_idef_cena IS NOT NULL AND cod_idef_opca IS NOT NULL)
        OR (nom_tipo_simu IN ('JUROS','FATURA') AND cod_idef_cena IS NULL AND cod_idef_opca IS NULL)
    )
);
GO

CREATE TABLE dbo.tbl_meta_financeira (
    cod_idef_meta VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_meta_financeira_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    nom_meta NVARCHAR(180) NOT NULL,
    des_meta NVARCHAR(1000) NULL,
    vlr_objt DECIMAL(18,2) NOT NULL,
    vlr_atua DECIMAL(18,2) NOT NULL
        CONSTRAINT DF_tbl_meta_financeira_atual DEFAULT 0,
    dat_prev_conc DATE NULL,
    ind_meta_conc BIT NOT NULL
        CONSTRAINT DF_tbl_meta_financeira_conc DEFAULT 0,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_meta_financeira_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_meta_financeira_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_meta_financeira_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_meta_financeira PRIMARY KEY CLUSTERED (cod_idef_meta),
    CONSTRAINT FK_tbl_meta_financeira_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT CHK_tbl_meta_financeira_valores CHECK (
        vlr_objt > 0 AND vlr_atua >= 0
    )
);
GO

CREATE TABLE dbo.tbl_historico_atividade (
    cod_idef_hist VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_historico_atividade_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    nom_tipo_ativ VARCHAR(30) NOT NULL,
    des_ativ NVARCHAR(1000) NOT NULL,
    qtd_xp_mov INT NOT NULL
        CONSTRAINT DF_tbl_historico_atividade_xp DEFAULT 0,
    dat_hor_ativ DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_historico_atividade_ativ DEFAULT SYSUTCDATETIME(),
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_historico_atividade_cria DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_historico_atividade_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_historico_atividade PRIMARY KEY CLUSTERED (cod_idef_hist),
    CONSTRAINT FK_tbl_historico_atividade_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT CHK_tbl_historico_atividade_tipo CHECK (
        nom_tipo_ativ IN ('LOGIN','TRILHA','MISSAO','SIMULACAO','AVALIACAO','META','BADGE','NIVEL','PERFIL')
    )
);
GO

CREATE TABLE dbo.tbl_notificacao_aluno (
    cod_idef_noti VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_notificacao_aluno_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    tit_noti NVARCHAR(150) NOT NULL,
    des_noti NVARCHAR(1000) NOT NULL,
    nom_tipo_noti VARCHAR(30) NOT NULL,
    ind_noti_lida BIT NOT NULL
        CONSTRAINT DF_tbl_notificacao_aluno_lida DEFAULT 0,
    dat_hor_noti DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_notificacao_aluno_noti DEFAULT SYSUTCDATETIME(),
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_notificacao_aluno_cria DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_notificacao_aluno_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_notificacao_aluno PRIMARY KEY CLUSTERED (cod_idef_noti),
    CONSTRAINT FK_tbl_notificacao_aluno_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT CHK_tbl_notificacao_aluno_tipo CHECK (
        nom_tipo_noti IN ('GERAL','TRILHA','MISSAO','BADGE','NIVEL','META','AVALIACAO')
    )
);
GO

CREATE TABLE dbo.tbl_plano_assinatura (
    cod_idef_plan VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    nom_plan NVARCHAR(120) NOT NULL,
    des_plan NVARCHAR(1000) NOT NULL,
    vlr_plan DECIMAL(18,2) NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_valor DEFAULT 0,
    nom_prcd_plan VARCHAR(20) NOT NULL,
    qtd_limi_usua INT NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_limite DEFAULT 1,
    ind_plan_ativ BIT NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_ativ DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_plano_assinatura_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_plano_assinatura PRIMARY KEY CLUSTERED (cod_idef_plan),
    CONSTRAINT UQ_tbl_plano_assinatura_nome UNIQUE (nom_plan),
    CONSTRAINT CHK_tbl_plano_assinatura_valor CHECK (vlr_plan >= 0),
    CONSTRAINT CHK_tbl_plano_assinatura_periodo CHECK (
        nom_prcd_plan IN ('MENSAL','TRIMESTRAL','SEMESTRAL','ANUAL')
    ),
    CONSTRAINT CHK_tbl_plano_assinatura_limite CHECK (qtd_limi_usua > 0)
);
GO

CREATE TABLE dbo.tbl_aluno_plano_assinatura (
    cod_idef_assn VARCHAR(36) NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_id DEFAULT CONVERT(VARCHAR(36), NEWID()),
    cod_idef_stud VARCHAR(36) NOT NULL,
    cod_idef_plan VARCHAR(36) NOT NULL,
    dat_hor_assn DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_assn DEFAULT SYSUTCDATETIME(),
    dat_hor_expr DATETIME2(0) NOT NULL,
    nom_stat_assn VARCHAR(20) NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_stat DEFAULT 'ATIVA',
    ind_renv_assn BIT NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_renv DEFAULT 1,
    dat_hor_cria DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_cria DEFAULT SYSUTCDATETIME(),
    dat_hor_ulti_atui DATETIME2(0) NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_atui DEFAULT SYSUTCDATETIME(),
    anomesdia INT NOT NULL
        CONSTRAINT DF_tbl_aluno_plano_assinatura_anomesdia DEFAULT CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112)),
    CONSTRAINT PK_tbl_aluno_plano_assinatura PRIMARY KEY CLUSTERED (cod_idef_assn),
    CONSTRAINT FK_tbl_aluno_plano_assinatura_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES dbo.tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_plano_assinatura_plano FOREIGN KEY (cod_idef_plan)
        REFERENCES dbo.tbl_plano_assinatura(cod_idef_plan),
    CONSTRAINT CHK_tbl_aluno_plano_assinatura_status CHECK (
        nom_stat_assn IN ('ATIVA','CANCELADA','EXPIRADA','SUSPENSA')
    ),
    CONSTRAINT CHK_tbl_aluno_plano_assinatura_datas CHECK (dat_hor_expr > dat_hor_assn)
);
GO

CREATE INDEX IX_tbl_pessoa_login_email ON dbo.tbl_pessoa_login(txt_emai);
CREATE INDEX IX_tbl_pessoa_login_usuario ON dbo.tbl_pessoa_login(nom_usua);
CREATE INDEX IX_tbl_aluno_badge_stud ON dbo.tbl_aluno_badge(cod_idef_stud);
CREATE INDEX IX_tbl_aluno_trilha_stud ON dbo.tbl_aluno_trilha(cod_idef_stud);
CREATE INDEX IX_tbl_aluno_trilha_tril ON dbo.tbl_aluno_trilha(cod_idef_tril);
CREATE INDEX IX_tbl_avaliacao_tril ON dbo.tbl_avaliacao(cod_idef_tril);
CREATE INDEX IX_tbl_questao_aval ON dbo.tbl_questao_avaliacao(cod_idef_aval);
CREATE INDEX IX_tbl_alternativa_ques ON dbo.tbl_alternativa_avaliacao(cod_idef_ques);
CREATE INDEX IX_tbl_resultado_stud ON dbo.tbl_resultado_avaliacao(cod_idef_stud);
CREATE INDEX IX_tbl_aluno_missao_stud ON dbo.tbl_aluno_missao(cod_idef_stud);
CREATE INDEX IX_tbl_opcao_cenario_cena ON dbo.tbl_opcao_cenario(cod_idef_cena);
CREATE INDEX IX_tbl_simulacao_aluno_stud ON dbo.tbl_simulacao_aluno(cod_idef_stud, dat_hor_real DESC);
CREATE INDEX IX_tbl_meta_financeira_stud ON dbo.tbl_meta_financeira(cod_idef_stud);
CREATE INDEX IX_tbl_historico_atividade_stud ON dbo.tbl_historico_atividade(cod_idef_stud, dat_hor_ativ DESC);
CREATE INDEX IX_tbl_notificacao_aluno_stud ON dbo.tbl_notificacao_aluno(cod_idef_stud, ind_noti_lida, dat_hor_noti DESC);
GO
