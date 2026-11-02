USE db_moneyshield;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_nivel WHERE cod_idef_nivl = 'NIVEL-1')
    INSERT INTO dbo.tbl_nivel(cod_idef_nivl, num_nivl, nom_nivl, qtd_xp_min, qtd_xp_max)
    VALUES ('NIVEL-1', 1, N'Iniciante Financeiro', 0, 299);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_nivel WHERE cod_idef_nivl = 'NIVEL-2')
    INSERT INTO dbo.tbl_nivel(cod_idef_nivl, num_nivl, nom_nivl, qtd_xp_min, qtd_xp_max)
    VALUES ('NIVEL-2', 2, N'Planejador', 300, 699);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_nivel WHERE cod_idef_nivl = 'NIVEL-3')
    INSERT INTO dbo.tbl_nivel(cod_idef_nivl, num_nivl, nom_nivl, qtd_xp_min, qtd_xp_max)
    VALUES ('NIVEL-3', 3, N'Estrategista', 700, 1199);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_nivel WHERE cod_idef_nivl = 'NIVEL-4')
    INSERT INTO dbo.tbl_nivel(cod_idef_nivl, num_nivl, nom_nivl, qtd_xp_min, qtd_xp_max)
    VALUES ('NIVEL-4', 4, N'Mestre Financeiro', 1200, NULL);
GO

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_badge WHERE cod_idef_badg = 'BADGE-NIVEL-2')
    INSERT INTO dbo.tbl_badge(cod_idef_badg, nom_badg, des_badg, qtd_pont_badg)
    VALUES ('BADGE-NIVEL-2', N'Medalha do Planejador', N'Concedida ao alcançar o nível Planejador.', 300);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_badge WHERE cod_idef_badg = 'BADGE-NIVEL-3')
    INSERT INTO dbo.tbl_badge(cod_idef_badg, nom_badg, des_badg, qtd_pont_badg)
    VALUES ('BADGE-NIVEL-3', N'Troféu do Estrategista', N'Concedido ao alcançar o nível Estrategista.', 700);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_badge WHERE cod_idef_badg = 'BADGE-NIVEL-4')
    INSERT INTO dbo.tbl_badge(cod_idef_badg, nom_badg, des_badg, qtd_pont_badg)
    VALUES ('BADGE-NIVEL-4', N'Insígnia do Mestre Financeiro', N'Concedida ao alcançar o nível Mestre Financeiro.', 1200);
GO

DECLARE @trilhas TABLE (
    id VARCHAR(36),
    ordem INT,
    nome NVARCHAR(220),
    descricao NVARCHAR(1000),
    conteudo NVARCHAR(MAX)
);

INSERT INTO @trilhas VALUES
('TRILHA-01', 1, N'A importância da independência e responsabilidade financeira na juventude', N'Introdução à autonomia, organização e responsabilidade financeira.', N'Ter independência financeira não significa apenas possuir dinheiro. Significa conseguir tomar decisões conscientes sobre como usar sua renda, organizar gastos e assumir responsabilidade pelas próprias escolhas. Um dos primeiros passos é conhecer quanto dinheiro entra e quanto sai. Acompanhar receitas e despesas ajuda a evitar gastos maiores do que a renda disponível. Também é importante diferenciar uma compra necessária de uma compra por impulso. Planejar não significa deixar de aproveitar o dinheiro, mas encontrar equilíbrio entre necessidades atuais, desejos e objetivos futuros.'),
('TRILHA-02', 2, N'Como fazer o primeiro orçamento pessoal e organizar a mesada ou salário', N'Orçamento pessoal, receitas, despesas e organização mensal.', NULL),
('TRILHA-03', 3, N'Distinção clara entre necessidades, desejos e consumo consciente', N'Como diferenciar prioridades de desejos e consumir com consciência.', NULL),
('TRILHA-04', 4, N'Como evitar armadilhas comuns: cartão de crédito, compras por impulso e parcelamentos', N'Riscos do crédito, parcelamentos e decisões impulsivas.', NULL),
('TRILHA-05', 5, N'Estratégias práticas para renegociar dívidas e sair do vermelho', N'Organização de dívidas e estratégias de renegociação.', NULL),
('TRILHA-06', 6, N'Como montar a primeira reserva de emergência', N'Planejamento e construção de uma reserva financeira.', NULL),
('TRILHA-07', 7, N'Entendendo o poder dos juros compostos a seu favor ao começar cedo', N'Conceitos de juros compostos aplicados ao longo do tempo.', NULL),
('TRILHA-08', 8, N'Introdução simples aos investimentos básicos (Tesouro Direto, CDBs e Caixinhas)', N'Primeiros conceitos sobre investimentos de baixo nível de complexidade.', NULL),
('TRILHA-09', 9, N'Definição e planejamento de metas financeiras (viagens, estudos, primeiro carro)', N'Criação e acompanhamento de objetivos financeiros.', NULL),
('TRILHA-10', 10, N'Cuidados com golpes na internet, apostas/bets e promessas de dinheiro fácil', N'Prevenção contra golpes, apostas e promessas financeiras enganosas.', NULL),
('TRILHA-11', 11, N'Noções essenciais sobre trabalho, fontes de renda extra e empreendedorismo jovem', N'Renda, trabalho e possibilidades de geração de renda extra.', NULL),
('TRILHA-12', 12, N'Transição para a vida adulta: custos de morar sozinho e planejamento de longo prazo', N'Custos da vida adulta e organização financeira de longo prazo.', NULL);

