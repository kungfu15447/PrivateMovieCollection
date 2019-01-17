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

/**
 *
 * @author Frederik Jensen
 */
public class MovieFilter
{

    MovieDAO modao;
    CatMovieDAO catdao;

    
    /**
     * The constructer of the MovieFilter class
     */
    public MovieFilter()
    {
        catdao = new CatMovieDAO();
        modao = new MovieDAO();
    }

    /**
     * Returns a list of movies whose titles contains a searched word
     * If the searched word is empty the method either returns 
     * a list containing all movies or a list containing movies based on 
     * specific selected categories.
     * @param searchBase the list getting searched through
     * @param categoryList the list of selected categories
     * @param query the searched word
     * @return SearchList
     * @throws MTBllException 
     */
    public List<Movie> searchFilter(List<Movie> searchBase, List<Category> categoryList, String query) throws MTBllException
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
                    searchList = categoriFilter(categoryList);
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

    /**
     * Returns a filtered list of movies based on a list of selected categories
     * This method gets a list of all movies from the CategoryMovie table
     * This list will have a bunch of duiplicates of the same movie objects. 
     * The only difference in these object is their list contains one integer.
     * That integer represent the id of a category. So if a movie object has 
     * three categories then that movie will occur three times in this list. 
     * After this the method then gets a list of selected categories and takes 
     * every category's id and inserts them into a new list. It then checks if 
     * that list only contains one id or multiple. If it has multiple category 
     * ids the method then goes into a loop where it checks every movie except 
     * the first one and compares them to a temporary movie object 
     * which is the first movie in that list. If the movie title is the same as
     * the temporary movie object then the list containing one category id 
     * will be added to the movie object. If the list finds a movie with a unique name
     * then that movie will be the new temporary movie object. 
     * So we will end up with a list where there is still duplicates of movies 
     * but the first occurence of a new movie will have all its categories id in its list. 
     * The method then goes through every movie again and checks if its list of categories id 
     * is identical to the list of selected categories which was made at the start of the method. 
     * If they are identical then they get added to the filtered list. 
     * After the loop is done the list is then returned.
     * Also if the list of categories is not greater than one then it doesnt
     * go through this whole process. Then it just checks if the movies contains 
     * that one category. And if they do then they are added to the filtered
     * list
     * @param catlist the list of selected categories
     * @return the list of filtered movies based on the selected categories
     * @throws MTBllException 
     */
    public List<Movie> categoriFilter(List<Category> catlist) throws MTBllException
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
