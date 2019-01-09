/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Frederik Jensen
 */
public class Category
{
    private int id;
    private String category;
    
    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId()
    {
        return id;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return category;
    }
    
    
}
