import java.io.*;
import java.util.*;





public class String_matching {


    public static void main(String[] args){
        try {
            File dictionary = new File("C:\\Users\\yizzhu\\Documents\\GitHub\\word-blending-detection\\2019S2-COMP90049_proj1-data_windows\\2019S2-COMP90049_proj1-data_windows\\dict.txt");

            File candidates = new File("C:\\Users\\yizzhu\\Documents\\GitHub\\word-blending-detection\\2019S2-COMP90049_proj1-data_windows\\2019S2-COMP90049_proj1-data_windows\\candidates.txt");

            Scanner dictionaryoutput = new Scanner(new FileReader(dictionary));

            Scanner candidatesoutput = new Scanner(new FileReader(candidates));

            ArrayList<String> compare_parte = new ArrayList<>();

            ArrayList<String> compare_child = new ArrayList<>();

            String temp;


            while (dictionaryoutput.hasNext()) {
                temp = dictionaryoutput.next();
                compare_parte.add(temp);

            }

            while (candidatesoutput.hasNext()) {
                temp = candidatesoutput.next();
                compare_child.add(temp);
            }

            int count = 0;

            for (String i : compare_child) {
                if (i.length() >= 16) {
                    count++;
                    System.out.println(i);
                }
                if (i.length() > 2) {
                    int num = 0;
                    for (int index = 1; index < i.length(); index++)
                    {
                        if (i.charAt(index - 1) == i.charAt(index))
                            num++;
                    }
                    if (num > 2)
                    {
                        count++;
                        System.out.println(i);
                    }

                    }

                }


            }
        catch(FileNotFoundException e)
        {

        }




    }


}
