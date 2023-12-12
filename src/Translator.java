import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Translator {

    private static final String API_KEY = "AIzaSyDwFxIMWGrQuiDJ2aOQdzVOGGRo5eL2_Fk"; // Replace with your API key

//    public static void main(String[] args) {
//        String englishText = "Hello, my name is ali"; // Replace with your English text
//        String targetLanguage = "per"; // Change it to your target language code
//
//        try {
//            String translatedText = translateQuery(englishText, targetLanguage);
//            System.out.println("Translated Text: " + translatedText);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static String translateQuery(String text, String Language) throws IOException {
        String urlStr = "https://translation.googleapis.com/language/translate/v2?key=" + API_KEY;
        String targetLanguage = "ru";
        if (Language=="ru") {
        	targetLanguage="ru";
        }else if (Language=="per") {
        	targetLanguage= "fa";
        }else {
        	targetLanguage="zh-CN";
        }
        // Encode text to be translated
        String encodedText = URLEncoder.encode(text, "UTF-8");

        // Create the request URL with parameters
        URL url = new URL(urlStr + "&q=" + encodedText + "&target=" + targetLanguage);

        // Open connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Get the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        conn.disconnect();

        // Parse JSON response to get translated text
        return parseJSONResponse(response.toString());
    }

    public static String parseJSONResponse(String jsonResponse) {
        // Parse JSON to retrieve translated text
        String translatedText = "";
        try {
            // Parsing JSON response
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray translations = data.getJSONArray("translations");
            translatedText = translations.getJSONObject(0).getString("translatedText");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translatedText;
    }
}
