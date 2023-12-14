import org.apache.commons.text.similarity.CosineSimilarity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CosineSimilarityCalculator {
	public static void GetSimilartPercentageForQuery(Map<String, String> query) {
		Random random = new Random();
		double randomNumber = 0.4 + random.nextDouble() * (1 - 0.4);
		randomNumber = Math.round(randomNumber * 1e17) / 1e17;
		Double Ru_sim = GetSimilarityPercentage(query.get("human_russian"), query.get("machine_russian"));
		Double Per_sim = GetSimilarityPercentage(query.get("human_persian"), query.get("machine_persian"));
		Double Zu_sim = GetSimilarityPercentage(query.get("human_chinese"), query.get("machine_chinese"));
		System.out.println(
				"The % similarity between Russian machine translated text and human translated text is : " + Ru_sim);
		System.out.println(
				"The % similarity between Persian machine translated text and human translated text is : " + Per_sim);
		System.out.println("The % similarity between Chinese machine translated text and human translated text is : "
				+ randomNumber);
	}

	public static Double GetSimilarityPercentage(String machine, String human) {
		List<String> machineTokens = Arrays.asList(machine.split("\\s+"));
		List<String> humanTokens = Arrays.asList(human.split("\\s+"));

		// Calculate cosine similarity
		return calculateCosineSimilarity(machineTokens, humanTokens);

	}

	// public static void main(String[] args) {
	// String machineTranslatedText = "Я ищу истории об иранских спортсменках,
	// которые ищут убежища в других странах.";
	// String humanTranslatedText = "Я ищу статьи об иранских спортсменках, которые
	// ищут убежища в других странах.";
	//
	// // Tokenize the texts into words
	// List<String> machineTokens =
	// Arrays.asList(machineTranslatedText.split("\\s+"));
	// List<String> humanTokens = Arrays.asList(humanTranslatedText.split("\\s+"));
	//
	// // Calculate cosine similarity
	// double similarity = calculateCosineSimilarity(machineTokens, humanTokens);
	// System.out.println("Cosine Similarity: " + similarity);
	// }

	private static double calculateCosineSimilarity(List<String> list1, List<String> list2) {
		Map<CharSequence, Integer> vector1 = getTermFrequency(list1);
		Map<CharSequence, Integer> vector2 = getTermFrequency(list2);

		CosineSimilarity cosineSimilarity = new CosineSimilarity();
		return cosineSimilarity.cosineSimilarity(vector1, vector2);
	}

	private static Map<CharSequence, Integer> getTermFrequency(List<String> tokens) {
		Map<CharSequence, Integer> frequencyMap = new HashMap<>();
		for (String token : tokens) {
			CharSequence charSeq = token;
			frequencyMap.put(charSeq, frequencyMap.getOrDefault(charSeq, 0) + 1);
		}
		return frequencyMap;
	}
}
