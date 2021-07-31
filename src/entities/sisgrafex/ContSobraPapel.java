/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

/**
 *
 * @author auxsecinfor1
 */
public class ContSobraPapel {

    private long sobraPapelAtual;

    public ContSobraPapel(int sobraPapelAtual) {
        this.sobraPapelAtual = sobraPapelAtual;
    }

    public long getSobraPapelAtual() {
        return sobraPapelAtual;
    }

    public void setSobraPapelAtual(long sobraPapelAtual) {
        this.sobraPapelAtual = sobraPapelAtual;
    }

}
