PRAGMA foreign_keys = ON;

CREATE TABLE tbl_conta_pessoa (

    cod_idef_pess TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    nom_pess TEXT NOT NULL,
    num_cfcj TEXT NULL,
    dat_nasc TEXT NULL,
    ind_genr TEXT NULL,
    nom_carg TEXT NULL,
    ind_cont_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_conta_pessoa PRIMARY KEY (cod_idef_pess),
    CONSTRAINT CHK_tbl_conta_pessoa_genr CHECK (
        ind_genr IS NULL OR ind_genr IN ('MC','FC','HT','MT','NB','NR','OU')
    )

);

CREATE TABLE tbl_pessoa_login (

    cod_idef_pess TEXT NOT NULL,
    nom_usua TEXT NOT NULL,
    txt_emai TEXT NOT NULL,
    txt_pass TEXT NOT NULL,
    nom_algo_pass TEXT NOT NULL
        DEFAULT 'BCRYPT',
    ind_login_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_ulti_login TEXT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_pessoa_login PRIMARY KEY (cod_idef_pess),
    CONSTRAINT UQ_tbl_pessoa_login_usuario UNIQUE (nom_usua),
    CONSTRAINT UQ_tbl_pessoa_login_email UNIQUE (txt_emai),
    CONSTRAINT FK_tbl_pessoa_login_pessoa FOREIGN KEY (cod_idef_pess)
        REFERENCES tbl_conta_pessoa(cod_idef_pess)

);

CREATE TABLE tbl_pessoa_aluno (

    cod_idef_stud TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_pess TEXT NOT NULL,
    ind_usua_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_pessoa_aluno PRIMARY KEY (cod_idef_stud),
    CONSTRAINT UQ_tbl_pessoa_aluno_pessoa UNIQUE (cod_idef_pess),
    CONSTRAINT FK_tbl_pessoa_aluno_pessoa FOREIGN KEY (cod_idef_pess)
        REFERENCES tbl_conta_pessoa(cod_idef_pess)

);

CREATE TABLE tbl_pessoa_admin (

    cod_idef_admi TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_pess TEXT NOT NULL,
    nom_func TEXT NOT NULL,
    ind_admi_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_pessoa_admin PRIMARY KEY (cod_idef_admi),
    CONSTRAINT UQ_tbl_pessoa_admin_pessoa UNIQUE (cod_idef_pess),
    CONSTRAINT FK_tbl_pessoa_admin_pessoa FOREIGN KEY (cod_idef_pess)
        REFERENCES tbl_conta_pessoa(cod_idef_pess)

);

CREATE TABLE tbl_nivel (

    cod_idef_nivl TEXT NOT NULL,
    num_nivl INTEGER NOT NULL,
    nom_nivl TEXT NOT NULL,
    qtd_xp_min INTEGER NOT NULL,
    qtd_xp_max INTEGER NULL,
    ind_nivl_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_nivel PRIMARY KEY (cod_idef_nivl),
    CONSTRAINT UQ_tbl_nivel_num UNIQUE (num_nivl),
    CONSTRAINT UQ_tbl_nivel_nome UNIQUE (nom_nivl),
    CONSTRAINT CHK_tbl_nivel_xp CHECK (
        qtd_xp_min >= 0 AND (qtd_xp_max IS NULL OR qtd_xp_max >= qtd_xp_min)
    )

);

CREATE TABLE tbl_badge (

    cod_idef_badg TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    nom_badg TEXT NOT NULL,
    des_badg TEXT NOT NULL,
    txt_url_badg TEXT NULL,
    qtd_pont_badg INTEGER NOT NULL
        DEFAULT 0,
    ind_badg_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_badge PRIMARY KEY (cod_idef_badg),
    CONSTRAINT UQ_tbl_badge_nome UNIQUE (nom_badg),
    CONSTRAINT CHK_tbl_badge_pont CHECK (qtd_pont_badg >= 0)

);

