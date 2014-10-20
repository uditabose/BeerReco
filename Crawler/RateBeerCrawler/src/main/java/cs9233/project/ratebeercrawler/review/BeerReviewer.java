

package cs9233.project.ratebeercrawler.review;

/**
 *
 * @author Udita
 */
public class BeerReviewer {
    private String userName = null;
    private String userId = null;
    private String avgRatings = null;
    private String aroma = null;
    private String appearance = null;
    private String taste = null;
    private String palate = null;
    private String overall = null;
    private String review = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvgRatings() {
        return avgRatings;
    }

    public void setAvgRatings(String avgRatings) {
        this.avgRatings = avgRatings;
    }

    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getPalate() {
        return palate;
    }

    public void setPalate(String palate) {
        this.palate = palate;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "BeerReviewer{" + "userName=" + userName + ", userId=" 
                + userId + ", avgRatings=" + avgRatings + ", aroma=" 
                + aroma + ", appearance=" + appearance + ", taste=" 
                + taste + ", palate=" + palate + ", overall=" + overall 
                + ", review=" + review + '}';
    }
}
