import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.List;

/**
 * Created by maverick on 11/5/14.
 */
public class BeerReviewBrowser {

    static WebDriver beerReviewDriver = new FirefoxDriver();

    public static void runCrawlerPhase2() throws IOException {
        readBeerListFile();
    }

    private static void readBeerListFile() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./run2/beerlinks.txt"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        String line = br.readLine();
        while( line != null) {
            line = line.substring(0,line.indexOf("?"));
            processBeer(line);
            line = br.readLine();

        }
    }

    private static void processBeer(String url) {
        beerReviewDriver.get(url);
        WebElement temp = null;
        List<WebElement> reviews = beerReviewDriver.findElements(By.id("rating_fullview_container"));
        for(WebElement element : reviews) {
            element = element.findElement(By.id("rating_fullview_content_2"));
            temp = element.findElement(By.className("BAscore_norm"));
            String bascore = temp.getText();
            temp = element.findElement(By.className("rAvg_norm"));
            String norm = temp.getText();
            List<WebElement> mutedClass = element.findElements(By.className("muted"));
            temp = mutedClass.get(0);
            String rating = temp.getText();
            String review = element.getText();
            temp = mutedClass.get(1);
            temp = temp.findElement(By.className("username"));
            String username = temp.getText();

        }
    }
}
