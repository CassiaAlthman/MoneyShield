package br.com.moneyshield.ui.student;

import br.com.moneyshield.service.GamificacaoService;
import br.com.moneyshield.ui.theme.TemaMoneyShield;
import javax.swing.*;
import java.awt.*;

public class TelaRecompensa {

    public static void mostrar(
            Component componentePai,
            int nivel) {

        String caminhoImagem =
                GamificacaoService.getCaminhoRecompensaNivel(
                        nivel
                );

        String nomeRecompensa =
                GamificacaoService.getNomeRecompensaNivel(
                        nivel
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

        painel.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        22,
                        18,
                        22
                )
        );

        JLabel categoria =
                new JLabel(
                        "NOVO NÍVEL"
                );

        categoria.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        categoria.setForeground(
                TemaMoneyShield.ROSA
        );

        categoria.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titulo =
                new JLabel(
                        "Parabéns!"
                );

        titulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        titulo.setForeground(
                TemaMoneyShield.MARINHO
        );

        titulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel textoNivel =
                new JLabel(
                        "Nível "
                                + nivel
                                + " - "
                                + GamificacaoService.getNomeNivel()
                );

        textoNivel.setFont(
                TemaMoneyShield.FONTE_SUBTITULO
        );

        textoNivel.setForeground(
                TemaMoneyShield.MALVA
        );

        textoNivel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JPanel cardRecompensa =
                TemaMoneyShield.criarCard();

        cardRecompensa.setLayout(
                new BoxLayout(
                        cardRecompensa,
                        BoxLayout.Y_AXIS
                )
        );

        cardRecompensa.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        cardRecompensa.setPreferredSize(
                new Dimension(
                        330,
                        285
                )
        );

        cardRecompensa.setMaximumSize(
                new Dimension(
                        330,
                        285
                )
        );

        JLabel textoRecompensa =
                new JLabel(
                        "RECOMPENSA DESBLOQUEADA"
                );

        textoRecompensa.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        textoRecompensa.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        textoRecompensa.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel imagem =
                new JLabel();

        imagem.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        imagem.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        imagem.setPreferredSize(
                new Dimension(
                        180,
                        180
                )
        );

        imagem.setMaximumSize(
                new Dimension(
                        180,
                        180
                )
        );

        if (caminhoImagem != null) {

            ImageIcon imagemOriginal =
                    new ImageIcon(
                            caminhoImagem
                    );

            if (imagemOriginal.getIconWidth() > 0) {

                Image imagemEscalada =
                        imagemOriginal
                                .getImage()
                                .getScaledInstance(
                                        165,
                                        165,
                                        Image.SCALE_SMOOTH
                                );

                imagem.setIcon(
                        new ImageIcon(
                                imagemEscalada
                        )
                );

            } else {

                imagem.setText(
                        "★"
                );

                imagem.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                72
                        )
                );

                imagem.setForeground(
                        TemaMoneyShield.AMARELO
                );
            }

        } else {

            imagem.setText(
                    "★"
            );

            imagem.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            72
                    )
            );

            imagem.setForeground(
                    TemaMoneyShield.AMARELO
            );
        }

        JLabel nome =
                new JLabel(
                        nomeRecompensa
                );

        nome.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        nome.setForeground(
                TemaMoneyShield.MARINHO
        );

        nome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel mensagem =
                new JLabel(
                        "Continue avançando para desbloquear novas recompensas."
                );

        mensagem.setFont(
                TemaMoneyShield.FONTE_TEXTO_PEQUENO
        );

        mensagem.setForeground(
                TemaMoneyShield.TEXTO_SECUNDARIO
        );

        mensagem.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        cardRecompensa.add(
                textoRecompensa
        );

        cardRecompensa.add(
                Box.createVerticalStrut(
                        8
                )
        );

        cardRecompensa.add(
                imagem
        );

        cardRecompensa.add(
                Box.createVerticalStrut(
                        8
                )
        );

        cardRecompensa.add(
                nome
        );

        painel.add(
                categoria
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                titulo
        );

        painel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        painel.add(
                textoNivel
        );

        painel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        painel.add(
                cardRecompensa
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
                componentePai,
                painel,
                "MoneyShield - Novo nível",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}