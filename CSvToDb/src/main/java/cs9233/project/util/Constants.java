

package cs9233.project.util;

/**
 *
 * @author Udita
 */
public interface Constants {
    
    interface TEMP_RDS {
        String DB_USER = "beer";
        String DB_PASSWORD = "beeradmin";
        String DB_URL = "jdbc:mysql://beer-reco-db.cjpv7wnw1u1i.us-west-2.rds.amazonaws.com:3306";
        String DB_NAME = "beer_db";
        String DB_REVIEW_TABLE = "beer_review_details";
    }
    
    interface FINAL_RDS {
        String DB_USER = "mydatabaseuser";
        String DB_PASSWORD = "mypassword";
        String DB_URL = "jdbc:mysql://beerratingrds.c6crwpihucv4.us-east-1.rds.amazonaws.com:3306";
        String DB_NAME = "beerRating";
        String DB_BEER_TABLE = "Beer";
    }
    
    /**
     * 
     * INSERT INTO beer_db.beer_review_details (user_seq, user_id, beer_id, brewery_id, avg_ratings, appearance, aroma, taste, palate, overall, review) 
	VALUES (0, '-', '-', '-', '-', NULL, NULL, '-', '-', '-', NULL);

     */
    
}
