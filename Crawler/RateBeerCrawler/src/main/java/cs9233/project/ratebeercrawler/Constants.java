/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs9233.project.ratebeercrawler;

/**
 *
 * @author Udita <udita.bose@nyu.edu>
 */
public interface Constants {
    
    interface LINK {
        String RATEBEER_ROOT = "http://www.ratebeer.com";
        String BREWERY_PAGE = "http://www.ratebeer.com/breweries/";
    }
    
    interface DIRNFILE {
        // Change the dump dir for running other
        String DUMP_DIR = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/BeerReco/Crawler/DataDump/";
        String RATEBEER = DUMP_DIR + "RateBeer/";
        String CITY = "City";
        String BREWERY_BY_CITY = "Brewer/";
        String BREWERY_BY_CITY_FILE = RATEBEER + BREWERY_BY_CITY + "Brewery-";
        String BREW_BY_BREWERY = "Beer/";
        String BREW_BY_BREWERY_FILE = RATEBEER + BREW_BY_BREWERY + "Beer-";
        String REVIEW = "Review/";
        String REVIEW_FILE = RATEBEER + REVIEW + "Review-";
    }            
}
