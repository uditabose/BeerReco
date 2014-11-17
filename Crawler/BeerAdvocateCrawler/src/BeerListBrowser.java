import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by maverick on 10/17/14.
 */
public class BeerListBrowser {

    static String home = "http://www.beeradvocate.com/beer/reviews/?view=all&order=nameA&start=0";
    static WebDriver beerListDriver = new FirefoxDriver();
    static BufferedWriter beerLinks;
    static BufferedWriter brewryLinks;
    static HashSet<String> beerSet = new HashSet<String>();
    static HashSet<String> brewrySet = new HashSet<String>();

    /**
     * Crawler runs in two phases
     * phase 1 - collect links of all the beers
     * phase 2 - visit each link and start browsing them one by one collecting reviews
     *
     * phase 1 code is present in the BeerListBrowser class
     * phase 2 code is present in the BeerReviewBrowser class
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //BeerListBrowser.runCrawlerPhase1();
        BeerReviewBrowser.runCrawlerPhase2();
    }


    public static void runCrawlerPhase1() throws IOException {
        beerListDriver.get(home);
        beerLinks = new BufferedWriter(new FileWriter("beerlinks.txt"));
        brewryLinks = new BufferedWriter(new FileWriter("brewrylinks.txt"));
        browseBeers();
        beerLinks.close();
        brewryLinks.close();
    }

    private static void browseBeers() {
        List<WebElement> tableElements = listBeerPageTable();
        WebElement nextPage = findNextPageLink(tableElements.get(1)); // element at index 1 contains link to further pages
        List<WebElement> beers = tableElements.subList(3, tableElements.size() - 1);
        while(nextPage != null ) {
            prepareLinks(beers);
            nextPage.click();
            tableElements = listBeerPageTable();
            nextPage = findNextPageLink(tableElements.get(1));
            beers = tableElements.subList(3, tableElements.size() - 1);
        }

    }

    private static WebElement findNextPageLink (WebElement elem) {
        WebElement temp = null;
        List<WebElement> list = null;
        elem = elem.findElement(By.tagName("td"));
        list = elem.findElements(By.tagName("a"));
        WebElement next = list.get(list.size()-2);
        if (next.getText().contains("next"))
            return next;
        else
            return null;
    }

    /*
    *   This function prepares all the beer elements into a list
     */
    private static List<WebElement> listBeerPageTable() {
        WebElement elem = beerListDriver.findElement(By.id("baContent")), temp = null;
        elem = elem.findElement(By.tagName("table"));
        elem = elem.findElement(By.tagName("tbody"));
        List<WebElement> list = elem.findElements(By.tagName("tr"));
        return list;
    }

    private static void prepareLinks(List<WebElement> beers) {
        for (WebElement beer : beers) {
            WebElement link = beer.findElements(By.tagName("td")).get(1);
            List<WebElement> list = link.findElements(By.tagName("a"));
            try {
                if(!beerSet.contains(list.get(0).getText())) {
                    beerSet.add(list.get(0).getText());
                    beerLinks.write(list.get(0).getAttribute("href"));
                    beerLinks.newLine();
                }
                else{
                    System.out.println(list.get(0).getText() + " already present");
                    System.out.println("link:" +list.get(0).getAttribute("href"));
                }
                if(!brewrySet.contains(list.get(1).getText())) {
                    brewrySet.add(list.get(1).getText());
                    brewryLinks.write(list.get(1).getAttribute("href"));
                    brewryLinks.newLine();
                }
                else{
                    System.out.println(list.get(1).getText() + " already present");
                    System.out.println("link:" +list.get(1).getAttribute("href"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
