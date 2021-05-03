/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tabelas;

import entities.sisgrafex.Orcamento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author claud
 */
public class OrcamentoExtTableModel extends AbstractTableModel{

    private List<Orcamento> dados = new ArrayList<>();
    private String[] colunas = {"PROPOSTA", "DATA EMISSÃO", "DATA VALIDADE", "VALOR TOTAL", "STATUS"};
    
    @Override
    public String getColumnName(int col){
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
        switch(coluna){
            case 0:
                return dados.get(linha).getCod();
            case 1:
                return dados.get(linha).getDataEmissaoString();
            case 2: 
                return dados.get(linha).getDataValidadeString();
            case 3:
                return dados.get(linha).getValorTotal();
            case 4:
                return dados.get(linha).getStatusString();
        }
        return null;
    }
    
    public Orcamento getValueAt(int linha){
        return dados.get(linha);
    }
    
    public void addRow(Orcamento orc){
        this.dados.add(orc);
        this.fireTableDataChanged();
    }
    
    public void setNumRows(int nRows){
        this.dados.clear();
        this.fireTableDataChanged();
    }
    
}
