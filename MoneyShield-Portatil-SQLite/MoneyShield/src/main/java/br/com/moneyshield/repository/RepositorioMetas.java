package br.com.moneyshield.repository;

import br.com.moneyshield.model.MetaFinanceira;
import br.com.moneyshield.dao.MetaFinanceiraDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepositorioMetas {

    private static final MetaFinanceiraDAO metaDAO =
            new MetaFinanceiraDAO();

    private static final ArrayList<MetaFinanceira> metas =
            new ArrayList<>();

    public static MetaFinanceira criarMeta(
            String nome,
            double valorObjetivo) {

        try {

            MetaFinanceiraDAO.DadosMeta dados =
                    metaDAO.criarMetaAtual(
                            nome,
                            valorObjetivo
                    );

            MetaFinanceira meta =
                    converter(
                            dados
                    );

            metas.add(
                    meta
            );

            return meta;

        } catch (SQLException
                 | IllegalArgumentException
                 | IllegalStateException erro) {

            System.err.println(
                    "Erro ao criar meta financeira:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return null;
        }
    }

    public static ArrayList<MetaFinanceira> getMetas() {

        try {

            ArrayList<MetaFinanceiraDAO.DadosMeta> dadosBanco =
                    metaDAO.listarMetasAtual();

            metas.clear();

            for (MetaFinanceiraDAO.DadosMeta dados
                    : dadosBanco) {

                metas.add(
                        converter(
                                dados
                        )
                );
            }

        } catch (SQLException
                 | IllegalStateException erro) {

            System.err.println(
                    "Erro ao carregar metas financeiras:"
            );

            System.err.println(
                    erro.getMessage()
            );
        }

        return new ArrayList<>(
                metas
        );
    }

    public static MetaFinanceira buscarPorId(
            String id) {

        if (id == null
                || id.isBlank()) {

            return null;
        }

        for (MetaFinanceira meta
                : metas) {

            if (id.equals(
                    meta.getId()
            )) {

                return meta;
            }
        }

        try {

            MetaFinanceiraDAO.DadosMeta dados =
                    metaDAO.buscarMetaAtual(
                            id
                    );

            if (dados == null) {
                return null;
            }

            MetaFinanceira meta =
                    converter(
                            dados
                    );

            substituirNoCache(
                    meta
            );

            return meta;

        } catch (SQLException
                 | IllegalArgumentException
                 | IllegalStateException erro) {

            System.err.println(
                    "Erro ao buscar meta financeira:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return null;
        }
    }

    public static boolean excluirMeta(
            String id) {

        if (id == null
                || id.isBlank()) {

            return false;
        }

        try {

            boolean excluida =
                    metaDAO.excluirMetaAtual(
                            id
                    );

            if (excluida) {

                metas.removeIf(
                        meta ->
                                id.equals(
                                        meta.getId()
                                )
                );
            }

            return excluida;

        } catch (SQLException
                 | IllegalArgumentException
                 | IllegalStateException erro) {

            System.err.println(
                    "Erro ao excluir meta financeira:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static boolean adicionarValor(
            String id,
            double valor) {

        if (id == null
                || id.isBlank()) {

            return false;
        }

        if (valor <= 0) {
            return false;
        }

        try {

            MetaFinanceiraDAO.DadosMeta dados =
                    metaDAO.adicionarValorAtual(
                            id,
                            valor
                    );

            if (dados == null) {
                return false;
            }

            MetaFinanceira metaCache =
                    buscarNoCache(
                            id
                    );

            if (metaCache != null) {

                atualizarMeta(
                        metaCache,
                        dados
                );

            } else {

                metas.add(
                        converter(
                                dados
                        )
                );
            }

            return true;

        } catch (SQLException
                 | IllegalArgumentException
                 | IllegalStateException erro) {

            System.err.println(
                    "Erro ao adicionar valor à meta:"
            );

            System.err.println(
                    erro.getMessage()
            );

            return false;
        }
    }

    public static int quantidade() {

        return getMetas().size();
    }

    private static MetaFinanceira buscarNoCache(
            String id) {

        for (MetaFinanceira meta
                : metas) {

            if (id.equals(
                    meta.getId()
            )) {

                return meta;
            }
        }

        return null;
    }

    private static void substituirNoCache(
            MetaFinanceira novaMeta) {

        for (int i = 0;
             i < metas.size();
             i++) {

            MetaFinanceira atual =
                    metas.get(
                            i
                    );

            if (atual.getId().equals(
                    novaMeta.getId()
            )) {

                metas.set(
                        i,
                        novaMeta
                );

                return;
            }
        }

        metas.add(
                novaMeta
        );
    }

    private static MetaFinanceira converter(
            MetaFinanceiraDAO.DadosMeta dados) {

        return new MetaFinanceira(
                dados.getId(),
                dados.getNome(),
                dados.getDescricao(),
                dados.getValorObjetivo(),
                dados.getValorAtual(),
                dados.getPrevisaoConclusao(),
                dados.isConcluida()
        );
    }

    private static void atualizarMeta(
            MetaFinanceira meta,
            MetaFinanceiraDAO.DadosMeta dados) {

        meta.setNome(
                dados.getNome()
        );

        meta.setDescricao(
                dados.getDescricao()
        );

        meta.setValorObjetivo(
                dados.getValorObjetivo()
        );

        meta.setValorAtual(
                dados.getValorAtual()
        );

        meta.setPrevisaoConclusao(
                dados.getPrevisaoConclusao()
        );

        meta.setConcluida(
                dados.isConcluida()
        );
    }
}