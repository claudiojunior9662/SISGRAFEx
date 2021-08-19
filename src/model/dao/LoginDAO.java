/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import exception.AtualizaSenhaException;
import exception.UsuarioNaoAtivoException;
import exception.UsuarioSenhaIncorretosException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import ui.controle.Controle;

/**
 *
 * @author Seçao SPD3
 */
public class LoginDAO extends RuntimeException {

    /**
     * Verifica o login do usuário e entra no sistema
     * @param login 
     * @param senha
     * @return
     * @throws SQLException 
     */
    public boolean verificaNome(String login, String senha) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            switch (login) {
                default:
                    stmt = con.prepareStatement("SELECT login_atendente, senha_atendente, ativo "
                            + "FROM tabela_atendentes "
                            + "WHERE login_atendente = ? AND senha_atendente = md5(?)");
                    stmt.setString(1, login);
                    stmt.setString(2, senha);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        if (rs.getInt("ativo") == 0) {
                            throw new UsuarioNaoAtivoException();
                        } else {
                            return true;
                        }
                    } else {
                        throw new UsuarioSenhaIncorretosException();
                    }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static int realizaConexao() {
        Connection con = null;
        int retorno = 0;
        try {
            con = ConnectionFactory.getConnection();
            retorno = 1;
        } catch (Exception ex) {
            retorno = 0;
        } finally {
            ConnectionFactory.closeConnection(con);
        }
        return retorno;
    }

    public Boolean verificaExpiracaoSenha(String codAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Date data2 = new Date();
        Date data1 = null;
        Boolean retorno = false;

        try {
            stmt = con.prepareStatement("SELECT mudanca_senha "
                    + "FROM tabela_atendentes "
                    + "WHERE codigo_atendente = ?");
            stmt.setString(1, codAtendente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                data1 = rs.getDate("mudanca_senha");
            }

            DateFormat df = Controle.dataPadrao;
            df.setLenient(false);

            long dt = (data2.getTime() - data1.getTime()) + 3600000;
            dt = dt / 86400000L;

            if (dt >= 15) {
                retorno = true;
            } else {
                retorno = false;
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static Boolean verificaSenhaAntiga(String senha, String codAtendente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean retorno = false;

        try {
            stmt = con.prepareStatement("SELECT senha_atendente"
                    + " FROM tabela_atendentes"
                    + " WHERE senha_atendente = md5(?) AND codigo_atendente = ?");
            stmt.setString(1, senha);
            stmt.setString(2, codAtendente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static void atualizaSenha(String senha, String codAtendente) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_atendentes "
                    + "SET senha_atendente = md5(?), mudanca_senha = ? "
                    + "WHERE codigo_atendente = ?");
            stmt.setString(1, senha);
            stmt.setDate(2, new java.sql.Date(new Date().getTime()));
            stmt.setString(3, codAtendente);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new AtualizaSenhaException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
