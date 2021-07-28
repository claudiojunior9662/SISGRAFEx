/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.relatorios.papeis;

import connection.ConnectionFactory;
import entities.sisgrafex.Papel;
import exception.ConsultaSemResultadoException;
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
 */
public class RelatoriosPapeisDAO {

    public static List<Papel> retornaConteudoRelatorioPapeis(boolean qtdGasta,
            boolean descricaoPapel,
            boolean codPapel,
            boolean precoUnit,
            boolean gramatura,
            byte tipoCondicaoPapel,
            byte tipoCondicaoOp,
            byte tipoCondicaoPeriodo,
            byte tipoCondicaoOrdenar,
            Papel papel,
            int op,
            Date textoPeriodoInicio,
            Date textoPeriodoFim) throws SQLException {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Papel> retorno = new ArrayList();
        String comando = null;

        try {
            comando = retornaComandoOrdemProducao(qtdGasta, descricaoPapel, codPapel, precoUnit, gramatura, tipoCondicaoPapel, tipoCondicaoOp,
                    tipoCondicaoPeriodo, tipoCondicaoOrdenar, papel, op, textoPeriodoInicio, textoPeriodoFim);
            stmt = con.prepareStatement(comando);
            rs = stmt.executeQuery();
            retorno = retornaResultadoQueryOrdemProducao(rs, qtdGasta, descricaoPapel, codPapel, precoUnit, gramatura);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static String retornaComandoOrdemProducao(boolean qtdGasta,
            boolean descricaoPapel,
            boolean codPapel,
            boolean precoUnit,
            boolean gramatura,
            byte tipoCondicaoPapel,
            byte tipoCondicaoOp,
            byte tipoCondicaoPeriodo,
            byte tipoCondicaoOrdenar,
            Papel papel,
            int op,
            Date textoPeriodoInicio,
            Date textoPeriodoFim) {

        String comando = "SELECT";
        int primeiro = 0;

        if (qtdGasta) {
            comando = comando + " Sum(tabela_calculos_op.qtd_folhas_total) AS QTD_GASTA";
            primeiro += 1;
        }
        if (descricaoPapel) {
            if (primeiro == 0) {
                comando = comando + " tabela_papeis.descricao";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_papeis.descricao";
            }
        }
        if (codPapel) {
            if (primeiro == 0) {
                comando = comando + " tabela_calculos_op.cod_papel";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_calculos_op.cod_papel";
            }
        }
        if (precoUnit) {
            if (primeiro == 0) {
                comando = comando + " tabela_papeis.unitario";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_papeis.unitario";
            }
        }
        if (gramatura) {
            if (primeiro == 0) {
                comando = comando + " tabela_papeis.gramatura";
                primeiro += 1;
            } else {
                comando = comando + " , tabela_papeis.gramatura";
            }
        }

        comando = comando + " FROM tabela_calculos_op";
        primeiro = 0;

        if (descricaoPapel | precoUnit | gramatura) {
            comando = comando + " INNER JOIN tabela_papeis ON "
                    + "tabela_papeis.cod = tabela_calculos_op.cod_papel";
        }

        if (tipoCondicaoPeriodo != 5) {
            comando = comando + " INNER JOIN tabela_ordens_producao ON "
                    + "tabela_ordens_producao.cod = tabela_calculos_op.cod_op";
        }

        /* tipo condicao papel
        * 1 - por papel (codigo)
        * 2 - por papel (descricao)
         3 - todos*/
        switch (tipoCondicaoPapel) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_calculos_op.cod_papel = " + papel.getCod();
                break;
            case 2:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_calculos_op.cod_papel = " + papel.getCod();
                break;
        }

        /* tipo condicao ordem producao
        * 1 - por ordem de producao (codigo)
        * 2 - todos*/
        switch (tipoCondicaoOp) {
            case 1:
                if (primeiro != 0) {
                    comando = comando + " AND";
                } else {
                    comando = comando + " WHERE";
                    primeiro++;
                }
                comando = comando + " tabela_calculos_op.cod_op = " + op;
                primeiro++;
                break;
        }
        /* tipo condicao periodo
        * 1 - por dia emissao
        * 2 - por dia entrega
        * 3 - por periodo data emissão
        * 4 - por periodo data entrega
        * 5 - todos*/
        switch (tipoCondicaoPeriodo) {
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
                comando = comando + " tabela_ordens_producao.data_entrega = BETWEEN '" + textoPeriodoInicio + "' AND '" + textoPeriodoFim + "'";
                break;
        }
        
        if(qtdGasta){
            comando += " GROUP BY tabela_calculos_op.cod_papel";
        }

        /*
        tipo condicao ordenar
        1 - por quantidade gasta cresc
        2 - por quantidade gasta desc
        3 - por descricao do papel
        4 - por codigo do papel
        5 - por preco unitario
        */
        switch (tipoCondicaoOrdenar) {
            case 1:
                comando = comando + " ORDER BY QTD_GASTA ASC";
                break;
            case 2:
                comando = comando + " ORDER BY QTD_GASTA DESC";
                break;
            case 3:
                comando = comando + " ORDER BY tabela_papeis.descricao";
                break;
            case 4:
                comando = comando + " ORDER BY tabela_papeis.cod";
                break;
            case 5:
                comando = comando + " ORDER BY tabela_papeis.unitario";
                break;
        }

        System.out.println(comando);
        return comando;

    }

    public static List<Papel> retornaResultadoQueryOrdemProducao(ResultSet rs,
            boolean qtdGasta,
            boolean descricaoPapel,
            boolean codPapel,
            boolean precoUnit,
            boolean gramatura) throws SQLException {

        List<Papel> retorno = new ArrayList();

        try {

            while (rs.next()) {
                Papel papeis = new Papel();

                if (qtdGasta) {
                    papeis.setQtdGasta(rs.getInt("QTD_GASTA"));
                }
                if (descricaoPapel) {
                    papeis.setDescricao(rs.getString("tabela_papeis.descricao"));
                }
                if (codPapel) {
                    papeis.setCod(rs.getInt("tabela_calculos_op.cod_papel"));
                }
                if (precoUnit) {
                    papeis.setUnitario(rs.getDouble("tabela_papeis.unitario"));
                }
                if (gramatura) {
                    papeis.setGramatura(rs.getInt("tabela_papeis.gramatura"));
                }
                
                retorno.add(papeis);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return retorno;
    }
}
