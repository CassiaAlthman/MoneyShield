package br.com.moneyshield.ui.student;

import br.com.moneyshield.model.MetaFinanceira;
import br.com.moneyshield.repository.RepositorioMetas;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MetasFinanceiras {

    private static final NumberFormat formatoDinheiro =
            NumberFormat.getCurrencyInstance(
                    Locale.of(
                            "pt",
                            "BR"
                    )
            );

    public static void abrir(
            JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Metas Financeiras"
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
                                18
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

        JPanel painelIntroducao =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        painelIntroducao.setOpaque(
                false
        );

        JPanel painelTitulos =
                new JPanel();

        painelTitulos.setOpaque(
                false
        );

        painelTitulos.setLayout(
                new BoxLayout(
                        painelTitulos,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Metas Financeiras"
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
                        "Transforme seus objetivos em planos e acompanhe sua evolução."
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

        painelTitulos.add(
                titulo
        );

        painelTitulos.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelTitulos.add(
                subtitulo
        );

        JButton botaoNovaMeta =
                TemaMoneyShield.criarBotaoPrimario(
                        "Nova Meta"
                );

        painelIntroducao.add(
                painelTitulos,
                BorderLayout.CENTER
        );

        painelIntroducao.add(
                botaoNovaMeta,
                BorderLayout.EAST
        );

        JPanel listaMetas =
                new JPanel();

        listaMetas.setOpaque(
                false
        );

        listaMetas.setLayout(
                new BoxLayout(
                        listaMetas,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scroll =
                new JScrollPane(
                        listaMetas
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
                painelIntroducao,
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

        botaoVoltar.addActionListener(e -> {

            janela.dispose();

            telaAnterior.setVisible(
                    true
            );
        });

        painelInferior.add(
                botaoVoltar
        );

        botaoNovaMeta.addActionListener(e -> {

            criarNovaMeta(
                    janela,
                    listaMetas
            );
        });

        atualizarLista(
                janela,
                listaMetas
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
                        "Planejamento de metas financeiras"
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

    private static void criarNovaMeta(
            JFrame janela,
            JPanel listaMetas) {

        JPanel painelFormulario =
                new JPanel();

        painelFormulario.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelFormulario.setLayout(
                new BoxLayout(
                        painelFormulario,
                        BoxLayout.Y_AXIS
                )
        );

        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        JLabel labelNome =
                criarLabelCampo(
                        "Nome da meta"
                );

        JTextField campoNome =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoNome
        );

        JLabel labelValor =
                criarLabelCampo(
                        "Valor objetivo (R$)"
                );

        JTextField campoValor =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoValor
        );

        painelFormulario.add(
                labelNome
        );

        painelFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painelFormulario.add(
                campoNome
        );

        painelFormulario.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelFormulario.add(
                labelValor
        );

        painelFormulario.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painelFormulario.add(
                campoValor
        );

        painelFormulario.setPreferredSize(
                new Dimension(
                        380,
                        170
                )
        );

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        painelFormulario,
                        "MoneyShield - Nova Meta",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String nome =
                campoNome
                        .getText()
                        .trim();

        String valorTexto =
                campoValor
                        .getText()
                        .trim()
                        .replace(
                                ",",
                                "."
                        );

        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Digite um nome para a meta.",
                    "Nome inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            double valorObjetivo =
                    Double.parseDouble(
                            valorTexto
                    );

            if (valorObjetivo <= 0) {

                JOptionPane.showMessageDialog(
                        janela,
                        "O valor objetivo deve ser maior que zero.",
                        "Valor inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            MetaFinanceira metaCriada =
                    RepositorioMetas.criarMeta(
                            nome,
                            valorObjetivo
                    );

            if (metaCriada == null) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível criar a meta.",
                        "Erro ao criar meta",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            atualizarLista(
                    janela,
                    listaMetas
            );

            JOptionPane.showMessageDialog(
                    janela,
                    "Meta criada com sucesso!",
                    "Nova meta",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Digite um valor numérico válido.",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private static void atualizarLista(
            JFrame janela,
            JPanel listaMetas) {

        listaMetas.removeAll();

        ArrayList<MetaFinanceira> metas =
                RepositorioMetas.getMetas();

        if (metas.isEmpty()) {

            JPanel cardVazio =
                    TemaMoneyShield.criarCard();

            cardVazio.setLayout(
                    new BorderLayout(
                            15,
                            0
                    )
            );

            cardVazio.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            cardVazio.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            125
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

            JLabel mensagem =
                    new JLabel(
                            "<html>"
                                    + "<b>Você ainda não possui metas financeiras.</b>"
                                    + "<br><br>"
                                    + "Crie uma meta para começar a acompanhar seu progresso."
                                    + "</html>"
                    );

            mensagem.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            mensagem.setForeground(
                    TemaMoneyShield.MARINHO
            );

            cardVazio.add(
                    faixa,
                    BorderLayout.WEST
            );

            cardVazio.add(
                    mensagem,
                    BorderLayout.CENTER
            );

            listaMetas.add(
                    cardVazio
            );

        } else {

            for (int i = 0;
                 i < metas.size();
                 i++) {

                MetaFinanceira meta =
                        metas.get(i);

                JPanel painelMeta =
                        criarPainelMeta(
                                janela,
                                listaMetas,
                                meta,
                                i
                        );

                listaMetas.add(
                        painelMeta
                );

                listaMetas.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }

        listaMetas.revalidate();
        listaMetas.repaint();
    }

    private static JPanel criarPainelMeta(
            JFrame janela,
            JPanel listaMetas,
            MetaFinanceira meta,
            int indice) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        14,
                        10
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        175
                )
        );

        JPanel faixa =
                new JPanel();

        faixa.setBackground(
                obterCorMeta(
                        indice,
                        meta.metaAtingida()
                )
        );

        faixa.setPreferredSize(
                new Dimension(
                        7,
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

        JLabel nome =
                new JLabel(
                        meta.getNome()
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

        JLabel valores =
                new JLabel(
                        "Guardado: "
                                + formatoDinheiro.format(
                                meta.getValorAtual()
                        )
                                + " de "
                                + formatoDinheiro.format(
                                meta.getValorObjetivo()
                        )
                );

        valores.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        valores.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        valores.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel restante =
                new JLabel();

        if (meta.metaAtingida()) {

            restante.setText(
                    "Objetivo alcançado"
            );

            restante.setForeground(
                    TemaMoneyShield.MALVA
            );

        } else {

            restante.setText(
                    "Faltam "
                            + formatoDinheiro.format(
                            meta.getValorRestante()
                    )
            );

            restante.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );
        }

        restante.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        restante.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JProgressBar progresso =
                new JProgressBar(
                        0,
                        100
                );

        TemaMoneyShield.estilizarBarraProgresso(
                progresso
        );

        progresso.setValue(
                meta.getProgressoPercentual()
        );

        progresso.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        progresso.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        24
                )
        );

        if (meta.metaAtingida()) {

            progresso.setString(
                    "Meta atingida!"
            );

        } else {

            progresso.setString(
                    meta.getProgressoPercentual()
                            + "%"
            );
        }

        conteudo.add(
                nome
        );

        conteudo.add(
                Box.createVerticalStrut(
                        5
                )
        );

        conteudo.add(
                valores
        );

        conteudo.add(
                Box.createVerticalStrut(
                        4
                )
        );

        conteudo.add(
                restante
        );

        conteudo.add(
                Box.createVerticalStrut(
                        10
                )
        );

        conteudo.add(
                progresso
        );

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        painelBotoes.setOpaque(
                false
        );

        JButton botaoAdicionar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Adicionar valor"
                );

        JButton botaoExcluir =
                TemaMoneyShield.criarBotaoSecundario(
                        "Excluir"
                );

        if (meta.metaAtingida()) {

            botaoAdicionar.setText(
                    "Adicionar mais"
            );
        }

        botaoAdicionar.addActionListener(e -> {

            adicionarValor(
                    janela,
                    listaMetas,
                    meta
            );
        });

        botaoExcluir.addActionListener(e -> {

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janela,
                            "Deseja excluir a meta \""
                                    + meta.getNome()
                                    + "\"?",
                            "Excluir meta",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (resposta
                    != JOptionPane.YES_OPTION) {

                return;
            }

            boolean excluida =
                    RepositorioMetas.excluirMeta(
                            meta.getId()
                    );

            if (!excluida) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível excluir a meta.",
                        "Erro ao excluir meta",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            atualizarLista(
                    janela,
                    listaMetas
            );
        });

        painelBotoes.add(
                botaoAdicionar
        );

        painelBotoes.add(
                botaoExcluir
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                conteudo,
                BorderLayout.CENTER
        );

        card.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        return card;
    }

    private static void adicionarValor(
            JFrame janela,
            JPanel listaMetas,
            MetaFinanceira meta) {

        JTextField campoValor =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoValor
        );

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

        JLabel titulo =
                new JLabel(
                        meta.getNome()
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

        JLabel atual =
                new JLabel(
                        "Valor atual: "
                                + formatoDinheiro.format(
                                meta.getValorAtual()
                        )
                );

        atual.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        atual.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        atual.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel labelValor =
                criarLabelCampo(
                        "Valor que deseja adicionar (R$)"
                );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painel.add(
                atual
        );

        painel.add(
                Box.createVerticalStrut(
                        16
                )
        );

        painel.add(
                labelValor
        );

        painel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        painel.add(
                campoValor
        );

        painel.setPreferredSize(
                new Dimension(
                        370,
                        150
                )
        );

        int resposta =
                JOptionPane.showConfirmDialog(
                        janela,
                        painel,
                        "MoneyShield - Atualizar Meta",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (resposta
                != JOptionPane.OK_OPTION) {

            return;
        }

        String entrada =
                campoValor
                        .getText()
                        .trim()
                        .replace(
                                ",",
                                "."
                        );

        try {

            double valor =
                    Double.parseDouble(
                            entrada
                    );

            if (valor <= 0) {

                JOptionPane.showMessageDialog(
                        janela,
                        "O valor deve ser maior que zero.",
                        "Valor inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean jaEstavaConcluida =
                    meta.metaAtingida();

            boolean atualizada =
                    RepositorioMetas.adicionarValor(
                            meta.getId(),
                            valor
                    );

            if (!atualizada) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível atualizar a meta.",
                        "Erro ao atualizar meta",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            boolean atingiuAgora =
                    !jaEstavaConcluida
                            && meta.metaAtingida();

            atualizarLista(
                    janela,
                    listaMetas
            );

            if (atingiuAgora) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Parabéns!\n\n"
                                + "Você atingiu a meta:\n"
                                + meta.getNome(),
                        "Meta atingida",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    janela,
                    "Digite um valor numérico válido.",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private static JLabel criarLabelCampo(
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

    private static Color obterCorMeta(
            int indice,
            boolean concluida) {

        if (concluida) {
            return TemaMoneyShield.MALVA;
        }

        int posicao =
                indice % 3;

        if (posicao == 0) {
            return TemaMoneyShield.ROSA;
        }

        if (posicao == 1) {
            return TemaMoneyShield.AZUL;
        }

        return TemaMoneyShield.AMARELO;
    }
}