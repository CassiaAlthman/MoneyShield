package br.com.moneyshield.ui.admin;

import br.com.moneyshield.dao.AlunoAdminDAO;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class GerenciamentoAlunosAdmin {

    private static final AlunoAdminDAO alunoDAO =
            new AlunoAdminDAO();

    public static void abrir(
            JFrame telaAnterior) {

        JFrame janela =
                new JFrame(
                        "MoneyShield - Gerenciamento de Alunos"
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

        painelPrincipal.add(
                criarCabecalho(),
                BorderLayout.NORTH
        );

        JPanel painelConteudo =
                new JPanel(
                        new BorderLayout(
                                18,
                                0
                        )
                );

        painelConteudo.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        12,
                        30
                )
        );

        JPanel painelLista =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        painelLista.setOpaque(
                false
        );

        painelLista.setPreferredSize(
                new Dimension(
                        225,
                        0
                )
        );

        JPanel cardBusca =
                TemaMoneyShield.criarCard();

        cardBusca.setLayout(
                new BoxLayout(
                        cardBusca,
                        BoxLayout.Y_AXIS
                )
        );

        cardBusca.setBorder(
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

        JLabel tituloLista =
                new JLabel(
                        "Alunos"
                );

        tituloLista.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        tituloLista.setForeground(
                TemaMoneyShield.MARINHO
        );

        tituloLista.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel descricaoLista =
                new JLabel(
                        "Carregando..."
                );

        descricaoLista.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        descricaoLista.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        descricaoLista.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JTextField campoBusca =
                new JTextField();

        TemaMoneyShield.estilizarCampoTexto(
                campoBusca
        );

        campoBusca.setToolTipText(
                "Buscar por nome, usuário ou e-mail"
        );

        campoBusca.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        cardBusca.add(
                tituloLista
        );

        cardBusca.add(
                Box.createVerticalStrut(
                        3
                )
        );

        cardBusca.add(
                descricaoLista
        );

        cardBusca.add(
                Box.createVerticalStrut(
                        10
                )
        );

        cardBusca.add(
                campoBusca
        );

        DefaultListModel<AlunoAdminDAO.DadosAluno> modelo =
                new DefaultListModel<>();

        JList<AlunoAdminDAO.DadosAluno> listaAlunos =
                new JList<>(
                        modelo
                );

        listaAlunos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        listaAlunos.setCellRenderer(
                new RenderizadorAluno()
        );

        listaAlunos.setBackground(
                TemaMoneyShield.FUNDO
        );

        listaAlunos.setFixedCellHeight(
                66
        );

        JScrollPane scrollAlunos =
                new JScrollPane(
                        listaAlunos
                );

        scrollAlunos.setBorder(
                BorderFactory.createLineBorder(
                        TemaMoneyShield.BORDA
                )
        );

        scrollAlunos.getViewport().setBackground(
                TemaMoneyShield.FUNDO
        );

        scrollAlunos.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelLista.add(
                cardBusca,
                BorderLayout.NORTH
        );

        painelLista.add(
                scrollAlunos,
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

        JScrollPane scrollDetalhes =
                new JScrollPane(
                        painelDetalhes
                );

        scrollDetalhes.setBorder(
                null
        );

        scrollDetalhes.setOpaque(
                false
        );

        scrollDetalhes.getViewport()
                .setOpaque(
                        false
                );

        scrollDetalhes.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        painelConteudo.add(
                painelLista,
                BorderLayout.WEST
        );

        painelConteudo.add(
                scrollDetalhes,
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

        JButton botaoAtualizar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Atualizar dados"
                );

        JButton botaoVoltar =
                TemaMoneyShield.criarBotaoSecundario(
                        "Voltar"
                );

        painelInferior.add(
                botaoAtualizar
        );

        painelInferior.add(
                botaoVoltar
        );

        painelPrincipal.add(
                painelConteudo,
                BorderLayout.CENTER
        );

        painelPrincipal.add(
                painelInferior,
                BorderLayout.SOUTH
        );

        ArrayList<AlunoAdminDAO.DadosAluno> alunos =
                new ArrayList<>();

        Runnable aplicarFiltro = () -> {

            String busca =
                    campoBusca.getText()
                            .trim()
                            .toLowerCase();

            String idSelecionado =
                    listaAlunos.getSelectedValue() == null
                            ? null
                            : listaAlunos
                            .getSelectedValue()
                            .getIdAluno();

            modelo.clear();

            for (AlunoAdminDAO.DadosAluno aluno
                    : alunos) {

                if (busca.isEmpty()
                        || contem(
                        aluno.getNome(),
                        busca
                )
                        || contem(
                        aluno.getUsuario(),
                        busca
                )
                        || contem(
                        aluno.getEmail(),
                        busca
                )) {

                    modelo.addElement(
                            aluno
                    );
                }
            }

            descricaoLista.setText(
                    modelo.getSize()
                            + (modelo.getSize() == 1
                            ? " aluno"
                            : " alunos")
            );

            if (modelo.isEmpty()) {

                mostrarMensagemDetalhes(
                        painelDetalhes,
                        "Nenhum aluno encontrado.",
                        "Cadastros reais do banco aparecerão aqui."
                );

                return;
            }

            int indiceSelecionar =
                    0;

            if (idSelecionado != null) {

                for (int i = 0;
                     i < modelo.size();
                     i++) {

                    if (idSelecionado.equals(
                            modelo.get(i)
                                    .getIdAluno()
                    )) {

                        indiceSelecionar = i;
                        break;
                    }
                }
            }

            listaAlunos.setSelectedIndex(
                    indiceSelecionar
            );
        };

        Runnable recarregar = () -> {

            String idSelecionado =
                    listaAlunos.getSelectedValue() == null
                            ? null
                            : listaAlunos
                            .getSelectedValue()
                            .getIdAluno();

            try {

                alunos.clear();
                alunos.addAll(
                        alunoDAO.listarAlunos()
                );

                aplicarFiltro.run();

                if (idSelecionado != null) {

                    for (int i = 0;
                         i < modelo.size();
                         i++) {

                        if (idSelecionado.equals(
                                modelo.get(i)
                                        .getIdAluno()
                        )) {

                            listaAlunos.setSelectedIndex(
                                    i
                            );
                            break;
                        }
                    }
                }

            } catch (SQLException
                     | RuntimeException erro) {

                descricaoLista.setText(
                        "Erro ao carregar"
                );

                mostrarMensagemDetalhes(
                        painelDetalhes,
                        "Não foi possível carregar os alunos.",
                        erro.getMessage()
                );
            }
        };

        listaAlunos.addListSelectionListener(e -> {

            if (e.getValueIsAdjusting()) {
                return;
            }

            AlunoAdminDAO.DadosAluno aluno =
                    listaAlunos.getSelectedValue();

            if (aluno == null) {
                return;
            }

            mostrarAluno(
                    painelDetalhes,
                    aluno
            );
        });

        campoBusca.getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e) {
                                aplicarFiltro.run();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e) {
                                aplicarFiltro.run();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e) {
                                aplicarFiltro.run();
                            }
                        }
                );

        botaoAtualizar.addActionListener(e ->
                recarregar.run()
        );

        botaoVoltar.addActionListener(e -> {

            janela.dispose();
            telaAnterior.setVisible(
                    true
            );
        });

        recarregar.run();

        janela.setContentPane(
                painelPrincipal
        );

        janela.setVisible(
                true
        );
    }

    private static void mostrarAluno(
            JPanel painel,
            AlunoAdminDAO.DadosAluno alunoLista) {

        painel.removeAll();

        try {

            AlunoAdminDAO.DadosAluno aluno =
                    alunoDAO.buscarAluno(
                            alunoLista.getIdAluno()
                    );

            if (aluno == null) {

                mostrarMensagemDetalhes(
                        painel,
                        "Aluno não encontrado.",
                        "O cadastro pode ter sido removido ou desativado."
                );

                return;
            }

            AlunoAdminDAO.ResultadoAvaliacoes avaliacoes =
                    alunoDAO.buscarAvaliacoesTrilha1(
                            aluno.getIdAluno()
                    );

            ArrayList<String> conquistas =
                    alunoDAO.buscarConquistas(
                            aluno
                    );

            ArrayList<String> atividades =
                    alunoDAO.buscarAtividadesRecentes(
                            aluno.getIdAluno()
                    );

            JPanel cardIdentidade =
                    TemaMoneyShield.criarCard();

            cardIdentidade.setLayout(
                    new BorderLayout(
                            14,
                            0
                    )
            );

            cardIdentidade.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            cardIdentidade.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            118
                    )
            );

            JPanel faixa =
                    new JPanel();

            faixa.setBackground(
                    aluno.isAtivo()
                            ? TemaMoneyShield.AZUL
                            : TemaMoneyShield.MALVA
            );

            faixa.setPreferredSize(
                    new Dimension(
                            7,
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

            JLabel nome =
                    new JLabel(
                            aluno.getNome()
                    );

            nome.setFont(
                    TemaMoneyShield.FONTE_TITULO
            );

            nome.setForeground(
                    TemaMoneyShield.MARINHO
            );

            JLabel usuario =
                    new JLabel(
                            "@"
                                    + aluno.getUsuario()
                                    + "  •  "
                                    + aluno.getEmail()
                    );

            usuario.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            usuario.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            JLabel status =
                    new JLabel(
                            aluno.isAtivo()
                                    ? "Conta ativa"
                                    : "Conta inativa"
                    );

            status.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            12
                    )
            );

            status.setForeground(
                    aluno.isAtivo()
                            ? TemaMoneyShield.MARINHO
                            : TemaMoneyShield.MALVA
            );

            textos.add(
                    nome
            );

            textos.add(
                    Box.createVerticalStrut(
                            4
                    )
            );

            textos.add(
                    usuario
            );

            textos.add(
                    Box.createVerticalStrut(
                            5
                    )
            );

            textos.add(
                    status
            );

            cardIdentidade.add(
                    faixa,
                    BorderLayout.WEST
            );

            cardIdentidade.add(
                    textos,
                    BorderLayout.CENTER
            );

            painel.add(
                    cardIdentidade
            );

            painel.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            JPanel cardNivel =
                    TemaMoneyShield.criarCard();

            cardNivel.setLayout(
                    new BoxLayout(
                            cardNivel,
                            BoxLayout.Y_AXIS
                    )
            );

            cardNivel.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            JLabel nivel =
                    new JLabel(
                            "Nível "
                                    + aluno.getNivel()
                                    + " — "
                                    + aluno.getNomeNivel()
                    );

            nivel.setFont(
                    TemaMoneyShield.FONTE_SUBTITULO
            );

            nivel.setForeground(
                    TemaMoneyShield.MARINHO
            );

            nivel.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            JLabel xp =
                    new JLabel(
                            aluno.getXp()
                                    + " XP acumulados"
                    );

            xp.setFont(
                    TemaMoneyShield.FONTE_TEXTO
            );

            xp.setForeground(
                    TemaMoneyShield.ROSA
            );

            xp.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            JProgressBar barra =
                    new JProgressBar(
                            0,
                            100
                    );

            TemaMoneyShield.estilizarBarraProgresso(
                    barra
            );

            barra.setValue(
                    aluno.getProgressoNivel()
            );

            barra.setString(
                    aluno.getProgressoNivel()
                            + "% do nível"
            );

            barra.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            barra.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            24
                    )
            );

            cardNivel.add(
                    nivel
            );

            cardNivel.add(
                    Box.createVerticalStrut(
                            4
                    )
            );

            cardNivel.add(
                    xp
            );

            cardNivel.add(
                    Box.createVerticalStrut(
                            8
                    )
            );

            cardNivel.add(
                    barra
            );

            painel.add(
                    cardNivel
            );

            painel.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            JPanel estatisticas =
                    new JPanel(
                            new GridLayout(
                                    1,
                                    3,
                                    10,
                                    0
                            )
                    );

            estatisticas.setOpaque(
                    false
            );

            estatisticas.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            estatisticas.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            90
                    )
            );

            estatisticas.add(
                    criarCardNumero(
                            "Trilhas",
                            aluno.getTrilhas(),
                            TemaMoneyShield.AZUL
                    )
            );

            estatisticas.add(
                    criarCardNumero(
                            "Missões",
                            aluno.getMissoes(),
                            TemaMoneyShield.ROSA
                    )
            );

            estatisticas.add(
                    criarCardNumero(
                            "Simulações",
                            aluno.getSimulacoes(),
                            TemaMoneyShield.AMARELO
                    )
            );

            painel.add(
                    estatisticas
            );

            painel.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            JPanel cardAvaliacao =
                    criarCardAvaliacao(
                            avaliacoes
                    );

            cardAvaliacao.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            painel.add(
                    cardAvaliacao
            );

            painel.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            painel.add(
                    criarCardLista(
                            "Conquistas",
                            conquistas,
                            "Nenhuma conquista de nível ainda.",
                            TemaMoneyShield.MALVA
                    )
            );

            painel.add(
                    Box.createVerticalStrut(
                            12
                    )
            );

            painel.add(
                    criarCardLista(
                            "Atividades recentes",
                            atividades,
                            "Nenhuma atividade persistida ainda.",
                            TemaMoneyShield.AZUL
                    )
            );

        } catch (SQLException
                 | RuntimeException erro) {

            mostrarMensagemDetalhes(
                    painel,
                    "Não foi possível consultar o aluno.",
                    erro.getMessage()
            );
        }

        painel.revalidate();
        painel.repaint();
    }

    private static JPanel criarCardNumero(
            String titulo,
            int valor,
            Color destaque) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout()
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
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        labelTitulo.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        JLabel labelValor =
                new JLabel(
                        String.valueOf(
                                valor
                        )
                );

        labelValor.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        labelValor.setForeground(
                TemaMoneyShield.MARINHO
        );

        textos.add(
                labelTitulo
        );

        textos.add(
                labelValor
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

    private static JPanel criarCardAvaliacao(
            AlunoAdminDAO.ResultadoAvaliacoes resultado) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        12,
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
                        "Avaliação — Trilha 1"
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        String pre =
                resultado.getPre() == null
                        ? "não realizado"
                        : resultado.getPre() + "%";

        String pos =
                resultado.getPos() == null
                        ? "não realizado"
                        : resultado.getPos() + "%";

        JLabel notas =
                new JLabel(
                        "Pré: "
                                + pre
                                + "  •  Pós: "
                                + pos
                );

        notas.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        notas.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        textos.add(
                titulo
        );

        textos.add(
                Box.createVerticalStrut(
                        5
                )
        );

        textos.add(
                notas
        );

        Integer evolucao =
                resultado.getEvolucao();

        if (evolucao != null) {

            JLabel labelEvolucao =
                    new JLabel(
                            "Evolução: "
                                    + (evolucao >= 0
                                    ? "+"
                                    : "")
                                    + evolucao
                                    + " ponto(s) percentuais"
                    );

            labelEvolucao.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            12
                    )
            );

            labelEvolucao.setForeground(
                    evolucao >= 0
                            ? TemaMoneyShield.MARINHO
                            : TemaMoneyShield.MALVA
            );

            textos.add(
                    Box.createVerticalStrut(
                            4
                    )
            );

            textos.add(
                    labelEvolucao
            );
        }

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

    private static JPanel criarCardLista(
            String tituloTexto,
            ArrayList<String> itens,
            String vazio,
            Color destaque) {

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BorderLayout(
                        12,
                        0
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
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

        JLabel titulo =
                new JLabel(
                        tituloTexto
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        textos.add(
                titulo
        );

        textos.add(
                Box.createVerticalStrut(
                        6
                )
        );

        if (itens.isEmpty()) {

            JLabel mensagem =
                    new JLabel(
                            vazio
                    );

            mensagem.setFont(
                    TemaMoneyShield.FONTE_TEXTO_PEQUENO
            );

            mensagem.setForeground(
                    TemaMoneyShield.TEXTO_SECUNDARIO
            );

            textos.add(
                    mensagem
            );

        } else {

            for (String item
                    : itens) {

                JLabel linha =
                        new JLabel(
                                "• " + item
                        );

                linha.setFont(
                        TemaMoneyShield.FONTE_TEXTO_PEQUENO
                );

                linha.setForeground(
                        TemaMoneyShield.TEXTO_SECUNDARIO
                );

                textos.add(
                        linha
                );

                textos.add(
                        Box.createVerticalStrut(
                                3
                        )
                );
            }
        }

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

    private static void mostrarMensagemDetalhes(
            JPanel painel,
            String tituloTexto,
            String descricaoTexto) {

        painel.removeAll();

        JPanel card =
                TemaMoneyShield.criarCard();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        tituloTexto
                );

        titulo.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        JLabel descricao =
                new JLabel(
                        "<html><div style='width:430px;'>"
                                + escaparHtml(
                                descricaoTexto == null
                                        ? ""
                                        : descricaoTexto
                        )
                                + "</div></html>"
                );

        descricao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        descricao.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        card.add(
                titulo
        );

        card.add(
                Box.createVerticalStrut(
                        7
                )
        );

        card.add(
                descricao
        );

        painel.add(
                card
        );

        painel.revalidate();
        painel.repaint();
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

        JPanel marca =
                new JPanel();

        marca.setOpaque(
                false
        );

        marca.setLayout(
                new BoxLayout(
                        marca,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo =
                TemaMoneyShield.criarLogoMoneyShield();

        JLabel descricao =
                new JLabel(
                        "Acompanhamento real dos alunos"
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

        marca.add(
                logo
        );

        marca.add(
                descricao
        );

        painel.add(
                marca,
                BorderLayout.WEST
        );

        return painel;
    }

    private static boolean contem(
            String texto,
            String busca) {

        return texto != null
                && texto.toLowerCase()
                .contains(
                        busca
                );
    }

    private static String escaparHtml(
            String texto) {

        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private static class RenderizadorAluno
            extends JPanel
            implements ListCellRenderer<AlunoAdminDAO.DadosAluno> {

        private final JLabel nome =
                new JLabel();

        private final JLabel usuario =
                new JLabel();

        private RenderizadorAluno() {

            setLayout(
                    new BoxLayout(
                            this,
                            BoxLayout.Y_AXIS
                    )
            );

            setBorder(
                    BorderFactory.createEmptyBorder(
                            10,
                            12,
                            10,
                            12
                    )
            );

            nome.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            13
                    )
            );

            usuario.setFont(
                    TemaMoneyShield.FONTE_TEXTO_PEQUENO
            );

            add(
                    nome
            );

            add(
                    Box.createVerticalStrut(
                            3
                    )
            );

            add(
                    usuario
            );
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends AlunoAdminDAO.DadosAluno> list,
                AlunoAdminDAO.DadosAluno value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            nome.setText(
                    value.getNome()
            );

            usuario.setText(
                    "@"
                            + value.getUsuario()
            );

            if (isSelected) {

                setBackground(
                        TemaMoneyShield.MARINHO_CLARO
                );

                nome.setForeground(
                        TemaMoneyShield.BRANCO
                );

                usuario.setForeground(
                        TemaMoneyShield.AZUL
                );

            } else {

                setBackground(
                        TemaMoneyShield.BRANCO
                );

                nome.setForeground(
                        TemaMoneyShield.MARINHO
                );

                usuario.setForeground(
                        TemaMoneyShield.TEXTO_SECUNDARIO
                );
            }

            setOpaque(
                    true
            );

            return this;
        }
    }
}
