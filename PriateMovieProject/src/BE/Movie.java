/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class Movie
{
    private int id;
    private String name;
    private double rating;
    private String filepath;
    private int lastview;
    private List<Integer> categoryIdList;
    
    public Movie(int id, String name, double rating, String filepath, int lastview) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filepath = filepath;
        this.lastview = lastview;
        categoryIdList = new ArrayList<>();
    }

    /**
     * returns id
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns name
     */
    public String getName()
    {
        return name;
    }

    /**
     * returns rating
     */
    public double getRating()
    {
        return rating;
    }
    
    /**
     * returns filepath
     */
    public String getFilepath()
    {
        return filepath;
    }

    /**
     * returns lastview
     */
    public int getLastview()
    {
        return lastview;
    }

    /**
     * sets name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * sets rating
     */
    public void setRating(double rating)
    {
        this.rating = rating;
    }

    /**
     * sets filepath
     */
    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }
    
    /**
     * sets lastview
     */
    public void setLastview(int lastview)
    {
        this.lastview = lastview;
    }
    
    /**
     * returns categoryIdList
     */
    public List<Integer> getList() {
        return categoryIdList;
    }
}
