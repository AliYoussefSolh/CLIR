import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QueryBenchmark {
	
	public static Map<String,Float> FindTopTenDocs(String q, String indexPath, String languageCode){
		Map<String,Float> docs = new HashMap<String, Float>();
		StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexReader reader = null;
        IndexSearcher searcher = null;
        QueryParser parser = new QueryParser("content", analyzer);
        try {
        	reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
            searcher = new IndexSearcher(reader);
            searcher.setSimilarity(new BM25Similarity());
            String queryText=Translator.translateQuery(q,languageCode);
            System.out.println("tr "+queryText);
            org.apache.lucene.search.Query query = parser.parse(queryText);
            TopDocs topDocs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                int docId = scoreDoc.doc;
                Document document = searcher.doc(docId);
                String t= document.get("id");
                float score = scoreDoc.score;
                docs.put(t,score);
//                System.out.println(docId);
//                System.out.println(score); 
            }
        }catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return docs;
	}
	public static LinkedHashMap<String, Float> FindTopTenAcrossAllCorpuses(String query){
		String russian_index= "C:\\Users\\HES\\Desktop\\russian_index";
		String persian_index= "C:\\Users\\HES\\Desktop\\persian_index";
		String chinese_index= "C:\\Users\\HES\\Desktop\\chinese_index";
		Map<String,Float> russian_res=FindTopTenDocs(query,russian_index,"ru");
		Map<String,Float> persian_res=FindTopTenDocs(query,persian_index,"per");
//		System.out.println(persian_res);
		Map<String,Float> chinese_res=FindTopTenDocs(query,chinese_index,"zu");
		List<Map.Entry<String, Float>> combinedList = new ArrayList<>();
        combinedList.addAll(russian_res.entrySet());
        combinedList.addAll(persian_res.entrySet());
        combinedList.addAll(chinese_res.entrySet());
		Collections.sort(combinedList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
		//System.out.println(combinedList);
        // Create a new LinkedHashMap to store the top 10 entries in decreasing order of values
        LinkedHashMap<String, Float> topTenMap = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Float> entry : combinedList) {
            if (count >= 10) {
                break;
            }
           // System.out.println(entry);
            topTenMap.put(entry.getKey(), entry.getValue());
            count++;
        }
		return topTenMap;
		
	}
    public static void main(String[] args) {
        
//     // Specify the retrieval models
//        Similarity vsmSimilarity = new ClassicSimilarity(); // Vector Space Model
//        Similarity bm25Similarity = new BM25Similarity();
//        Similarity dirichletSimilarity = new LMDirichletSimilarity();
        // Initialize retrieval models
//        Scanner scan=new Scanner(System.in);
//        System.out.print("Enter the query:");
        //String queryText = scan.nextLine();
        //System.out.println(FindTopTenAcrossAllCorpuses(queryText));
    	List<Map<String,String>> queries= QueryReader.readQueriesFromJsonLines("C:\\Users\\HES\\Downloads\\topics.0720.utf8.jsonl.txt");
    	String queryText=queries.get(0).get("text");
    	System.out.println(queries.get(0).get("id"));
    	//System.out.println(queryText);
    	List<String> l=new ArrayList<String>();
        for (Map.Entry<String, Float> entry : FindTopTenAcrossAllCorpuses(queryText).entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            l.add(entry.getKey());
        }
       // System.out.println(l);
        List<Integer> TrustScores = RelevenceScroreReader.getTrustValuesForQuery(l,queries.get(0).get("id"));
        System.out.println("ndcg@10 is "+ NDCGCalculator.calculateNDCG(TrustScores, 10));
        List<List<Integer>> allScores= new ArrayList<List<Integer>>();
        allScores.add(TrustScores);
        System.out.println("mrr@10 is "+ MRRCalculator.calculateMRR(allScores));
        System.out.println("map@10 is "+ MAPCalculator.calculateMAP(allScores));
        
            
            
    }
}
