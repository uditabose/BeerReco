package cs9233.project.ratebeercrawler.review;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import static cs9233.project.ratebeercrawler.Constants.DIRNFILE.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
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
public class BeerReviewCollector {

    private final Gson gson = new Gson();

    public void collect() {
        File beerDir = new File(RATEBEER + BREW_BY_BREWERY);
        File[] beerDataFiles = beerDir.listFiles();

        Map<String, String> brews = null;
        for (File beerDataFile : beerDataFiles) {
            brews = getFromJson(beerDataFile);
            //System.out.println(brews);
            System.out.println("----------------------------------------------");
            if (brews != null) {
                for (Map.Entry<String, String> brewEntry : brews.entrySet()) {
                    parse(brewEntry.getKey(), brewEntry.getValue());
                }
            }
            //break;
        }

    }

    private Map<String, String> getFromJson(File beerDataFile) {
        FileReader fileReader = null;
        Map<String, String> brews = null;
        try {
            fileReader = new FileReader(beerDataFile);
            brews = gson.fromJson(fileReader, Map.class);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BeerReviewCollector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(BeerReviewCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return brews;
    }

    private void parse(String brewName, String brewUrl) {
        try {
            
            System.out.println(brewUrl);
            Document brewDoc = Jsoup.connect(brewUrl).get();
            Elements smallElements = brewDoc.select("small:contains(RATINGS)");
            BeerReview beerReview = null;
            if (smallElements != null && smallElements.size() >= 1) {
                Element smallElem = smallElements.get(0);
                Elements rateElems = smallElem.children();

                //System.out.println("rateElems -- > " + rateElems);
                beerReview = (rateElems.isEmpty()) ? null : new BeerReview();

                if (beerReview != null) {
                    String key = null, value = null, temp = null;
                    for (Element rateElem : rateElems) {
                        temp = rateElem.text().replaceAll(":", "").trim();
                        if (temp.equals(temp.toUpperCase())) {
                            key = temp.toLowerCase();
                        } else {
                            value = temp;
                        }
                        beerReview.addRating(key, value);
                    }
                }

                Elements tableElements = brewDoc.select("div#container table");
                Element targetTable = null;
                if (tableElements.size() == 5) {
                    targetTable = tableElements.last();
                } else if (tableElements.size() == 6) {
                    targetTable = tableElements.get(4);
                }
                Element targetTd = null;
                if (targetTable != null) {
                    targetTd = targetTable.getElementsByTag("td").first();
                }

                if (targetTd != null) {
                    BeerReviewer beerReviewer = null;
                    int childCounter = 1;
                    for (Element revElem : targetTd.children()) {
                        try {
                            String allText = revElem.text();

                            if (revElem.tagName().equals("div") && childCounter == 1) {
                                beerReviewer = new BeerReviewer();
                                beerReview.addReview(beerReviewer);
                                String[] splitted = allText.split(" ");
                                beerReviewer.setAvgRatings(splitted[0]);
                                beerReviewer.setAroma(splitted[2]);
                                beerReviewer.setAppearance(splitted[4]);
                                beerReviewer.setTaste(splitted[6]);
                                beerReviewer.setPalate(splitted[8]);
                                beerReviewer.setOverall(splitted[10]);
                                childCounter = 2;
                            }  else if (revElem.tagName().equals("div") && childCounter != 1) {
                                beerReviewer.setReview(allText);
                                childCounter = 1;
                            } else if (revElem.tagName().equals("small")) {
                                beerReviewer.setUserName(allText.split("\\(")[0]);
                            }
                        } catch (Exception e) {
                        }

                        
                    }
                }

                
                //System.out.println(beerReview);
            }
            dump(brewName, beerReview);
            System.out.println("***********************************************");
        } catch (IOException ex) {
            Logger.getLogger(BeerReviewCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dump(String brewName, BeerReview beerReview) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(REVIEW_FILE + URLEncoder.encode(brewName, "UTF-8"));
            gson.toJson(beerReview, Streams.writerForAppendable(fileWriter));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BeerReviewCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BeerReviewCollector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                //Logger.getLogger(BeerReviewCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
