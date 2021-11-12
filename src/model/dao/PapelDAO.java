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
import entities.sisgrafex.Papel;
import java.util.Date;
import ui.cadastros.papeis.PapelBEAN;

/**
 *
 * @author spd3
 */
public class PapelDAO {
    
    public static void create(Papel pcBEAN) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int face;
        
        try {
            stmt = con.prepareStatement("INSERT INTO tabela_papeis(descricao, medida, gramatura, formato, uma_face, unitario) VALUES(?,?,?,?,?,?)");
            stmt.setString(1, pcBEAN.getDescricao());
            stmt.setString(2, pcBEAN.getMedida());
            stmt.setInt(3, (int) pcBEAN.getGramatura());
            stmt.setString(4, pcBEAN.getFormato());
            stmt.setInt(5, pcBEAN.getUma_face());
            stmt.setDouble(6, pcBEAN.getUnitario());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<Papel> mostraTodos() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Papel> cadastropc = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_papeis");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Papel pc = new Papel();
                pc.setCod(rs.getInt("cod"));
                pc.setDescricao(rs.getString("descricao"));
                pc.setMedida(rs.getString("medida"));
                pc.setGramatura(rs.getInt("gramatura"));
                pc.setFormato(rs.getString("formato"));
                pc.setUma_face(rs.getInt("uma_face"));
                pc.setUnitario(rs.getFloat("unitario"));
                cadastropc.add(pc);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println("Não foi possível atualizar a tabela! " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return cadastropc;
    }
    
    public static List<Papel> pesquisaRegistro(String tipo, String pesquisa) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Papel> retorno = new ArrayList<>();
        
        try {
            
            if (tipo == "Código") {
                stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE cod  = " + pesquisa);
            } else if (tipo == "Descrição") {
                stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE descricao LIKE " + "'%" + pesquisa + "%'");
            } else if (tipo == "Medida") {
                stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE medida LIKE '" + "'%" + pesquisa + "%'");
                stmt.setString(1, pesquisa);
            } else if (tipo == "Gramatura") {
                stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE gramatura = " + pesquisa);
            } else if (tipo == "Formato") {
                stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE formato LIKE " + "'%" + pesquisa + "%'");
                stmt.setString(1, pesquisa);
            } else if (tipo == "Valor Unitário") {
                stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE unitario LIKE " + "'%" + pesquisa + "%'");
                stmt.setString(1, pesquisa);
            }
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Papel papel = new Papel();
                papel.setCod(rs.getInt("cod"));
                papel.setDescricao(rs.getString("descricao"));
                papel.setMedida(rs.getString("medida"));
                papel.setGramatura(rs.getInt("gramatura"));
                papel.setFormato(rs.getString("formato"));
                papel.setUma_face(rs.getInt("uma_face"));
                papel.setUnitario(rs.getFloat("unitario"));
                retorno.add(papel);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }
    
    public void excluir_registro(int cod) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM tabela_papeis WHERE cod = ?");
            stmt.setInt(1, cod);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir registro" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<Papel> carregar_edicao(int cod) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Papel> cadastropc = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE cod = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Papel pc = new Papel();
                pc.setCod(rs.getInt("cod"));
                pc.setDescricao(rs.getString("descricao"));
                pc.setMedida(rs.getString("medida"));
                pc.setGramatura(rs.getInt("gramatura"));
                pc.setFormato(rs.getString("formato"));
                pc.setUma_face(rs.getInt("uma_face"));
                pc.setUnitario(rs.getDouble("unitario"));
                cadastropc.add(pc);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println("Não foi possível atualizar a tabela! " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return cadastropc;
    }
    
    public void altera_registro(int cod, Papel pcBEAN) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE tabela_papeis SET  descricao = ?, medida = ?, gramatura = ?, formato = ?, uma_face = ?, unitario = ? WHERE cod = ?");
            stmt.setString(1, pcBEAN.getDescricao());
            stmt.setString(2, pcBEAN.getMedida());
            stmt.setInt(3, pcBEAN.getGramatura());
            stmt.setString(4, pcBEAN.getFormato());
            stmt.setInt(5, pcBEAN.getUma_face());
            stmt.setDouble(6, pcBEAN.getUnitario());
            stmt.setInt(7, cod);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar registro" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<Papel> carregaOrcamento(int cod) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Papel> pclBEAN = new ArrayList();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_papeis WHERE cod = ?");
            stmt.setInt(1, cod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Papel pc = new Papel();
                pc.setCod(cod);
                pc.setDescricao(rs.getString("descricao"));
                pc.setMedida(rs.getString("medida"));
                pc.setGramatura(rs.getInt("gramatura"));
                pc.setFormato(rs.getString("formato"));
                pc.setUnitario(rs.getFloat("unitario"));
                pclBEAN.add(pc);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao carregar orçamento " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return pclBEAN;
    }
    
