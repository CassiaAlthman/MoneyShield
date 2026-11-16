package br.com.moneyshield.ui.student;

import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class SimulacoesJuros {

    private static final NumberFormat FORMATO_DINHEIRO =
            NumberFormat.getCurrencyInstance(
                    Locale.of("pt", "BR")
            );

    public static void abrir(JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Simulador de Juros"
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
                        "Simulador de Juros"
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
                        "Veja como o dinheiro evolui ao longo do tempo."
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
                        330
                )
        );

        JLabel labelValor =
                criarLabelCampo(
                        "Valor inicial"
                );

        JTextField campoValor =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoValor
        );

        JLabel labelTaxa =
                criarLabelCampo(
                        "Taxa de juros (%)"
                );

        JTextField campoTaxa =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoTaxa
        );

        JLabel labelPeriodo =
                criarLabelCampo(
                        "Período em meses"
                );

        JTextField campoPeriodo =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoPeriodo
        );

        JLabel labelTipo =
                criarLabelCampo(
                        "Tipo de juros"
                );

        JComboBox<String> campoTipo =
                new JComboBox<>(
                        new String[]{
                                "Juros Compostos",
                                "Juros Simples"
                        }
                );

        estilizarCombo(
                campoTipo
        );

        JButton botaoCalcular =
                TemaMoneyShield.criarBotaoPrimario(
                        "Calcular"
                );

        botaoCalcular.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoCalcular.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        cardFormulario.add(
                labelValor
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoValor
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

        cardFormulario.add(
                Box.createVerticalStrut(
                        12
                )
        );

        cardFormulario.add(
                labelPeriodo
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoPeriodo
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        12
                )
        );

        cardFormulario.add(
                labelTipo
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        cardFormulario.add(
                campoTipo
        );

        cardFormulario.add(
                Box.createVerticalStrut(
                        18
                )
        );

        cardFormulario.add(
                botaoCalcular
        );

        painelEsquerdo.add(
                cardFormulario
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

        JPanel cardResultado =
                TemaMoneyShield.criarCard();

        cardResultado.setLayout(
                new BoxLayout(
                        cardResultado,
                        BoxLayout.Y_AXIS
                )
        );

        cardResultado.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardResultado.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        175
                )
        );

        JLabel labelMontanteTitulo =
                new JLabel(
                        "Montante final"
                );

        labelMontanteTitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        labelMontanteTitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        labelMontanteTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelMontante =
                new JLabel(
                        "R$ 0,00"
                );

        labelMontante.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        28
                )
        );

        labelMontante.setForeground(
                TemaMoneyShield.ROSA
        );

        labelMontante.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelJurosTitulo =
                new JLabel(
                        "Juros acumulados"
                );

        labelJurosTitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        labelJurosTitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        labelJurosTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelJuros =
                new JLabel(
                        "R$ 0,00"
                );

        labelJuros.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        labelJuros.setForeground(
                TemaMoneyShield.MARINHO
        );

        labelJuros.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardResultado.add(
                labelMontanteTitulo
        );

        cardResultado.add(
                Box.createVerticalStrut(
                        4
                )
        );

        cardResultado.add(
                labelMontante
        );

        cardResultado.add(
                Box.createVerticalStrut(
                        18
                )
        );

        cardResultado.add(
                labelJurosTitulo
        );

        cardResultado.add(
                Box.createVerticalStrut(
                        4
                )
        );

        cardResultado.add(
                labelJuros
        );

        painelDireito.add(
                cardResultado
        );

        painelDireito.add(
                Box.createVerticalStrut(
                        16
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
                        120
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
                                + "<div style='width:230px;'>"
                                + "<font face='Comic Sans MS' size='3'><b>Entenda o resultado</b></font><br><br>"
                                + "<font face='Trebuchet MS' size='2'>"
                                + "Nos juros compostos, os juros acumulados passam a gerar novos juros ao longo do tempo."
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

        JButton botaoGrafico =
                TemaMoneyShield.criarBotaoSecundario(
                        "Ver evolução"
                );

        botaoGrafico.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botaoGrafico.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        botaoGrafico.setEnabled(
                false
        );

        painelDireito.add(
                Box.createVerticalStrut(
                        16
                )
        );

        painelDireito.add(
                botaoGrafico
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

        final double[][] dadosGrafico =
                new double[1][];

        botaoCalcular.addActionListener(e -> {

            String valorTexto =
                    campoValor
                            .getText()
                            .trim()
                            .replace(",", ".");

            String taxaTexto =
                    campoTaxa
                            .getText()
                            .trim()
                            .replace(",", ".");

            String periodoTexto =
                    campoPeriodo
                            .getText()
                            .trim();

            if (valorTexto.isEmpty()
                    || taxaTexto.isEmpty()
                    || periodoTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Preencha todos os campos.",
                        "Dados incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                double valorInicial =
                        Double.parseDouble(
                                valorTexto
                        );

                double taxa =
                        Double.parseDouble(
                                taxaTexto
                        );

                int periodo =
                        Integer.parseInt(
                                periodoTexto
                        );

                if (valorInicial <= 0
                        || taxa < 0
                        || periodo <= 0) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "Informe valores válidos e maiores que zero.",
                            "Dados inválidos",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                boolean composto =
                        campoTipo
                                .getSelectedIndex()
                                == 0;

                double montante;

                double[] evolucao =
                        new double[
                                periodo + 1
                                ];

                evolucao[0] =
                        valorInicial;

                if (composto) {

                    for (int i = 1;
                         i <= periodo;
                         i++) {

                        evolucao[i] =
                                valorInicial
                                        * Math.pow(
                                        1
                                                + taxa
                                                / 100.0,
                                        i
                                );
                    }

                    montante =
                            evolucao[
                                    periodo
                                    ];

                } else {

                    for (int i = 1;
                         i <= periodo;
                         i++) {

                        evolucao[i] =
                                valorInicial
                                        * (
                                        1
                                                + (
                                                taxa
                                                        / 100.0
                                        )
                                                * i
                                );
                    }

                    montante =
                            evolucao[
                                    periodo
                                    ];
                }

                double juros =
                        montante
                                - valorInicial;

                labelMontante.setText(
                        FORMATO_DINHEIRO.format(
                                montante
                        )
                );

                labelJuros.setText(
                        FORMATO_DINHEIRO.format(
                                juros
                        )
                );

                dadosGrafico[0] =
                        evolucao;

                botaoGrafico.setEnabled(
                        true
                );

                int nivelAnterior =
                        GamificacaoService.getNivel();

                boolean primeiraRealizacao =
                        GamificacaoService
                                .registrarSimulacao(
                                        "Simulador de Juros"
                                );

                if (primeiraRealizacao) {

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

            } catch (NumberFormatException erro) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Digite apenas valores numéricos válidos.",
                        "Dados inválidos",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        botaoGrafico.addActionListener(e -> {

            if (dadosGrafico[0] == null) {

                return;
            }

            abrirGrafico(
                    janela,
                    dadosGrafico[0]
            );
        });

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaAnterior.setVisible(
                    true
            );
        });

        janela.getRootPane()
                .setDefaultButton(
                        botaoCalcular
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
                        "Calculadora de juros"
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

    private static void abrirGrafico(
            JFrame telaAnterior,
            double[] valores) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Evolução dos Juros"
                );

        janela.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        janela.setSize(
                720,
                500
        );

        janela.setLocationRelativeTo(
                telaAnterior
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

        JPanel painelTitulo =
                new JPanel();

        painelTitulo.setBackground(
                TemaMoneyShield.MARINHO
        );

        painelTitulo.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        24,
                        16,
                        24
                )
        );

        JLabel titulo =
                new JLabel(
                        "Evolução do valor ao longo do tempo"
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        titulo.setForeground(
                TemaMoneyShield.BRANCO
        );

        painelTitulo.add(
                titulo
        );

        GraficoPainel grafico =
                new GraficoPainel(
                        valores
                );

        grafico.setBackground(
                TemaMoneyShield.BRANCO
        );

        grafico.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        30,
                        30,
                        30
                )
        );

        painelPrincipal.add(
                painelTitulo,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                grafico,
                BorderLayout.CENTER
        );

        janela.setContentPane(
                painelPrincipal
        );

        janela.setVisible(
                true
        );
    }

    private static class GraficoPainel
            extends JPanel {

        private final double[] valores;

        private GraficoPainel(
                double[] valores) {

            this.valores =
                    valores;
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            super.paintComponent(
                    g
            );

            if (valores == null
                    || valores.length < 2) {

                return;
            }

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int esquerda =
                    70;

            int direita =
                    30;

            int topo =
                    35;

            int inferior =
                    55;

            int largura =
                    getWidth()
                            - esquerda
                            - direita;

            int altura =
                    getHeight()
                            - topo
                            - inferior;

            double maior =
                    valores[0];

            for (double valor : valores) {

                if (valor > maior) {

                    maior =
                            valor;
                }
            }

            g2.setColor(
                    TemaMoneyShield.BORDA
            );

            g2.drawLine(
                    esquerda,
                    topo,
                    esquerda,
                    topo + altura
            );

            g2.drawLine(
                    esquerda,
                    topo + altura,
                    esquerda + largura,
                    topo + altura
            );

            g2.setColor(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            g2.setFont(
                    TemaMoneyShield.FONTE_TEXTO_PEQUENO
            );

            g2.drawString(
                    "Valor",
                    15,
                    topo
            );

            g2.drawString(
                    "Período",
                    esquerda
                            + largura
                            - 40,
                    getHeight()
                            - 15
            );

            int[] pontosX =
                    new int[
                            valores.length
                            ];

            int[] pontosY =
                    new int[
                            valores.length
                            ];

            for (int i = 0;
                 i < valores.length;
                 i++) {

                double proporcaoX =
                        (double) i
                                / (
                                valores.length - 1
                        );

                double proporcaoY =
                        valores[i]
                                / maior;

                pontosX[i] =
                        esquerda
                                + (int) (
                                proporcaoX
                                        * largura
                        );

                pontosY[i] =
                        topo
                                + altura
                                - (int) (
                                proporcaoY
                                        * altura
                        );
            }

            g2.setStroke(
                    new BasicStroke(
                            3f
                    )
            );

            g2.setColor(
                    TemaMoneyShield.ROSA
            );

            for (int i = 0;
                 i < valores.length - 1;
                 i++) {

                g2.drawLine(
                        pontosX[i],
                        pontosY[i],
                        pontosX[i + 1],
                        pontosY[i + 1]
                );
            }

            g2.setColor(
                    TemaMoneyShield.MARINHO
            );

            for (int i = 0;
                 i < valores.length;
                 i++) {

                g2.fillOval(
                        pontosX[i] - 4,
                        pontosY[i] - 4,
                        8,
                        8
                );
            }

            g2.setColor(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            g2.drawString(
                    FORMATO_DINHEIRO.format(
                            maior
                    ),
                    5,
                    topo + 10
            );

            g2.drawString(
                    FORMATO_DINHEIRO.format(
                            valores[0]
                    ),
                    5,
                    topo + altura
            );

            g2.drawString(
                    "0",
                    esquerda - 3,
                    topo + altura + 22
            );

            g2.drawString(
                    String.valueOf(
                            valores.length - 1
                    ),
                    esquerda
                            + largura
                            - 5,
                    topo
                            + altura
                            + 22
            );

            g2.dispose();
        }
    }
}