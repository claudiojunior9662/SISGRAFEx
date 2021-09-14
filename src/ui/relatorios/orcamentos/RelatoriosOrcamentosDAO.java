/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.relatorios.orcamentos;

import connection.ConnectionFactory;
import entities.sisgrafex.Cliente;
import entities.sisgrafex.Orcamento;
import entities.sisgrafex.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.OrdemProducaoDAO;
import model.dao.ClienteDAO;

/**
 *
 * @author claud
 */
public class RelatoriosOrcamentosDAO {

    public static List<Orcamento> retornaConteudoRelatorioOrcamento(boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorUnitario,
            boolean valorTotal,
            boolean cif,
            boolean desconto,
            boolean frete,
            boolean dataEmissao,
            boolean dataValidade,
            boolean emissor,
            boolean nomeCliente,
            boolean status,
            byte condicaoCliente,
            byte condicaoOrcamento,
            byte condicaoProduto,
            byte condicaoEmissor,
            byte condicaoPeriodo,
            byte condicaoOrdenar,
            String textoInicioOrcamento,
            String textoFimOrcamento,
            String textoEmissor,
            Date periodoInicial,
            Date periodoFinal,
            Cliente cliente,
            Produto produto) throws SQLException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Orcamento> retorno = new ArrayList();
        String comando = null;

