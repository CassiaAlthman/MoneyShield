package br.com.moneyshield.ui.student;

import br.com.moneyshield.model.AvaliacaoTrilha;
import br.com.moneyshield.model.Trilha;
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

    private static JButton criarBotaoTrilha(
            int numero,
            String nome,
            String status) {

        JButton botao =
                TemaMoneyShield.criarBotaoCard(
                        "TRILHA "
                                + numero,
                        nome
                                + "\n"
                                + status,
                        TemaMoneyShield.BRANCO,
                        TemaMoneyShield.MARINHO
                );

        botao.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        botao.setPreferredSize(
                new Dimension(
                        240,
                        115
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
                new BorderLayout(
                        0,
                        8
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        Trilha trilha =
                RepositorioTrilhas.getPorOrdem(
                        numero
                );

        String descricao =
                trilha == null
                        || trilha.getDescricao() == null
                        ? ""
                        : trilha.getDescricao().trim();

        String conteudo =
                trilha == null
                        || trilha.getConteudo() == null
                        ? ""
                        : trilha.getConteudo().trim();

        StringBuilder texto =
                new StringBuilder();

        if (!descricao.isBlank()) {

            texto.append(
                    descricao
            );
        }

        if (!conteudo.isBlank()) {

            if (!texto.isEmpty()) {
                texto.append("\n\n");
            }

            texto.append(
                    conteudo
            );
        }

        if (texto.isEmpty()) {

            texto.append(
                    "O conteúdo desta trilha ainda não foi cadastrado pelo administrador."
            );
        }

        JLabel tituloConteudo =
                new JLabel(
                        "Conteúdo da trilha"
                );

        tituloConteudo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        tituloConteudo.setForeground(
                TemaMoneyShield.MARINHO
        );

        JTextArea areaConteudo =
                new JTextArea(
                        texto.toString()
                );

        areaConteudo.setEditable(
                false
        );

        areaConteudo.setLineWrap(
                true
        );

        areaConteudo.setWrapStyleWord(
                true
        );

        areaConteudo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        areaConteudo.setForeground(
                TemaMoneyShield.MARINHO
        );

        areaConteudo.setBackground(
                TemaMoneyShield.BRANCO
        );

        areaConteudo.setMargin(
                new Insets(
                        8,
                        8,
                        8,
                        8
                )
        );

        areaConteudo.setCaretPosition(
                0
        );

        JScrollPane scrollConteudo =
                new JScrollPane(
                        areaConteudo
                );

        scrollConteudo.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
                )
        );

        scrollConteudo.setPreferredSize(
                new Dimension(
                        720,
                        175
                )
        );

        scrollConteudo.getVerticalScrollBar()
                .setUnitIncrement(
                        14
                );

        card.add(
                tituloConteudo,
                BorderLayout.NORTH
        );

        card.add(
                scrollConteudo,
                BorderLayout.CENTER
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        245
                )
        );

        card.setPreferredSize(
                new Dimension(
                        760,
                        245
                )
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

        JPanel faixa =
                new JPanel();

        faixa.setPreferredSize(
                new Dimension(
                        5,
                        42
                )
        );

        faixa.setBackground(
                destaque
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
                TemaMoneyShield.FONTE_TEXTO
        );

        labelTitulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        JLabel labelTexto =
                new JLabel(
                        "<html>"
                                + "<div style='width:690px;'>"
                                + texto
                                + "</div>"
                                + "</html>"
                );

        labelTexto.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        labelTexto.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        textos.add(
                labelTitulo
        );

        textos.add(
                Box.createVerticalStrut(
                        4
                )
        );

        textos.add(
                labelTexto
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                textos,
                BorderLayout.CENTER
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        75
                )
        );

        card.setPreferredSize(
                new Dimension(
                        760,
                        75
                )
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
                        10,
                        0
                )
        );

        JPanel faixa =
                new JPanel();

        faixa.setPreferredSize(
                new Dimension(
                        5,
                        42
                )
        );

        faixa.setBackground(
                destaque
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
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        labelTitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        JLabel labelDescricao =
                new JLabel(
                        descricao
                );

        labelDescricao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        labelDescricao.setForeground(
                TemaMoneyShield.MARINHO
        );

        JLabel labelPercentual =
                new JLabel(
                        percentual
                );

        labelPercentual.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        20
                )
        );

        labelPercentual.setForeground(
                TemaMoneyShield.MARINHO
        );

        textos.add(
                labelTitulo
        );

        textos.add(
                Box.createVerticalStrut(
                        3
                )
        );

        textos.add(
                labelDescricao
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
            String nome,
            JFrame janela) {

        JButton botao =
                TemaMoneyShield.criarBotaoPrimario(
                        "Concluir trilha"
                );

        botao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        botao.setMaximumSize(
                new Dimension(
                        180,
                        38
                )
        );

        botao.addActionListener(e -> {

            boolean concluiu =
                    GamificacaoService.concluirTrilha(
                            nome
                    );

            if (concluiu) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Trilha concluída! Você recebeu 150 XP.",
                        "Parabéns!",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        janela,
                        "Esta trilha já foi concluída ou não foi possível registrar seu progresso.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            janela.dispose();
        });

        return botao;
    }
}