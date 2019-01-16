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
    private final int ID;
    private String name;
    private double rating;
    private double imdbRating;
    private String stringRating;
    private String stringImdbRating;
    private String filepath;
    private int lastview;
    private final List<Integer> categoryIdList;
    
    public Movie(int id, String name, double rating, double imdbRating, String filepath, int lastview) {
        this.ID = id;
        this.name = name;
        this.rating = rating;
        this.filepath = filepath;
        this.lastview = lastview;
        this.imdbRating = imdbRating;
        categoryIdList = new ArrayList<>();
        if (rating == -1) {
            this.stringRating = "No rating given";
        } else {
            this.stringRating = Double.toString(rating);
        }
        if (imdbRating == -1) {
            this.stringImdbRating = "No rating given";
        } else {
            this.stringImdbRating = Double.toString(imdbRating);
        }
    }

    /**

     * returns the id
     * @return ID
     */
    public int getId()
    {
        return ID;
    }

    /**
     * returns the name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * returns the rating
     * @return rating
     */
    public double getRating()
    {
        return rating;
    }
    
    /**
     * returns the filepath
     * @return filepath
     */
    public String getFilepath()
    {
        return filepath;
    }

    /**
     * returns the last viewed time
     * @return lastview
     */
    public int getLastview()
    {
        return lastview;
    }

    /**

     * sets the name
     * @param name 
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * sets the rating
     * @param rating 
     */
    public void setRating(double rating)
    {
        this.rating = rating;
    }

    /**
     * sets the filepath
     * @param filepath 
     */
    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }
    
    /**
     * sets the lastview
     * @param lastview 
     */
    public void setLastview(int lastview)
    {
        this.lastview = lastview;
    }
    
    /**
     * returns categoryIdLis
     * @return categoryIdList
     */
    public List<Integer> getList() {
        return categoryIdList;
    }
    
    public double getImdbRating() {
        return imdbRating;
    }
    
    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }
    
    public String getStringRating() {
        return stringRating;
    }
    
    public void setStringRating(String stringRating) {
        this.stringRating = stringRating;
    }
    
    public String getStringImdbRating() {
        return stringImdbRating;
    }
    
    public void setStringImdbRating(String stringImdbRating) {
        this.stringImdbRating = stringImdbRating;
    }
    
    
            
}
