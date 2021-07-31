/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

/**
 *
 * @author claud
 */
public class CalculosOp {

    private int codOp;
    private byte tipoProduto;
    private int codProduto;
    private String tipoPapel;
    private int qtdFolhas;
    private int qtdFolhasTotal;
    private int montagem;
    private int formato;
    private int qtdChapas;
    private float perca;
    private int codigoProposta;
    private int codigoPapel;

    public CalculosOp() {
    }

    public CalculosOp(int qtdFolhasTotal, float perca) {
        this.qtdFolhasTotal = qtdFolhasTotal;
        this.perca = perca;
    }

    public int getCodigoPapel() {
        return codigoPapel;
    }

    public void setCodigoPapel(int codigoPapel) {
        this.codigoPapel = codigoPapel;
    }

    public int getCodigoProposta() {
        return codigoProposta;
    }

    public void setCodigoProposta(int codigoProposta) {
        this.codigoProposta = codigoProposta;
    }

    public float getPerca() {
        return perca;
    }

    public void setPerca(float perca) {
        this.perca = perca;
    }

    public int getQtdChapas() {
        return qtdChapas;
    }

    public void setQtdChapas(int qtdChapas) {
        this.qtdChapas = qtdChapas;
    }

    public String getTipoPapel() {
        return tipoPapel;
    }

    public void setTipoPapel(String tipoPapel) {
        this.tipoPapel = tipoPapel;
    }

    public int getCodOp() {
        return codOp;
    }

    public void setCodOp(int codOp) {
        this.codOp = codOp;
    }

    public byte getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(byte tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getQtdFolhas() {
        return qtdFolhas;
    }

    public void setQtdFolhas(int qtdFolhas) {
        this.qtdFolhas = qtdFolhas;
    }

    public int getQtdFolhasTotal() {
        return qtdFolhasTotal;
    }

    public void setQtdFolhasTotal(int qtdFolhasTotal) {
        this.qtdFolhasTotal = qtdFolhasTotal;
    }

    public int getMontagem() {
        return montagem;
    }

    public void setMontagem(int montagem) {
        this.montagem = montagem;
    }

    public int getFormato() {
        return formato;
    }

    public void setFormato(int formato) {
        this.formato = formato;
    }

}
