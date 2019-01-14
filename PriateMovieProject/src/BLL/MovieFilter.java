/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Category;
import BE.Movie;
import BLL.Exception.MTBllException;
import DAL.CatMovieDAO;
import DAL.Exception.MTDalException;
import DAL.MovieDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frederik Jensen
 */
public class MovieFilter
{

    MovieDAO modao;
    CatMovieDAO catdao;

    public MovieFilter()
    {
        catdao = new CatMovieDAO();
        modao = new MovieDAO();
    }

    public List<Movie> searchMovies(List<Movie> searchBase, List<Category> categoryList, String query) throws MTBllException
    {
        try
        {
            List<Movie> searchList = new ArrayList<>();

            if (query.isEmpty())
            {
                if (categoryList.isEmpty())
                {
                    searchList = modao.getAllMovies();
                } else
                {
                    searchList = genreFilter(categoryList);
                }

            } else
            {
                for (Movie movie : searchBase)
                {
                    if (movie.getName().toLowerCase().contains(query))
                    {
                        searchList.add(movie);
                    }
                }
            }

            return searchList;
        } catch (MTDalException ex)
        {
            throw new MTBllException("Could not connect to the DAL layer");
        }
    }

    public List<Movie> genreFilter(List<Category> catlist) throws MTBllException
    {
        try
        {
            List<Movie> movieCatList = catdao.getMoviesFromCats();
            List<Movie> filteredList = new ArrayList<>();
            List<Integer> catIdList = new ArrayList<>();
            Movie tempMovie = movieCatList.get(0);

            for (Category cat : catlist)
            {
                catIdList.add(cat.getId());
            }
            if (catIdList.size() == 1)
            {
                for (Movie movie : movieCatList)
                {
                    if (movie.getList().equals(catIdList))
                    {
                        filteredList.add(movie);
                    }
                }
            } else
            {
                for (int i = 1; i < movieCatList.size(); i++)
                {
                    if (tempMovie.getName().equals(movieCatList.get(i).getName()))
                    {
                        tempMovie.getList().add(movieCatList.get(i).getList().get(0));
                    } else
                    {
                        tempMovie = movieCatList.get(i);
                    }
                }

                for (Movie movie : movieCatList)
                {
                    if (movie.getList().equals(catIdList))
                    {
                        filteredList.add(movie);
                    }
                }
            }

            return filteredList;
        } catch (MTDalException ex)
        {
            throw new MTBllException();
        }
    }
}
