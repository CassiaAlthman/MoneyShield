package br.com.moneyshield.ui.student;

import br.com.moneyshield.app.EducacaoFinanceiraMain;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;

public class ConfiguracoesUsuario {

    public static JPanel criarPainel(
            JFrame janelaAtual) {

        JPanel painel = new JPanel();

        painel.setOpaque(false);

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
                        175
                )
        );

        boolean telaCheiaAtiva =
                (janelaAtual.getExtendedState()
                        & JFrame.MAXIMIZED_BOTH)
                        == JFrame.MAXIMIZED_BOTH;

        JPanel cardTelaCheia =
                criarCardConfiguracao();

        cardTelaCheia.setLayout(
                new BorderLayout(
                        10,
                        0
                )
        );

        JPanel textosTelaCheia =
                new JPanel();

        textosTelaCheia.setOpaque(false);

        textosTelaCheia.setLayout(
                new BoxLayout(
                        textosTelaCheia,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel tituloTelaCheia =
                new JLabel(
                        "Tela cheia"
                );

        tituloTelaCheia.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        tituloTelaCheia.setForeground(
                TemaMoneyShield.BRANCO
        );

        tituloTelaCheia.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel descricaoTelaCheia =
                new JLabel(
                        "Maximizar a janela do MoneyShield"
                );

        descricaoTelaCheia.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricaoTelaCheia.setForeground(
                new Color(
                        190,
                        199,
                        220
                )
        );

        descricaoTelaCheia.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        textosTelaCheia.add(
                tituloTelaCheia
        );

        textosTelaCheia.add(
                Box.createVerticalStrut(
                        3
                )
        );

        textosTelaCheia.add(
                descricaoTelaCheia
        );

        JCheckBox telaCheia =
                new JCheckBox();

        telaCheia.setOpaque(false);

        telaCheia.setSelected(
                telaCheiaAtiva
        );

        telaCheia.setFocusPainted(false);

        telaCheia.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        telaCheia.setToolTipText(
                "Ativar ou desativar tela cheia"
        );

        cardTelaCheia.add(
                textosTelaCheia,
                BorderLayout.CENTER
        );

        cardTelaCheia.add(
                telaCheia,
                BorderLayout.EAST
        );

        JPanel cardSessao =
                criarCardConfiguracao();

        cardSessao.setLayout(
                new BoxLayout(
                        cardSessao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel tituloSessao =
                new JLabel(
                        "Sessão"
                );

        tituloSessao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        tituloSessao.setForeground(
                TemaMoneyShield.BRANCO
        );

        tituloSessao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel descricaoSessao =
                new JLabel(
                        "Encerrar o acesso desta conta"
                );

        descricaoSessao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricaoSessao.setForeground(
                new Color(
                        190,
                        199,
                        220
                )
        );

        descricaoSessao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JButton botaoDeslogar =
                criarBotaoDeslogar();

        botaoDeslogar.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoDeslogar.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        cardSessao.add(
                tituloSessao
        );

        cardSessao.add(
                Box.createVerticalStrut(
                        3
                )
        );

        cardSessao.add(
                descricaoSessao
        );

        cardSessao.add(
                Box.createVerticalStrut(
                        10
                )
        );

        cardSessao.add(
                botaoDeslogar
        );

        telaCheia.addActionListener(e -> {

            if (telaCheia.isSelected()) {

                ativarTelaCheia(
                        janelaAtual
                );

            } else {

                desativarTelaCheia(
                        janelaAtual
                );
            }
        });

        botaoDeslogar.addActionListener(e -> {

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janelaAtual,
                            "Deseja realmente sair da sua conta?",
                            "Deslogar",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (resposta
                    == JOptionPane.YES_OPTION) {

                janelaAtual.dispose();

                EducacaoFinanceiraMain
                        .criarTelaLogin();
            }
        });

        painel.add(
                cardTelaCheia
        );

        painel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painel.add(
                cardSessao
        );

        return painel;
    }

    private static JPanel criarCardConfiguracao() {

        JPanel card = new JPanel();

        card.setBackground(
                TemaMoneyShield.MARINHO_CLARO
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        38,
                                        51,
                                        91
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                11,
                                12,
                                11,
                                12
                        )
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        92
                )
        );

        return card;
    }

    private static JButton criarBotaoDeslogar() {

        JButton botao =
                new JButton(
                        "Deslogar"
                );

        botao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        botao.setForeground(
                TemaMoneyShield.BRANCO
        );

        botao.setBackground(
                TemaMoneyShield.ROSA
        );

        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);

        botao.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        botao.setBorder(
                BorderFactory.createEmptyBorder(
                        9,
                        14,
                        9,
                        14
                )
        );

        return botao;
    }

    private static void ativarTelaCheia(
            JFrame janela) {

        janela.setResizable(
                true
        );

        janela.setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );
    }

    private static void desativarTelaCheia(
            JFrame janela) {

        janela.setExtendedState(
                JFrame.NORMAL
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
    }
}