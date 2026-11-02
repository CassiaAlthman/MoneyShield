package br.com.moneyshield.ui.admin;

import br.com.moneyshield.model.CenarioFinanceiro;
import br.com.moneyshield.model.OpcaoCenario;
import br.com.moneyshield.repository.RepositorioCenarios;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CenariosFinanceirosAdmin {

    public static void abrir(JFrame telaAnterior) {

        JFrame janela = new JFrame("MoneyShield - Gerenciamento de Cenários");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(850, 650);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(TemaMoneyShield.FUNDO);

        JPanel painelSuperior = criarCabecalho();

        JPanel painelConteudo = new JPanel(new BorderLayout(0, 16));
        painelConteudo.setBackground(TemaMoneyShield.FUNDO);
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(22, 36, 12, 36));

        JPanel painelTitulo = new JPanel(new BorderLayout(20, 0));
        painelTitulo.setOpaque(false);

        JPanel painelTextos = new JPanel();
        painelTextos.setOpaque(false);
        painelTextos.setLayout(new BoxLayout(painelTextos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Gerenciamento de Cenários");
        titulo.setFont(TemaMoneyShield.FONTE_TITULO);
        titulo.setForeground(TemaMoneyShield.MARINHO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Crie, configure e publique cenários financeiros para os alunos."
        );

        subtitulo.setFont(TemaMoneyShield.FONTE_TEXTO);
        subtitulo.setForeground(TemaMoneyShield.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelTextos.add(titulo);
        painelTextos.add(Box.createVerticalStrut(5));
        painelTextos.add(subtitulo);

        JButton botaoNovo =
                TemaMoneyShield.criarBotaoPrimario(
                        "Novo Cenário"
                );

        botaoNovo.setPreferredSize(
                new Dimension(
                        140,
                        42
                )
        );

        JPanel painelBotao =
                new JPanel(
                        new GridBagLayout()
                );

        painelBotao.setOpaque(false);
        painelBotao.add(botaoNovo);

        painelTitulo.add(
                painelTextos,
                BorderLayout.WEST
        );

        painelTitulo.add(
                painelBotao,
                BorderLayout.EAST
        );

        JLabel valorTotal =
                criarValorResumo();

        JLabel valorPublicados =
                criarValorResumo();

        JLabel valorRascunhos =
                criarValorResumo();

        JPanel painelResumo =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                12,
                                0
                        )
                );

        painelResumo.setOpaque(false);

        painelResumo.add(
                criarCardResumo(
                        "Total",
                        valorTotal,
                        TemaMoneyShield.AZUL
                )
        );

        painelResumo.add(
                criarCardResumo(
                        "Publicados",
                        valorPublicados,
                        TemaMoneyShield.ROSA
                )
        );

        painelResumo.add(
                criarCardResumo(
                        "Rascunhos",
                        valorRascunhos,
                        TemaMoneyShield.AMARELO
                )
        );

        JPanel painelTopo =
                new JPanel();

        painelTopo.setOpaque(false);

        painelTopo.setLayout(
                new BoxLayout(
                        painelTopo,
                        BoxLayout.Y_AXIS
                )
        );

        painelTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelResumo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelTopo.add(
                painelTitulo
        );

        painelTopo.add(
                Box.createVerticalStrut(
                        16
                )
        );

        painelTopo.add(
                painelResumo
        );

        JPanel painelLista =
                new JPanel();

        painelLista.setOpaque(false);

        painelLista.setLayout(
                new BoxLayout(
                        painelLista,
                        BoxLayout.Y_AXIS
                )
        );

        painelLista.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        0,
                        10,
                        0
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        painelLista
                );

        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelConteudo.add(
                painelTopo,
                BorderLayout.NORTH
        );

        painelConteudo.add(
                scroll,
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

        painelInferior.add(
                botaoVoltar
        );

        Runnable atualizarLista =
                () -> atualizarPainelLista(
                        painelLista,
                        janela,
                        valorTotal,
                        valorPublicados,
                        valorRascunhos
                );

        botaoNovo.addActionListener(e ->
                abrirFormularioNovo(
                        janela,
                        atualizarLista
                )
        );

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

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

        atualizarLista.run();

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

        painelMarca.setOpaque(false);

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
                        "Administração de cenários"
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

        painel.add(
                painelMarca,
                BorderLayout.WEST
        );

        return painel;
    }

    private static JPanel criarCardResumo(
            String titulo,
            JLabel valor,
            Color destaque) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        10,
                        0
                )
        );

        card.setPreferredSize(
                new Dimension(
                        230,
                        72
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

        JPanel textos =
                new JPanel();

        textos.setOpaque(false);

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

        textos.add(
                labelTitulo
        );

        textos.add(
                Box.createVerticalStrut(
                        2
                )
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

    private static JLabel criarValorResumo() {

        JLabel label =
                new JLabel(
                        "0"
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        label.setForeground(
                TemaMoneyShield.MARINHO
        );

        return label;
    }

    private static JPanel criarCardCenario(
            JFrame janela,
            CenarioFinanceiro cenario,
            Runnable atualizarLista) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        14,
                        8
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        135
                )
        );

        card.setPreferredSize(
                new Dimension(
                        730,
                        135
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                cenario.isPublicado()
                        ? TemaMoneyShield.ROSA
                        : TemaMoneyShield.AMARELO
        );

        faixa.setPreferredSize(
                new Dimension(
                        6,
                        1
                )
        );

        JPanel informacoes =
                new JPanel();

        informacoes.setOpaque(false);

        informacoes.setLayout(
                new BoxLayout(
                        informacoes,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel categoria =
                new JLabel(
                        "CENÁRIO #"
                                + cenario.getId()
                );

        categoria.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        categoria.setForeground(
                cenario.isPublicado()
                        ? TemaMoneyShield.ROSA
                        : TemaMoneyShield.TEXTO_SECUNDARIO
        );

        categoria.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        cenario.getTitulo()
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

        JLabel descricao =
                new JLabel(
                        "<html>"
                                + "<div style='width:430px;'>"
                                + cenario.getDescricao()
                                + "</div>"
                                + "</html>"
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        descricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        String statusTexto =
                cenario.isPublicado()
                        ? "Publicado"
                        : "Rascunho";

        JLabel status =
                new JLabel(
                        statusTexto
                                + "  •  "
                                + cenario
                                .getOpcoes()
                                .size()
                                + " opção(ões)"
                );

        status.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        status.setForeground(
                cenario.isPublicado()
                        ? TemaMoneyShield.ROSA
                        : TemaMoneyShield.MALVA
        );

        status.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        informacoes.add(
                categoria
        );

        informacoes.add(
                Box.createVerticalStrut(
                        3
                )
        );

        informacoes.add(
                titulo
        );

        informacoes.add(
                Box.createVerticalStrut(
                        4
                )
        );

        informacoes.add(
                descricao
        );

        informacoes.add(
                Box.createVerticalStrut(
                        5
                )
        );

        informacoes.add(
                status
        );

        JPanel botoes =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                8,
                                8
                        )
                );

        botoes.setOpaque(false);

        botoes.setPreferredSize(
                new Dimension(
                        245,
                        82
                )
        );

        JButton editar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Editar"
                );

        JButton opcoesBotao =
                criarBotaoAzul(
                        "Opções"
                );

        JButton publicar =
                criarBotaoPublicacao(
                        cenario.isPublicado()
                                ? "Despublicar"
                                : "Publicar",
                        cenario.isPublicado()
                );

        JButton excluir =
                criarBotaoExcluir(
                        "Excluir"
                );

        editar.addActionListener(e ->
                abrirFormularioEditar(
                        janela,
                        cenario,
                        atualizarLista
                )
        );

        opcoesBotao.addActionListener(e ->
                abrirGerenciamentoOpcoes(
                        janela,
                        cenario,
                        atualizarLista
                )
        );

        publicar.addActionListener(e -> {

            if (!cenario.isPublicado()
                    && cenario
                    .getOpcoes()
                    .isEmpty()) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Adicione pelo menos uma opção antes de publicar o cenário.",
                        "Cenário sem opções",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            cenario.setPublicado(
                    !cenario.isPublicado()
            );

            atualizarLista.run();
        });

        excluir.addActionListener(e -> {

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janela,
                            "Deseja excluir o cenário \""
                                    + cenario.getTitulo()
                                    + "\"?",
                            "Excluir cenário",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (resposta
                    == JOptionPane.YES_OPTION) {

                RepositorioCenarios
                        .excluirCenario(
                                cenario.getId()
                        );

                atualizarLista.run();
            }
        });

        botoes.add(
                editar
        );

        botoes.add(
                opcoesBotao
        );

        botoes.add(
                publicar
        );

        botoes.add(
                excluir
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                informacoes,
                BorderLayout.CENTER
        );

        card.add(
                botoes,
                BorderLayout.EAST
        );

        return card;
    }

    private static JButton criarBotaoAzul(
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
                TemaMoneyShield.MARINHO
        );

        botao.setBackground(
                TemaMoneyShield.AZUL
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
                        8,
                        13,
                        8,
                        13
                )
        );

        return botao;
    }

    private static JButton criarBotaoPublicacao(
            String texto,
            boolean publicado) {

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
                publicado
                        ? TemaMoneyShield.MARINHO
                        : TemaMoneyShield.BRANCO
        );

        botao.setBackground(
                publicado
                        ? TemaMoneyShield.AMARELO
                        : TemaMoneyShield.MALVA
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
                        8,
                        13,
                        8,
                        13
                )
        );

        return botao;
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

        botao.setFocusPainted(false);
        botao.setOpaque(true);

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
                                13,
                                8,
                                13
                        )
                )
        );

        return botao;
    }

    private static void abrirFormularioNovo(
            JFrame janela,
            Runnable atualizarLista) {

        JTextField campoTitulo =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoTitulo
        );

        JTextArea campoDescricao =
                criarCampoTexto(
                        5,
                        30,
                        ""
                );

        JPanel painel =
                criarFormularioCenario(
                        campoTitulo,
                        campoDescricao
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        painel,
                        "Novo Cenário",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String titulo =
                campoTitulo
                        .getText()
                        .trim();

        String descricao =
                campoDescricao
                        .getText()
                        .trim();

        if (titulo.isEmpty()
                || descricao.isEmpty()) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Preencha o título e a descrição.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        CenarioFinanceiro cenario =
                RepositorioCenarios
                        .adicionarCenario(
                                titulo,
                                descricao,
                                false
                        );

        atualizarLista.run();

        JOptionPane.showMessageDialog(
                janela,
                "Cenário criado. Agora configure suas opções.",
                "Cenário criado",
                JOptionPane.INFORMATION_MESSAGE
        );

        abrirGerenciamentoOpcoes(
                janela,
                cenario,
                atualizarLista
        );
    }

    private static void abrirFormularioEditar(
            JFrame janela,
            CenarioFinanceiro cenario,
            Runnable atualizarLista) {

        JTextField campoTitulo =
                new JTextField(
                        cenario.getTitulo()
                );

        TemaMoneyShield.estilizarCampoTexto(
                campoTitulo
        );

        JTextArea campoDescricao =
                criarCampoTexto(
                        5,
                        30,
                        cenario.getDescricao()
                );

        JPanel painel =
                criarFormularioCenario(
                        campoTitulo,
                        campoDescricao
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        painel,
                        "Editar Cenário",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String titulo =
                campoTitulo
                        .getText()
                        .trim();

        String descricao =
                campoDescricao
                        .getText()
                        .trim();

        if (titulo.isEmpty()
                || descricao.isEmpty()) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Preencha o título e a descrição.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        cenario.setTitulo(
                titulo
        );

        cenario.setDescricao(
                descricao
        );

        atualizarLista.run();
    }

    private static JPanel criarFormularioCenario(
            JTextField campoTitulo,
            JTextArea campoDescricao) {

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

        JLabel labelTitulo =
                criarLabelFormulario(
                        "Título do cenário"
                );

        JLabel labelDescricao =
                criarLabelFormulario(
                        "Descrição da situação"
                );

        JScrollPane scrollDescricao =
                new JScrollPane(
                        campoDescricao
                );

        scrollDescricao.setPreferredSize(
                new Dimension(
                        380,
                        125
                )
        );

        scrollDescricao.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
                )
        );

        painel.add(
                labelTitulo
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                campoTitulo
        );

        painel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painel.add(
                labelDescricao
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                scrollDescricao
        );

        return painel;
    }

    private static void abrirGerenciamentoOpcoes(
            JFrame janela,
            CenarioFinanceiro cenario,
            Runnable atualizarListaPrincipal) {

        JDialog dialog =
                new JDialog(
                        janela,
                        "MoneyShield - Opções do Cenário",
                        true
                );

        dialog.setSize(
                700,
                500
        );

        dialog.setLocationRelativeTo(
                janela
        );

        dialog.setResizable(
                false
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

        textosCabecalho.setOpaque(false);

        textosCabecalho.setLayout(
                new BoxLayout(
                        textosCabecalho,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Opções do cenário"
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

        JLabel nomeCenario =
                new JLabel(
                        cenario.getTitulo()
                );

        nomeCenario.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        nomeCenario.setForeground(
                TemaMoneyShield.AZUL
        );

        nomeCenario.setAlignmentX(
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
                nomeCenario
        );

        cabecalho.add(
                textosCabecalho,
                BorderLayout.WEST
        );

        JPanel lista =
                new JPanel();

        lista.setOpaque(false);

        lista.setLayout(
                new BoxLayout(
                        lista,
                        BoxLayout.Y_AXIS
                )
        );

        lista.setBorder(
                BorderFactory.createEmptyBorder(
                        16,
                        20,
                        12,
                        20
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        lista
                );

        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        JPanel rodape =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        rodape.setBackground(
                TemaMoneyShield.FUNDO
        );

        rodape.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        12,
                        6,
                        12
                )
        );

        JButton adicionar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Adicionar Opção"
                );

        JButton fechar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Fechar"
                );

        rodape.add(
                adicionar
        );

        rodape.add(
                fechar
        );

        final Runnable[] atualizarOpcoes =
                new Runnable[1];

        atualizarOpcoes[0] = () -> {

            lista.removeAll();

            ArrayList<OpcaoCenario> opcoes =
                    cenario.getOpcoes();

            if (opcoes.isEmpty()) {

                JPanel vazio =
                        TemaMoneyShield.criarCard();

                vazio.setLayout(
                        new BoxLayout(
                                vazio,
                                BoxLayout.Y_AXIS
                        )
                );

                vazio.setAlignmentX(
                        Component.LEFT_ALIGNMENT
                );

                vazio.setMaximumSize(
                        new Dimension(
                                Integer.MAX_VALUE,
                                110
                        )
                );

                JLabel tituloVazio =
                        new JLabel(
                                "Nenhuma opção cadastrada"
                        );

                tituloVazio.setFont(
                        TemaMoneyShield.FONTE_SUBTITULO
                );

                tituloVazio.setForeground(
                        TemaMoneyShield.MARINHO
                );

                tituloVazio.setAlignmentX(
                        Component.CENTER_ALIGNMENT
                );

                JLabel textoVazio =
                        new JLabel(
                                "Adicione pelo menos uma opção para poder publicar este cenário."
                        );

                textoVazio.setFont(
                        TemaMoneyShield.FONTE_TEXTO_PEQUENO
                );

                textoVazio.setForeground(
                        TemaMoneyShield.TEXTO_SECUNDARIO
                );

                textoVazio.setAlignmentX(
                        Component.CENTER_ALIGNMENT
                );

                vazio.add(
                        Box.createVerticalGlue()
                );

                vazio.add(
                        tituloVazio
                );

                vazio.add(
                        Box.createVerticalStrut(
                                6
                        )
                );

                vazio.add(
                        textoVazio
                );

                vazio.add(
                        Box.createVerticalGlue()
                );

                lista.add(
                        vazio
                );

            } else {

                for (OpcaoCenario opcao
                        : opcoes) {

                    JPanel card =
                            TemaMoneyShield.criarCard();

                    card.setLayout(
                            new BorderLayout(
                                    12,
                                    0
                            )
                    );

                    card.setMaximumSize(
                            new Dimension(
                                    Integer.MAX_VALUE,
                                    88
                            )
                    );

                    card.setAlignmentX(
                            Component.LEFT_ALIGNMENT
                    );

                    JPanel faixa =
                            new JPanel();

                    faixa.setBackground(
                            TemaMoneyShield.AZUL
                    );

                    faixa.setPreferredSize(
                            new Dimension(
                                    5,
                                    1
                            )
                    );

                    JPanel textos =
                            new JPanel();

                    textos.setOpaque(false);

                    textos.setLayout(
                            new BoxLayout(
                                    textos,
                                    BoxLayout.Y_AXIS
                            )
                    );

                    JLabel codigo =
                            new JLabel(
                                    "OPÇÃO #"
                                            + opcao.getId()
                            );

                    codigo.setFont(
                            new Font(
                                    "SansSerif",
                                    Font.BOLD,
                                    9
                            )
                    );

                    codigo.setForeground(
                            TemaMoneyShield.AZUL
                    );

                    codigo.setAlignmentX(
                            Component.LEFT_ALIGNMENT
                    );

                    JLabel nome =
                            new JLabel(
                                    opcao.getTitulo()
                            );

                    nome.setFont(
                            TemaMoneyShield.FONTE_SUBTITULO
                    );

                    nome.setForeground(
                            TemaMoneyShield.MARINHO
                    );

                    nome.setAlignmentX(
                            Component.LEFT_ALIGNMENT
                    );

                    textos.add(
                            codigo
                    );

                    textos.add(
                            Box.createVerticalStrut(
                                    3
                            )
                    );

                    textos.add(
                            nome
                    );

                    JPanel botoes =
                            new JPanel(
                                    new FlowLayout(
                                            FlowLayout.RIGHT,
                                            8,
                                            0
                                    )
                            );

                    botoes.setOpaque(false);

                    JButton editar =
                            TemaMoneyShield.criarBotaoSecundario(
                                    "Editar"
                            );

                    JButton excluir =
                            criarBotaoExcluir(
                                    "Excluir"
                            );

                    editar.addActionListener(e -> {

                        editarOpcao(
                                dialog,
                                opcao
                        );

                        atualizarOpcoes[0].run();
                        atualizarListaPrincipal.run();
                    });

                    excluir.addActionListener(e -> {

                        int resposta =
                                JOptionPane.showConfirmDialog(
                                        dialog,
                                        "Deseja excluir a opção \""
                                                + opcao.getTitulo()
                                                + "\"?",
                                        "Excluir opção",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE
                                );

                        if (resposta
                                == JOptionPane.YES_OPTION) {

                            cenario.excluirOpcao(
                                    opcao.getId()
                            );

                            atualizarOpcoes[0].run();
                            atualizarListaPrincipal.run();
                        }
                    });

                    botoes.add(
                            editar
                    );

                    botoes.add(
                            excluir
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
                            botoes,
                            BorderLayout.EAST
                    );

                    lista.add(
                            card
                    );

                    lista.add(
                            Box.createVerticalStrut(
                                    10
                            )
                    );
                }
            }

            lista.revalidate();
            lista.repaint();
        };

        adicionar.addActionListener(e -> {

            adicionarOpcao(
                    dialog,
                    cenario
            );

            atualizarOpcoes[0].run();
            atualizarListaPrincipal.run();
        });

        fechar.addActionListener(e -> {

            dialog.dispose();
            atualizarListaPrincipal.run();
        });

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

        atualizarOpcoes[0].run();

        dialog.setContentPane(
                painelPrincipal
        );

        dialog.setVisible(
                true
        );
    }

    private static void adicionarOpcao(
            Component pai,
            CenarioFinanceiro cenario) {

        JTextField titulo =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                titulo
        );

        JTextArea consequencia =
                criarCampoTexto(
                        8,
                        30,
                        ""
                );

        JPanel painel =
                criarFormularioOpcao(
                        titulo,
                        consequencia
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        pai,
                        painel,
                        "Adicionar Opção",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String nome =
                titulo
                        .getText()
                        .trim();

        String resultado =
                consequencia
                        .getText()
                        .trim();

        if (nome.isEmpty()
                || resultado.isEmpty()) {

            JOptionPane.showMessageDialog(
                    pai,
                    "Preencha o título e a consequência.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        cenario.adicionarOpcao(
                nome,
                resultado
        );
    }

    private static void editarOpcao(
            Component pai,
            OpcaoCenario opcao) {

        JTextField titulo =
                new JTextField(
                        opcao.getTitulo()
                );

        TemaMoneyShield.estilizarCampoTexto(
                titulo
        );

        JTextArea consequencia =
                criarCampoTexto(
                        8,
                        30,
                        opcao.getConsequencia()
                );

        JPanel painel =
                criarFormularioOpcao(
                        titulo,
                        consequencia
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        pai,
                        painel,
                        "Editar Opção",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String nome =
                titulo
                        .getText()
                        .trim();

        String resultado =
                consequencia
                        .getText()
                        .trim();

        if (nome.isEmpty()
                || resultado.isEmpty()) {

            JOptionPane.showMessageDialog(
                    pai,
                    "Preencha o título e a consequência.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        opcao.setTitulo(
                nome
        );

        opcao.setConsequencia(
                resultado
        );
    }

    private static JPanel criarFormularioOpcao(
            JTextField titulo,
            JTextArea consequencia) {

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

        JLabel labelTitulo =
                criarLabelFormulario(
                        "Título da opção"
                );

        JLabel labelConsequencia =
                criarLabelFormulario(
                        "Consequência / feedback"
                );

        JScrollPane scrollConsequencia =
                new JScrollPane(
                        consequencia
                );

        scrollConsequencia.setPreferredSize(
                new Dimension(
                        390,
                        170
                )
        );

        scrollConsequencia.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
                )
        );

        painel.add(
                labelTitulo
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
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
                labelConsequencia
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                scrollConsequencia
        );

        return painel;
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

    private static JTextArea criarCampoTexto(
            int linhas,
            int colunas,
            String texto) {

        JTextArea campo =
                new JTextArea(
                        texto,
                        linhas,
                        colunas
                );

        campo.setLineWrap(
                true
        );

        campo.setWrapStyleWord(
                true
        );

        campo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        campo.setForeground(
                TemaMoneyShield.MARINHO
        );

        campo.setBackground(
                TemaMoneyShield.BRANCO
        );

        campo.setCaretColor(
                TemaMoneyShield.MARINHO
        );

        campo.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        10,
                        8,
                        10
                )
        );

        return campo;
    }

    private static void atualizarPainelLista(
            JPanel painelLista,
            JFrame janela,
            JLabel valorTotal,
            JLabel valorPublicados,
            JLabel valorRascunhos) {

        painelLista.removeAll();

        ArrayList<CenarioFinanceiro> cenarios =
                RepositorioCenarios.getCenarios();

        int total =
                RepositorioCenarios.quantidade();

        int publicados =
                RepositorioCenarios.quantidadePublicados();

        valorTotal.setText(
                String.valueOf(
                        total
                )
        );

        valorPublicados.setText(
                String.valueOf(
                        publicados
                )
        );

        valorRascunhos.setText(
                String.valueOf(
                        total
                                - publicados
                )
        );

        if (cenarios.isEmpty()) {

            JPanel cardVazio =
                    TemaMoneyShield.criarCard();

            cardVazio.setLayout(
                    new BoxLayout(
                            cardVazio,
                            BoxLayout.Y_AXIS
                    )
            );

            cardVazio.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            cardVazio.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            145
                    )
            );

            JLabel titulo =
                    new JLabel(
                            "Nenhum cenário cadastrado"
                    );

            titulo.setFont(
                    TemaMoneyShield.FONTE_SUBTITULO
            );

            titulo.setForeground(
                    TemaMoneyShield.MARINHO
            );

            titulo.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            JLabel texto =
                    new JLabel(
                            "Crie um cenário e configure suas opções para disponibilizá-lo aos alunos."
                    );

            texto.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            texto.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            texto.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            cardVazio.add(
                    Box.createVerticalGlue()
            );

            cardVazio.add(
                    titulo
            );

            cardVazio.add(
                    Box.createVerticalStrut(
                            8
                    )
            );

            cardVazio.add(
                    texto
            );

            cardVazio.add(
                    Box.createVerticalGlue()
            );

            painelLista.add(
                    Box.createVerticalStrut(
                            16
                    )
            );

            painelLista.add(
                    cardVazio
            );

        } else {

            Runnable atualizar =
                    () -> atualizarPainelLista(
                            painelLista,
                            janela,
                            valorTotal,
                            valorPublicados,
                            valorRascunhos
                    );

            for (CenarioFinanceiro cenario
                    : cenarios) {

                painelLista.add(
                        criarCardCenario(
                                janela,
                                cenario,
                                atualizar
                        )
                );

                painelLista.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }

        painelLista.revalidate();
        painelLista.repaint();
    }
}