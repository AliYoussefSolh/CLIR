import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class IndexingDemo {
    public static void main(String[] args) {
        String corpusPath_Chinese = "C:\\Users\\HES\\Downloads\\chinese_corpus.jsonl"; // Set the path to your HTML corpus directory
        String corpusPath_Persian = "C:\\Users\\HES\\Downloads\\persian_corpus.jsonl";
        String corpusPath_Russian = "C:\\Users\\HES\\Downloads\\russian_corpus.jsonl";
        String indexPath_Chinese = "C:\\Users\\HES\\Desktop\\chinese_index"; // Set the path where you want to store the indexes
        String indexPath_Russian = "C:\\Users\\HES\\Desktop\\russian_index";
        String indexPath_Persian = "C:\\Users\\HES\\Desktop\\persian_index";
        // Create an instance of the text processor
        TextProcessor textProcessor = new TextProcessor();

        

        // Index using VSM
        createIndex(corpusPath_Chinese, indexPath_Chinese, textProcessor,"zu");
        createIndex(corpusPath_Persian, indexPath_Persian, textProcessor,"per");
        createIndex(corpusPath_Russian, indexPath_Russian, textProcessor,"ru");

        // Index using BM25
//        createIndex(corpusPath, indexPath + "_bm25", textProcessor, bm25Similarity);

        // Index using Dirichlet Smoothing
//        createIndex(corpusPath, indexPath + "_dirichlet", textProcessor, dirichletSimilarity);
    }
    

    public static void createIndex(String corpusPath, String indexPath, TextProcessor textProcessor, String target_language) {
        try {
            Directory indexDir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE); 
            IndexWriter writer = new IndexWriter(indexDir, config); 
            List<String> files =JSONLReader.readJSONLFile(corpusPath);
            if (files != null) {
                for (String file : files) {
                    String textContent = JSONLReader.readJsonAttribute(file,"text");
                    String TextContentFromatted=textProcessor.processText(textContent, target_language);
                    Document doc = new Document();
                    doc.add(new TextField("content", TextContentFromatted, Field.Store.NO));
                    doc.add(new StringField("id",JSONLReader.readJsonAttribute(file,"id") , Field.Store.YES));
                    writer.addDocument(doc);
                }
            }

            // Close the index writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
