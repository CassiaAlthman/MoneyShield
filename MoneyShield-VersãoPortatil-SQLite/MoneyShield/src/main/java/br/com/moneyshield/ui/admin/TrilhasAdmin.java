package br.com.moneyshield.ui.admin;

import br.com.moneyshield.model.Trilha;
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
                        "Editar conteúdo"
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

            Trilha trilha =
                    RepositorioTrilhas.getDetalhes(
                            indice
                    );

            if (trilha == null) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível carregar os dados da trilha.",
                        "Erro ao carregar trilha",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JTextField campoNome =
                    new JTextField(
                            trilha.getNome()
                    );

            TemaMoneyShield.estilizarCampoTexto(
                    campoNome
            );

            campoNome.setPreferredSize(
                    new Dimension(
                            540,
                            42
                    )
            );

            campoNome.setMaximumSize(
                    new Dimension(
                            540,
                            42
                    )
            );

            JTextArea campoDescricao =
                    new JTextArea(
                            trilha.getDescricao() == null
                                    ? ""
                                    : trilha.getDescricao(),
                            3,
                            45
                    );

            campoDescricao.setLineWrap(true);
            campoDescricao.setWrapStyleWord(true);
            campoDescricao.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );
            campoDescricao.setForeground(
                    TemaMoneyShield.MARINHO
            );
            campoDescricao.setBackground(
                    TemaMoneyShield.BRANCO
            );
            campoDescricao.setBorder(
                    BorderFactory.createEmptyBorder(
                            8,
                            8,
                            8,
                            8
                    )
            );

            JTextArea campoConteudo =
                    new JTextArea(
                            trilha.getConteudo() == null
                                    ? ""
                                    : trilha.getConteudo(),
                            10,
                            45
                    );

            campoConteudo.setLineWrap(true);
            campoConteudo.setWrapStyleWord(true);
            campoConteudo.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );
            campoConteudo.setForeground(
                    TemaMoneyShield.MARINHO
            );
            campoConteudo.setBackground(
                    TemaMoneyShield.BRANCO
            );
            campoConteudo.setBorder(
                    BorderFactory.createEmptyBorder(
                            8,
                            8,
                            8,
                            8
                    )
            );

            JScrollPane scrollDescricao =
                    new JScrollPane(
                            campoDescricao
                    );

            scrollDescricao.setPreferredSize(
                    new Dimension(
                            540,
                            85
                    )
            );

            scrollDescricao.setBorder(
                    BorderFactory.createLineBorder(
                            TemaMoneyShield.BORDA
                    )
            );

            JScrollPane scrollConteudo =
                    new JScrollPane(
                            campoConteudo
                    );

            scrollConteudo.setPreferredSize(
                    new Dimension(
                            540,
                            220
                    )
            );

            scrollConteudo.setBorder(
                    BorderFactory.createLineBorder(
                            TemaMoneyShield.BORDA
                    )
            );

            JPanel painelFormulario =
                    new JPanel();

            painelFormulario.setBackground(
                    TemaMoneyShield.BRANCO
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

            painelFormulario.add(
                    criarPainelFormulario(
                            "Nome da trilha",
                            campoNome
                    )
            );

            painelFormulario.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            painelFormulario.add(
                    criarPainelFormulario(
                            "Descrição breve",
                            scrollDescricao
                    )
            );

            painelFormulario.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            painelFormulario.add(
                    criarPainelFormulario(
                            "Conteúdo apresentado ao aluno",
                            scrollConteudo
                    )
            );

            JScrollPane scrollFormulario =
                    new JScrollPane(
                            painelFormulario
                    );

            scrollFormulario.setPreferredSize(
                    new Dimension(
                            590,
                            440
                    )
            );

            scrollFormulario.setBorder(
                    BorderFactory.createEmptyBorder()
            );

            int resposta =
                    JOptionPane.showConfirmDialog(
                            janela,
                            scrollFormulario,
                            "Editar trilha",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (resposta
                    != JOptionPane.OK_OPTION) {

                return;
            }

            String novoNome =
                    campoNome.getText().trim();

            String novaDescricao =
                    campoDescricao.getText().trim();

            String novoConteudo =
                    campoConteudo.getText();

            if (novoNome.isEmpty()) {

                JOptionPane.showMessageDialog(
                        janela,
                        "O nome da trilha é obrigatório.",
                        "Campo obrigatório",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (novoNome.length() > 220) {

                JOptionPane.showMessageDialog(
                        janela,
                        "O nome da trilha deve ter no máximo 220 caracteres.",
                        "Nome muito longo",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (novaDescricao.length() > 1000) {

                JOptionPane.showMessageDialog(
                        janela,
                        "A descrição deve ter no máximo 1000 caracteres.",
                        "Descrição muito longa",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean salvou =
                    RepositorioTrilhas.editarTrilha(
                            indice,
                            novoNome,
                            novaDescricao,
                            novoConteudo
                    );

            if (!salvou) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível salvar as alterações. Verifique se já existe outra trilha com esse nome e tente novamente.",
                        "Erro ao editar trilha",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    janela,
                    "Trilha atualizada com sucesso.",
                    "Edição concluída",
                    JOptionPane.INFORMATION_MESSAGE
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
                        220,
                        72
                )
        );

        JPanel faixa =
                new JPanel();

        faixa.setPreferredSize(
                new Dimension(
                        5,
                        45
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

        valor.setFont(
                new Font(
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        21
                )
        );

        valor.setForeground(
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
                        TemaMoneyShield.FAMILIA_TITULO,
                        Font.BOLD,
                        21
                )
        );

        label.setForeground(
                TemaMoneyShield.MARINHO
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
                TemaMoneyShield.FONTE_TEXTO
        );

        botao.setForeground(
                TemaMoneyShield.BRANCO
        );

        botao.setBackground(
                TemaMoneyShield.ROSA
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
                        8,
                        16,
                        8,
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
                TemaMoneyShield.FONTE_TEXTO
        );

        botao.setForeground(
                TemaMoneyShield.BRANCO
        );

        botao.setBackground(
                TemaMoneyShield.ROSA
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
                        8,
                        16,
                        8,
                        16
                )
        );

        return botao;
    }

    private static JPanel criarPainelFormulario(
            String titulo,
            JComponent campo) {

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
                                    10,
                                    12,
                                    10,
                                    12
                            )
                    )
            );

            faixa =
                    new JPanel();

            faixa.setPreferredSize(
                    new Dimension(
                            5,
                            42
                    )
            );

            faixa.setBackground(
                    TemaMoneyShield.AZUL
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

            status.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
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
                JList<? extends String> lista,
                String valor,
                int indice,
                boolean selecionado,
                boolean foco) {

            String[] partes =
                    valor.split(
                            " \\[",
                            2
                    );

            titulo.setText(
                    partes[0]
            );

            status.setText(
                    partes.length > 1
                            ? partes[1].replace(
                            "]",
                            ""
                    )
                            : ""
            );

            if (selecionado) {

                setBackground(
                        TemaMoneyShield.AZUL
                );

                faixa.setBackground(
                        TemaMoneyShield.ROSA
                );

            } else {

                setBackground(
                        TemaMoneyShield.BRANCO
                );

                faixa.setBackground(
                        TemaMoneyShield.AZUL
                );
            }

            return this;
        }
    }
}