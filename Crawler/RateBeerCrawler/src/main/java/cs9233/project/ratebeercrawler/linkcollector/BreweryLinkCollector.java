

package cs9233.project.ratebeercrawler.linkcollector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Udita
 */
public class BreweryLinkCollector {
    
    public Map<String, String> collect(String cityName, String breweryListPageLink) {
        Map<String, String> breweryLinks = new HashMap<>();
        try {
            //System.out.println(breweryListPageLink);
            Document breweryListPageDoc = Jsoup.connect(breweryListPageLink).get();
            Elements listOfLinks = breweryListPageDoc.getElementById("brewerTable").getElementsByTag("a");
            
            for (Element brewLink : listOfLinks) {
                breweryLinks.put(brewLink.text(), brewLink.attr("abs:href"));
            }
        } catch (IOException ex) {
            Logger.getLogger(BreweryLinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //Logger.getLogger(BrewsByBreweryLinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        breweryLinks.remove("My Ratings");
        return breweryLinks;
    }

}
