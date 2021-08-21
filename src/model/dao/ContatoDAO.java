/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import entities.sisgrafex.Contato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ui.controle.Controle;

/**
 *
 * @author claudio
 */
public class ContatoDAO {

    /**
     * Verifica se existe cadastro para o contato informado
     *
     * @param contato
     * @return
     */
    public static Boolean verificaContato(Contato contato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (contato.getTelefone2() != null && contato.getTelefone() == null) {
                stmt = con.prepareStatement("SELECT tabela_contatos.cod "
                        + "FROM tabela_contatos "
                        + "WHERE tabela_contatos.telefone2 = ? ");
                stmt.setString(1, Controle.retornaTelefoneFormatado(contato.getTelefone2().replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
                System.out.println(stmt);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } else if(contato.getTelefone() != null && contato.getTelefone2() == null) {
                stmt = con.prepareStatement("SELECT tabela_contatos.cod "
                        + "FROM tabela_contatos "
                        + "WHERE tabela_contatos.telefone = ? ");
                stmt.setString(1, Controle.retornaTelefoneFormatado(contato.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }else{
                stmt = con.prepareStatement("SELECT tabela_contatos.cod "
                        + "FROM tabela_contatos "
                        + "WHERE tabela_contatos.telefone = ? "
                        + "OR tabela_contatos.telefone2 = ?");
                stmt.setString(1, Controle.retornaTelefoneFormatado(contato.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
                stmt.setString(2, Controle.retornaTelefoneFormatado(contato.getTelefone2().replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna o código do contato a partir dos telefones informados
     *
     * @param contato
     * @return
     * @throws SQLException
     */
    public synchronized static int retornaCodPorTelefone(Contato contato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT tabela_contatos.cod "
                    + "FROM tabela_contatos "
                    + "WHERE tabela_contatos.telefone = ? "
                    + "OR tabela_contatos.telefone2 = ?");
            stmt.setString(1, contato.getTelefone().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
            stmt.setString(2, contato.getTelefone2().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("tabela_contatos.cod");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static List<Integer> selecionaContatos(byte tipoPessoa, Integer codigoCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> retorno = new ArrayList();
        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("SELECT cod_contato "
                        + "FROM tabela_associacao_contatos "
                        + "WHERE cod_cliente = ? "
                        + "AND tipo_cliente = 1");
                stmt.setInt(1, codigoCliente);
            }
            if (tipoPessoa == 2) {
                stmt = con.prepareStatement("SELECT cod_contato "
                        + "FROM tabela_associacao_contatos "
                        + "WHERE cod_cliente = ? "
                        + "AND tipo_cliente = 2");
                stmt.setInt(1, codigoCliente);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(rs.getInt("cod_contato"));
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Retorna último registro de contato do BD
     *
     * @return
     * @throws SQLException
     */
    public static int retornaUltimoRegistroContatos() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT cod " + "FROM tabela_contatos " + "ORDER BY cod " + "DESC LIMIT 1");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Atualiza registro de contato no BD
     *
     * @param contato
     * @param codigoContato
     * @throws SQLException
     */
    public static void atualizaContatos(Contato contato, int codigoContato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tabela_contatos SET  nome_contato = ?," + "email = ?, telefone = ?, ramal = ?, telefone2 = ?," + "ramal2 = ?, departamento = ? WHERE cod = ?");
            stmt.setString(1, contato.getNomeContato());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getTelefone());
            stmt.setString(4, contato.getRamal());
            stmt.setString(5, contato.getTelefone2());
            stmt.setString(6, contato.getRamal2());
            stmt.setString(7, contato.getDepartamento());
            stmt.setInt(8, codigoContato);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Realiza o registro de contato no BD
     *
     * @param contato
     * @throws SQLException
     */
    public static void gravaContatos(Contato contato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO tabela_contatos(cod, nome_contato, email," + "telefone, ramal, telefone2, ramal2, departamento" + ") "
                    + "VALUES(?,?,?,?,?,?,?,?)");
            stmt.setInt(1, contato.getCod());
            stmt.setString(2, contato.getNomeContato());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getTelefone());
            stmt.setString(5, contato.getRamal());
            stmt.setString(6, contato.getTelefone2());
            stmt.setString(7, contato.getRamal2());
            stmt.setString(8, contato.getDepartamento());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
