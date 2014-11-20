

package cs9233.project.ratebeercrawler.linkcollector;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import java.io.FileWriter;
import java.util.Map;

import static cs9233.project.ratebeercrawler.Constants.DIRNFILE.*;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Udita
 */
public class LinkCollector {
    
    private Gson gson = new Gson();
    
    public void dump() {
        collectCity();
    }
    
    private void collectCity() {
        BreweryByCityLinkCollector bbclc = new BreweryByCityLinkCollector();
        Map<String, String> cityLink = bbclc.collect();
        try {
            gson.toJson(cityLink, Streams.writerForAppendable(new FileWriter(DUMP_DIR + CITY)));
        } catch (IOException ex) {
            Logger.getLogger(LinkCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        collectBrewery(cityLink);
    }
    
    private void collectBrewery(Map<String, String> breweryByCityMap) {
        BreweryLinkCollector blc = new BreweryLinkCollector();
        
        Map<String, String> linkMap = null;
        for (Map.Entry<String, String> bbcEnt : breweryByCityMap.entrySet()) {
            linkMap = blc.collect(bbcEnt.getKey(), bbcEnt.getValue());
            Writer writer = null;
            try {
                writer = new FileWriter(
                        BREWERY_BY_CITY_FILE + URLEncoder.encode(bbcEnt.getKey(), "UTF-8"));
                gson.toJson(linkMap, Streams.writerForAppendable(writer));
            } catch (IOException ex) {
                Logger.getLogger(LinkCollector.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {
                    Logger.getLogger(LinkCollector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            collectBrew(linkMap);
        }
    }
    
    private void collectBrew(Map<String, String> brewsByBrewery) {
        BrewsByBreweryLinkCollector bbblc = new BrewsByBreweryLinkCollector();
        
        Map<String, String> linkMap = null;
        Writer writer = null;
        for (Map.Entry<String, String> bbbcEnt : brewsByBrewery.entrySet()) {
            linkMap = bbblc.collect(bbbcEnt.getKey(), bbbcEnt.getValue());
            //System.out.println(linkMap);
            try {
                writer = new FileWriter(
                        BREW_BY_BREWERY_FILE + URLEncoder.encode(bbbcEnt.getKey(), "UTF-8"));
                gson.toJson(linkMap, Streams.writerForAppendable(writer));
            } catch (IOException ex) {
                Logger.getLogger(LinkCollector.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (Exception ex) {
                    Logger.getLogger(LinkCollector.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }

}
