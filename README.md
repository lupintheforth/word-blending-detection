# word-blending-detection

target: tring to find a more reliable algorithmn for identifying blends

(The main frame of the program is unchanged but the parameter using in the judgement and data cleaning process may be varied)

The program first read in all the files including dictionary, candidates and blends data and the useful
data are extracted. The candidates are stores in the arraylist named compare_child while the dictionary words in the comapre_parent.The place to hold the true blends is named blends.

The data cleaning process will exclude the words longer than 15 chars and shorter than 2 chars. A word consists of too many duplicated chars are also excluded.

The remaining ones are stored in compare_chile.

Then, the matching process starts. For each word in the compare_chile, compare the word with the words in the dictionary having the same starting char. Using jaro-winkler or GED, the prefix will be sorted out in those words.

After finding the "prefix" , the process will start another loop to find the matching suffix words in the dictioanry with the same last char.

if the prefix and suffix are different from the initial state, the identified candidate will be added to the blends_candidates list.

\\ the Jaro-winkler method is sourced from Désilets, Alain url = https://github.com/
    alaindesilets/iutools
    /blob/d5c825d630429f337e553
    235f040533452b1afe9/
    java/iutools-core/src/
    main/java/ca/inuktitutcomputing/
    utilities/JaroWinkler.java
