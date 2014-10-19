

package cs9233.project.ratebeercrawler;

import java.util.Map;

/**
 *
 * @author Udita
 */
public class RateBeerCrawler {
    
    public static void main(String[] args) {
//        Map<String, String> listByCity = new BreweryByCityLinkCollector().collect();
//        Map<String, String> cityBreweries = new BreweryLinkCollector().collect("Maryland", listByCity.get("Maryland"));
//        Map<String, String> brewsLink = new BrewsByBreweryLinkCollector().collect("Ellicott Mills Brewing", cityBreweries.get("Ellicott Mills Brewing"));
//        //Stillwater Artisanal
//        System.out.println(brewsLink);
        
        new LinkCollector().dump();
    }

}
