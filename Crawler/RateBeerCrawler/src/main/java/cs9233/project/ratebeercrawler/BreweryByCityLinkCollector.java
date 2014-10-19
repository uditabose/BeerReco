

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
public class BreweryByCityLinkCollector {
    
    public Map<String, String> collect() {
        Map<String, String> breweryCityLinks = new HashMap<>();
        
        try {
            Document breweryDoc = Jsoup.connect(Constants.LINK.BREWERY_PAGE).get();
            Elements linkTable = breweryDoc.select("div#container > table");
            Element tableCell = linkTable.select("td").last();
            Elements breweryAnchors = tableCell.getElementsByTag("a");
            
            for (Element breweryAnchor : breweryAnchors) {
                breweryCityLinks.put(breweryAnchor.text(), breweryAnchor.attr("abs:href"));
            }

        } catch (IOException ex) {
            Logger.getLogger(BreweryByCityLinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BrewsByBreweryLinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return breweryCityLinks;
    }
    
    public void dump() {
        Map<String, String> breweryCityLinks = collect();
        
    } 

}
