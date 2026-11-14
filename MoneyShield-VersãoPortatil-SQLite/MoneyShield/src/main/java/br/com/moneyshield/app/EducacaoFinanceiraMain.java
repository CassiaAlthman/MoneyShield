package br.com.moneyshield.app;

import br.com.moneyshield.ui.admin.CenariosFinanceirosAdmin;
import br.com.moneyshield.ui.student.ConfiguracoesUsuario;
import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.ui.admin.GerenciamentoAlunosAdmin;
import br.com.moneyshield.dao.LoginDAO;
import br.com.moneyshield.ui.student.MetasFinanceiras;
import br.com.moneyshield.ui.student.MissoesDesafios;
import br.com.moneyshield.ui.admin.MissoesDesafiosAdmin;
import br.com.moneyshield.repository.RepositorioCenarios;
import br.com.moneyshield.repository.RepositorioMissoes;
import br.com.moneyshield.repository.RepositorioTrilhas;
import br.com.moneyshield.session.SessaoUsuario;
import br.com.moneyshield.ui.auth.TelaCadastro;
import br.com.moneyshield.ui.student.TelaSimulacoesAluno;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import br.com.moneyshield.ui.admin.TrilhasAdmin;
import br.com.moneyshield.ui.student.TrilhasAluno;
import br.com.moneyshield.session.UsuarioAutenticado;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducacaoFinanceiraMain {

    private static final Color ROXO_LOGIN =
            new Color(76, 55, 135);

    public static void main(String[] args) {

        TemaMoneyShield.instalarIconeAplicacao();

        SwingUtilities.invokeLater(
                EducacaoFinanceiraMain::criarTelaLogin
        );
    }

    public static void criarTelaLogin() {

        SessaoUsuario.encerrar();

        JFrame janela =
                new JFrame(
                        "MoneyShield - Login"
                );

        janela.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        janela.setSize(
                1000,
                720
        );

        janela.setLocationRelativeTo(
                null
        );

        janela.setResizable(
                false
        );

        JLayeredPane camada =
                new JLayeredPane();

        JPanel painelFundo =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        JPanel painelSuperior =
                new JPanel();

        painelSuperior.setBackground(
                ROXO_LOGIN
        );

        JPanel painelInferior =
                new JPanel();

        painelInferior.setBackground(
                Color.WHITE
        );

        painelFundo.add(
                painelSuperior
        );

        painelFundo.add(
                painelInferior
        );

        JPanel painelNovidades =
                criarPainelNovidades();

        JPanel painelLogo =
                criarPainelLogoLogin();

        JPanel painelInfoInferior =
                criarPainelTextoInferiorLogin();

        PainelLoginArredondado painelLogin =
                new PainelLoginArredondado();

        painelLogin.setLayout(
                new BoxLayout(
                        painelLogin,
                        BoxLayout.Y_AXIS
                )
        );

        painelLogin.setBorder(
                BorderFactory.createCompoundBorder(
                        new BordaCartaoLogin(),
                        BorderFactory.createEmptyBorder(
                                24,
                                34,
                                20,
                                34
                        )
                )
        );

        JLabel titulo =
                new JLabel(
                        "Bem-vindo de volta"
                );

        titulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        21
                )
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitulo =
                new JLabel(
                        "Entre na sua conta para continuar."
                );

        subtitulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.PLAIN,
                        12
                )
        );

        subtitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        subtitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel painelCampos =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                16,
                                0
                        )
                );

        painelCampos.setOpaque(
                false
        );

        painelCampos.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelCampos.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        72
                )
        );

        JPanel blocoUsuario =
                new JPanel();

        blocoUsuario.setOpaque(
                false
        );

        blocoUsuario.setLayout(
                new BoxLayout(
                        blocoUsuario,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel labelUsuario =
                criarLabelLogin(
                        "Usuário ou e-mail"
                );

        JTextField campoUsuario =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoUsuario
        );

        campoUsuario.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        campoUsuario.setPreferredSize(
                new Dimension(
                        220,
                        40
                )
        );

        blocoUsuario.add(
                labelUsuario
        );

        blocoUsuario.add(
                Box.createVerticalStrut(
                        4
                )
        );

        blocoUsuario.add(
                campoUsuario
        );

        JPanel blocoSenha =
                new JPanel();

        blocoSenha.setOpaque(
                false
        );

        blocoSenha.setLayout(
                new BoxLayout(
                        blocoSenha,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel labelSenha =
                criarLabelLogin(
                        "Senha"
                );

        JPasswordField campoSenha =
                new JPasswordField();

        TemaMoneyShield.estilizarCampoSenha(
                campoSenha
        );

        campoSenha.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        campoSenha.setPreferredSize(
                new Dimension(
                        220,
                        40
                )
        );

        blocoSenha.add(
                labelSenha
        );

        blocoSenha.add(
                Box.createVerticalStrut(
                        4
                )
        );

        blocoSenha.add(
                campoSenha
        );

        painelCampos.add(
                blocoUsuario
        );

        painelCampos.add(
                blocoSenha
        );

        JButton botaoEntrar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Entrar"
                );

        botaoEntrar.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoEntrar.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        JLabel mensagemErro =
                new JLabel(
                        " "
                );

        mensagemErro.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        mensagemErro.setForeground(
                TemaMoneyShield.ROSA
        );

        mensagemErro.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel textoCadastro =
                new JLabel(
                        "<html>Ainda não tem uma conta? <u>Criar conta</u></html>"
                );

        textoCadastro.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        textoCadastro.setForeground(
                ROXO_LOGIN
        );

        textoCadastro.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        textoCadastro.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelLogin.add(
                titulo
        );

        painelLogin.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelLogin.add(
                subtitulo
        );

        painelLogin.add(
                Box.createVerticalStrut(
                        16
                )
        );

        painelLogin.add(
                painelCampos
        );

        painelLogin.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelLogin.add(
                botaoEntrar
        );

        painelLogin.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelLogin.add(
                mensagemErro
        );

        painelLogin.add(
                Box.createVerticalGlue()
        );

        painelLogin.add(
                textoCadastro
        );

        LoginDAO loginDAO =
                new LoginDAO();

        botaoEntrar.addActionListener(e -> {

            String login =
                    campoUsuario
                            .getText()
                            .trim();

            String senha =
                    new String(
                            campoSenha.getPassword()
                    );

            mensagemErro.setText(
                    " "
            );

            if (login.isBlank()
                    || senha.isBlank()) {

                mensagemErro.setText(
                        "Informe o usuário e a senha."
                );

                return;
            }

            try {

                UsuarioAutenticado usuario =
                        loginDAO.autenticar(
                                login,
                                senha
                        );

                if (usuario == null) {

                    mensagemErro.setText(
                            "Login ou senha inválidos."
                    );

                    campoSenha.setText(
                            ""
                    );

                    return;
                }

                SessaoUsuario.iniciar(
                        usuario
                );

                janela.dispose();

                if (usuario.isAdministrador()) {

                    criarTelaAdministrador();

                } else if (usuario.isAluno()) {

                    criarTelaAluno();

                } else {

                    SessaoUsuario.encerrar();
                    criarTelaLogin();
                }

            } catch (SQLException erro) {

                mensagemErro.setText(
                        "Não foi possível acessar o banco de dados."
                );

                System.err.println(
                        "Erro ao autenticar usuário: "
                                + erro.getMessage()
                );
            }
        });

        campoSenha.addActionListener(e ->
                botaoEntrar.doClick()
        );

        textoCadastro.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e) {

                        TelaCadastro.abrir(
                                janela
                        );
                    }
                }
        );

        camada.add(
                painelFundo,
                JLayeredPane.DEFAULT_LAYER
        );

        camada.add(
                painelNovidades,
                JLayeredPane.PALETTE_LAYER
        );

        camada.add(
                painelLogo,
                JLayeredPane.PALETTE_LAYER
        );

        camada.add(
                painelLogin,
                JLayeredPane.PALETTE_LAYER
        );

        camada.add(
                painelInfoInferior,
                JLayeredPane.PALETTE_LAYER
        );

        janela.setContentPane(
                camada
        );

        janela.addComponentListener(
                new ComponentAdapter() {

                    @Override
                    public void componentResized(
                            ComponentEvent e) {

                        atualizarLayoutTelaLogin(
                                camada,
                                painelFundo,
                                painelNovidades,
                                painelLogo,
                                painelLogin,
                                painelInfoInferior
                        );
                    }

                    @Override
                    public void componentShown(
                            ComponentEvent e) {

                        atualizarLayoutTelaLogin(
                                camada,
                                painelFundo,
                                painelNovidades,
                                painelLogo,
                                painelLogin,
                                painelInfoInferior
                        );
                    }
                }
        );

        janela.getRootPane().setDefaultButton(
                botaoEntrar
        );

        janela.setVisible(
                true
        );

        atualizarLayoutTelaLogin(
                camada,
                painelFundo,
                painelNovidades,
                painelLogo,
                painelLogin,
                painelInfoInferior
        );
    }

    private static void atualizarLayoutTelaLogin(
            JLayeredPane camada,
            JPanel painelFundo,
            JPanel painelNovidades,
            JPanel painelLogo,
            JPanel painelLogin,
            JPanel painelInfoInferior) {

        int largura =
                camada.getWidth();

        int altura =
                camada.getHeight();

        painelFundo.setBounds(
                0,
                0,
                largura,
                altura
        );

        int larguraLogin =
                590;

        int alturaLogin =
                320;

        int xLogin =
                (largura - larguraLogin) / 2;

        int yLogin =
                (altura - alturaLogin) / 2;

        painelLogin.setBounds(
                xLogin,
                yLogin,
                larguraLogin,
                alturaLogin
        );

        int larguraLogo =
                430;

        int alturaLogo =
                95;

        int xLogo =
                (largura - larguraLogo) / 2;

        int yLogo =
                (yLogin - alturaLogo) / 2 + 6;

        painelLogo.setBounds(
                xLogo,
                yLogo,
                larguraLogo,
                alturaLogo
        );

        int larguraNovidades =
                160;

        int alturaNovidades =
                142;

        int xNovidades =
                38;

        int yNovidades =
                28;

        painelNovidades.setBounds(
                xNovidades,
                yNovidades,
                larguraNovidades,
                alturaNovidades
        );

        int larguraInfo =
                360;

        int alturaInfo =
                78;

        int xInfo =
                (largura - larguraInfo) / 2;

        int espacoInferior =
                altura
                        - (
                        yLogin
                                + alturaLogin
                );

        int yInfo =
                yLogin
                        + alturaLogin
                        + (
                        espacoInferior
                                - alturaInfo
                ) / 2;

        painelInfoInferior.setBounds(
                xInfo,
                yInfo,
                larguraInfo,
                alturaInfo
        );

        painelLogin.revalidate();
        painelLogin.repaint();

        painelLogo.revalidate();
        painelLogo.repaint();

        painelNovidades.revalidate();
        painelNovidades.repaint();

        painelInfoInferior.revalidate();
        painelInfoInferior.repaint();
    }

    private static JPanel criarPainelLogoLogin() {

        JPanel painel =
                new JPanel(
                        new GridBagLayout()
                );

        painel.setOpaque(
                false
        );

        JPanel logo =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                0
                        )
                );

        logo.setOpaque(
                false
        );

        JLabel money =
                new JLabel(
                        "Money"
                );

        money.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        58
                )
        );

        money.setForeground(
                TemaMoneyShield.AZUL
        );

        JLabel shield =
                new JLabel(
                        "Shield"
                );

        shield.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        58
                )
        );

        shield.setForeground(
                TemaMoneyShield.AMARELO
        );

        logo.add(
                money
        );

        logo.add(
                shield
        );

        painel.add(
                logo
        );

        return painel;
    }

    private static JPanel criarPainelNovidades() {

        PainelNovidadesArredondado painel =
                new PainelNovidadesArredondado();

        painel.setLayout(
                new BoxLayout(
                        painel,
                        BoxLayout.Y_AXIS
                )
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        14,
                        10,
                        10
                )
        );

        JLabel categoria =
                new JLabel(
                        "NOVIDADES"
                );

        categoria.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        8
                )
        );

        categoria.setForeground(
                TemaMoneyShield.AMARELO
        );

        categoria.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        "Última trilha adicionada"
                );

        titulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        10
                )
        );

        titulo.setForeground(
                Color.WHITE
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel nomeTrilha =
                new JLabel(
                        "<html>"
                                + "<div style='width:115px;'>"
                                + obterUltimaTrilhaAdicionada()
                                + "</div>"
                                + "</html>"
                );

        nomeTrilha.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        9
                )
        );

        nomeTrilha.setForeground(
                TemaMoneyShield.AZUL
        );

        nomeTrilha.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painel.add(
                categoria
        );

        painel.add(
                Box.createVerticalStrut(
                        7
                )
        );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                nomeTrilha
        );

        return painel;
    }

    private static JPanel criarPainelTextoInferiorLogin() {

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

        JLabel titulo =
                new JLabel(
                        "Educação financeira em prática"
                );

        titulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        14
                )
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel slogan =
                new JLabel(
                        "Proteja seu futuro aprendendo a cuidar do seu dinheiro."
                );

        slogan.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        11
                )
        );

        slogan.setForeground(
                ROXO_LOGIN
        );

        slogan.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel descricao =
                new JLabel(
                        "<html><div style='text-align:center;'>"
                                + "Trilhas, missões, metas e simulações para praticar decisões financeiras."
                                + "</div></html>"
                );

        descricao.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.PLAIN,
                        10
                )
        );

        descricao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        descricao.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                slogan
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                descricao
        );

        return painel;
    }

    private static JLabel criarLabelLogin(
            String texto) {

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

        return label;
    }

    private static String obterUltimaTrilhaAdicionada() {

        ArrayList<String> trilhas =
                RepositorioTrilhas.getTrilhas();

        if (trilhas == null
                || trilhas.isEmpty()) {

            return "Nenhuma trilha cadastrada";
        }

        return trilhas.get(
                trilhas.size() - 1
        );
    }

    public static void criarTelaAdministrador() {

        if (!SessaoUsuario.isAdministrador()) {
            criarTelaLogin();
            return;
        }

        JFrame janela =
                new JFrame(
                        "MoneyShield - Área do Administrador"
                );

        janela.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        janela.setSize(
                850,
                650
        );

        janela.setLocationRelativeTo(
                null
        );

        janela.setResizable(
                false
        );

        JLayeredPane painelCamadas =
                new JLayeredPane();

        painelCamadas.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelCamadas.setOpaque(
                true
        );

        JPanel painelPrincipal =
                new JPanel(
                        new BorderLayout()
                );

        painelPrincipal.setBounds(
                0,
                0,
                850,
                610
        );

        painelPrincipal.setBackground(
                TemaMoneyShield.FUNDO
        );

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout()
                );

        painelSuperior.setBackground(
                TemaMoneyShield.MARINHO
        );

        painelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        28,
                        16,
                        28
                )
        );

        painelSuperior.setPreferredSize(
                new Dimension(
                        850,
                        84
                )
        );

        JPanel painelMarca =
                new JPanel();

        painelMarca.setOpaque(
                false
        );

        painelMarca.setLayout(
                new BoxLayout(
                        painelMarca,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo =
                TemaMoneyShield.criarLogoMoneyShield();

        JLabel descricaoMarca =
                new JLabel(
                        "Painel administrativo"
                );

        descricaoMarca.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricaoMarca.setForeground(
                TemaMoneyShield.AZUL
        );

        descricaoMarca.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        0,
                        4,
                        0
                )
        );

        descricaoMarca.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelMarca.add(
                logo
        );

        painelMarca.add(
                descricaoMarca
        );

        JButton botaoPerfil =
                TemaMoneyShield.criarBotaoCabecalho(
                        "Perfil"
                );

        JPanel painelAcoesCabecalho =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                4
                        )
                );

        painelAcoesCabecalho.setOpaque(
                false
        );

        painelAcoesCabecalho.add(
                botaoPerfil
        );

        painelSuperior.add(
                painelMarca,
                BorderLayout.WEST
        );

        painelSuperior.add(
                painelAcoesCabecalho,
                BorderLayout.EAST
        );

        JPanel painelConteudo =
                new JPanel(
                        new BorderLayout(
                                22,
                                0
                        )
                );

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        30,
                        12,
                        30
                )
        );

        JPanel painelPrincipalConteudo =
                new JPanel();

        painelPrincipalConteudo.setOpaque(
                false
        );

        painelPrincipalConteudo.setLayout(
                new BoxLayout(
                        painelPrincipalConteudo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Olá, "
                                + SessaoUsuario.getNome()
                                + "!"
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_TITULO
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitulo =
                new JLabel(
                        "Gerencie os conteúdos e acompanhe o sistema."
                );

        subtitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        subtitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        subtitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelPrincipalConteudo.add(
                titulo
        );

        painelPrincipalConteudo.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelPrincipalConteudo.add(
                subtitulo
        );

        painelPrincipalConteudo.add(
                Box.createVerticalStrut(
                        18
                )
        );

        JPanel painelBotoes =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                14,
                                14
                        )
                );

        painelBotoes.setOpaque(
                false
        );

        painelBotoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelBotoes.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        250
                )
        );

        painelBotoes.setPreferredSize(
                new Dimension(
                        560,
                        250
                )
        );

        JButton botaoTrilhas =
                TemaMoneyShield.criarBotaoCard(
                        "Trilhas",
                        "Gerencie trilhas e avaliações",
                        TemaMoneyShield.AZUL,
                        TemaMoneyShield.MARINHO
                );

        JButton botaoMissoes =
                TemaMoneyShield.criarBotaoCard(
                        "Missões",
                        "Crie e gerencie desafios",
                        TemaMoneyShield.ROSA,
                        TemaMoneyShield.BRANCO
                );

        JButton botaoCenarios =
                TemaMoneyShield.criarBotaoCard(
                        "Cenários",
                        "Configure simulações financeiras",
                        TemaMoneyShield.AMARELO,
                        TemaMoneyShield.MARINHO
                );

        JButton botaoAlunos =
                TemaMoneyShield.criarBotaoCard(
                        "Alunos",
                        "Acompanhe progresso e resultados",
                        TemaMoneyShield.MALVA,
                        TemaMoneyShield.BRANCO
                );

        painelBotoes.add(
                botaoTrilhas
        );

        painelBotoes.add(
                botaoMissoes
        );

        painelBotoes.add(
                botaoCenarios
        );

        painelBotoes.add(
                botaoAlunos
        );

        painelPrincipalConteudo.add(
                painelBotoes
        );

        JLabel valorTrilhasAdmin =
                new JLabel();

        JLabel valorMissoesAdmin =
                new JLabel();

        JLabel valorCenariosAdmin =
                new JLabel();

        JLabel valorPublicadosAdmin =
                new JLabel();

        JPanel painelResumo =
                new JPanel();

        painelResumo.setOpaque(
                false
        );

        painelResumo.setLayout(
                new BoxLayout(
                        painelResumo,
                        BoxLayout.Y_AXIS
                )
        );

        painelResumo.setPreferredSize(
                new Dimension(
                        190,
                        0
                )
        );

        painelResumo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                1,
                                0,
                                0,
                                TemaMoneyShield.BORDA
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                18,
                                0,
                                0
                        )
                )
        );

        JLabel tituloResumo =
                new JLabel(
                        "Resumo do sistema"
                );

        tituloResumo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        18
                )
        );

        tituloResumo.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloResumo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelResumo.add(
                tituloResumo
        );

        painelResumo.add(
                Box.createVerticalStrut(
                        18
                )
        );

        JPanel cardTrilhas =
                criarCardEstatisticaAluno(
                        "Trilhas",
                        valorTrilhasAdmin,
                        TemaMoneyShield.AZUL
                );

        JPanel cardMissoes =
                criarCardEstatisticaAluno(
                        "Missões",
                        valorMissoesAdmin,
                        TemaMoneyShield.ROSA
                );

        JPanel cardCenarios =
                criarCardEstatisticaAluno(
                        "Cenários",
                        valorCenariosAdmin,
                        TemaMoneyShield.AMARELO
                );

        JPanel cardPublicados =
                criarCardEstatisticaAluno(
                        "Publicados",
                        valorPublicadosAdmin,
                        TemaMoneyShield.MALVA
                );

        Dimension tamanhoCardResumo =
                new Dimension(
                        170,
                        82
                );

        JPanel[] cardsResumo = {
                cardTrilhas,
                cardMissoes,
                cardCenarios,
                cardPublicados
        };

        for (JPanel card : cardsResumo) {

            card.setMaximumSize(
                    tamanhoCardResumo
            );

            card.setPreferredSize(
                    tamanhoCardResumo
            );

            card.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painelResumo.add(
                    card
            );

            painelResumo.add(
                    Box.createVerticalStrut(
                            11
                    )
            );
        }

        atualizarResumoAdministrador(
                valorTrilhasAdmin,
                valorMissoesAdmin,
                valorCenariosAdmin,
                valorPublicadosAdmin
        );

        painelConteudo.add(
                painelPrincipalConteudo,
                BorderLayout.CENTER
        );

        painelConteudo.add(
                painelResumo,
                BorderLayout.EAST
        );

        JPanel painelInferior =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                28,
                                6
                        )
                );

        painelInferior.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelInferior.setPreferredSize(
                new Dimension(
                        850,
                        45
                )
        );

        JButton botaoSair =
                TemaMoneyShield.criarBotaoSecundario(
                        "Sair"
                );

        painelInferior.add(
                botaoSair
        );

        botaoSair.addActionListener(e -> {

            janela.dispose();
            criarTelaLogin();
        });

        botaoTrilhas.addActionListener(e -> {

            janela.setVisible(false);
            TrilhasAdmin.abrir(janela);
        });

        botaoMissoes.addActionListener(e -> {

            janela.setVisible(false);
            MissoesDesafiosAdmin.abrir(janela);
        });

        botaoCenarios.addActionListener(e -> {

            janela.setVisible(false);
            CenariosFinanceirosAdmin.abrir(janela);
        });

        botaoAlunos.addActionListener(e -> {

            janela.setVisible(false);
            GerenciamentoAlunosAdmin.abrir(janela);
        });

        painelPrincipal.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                painelConteudo,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                painelInferior,
                BorderLayout.SOUTH
        );

        JPanel painelPerfil =
                criarPainelLateralAluno();

        painelPerfil.setBounds(
                545,
                0,
                285,
                610
        );

        JPanel cabecalhoPerfil =
                criarCabecalhoPainelAluno(
                        "Perfil"
                );

        JButton fecharPerfil =
                TemaMoneyShield.criarBotaoCabecalho(
                        "X"
                );

        cabecalhoPerfil.add(
                fecharPerfil,
                BorderLayout.EAST
        );

        JPanel conteudoPerfil =
                new JPanel();

        conteudoPerfil.setOpaque(
                false
        );

        conteudoPerfil.setLayout(
                new BoxLayout(
                        conteudoPerfil,
                        BoxLayout.Y_AXIS
                )
        );

        conteudoPerfil.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        20,
                        15
                )
        );

        JScrollPane scrollPerfil =
                new JScrollPane(
                        conteudoPerfil
                );

        scrollPerfil.setBorder(
                null
        );

        scrollPerfil.setOpaque(
                false
        );

        scrollPerfil
                .getViewport()
                .setOpaque(
                        false
                );

        scrollPerfil
                .getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelPerfil.add(
                cabecalhoPerfil,
                BorderLayout.NORTH
        );

        painelPerfil.add(
                scrollPerfil,
                BorderLayout.CENTER
        );

        painelPerfil.setVisible(
                false
        );

        botaoPerfil.addActionListener(e -> {

            if (painelPerfil.isVisible()) {

                painelPerfil.setVisible(
                        false
                );

                return;
            }

            atualizarPerfilAdministrador(
                    conteudoPerfil,
                    janela
            );

            painelPerfil.setVisible(
                    true
            );
        });

        fecharPerfil.addActionListener(e ->
                painelPerfil.setVisible(
                        false
                )
        );

        janela.addComponentListener(
                new ComponentAdapter() {

                    @Override
                    public void componentShown(
                            ComponentEvent e) {

                        atualizarResumoAdministrador(
                                valorTrilhasAdmin,
                                valorMissoesAdmin,
                                valorCenariosAdmin,
                                valorPublicadosAdmin
                        );

                        if (painelPerfil.isVisible()) {

                            atualizarPerfilAdministrador(
                                    conteudoPerfil,
                                    janela
                            );
                        }
                    }
                }
        );

        painelCamadas.add(
                painelPrincipal,
                JLayeredPane.DEFAULT_LAYER
        );

        painelCamadas.add(
                painelPerfil,
                JLayeredPane.PALETTE_LAYER
        );

        janela.add(
                painelCamadas
        );

        janela.setVisible(
                true
        );
    }

    public static void criarTelaAluno() {

        if (!SessaoUsuario.isAluno()) {
            criarTelaLogin();
            return;
        }

        JFrame janela =
                new JFrame(
                        "MoneyShield - Área do Aluno"
                );

        janela.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        janela.setSize(
                850,
                650
        );

        janela.setLocationRelativeTo(
                null
        );

        janela.setResizable(
                false
        );

        JLayeredPane painelCamadas =
                new JLayeredPane();

        painelCamadas.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelCamadas.setOpaque(
                true
        );

        JPanel painelPrincipal =
                new JPanel(
                        new BorderLayout()
                );

        painelPrincipal.setBounds(
                0,
                0,
                850,
                610
        );

        painelPrincipal.setBackground(
                TemaMoneyShield.FUNDO
        );

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout()
                );

        painelSuperior.setBackground(
                TemaMoneyShield.MARINHO
        );

        painelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        28,
                        16,
                        28
                )
        );

        painelSuperior.setPreferredSize(
                new Dimension(
                        850,
                        84
                )
        );

        JPanel painelMarca =
                new JPanel();

        painelMarca.setOpaque(
                false
        );

        painelMarca.setLayout(
                new BoxLayout(
                        painelMarca,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo =
                TemaMoneyShield.criarLogoMoneyShield();

        JLabel descricaoMarca =
                new JLabel(
                        "Educação financeira gamificada"
                );

        descricaoMarca.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.PLAIN,
                        13
                )
        );

        descricaoMarca.setForeground(
                TemaMoneyShield.AZUL
        );

        descricaoMarca.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        0,
                        4,
                        0
                )
        );

        descricaoMarca.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelMarca.add(
                logo
        );

        painelMarca.add(
                descricaoMarca
        );

        JButton botaoNotificacoes =
                TemaMoneyShield.criarBotaoCabecalho(
                        "Notificações"
                );

        JButton botaoPerfil =
                TemaMoneyShield.criarBotaoCabecalho(
                        "Perfil"
                );

        JPanel painelAcoesCabecalho =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                4
                        )
                );

        painelAcoesCabecalho.setOpaque(
                false
        );

        painelAcoesCabecalho.add(
                botaoNotificacoes
        );

        painelAcoesCabecalho.add(
                botaoPerfil
        );

        painelSuperior.add(
                painelMarca,
                BorderLayout.WEST
        );

        painelSuperior.add(
                painelAcoesCabecalho,
                BorderLayout.EAST
        );

        JPanel painelConteudo =
                new JPanel(
                        new BorderLayout(
                                22,
                                0
                        )
                );

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        30,
                        12,
                        30
                )
        );

        JPanel painelPrincipalConteudo =
                new JPanel();

        painelPrincipalConteudo.setOpaque(
                false
        );

        painelPrincipalConteudo.setLayout(
                new BoxLayout(
                        painelPrincipalConteudo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Olá, "
                                + SessaoUsuario.getNome()
                                + "!"
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_TITULO
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitulo =
                new JLabel(
                        "Continue evoluindo sua vida financeira."
                );

        subtitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        subtitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        subtitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelPrincipalConteudo.add(
                titulo
        );

        painelPrincipalConteudo.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelPrincipalConteudo.add(
                subtitulo
        );

        painelPrincipalConteudo.add(
                Box.createVerticalStrut(
                        14
                )
        );

        JPanel painelBotoes =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                14,
                                14
                        )
                );

        painelBotoes.setOpaque(
                false
        );

        painelBotoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelBotoes.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        216
                )
        );

        painelBotoes.setPreferredSize(
                new Dimension(
                        560,
                        216
                )
        );

        JButton botaoTrilhas =
                TemaMoneyShield.criarBotaoCard(
                        "Trilhas",
                        "Aprenda conceitos financeiros",
                        TemaMoneyShield.AZUL,
                        TemaMoneyShield.MARINHO
                );

        JButton botaoSimulacoes =
                TemaMoneyShield.criarBotaoCard(
                        "Simulações",
                        "Pratique decisões financeiras",
                        TemaMoneyShield.AMARELO,
                        TemaMoneyShield.MARINHO
                );

        JButton botaoMissoes =
                TemaMoneyShield.criarBotaoCard(
                        "Missões",
                        "Complete desafios e ganhe XP",
                        TemaMoneyShield.ROSA,
                        TemaMoneyShield.BRANCO
                );

        JButton botaoMetas =
                TemaMoneyShield.criarBotaoCard(
                        "Metas",
                        "Acompanhe seus objetivos",
                        TemaMoneyShield.MALVA,
                        TemaMoneyShield.BRANCO
                );

        painelBotoes.add(
                botaoTrilhas
        );

        painelBotoes.add(
                botaoSimulacoes
        );

        painelBotoes.add(
                botaoMissoes
        );

        painelBotoes.add(
                botaoMetas
        );

        painelPrincipalConteudo.add(
                painelBotoes
        );

        painelPrincipalConteudo.add(
                Box.createVerticalStrut(
                        14
                )
        );

        JPanel cardProgresso =
                TemaMoneyShield.criarCard();

        cardProgresso.setLayout(
                new BorderLayout(
                        15,
                        8
                )
        );

        cardProgresso.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardProgresso.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        88
                )
        );

        cardProgresso.setPreferredSize(
                new Dimension(
                        560,
                        88
                )
        );

        JLabel labelNivel =
                new JLabel();

        labelNivel.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        labelNivel.setForeground(
                TemaMoneyShield.MARINHO
        );

        JLabel labelXp =
                new JLabel();

        labelXp.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        13
                )
        );

        labelXp.setForeground(
                TemaMoneyShield.ROSA
        );

        JPanel painelDadosProgresso =
                new JPanel(
                        new BorderLayout()
                );

        painelDadosProgresso.setOpaque(
                false
        );

        painelDadosProgresso.add(
                labelNivel,
                BorderLayout.WEST
        );

        painelDadosProgresso.add(
                labelXp,
                BorderLayout.EAST
        );

        JProgressBar barraXp =
                new JProgressBar(
                        0,
                        100
                );

        TemaMoneyShield.estilizarBarraProgresso(
                barraXp
        );

        barraXp.setPreferredSize(
                new Dimension(
                        520,
                        22
                )
        );

        atualizarStatusAluno(
                labelNivel,
                labelXp,
                barraXp
        );

        cardProgresso.add(
                painelDadosProgresso,
                BorderLayout.NORTH
        );

        cardProgresso.add(
                barraXp,
                BorderLayout.CENTER
        );

        painelPrincipalConteudo.add(
                cardProgresso
        );

        JLabel valorTrilhas =
                new JLabel();

        JLabel valorMissoes =
                new JLabel();

        JLabel valorSimulacoes =
                new JLabel();

        JPanel painelLateralProgresso =
                new JPanel();

        painelLateralProgresso.setOpaque(
                false
        );

        painelLateralProgresso.setLayout(
                new BoxLayout(
                        painelLateralProgresso,
                        BoxLayout.Y_AXIS
                )
        );

        painelLateralProgresso.setPreferredSize(
                new Dimension(
                        190,
                        0
                )
        );

        painelLateralProgresso.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                1,
                                0,
                                0,
                                TemaMoneyShield.BORDA
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                18,
                                0,
                                0
                        )
                )
        );

        JLabel tituloProgresso =
                new JLabel(
                        "Seu progresso"
                );

        tituloProgresso.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        18
                )
        );

        tituloProgresso.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloProgresso.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelLateralProgresso.add(
                tituloProgresso
        );

        painelLateralProgresso.add(
                Box.createVerticalStrut(
                        18
                )
        );

        JPanel cardTrilhas =
                criarCardEstatisticaAluno(
                        "Trilhas",
                        valorTrilhas,
                        TemaMoneyShield.AZUL
                );

        JPanel cardMissoes =
                criarCardEstatisticaAluno(
                        "Missões",
                        valorMissoes,
                        TemaMoneyShield.ROSA
                );

        JPanel cardSimulacoes =
                criarCardEstatisticaAluno(
                        "Simulações",
                        valorSimulacoes,
                        TemaMoneyShield.AMARELO
                );

        Dimension tamanhoCardLateral =
                new Dimension(
                        170,
                        104
                );

        JPanel[] cards = {
                cardTrilhas,
                cardMissoes,
                cardSimulacoes
        };

        for (JPanel card : cards) {

            card.setMaximumSize(
                    tamanhoCardLateral
            );

            card.setPreferredSize(
                    tamanhoCardLateral
            );

            card.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );
        }

        painelLateralProgresso.add(
                cardTrilhas
        );

        painelLateralProgresso.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelLateralProgresso.add(
                cardMissoes
        );

        painelLateralProgresso.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelLateralProgresso.add(
                cardSimulacoes
        );

        atualizarEstatisticasAluno(
                valorTrilhas,
                valorMissoes,
                valorSimulacoes
        );

        painelConteudo.add(
                painelPrincipalConteudo,
                BorderLayout.CENTER
        );

        painelConteudo.add(
                painelLateralProgresso,
                BorderLayout.EAST
        );

        JPanel painelInferior =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                28,
                                6
                        )
                );

        painelInferior.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelInferior.setPreferredSize(
                new Dimension(
                        850,
                        45
                )
        );

        JButton botaoSair =
                TemaMoneyShield.criarBotaoSecundario(
                        "Sair"
                );

        painelInferior.add(
                botaoSair
        );

        botaoSair.addActionListener(e -> {

            janela.dispose();
            criarTelaLogin();
        });

        botaoTrilhas.addActionListener(e -> {

            janela.setVisible(false);
            TrilhasAluno.abrir(janela);
        });

        botaoSimulacoes.addActionListener(e -> {

            janela.setVisible(false);
            TelaSimulacoesAluno.abrir(janela);
        });

        botaoMissoes.addActionListener(e -> {

            janela.setVisible(false);
            MissoesDesafios.abrir(janela);
        });

        botaoMetas.addActionListener(e -> {

            janela.setVisible(false);
            MetasFinanceiras.abrir(janela);
        });

        painelPrincipal.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                painelConteudo,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                painelInferior,
                BorderLayout.SOUTH
        );

        JPanel painelNotificacoes =
                criarPainelLateralAluno();

        painelNotificacoes.setBounds(
                545,
                0,
                285,
                610
        );

        JPanel cabecalhoNotificacoes =
                criarCabecalhoPainelAluno(
                        "Notificações"
                );

        JButton fecharNotificacoes =
                TemaMoneyShield.criarBotaoCabecalho(
                        "X"
                );

        cabecalhoNotificacoes.add(
                fecharNotificacoes,
                BorderLayout.EAST
        );

        JPanel listaNotificacoes =
                new JPanel();

        listaNotificacoes.setOpaque(
                false
        );

        listaNotificacoes.setLayout(
                new BoxLayout(
                        listaNotificacoes,
                        BoxLayout.Y_AXIS
                )
        );

        listaNotificacoes.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        JScrollPane scrollNotificacoes =
                new JScrollPane(
                        listaNotificacoes
                );

        scrollNotificacoes.setBorder(
                null
        );

        scrollNotificacoes.setOpaque(
                false
        );

        scrollNotificacoes
                .getViewport()
                .setOpaque(
                        false
                );

        scrollNotificacoes
                .getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        JButton botaoLimparNotificacoes =
                TemaMoneyShield.criarBotaoSecundario(
                        "Limpar notificações"
                );

        JPanel painelLimparNotificacoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                10
                        )
                );

        painelLimparNotificacoes.setOpaque(
                false
        );

        painelLimparNotificacoes.add(
                botaoLimparNotificacoes
        );

        painelNotificacoes.add(
                cabecalhoNotificacoes,
                BorderLayout.NORTH
        );

        painelNotificacoes.add(
                scrollNotificacoes,
                BorderLayout.CENTER
        );

        painelNotificacoes.add(
                painelLimparNotificacoes,
                BorderLayout.SOUTH
        );

        painelNotificacoes.setVisible(
                false
        );

        JPanel painelPerfil =
                criarPainelLateralAluno();

        painelPerfil.setBounds(
                545,
                0,
                285,
                610
        );

        JPanel cabecalhoPerfil =
                criarCabecalhoPainelAluno(
                        "Perfil"
                );

        JButton fecharPerfil =
                TemaMoneyShield.criarBotaoCabecalho(
                        "X"
                );

        cabecalhoPerfil.add(
                fecharPerfil,
                BorderLayout.EAST
        );

        JPanel conteudoPerfil =
                new JPanel();

        conteudoPerfil.setOpaque(
                false
        );

        conteudoPerfil.setLayout(
                new BoxLayout(
                        conteudoPerfil,
                        BoxLayout.Y_AXIS
                )
        );

        conteudoPerfil.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        20,
                        15
                )
        );

        JScrollPane scrollPerfil =
                new JScrollPane(
                        conteudoPerfil
                );

        scrollPerfil.setBorder(
                null
        );

        scrollPerfil.setOpaque(
                false
        );

        scrollPerfil
                .getViewport()
                .setOpaque(
                        false
                );

        scrollPerfil
                .getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelPerfil.add(
                cabecalhoPerfil,
                BorderLayout.NORTH
        );

        painelPerfil.add(
                scrollPerfil,
                BorderLayout.CENTER
        );

        painelPerfil.setVisible(
                false
        );

        botaoNotificacoes.addActionListener(e -> {

            if (painelNotificacoes.isVisible()) {

                painelNotificacoes.setVisible(
                        false
                );

                return;
            }

            painelPerfil.setVisible(
                    false
            );

            atualizarNotificacoes(
                    listaNotificacoes
            );

            painelNotificacoes.setVisible(
                    true
            );

            painelNotificacoes.revalidate();
            painelNotificacoes.repaint();
        });

        botaoLimparNotificacoes.addActionListener(e -> {

            GamificacaoService.limparNotificacoes();

            atualizarNotificacoes(
                    listaNotificacoes
            );

            painelNotificacoes.revalidate();
            painelNotificacoes.repaint();
        });

        botaoPerfil.addActionListener(e -> {

            if (painelPerfil.isVisible()) {

                painelPerfil.setVisible(
                        false
                );

                return;
            }

            painelNotificacoes.setVisible(
                    false
            );

            atualizarPerfilAluno(
                    conteudoPerfil,
                    janela
            );

            painelPerfil.setVisible(
                    true
            );
        });

        fecharNotificacoes.addActionListener(e ->
                painelNotificacoes.setVisible(
                        false
                )
        );

        fecharPerfil.addActionListener(e ->
                painelPerfil.setVisible(
                        false
                )
        );

        janela.addComponentListener(
                new ComponentAdapter() {

                    @Override
                    public void componentShown(
                            ComponentEvent e) {

                        atualizarStatusAluno(
                                labelNivel,
                                labelXp,
                                barraXp
                        );

                        atualizarEstatisticasAluno(
                                valorTrilhas,
                                valorMissoes,
                                valorSimulacoes
                        );

                        if (painelPerfil.isVisible()) {

                            atualizarPerfilAluno(
                                    conteudoPerfil,
                                    janela
                            );
                        }
                    }
                }
        );

        painelCamadas.add(
                painelPrincipal,
                JLayeredPane.DEFAULT_LAYER
        );

        painelCamadas.add(
                painelNotificacoes,
                JLayeredPane.PALETTE_LAYER
        );

        painelCamadas.add(
                painelPerfil,
                JLayeredPane.PALETTE_LAYER
        );

        janela.add(
                painelCamadas
        );

        janela.setVisible(
                true
        );
    }

    private static void atualizarResumoAdministrador(
            JLabel valorTrilhas,
            JLabel valorMissoes,
            JLabel valorCenarios,
            JLabel valorPublicados) {

        valorTrilhas.setText(
                String.valueOf(
                        RepositorioTrilhas.quantidade()
                )
        );

        valorMissoes.setText(
                String.valueOf(
                        RepositorioMissoes.quantidade()
                )
        );

        valorCenarios.setText(
                String.valueOf(
                        RepositorioCenarios.quantidade()
                )
        );

        valorPublicados.setText(
                String.valueOf(
                        RepositorioCenarios.quantidadePublicados()
                )
        );
    }

    private static JPanel criarCardEstatisticaAluno(
            String titulo,
            JLabel valor,
            Color destaque) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout()
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                destaque
        );

        faixa.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JLabel labelTitulo =
                new JLabel(
                        titulo
                );

        labelTitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        labelTitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        valor.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        20
                )
        );

        valor.setForeground(
                TemaMoneyShield.MARINHO
        );

        JPanel textos =
                new JPanel();

        textos.setOpaque(
                false
        );

        textos.setLayout(
                new BoxLayout(
                        textos,
                        BoxLayout.Y_AXIS
                )
        );

        textos.add(
                labelTitulo
        );

        textos.add(
                valor
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                textos,
                BorderLayout.CENTER
        );

        return card;
    }

    private static void atualizarEstatisticasAluno(
            JLabel valorTrilhas,
            JLabel valorMissoes,
            JLabel valorSimulacoes) {

        valorTrilhas.setText(
                String.valueOf(
                        GamificacaoService.getTrilhasConcluidas()
                )
        );

        valorMissoes.setText(
                String.valueOf(
                        GamificacaoService.getMissoesConcluidas()
                )
        );

        valorSimulacoes.setText(
                String.valueOf(
                        GamificacaoService.getSimulacoesRealizadas()
                )
        );
    }

    private static JPanel criarPainelLateralAluno() {

        JPanel painel =
                new JPanel(
                        new BorderLayout()
                );

        painel.setBackground(
                TemaMoneyShield.MARINHO
        );

        painel.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        3,
                        0,
                        0,
                        TemaMoneyShield.ROSA
                )
        );

        return painel;
    }

    private static JPanel criarCabecalhoPainelAluno(
            String titulo) {

        JPanel painel =
                new JPanel(
                        new BorderLayout()
                );

        painel.setOpaque(
                false
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        15,
                        12,
                        15
                )
        );

        JLabel labelTitulo =
                new JLabel(
                        titulo
                );

        labelTitulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        19
                )
        );

        labelTitulo.setForeground(
                TemaMoneyShield.BRANCO
        );

        painel.add(
                labelTitulo,
                BorderLayout.WEST
        );

        return painel;
    }

    private static void atualizarPerfilAdministrador(
            JPanel conteudoPerfil,
            JFrame janela) {

        conteudoPerfil.removeAll();

        conteudoPerfil.add(
                criarTextoPerfil(
                        SessaoUsuario.getNome(),
                        18,
                        Font.BOLD
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        3
                )
        );

        conteudoPerfil.add(
                criarTextoPerfil(
                        "Conta de Administrador",
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        25
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfil(
                        "ADMINISTRAÇÃO"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        8
                )
        );

        conteudoPerfil.add(
                criarTextoPerfil(
                        "Trilhas cadastradas: "
                                + RepositorioTrilhas.quantidade(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        5
                )
        );

        conteudoPerfil.add(
                criarTextoPerfil(
                        "Missões cadastradas: "
                                + RepositorioMissoes.quantidade(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        5
                )
        );

        conteudoPerfil.add(
                criarTextoPerfil(
                        "Cenários cadastrados: "
                                + RepositorioCenarios.quantidade(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        5
                )
        );

        conteudoPerfil.add(
                criarTextoPerfil(
                        "Cenários publicados: "
                                + RepositorioCenarios.quantidadePublicados(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        25
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfil(
                        "CONFIGURAÇÕES"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        10
                )
        );

        conteudoPerfil.add(
                ConfiguracoesUsuario.criarPainel(
                        janela
                )
        );

        conteudoPerfil.revalidate();
        conteudoPerfil.repaint();
    }

    private static void atualizarPerfilAluno(
            JPanel conteudoPerfil,
            JFrame janela) {

        conteudoPerfil.removeAll();

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        SessaoUsuario.getNome(),
                        18,
                        Font.BOLD
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        3
                )
        );

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        "Conta de Aluno",
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        20
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfilAluno(
                        "PROGRESSO"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        8
                )
        );

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        "Nível "
                                + GamificacaoService.getNivel()
                                + " - "
                                + GamificacaoService.getNomeNivel(),
                        14,
                        Font.BOLD
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        4
                )
        );

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        GamificacaoService.getPontos()
                                + " XP",
                        13,
                        Font.BOLD
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        8
                )
        );

        JProgressBar barra =
                new JProgressBar(
                        0,
                        100
                );

        TemaMoneyShield.estilizarBarraProgresso(
                barra
        );

        barra.setValue(
                GamificacaoService.getProgressoNivel()
        );

        barra.setMaximumSize(
                new Dimension(
                        240,
                        25
                )
        );

        barra.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        if (GamificacaoService.getNivel() < 4) {

            barra.setString(
                    GamificacaoService.getXpDentroNivel()
                            + " / "
                            + GamificacaoService.getXpNecessarioNivel()
                            + " XP"
            );

        } else {

            barra.setString(
                    "Nível máximo"
            );
        }

        conteudoPerfil.add(
                barra
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        22
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfilAluno(
                        "ESTATÍSTICAS"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        8
                )
        );

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        "Missões concluídas: "
                                + GamificacaoService.getMissoesConcluidas(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        5
                )
        );

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        "Trilhas concluídas: "
                                + GamificacaoService.getTrilhasConcluidas(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        5
                )
        );

        conteudoPerfil.add(
                criarTextoPerfilAluno(
                        "Simulações realizadas: "
                                + GamificacaoService.getSimulacoesRealizadas(),
                        12,
                        Font.PLAIN
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        22
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfilAluno(
                        "CONQUISTAS"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        8
                )
        );

        ArrayList<String> conquistas =
                GamificacaoService.getConquistas();

        if (conquistas.isEmpty()) {

            conteudoPerfil.add(
                    criarTextoPerfilAluno(
                            "Nenhuma conquista desbloqueada.",
                            11,
                            Font.PLAIN
                    )
            );

        } else {

            for (String conquista : conquistas) {

                conteudoPerfil.add(
                        criarTextoPerfilAluno(
                                "• " + conquista,
                                11,
                                Font.PLAIN
                        )
                );

                conteudoPerfil.add(
                        Box.createVerticalStrut(
                                6
                        )
                );
            }
        }

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        22
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfilAluno(
                        "HISTÓRICO DE ATIVIDADES"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        8
                )
        );

        ArrayList<String> historico =
                GamificacaoService.getHistorico();

        if (historico.isEmpty()) {

            conteudoPerfil.add(
                    criarTextoPerfilAluno(
                            "Nenhuma atividade registrada ainda.",
                            11,
                            Font.PLAIN
                    )
            );

        } else {

            for (int i = historico.size() - 1;
                 i >= 0;
                 i--) {

                JLabel item =
                        criarTextoPerfilAluno(
                                "<html><div style='width:220px;'>• "
                                        + historico.get(i)
                                        + "</div></html>",
                                11,
                                Font.PLAIN
                        );

                conteudoPerfil.add(
                        item
                );

                conteudoPerfil.add(
                        Box.createVerticalStrut(
                                8
                        )
                );
            }
        }

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        22
                )
        );

        conteudoPerfil.add(
                criarTituloSecaoPerfilAluno(
                        "CONFIGURAÇÕES"
                )
        );

        conteudoPerfil.add(
                Box.createVerticalStrut(
                        10
                )
        );

        conteudoPerfil.add(
                ConfiguracoesUsuario.criarPainel(
                        janela
                )
        );

        conteudoPerfil.revalidate();
        conteudoPerfil.repaint();
    }

    private static JLabel criarTextoPerfilAluno(
            String texto,
            int tamanho,
            int estilo) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        estilo,
                        tamanho
                )
        );

        label.setForeground(
                TemaMoneyShield.BRANCO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    private static JLabel criarTituloSecaoPerfilAluno(
            String texto) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                TemaMoneyShield.AMARELO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    private static void atualizarStatusAluno(
            JLabel labelNivel,
            JLabel labelXp,
            JProgressBar barraXp) {

        labelNivel.setText(
                "Nível "
                        + GamificacaoService.getNivel()
                        + " - "
                        + GamificacaoService.getNomeNivel()
        );

        labelXp.setText(
                GamificacaoService.getPontos()
                        + " XP"
        );

        barraXp.setValue(
                GamificacaoService.getProgressoNivel()
        );

        if (GamificacaoService.getNivel() < 4) {

            barraXp.setString(
                    GamificacaoService.getXpDentroNivel()
                            + " / "
                            + GamificacaoService.getXpNecessarioNivel()
                            + " XP"
            );

        } else {

            barraXp.setString(
                    "Nível máximo"
            );
        }
    }

    private static JLabel criarTextoPerfil(
            String texto,
            int tamanho,
            int estilo) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        estilo,
                        tamanho
                )
        );

        label.setForeground(
                TemaMoneyShield.BRANCO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    private static JLabel criarTituloSecaoPerfil(
            String texto) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                TemaMoneyShield.AMARELO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    private static void atualizarNotificacoes(
            JPanel listaNotificacoes) {

        listaNotificacoes.removeAll();

        ArrayList<String> notificacoes =
                GamificacaoService.getNotificacoes();

        if (notificacoes.isEmpty()) {

            JLabel nenhumaNotificacao =
                    criarTextoPerfilAluno(
                            "Nenhuma notificação no momento.",
                            11,
                            Font.PLAIN
                    );

            nenhumaNotificacao.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            listaNotificacoes.add(
                    Box.createVerticalStrut(
                            20
                    )
            );

            listaNotificacoes.add(
                    nenhumaNotificacao
            );

        } else {

            for (int i = notificacoes.size() - 1;
                 i >= 0;
                 i--) {

                listaNotificacoes.add(
                        criarNotificacao(
                                notificacoes.get(i)
                        )
                );

                listaNotificacoes.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }

        listaNotificacoes.revalidate();
        listaNotificacoes.repaint();
    }

    private static JPanel criarNotificacao(
            String texto) {

        JPanel painel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        painel.setBackground(
                TemaMoneyShield.MARINHO_CLARO
        );

        painel.setOpaque(
                true
        );

        painel.setMaximumSize(
                new Dimension(
                        245,
                        76
                )
        );

        painel.setPreferredSize(
                new Dimension(
                        245,
                        76
                )
        );

        painel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                4,
                                0,
                                0,
                                TemaMoneyShield.ROSA
                        ),
                        BorderFactory.createEmptyBorder(
                                9,
                                10,
                                9,
                                10
                        )
                )
        );

        JLabel marcador =
                new JLabel(
                        "•",
                        SwingConstants.CENTER
                );

        marcador.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        24
                )
        );

        marcador.setForeground(
                TemaMoneyShield.AMARELO
        );

        marcador.setPreferredSize(
                new Dimension(
                        22,
                        40
                )
        );

        JLabel label =
                new JLabel(
                        "<html><div style='width:175px;'>"
                                + texto
                                + "</div></html>"
                );

        label.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        label.setForeground(
                TemaMoneyShield.BRANCO
        );

        painel.add(
                marcador,
                BorderLayout.WEST
        );

        painel.add(
                label,
                BorderLayout.CENTER
        );

        return painel;
    }

    public static void criarTelaCadastro() {

        TelaCadastro.abrir(
                null
        );
    }

    private static class PainelLoginArredondado extends JPanel {

        private static final int ARCO = 30;

        public PainelLoginArredondado() {

            setOpaque(
                    false
            );
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    Color.WHITE
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    ARCO,
                    ARCO
            );

            g2.dispose();

            super.paintComponent(
                    g
            );
        }
    }

    private static class PainelNovidadesArredondado extends JPanel {

        private static final int ARCO = 24;

        public PainelNovidadesArredondado() {

            setOpaque(
                    false
            );
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    TemaMoneyShield.MARINHO_CLARO
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    ARCO,
                    ARCO
            );

            g2.setColor(
                    TemaMoneyShield.AMARELO
            );

            g2.fillRoundRect(
                    0,
                    0,
                    5,
                    getHeight() - 1,
                    ARCO,
                    ARCO
            );

            g2.dispose();

            super.paintComponent(
                    g
            );
        }
    }

    private static class BordaCartaoLogin extends AbstractBorder {

        private static final int ESPESSURA = 5;
        private static final int ARCO = 30;

        @Override
        public Insets getBorderInsets(
                Component c) {

            return new Insets(
                    ESPESSURA,
                    ESPESSURA,
                    ESPESSURA,
                    ESPESSURA
            );
        }

        @Override
        public Insets getBorderInsets(
                Component c,
                Insets insets) {

            insets.left =
                    ESPESSURA;

            insets.right =
                    ESPESSURA;

            insets.top =
                    ESPESSURA;

            insets.bottom =
                    ESPESSURA;

            return insets;
        }

        @Override
        public void paintBorder(
                Component c,
                Graphics g,
                int x,
                int y,
                int width,
                int height) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setStroke(
                    new BasicStroke(
                            ESPESSURA,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            Shape borda =
                    new RoundRectangle2D.Double(
                            x + ESPESSURA / 2.0,
                            y + ESPESSURA / 2.0,
                            width - ESPESSURA,
                            height - ESPESSURA,
                            ARCO,
                            ARCO
                    );

            Shape clipOriginal =
                    g2.getClip();

            g2.clipRect(
                    x,
                    y,
                    width,
                    height / 2 + ESPESSURA
            );

            g2.setColor(
                    TemaMoneyShield.AMARELO
            );

            g2.draw(
                    borda
            );

            g2.setClip(
                    clipOriginal
            );

            g2.clipRect(
                    x,
                    y + height / 2,
                    width,
                    height / 2 + ESPESSURA
            );

            g2.setColor(
                    ROXO_LOGIN
            );

            g2.draw(
                    borda
            );

            g2.dispose();
        }
    }
}