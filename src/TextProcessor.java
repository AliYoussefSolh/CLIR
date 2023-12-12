import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class TextProcessor {
    private Set<String> stopwords_ru;
    private Set<String> stopwords_zu;
    private Set<String> stopwords_per;

    public TextProcessor() {
        stopwords_ru = new HashSet<>();
        stopwords_zu = new HashSet<>();
        stopwords_per = new HashSet<>();
        setStopWords();
        // Add your custom stopwords to the HashSet, or you can load stopwords from a file.
    }
    private static Set<String> processFile(String filePath) {
        Set<String> wordSet = new HashSet<>();

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                // Assuming each word is on a separate line, you may need to modify this based on your file structure
                wordSet.add(line.trim());
            }
            
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return wordSet;}

    public void setStopWords(){
    	String[] filePaths = {"C:\\Users\\HES\\eclipse-workspace\\Lucine\\src\\short.txt", "C:\\Users\\HES\\eclipse-workspace\\Lucine\\src\\stopwords-ru.txt", "C:\\Users\\HES\\eclipse-workspace\\Lucine\\src\\stopwords-zh.txt"};
    	this.stopwords_per=processFile(filePaths[0]);
    	this.stopwords_ru=processFile(filePaths[1]);
    	this.stopwords_zu=processFile(filePaths[2]);
    }

    public String processText(String text, String target_language) {
        text = text.toLowerCase(); // Convert to lowercase
        StringBuilder result = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(text);
        Set<String> stopwords=null;
        if (target_language=="ru") {
        	stopwords=this.stopwords_ru;
        }else if (target_language=="zu") {
        	stopwords=this.stopwords_zu;
        }else {
        	stopwords=this.stopwords_per;
        }
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!stopwords.contains(token)) {
                result.append(token).append(" ");
            }
        }
        return result.toString();
    }
//    public static void main(String[] args) {
//    	TextProcessor tp=new TextProcessor();
//    	System.out.println(tp.processText("алло ali","ru"));
//    }
}