        try {
            comando = retornaComandoOrcamento(codigoOp, codigoOrcamento, codigoCliente, codigoProduto, descricaoProduto, tipoPessoa, quantidade,
                    valorUnitario, valorTotal, cif, desconto, frete, dataEmissao, dataValidade, emissor, nomeCliente, status, condicaoCliente, condicaoOrcamento,
                    condicaoProduto, condicaoEmissor, condicaoPeriodo, condicaoOrdenar, textoInicioOrcamento, textoFimOrcamento, textoEmissor,
                    periodoInicial, periodoFinal, cliente, produto);
            stmt = con.prepareStatement(comando);
            rs = stmt.executeQuery();
            retorno = retornaResultadoQueryOrcamento(rs, codigoOp, codigoOrcamento, codigoCliente, codigoProduto,
                    descricaoProduto, tipoPessoa, quantidade, valorUnitario, valorTotal, cif, desconto, frete,
                    dataEmissao, dataValidade, emissor, nomeCliente, status);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Retorna a lista de códigos de orçamentos existentes para o comando criado
     *
     * @param comando comando criado
     * @param codigoOp
     * @param codigoOrcamento
     * @param codigoCliente
     * @param codigoProduto
     * @param descricaoProduto
     * @param tipoPessoa
     * @param quantidade
     * @param valorUnitario
     * @param valorTotal
     * @param cif
     * @param desconto
     * @param frete
     * @param dataEmissao
     * @param dataValidade
     * @param emissor
     * @param nomeCliente
     * @param status
     * @throws SQLException
     */
    public static void retornaCodOrcamento(String comando,
            boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorUnitario,
            boolean valorTotal,
            boolean cif,
            boolean desconto,
            boolean frete,
            boolean dataEmissao,
            boolean dataValidade,
            boolean emissor,
            boolean nomeCliente,
            boolean status) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            RelatorioOrcamentos.orcamentosAgrupados = new ArrayList();
            stmt = con.prepareStatement(comando);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Orcamento orcamento = new Orcamento();
                if (codigoOrcamento) {
                    orcamento.setCod(rs.getInt("tabela_orcamentos.cod"));
                }
                if (codigoCliente | nomeCliente) {
                    orcamento.setCodCliente(rs.getInt("tabela_orcamentos.cod_cliente"));
                }
                if (codigoProduto) {
                    orcamento.setCodProduto(rs.getInt("tabela_produtos_orcamento.cod_produto"));
                }
                if (descricaoProduto) {
                    orcamento.setDescricaoProduto(rs.getString("produtos.DESCRICAO"));
                }
                if (tipoPessoa | nomeCliente) {
                    orcamento.setTipoPessoa(rs.getInt("tabela_orcamentos.tipo_cliente"));
                }
                if (quantidade) {
                    orcamento.setQuantidade(rs.getInt("tabela_produtos_orcamento.quantidade"));
                }
                if (valorUnitario) {
                    orcamento.setValor_unitario(rs.getFloat("tabela_produtos_orcamento.preco_unitario"));
                }
                if (valorTotal) {
                    orcamento.setValorTotal(rs.getFloat("tabela_orcamentos.valor_total"));
                }
                if (cif) {
                    orcamento.setCif(rs.getFloat("tabela_orcamentos.sif"));
                }
                if (desconto) {
                    orcamento.setDesconto(rs.getFloat("tabela_orcamentos.desconto"));
                }
                if (frete) {
                    orcamento.setFrete(rs.getDouble("tabela_orcamentos.frete"));
                }
                if (dataEmissao) {
                    orcamento.setDataEmissao(rs.getDate("tabela_orcamentos.data_emissao"));
                }
                if (dataValidade) {
                    orcamento.setDataValidade(rs.getDate("tabela_orcamentos.data_validade"));
                }
                if (emissor) {
                    orcamento.setCodEmissor(rs.getString("tabela_orcamentos.cod_emissor"));
                }
                if (status) {
                    orcamento.setStatus(rs.getByte("tabela_orcamentos.status"));
                }
                if (nomeCliente) {
                    orcamento.setNomeCliente(ClienteDAO.retornaNomeCliente(orcamento.getCodCliente(), (byte) orcamento.getTipoPessoa()));
                }
                if (codigoOp) {
                    orcamento.setCodigoOp(OrdemProducaoDAO.retornaCodOpOrcProd(rs.getInt("tabela_orcamentos.cod"),
                            rs.getInt("tabela_produtos_orcamento.cod_produto")));
                }
                RelatorioOrcamentos.orcamentosAgrupados.add(orcamento);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public static String retornaComandoOrcamento(boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorUnitario,
            boolean valorTotal,
            boolean cif,
            boolean desconto,
            boolean frete,
            boolean dataEmissao,
            boolean dataValidade,
            boolean emissor,
            boolean nomeCliente,
            boolean status,
            byte condicaoCliente,
            byte condicaoOrcamento,
            byte condicaoProduto,
            byte condicaoEmissor,
            byte condicaoPeriodo,
            byte condicaoOrdenar,
            String textoInicioOrcamento,
            String textoFimOrcamento,
            String textoEmissor,
            Date periodoInicial,
            Date periodoFinal,
            Cliente cliente,
            Produto produto) throws SQLException {

        String comando = "SELECT";
        int primeiro = 0;

        if (codigoOrcamento) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.cod";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.cod";
            }
        }
        if (codigoCliente || nomeCliente) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.cod_cliente";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.cod_cliente";
            }
        }
        if (tipoPessoa | codigoCliente | nomeCliente) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.tipo_cliente";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.tipo_cliente";
            }
        }
        if (codigoProduto || codigoOp) {
            if (primeiro == 0) {
                comando = comando + " tabela_produtos_orcamento.cod_produto";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_produtos_orcamento.cod_produto";
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
            comando = comando + " tabela_orcamentos.tipo_cliente";
            primeiro += 1;
        } else {
            comando = comando + " , tabela_orcamentos.tipo_cliente";
        }

        if (quantidade) {
            if (primeiro == 0) {
                comando = comando + " tabela_produtos_orcamento.quantidade";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_produtos_orcamento.quantidade";
            }
        }
        if (valorUnitario) {
            if (primeiro == 0) {
                comando = comando + " tabela_produtos_orcamento.preco_unitario";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_produtos_orcamento.preco_unitario";
            }
        }
        if (valorTotal) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.valor_total";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.valor_total";
            }
        }
        if (cif) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.sif";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.sif";
            }
        }
        if (desconto) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.desconto";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.desconto";
            }
        }
        if (frete) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.frete";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.frete";
            }
        }
        if (dataEmissao) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.data_emissao";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.data_emissao";
            }
        }
        if (dataValidade) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.data_validade";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.data_validade";
            }
        }
        if (emissor) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.cod_emissor";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.cod_emissor";
            }
        }
        if (status) {
            if (primeiro == 0) {
                comando = comando + " tabela_orcamentos.status";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_orcamentos.status";
            }
        }

        comando = comando + " FROM tabela_orcamentos";
        primeiro = 0;

        if (quantidade || valorUnitario || codigoOp || codigoProduto || descricaoProduto) {
            comando = comando + " INNER JOIN tabela_produtos_orcamento ON "
                    + "tabela_produtos_orcamento.cod_orcamento = tabela_orcamentos.cod ";
        }
//@param condicaoProduto
//1 - por codigo
//2 - por descricao
//3 - por tipo
//4 - todos
        switch (condicaoProduto) {
            case 1:
                comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_produtos_orcamento.cod_produto ";
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_produtos_orcamento.cod_produto = " + produto.getCodigo();
                break;
            case 2:
                comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_produtos_orcamento.cod_produto ";
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_produtos_orcamento.cod_produto = " + produto.getCodigo();
                break;
            case 3:
                if (descricaoProduto) {
                    comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_produtos_orcamento.cod_produto ";
                    comando = comando + " AND produtos.tipo = '" + produto.getTipo() + "' ";
                } else {
                    comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_produtos_orcamento.cod_produto AND produtos.TIPO = '" + produto.getTipo() + "'";
                }
                break;
            case 4:
                if (descricaoProduto) {
                    comando = comando + " INNER JOIN produtos ON produtos.CODIGO = tabela_produtos_orcamento.cod_produto ";
                }
                break;
        }

