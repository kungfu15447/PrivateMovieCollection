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
public class Movie
{
    private int id;
    private String name;
    private double rating;
    private String filepath;
    private int lastview;
    
    public Movie(int id, String name, double rating, String filepath, int lastview) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filepath = filepath;
        this.lastview = lastview;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getRating()
    {
        return rating;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public int getLastview()
    {
        return lastview;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }

    public void setLastview(int lastview)
    {
        this.lastview = lastview;
    }
}
