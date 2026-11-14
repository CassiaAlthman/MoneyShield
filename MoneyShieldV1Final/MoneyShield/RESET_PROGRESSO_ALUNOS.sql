USE db_moneyshield;
GO

SET NOCOUNT ON;
SET XACT_ABORT ON;

DECLARE @idNivelInicial VARCHAR(36);

SELECT @idNivelInicial = cod_idef_nivl
FROM dbo.tbl_nivel
WHERE num_nivl = 1
  AND ind_nivl_ativ = 1;

IF @idNivelInicial IS NULL
BEGIN
    THROW 50001, 'O nível 1 ativo não foi encontrado. Nenhuma alteração foi realizada.', 1;
END;

BEGIN TRY
    BEGIN TRANSACTION;

    -- Remove primeiro as respostas, que dependem dos resultados das avaliações.
    DELETE resposta
    FROM dbo.tbl_resposta_avaliacao AS resposta
    INNER JOIN dbo.tbl_resultado_avaliacao AS resultado
        ON resultado.cod_idef_resu_aval = resposta.cod_idef_resu_aval;

    -- Apaga os resultados de pré e pós-teste de todos os alunos.
    DELETE FROM dbo.tbl_resultado_avaliacao;

    -- Zera somente XP e nível. Mantém os contadores de trilhas, missões e simulações.
    UPDATE dbo.tbl_progresso_aluno
    SET
        cod_idef_nivl = @idNivelInicial,
        qtd_xp = 0,
        dat_hor_ulti_atui = SYSUTCDATETIME(),
        anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112));

    -- O status da trilha também é usado pelo fluxo das avaliações.
    -- DISPONIVEL permite que o aluno refaça o pré-teste.
    UPDATE dbo.tbl_aluno_trilha
    SET
        nom_stat_tril = 'DISPONIVEL',
        dat_hor_inic = NULL,
        dat_hor_conc = NULL,
        dat_hor_ulti_atui = SYSUTCDATETIME(),
        anomesdia = CONVERT(INT, CONVERT(CHAR(8), SYSUTCDATETIME(), 112));

    COMMIT TRANSACTION;

    PRINT 'Reset concluído.';
    PRINT 'XP zerado e nível redefinido para 1.';
    PRINT 'Resultados e respostas de avaliações apagados.';
    PRINT 'Status das trilhas redefinido para DISPONIVEL.';
    PRINT 'Contas, conteúdos, perguntas, alternativas, missões, simulações e metas foram preservados.';
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;

    THROW;
END CATCH;
GO
