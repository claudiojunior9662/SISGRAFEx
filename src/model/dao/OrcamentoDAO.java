/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import entities.sisgrafex.AlteraData;
import entities.sisgrafex.Cliente;
import entities.sisgrafex.Contato;
import entities.sisgrafex.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.sisgrafex.Orcamento;
import entities.sisgrafex.CalculosOp;
import entities.sisgrafex.Servicos;
import entities.sisgrafex.StsOrcamento;
import exception.OrcamentoInexistenteException;
import java.text.ParseException;
import entities.sisgrafex.ProdOrcamento;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.controle.Controle;

/**
 *
 * @author spd3
 */
public class OrcamentoDAO {

    public static int retornaUltimoRegistro() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int retorno = 0;

        try {
            stmt = con.prepareStatement("SELECT cod "
                    + "FROM tabela_orcamentos "
                    + "ORDER BY cod "
                    + "DESC "
                    + "LIMIT 1");
            rs = stmt.executeQuery();
            if (rs.next()) {
                retorno = rs.getInt("cod");
            } else {
                throw new OrcamentoInexistenteException();
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static void createOrcamentos(Orcamento orcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tabela_orcamentos(cod, "
                    + "COD_LI, "
                    + "cod_cliente,  "
                    + "data_validade, "
                    + "data_emissao, "
                    + "valor_unitario, "
                    + "sif, "
                    + "valor_total,"
                    + "status, "
                    + "descricao, "
                    + "tipo_cliente, "
                    + "cod_emissor, "
                    + "desconto, "
                    + "cod_contato, "
                    + "cod_endereco, "
                    + "precos_manuais, "
                    + "frete, "
                    + "ARTE) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, orcamento.getCod());
            stmt.setInt(2, orcamento.getCodigoLI());
            stmt.setInt(3, orcamento.getCodCliente());
            stmt.setDate(4, new java.sql.Date(orcamento.getDataValidade().getTime()));
            stmt.setDate(5, new java.sql.Date(orcamento.getDataEmissao().getTime()));
            stmt.setFloat(6, orcamento.getValorUnitario());
            stmt.setFloat(7, orcamento.getCif());
            stmt.setFloat(8, orcamento.getValorTotal());
            stmt.setByte(9, (byte) 1);
            stmt.setString(10, orcamento.getDescricao());
            stmt.setInt(11, orcamento.getTipoPessoa());
            stmt.setString(12, orcamento.getCodEmissor());
            stmt.setFloat(13, orcamento.getDesconto());
            stmt.setInt(14, orcamento.getCodContato());
            stmt.setInt(15, orcamento.getCodEndereco());
            stmt.setInt(16, orcamento.getPrecosManuais());
            stmt.setFloat(17, Float.valueOf(orcamento.getFrete().toString()));
            stmt.setFloat(18, Float.valueOf(orcamento.getArte().toString()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static void createServicosOrcamento(Servicos servicos) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tabela_componentes_orcamentos(cod_orcamento, cod_componente, cod_componente_1) VALUES(?,?,?)");
            stmt.setInt(1, servicos.getCod_orcamento());
            stmt.setInt(2, servicos.getCod_componente());
            stmt.setInt(3, servicos.getCod());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Mostra os últimos 45 orçamentos
     *
     * @return
     * @throws SQLException
     */
    public static List<Orcamento> mostraTodos() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Orcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_orcamentos "
                    + "ORDER BY cod "
                    + "DESC "
                    + "LIMIT 45");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Orcamento orcamento = new Orcamento();
                orcamento.setCod(rs.getInt("cod"));
                orcamento.setCodigoLI(rs.getInt("COD_LI"));
                orcamento.setCodCliente(rs.getInt("cod_cliente"));
                orcamento.setDataEmissao(rs.getDate("data_emissao"));
                orcamento.setDataValidade(rs.getDate("data_validade"));
                orcamento.setValorTotal(rs.getFloat("valor_total"));
                orcamento.setStatus(rs.getByte("status"));
                orcamento.setTipoPessoa(rs.getInt("tipo_cliente"));
                orcamento.setCodEmissor(rs.getString("cod_emissor"));
                retorno.add(orcamento);
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    public static String carregaNomeCliente(int tipoPessoa, int codigoCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            if (tipoPessoa == 1) {
                stmt = con.prepareStatement("SELECT nome FROM tabela_clientes_fisicos WHERE cod = ?");
                stmt.setInt(1, codigoCliente);
            } else if (tipoPessoa == 2) {
                stmt = con.prepareStatement("SELECT nome FROM tabela_clientes_juridicos WHERE cod = ?");
                stmt.setInt(1, codigoCliente);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("nome");
            }
        } catch (SQLException ex) {
            retorno = null;
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * @param codOrc código do orçamento
     * @return orçamento a ser selecionado
     * @throws java.sql.SQLException
     * @see carregaEdicaoOrcamento
     */
    public static Orcamento carregaEdicaoOrcamento(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod, cod_cliente, cod_emissor, data_validade,"
                    + "data_emissao, sif, status, descricao, tipo_cliente, desconto,"
                    + "valor_total, cod_contato, cod_endereco, precos_manuais,"
                    + "frete, ARTE "
                    + "FROM tabela_orcamentos "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Orcamento(
                        rs.getInt("cod"),
                        rs.getInt("cod_cliente"),
                        rs.getString("cod_emissor"),
                        rs.getDate("data_validade"),
                        rs.getDate("data_emissao"),
                        rs.getFloat("sif"),
                        rs.getInt("status"),
                        rs.getString("descricao"),
                        rs.getInt("tipo_cliente"),
                        rs.getFloat("desconto"),
                        rs.getFloat("valor_total"),
                        rs.getInt("cod_contato"),
                        rs.getInt("cod_endereco"),
                        rs.getInt("precos_manuais"),
                        rs.getDouble("frete"),
                        rs.getDouble("ARTE")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    //PARADO
    public static String carregaNomeVendedor(String cod) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_atendentes WHERE codigo_atendente = ?");
            stmt.setString(1, cod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("nome_atendente");
            }
            stmt.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static String carregaNomeVendedorOp(Integer codOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            stmt = con.prepareStatement("SELECT cod_emissor FROM tabela_orcamentos WHERE cod = ?");
            stmt.setInt(1, codOrcamento);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("cod_emissor");
            }
            stmt.close();
            rs.close();
            retorno = carregaNomeVendedor(retorno) + " - " + retorno;
        } catch (Exception ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static void excluiOrcamento(int codigoOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tabela_orcamentos "
                    + "WHERE cod = " + codigoOrcamento);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public int retorna_codigo_produto(int codOrcamento) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int retorno = 0;

        try {
            stmt = con.prepareStatement("SELECT cod_produto FROM tabela_orcamentos WHERE cod = ?");
            stmt.setInt(1, codOrcamento);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getInt("cod_produto");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao retornar codigo produto orcamentos " + ex);
            retorno = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static List<Servicos> retornaServicosOrcamento(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Servicos> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod_componente_1 "
                    + "FROM tabela_componentes_orcamentos "
                    + "WHERE cod_orcamento = ? AND cod_componente = 2");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Servicos(rs.getInt("cod_componente_1")));
            }
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Altera o status da proposta de orçamento
     *
     * @param cod código da proposta
     * @param status status a mudar
     * @throws SQLException
     */
    public static void alteraStatus(int cod, int status) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_orcamentos "
                    + "SET status = ? "
                    + "WHERE cod = ?");
            stmt.setInt(1, status);
            stmt.setInt(2, cod);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * @param codOrcamento código do orçamento
     * @param novoStatus novo status do orçamento
     * @throws java.sql.SQLException
     * @see mudarStatus
     */
    public static void mudarStatus(int codOrcamento, byte novoStatus) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_orcamentos SET status = ? WHERE cod = ?");
            stmt.setByte(1, novoStatus);
            stmt.setInt(2, codOrcamento);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Cria produtos do orçamento
     *
     * @param prodOrc lista de produtos do orçamento
     * @throws SQLException
     */
    public static void criaProdOrc(ProdOrcamento prodOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tabela_produtos_orcamento(cod_orcamento, "
                    + "cod_produto, "
                    + "tipo_produto, "
                    + "descricao_produto, "
                    + "quantidade, "
                    + "observacao_produto, "
                    + "preco_unitario, "
                    + "valor_digital, "
                    + "maquina) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, prodOrc.getCodOrcamento());
            stmt.setInt(2, prodOrc.getCodProduto());
            stmt.setByte(3, prodOrc.getTipoProduto());
            stmt.setString(4, prodOrc.getDescricaoProduto());
            stmt.setInt(5, prodOrc.getQuantidade());
            stmt.setString(6, prodOrc.getObservacaoProduto());
            stmt.setFloat(7, prodOrc.getPrecoUnitario());
            stmt.setDouble(8, prodOrc.getValorDigital());
            stmt.setInt(9, prodOrc.getMaquina());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Exclui produtos do orçamento
     *
     * @param codOrc código do orçamento
     * @throws SQLException
     */
    public static void excluiProdOrc(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE cod_orcamento = ?");
            stmt.setInt(1, codOrc);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Carrega os produtos do orçamento
     *
     * @param codOrc código do orçamento
     * @return
     * @throws SQLException
     */
    public static List<ProdOrcamento> carregaProdOrc(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ProdOrcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE cod_orcamento = ?");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ProdOrcamento poAUX = new ProdOrcamento();
                poAUX.setCodOrcamento(rs.getInt("cod_orcamento"));
                poAUX.setTipoProduto(rs.getByte("tipo_produto"));
                poAUX.setCodProduto(rs.getInt("cod_produto"));
                poAUX.setDescricaoProduto(rs.getString("descricao_produto"));
                poAUX.setQuantidade(rs.getInt("quantidade"));
                poAUX.setObservacaoProduto(rs.getString("observacao_produto"));
                poAUX.setValorDigital(rs.getDouble("valor_digital"));
                poAUX.setMaquina(rs.getInt("maquina"));
                retorno.add(poAUX);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public synchronized static double retornaValorUnitario(int codOrcamento, int codProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double retorno = 0;

        try {
            stmt = con.prepareStatement("SELECT preco_unitario "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE cod_orcamento = ? AND cod_produto = ?");
            stmt.setInt(1, codOrcamento);
            stmt.setInt(2, codProduto);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("preco_unitario");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    //CONTATOS------------------------------------------------------------------
    public static List<Contato> retornaInformacoesContatos(int codContato) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Contato> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT nome_contato, telefone FROM tabela_contatos WHERE cod = ?");
            stmt.setInt(1, codContato);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contato a2 = new Contato();
                a2.setNomeContato(rs.getString("nome_contato"));
                a2.setTelefone(rs.getString("telefone"));
                retorno.add(a2);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    //ENDERECOS-----------------------------------------------------------------
    public static List<Endereco> retornaInformacoesEnderecos(int codEndereco) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Endereco> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cidade, uf FROM tabela_enderecos WHERE cod = ?");
            stmt.setInt(1, codEndereco);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Endereco a2 = new Endereco();
                a2.setCidade(rs.getString("cidade"));
                a2.setUf(rs.getString("uf"));
                retorno.add(a2);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Faz a pesquisa de orçamentos de acordo com os filtros selecionados
     *
     * @param p1 1- CODIGO, 2 - CLIENTE, 3 - DATA EMISSÃO, 4 - DATA VALIDADE, 5
     * - STATUS
     * @param p2
     * @param p3
     * @param pagina
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public static List<Orcamento> pesquisaRegistro(byte p1,
            String p2,
            String p3,
            int pagina) throws SQLException, ParseException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean cliente = false;

        int limite = 45;
        int offset = 0;

        List<Orcamento> retorno = new ArrayList();
        List retornoCliente = new ArrayList();

        if (pagina == 1) {
            try {
                switch (p1) {
                    case 1:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.cod = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setInt(1, Integer.valueOf(p3));
                        break;
                    case 2:
                        cliente = true;
                        break;
                    case 3:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.data_emissao = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setDate(1, new java.sql.Date(Controle.dataPadrao.parse(p3).getTime()));
                        break;
                    case 4:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.data_validade = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setDate(1, new java.sql.Date(Controle.dataPadrao.parse(p3).getTime()));
                        break;
                    case 5:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.status = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setInt(1, Integer.valueOf(p2));
                        break;
                }

                if (cliente) {
                    switch (p2) {
                        case "PESSOA FÍSICA - CÓDIGO":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_fisicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_fisicos ON tabela_clientes_fisicos.cod = tabela_orcamentos.cod_cliente "
                                    + "WHERE tabela_orcamentos.cod_cliente = ?  AND tabela_orcamentos.tipo_cliente = 1 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            stmt.setInt(1, Integer.valueOf(p3));
                            break;
                        case "PESSOA FÍSICA - NOME":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_fisicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_fisicos ON tabela_clientes_fisicos.nome LIKE '%" + p3 + "%' "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_fisicos.cod  AND tabela_orcamentos.tipo_cliente = 1 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            break;
                        case "PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_fisicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_fisicos ON tabela_clientes_fisicos.cpf = ? "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_fisicos.cod  AND tabela_orcamentos.tipo_cliente = 1 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            stmt.setString(1, p3);
                            break;
                        case "PESSOA JURÍDICA - CÓDIGO":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.cod = tabela_orcamentos.cod_cliente "
                                    + "WHERE tabela_orcamentos.cod_cliente = ?  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            stmt.setInt(1, Integer.valueOf(p3));
                            break;
                        case "PESSOA JURÍDICA - NOME":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.nome LIKE '%" + p3 + "%' "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_juridicos.cod  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            break;
                        case "PESSOA JURÍDICA - NOME FANTASIA":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.nome_fantasia LIKE '%" + p3 + "%' "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_juridicos.cod  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            break;
                        case "PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.cnpj = ? "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_juridicos.cod  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45");
                            stmt.setString(1, p3);
                            break;
                    }
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        Orcamento aux = new Orcamento();
                        aux.setCod(rs.getInt("tabela_orcamentos.cod"));
                        aux.setCodigoLI(rs.getInt("tabela_orcamentos.COD_LI"));
                        aux.setNomeCliente(rs.getInt("tabela_orcamentos.tipo_cliente") == 1
                                ? retornaNomeCliente(rs.getInt("tabela_clientes_fisicos.cod"), rs.getInt("tabela_orcamentos.tipo_cliente"))
                                : retornaNomeCliente(rs.getInt("tabela_clientes_juridicos.cod"), rs.getInt("tabela_orcamentos.tipo_cliente")));
                        aux.setTipoPessoa(rs.getInt("tabela_orcamentos.tipo_cliente"));
                        aux.setDataEmissao(rs.getDate("tabela_orcamentos.data_emissao"));
                        aux.setDataValidade(rs.getDate("tabela_orcamentos.data_validade"));
                        aux.setValorTotal(rs.getFloat("tabela_orcamentos.valor_total"));
                        aux.setStatus(rs.getInt("tabela_orcamentos.status"));
                        aux.setCodEmissor(rs.getString("tabela_orcamentos.cod_emissor"));
                        retorno.add(aux);
                    }
                } else {
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        Orcamento aux = new Orcamento();
                        aux.setCod(rs.getInt("tabela_orcamentos.cod"));
                        aux.setCodigoLI(rs.getInt("tabela_orcamentos.COD_LI"));
                        aux.setNomeCliente(retornaNomeCliente(rs.getInt("tabela_orcamentos.cod_cliente"), rs.getInt("tabela_orcamentos.tipo_cliente")));
                        aux.setTipoPessoa(rs.getInt("tabela_orcamentos.tipo_cliente"));
                        aux.setDataEmissao(rs.getDate("tabela_orcamentos.data_emissao"));
                        aux.setDataValidade(rs.getDate("tabela_orcamentos.data_validade"));
                        aux.setValorTotal(rs.getFloat("tabela_orcamentos.valor_total"));
                        aux.setStatus(rs.getInt("tabela_orcamentos.status"));
                        aux.setCodEmissor(rs.getString("tabela_orcamentos.cod_emissor"));
                        retorno.add(aux);
                    }
                }
                return retorno;
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ParseException ex) {
                throw new ParseException(ex.toString(), offset);
            } finally {
                ConnectionFactory.closeConnection(con, stmt, rs);
            }
        } else {
            offset = (pagina * limite) - limite;
            try {
                switch (p1) {
                    case 1:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.cod = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setInt(1, Integer.valueOf(p3));
                        break;
                    case 2:
                        cliente = true;
                        break;
                    case 3:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.data_emissao = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setDate(1, new java.sql.Date(Controle.dataPadrao.parse(p3).getTime()));
                        break;
                    case 4:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.data_validade = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setDate(1, new java.sql.Date(Controle.dataPadrao.parse(p3).getTime()));
                        break;
                    case 5:
                        stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, "
                                + "tabela_orcamentos.COD_LI, "
                                + "tabela_orcamentos.cod_cliente, "
                                + "tabela_orcamentos.tipo_cliente, "
                                + "tabela_orcamentos.data_emissao, "
                                + "tabela_orcamentos.data_validade, "
                                + "tabela_orcamentos.valor_total, "
                                + "tabela_orcamentos.status, "
                                + "tabela_orcamentos.cod_emissor "
                                + "FROM tabela_orcamentos "
                                + "WHERE tabela_orcamentos.status = ? "
                                + "ORDER BY tabela_orcamentos.cod "
                                + "DESC "
                                + "LIMIT 45");
                        stmt.setInt(1, Integer.valueOf(p2));
                        break;
                }

                if (cliente == true) {
                    switch (p2) {
                        case "PESSOA FÍSICA - CÓDIGO":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_fisicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_fisicos ON tabela_clientes_fisicos.cod = tabela_orcamentos.cod_cliente "
                                    + "WHERE tabela_orcamentos.cod_cliente = ?  AND tabela_orcamentos.tipo_cliente = 1 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setInt(1, Integer.valueOf(p3));
                            stmt.setInt(2, offset);
                            break;
                        case "PESSOA FÍSICA - NOME":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_fisicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_fisicos ON tabela_clientes_fisicos.nome LIKE '%" + p3 + "%' "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_fisicos.cod  AND tabela_orcamentos.tipo_cliente = 1 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setInt(1, offset);
                            break;
                        case "PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_fisicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_fisicos ON tabela_clientes_fisicos.cpf = ? "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_fisicos.cod  AND tabela_orcamentos.tipo_cliente = 1 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setString(1, p3);
                            stmt.setInt(2, offset);
                            break;
                        case "PESSOA JURÍDICA - CÓDIGO":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.cod = tabela_orcamentos.cod_cliente "
                                    + "WHERE tabela_orcamentos.cod_cliente = ?  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setInt(1, Integer.valueOf(p3));
                            stmt.setInt(2, offset);
                            break;
                        case "PESSOA JURÍDICA - NOME":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.nome LIKE '%" + p3 + "%' "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_juridicos.cod  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setInt(1, offset);
                            break;
                        case "PESSOA JURÍDICA - NOME FANTASIA":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.nome_fantasia LIKE '%" + p3 + "%' "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_juridicos.cod  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setInt(1, offset);
                            break;
                        case "PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)":
                            stmt = con.prepareStatement("SELECT "
                                    + "tabela_orcamentos.cod, "
                                    + "tabela_orcamentos.COD_LI, "
                                    + "tabela_orcamentos.tipo_cliente, "
                                    + "tabela_orcamentos.data_emissao, "
                                    + "tabela_orcamentos.data_validade, "
                                    + "tabela_orcamentos.valor_total, "
                                    + "tabela_orcamentos.status, "
                                    + "tabela_orcamentos.cod_emissor, "
                                    + "tabela_clientes_juridicos.cod "
                                    + "FROM tabela_orcamentos "
                                    + "INNER JOIN tabela_clientes_juridicos ON tabela_clientes_juridicos.cnpj = ? "
                                    + "WHERE tabela_orcamentos.cod_cliente = tabela_clientes_juridicos.cod  AND tabela_orcamentos.tipo_cliente = 2 "
                                    + "ORDER BY tabela_orcamentos.cod "
                                    + "DESC "
                                    + "LIMIT 45 "
                                    + "OFFSET ?");
                            stmt.setString(1, p3);
                            stmt.setInt(2, offset);
                            break;
                    }
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        Orcamento aux = new Orcamento();
                        aux.setCod(rs.getInt("tabela_orcamentos.cod"));
                        aux.setCodigoLI(rs.getInt("tabela_orcamentos.COD_LI"));
                        aux.setNomeCliente(rs.getInt("tabela_orcamentos.tipo_cliente") == 1
                                ? retornaNomeCliente(rs.getInt("tabela_clientes_fisicos.cod"), rs.getInt("tabela_orcamentos.tipo_cliente"))
                                : retornaNomeCliente(rs.getInt("tabela_clientes_juridicos.cod"), rs.getInt("tabela_orcamentos.tipo_cliente")));
                        aux.setTipoPessoa(rs.getInt("tabela_orcamentos.tipo_cliente"));
                        aux.setDataEmissao(rs.getDate("tabela_orcamentos.data_emissao"));
                        aux.setDataValidade(rs.getDate("tabela_orcamentos.data_validade"));
                        aux.setValorTotal(rs.getFloat("tabela_orcamentos.valor_total"));
                        aux.setStatus(rs.getInt("tabela_orcamentos.status"));
                        aux.setCodEmissor(rs.getString("tabela_orcamentos.cod_emissor"));
                        retorno.add(aux);
                    }
                } else {
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        Orcamento aux = new Orcamento();
                        aux.setCod(rs.getInt("tabela_orcamentos.cod"));
                        aux.setCodigoLI(rs.getInt("tabela_orcamentos.COD_LI"));
                        aux.setNomeCliente(rs.getInt("tabela_orcamentos.tipo_cliente") == 1
                                ? retornaNomeCliente(rs.getInt("tabela_clientes_fisicos.cod"), rs.getInt("tabela_orcamentos.tipo_cliente"))
                                : retornaNomeCliente(rs.getInt("tabela_clientes_juridicos.cod"), rs.getInt("tabela_orcamentos.tipo_cliente")));
                        aux.setTipoPessoa(rs.getInt("tabela_orcamentos.tipo_cliente"));
                        aux.setDataEmissao(rs.getDate("tabela_orcamentos.data_emissao"));
                        aux.setDataValidade(rs.getDate("tabela_orcamentos.data_validade"));
                        aux.setValorTotal(rs.getFloat("tabela_orcamentos.valor_total"));
                        aux.setStatus(rs.getInt("tabela_orcamentos.status"));
                        aux.setCodEmissor(rs.getString("tabela_orcamentos.cod_emissor"));
                        retorno.add(aux);
                    }
                }
                return retorno;
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } finally {
                ConnectionFactory.closeConnection(con, stmt, rs);
            }
        }
    }

    public static String retornaNomeCliente(int codCliente, int tipoCliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            if (tipoCliente == 1) {
                stmt = con.prepareStatement("SELECT nome FROM tabela_clientes_fisicos WHERE cod = ?");
                stmt.setInt(1, codCliente);
            } else {
                stmt = con.prepareStatement("SELECT nome FROM tabela_clientes_juridicos WHERE cod = ?");
                stmt.setInt(1, codCliente);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("nome");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    //--------------------------------------------------------------------------
    public static List<ProdOrcamento> retornaProdutos(int codOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ProdOrcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod_produto, "
                    + "tipo_produto, "
                    + "descricao_produto, "
                    + "quantidade, "
                    + "preco_unitario "
                    + "FROM tabela_produtos_orcamento"
                    + " WHERE cod_orcamento = ?");
            stmt.setInt(1, codOrcamento);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ProdOrcamento aux = new ProdOrcamento();
                aux.setCodProduto(rs.getInt("cod_produto"));
                aux.setTipoProduto(rs.getByte("tipo_produto"));
                aux.setDescricaoProduto(rs.getString("descricao_produto"));
                aux.setQuantidade(rs.getInt("quantidade"));
                aux.setPrecoUnitario(rs.getFloat("preco_unitario"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * @param codOrc
     * @return double valor do frete
     * @throws java.sql.SQLException
     * @see retornaValorFrete
     */
    public static Double retornaValorFrete(Integer codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT frete FROM tabela_orcamentos WHERE cod = ?");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("frete");
            }
            return null;
        } catch (Exception ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Cria os cálculos de papel da proposta, que serão reutilizados na ordem de
     * produção
     *
     * @param calculosBEAN cálculos da proposta
     * @throws SQLException
     */
    public static void createCalculosProposta(CalculosOp calculosBEAN) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tabela_calculos_op(cod_op, tipo_papel,"
                    + "qtd_folhas_total, formato, qtd_chapas, perca, cod_proposta, cod_produto, tipo_produto, "
                    + "cod_papel) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, calculosBEAN.getCodOp());
            stmt.setString(2, calculosBEAN.getTipoPapel());
            stmt.setInt(3, calculosBEAN.getQtdFolhasTotal());
            stmt.setInt(4, calculosBEAN.getFormato());
            stmt.setInt(5, calculosBEAN.getQtdChapas());
            stmt.setFloat(6, calculosBEAN.getPerca());
            stmt.setInt(7, calculosBEAN.getCodigoProposta());
            stmt.setInt(8, calculosBEAN.getCodProduto());
            stmt.setByte(9, calculosBEAN.getTipoProduto());
            stmt.setInt(10, calculosBEAN.getCodigoPapel());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Retorna os cálculos de papel da proposta de orçamento
     *
     * @param codProposta código da proposta
     * @param codProd código do produto
     * @param tipoProd tipo do produto
     * @param codPapel código do papel
     * @param tipoPapel tipo do papel
     * @return
     * @throws SQLException
     */
    public static CalculosOp retornaCalculosProposta(int codProposta,
            int codProd,
            byte tipoProd,
            int codPapel,
            String tipoPapel) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        CalculosOp retorno = new CalculosOp();

        try {
            stmt = con.prepareStatement("SELECT formato, perca, qtd_folhas_total, qtd_chapas, cod_papel "
                    + "FROM tabela_calculos_op "
                    + "WHERE cod_proposta = ? "
                    + "AND cod_produto = ? "
                    + "AND tipo_papel = ? "
                    + "AND cod_papel = ? "
                    + "AND tipo_produto = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, codProposta);
            stmt.setInt(2, codProd);
            stmt.setString(3, tipoPapel);
            stmt.setInt(4, codPapel);
            stmt.setByte(5, tipoProd);
            rs = stmt.executeQuery();
            if (rs.first()) {
                retorno.setFormato(rs.getInt("formato"));
                retorno.setPerca(rs.getFloat("perca"));
                retorno.setQtdFolhasTotal(rs.getInt("qtd_folhas_total"));
                retorno.setQtdChapas(rs.getInt("qtd_chapas"));
                retorno.setCodigoPapel(rs.getInt("cod_papel"));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Exclui os cálculos de papéis do orçamento
     *
     * @param codProposta código da proposta de orçamento
     * @throws SQLException
     */
    public static void excluiCalculosProposta(int codProposta) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE "
                    + "FROM tabela_calculos_op "
                    + "WHERE cod_proposta = ?");
            stmt.setInt(1, codProposta);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Verifica se existe cálculos pré-cadastrados de papéis
     *
     * @param codOrc código da proposta de orçamento
     * @return
     * @throws SQLException
     */
    public static boolean verificaCalculos(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod "
                    + "FROM tabela_calculos_op "
                    + "WHERE cod_proposta = ?");
            stmt.setInt(1, codOrc);
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
     * @param codigoOrcamento Código do orçamento
     * @return Status do faturamento para a nota de venda
     * @throws java.sql.SQLException
     * @see retornaStatusFaturamento
     */
    public static byte retornaStatusFaturamento(int codigoOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT FAT_TOTALMENTE FROM tabela_orcamentos WHERE cod = ?");
            stmt.setInt(1, codigoOrcamento);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getByte("FAT_TOTALMENTE");
            }
            return 4;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * @param codigoOrcamento Código do orçamento
     * @param statusFaturamento Status do faturamento
     * @throws java.sql.SQLException
     * @see atualizaStatusFaturamento
     */
    public static void atualizaStatusFaturamento(int codigoOrcamento, byte statusFaturamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_orcamentos "
                    + "SET FAT_TOTALMENTE = ? "
                    + "WHERE cod = ?");
            stmt.setInt(1, statusFaturamento);
            stmt.setInt(2, codigoOrcamento);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Retorna os status cadastrados no banco de dados
     *
     * @return List<> lista de status
     * @throws java.sql.SQLException
     * @see retornaStsOrcamento
     */
    public static List<StsOrcamento> retornaStsOrcamento() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<StsOrcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM sts_orcamento");
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new StsOrcamento(rs.getInt("CODIGO"), rs.getString("STS_DESCRICAO")));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * @param codOrc código do orçamento
     * @return Orcamento orçamento a ser selecionado para a nota de venda
     * @throws java.sql.SQLException
     * @see selInfoNota
     */
    public static Orcamento selecionaInformacoesNota(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod_cliente, cod_contato, cod_endereco, frete, ARTE, "
                    + "tipo_cliente "
                    + "FROM tabela_orcamentos "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Orcamento(rs.getInt("cod_cliente"),
                        rs.getInt("cod_contato"),
                        rs.getInt("cod_endereco"),
                        rs.getDouble("frete"),
                        rs.getDouble("ARTE"),
                        rs.getInt("tipo_cliente")
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
     * @param codOrc código do orcamento
     * @param codProduto
     * @return ProdOrcamento produto do orçamento
     * @throws java.sql.SQLException
     * @see selecionaInformacoesProduto
     */
    public synchronized static ProdOrcamento selecionaInformacoesProduto(int codOrc,
            int codProduto,
            byte tipoProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (codProduto == 0) {
                stmt = con.prepareStatement("SELECT cod_produto, "
                        + "tipo_produto, "
                        + "descricao_produto, "
                        + "quantidade, "
                        + "preco_unitario,"
                        + "observacao_produto "
                        + "FROM tabela_produtos_orcamento "
                        + "WHERE cod_orcamento = ?");
                stmt.setInt(1, codOrc);
            } else {
                stmt = con.prepareStatement("SELECT cod_produto, "
                        + "tipo_produto, "
                        + "descricao_produto, "
                        + "quantidade, "
                        + "preco_unitario,"
                        + "observacao_produto "
                        + "FROM tabela_produtos_orcamento "
                        + "WHERE cod_orcamento = ? AND cod_produto = ? AND tipo_produto = ?");
                stmt.setInt(1, codOrc);
                stmt.setInt(2, codProduto);
                stmt.setByte(3, tipoProduto);
            }

            rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProdOrcamento(rs.getString("descricao_produto"),
                        rs.getInt("quantidade"),
                        rs.getFloat("preco_unitario"),
                        rs.getInt("cod_produto"),
                        rs.getByte("tipo_produto"),
                        rs.getString("observacao_produto")
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
     * @param codOrc código do orçamento a ser selecionado
     * @return Cliente código e tipo do cliente a ser selecionado
     * @throws java.sql.SQLException
     * @see retornaClienteOrc
     */
    public static Cliente retornaClienteOrc(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod_cliente, tipo_cliente "
                    + "FROM tabela_orcamentos "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("cod_cliente"),
                        (byte) rs.getInt("tipo_cliente")
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
     * Retorna o valor parcial do produto (unitario * quantidade) para
     * restauramento do crédito da OP
     *
     * @param codOrc código do orçamento
     * @param codProd código do produto
     * @return
     * @throws java.sql.SQLException
     */
    public static double retornaVlrParcProd(int codOrc, byte tipoProduto, int codProd) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT (quantidade * preco_unitario) AS VLR_PARC "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE cod_orcamento = ? AND cod_produto = ? AND tipo_produto = ?");
            stmt.setInt(1, codOrc);
            stmt.setInt(2, codProd);
            stmt.setByte(3, tipoProduto);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("VLR_PARC");
            }
            return 0d;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna a quantidade do produto solicitada no orçamento
     *
     * @param codOrc código do orçamento
     * @param codProd código do produto
     * @return
     * @throws SQLException
     */
    public static int retornaQtdProdPe(int codOrc, String codProd) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT quantidade "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE cod_orcamento = ? AND cod_produto = ?");
            stmt.setInt(1, codOrc);
            stmt.setString(2, codProd);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantidade");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna dados do produto que está contido no orçamento
     *
     * @param codOrcamento código do orçamento
     * @param codProduto código do produto
     * @return
     * @throws SQLException
     */
    public static ProdOrcamento retornaProdutoOrcamento(int codOrcamento, int codProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT observacao_produto, "
                    + "quantidade, "
                    + "preco_unitario, "
                    + "tipo_produto,"
                    + "cod_produto,"
                    + "descricao_produto "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE cod_orcamento = ? AND cod_produto = ?");
            stmt.setInt(1, codOrcamento);
            stmt.setInt(2, codProduto);
            System.out.println(stmt);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProdOrcamento(
                        rs.getString("descricao_produto"),
                        rs.getInt("quantidade"),
                        rs.getFloat("preco_unitario"),
                        rs.getInt("cod_produto"),
                        rs.getByte("tipo_produto"),
                        rs.getString("observacao_produto")
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
     * Retorna o valor total do orçamento
     *
     * @param codOrcamento código do orçamento
     * @return
     * @throws SQLException
     */
    public static Double retornaVlrTotal(int codOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT valor_total "
                    + "FROM tabela_orcamentos "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOrcamento);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("valor_total");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna os orçamentos aprovados pelo OD
     *
     * @return
     * @throws SQLException
     */
    public static List<Orcamento> retornaApOD() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Orcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod, cod_cliente, tipo_cliente "
                    + "FROM tabela_orcamentos "
                    + "WHERE status = 4 OR status = 11 "
                    + "ORDER BY cod "
                    + "DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Orcamento(
                        rs.getInt("cod"),
                        rs.getInt("cod_cliente"),
                        rs.getInt("tipo_cliente")
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
     * Consulta as alterações de recálculo das OP
     *
     * @return @throws SQLException
     */
    public static List<AlteraData> consultarAlteracoes() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Timestamp alteracao = null;
        List<AlteraData> listaAlteracoes = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM alteracoes_ordem_producao "
                    + "ORDER BY alteracoes_ordem_producao.CODIGO "
                    + "DESC "
                    + "LIMIT 3");
            rs = stmt.executeQuery();
            while (rs.next()) {
                listaAlteracoes.add(new AlteraData(
                        rs.getInt("alteracoes_ordem_producao.OP"),
                        rs.getTimestamp("alteracoes_ordem_producao.ALTERACAO"),
                        rs.getDate("alteracoes_ordem_producao.DATA_ANTERIOR"),
                        rs.getString("alteracoes_ordem_producao.USUARIO"),
                        rs.getString("alteracoes_ordem_producao.MOTIVO")
                ));
            }

            return listaAlteracoes;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna as propostas de orçamentos que venceram por prazo de validade
     *
     * @return
     * @throws SQLException
     */
    public static List<Orcamento> consultaPropostasVencidas() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Orcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, tabela_orcamentos.data_validade, tabela_orcamentos.status "
                    + "FROM tabela_orcamentos "
                    + "WHERE tabela_orcamentos.data_validade < ? AND (tabela_orcamentos.status = 1 OR tabela_orcamentos.status = 3 "
                    + "OR tabela_orcamentos.status = 4 OR tabela_orcamentos.status = 11)");
            stmt.setDate(1, new java.sql.Date(new Date().getTime()));
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Orcamento(
                        rs.getInt("tabela_orcamentos.cod"),
                        rs.getDate("tabela_orcamentos.data_validade"),
                        rs.getByte("tabela_orcamentos.status")
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
     * Retorna as propostas de orçamento associadas ao cliente
     *
     * @param cliente
     * @return
     * @throws SQLException
     */
    public static List<Orcamento> retornaPropostasCliente(Cliente cliente) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Orcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT tabela_orcamentos.cod, tabela_orcamentos.data_emissao, tabela_orcamentos.data_validade, tabela_orcamentos.status, tabela_orcamentos.valor_total "
                    + "FROM tabela_orcamentos "
                    + "WHERE tabela_orcamentos.cod_cliente = ? AND tabela_orcamentos.tipo_cliente = ?");
            stmt.setInt(1, cliente.getCodigo());
            stmt.setByte(2, cliente.getTipoPessoa());
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Orcamento(
                        rs.getInt("tabela_orcamentos.cod"),
                        Controle.dataPadrao.format(rs.getDate("tabela_orcamentos.data_emissao")),
                        Controle.dataPadrao.format(rs.getDate("tabela_orcamentos.data_validade")),
                        rs.getFloat("tabela_orcamentos.valor_total"),
                        rs.getInt("tabela_orcamentos.status") + " - " + Controle.getStatusByCod(rs.getInt("tabela_orcamentos.status")).toString(),
                        rs.getInt("tabela_orcamentos.status")
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
     * Retorna os produtos associados à proposta de orçamento
     *
     * @param orcamento
     * @return
     * @throws SQLException
     */
    public static List<ProdOrcamento> retornaProdutosOrcamento(Orcamento orcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ProdOrcamento> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT tabela_produtos_orcamento.cod_produto, tabela_produtos_orcamento.descricao_produto, tabela_produtos_orcamento.quantidade, tabela_produtos_orcamento.preco_unitario "
                    + "FROM tabela_produtos_orcamento "
                    + "WHERE tabela_produtos_orcamento.cod_orcamento = ?");
            stmt.setInt(1, orcamento.getCod());
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new ProdOrcamento(
                        rs.getInt("tabela_produtos_orcamento.cod_produto"),
                        rs.getString("tabela_produtos_orcamento.descricao_produto"),
                        rs.getInt("tabela_produtos_orcamento.quantidade"),
                        rs.getFloat("tabela_produtos_orcamento.preco_unitario")
                ));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static Boolean verificaOrcamentoExistente(int codOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT tabela_orcamentos.cod "
                    + "FROM tabela_orcamentos "
                    + "WHERE tabela_orcamentos.cod = ? "
                    + "ORDER BY tabela_orcamentos.cod "
                    + "DESC "
                    + "LIMIT 1");
            stmt.setInt(1, codOrcamento);
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

}
