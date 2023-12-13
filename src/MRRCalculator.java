import java.util.*;

public class MRRCalculator {

    // Function to calculate MRR for multiple queries
    public static  double calculateMRR(List<List<Integer>> allQueriesRelevance) {
        double totalMRR = 0.0;
        int queryCount = 0;

        for (List<Integer> trueRelevance : allQueriesRelevance) {
            int rank = 1;
            for (Integer rel : trueRelevance) {
                if (rel > 0) {
                    totalMRR += 1.0 / rank; // Add reciprocal of the rank
                    break; // Break once the first relevant document is found
                }
                rank++;
            }
            queryCount++;
        }

        if (queryCount == 0) {
            return 0; // No queries
        }

        return totalMRR / queryCount;
    }

    public static void main(String[] args) {
        // Example relevance scores for multiple queries
        List<List<Integer>> allQueriesRelevance = new ArrayList<>();
        allQueriesRelevance.add(Arrays.asList(0, 0, 1, 0, 2, 0, 1, 0, 0, 3));
        allQueriesRelevance.add(Arrays.asList(1, 0, 0, 0, 2, 0, 1, 0, 0, 3));
        allQueriesRelevance.add(Arrays.asList(0, 1, 0, 0, 0, 0, 2, 0, 0, 3));

        MRRCalculator mrrCalculator = new MRRCalculator();
        double mrr = mrrCalculator.calculateMRR(allQueriesRelevance);

        System.out.println("MRR: " + mrr);
    }
}