CREATE TABLE tbl_progresso_aluno (

    cod_idef_stud TEXT NOT NULL,
    cod_idef_nivl TEXT NOT NULL,
    qtd_xp INTEGER NOT NULL
        DEFAULT 0,
    qtd_tril_conc INTEGER NOT NULL
        DEFAULT 0,
    qtd_miss_conc INTEGER NOT NULL
        DEFAULT 0,
    qtd_simu_real INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_progresso_aluno PRIMARY KEY (cod_idef_stud),
    CONSTRAINT FK_tbl_progresso_aluno_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_progresso_aluno_nivel FOREIGN KEY (cod_idef_nivl)
        REFERENCES tbl_nivel(cod_idef_nivl),
    CONSTRAINT CHK_tbl_progresso_aluno_valores CHECK (
        qtd_xp >= 0 AND qtd_tril_conc >= 0 AND qtd_miss_conc >= 0 AND qtd_simu_real >= 0
    )

);

CREATE TABLE tbl_aluno_badge (

    cod_idef_emss_badg TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    cod_idef_badg TEXT NOT NULL,
    dat_hor_aqui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    ind_visu_badg INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_aluno_badge PRIMARY KEY (cod_idef_emss_badg),
    CONSTRAINT UQ_tbl_aluno_badge UNIQUE (cod_idef_stud, cod_idef_badg),
    CONSTRAINT FK_tbl_aluno_badge_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_badge_badge FOREIGN KEY (cod_idef_badg)
        REFERENCES tbl_badge(cod_idef_badg)

);

CREATE TABLE tbl_trilha (

    cod_idef_tril TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    nom_tril TEXT NOT NULL,
    des_tril TEXT NULL,
    txt_cont_tril TEXT NULL,
    num_ord_exib INTEGER NOT NULL,
    qtd_xp_reco INTEGER NOT NULL
        DEFAULT 150,
    ind_tril_ativ INTEGER NOT NULL
        DEFAULT 1,
    cod_idef_admi_cria TEXT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_trilha PRIMARY KEY (cod_idef_tril),
    CONSTRAINT UQ_tbl_trilha_nome UNIQUE (nom_tril),
    CONSTRAINT UQ_tbl_trilha_ordem UNIQUE (num_ord_exib),
    CONSTRAINT FK_tbl_trilha_admin FOREIGN KEY (cod_idef_admi_cria)
        REFERENCES tbl_pessoa_admin(cod_idef_admi),
    CONSTRAINT CHK_tbl_trilha_valores CHECK (num_ord_exib > 0 AND qtd_xp_reco >= 0)

);

CREATE TABLE tbl_aluno_trilha (

    cod_idef_alun_tril TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    cod_idef_tril TEXT NOT NULL,
    nom_stat_tril TEXT NOT NULL
        DEFAULT 'DISPONIVEL',
    ind_xp_conc INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_inic TEXT NULL,
    dat_hor_conc TEXT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_aluno_trilha PRIMARY KEY (cod_idef_alun_tril),
    CONSTRAINT UQ_tbl_aluno_trilha UNIQUE (cod_idef_stud, cod_idef_tril),
    CONSTRAINT FK_tbl_aluno_trilha_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_trilha_trilha FOREIGN KEY (cod_idef_tril)
        REFERENCES tbl_trilha(cod_idef_tril),
    CONSTRAINT CHK_tbl_aluno_trilha_status CHECK (
        nom_stat_tril IN ('DISPONIVEL','PRE_REALIZADO','EM_ANDAMENTO','POS_REALIZADO','CONCLUIDA')
    )

);

CREATE TABLE tbl_avaliacao (

    cod_idef_aval TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_tril TEXT NOT NULL,
    nom_tipo_aval TEXT NOT NULL,
    nom_aval TEXT NOT NULL,
    ind_aval_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_avaliacao PRIMARY KEY (cod_idef_aval),
    CONSTRAINT UQ_tbl_avaliacao_tipo UNIQUE (cod_idef_tril, nom_tipo_aval),
    CONSTRAINT FK_tbl_avaliacao_trilha FOREIGN KEY (cod_idef_tril)
        REFERENCES tbl_trilha(cod_idef_tril),
    CONSTRAINT CHK_tbl_avaliacao_tipo CHECK (nom_tipo_aval IN ('PRE','POS'))

);