//          @param condicaoCliente
//          1 - por codigo
//          2 - por nome
//          3 - por tipo pessoa
//          4 - todos
        switch (condicaoCliente) {
            case 1:
                comando = comando + " WHERE tabela_orcamentos.tipo_cliente = " + cliente.getTipoPessoa() + " AND tabela_orcamentos.cod_cliente = " + cliente.getCodigo();
                primeiro++;
                break;
            case 2:
                comando = comando + " WHERE tabela_orcamentos.tipo_cliente = " + cliente.getTipoPessoa() + " AND tabela_orcamentos.cod_cliente = " + cliente.getCodigo();
                primeiro++;
                break;
            case 3:
                comando = comando + " WHERE tabela_orcamentos.tipo_cliente = " + cliente.getTipoPessoa();
                primeiro++;
                break;
        }
//@param condicaoOrcamento
//1 - por codigo
//2 - por status
//3 - por valor total entre
//4 - todos
        switch (condicaoOrcamento) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.cod = " + textoInicioOrcamento;
                break;
            case 2:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.status = " + textoInicioOrcamento;
                break;
            case 3:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.valor_total BETWEEN " + Float.valueOf(textoInicioOrcamento)
                        + " AND " + Float.valueOf(textoFimOrcamento);
                break;
        }

//@param condicaoEmissor
//1 - por emissor
//2 - todos
        if (condicaoEmissor == 1) {
            if (primeiro != 0) {
                comando = comando + " AND";
            } else {
                comando = comando + " WHERE";
                primeiro++;
            }
            comando = comando + " tabela_orcamentos.cod_emissor = '" + textoEmissor + "'";
        }

