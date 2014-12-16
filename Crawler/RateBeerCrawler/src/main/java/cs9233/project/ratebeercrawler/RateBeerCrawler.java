

package cs9233.project.ratebeercrawler;

import com.google.gson.Gson;
import cs9233.project.ratebeercrawler.linkcollector.LinkCollector;
import cs9233.project.ratebeercrawler.review.BeerReview;
import cs9233.project.ratebeercrawler.review.BeerReviewCollector;
import cs9233.project.ratebeercrawler.review.BeerReviewer;
import static cs9233.project.ratebeercrawler.Constants.DIRNFILE.*;
import java.io.File;

/**
 *
 * @author Udita
 */
public class RateBeerCrawler {
    
    public static void main(String[] args) {  
        setupDataDirectories();
        new LinkCollector().dump();
        new BeerReviewCollector().collect();
    }
    
    private static void setupDataDirectories() {
        File theDir = new File(DUMP_DIR);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        theDir = new File(RATEBEER);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        theDir = new File(RATEBEER + BREWERY_BY_CITY);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        theDir = new File(RATEBEER + BREW_BY_BREWERY);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        theDir = new File(RATEBEER + REVIEW);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }
    
    private static void jsonTest() {
        BeerReviewer reviewer = new BeerReviewer();
        reviewer.setAppearance("5");
        reviewer.setAroma("5");
        reviewer.setAvgRatings("5");
        reviewer.setOverall("5");
        reviewer.setPalate("5");
        reviewer.setReview("Test review");
        reviewer.setTaste("5");
        reviewer.setUserId("12345");
        reviewer.setUserName("TestUser");
        
        BeerReview beerReview = new BeerReview();
        beerReview.addReview(reviewer);
        beerReview.setBeerName("Beer");
        beerReview.setRelativePerformance("5.0");
        beerReview.setRelativeStyle("5.0");
        
        Gson gson = new Gson();
        String theJson = gson.toJson(beerReview);
        Object object = gson.fromJson(theJson, BeerReview.class);
        System.out.println(object);
    }

    

}
