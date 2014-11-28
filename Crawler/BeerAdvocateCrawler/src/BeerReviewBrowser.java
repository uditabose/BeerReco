import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * Created by maverick on 11/5/14.
 */
public class BeerReviewBrowser {

    // files used to preserve state
    static String brewryHashMapFile = "brewryHashMapFile.ser";
    static String beerHashMapFile = "beerHashMapFile.ser";
    static String metadataFile = "metadata.txt";
    // input file
    static String inputUrlFile = "./run2/beerlinks.txt";
    // file used to  keep temp reviews for a beer
    static String tempReviewFileName = "temp.txt";
    // file used to keep the final data
    static String reviewData = "Badata.txt";
    // files used to keep the map data after the crawler has finished
    static String beerIdtoNameMap = "beerIdToName";
    static String brewryIdToNameMap = "brewryIdToName";
    static String beerDataFile = "beerData.txt";
    static String brewryData = "brewryData.txt";
    static int reviewCounter = 0;

    // driver to browse through beer reviews
    static ProfilesIni profile = new ProfilesIni();
    static FirefoxProfile ffprofile = profile.getProfile("default");
    static WebDriver beerReviewDriver = new FirefoxDriver(ffprofile);

    static BufferedWriter dataFile;
    static BufferedReader urlFileReader;
    public static void runCrawlerPhase2() throws IOException {
        readBeerListFile();
    }
    static HashMap<String, String> brewryIdMap = new HashMap<>();
    static HashMap<String, String> beerIdMap = new HashMap<>();
    static int beerid = 0;
    static int brewryId = 0;
    static int urlsProcessed = 0;

    private static void readBeerListFile() {
        try{
            initializeAndLoadState();

            String url = urlFileReader.readLine();
            while( url != null) {
                url = url.substring(0,url.indexOf("?"));
                processBeer(url);
                mergeReviewsToDataFile();
                urlsProcessed++;
                preserveState();
                url = urlFileReader.readLine();
            }
            dataFile.close();
            idMapOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void idMapOutput() throws IOException {
        File beerIdtoName = new File(beerIdtoNameMap);
        if(beerIdtoName.exists()) {
            beerIdtoName.delete();
        }
        File brewryIdToName = new File(brewryIdToNameMap);
        if(brewryIdToName.exists()) {
            brewryIdToName.delete();
        }

        BufferedWriter beerWriter = new BufferedWriter(new FileWriter(beerIdtoName));
        BufferedWriter brewryWriter = new BufferedWriter(new FileWriter(brewryIdToName));

        for(String key : beerIdMap.keySet()) {
            beerWriter.append(String.format("%1$s,%2$s\n", key, beerIdMap.get(key)));
        }

        for(String key2: brewryIdMap.keySet()) {
            brewryWriter.append(String.format("%1$s,%2$s\n", key2, brewryIdMap.get(key2)));
        }

        beerWriter.close();
        brewryWriter.close();
    }

    private static void mergeReviewsToDataFile() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(tempReviewFileName));
        String line = br.readLine();
        while(line != null) {
            dataFile.append(line+"\n");
            line = br.readLine();
        }
        br.close();
        dataFile.flush();
    }