INSERT INTO dbo.tbl_trilha(cod_idef_tril, nom_tril, des_tril, txt_cont_tril, num_ord_exib, qtd_xp_reco)
SELECT t.id, t.nome, t.descricao, t.conteudo, t.ordem, 150
FROM @trilhas t
WHERE NOT EXISTS (
    SELECT 1 FROM dbo.tbl_trilha x WHERE x.cod_idef_tril = t.id OR x.nom_tril = t.nome
);
GO

DECLARE @missoes TABLE (
    id VARCHAR(36),
    nome NVARCHAR(180),
    descricao NVARCHAR(1000),
    tipo VARCHAR(30),
    meta INT
);

INSERT INTO @missoes VALUES
('MISSAO-01', N'Raio-X da Carteira', N'Anotar 100% dos seus gastos durante 7 dias.', 'REGISTRO_GASTOS', 7),
('MISSAO-02', N'Desafio 50/30/20', N'Dividir sua renda ou mesada entre necessidades, desejos e planejamento financeiro.', 'PLANEJAMENTO', 1),
('MISSAO-03', N'Permanência — Assista 10 Aulas', N'Complete 10 conteúdos ou aulas das trilhas de aprendizagem.', 'CONTEUDOS', 10),
('MISSAO-04', N'Detector de Armadilhas', N'Analisar uma fatura de cartão de crédito e identificar juros, parcelas e taxas.', 'SIMULACAO_FATURA', 1),
('MISSAO-05', N'Escudo de Emergência', N'Calcular o valor necessário para cobrir de 3 a 6 meses do seu custo de vida.', 'RESERVA', 1);

INSERT INTO dbo.tbl_missao(cod_idef_miss, nom_miss, des_miss, nom_tipo_miss, qtd_meta_miss, qtd_xp_reco)
SELECT m.id, m.nome, m.descricao, m.tipo, m.meta, 100
FROM @missoes m
WHERE NOT EXISTS (
    SELECT 1 FROM dbo.tbl_missao x WHERE x.cod_idef_miss = m.id OR x.nom_miss = m.nome
);
GO

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_cenario_financeiro WHERE cod_idef_cena = 'CENARIO-01')
BEGIN
    INSERT INTO dbo.tbl_cenario_financeiro(cod_idef_cena, nom_cena, des_cena, ind_cena_publ)
    VALUES ('CENARIO-01', N'Comprar um notebook', N'Você possui R$ 3.000 disponíveis e precisa comprar um notebook que custa R$ 2.500. Qual decisão você tomaria?', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_cenario_financeiro WHERE cod_idef_cena = 'CENARIO-02')
