USE db_moneyshield;
GO

/*
Este arquivo NÃO cria senha em texto puro.
A aplicação Java deve gerar o hash BCrypt e inserir o valor em txt_pass.

Fluxo recomendado de cadastro de aluno:
1. INSERT tbl_conta_pessoa
2. INSERT tbl_pessoa_login com hash BCrypt
3. INSERT tbl_pessoa_aluno
4. INSERT tbl_progresso_aluno usando NIVEL-1

Administrador não deve ser criado pelo cadastro público.
*/

DECLARE @pessoa VARCHAR(36) = CONVERT(VARCHAR(36), NEWID());
DECLARE @aluno VARCHAR(36) = CONVERT(VARCHAR(36), NEWID());
DECLARE @hash_bcrypt VARCHAR(255) = '$2a$10$SUBSTITUA_PELO_HASH_GERADO_NO_JAVA';

IF NOT EXISTS (SELECT 1 FROM dbo.tbl_pessoa_login WHERE nom_usua = N'Aluno 123')
BEGIN
    INSERT INTO dbo.tbl_conta_pessoa(cod_idef_pess, nom_pess)
    VALUES (@pessoa, N'Aluno 123');

    INSERT INTO dbo.tbl_pessoa_login(cod_idef_pess, nom_usua, txt_emai, txt_pass)
    VALUES (@pessoa, N'Aluno 123', N'aluno123@moneyshield.local', @hash_bcrypt);

    INSERT INTO dbo.tbl_pessoa_aluno(cod_idef_stud, cod_idef_pess)
    VALUES (@aluno, @pessoa);

    INSERT INTO dbo.tbl_progresso_aluno(cod_idef_stud, cod_idef_nivl)
    VALUES (@aluno, 'NIVEL-1');
END;
GO
