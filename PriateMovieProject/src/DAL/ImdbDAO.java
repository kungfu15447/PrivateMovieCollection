/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.IMDBMovie;
import DAL.Exception.MTDalException;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Frederik Jensen
 */
public class ImdbDAO
{
    /**
     * Unzips the rating zip-files.
     */
    private void unzipRating()
    {
        String inputGZFile = "data/title.ratings.tsv.gz";
        String outputFile = "data/data.tsv";
        byte[] buffer = new byte[1024];
        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(inputGZFile)); FileOutputStream out = new FileOutputStream(outputFile))
        {
            int len;
            while ((len = gzis.read(buffer)) > 0)
            {
                out.write(buffer, 0, len);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(ImdbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Unzips the title zip-file.
     */
    private void unzipTitle()
    {
        String inputGZFile = "data/title.basics.tsv.gz";
        String outputFile = "data/data.tsv";
        byte[] buffer = new byte[1024];
        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(inputGZFile)); FileOutputStream out = new FileOutputStream(outputFile))
        {
            int len;
            while ((len = gzis.read(buffer)) > 0)
            {
                out.write(buffer, 0, len);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(ImdbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the movie titles from IMDB by reading the downloaded files from
     * the IMDB website.
     * @param searchWord
     * @return The movie titles.
     */
    public List<IMDBMovie> getIMDBMovieTitles(String searchWord) {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        
        String source = "data/title.tsv";
        File imdbMovies = new File(source);
        
        parser.beginParsing(imdbMovies);
        ArrayList<IMDBMovie> searchedMovies = new ArrayList<>();
        
        String[] row;
        while ((row = parser.parseNext()) != null) {
            if (row[3].toLowerCase().contains(searchWord.toLowerCase())) {
                IMDBMovie movie = new IMDBMovie(row[0], row[3]);
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }
    
    /**
     * Gets the rating from IMDB by reading the downloaded files from
     * the IMDB website.
     * @param movieId
     * @return 
     */
    public double getIMDBMovieRating(String movieId) {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        
        String source = "data/rating.tsv";
        File imdbMovies = new File(source);
        
        List<String[]> allrows = parser.parseAll(imdbMovies);
        
        for (String[] row : allrows) {
            if (row[0].equals(movieId)) {
                double rating = Double.parseDouble(row[1]);
                return rating;
            }
        }
        return -1;
    }
    
    /**
     * Downloads two zipped files from the IMDB database that contains ratings
     * and titles of movies on their platform. The two zipped files then get
     * unzipped and deleted off the computer. If files from the website already
     * exist on the computer they will be deleated before the files gets
     * unzipped
     * @throws MTDalException 
     */
    public void downloadIMDBDatabase() throws MTDalException {
        try {
            String imdbRatingsURL = "https://datasets.imdbws.com/title.ratings.tsv.gz";
            String localFile = "data/title.ratings.tsv.gz";
            
            URL url = new URL(imdbRatingsURL);
            File file = new File(localFile);
            
            FileUtils.copyURLToFile(url, file, 10000, 10000);
            
            String imdbBasicsURL = "https://datasets.imdbws.com/title.basics.tsv.gz";
            String localFile2 = "data/title.basics.tsv.gz";
            
            URL url2 = new URL(imdbBasicsURL);
            File file2 = new File(localFile2);
            
            FileUtils.copyURLToFile(url2, file2, 10000, 10000);
            
            File deleteOldRating = new File("data/rating.tsv");
            File deleteOldTitle = new File("data/title.tsv");
            deleteOldRating.delete();
            deleteOldTitle.delete();
            
            unzipRating();
            
            File data1 = new File("data/data.tsv");
            File rating = new File("data/rating.tsv");
            data1.renameTo(rating);
            
            unzipTitle();
            
            File data2 = new File("data/data.tsv");
            File title = new File("data/title.tsv");
            data2.renameTo(title);
            
            File deleteBasicZip = new File("data/title.basics.tsv.gz");
            File deleteRatingZip = new File("data/title.ratings.tsv.gz");
            deleteBasicZip.delete();
            deleteRatingZip.delete();
            
        }  catch (IOException ex) {
            throw new MTDalException("Could not get files from the IMDB website");
        }
    }
    
}
