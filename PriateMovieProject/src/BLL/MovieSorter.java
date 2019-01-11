/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Movie;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class MovieSorter
{

    public void sortMovieListTitle(List<Movie> catlist)
    {
        Comparator<Movie> movies;
        movies = new Comparator<Movie>()
        {
            @Override
            public int compare(Movie t, Movie t1)
            {
                return t.getName().compareTo(t1.getName());
            }
        };
        Collections.sort(catlist, movies);
    }
    
    public void sortMovieListRating(List<Movie> catlist) {
        Comparator<Movie> movies;
        movies = new Comparator<Movie>()
        {
            @Override
            public int compare(Movie t, Movie t1)
            {
                return Double.compare(t1.getRating(), t.getRating());
            }
        };
        Collections.sort(catlist, movies);
    }
    
    public void sortMovieListId(List<Movie> catlist) {
        Comparator<Movie> movies;
        movies = new Comparator<Movie>()
        {
            @Override
            public int compare(Movie t, Movie t1)
            {
                return Integer.compare(t.getId(), t1.getId());
            }
        };
        Collections.sort(catlist, movies);
    }
}
