package br.com.moneyshield.ui.student;

import br.com.moneyshield.model.AvaliacaoTrilha;
import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.repository.RepositorioAvaliacoes;
import br.com.moneyshield.repository.RepositorioResultadosAvaliacoes;
import br.com.moneyshield.repository.RepositorioTrilhas;
import br.com.moneyshield.model.ResultadoAvaliacao;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrilhasAluno {

    public static void abrir(JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Trilhas"
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
                        "Trilhas de aprendizagem",
                        850
                );

        JPanel painelConteudo =
                new JPanel(
                        new BorderLayout()
                );

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        28,
                        8,
                        28
                )
        );

        JPanel painelTopoConteudo =
                new JPanel();

        painelTopoConteudo.setOpaque(
                false
        );

        painelTopoConteudo.setLayout(
                new BoxLayout(
                        painelTopoConteudo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Trilhas de Educação Financeira"
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
                        "Escolha uma trilha para desenvolver seus conhecimentos financeiros."
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

        painelTopoConteudo.add(
                titulo
        );

        painelTopoConteudo.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelTopoConteudo.add(
                subtitulo
        );

        painelTopoConteudo.add(
                Box.createVerticalStrut(
                        12
                )
        );

        ArrayList<String> trilhas =
                RepositorioTrilhas.getTrilhas();

        int colunas =
                trilhas.size() <= 12
                        ? 3
                        : 4;

        int linhas =
                (int) Math.ceil(
                        trilhas.size()
                                / (double) colunas
                );

        JPanel painelTrilhas =
                new JPanel(
                        new GridLayout(
                                linhas,
                                colunas,
                                10,
                                10
                        )
                );

        painelTrilhas.setOpaque(
                false
        );

        for (int i = 0;
             i < trilhas.size();
             i++) {

            int numeroTrilha =
                    RepositorioTrilhas.getNumeroOrdem(
                            i
                    );

            String nomeTrilha =
                    trilhas.get(i);

            AvaliacaoTrilha avaliacao =
                    RepositorioAvaliacoes
                            .getAvaliacao(
                                    numeroTrilha
                            );

            String status;

            if (GamificacaoService.trilhaConcluida(
                    nomeTrilha
            )) {

                status =
                        "Concluída";

            } else if (
                    avaliacao
                            .possuiAvaliacaoCompleta()
            ) {

                ResultadoAvaliacao resultado =
                        RepositorioResultadosAvaliacoes
                                .getResultado(
                                        numeroTrilha
                                );

                if (!resultado.realizouPre()) {

                    status =
                            "Pré-teste disponível";

                } else if (!resultado.realizouPos()) {

                    status =
                            "Em andamento";

                } else {

                    status =
                            "Avaliação concluída";
                }

            } else {

                status =
                        "Disponível";
            }

            JButton botaoTrilha =
                    criarBotaoTrilha(
                            numeroTrilha,
                            nomeTrilha,
                            status
                    );

            botaoTrilha.addActionListener(e ->
                    abrirTrilha(
                            numeroTrilha,
                            nomeTrilha,
                            janela
                    )
            );

            painelTrilhas.add(
                    botaoTrilha
            );
        }

        painelConteudo.add(
                painelTopoConteudo,
                BorderLayout.NORTH
        );

        painelConteudo.add(
                painelTrilhas,
                BorderLayout.CENTER
        );

        JPanel painelInferior =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                28,
                                5
                        )
                );

        painelInferior.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelInferior.setPreferredSize(
                new Dimension(
                        850,
                        44
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

    private static JButton criarBotaoTrilha(
            int numero,
            String nome,
            String status) {

        JButton botao =
                new JButton(
                        "<html>"
                                + "<div style='width:205px;'>"
                                + "<font face='Trebuchet MS' size='2'>"
                                + "TRILHA "
                                + numero
                                + "</font>"
                                + "<br>"
                                + "<font face='Comic Sans MS' size='3'>"
                                + "<b>"
                                + nome
                                + "</b>"
                                + "</font>"
                                + "<br>"
                                + "<font face='Trebuchet MS' size='2'>"
                                + status
                                + "</font>"
                                + "</div>"
                                + "</html>"
                );

        botao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        botao.setForeground(
                TemaMoneyShield.MARINHO
        );

        botao.setBackground(
                TemaMoneyShield.BRANCO
        );

        botao.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        botao.setVerticalAlignment(
                SwingConstants.CENTER
        );

        botao.setFocusPainted(
                false
        );

        botao.setOpaque(
                true
        );

        botao.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        botao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                5,
                                0,
                                0,
                                TemaMoneyShield.AZUL
                        ),
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        TemaMoneyShield.BORDA
                                ),
                                BorderFactory.createEmptyBorder(
                                        6,
                                        10,
                                        6,
                                        10
                                )
                        )
                )
        );

        return botao;
    }

    private static void abrirTrilha(
            int numero,
            String nome,
            JFrame telaTrilhas) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Trilha "
                                + numero
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

        telaTrilhas.setVisible(
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
                        "Trilha "
                                + numero,
                        850
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
                        10,
                        36,
                        6,
                        36
                )
        );

        JPanel painelInferior =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                28,
                                4
                        )
                );

        painelInferior.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelInferior.setPreferredSize(
                new Dimension(
                        850,
                        44
                )
        );

        JButton botaoVoltar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Voltar para as trilhas"
                );

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaTrilhas.setVisible(
                    true
            );
        });

        painelInferior.add(
                botaoVoltar
        );

        Runnable atualizar =
                () ->
                        atualizarConteudoTrilha(
                                painelConteudo,
                                janela,
                                numero,
                                nome
                        );

        atualizar.run();

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
            String descricao,
            int largura) {

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout()
                );

        painelSuperior.setBackground(
                TemaMoneyShield.MARINHO
        );

        painelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        13,
                        28,
                        13,
                        28
                )
        );

        painelSuperior.setPreferredSize(
                new Dimension(
                        largura,
                        74
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
                TemaMoneyShield
                        .criarLogoMoneyShield();

        JLabel descricaoMarca =
                new JLabel(
                        descricao
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
                        3,
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

        return painelSuperior;
    }

    private static void atualizarConteudoTrilha(
            JPanel painel,
            JFrame janela,
            int numero,
            String nome) {

        painel.removeAll();

        JLabel numeroTrilha =
                new JLabel(
                        "TRILHA "
                                + numero
                );

        numeroTrilha.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        10
                )
        );

        numeroTrilha.setForeground(
                TemaMoneyShield.ROSA
        );

        numeroTrilha.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        "<html>"
                                + "<div style='width:700px;'>"
                                + nome
                                + "</div>"
                                + "</html>"
                );

        titulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        20
                )
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painel.add(
                numeroTrilha
        );

        painel.add(
                Box.createVerticalStrut(
                        2
                )
        );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        AvaliacaoTrilha avaliacao =
                RepositorioAvaliacoes
                        .getAvaliacao(
                                numero
                        );

        ResultadoAvaliacao resultado =
                RepositorioResultadosAvaliacoes
                        .getResultado(
                                numero
                        );

        if (!avaliacao.possuiAvaliacaoCompleta()) {

            painel.add(
                    criarCardInformacao(
                            "Trilha disponível",
                            "Esta trilha ainda não possui uma avaliação pré e pós configurada. "
                                    + "O administrador poderá adicionar essa avaliação posteriormente.",
                            TemaMoneyShield.AZUL
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            8
                    )
            );

            painel.add(
                    criarConteudoEducacional(
                            numero
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            8
                    )
            );

            painel.add(
                    criarBotaoConcluir(
                            nome,
                            janela
                    )
            );

        } else if (!resultado.realizouPre()) {

            painel.add(
                    criarCardInformacao(
                            "Pré-teste",
                            "Antes de iniciar o conteúdo desta trilha, realize uma avaliação rápida. "
                                    + "Ela será usada para comparar seu conhecimento antes e depois da aprendizagem.",
                            TemaMoneyShield.AMARELO
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            JButton botaoPre =
                    TemaMoneyShield
                            .criarBotaoPrimario(
                                    "Realizar Pré-teste"
                            );

            botaoPre.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            botaoPre.setMaximumSize(
                    new Dimension(
                            190,
                            38
                    )
            );

            botaoPre.addActionListener(e -> {

                janela.setVisible(
                        false
                );

                TelaAvaliacaoTrilha.abrir(
                        janela,
                        numero,
                        true,
                        () -> {

                            janela.setVisible(
                                    true
                            );

                            atualizarConteudoTrilha(
                                    painel,
                                    janela,
                                    numero,
                                    nome
                            );
                        }
                );
            });

            painel.add(
                    botaoPre
            );

        } else if (!resultado.realizouPos()) {

            JPanel resultadoPre =
                    criarCardResultado(
                            "Resultado do Pré-teste",
                            resultado.getAcertosPre()
                                    + " de "
                                    + resultado.getTotalPre()
                                    + " respostas corretas",
                            resultado.getPercentualPre()
                                    + "%",
                            TemaMoneyShield.AZUL
                    );

            resultadoPre.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            66
                    )
            );

            resultadoPre.setPreferredSize(
                    new Dimension(
                            760,
                            66
                    )
            );

            resultadoPre.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painel.add(
                    resultadoPre
            );

            painel.add(
                    Box.createVerticalStrut(
                            8
                    )
            );

            painel.add(
                    criarConteudoEducacional(
                            numero
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            8
                    )
            );

            JButton botaoPos =
                    TemaMoneyShield
                            .criarBotaoPrimario(
                                    "Realizar Pós-teste"
                            );

            botaoPos.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            botaoPos.setMaximumSize(
                    new Dimension(
                            190,
                            38
                    )
            );

            botaoPos.addActionListener(e -> {

                janela.setVisible(
                        false
                );

                TelaAvaliacaoTrilha.abrir(
                        janela,
                        numero,
                        false,
                        () -> {

                            janela.setVisible(
                                    true
                            );

                            atualizarConteudoTrilha(
                                    painel,
                                    janela,
                                    numero,
                                    nome
                            );
                        }
                );
            });

            painel.add(
                    botaoPos
            );

        } else {

            JPanel painelResultados =
                    new JPanel(
                            new GridLayout(
                                    1,
                                    2,
                                    10,
                                    0
                            )
                    );

            painelResultados.setOpaque(
                    false
            );

            painelResultados.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painelResultados.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            66
                    )
            );

            painelResultados.setPreferredSize(
                    new Dimension(
                            760,
                            66
                    )
            );

            painelResultados.add(
                    criarCardResultado(
                            "Pré-teste",
                            resultado.getAcertosPre()
                                    + " / "
                                    + resultado.getTotalPre(),
                            resultado.getPercentualPre()
                                    + "%",
                            TemaMoneyShield.AZUL
                    )
            );

            painelResultados.add(
                    criarCardResultado(
                            "Pós-teste",
                            resultado.getAcertosPos()
                                    + " / "
                                    + resultado.getTotalPos(),
                            resultado.getPercentualPos()
                                    + "%",
                            TemaMoneyShield.ROSA
                    )
            );

            painel.add(
                    painelResultados
            );

            painel.add(
                    Box.createVerticalStrut(
                            7
                    )
            );

            int evolucao =
                    resultado.getEvolucao();

            String textoEvolucao =
                    evolucao > 0
                            ? "+"
                            + evolucao
                            + " pontos percentuais"
                            : evolucao
                            + " pontos percentuais";

            painel.add(
                    criarCardInformacao(
                            "Evolução",
                            "Sua evolução entre o pré e o pós-teste foi de "
                                    + textoEvolucao
                                    + ".",
                            TemaMoneyShield.AMARELO
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            7
                    )
            );

            painel.add(
                    criarConteudoEducacional(
                            numero
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            7
                    )
            );

            painel.add(
                    criarBotaoConcluir(
                            nome,
                            janela
                    )
            );
        }

        painel.add(
                Box.createVerticalGlue()
        );

        painel.revalidate();
        painel.repaint();
    }

    private static JPanel criarConteudoEducacional(
            int numero) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel label =
                new JLabel();

        label.setForeground(
                TemaMoneyShield.MARINHO
        );

        if (numero == 1) {

            label.setText(
                    "<html>"
                            + "<div style='width:690px;'>"
                            + "<font face='Comic Sans MS' size='3'>"
                            + "<b>"
                            + "Independência e responsabilidade financeira"
                            + "</b>"
                            + "</font>"
                            + "<br><br>"
                            + "<font face='Trebuchet MS' size='2'>"
                            + "Ter independência financeira não significa apenas possuir dinheiro. "
                            + "Significa conseguir tomar decisões conscientes sobre como usar sua renda, "
                            + "organizar gastos e assumir responsabilidade pelas próprias escolhas."
                            + "<br><br>"
                            + "Um dos primeiros passos é conhecer quanto dinheiro entra e quanto sai. "
                            + "Acompanhar receitas e despesas ajuda a evitar gastos maiores do que a renda disponível."
                            + "<br><br>"
                            + "Também é importante diferenciar uma compra necessária de uma compra por impulso. "
                            + "Antes de gastar, vale pensar se aquele item é realmente necessário, "
                            + "se cabe no orçamento e quais outras prioridades podem ser afetadas."
                            + "<br><br>"
                            + "Planejar não significa deixar de aproveitar o dinheiro. "
                            + "Significa encontrar equilíbrio entre necessidades atuais, desejos e objetivos futuros."
                            + "</font>"
                            + "</div>"
                            + "</html>"
            );

            card.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            205
                    )
            );

            card.setPreferredSize(
                    new Dimension(
                            760,
                            205
                    )
            );

        } else {

            label.setText(
                    "<html>"
                            + "<div style='width:690px;'>"
                            + "<font face='Comic Sans MS' size='3'>"
                            + "<b>"
                            + "Conteúdo da trilha"
                            + "</b>"
                            + "</font>"
                            + "<br><br>"
                            + "<font face='Trebuchet MS' size='2'>"
                            + "O conteúdo completo desta trilha poderá ser desenvolvido posteriormente."
                            + "</font>"
                            + "</div>"
                            + "</html>"
            );

            card.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            82
                    )
            );

            card.setPreferredSize(
                    new Dimension(
                            760,
                            82
                    )
            );
        }

        label.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        card.add(
                label,
                BorderLayout.CENTER
        );

        return card;
    }

    private static JPanel criarCardInformacao(
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

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        66
                )
        );

        card.setPreferredSize(
                new Dimension(
                        760,
                        66
                )
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                destaque
        );

        faixa.setPreferredSize(
                new Dimension(
                        5,
                        1
                )
        );

        JLabel label =
                new JLabel(
                        "<html>"
                                + "<div style='width:675px;'>"
                                + "<font face='Comic Sans MS' size='3'>"
                                + "<b>"
                                + titulo
                                + "</b>"
                                + "</font>"
                                + "<br>"
                                + "<font face='Trebuchet MS' size='2'>"
                                + texto
                                + "</font>"
                                + "</div>"
                                + "</html>"
                );

        label.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        label.setForeground(
                TemaMoneyShield.MARINHO
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                label,
                BorderLayout.CENTER
        );

        return card;
    }

    private static JPanel criarCardResultado(
            String titulo,
            String descricao,
            String percentual,
            Color destaque) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        8,
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
                        5,
                        1
                )
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

        JLabel labelTitulo =
                new JLabel(
                        titulo
                );

        labelTitulo.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        11
                )
        );

        labelTitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        JLabel labelDescricao =
                new JLabel(
                        descricao
                );

        labelDescricao.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.PLAIN,
                        11
                )
        );

        labelDescricao.setForeground(
                TemaMoneyShield.MARINHO
        );

        textos.add(
                labelTitulo
        );

        textos.add(
                labelDescricao
        );

        JLabel labelPercentual =
                new JLabel(
                        percentual
                );

        labelPercentual.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TEXTO,
                        Font.BOLD,
                        18
                )
        );

        labelPercentual.setForeground(
                destaque
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                textos,
                BorderLayout.CENTER
        );

        card.add(
                labelPercentual,
                BorderLayout.EAST
        );

        return card;
    }

    private static JButton criarBotaoConcluir(
            String nomeTrilha,
            JFrame janela) {

        boolean concluida =
                GamificacaoService.trilhaConcluida(
                        nomeTrilha
                );

        JButton botao;

        if (concluida) {

            botao =
                    TemaMoneyShield
                            .criarBotaoSecundario(
                                    "Trilha concluída"
                            );

            botao.setEnabled(
                    false
            );

        } else {

            botao =
                    TemaMoneyShield
                            .criarBotaoPrimario(
                                    "Concluir trilha (+150 XP)"
                            );

            botao.addActionListener(e -> {

                int nivelAnterior =
                        GamificacaoService.getNivel();

                boolean concluiu =
                        GamificacaoService.concluirTrilha(
                                nomeTrilha
                        );

                if (!concluiu) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "Esta trilha já foi concluída."
                    );

                    botao.setText(
                            "Trilha concluída"
                    );

                    botao.setEnabled(
                            false
                    );

                    return;
                }

                int novoNivel =
                        GamificacaoService.getNivel();

                JOptionPane.showMessageDialog(
                        janela,
                        "Trilha concluída!\n+150 XP"
                );

                botao.setText(
                        "Trilha concluída"
                );

                botao.setEnabled(
                        false
                );

                if (novoNivel
                        > nivelAnterior) {

                    TelaRecompensa.mostrar(
                            janela,
                            novoNivel
                    );
                }
            });
        }

        botao.setFont(
                TemaMoneyShield.FONTE_BOTAO
        );

        botao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botao.setMaximumSize(
                new Dimension(
                        210,
                        38
                )
        );

        return botao;
    }
}