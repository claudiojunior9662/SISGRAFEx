/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cadastros.produtos;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author claud
 */
public class ProdutoPrEntBEAN {

    private int codigo;
    private String descricao;
    private float largura;
    private float altura;
    private float espessura;
    private float peso;
    private byte preVenda;
    private byte promocao;
    private double vlrPromocao;
    private Date inicioPromocao;
    private Date fimPromocao;
    private int qtdPaginas;
    private int estoque;
    private byte avisoEstoque;
    private int avisoEstoqueUn;
    private String tipo;
    private double vlrUnit;
    private Timestamp ultMov;
    private int pdQtdMin;
    private byte pdMax;
    private int pdQtdMax;
    private boolean ativo;
    private boolean utilizadoEcommerce;

    public ProdutoPrEntBEAN() {
    }

    public ProdutoPrEntBEAN(int codigo, String descricao, byte preVenda, byte promocao,
            int estoque, byte avisoEstoque, double vlrUnit) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preVenda = preVenda;
        this.promocao = promocao;
        this.estoque = estoque;
        this.avisoEstoque = avisoEstoque;
        this.vlrUnit = vlrUnit;
    }

    public ProdutoPrEntBEAN(int codigo, String descricao, float largura, float altura, float espessura,
            float peso, byte preVenda, byte promocao, double vlrPromocao, Date inicioPromocao,
            Date fimPromocao, int qtdPaginas, int estoque, byte avisoEstoque, int avisoEstoqueUn, String tipo,
            double vlrUnit, Timestamp ultMov, int pdQtdMin, byte pdMax, int pdQtdMax, boolean ativo, boolean utilizadoEcommerce) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.largura = largura;
        this.altura = altura;
        this.espessura = espessura;
        this.peso = peso;
        this.preVenda = preVenda;
        this.promocao = promocao;
        this.vlrPromocao = vlrPromocao;
        this.inicioPromocao = inicioPromocao;
        this.fimPromocao = fimPromocao;
        this.qtdPaginas = qtdPaginas;
        this.estoque = estoque;
        this.avisoEstoque = avisoEstoque;
        this.avisoEstoqueUn = avisoEstoqueUn;
        this.tipo = tipo;
        this.vlrUnit = vlrUnit;
        this.ultMov = ultMov;
        this.pdQtdMin = pdQtdMin;
        this.pdMax = pdMax;
        this.pdQtdMax = pdQtdMax;
        this.ativo = ativo;
        this.utilizadoEcommerce = utilizadoEcommerce;
    }

    public ProdutoPrEntBEAN(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public ProdutoPrEntBEAN(String descricao, float largura, float altura, int qtdPaginas, int estoque, double vlrUnit) {
        this.descricao = descricao;
        this.largura = largura;
        this.altura = altura;
        this.qtdPaginas = qtdPaginas;
        this.estoque = estoque;
        this.vlrUnit = vlrUnit;
    }

    public ProdutoPrEntBEAN(int codigo, String descricao, int estoque) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.estoque = estoque;
    }

    public ProdutoPrEntBEAN(int codigo,
            int estoque,
            byte avisoEstoque,
            int avisoEstoqueUn,
            double vlrUnit
    ) {
        this.codigo = codigo;
        this.estoque = estoque;
        this.avisoEstoque = avisoEstoque;
        this.avisoEstoqueUn = avisoEstoqueUn;
        this.vlrUnit = vlrUnit;
    }

    public ProdutoPrEntBEAN(float altura,
            float largura,
            float espessura,
            float peso) {
        this.altura = altura;
        this.largura = largura;
        this.espessura = espessura;
        this.peso = peso;
    }

    public boolean isUtilizadoEcommerce() {
        return utilizadoEcommerce;
    }

    public void setUtilizadoEcommerce(boolean utilizadoEcommerce) {
        this.utilizadoEcommerce = utilizadoEcommerce;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getEspessura() {
        return espessura;
    }

    public void setEspessura(float espessura) {
        this.espessura = espessura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public byte getPreVenda() {
        return preVenda;
    }

    public void setPreVenda(byte preVenda) {
        this.preVenda = preVenda;
    }

    public byte getPromocao() {
        return promocao;
    }

    public void setPromocao(byte promocao) {
        this.promocao = promocao;
    }

    public double getVlrPromocao() {
        return vlrPromocao;
    }

    public void setVlrPromocao(double vlrPromocao) {
        this.vlrPromocao = vlrPromocao;
    }

    public Date getInicioPromocao() {
        return inicioPromocao;
    }

    public void setInicioPromocao(Date inicioPromocao) {
        this.inicioPromocao = inicioPromocao;
    }

    public Date getFimPromocao() {
        return fimPromocao;
    }

    public void setFimPromocao(Date fimPromocao) {
        this.fimPromocao = fimPromocao;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(int qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public byte getAvisoEstoque() {
        return avisoEstoque;
    }

    public void setAvisoEstoque(byte avisoEstoque) {
        this.avisoEstoque = avisoEstoque;
    }

    public int getAvisoEstoqueUn() {
        return avisoEstoqueUn;
    }

    public void setAvisoEstoqueUn(int avisoEstoqueUn) {
        this.avisoEstoqueUn = avisoEstoqueUn;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    public Timestamp getUltMov() {
        return ultMov;
    }

    public void setUltMov(Timestamp ultMov) {
        this.ultMov = ultMov;
    }

    public int getPdQtdMin() {
        return pdQtdMin;
    }

    public void setPdQtdMin(int pdQtdMin) {
        this.pdQtdMin = pdQtdMin;
    }

    public byte getPdMax() {
        return pdMax;
    }

    public void setPdMax(byte pdMax) {
        this.pdMax = pdMax;
    }

    public int getPdQtdMax() {
        return pdQtdMax;
    }

    public void setPdQtdMaxUn(int pdQtdMaxUn) {
        this.pdQtdMax = pdQtdMaxUn;
    }

}
