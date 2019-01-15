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
    private String rating;
    
    public IMDBMovie(String id, String title) {
        this.ID = id;
        this. title = title;
    }

    public String getId()
    {
        return ID;
    }

    public String getTitle()
    {
        return title;
    }

    public String getRating()
    {
        return rating;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }
    
    
}