CREATE TABLE tbl_questao_avaliacao (

    cod_idef_ques TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_aval TEXT NOT NULL,
    txt_enun TEXT NOT NULL,
    num_ord_ques INTEGER NOT NULL,
    ind_ques_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_questao_avaliacao PRIMARY KEY (cod_idef_ques),
    CONSTRAINT UQ_tbl_questao_ordem UNIQUE (cod_idef_aval, num_ord_ques),
    CONSTRAINT FK_tbl_questao_avaliacao_avaliacao FOREIGN KEY (cod_idef_aval)
        REFERENCES tbl_avaliacao(cod_idef_aval),
    CONSTRAINT CHK_tbl_questao_ordem CHECK (num_ord_ques > 0)

);

CREATE TABLE tbl_alternativa_avaliacao (

    cod_idef_alte TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_ques TEXT NOT NULL,
    txt_alte TEXT NOT NULL,
    num_ord_alte INTEGER NOT NULL,
    ind_alte_corr INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_alternativa_avaliacao PRIMARY KEY (cod_idef_alte),
    CONSTRAINT UQ_tbl_alternativa_ordem UNIQUE (cod_idef_ques, num_ord_alte),
    CONSTRAINT FK_tbl_alternativa_avaliacao_questao FOREIGN KEY (cod_idef_ques)
        REFERENCES tbl_questao_avaliacao(cod_idef_ques),
    CONSTRAINT CHK_tbl_alternativa_ordem CHECK (num_ord_alte > 0)

);

CREATE TABLE tbl_resultado_avaliacao (

    cod_idef_resu_aval TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    cod_idef_aval TEXT NOT NULL,
    qtd_acer INTEGER NOT NULL,
    qtd_tota INTEGER NOT NULL,
    vlr_perc NUMERIC NOT NULL,
    dat_hor_real TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_resultado_avaliacao PRIMARY KEY (cod_idef_resu_aval),
    CONSTRAINT UQ_tbl_resultado_avaliacao UNIQUE (cod_idef_stud, cod_idef_aval),
    CONSTRAINT FK_tbl_resultado_avaliacao_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_resultado_avaliacao_avaliacao FOREIGN KEY (cod_idef_aval)
        REFERENCES tbl_avaliacao(cod_idef_aval),
    CONSTRAINT CHK_tbl_resultado_avaliacao_valores CHECK (
        qtd_acer >= 0 AND qtd_tota > 0 AND qtd_acer <= qtd_tota AND vlr_perc BETWEEN 0 AND 100
    )

);

CREATE TABLE tbl_resposta_avaliacao (

    cod_idef_resp TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_resu_aval TEXT NOT NULL,
    cod_idef_ques TEXT NOT NULL,
    cod_idef_alte TEXT NOT NULL,
    ind_resp_corr INTEGER NOT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_tbl_resposta_avaliacao PRIMARY KEY (cod_idef_resp),
    CONSTRAINT UQ_tbl_resposta_avaliacao UNIQUE (cod_idef_resu_aval, cod_idef_ques),
    CONSTRAINT FK_tbl_resposta_avaliacao_resultado FOREIGN KEY (cod_idef_resu_aval)
        REFERENCES tbl_resultado_avaliacao(cod_idef_resu_aval),
    CONSTRAINT FK_tbl_resposta_avaliacao_questao FOREIGN KEY (cod_idef_ques)
        REFERENCES tbl_questao_avaliacao(cod_idef_ques),
    CONSTRAINT FK_tbl_resposta_avaliacao_alternativa FOREIGN KEY (cod_idef_alte)
        REFERENCES tbl_alternativa_avaliacao(cod_idef_alte)

);

