import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
public class JSONLReader {
	
	
	public static String readJsonAttribute(String jsonString, String attr) {
		JSONObject obj = new JSONObject(jsonString);
		String pageName = obj.getString(attr);
		return pageName;
	}

    public static List<String> readJSONLFile(String filePath) {
        List<String> jsonStrings = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonStrings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonStrings;
    }
    

//    public static void main(String[] args) {
//        String filePath = "C:\\Users\\HES\\Downloads\\corpus.jsonl";
//        List<String> jsonStrings = readJSONLFile(filePath);
//
//        // Print all JSON strings
//        for (String jsonString : jsonStrings) {
//            System.out.println(readJsonAttribute(jsonString));
//        }
//    }
}
