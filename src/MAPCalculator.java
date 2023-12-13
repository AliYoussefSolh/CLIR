import java.util.*;

public class MAPCalculator {

    // Function to calculate Average Precision for a single query
    private static double calculateAveragePrecision(List<Integer> trueRelevance) {
        double averagePrecision = 0.0;
        int relevantCount = 0;
        double precisionSum = 0.0;

        for (int i = 0; i < trueRelevance.size(); i++) {
            if (trueRelevance.get(i) > 0) {
                relevantCount++;
                precisionSum += (double) relevantCount / (i + 1); // Precision at this position
            }
        }

        if (relevantCount == 0) {
            return 0; // If no relevant documents found
        }

        averagePrecision = precisionSum / relevantCount;
        return averagePrecision;
    }

    // Function to calculate Mean Average Precision (MAP) for multiple queries
    public static double calculateMAP(List<List<Integer>> allQueriesRelevance) {
        double totalAveragePrecision = 0.0;
        int queryCount = 0;

        for (List<Integer> queryRelevance : allQueriesRelevance) {
            double averagePrecision = calculateAveragePrecision(queryRelevance);
            totalAveragePrecision += averagePrecision;
            queryCount++;
        }

        if (queryCount == 0) {
            return 0; // No queries
        }

        return totalAveragePrecision / queryCount;
    }

    public static void main(String[] args) {
        // Example relevance scores for multiple queries
        List<List<Integer>> allQueriesRelevance = new ArrayList<>();
        allQueriesRelevance.add(Arrays.asList(0, 1, 0, 2, 1, 0, 0, 1, 0, 1));
        allQueriesRelevance.add(Arrays.asList(1, 0, 0, 1, 2, 0, 1, 0, 0, 1));
        allQueriesRelevance.add(Arrays.asList(0, 1, 0, 0, 0, 1, 2, 1, 1, 1));

        MAPCalculator mapCalculator = new MAPCalculator();
        double map = mapCalculator.calculateMAP(allQueriesRelevance);

        System.out.println("MAP: " + map);
    }
}
