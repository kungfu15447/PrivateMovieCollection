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
     * returns the id
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns the category
     * @return category
     */
    public String getCategory()
    {
        return category;
    }
    /**
     * sets the category
     * @param category 
     */
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    /**
     * returns the selected checkbox
     * @return select
     */
    
    public CheckBox getSelect() {
        return select;
    }
    
    /**
     * 
     * @param select 
     */
    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
    /**
     * returns category as a string
     * @return category
     */
    @Override
    public String toString() {
        return category;
    }
    
    
}
