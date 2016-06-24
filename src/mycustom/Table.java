/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycustom;

import interfaces.ModJTable;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thomaz
 * @param <T>
 */
public class Table<T extends ModJTable> extends DefaultTableModel {
    
    public Table(List<T> ts, Object... title) {
        for (Object t : title) {
            addColumn(t);
        }
        
        for (T t : ts) {
            addRow(new Object[]{t.getFirst(), t.getSecond(), t.getThird()});
        }   
    }
    
}
