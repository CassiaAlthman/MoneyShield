package br.com.moneyshield.ui.admin;

import br.com.moneyshield.model.Missao;
import br.com.moneyshield.repository.RepositorioMissoes;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MissoesDesafiosAdmin {

    public static void abrir(
            JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Gerenciamento de Missões"
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
                                0,
                                16
                        )
                );

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        36,
                        12,
                        36
                )
        );

        JPanel painelTitulo =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        painelTitulo.setOpaque(
                false
        );

        JPanel painelTextos =
                new JPanel();

        painelTextos.setOpaque(
                false
        );

        painelTextos.setLayout(
                new BoxLayout(
                        painelTextos,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Gerenciamento de Missões"
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
                        "Crie e mantenha os desafios disponíveis para os alunos."
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

        painelTextos.add(
                titulo
        );

        painelTextos.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelTextos.add(
                subtitulo
        );

        JButton botaoAdicionar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Adicionar Missão"
                );

        botaoAdicionar.setPreferredSize(
                new Dimension(
                        150,
                        42
                )
        );

        JPanel painelBotao =
                new JPanel(
                        new GridBagLayout()
                );

        painelBotao.setOpaque(
                false
        );

        painelBotao.add(
                botaoAdicionar
        );

        painelTitulo.add(
                painelTextos,
                BorderLayout.WEST
        );

        painelTitulo.add(
                painelBotao,
                BorderLayout.EAST
        );

        JLabel valorQuantidade =
                new JLabel(
                        "0"
                );

        valorQuantidade.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        valorQuantidade.setForeground(
                TemaMoneyShield.MARINHO
        );

        JPanel cardResumo =
                criarCardResumo(
                        valorQuantidade
                );

        JPanel painelTopo =
                new JPanel();

        painelTopo.setOpaque(
                false
        );

        painelTopo.setLayout(
                new BoxLayout(
                        painelTopo,
                        BoxLayout.Y_AXIS
                )
        );

        painelTitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cardResumo.setAlignmentX(
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
                cardResumo
        );

        JPanel listaMissoes =
                new JPanel();

        listaMissoes.setOpaque(
                false
        );

        listaMissoes.setLayout(
                new BoxLayout(
                        listaMissoes,
                        BoxLayout.Y_AXIS
                )
        );

        listaMissoes.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        0,
                        10,
                        0
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        listaMissoes
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

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaAnterior.setVisible(
                    true
            );
        });

        botaoAdicionar.addActionListener(e -> {

            adicionarMissao(
                    janela,
                    listaMissoes,
                    valorQuantidade
            );
        });

        atualizarLista(
                janela,
                listaMissoes,
                valorQuantidade
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
                        "Administração de missões"
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
            JLabel valorQuantidade) {

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
                        72
                )
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                TemaMoneyShield.ROSA
        );

        faixa.setPreferredSize(
                new Dimension(
                        6,
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

        JLabel titulo =
                new JLabel(
                        "Missões cadastradas"
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        titulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        textos.add(
                titulo
        );

        textos.add(
                Box.createVerticalStrut(
                        2
                )
        );

        textos.add(
                valorQuantidade
        );

        JLabel descricao =
                new JLabel(
                        "Desafios atualmente disponíveis no repositório"
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
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
                descricao,
                BorderLayout.EAST
        );

        return card;
    }

    private static void atualizarLista(
            JFrame janela,
            JPanel listaMissoes,
            JLabel valorQuantidade) {

        listaMissoes.removeAll();

        ArrayList<Missao> missoes =
                RepositorioMissoes.getMissoes();

        valorQuantidade.setText(
                String.valueOf(
                        RepositorioMissoes.quantidade()
                )
        );

        if (missoes.isEmpty()) {

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
                            150
                    )
            );

            JLabel titulo =
                    new JLabel(
                            "Nenhuma missão cadastrada"
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
                            "Adicione uma missão para disponibilizar um novo desafio aos alunos."
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

            listaMissoes.add(
                    Box.createVerticalStrut(
                            16
                    )
            );

            listaMissoes.add(
                    cardVazio
            );

        } else {

            for (Missao missao
                    : missoes) {

                JPanel painelMissao =
                        criarPainelMissao(
                                janela,
                                listaMissoes,
                                valorQuantidade,
                                missao
                        );

                listaMissoes.add(
                        painelMissao
                );

                listaMissoes.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }

        listaMissoes.revalidate();
        listaMissoes.repaint();
    }

    private static JPanel criarPainelMissao(
            JFrame janela,
            JPanel listaMissoes,
            JLabel valorQuantidade,
            Missao missao) {

        JPanel painel =
                TemaMoneyShield.criarCard();

        painel.setLayout(
                new BorderLayout(
                        14,
                        8
                )
        );

        painel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        126
                )
        );

        painel.setPreferredSize(
                new Dimension(
                        730,
                        126
                )
        );

        painel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                TemaMoneyShield.ROSA
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

        JLabel categoria =
                new JLabel(
                        "MISSÃO #"
                                + missao.getId()
                );

        categoria.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        categoria.setForeground(
                TemaMoneyShield.ROSA
        );

        categoria.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        missao.getTitulo()
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
                                + "<div style='width:470px;'>"
                                + missao.getDescricao()
                                + "</div>"
                                + "</html>"
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        descricao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        descricao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel recompensa =
                new JLabel(
                        "+100 XP ao aluno"
                );

        recompensa.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        recompensa.setForeground(
                TemaMoneyShield.ROSA
        );

        recompensa.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelTexto.add(
                categoria
        );

        painelTexto.add(
                Box.createVerticalStrut(
                        3
                )
        );

        painelTexto.add(
                titulo
        );

        painelTexto.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelTexto.add(
                descricao
        );

        painelTexto.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelTexto.add(
                recompensa
        );

        JPanel painelBotoes =
                new JPanel();

        painelBotoes.setOpaque(
                false
        );

        painelBotoes.setLayout(
                new BoxLayout(
                        painelBotoes,
                        BoxLayout.Y_AXIS
                )
        );

        JButton botaoEditar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Editar"
                );

        JButton botaoExcluir =
                criarBotaoExcluir(
                        "Excluir"
                );

        botaoEditar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        botaoExcluir.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        botaoEditar.setMaximumSize(
                new Dimension(
                        100,
                        36
                )
        );

        botaoExcluir.setMaximumSize(
                new Dimension(
                        100,
                        36
                )
        );

        painelBotoes.add(
                Box.createVerticalGlue()
        );

        painelBotoes.add(
                botaoEditar
        );

        painelBotoes.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painelBotoes.add(
                botaoExcluir
        );

        painelBotoes.add(
                Box.createVerticalGlue()
        );

        botaoEditar.addActionListener(e -> {

            editarMissao(
                    janela,
                    listaMissoes,
                    valorQuantidade,
                    missao
            );
        });

        botaoExcluir.addActionListener(e -> {

            excluirMissao(
                    janela,
                    listaMissoes,
                    valorQuantidade,
                    missao
            );
        });

        painel.add(
                faixa,
                BorderLayout.WEST
        );

        painel.add(
                painelTexto,
                BorderLayout.CENTER
        );

        painel.add(
                painelBotoes,
                BorderLayout.EAST
        );

        return painel;
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

    private static void adicionarMissao(
            JFrame janela,
            JPanel listaMissoes,
            JLabel valorQuantidade) {

        JTextField campoTitulo =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoTitulo
        );

        JTextArea campoDescricao =
                criarCampoDescricao(
                        ""
                );

        JComboBox<String> campoTipo =
                criarComboTipoMissao(
                        "ADMINISTRATIVA"
                );

        JPanel painel =
                criarFormularioMissao(
                        campoTitulo,
                        campoDescricao,
                        campoTipo
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        painel,
                        "Adicionar Missão",
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
                    "Preencha o título e a descrição da missão.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tipo =
                obterTipoMissao(
                        campoTipo
                );

        Missao criada =
                RepositorioMissoes.adicionarMissao(
                        titulo,
                        descricao,
                        tipo,
                        obterMetaPadrao(
                                tipo
                        )
                );

        if (criada == null) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Não foi possível adicionar a missão no banco.",
                    "Erro ao adicionar missão",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        atualizarLista(
                janela,
                listaMissoes,
                valorQuantidade
        );

        JOptionPane.showMessageDialog(
                janela,
                "Missão adicionada com sucesso.",
                "Missão adicionada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static void editarMissao(
            JFrame janela,
            JPanel listaMissoes,
            JLabel valorQuantidade,
            Missao missao) {

        JTextField campoTitulo =
                new JTextField(
                        missao.getTitulo()
                );

        TemaMoneyShield.estilizarCampoTexto(
                campoTitulo
        );

        JTextArea campoDescricao =
                criarCampoDescricao(
                        missao.getDescricao()
                );

        JComboBox<String> campoTipo =
                criarComboTipoMissao(
                        missao.getTipo()
                );

        JPanel painel =
                criarFormularioMissao(
                        campoTitulo,
                        campoDescricao,
                        campoTipo
                );

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        painel,
                        "Editar Missão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String novoTitulo =
                campoTitulo
                        .getText()
                        .trim();

        String novaDescricao =
                campoDescricao
                        .getText()
                        .trim();

        if (novoTitulo.isEmpty()
                || novaDescricao.isEmpty()) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Preencha o título e a descrição da missão.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tipo =
                obterTipoMissao(
                        campoTipo
                );

        boolean editada =
                RepositorioMissoes.editarMissao(
                        missao.getId(),
                        novoTitulo,
                        novaDescricao,
                        tipo,
                        obterMetaPadrao(
                                tipo
                        )
                );

        if (editada) {

            atualizarLista(
                    janela,
                    listaMissoes,
                    valorQuantidade
            );

            JOptionPane.showMessageDialog(
                    janela,
                    "Missão atualizada com sucesso.",
                    "Missão atualizada",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    janela,
                    "Não foi possível atualizar a missão no banco.",
                    "Erro ao atualizar missão",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static void excluirMissao(
            JFrame janela,
            JPanel listaMissoes,
            JLabel valorQuantidade,
            Missao missao) {

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        "Deseja realmente excluir a missão:\n\n"
                                + missao.getTitulo()
                                + "?",
                        "Excluir Missão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (resposta
                != JOptionPane.YES_OPTION) {

            return;
        }

        boolean excluida =
                RepositorioMissoes.excluirMissao(
                        missao.getId()
                );

        if (excluida) {

            atualizarLista(
                    janela,
                    listaMissoes,
                    valorQuantidade
            );

            JOptionPane.showMessageDialog(
                    janela,
                    "Missão excluída com sucesso.",
                    "Missão excluída",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private static JTextArea criarCampoDescricao(
            String texto) {

        JTextArea campo =
                new JTextArea(
                        texto,
                        5,
                        30
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

    private static JPanel criarFormularioMissao(
            JTextField campoTitulo,
            JTextArea campoDescricao,
            JComboBox<String> campoTipo) {

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
                        "Título da missão"
                );

        JLabel labelDescricao =
                criarLabelFormulario(
                        "Descrição"
                );

        JLabel labelTipo =
                criarLabelFormulario(
                        "Regra de conclusão"
                );

        JScrollPane scrollDescricao =
                new JScrollPane(
                        campoDescricao
                );

        scrollDescricao.setPreferredSize(
                new Dimension(
                        360,
                        110
                )
        );

        scrollDescricao.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
                )
        );

        campoTipo.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        campoTipo.setBackground(
                TemaMoneyShield.BRANCO
        );

        campoTipo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        campoTipo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel explicacao =
                new JLabel(
                        "<html>"
                                + "<div style='width:350px;'>"
                                + "A regra define como o MoneyShield verifica a conclusão. "
                                + "Missões sem regra automática não podem ser concluídas manualmente pelo aluno."
                                + "</div>"
                                + "</html>"
                );

        explicacao.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        explicacao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        explicacao.setAlignmentX(
                Component.LEFT_ALIGNMENT
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

        painel.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painel.add(
                labelTipo
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                campoTipo
        );

        painel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        painel.add(
                explicacao
        );

        return painel;
    }

    private static JComboBox<String> criarComboTipoMissao(
            String tipoAtual) {

        JComboBox<String> combo =
                new JComboBox<>(
                        new String[]{
                                "Registro de gastos - 7 dias",
                                "Planejamento 50/30/20",
                                "10 conteúdos/trilhas concluídos",
                                "Simulação de fatura",
                                "Reserva de emergência",
                                "Sem regra automática"
                        }
                );

        combo.setSelectedIndex(
                indiceTipoMissao(
                        tipoAtual
                )
        );

        return combo;
    }

    private static String obterTipoMissao(
            JComboBox<String> combo) {

        int indice =
                combo.getSelectedIndex();

        if (indice == 0) {
            return "REGISTRO_GASTOS";
        }

        if (indice == 1) {
            return "PLANEJAMENTO";
        }

        if (indice == 2) {
            return "CONTEUDOS";
        }

        if (indice == 3) {
            return "SIMULACAO_FATURA";
        }

        if (indice == 4) {
            return "RESERVA";
        }

        return "ADMINISTRATIVA";
    }

    private static int indiceTipoMissao(
            String tipo) {

        if ("REGISTRO_GASTOS".equals(
                tipo
        )) {
            return 0;
        }

        if ("PLANEJAMENTO".equals(
                tipo
        )) {
            return 1;
        }

        if ("CONTEUDOS".equals(
                tipo
        )) {
            return 2;
        }

        if ("SIMULACAO_FATURA".equals(
                tipo
        )) {
            return 3;
        }

        if ("RESERVA".equals(
                tipo
        )) {
            return 4;
        }

        return 5;
    }

    private static Integer obterMetaPadrao(
            String tipo) {

        if ("REGISTRO_GASTOS".equals(
                tipo
        )) {
            return 7;
        }

        if ("CONTEUDOS".equals(
                tipo
        )) {
            return 10;
        }

        return 1;
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
}