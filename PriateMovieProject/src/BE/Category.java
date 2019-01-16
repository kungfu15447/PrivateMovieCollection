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
     * @return 
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns category
     * @return 
     */
    public String getCategory()
    {
        return category;
    }
    /**
     * sets category
     * @param category
     */
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    /**
     * returns selected checkbox
     * @return 
     */
    public CheckBox getSelect() {
        return select;
    }
    
    /**
     * selects checkbox
     * @param select
     */
    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
    @Override
    public String toString() {
        return category;
    }
    
    
}
