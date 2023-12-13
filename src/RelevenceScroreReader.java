import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelevenceScroreReader {
//	public static void main(String[] args) {
//		System.out.println(getTrustValues().get("45"));
//	}
	
	public static List<Integer> getTrustValuesForQuery(List<String> docs, String query_id){
		Map<String, List<Map<String, String>>> all= getTrustValues();
		List<Integer> res=new ArrayList<Integer>();
		List<Map<String, String>> query_specific = all.get(query_id);
		//System.out.println(query_specific);
		for (String s : docs) {
			for (Map<String, String> record : query_specific) {
			    if (s.equals(record.get("doc_id"))){
//			    	System.out.println("in");
			    	res.add(Integer.parseInt(record.get("score")));
			    	break;
			    }
			}
			
		}
		
		return res;
		
	}

    public static Map<String, List<Map<String, String>>> getTrustValues() {
        String filePath = "C:\\Users\\HES\\eclipse-workspace\\Lucine\\src\\relevanceScores.txt";
        List<Map<String, String>> resultList = parseFile(filePath);
        //System.out.println(groupById(resultList));
//        for (Map<String, String> map : resultList) {
//            System.out.println(map);
//            break;
//        }
        return groupById(resultList);
    }

    public static List<Map<String, String>> parseFile(String filePath) {
        List<Map<String, String>> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");

                if (parts.length >= 4) {
                    String id = parts[0];
                    String docId = parts[2];
                    String score = parts[3];

                    Map<String, String> map = new HashMap<>();
                    map.put("id", id);
                    map.put("doc_id", docId);
                    map.put("score", score);

                    result.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static Map<String, List<Map<String, String>>> groupById(List<Map<String, String>> inputList) {
        Map<String, List<Map<String, String>>> groupedMap = new HashMap<>();

        for (Map<String, String> map : inputList) {
            String id = map.get("id");
            String docId = map.get("doc_id");
            String score = map.get("score");

            // Check if the ID already exists in the groupedMap
            if (groupedMap.containsKey(id)) {
                List<Map<String, String>> existingList = groupedMap.get(id);
                Map<String, String> newMap = new HashMap<>();
                newMap.put("doc_id", docId);
                newMap.put("score", score);
                existingList.add(newMap);
            } else {
                List<Map<String, String>> newList = new ArrayList<>();
                Map<String, String> newMap = new HashMap<>();
                newMap.put("doc_id", docId);
                newMap.put("score", score);
                newList.add(newMap);
                groupedMap.put(id, newList);
            }
        }
        return groupedMap;
    }
}
