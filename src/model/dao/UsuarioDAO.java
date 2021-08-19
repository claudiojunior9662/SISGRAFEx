/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.sisgrafex.Usuario;
import java.util.Date;

/**
 *
 * @author spd3
 */
public class UsuarioDAO {

    //CRUD----------------------------------------------------------------------
    /**
     * Cria um novo usuário
     * @param usuario novo usuário
     * @throws SQLException 
     */
    public static void cria(Usuario usuario) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            /**
             * Insere as informações do usuário na tabela atendentes
             */
            stmt = con.prepareStatement("INSERT INTO tabela_atendentes("
                    + "codigo_atendente,"
                    + "nome_atendente,"
                    + "login_atendente,"
                    + "senha_atendente,"
                    + "tipo_atendente,"
                    + "mudanca_senha,"
                    + "ativo)"
                    + "VALUES (?,?,?,md5(?),?,?,?)");
            stmt.setString(1, usuario.getCodigo());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getLogin());
            stmt.setString(4, usuario.getSenhaAtendente());
            stmt.setString(5, usuario.getTipo());
            stmt.setDate(6, new java.sql.Date(usuario.getUltMudSenha().getTime()));
            stmt.setByte(7, (byte) 1);
            stmt.executeUpdate();
            
            /**
             * Insere os acessos do usuário na tabela usuario acessos
             */
            stmt = con.prepareStatement("INSERT INTO usuario_acessos "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?);");
            
            stmt.setString(1, usuario.getCodigo());
            stmt.setByte(2, usuario.getAcessoOrc());
            stmt.setByte(3, usuario.getAcessoOrcAdm());
            stmt.setByte(4, usuario.getAcessoProd());
            stmt.setByte(5, usuario.getAcessoProdAdm());
            stmt.setByte(6, usuario.getAcessoExp());
            stmt.setByte(7, usuario.getAcessoExpAdm());
            stmt.setByte(8, usuario.getAcessoFin());
            stmt.setByte(9, usuario.getAcessoFinAdm());
            stmt.setByte(10, usuario.getAcessoEst());
            stmt.setByte(11, usuario.getAcessoOrd());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Atualiza um usuário
     * @param usuario usuário a ser atualizado
     * @throws SQLException 
     */
    public static void atualiza(Usuario usuario) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            /**
             * Insere as informações do usuário na tabela atendentes
             */
            stmt = con.prepareStatement("UPDATE tabela_atendentes "
                    + "SET nome_atendente = ?, login_atendente = ?, senha_atendente = md5(?),"
                    + "tipo_atendente = ?, mudanca_senha = ?, ativo = ? "
                    + "WHERE codigo_atendente = ?");
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenhaAtendente());
            stmt.setString(4, usuario.getTipo());
            stmt.setDate(5, new java.sql.Date(usuario.getUltMudSenha().getTime()));
            stmt.setByte(6, (byte) 1);
            stmt.setString(7, usuario.getCodigo());
            stmt.executeUpdate();
            
            /**
             * Insere os acessos do usuário na tabela usuario acessos
             */
            stmt = con.prepareStatement("UPDATE usuario_acessos "
                    + "SET ORC = ?, ORC_ADM = ?, PROD = ?, PROD_ADM = ?, EXP = ?, EXP_ADM = ?,"
                    + "FIN = ?, FIN_ADM = ?, EST = ?, ORD = ? "
                    + "WHERE CODIGO_USR = ?");
            
            stmt.setByte(1, usuario.getAcessoOrc());
            stmt.setByte(2, usuario.getAcessoOrcAdm());
            stmt.setByte(3, usuario.getAcessoProd());
            stmt.setByte(4, usuario.getAcessoProdAdm());
            stmt.setByte(5, usuario.getAcessoExp());
            stmt.setByte(6, usuario.getAcessoExpAdm());
            stmt.setByte(7, usuario.getAcessoFin());
            stmt.setByte(8, usuario.getAcessoFinAdm());
            stmt.setByte(9, usuario.getAcessoEst());
            stmt.setByte(10, usuario.getAcessoOrd());
            stmt.setString(11, usuario.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Desativa um usuário
     * @param estado estado 1 - ativo, 0 - desativo
     * @param codigoAtendente código do usuário
     * @throws SQLException 
     */
    public static void ativaDesativa(Integer estado, String codigoAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_atendentes "
                    + "SET ativo = ? "
                    + "WHERE codigo_atendente = ?");
            stmt.setInt(1, estado);
            stmt.setString(2, codigoAtendente);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    //--------------------------------------------------------------------------

    public boolean verificaLogin(String login) throws SQLException {
        boolean retorno = false;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = con.prepareStatement("SELECT * FROM tabela_atendentes WHERE login_atendente = ?");

        try {
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (rs.next()) {
                retorno = true;
            }
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static boolean verificaCodigo(String codigo) throws SQLException {
        boolean retorno = false;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = con.prepareStatement("SELECT codigo_atendente FROM tabela_atendentes WHERE codigo_atendente = ?");

        try {
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                retorno = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public boolean verificaVersao(String versao) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean retorno = false;
        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_versoes WHERE n_versao = ?");
            stmt.setString(1, versao);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;
            }
            stmt.close();
        } catch (SQLException ex) {
            retorno = false;
            System.out.println("Erro na função verifica versao " + ex);
        }
        return retorno;
    }

    public static List<Usuario> retornaAcessos(String login_atendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> cadastrofc = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT acesso_orc, acesso_prod, acesso_exp, acesso_fin, acesso_estoque,"
                    + "acesso_ord FROM tabela_atendentes WHERE login_atendente = ?");
            stmt.setString(1, login_atendente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario cadastroFuncionariosBEAN = new Usuario();
                cadastroFuncionariosBEAN.setAcessoOrc(rs.getByte("acesso_orc"));
                cadastroFuncionariosBEAN.setAcessoProd(rs.getByte("acesso_prod"));
                cadastroFuncionariosBEAN.setAcessoExp(rs.getByte("acesso_exp"));
                cadastroFuncionariosBEAN.setAcessoFin(rs.getByte("acesso_fin"));
                cadastroFuncionariosBEAN.setAcessoEst(rs.getByte("acesso_estoque"));
                cadastroFuncionariosBEAN.setAcessoOrd(rs.getByte("acesso_ord"));
                cadastrofc.add(cadastroFuncionariosBEAN);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return cadastrofc;
    }

    public static List<Usuario> carregaLista() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_atendentes "
                    + "INNER JOIN usuario_acessos "
                    + "ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                    + "ORDER BY nome_atendente ASC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Usuario(
                        rs.getString("nome_atendente"),
                        rs.getString("codigo_atendente"),
                        rs.getString("login_atendente"),
                        rs.getString("tipo_atendente"),
                        rs.getByte("usuario_acessos.ORC"),
                        rs.getByte("usuario_acessos.ORC_ADM"),
                        rs.getByte("usuario_acessos.PROD"),
                        rs.getByte("usuario_acessos.PROD_ADM"),
                        rs.getByte("usuario_acessos.EXP"),
                        rs.getByte("usuario_acessos.EXP_ADM"),
                        rs.getByte("usuario_acessos.FIN"),
                        rs.getByte("usuario_acessos.FIN_ADM"),
                        rs.getByte("usuario_acessos.EST"),
                        rs.getByte("usuario_acessos.ORD"),
                        rs.getByte("ativo"),
                        rs.getTimestamp("DT_ULT_LOGIN")
                ));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static void excluiFuncionario(String codigoAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tabela_atendentes WHERE codigo_atendente = ?");
            stmt.setString(1, codigoAtendente);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna pesquisa de usuários
     *
     * @param tipo
     * @param tipoAux
     * @param texto
     * @return
     * @throws SQLException
     */
    public static List<Usuario> retornaPesquisa(String tipo,
            String tipoAux,
            String texto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> retorno = new ArrayList();

        try {
            if (tipo.equals("NOME ATENDENTE")) {
                stmt = con.prepareStatement("SELECT * "
                        + "FROM tabela_atendentes "
                        + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                        + "WHERE nome_atendente = ? "
                        + "ORDER BY codigo_atendente "
                        + "ASC");
                stmt.setString(1, texto);
            }
            if (tipo.equals("CÓDIGO ATENDENTE")) {
                stmt = con.prepareStatement("SELECT * "
                        + "FROM tabela_atendentes "
                        + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                        + "WHERE codigo_atendente = ?");
                stmt.setString(1, texto);
            }
            if (tipo.equals("LOGIN ATENDENTE")) {
                stmt = con.prepareStatement("SELECT * "
                        + "FROM tabela_atendentes "
                        + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                        + "WHERE login_atendente = ?");
                stmt.setString(1, texto);
            }
            if (tipo.equals("TIPO ATENDENTE")) {
                if (tipoAux.equals("USUÁRIO")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE tipo_atendente = 'USUÁRIO' "
                            + "ORDER BY codigo_atendente "
                            + "ASC");
                }
                if (tipoAux.equals("ADMINISTRADOR")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE tipo_atendente = 'ADMINISTRADOR' "
                            + "ORDER BY codigo_atendente "
                            + "ASC");
                }
            }
            if (tipo.equals("ACESSO PRODUÇÃO")) {
                if (tipoAux.equals("SIM")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE acesso_prod = 1 "
                            + "ORDER BY codigo_atendente "
                            + "ASC");
                } else {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE acesso_prod = 0 "
                            + "ORDER BY codigo_atendente "
                            + "ASC");
                }
            }
            if (tipo.equals("ACESSO ORÇAMENTAÇÃO")) {
                if (tipoAux.equals("SIM")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE acesso_orc = 1 "
                            + "ORDER BY codigo_atendente "
                            + "ASC");
                } else {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE acesso_orc = 0 "
                            + "ORDER BY codigo_atendente "
                            + "ASC");
                }
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Usuario(
                        rs.getString("nome_atendente"),
                        rs.getString("codigo_atendente"),
                        rs.getString("login_atendente"),
                        rs.getString("tipo_atendente"),
                        rs.getByte("usuario_acessos.ORC"),
                        rs.getByte("usuario_acessos.ORC_ADM"),
                        rs.getByte("usuario_acessos.PROD"),
                        rs.getByte("usuario_acessos.PROD_ADM"),
                        rs.getByte("usuario_acessos.EXP"),
                        rs.getByte("usuario_acessos.EXP_ADM"),
                        rs.getByte("usuario_acessos.FIN"),
                        rs.getByte("usuario_acessos.FIN_ADM"),
                        rs.getByte("usuario_acessos.EST"),
                        rs.getByte("usuario_acessos.ORD"),
                        rs.getByte("ativo"),
                        rs.getTimestamp("DT_ULT_LOGIN")
                ));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public String retornaSenha(String codAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            stmt = con.prepareStatement("SELECT senha_atendente FROM tabela_atendentes WHERE codigo_atendente = ?");
            stmt.setString(1, codAtendente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("senha_atendente");
            }
            stmt.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    

    public static void resetaSenha(String loginAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_atendentes SET senha_atendente = md5(?), mudanca_senha = '2018-01-01' "
                    + "WHERE login_atendente = ?");
            stmt.setString(1, loginAtendente);
            stmt.setString(2, loginAtendente);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Retorna atendentes
     *
     * @param acesso 0 - TODOS 1 - PRODUCAO 2 - ORCAMENTO 3 - EXPEDIÇÃO 4 -
     * FINANCEIRO 5 - ESTOQUE
     */
    public static List<Usuario> retornaAtendentes(byte acesso) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> retorno = new ArrayList();

        try {
            switch (acesso) {
                case 0:
                    stmt = con.prepareStatement("SELECT codigo_atendente, nome_atendente "
                            + "FROM tabela_atendentes "
                            + "ORDER BY nome_atendente ASC");
                    break;
                case 1:
                    stmt = con.prepareStatement("SELECT codigo_atendente, nome_atendente "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE usuario_acessos.PROD = 1 "
                            + "ORDER BY nome_atendente ASC");
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT codigo_atendente, nome_atendente "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE usuario_acessos.ORC = 1 "
                            + "ORDER BY nome_atendente ASC");
                    break;
                case 3:
                    stmt = con.prepareStatement("SELECT codigo_atendente, nome_atendente "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE usuario_acessos.EXP = 1 "
                            + "ORDER BY nome_atendente ASC");
                    break;
                case 4:
                    stmt = con.prepareStatement("SELECT codigo_atendente, nome_atendente "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE usuario_acessos.FIN = 1 "
                            + "ORDER BY nome_atendente ASC");
                    break;
                case 5:
                    stmt = con.prepareStatement("SELECT codigo_atendente, nome_atendente "
                            + "FROM tabela_atendentes "
                            + "INNER JOIN usuario_acessos ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                            + "WHERE usuario_acessos.EST = 1 "
                            + "ORDER BY nome_atendente ASC");
                    break;
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Usuario(rs.getString("codigo_atendente"),
                        rs.getString("nome_atendente")));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Retorna o nome do atendente
     *
     * @param codAtendente código do atendente
     * @return
     * @throws SQLException
     */
    public static String retornaNomeAtendente(String codAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT nome_atendente "
                    + "FROM tabela_atendentes "
                    + "WHERE codigo_atendente = ?");
            stmt.setString(1, codAtendente);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome_atendente");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna as informações básicas do usuário
     *
     * @param login login do usuário
     * @param senha senha do usuário
     * @return
     * @throws SQLException
     */
    public static Usuario retornaInfoUsr(String login, String senha) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_atendentes "
                    + "INNER JOIN usuario_acessos "
                    + "ON usuario_acessos.CODIGO_USR = tabela_atendentes.codigo_atendente "
                    + "WHERE login_atendente = ? AND senha_atendente = md5(?)");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getString("nome_atendente"),
                        rs.getString("codigo_atendente"),
                        rs.getString("login_atendente"),
                        rs.getString("tipo_atendente"),
                        rs.getByte("usuario_acessos.ORC"),
                        rs.getByte("usuario_acessos.ORC_ADM"),
                        rs.getByte("usuario_acessos.PROD"),
                        rs.getByte("usuario_acessos.PROD_ADM"),
                        rs.getByte("usuario_acessos.EXP"),
                        rs.getByte("usuario_acessos.EXP_ADM"),
                        rs.getByte("usuario_acessos.FIN"),
                        rs.getByte("usuario_acessos.FIN_ADM"),
                        rs.getByte("usuario_acessos.EST"),
                        rs.getByte("usuario_acessos.ORD"),
                        rs.getByte("ativo"),
                        rs.getTimestamp("DT_ULT_LOGIN")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Verifica se o código já existe no banco de dados
     * @param codUsr código do usuário
     * @return
     * @throws SQLException 
     */
    public static boolean verificaCodEx(String codUsr) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("SELECT codigo_atendente "
                    + "FROM tabela_atendentes "
                    + "WHERE codigo_atendente = ?");
            stmt.setString(1, codUsr);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    
    /**
     * Verifica se o código já existe no banco de dados
     * @param loginUsr login do usuário
     * @return
     * @throws SQLException 
     */
    public static boolean verificaLoginEx(String loginUsr) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("SELECT login_atendente "
                    + "FROM tabela_atendentes "
                    + "WHERE login_atendente = ?");
            stmt.setString(1, loginUsr);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    
    /**
     * Atualiza a data e hora do último login do usuário.
     * @param usuario usuário a ser atualizado.
     * @throws SQLException 
     */
    public synchronized static void atualizaUltimoLogin(Usuario usuario) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("UPDATE tabela_atendentes "
                    + "SET DT_ULT_LOGIN = ? "
                    + "WHERE codigo_atendente = ?");
            stmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
            stmt.setString(2, usuario.getCodigo());
            stmt.executeUpdate();
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    
}
