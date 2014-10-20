

package cs9233.project.ratebeercrawler;

import com.google.gson.Gson;
import cs9233.project.ratebeercrawler.review.BeerReview;
import cs9233.project.ratebeercrawler.review.BeerReviewCollector;
import cs9233.project.ratebeercrawler.review.BeerReviewer;
import java.util.Map;

/**
 *
 * @author Udita
 */
public class RateBeerCrawler {
    
    public static void main(String[] args) {        
        // new LinkCollector().dump();
        new BeerReviewCollector().collect();
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
