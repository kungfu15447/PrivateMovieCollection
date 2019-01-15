/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Frederik Jensen
 */
public class Category
{
    private int id;
    private String category;
    private CheckBox select;
    
    public Category(int id, String category) {
        this.id = id;
        this.category = category;
        this.select = new CheckBox();
    }
    
    /**
     * returns id
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns category
     */
    public String getCategory()
    {
        return category;
    }
    /**
     * sets category
     */
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    /**
     * returns selected checkbox
     */
    public CheckBox getSelect() {
        return select;
    }
    
    /**
     * selects checkbox
     */
    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
    @Override
    public String toString() {
        return category;
    }
    
    
}
