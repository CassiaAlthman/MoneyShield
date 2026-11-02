package br.com.moneyshield.ui.admin;

import br.com.moneyshield.repository.RepositorioAvaliacoes;
import br.com.moneyshield.repository.RepositorioTrilhas;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TrilhasAdmin {

    public static void abrir(
            JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Gerenciamento de Trilhas"
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
                        14,
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

        JPanel textosTitulo =
                new JPanel();

        textosTitulo.setOpaque(
                false
        );

        textosTitulo.setLayout(
                new BoxLayout(
                        textosTitulo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "Gerenciamento de Trilhas"
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
                        "Cadastre, edite e organize as trilhas de aprendizagem do sistema."
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

        textosTitulo.add(
                titulo
        );

        textosTitulo.add(
                Box.createVerticalStrut(
                        5
                )
        );

        textosTitulo.add(
                subtitulo
        );

        JButton botaoAvaliacoes =
                criarBotaoDestaque(
                        "Avaliações"
                );

        botaoAvaliacoes.setPreferredSize(
                new Dimension(
                        125,
                        42
                )
        );

        painelTitulo.add(
                textosTitulo,
                BorderLayout.WEST
        );

        painelTitulo.add(
                botaoAvaliacoes,
                BorderLayout.EAST
        );

        DefaultListModel<String> modelo =
                new DefaultListModel<>();

        JList<String> lista =
                new JList<>(
                        modelo
                );

        lista.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        lista.setCellRenderer(
                new RenderizadorTrilha()
        );

        lista.setBackground(
                TemaMoneyShield.FUNDO
        );

        lista.setFixedCellHeight(
                70
        );

        lista.setSelectionBackground(
                TemaMoneyShield.FUNDO
        );

        lista.setSelectionForeground(
                TemaMoneyShield.MARINHO
        );

        JLabel labelQuantidade =
                criarValorResumo();

        JLabel labelComAvaliacao =
                criarValorResumo();

        JLabel labelSemAvaliacao =
                criarValorResumo();

        Runnable atualizar = () -> {

            modelo.clear();

            ArrayList<String> trilhas =
                    RepositorioTrilhas
                            .getTrilhas();

            int quantidadeComAvaliacao =
                    0;

            for (int i = 0;
                 i < trilhas.size();
                 i++) {

                int numero =
                        i + 1;

                String status;

                if (RepositorioAvaliacoes
                        .possuiAvaliacao(
                                numero
                        )) {

                    status =
                            " [Pré/Pós configurado]";

                    quantidadeComAvaliacao++;

                } else {

                    status =
                            " [Sem avaliação]";
                }

                modelo.addElement(
                        numero
                                + ". "
                                + trilhas.get(i)
                                + status
                );
            }

            labelQuantidade.setText(
                    String.valueOf(
                            trilhas.size()
                    )
            );

            labelComAvaliacao.setText(
                    String.valueOf(
                            quantidadeComAvaliacao
                    )
            );

            labelSemAvaliacao.setText(
                    String.valueOf(
                            trilhas.size()
                                    - quantidadeComAvaliacao
                    )
            );
        };

        JPanel painelResumo =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                12,
                                0
                        )
                );

        painelResumo.setOpaque(
                false
        );

        painelResumo.add(
                criarCardResumo(
                        "Trilhas",
                        labelQuantidade,
                        TemaMoneyShield.AZUL
                )
        );

        painelResumo.add(
                criarCardResumo(
                        "Com avaliação",
                        labelComAvaliacao,
                        TemaMoneyShield.ROSA
                )
        );

        painelResumo.add(
                criarCardResumo(
                        "Sem avaliação",
                        labelSemAvaliacao,
                        TemaMoneyShield.AMARELO
                )
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

        JScrollPane scroll =
                new JScrollPane(
                        lista
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
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

        JButton adicionar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Adicionar trilha"
                );

        JButton editar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Editar"
                );

        JButton excluir =
                criarBotaoExcluir(
                        "Excluir"
                );

        painelAcoes.add(
                adicionar
        );

        painelAcoes.add(
                editar
        );

        painelAcoes.add(
                excluir
        );

        JPanel painelCentro =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        painelCentro.setOpaque(
                false
        );

        JLabel tituloLista =
                new JLabel(
                        "Trilhas cadastradas"
                );

        tituloLista.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        tituloLista.setForeground(
                TemaMoneyShield.MARINHO
        );

        JPanel painelListaSuperior =
                new JPanel(
                        new BorderLayout()
                );

        painelListaSuperior.setOpaque(
                false
        );

        painelListaSuperior.add(
                tituloLista,
                BorderLayout.WEST
        );

        painelListaSuperior.add(
                painelAcoes,
                BorderLayout.EAST
        );

        painelCentro.add(
                painelListaSuperior,
                BorderLayout.NORTH
        );

        painelCentro.add(
                scroll,
                BorderLayout.CENTER
        );

        painelConteudo.add(
                painelTopo,
                BorderLayout.NORTH
        );

        painelConteudo.add(
                painelCentro,
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

        adicionar.addActionListener(e -> {

            JTextField campoNome =
                    new JTextField();

            TemaMoneyShield.estilizarCampoTexto(
                    campoNome
            );

            JPanel painel =
                    criarPainelFormulario(
                            "Nome da nova trilha",
                            campoNome
                    );

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janela,
                            painel,
                            "Adicionar trilha",
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

            if (nome.isEmpty()) {

                return;
            }

            RepositorioTrilhas
                    .adicionar(
                            nome
                    );

            atualizar.run();
        });

        editar.addActionListener(e -> {

            int indice =
                    lista.getSelectedIndex();

            if (indice < 0) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Selecione uma trilha.",
                        "Nenhuma trilha selecionada",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String atual =
                    RepositorioTrilhas.get(
                            indice
                    );

            JTextField campoNome =
                    new JTextField(
                            atual
                    );

            TemaMoneyShield.estilizarCampoTexto(
                    campoNome
            );

            JPanel painel =
                    criarPainelFormulario(
                            "Novo nome da trilha",
                            campoNome
                    );

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janela,
                            painel,
                            "Editar trilha",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (resposta
                    != JOptionPane.OK_OPTION) {

                return;
            }

            String novoNome =
                    campoNome
                            .getText()
                            .trim();

            if (novoNome.isEmpty()) {

                return;
            }

            RepositorioTrilhas.editar(
                    indice,
                    novoNome
            );

            atualizar.run();
        });

        excluir.addActionListener(e -> {

            int indice =
                    lista.getSelectedIndex();

            if (indice < 0) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Selecione uma trilha.",
                        "Nenhuma trilha selecionada",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String nomeTrilha =
                    RepositorioTrilhas.get(
                            indice
                    );

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janela,
                            "Deseja excluir a trilha \""
                                    + nomeTrilha
                                    + "\"?",
                            "Excluir trilha",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (resposta
                    != JOptionPane.YES_OPTION) {

                return;
            }

            RepositorioTrilhas.excluir(
                    indice
            );

            atualizar.run();
        });

        botaoAvaliacoes.addActionListener(e -> {

            janela.setVisible(
                    false
            );

            GerenciamentoAvaliacoesAdmin
                    .abrir(
                            janela
                    );
        });

        voltar.addActionListener(e -> {

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

        atualizar.run();

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
                        "Administração de trilhas"
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

        valor.setForeground(
                TemaMoneyShield.MARINHO
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

        return label;
    }

    private static JButton criarBotaoDestaque(
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

        botao.setFocusPainted(
                false
        );

        botao.setBorderPainted(
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
                BorderFactory.createEmptyBorder(
                        10,
                        16,
                        10,
                        16
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
                                16,
                                8,
                                16
                        )
                )
        );

        return botao;
    }

    private static JPanel criarPainelFormulario(
            String titulo,
            JTextField campo) {

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

        JLabel label =
                new JLabel(
                        titulo
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

        painel.add(
                label
        );

        painel.add(
                Box.createVerticalStrut(
                        7
                )
        );

        painel.add(
                campo
        );

        return painel;
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
                            12,
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
                                    9,
                                    12,
                                    9,
                                    12
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
                            4
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

            boolean configurado =
                    value.contains(
                            "[Pré/Pós configurado]"
                    );

            String nome =
                    value
                            .replace(
                                    " [Pré/Pós configurado]",
                                    ""
                            )
                            .replace(
                                    " [Sem avaliação]",
                                    ""
                            );

            titulo.setText(
                    nome
            );

            if (configurado) {

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
                        "Sem avaliação"
                );

                status.setForeground(
                        TemaMoneyShield.TEXTO_SECUNDARIO
                );

                faixa.setBackground(
                        TemaMoneyShield.AMARELO
                );
            }

            if (isSelected) {

                setBackground(
                        new Color(
                                237,
                                242,
                                250
                        )
                );

            } else {

                setBackground(
                        TemaMoneyShield.BRANCO
                );
            }

            return this;
        }
    }
}