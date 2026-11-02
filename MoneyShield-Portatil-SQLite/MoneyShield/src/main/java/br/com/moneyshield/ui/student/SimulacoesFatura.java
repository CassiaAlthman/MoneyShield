package br.com.moneyshield.ui.student;

import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class SimulacoesFatura {

    public static void abrir(JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Simulador de Fatura"
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
                criarCabecalho();

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
                        24,
                        36,
                        15,
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
                        "Simulador de Fatura"
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
                        "Compare o pagamento total com o pagamento mínimo."
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
                        5
                )
        );

        painelEsquerdo.add(
                subtitulo
        );

        painelEsquerdo.add(
                Box.createVerticalStrut(
                        20
                )
        );

        JPanel cardFormulario =
                TemaMoneyShield.criarCard();

        cardFormulario.setLayout(
                new BoxLayout(
                        cardFormulario,
                        BoxLayout.Y_AXIS
                )
        );

        cardFormulario.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardFormulario.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        345
                )
        );

        JLabel labelValorFatura =
                criarLabelCampo(
                        "Valor total da fatura (R$)"
                );

        JTextField campoValorFatura =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoValorFatura
        );

        JLabel labelPagamento =
                criarLabelCampo(
                        "Forma de pagamento"
                );

        JComboBox<String> campoPagamento =
                new JComboBox<>(
                        new String[]{
                                "Pagamento Total",
                                "Pagamento Mínimo"
                        }
                );

        estilizarCombo(
                campoPagamento
        );

        JLabel labelPercentualMinimo =
                criarLabelCampo(
                        "Pagamento mínimo (%)"
                );

        JTextField campoPercentualMinimo =
                new JTextField(
                        "20"
                );

        TemaMoneyShield.estilizarCampoTexto(
                campoPercentualMinimo
        );

        JLabel labelTaxa =
                criarLabelCampo(
                        "Juros do rotativo (% ao mês)"
                );

        JTextField campoTaxa =
                new JTextField(
                        "12"
                );

        TemaMoneyShield.estilizarCampoTexto(
                campoTaxa
        );

        cardFormulario.add(
                labelValorFatura
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoValorFatura
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        12
                )
        );

        cardFormulario.add(
                labelPagamento
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoPagamento
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        12
                )
        );

        cardFormulario.add(
                labelPercentualMinimo
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoPercentualMinimo
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        12
                )
        );

        cardFormulario.add(
                labelTaxa
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoTaxa
        );

        painelEsquerdo.add(
                cardFormulario
        );

        painelEsquerdo.add(
                Box.createVerticalStrut(
                        14
                )
        );

        JPanel painelAcoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        painelAcoes.setOpaque(
                false
        );

        painelAcoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JButton botaoSimular =
                TemaMoneyShield.criarBotaoPrimario(
                        "Simular"
                );

        JButton botaoLimpar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Limpar"
                );

        painelAcoes.add(
                botaoSimular
        );

        painelAcoes.add(
                botaoLimpar
        );

        painelEsquerdo.add(
                painelAcoes
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
                        335,
                        0
                )
        );

        JLabel tituloResultado =
                new JLabel(
                        "Resultado"
                );

        tituloResultado.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
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

        estilizarAreaTexto(
                resultado
        );

        resultado.setText(
                "Informe o valor da fatura e escolha uma forma de pagamento para iniciar a simulação."
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
                        335,
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
                                        8,
                                        8,
                                        8,
                                        8
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
                        112
                )
        );

        JPanel faixaEducativa =
                new JPanel();

        faixaEducativa.setBackground(
                TemaMoneyShield.AMARELO
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
                                + "<div style='width:245px;'>"
                                + "<font face='Comic Sans MS' size='3'><b>Atenção ao crédito rotativo</b></font><br><br>"
                                + "<font face='Trebuchet MS' size='2'>"
                                + "Pagar apenas o mínimo mantém parte da dívida em aberto e pode gerar novos juros."
                                + "</font>"
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
                        "Comparar pagamentos"
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

        painelConteudo.add(
                painelEsquerdo,
                BorderLayout.CENTER
        );

        painelConteudo.add(
                painelDireito,
                BorderLayout.EAST
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

        NumberFormat formatoDinheiro =
                NumberFormat.getCurrencyInstance(
                        Locale.of(
                                "pt",
                                "BR"
                        )
                );

        final double[] dadosComparacao =
                new double[6];

        campoPagamento.addActionListener(e -> {

            String opcao =
                    (String) campoPagamento
                            .getSelectedItem();

            boolean pagamentoMinimo =
                    "Pagamento Mínimo"
                            .equals(
                                    opcao
                            );

            campoPercentualMinimo.setEnabled(
                    pagamentoMinimo
            );

            campoTaxa.setEnabled(
                    pagamentoMinimo
            );

            atualizarAparenciaCampo(
                    campoPercentualMinimo,
                    pagamentoMinimo
            );

            atualizarAparenciaCampo(
                    campoTaxa,
                    pagamentoMinimo
            );
        });

        campoPercentualMinimo.setEnabled(
                false
        );

        campoTaxa.setEnabled(
                false
        );

        atualizarAparenciaCampo(
                campoPercentualMinimo,
                false
        );

        atualizarAparenciaCampo(
                campoTaxa,
                false
        );

        botaoSimular.addActionListener(e -> {

            try {

                String valorTexto =
                        campoValorFatura
                                .getText()
                                .trim()
                                .replace(
                                        ",",
                                        "."
                                );

                double valorFatura =
                        Double.parseDouble(
                                valorTexto
                        );

                if (valorFatura <= 0) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "O valor da fatura deve ser maior que zero.",
                            "Valor inválido",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                String formaPagamento =
                        (String) campoPagamento
                                .getSelectedItem();

                double percentualMinimo =
                        Double.parseDouble(
                                campoPercentualMinimo
                                        .getText()
                                        .trim()
                                        .replace(
                                                ",",
                                                "."
                                        )
                        );

                double taxa =
                        Double.parseDouble(
                                campoTaxa
                                        .getText()
                                        .trim()
                                        .replace(
                                                ",",
                                                "."
                                        )
                        );

                if (percentualMinimo <= 0
                        || percentualMinimo >= 100) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "O pagamento mínimo deve estar entre 1% e 99%.",
                            "Percentual inválido",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                if (taxa < 0) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "A taxa de juros não pode ser negativa.",
                            "Taxa inválida",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                double pagamentoMinimo =
                        valorFatura
                                * percentualMinimo
                                / 100.0;

                double saldoRestante =
                        valorFatura
                                - pagamentoMinimo;

                double juros =
                        saldoRestante
                                * taxa
                                / 100.0;

                double dividaProximoMes =
                        saldoRestante
                                + juros;

                dadosComparacao[0] =
                        valorFatura;

                dadosComparacao[1] =
                        pagamentoMinimo;

                dadosComparacao[2] =
                        saldoRestante;

                dadosComparacao[3] =
                        juros;

                dadosComparacao[4] =
                        dividaProximoMes;

                dadosComparacao[5] =
                        percentualMinimo;

                if ("Pagamento Total".equals(
                        formaPagamento
                )) {

                    resultado.setText(
                            "FORMA ESCOLHIDA\n"
                                    + "Pagamento Total\n\n"
                                    + "Valor da fatura: "
                                    + formatoDinheiro.format(
                                    valorFatura
                            )
                                    + "\n"
                                    + "Valor pago: "
                                    + formatoDinheiro.format(
                                    valorFatura
                            )
                                    + "\n"
                                    + "Saldo restante: "
                                    + formatoDinheiro.format(
                                    0
                            )
                                    + "\n"
                                    + "Juros: "
                                    + formatoDinheiro.format(
                                    0
                            )
                                    + "\n\n"
                                    + "CONSEQUÊNCIA\n"
                                    + "A fatura foi totalmente paga. "
                                    + "Não existe saldo restante sujeito aos juros "
                                    + "do crédito rotativo."
                    );

                } else {

                    resultado.setText(
                            "FORMA ESCOLHIDA\n"
                                    + "Pagamento Mínimo\n\n"
                                    + "Valor da fatura: "
                                    + formatoDinheiro.format(
                                    valorFatura
                            )
                                    + "\n"
                                    + "Pagamento mínimo: "
                                    + formatoDinheiro.format(
                                    pagamentoMinimo
                            )
                                    + "\n"
                                    + "Saldo não pago: "
                                    + formatoDinheiro.format(
                                    saldoRestante
                            )
                                    + "\n"
                                    + "Juros do próximo período: "
                                    + formatoDinheiro.format(
                                    juros
                            )
                                    + "\n"
                                    + "Dívida projetada para o próximo mês: "
                                    + formatoDinheiro.format(
                                    dividaProximoMes
                            )
                                    + "\n\n"
                                    + "CONSEQUÊNCIA\n"
                                    + "Ao pagar somente o mínimo, uma parte da fatura "
                                    + "permanece em aberto e recebe a incidência dos "
                                    + "juros do crédito rotativo."
                    );
                }

                resultado.setCaretPosition(
                        0
                );

                int nivelAnterior =
                        GamificacaoService.getNivel();

                boolean primeiraSimulacao =
                        GamificacaoService.registrarSimulacao(
                                "Simulador de Fatura"
                        );

                if (primeiraSimulacao) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "Primeira simulação de fatura concluída!\n\n"
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

                    int novoNivel =
                            GamificacaoService.getNivel();

                    if (novoNivel > nivelAnterior) {

                        TelaRecompensa.mostrar(
                                janela,
                                novoNivel
                        );
                    }
                }

                botaoComparar.setEnabled(
                        true
                );

            } catch (NumberFormatException erro) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Preencha todos os campos com valores numéricos válidos.",
                        "Dados inválidos",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        botaoComparar.addActionListener(e -> {

            double valorFatura =
                    dadosComparacao[0];

            double pagamentoMinimo =
                    dadosComparacao[1];

            double saldoRestante =
                    dadosComparacao[2];

            double juros =
                    dadosComparacao[3];

            double dividaProximoMes =
                    dadosComparacao[4];

            double percentual =
                    dadosComparacao[5];

            JPanel painelComparacao =
                    new JPanel(
                            new BorderLayout(
                                    0,
                                    14
                            )
                    );

            painelComparacao.setBackground(
                    TemaMoneyShield.FUNDO
            );

            JLabel tituloComparacao =
                    new JLabel(
                            "Comparação das formas de pagamento"
                    );

            tituloComparacao.setFont(
                    TemaMoneyShield.FONTE_SUBTITULO
            );

            tituloComparacao.setForeground(
                    TemaMoneyShield.MARINHO
            );

            JPanel cards =
                    new JPanel(
                            new GridLayout(
                                    1,
                                    2,
                                    12,
                                    0
                            )
                    );

            cards.setOpaque(
                    false
            );

            JPanel cardTotal =
                    criarCardComparacao(
                            "Pagamento Total",
                            "Valor pago: "
                                    + formatoDinheiro.format(
                                    valorFatura
                            )
                                    + "\nSaldo restante: "
                                    + formatoDinheiro.format(
                                    0
                            )
                                    + "\nJuros: "
                                    + formatoDinheiro.format(
                                    0
                            ),
                            TemaMoneyShield.AZUL
                    );

            JPanel cardMinimo =
                    criarCardComparacao(
                            "Pagamento Mínimo ("
                                    + percentual
                                    + "%)",
                            "Valor pago: "
                                    + formatoDinheiro.format(
                                    pagamentoMinimo
                            )
                                    + "\nSaldo restante: "
                                    + formatoDinheiro.format(
                                    saldoRestante
                            )
                                    + "\nJuros: "
                                    + formatoDinheiro.format(
                                    juros
                            )
                                    + "\nDívida projetada: "
                                    + formatoDinheiro.format(
                                    dividaProximoMes
                            ),
                            TemaMoneyShield.ROSA
                    );

            cards.add(
                    cardTotal
            );

            cards.add(
                    cardMinimo
            );

            JPanel cardConclusao =
                    TemaMoneyShield.criarCard();

            cardConclusao.setLayout(
                    new BorderLayout(
                            10,
                            0
                    )
            );

            JPanel faixa =
                    new JPanel();

            faixa.setBackground(
                    TemaMoneyShield.AMARELO
            );

            faixa.setPreferredSize(
                    new Dimension(
                            6,
                            1
                    )
            );

            JLabel conclusao =
                    new JLabel(
                            "<html>"
                                    + "<div style='width:500px;'>"
                                    + "<font face='Comic Sans MS' size='3'><b>Comparação</b></font><br><br>"
                                    + "<font face='Trebuchet MS' size='2'>"
                                    + "O pagamento total encerra a dívida da fatura. "
                                    + "O pagamento mínimo reduz o valor desembolsado imediatamente, "
                                    + "mas mantém parte da dívida em aberto e gera juros."
                                    + "</font>"
                                    + "</div>"
                                    + "</html>"
                    );

            conclusao.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            conclusao.setForeground(
                    TemaMoneyShield.MARINHO
            );

            cardConclusao.add(
                    faixa,
                    BorderLayout.WEST
            );

            cardConclusao.add(
                    conclusao,
                    BorderLayout.CENTER
            );

            JPanel conteudoComparacao =
                    new JPanel();

            conteudoComparacao.setOpaque(
                    false
            );

            conteudoComparacao.setLayout(
                    new BoxLayout(
                            conteudoComparacao,
                            BoxLayout.Y_AXIS
                    )
            );

            cards.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            cardConclusao.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            conteudoComparacao.add(
                    cards
            );

            conteudoComparacao.add(
                    Box.createVerticalStrut(
                            14
                    )
            );

            conteudoComparacao.add(
                    cardConclusao
            );

            painelComparacao.add(
                    tituloComparacao,
                    BorderLayout.NORTH
            );

            painelComparacao.add(
                    conteudoComparacao,
                    BorderLayout.CENTER
            );

            painelComparacao.setPreferredSize(
                    new Dimension(
                            600,
                            330
                    )
            );

            JOptionPane.showMessageDialog(
                    janela,
                    painelComparacao,
                    "MoneyShield - Comparação",
                    JOptionPane.PLAIN_MESSAGE
            );
        });

        botaoLimpar.addActionListener(e -> {

            campoValorFatura.setText(
                    ""
            );

            campoPagamento.setSelectedIndex(
                    0
            );

            campoPercentualMinimo.setText(
                    "20"
            );

            campoTaxa.setText(
                    "12"
            );

            resultado.setText(
                    "Informe o valor da fatura e escolha uma forma de pagamento para iniciar a simulação."
            );

            botaoComparar.setEnabled(
                    false
            );

            campoValorFatura.requestFocusInWindow();
        });

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaAnterior.setVisible(
                    true
            );
        });

        janela.getRootPane().setDefaultButton(
                botaoSimular
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

    private static JPanel criarCabecalho() {

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

        JLabel descricao =
                new JLabel(
                        "Simulação de fatura"
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricao.setForeground(
                TemaMoneyShield.AZUL
        );

        descricao.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        0,
                        4,
                        0
                )
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

    private static JLabel criarLabelCampo(
            String texto) {

        JLabel label =
                new JLabel(
                        texto
                );

        label.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
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

        return label;
    }

    private static void estilizarCombo(
            JComboBox<String> combo) {

        combo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        combo.setForeground(
                TemaMoneyShield.MARINHO
        );

        combo.setBackground(
                TemaMoneyShield.BRANCO
        );

        combo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        combo.setPreferredSize(
                new Dimension(
                        285,
                        42
                )
        );

        combo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );
    }

    private static void estilizarAreaTexto(
            JTextArea area) {

        area.setEditable(
                false
        );

        area.setLineWrap(
                true
        );

        area.setWrapStyleWord(
                true
        );

        area.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        area.setForeground(
                TemaMoneyShield.MARINHO
        );

        area.setBackground(
                TemaMoneyShield.BRANCO
        );

        area.setMargin(
                new Insets(
                        10,
                        10,
                        10,
                        10
                )
        );
    }

    private static void atualizarAparenciaCampo(
            JTextField campo,
            boolean habilitado) {

        campo.setBackground(
                habilitado
                        ? TemaMoneyShield.BRANCO
                        : new Color(
                        238,
                        240,
                        245
                )
        );

        campo.setForeground(
                habilitado
                        ? TemaMoneyShield.MARINHO
                        : TemaMoneyShield.TEXTO_SECUNDARIO
        );
    }

    private static JPanel criarCardComparacao(
            String titulo,
            String texto,
            Color destaque) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        10,
                        0
                )
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

        JLabel labelTitulo =
                new JLabel(
                        titulo
                );

        labelTitulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        14
                )
        );

        labelTitulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        labelTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JTextArea areaTexto =
                new JTextArea();

        areaTexto.setEditable(
                false
        );

        areaTexto.setFocusable(
                false
        );

        areaTexto.setOpaque(
                false
        );

        areaTexto.setLineWrap(
                true
        );

        areaTexto.setWrapStyleWord(
                true
        );

        areaTexto.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        areaTexto.setForeground(
                TemaMoneyShield.MARINHO
        );

        areaTexto.setText(
                texto
        );

        areaTexto.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        conteudo.add(
                labelTitulo
        );

        conteudo.add(
                Box.createVerticalStrut(
                        10
                )
        );

        conteudo.add(
                areaTexto
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                conteudo,
                BorderLayout.CENTER
        );

        return card;
    }
}