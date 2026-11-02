package br.com.moneyshield.ui.auth;

import br.com.moneyshield.dao.CadastroDAO;
import br.com.moneyshield.app.EducacaoFinanceiraMain;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaCadastro {

    private static final Color ROXO_LOGIN =
            new Color(76, 55, 135);

    private TelaCadastro() {
    }

    public static void abrir(JFrame janelaAnterior) {

        if (janelaAnterior != null) {
            janelaAnterior.dispose();
        }

        JFrame janela =
                new JFrame(
                        "MoneyShield - Criar Conta"
                );

        janela.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        janela.setSize(
                620,
                650
        );

        janela.setLocationRelativeTo(
                null
        );

        janela.setResizable(
                false
        );

        JPanel painelPrincipal =
                new JPanel();

        painelPrincipal.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelPrincipal.setLayout(
                new BoxLayout(
                        painelPrincipal,
                        BoxLayout.Y_AXIS
                )
        );

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        70,
                        30,
                        70
                )
        );

        JLabel logo =
                TemaMoneyShield.criarLogoMoneyShield(
                        34
                );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        "Crie sua conta"
                );

        titulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        23
                )
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitulo =
                new JLabel(
                        "Comece sua jornada de educação financeira."
                );

        subtitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        subtitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        subtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JTextField campoNome =
                criarCampoTexto();

        JTextField campoUsuario =
                criarCampoTexto();

        JTextField campoEmail =
                criarCampoTexto();

        JPasswordField campoSenha =
                criarCampoSenha();

        JPasswordField campoConfirmarSenha =
                criarCampoSenha();

        JPanel blocoNome =
                criarBlocoCampo(
                        "Nome",
                        campoNome
                );

        JPanel blocoUsuario =
                criarBlocoCampo(
                        "Usuário",
                        campoUsuario
                );

        JPanel blocoEmail =
                criarBlocoCampo(
                        "E-mail",
                        campoEmail
                );

        JPanel blocoSenha =
                criarBlocoCampo(
                        "Senha",
                        campoSenha
                );

        JPanel blocoConfirmarSenha =
                criarBlocoCampo(
                        "Confirmar senha",
                        campoConfirmarSenha
                );

        JLabel mensagem =
                new JLabel(
                        " "
                );

        mensagem.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        mensagem.setForeground(
                TemaMoneyShield.ROSA
        );

        mensagem.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JButton botaoCriar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Criar Conta"
                );

        botaoCriar.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoCriar.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        JButton botaoVoltar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Voltar para o login"
                );

        botaoVoltar.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoVoltar.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        botaoCriar.addActionListener(e -> {

            String nome =
                    campoNome
                            .getText()
                            .trim();

            String usuario =
                    campoUsuario
                            .getText()
                            .trim();

            String email =
                    campoEmail
                            .getText()
                            .trim();

            String senha =
                    new String(
                            campoSenha.getPassword()
                    );

            String confirmarSenha =
                    new String(
                            campoConfirmarSenha.getPassword()
                    );

            if (nome.isBlank()
                    || usuario.isBlank()
                    || email.isBlank()
                    || senha.isBlank()
                    || confirmarSenha.isBlank()) {

                mensagem.setText(
                        "Preencha todos os campos."
                );

                return;
            }

            if (!senha.equals(
                    confirmarSenha
            )) {

                mensagem.setText(
                        "As senhas não coincidem."
                );

                campoSenha.setText("");
                campoConfirmarSenha.setText("");

                return;
            }

            botaoCriar.setEnabled(
                    false
            );

            mensagem.setText(
                    "Criando conta..."
            );

            try {

                CadastroDAO cadastroDAO =
                        new CadastroDAO();

                CadastroDAO.ResultadoCadastro resultado =
                        cadastroDAO.cadastrarAluno(
                                nome,
                                usuario,
                                email,
                                senha
                        );

                switch (resultado) {

                    case SUCESSO -> {

                        JOptionPane.showMessageDialog(
                                janela,
                                "Conta criada com sucesso!\n"
                                        + "Agora você já pode entrar no MoneyShield.",
                                "Conta criada",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        janela.dispose();

                        EducacaoFinanceiraMain.criarTelaLogin();
                    }

                    case NOME_INVALIDO ->
                            mensagem.setText(
                                    "Digite um nome válido."
                            );

                    case USUARIO_INVALIDO ->
                            mensagem.setText(
                                    "O usuário deve ter entre 3 e 60 caracteres."
                            );

                    case EMAIL_INVALIDO ->
                            mensagem.setText(
                                    "Digite um e-mail válido."
                            );

                    case SENHA_INVALIDA ->
                            mensagem.setText(
                                    "A senha deve ter pelo menos 8 caracteres."
                            );

                    case USUARIO_EXISTENTE ->
                            mensagem.setText(
                                    "Esse nome de usuário já está em uso."
                            );

                    case EMAIL_EXISTENTE ->
                            mensagem.setText(
                                    "Já existe uma conta com esse e-mail."
                            );

                    case NIVEL_INICIAL_NAO_ENCONTRADO ->
                            mensagem.setText(
                                    "Não foi possível configurar o nível inicial."
                            );
                }

            } catch (SQLException erro) {

                mensagem.setText(
                        "Não foi possível criar a conta."
                );

                System.err.println(
                        "Erro ao cadastrar aluno:"
                );

                System.err.println(
                        erro.getMessage()
                );

            } finally {

                botaoCriar.setEnabled(
                        true
                );
            }
        });

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            EducacaoFinanceiraMain.criarTelaLogin();
        });

        campoConfirmarSenha.addActionListener(e ->
                botaoCriar.doClick()
        );

        painelPrincipal.add(
                logo
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painelPrincipal.add(
                titulo
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelPrincipal.add(
                subtitulo
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        22
                )
        );

        painelPrincipal.add(
                blocoNome
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painelPrincipal.add(
                blocoUsuario
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painelPrincipal.add(
                blocoEmail
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painelPrincipal.add(
                blocoSenha
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painelPrincipal.add(
                blocoConfirmarSenha
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painelPrincipal.add(
                mensagem
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painelPrincipal.add(
                botaoCriar
        );

        painelPrincipal.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painelPrincipal.add(
                botaoVoltar
        );

        janela.setContentPane(
                painelPrincipal
        );

        janela.getRootPane()
                .setDefaultButton(
                        botaoCriar
                );

        janela.setVisible(
                true
        );
    }

    private static JPanel criarBlocoCampo(
            String texto,
            JComponent campo) {

        JPanel painel =
                new JPanel();

        painel.setOpaque(
                false
        );

        painel.setLayout(
                new BoxLayout(
                        painel,
                        BoxLayout.Y_AXIS
                )
        );

        painel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        65
                )
        );

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(
                TemaMoneyShield.MARINHO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        campo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painel.add(
                label
        );

        painel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painel.add(
                campo
        );

        return painel;
    }

    private static JTextField criarCampoTexto() {

        JTextField campo =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campo
        );

        campo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        campo.setPreferredSize(
                new Dimension(
                        480,
                        38
                )
        );

        return campo;
    }

    private static JPasswordField criarCampoSenha() {

        JPasswordField campo =
                new JPasswordField();

        TemaMoneyShield.estilizarCampoSenha(
                campo
        );

        campo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        campo.setPreferredSize(
                new Dimension(
                        480,
                        38
                )
        );

        return campo;
    }
}