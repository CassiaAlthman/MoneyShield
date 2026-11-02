package br.com.moneyshield.ui.theme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public final class TemaMoneyShield {

    public static final Color MARINHO = new Color(6, 13, 40);
    public static final Color AMARELO = new Color(253, 191, 107);
    public static final Color ROSA = new Color(236, 17, 108);
    public static final Color AZUL = new Color(107, 164, 225);
    public static final Color MALVA = new Color(181, 92, 119);

    public static final Color FUNDO = new Color(247, 248, 252);
    public static final Color BRANCO = Color.WHITE;
    public static final Color TEXTO_SECUNDARIO = new Color(91, 99, 120);
    public static final Color BORDA = new Color(224, 228, 237);
    public static final Color MARINHO_CLARO = new Color(16, 27, 65);

    public static final String FAMILIA_TEXTO = "Trebuchet MS";
    public static final String FAMILIA_TITULO = "Comic Sans MS";

    public static final Font FONTE_LOGO =
            new Font(
                    FAMILIA_TITULO,
                    Font.BOLD,
                    27
            );

    public static final Font FONTE_TITULO =
            new Font(
                    FAMILIA_TITULO,
                    Font.BOLD,
                    24
            );

    public static final Font FONTE_SUBTITULO =
            new Font(
                    FAMILIA_TITULO,
                    Font.BOLD,
                    16
            );

    public static final Font FONTE_TEXTO =
            new Font(
                    FAMILIA_TEXTO,
                    Font.PLAIN,
                    14
            );

    public static final Font FONTE_TEXTO_PEQUENO =
            new Font(
                    FAMILIA_TEXTO,
                    Font.PLAIN,
                    12
            );

    public static final Font FONTE_BOTAO =
            new Font(
                    FAMILIA_TEXTO,
                    Font.BOLD,
                    15
            );

    private TemaMoneyShield() {
    }


    private static final String[] RECURSOS_ICONE = {
            "/images/moneyshield-icon-16.png",
            "/images/moneyshield-icon-24.png",
            "/images/moneyshield-icon-32.png",
            "/images/moneyshield-icon-48.png",
            "/images/moneyshield-icon-64.png",
            "/images/moneyshield-icon-128.png",
            "/images/moneyshield-icon-256.png",
            "/images/moneyshield-icon-512.png"
    };

    private static boolean iconeAplicacaoInstalado;

    public static void instalarIconeAplicacao() {

        if (iconeAplicacaoInstalado) {
            return;
        }

        java.util.List<Image> icones =
                carregarIconesAplicacao();

        if (icones.isEmpty()) {
            return;
        }

        iconeAplicacaoInstalado =
                true;

        try {

            if (Taskbar.isTaskbarSupported()) {

                Taskbar taskbar =
                        Taskbar.getTaskbar();

                if (taskbar.isSupported(
                        Taskbar.Feature.ICON_IMAGE
                )) {

                    taskbar.setIconImage(
                            icones.get(
                                    icones.size() - 1
                            )
                    );
                }
            }

        } catch (UnsupportedOperationException
                 | SecurityException ignorado) {
        }

        try {

            Toolkit.getDefaultToolkit()
                    .addAWTEventListener(
                            evento -> {

                                if (evento.getID()
                                        != java.awt.event.WindowEvent.WINDOW_OPENED) {

                                    return;
                                }

                                if (evento.getSource()
                                        instanceof Window janela) {

                                    janela.setIconImages(
                                            icones
                                    );
                                }
                            },
                            AWTEvent.WINDOW_EVENT_MASK
                    );

        } catch (SecurityException ignorado) {
        }

        for (Window janela
                : Window.getWindows()) {

            janela.setIconImages(
                    icones
            );
        }
    }

    private static java.util.List<Image> carregarIconesAplicacao() {

        java.util.List<Image> icones =
                new java.util.ArrayList<>();

        for (String recurso
                : RECURSOS_ICONE) {

            java.net.URL url =
                    TemaMoneyShield.class
                            .getResource(
                                    recurso
                            );

            if (url == null) {
                continue;
            }

            Image imagem =
                    new ImageIcon(
                            url
                    ).getImage();

            icones.add(
                    imagem
            );
        }

        return icones;
    }

    public static JLabel criarLogoMoneyShield() {

        JLabel logo =
                new JLabel(
                        "<html>"
                                + "<font color='#6BA4E1'>Money</font>"
                                + "<font color='#FDBF6B'>Shield</font>"
                                + "</html>"
                );

        logo.setFont(
                FONTE_LOGO
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return logo;
    }

    public static JLabel criarLogoMoneyShield(
            int tamanho) {

        JLabel logo =
                criarLogoMoneyShield();

        logo.setFont(
                new Font(
                        FAMILIA_TITULO,
                        Font.BOLD,
                        tamanho
                )
        );

        return logo;
    }

    public static JPanel criarCard() {

        JPanel painel =
                new JPanel();

        painel.setBackground(
                BRANCO
        );

        painel.setBorder(
                criarBordaCard()
        );

        return painel;
    }

    public static Border criarBordaCard() {

        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        BORDA
                ),
                BorderFactory.createEmptyBorder(
                        14,
                        16,
                        14,
                        16
                )
        );
    }

    public static JButton criarBotaoCard(
            String titulo,
            String descricao,
            Color fundo,
            Color texto) {

        JButton botao =
                new JButton(
                        "<html>"
                                + "<div style='text-align:left;'>"
                                + "<b>"
                                + titulo
                                + "</b>"
                                + "<br>"
                                + "<span style='font-size:10px;'>"
                                + descricao
                                + "</span>"
                                + "</div>"
                                + "</html>"
                );

        botao.setFont(
                FONTE_BOTAO
        );

        botao.setForeground(
                texto
        );

        botao.setBackground(
                fundo
        );

        botao.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        botao.setVerticalAlignment(
                SwingConstants.CENTER
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
                        14,
                        18,
                        14,
                        18
                )
        );

        return botao;
    }

    public static JButton criarBotaoCabecalho(
            String texto) {

        JButton botao =
                new JButton(
                        texto
                );

        botao.setFont(
                new Font(
                        FAMILIA_TEXTO,
                        Font.BOLD,
                        12
                )
        );

        botao.setForeground(
                BRANCO
        );

        botao.setBackground(
                MARINHO_CLARO
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
                        9,
                        14,
                        9,
                        14
                )
        );

        return botao;
    }

    public static JButton criarBotaoSecundario(
            String texto) {

        JButton botao =
                new JButton(
                        texto
                );

        botao.setFont(
                new Font(
                        FAMILIA_TEXTO,
                        Font.BOLD,
                        12
                )
        );

        botao.setForeground(
                MARINHO
        );

        botao.setBackground(
                BRANCO
        );

        botao.setFocusPainted(
                false
        );

        botao.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        botao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDA
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

    public static JButton criarBotaoPrimario(
            String texto) {

        JButton botao =
                new JButton(
                        texto
                );

        botao.setFont(
                FONTE_BOTAO
        );

        botao.setForeground(
                BRANCO
        );

        botao.setBackground(
                ROSA
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
                        11,
                        20,
                        11,
                        20
                )
        );

        return botao;
    }

    public static void estilizarCampoTexto(
            JTextField campo) {

        campo.setFont(
                FONTE_TEXTO
        );

        campo.setForeground(
                MARINHO
        );

        campo.setBackground(
                BRANCO
        );

        campo.setCaretColor(
                MARINHO
        );

        campo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        campo.setPreferredSize(
                new Dimension(
                        285,
                        42
                )
        );

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDA
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        campo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );
    }

    public static void estilizarCampoSenha(
            JPasswordField campo) {

        campo.setFont(
                FONTE_TEXTO
        );

        campo.setForeground(
                MARINHO
        );

        campo.setBackground(
                BRANCO
        );

        campo.setCaretColor(
                MARINHO
        );

        campo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        campo.setPreferredSize(
                new Dimension(
                        285,
                        42
                )
        );

        campo.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDA
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        campo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );
    }

    public static void estilizarBarraProgresso(
            JProgressBar barra) {

        barra.setForeground(
                ROSA
        );

        barra.setBackground(
                new Color(
                        232,
                        235,
                        243
                )
        );

        barra.setBorderPainted(
                false
        );

        barra.setStringPainted(
                true
        );

        barra.setFont(
                new Font(
                        FAMILIA_TEXTO,
                        Font.BOLD,
                        11
                )
        );
    }
}