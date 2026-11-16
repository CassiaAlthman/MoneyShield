package br.com.moneyshield.service;

import br.com.moneyshield.dao.MissaoAlunoDAO;
import br.com.moneyshield.dao.ProgressoAlunoDAO;
import br.com.moneyshield.session.SessaoUsuario;
import br.com.moneyshield.dao.SimulacaoAlunoDAO;
import br.com.moneyshield.dao.TrilhaAlunoDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class GamificacaoService {

    private static int pontos = 0;
    private static int missoesConcluidas = 0;
    private static int trilhasConcluidas = 0;
    private static int simulacoesRealizadas = 0;

    private static final ArrayList<String> conquistas =
            new ArrayList<>();

    private static final ArrayList<String> notificacoes =
            new ArrayList<>();

    private static final ArrayList<String> historico =
            new ArrayList<>();

    private static final ArrayList<String> listaMissoesConcluidas =
            new ArrayList<>();

    private static final ArrayList<String> listaTrilhasConcluidas =
            new ArrayList<>();

    private static final ArrayList<String> listaSimulacoesRealizadas =
            new ArrayList<>();

    private static final ProgressoAlunoDAO progressoDAO =
            new ProgressoAlunoDAO();

    private static final MissaoAlunoDAO missaoAlunoDAO =
            new MissaoAlunoDAO();

    private static final TrilhaAlunoDAO trilhaAlunoDAO =
            new TrilhaAlunoDAO();

    private static final SimulacaoAlunoDAO simulacaoAlunoDAO =
            new SimulacaoAlunoDAO();

    private static String idAlunoCarregado;

    private static boolean carregandoProgresso;

    public static boolean carregarProgressoDoBanco() {

        if (!SessaoUsuario.possuiUsuarioLogado()
                || !SessaoUsuario.isAluno()) {

            limparEstadoMemoria();

            idAlunoCarregado = null;

            return false;
        }

        carregandoProgresso = true;

        try {

            limparEstadoMemoria();

            missaoAlunoDAO
                    .sincronizarMissoesAtual();

            ProgressoAlunoDAO.DadosProgresso dados =
                    progressoDAO.buscarProgressoAtual();

            if (dados == null) {

                idAlunoCarregado = null;

                System.err.println(
                        "Progresso do aluno não encontrado no banco."
                );

                return false;
            }

            pontos =
                    dados.getXp();

            trilhasConcluidas =
                    dados.getTrilhasConcluidas();

            missoesConcluidas =
                    dados.getMissoesConcluidas();

            simulacoesRealizadas =
                    dados.getSimulacoesRealizadas();

            ArrayList<String> missoesBanco =
                    missaoAlunoDAO
                            .buscarMissoesConcluidasAtual();

            listaMissoesConcluidas.addAll(
                    missoesBanco
            );

            ArrayList<String> trilhasBanco =
                    trilhaAlunoDAO
                            .buscarTrilhasConcluidasAtual();

            listaTrilhasConcluidas.addAll(
                    trilhasBanco
            );

            ArrayList<String> simulacoesBanco =
                    simulacaoAlunoDAO
                            .buscarSimulacoesRealizadasAtual();

            listaSimulacoesRealizadas.addAll(
                    simulacoesBanco
            );

            idAlunoCarregado =
                    dados.getIdAluno();

            reconstruirConquistas();

            return true;

        } catch (SQLException erro) {

            limparEstadoMemoria();

            idAlunoCarregado = null;

            System.err.println(
                    "Erro ao carregar progresso do aluno:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;

        } finally {

            carregandoProgresso = false;
        }
    }

    public static void adicionarPontos(
            int quantidade) {

        garantirProgressoCarregado();

        if (quantidade <= 0) {
            return;
        }

        int nivelAnterior =
                calcularNivel(
                        pontos
                );

        pontos += quantidade;

        verificarConquistas();

        int novoNivel =
                calcularNivel(
                        pontos
                );

        registrarNiveisAlcancados(
                nivelAnterior,
                novoNivel
        );

        salvarProgressoNoBanco();
    }

    public static int getPontos() {

        garantirProgressoCarregado();

        return pontos;
    }

    public static int getNivel() {

        garantirProgressoCarregado();

        return calcularNivel(
                pontos
        );
    }

    public static String getNomeNivel() {

        garantirProgressoCarregado();

        return obterNomeNivel(
                calcularNivel(
                        pontos
                )
        );
    }

    public static int getInicioNivelAtual() {

        garantirProgressoCarregado();

        int nivel =
                calcularNivel(
                        pontos
                );

        if (nivel == 1) {
            return 0;
        }

        if (nivel == 2) {
            return 300;
        }

        if (nivel == 3) {
            return 700;
        }

        return 1200;
    }

    public static int getPontosProximoNivel() {

        garantirProgressoCarregado();

        int nivel =
                calcularNivel(
                        pontos
                );

        if (nivel == 1) {
            return 300;
        }

        if (nivel == 2) {
            return 700;
        }

        if (nivel == 3) {
            return 1200;
        }

        return 1200;
    }

    public static int getXpDentroNivel() {

        garantirProgressoCarregado();

        int nivel =
                calcularNivel(
                        pontos
                );

        if (nivel == 4) {
            return 0;
        }

        return pontos
                - getInicioNivelAtual();
    }

    public static int getXpNecessarioNivel() {

        garantirProgressoCarregado();

        int nivel =
                calcularNivel(
                        pontos
                );

        if (nivel == 4) {
            return 0;
        }

        return getPontosProximoNivel()
                - getInicioNivelAtual();
    }

    public static int getProgressoNivel() {

        garantirProgressoCarregado();

        if (calcularNivel(pontos) == 4) {
            return 100;
        }

        int xpAtual =
                getXpDentroNivel();

        int xpNecessario =
                getXpNecessarioNivel();

        if (xpNecessario <= 0) {
            return 0;
        }

        int progresso =
                (int) (
                        (
                                (double) xpAtual
                                        / xpNecessario
                        ) * 100
                );

        if (progresso < 0) {
            return 0;
        }

        if (progresso > 100) {
            return 100;
        }

        return progresso;
    }

    public static String getCaminhoRecompensaNivel(
            int nivel) {

        if (nivel == 2) {
            return "src/main/resources/recompensas/nivel2.png";
        }

        if (nivel == 3) {
            return "src/main/resources/recompensas/nivel3.png";
        }

        if (nivel == 4) {
            return "src/main/resources/recompensas/nivel4.png";
        }

        return null;
    }

    public static String getNomeRecompensaNivel(
            int nivel) {

        if (nivel == 2) {
            return "Medalha do Planejador";
        }

        if (nivel == 3) {
            return "Troféu do Estrategista";
        }

        if (nivel == 4) {
            return "Insígnia do Mestre Financeiro";
        }

        return "";
    }

    public static boolean concluirMissao(
            String nomeMissao) {

        garantirProgressoCarregado();

        if (nomeMissao == null
                || nomeMissao.isBlank()) {

            return false;
        }

        boolean jaConcluida =
                listaMissoesConcluidas.contains(
                        nomeMissao
                );

        try {

            missaoAlunoDAO
                    .sincronizarMissoesAtual();

            carregarProgressoDoBanco();

            return !jaConcluida
                    && listaMissoesConcluidas.contains(
                    nomeMissao
            );

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao verificar missão:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static ArrayList<String> sincronizarMissoesVerificadas() {

        garantirProgressoCarregado();

        try {

            ArrayList<String> novasConcluidas =
                    missaoAlunoDAO
                            .sincronizarMissoesAtual();

            carregarProgressoDoBanco();

            return novasConcluidas;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao sincronizar missões:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return new ArrayList<>();
        }
    }


    public static boolean concluirTrilha(
            String nomeTrilha) {

        garantirProgressoCarregado();

        if (nomeTrilha == null
                || nomeTrilha.isBlank()) {

            return false;
        }

        if (listaTrilhasConcluidas.contains(
                nomeTrilha
        )) {

            return false;
        }

        try {

            int xpTrilha =
                    trilhaAlunoDAO
                            .buscarXpTrilhaAtual(
                                    nomeTrilha
                            );

            if (xpTrilha <= 0) {
                xpTrilha = 150;
            }

            int nivelAnterior =
                    calcularNivel(
                            pontos
                    );

            int novosPontos =
                    pontos + xpTrilha;

            int novasTrilhasConcluidas =
                    trilhasConcluidas + 1;

            int novoNivel =
                    calcularNivel(
                            novosPontos
                    );

            boolean persistida =
                    trilhaAlunoDAO
                            .concluirTrilhaAtual(
                                    nomeTrilha,
                                    novosPontos,
                                    novoNivel,
                                    novasTrilhasConcluidas,
                                    missoesConcluidas,
                                    simulacoesRealizadas
                            );

            if (!persistida) {

                carregarProgressoDoBanco();

                return false;
            }

            listaTrilhasConcluidas.add(
                    nomeTrilha
            );

            trilhasConcluidas =
                    novasTrilhasConcluidas;

            pontos =
                    novosPontos;

            historico.add(
                    "Trilha concluída: "
                            + nomeTrilha
                            + " (+"
                            + xpTrilha
                            + " XP)"
            );

            notificacoes.add(
                    "Trilha concluída: "
                            + nomeTrilha
            );

            verificarConquistas();

            registrarNiveisAlcancados(
                    nivelAnterior,
                    novoNivel
            );

            return true;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao concluir trilha:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean registrarSimulacao(
            String nomeSimulacao) {

        garantirProgressoCarregado();

        if (nomeSimulacao == null
                || nomeSimulacao.isBlank()) {

            return false;
        }

        if ("Simulador de Juros".equalsIgnoreCase(
                nomeSimulacao
        )) {

            return registrarSimulacaoJuros(
                    null,
                    null
            );
        }

        if ("Simulador de Fatura".equalsIgnoreCase(
                nomeSimulacao
        )) {

            return registrarSimulacaoFatura(
                    null,
                    null
            );
        }

        return registrarSimulacaoTemporaria(
                nomeSimulacao
        );
    }

    public static boolean registrarSimulacaoJuros(
            String entrada,
            String resultado) {

        return registrarSimulacaoSimplesPersistida(
                "Simulador de Juros",
                true,
                entrada,
                resultado
        );
    }

    public static boolean registrarSimulacaoFatura(
            String entrada,
            String resultado) {

        return registrarSimulacaoSimplesPersistida(
                "Simulador de Fatura",
                false,
                entrada,
                resultado
        );
    }

    public static boolean registrarSimulacaoCenario(
            String nomeCenario,
            String nomeOpcao,
            String entrada,
            String resultado) {

        garantirProgressoCarregado();

        if (nomeCenario == null
                || nomeCenario.isBlank()) {

            return false;
        }

        if (nomeOpcao == null
                || nomeOpcao.isBlank()) {

            return false;
        }

        int nivelAnterior =
                calcularNivel(
                        pontos
                );

        int novosPontos =
                pontos + 50;

        int novasSimulacoesRealizadas =
                simulacoesRealizadas + 1;

        int novoNivel =
                calcularNivel(
                        novosPontos
                );

        try {

            boolean concedeuXp =
                    simulacaoAlunoDAO
                            .registrarCenarioAtual(
                                    nomeCenario,
                                    nomeOpcao,
                                    entrada,
                                    resultado,
                                    novosPontos,
                                    novoNivel,
                                    trilhasConcluidas,
                                    missoesConcluidas,
                                    novasSimulacoesRealizadas
                            );

            if (!listaSimulacoesRealizadas.contains(
                    nomeCenario
            )) {

                listaSimulacoesRealizadas.add(
                        nomeCenario
                );
            }

            if (!concedeuXp) {
                return false;
            }

            pontos =
                    novosPontos;

            simulacoesRealizadas =
                    novasSimulacoesRealizadas;

            historico.add(
                    "Simulação realizada: "
                            + nomeCenario
                            + " (+50 XP)"
            );

            notificacoes.add(
                    "Simulação concluída: "
                            + nomeCenario
            );

            verificarConquistas();

            registrarNiveisAlcancados(
                    nivelAnterior,
                    novoNivel
            );

            return true;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao registrar cenário:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    private static boolean registrarSimulacaoSimplesPersistida(
            String nomeSimulacao,
            boolean juros,
            String entrada,
            String resultado) {

        garantirProgressoCarregado();

        if (!SessaoUsuario.possuiUsuarioLogado()
                || !SessaoUsuario.isAluno()) {

            return false;
        }

        int nivelAnterior =
                calcularNivel(
                        pontos
                );

        int novosPontos =
                pontos + 50;

        int novasSimulacoesRealizadas =
                simulacoesRealizadas + 1;

        int novoNivel =
                calcularNivel(
                        novosPontos
                );

        try {

            boolean concedeuXp;

            if (juros) {

                concedeuXp =
                        simulacaoAlunoDAO
                                .registrarJurosAtual(
                                        entrada,
                                        resultado,
                                        novosPontos,
                                        novoNivel,
                                        trilhasConcluidas,
                                        missoesConcluidas,
                                        novasSimulacoesRealizadas
                                );

            } else {

                concedeuXp =
                        simulacaoAlunoDAO
                                .registrarFaturaAtual(
                                        entrada,
                                        resultado,
                                        novosPontos,
                                        novoNivel,
                                        trilhasConcluidas,
                                        missoesConcluidas,
                                        novasSimulacoesRealizadas
                                );
            }

            if (!listaSimulacoesRealizadas.contains(
                    nomeSimulacao
            )) {

                listaSimulacoesRealizadas.add(
                        nomeSimulacao
                );
            }

            if (!concedeuXp) {
                return false;
            }

            pontos =
                    novosPontos;

            simulacoesRealizadas =
                    novasSimulacoesRealizadas;

            historico.add(
                    "Simulação realizada: "
                            + nomeSimulacao
                            + " (+50 XP)"
            );

            notificacoes.add(
                    "Simulação concluída: "
                            + nomeSimulacao
            );

            verificarConquistas();

            registrarNiveisAlcancados(
                    nivelAnterior,
                    novoNivel
            );

            return true;

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao registrar simulação:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    private static boolean registrarSimulacaoTemporaria(
            String nomeSimulacao) {

        if (listaSimulacoesRealizadas.contains(
                nomeSimulacao
        )) {

            return false;
        }

        listaSimulacoesRealizadas.add(
                nomeSimulacao
        );

        simulacoesRealizadas++;

        historico.add(
                "Simulação realizada: "
                        + nomeSimulacao
                        + " (+50 XP)"
        );

        notificacoes.add(
                "Simulação concluída: "
                        + nomeSimulacao
        );

        adicionarPontos(
                50
        );

        return true;
    }

    public static boolean missaoConcluida(
            String nomeMissao) {

        garantirProgressoCarregado();

        return listaMissoesConcluidas.contains(
                nomeMissao
        );
    }

    public static boolean trilhaConcluida(
            String nomeTrilha) {

        garantirProgressoCarregado();

        return listaTrilhasConcluidas.contains(
                nomeTrilha
        );
    }

    public static boolean simulacaoRealizada(
            String nomeSimulacao) {

        garantirProgressoCarregado();

        return listaSimulacoesRealizadas.contains(
                nomeSimulacao
        );
    }

    public static int getMissoesConcluidas() {

        garantirProgressoCarregado();

        return missoesConcluidas;
    }

    public static int getTrilhasConcluidas() {

        garantirProgressoCarregado();

        return trilhasConcluidas;
    }

    public static int getSimulacoesRealizadas() {

        garantirProgressoCarregado();

        return simulacoesRealizadas;
    }

    public static ArrayList<String> getConquistas() {

        garantirProgressoCarregado();

        return new ArrayList<>(
                conquistas
        );
    }

    public static ArrayList<String> getNotificacoes() {

        garantirProgressoCarregado();

        return new ArrayList<>(
                notificacoes
        );
    }

    public static ArrayList<String> getHistorico() {

        garantirProgressoCarregado();

        return new ArrayList<>(
                historico
        );
    }

    public static void limparNotificacoes() {

        garantirProgressoCarregado();

        notificacoes.clear();
    }

    public static void limparProgressoLocal() {

        limparEstadoMemoria();

        idAlunoCarregado = null;
    }

    private static void garantirProgressoCarregado() {

        if (carregandoProgresso) {
            return;
        }

        if (!SessaoUsuario.possuiUsuarioLogado()) {
            return;
        }

        if (!SessaoUsuario.isAluno()) {
            return;
        }

        String idAlunoAtual =
                SessaoUsuario.getIdPerfil();

        if (idAlunoAtual == null
                || idAlunoAtual.isBlank()) {

            return;
        }

        if (idAlunoAtual.equals(
                idAlunoCarregado
        )) {

            return;
        }

        carregarProgressoDoBanco();
    }

    private static void salvarProgressoNoBanco() {

        if (!SessaoUsuario.possuiUsuarioLogado()) {
            return;
        }

        if (!SessaoUsuario.isAluno()) {
            return;
        }

        try {

            progressoDAO.salvarProgressoAtual(
                    pontos,
                    calcularNivel(
                            pontos
                    ),
                    trilhasConcluidas,
                    missoesConcluidas,
                    simulacoesRealizadas
            );

        } catch (SQLException erro) {

            System.err.println(
                    "Erro ao salvar progresso do aluno:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }
    }

    private static int calcularNivel(
            int quantidadePontos) {

        if (quantidadePontos >= 1200) {
            return 4;
        }

        if (quantidadePontos >= 700) {
            return 3;
        }

        if (quantidadePontos >= 300) {
            return 2;
        }

        return 1;
    }

    private static String obterNomeNivel(
            int nivel) {

        if (nivel == 1) {
            return "Iniciante Financeiro";
        }

        if (nivel == 2) {
            return "Planejador";
        }

        if (nivel == 3) {
            return "Estrategista";
        }

        return "Mestre Financeiro";
    }

    private static void registrarNiveisAlcancados(
            int nivelAnterior,
            int novoNivel) {

        if (novoNivel <= nivelAnterior) {
            return;
        }

        for (int nivel = nivelAnterior + 1;
             nivel <= novoNivel;
             nivel++) {

            registrarNovoNivel(
                    nivel
            );
        }
    }

    private static void registrarNovoNivel(
            int nivel) {

        String nomeNivel =
                obterNomeNivel(
                        nivel
                );

        String conquista =
                "Nível "
                        + nivel
                        + " - "
                        + nomeNivel;

        if (!conquistas.contains(
                conquista
        )) {

            conquistas.add(
                    conquista
            );

            notificacoes.add(
                    "Novo nível desbloqueado: "
                            + nomeNivel
            );

            historico.add(
                    "Novo nível alcançado: "
                            + nivel
            );

            String recompensa =
                    getNomeRecompensaNivel(
                            nivel
                    );

            if (!recompensa.isEmpty()) {

                notificacoes.add(
                        "Nova recompensa: "
                                + recompensa
                );

                if (!conquistas.contains(
                        recompensa
                )) {

                    conquistas.add(
                            recompensa
                    );
                }

                historico.add(
                        "Recompensa desbloqueada: "
                                + recompensa
                );
            }
        }
    }

    private static void verificarConquistas() {

        if (missoesConcluidas >= 1) {

            adicionarConquista(
                    "Primeira Missão"
            );
        }

        if (trilhasConcluidas >= 1) {

            adicionarConquista(
                    "Primeira Trilha"
            );
        }

        if (simulacoesRealizadas >= 1) {

            adicionarConquista(
                    "Primeira Simulação"
            );
        }

        if (missoesConcluidas >= 5) {

            adicionarConquista(
                    "5 Missões Concluídas"
            );
        }

        if (pontos >= 500) {

            adicionarConquista(
                    "500 XP"
            );
        }

        if (pontos >= 1000) {

            adicionarConquista(
                    "1000 XP"
            );
        }
    }

    private static void adicionarConquista(
            String conquista) {

        if (!conquistas.contains(
                conquista
        )) {

            conquistas.add(
                    conquista
            );

            notificacoes.add(
                    "Nova conquista: "
                            + conquista
            );

            historico.add(
                    "Conquista desbloqueada: "
                            + conquista
            );
        }
    }

    private static void reconstruirConquistas() {

        conquistas.clear();

        if (missoesConcluidas >= 1) {

            adicionarConquistaCarregada(
                    "Primeira Missão"
            );
        }

        if (trilhasConcluidas >= 1) {

            adicionarConquistaCarregada(
                    "Primeira Trilha"
            );
        }

        if (simulacoesRealizadas >= 1) {

            adicionarConquistaCarregada(
                    "Primeira Simulação"
            );
        }

        if (missoesConcluidas >= 5) {

            adicionarConquistaCarregada(
                    "5 Missões Concluídas"
            );
        }

        if (pontos >= 500) {

            adicionarConquistaCarregada(
                    "500 XP"
            );
        }

        if (pontos >= 1000) {

            adicionarConquistaCarregada(
                    "1000 XP"
            );
        }

        int nivelAtual =
                calcularNivel(
                        pontos
                );

        for (int nivel = 2;
             nivel <= nivelAtual;
             nivel++) {

            adicionarConquistaCarregada(
                    "Nível "
                            + nivel
                            + " - "
                            + obterNomeNivel(
                            nivel
                    )
            );

            String recompensa =
                    getNomeRecompensaNivel(
                            nivel
                    );

            if (!recompensa.isEmpty()) {

                adicionarConquistaCarregada(
                        recompensa
                );
            }
        }
    }

    private static void adicionarConquistaCarregada(
            String conquista) {

        if (!conquistas.contains(
                conquista
        )) {

            conquistas.add(
                    conquista
            );
        }
    }

    private static void limparEstadoMemoria() {

        pontos = 0;
        missoesConcluidas = 0;
        trilhasConcluidas = 0;
        simulacoesRealizadas = 0;

        conquistas.clear();
        notificacoes.clear();
        historico.clear();

        listaMissoesConcluidas.clear();
        listaTrilhasConcluidas.clear();
        listaSimulacoesRealizadas.clear();
    }
}