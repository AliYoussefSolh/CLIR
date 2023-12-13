import java.util.*;

public class NDCGCalculator {

    // Function to calculate NDCG
    public static double calculateNDCG(List<Integer> trueRelevance, int k) {
        double dcg = calculateDCG(trueRelevance, k);
        double idcg = calculateIdealDCG(trueRelevance, k);
        if (idcg == 0) {
            return 0; // To handle division by zero
        }
        // System.out.println(dcg);
        // System.out.println(idcg);
        return dcg / idcg;
    }

    // Function to calculate Discounted Cumulative Gain (DCG)
    public static double calculateDCG(List<Integer> trueRelevance, int k) {
        double dcg = 0.0;
        for (int i = 0; i < k && i < trueRelevance.size(); i++) {
            int rel = trueRelevance.get(i);
            dcg += (Math.pow(2, rel) - 1) / (Math.log(i + 2) / Math.log(2));
        }
        return dcg;
    }

    // Function to calculate Ideal DCG (IDCG)
    public static double calculateIdealDCG(List<Integer> trueRelevance, int k) {
        List<Integer> sortedRelevance = new ArrayList<>(trueRelevance);
        sortedRelevance.sort(Comparator.reverseOrder()); // Sort in descending order
        System.out.println(sortedRelevance);
        return calculateDCG(sortedRelevance, k);
    }

    public static void main(String[] args) {
        // Example true relevance scores for retrieved documents
        List<Integer> trueRelevance = Arrays.asList(3, 2, 1, 3, 0, 2, 1, 0, 0, 1);

        int k = 10; // Top k documents

        NDCGCalculator ndcgCalculator = new NDCGCalculator();
        double ndcg = ndcgCalculator.calculateNDCG(trueRelevance, k);

        System.out.println("NDCG@k: " + ndcg);
    }
}
