/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.lojaIntegrada;

import entities.sisgrafex.Cliente;
import entities.sisgrafex.Contato;
import entities.sisgrafex.Endereco;
import entities.sisgrafex.Orcamento;
import entities.sisgrafex.ProdOrcamento;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Claudio Júnior
 */
public class Order {

    //Informações Gerais do pedido----------------------------------------------
    private String clienteResource;
    private Timestamp dataCriacao;
    private Timestamp dataExpiracao;
    private Timestamp dataAtualizacao;
    private int numero;
    private boolean aprovado;
    private boolean cancelado;
    private int situacao;
    private String situacaoDescricao;
    private double subtotal;
    private double total;
    private int idExterno;

    //Informações específicas do pedido-----------------------------------------
    private Cliente cliente;
    private Contato contato;
    private Endereco endereco;
    private List<ProdOrcamento> produtos;
    private Orcamento orcamento;

    @Override
    public String toString() {
        return "Order{" + "clienteResource=" + clienteResource + ", dataCriacao=" + dataCriacao + ", dataExpiracao=" + dataExpiracao + ", dataAtualizacao=" + dataAtualizacao + ", numero=" + numero + ", aprovado=" + aprovado + ", cancelado=" + cancelado + ", situacao=" + situacao + ", situacaoDescricao=" + situacaoDescricao + ", subtotal=" + subtotal + ", total=" + total + ", cliente=" + cliente + ", contato=" + contato + ", endereco=" + endereco + ", produtos=" + produtos + '}';
    }

    public Order(String cliente,
            Timestamp dataCriacao,
            Timestamp dataExpiracao,
            Timestamp dataAtualizacao,
            int numero,
            int idExterno,
            boolean aprovado,
            boolean cancelado,
            int situacao,
            String situacaoDescricao,
            double subtotal,
            double total) {
        this.clienteResource = cliente;
        this.dataCriacao = dataCriacao;
        this.dataExpiracao = dataExpiracao;
        this.dataAtualizacao = dataAtualizacao;
        this.numero = numero;
        this.idExterno = idExterno;
        this.aprovado = aprovado;
        this.cancelado = cancelado;
        this.situacao = situacao;
        this.situacaoDescricao = situacaoDescricao;
        this.subtotal = subtotal;
        this.total = total;
    }

    public Order(Cliente cliente,
            Contato contato,
            Endereco endereco,
            Orcamento orcamento,
            List<ProdOrcamento> produtos) {
        this.cliente = cliente;
        this.contato = contato;
        this.endereco = endereco;
        this.orcamento = orcamento;
        this.produtos = produtos;
    }

    public int getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(int idExterno) {
        this.idExterno = idExterno;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<ProdOrcamento> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdOrcamento> produtos) {
        this.produtos = produtos;
    }

    public String getClienteResource() {
        return clienteResource;
    }

    public void setClienteResource(String clienteResource) {
        this.clienteResource = clienteResource;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Timestamp getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Timestamp dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Timestamp getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Timestamp dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public String getSituacaoDescricao() {
        return situacaoDescricao;
    }

    public void setSituacaoDescricao(String situacaoDescricao) {
        this.situacaoDescricao = situacaoDescricao;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
