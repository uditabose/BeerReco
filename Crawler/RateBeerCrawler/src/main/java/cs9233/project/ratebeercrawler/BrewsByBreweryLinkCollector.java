

package cs9233.project.ratebeercrawler;

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
public class BrewsByBreweryLinkCollector {
    
    public Map<String, String> collect(String breweryName, String breweryLink) {
        Map<String, String> brewsByBrewery = new HashMap<>();
        
        try {
            //System.out.println(breweryLink);
            Document brewsDoc = Jsoup.connect(breweryLink).get();
            Elements brewsLinkRows = brewsDoc.select("table.maintable").get(0).select("td:eq(0)");
            Elements brewsLinks = brewsLinkRows.select("a");
            Element link = null;
            for (int i = 2; i < brewsLinks.size(); i++) {
                link = brewsLinks.get(i);
                brewsByBrewery.put(link.text(), link.attr("abs:href"));
            }

        } catch (IOException ex) {
            Logger.getLogger(BrewsByBreweryLinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //Logger.getLogger(BrewsByBreweryLinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return brewsByBrewery;
                
    }

}
