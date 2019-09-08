import java.io.*;
import java.util.*;





public class String_matching {


    public static void main(String[] args){
        try {
            File dictionary = new File("C:\\Users\\yizzhu\\Documents\\GitHub\\word-blending-detection\\2019S2-COMP90049_proj1-data_windows\\2019S2-COMP90049_proj1-data_windows\\dict.txt");

            File candidates = new File("C:\\Users\\yizzhu\\Documents\\GitHub\\word-blending-detection\\2019S2-COMP90049_proj1-data_windows\\2019S2-COMP90049_proj1-data_windows\\candidates.txt");

            File true_bleands = new File("C:\\Users\\yizzhu\\Documents\\GitHub\\word-blending-detection\\2019S2-COMP90049_proj1-data_windows\\2019S2-COMP90049_proj1-data_windows\\blends.txt");

            Scanner dictionaryoutput = new Scanner(new FileReader(dictionary));

            Scanner candidatesoutput = new Scanner(new FileReader(candidates));

            Scanner blendsoutput = new Scanner(new FileReader(true_bleands));

            ArrayList<String> compare_parte = new ArrayList<>();

            ArrayList<String> compare_child = new ArrayList<>();

            ArrayList<String> blends = new ArrayList<>();

            String temp;


            while (dictionaryoutput.hasNext()) {
                temp = dictionaryoutput.next();
                compare_parte.add(temp);

            }

            while (candidatesoutput.hasNext()) {
                temp = candidatesoutput.next();
                compare_child.add(temp);
            }

            while(blendsoutput.hasNextLine()){
                temp = blendsoutput.nextLine().split("\t")[0];
                if(compare_child.indexOf(temp) != -1)
                     blends.add(temp);


            }



            int count = 0;

            ArrayList<String> compare_chile = new ArrayList<>();

            for (String i : compare_child) {
                if (i.length() >= 16) {
                    count++;
                   continue;
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
                       continue;
                    }

                    }
                compare_chile.add(i);

                }
            System.out.println(compare_chile.size());



            ArrayList<String> blends_candidates = new ArrayList<>();
            for (String i: compare_chile)
            {
                for (String ii: compare_parte) {
                    if (ii.length() > 1) {
                        if (i.charAt(0) == ii.charAt(0) || i.charAt(i.length() - 1) == ii.charAt(ii.length() - 1)) {




                            int distance = Global_edit_distance(i, ii);
                            if (distance < 2) {
                                blends_candidates.add(i);
                                break;
                            }

                        }

                    }
                }
            }


            System.out.println("done");
            System.out.println(blends_candidates.size());

            float precision = 0;
            float recall = 0;

            for(String candidate: blends_candidates)
            {
                if(blends.indexOf(candidate) != -1)
                {
                    precision += 1/(float)blends_candidates.size();
                    recall += 1/(float)blends.size();
                }

            }

            System.out.println("prec:" + precision);
            System.out.println("recall: " + recall);




            }
        catch(FileNotFoundException e)
        {

        }




    }

    private static int Global_edit_distance(String a, String b)
    {
       int[][] martix = new int[a.length()+1][b.length()+1];

       for (int i = 0; i<= a.length(); i++)
       {
           martix[i][0] = i;
       }

       for(int j = 0; j <= b.length();j++)
       {
           martix[0][j] = j;
       }

        for (int i = 0; i< a.length(); i++)
        {
            char c1 = a.charAt(i);

            for(int j = 0; j < b.length();j++)
            {
                char c2 = b.charAt(j);

                if (c1==c2) {
                    martix[i+1][j+1] = martix [i][j];
                }
                else{
                    int replace = martix[i][j] + 1;
                    int insert = martix[i][j + 1] + 1;
                    int delete = martix[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    martix[i + 1][j + 1] = min;

                }


            }


        }

        return martix [a.length()][b.length()];




    }


}
