/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tabelas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ui.sproducao.controle.observacoes.ObservacaoBEAN;

/**
 *
 * @author claud
 */
public class ObsOpTableModel extends AbstractTableModel {

    private List<ObservacaoBEAN> dados = new ArrayList<>();
    private String[] colunas = {"OP", "DATA", "OBSERVAÇÃO"};

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).getCodigoOp();
            case 1:
                return dados.get(linha).getData();
            case 2:
                return dados.get(linha).getObservacao();
        }
        return null;
    }

    public ObservacaoBEAN getValueAt(int linha) {
        return dados.get(linha);
    }

    public void addRow(ObservacaoBEAN obs) {
        this.dados.add(obs);
        this.fireTableDataChanged();
    }

    public void setNumRows(int nRows) {
        this.dados.clear();
        this.fireTableDataChanged();
    }

}
