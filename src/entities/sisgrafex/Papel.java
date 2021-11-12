/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

import exception.LimiteContadorException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import model.dao.PapelDAO;

/**
 *
 * @author spd3
 */
public class Papel {

    private int cod;
    private int codOrcamento;
    private String descricao;
    private String medida;
    private int gramatura;
    private String formato;
    private int umaFace;
    private double unitario;
    private String tipo;
    private int coresFrente;
    private int coresVerso;
    private int codProduto;
    private int qtdGasta;
    //CONTADOR DE CLIQUES-------------------------------------------------------
    private int valorAtual;
    private int valorLimite;
    private java.sql.Timestamp alteracao;

    public Papel() {

    }

    public Papel(Integer codPapel,
            String tipo,
            String descricao) {
        this.cod = codPapel;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Papel(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Papel(int cod) {
        this.cod = cod;
    }

    public Papel(int valorAtual, 
            int valorLimite, 
            java.sql.Timestamp alteracao) {
        this.valorAtual = valorAtual;
        this.valorLimite = valorLimite;
        this.alteracao = alteracao;
    }
    
    public Papel(int coresFrente, 
            int coresVerso, 
            int qtdGasta){
        this.coresFrente = coresFrente;
        this.coresVerso = coresVerso;
        this.qtdGasta = qtdGasta;
    }
    
    public int getCoresFrente() {
        return coresFrente;
    }
    
    public void setCoresFrente(int coresFrente) {
        this.coresFrente = coresFrente;
    }
    
    public int getCoresVerso() {
        return coresVerso;
    }
    
    public void setCoresVerso(int coresVerso) {
        this.coresVerso = coresFrente;
    }
    
    public int getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(int valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Timestamp getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Timestamp alteracao) {
        this.alteracao = alteracao;
    }

    public int getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(int valorLimite) {
        this.valorLimite = valorLimite;
    }

    public int getQtdGasta() {
        return qtdGasta;
    }

    public void setQtdGasta(int qtdGasta) {
        this.qtdGasta = qtdGasta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getGramatura() {
        return gramatura;
    }

    public void setGramatura(int gramatura) {
        this.gramatura = gramatura;
    }

    public double getUnitario() {
        return unitario;
    }

    public void setUnitario(double unitario) {
        this.unitario = unitario;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getUma_face() {
        return umaFace;
    }

    public void setUma_face(int uma_face) {
        this.umaFace = uma_face;
    }

    
    
    /**
     * Realiza o cálculo de cliques que serão computados na digital
     *
     * @param formato formato do papel
     * @param qtdPapeis quantidade gasta de papéis para o serviço
     * @param coresFrente quantidade de cores a serem gastas na frente
     * @param coresVerso quantidade de cores a serem gastas no verso
     * @throws SQLException
     */
    public synchronized static void contaCliques(Papel papel) throws SQLException, LimiteContadorException {
        int cliques = 0;

        if (papel.getCoresFrente() > 0) {
            cliques = 2;
            if (papel.getCoresVerso() > 0) {
                cliques = 4;
            }
        }

        cliques = cliques * (papel.getQtdGasta() * 8);

        Papel contador = PapelDAO.retornaContadorCliques();
        Calendar dataAnterior = Calendar.getInstance();
        dataAnterior.setTime(Date.from(contador.getAlteracao().toInstant()));
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(new Date());

        if (dataAnterior.get(Calendar.MONTH) < dataAtual.get(Calendar.MONTH)) {
            if (cliques > contador.getValorLimite()) {
                throw new LimiteContadorException();
            } else {
                PapelDAO.atualizaContadorCliques(new Papel(
                        cliques,
                        contador.getValorLimite(),
                        new java.sql.Timestamp(dataAtual.getTimeInMillis())
                ));
            }
        } else {
            cliques = cliques + contador.getValorAtual();
            if (cliques > contador.getValorLimite()) {
                throw new LimiteContadorException();
            } else {
                PapelDAO.atualizaContadorCliques(new Papel(
                        cliques,
                        contador.getValorLimite(),
                        new java.sql.Timestamp(dataAtual.getTimeInMillis())
                ));
            }
        }
    }

}
