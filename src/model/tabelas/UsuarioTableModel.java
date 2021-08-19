/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tabelas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entities.sisgrafex.Usuario;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author claud
 */
public class UsuarioTableModel extends AbstractTableModel{

    private List<Usuario> dados = new ArrayList<>();
    private final String[] colunas = {"NOME", "COD", "LOGIN", "TIPO", "ORC", "PROD", "EXP", "FIN", "EST", "ATIVO", "ULT LOGIN"};
    
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
    public void fireTableChanged(TableModelEvent e) {
        super.fireTableChanged(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableStructureChanged() {
        super.fireTableStructureChanged(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public Usuario getValueAt(int linha){
        return dados.get(linha);
    }
    
    public void addRow(Usuario usr){
        this.dados.add(usr);
        this.fireTableDataChanged();
    }
    
    public void setNumRows(int nRows){
        this.dados.clear();
        this.fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case 0:
                return dados.get(linha).getNome();
            case 1:
                return dados.get(linha).getCodigo();
            case 2: 
                return dados.get(linha).getLogin();
            case 3:
                return dados.get(linha).getTipo();
            case 4:
                return dados.get(linha).getAcessoOrc();
            case 5:
                return dados.get(linha).getAcessoProd();
            case 6:
                return dados.get(linha).getAcessoExp();
            case 7:
                return dados.get(linha).getAcessoFin();
            case 8:
                return dados.get(linha).getAcessoEst();
            case 9:
                return dados.get(linha).getAtivo();
            case 10:
                return dados.get(linha).getUltLogin();
        }
        return null;
    }
    
}
