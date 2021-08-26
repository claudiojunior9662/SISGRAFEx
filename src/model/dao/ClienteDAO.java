/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import entities.sisgrafex.Contato;
import connection.ConnectionFactory;
import entities.sisgrafex.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.sisgrafex.Orcamento;
import exception.ClienteNaoEncontradoException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claudio
 */
public class ClienteDAO {

    //INFORMAÇÕES CLIENTE-------------------------------------------------------
    /**
     * Retorna a pesquisa do cliente, tela de pesquisa de clientes
     *
     * @param p1 filtro 1
     * @param p2 filtro 2
     * @param p3 filtro 3
     * @return
     * @throws SQLException
     */
    public static List<Cliente> retornaPesquisa(String p1, String p2, String p3) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Cliente> retorno = new ArrayList();

        try {
            if (p1.equals("PESSOA FÍSICA")) {
                if (p2.equals("CÓDIGO")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE cod = ? AND excluido != 1 "
                            + "ORDER BY cod DESC LIMIT 45");
                    stmt.setInt(1, Integer.valueOf(p3));
                }
                if (p2.equals("NOME")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE nome "
                            + "LIKE " + "'%" + p3 + "%' "
                            + "AND excluido != 1 "
                            + "ORDER BY cod "
                            + "DESC "
                            + "LIMIT 45");
                }
                if (p2.equals("CPF")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE cpf "
                            + "LIKE " + "'%" + p3 + "%' "
                            + "AND excluido != 1 "
                            + "ORDER BY cod "
                            + "DESC "
                            + "LIMIT 45");
                }
                if (p2.equals("ATIVIDADE")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE atividade "
                            + "LIKE " + "'%" + p3 + "%' "
                            + "AND excluido != 1 "
                            + "ORDER BY cod "
                            + "DESC "
                            + "LIMIT 45");
                }
            }
            if (p1.equals("PESSOA JURÍDICA")) {
                if (p2.equals("CÓDIGO")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_clientes_juridicos "
                            + "WHERE cod = ? "
                            + "AND excluido != 1 "
                            + "ORDER BY cod "
                            + "DESC "
                            + "LIMIT 45");
                    stmt.setInt(1, Integer.valueOf(p3));
                }
                if (p2.equals("NOME")) {
                    stmt = con.prepareStatement("SELECT * "
                            + "FROM tabela_clientes_juridicos "
                            + "WHERE nome "
                            + "LIKE " + "'%" + p3 + "%' AND excluido != 1 ORDER BY cod DESC LIMIT 45");
                }
                if (p2.equals("NOME FANTASIA")) {
                    stmt = con.prepareStatement("SELECT * FROM tabela_clientes_juridicos WHERE nome_fantasia LIKE " + "'%" + p3 + "%' AND excluido != 1 ORDER BY cod DESC LIMIT 45");
                }
                if (p2.equals("CNPJ")) {
                    stmt = con.prepareStatement("SELECT * FROM tabela_clientes_juridicos WHERE cnpj LIKE " + "'%" + p3 + "%' AND excluido != 1 ORDER BY cod DESC LIMIT 45");
                }
                if (p2.equals("ATIVIDADE")) {
                    stmt = con.prepareStatement("SELECT * FROM tabela_clientes_juridicos WHERE atividade LIKE " + "'%" + p3 + "%' AND excluido != 1 ORDER BY cod DESC LIMIT 45");
                }
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (p1.equals("PESSOA FÍSICA")) {
                    retorno.add(new Cliente(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            "",
                            rs.getString("cpf"),
                            "",
                            (byte) 1,
                            rs.getString("atividade"),
                            "",
                            rs.getFloat("credito"),
                            rs.getString("cod_atendente"),
                            rs.getString("nome_atendente")
                    ));
                }
                if (p1.equals("PESSOA JURÍDICA")) {
                    retorno.add(new Cliente(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            rs.getString("nome_fantasia"),
                            "",
                            rs.getString("cnpj"),
                            (byte) 2,
                            rs.getString("atividade"),
                            rs.getString("filial_coligada"),
                            rs.getFloat("credito"),
                            rs.getString("cod_atendente"),
                            rs.getString("nome_atendente")
                    ));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Mostra os últimos clientes cadastrados no sistema
     *
     * @param p1 tipo de cliente
     * @return
     * @throws SQLException
     */
    public static List<Cliente> mostraUltimos(String p1) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Cliente> retorno = new ArrayList();

        try {
            if (p1.equals("PESSOA FÍSICA")) {
                stmt = con.prepareStatement("SELECT * FROM tabela_clientes_fisicos WHERE excluido != 1 ORDER BY cod DESC LIMIT 45");
            } else {
                stmt = con.prepareStatement("SELECT * FROM tabela_clientes_juridicos WHERE excluido != 1 ORDER BY cod DESC LIMIT 45");
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (p1.equals("PESSOA FÍSICA")) {
                    retorno.add(new Cliente(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            "",
                            rs.getString("cpf"),
                            "",
                            (byte) 1,
                            rs.getString("atividade"),
                            "",
                            rs.getFloat("credito"),
                            rs.getString("cod_atendente"),
                            rs.getString("nome_atendente")
                    ));
                }
                if (p1.equals("PESSOA JURÍDICA")) {
                    retorno.add(new Cliente(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            rs.getString("nome_fantasia"),
                            "",
                            rs.getString("cnpj"),
                            (byte) 2,
                            rs.getString("atividade"),
                            rs.getString("filial_coligada"),
                            rs.getFloat("credito"),
                            rs.getString("cod_atendente"),
                            rs.getString("nome_atendente")
                    ));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    /**
     * Seleciona as informações de um cliente específico
     *
     * @param tipoPessoa tipo do cliente
     * @param codigoCliente código do cliente
     * @return
     * @throws SQLException
     */
    public static Cliente selecionaInformacoes(byte tipoPessoa, Integer codigoCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //INFORMAÇÕES CLIENTE-----------------------------------------------
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("SELECT * "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE cod = ? "
                        + "AND excluido != 1");
                stmt.setInt(1, codigoCliente);
            }
            if (tipoPessoa == 2) {
                stmt = con.prepareStatement("SELECT * "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE cod = ? "
                        + "AND excluido != 1");
                stmt.setInt(1, codigoCliente);
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (tipoPessoa == 1) {
                    return new Cliente(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            "",
                            rs.getString("cpf"),
                            "",
                            (byte) 1,
                            rs.getString("atividade"),
                            "",
                            rs.getFloat("credito"),
                            rs.getString("cod_atendente"),
                            rs.getString("nome_atendente")
                    );

                } else if (tipoPessoa == 2) {
                    return new Cliente(
                            rs.getInt("cod"),
                            rs.getString("nome"),
                            rs.getString("nome_fantasia"),
                            "",
                            rs.getString("cnpj"),
                            (byte) 2,
                            rs.getString("atividade"),
                            rs.getString("filial_coligada"),
                            rs.getFloat("credito"),
                            rs.getString("cod_atendente"),
                            rs.getString("nome_atendente")
                    );
                }
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static void desativaAtivaCliente(int codigoCliente, byte tipoPessoa, byte funcao) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("UPDATE tabela_clientes_fisicos SET excluido = ? WHERE cod = ?");
                stmt.setInt(1, funcao);
                stmt.setInt(2, codigoCliente);
            }
            if (tipoPessoa == 2) {
                stmt = con.prepareStatement("UPDATE tabela_clientes_juridicos SET excluido = ? WHERE cod = ?");
                stmt.setInt(1, funcao);
                stmt.setInt(2, codigoCliente);
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static Integer retornaCliente(String cpfCnpj, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer retorno = null;

        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_fisicos WHERE cpf = ?");
                stmt.setString(1, cpfCnpj);
            } else if (tipoPessoa == 2) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_juridicos WHERE cnpj = ?");
                stmt.setString(1, cpfCnpj);
            }

            if ((rs = stmt.executeQuery()).next()) {
                retorno = rs.getInt("cod");
            } else {
                retorno = 0;
                throw new ClienteNaoEncontradoException();
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Grava os clientes no BD
     *
     * @param cliente
     * @param tipoPessoa
     * @throws SQLException
     */
    public static void gravarClientes(Cliente cliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("INSERT INTO tabela_clientes_fisicos(cod, nome, cpf,"
                        + "atividade, cod_atendente, nome_atendente, observacoes, excluido)"
                        + "VALUES(?,?,?,?,?,?,?,?)");
                stmt.setInt(1, cliente.getCodigo());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getCpf());
                stmt.setString(4, cliente.getAtividade());
                stmt.setString(5, cliente.getCodigoAtendente());
                stmt.setString(6, cliente.getNomeAtendente());
                stmt.setString(7, cliente.getObservacoes());
                stmt.setInt(8, 0);
            }
            if (tipoPessoa == 2) {
                stmt = con.prepareStatement("INSERT INTO tabela_clientes_juridicos(cod, nome, nome_fantasia,"
                        + "cnpj,atividade,filial_coligada, cod_atendente, nome_atendente, observacao, excluido)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)");
                stmt.setInt(1, cliente.getCodigo());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getNomeFantasia());
                stmt.setString(4, cliente.getCnpj());
                stmt.setString(5, cliente.getAtividade());
                stmt.setString(6, cliente.getFilialColigada());
                stmt.setString(7, cliente.getCodigoAtendente());
                stmt.setString(8, cliente.getNomeAtendente());
                stmt.setString(9, cliente.getObservacoes());
                stmt.setInt(10, 0);
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static void atualizaClientes(Cliente cliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("UPDATE tabela_clientes_fisicos SET  nome = ?, cpf = ?,"
                        + "atividade = ?, cod_atendente = ?, nome_atendente = ?, observacoes = ? WHERE cod = ?");
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCpf());
                stmt.setString(3, cliente.getAtividade());
                stmt.setString(4, cliente.getCodigoAtendente());
                stmt.setString(5, cliente.getNomeAtendente());
                stmt.setString(6, cliente.getObservacoes());
                stmt.setInt(7, cliente.getCodigo());
            }
            if (tipoPessoa == 2) {
                stmt = con.prepareStatement("UPDATE tabela_clientes_juridicos SET  nome = ?, nome_fantasia = ?,"
                        + "cnpj = ?,atividade = ?,filial_coligada = ?, cod_atendente = ?, nome_atendente = ?, observacao = ? WHERE cod = ?");
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getNomeFantasia());
                stmt.setString(3, cliente.getCnpj());
                stmt.setString(4, cliente.getAtividade());
                stmt.setString(5, cliente.getFilialColigada());
                stmt.setString(6, cliente.getCodigoAtendente());
                stmt.setString(7, cliente.getNomeAtendente());
                stmt.setString(8, cliente.getObservacoes());
                stmt.setInt(9, cliente.getCodigo());
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static void associacaoClientes(List codigosEnderecos, List codigosContatos, int codCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        int tipo = 0;

        if (tipoPessoa == 1) {
            tipo = 1;
        }
        if (tipoPessoa == 2) {
            tipo = 2;
        }

        try {
            for (int i = 0; i < codigosEnderecos.size(); i++) {
                stmt = con.prepareStatement("INSERT INTO tabela_associacao_enderecos(cod_endereco,"
                        + "cod_cliente, tipo_cliente) VALUES(?,?,?)");
                stmt.setInt(1, Integer.valueOf(codigosEnderecos.get(i).toString()));
                stmt.setInt(2, codCliente);
                stmt.setInt(3, tipo);
                stmt.executeUpdate();
            }
            for (int i = 0; i < codigosContatos.size(); i++) {
                stmt2 = con.prepareStatement("INSERT INTO tabela_associacao_contatos(cod_contato,"
                        + "cod_cliente, tipo_cliente) VALUES(?,?,?)");
                stmt2.setInt(1, Integer.valueOf(codigosContatos.get(i).toString()));
                stmt2.setInt(2, codCliente);
                stmt2.setInt(3, tipo);
                stmt2.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
            ConnectionFactory.closeConnection(con, stmt2);
        }
    }
    
    /**
     * Realiza a associação dos endereços cadastrados ao cliente
     * @param codigosEnderecos
     * @param codCliente
     * @param tipoPessoa
     * @throws SQLException 
     */
    public static void associacaoClientesEnderecos(List codigosEnderecos, int codCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int tipo = 0;

        if (tipoPessoa == 1) {
            tipo = 1;
        }
        if (tipoPessoa == 2) {
            tipo = 2;
        }

        try {
            for (int i = 0; i < codigosEnderecos.size(); i++) {
                stmt = con.prepareStatement("INSERT INTO tabela_associacao_enderecos(cod_endereco,"
                        + "cod_cliente, tipo_cliente) VALUES(?,?,?)");
                stmt.setInt(1, Integer.valueOf(codigosEnderecos.get(i).toString()));
                stmt.setInt(2, codCliente);
                stmt.setInt(3, tipo);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Realiza a associação dos contatos cadastrados ao cliente
     * @param codigosContatos
     * @param codCliente
     * @param tipoPessoa
     * @throws SQLException 
     */
    public static void associacaoClientesContatos(List codigosContatos, int codCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int tipo = 0;

        if (tipoPessoa == 1) {
            tipo = 1;
        }
        if (tipoPessoa == 2) {
            tipo = 2;
        }

        try {
            for (int i = 0; i < codigosContatos.size(); i++) {
                stmt = con.prepareStatement("INSERT INTO tabela_associacao_contatos(cod_contato,"
                        + "cod_cliente, tipo_cliente) VALUES(?,?,?)");
                stmt.setInt(1, Integer.valueOf(codigosContatos.get(i).toString()));
                stmt.setInt(2, codCliente);
                stmt.setInt(3, tipo);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static void excluirAssociacaoClientes(int codigoCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tabela_associacao_contatos WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            stmt.executeUpdate();

            stmt = con.prepareStatement("DELETE FROM tabela_associacao_enderecos WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            stmt.executeUpdate();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Retorna o último código do cadastro de cliente
     *
     * @param tipoPessoa
     * @return
     * @throws SQLException
     */
    public static int retornaUltimoRegistroClientes(byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int retorno = 0;

        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_fisicos "
                        + "ORDER BY cod "
                        + "DESC "
                        + "LIMIT 1");
            }
            if (tipoPessoa == 2) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "ORDER BY cod "
                        + "DESC "
                        + "LIMIT 1");
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getInt("cod");
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static Boolean verificaCpfCnpj(byte tipoPessoa, String valor, Boolean excluido) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean retorno = false;

        try {
            if (tipoPessoa == 1) {
                if (!excluido) {
                    stmt = con.prepareStatement("SELECT cpf FROM tabela_clientes_fisicos WHERE excluido = 0");
                } else if (excluido) {
                    stmt = con.prepareStatement("SELECT cpf FROM tabela_clientes_fisicos WHERE excluido = 1");
                }
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (valor.equals(rs.getString("cpf"))) {
                        retorno = true;
                    }
                }
            } else {
                if (!excluido) {
                    stmt = con.prepareStatement("SELECT cnpj FROM tabela_clientes_juridicos WHERE excluido = 0");
                } else if (excluido) {
                    stmt = con.prepareStatement("SELECT cnpj FROM tabela_clientes_juridicos WHERE excluido = 1");
                }
                rs = stmt.executeQuery();
                while (rs.next()) {
                    if (valor.equals(rs.getString("cnpj"))) {
                        retorno = true;
                    }
                }
            }
        } catch (Exception ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static Boolean verificaAtrelamentoCliente(Integer codigoCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean retorno = false;

        try {
            //VERIFICA ORCAMENTOS
            stmt = con.prepareStatement("SELECT cod_cliente FROM tabela_orcamentos WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            if ((rs = stmt.executeQuery()).next()) {
                retorno = true;
            }
            //VERIFICA ORDENS DE PRODUCAO
            stmt = con.prepareStatement("SELECT cod_cliente FROM tabela_ordens_producao WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            if ((rs = stmt.executeQuery()).next()) {
                retorno = true;
            }
            //VERIFICA NOTAS
            stmt = con.prepareStatement("SELECT cod_cliente FROM tabela_notas WHERE cod_cliente = ? AND tipo_pessoa = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            if ((rs = stmt.executeQuery()).next()) {
                retorno = true;
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static Cliente retornaTipoCodClienteRelatorio(String nome) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT cod, nome "
                    + "FROM tabela_clientes_fisicos "
                    + "WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("cod"),
                        (byte) 1,
                        rs.getString("nome"));
            } else {
                stmt = con.prepareStatement("SELECT cod, nome "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE nome = ?");
                stmt.setString(1, nome);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Cliente(rs.getInt("cod"),
                            (byte) 2,
                            rs.getString("nome"));
                }
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static List<String> retornaPesquisaClientesAproximada(String texto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<String> retorno = new ArrayList();
        retorno.clear();

        try {
            stmt = con.prepareStatement("SELECT nome FROM tabela_clientes_fisicos WHERE nome LIKE '%"
                    + texto + "%' ORDER BY nome DESC LIMIT 5");
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(rs.getString("nome"));
            }

            stmt = con.prepareStatement("SELECT cod,nome FROM tabela_clientes_juridicos WHERE nome LIKE '%"
                    + texto + "%' ORDER BY nome DESC LIMIT 5");
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Retorna o nome do cliente por extenso
     *
     * @param codCliente código do cliente
     * @param tipoCliente 1 - Pessoa física, 2 - Pessoa jurídica
     * @return
     * @throws SQLException
     */
    public static String retornaNomeCliente(int codCliente, byte tipoCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            if (tipoCliente == 1) {
                stmt = con.prepareStatement("SELECT nome "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE cod = ?");
                stmt.setInt(1, codCliente);
            } else {
                stmt = con.prepareStatement("SELECT nome "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE cod = ?");
                stmt.setInt(1, codCliente);
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                retorno = rs.getString("nome");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static List<Contato> retornaDescricaoContatos(List codigos) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Contato> retorno = new ArrayList();

        try {
            for (int i = 0; i < codigos.size(); i++) {
                stmt = con.prepareStatement("SELECT * FROM tabela_contatos WHERE cod = ?");
                stmt.setInt(1, Integer.valueOf(codigos.get(i).toString()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    retorno.add(new Contato(rs.getInt("cod"),
                            rs.getString("nome_contato"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("ramal"),
                            rs.getString("telefone2"),
                            rs.getString("ramal2"),
                            rs.getString("departamento")));
                }
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static void desativaAtivaContatos(int codigoCliente, int codigoContato, byte tipoPessoa, byte funcao, boolean associacao) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            if (!associacao) {
                stmt = con.prepareStatement("UPDATE tabela_contatos SET excluido = ? WHERE cod = ?");
                stmt.setInt(1, funcao);
                stmt.setInt(2, codigoContato);
                stmt.executeUpdate();
            }
            if (associacao) {
                for (int codigoContatoAux : EnderecoDAO.retornaEnderecosAssociados(codigoCliente, tipoPessoa)) {
                    stmt = con.prepareStatement("UPDATE tabela_contatos SET excluido = ? WHERE cod = ?");
                    stmt.setInt(1, funcao);
                    stmt.setInt(2, codigoContatoAux);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static List<Integer> retornaCodigosContatos(int codigoCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod_contato FROM tabela_associacao_contatos WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            while ((rs = stmt.executeQuery()).next()) {
                retorno.add(rs.getInt("cod_contato"));
            }
            stmt.executeUpdate();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static List<Contato> retornaPesquisaContatos(String p1, String p2, boolean todos) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Contato> retorno = new ArrayList();

        try {
            if (p1.equals("CÓDIGO")) {
                stmt = con.prepareStatement("SELECT * FROM tabela_contatos"
                        + " WHERE cod = ? AND excluido != 1");
                stmt.setInt(1, Integer.valueOf(p2));
            }
            if (p1.equals("NOME CONTATO")) {
                stmt = con.prepareStatement("SELECT * FROM tabela_contatos"
                        + " WHERE nome_contato"
                        + " LIKE " + "'%" + p2 + "%' AND excluido != 1");
            }
            if (p1.equals("EMAIL")) {
                stmt = con.prepareStatement("SELECT * FROM tabela_contatos"
                        + " WHERE email"
                        + " LIKE " + "'%" + p2 + "%' AND excluido != 1");
            }
            if (p1.equals("DEPARTAMENTO")) {
                stmt = con.prepareStatement("SELECT * FROM tabela_contatos"
                        + " WHERE departamento"
                        + " LIKE " + "'%" + p2 + "%' AND excluido != 1");
            }
            if (todos == true) {
                stmt = con.prepareStatement("SELECT * FROM tabela_contatos WHERE excluido != 1");
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Contato(rs.getInt("cod"),
                        rs.getString("nome_contato"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("ramal"),
                        rs.getString("telefone2"),
                        rs.getString("ramal2"),
                        rs.getString("departamento")));
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public List<Integer> retornaCodigoContato(Integer codigoCliente, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod_contato FROM tabela_associacao_contatos WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(rs.getInt("cod_contato"));
            }
        } catch (SQLException ex) {
            retorno = null;
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public Boolean verificaAtrelamentoContatos(Integer codigoContato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Boolean retorno = false;

        try {
            //VERIFICA ORCAMENTOS
            stmt = con.prepareStatement("SELECT cod_contato FROM tabela_orcamentos WHERE cod_contato = ?");
            stmt.setInt(1, codigoContato);
            if ((rs = stmt.executeQuery()).next()) {
                retorno = true;
            }
            //VERIFICA ORDENS DE PRODUCAO
            stmt = con.prepareStatement("SELECT cod_contato FROM tabela_ordens_producao WHERE cod_contato = ?");
            stmt.setInt(1, codigoContato);
            if ((rs = stmt.executeQuery()).next()) {
                retorno = true;
            }
            //VERIFICA NOTAS
            stmt = con.prepareStatement("SELECT cod_contato FROM tabela_notas WHERE cod_contato = ?");
            stmt.setInt(1, codigoContato);
            if ((rs = stmt.executeQuery()).next()) {
                retorno = true;
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    //NOTA FISCAL - INFORMAÇÕES CLIENTE-----------------------------------------
    /**
     * Usada para retornar informações do cliente para a pesquisa de notas
     *
     * @param tipoPessoa tipo de cliente
     * @param codCliente código do cliente
     * @return Cliente(cod, nome, doc - cpf ou cnpj) cliente da nota
     * @see selecionaInformacoesNota#selInfoNota
     */
    public static Cliente selInfoNota(byte tipoPessoa, int codCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            //INFORMAÇÕES CLIENTE-----------------------------------------------
            switch (tipoPessoa) {
                case 1:
                    stmt = con.prepareStatement("SELECT cod, nome, cpf "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE cod = ?");
                    stmt.setInt(1, codCliente);
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT cod, nome, cnpj "
                            + "FROM tabela_clientes_juridicos "
                            + "WHERE cod = ?");
                    stmt.setInt(1, codCliente);
                    break;
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                switch (tipoPessoa) {
                    case 1:
                        return new Cliente(
                                rs.getInt("cod"),
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                (byte) 1
                        );
                    case 2:
                        return new Cliente(
                                rs.getInt("cod"),
                                rs.getString("nome"),
                                rs.getString("cnpj"),
                                (byte) 2
                        );
                }
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    //CARREGA ORÇAMENTOS CLIENTE------------------------------------------------
    public List<Orcamento> carregaOrcamentos(int codigoCliente, int tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Orcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod, data_emissao, data_validade, valor_total, status, cod_emissor FROM tabela_orcamentos WHERE cod_cliente = ? AND tipo_cliente = ?");
            stmt.setInt(1, codigoCliente);
            stmt.setInt(2, tipoPessoa);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Orcamento orcamentoBEAN = new Orcamento();
                orcamentoBEAN.setCod(rs.getInt("cod"));
                orcamentoBEAN.setDataEmissao(rs.getDate("data_emissao"));
                orcamentoBEAN.setDataValidade(rs.getDate("data_validade"));
                orcamentoBEAN.setValorTotal(rs.getFloat("valor_total"));
                orcamentoBEAN.setStatus(rs.getByte("status"));
                orcamentoBEAN.setCodEmissor(rs.getString("cod_emissor"));
                retorno.add(orcamentoBEAN);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public List<Orcamento> carregaOrcamentosPersonalizado(int codigoCliente, int tipoPessoa, String p1, String p2) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Orcamento> retorno = new ArrayList();

        try {
            if (p1.equals("MÊS/ANO") || p1.equals("ANO")) {
                stmt = con.prepareStatement("SELECT cod, data_emissao, data_validade, valor_total, status, cod_emissor "
                        + "FROM tabela_orcamentos WHERE data_emissao LIKE '%" + p2 + "%' AND "
                        + "cod_cliente = ? AND tipo_cliente = ?");
                stmt.setInt(1, codigoCliente);
                stmt.setInt(2, tipoPessoa);
            } else if (p1.equals("EMISSOR")) {
                stmt = con.prepareStatement("SELECT cod, data_emissao, data_validade, valor_total, status, cod_emissor "
                        + "FROM tabela_orcamentos WHERE cod_emissor LIKE '%" + p2 + "%' AND "
                        + "cod_cliente = ? AND tipo_cliente = ?");
                stmt.setInt(1, codigoCliente);
                stmt.setInt(2, tipoPessoa);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                Orcamento aux = new Orcamento();
                aux.setCod(rs.getInt("cod"));
                aux.setDataEmissao(rs.getDate("data_emissao"));
                aux.setDataValidade(rs.getDate("data_validade"));
                aux.setValorTotal(rs.getFloat("valor_total"));
                aux.setStatus(rs.getByte("status"));
                aux.setCodEmissor(rs.getString("cod_emissor"));
                retorno.add(aux);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Usada para retornar o crédito do cliente
     *
     * @param codCliente Código do cliente
     * @param tipoPessoa Tipo de pessoa
     * @return double crédito do cliente
     * @see retornaCredCliente
     */
    public static float retornaCredCliente(int codCliente, int tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            switch (tipoPessoa) {
                case 1:
                    stmt = con.prepareStatement("SELECT credito FROM tabela_clientes_fisicos WHERE cod = ?");
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT credito FROM tabela_clientes_juridicos WHERE cod = ?");
                    break;
            }
            stmt.setInt(1, codCliente);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("credito");
            }
            return 0f;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Usada para o retorna de Endereço a partir do código
     *
     * @param codContato código do contato
     * @return Contato(nome_contato, email, telefone, telefone2) informações do
     * contato do cliente
     * @see selInfoContatosNota#selInfoContato
     */
    public synchronized static Contato selInfoContato(int codContato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT "
                    + "nome_contato, "
                    + "telefone, "
                    + "telefone2, "
                    + "email "
                    + "FROM tabela_contatos "
                    + "WHERE cod = ?");
            stmt.setInt(1, codContato);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contato(rs.getString("nome_contato"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("telefone2")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Usada para o retorno de lista de código de cliente para a pesquisa de
     * notas
     *
     * @param condicao condição da pesquisa
     * @param pesquisa tipo de pesquisa
     * @return List<Integer> lista de códigos de clientes
     * @see retornaCodCliente
     */
    public static List<Integer> retornaCodCliente(String condicao, String pesquisa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List retorno = new ArrayList();

        try {
            if (condicao.equals("PESSOA FÍSICA - NOME")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE nome "
                        + "LIKE " + "'%" + pesquisa + "%' ORDER BY cod ASC");
            } else if (condicao.equals("PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)")) {
                stmt = con.prepareStatement("SELECT cod, nome "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE cpf = ? O"
                        + "RDER BY cod "
                        + "ASC");
                stmt.setString(1, pesquisa);
            } else if (condicao.equals("PESSOA JURÍDICA - NOME")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE nome "
                        + "LIKE " + "'%" + pesquisa + "%' "
                        + "ORDER BY cod "
                        + "ASC");
            } else if (condicao.equals("PESSOA JURÍDICA - NOME FANTASIA")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE nome_fantasia "
                        + "LIKE " + "'%" + pesquisa + "%' "
                        + "ORDER BY cod "
                        + "ASC");
            } else if (condicao.equals("PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE cnpj = ? "
                        + "ORDER BY cod "
                        + "ASC");
                stmt.setString(1, pesquisa);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(rs.getInt("cod"));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna pesquisa com condição cliente (List<Cliente> (cod, tipoCliente))
     *
     * @param condicao
     * @param pesquisa
     * @return
     * @throws SQLException
     */
    public static List<Cliente> retornaCliente(String condicao, String pesquisa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> retorno = new ArrayList();
        byte tipoCliente = 0;

        try {
            if (condicao.equals("PESSOA FÍSICA - NOME")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE nome "
                        + "LIKE " + "'%" + pesquisa + "%' ORDER BY cod ASC");
                tipoCliente = 1;
            } else if (condicao.equals("PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)")) {
                stmt = con.prepareStatement("SELECT cod, nome "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE cpf = ? "
                        + "ORDER BY cod "
                        + "ASC");
                stmt.setString(1, pesquisa);
                tipoCliente = 1;
            } else if (condicao.equals("PESSOA JURÍDICA - NOME")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE nome "
                        + "LIKE " + "'%" + pesquisa + "%' "
                        + "ORDER BY cod "
                        + "ASC");
                tipoCliente = 2;
            } else if (condicao.equals("PESSOA JURÍDICA - NOME FANTASIA")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE nome_fantasia "
                        + "LIKE " + "'%" + pesquisa + "%' "
                        + "ORDER BY cod "
                        + "ASC");
                tipoCliente = 2;
            } else if (condicao.equals("PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)")) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE cnpj = ? "
                        + "ORDER BY cod "
                        + "ASC");
                stmt.setString(1, pesquisa);
                tipoCliente = 1;
            } else if (condicao.contains("FÍSICA") & condicao.contains("CÓDIGO")) {
                stmt = con.prepareStatement("SELECT cod, nome "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE cod = ? "
                        + "ORDER BY cod "
                        + "ASC");
                stmt.setInt(1, Integer.valueOf(pesquisa));
                tipoCliente = 1;
            } else if (condicao.contains("JURÍDICA") & condicao.contains("CÓDIGO")) {
                stmt = con.prepareStatement("SELECT cod, nome "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE cod = ? "
                        + "ORDER BY cod "
                        + "ASC");
                stmt.setInt(1, Integer.valueOf(pesquisa));
                tipoCliente = 2;
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Cliente(
                        rs.getInt("cod"),
                        tipoCliente
                ));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Corrige o crédito do cliente
     *
     * @param codCliente código do cliente
     * @param tipoPessoa 1 - PF, 2 - PJ
     * @param valor valor para ser corrigido
     * @param operacao 1 - CRED, 2 - DEB
     * @throws SQLException
     */
    public static void corrigeCredito(int codCliente,
            byte tipoPessoa,
            double valor,
            byte operacao) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double credito = 0d;

        try {

            credito = retornaCredCliente(codCliente, tipoPessoa);

            switch (operacao) {
                case 1:
                    credito = credito + valor;
                    break;
                case 2:
                    credito = credito - valor;
                    break;
            }

            switch (tipoPessoa) {
                case 1:
                    stmt = con.prepareStatement("UPDATE tabela_clientes_fisicos "
                            + "SET credito = ? "
                            + "WHERE cod = ?");
                    stmt.setDouble(1, credito);
                    stmt.setInt(2, codCliente);
                    break;
                case 2:
                    stmt = con.prepareStatement("UPDATE tabela_clientes_juridicos "
                            + "SET credito = ? "
                            + "WHERE cod = ?");
                    stmt.setDouble(1, credito);
                    stmt.setInt(2, codCliente);
                    break;
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Seleciona as informações do cliente para a ordem de produção
     *
     * @param tipoCliente tipo do cliente
     * @param codCliente código do cliente
     * @return
     * @throws SQLException
     */
    public static Cliente selInfoOp(int tipoCliente, int codCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            switch (tipoCliente) {
                case 1:
                    stmt = con.prepareStatement("SELECT nome, cpf, observacoes "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE cod = ?");
                    stmt.setInt(1, codCliente);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        return new Cliente(
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                rs.getString("observacoes"),
                                0f,
                                (byte) 1
                        );
                    }
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT nome, cnpj, nome_fantasia, observacao "
                            + "FROM tabela_clientes_juridicos "
                            + "WHERE cod = ?");
                    stmt.setInt(1, codCliente);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        return new Cliente(
                                rs.getString("nome"),
                                "",
                                rs.getString("cnpj"),
                                "",
                                rs.getString("nome_fantasia"),
                                "",
                                "",
                                "",
                                rs.getString("observacao"),
                                0f,
                                (byte) 2
                        );
                    }
                    break;
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna conteúdo do relatório de contabilidade (crédito do cliente)
     *
     * @param tipoCondicaoCliente 1 - PF, 2 - PJ
     * @param tipoCondicaoOrdenar 1 - CODIGO ASC, 2 - CODIGO DESC, 2 - NOME ASC,
     * NOME DESC
     * @return
     */
    public static List<Cliente> retornaContRelContabilidade(
            byte tipoCondicaoCliente,
            byte tipoCondicaoOrdenar) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder sb = null;
        List<Cliente> retorno = new ArrayList();

        try {
            switch (tipoCondicaoCliente) {
                case 1:
                    sb = new StringBuilder().append("SELECT cod, nome, cpf, credito "
                            + "FROM tabela_clientes_fisicos ");
                    switch (tipoCondicaoOrdenar) {
                        case 1:
                            sb.append("ORDER BY cod ASC");
                            break;
                        case 2:
                            sb.append("ORDER BY cod DESC");
                            break;
                        case 3:
                            sb.append("ORDER BY nome ASC");
                            break;
                        case 4:
                            sb.append("ORDER BY nome DESC");
                            break;
                    }
                    break;
                case 2:
                    sb = new StringBuilder().append("SELECT cod, nome, nome_fantasia, cnpj, credito "
                            + "FROM tabela_clientes_juridicos ");
                    switch (tipoCondicaoOrdenar) {
                        case 1:
                            sb.append("ORDER BY cod ASC");
                            break;
                        case 2:
                            sb.append("ORDER BY cod DESC");
                            break;
                        case 3:
                            sb.append("ORDER BY nome ASC");
                            break;
                        case 4:
                            sb.append("ORDER BY nome DESC");
                            break;
                    }
                    break;
            }
            stmt = con.prepareStatement(sb.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                switch (tipoCondicaoCliente) {
                    case 1:
                        retorno.add(new Cliente(
                                rs.getInt("cod"),
                                rs.getString("nome"),
                                null,
                                rs.getString("cpf"),
                                null,
                                (byte) 1,
                                null,
                                null,
                                rs.getFloat("credito"),
                                null,
                                null
                        ));
                        break;
                    case 2:
                        retorno.add(new Cliente(
                                rs.getInt("cod"),
                                rs.getString("nome"),
                                rs.getString("nome_fantasia"),
                                null,
                                rs.getString("cnpj"),
                                (byte) 2,
                                null,
                                null,
                                rs.getFloat("credito"),
                                null,
                                null
                        ));
                        break;
                }
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Faz a autenticação da senha do cliente
     *
     * @param cod código do cliente
     * @param senha
     * @return
     */
    public static boolean autenticaCliente(int cod, String senha) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT senha "
                    + "FROM tabela_clientes_juridicos "
                    + "WHERE senha = md5(?)");
            stmt.setString(1, senha);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return false;
    }

    /**
     * Retorna clientes com créditos negativos para apreciação do financeiro
     *
     * @return
     * @throws SQLException
     */
    public static List<Cliente> retornaCredNeg() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> creditos = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod, nome, credito "
                    + "FROM tabela_clientes_fisicos "
                    + "WHERE credito < 0");
            rs = stmt.executeQuery();
            while (rs.next()) {
                creditos.add(new Cliente(
                        rs.getInt("cod"),
                        rs.getString("nome"),
                        null,
                        null,
                        null,
                        (byte) 1,
                        null,
                        null,
                        rs.getFloat("credito"),
                        null,
                        null
                ));
            }
            stmt = con.prepareStatement("SELECT cod, nome, nome_fantasia, credito "
                    + "FROM tabela_clientes_juridicos "
                    + "WHERE credito < 0");
            rs = stmt.executeQuery();
            while (rs.next()) {
                creditos.add(new Cliente(
                        rs.getInt("cod"),
                        rs.getString("nome"),
                        rs.getString("nome_fantasia"),
                        null,
                        null,
                        (byte) 1,
                        null,
                        null,
                        rs.getFloat("credito"),
                        null,
                        null
                ));
            }
            return creditos;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Atualiza os dados de acesso (contator e último acesso) dos clientes
     *
     * @param cod - código do cliente
     * @throws SQLException
     */
    public static void atualizaClientes(int cod) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int acessos = 0;
        Timestamp data = new Timestamp(new Date().getTime());

        try {
            stmt = con.prepareStatement("SELECT "
                    + "tabela_clientes_juridicos.QTD_ACESSOS "
                    + "FROM tabela_clientes_juridicos "
                    + "WHERE tabela_clientes_juridicos.cod = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            if (rs.next()) {
                acessos = rs.getInt("tabela_clientes_juridicos.QTD_ACESSOS");
            }

            acessos++;

            stmt = con.prepareStatement("UPDATE tabela_clientes_juridicos "
                    + "SET tabela_clientes_juridicos.ULTIMO_ACESSO = ?, "
                    + "tabela_clientes_juridicos.QTD_ACESSOS = ? "
                    + "WHERE tabela_clientes_juridicos.cod = ?");
            stmt.setTimestamp(1, data);
            stmt.setInt(2, acessos);
            stmt.setInt(3, cod);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    //E-COMMERCE----------------------------------------------------------------
    /**
     * Verifica o cadastro do cliente no BD a partir do nº do documento
     *
     * @param cliente
     * @return Retorna se o cliente está cadastrado no BD ou não
     * @throws java.sql.SQLException
     */
    public static Boolean verificaCadastroCliente(Cliente cliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            switch (cliente.getTipoPessoa()) {
                case 1:
                    stmt = con.prepareStatement("SELECT tabela_clientes_fisicos.nome "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE tabela_clientes_fisicos.cpf = ?");
                    stmt.setString(1, cliente.getCpf());
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT tabela_clientes_juridicos.nome "
                            + "FROM tabela_clientes_juridicos "
                            + "WHERE tabela_clientes_juridicos.cnpj = ?");
                    stmt.setString(1, cliente.getCnpj());
                    break;
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Carrega nome e nº documento do cliente
     *
     * @param codCliente código do cliente
     * @param tipoCliente tipo pessoa
     * @return
     * @throws SQLException
     */
    public static List<Cliente> carregaClientes(int codCliente, int tipoCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Cliente> retorno = new ArrayList();

        try {
            if (tipoCliente == 1) {
                stmt = con.prepareStatement("SELECT tabela_clientes_fisicos.nome, tabela_clientes_fisicos.cpf "
                        + "FROM tabela_clientes_fisicos "
                        + "WHERE tabela_clientes_fisicos.cod = ?");
                stmt.setInt(1, codCliente);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    retorno.add(new Cliente(
                            codCliente,
                            rs.getString("tabela_clientes_fisicos.nome"),
                            rs.getString("tabela_clientes_fisicos.cpf"),
                            (byte) 1
                    ));
                }
            } else if (tipoCliente == 2) {
                stmt = con.prepareStatement("SELECT tabela_clientes_juridicos.nome, tabela_clientes_juridicos.cnpj "
                        + "FROM tabela_clientes_juridicos "
                        + "WHERE tabela_clientes_juridicos.cod = ?");
                stmt.setInt(1, codCliente);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    retorno.add(new Cliente(
                            codCliente,
                            rs.getString("tabela_clientes_juridicos.nome"),
                            rs.getString("tabela_clientes_juridicos.cnpj"),
                            (byte) 2
                    ));
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Retorna o código do cliente a partir do documento informado
     *
     * @param documento documento do cliente (já formatado)
     * @param tipoPessoa
     * @return
     */
    public static synchronized int retornaCodPorDoc(String documento, byte tipoPessoa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            switch (tipoPessoa) {
                case 1:
                    stmt = con.prepareStatement("SELECT tabela_clientes_fisicos.cod "
                            + "FROM tabela_clientes_fisicos "
                            + "WHERE tabela_clientes_fisicos.cpf = ? "
                            + "ORDER BY tabela_clientes_fisicos.cod "
                            + "ASC "
                            + "LIMIT 1");
                    stmt.setString(1, documento);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("tabela_clientes_fisicos.cod");
                    }
                    return 0;
                case 2:
                    stmt = con.prepareStatement("SELECT tabela_clientes_juridicos.cod "
                            + "FROM tabela_clientes_juridicos "
                            + "WHERE tabela_clientes_juridicos.cnpj = ? "
                            + "ORDER BY tabela_clientes_juridicos.cod "
                            + "ASC "
                            + "LIMIT 1");
                    stmt.setString(1, documento);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("tabela_clientes_juridicos.cod");
                    }
                    return 0;
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
