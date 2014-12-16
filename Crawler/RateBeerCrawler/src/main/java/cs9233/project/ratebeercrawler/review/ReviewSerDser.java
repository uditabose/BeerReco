

package cs9233.project.ratebeercrawler.review;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author Udita
 */
public class ReviewSerDser {
    
    class BeerReviewSerDser implements JsonSerializer<BeerReview>, JsonDeserializer<BeerReview>{

        @Override
        public JsonElement serialize(BeerReview src, Type typeOfSrc, JsonSerializationContext context) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public BeerReview deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    class BeerReviewerSerDser implements JsonSerializer<BeerReviewer>, JsonDeserializer<BeerReviewer>{

        @Override
        public JsonElement serialize(BeerReviewer src, Type typeOfSrc, JsonSerializationContext context) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public BeerReviewer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

}
