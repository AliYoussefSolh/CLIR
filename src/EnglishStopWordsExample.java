import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

public class EnglishStopWordsExample {
    public static void main(String[] args) {
        // Get the English stop words set from Lucene's EnglishAnalyzer
    	Iterator iter = EnglishAnalyzer.getDefaultStopSet().iterator();
        Set<String> stopWords = new HashSet<>();
        while(iter.hasNext()) {
            char[] stopWord = (char[]) iter.next();
            stopWords.add(new String (stopWord));
        }
        
    }
}