    /**
     * Cria papel atrelado ao produto
     * @param papel
     * @throws SQLException 
     */
    public static void criaPplProduto(PapelBEAN papel) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO tabela_papeis_produto(tipo_produto, "
                    + "cod_produto,  "
                    + "cod_papel, "
                    + "tipo_papel, "
                    + "cor_frente, "
                    + "cor_verso, "
                    + "descricao, "
                    + "orelha) "
                    + "VALUES(?,?,?,?,?,?,?,?)");
            stmt.setInt(1, papel.getTipoProduto());
            stmt.setInt(2, papel.getCodProduto());
            stmt.setInt(3, papel.getCodigo());
            stmt.setString(4, papel.getTipoPapel());
            stmt.setInt(5, papel.getCorFrente());
            stmt.setInt(6, papel.getCorVerso());
            stmt.setString(7, papel.getDescricaoPapel());
            stmt.setFloat(8, (float) papel.getOrelha());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Exclui os papéis associados ao produto
     * @param codProd código do produto
     * @param tipoProd tipo de produto 1 - PERSONALIZADO (PP), 2 - PRONTA ENTREGA (PE), 3 - INTERNET (PI)
     * @throws SQLException 
     */
    public static void exluiPapeisProduto(int codProd, byte tipoProd) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE "
                    + "FROM tabela_papeis_produto "
                    + "WHERE cod_produto = ? AND tipo_produto = ?");
            stmt.setInt(1, codProd);
            stmt.setByte(2, tipoProd);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /*
    @param descricao descrição aproximada do produto
    @return list lista de produtos pela descrição aproximada fornecida
    @see retornaPesqRel
     */
    public static List<Papel> retornaPesqRel(String descricao, byte tipo) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Papel> retorno = new ArrayList();
        
        try {
            switch (tipo) {
                case 1:
                    stmt = con.prepareStatement("SELECT cod, descricao "
                            + "FROM tabela_papeis "
                            + "WHERE descricao LIKE '%" + descricao + "%' ORDER BY cod DESC LIMIT 5");
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        retorno.add(new Papel(rs.getInt("cod"), rs.getString("descricao")));
                    }
                    break;
                case 2:
                    stmt = con.prepareStatement("SELECT cod, descricao "
                            + "FROM tabela_papeis "
                            + "WHERE cod = ?");
                    System.out.println(Integer.valueOf(descricao.substring(0, descricao.indexOf("-") - 1)));
                    stmt.setInt(1, Integer.valueOf(descricao.substring(0, descricao.indexOf("-") - 1)));
                    if (rs.next()) {
                        retorno.add(new Papel(rs.getInt("cod"), rs.getString("descricao")));
                    }
                    break;
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
    @param codProd código do produto a ser selecionado os papéis
    @return papel papel selecionado
    @see retornaPapeisOrcamento
     **/
    public static List<Papel> retornaPapeisOrcamento(int codProd) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Papel> retorno = new ArrayList();
        
        try {
            stmt = con.prepareStatement("SELECT cod_papel, tipo_papel, descricao "
                    + "FROM tabela_papeis_produto "
                    + "WHERE cod_produto = ?");
            stmt.setInt(1, codProd);
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno.add(new Papel(
                        rs.getInt("cod_papel"),
                        rs.getString("tipo_papel"),
                        rs.getString("descricao")
                ));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Retorna os papéis atrelados ao produto
     * @param codProduto código do produto
     * @return
     * @throws SQLException 
     */
    public static List<PapelBEAN> carregaPapeisProd(int codProduto) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<PapelBEAN> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * "
                    + "FROM tabela_papeis_produto "
                    + "WHERE cod_produto = ?");
            stmt.setInt(1, codProduto);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PapelBEAN papel = new PapelBEAN();
                papel.setCodProduto(codProduto);
                papel.setCodPapel(rs.getInt("cod_papel"));
                papel.setTipo_papel(rs.getString("tipo_papel"));
                papel.setCor_frente(rs.getInt("cor_frente"));
                papel.setCor_verso(rs.getInt("cor_verso"));
                papel.setDescricaoPapel(rs.getString("descricao"));
                papel.setOrelha(rs.getFloat("orelha"));
                retorno.add(papel);
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Atualiza o contador de clique de impressão digital
     * @param cliques
     * @throws SQLException 
     */
    public static void atualizaContadorCliques(Papel contador) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("UPDATE tabela_contadores "
                    + "SET VALOR_ATUAL = ?, ALTERACAO = ? "
                    + "WHERE COD = 1");
            stmt.setInt(1, contador.getValorAtual());
            stmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            stmt.executeUpdate();
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Retorna a quantidade de cliques armazenados
     * @return quantidade de cliques armazenados
     * @throws SQLException 
     */
    public static Papel retornaContadorCliques() throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = con.prepareStatement("SELECT tabela_contadores.VALOR_ATUAL, tabela_contadores.VALOR_LIMITE, tabela_contadores.ALTERACAO "
                    + "FROM tabela_contadores "
                    + "WHERE COD = 1");
            rs = stmt.executeQuery();
            if(rs.next()){
                return new Papel(
                        rs.getInt("tabela_contadores.VALOR_ATUAL"),
                        rs.getInt("tabela_contadores.VALOR_LIMITE"),
                        rs.getTimestamp("tabela_contadores.ALTERACAO")
                );
            }else{
                return null;
            }
        }catch(SQLException ex){
            throw new SQLException(ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
