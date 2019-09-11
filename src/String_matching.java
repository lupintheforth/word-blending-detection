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

            System.out.println(blends.size());



            int count = 0;

            ArrayList<String> compare_chile = new ArrayList<>();

            for (String i : compare_child) {
                if (i.length() >= 15) {
                   continue;
                }
                if (i.length() <= 2)
                {
                    continue;
                }
                if (i.length() > 2) {
                    int num = 0;
                    for (int index = 1; index < i.length(); index++)
                    {
                        if (i.charAt(index - 1) == i.charAt(index))
                            num++;
                    }
                    if (num > 1)
                    {
                       continue;
                    }

                    }
                if (i.length() > 2) {
                    int num = 0;
                    for (int index = 2 ; index < i.length(); index++)
                    {
                        if (i.charAt(index - 2) == i.charAt(index))
                            num++;
                    }
                    if (num > 2)
                    {
                        continue;
                    }




                }


                compare_chile.add(i);

                }
            System.out.println(compare_chile.size());

            float tn = 0;



            ArrayList<String> blends_candidates = new ArrayList<>();
            for (String i: compare_chile)
            {   String prefix = "";
                String suffix = "";



                if (blends.indexOf(i) == -1)

                {

                    tn += 1;

                }


                for (String ii: compare_parte) {

                    if (ii.length() > 1) {
                        if (i.charAt(0) == ii.charAt(0)) {

                            double similarity = compute(i, ii);
                            if (similarity > 0.85) {
                                prefix = ii;
                                break;

                            }


                        }


                    }
                }
                for (String ii : compare_parte){
                    if(ii.length() > 1){
                        if (i.charAt(i.length()-1) == ii.charAt(ii.length()-1)) {

                            double similarity = compute(reversedString(i), reversedString(ii));
                            if (similarity > 0.905){
                                suffix = ii;
                                break;
                            }


                        }
                    }

                }
                // establish a algorithm to make the first matched source word does not resembly the second one

                if (!prefix.equals("")&&!suffix.equals("")&&compute(prefix,suffix) < 0.85)
                {
                    blends_candidates.add(i);
                    tn -= 1;
                }

            }


            System.out.println("done");
            System.out.println(blends_candidates.size());

            float precision = 0;
            float recall = 0;


            for(String blend: blends)
            {
                if(blends_candidates.indexOf(blend) != -1)
                    System.out.println(blend+" found");
                else
                    System.out.println(blend);
            }

            int tp = 0;

            for(String candidate: blends_candidates)
            {
                if(blends.indexOf(candidate) != -1)
                {
                    precision += 1/(float)blends_candidates.size();
                    recall += 1/(float)blends.size();
                    tp += 1;
                }

            }

            System.out.println("accuracy: "+ (tp+tn+16684-compare_chile.size())/16684);

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
    private static double compute(final String s1, final String s2) {
        // lowest score on empty strings
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0;
        }
        // highest score on equal strings
        if (s1.equals(s2)) {
            return 1;
        }
        // some score on different strings
        int prefixMatch = 0; // exact prefix matches
        int matches = 0; // matches (including prefix and ones requiring transpostion)
        int transpositions = 0; // matching characters that are not aligned but close together
        int maxLength = Math.max(s1.length(), s2.length());
        int maxMatchDistance = Math.max((int) Math.floor(maxLength / 2.0) - 1, 0); // look-ahead/-behind to limit transposed matches
        // comparison
        final String shorter = s1.length() < s2.length() ? s1 : s2;
        final String longer = s1.length() >= s2.length() ? s1 : s2;
        for (int i = 0; i < shorter.length(); i++) {
            // check for exact matches
            boolean match = shorter.charAt(i) == longer.charAt(i);
            if (match) {
                if (i < 4) {
                    // prefix match (of at most 4 characters, as described by the algorithm)
                    prefixMatch++;
                }
                matches++;
                continue;
            }
            // check fro transposed matches
            for (int j = Math.max(i - maxMatchDistance, 0); j < Math.min(i + maxMatchDistance, longer.length()); j++) {
                if (i == j) {
                    // case already covered
                    continue;
                }
                // transposition required to match?
                match = shorter.charAt(i) == longer.charAt(j);
                if (match) {
                    transpositions++;
                    break;
                }
            }
        }
        // any matching characters?
        if (matches == 0) {
            return 0;
        }
        // modify transpositions (according to the algorithm)
        transpositions = (int) (transpositions / 2.0);
        // non prefix-boosted score
        double score = 0.3334 * (matches / (double) longer.length() + matches / (double) shorter.length() + (matches - transpositions)
                / (double) matches);
        if (score < 0.7) {
            return score;
        }
        // we already have a good match, hence we boost the score proportional to the common prefix
        double boostedScore = score + prefixMatch * 0.1 * (1.0 - score);
        return boostedScore;
    }

    private static String reversedString(String a)
    {
        StringBuilder sb = new StringBuilder();

        for(int i = a.length()-1;i>=0;i--)
        {

            sb.append(a.charAt(i));

        }

        return sb.toString();



    }


}