BEGIN
    INSERT INTO dbo.tbl_cenario_financeiro(cod_idef_cena, nom_cena, des_cena, ind_cena_publ)
    VALUES ('CENARIO-02', N'Recebeu o primeiro salário', N'Você recebeu seu primeiro salário de R$ 2.000. Depois de pagar suas despesas essenciais, restaram R$ 800. O que você faria com esse dinheiro?', 1);
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_opcao_cenario WHERE cod_idef_opca = 'OPCAO-01-01')
INSERT INTO dbo.tbl_opcao_cenario(cod_idef_opca, cod_idef_cena, nom_opca, des_cons, num_ord_opca)
VALUES ('OPCAO-01-01', 'CENARIO-01', N'Comprar à vista', N'Decisão: Comprar à vista. Saldo inicial: R$ 3.000,00. Valor do notebook: R$ 2.500,00. Saldo restante: R$ 500,00. Dívida: R$ 0,00. Consequência: você evita juros e não cria uma dívida, mas utiliza grande parte do dinheiro disponível e fica com uma reserva menor para emergências.', 1);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_opcao_cenario WHERE cod_idef_opca = 'OPCAO-01-02')
INSERT INTO dbo.tbl_opcao_cenario(cod_idef_opca, cod_idef_cena, nom_opca, des_cons, num_ord_opca)
VALUES ('OPCAO-01-02', 'CENARIO-01', N'Parcelar', N'Decisão: Parcelar o notebook. Parcelamento: 10 parcelas de R$ 290,00. Valor total pago: R$ 2.900,00. Custo adicional: R$ 400,00. Consequência: você preserva parte do dinheiro no curto prazo, mas assume uma dívida mensal e paga R$ 400,00 a mais.', 2);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_opcao_cenario WHERE cod_idef_opca = 'OPCAO-01-03')
INSERT INTO dbo.tbl_opcao_cenario(cod_idef_opca, cod_idef_cena, nom_opca, des_cons, num_ord_opca)
VALUES ('OPCAO-01-03', 'CENARIO-01', N'Comprar depois', N'Decisão: Esperar para comprar. Você mantém sua reserva financeira e não assume uma dívida. Em contrapartida, precisará esperar mais tempo para adquirir o notebook.', 3);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_opcao_cenario WHERE cod_idef_opca = 'OPCAO-02-01')
INSERT INTO dbo.tbl_opcao_cenario(cod_idef_opca, cod_idef_cena, nom_opca, des_cons, num_ord_opca)
VALUES ('OPCAO-02-01', 'CENARIO-02', N'Gastar tudo', N'Decisão: gastar todo o dinheiro restante. Você aproveita o dinheiro imediatamente, mas termina o mês sem nenhuma reserva para emergências ou objetivos futuros.', 1);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_opcao_cenario WHERE cod_idef_opca = 'OPCAO-02-02')
INSERT INTO dbo.tbl_opcao_cenario(cod_idef_opca, cod_idef_cena, nom_opca, des_cons, num_ord_opca)
VALUES ('OPCAO-02-02', 'CENARIO-02', N'Dividir o dinheiro', N'Decisão: dividir os R$ 800,00 entre lazer e reserva. Você consegue aproveitar parte do salário e ao mesmo tempo começa a construir uma reserva.', 2);

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_opcao_cenario WHERE cod_idef_opca = 'OPCAO-02-03')
INSERT INTO dbo.tbl_opcao_cenario(cod_idef_opca, cod_idef_cena, nom_opca, des_cons, num_ord_opca)
VALUES ('OPCAO-02-03', 'CENARIO-02', N'Guardar tudo', N'Decisão: guardar todo o dinheiro restante. Sua reserva cresce mais rapidamente, mas você abre mão de utilizar parte do salário para lazer ou consumo pessoal.', 3);
GO

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_avaliacao WHERE cod_idef_aval = 'AVAL-TRILHA01-PRE')
INSERT INTO dbo.tbl_avaliacao(cod_idef_aval, cod_idef_tril, nom_tipo_aval, nom_aval)
VALUES ('AVAL-TRILHA01-PRE', 'TRILHA-01', 'PRE', N'Pré-teste da Trilha 1');

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_avaliacao WHERE cod_idef_aval = 'AVAL-TRILHA01-POS')
INSERT INTO dbo.tbl_avaliacao(cod_idef_aval, cod_idef_tril, nom_tipo_aval, nom_aval)
VALUES ('AVAL-TRILHA01-POS', 'TRILHA-01', 'POS', N'Pós-teste da Trilha 1');
GO

DECLARE @questoes TABLE (
    id VARCHAR(36), aval VARCHAR(36), ordem INT, enunciado NVARCHAR(1000),
    a1 NVARCHAR(1000), a2 NVARCHAR(1000), a3 NVARCHAR(1000), a4 NVARCHAR(1000), correta INT
);