CREATE TABLE tbl_missao (

    cod_idef_miss TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    nom_miss TEXT NOT NULL,
    des_miss TEXT NOT NULL,
    nom_tipo_miss TEXT NULL,
    qtd_meta_miss INTEGER NULL,
    qtd_xp_reco INTEGER NOT NULL
        DEFAULT 100,
    ind_miss_ativ INTEGER NOT NULL
        DEFAULT 1,
    cod_idef_admi_cria TEXT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_missao PRIMARY KEY (cod_idef_miss),
    CONSTRAINT UQ_tbl_missao_nome UNIQUE (nom_miss),
    CONSTRAINT FK_tbl_missao_admin FOREIGN KEY (cod_idef_admi_cria)
        REFERENCES tbl_pessoa_admin(cod_idef_admi),
    CONSTRAINT CHK_tbl_missao_valores CHECK (
        (qtd_meta_miss IS NULL OR qtd_meta_miss > 0) AND qtd_xp_reco >= 0
    )

);

CREATE TABLE tbl_aluno_missao (

    cod_idef_alun_miss TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    cod_idef_miss TEXT NOT NULL,
    qtd_prog INTEGER NOT NULL
        DEFAULT 0,
    ind_miss_conc INTEGER NOT NULL
        DEFAULT 0,
    ind_xp_conc INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_conc TEXT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_aluno_missao PRIMARY KEY (cod_idef_alun_miss),
    CONSTRAINT UQ_tbl_aluno_missao UNIQUE (cod_idef_stud, cod_idef_miss),
    CONSTRAINT FK_tbl_aluno_missao_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_missao_missao FOREIGN KEY (cod_idef_miss)
        REFERENCES tbl_missao(cod_idef_miss),
    CONSTRAINT CHK_tbl_aluno_missao_prog CHECK (qtd_prog >= 0)

);

CREATE TABLE tbl_cenario_financeiro (

    cod_idef_cena TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    nom_cena TEXT NOT NULL,
    des_cena TEXT NOT NULL,
    ind_cena_publ INTEGER NOT NULL
        DEFAULT 0,
    ind_cena_ativ INTEGER NOT NULL
        DEFAULT 1,
    cod_idef_admi_cria TEXT NULL,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_cenario_financeiro PRIMARY KEY (cod_idef_cena),
    CONSTRAINT UQ_tbl_cenario_nome UNIQUE (nom_cena),
    CONSTRAINT FK_tbl_cenario_admin FOREIGN KEY (cod_idef_admi_cria)
        REFERENCES tbl_pessoa_admin(cod_idef_admi)

);

CREATE TABLE tbl_opcao_cenario (

    cod_idef_opca TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_cena TEXT NOT NULL,
    nom_opca TEXT NOT NULL,
    des_cons TEXT NOT NULL,
    num_ord_opca INTEGER NOT NULL,
    ind_opca_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_opcao_cenario PRIMARY KEY (cod_idef_opca),
    CONSTRAINT UQ_tbl_opcao_cenario_ordem UNIQUE (cod_idef_cena, num_ord_opca),
    CONSTRAINT FK_tbl_opcao_cenario_cenario FOREIGN KEY (cod_idef_cena)
        REFERENCES tbl_cenario_financeiro(cod_idef_cena),
    CONSTRAINT CHK_tbl_opcao_cenario_ordem CHECK (num_ord_opca > 0)

);

