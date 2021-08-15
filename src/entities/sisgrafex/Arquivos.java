/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

import java.io.IOException;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

/**
 *
 * @author Claudio Júnior
 */
public class Arquivos {

    private int op;
    private byte tipo;
    private String diretorio;
    private Timestamp dtMod;
    private String usrMod;

    public Arquivos(int op, byte tipo, String diretorio, Timestamp dtMod, String usrMod) {
        this.op = op;
        this.tipo = tipo;
        this.diretorio = diretorio;
        this.dtMod = dtMod;
        this.usrMod = usrMod;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public Timestamp getDtMod() {
        return dtMod;
    }

    public void setDtMod(Timestamp dtMod) {
        this.dtMod = dtMod;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }
    

}
