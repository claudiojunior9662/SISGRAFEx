
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import connection.ConnectionFactory;
import entities.sisgrafex.OrdemProducao;
import entities.sisgrafex.Valores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.OrdemProducaoDAO;
import ui.controle.Controle;
import ui.relatorios.financeiro.ClienteRelFin;

/**
 *
 * @author claudio
 */
public class NewMain {

    final static private String driver = "org.firebirdsql.jdbc.FBDriver";
    static String CHARSET = "?encoding=ISO8859_1";
    static String SERVIDOR = "10.67.32.111";
    static String PORTA = "3050";
    static String USUARIO = "sysdba";
    static String SENHA = "masterkey";

    static List<ClienteRelFin> teste;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ajustaTelefone();
    }

    //--------------------------------------------------------------------------
    private static void mostraCredito() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        double credito;
        DecimalFormat df = new DecimalFormat("###,##0.00");
        int contador = 0;

        try {
            stmt = con.prepareStatement("SELECT cod, nome "
                    + "FROM tabela_clientes_juridicos ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                credito = 0d;

                //CRÉDITO-------------------------------------------------------
//                stmt = con.prepareStatement("SELECT valor "
//                        + "FROM tabela_notas "
//                        + "WHERE DATE_FORMAT(STR_TO_DATE(`data`, '%d/%m/%Y'), '%Y-%m-%d') BETWEEN "
//                        + "DATE_FORMAT(STR_TO_DATE('01/05/2021', '%d/%m/%Y'), '%Y-%m-%d') AND "
//                        + "DATE_FORMAT(STR_TO_DATE('31/05/2021', '%d/%m/%Y'), '%Y-%m-%d') AND "
//                        + "cod_cliente = ? AND tipo_pessoa = 2");
//                stmt.setInt(1, rs.getInt("cod"));
//                rs2 = stmt.executeQuery();
//                while (rs2.next()) {
//                    credito += rs2.getFloat("valor");
//                }
//                DÉBITO--------------------------------------------------------
//                stmt = con.prepareStatement("SELECT faturamentos.VLR_FAT "
//                        + "FROM faturamentos "
//                        + "INNER JOIN tabela_ordens_producao ON tabela_ordens_producao.cod = faturamentos.CODIGO_OP "
//                        + "WHERE faturamentos.DT_FAT BETWEEN '2021-05-01' AND '2021-05-31' AND "
//                        + "tabela_ordens_producao.cod_cliente = ? AND tabela_ordens_producao.tipo_cliente = 2");
//                stmt.setInt(1, rs.getInt("cod"));
//                rs2 = stmt.executeQuery();
//                while (rs2.next()) {
//                    credito -= rs2.getFloat("faturamentos.VLR_FAT");
//                }
//                System.out.println(rs.getInt("cod") + "#" + rs.getString("nome") + "#" + df.format(credito));
                System.out.println(df.format(credito));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void mostraEmAberto() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet count = null;
        double valor;
        DecimalFormat df = new DecimalFormat("###,##0.00");
        List<Integer> codigosProcessados = new ArrayList();
        List<Integer> clientes = new ArrayList();
        List<Valores> teste = new ArrayList();
        List<Valores> teste2 = new ArrayList();
        int cont = 0;

        try {
            stmt = con.prepareStatement("SELECT cod, nome "
                    + "FROM tabela_clientes_juridicos ");
            count = stmt.executeQuery();
            while (count.next()) {
                clientes.add(count.getInt("cod"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            stmt = con.prepareStatement("SELECT tabela_ordens_producao.cod_cliente, "
                    + "tabela_ordens_producao.orcamento_base, "
                    + "tabela_ordens_producao.cod_produto "
                    + "FROM tabela_ordens_producao "
                    + "INNER JOIN tabela_orcamentos ON tabela_orcamentos.cod = tabela_ordens_producao.orcamento_base "
                    + "WHERE tabela_ordens_producao.status != 'ENTREGUE' "
                    + "AND tabela_ordens_producao.status != 'ENTREGUE PARCIALMENTE' "
                    + "AND tabela_ordens_producao.status != 'CANCELADA'"
                    + "AND tabela_ordens_producao.tipo_cliente = 2 "
                    + "AND tabela_ordens_producao.data_emissao BETWEEN '2019-01-01' AND '2021-05-31' "
                    + "ORDER BY tabela_ordens_producao.cod_cliente ASC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (!codigosProcessados.contains(rs.getInt("tabela_ordens_producao.cod_cliente"))) {
                    valor = 0d;
                    stmt = con.prepareStatement("SELECT tabela_ordens_producao.orcamento_base, "
                            + "tabela_ordens_producao.cod_produto "
                            + "FROM tabela_ordens_producao "
                            + "INNER JOIN tabela_orcamentos ON tabela_orcamentos.cod = tabela_ordens_producao.orcamento_base "
                            + "WHERE tabela_ordens_producao.status != 'ENTREGUE' "
                            + "AND tabela_ordens_producao.status != 'ENTREGUE PARCIALMENTE' "
                            + "AND tabela_ordens_producao.status != 'CANCELADA'"
                            + "AND tabela_ordens_producao.tipo_cliente = 2 "
                            + "AND tabela_ordens_producao.cod_cliente = ? "
                            + "AND tabela_ordens_producao.data_emissao BETWEEN '2019-01-01' AND '2021-05-31' "
                            + "ORDER BY tabela_ordens_producao.cod_cliente ASC");
                    stmt.setInt(1, rs.getInt("tabela_ordens_producao.cod_cliente"));
                    rs2 = stmt.executeQuery();
                    while (rs2.next()) {
                        stmt = con.prepareStatement("SELECT (quantidade * preco_unitario) AS valor "
                                + "FROM tabela_produtos_orcamento "
                                + "WHERE cod_orcamento = ? AND cod_produto = ?");
                        stmt.setInt(1, rs2.getInt("tabela_ordens_producao.orcamento_base"));
                        stmt.setInt(2, rs2.getInt("tabela_ordens_producao.cod_produto"));
                        rs3 = stmt.executeQuery();
                        if (rs3.next()) {
                            valor += rs3.getDouble("valor");
                        }
                    }
                    codigosProcessados.add(rs.getInt("tabela_ordens_producao.cod_cliente"));
                    teste.add(new Valores(rs.getInt("tabela_ordens_producao.cod_cliente"), valor * (-1)));

                    //System.out.println(rs.getInt("tabela_ordens_producao.cod_cliente") + ";" + df.format(valor * (-1)));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i : clientes) {
            boolean add = false;
            for (Valores val : teste) {
                if (val.getCodigo() == i) {
                    teste2.add(new Valores(i, val.getValor()));
                    add = true;
                }
            }
            if (!add) {
                teste2.add(new Valores(i, 0));
            }
        }

        for (Valores val : teste2) {
            System.out.println(df.format(val.getValor()));
        }
    }

    private static void ranking() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        List<Integer> opsLidas = new ArrayList();
        int qtdProdutos = 0;
        List<String> ranking = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT tabela_ordens_producao.cod, "
                    + "tabela_ordens_producao.cod_cliente, "
                    + "tabela_ordens_producao.orcamento_base,"
                    + "tabela_ordens_producao.cod_produto,"
                    + "produtos.DESCRICAO "
                    + "FROM tabela_ordens_producao "
                    + "INNER JOIN tabela_orcamentos ON tabela_orcamentos.cod = tabela_ordens_producao.orcamento_base "
                    + "INNER JOIN produtos ON produtos.CODIGO = tabela_ordens_producao.cod_produto "
                    + "WHERE tabela_ordens_producao.status != 'CANCELADA' "
                    + "AND tabela_ordens_producao.data_emissao BETWEEN '2018-01-01' AND '2020-08-13' "
                    + "AND tabela_ordens_producao.tipo_cliente = 2 "
                    + "AND tabela_orcamentos.FAT_TOTALMENTE > 1");
            rs = stmt.executeQuery();
            while (rs.next()) {

                if (!opsLidas.contains(rs.getInt("tabela_ordens_producao.cod"))) {
                    qtdProdutos = 0;
                    stmt = con.prepareStatement("SELECT tabela_ordens_producao.cod, "
                            + "tabela_ordens_producao.orcamento_base,"
                            + "tabela_ordens_producao.cod_produto,"
                            + "produtos.DESCRICAO  "
                            + "FROM tabela_ordens_producao "
                            + "INNER JOIN tabela_orcamentos ON tabela_orcamentos.cod = tabela_ordens_producao.orcamento_base "
                            + "INNER JOIN produtos ON produtos.CODIGO = tabela_ordens_producao.cod_produto "
                            + "WHERE tabela_ordens_producao.status != 'CANCELADA' "
                            + "AND tabela_ordens_producao.cod_cliente = ? "
                            + "AND tabela_ordens_producao.tipo_cliente = 2 "
                            + "AND tabela_ordens_producao.data_emissao BETWEEN '2018-01-01' AND '2020-08-13'"
                            + "AND tabela_orcamentos.FAT_TOTALMENTE > 1");
                    stmt.setInt(1, rs.getInt("cod_cliente"));
                    rs2 = stmt.executeQuery();
                    while (rs2.next()) {
                        opsLidas.add(rs2.getInt("tabela_ordens_producao.cod"));
                        stmt = con.prepareStatement("SELECT quantidade "
                                + "FROM tabela_produtos_orcamento "
                                + "WHERE cod_orcamento = ? AND cod_produto = ?");
                        stmt.setInt(1, rs2.getInt("tabela_ordens_producao.orcamento_base"));
                        stmt.setInt(2, rs2.getInt("tabela_ordens_producao.cod_produto"));
                        rs3 = stmt.executeQuery();
                        if (rs3.next()) {
                            qtdProdutos += rs3.getInt("quantidade");
                        }
                        System.out.println(rs2.getInt("tabela_ordens_producao.cod_produto") + "#"
                                + rs2.getString("produtos.DESCRICAO") + "#"
                                + qtdProdutos);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void preencheAcessoUsr() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        System.out.println(con);

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_atendentes");
            rs = stmt.executeQuery();
            while (rs.next()) {
                stmt = con.prepareStatement("INSERT INTO usuario_acessos VALUES(?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setString(1, rs.getString("codigo_atendente"));
                stmt.setByte(2, rs.getByte("acesso_orc"));
                stmt.setByte(3, rs.getByte("acesso_orc_adm"));
                stmt.setByte(4, rs.getByte("acesso_prod"));
                stmt.setByte(5, rs.getByte("acesso_prod_adm"));
                stmt.setByte(6, rs.getByte("acesso_exp"));
                stmt.setByte(7, rs.getByte("acesso_exp_adm"));
                stmt.setByte(8, rs.getByte("acesso_fin"));
                stmt.setByte(9, rs.getByte("acesso_fin_adm"));
                stmt.setByte(10, rs.getByte("acesso_estoque"));
                stmt.setByte(11, rs.getByte("acesso_ord"));
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void retornaCreditosDebitos() {
        teste = new ArrayList();

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        double credito;
        DecimalFormat df = new DecimalFormat("###,##0.00");

        try {
            stmt = con.prepareStatement("SELECT cod, nome "
                    + "FROM tabela_clientes_fisicos ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                credito = 0d;
                stmt = con.prepareStatement("SELECT valor "
                        + "FROM tabela_notas "
                        + "WHERE DATE_FORMAT(STR_TO_DATE(`data`, '%d/%m/%Y'), '%Y-%m-%d') BETWEEN "
                        + "DATE_FORMAT(STR_TO_DATE('01/01/2019', '%d/%m/%Y'), '%Y-%m-%d') AND "
                        + "DATE_FORMAT(STR_TO_DATE('31/12/2019', '%d/%m/%Y'), '%Y-%m-%d') AND "
                        + "cod_cliente = ? AND tipo_pessoa = 1");
                stmt.setInt(1, rs.getInt("cod"));
                rs2 = stmt.executeQuery();
                while (rs2.next()) {
                    credito += rs2.getFloat("valor");

                }

                ClienteRelFin cliente = new ClienteRelFin(
                        rs.getInt("cod"),
                        rs.getString("nome"),
                        credito
                );

                teste.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void decodificaSenha() {

    }

    private static void alteraStatusOp() {
        try {
            Controle.stsOp = OrdemProducaoDAO.retornaStsOp();

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            List<OrdemProducao> listaOp = new ArrayList();

            stmt = con.prepareStatement("SELECT tabela_ordens_producao.cod, tabela_ordens_producao.status "
                    + "FROM tabela_ordens_producao ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                listaOp.add(new OrdemProducao(
                        rs.getInt("tabela_ordens_producao.cod"),
                        rs.getString("tabela_ordens_producao.status")
                ));
            }

            for (OrdemProducao op : listaOp) {
                byte statusNovo = 0;

                switch (op.getStatusString()) {
                    case "EM AVALIAÇÃO PELA SEÇ TÉCNICA":
                        statusNovo = (byte) 1;
                        break;
                    case "ENCAMINHADO PARA PRÉ IMP":
                        statusNovo = (byte) 2;
                        break;
                    case "DIAGRAMAÇÃO":
                        statusNovo = (byte) 3;
                        break;
                    case "PRODUZINDO PROVA":
                        statusNovo = (byte) 4;
                        break;
                    case "AGUARDANDO APR CLIENTE":
                        statusNovo = (byte) 5;
                        break;
                    case "ENCAMINHADO PARA TIPOGRAFIA":
                        statusNovo = (byte) 8;
                        break;
                    case "ENCAMINHADO PARA ACABAMENTO":
                        statusNovo = (byte) 9;
                        break;
                    case "ENCAMINHADO PARA EXPEDIÇÃO":
                        statusNovo = (byte) 10;
                        break;
                    case "ENTREGUE":
                        statusNovo = (byte) 11;
                        break;
                    case "ENTREGUE PARCIALMENTE":
                        statusNovo = (byte) 12;
                        break;
                    case "CANCELADA":
                        statusNovo = (byte) 13;
                        break;
                }

                stmt = con.prepareStatement("UPDATE tabela_ordens_producao "
                        + "SET status = ? "
                        + "WHERE cod = ?");
                stmt.setByte(1, statusNovo);
                stmt.setInt(2, op.getCodigo());
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void ajustaCEP() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod, cep FROM tabela_enderecos");
            rs = stmt.executeQuery();
            while (rs.next()) {
                stmt2 = con.prepareStatement("UPDATE tabela_enderecos SET cep = ? WHERE cod = ?");
                stmt2.setString(1, Controle.retornaCepFormatado(rs.getString("cep")));
                stmt2.setInt(2, rs.getInt("cod"));
                stmt2.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    private static void ajustaTelefone() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT cod, telefone, telefone2 FROM tabela_contatos");
            rs = stmt.executeQuery();
            while (rs.next()) {
                stmt2 = con.prepareStatement("UPDATE tabela_contatos SET telefone = ?, telefone2 = ? WHERE cod = ?");
                stmt2.setString(1, Controle.retornaTelefoneFormatado(rs.getString("telefone")));
                stmt2.setString(2, Controle.retornaTelefoneFormatado(rs.getString("telefone2")));
                stmt2.setInt(3, rs.getInt("cod"));
                stmt2.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }
}
