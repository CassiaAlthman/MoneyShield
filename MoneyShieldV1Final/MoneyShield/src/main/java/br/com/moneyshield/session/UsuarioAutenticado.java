package br.com.moneyshield.session;

public class UsuarioAutenticado {

    public enum TipoUsuario {
        ALUNO,
        ADMINISTRADOR
    }

    private final String idPessoa;
    private final String idPerfil;
    private final String nome;
    private final String usuario;
    private final String email;
    private final TipoUsuario tipo;

    public UsuarioAutenticado(
            String idPessoa,
            String idPerfil,
            String nome,
            String usuario,
            String email,
            TipoUsuario tipo) {

        this.idPessoa =
                idPessoa;

        this.idPerfil =
                idPerfil;

        this.nome =
                nome;

        this.usuario =
                usuario;

        this.email =
                email;

        this.tipo =
                tipo;
    }

    public String getIdPessoa() {

        return idPessoa;
    }

    public String getIdPerfil() {

        return idPerfil;
    }

    public String getNome() {

        return nome;
    }

    public String getUsuario() {

        return usuario;
    }

    public String getEmail() {

        return email;
    }

    public TipoUsuario getTipo() {

        return tipo;
    }

    public boolean isAluno() {

        return tipo
                == TipoUsuario.ALUNO;
    }

    public boolean isAdministrador() {

        return tipo
                == TipoUsuario.ADMINISTRADOR;
    }

    @Override
    public String toString() {

        return nome
                + " - "
                + tipo;
    }
}