//        @param
//        condicaoPeriodo
//        1 - por dia emissao
//        2 - por dia validade
//        3 - por periodo data emissao
//        4 - por periodo data validade
//        5 - todos
        switch (condicaoPeriodo) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.data_emissao = '" + periodoInicial + "'";
                break;
            case 2:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.data_validade = '" + periodoFinal + "'";
                break;
            case 3:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.data_emissao BETWEEN '" + periodoInicial
                        + "' AND '" + periodoFinal + "'";
                break;
            case 4:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_orcamentos.data_validade BETWEEN '" + periodoInicial
                        + "' AND '" + periodoFinal + "'";
                break;
        }

        String comandoCodOrc = comando;
        comandoCodOrc += " GROUP BY tabela_orcamentos.cod";

        //-----Ordenar para lista de códigos--------------------------
        /*     @param condicaoOrdenar
        2 - codigo orcamento
        3 - quantidade crescente
        4 - quantidade decrescente
        5 - emissor
        6 - tipo pessoa
        7 - valor total
        8 - cif
        9 - desconto
        10 - valor frete
        11 - data emissao
        12 - data validade
         */
        switch (condicaoOrdenar) {
            case 2:
                comandoCodOrc += " ORDER BY tabela_orcamentos.cod ASC";
                break;
            case 3:
                comandoCodOrc += " ORDER BY tabela_produtos_orcamento.quantidade ASC";
                break;
            case 4:
                comandoCodOrc += " ORDER BY tabela_produtos_orcamento.quantidade DESC";
                break;
            case 5:
                comandoCodOrc += " ORDER BY tabela_orcamentos.cod_emissor";
                break;
            case 6:
                comandoCodOrc += " ORDER BY tabela_orcamentos.tipo_cliente";
                break;
            case 7:
                comandoCodOrc += " ORDER BY tabela_orcamentos.valor_total";
                break;
            case 8:
                comandoCodOrc += " ORDER BY tabela_orcamentos.sif";
                break;
            case 9:
                comandoCodOrc += " ORDER BY tabela_orcamentos.desconto";
                break;
            case 10:
                comandoCodOrc += " ORDER BY tabela_orcamentos.frete";
                break;
            case 11:
                comandoCodOrc += " ORDER BY tabela_orcamentos.data_emissao";
                break;
            case 12:
                comandoCodOrc += " ORDER BY tabela_orcamentos.data_validade";
                break;
        }
        //-----Ordenar para lista de códigos--------------------------

        retornaCodOrcamento(comandoCodOrc,
                codigoOp,
                codigoOrcamento,
                codigoCliente,
                 codigoProduto,
                descricaoProduto,
                tipoPessoa,
                quantidade,
                valorUnitario,
                valorTotal,
                cif,
                desconto,
                frete,
                dataEmissao,
                dataValidade,
                emissor,
                nomeCliente,
                status
        );

        /*     @param condicaoOrdenar
        2 - codigo orcamento
        3 - quantidade crescente
        4 - quantidade decrescente
        5 - emissor
        6 - tipo pessoa
        7 - valor total
        8 - cif
        9 - desconto
        10 - valor frete
        11 - data emissao
        12 - data validade
         */
        switch (condicaoOrdenar) {
            case 2:
                comando = comando + " ORDER BY tabela_orcamentos.cod ASC";
                break;
            case 3:
                comando = comando + " ORDER BY tabela_produtos_orcamento.quantidade ASC";
                break;
            case 4:
                comando = comando + " ORDER BY tabela_produtos_orcamento.quantidade DESC";
                break;
            case 5:
                comando = comando + " ORDER BY tabela_orcamentos.cod_emissor";
                break;
            case 6:
                comando = comando + " ORDER BY tabela_orcamentos.tipo_cliente";
                break;
            case 7:
                comando = comando + " ORDER BY tabela_orcamentos.valor_total";
                break;
            case 8:
                comando = comando + " ORDER BY tabela_orcamentos.sif";
                break;
            case 9:
                comando = comando + " ORDER BY tabela_orcamentos.desconto";
                break;
            case 10:
                comando = comando + " ORDER BY tabela_orcamentos.frete";
                break;
            case 11:
                comando = comando + " ORDER BY tabela_orcamentos.data_emissao";
                break;
            case 12:
                comando = comando + " ORDER BY tabela_orcamentos.data_validade";
                break;

        }

        System.out.println(comando);
        return comando;

    }

    public static List<Orcamento> retornaResultadoQueryOrcamento(ResultSet rs,
            boolean codigoOp,
            boolean codigoOrcamento,
            boolean codigoCliente,
            boolean codigoProduto,
            boolean descricaoProduto,
            boolean tipoPessoa,
            boolean quantidade,
            boolean valorUnitario,
            boolean valorTotal,
            boolean cif,
            boolean desconto,
            boolean frete,
            boolean dataEmissao,
            boolean dataValidade,
            boolean emissor,
            boolean nomeCliente,
            boolean status) throws SQLException {

        List<Orcamento> retorno = new ArrayList();

        try {

            while (rs.next()) {
                Orcamento orcamento = new Orcamento();

                if (codigoOrcamento) {
                    orcamento.setCod(rs.getInt("tabela_orcamentos.cod"));
                }
                if (codigoCliente | nomeCliente) {
                    orcamento.setCodCliente(rs.getInt("tabela_orcamentos.cod_cliente"));
                }
                if (codigoProduto) {
                    orcamento.setCodProduto(rs.getInt("tabela_produtos_orcamento.cod_produto"));
                }
                if (descricaoProduto) {
                    orcamento.setDescricaoProduto(rs.getString("produtos.DESCRICAO"));
                }
                if (tipoPessoa | nomeCliente) {
                    orcamento.setTipoPessoa(rs.getInt("tabela_orcamentos.tipo_cliente"));
                }
                if (quantidade) {
                    orcamento.setQuantidade(rs.getInt("tabela_produtos_orcamento.quantidade"));
                }
                if (valorUnitario) {
                    orcamento.setValor_unitario(rs.getFloat("tabela_produtos_orcamento.preco_unitario"));
                }
                if (valorTotal) {
                    orcamento.setValorTotal(rs.getFloat("tabela_orcamentos.valor_total"));
                }
                if (cif) {
                    orcamento.setCif(rs.getFloat("tabela_orcamentos.sif"));
                }
                if (desconto) {
                    orcamento.setDesconto(rs.getFloat("tabela_orcamentos.desconto"));
                }
                if (frete) {
                    orcamento.setFrete(rs.getDouble("tabela_orcamentos.frete"));
                }
                if (dataEmissao) {
                    orcamento.setDataEmissao(rs.getDate("tabela_orcamentos.data_emissao"));
                }
                if (dataValidade) {
                    orcamento.setDataValidade(rs.getDate("tabela_orcamentos.data_validade"));
                }
                if (emissor) {
                    orcamento.setCodEmissor(rs.getString("tabela_orcamentos.cod_emissor"));
                }
                if (status) {
                    orcamento.setStatus(rs.getByte("tabela_orcamentos.status"));
                }
                if (nomeCliente) {
                    orcamento.setNomeCliente(ClienteDAO.retornaNomeCliente(orcamento.getCodCliente(), (byte) orcamento.getTipoPessoa()));
                }
                if (codigoOp) {
                    orcamento.setCodigoOp(OrdemProducaoDAO.retornaCodOpOrcProd(rs.getInt("tabela_orcamentos.cod"),
                            rs.getInt("tabela_produtos_orcamento.cod_produto")));
                }

                retorno.add(orcamento);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return retorno;
    }

}
