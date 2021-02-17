import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Data {
    private String path = "C:\\Users\\Waifu\\Documents\\Vs Projects\\Java\\Robobot\\src\\main\\java\\data";  

    public ArrayList<String> get_words(String theme) throws InterruptedException {

        HashMap<String, Integer> themes = new HashMap<String, Integer>();
        ArrayList <String> words = new ArrayList<String>();
        Scanner myReader;

        themes.put("lol", 0);

        try{
            int value = themes.get(theme);
            switch (value){
                case 0:
                    try {                        
                        myReader = new Scanner(new File(path +"\\"+ theme + ".txt"));
                        while (myReader.hasNextLine()) {
                            words.add(myReader.nextLine().substring(4));                        
                        }
                        myReader.close();
                        } catch (FileNotFoundException e) {
                        System.out.println("Could not find the data.");
                        e.printStackTrace();
                        }

                    
                    break;
                default:
                    System.out.println("The theme could not be found in the databank.");
                    return words;
                    
            }
        } catch(Exception e){
            System.out.println("The theme could not be found in the databank.");;
            return words;
        }

        return words;

        }

}