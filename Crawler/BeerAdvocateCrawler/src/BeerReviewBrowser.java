import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;

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
    }
}
