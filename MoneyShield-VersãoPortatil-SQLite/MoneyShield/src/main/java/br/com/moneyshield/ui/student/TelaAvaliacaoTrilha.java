package br.com.moneyshield.ui.student;

import br.com.moneyshield.model.AvaliacaoTrilha;
import br.com.moneyshield.model.PerguntaAvaliacao;
import br.com.moneyshield.repository.RepositorioAvaliacoes;
import br.com.moneyshield.repository.RepositorioResultadosAvaliacoes;
import br.com.moneyshield.model.ResultadoAvaliacao;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaAvaliacaoTrilha {

    public static void abrir(
            JFrame telaAnterior,
            int numeroTrilha,
            boolean preTeste,
            Runnable aoFinalizar) {

        AvaliacaoTrilha avaliacao =
                RepositorioAvaliacoes
                        .getAvaliacao(
                                numeroTrilha
                        );

        ArrayList<PerguntaAvaliacao> perguntas =
                preTeste
                        ? avaliacao.getPerguntasPre()
                        : avaliacao.getPerguntasPos();

        if (perguntas.isEmpty()) {

            JOptionPane.showMessageDialog(
                    telaAnterior,
                    "Esta avaliação ainda não possui perguntas.",
                    "Avaliação indisponível",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JFrame janela =
                new JFrame(
                        preTeste
                                ? "MoneyShield - Pré-teste"
                                : "MoneyShield - Pós-teste"
                );

        janela.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        janela.setSize(
                750,
                600
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

        JPanel painelSuperior =
                criarCabecalho(
                        preTeste
                                ? "Pré-teste"
                                : "Pós-teste"
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
                        20,
                        32,
                        12,
                        32
                )
        );

        JPanel painelIntroducao =
                new JPanel();

        painelIntroducao.setOpaque(
                false
        );

        painelIntroducao.setLayout(
                new BoxLayout(
                        painelIntroducao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel categoria =
                new JLabel(
                        preTeste
                                ? "ANTES DA TRILHA"
                                : "DEPOIS DA TRILHA"
                );

        categoria.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        categoria.setForeground(
                preTeste
                        ? TemaMoneyShield.AZUL
                        : TemaMoneyShield.ROSA
        );

        categoria.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        (preTeste
                                ? "Pré-teste da Trilha "
                                : "Pós-teste da Trilha ")
                                + numeroTrilha
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
                        preTeste
                                ? "Responda às perguntas para registrar seu conhecimento antes de estudar o conteúdo."
                                : "Responda novamente para comparar sua evolução após estudar o conteúdo."
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

        JPanel cardInformacao =
                criarCardInformacao(
                        preTeste
                                ? "Este teste não vale nota."
                                : "Agora vamos medir sua evolução.",
                        preTeste
                                ? "O resultado será usado apenas para comparar seu conhecimento antes e depois da trilha."
                                : "Seu resultado será comparado com o pré-teste realizado antes do conteúdo.",
                        preTeste
                                ? TemaMoneyShield.AZUL
                                : TemaMoneyShield.ROSA
                );

        painelIntroducao.add(
                categoria
        );

        painelIntroducao.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelIntroducao.add(
                titulo
        );

        painelIntroducao.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painelIntroducao.add(
                subtitulo
        );

        painelIntroducao.add(
                Box.createVerticalStrut(
                        14
                )
        );

        painelIntroducao.add(
                cardInformacao
        );

        painelIntroducao.add(
                Box.createVerticalStrut(
                        16
                )
        );

        JPanel painelPerguntas =
                new JPanel();

        painelPerguntas.setOpaque(
                false
        );

        painelPerguntas.setLayout(
                new BoxLayout(
                        painelPerguntas,
                        BoxLayout.Y_AXIS
                )
        );

        ArrayList<ArrayList<JRadioButton>> botoes =
                new ArrayList<>();

        for (int i = 0;
             i < perguntas.size();
             i++) {

            PerguntaAvaliacao pergunta =
                    perguntas.get(i);

            JPanel card =
                    criarCardPergunta(
                            i + 1,
                            pergunta,
                            preTeste
                                    ? TemaMoneyShield.AZUL
                                    : TemaMoneyShield.ROSA
                    );

            ButtonGroup grupo =
                    new ButtonGroup();

            ArrayList<JRadioButton> botoesPergunta =
                    new ArrayList<>();

            JPanel painelAlternativas =
                    new JPanel();

            painelAlternativas.setOpaque(
                    false
            );

            painelAlternativas.setLayout(
                    new BoxLayout(
                            painelAlternativas,
                            BoxLayout.Y_AXIS
                    )
            );

            ArrayList<String> alternativas =
                    pergunta.getAlternativas();

            for (String alternativa
                    : alternativas) {

                JRadioButton opcao =
                        criarOpcao(
                                alternativa
                        );

                grupo.add(
                        opcao
                );

                botoesPergunta.add(
                        opcao
                );

                painelAlternativas.add(
                        opcao
                );

                painelAlternativas.add(
                        Box.createVerticalStrut(
                                5
                        )
                );
            }

            card.add(
                    painelAlternativas,
                    BorderLayout.CENTER
            );

            botoes.add(
                    botoesPergunta
            );

            painelPerguntas.add(
                    card
            );

            painelPerguntas.add(
                    Box.createVerticalStrut(
                            12
                    )
            );
        }

        JScrollPane scroll =
                new JScrollPane(
                        painelPerguntas
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
                        new BorderLayout()
                );

        painelInferior.setBackground(
                TemaMoneyShield.FUNDO
        );

        painelInferior.setBorder(
                BorderFactory.createEmptyBorder(
                        7,
                        32,
                        10,
                        32
                )
        );

        JLabel contador =
                new JLabel(
                        perguntas.size()
                                + " perguntas"
                );

        contador.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        contador.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        JButton finalizar =
                TemaMoneyShield.criarBotaoPrimario(
                        "Finalizar avaliação"
                );

        finalizar.addActionListener(e -> {

            ArrayList<Integer> respostasSelecionadas =
                    new ArrayList<>();

            for (int i = 0;
                 i < perguntas.size();
                 i++) {

                ArrayList<JRadioButton> respostas =
                        botoes.get(i);

                int selecionada =
                        -1;

                for (int j = 0;
                     j < respostas.size();
                     j++) {

                    if (respostas
                            .get(j)
                            .isSelected()) {

                        selecionada =
                                j;

                        break;
                    }
                }

                if (selecionada
                        == -1) {

                    JOptionPane.showMessageDialog(
                            janela,
                            "Responda todas as perguntas antes de finalizar.",
                            "Avaliação incompleta",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                respostasSelecionadas.add(
                        selecionada
                );
            }

            ResultadoAvaliacao resultado =
                    RepositorioResultadosAvaliacoes
                            .getResultado(
                                    numeroTrilha
                            );

            boolean registrada =
                    preTeste
                            ? resultado.registrarPre(
                            respostasSelecionadas
                    )
                            : resultado.registrarPos(
                            respostasSelecionadas
                    );

            if (!registrada) {

                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível salvar a avaliação no banco de dados.",
                        "Erro ao salvar avaliação",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int acertos =
                    preTeste
                            ? resultado.getAcertosPre()
                            : resultado.getAcertosPos();

            int total =
                    preTeste
                            ? resultado.getTotalPre()
                            : resultado.getTotalPos();

            int percentual =
                    preTeste
                            ? resultado.getPercentualPre()
                            : resultado.getPercentualPos();

            mostrarResultado(
                    janela,
                    acertos,
                    total,
                    percentual,
                    preTeste
            );

            janela.dispose();

            if (aoFinalizar
                    != null) {

                aoFinalizar.run();
            }
        });

        painelInferior.add(
                contador,
                BorderLayout.WEST
        );

        painelInferior.add(
                finalizar,
                BorderLayout.EAST
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
            String descricao) {

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout()
                );

        painelSuperior.setBackground(
                TemaMoneyShield.MARINHO
        );

        painelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        28,
                        14,
                        28
                )
        );

        painelSuperior.setPreferredSize(
                new Dimension(
                        750,
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

        JLabel subtitulo =
                new JLabel(
                        descricao
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

        painelMarca.add(
                logo
        );

        painelMarca.add(
                subtitulo
        );

        painelSuperior.add(
                painelMarca,
                BorderLayout.WEST
        );

        return painelSuperior;
    }

    private static JPanel criarCardPergunta(
            int numero,
            PerguntaAvaliacao pergunta,
            Color destaque) {

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
                        210
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

        JPanel painelQuestao =
                new JPanel();

        painelQuestao.setOpaque(
                false
        );

        painelQuestao.setLayout(
                new BoxLayout(
                        painelQuestao,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel numeroPergunta =
                new JLabel(
                        "PERGUNTA "
                                + numero
                );

        numeroPergunta.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        numeroPergunta.setForeground(
                destaque
        );

        numeroPergunta.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel texto =
                new JLabel(
                        "<html>"
                                + "<div style='width:570px;'>"
                                + pergunta.getPergunta()
                                + "</div>"
                                + "</html>"
                );

        texto.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        texto.setForeground(
                TemaMoneyShield.MARINHO
        );

        texto.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        painelQuestao.add(
                numeroPergunta
        );

        painelQuestao.add(
                Box.createVerticalStrut(
                        4
                )
        );

        painelQuestao.add(
                texto
        );

        card.add(
                faixa,
                BorderLayout.WEST
        );

        card.add(
                painelQuestao,
                BorderLayout.NORTH
        );

        return card;
    }

    private static JRadioButton criarOpcao(
            String texto) {

        JRadioButton opcao =
                new JRadioButton(
                        texto
                );

        opcao.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        opcao.setForeground(
                TemaMoneyShield.MARINHO
        );

        opcao.setOpaque(
                false
        );

        opcao.setFocusPainted(
                false
        );

        opcao.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        opcao.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return opcao;
    }

    private static JPanel criarCardInformacao(
            String titulo,
            String texto,
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

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        80
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

        JLabel label =
                new JLabel(
                        "<html>"
                                + "<div style='width:590px;'>"
                                + "<b>"
                                + titulo
                                + "</b><br>"
                                + texto
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

    private static void mostrarResultado(
            JFrame janela,
            int acertos,
            int total,
            int percentual,
            boolean preTeste) {

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
                        12,
                        12,
                        12,
                        12
                )
        );

        JLabel titulo =
                new JLabel(
                        "Avaliação concluída!"
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

        JLabel percentualLabel =
                new JLabel(
                        percentual
                                + "%"
                );

        percentualLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        34
                )
        );

        percentualLabel.setForeground(
                preTeste
                        ? TemaMoneyShield.AZUL
                        : TemaMoneyShield.ROSA
        );

        percentualLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel acertosLabel =
                new JLabel(
                        acertos
                                + " de "
                                + total
                                + " respostas corretas"
                );

        acertosLabel.setFont(
                TemaMoneyShield.FONTE_TEXTO
        );

        acertosLabel.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        acertosLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel mensagem =
                new JLabel(
                        preTeste
                                ? "Agora você pode iniciar o conteúdo da trilha."
                                : "Seu resultado será comparado com o pré-teste."
                );

        mensagem.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        mensagem.setForeground(
                TemaMoneyShield.MARINHO
        );

        mensagem.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        12
                )
        );

        painel.add(
                percentualLabel
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                acertosLabel
        );

        painel.add(
                Box.createVerticalStrut(
                        12
                )
        );

        painel.add(
                mensagem
        );

        JOptionPane.showMessageDialog(
                janela,
                painel,
                preTeste
                        ? "Pré-teste concluído"
                        : "Pós-teste concluído",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}