INSERT INTO @questoes VALUES
('Q-PRE-01','AVAL-TRILHA01-PRE',1,N'O que significa ter independência financeira?',N'Nunca precisar trabalhar',N'Conseguir administrar o próprio dinheiro e tomar decisões financeiras responsáveis',N'Gastar todo o dinheiro sem pedir ajuda',N'Ter apenas um cartão de crédito',2),
('Q-PRE-02','AVAL-TRILHA01-PRE',2,N'Qual atitude ajuda a manter uma vida financeira organizada?',N'Gastar antes de verificar quanto possui',N'Ignorar pequenas despesas',N'Acompanhar receitas e gastos',N'Utilizar sempre o limite do cartão',3),
('Q-PRE-03','AVAL-TRILHA01-PRE',3,N'Por que é importante pensar antes de realizar uma compra?',N'Para evitar qualquer tipo de consumo',N'Para analisar se a compra cabe no orçamento e é realmente necessária',N'Porque todas as compras são prejudiciais',N'Somente para conseguir descontos',2),
('Q-PRE-04','AVAL-TRILHA01-PRE',4,N'Uma pessoa financeiramente responsável deve:',N'Planejar seus gastos considerando sua renda',N'Gastar toda a renda mensal',N'Utilizar crédito sempre que possível',N'Evitar controlar suas despesas',1),
('Q-PRE-05','AVAL-TRILHA01-PRE',5,N'Qual situação representa maior responsabilidade financeira?',N'Comprar algo mesmo sem dinheiro disponível',N'Planejar uma compra antes de realizá-la',N'Utilizar todo o limite do cartão',N'Ignorar despesas futuras',2),
('Q-POS-01','AVAL-TRILHA01-POS',1,N'Depois de receber sua renda mensal, qual atitude demonstra maior autonomia financeira?',N'Planejar como o dinheiro será utilizado',N'Gastar primeiro e organizar depois',N'Usar todo o limite disponível',N'Não acompanhar os gastos',1),
('Q-POS-02','AVAL-TRILHA01-POS',2,N'Qual é uma consequência positiva do planejamento financeiro?',N'Eliminar completamente todos os gastos',N'Aumentar automaticamente a renda',N'Ter maior controle sobre decisões e objetivos financeiros',N'Nunca precisar economizar',3),
('Q-POS-03','AVAL-TRILHA01-POS',3,N'Antes de assumir uma nova despesa, o ideal é:',N'Verificar se ela cabe no orçamento',N'Comprar imediatamente',N'Utilizar crédito sem analisar',N'Ignorar os gastos já existentes',1),
('Q-POS-04','AVAL-TRILHA01-POS',4,N'Qual comportamento demonstra responsabilidade financeira?',N'Acompanhar gastos e estabelecer prioridades',N'Comprar tudo que deseja imediatamente',N'Depender sempre de crédito',N'Não planejar despesas futuras',1),
('Q-POS-05','AVAL-TRILHA01-POS',5,N'Independência financeira está mais relacionada a:',N'Possuir muito dinheiro',N'Nunca realizar compras',N'Controlar conscientemente os próprios recursos',N'Não possuir despesas',3);

DECLARE @qid VARCHAR(36), @aval VARCHAR(36), @ordem INT, @enun NVARCHAR(1000), @a1 NVARCHAR(1000), @a2 NVARCHAR(1000), @a3 NVARCHAR(1000), @a4 NVARCHAR(1000), @correta INT;
DECLARE cur CURSOR LOCAL FAST_FORWARD FOR
SELECT id, aval, ordem, enunciado, a1, a2, a3, a4, correta FROM @questoes;
OPEN cur;
FETCH NEXT FROM cur INTO @qid,@aval,@ordem,@enun,@a1,@a2,@a3,@a4,@correta;
WHILE @@FETCH_STATUS = 0
BEGIN
    IF NOT EXISTS (SELECT 1 FROM dbo.tbl_questao_avaliacao WHERE cod_idef_ques = @qid)
        INSERT INTO dbo.tbl_questao_avaliacao(cod_idef_ques, cod_idef_aval, txt_enun, num_ord_ques)
        VALUES (@qid,@aval,@enun,@ordem);

    IF NOT EXISTS (SELECT 1 FROM dbo.tbl_alternativa_avaliacao WHERE cod_idef_alte = @qid + '-A1')
        INSERT INTO dbo.tbl_alternativa_avaliacao(cod_idef_alte,cod_idef_ques,txt_alte,num_ord_alte,ind_alte_corr)
        VALUES (@qid + '-A1',@qid,@a1,1,CASE WHEN @correta=1 THEN 1 ELSE 0 END);
    IF NOT EXISTS (SELECT 1 FROM dbo.tbl_alternativa_avaliacao WHERE cod_idef_alte = @qid + '-A2')
        INSERT INTO dbo.tbl_alternativa_avaliacao(cod_idef_alte,cod_idef_ques,txt_alte,num_ord_alte,ind_alte_corr)
        VALUES (@qid + '-A2',@qid,@a2,2,CASE WHEN @correta=2 THEN 1 ELSE 0 END);
    IF NOT EXISTS (SELECT 1 FROM dbo.tbl_alternativa_avaliacao WHERE cod_idef_alte = @qid + '-A3')
        INSERT INTO dbo.tbl_alternativa_avaliacao(cod_idef_alte,cod_idef_ques,txt_alte,num_ord_alte,ind_alte_corr)
        VALUES (@qid + '-A3',@qid,@a3,3,CASE WHEN @correta=3 THEN 1 ELSE 0 END);
    IF NOT EXISTS (SELECT 1 FROM dbo.tbl_alternativa_avaliacao WHERE cod_idef_alte = @qid + '-A4')
        INSERT INTO dbo.tbl_alternativa_avaliacao(cod_idef_alte,cod_idef_ques,txt_alte,num_ord_alte,ind_alte_corr)
        VALUES (@qid + '-A4',@qid,@a4,4,CASE WHEN @correta=4 THEN 1 ELSE 0 END);

    FETCH NEXT FROM cur INTO @qid,@aval,@ordem,@enun,@a1,@a2,@a3,@a4,@correta;
END
CLOSE cur;
DEALLOCATE cur;
GO
