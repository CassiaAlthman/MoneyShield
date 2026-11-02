package br.com.moneyshield.ui.student;

import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;

public class TelaSimulacoesAluno {

    public static void abrir(JFrame telaAnterior) {

        JFrame janelaSimulacoes =
                new JFrame(
                        "MoneyShield - Simulações Financeiras"
                );

        janelaSimulacoes.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        janelaSimulacoes.setSize(
                850,
                650
        );

        janelaSimulacoes.setLocationRelativeTo(
                null
        );

        janelaSimulacoes.setResizable(
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
                        "Simulações financeiras"
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

        painelSuperior.add(
                painelMarca,
                BorderLayout.WEST
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
                        28,
                        45,
                        22,
                        45
                )
        );

        JLabel titulo =
                new JLabel(
                        "Simulações Financeiras"
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
                        "Escolha uma ferramenta para praticar decisões financeiras."
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
                        28
                )
        );

        JPanel painelOpcoes =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                16,
                                0
                        )
                );

        painelOpcoes.setOpaque(
                false
        );

        painelOpcoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelOpcoes.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        190
                )
        );

        painelOpcoes.setPreferredSize(
                new Dimension(
                        760,
                        190
                )
        );

        JButton botaoCenarios =
                TemaMoneyShield.criarBotaoCard(
                        "<font face='Comic Sans MS'>Cenários</font>",
                        "<font face='Trebuchet MS'>Escolha entre situações reais e veja as consequências das suas decisões</font>",
                        TemaMoneyShield.AZUL,
                        TemaMoneyShield.MARINHO
                );

        JButton botaoJuros =
                TemaMoneyShield.criarBotaoCard(
                        "<font face='Comic Sans MS'>Juros</font>",
                        "<font face='Trebuchet MS'>Simule juros simples e compostos e compare diferentes resultados</font>",
                        TemaMoneyShield.AMARELO,
                        TemaMoneyShield.MARINHO
                );

        JButton botaoFatura =
                TemaMoneyShield.criarBotaoCard(
                        "<font face='Comic Sans MS'>Fatura</font>",
                        "<font face='Trebuchet MS'>Entenda o impacto do pagamento total ou mínimo de uma fatura</font>",
                        TemaMoneyShield.ROSA,
                        TemaMoneyShield.BRANCO
                );

        painelOpcoes.add(
                botaoCenarios
        );

        painelOpcoes.add(
                botaoJuros
        );

        painelOpcoes.add(
                botaoFatura
        );

        painelConteudo.add(
                painelOpcoes
        );

        painelConteudo.add(
                Box.createVerticalStrut(
                        28
                )
        );

        JPanel cardInformacao =
                TemaMoneyShield.criarCard();

        cardInformacao.setLayout(
                new BorderLayout(
                        14,
                        0
                )
        );

        cardInformacao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardInformacao.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        90
                )
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                TemaMoneyShield.MALVA
        );

        faixa.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JPanel painelTextoInformacao =
                new JPanel();

        painelTextoInformacao.setOpaque(
                false
        );

        painelTextoInformacao.setLayout(
                new BoxLayout(
                        painelTextoInformacao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel tituloInformacao =
                new JLabel(
                        "Pratique sem risco."
                );

        tituloInformacao.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        14
                )
        );

        tituloInformacao.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloInformacao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel textoInformacao =
                new JLabel(
                        "As simulações ajudam você a testar escolhas financeiras antes de aplicá-las na vida real."
                );

        textoInformacao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        textoInformacao.setForeground(
                TemaMoneyShield.MARINHO
        );

        textoInformacao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelTextoInformacao.add(
                tituloInformacao
        );

        painelTextoInformacao.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelTextoInformacao.add(
                textoInformacao
        );

        cardInformacao.add(
                faixa,
                BorderLayout.WEST
        );

        cardInformacao.add(
                painelTextoInformacao,
                BorderLayout.CENTER
        );

        painelConteudo.add(
                cardInformacao
        );

        painelConteudo.add(
                Box.createVerticalGlue()
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

        painelInferior.add(
                botaoVoltar
        );

        botaoCenarios.addActionListener(e -> {

            janelaSimulacoes.setVisible(
                    false
            );

            CenariosFinanceiros.abrir(
                    janelaSimulacoes
            );
        });

        botaoJuros.addActionListener(e -> {

            janelaSimulacoes.setVisible(
                    false
            );

            SimulacoesJuros.abrir(
                    janelaSimulacoes
            );
        });

        botaoFatura.addActionListener(e -> {

            janelaSimulacoes.setVisible(
                    false
            );

            SimulacoesFatura.abrir(
                    janelaSimulacoes
            );
        });

        botaoVoltar.addActionListener(e -> {

            janelaSimulacoes.dispose();

            telaAnterior.setVisible(
                    true
            );
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

        janelaSimulacoes.setContentPane(
                painelPrincipal
        );

        janelaSimulacoes.setVisible(
                true
        );
    }
}