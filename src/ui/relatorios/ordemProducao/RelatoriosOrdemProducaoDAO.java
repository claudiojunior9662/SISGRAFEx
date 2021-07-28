/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.relatorios.ordemProducao;

import connection.ConnectionFactory;
import entities.sisgrafex.Cliente;
import entities.sisgrafex.OrdemProducao;
import entities.sisgrafex.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * *
 * @author 1113778771
 *
 * TIPO CONDIÇÃO CLIENTE: 1 - POR CODIGO (TIPO PESSOA, CODIGO) 2 - POR NOME
 * (CLIENTE) 3 - POR TIPO PESSOA (TIPO PESSOA) 4 - TODOS
 *
 * TIPO CONDIÇÃO OP-ORCAMENTO: 1 - ORDEM PRODUCAO (CODIGO) 2 - ORCAMENTO BASE
 * (CODIGO) 3 - TODOS
 *
 * TIPO CONDIÇÃO PRODUTO: 1 - POR CÓDIGO 2 - POR DESCRIÇÃO (PRODUTO) 3 - POR
 * TIPO 4 - TODOS
 *
 * TIPO CONDIÇÃO EMISSOR: 1 - POR EMISSOR 2 - TODOS
 *
 * TIPO CONDIÇÃO PERÍODO: 1 - POR DIA EMISSAO(DATE) 2 - POR DIA ENTREGA (DATE) 3
 * - POR PERIODO DATA EMISSÃO (DATE INICIO, DATE FIM) 4 - POR PERIODO DATA
 * EMISSÃO (DATE INICIO, DATE FIM) 5 - TODOS
 *
 * TIPO CONDIÇÃO ORDENAR: 1 - CÓDIGO CRESCENTE 2 - CÓDIGO DECRESCENTE 3 -
 * QUANTIDADE CRESCENTE 4 - QUANTIDADE DECRESCENTE 5 - EMISSOR 6 - TIPO PESSOA 7
 * - VALOR CRESCENTE 8 - VALOR DECRESCENTE 9 - DATA EMISSÃO MAIS ATUAL 10 - DATA
 * EMISSÃO MAIS ANTIGA 11 - DATA ENTREGA MAIS ATUAL 12 - DATA ENTREGA MAIS
 * ANTIGA
 *
 * TIPO CONDIÇÃO STATUS: 1 - POR STATUS 2 - TODOS
 *
 */
public class RelatoriosOrdemProducaoDAO {

    public static List<OrdemProducao> retornaConteudoRelatorioOrdemProducao(boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorParcial,
            boolean dataEmissao,
            boolean dataEntrega,
            boolean dataEntrega1aProva,
            boolean dataEntrega2aProva,
            boolean dataEntrega3aProva,
            boolean dataEntrega4aProva,
            boolean dataEntrega5aProva,
            boolean operador,
            boolean emissor,
            boolean nomeCliente,
            boolean status,
            byte condicaoCliente,
            byte condicaoOpOrcamento,
            byte condicaoProduto,
            byte condicaoEmissor,
            byte condicaoPeriodo,
            byte condicaoOrdenar,
            byte condicaoStatus,
            int textoOpOrcamento,
            String textoEmissor,
            String textoStatus,
            Date textoPeriodoInicio,
            Date textoPeriodoFim,
            Cliente cliente,
            Produto produto) throws SQLException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<OrdemProducao> retorno = new ArrayList();
        String comando = null;

