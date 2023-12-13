import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryReader {

//    public static void main(String[] args) {
//        List<Map<String,String>> queries = readQueriesFromJsonLines("C:\\Users\\HES\\Downloads\\topics.0720.utf8.jsonl.txt");
//        for (Map<String,String> query : queries) {
//        	System.out.println(query.get("text"));
//            System.out.println(query.get("human_russian"));
//            break;
//        }
//    }

    public static List<Map<String,String>> readQueriesFromJsonLines(String filePath) {
        List<Map<String,String>> queries = new ArrayList<Map<String,String>>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(line);
                String queryId=jsonObject.getString("topic_id");
                String query="";
                String human_chinese="";
                String human_russian="";
                String human_persian="";
                String query_expanded="";
                String machine_chinese="";
                String machine_russian="";
                String machine_persian="";
                
                JSONArray topicsArray = jsonObject.getJSONArray("topics");
                for (int i = 0; i < topicsArray.length(); i++) {
                    JSONObject topic = topicsArray.getJSONObject(i);
                    String lang= topic.getString("lang");
                    String src= topic.getString("source");
                    if (lang.equals("eng") && src.equals("original")) {
                    	query = topic.getString("topic_description");
                    	query_expanded= topic.getString("topic_narrative");
                    }else if (lang.equals("fas") && src.equals("human translation")) {
                    	human_persian=topic.getString("topic_description");
                    }else if (lang.equals("rus") && src.equals("human translation")) {
                    	human_russian= topic.getString("topic_description");
                    }else if (lang.equals("fas") && src.equals("google translation")) {
                    	machine_persian=topic.getString("topic_description");
                    }else if (lang.equals("rus") && src.equals("google translation")) {
                    	machine_russian=topic.getString("topic_description");
                    }
                    else {
                    	if (src.equals("human translation")) {
                    		human_chinese= topic.getString("topic_description");
                    	}else {
                    		machine_chinese=topic.getString("topic_description");
                    	}
                    	
                    }
                }
                Map<String, String> map=new HashMap<String, String>();
                map.put("id", queryId);
                map.put("text", query);
                map.put("query_expanded", query_expanded);
                map.put("human_persian", human_persian);
                map.put("human_chinese", human_chinese);
                map.put("human_russian", human_russian);
                map.put("machine_chinese", machine_chinese);
                map.put("machine_russian", machine_russian);
                map.put("machine_persian", machine_persian);
                queries.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queries;
    }
}
