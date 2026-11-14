package br.com.moneyshield.ui.admin;

import br.com.moneyshield.model.AvaliacaoTrilha;
import br.com.moneyshield.model.PerguntaAvaliacao;
import br.com.moneyshield.repository.RepositorioAvaliacoes;
import br.com.moneyshield.repository.RepositorioTrilhas;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GerenciamentoAvaliacoesAdmin {

    public static void abrir(JFrame telaAnterior) {

        JFrame janela = new JFrame("MoneyShield - Gerenciamento de Avaliações");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setSize(850, 650);
        janela.setLocationRelativeTo(telaAnterior);
        janela.setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(TemaMoneyShield.FUNDO);

        JPanel painelSuperior = criarCabecalho();

        JPanel painelConteudo = new JPanel(new BorderLayout(18, 0));
        painelConteudo.setBackground(TemaMoneyShield.FUNDO);
        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        30,
                        12,
                        30
                )
        );

        JPanel painelTrilhas = new JPanel(new BorderLayout(0, 12));
        painelTrilhas.setOpaque(false);
        painelTrilhas.setPreferredSize(
                new Dimension(
                        330,
                        0
                )
        );

        JPanel cardTituloLista = TemaMoneyShield.criarCard();

        cardTituloLista.setLayout(
                new BorderLayout(
                        12,
                        0
                )
        );

        cardTituloLista.setBorder(
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
                                        12,
                                        14,
                                        12,
                                        14
                                )
                        )
                )
        );

        JPanel textosLista = new JPanel();

        textosLista.setOpaque(false);

        textosLista.setLayout(
                new BoxLayout(
                        textosLista,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel tituloLista = new JLabel("Trilhas");

        tituloLista.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        tituloLista.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloLista.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtituloLista =
                new JLabel(
                        "Selecione uma trilha para gerenciar"
                );

        subtituloLista.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        subtituloLista.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        subtituloLista.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        textosLista.add(
                tituloLista
        );

        textosLista.add(
                Box.createVerticalStrut(
                        3
                )
        );

        textosLista.add(
                subtituloLista
        );

        cardTituloLista.add(
                textosLista,
                BorderLayout.CENTER
        );

        DefaultListModel<String> modelo =
                new DefaultListModel<>();

        ArrayList<String> trilhas =
                RepositorioTrilhas.getTrilhas();

        for (int i = 0;
             i < trilhas.size();
             i++) {

            modelo.addElement(
                    (i + 1)
                            + ". "
                            + trilhas.get(i)
            );
        }

        JList<String> listaTrilhas =
                new JList<>(
                        modelo
                );

        listaTrilhas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        listaTrilhas.setCellRenderer(
                new RenderizadorTrilha()
        );

        listaTrilhas.setBackground(
                TemaMoneyShield.FUNDO
        );

        listaTrilhas.setSelectionBackground(
                TemaMoneyShield.FUNDO
        );

        listaTrilhas.setFixedCellHeight(
                72
        );

        if (!modelo.isEmpty()) {

            listaTrilhas.setSelectedIndex(
                    0
            );
        }

        JScrollPane scroll =
                new JScrollPane(
                        listaTrilhas
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
                )
        );

        scroll.getViewport().setBackground(
                TemaMoneyShield.FUNDO
        );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelTrilhas.add(
                cardTituloLista,
                BorderLayout.NORTH
        );

        painelTrilhas.add(
                scroll,
                BorderLayout.CENTER
        );

        JPanel painelDetalhes =
                new JPanel();

        painelDetalhes.setOpaque(
                false
        );

        painelDetalhes.setLayout(
                new BoxLayout(
                        painelDetalhes,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Pré e Pós-testes"
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
                        "Configure as avaliações de conhecimento de cada trilha."
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
                        150
                )
        );

        JPanel faixaSituacao =
                new JPanel();

        faixaSituacao.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JPanel dadosSituacao =
                new JPanel();

        dadosSituacao.setOpaque(
                false
        );

        dadosSituacao.setLayout(
                new BoxLayout(
                        dadosSituacao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel labelTrilhaSelecionada =
                new JLabel();

        labelTrilhaSelecionada.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        labelTrilhaSelecionada.setForeground(
                TemaMoneyShield.MARINHO
        );

        labelTrilhaSelecionada.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelPre =
                criarLinhaSituacao();

        JLabel labelPos =
                criarLinhaSituacao();

        JLabel labelStatus =
                criarLinhaSituacao();

        dadosSituacao.add(
                labelTrilhaSelecionada
        );

        dadosSituacao.add(
                Box.createVerticalStrut(
                        12
                )
        );

        dadosSituacao.add(
                labelPre
        );

        dadosSituacao.add(
                Box.createVerticalStrut(
                        5
                )
        );

        dadosSituacao.add(
                labelPos
        );

        dadosSituacao.add(
                Box.createVerticalStrut(
                        5
                )
        );

        dadosSituacao.add(
                labelStatus
        );

        cardSituacao.add(
                faixaSituacao,
                BorderLayout.WEST
        );

        cardSituacao.add(
                dadosSituacao,
                BorderLayout.CENTER
        );

        JPanel painelAcoes =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                0,
                                10
                        )
                );

        painelAcoes.setOpaque(
                false
        );

        painelAcoes.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelAcoes.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        160
                )
        );

        JButton preTeste =
                TemaMoneyShield.criarBotaoCard(
                        "Gerenciar Pré-teste",
                        "Perguntas realizadas antes do conteúdo",
                        TemaMoneyShield.AZUL,
                        TemaMoneyShield.MARINHO
                );

        JButton posTeste =
                TemaMoneyShield.criarBotaoCard(
                        "Gerenciar Pós-teste",
                        "Perguntas realizadas após o conteúdo",
                        TemaMoneyShield.ROSA,
                        TemaMoneyShield.BRANCO
                );

        JButton visualizar =
                TemaMoneyShield.criarBotaoCard(
                        "Ver situação",
                        "Resumo da configuração desta trilha",
                        TemaMoneyShield.AMARELO,
                        TemaMoneyShield.MARINHO
                );

        painelAcoes.add(
                preTeste
        );

        painelAcoes.add(
                posTeste
        );

        painelAcoes.add(
                visualizar
        );

        JPanel cardOrientacao =
                TemaMoneyShield.criarCard();

        cardOrientacao.setLayout(
                new BorderLayout(
                        12,
                        0
                )
        );

        cardOrientacao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardOrientacao.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );

        JPanel faixaOrientacao =
                new JPanel();

        faixaOrientacao.setBackground(
                TemaMoneyShield.MALVA
        );

        faixaOrientacao.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JLabel textoOrientacao =
                new JLabel(
                        "<html>"
                                + "<div style='width:360px;'>"
                                + "<b>Avaliação completa</b><br><br>"
                                + "Uma trilha é considerada pronta para avaliação quando possui pelo menos uma pergunta no pré-teste e uma no pós-teste."
                                + "</div>"
                                + "</html>"
                );

        textoOrientacao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        textoOrientacao.setForeground(
                TemaMoneyShield.MARINHO
        );

        cardOrientacao.add(
                faixaOrientacao,
                BorderLayout.WEST
        );

        cardOrientacao.add(
                textoOrientacao,
                BorderLayout.CENTER
        );

        painelDetalhes.add(
                titulo
        );

        painelDetalhes.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelDetalhes.add(
                subtitulo
        );

        painelDetalhes.add(
                Box.createVerticalStrut(
                        18
                )
        );

        painelDetalhes.add(
                cardSituacao
        );

        painelDetalhes.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelDetalhes.add(
                painelAcoes
        );

        painelDetalhes.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelDetalhes.add(
                cardOrientacao
        );

        painelConteudo.add(
                painelTrilhas,
                BorderLayout.WEST
        );

        painelConteudo.add(
                painelDetalhes,
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

        JButton voltar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Voltar"
                );

        painelInferior.add(
                voltar
        );

        Runnable atualizarSituacao = () -> {

            int indice =
                    listaTrilhas.getSelectedIndex();

            if (indice < 0) {

                labelTrilhaSelecionada.setText(
                        "Nenhuma trilha selecionada"
                );

                labelPre.setText(
                        "Pré-teste: —"
                );

                labelPos.setText(
                        "Pós-teste: —"
                );

                labelStatus.setText(
                        "Situação: —"
                );

                faixaSituacao.setBackground(
                        TemaMoneyShield.BORDA
                );

                return;
            }

            int numeroTrilha =
                    RepositorioTrilhas.getNumeroOrdem(
                            indice
                    );

            AvaliacaoTrilha avaliacao =
                    RepositorioAvaliacoes
                            .getAvaliacao(
                                    numeroTrilha
                            );

            labelTrilhaSelecionada.setText(
                    "Trilha "
                            + numeroTrilha
            );

            labelPre.setText(
                    "Pré-teste: "
                            + avaliacao
                            .getPerguntasPre()
                            .size()
                            + " pergunta(s)"
            );

            labelPos.setText(
                    "Pós-teste: "
                            + avaliacao
                            .getPerguntasPos()
                            .size()
                            + " pergunta(s)"
            );

            if (avaliacao.possuiAvaliacaoCompleta()) {

                labelStatus.setText(
                        "Situação: Avaliação disponível"
                );

                labelStatus.setForeground(
                        TemaMoneyShield.ROSA
                );

                faixaSituacao.setBackground(
                        TemaMoneyShield.ROSA
                );

            } else {

                labelStatus.setText(
                        "Situação: Avaliação incompleta"
                );

                labelStatus.setForeground(
                        TemaMoneyShield.MALVA
                );

                faixaSituacao.setBackground(
                        TemaMoneyShield.AMARELO
                );
            }
        };

        listaTrilhas.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                atualizarSituacao.run();
            }
        });

        preTeste.addActionListener(e -> {

            int indice =
                    listaTrilhas.getSelectedIndex();

            if (indice < 0) {

                mostrarSelecaoObrigatoria(
                        janela
                );

                return;
            }

            gerenciarPerguntas(
                    janela,
                    RepositorioTrilhas.getNumeroOrdem(
                            indice
                    ),
                    true
            );

            atualizarSituacao.run();
            listaTrilhas.repaint();
        });

        posTeste.addActionListener(e -> {

            int indice =
                    listaTrilhas.getSelectedIndex();

            if (indice < 0) {

                mostrarSelecaoObrigatoria(
                        janela
                );

                return;
            }

            gerenciarPerguntas(
                    janela,
                    RepositorioTrilhas.getNumeroOrdem(
                            indice
                    ),
                    false
            );

            atualizarSituacao.run();
            listaTrilhas.repaint();
        });

        visualizar.addActionListener(e -> {

            int indice =
                    listaTrilhas.getSelectedIndex();

            if (indice < 0) {

                mostrarSelecaoObrigatoria(
                        janela
                );

                return;
            }

            int numeroTrilha =
                    RepositorioTrilhas.getNumeroOrdem(
                            indice
                    );

            AvaliacaoTrilha avaliacao =
                    RepositorioAvaliacoes
                            .getAvaliacao(
                                    numeroTrilha
                            );

            mostrarSituacao(
                    janela,
                    numeroTrilha,
                    avaliacao
            );
        });

        voltar.addActionListener(e -> {

            janela.dispose();

            telaAnterior.setVisible(
                    true
            );
        });

        janela.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(
                            WindowEvent e) {

                        if (!telaAnterior.isVisible()) {

                            telaAnterior.setVisible(
                                    true
                            );
                        }
                    }
                }
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

        atualizarSituacao.run();

        telaAnterior.setVisible(
                false
        );

        janela.setContentPane(
                painelPrincipal
        );

        janela.setVisible(
                true
        );
    }

    private static JPanel criarCabecalho() {

        JPanel painel =
                new JPanel(
                        new BorderLayout()
                );

        painel.setBackground(
                TemaMoneyShield.MARINHO
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        28,
                        16,
                        28
                )
        );

        painel.setPreferredSize(
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
                        "Administração de avaliações"
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

        JLabel area =
                new JLabel(
                        "Pré e Pós-testes"
                );

        area.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        area.setForeground(
                TemaMoneyShield.BRANCO
        );

        painel.add(
                painelMarca,
                BorderLayout.WEST
        );

        painel.add(
                area,
                BorderLayout.EAST
        );

        return painel;
    }

    private static JLabel criarLinhaSituacao() {

        JLabel label =
                new JLabel();

        label.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        label.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    private static void mostrarSelecaoObrigatoria(
            Component pai) {

        JOptionPane.showMessageDialog(
                pai,
                "Selecione uma trilha.",
                "Nenhuma trilha selecionada",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private static void mostrarSituacao(
            Component pai,
            int numeroTrilha,
            AvaliacaoTrilha avaliacao) {

        JPanel painel =
                new JPanel();

        painel.setBackground(
                TemaMoneyShield.FUNDO
        );

        painel.setLayout(
                new BoxLayout(
                        painel,
                        BoxLayout.Y_AXIS
                )
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        JLabel titulo =
                new JLabel(
                        "Trilha "
                                + numeroTrilha
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel pre =
                new JLabel(
                        "Pré-teste: "
                                + avaliacao
                                .getPerguntasPre()
                                .size()
                                + " pergunta(s)"
                );

        pre.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        pre.setForeground(
                TemaMoneyShield.MARINHO
        );

        pre.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel pos =
                new JLabel(
                        "Pós-teste: "
                                + avaliacao
                                .getPerguntasPos()
                                .size()
                                + " pergunta(s)"
                );

        pos.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        pos.setForeground(
                TemaMoneyShield.MARINHO
        );

        pos.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel status =
                new JLabel(
                        avaliacao.possuiAvaliacaoCompleta()
                                ? "Avaliação disponível"
                                : "Avaliação incompleta"
                );

        status.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        status.setForeground(
                avaliacao.possuiAvaliacaoCompleta()
                        ? TemaMoneyShield.ROSA
                        : TemaMoneyShield.MALVA
        );

        status.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painel.add(
                pre
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                pos
        );

        painel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painel.add(
                status
        );

        JOptionPane.showMessageDialog(
                pai,
                painel,
                "MoneyShield - Situação da avaliação",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private static void gerenciarPerguntas(
            JFrame pai,
            int numeroTrilha,
            boolean preTeste) {

        JDialog dialog =
                new JDialog(
                        pai,
                        preTeste
                                ? "MoneyShield - Pré-teste da Trilha "
                                + numeroTrilha
                                : "MoneyShield - Pós-teste da Trilha "
                                + numeroTrilha,
                        true
                );

        dialog.setSize(
                750,
                520
        );

        dialog.setLocationRelativeTo(
                pai
        );

        dialog.setResizable(
                false
        );

        AvaliacaoTrilha avaliacao =
                RepositorioAvaliacoes
                        .getAvaliacao(
                                numeroTrilha
                        );

        JPanel painelPrincipal =
                new JPanel(
                        new BorderLayout()
                );

        painelPrincipal.setBackground(
                TemaMoneyShield.FUNDO
        );

        JPanel cabecalho =
                new JPanel(
                        new BorderLayout()
                );

        cabecalho.setBackground(
                TemaMoneyShield.MARINHO
        );

        cabecalho.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        22,
                        14,
                        22
                )
        );

        JPanel textosCabecalho =
                new JPanel();

        textosCabecalho.setOpaque(
                false
        );

        textosCabecalho.setLayout(
                new BoxLayout(
                        textosCabecalho,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        preTeste
                                ? "Perguntas do Pré-teste"
                                : "Perguntas do Pós-teste"
                );

        titulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        titulo.setForeground(
                TemaMoneyShield.BRANCO
        );

        titulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitulo =
                new JLabel(
                        "Trilha "
                                + numeroTrilha
                );

        subtitulo.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        subtitulo.setForeground(
                TemaMoneyShield.AZUL
        );

        subtitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        textosCabecalho.add(
                titulo
        );

        textosCabecalho.add(
                Box.createVerticalStrut(
                        2
                )
        );

        textosCabecalho.add(
                subtitulo
        );

        cabecalho.add(
                textosCabecalho,
                BorderLayout.WEST
        );

        DefaultListModel<PerguntaAvaliacao> modelo =
                new DefaultListModel<>();

        JList<PerguntaAvaliacao> lista =
                new JList<>(
                        modelo
                );

        lista.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        lista.setCellRenderer(
                new RenderizadorPergunta(
                        preTeste
                )
        );

        lista.setBackground(
                TemaMoneyShield.FUNDO
        );

        lista.setSelectionBackground(
                TemaMoneyShield.FUNDO
        );

        lista.setFixedCellHeight(
                72
        );

        Runnable atualizar = () -> {

            modelo.clear();

            ArrayList<PerguntaAvaliacao> perguntas =
                    preTeste
                            ? avaliacao.getPerguntasPre()
                            : avaliacao.getPerguntasPos();

            for (PerguntaAvaliacao pergunta
                    : perguntas) {

                modelo.addElement(
                        pergunta
                );
            }
        };

        JScrollPane scroll =
                new JScrollPane(
                        lista
                );

        scroll.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        20,
                        12,
                        20
                )
        );

        scroll.setOpaque(
                false
        );

        scroll.getViewport()
                .setBackground(
                        TemaMoneyShield.FUNDO
                );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        JPanel rodape =
                new JPanel(
                        new BorderLayout()
                );

        rodape.setBackground(
                TemaMoneyShield.FUNDO
        );

        rodape.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        20,
                        12,
                        20
                )
        );

        JLabel quantidade =
                new JLabel();

        quantidade.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        quantidade.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        JPanel botoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        botoes.setOpaque(
                false
        );

        JButton adicionar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Adicionar"
                );

        JButton editar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Editar"
                );

        JButton excluir =
                criarBotaoExcluir(
                        "Excluir"
                );

        JButton fechar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Fechar"
                );

        botoes.add(
                adicionar
        );

        botoes.add(
                editar
        );

        botoes.add(
                excluir
        );

        botoes.add(
                fechar
        );

        rodape.add(
                quantidade,
                BorderLayout.WEST
        );

        rodape.add(
                botoes,
                BorderLayout.EAST
        );

        Runnable atualizarTudo = () -> {

            atualizar.run();

            quantidade.setText(
                    modelo.size()
                            + " pergunta(s)"
            );
        };

        adicionar.addActionListener(e -> {

            PerguntaAvaliacao pergunta =
                    formularioPergunta(
                            dialog,
                            null
                    );

            if (pergunta == null) {

                return;
            }

            if (preTeste) {

                avaliacao.adicionarPerguntaPre(
                        pergunta
                );

            } else {

                avaliacao.adicionarPerguntaPos(
                        pergunta
                );
            }

            atualizarTudo.run();
        });

        editar.addActionListener(e -> {

            PerguntaAvaliacao selecionada =
                    lista.getSelectedValue();

            if (selecionada == null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Selecione uma pergunta.",
                        "Nenhuma pergunta selecionada",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            formularioPergunta(
                    dialog,
                    selecionada
            );

            atualizarTudo.run();
        });

        excluir.addActionListener(e -> {

            PerguntaAvaliacao selecionada =
                    lista.getSelectedValue();

            if (selecionada == null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Selecione uma pergunta.",
                        "Nenhuma pergunta selecionada",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int resposta =
                    JOptionPane.showConfirmDialog(
                            dialog,
                            "Excluir esta pergunta?",
                            "Excluir pergunta",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (resposta
                    != JOptionPane.YES_OPTION) {

                return;
            }

            if (preTeste) {

                avaliacao.removerPerguntaPre(
                        selecionada
                );

            } else {

                avaliacao.removerPerguntaPos(
                        selecionada
                );
            }

            atualizarTudo.run();
        });

        fechar.addActionListener(e ->
                dialog.dispose()
        );

        painelPrincipal.add(
                cabecalho,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                scroll,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                rodape,
                BorderLayout.SOUTH
        );

        atualizarTudo.run();

        dialog.setContentPane(
                painelPrincipal
        );

        dialog.setVisible(
                true
        );
    }

    private static PerguntaAvaliacao formularioPergunta(
            Component pai,
            PerguntaAvaliacao existente) {

        JTextField campoPergunta =
                new JTextField();

        JTextField alternativa1 =
                new JTextField();

        JTextField alternativa2 =
                new JTextField();

        JTextField alternativa3 =
                new JTextField();

        JTextField alternativa4 =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoPergunta
        );

        TemaMoneyShield.estilizarCampoTexto(
                alternativa1
        );

        TemaMoneyShield.estilizarCampoTexto(
                alternativa2
        );

        TemaMoneyShield.estilizarCampoTexto(
                alternativa3
        );

        TemaMoneyShield.estilizarCampoTexto(
                alternativa4
        );

        JComboBox<String> correta =
                new JComboBox<>(
                        new String[]{
                                "Alternativa 1",
                                "Alternativa 2",
                                "Alternativa 3",
                                "Alternativa 4"
                        }
                );

        correta.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        correta.setForeground(
                TemaMoneyShield.MARINHO
        );

        correta.setBackground(
                TemaMoneyShield.BRANCO
        );

        correta.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        correta.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        if (existente != null) {

            campoPergunta.setText(
                    existente.getPergunta()
            );

            ArrayList<String> alternativas =
                    existente.getAlternativas();

            alternativa1.setText(
                    alternativas.get(0)
            );

            alternativa2.setText(
                    alternativas.get(1)
            );

            alternativa3.setText(
                    alternativas.get(2)
            );

            alternativa4.setText(
                    alternativas.get(3)
            );

            correta.setSelectedIndex(
                    existente.getRespostaCorreta()
            );
        }

        JPanel painel =
                new JPanel();

        painel.setBackground(
                TemaMoneyShield.BRANCO
        );

        painel.setLayout(
                new BoxLayout(
                        painel,
                        BoxLayout.Y_AXIS
                )
        );

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        painel.add(
                criarLabelFormulario(
                        "Pergunta"
                )
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                campoPergunta
        );

        painel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painel.add(
                criarLabelFormulario(
                        "Alternativa 1"
                )
        );

        painel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painel.add(
                alternativa1
        );

        painel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painel.add(
                criarLabelFormulario(
                        "Alternativa 2"
                )
        );

        painel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painel.add(
                alternativa2
        );

        painel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painel.add(
                criarLabelFormulario(
                        "Alternativa 3"
                )
        );

        painel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painel.add(
                alternativa3
        );

        painel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painel.add(
                criarLabelFormulario(
                        "Alternativa 4"
                )
        );

        painel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painel.add(
                alternativa4
        );

        painel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        painel.add(
                criarLabelFormulario(
                        "Resposta correta"
                )
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                correta
        );

        JScrollPane scrollFormulario =
                new JScrollPane(
                        painel
                );

        scrollFormulario.setBorder(
                null
        );

        scrollFormulario.setPreferredSize(
                new Dimension(
                        430,
                        410
                )
        );

        scrollFormulario.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        pai,
                        scrollFormulario,
                        existente == null
                                ? "Adicionar pergunta"
                                : "Editar pergunta",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return null;
        }

        String pergunta =
                campoPergunta
                        .getText()
                        .trim();

        String a1 =
                alternativa1
                        .getText()
                        .trim();

        String a2 =
                alternativa2
                        .getText()
                        .trim();

        String a3 =
                alternativa3
                        .getText()
                        .trim();

        String a4 =
                alternativa4
                        .getText()
                        .trim();

        if (pergunta.isEmpty()
                || a1.isEmpty()
                || a2.isEmpty()
                || a3.isEmpty()
                || a4.isEmpty()) {

            JOptionPane.showMessageDialog(
                    pai,
                    "Preencha todos os campos.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        if (existente != null) {

            existente.setPergunta(
                    pergunta
            );

            existente.setAlternativa(
                    0,
                    a1
            );

            existente.setAlternativa(
                    1,
                    a2
            );

            existente.setAlternativa(
                    2,
                    a3
            );

            existente.setAlternativa(
                    3,
                    a4
            );

            existente.setRespostaCorreta(
                    correta.getSelectedIndex()
            );

            return existente;
        }

        ArrayList<String> alternativas =
                new ArrayList<>();

        alternativas.add(
                a1
        );

        alternativas.add(
                a2
        );

        alternativas.add(
                a3
        );

        alternativas.add(
                a4
        );

        return new PerguntaAvaliacao(
                pergunta,
                alternativas,
                correta.getSelectedIndex()
        );
    }

    private static JLabel criarLabelFormulario(
            String texto) {

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

        return label;
    }

    private static JButton criarBotaoExcluir(
            String texto) {

        JButton botao =
                new JButton(
                        texto
                );

        botao.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        botao.setForeground(
                TemaMoneyShield.ROSA
        );

        botao.setBackground(
                TemaMoneyShield.BRANCO
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
                        BorderFactory.createLineBorder(
                                TemaMoneyShield.ROSA
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                14,
                                8,
                                14
                        )
                )
        );

        return botao;
    }

    private static class RenderizadorTrilha
            extends JPanel
            implements ListCellRenderer<String> {

        private final JLabel titulo;
        private final JLabel status;
        private final JPanel faixa;

        private RenderizadorTrilha() {

            setLayout(
                    new BorderLayout(
                            10,
                            0
                    )
            );

            setOpaque(
                    true
            );

            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(
                                    0,
                                    0,
                                    1,
                                    0,
                                    TemaMoneyShield.BORDA
                            ),
                            BorderFactory.createEmptyBorder(
                                    8,
                                    10,
                                    8,
                                    10
                            )
                    )
            );

            faixa =
                    new JPanel();

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

            titulo =
                    new JLabel();

            titulo.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            titulo.setForeground(
                    TemaMoneyShield.MARINHO
            );

            status =
                    new JLabel();

            status.setFont(
                    TemaMoneyShield.FONTE_TEXTO_PEQUENO
            );

            textos.add(
                    titulo
            );

            textos.add(
                    Box.createVerticalStrut(
                            3
                    )
            );

            textos.add(
                    status
            );

            add(
                    faixa,
                    BorderLayout.WEST
            );

            add(
                    textos,
                    BorderLayout.CENTER
            );
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends String> list,
                String value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            int numeroTrilha =
                    RepositorioTrilhas.getNumeroOrdem(
                            index
                    );

            AvaliacaoTrilha avaliacao =
                    RepositorioAvaliacoes
                            .getAvaliacao(
                                    numeroTrilha
                            );

            titulo.setText(
                    value
            );

            if (avaliacao.possuiAvaliacaoCompleta()) {

                status.setText(
                        "Pré/Pós configurado"
                );

                status.setForeground(
                        TemaMoneyShield.ROSA
                );

                faixa.setBackground(
                        TemaMoneyShield.AZUL
                );

            } else {

                status.setText(
                        "Avaliação incompleta"
                );

                status.setForeground(
                        TemaMoneyShield.TEXTO_SECUNDARIO
                );

                faixa.setBackground(
                        TemaMoneyShield.AMARELO
                );
            }

            setBackground(
                    isSelected
                            ? new Color(
                            237,
                            242,
                            250
                    )
                            : TemaMoneyShield.BRANCO
            );

            return this;
        }
    }

    private static class RenderizadorPergunta
            extends JPanel
            implements ListCellRenderer<PerguntaAvaliacao> {

        private final JLabel numero;
        private final JLabel pergunta;
        private final JPanel faixa;
        private final boolean preTeste;

        private RenderizadorPergunta(
                boolean preTeste) {

            this.preTeste =
                    preTeste;

            setLayout(
                    new BorderLayout(
                            10,
                            0
                    )
            );

            setOpaque(
                    true
            );

            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(
                                    0,
                                    0,
                                    1,
                                    0,
                                    TemaMoneyShield.BORDA
                            ),
                            BorderFactory.createEmptyBorder(
                                    8,
                                    10,
                                    8,
                                    10
                            )
                    )
            );

            faixa =
                    new JPanel();

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

            numero =
                    new JLabel();

            numero.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            9
                    )
            );

            pergunta =
                    new JLabel();

            pergunta.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            pergunta.setForeground(
                    TemaMoneyShield.MARINHO
            );

            textos.add(
                    numero
            );

            textos.add(
                    Box.createVerticalStrut(
                            4
                    )
            );

            textos.add(
                    pergunta
            );

            add(
                    faixa,
                    BorderLayout.WEST
            );

            add(
                    textos,
                    BorderLayout.CENTER
            );
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends PerguntaAvaliacao> list,
                PerguntaAvaliacao value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            Color destaque =
                    preTeste
                            ? TemaMoneyShield.AZUL
                            : TemaMoneyShield.ROSA;

            numero.setText(
                    "PERGUNTA "
                            + (index + 1)
            );

            numero.setForeground(
                    destaque
            );

            pergunta.setText(
                    "<html>"
                            + "<div style='width:560px;'>"
                            + value.getPergunta()
                            + "</div>"
                            + "</html>"
            );

            faixa.setBackground(
                    destaque
            );

            setBackground(
                    isSelected
                            ? new Color(
                            237,
                            242,
                            250
                    )
                            : TemaMoneyShield.BRANCO
            );

            return this;
        }
    }
}