        try {
            comando = retornaComandoOrdemProducao(codigoOp, codigoOrcamento, codigoCliente, codigoProduto, descricaoProduto, tipoPessoa, quantidade, valorParcial,
                    dataEmissao, dataEntrega, dataEntrega1aProva, dataEntrega2aProva, dataEntrega3aProva, dataEntrega4aProva, dataEntrega5aProva, operador, emissor,
                    nomeCliente, status, condicaoCliente, condicaoOpOrcamento, condicaoProduto, condicaoEmissor, condicaoPeriodo, condicaoOrdenar,
                    condicaoStatus, textoOpOrcamento, textoEmissor, textoStatus, textoPeriodoInicio, textoPeriodoFim, cliente, produto);
            stmt = con.prepareStatement(comando);
            rs = stmt.executeQuery();
            retorno = retornaResultadoQueryOrdemProducao(rs, codigoOp, codigoOrcamento, codigoCliente, codigoProduto, descricaoProduto, tipoPessoa, quantidade, valorParcial, dataEmissao, dataEntrega,
                    dataEntrega1aProva, dataEntrega2aProva, dataEntrega3aProva, dataEntrega4aProva, dataEntrega5aProva, operador, emissor, nomeCliente, status);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static String retornaComandoOrdemProducao(boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorParcial,
            boolean dataEmissao,
            boolean dataEntrega,
            boolean dataEntrega1aProva,
            boolean dataEntrega2aProva,
            boolean dataEntrega3aProva,
            boolean dataEntrega4aProva,
            boolean dataEntrega5aProva,
            boolean operador,
            boolean emissor,
            boolean nomeCliente,
            boolean status,
            byte condicaoCliente,
            byte condicaoOpOrcamento,
            byte condicaoProduto,
            byte condicaoEmissor,
            byte condicaoPeriodo,
            byte condicaoOrdenar,
            byte condicaoStatus,
            int textoOpOrcamento,
            String textoEmissor,
            String textoStatus,
            Date textoPeriodoInicio,
            Date textoPeriodoFim,
            Cliente cliente,
            Produto produto) {

        String comando = "SELECT";
        int primeiro = 0;

        if (codigoOp) {
            comando = comando + " tabela_ordens_producao.cod";
            primeiro += 1;
        }
        if (codigoOrcamento) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.orcamento_base";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.orcamento_base";
            }
        }
        if (codigoCliente || nomeCliente) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.cod_cliente";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.cod_cliente";
            }
        }
        if (codigoProduto || descricaoProduto) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.cod_produto, tabela_ordens_producao.tipo_produto";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.cod_produto, tabela_ordens_producao.tipo_produto";
            }
        }
        if (descricaoProduto) {
            if (primeiro == 0) {
                comando = comando + " produtos.DESCRICAO";
                primeiro += 1;
            } else {
                comando = comando + " , produtos.DESCRICAO";
            }
        }

        if (primeiro == 0) {
            comando = comando + " tabela_ordens_producao.tipo_cliente";
            primeiro += 1;
        } else {
            comando = comando + " , tabela_ordens_producao.tipo_cliente";
        }

        if (quantidade) {
            if (primeiro == 0) {
                comando = comando + " tabela_produtos_orcamento.quantidade";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_produtos_orcamento.quantidade";
            }
        }
        if (valorParcial) {
            if (primeiro == 0) {
                comando = comando + " (tabela_produtos_orcamento.quantidade * tabela_produtos_orcamento.preco_unitario) AS valor_parcial";
                primeiro += 1;
            } else {
                comando = comando + " , (tabela_produtos_orcamento.quantidade * tabela_produtos_orcamento.preco_unitario) AS valor_parcial";
            }
        }
        if (dataEmissao) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_emissao";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_emissao";
            }
        }
        if (dataEntrega) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_entrega";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_entrega";
            }
        }
        if (dataEntrega1aProva) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_1a_prova";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_1a_prova";
            }
        }
        if (dataEntrega2aProva) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_2a_prova";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_2a_prova";
            }
        }
        if (dataEntrega3aProva) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_3a_prova";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_3a_prova";
            }
        }
        if (dataEntrega4aProva) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_4a_prova";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_4a_prova";
            }
        }
        if (dataEntrega5aProva) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.data_5a_prova";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.data_5a_prova";
            }
        }
        if (emissor) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.cod_emissor";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.cod_emissor";
            }
        }
        if (operador) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.op_secao";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.op_secao";
            }
        }
        if (status) {
            if (primeiro == 0) {
                comando = comando + " tabela_ordens_producao.status";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_ordens_producao.status";
            }
        }

        comando = comando + " FROM tabela_ordens_producao";
        primeiro = 0;

        if (quantidade || valorParcial) {
            comando = comando + " INNER JOIN tabela_produtos_orcamento ON "
                    + "tabela_produtos_orcamento.cod_orcamento = tabela_ordens_producao.orcamento_base AND "
                    + "tabela_produtos_orcamento.cod_produto = tabela_ordens_producao.cod_produto";
        }

        switch (condicaoProduto) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.cod_produto = " + produto.getCodigo();
                break;
            case 2:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.cod_produto = " + produto.getCodigo();
                break;
            case 3:
                if (descricaoProduto) {
                    comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_ordens_producao.cod_produto ";
                    comando = comando + " AND produtos.TIPO = '" + produto.getTipo() + "' ";
                } else {
                    comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_ordens_producao.cod_produto AND produtos.TIPO = '" + produto.getTipo() + "'";
                }
                break;
            case 4:
                if (descricaoProduto) {
                    comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_ordens_producao.cod_produto ";
                }
                break;
        }

        switch (condicaoCliente) {
            case 1:
                comando = comando + " WHERE tabela_ordens_producao.tipo_cliente = " + cliente.getTipoPessoa() + " AND tabela_ordens_producao.cod_cliente = " + cliente.getCodigo();
                primeiro++;
                break;
            case 2:
                comando = comando + " WHERE tabela_ordens_producao.tipo_cliente = " + cliente.getTipoPessoa() + " AND tabela_ordens_producao.cod_cliente = " + cliente.getCodigo();
                primeiro++;
                break;
            case 3:
                comando = comando + " WHERE tabela_ordens_producao.tipo_cliente = " + cliente.getTipoPessoa();
                primeiro++;
                break;
        }

        switch (condicaoOpOrcamento) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.cod = " + textoOpOrcamento;
                break;
            case 2:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.orcamento_base = " + textoOpOrcamento;
                break;
        }

        if (condicaoEmissor == 1) {
            if (primeiro != 0) {
                comando = comando + " AND";
            } else {
                comando = comando + " WHERE";
                primeiro++;
            }
            comando = comando + " tabela_ordens_producao.cod_emissor = '" + textoEmissor + "'";
        }

        switch (condicaoPeriodo) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.data_emissao = '" + textoPeriodoInicio + "'";
                break;
            case 2:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.data_entrega = '" + textoPeriodoInicio + "'";
                break;
            case 3:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.data_emissao BETWEEN '" + textoPeriodoInicio + "' AND '" + textoPeriodoFim + "'";
                break;
            case 4:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.data_entrega BETWEEN '" + textoPeriodoInicio + "' AND '" + textoPeriodoFim + "'";
                break;
        }

        switch (condicaoStatus) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_ordens_producao.status = '" + textoStatus.split(" ")[0] + "'";
                break;
        }

        switch (condicaoOrdenar) {
            case 1:
                comando = comando + " ORDER BY tabela_ordens_producao.cod ASC";
                break;
            case 2:
                comando = comando + " ORDER BY tabela_ordens_producao.cod DESC";
                break;
            case 3:
                comando = comando + " ORDER BY tabela_produtos_orcamento.quantidade ASC";
                break;
            case 4:
                comando = comando + " ORDER BY tabela_produtos_orcamento.quantidade DESC";
                break;
            case 5:
                comando = comando + " ORDER BY tabela_ordens_producao.cod_emissor ASC";
                break;
            case 6:
                comando = comando + " ORDER BY tabela_ordens_producao.tipo_cliente ASC";
                break;
            case 7:
                comando = comando + " ORDER BY valor_parcial ASC";
                break;
            case 8:
                comando = comando + " ORDER BY valor_parcial DESC";
                break;
            case 9:
                comando = comando + " ORDER BY data_emissao DESC";
                break;
            case 10:
                comando = comando + " ORDER BY data_emissao ASC";
                break;
            case 11:
                comando = comando + " ORDER BY data_entrega DESC";
                break;
            case 12:
                comando = comando + " ORDER BY data_entrega ASC";
                break;
        }

        System.out.println(comando);
        return comando;

    }

    public static List<OrdemProducao> retornaResultadoQueryOrdemProducao(ResultSet rs,
            boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorParcial,
            boolean dataEmissao,
            boolean dataEntrega,
            boolean dataEntrega1aProva,
            boolean dataEntrega2aProva,
            boolean dataEntrega3aProva,
            boolean dataEntrega4aProva,
            boolean dataEntrega5aProva,
            boolean operador,
            boolean emissor,
            boolean nomeCliente,
            boolean status) throws SQLException {

        List<OrdemProducao> retorno = new ArrayList();

        try {

            while (rs.next()) {
                OrdemProducao ordensProducao = new OrdemProducao();

                if (codigoOp) {
                    ordensProducao.setCodigo(rs.getInt("tabela_ordens_producao.cod"));
                }
                if (codigoOrcamento) {
                    ordensProducao.setOrcBase(rs.getInt("tabela_ordens_producao.orcamento_base"));
                }
                if (codigoCliente || nomeCliente) {
                    ordensProducao.setCodCliente(rs.getInt("tabela_ordens_producao.cod_cliente"));
                }
                if (codigoProduto | descricaoProduto) {
                    ordensProducao.setCodProduto(rs.getInt("tabela_ordens_producao.cod_produto"));
                    ordensProducao.setTipoProduto(rs.getByte("tabela_ordens_producao.tipo_produto"));
                }

                ordensProducao.setTipoPessoa(rs.getByte("tabela_ordens_producao.tipo_cliente"));

                if (quantidade) {
                    ordensProducao.setQuantidade(rs.getInt("tabela_produtos_orcamento.quantidade"));
                }
                if (valorParcial) {
                    ordensProducao.setValorParcial(rs.getFloat("valor_parcial"));
                }
                if (dataEmissao) {
                    ordensProducao.setDataEmissao(rs.getDate("tabela_ordens_producao.data_emissao"));
                }
                if (dataEntrega) {
                    ordensProducao.setDataEntrega(rs.getDate("tabela_ordens_producao.data_entrega"));
                }
                if(dataEntrega1aProva){
                    ordensProducao.setData1aProva(rs.getDate("tabela_ordens_producao.data_1a_prova"));
                }
                if(dataEntrega2aProva){
                    ordensProducao.setData2aProva(rs.getDate("tabela_ordens_producao.data_2a_prova"));
                }
                if(dataEntrega3aProva){
                    ordensProducao.setData3aProva(rs.getDate("tabela_ordens_producao.data_3a_prova"));
                }
                if(dataEntrega4aProva){
                    ordensProducao.setData4aProva(rs.getDate("tabela_ordens_producao.data_4a_prova"));
                }
                if(dataEntrega5aProva){
                    ordensProducao.setData5aProva(rs.getDate("tabela_ordens_producao.data_5a_prova"));
                }
                if(operador){
                    ordensProducao.setOpSecao(rs.getString("tabela_ordens_producao.op_secao"));
                }
                if (emissor) {
                    ordensProducao.setCodEmissor(rs.getString("tabela_ordens_producao.cod_emissor"));
                }
                if (status) {
                    ordensProducao.setStatus(rs.getByte("tabela_ordens_producao.status"));
                }

                retorno.add(ordensProducao);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return retorno;
    }
}
