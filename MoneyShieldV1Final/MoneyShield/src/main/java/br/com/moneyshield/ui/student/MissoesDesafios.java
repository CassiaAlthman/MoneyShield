package br.com.moneyshield.ui.student;

import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.dao.MissaoAlunoDAO;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MissoesDesafios {

    private static final MissaoAlunoDAO missaoDAO =
            new MissaoAlunoDAO();

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy"
            );

    public static void abrir(JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Missões"
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

        JPanel painelPrincipal =
                new JPanel(
                        new BorderLayout()
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
                        78
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
                new JLabel(
                        "MoneyShield"
                );

        logo.setFont(
                TemaMoneyShield.FONTE_LOGO
        );

        logo.setForeground(
                TemaMoneyShield.BRANCO
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel descricaoMarca =
                new JLabel(
                        "Missões e progresso"
                );

        descricaoMarca.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricaoMarca.setForeground(
                TemaMoneyShield.AZUL
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

        painelSuperior.add(
                painelMarca,
                BorderLayout.WEST
        );

        CardLayout cardLayout =
                new CardLayout();

        JPanel painelPaginas =
                new JPanel(
                        cardLayout
                );

        painelPaginas.setBackground(
                TemaMoneyShield.FUNDO
        );

        JPanel paginaMissoes =
                criarPaginaMissoes(
                        cardLayout,
                        painelPaginas
                );

        JPanel paginaProgresso =
                criarPaginaProgresso(
                        cardLayout,
                        painelPaginas
                );

        painelPaginas.add(
                paginaMissoes,
                "MISSOES"
        );

        painelPaginas.add(
                paginaProgresso,
                "PROGRESSO"
        );

        JPanel painelInferior =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                28,
                                7
                        )
                );

        painelInferior.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelInferior.setPreferredSize(
                new Dimension(
                        850,
                        50
                )
        );

        JButton botaoVoltar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Voltar"
                );

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaAnterior.setVisible(
                    true
            );
        });

        painelInferior.add(
                botaoVoltar
        );

        painelPrincipal.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                painelPaginas,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                painelInferior,
                BorderLayout.SOUTH
        );

        janela.setContentPane(
                painelPrincipal
        );

        janela.setVisible(
                true
        );
    }

    private static JPanel criarPaginaMissoes(
            CardLayout cardLayout,
            JPanel painelPaginas) {

        JPanel pagina =
                new JPanel(
                        new BorderLayout()
                );

        pagina.setBackground(
                TemaMoneyShield.FUNDO
        );

        pagina.setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        36,
                        10,
                        36
                )
        );

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        painelSuperior.setOpaque(
                false
        );

        JPanel painelTitulo =
                new JPanel();

        painelTitulo.setOpaque(
                false
        );

        painelTitulo.setLayout(
                new BoxLayout(
                        painelTitulo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Quadro de Missões"
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
                        "Complete desafios, acumule XP e avance de nível."
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

        painelTitulo.add(
                titulo
        );

        painelTitulo.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelTitulo.add(
                subtitulo
        );

        JButton botaoProgresso =
                TemaMoneyShield.criarBotaoSecundario(
                        "Ver progresso →"
                );

        botaoProgresso.setPreferredSize(
                new Dimension(
                        145,
                        40
                )
        );

        botaoProgresso.addActionListener(e -> {

            JPanel novaPagina =
                    criarPaginaProgresso(
                            cardLayout,
                            painelPaginas
                    );

            painelPaginas.add(
                    novaPagina,
                    "PROGRESSO_ATUAL"
            );

            cardLayout.show(
                    painelPaginas,
                    "PROGRESSO_ATUAL"
            );
        });

        painelSuperior.add(
                painelTitulo,
                BorderLayout.WEST
        );

        painelSuperior.add(
                botaoProgresso,
                BorderLayout.EAST
        );

        JPanel painelMissoes =
                new JPanel();

        painelMissoes.setOpaque(
                false
        );

        painelMissoes.setLayout(
                new BoxLayout(
                        painelMissoes,
                        BoxLayout.Y_AXIS
                )
        );

        painelMissoes.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        0,
                        10,
                        0
                )
        );

        try {

            ArrayList<MissaoAlunoDAO.DadosProgressoMissao> missoes =
                    missaoDAO.buscarProgressoMissoesAtual();

            GamificacaoService.carregarProgressoDoBanco();

            for (MissaoAlunoDAO.DadosProgressoMissao missao
                    : missoes) {

                adicionarMissao(
                        painelMissoes,
                        missao
                );
            }

        } catch (SQLException erro) {

            JPanel cardErro =
                    TemaMoneyShield.criarCard();

            cardErro.setLayout(
                    new BorderLayout()
            );

            cardErro.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            JLabel mensagem =
                    new JLabel(
                            "Não foi possível carregar o progresso das missões."
                    );

            mensagem.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            mensagem.setForeground(
                    TemaMoneyShield.ROSA
            );

            cardErro.add(
                    mensagem,
                    BorderLayout.CENTER
            );

            painelMissoes.add(
                    cardErro
            );
        }

        JScrollPane scroll =
                new JScrollPane(
                        painelMissoes
                );

        scroll.setBorder(
                null
        );

        scroll.setOpaque(
                false
        );

        scroll.getViewport().setOpaque(
                false
        );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        pagina.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        pagina.add(
                scroll,
                BorderLayout.CENTER
        );

        return pagina;
    }

    private static JPanel criarPaginaProgresso(
            CardLayout cardLayout,
            JPanel painelPaginas) {

        JPanel pagina =
                new JPanel(
                        new BorderLayout()
                );

        pagina.setBackground(
                TemaMoneyShield.FUNDO
        );

        pagina.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        36,
                        10,
                        36
                )
        );

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        painelSuperior.setOpaque(
                false
        );

        JButton botaoVoltarMissoes =
                TemaMoneyShield.criarBotaoSecundario(
                        "← Missões"
                );

        botaoVoltarMissoes.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        botaoVoltarMissoes.addActionListener(e -> {

            cardLayout.show(
                    painelPaginas,
                    "MISSOES"
            );
        });

        JPanel painelTitulo =
                new JPanel();

        painelTitulo.setOpaque(
                false
        );

        painelTitulo.setLayout(
                new BoxLayout(
                        painelTitulo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Seu Progresso"
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
                        "Acompanhe seu nível, conquistas e recompensas."
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

        painelTitulo.add(
                titulo
        );

        painelTitulo.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelTitulo.add(
                subtitulo
        );

        painelSuperior.add(
                botaoVoltarMissoes,
                BorderLayout.WEST
        );

        painelSuperior.add(
                painelTitulo,
                BorderLayout.CENTER
        );

        JPanel conteudo =
                new JPanel();

        conteudo.setOpaque(
                false
        );

        conteudo.setLayout(
                new BoxLayout(
                        conteudo,
                        BoxLayout.Y_AXIS
                )
        );

        conteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        0,
                        20,
                        0
                )
        );

        JPanel cardNivel =
                TemaMoneyShield.criarCard();

        cardNivel.setLayout(
                new BorderLayout(
                        12,
                        12
                )
        );

        cardNivel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardNivel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );

        JPanel painelNivelTexto =
                new JPanel();

        painelNivelTexto.setOpaque(
                false
        );

        painelNivelTexto.setLayout(
                new BoxLayout(
                        painelNivelTexto,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel nivel =
                new JLabel(
                        "Nível "
                                + GamificacaoService.getNivel()
                                + " - "
                                + GamificacaoService.getNomeNivel()
                );

        nivel.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        nivel.setForeground(
                TemaMoneyShield.MARINHO
        );

        nivel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel xp =
                new JLabel(
                        GamificacaoService.getPontos()
                                + " XP acumulados"
                );

        xp.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        xp.setForeground(
                TemaMoneyShield.ROSA
        );

        xp.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelNivelTexto.add(
                nivel
        );

        painelNivelTexto.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelNivelTexto.add(
                xp
        );

        JProgressBar barraXp =
                new JProgressBar(
                        0,
                        100
                );

        TemaMoneyShield.estilizarBarraProgresso(
                barraXp
        );

        barraXp.setValue(
                GamificacaoService.getProgressoNivel()
        );

        if (GamificacaoService.getNivel() < 4) {

            barraXp.setString(
                    GamificacaoService.getXpDentroNivel()
                            + " / "
                            + GamificacaoService.getXpNecessarioNivel()
                            + " XP para o próximo nível"
            );

        } else {

            barraXp.setString(
                    "Nível máximo alcançado"
            );
        }

        barraXp.setPreferredSize(
                new Dimension(
                        650,
                        24
                )
        );

        cardNivel.add(
                painelNivelTexto,
                BorderLayout.NORTH
        );

        cardNivel.add(
                barraXp,
                BorderLayout.CENTER
        );

        conteudo.add(
                cardNivel
        );

        conteudo.add(
                Box.createVerticalStrut(
                        18
                )
        );

        JPanel painelEstatisticas =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                14,
                                0
                        )
                );

        painelEstatisticas.setOpaque(
                false
        );

        painelEstatisticas.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelEstatisticas.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        95
                )
        );

        painelEstatisticas.add(
                criarEstatistica(
                        "Missões",
                        GamificacaoService.getMissoesConcluidas(),
                        TemaMoneyShield.ROSA
                )
        );

        painelEstatisticas.add(
                criarEstatistica(
                        "Trilhas",
                        GamificacaoService.getTrilhasConcluidas(),
                        TemaMoneyShield.AZUL
                )
        );

        painelEstatisticas.add(
                criarEstatistica(
                        "Simulações",
                        GamificacaoService.getSimulacoesRealizadas(),
                        TemaMoneyShield.AMARELO
                )
        );

        conteudo.add(
                painelEstatisticas
        );

        conteudo.add(
                Box.createVerticalStrut(
                        24
                )
        );

        JLabel tituloConquistas =
                criarTituloSecao(
                        "Conquistas"
                );

        conteudo.add(
                tituloConquistas
        );

        conteudo.add(
                Box.createVerticalStrut(
                        10
                )
        );

        JPanel painelConquistas =
                TemaMoneyShield.criarCard();

        painelConquistas.setLayout(
                new BoxLayout(
                        painelConquistas,
                        BoxLayout.Y_AXIS
                )
        );

        painelConquistas.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        ArrayList<String> conquistas =
                GamificacaoService.getConquistas();

        if (conquistas.isEmpty()) {

            JLabel nenhuma =
                    new JLabel(
                            "Nenhuma conquista desbloqueada ainda."
                    );

            nenhuma.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            nenhuma.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            nenhuma.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painelConquistas.add(
                    nenhuma
            );

        } else {

            for (String conquista : conquistas) {

                JLabel item =
                        new JLabel(
                                "✓ " + conquista
                        );

                item.setFont(
                        TemaMoneyShield.FONTE_TEXTO
                );

                item.setForeground(
                        TemaMoneyShield.MARINHO
                );

                item.setAlignmentX(
                        Component.LEFT_ALIGNMENT
                );

                painelConquistas.add(
                        item
                );

                painelConquistas.add(
                        Box.createVerticalStrut(
                                7
                        )
                );
            }
        }

        conteudo.add(
                painelConquistas
        );

        conteudo.add(
                Box.createVerticalStrut(
                        24
                )
        );

        JLabel tituloRecompensas =
                criarTituloSecao(
                        "Recompensas de Nível"
                );

        conteudo.add(
                tituloRecompensas
        );

        conteudo.add(
                Box.createVerticalStrut(
                        10
                )
        );

        JPanel painelRecompensas =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                14,
                                0
                        )
                );

        painelRecompensas.setOpaque(
                false
        );

        painelRecompensas.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelRecompensas.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        190
                )
        );

        adicionarRecompensa(
                painelRecompensas,
                2
        );

        adicionarRecompensa(
                painelRecompensas,
                3
        );

        adicionarRecompensa(
                painelRecompensas,
                4
        );

        conteudo.add(
                painelRecompensas
        );

        JScrollPane scroll =
                new JScrollPane(
                        conteudo
                );

        scroll.setBorder(
                null
        );

        scroll.setOpaque(
                false
        );

        scroll.getViewport().setOpaque(
                false
        );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        pagina.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        pagina.add(
                scroll,
                BorderLayout.CENTER
        );

        return pagina;
    }

    private static JPanel criarEstatistica(
            String nome,
            int quantidade,
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

        JPanel painelTexto =
                new JPanel();

        painelTexto.setOpaque(
                false
        );

        painelTexto.setLayout(
                new BoxLayout(
                        painelTexto,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel numero =
                new JLabel(
                        String.valueOf(
                                quantidade
                        )
                );

        numero.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        numero.setForeground(
                TemaMoneyShield.MARINHO
        );

        numero.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel texto =
                new JLabel(
                        nome
                );

        texto.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        texto.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        texto.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelTexto.add(
                numero
        );

        painelTexto.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelTexto.add(
                texto
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                painelTexto,
                BorderLayout.CENTER
        );

        return card;
    }

    private static void adicionarRecompensa(
            JPanel painel,
            int nivel) {

        JPanel recompensa =
                TemaMoneyShield.criarCard();

        recompensa.setLayout(
                new BoxLayout(
                        recompensa,
                        BoxLayout.Y_AXIS
                )
        );

        recompensa.setPreferredSize(
                new Dimension(
                        210,
                        180
                )
        );

        boolean desbloqueada =
                GamificacaoService.getNivel()
                        >= nivel;

        String caminho =
                GamificacaoService
                        .getCaminhoRecompensaNivel(
                                nivel
                        );

        JLabel labelNivel =
                new JLabel(
                        "NÍVEL " + nivel
                );

        labelNivel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        labelNivel.setForeground(
                desbloqueada
                        ? TemaMoneyShield.ROSA
                        : TemaMoneyShield.TEXTO_SECUNDARIO
        );

        labelNivel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel imagem =
                new JLabel();

        imagem.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        imagem.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        imagem.setPreferredSize(
                new Dimension(
                        100,
                        100
                )
        );

        if (desbloqueada
                && caminho != null) {

            ImageIcon original =
                    new ImageIcon(
                            caminho
                    );

            if (original.getIconWidth() > 0) {

                Image escalada =
                        original
                                .getImage()
                                .getScaledInstance(
                                        90,
                                        90,
                                        Image.SCALE_SMOOTH
                                );

                imagem.setIcon(
                        new ImageIcon(
                                escalada
                        )
                );

            } else {

                imagem.setText(
                        "Desbloqueada"
                );

                imagem.setFont(
                        TemaMoneyShield.FONTE_TEXTO_PEQUENO
                );

                imagem.setForeground(
                        TemaMoneyShield.ROSA
                );
            }

        } else {

            imagem.setText(
                    "🔒"
            );

            imagem.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            34
                    )
            );

            imagem.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );
        }

        String nome =
                GamificacaoService
                        .getNomeRecompensaNivel(
                                nivel
                        );

        JLabel texto =
                new JLabel(
                        "<html>"
                                + "<div style='text-align:center; width:150px;'>"
                                + nome
                                + "</div>"
                                + "</html>"
                );

        texto.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        texto.setForeground(
                TemaMoneyShield.MARINHO
        );

        texto.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        recompensa.add(
                labelNivel
        );

        recompensa.add(
                Box.createVerticalStrut(
                        8
                )
        );

        recompensa.add(
                imagem
        );

        recompensa.add(
                Box.createVerticalStrut(
                        8
                )
        );

        recompensa.add(
                texto
        );

        painel.add(
                recompensa
        );
    }

    private static void adicionarMissao(
            JPanel painel,
            MissaoAlunoDAO.DadosProgressoMissao missao) {

        JPanel painelMissao =
                TemaMoneyShield.criarCard();

        painelMissao.setLayout(
                new BorderLayout(
                        18,
                        10
                )
        );

        painelMissao.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        146
                )
        );

        painelMissao.setPreferredSize(
                new Dimension(
                        720,
                        146
                )
        );

        painelMissao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                missao.isConcluida()
                        ? TemaMoneyShield.MALVA
                        : TemaMoneyShield.ROSA
        );

        faixa.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JPanel painelTexto =
                new JPanel();

        painelTexto.setOpaque(
                false
        );

        painelTexto.setLayout(
                new BoxLayout(
                        painelTexto,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel labelTitulo =
                new JLabel(
                        missao.getNome()
                );

        labelTitulo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        labelTitulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        labelTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelDescricao =
                new JLabel(
                        "<html>"
                                + "<div style='width:470px;'>"
                                + missao.getDescricao()
                                + "</div>"
                                + "</html>"
                );

        labelDescricao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        labelDescricao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        labelDescricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelProgresso =
                new JLabel(
                        textoProgresso(
                                missao
                        )
                );

        labelProgresso.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        labelProgresso.setForeground(
                missao.isConcluida()
                        ? TemaMoneyShield.MALVA
                        : TemaMoneyShield.ROSA
        );

        labelProgresso.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelTexto.add(
                labelTitulo
        );

        painelTexto.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelTexto.add(
                labelDescricao
        );

        painelTexto.add(
                Box.createVerticalStrut(
                        7
                )
        );

        painelTexto.add(
                labelProgresso
        );

        JButton botaoAcao;

        if (missao.isConcluida()) {

            botaoAcao =
                    TemaMoneyShield.criarBotaoSecundario(
                            "Concluída"
                    );

            botaoAcao.setEnabled(
                    false
            );

        } else {

            String textoBotao =
                    textoBotaoMissao(
                            missao.getTipo()
                    );

            botaoAcao =
                    TemaMoneyShield.criarBotaoPrimario(
                            textoBotao
                    );

            if ("ADMINISTRATIVA".equals(
                    missao.getTipo()
            )
                    || missao.getTipo() == null) {

                botaoAcao.setEnabled(
                        false
                );
            }
        }

        botaoAcao.setPreferredSize(
                new Dimension(
                        150,
                        40
                )
        );

        botaoAcao.addActionListener(e -> {

            int nivelAnterior =
                    GamificacaoService.getNivel();

            boolean concluidaAntes =
                    missao.isConcluida();

            try {

                executarAtividadeMissao(
                        painel,
                        missao
                );

                GamificacaoService
                        .carregarProgressoDoBanco();

                MissaoAlunoDAO.DadosProgressoMissao atualizada =
                        missaoDAO
                                .buscarProgressoMissaoAtual(
                                        missao.getNome()
                                );

                if (atualizada == null) {

                    JOptionPane.showMessageDialog(
                            painel,
                            "Não foi possível atualizar o progresso da missão.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                labelProgresso.setText(
                        textoProgresso(
                                atualizada
                        )
                );

                boolean concluiuAgora =
                        !concluidaAntes
                                && atualizada.isConcluida();

                if (atualizada.isConcluida()) {

                    botaoAcao.setText(
                            "Concluída"
                    );

                    botaoAcao.setEnabled(
                            false
                    );

                    labelProgresso.setForeground(
                            TemaMoneyShield.MALVA
                    );

                    faixa.setBackground(
                            TemaMoneyShield.MALVA
                    );
                }

                if (concluiuAgora) {

                    JOptionPane.showMessageDialog(
                            painel,
                            "Missão concluída!\n\n"
                                    + "+"
                                    + atualizada.getXp()
                                    + " XP\n"
                                    + "Total: "
                                    + GamificacaoService.getPontos()
                                    + " XP\n"
                                    + "Nível atual: "
                                    + GamificacaoService.getNivel()
                                    + " - "
                                    + GamificacaoService.getNomeNivel(),
                            "Missão concluída",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    int novoNivel =
                            GamificacaoService.getNivel();

                    if (novoNivel
                            > nivelAnterior) {

                        TelaRecompensa.mostrar(
                                painel,
                                novoNivel
                        );
                    }

                } else if (!atualizada.isConcluida()) {

                    JOptionPane.showMessageDialog(
                            painel,
                            "Progresso atualizado: "
                                    + atualizada.getProgresso()
                                    + " de "
                                    + metaMissao(
                                    atualizada
                            )
                                    + ".",
                            "Progresso da missão",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (SQLException
                     | IllegalArgumentException erro) {

                JOptionPane.showMessageDialog(
                        painel,
                        erro.getMessage(),
                        "Não foi possível atualizar a missão",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        JPanel painelAcao =
                new JPanel(
                        new GridBagLayout()
                );

        painelAcao.setOpaque(
                false
        );

        painelAcao.add(
                botaoAcao
        );

        JPanel conteudo =
                new JPanel(
                        new BorderLayout(
                                14,
                                0
                        )
                );

        conteudo.setOpaque(
                false
        );

        conteudo.add(
                painelTexto,
                BorderLayout.CENTER
        );

        conteudo.add(
                painelAcao,
                BorderLayout.EAST
        );

        painelMissao.add(
                faixa,
                BorderLayout.WEST
        );

        painelMissao.add(
                conteudo,
                BorderLayout.CENTER
        );

        painel.add(
                painelMissao
        );

        painel.add(
                Box.createVerticalStrut(
                        12
                )
        );
    }

    private static void executarAtividadeMissao(
            Component pai,
            MissaoAlunoDAO.DadosProgressoMissao missao)
            throws SQLException {

        String tipo =
                missao.getTipo();

        if ("REGISTRO_GASTOS".equals(
                tipo
        )) {

            registrarGasto(
                    pai
            );

            return;
        }

        if ("PLANEJAMENTO".equals(
                tipo
        )) {

            registrarPlanejamento(
                    pai
            );

            return;
        }

        if ("CONTEUDOS".equals(
                tipo
        )
                || "SIMULACAO_FATURA".equals(
                tipo
        )) {

            missaoDAO
                    .sincronizarMissoesAtual();

            return;
        }

        if ("RESERVA".equals(
                tipo
        )) {

            registrarReserva(
                    pai
            );

            return;
        }

        throw new IllegalArgumentException(
                "Esta missão ainda não possui uma regra automática configurada."
        );
    }

    private static void registrarGasto(
            Component pai)
            throws SQLException {

        JTextField campoData =
                new JTextField(
                        LocalDate.now()
                                .format(
                                        FORMATO_DATA
                                )
                );

        TemaMoneyShield.estilizarCampoTexto(
                campoData
        );

        campoData.setEditable(
                false
        );

        campoData.setFocusable(
                false
        );

        JTextField campoValor =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoValor
        );

        JPanel formulario =
                criarFormularioMissao(
                        "Data do registro (dd/MM/aaaa)",
                        campoData,
                        "Total gasto no dia (R$)",
                        campoValor
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        pai,
                        formulario,
                        "Registrar gastos do dia",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        BigDecimal valor =
                decimal(
                        campoValor.getText()
                );

        missaoDAO.registrarGastoDiaAtual(
                LocalDate.now(),
                valor
        );
    }

    private static void registrarPlanejamento(
            Component pai)
            throws SQLException {

        JTextField renda =
                new JTextField();

        JTextField necessidades =
                new JTextField();

        JTextField desejos =
                new JTextField();

        JTextField planejamento =
                new JTextField();

        JTextField[] campos = {
                renda,
                necessidades,
                desejos,
                planejamento
        };

        for (JTextField campo
                : campos) {

            TemaMoneyShield.estilizarCampoTexto(
                    campo
            );
        }

        JPanel formulario =
                new JPanel();

        formulario.setBackground(
                TemaMoneyShield.BRANCO
        );

        formulario.setLayout(
                new BoxLayout(
                        formulario,
                        BoxLayout.Y_AXIS
                )
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        adicionarCampoFormulario(
                formulario,
                "Renda ou mesada (R$)",
                renda
        );

        adicionarCampoFormulario(
                formulario,
                "Necessidades - aproximadamente 50% (R$)",
                necessidades
        );

        adicionarCampoFormulario(
                formulario,
                "Desejos - aproximadamente 30% (R$)",
                desejos
        );

        adicionarCampoFormulario(
                formulario,
                "Planejamento/poupança - aproximadamente 20% (R$)",
                planejamento
        );

        int resposta =
                JOptionPane.showConfirmDialog(
                        pai,
                        formulario,
                        "Desafio 50/30/20",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        missaoDAO.registrarPlanejamento503020Atual(
                decimal(
                        renda.getText()
                ),
                decimal(
                        necessidades.getText()
                ),
                decimal(
                        desejos.getText()
                ),
                decimal(
                        planejamento.getText()
                )
        );
    }

    private static void registrarReserva(
            Component pai)
            throws SQLException {

        JTextField custoMensal =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                custoMensal
        );

        JComboBox<Integer> meses =
                new JComboBox<>(
                        new Integer[]{
                                3,
                                4,
                                5,
                                6
                        }
                );

        meses.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        meses.setBackground(
                TemaMoneyShield.BRANCO
        );

        meses.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        meses.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel formulario =
                new JPanel();

        formulario.setBackground(
                TemaMoneyShield.BRANCO
        );

        formulario.setLayout(
                new BoxLayout(
                        formulario,
                        BoxLayout.Y_AXIS
                )
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        adicionarCampoFormulario(
                formulario,
                "Custo mensal essencial (R$)",
                custoMensal
        );

        JLabel labelMeses =
                new JLabel(
                        "Meses de cobertura"
                );

        labelMeses.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        labelMeses.setForeground(
                TemaMoneyShield.MARINHO
        );

        labelMeses.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        formulario.add(
                labelMeses
        );

        formulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        formulario.add(
                meses
        );

        int resposta =
                JOptionPane.showConfirmDialog(
                        pai,
                        formulario,
                        "Calcular reserva de emergência",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        Integer quantidadeMeses =
                (Integer) meses
                        .getSelectedItem();

        BigDecimal total =
                missaoDAO.registrarReservaAtual(
                        decimal(
                                custoMensal.getText()
                        ),
                        quantidadeMeses == null
                                ? 3
                                : quantidadeMeses
                );

        JOptionPane.showMessageDialog(
                pai,
                "Reserva recomendada: R$ "
                        + total.toPlainString(),
                "Reserva de emergência",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static JPanel criarFormularioMissao(
            String label1,
            JTextField campo1,
            String label2,
            JTextField campo2) {

        JPanel formulario =
                new JPanel();

        formulario.setBackground(
                TemaMoneyShield.BRANCO
        );

        formulario.setLayout(
                new BoxLayout(
                        formulario,
                        BoxLayout.Y_AXIS
                )
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        adicionarCampoFormulario(
                formulario,
                label1,
                campo1
        );

        adicionarCampoFormulario(
                formulario,
                label2,
                campo2
        );

        return formulario;
    }

    private static void adicionarCampoFormulario(
            JPanel painel,
            String texto,
            JComponent campo) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                TemaMoneyShield.MARINHO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painel.add(
                label
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                campo
        );

        painel.add(
                Box.createVerticalStrut(
                        12
                )
        );
    }

    private static BigDecimal decimal(
            String texto) {

        if (texto == null
                || texto.isBlank()) {

            throw new IllegalArgumentException(
                    "Preencha todos os valores."
            );
        }

        try {

            String valor =
                    texto.trim();

            if (valor.contains(",")) {

                valor =
                        valor.replace(
                                ".",
                                ""
                        ).replace(
                                ",",
                                "."
                        );
            }

            return new BigDecimal(
                    valor
            );

        } catch (NumberFormatException erro) {

            throw new IllegalArgumentException(
                    "Informe valores numéricos válidos."
            );
        }
    }

    private static String textoProgresso(
            MissaoAlunoDAO.DadosProgressoMissao missao) {

        if (missao.isConcluida()) {

            return "Concluída • +"
                    + missao.getXp()
                    + " XP";
        }

        return "Progresso: "
                + missao.getProgresso()
                + " / "
                + metaMissao(
                missao
        )
                + " • +"
                + missao.getXp()
                + " XP";
    }

    private static int metaMissao(
            MissaoAlunoDAO.DadosProgressoMissao missao) {

        return missao.getMeta() == null
                ? 1
                : missao.getMeta();
    }

    private static String textoBotaoMissao(
            String tipo) {

        if ("REGISTRO_GASTOS".equals(
                tipo
        )) {
            return "Registrar dia";
        }

        if ("PLANEJAMENTO".equals(
                tipo
        )) {
            return "Fazer 50/30/20";
        }

        if ("CONTEUDOS".equals(
                tipo
        )
                || "SIMULACAO_FATURA".equals(
                tipo
        )) {
            return "Verificar";
        }

        if ("RESERVA".equals(
                tipo
        )) {
            return "Calcular reserva";
        }

        return "Sem regra";
    }

    private static JLabel criarTituloSecao(
            String texto) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
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
}