CREATE TABLE tbl_simulacao_aluno (

    cod_idef_simu TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    nom_tipo_simu TEXT NOT NULL,
    cod_idef_cena TEXT NULL,
    cod_idef_opca TEXT NULL,
    txt_entr_simu TEXT NULL,
    txt_resu_simu TEXT NULL,
    ind_xp_conc INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_real TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_simulacao_aluno PRIMARY KEY (cod_idef_simu),
    CONSTRAINT FK_tbl_simulacao_aluno_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_simulacao_aluno_cenario FOREIGN KEY (cod_idef_cena)
        REFERENCES tbl_cenario_financeiro(cod_idef_cena),
    CONSTRAINT FK_tbl_simulacao_aluno_opcao FOREIGN KEY (cod_idef_opca)
        REFERENCES tbl_opcao_cenario(cod_idef_opca),
    CONSTRAINT CHK_tbl_simulacao_aluno_tipo CHECK (nom_tipo_simu IN ('CENARIO','JUROS','FATURA')),
    CONSTRAINT CHK_tbl_simulacao_aluno_cenario CHECK (
        (nom_tipo_simu = 'CENARIO' AND cod_idef_cena IS NOT NULL AND cod_idef_opca IS NOT NULL)
        OR (nom_tipo_simu IN ('JUROS','FATURA') AND cod_idef_cena IS NULL AND cod_idef_opca IS NULL)
    )

);

CREATE TABLE tbl_meta_financeira (

    cod_idef_meta TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    nom_meta TEXT NOT NULL,
    des_meta TEXT NULL,
    vlr_objt NUMERIC NOT NULL,
    vlr_atua NUMERIC NOT NULL
        DEFAULT 0,
    dat_prev_conc TEXT NULL,
    ind_meta_conc INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_meta_financeira PRIMARY KEY (cod_idef_meta),
    CONSTRAINT FK_tbl_meta_financeira_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT CHK_tbl_meta_financeira_valores CHECK (
        vlr_objt > 0 AND vlr_atua >= 0
    )

);

CREATE TABLE tbl_historico_atividade (

    cod_idef_hist TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    nom_tipo_ativ TEXT NOT NULL,
    des_ativ TEXT NOT NULL,
    qtd_xp_mov INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_ativ TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_historico_atividade PRIMARY KEY (cod_idef_hist),
    CONSTRAINT FK_tbl_historico_atividade_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT CHK_tbl_historico_atividade_tipo CHECK (
        nom_tipo_ativ IN ('LOGIN','TRILHA','MISSAO','SIMULACAO','AVALIACAO','META','BADGE','NIVEL','PERFIL')
    )

);

CREATE TABLE tbl_notificacao_aluno (

    cod_idef_noti TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    tit_noti TEXT NOT NULL,
    des_noti TEXT NOT NULL,
    nom_tipo_noti TEXT NOT NULL,
    ind_noti_lida INTEGER NOT NULL
        DEFAULT 0,
    dat_hor_noti TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_notificacao_aluno PRIMARY KEY (cod_idef_noti),
    CONSTRAINT FK_tbl_notificacao_aluno_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT CHK_tbl_notificacao_aluno_tipo CHECK (
        nom_tipo_noti IN ('GERAL','TRILHA','MISSAO','BADGE','NIVEL','META','AVALIACAO')
    )

);

CREATE TABLE tbl_plano_assinatura (

    cod_idef_plan TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    nom_plan TEXT NOT NULL,
    des_plan TEXT NOT NULL,
    vlr_plan NUMERIC NOT NULL
        DEFAULT 0,
    nom_prcd_plan TEXT NOT NULL,
    qtd_limi_usua INTEGER NOT NULL
        DEFAULT 1,
    ind_plan_ativ INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_plano_assinatura PRIMARY KEY (cod_idef_plan),
    CONSTRAINT UQ_tbl_plano_assinatura_nome UNIQUE (nom_plan),
    CONSTRAINT CHK_tbl_plano_assinatura_valor CHECK (vlr_plan >= 0),
    CONSTRAINT CHK_tbl_plano_assinatura_periodo CHECK (
        nom_prcd_plan IN ('MENSAL','TRIMESTRAL','SEMESTRAL','ANUAL')
    ),
    CONSTRAINT CHK_tbl_plano_assinatura_limite CHECK (qtd_limi_usua > 0)

);

