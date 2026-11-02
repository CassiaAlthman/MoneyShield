package br.com.moneyshield.session;

public final class SessaoUsuario {

    private static UsuarioAutenticado usuarioAtual;

    private SessaoUsuario() {
    }

    public static void iniciar(
            UsuarioAutenticado usuario) {

        if (usuario == null) {

            throw new IllegalArgumentException(
                    "Usuário da sessão não pode ser nulo."
            );
        }

        usuarioAtual =
                usuario;
    }

    public static void encerrar() {

        usuarioAtual =
                null;
    }

    public static boolean possuiUsuarioLogado() {

        return usuarioAtual != null;
    }

    public static UsuarioAutenticado getUsuarioAtual() {

        return usuarioAtual;
    }

    public static String getIdPessoa() {

        validarSessao();

        return usuarioAtual
                .getIdPessoa();
    }

    public static String getIdPerfil() {

        validarSessao();

        return usuarioAtual
                .getIdPerfil();
    }

    public static String getNome() {

        validarSessao();

        return usuarioAtual
                .getNome();
    }

    public static String getUsuario() {

        validarSessao();

        return usuarioAtual
                .getUsuario();
    }

    public static String getEmail() {

        validarSessao();

        return usuarioAtual
                .getEmail();
    }

    public static UsuarioAutenticado.TipoUsuario getTipo() {

        validarSessao();

        return usuarioAtual
                .getTipo();
    }

    public static boolean isAluno() {

        return possuiUsuarioLogado()
                && usuarioAtual.isAluno();
    }

    public static boolean isAdministrador() {

        return possuiUsuarioLogado()
                && usuarioAtual.isAdministrador();
    }

    private static void validarSessao() {

        if (usuarioAtual == null) {

            throw new IllegalStateException(
                    "Nenhum usuário está autenticado."
            );
        }
    }
}