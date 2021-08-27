/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

import java.sql.Timestamp;

/**
 *
 * @author auxsecinfor1
 */
public class AlteracoesOP {

    private int alteracaoCod;
    private String alteracaoDesc;
    private java.sql.Timestamp dataHora;
    private String usuario;
    private int op;

    public AlteracoesOP(int alteracaoCod, String alteracaoDesc, Timestamp dataHora, String usuario, int op) {
        this.alteracaoCod = alteracaoCod;
        this.alteracaoDesc = alteracaoDesc;
        this.dataHora = dataHora;
        this.usuario = usuario;
        this.op = op;
    }

    public String getAlteracaoDesc() {
        return alteracaoDesc;
    }

    public void setAlteracaoDesc(String alteracaoDesc) {
        this.alteracaoDesc = alteracaoDesc;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getAlteracaoCod() {
        return alteracaoCod;
    }

    public void setAlteracaoCod(int alteracaoCod) {
        this.alteracaoCod = alteracaoCod;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
