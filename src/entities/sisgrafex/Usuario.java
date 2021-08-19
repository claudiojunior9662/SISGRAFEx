/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author spd3
 */
public class Usuario {

    private String codigoAtendente;
    private String loginAtendente;
    private String nomeAtendente;
    private String senhaAtendente;
    private String tipoAtendente;
    private Date ultMudSenha;
    private byte acessoOrc;
    private byte acessoOrcAdm;
    private byte acessoProd;
    private byte acessoProdAdm;
    private byte acessoExp;
    private byte acessoExpAdm;
    private byte acessoFin;
    private byte acessoFinAdm;
    private byte acessoEst;
    private byte acessoOrd;
    private byte ativo;
    private java.sql.Timestamp ultLogin;

    public Usuario(String codigoAtendente,
            String loginAtendente,
            String nomeAtendente,
            String tipoAtendente) {
        this.codigoAtendente = codigoAtendente;
        this.loginAtendente = loginAtendente;
        this.nomeAtendente = nomeAtendente;
        this.tipoAtendente = tipoAtendente;
    }

    public Usuario(String nomeAtendente,
            String codigoAtendente,
            String loginAtendente,
            String tipoAtendente,
            byte acessoOrc,
            byte acessoOrcAdm,
            byte acessoProd,
            byte acessoProdAdm,
            byte acessoExp,
            byte acessoExpAdm,
            byte acessoFin,
            byte acessoFinAdm,
            byte acessoEst,
            byte acessoOrd,
            byte ativo,
            java.sql.Timestamp ultLogin) {
        this.nomeAtendente = nomeAtendente;
        this.codigoAtendente = codigoAtendente;
        this.loginAtendente = loginAtendente;
        this.tipoAtendente = tipoAtendente;
        this.acessoOrc = acessoOrc;
        this.acessoOrcAdm = acessoOrcAdm;
        this.acessoProd = acessoProd;
        this.acessoProdAdm = acessoProdAdm;
        this.acessoExp = acessoExp;
        this.acessoExpAdm = acessoExpAdm;
        this.acessoFin = acessoFin;
        this.acessoFinAdm = acessoFinAdm;
        this.acessoEst = acessoEst;
        this.acessoOrd = acessoOrd;
        this.ativo = ativo;
        this.ultLogin = ultLogin;
    }

    public Usuario(String codigoAtendente, String nomeAtendente) {
        this.codigoAtendente = codigoAtendente;
        this.nomeAtendente = nomeAtendente;
    }

    public Usuario(String codigoAtendente) {
        this.codigoAtendente = codigoAtendente;
    }

    public Usuario() {

    }

    @Override
    public String toString() {
        return codigoAtendente + " - " + nomeAtendente;
    }

    public Timestamp getUltLogin() {
        return ultLogin;
    }

    public void setUltLogin(Timestamp ultLogin) {
        this.ultLogin = ultLogin;
    }

    public Date getUltMudSenha() {
        return ultMudSenha;
    }

    public void setUltMudSenha(Date ultMudSenha) {
        this.ultMudSenha = ultMudSenha;
    }

    public byte getAcessoOrcAdm() {
        return acessoOrcAdm;
    }

    public void setAcessoOrcAdm(byte acessoOrcAdm) {
        this.acessoOrcAdm = acessoOrcAdm;
    }

    public byte getAcessoProdAdm() {
        return acessoProdAdm;
    }

    public void setAcessoProdAdm(byte acessoProdAdm) {
        this.acessoProdAdm = acessoProdAdm;
    }

    public byte getAcessoExpAdm() {
        return acessoExpAdm;
    }

    public void setAcessoExpAdm(byte acessoExpAdm) {
        this.acessoExpAdm = acessoExpAdm;
    }

    public byte getAcessoFinAdm() {
        return acessoFinAdm;
    }

    public void setAcessoFinAdm(byte acessoFinAdm) {
        this.acessoFinAdm = acessoFinAdm;
    }

    public byte getAcessoOrd() {
        return acessoOrd;
    }

    public void setAcessoOrd(byte acessoOrd) {
        this.acessoOrd = acessoOrd;
    }

    public byte getAcessoOrc() {
        return acessoOrc;
    }

    public void setAcessoOrc(byte acessoOrc) {
        this.acessoOrc = acessoOrc;
    }

    public byte getAcessoProd() {
        return acessoProd;
    }

    public void setAcessoProd(byte acessoProd) {
        this.acessoProd = acessoProd;
    }

    public byte getAcessoExp() {
        return acessoExp;
    }

    public void setAcessoExp(byte acessoExp) {
        this.acessoExp = acessoExp;
    }

    public byte getAcessoFin() {
        return acessoFin;
    }

    public void setAcessoFin(byte acessoFin) {
        this.acessoFin = acessoFin;
    }

    public byte getAcessoEst() {
        return acessoEst;
    }

    public void setAcessoEst(byte acessoEst) {
        this.acessoEst = acessoEst;
    }

    public byte getAtivo() {
        return ativo;
    }

    public void setAtivo(byte ativo) {
        this.ativo = ativo;
    }

    public String getCodigo() {
        return codigoAtendente;
    }

    public void setCodigo(String codigoAtendente) {
        this.codigoAtendente = codigoAtendente;
    }

    public String getLogin() {
        return loginAtendente;
    }

    public void setLogin(String login_atendente) {
        this.loginAtendente = login_atendente;
    }

    public String getNome() {
        return nomeAtendente;
    }

    public void setNome(String nome_atendente) {
        this.nomeAtendente = nome_atendente;
    }

    public String getSenhaAtendente() {
        return senhaAtendente;
    }

    public void setSenha(String senha_atendente) {
        this.senhaAtendente = senha_atendente;
    }

    public String getTipo() {
        return tipoAtendente;
    }

    public void setTipo(String tipo_atendente) {
        this.tipoAtendente = tipo_atendente;
    }

}
