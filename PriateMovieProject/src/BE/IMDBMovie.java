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
public class IMDBMovie
{
    private final String ID;
    private String title;
    private double rating;
    
    /**
     * 
     * @param id
     * @param title 
     */
    public IMDBMovie(String id, String title) {
        this.ID = id;
        this. title = title;
    }

    /**
     * returns the ID
     * @return ID
     */
    public String getId()
    {
        return ID;
    }

    /**
     * returns the title
     * @return title
     */
    public String getTitle()
    {
        return title;
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
     * sets the title
     * @param title 
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * sets the rating
     * @param rating 
     */
    public void setRating(Double rating)
    {
        this.rating = rating;
    }
    
    /**
     * returns the title
     * @return title
     */
    @Override
    public String toString() {
        return title;
    }
    
}
