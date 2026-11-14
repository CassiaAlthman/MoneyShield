package br.com.moneyshield.ui.student;

import br.com.moneyshield.model.CenarioFinanceiro;
import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.model.OpcaoCenario;
import br.com.moneyshield.repository.RepositorioCenarios;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CenariosFinanceiros {

    public static void abrir(
            JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Cenários Financeiros"
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
                criarCabecalho(
                        "Cenários financeiros"
                );

        JPanel painelConteudo =
                new JPanel();

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setLayout(
                new BoxLayout(
                        painelConteudo,
                        BoxLayout.Y_AXIS
                )
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        36,
                        18,
                        36
                )
        );

        JLabel titulo =
                new JLabel(
                        "Cenários Financeiros"
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
                        "Pratique decisões financeiras e veja as consequências de cada escolha."
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

        painelConteudo.add(
                titulo
        );

        painelConteudo.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelConteudo.add(
                subtitulo
        );

        painelConteudo.add(
                Box.createVerticalStrut(
                        20
                )
        );

        JPanel painelCenarios =
                new JPanel();

        painelCenarios.setOpaque(
                false
        );

        painelCenarios.setLayout(
                new BoxLayout(
                        painelCenarios,
                        BoxLayout.Y_AXIS
                )
        );

        ArrayList<CenarioFinanceiro> cenarios =
                RepositorioCenarios
                        .getCenariosPublicados();

        if (cenarios.isEmpty()) {

            JPanel cardVazio =
                    TemaMoneyShield.criarCard();

            cardVazio.setLayout(
                    new BorderLayout()
            );

            cardVazio.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            cardVazio.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            100
                    )
            );

            JLabel vazio =
                    new JLabel(
                            "Nenhum cenário financeiro está disponível no momento."
                    );

            vazio.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            vazio.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            cardVazio.add(
                    vazio,
                    BorderLayout.CENTER
            );

            painelCenarios.add(
                    cardVazio
            );

        } else {

            for (int i = 0;
                 i < cenarios.size();
                 i++) {

                CenarioFinanceiro cenario =
                        cenarios.get(i);

                Color corCard =
                        obterCorCenario(
                                i
                        );

                Color corTexto =
                        corCard.equals(
                                TemaMoneyShield.ROSA
                        )
                                || corCard.equals(
                                TemaMoneyShield.MALVA
                        )
                                ? TemaMoneyShield.BRANCO
                                : TemaMoneyShield.MARINHO;

                JButton botao =
                        TemaMoneyShield.criarBotaoCard(
                                (i + 1)
                                        + ". "
                                        + cenario.getTitulo(),
                                criarResumoCenario(
                                        cenario
                                ),
                                corCard,
                                corTexto
                        );

                botao.setAlignmentX(
                        Component.LEFT_ALIGNMENT
                );

                botao.setMaximumSize(
                        new Dimension(
                                Integer.MAX_VALUE,
                                82
                        )
                );

                botao.setPreferredSize(
                        new Dimension(
                                750,
                                82
                        )
                );

                botao.addActionListener(e -> {

                    janela.setVisible(
                            false
                    );

                    abrirCenario(
                            janela,
                            cenario
                    );
                });

                painelCenarios.add(
                        botao
                );

                painelCenarios.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }

        JScrollPane scroll =
                new JScrollPane(
                        painelCenarios
                );

        scroll.setBorder(
                null
        );

        scroll.setOpaque(
                false
        );

        scroll.getViewport()
                .setOpaque(
                        false
                );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelConteudo.add(
                scroll
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
                painelConteudo,
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

    private static void abrirCenario(
            JFrame telaCenarios,
            CenarioFinanceiro cenario) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - "
                                + cenario.getTitulo()
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
                criarCabecalho(
                        "Simulação de cenário"
                );

        JPanel painelConteudo =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        36,
                        14,
                        36
                )
        );

        JPanel painelEsquerdo =
                new JPanel();

        painelEsquerdo.setOpaque(
                false
        );

        painelEsquerdo.setLayout(
                new BoxLayout(
                        painelEsquerdo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        cenario.getTitulo()
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
                        "Analise a situação e escolha como você agiria."
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

        painelEsquerdo.add(
                titulo
        );

        painelEsquerdo.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelEsquerdo.add(
                subtitulo
        );

        painelEsquerdo.add(
                Box.createVerticalStrut(
                        18
                )
        );

        JPanel cardSituacao =
                TemaMoneyShield.criarCard();

        cardSituacao.setLayout(
                new BorderLayout(
                        12,
                        0
                )
        );

        cardSituacao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardSituacao.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        135
                )
        );

        JPanel faixaSituacao =
                new JPanel();

        faixaSituacao.setBackground(
                TemaMoneyShield.AMARELO
        );

        faixaSituacao.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JLabel descricao =
                new JLabel(
                        "<html>"
                                + "<div style='width:390px;'>"
                                + "<b>Situação</b><br><br>"
                                + cenario.getDescricao()
                                + "</div>"
                                + "</html>"
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        descricao.setForeground(
                TemaMoneyShield.MARINHO
        );

        cardSituacao.add(
                faixaSituacao,
                BorderLayout.WEST
        );

        cardSituacao.add(
                descricao,
                BorderLayout.CENTER
        );

        painelEsquerdo.add(
                cardSituacao
        );

        painelEsquerdo.add(
                Box.createVerticalStrut(
                        18
                )
        );

        JLabel tituloDecisoes =
                new JLabel(
                        "Qual decisão você tomaria?"
                );

        tituloDecisoes.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        tituloDecisoes.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloDecisoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelEsquerdo.add(
                tituloDecisoes
        );

        painelEsquerdo.add(
                Box.createVerticalStrut(
                        10
                )
        );

        JPanel painelDecisoes =
                new JPanel();

        painelDecisoes.setOpaque(
                false
        );

        painelDecisoes.setLayout(
                new BoxLayout(
                        painelDecisoes,
                        BoxLayout.Y_AXIS
                )
        );

        painelDecisoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel painelDireito =
                new JPanel();

        painelDireito.setOpaque(
                false
        );

        painelDireito.setLayout(
                new BoxLayout(
                        painelDireito,
                        BoxLayout.Y_AXIS
                )
        );

        painelDireito.setPreferredSize(
                new Dimension(
                        315,
                        0
                )
        );

        JLabel tituloResultado =
                new JLabel(
                        "Resultado da decisão"
                );

        tituloResultado.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        tituloResultado.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloResultado.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelDireito.add(
                tituloResultado
        );

        painelDireito.add(
                Box.createVerticalStrut(
                        12
                )
        );

        JTextArea resultado =
                new JTextArea();

        resultado.setEditable(
                false
        );

        resultado.setLineWrap(
                true
        );

        resultado.setWrapStyleWord(
                true
        );

        resultado.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        resultado.setForeground(
                TemaMoneyShield.MARINHO
        );

        resultado.setBackground(
                TemaMoneyShield.BRANCO
        );

        resultado.setMargin(
                new Insets(
                        12,
                        12,
                        12,
                        12
                )
        );

        resultado.setText(
                "Escolha uma das decisões ao lado para visualizar a consequência."
        );

        JScrollPane scrollResultado =
                new JScrollPane(
                        resultado
                );

        scrollResultado.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        scrollResultado.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        245
                )
        );

        scrollResultado.setPreferredSize(
                new Dimension(
                        315,
                        245
                )
        );

        scrollResultado.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                6,
                                0,
                                0,
                                TemaMoneyShield.ROSA
                        ),
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        TemaMoneyShield.BORDA
                                ),
                                BorderFactory.createEmptyBorder(
                                        6,
                                        6,
                                        6,
                                        6
                                )
                        )
                )
        );

        painelDireito.add(
                scrollResultado
        );

        painelDireito.add(
                Box.createVerticalStrut(
                        14
                )
        );

        JPanel cardEducativo =
                TemaMoneyShield.criarCard();

        cardEducativo.setLayout(
                new BorderLayout(
                        10,
                        0
                )
        );

        cardEducativo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardEducativo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        115
                )
        );

        JPanel faixaEducativa =
                new JPanel();

        faixaEducativa.setBackground(
                TemaMoneyShield.AZUL
        );

        faixaEducativa.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JLabel textoEducativo =
                new JLabel(
                        "<html>"
                                + "<div style='width:225px;'>"
                                + "<b>Aprenda com a escolha</b><br><br>"
                                + "Compare alternativas para entender como decisões diferentes podem afetar sua vida financeira."
                                + "</div>"
                                + "</html>"
                );

        textoEducativo.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        textoEducativo.setForeground(
                TemaMoneyShield.MARINHO
        );

        cardEducativo.add(
                faixaEducativa,
                BorderLayout.WEST
        );

        cardEducativo.add(
                textoEducativo,
                BorderLayout.CENTER
        );

        painelDireito.add(
                cardEducativo
        );

        painelDireito.add(
                Box.createVerticalStrut(
                        14
                )
        );

        JButton botaoComparar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Comparar decisões"
                );

        botaoComparar.setEnabled(
                false
        );

        botaoComparar.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoComparar.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        painelDireito.add(
                botaoComparar
        );

        ArrayList<OpcaoCenario> opcoes =
                cenario.getOpcoes();

        if (opcoes.isEmpty()) {

            JLabel vazio =
                    new JLabel(
                            "Este cenário ainda não possui opções."
                    );

            vazio.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            vazio.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            vazio.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painelDecisoes.add(
                    vazio
            );

        } else {

            for (int i = 0;
                 i < opcoes.size();
                 i++) {

                OpcaoCenario opcao =
                        opcoes.get(i);

                JButton botaoOpcao =
                        TemaMoneyShield.criarBotaoCard(
                                opcao.getTitulo(),
                                "Clique para analisar esta decisão",
                                obterCorOpcao(
                                        i
                                ),
                                obterCorTextoOpcao(
                                        i
                                )
                        );

                botaoOpcao.setAlignmentX(
                        Component.LEFT_ALIGNMENT
                );

                botaoOpcao.setMaximumSize(
                        new Dimension(
                                Integer.MAX_VALUE,
                                62
                        )
                );

                botaoOpcao.setPreferredSize(
                        new Dimension(
                                400,
                                62
                        )
                );

                botaoOpcao.addActionListener(e -> {

                    int nivelAnterior =
                            GamificacaoService.getNivel();

                    resultado.setText(
                            opcao.getConsequencia()
                    );

                    resultado.setCaretPosition(
                            0
                    );

                    botaoComparar.setEnabled(
                            true
                    );

                    boolean primeiraRealizacao =
                            GamificacaoService
                                    .registrarSimulacaoCenario(
                                            cenario.getTitulo(),
                                            opcao.getTitulo(),
                                            cenario.getDescricao(),
                                            opcao.getConsequencia()
                                    );

                    if (primeiraRealizacao) {

                        JOptionPane.showMessageDialog(
                                janela,
                                "Cenário concluído!\n\n"
                                        + "+50 XP\n"
                                        + "Total: "
                                        + GamificacaoService.getPontos()
                                        + " XP\n"
                                        + "Nível atual: "
                                        + GamificacaoService.getNivel()
                                        + " - "
                                        + GamificacaoService.getNomeNivel(),
                                "Simulação concluída",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        int nivelAtual =
                                GamificacaoService.getNivel();

                        if (nivelAtual
                                > nivelAnterior) {

                            TelaRecompensa.mostrar(
                                    janela,
                                    nivelAtual
                            );
                        }
                    }
                });

                painelDecisoes.add(
                        botaoOpcao
                );

                painelDecisoes.add(
                        Box.createVerticalStrut(
                                10
                        )
                );
            }
        }

        painelEsquerdo.add(
                painelDecisoes
        );

        botaoComparar.addActionListener(e -> {

            JPanel painelComparacao =
                    new JPanel();

            painelComparacao.setBackground(
                    TemaMoneyShield.FUNDO
            );

            painelComparacao.setLayout(
                    new BoxLayout(
                            painelComparacao,
                            BoxLayout.Y_AXIS
                    )
            );

            JLabel tituloComparacao =
                    new JLabel(
                            "Comparação entre decisões"
                    );

            tituloComparacao.setFont(
                    TemaMoneyShield.FONTE_SUBTITULO
            );

            tituloComparacao.setForeground(
                    TemaMoneyShield.MARINHO
            );

            tituloComparacao.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painelComparacao.add(
                    tituloComparacao
            );

            painelComparacao.add(
                    Box.createVerticalStrut(
                            14
                    )
            );

            for (int i = 0;
                 i < cenario.getOpcoes().size();
                 i++) {

                OpcaoCenario opcao =
                        cenario.getOpcoes()
                                .get(i);

                JPanel card =
                        TemaMoneyShield.criarCard();

                card.setLayout(
                        new BorderLayout(
                                10,
                                0
                        )
                );

                card.setAlignmentX(
                        Component.LEFT_ALIGNMENT
                );

                JPanel faixa =
                        new JPanel();

                faixa.setBackground(
                        obterCorOpcao(
                                i
                        )
                );

                faixa.setPreferredSize(
                        new Dimension(
                                6,
                                1
                        )
                );

                JTextArea texto =
                        new JTextArea();

                texto.setEditable(
                        false
                );

                texto.setFocusable(
                        false
                );

                texto.setOpaque(
                        false
                );

                texto.setLineWrap(
                        true
                );

                texto.setWrapStyleWord(
                        true
                );

                texto.setFont(
                        TemaMoneyShield.FONTE_TEXTO
                );

                texto.setForeground(
                        TemaMoneyShield.MARINHO
                );

                texto.setText(
                        opcao.getTitulo()
                                + "\n\n"
                                + opcao.getConsequencia()
                );

                card.add(
                        faixa,
                        BorderLayout.WEST
                );

                card.add(
                        texto,
                        BorderLayout.CENTER
                );

                painelComparacao.add(
                        card
                );

                painelComparacao.add(
                        Box.createVerticalStrut(
                                10
                        )
                );
            }

            JScrollPane scrollComparacao =
                    new JScrollPane(
                            painelComparacao
                    );

            scrollComparacao.setBorder(
                    null
            );

            scrollComparacao.setPreferredSize(
                    new Dimension(
                            610,
                            420
                    )
            );

            scrollComparacao.getVerticalScrollBar()
                    .setUnitIncrement(
                            16
                    );

            JOptionPane.showMessageDialog(
                    janela,
                    scrollComparacao,
                    "MoneyShield - Comparação",
                    JOptionPane.PLAIN_MESSAGE
            );
        });

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
                        "Voltar para os cenários"
                );

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaCenarios.setVisible(
                    true
            );
        });

        painelInferior.add(
                botaoVoltar
        );

        painelConteudo.add(
                painelEsquerdo,
                BorderLayout.CENTER
        );

        painelConteudo.add(
                painelDireito,
                BorderLayout.EAST
        );

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

        janela.setContentPane(
                painelPrincipal
        );

        janela.setVisible(
                true
        );
    }

    private static JPanel criarCabecalho(
            String descricaoCabecalho) {

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

        JLabel descricao =
                new JLabel(
                        descricaoCabecalho
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricao.setForeground(
                TemaMoneyShield.AZUL
        );

        descricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelMarca.add(
                logo
        );

        painelMarca.add(
                descricao
        );

        painelSuperior.add(
                painelMarca,
                BorderLayout.WEST
        );

        return painelSuperior;
    }

    private static String criarResumoCenario(
            CenarioFinanceiro cenario) {

        String descricao =
                cenario.getDescricao();

        if (descricao == null
                || descricao.isBlank()) {

            return "Pratique uma decisão financeira";
        }

        String resumo =
                descricao.replaceAll(
                        "<[^>]*>",
                        ""
                ).trim();

        if (resumo.length() > 75) {

            return resumo.substring(
                    0,
                    72
            ) + "...";
        }

        return resumo;
    }

    private static Color obterCorCenario(
            int indice) {

        int posicao =
                indice % 4;

        if (posicao == 0) {
            return TemaMoneyShield.AMARELO;
        }

        if (posicao == 1) {
            return TemaMoneyShield.AZUL;
        }

        if (posicao == 2) {
            return TemaMoneyShield.MALVA;
        }

        return TemaMoneyShield.ROSA;
    }

    private static Color obterCorOpcao(
            int indice) {

        int posicao =
                indice % 3;

        if (posicao == 0) {
            return TemaMoneyShield.AZUL;
        }

        if (posicao == 1) {
            return TemaMoneyShield.AMARELO;
        }

        return TemaMoneyShield.MALVA;
    }

    private static Color obterCorTextoOpcao(
            int indice) {

        if (indice % 3 == 2) {
            return TemaMoneyShield.BRANCO;
        }

        return TemaMoneyShield.MARINHO;
    }
}