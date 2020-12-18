/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import entidades.AlteraData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidades.OrdemProducao;
import entidades.CalculosOpBEAN;
import entidades.Servicos;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.dao.OrdemProducaoDAO.alteraDtCancelamento;
import static model.dao.OrdemProducaoDAO.alteraStatusOp;
import static model.dao.OrdemProducaoDAO.consultaOp;
import static model.dao.OrdemProducaoDAO.retornaCodOpOrcProd;
import ui.cadastros.clientes.ClienteBEAN;
import ui.cadastros.clientes.ClienteDAO;
import static ui.cadastros.clientes.ClienteDAO.selInfoNota;
import ui.cadastros.produtos.ProdutoDAO;

/**
 *
 * @author spd3
 */
public class OrdemProducaoDAO {

    public static OrdemProducao retornaDadosOp(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new OrdemProducao(
                        rs.getInt("cod"),
                        rs.getInt("cod_cliente"),
                        rs.getByte("tipo_cliente"),
                        rs.getDate("data_emissao"),
                        rs.getDate("data_entrega"),
                        rs.getString("status"),
                        rs.getString("descricao"),
                        rs.getString("op_secao"),
                        rs.getString("cod_emissor"),
                        rs.getDate("data_1a_prova"),
                        rs.getDate("data_2a_prova"),
                        rs.getDate("data_3a_prova"),
                        rs.getDate("data_4a_prova"),
                        rs.getDate("data_5a_prova"),
                        rs.getDate("data_apr_cliente"),
                        rs.getDate("data_ent_final"),
                        rs.getDate("data_imp_dir"),
                        rs.getDate("data_ent_offset"),
                        rs.getDate("DT_ENT_DIGITAL"),
                        rs.getDate("data_ent_tipografia"),
                        rs.getDate("data_ent_acabamento"),
                        rs.getDate("data_envio_div_cmcl"),
                        rs.getInt("ind_ent_prazo"),
                        rs.getInt("ind_ent_erro"),
                        rs.getInt("cod_produto"),
                        rs.getByte("tipo_produto"),
                        rs.getInt("orcamento_base"),
                        rs.getInt("cod_contato"),
                        rs.getInt("cod_endereco"),
                        rs.getDate("DT_CANCELAMENTO"),
                        rs.getString("tipo_trabalho")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static int retornaUltimoRegistro() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int retorno = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_ordens_producao ORDER BY cod DESC LIMIT 1");
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getInt("cod");
            }
            stmt.close();
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public boolean verificaOp(int cod) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean retorno = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_ordens_producao WHERE cod = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao verificar OP " + ex);
            retorno = false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Cria a ordem de produção
     * @param op ordem de produção
     * @throws SQLException 
     */
    public static void createOp(OrdemProducao op) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tabela_ordens_producao(cod, "
                    + "orcamento_base, "
                    + "cod_cliente, "
                    + "tipo_cliente,"
                    + "data_emissao, "
                    + "data_entrega, "
                    + "status, "
                    + "descricao, "
                    + "cod_emissor, "
                    + "cod_produto, "
                    + "tipo_produto, "
                    + "cod_contato, "
                    + "cod_endereco,"
                    + "DT_ENTG_PROVA,"
                    + "OBS_FRETE) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, op.getCodigo());
            stmt.setInt(2, op.getOrcBase());
            stmt.setInt(3, op.getCodCliente());
            stmt.setInt(4, op.getTipoPessoa());
            stmt.setDate(5, new java.sql.Date(op.getDataEmissao().getTime()));
            stmt.setDate(6, new java.sql.Date(op.getDataEntrega().getTime()));
            stmt.setString(7, op.getStatus());
            stmt.setString(8, op.getDescricao());
            stmt.setString(9, op.getCodEmissor());
            stmt.setInt(10, op.getCodProduto());
            stmt.setByte(11, op.getTipoProduto());
            stmt.setInt(12, op.getCodContato());
            stmt.setInt(13, op.getCodEndereco());
            stmt.setDate(14, op.getDataEntgProva() != null 
                    ? new java.sql.Date(op.getDataEntgProva().getTime()) 
                    : null);
            stmt.setString(15, op.getObsFrete());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Atualiza os cálculo da ordem de produção
     * @param codProposta código da proposta de orçamento
     * @param codProd código do produto
     * @param tipoProd tipo do produto 1 - PERSONALIZADO (PP), 2 - PRONTA ENTREGA (PE), 3 - INTERNET (PI)
     * @param codPapel código do papel
     * @param codOp código da ordem de produção
     * @param tipoPapel tipo do papel
     * @throws SQLException 
     */
    public static void alteraCalculosOp(int codProposta,
            int codProd,
            byte tipoProd,
            int codPapel,
            int codOp,
            String tipoPapel) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_calculos_op "
                    + "SET cod_op = ? "
                    + "WHERE cod_proposta = ? "
                    + "AND cod_produto = ? "
                    + "AND cod_papel = ? "
                    + "AND tipo_papel = ? "
                    + "AND tipo_produto = ?");
            stmt.setInt(1, codOp);
            stmt.setInt(2, codProposta);
            stmt.setInt(3, codProd);
            stmt.setInt(4, codPapel);
            stmt.setString(5, tipoPapel);
            stmt.setByte(6, tipoProd);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static OrdemProducao carregaPdfOp(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new OrdemProducao(
                        codOp,
                        rs.getInt("orcamento_base"),
                        rs.getInt("cod_cliente"),
                        rs.getInt("cod_contato"),
                        rs.getInt("cod_endereco"),
                        rs.getInt("cod_produto"),
                        rs.getByte("tipo_produto"),
                        rs.getDate("data_entrega"),
                        rs.getDate("data_emissao"),
                        rs.getDate("DT_ENTG_PROVA"),
                        rs.getByte("tipo_cliente"),
                        rs.getString("cod_emissor"),
                        rs.getDate("DT_CANCELAMENTO"),
                        rs.getString("OBS_FRETE")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static int retornaGramaturaPapel(int codPapel) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int retorno = 0;

        try {
            stmt = con.prepareStatement("SELECT gramatura FROM tabela_papeis WHERE cod = ?");
            stmt.setInt(1, codPapel);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getInt("gramatura");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Retorna os cálculos da op feitos no orçamento
     *
     * @param codOp código da ordem de produção
     * @param tipoProduto
     * @param codProduto código do produto
     * @param tipoPapel tipo de papel
     * @return
     * @throws SQLException
     */
    public static List<CalculosOpBEAN> retornaCalculosOp(int codOp, 
            byte tipoProduto,
            int codProduto, 
            String tipoPapel) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<CalculosOpBEAN> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_calculos_op "
                    + "WHERE cod_op = ? "
                    + "AND cod_produto = ? "
                    + "AND tipo_produto = ? "
                    + "AND tipo_papel = ? "
                    + "ORDER BY cod_op "
                    + "DESC "
                    + "LIMIT 1");
            stmt.setInt(1, codOp);
            stmt.setInt(2, codProduto);
            stmt.setByte(3, tipoProduto);
            stmt.setString(4, tipoPapel);
            rs = stmt.executeQuery();
            while (rs.next()) {
                CalculosOpBEAN calculo = new CalculosOpBEAN();
                calculo.setQtdFolhas(rs.getInt("qtd_folhas"));
                calculo.setQtdFolhasTotal(rs.getInt("qtd_folhas_total"));
                calculo.setMontagem(rs.getInt("montagem"));
                calculo.setFormato(rs.getInt("formato"));
                calculo.setPerca(rs.getFloat("perca"));
                retorno.add(calculo);
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna os serviços atrelados ao orçamento
     *
     * @param codOrcamento código do orçamento
     * @return
     * @throws SQLException
     */
    public static List<Servicos> retornaComponentesOrcamento(int codOrcamento) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Servicos> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT cod_componente_1 "
                    + "FROM tabela_componentes_orcamentos "
                    + "WHERE cod_orcamento = ?");
            stmt.setInt(1, codOrcamento);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Servicos servicos = new Servicos();
                servicos.setCod(rs.getInt("cod_componente_1"));
                retorno.add(servicos);
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static String retornaDescricaoServico(int codServico) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_servicos_orcamento WHERE cod = ?");
            stmt.setInt(1, codServico);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("descricao");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public void incluiChapas(int codOp, int qtdChapas, int codProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_calculos_op SET qtd_chapas = ? WHERE cod_op = ? AND cod_produto = ?");
            stmt.setInt(1, qtdChapas);
            stmt.setInt(2, codOp);
            stmt.setInt(3, codProduto);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static List<CalculosOpBEAN> retornaQtdChapas(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<CalculosOpBEAN> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT qtd_chapas, cod_papel FROM tabela_calculos_op WHERE cod_op = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            while (rs.next()) {
                CalculosOpBEAN calculosBEAN = new CalculosOpBEAN();
                calculosBEAN.setQtdChapas(rs.getInt("qtd_chapas"));
                calculosBEAN.setCodigoPapel(rs.getInt("cod_papel"));
                retorno.add(calculosBEAN);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return retorno;
    }

    /**
     * Retorna o código do produto atrelado à OP
     *
     * @param codOp código da ordem de produção
     * @return
     * @throws SQLException
     */
    public static int retornaCodProd(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod_produto "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod_produto");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public static String retornaObservacao(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            stmt = con.prepareStatement("SELECT descricao FROM tabela_ordens_producao WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("descricao");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public String retornaTipoTrabalho(int codOrcamentoBase, int codProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;

        try {
            stmt = con.prepareStatement("SELECT tipo_trabalho FROM tabela_produtos_orcamento WHERE cod_orcamento = ? AND cod_produto = ?");
            stmt.setInt(1, codOrcamentoBase);
            stmt.setInt(2, codProduto);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getString("tipo_trabalho");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * Define o status como cancelada e aciciona o motivo do cancelamento
     * @param codOp código da OP
     * @param motivoCanc motivo do cancelamento
     * @throws SQLException 
     */
    public static void cancelaOp(int codOp, String motivoCanc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_ordens_producao "
                    + "SET status = 'CANCELADA', descricao = ? "
                    + "WHERE cod = ?");
            stmt.setString(1, motivoCanc);
            stmt.setInt(2, codOp);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static int retornaOrcamentoBase(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT orcamento_base "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("orcamento_base");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Retorna a observação existente na OP
     * @param codOp código da OP
     * @return
     * @throws SQLException 
     */
    public static String retornaObsOp(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT descricao "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("descricao");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    //NOTA FISCAL---------------------------------------------------------------
    /**
     * @param codOp código da ordem de produção a ser selecionada para a nota de
     * venda
     * @return OrdemProducao ordem de produção
     * @see selInfoNota
     */
    public static OrdemProducao selecionaInformacoesNota(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod, cod_produto, tipo_produto, cod_contato, "
                    + "cod_endereco, cod_cliente, tipo_cliente, cod_emissor, orcamento_base "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new OrdemProducao(codOp,
                        rs.getInt("orcamento_base"),
                        rs.getInt("cod_produto"),
                        rs.getByte("tipo_produto"),
                        rs.getInt("cod_cliente"),
                        rs.getInt("cod_contato"),
                        rs.getInt("cod_endereco"),
                        rs.getByte("tipo_cliente"),
                        rs.getString("cod_emissor"));
            }
            return null;
        } catch (Exception ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    //PAGINACAO PESQUISA--------------------------------------------------------
    public static List<OrdemProducao> consultaOpTodos(int pagina) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int limite = 45;
        int offset = 0;

        List<OrdemProducao> retorno = new ArrayList();

        try {
            if (pagina == 1) {
                stmt = con.prepareStatement("SELECT cod, orcamento_base, cod_produto, tipo_produto, "
                        + "cod_cliente, tipo_cliente, data_emissao, data_entrega, status "
                        + "FROM tabela_ordens_producao ORDER BY cod DESC LIMIT 45");
            } else {
                offset = (pagina * limite) - limite;
                stmt = con.prepareStatement("SELECT cod, orcamento_base, cod_produto, tipo_produto, "
                        + "cod_cliente, tipo_cliente, data_emissao, data_entrega, status "
                        + "FROM tabela_ordens_producao ORDER BY cod DESC LIMIT 45 OFFSET ?");
                stmt.setInt(1, offset);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemProducao aux = new OrdemProducao();
                aux.setCodigo(rs.getInt("cod"));
                aux.setOrcBase(rs.getInt("orcamento_base"));
                aux.setCodProduto(rs.getInt("cod_produto"));
                aux.setTipoProduto(rs.getByte("tipo_produto"));
                aux.setCodCliente(rs.getInt("cod_cliente"));
                aux.setTipoPessoa(rs.getByte("tipo_cliente"));
                aux.setDataEmissao(rs.getDate("data_emissao"));
                aux.setDataEntrega(rs.getDate("data_entrega"));
                aux.setStatus(rs.getString("status"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public int retornaCodigoCliente(String p2, String p3) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int retorno = 0;

        try {
            if (p2.equals("PESSOA FÍSICA - NOME")) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_fisicos WHERE nome LIKE " + "'%" + p3 + "%' ORDER BY cod ASC");
            }
            if (p2.equals("PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)")) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_fisicos WHERE cpf = ? ORDER BY cod ASC");
                stmt.setString(1, p3);
            }
            if (p2.equals("PESSOA JURÍDICA - NOME")) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_juridicos WHERE nome LIKE " + "'%" + p3 + "%' ORDER BY cod ASC");
            }
            if (p2.equals("PESSOA JURÍDICA - NOME FANTASIA")) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_juridicos WHERE nome_fantasia LIKE " + "'%" + p3 + "%' ORDER BY cod ASC");
            }
            if (p2.equals("PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)")) {
                stmt = con.prepareStatement("SELECT cod FROM tabela_clientes_juridicos WHERE cnpj = ? ORDER BY cod ASC");
                stmt.setString(1, p3);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = rs.getInt("cod");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
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
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    /**
     * @param codigoOp Código da ordem de produção
     * @param status Novo status da ordem de produção
     * @return void Altera o staus da ordem de produção
     * @see alteraStatusOp
     */
    public static void alteraStatusOp(int codigoOp, String status) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_ordens_producao SET status = ? WHERE cod = ?");
            stmt.setString(1, status);
            stmt.setInt(2, codigoOp);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * @param codigoOp Código da ordem de produção
     * @param data Nova data de cancelamento
     * @return void Atualiza a data de cancelamento
     * @see alteraDtCancelamento
     */
    public static void alteraDtCancelamento(int codigoOp, java.util.Date data) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_ordens_producao SET DT_CANCELAMENTO = ? "
                    + "WHERE cod = ?");
            stmt.setDate(1, new java.sql.Date(data.getTime()));
            stmt.setInt(2, codigoOp);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * @param codOrcamento código do orçamento
     * @param codProduto código do produto
     * @return int código da ordem de produção (se houver)
     * @see retornaCodOpOrcProd
     */
    public static int retornaCodOpOrcProd(int codOrcamento, int codProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod FROM tabela_ordens_producao WHERE orcamento_base = ? AND "
                    + "cod_produto = ?");
            stmt.setInt(1, codOrcamento);
            stmt.setInt(2, codProduto);
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
     * @param codOrc código do orçamento
     * @return int código da ordem de produção
     * @see retornaCodOpOrcProd
     */
    public static int retornaCodOpOrc(int codOrc) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod FROM tabela_ordens_producao WHERE orcamento_base = ?");
            stmt.setInt(1, codOrc);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod");
            }
            return 0;
        } catch (Exception ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * @param tipo tipo de pesquisa selecionado 1 - CÓDIGO 2 - ORÇAMENTO BASE 3
     * - PRODUTO 4 - CLIENTE 5 - DATA EMISSAO 6 - DATA ENTREGA 7 - STATUS
     * @param tipoPesqCliente tipo de pesquisa do cliente
     * @param pesquisa conteúdo a ser pesquisado
     * @param data data a ser pesquisada
     * @return List<OrdemProducao> lista de ordem de produção
     * @see consultaOp
     */
    public static List<OrdemProducao> consultaOp(byte tipo, String tipoPesqCliente, String pesquisa, Date data) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<OrdemProducao> retorno = new ArrayList();
        List retornoCliente = new ArrayList();
        boolean cliente = false;
        boolean produto = false;

        try {
            switch (tipo) {
                case 1:
                    stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                            + "cod_produto, tipo_produto, cod_cliente,"
                            + "tipo_cliente, data_emissao,"
                            + "data_entrega, status"
                            + " FROM tabela_ordens_producao WHERE cod = ?");
                    stmt.setString(1, pesquisa);
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                            + "cod_produto, tipo_produto, cod_cliente,"
                            + "tipo_cliente, data_emissao,"
                            + "data_entrega, status"
                            + " FROM tabela_ordens_producao WHERE orcamento_base =  ?");
                    stmt.setInt(1, Integer.valueOf(pesquisa));
                    break;
                case 3:
                    produto = true;
                    List codProd = ProdutoDAO.retornaCodigosProdutos(pesquisa);
                    for (int i = 0; i < codProd.size(); i++) {
                        stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                                + "cod_produto, tipo_produto, cod_cliente,"
                                + "tipo_cliente, data_emissao,"
                                + "data_entrega, status"
                                + " FROM tabela_ordens_producao WHERE cod_produto = ? ORDER BY cod DESC");
                        stmt.setInt(1, Integer.parseInt(codProd.get(i).toString()));
                        rs = stmt.executeQuery();
                        while (rs.next()) {
                            if (rs.getInt("cod") >= 364) {
                                OrdemProducao oBEAN = new OrdemProducao();
                                oBEAN.setCodigo(rs.getInt("cod"));
                                oBEAN.setOrcBase(rs.getInt("orcamento_base"));
                                oBEAN.setCodProduto(rs.getInt("cod_produto"));
                                oBEAN.setTipoProduto(rs.getByte("tipo_produto"));
                                oBEAN.setCodCliente(rs.getInt("cod_cliente"));
                                oBEAN.setTipoPessoa(rs.getByte("tipo_cliente"));
                                oBEAN.setDataEmissao(rs.getDate("data_emissao"));
                                oBEAN.setDataEntrega(rs.getDate("data_entrega"));
                                oBEAN.setStatus(rs.getString("status"));
                                retorno.add(oBEAN);
                            }
                        }
                    }
                    break;
                case 4:
                    if (tipoPesqCliente.contains("CÓDIGO")) {
                        retornoCliente.add(pesquisa);
                    } else {
                        retornoCliente = ClienteDAO.retornaCodCliente(tipoPesqCliente, pesquisa);
                    }
                    cliente = true;
                    break;
                case 5:
                    stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                            + "cod_produto, tipo_produto, cod_cliente,"
                            + "tipo_cliente, data_emissao,"
                            + "data_entrega, status"
                            + " FROM tabela_ordens_producao WHERE data_emissao =  ?");
                    stmt.setDate(1, new java.sql.Date(data.getTime()));
                    break;
                case 6:
                    stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                            + "cod_produto, tipo_produto, cod_cliente,"
                            + "tipo_cliente, data_emissao,"
                            + "data_entrega, status"
                            + " FROM tabela_ordens_producao WHERE data_entrega = ?");
                    stmt.setDate(1, new java.sql.Date(data.getTime()));
                    break;
                case 7:
                    stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                            + "cod_produto, tipo_produto, cod_cliente,"
                            + "tipo_cliente, data_emissao,"
                            + "data_entrega, status"
                            + " FROM tabela_ordens_producao WHERE status = ?");
                    stmt.setString(1, tipoPesqCliente);
                    break;
            }

            if (cliente == true && produto == false) {
                for (int i = 0; i < retornoCliente.size(); i++) {
                    if (tipoPesqCliente.contains("FÍSICA")) {
                        stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                                + "cod_produto, tipo_produto, cod_cliente,"
                                + "tipo_cliente, data_emissao,"
                                + "data_entrega, status"
                                + " FROM tabela_ordens_producao WHERE cod_cliente = ? AND tipo_cliente = 1 ORDER BY cod ASC");
                        stmt.setInt(1, Integer.valueOf(retornoCliente.get(i).toString()));
                    }
                    if (tipoPesqCliente.contains("JURÍDICA")) {
                        stmt = con.prepareStatement("SELECT cod, orcamento_base,"
                                + "cod_produto, tipo_produto, cod_cliente,"
                                + "tipo_cliente, data_emissao,"
                                + "data_entrega, status"
                                + " FROM tabela_ordens_producao WHERE cod_cliente = ? AND tipo_cliente = 2 ORDER BY cod ASC");
                        stmt.setInt(1, Integer.valueOf(retornoCliente.get(i).toString()));
                    }
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        OrdemProducao oBEAN = new OrdemProducao();
                        oBEAN.setCodigo(rs.getInt("cod"));
                        oBEAN.setOrcBase(rs.getInt("orcamento_base"));
                        oBEAN.setCodProduto(rs.getInt("cod_produto"));
                        oBEAN.setTipoProduto(rs.getByte("tipo_produto"));
                        oBEAN.setCodCliente(rs.getInt("cod_cliente"));
                        oBEAN.setTipoPessoa(rs.getByte("tipo_cliente"));
                        oBEAN.setDataEmissao(rs.getDate("data_emissao"));
                        oBEAN.setDataEntrega(rs.getDate("data_entrega"));
                        oBEAN.setStatus(rs.getString("status"));
                        retorno.add(oBEAN);
                    }
                }
            }
            if (cliente == false && produto == false) {
                rs = stmt.executeQuery();
                while (rs.next()) {
                    OrdemProducao oBEAN = new OrdemProducao();
                    oBEAN.setCodigo(rs.getInt("cod"));
                    oBEAN.setOrcBase(rs.getInt("orcamento_base"));
                    oBEAN.setCodProduto(rs.getInt("cod_produto"));
                    oBEAN.setTipoProduto(rs.getByte("tipo_produto"));
                    oBEAN.setCodCliente(rs.getInt("cod_cliente"));
                    oBEAN.setTipoPessoa(rs.getByte("tipo_cliente"));
                    oBEAN.setDataEmissao(rs.getDate("data_emissao"));
                    oBEAN.setDataEntrega(rs.getDate("data_entrega"));
                    oBEAN.setStatus(rs.getString("status"));
                    retorno.add(oBEAN);
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
     * Retorna o código do cliente atrelado à OP
     *
     * @param codOp
     * @return
     * @throws SQLException
     */
    public static int retornaCodCliente(int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod_cliente "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod_cliente");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Retorna uma lista de códigos de OP através de uma lista de códigos de clientes
     * @param clientes lista de códigos de clientes
     * @return
     * @throws SQLException 
     */
    public static List<Integer> retornaCodOpCliente(List<ClienteBEAN> clientes) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> retorno = new ArrayList();

        try {
            for (ClienteBEAN cliente : clientes) {
                stmt = con.prepareStatement("SELECT cod "
                        + "FROM tabela_ordens_producao "
                        + "WHERE cod_cliente = ? AND tipo_cliente = ?");
                stmt.setInt(1,cliente.getCod());
                stmt.setInt(2, cliente.getTipoCliente());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    retorno.add(rs.getInt("cod"));
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
     * Altera o status da ordem de produção
     * @param status novo status da op
     * @param codOp código da ordem de produção
     * @throws SQLException 
     */
    public static void alteraStatus(String status, int codOp) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_ordens_producao "
                    + "SET status = ? "
                    + "WHERE cod = ?");
            stmt.setString(1, status);
            stmt.setInt(2, codOp);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Verifica se existe mais uma OP no orçamento, e, se existir, se foram entregues
     * @param codOrc código do orçamento
     * @param codOp código da OP
     * @return
     * @throws SQLException 
     */
    public static boolean verificaOpOrcEntregues(int codOrc, int codOp) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod != ? "
                    + "AND orcamento_base = ? "
                    + "AND status != 'ENTREGUE' "
                    + "AND status != 'CANCELADA'");
            stmt.setInt(1, codOp);
            stmt.setInt(2, codOrc);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Verifica se existe mais uma OP no orçamento
     * @param codOrc código do orçamento
     * @param codOp código da OP
     * @return
     * @throws SQLException 
     */
    public static boolean verificaOpOrcNEntregues(int codOrc, int codOp) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT cod "
                    + "FROM tabela_ordens_producao "
                    + "WHERE cod != ? "
                    + "AND orcamento_base = ? "
                    + "AND status != 'CANCELADA'");
            stmt.setInt(1, codOp);
            stmt.setInt(2, codOrc);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Retorna o ano da ordem de produção
     * @param codOp código da ordem de produção
     * @return
     * @throws SQLException 
     */
    public static int retornaAnoOp(int codOp) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("SELECT YEAR(tabela_ordens_producao.data_emissao) AS ano "
                    + "FROM tabela_ordens_producao "
                    + "WHERE tabela_ordens_producao.cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("ano");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    public static byte retornaTipoProduto(int codOp) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("SELECT tabela_ordens_producao.tipo_produto "
                    + "FROM tabela_ordens_producao "
                    + "WHERE tabela_ordens_producao.cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getByte("tabela_ordens_producao.tipo_produto");
            }
            return 0;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Retorna as OP associadas a proposta de orçamento
     * @param codProp código da proposta
     * @return
     * @throws SQLException 
     */
    public static String retornaOpsAssociadas(int codProp) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        StringBuilder retorno = new StringBuilder();
        
        try{
            stmt = con.prepareStatement("SELECT tabela_ordens_producao.cod "
                    + "FROM tabela_ordens_producao "
                    + "WHERE tabela_ordens_producao.orcamento_base = ?");
            stmt.setInt(1, codProp);
            rs = stmt.executeQuery();
            while(rs.next()){
                retorno.append(rs.getString("tabela_ordens_producao.cod"));
                retorno.append("\n");
            }
            return retorno.toString();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Retorna a data de entrega da ordem de produção
     * @param codOp
     * @return 
     */
    public static Date retornaDataEntregaOp(int codOp) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("SELECT tabela_ordens_producao.data_entrega "
                    + "FROM tabela_ordens_producao "
                    + "WHERE tabela_ordens_producao.cod = ?");
            stmt.setInt(1, codOp);
            rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getDate("tabela_ordens_producao.data_entrega");
            }
            return null;            
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Salva a alteração de data da OP
     * @param alteraData 
     */
    public static void salvaAlteracaoData (AlteraData alteraData) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("INSERT INTO alteracoes_ordem_producao(OP, ALTERACAO, DATA ANTERIOR, USUARIO) "
                    + "VALUES(?,?,?,?)");
            stmt.setInt(1, alteraData.getCodigoOp());
            stmt.setTimestamp(2, alteraData.getAlteracao());
            stmt.setDate(3, new java.sql.Date(alteraData.getDataAnterior().getTime()));
            stmt.setString(4, alteraData.getUsuario());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