    private static void processBeer(String url) throws IOException {
        File beerReviews = new File(tempReviewFileName);

        if (beerReviews.exists()) {
            beerReviews.delete();
            beerReviews.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(beerReviews));

        beerReviewDriver.get(url);
        String beerName, brewryName;

        WebElement next;
        WebElement temp = null;
        temp = beerReviewDriver.findElement(By.cssSelector(".titleBar"));
        beerName = temp.getText();
        brewryName = beerName.substring(beerName.indexOf("-")+1, beerName.length());
        beerName = beerName.substring(beerName.indexOf("-"));
        String id = "", brewryid = "";

        if(brewryIdMap.containsKey(brewryName))

            brewryid = brewryIdMap.get(brewryName);
        else {
            brewryid = "bw"+ (new Integer(brewryId++).toString());
            brewryIdMap.put(brewryName, brewryid);
        }
        if(beerIdMap.containsKey(beerName)) {
            id = beerIdMap.get(beerName);
        }
        else {
            id = "be"+ new Integer(beerid++).toString();
            beerIdMap.put(beerName, id);
        }

        do {
            next = findNextPageLink();
            List<WebElement> reviews = null;
            reviews = beerReviewDriver.findElements(By.id("rating_fullview_container"));
            String bascore, rating, review, username, look = "", smell = "", taste = "", feel = "", overall = "";
            for(WebElement element : reviews) {
                review = element.getText();
                String [] parts = review.split("\n");
                bascore = parts[0];
                rating = parts[1];
                username = parts[parts.length-1];
                username = username.substring(0,username.indexOf(','));
                review = "";
                for(int i = 2; i < parts.length-1; i++) {
                    if(parts[i].length() > 1)
                        review += parts[i];
                }
                String[] ratingComponents = rating.split("|");
                for(String ratingComponent : ratingComponents) {
                    if (ratingComponent.contains("look"))
                        look = ratingComponent.substring(ratingComponent.indexOf(":")+1);
                    else if (ratingComponent.contains("smell"))
                        smell = ratingComponent.substring(ratingComponent.indexOf(":")+1);
                    else if (ratingComponent.contains("taste"))
                        taste = ratingComponent.substring(ratingComponent.indexOf(":")+1);
                    else if (ratingComponent.contains("feel"))
                        feel = ratingComponent.substring(ratingComponent.indexOf(":")+1);
                    else if (ratingComponent.contains("overall"))
                        overall = ratingComponent.substring(ratingComponent.indexOf(":")+1);
                }
                bascore = bascore.substring(0,bascore.indexOf(' '));
                bw.append(String.format("%9$s,%10$s,%1$s,%2$s,%3$s,%4$s,%5$s,%6$s,%7$s,%8$s\n",bascore, look, smell, taste, feel, overall,"ba-" +username, review, id, brewryid));
                System.out.println(reviewCounter++);
            }
            if(next != null)
                next.click();
        } while(next != null);
        bw.close();
    }

    private static WebElement findNextPageLink() {
        WebElement elem = null;
        try {
            elem = beerReviewDriver.findElement(By.cssSelector("#baContent>div:nth-last-child(2)>:nth-last-child(2)"));
            if(!elem.getTagName().equals("a"))
                return null;
        } catch (NoSuchElementException e) {
            return null;
        }
       return elem;
    }

    private static void preserveState() {
        try{
            // save the brewry map
            File file = new File(brewryHashMapFile);
            serializeObjectToFile(file, brewryIdMap);
            file = new File(beerHashMapFile);
            serializeObjectToFile(file, beerIdMap);

            File file2 = new File(metadataFile);
            if(file2.exists()) {
                file2.delete();
                file2.createNewFile();
            }
            FileWriter writer = new FileWriter(file2);
            writer.append(beerid +"\n");
            writer.append(brewryId + "\n");
            writer.append(urlsProcessed+"\n");
            writer.append(reviewCounter + "\n");
            writer.close();
            idMapOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void serializeObjectToFile(File file, Object obj) throws IOException {
        if(file.exists()) {
            file.delete();
            file.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(brewryIdMap);
        oos.close();
    }

    private static void initializeAndLoadState() {
        boolean isLoadingAfterACrash = false;
        // load the brewry map
        File file = new File(brewryHashMapFile);

        try {
            urlFileReader = new BufferedReader(new FileReader(new File(inputUrlFile)));
            dataFile = new BufferedWriter(new FileWriter(reviewData, true));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try{
            if(file.exists()) {
                isLoadingAfterACrash = true;
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                brewryIdMap = (HashMap<String,String>) ois.readObject();
                ois.close();
            }
            file = new File(beerHashMapFile);
            if(file.exists()) {
                isLoadingAfterACrash = true;
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                beerIdMap = (HashMap<String,String>) ois.readObject();
                ois.close();
            }

            if(isLoadingAfterACrash) {
                File file2 = new File(metadataFile);
                BufferedReader br = new BufferedReader(new FileReader(file2));
                beerid = Integer.parseInt(br.readLine());
                brewryId = Integer.parseInt(br.readLine());
                urlsProcessed = Integer.parseInt(br.readLine());
                reviewCounter = Integer.parseInt(br.readLine());
                br.close();

                for(int i = 0; i < urlsProcessed; i++) {
                    urlFileReader.readLine();
                }

            }
            else {
                // write the csv header
                dataFile.write("beerid, brewryid, score, look, smell ,taste , feel, overall, username, review\n");
            }
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
