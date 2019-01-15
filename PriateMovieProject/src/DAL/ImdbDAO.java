/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.IMDBMovie;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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

    public void unzipRating()
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

    public void unzipTitle()
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
            if (searchWord.toLowerCase().contains(row[3].toLowerCase())) {
                IMDBMovie movie = new IMDBMovie(row[0], row[3]);
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }
    
    public String getIMDBMovieRating(String movieId) {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        
        String source = "data/rating.tsv";
        File imdbMovies = new File(source);
        
        List<String[]> allrows = parser.parseAll(imdbMovies);
        
        for (String[] row : allrows) {
            if (row[0].equals(movieId)) {
                return row[1];
            }
        }
        return "No rating found";
    }
    
    public void downloadIMDBDatabase() {
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
            
            FileUtils.copyURLToFile(url, file, 10000, 10000);
            
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
            
            File deleteBasicZip = new File("data/title.basics.tsv.gz");
            File deleteRatingZip = new File("data/title.ratings.tsv.gz");
            
        }  catch (IOException ex) {
            Logger.getLogger(ImdbDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
