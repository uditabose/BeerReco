package cs9233.project.dabpopulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Udita
 */
public class BeerIdMapper {

    public static void main(String[] args) {
        Map<String, Long> beerIdToIdxMapper = beerIdToIdxMapper();
        mapAndRewriteHiveData(beerIdToIdxMapper);
    }

    private static Map<String, Long> beerIdToIdxMapper() {
        Map<String, Long> beerIdToIdx = new HashMap<>();
        BufferedReader dataReader = null;
        String dataPath = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/By_Beer_ReWrite";

        try {
            dataReader = new BufferedReader(new FileReader(dataPath));
            String dataLine = null;

            while ((dataLine = dataReader.readLine()) != null) {
                String[] splittedData = dataLine.split(",");
                beerIdToIdx.put(splittedData[1], Long.parseLong(splittedData[0]));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                dataReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return beerIdToIdx;
    }

    private static void mapAndRewriteHiveData(Map<String, Long> beerIdToIdxMapper) {
        String hiveDumpPath = 
                "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/hive_dump/rb_sorted_dump";
        String dataReWritePath = "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS9233/Project/Resource/hive_dump/rb_sorted_dump_rewrite";
         
       
        BufferedReader dataReader = null;
        BufferedWriter dataReWriter = null;
        try {
            dataReader = new BufferedReader(new FileReader(hiveDumpPath));
            dataReWriter = new BufferedWriter(new FileWriter(dataReWritePath, true));

            String dataLine = null;

            while((dataLine = dataReader.readLine()) != null) {
                List<String> removeBlank = removeBlank(dataLine);
                if (removeBlank == null) {
                    continue;
                }
                
                System.out.println(beerIdToIdxMapper.get(removeBlank.get(0)));
                dataReWriter.append(beerIdToIdxMapper.get(removeBlank.get(0)) + "");
                dataReWriter.append("\t");
                dataReWriter.append(beerIdToIdxMapper.get(removeBlank.get(1)) + "");
                dataReWriter.append("\t");
                dataReWriter.append(removeBlank.get(2));
                dataReWriter.newLine();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                dataReWriter.close();
                dataReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static List<String> removeBlank(String aString) {
        char blank1 = (char) 32;
        char blank2 = (char) 9;
        char blank3 = (char) 160;
        aString = aString.replaceAll(blank1 + "", "@")
                .replaceAll(blank2 + "", "@")
                .replaceAll(blank3 + "", "@");
        //System.out.println(aString);
        String[] splitted = aString.split("@");
        List<String> pieces = new ArrayList<>();
        for (String str : splitted) {
            System.out.println(str);
            if (!str.trim().equals("")) {
                pieces.add(str.trim());
            }
        }
        //System.out.println(pieces);
        if (pieces.size() < 3) {
            return null;
        }
        
        return pieces;
    }
}