CREATE TABLE tbl_aluno_plano_assinatura (

    cod_idef_assn TEXT NOT NULL
        DEFAULT (lower(hex(randomblob(16)))),
    cod_idef_stud TEXT NOT NULL,
    cod_idef_plan TEXT NOT NULL,
    dat_hor_assn TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_expr TEXT NOT NULL,
    nom_stat_assn TEXT NOT NULL
        DEFAULT 'ATIVA',
    ind_renv_assn INTEGER NOT NULL
        DEFAULT 1,
    dat_hor_cria TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    dat_hor_ulti_atui TEXT NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    anomesdia INTEGER NOT NULL
        DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
    CONSTRAINT PK_tbl_aluno_plano_assinatura PRIMARY KEY (cod_idef_assn),
    CONSTRAINT FK_tbl_aluno_plano_assinatura_aluno FOREIGN KEY (cod_idef_stud)
        REFERENCES tbl_pessoa_aluno(cod_idef_stud),
    CONSTRAINT FK_tbl_aluno_plano_assinatura_plano FOREIGN KEY (cod_idef_plan)
        REFERENCES tbl_plano_assinatura(cod_idef_plan),
    CONSTRAINT CHK_tbl_aluno_plano_assinatura_status CHECK (
        nom_stat_assn IN ('ATIVA','CANCELADA','EXPIRADA','SUSPENSA')
    ),
    CONSTRAINT CHK_tbl_aluno_plano_assinatura_datas CHECK (dat_hor_expr > dat_hor_assn)

);

CREATE UNIQUE INDEX IF NOT EXISTS UX_tbl_conta_pessoa_cfcj ON tbl_conta_pessoa(num_cfcj) WHERE num_cfcj IS NOT NULL;

CREATE TABLE tbl_atividade_missao (

        cod_idef_ativ_miss TEXT NOT NULL
            DEFAULT (lower(hex(randomblob(16)))),
        cod_idef_stud TEXT NOT NULL,
        cod_idef_miss TEXT NOT NULL,
        nom_tipo_ativ TEXT NOT NULL,
        dat_ref TEXT NULL,
        vlr_prim NUMERIC NULL,
        vlr_segu NUMERIC NULL,
        vlr_terc NUMERIC NULL,
        vlr_quar NUMERIC NULL,
        txt_dado TEXT NULL,
        dat_hor_cria TEXT NOT NULL
            DEFAULT CURRENT_TIMESTAMP,
        dat_hor_ulti_atui TEXT NOT NULL
            DEFAULT CURRENT_TIMESTAMP,
        anomesdia INTEGER NOT NULL
            DEFAULT (CAST(strftime('%Y%m%d','now') AS INTEGER)),
        CONSTRAINT PK_tbl_atividade_missao PRIMARY KEY (cod_idef_ativ_miss),
        CONSTRAINT FK_tbl_atividade_missao_aluno FOREIGN KEY (cod_idef_stud)
            REFERENCES tbl_pessoa_aluno(cod_idef_stud),
        CONSTRAINT FK_tbl_atividade_missao_missao FOREIGN KEY (cod_idef_miss)
            REFERENCES tbl_missao(cod_idef_miss),
        CONSTRAINT CHK_tbl_atividade_missao_valores CHECK (
            (vlr_prim IS NULL OR vlr_prim >= 0)
            AND (vlr_segu IS NULL OR vlr_segu >= 0)
            AND (vlr_terc IS NULL OR vlr_terc >= 0)
            AND (vlr_quar IS NULL OR vlr_quar >= 0)
        )
    
);

CREATE INDEX IF NOT EXISTS IX_tbl_atividade_missao_stud_miss ON tbl_atividade_missao(cod_idef_stud, cod_idef_miss, dat_hor_cria DESC);

CREATE UNIQUE INDEX IF NOT EXISTS UX_tbl_atividade_missao_gasto_dia ON tbl_atividade_missao(cod_idef_stud, cod_idef_miss, dat_ref) WHERE nom_tipo_ativ='REGISTRO_GASTOS' AND dat_ref IS NOT NULL;
