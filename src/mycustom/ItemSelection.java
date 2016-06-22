package mycustom;

import interfaces.ModItemSelection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomaz
 * @param <T>
 */
public class ItemSelection<T extends ModItemSelection> {
    
    private T t;

    public ItemSelection(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return t.value();
    }
 
    public T get() {
        return t;
    }
    
}
