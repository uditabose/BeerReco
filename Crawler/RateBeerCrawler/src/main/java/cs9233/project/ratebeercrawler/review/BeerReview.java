

package cs9233.project.ratebeercrawler.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Udita
 */
public class BeerReview {
    
    private String beerName = null;
    private String relativePerformance = null;
    private String relativeStyle = null;
    private Map<String, String> ratings = null;
    private List<BeerReviewer> reviewer = null;

    public BeerReview() {
        reviewer = new ArrayList<>();
        ratings = new HashMap<>();
    }
    
    

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getRelativePerformance() {
        return relativePerformance;
    }

    public void setRelativePerformance(String relativePerformance) {
        this.relativePerformance = relativePerformance;
    }

    public String getRelativeStyle() {
        return relativeStyle;
    }

    public void setRelativeStyle(String relativeStyle) {
        this.relativeStyle = relativeStyle;
    }

    public Map<String, String> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, String> ratings) {
        this.ratings = ratings;
    }
    
    public void addRating(String key, String value) {
        this.ratings.put(key, value);
    }

    public List<BeerReviewer> getReviewer() {
        return reviewer;
    }

    public void setReviewer(List<BeerReviewer> reviewer) {
        this.reviewer = reviewer;
    }
    
    public void addReview(BeerReviewer beerReviewer) {  
        if (beerReviewer != null) {
            this.reviewer.add(beerReviewer);
        }
    }

    @Override
    public String toString() {
        return "BeerReview{" + "beerName=" + beerName + ", relativePerformance=" 
                + relativePerformance + ", relativeStyle=" + relativeStyle 
                + ", ratings=" + ratings + ", reviewer=" + reviewer + '}';
    }
    
    

}
