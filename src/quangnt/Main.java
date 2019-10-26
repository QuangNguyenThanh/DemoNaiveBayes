package quangnt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author QuangNT
 * The Class Main.
 */
public class Main {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        // List data training
        List<DataRow> dataTraining = new ArrayList<>();

        dataTraining.add(new DataRow("d1", "hanoi pho chaolong hanoi", "B"));
        dataTraining.add(new DataRow("d2", "hanoi buncha pho omai", "B"));
        dataTraining.add(new DataRow("d3", "pho banhgio omai", "B"));
        dataTraining.add(new DataRow("d4", "saigon hutiu banhbo pho", "N"));
        dataTraining.add(new DataRow("d5", "saigon banhpia banhtet pho", "N"));
        dataTraining.add(new DataRow("d6", "hutiu banhtet thitkho", "N"));
        dataTraining.add(new DataRow("d7", "banhpia banhxeo banhtet saigon", "N"));
        dataTraining.add(new DataRow("d8", "bunrieu saigon saigon canhchua", "N"));
        dataTraining.add(new DataRow("d9", "hutiu banhpia banhtrangtron", "N"));
        dataTraining.add(new DataRow("d10", "thitkho banhxeo banhtrangtron", "N"));
        dataTraining.add(new DataRow("d11", "mientrung caolau miquang bunbo", "T"));
        dataTraining.add(new DataRow("d12", "mientrung caolau banhcanh comgo", "T"));
        dataTraining.add(new DataRow("d13", "comhen caolau miquang bunbo", "T"));
        dataTraining.add(new DataRow("d14", "comhen caolau miquang mientrung buncha", "T"));
        dataTraining.add(new DataRow("d15", "comhen caolau comhen comga", "T"));
        dataTraining.add(new DataRow("d16", "comhen caolau mientrung banhtet", "T"));
        dataTraining.add(new DataRow("d17", "xoixeo pho hanoi bunngan", "B"));
        dataTraining.add(new DataRow("d18", "xoixeo bunmoc hanoi bunngan", "B"));

        Set<String> bagOfWords = getBagOfWords(dataTraining);
        Set<String> listResult = getListResult(dataTraining);
        List<Integer> listCountResult = getListCountResult(dataTraining, listResult);
        List<int[]> listVector = getListVector(dataTraining, listResult, bagOfWords);
        System.out.println("ListResult: " + listResult);
        System.err.println("List count result: " + listCountResult);
        System.out.println("Bag of words: " + bagOfWords);

        System.out.println("List vector:");
        for (int i = 0; i < listVector.size(); i++) {
            for (int j = 0; j < listVector.get(i).length; j++) {
                System.out.print(listVector.get(i)[j] + " ");
            }
            System.out.println();
        }

        // List data test
        DataRow dataTest1 = new DataRow("d", "hanoi hanoi buncha hutiu");
        DataRow dataTest2 = new DataRow("d", "pho hutiu banhbo");
        DataRow dataTest3 = new DataRow("d", "mientrung caolau pho hutiu buncha");

        List<Double> predicts = getPredict(listVector, bagOfWords, listResult, listCountResult, dataTraining.size(),
                dataTest2);
        String result = findResult(listResult, predicts);
        System.out.println("Predict: " + predicts);
        System.out.println("Result: " + result);
    }

    /**
     * Find result.
     *
     * @param listResult the list result
     * @param predicts the predicts
     * @return the string
     */
    private static String findResult(Set<String> listResult, List<Double> predicts) {
        int dirMax = findMaxPredict(predicts);
        return new ArrayList<>(listResult).get(dirMax);
    }

    /**
     * Find max predict.
     *
     * @param predicts the predicts
     * @return the int
     */
    private static int findMaxPredict(List<Double> predicts) {
        double max = predicts.get(0);
        int dirMax = 0;
        for (int i = 1; i < predicts.size(); i++) {
            if (max < predicts.get(i)) {
                max = predicts.get(i);
                dirMax = i;
            }
        }
        return dirMax;
    }

    /**
     * Gets the predict.
     *
     * @param listVector the list vector
     * @param bagOfWords the bag of words
     * @param listResult the list result
     * @param listCountResult the list count result
     * @param dataSize the data size
     * @param dataTest the data test
     * @return the predict
     */
    private static List<Double> getPredict(List<int[]> listVector, Set<String> bagOfWords, Set<String> listResult,
            List<Integer> listCountResult, int dataSize, DataRow dataTest) {
        List<Double> predicts = new ArrayList<Double>();
        String[] words = dataTest.content.split(" ");
        List<Integer> indexes = new ArrayList<Integer>();
        for (String word : words) {
            int index = new ArrayList<>(bagOfWords).indexOf(word);
            if (index != -1) {
                indexes.add(index);
            }
        }
        for (String result : listResult) {
            int i = new ArrayList<>(listResult).indexOf(result);
            int[] vector = listVector.get(i);
            int total = sumVector(vector) + bagOfWords.size();
            double predict = (double) listCountResult.get(i) / dataSize;
            for (int index : indexes) {
                predict *= ((double) (vector[index] + 1) / total);
            }
            predicts.add(predict);
        }
        double sumPredict = 0;
        for (double predict : predicts) {
            sumPredict += predict;
        }
        List<Double> finalPredicts = new ArrayList<Double>();
        for (double predict : predicts) {
            double p = predict / sumPredict;
            finalPredicts.add(p);
        }
        return finalPredicts;
    }

    /**
     * Sum vector.
     *
     * @param vector the vector
     * @return the int
     */
    private static int sumVector(int[] vector) {
        int sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    /**
     * Gets the list count result.
     *
     * @param dataTraining the data training
     * @param listResult the list result
     * @return the list count result
     */
    private static List<Integer> getListCountResult(List<DataRow> dataTraining, Set<String> listResult) {
        List<Integer> listCountResult = new ArrayList<Integer>();
        for (String result : listResult) {
            int count = 0;
            for (DataRow row : dataTraining) {
                if (row.type == result) {
                    count++;
                }
            }
            listCountResult.add(count);
        }
        return listCountResult;
    }

    /**
     * Gets the list vector.
     *
     * @param dataTraining the data training
     * @param listResult the list result
     * @param bagOfWords the bag of words
     * @return the list vector
     */
    private static List<int[]> getListVector(List<DataRow> dataTraining, Set<String> listResult,
            Set<String> bagOfWords) {
        List<int[]> listVector = new ArrayList<int[]>();
        for (String result : listResult) {
            int[] vector = new int[bagOfWords.size()];
            for (DataRow row : dataTraining) {
                if (row.type == result) {
                    String[] words = row.content.split(" ");
                    for (String word : words) {
                        int index = new ArrayList<>(bagOfWords).indexOf(word);
                        if (index != -1) {
                            vector[index]++;
                        }
                    }
                }
            }
            listVector.add(vector);
        }
        return listVector;
    }

    /**
     * Gets the list result.
     *
     * @param dataTraining the data training
     * @return the list result
     */
    private static Set<String> getListResult(List<DataRow> dataTraining) {
        Set<String> listResult = new HashSet<String>();
        for (DataRow row : dataTraining) {
            if (!listResult.contains(row.type)) {
                listResult.add(row.type);
            }
        }
        return listResult;
    }

    /**
     * Gets the bag of words.
     *
     * @param dataTraining the data training
     * @return the bag of words
     */
    private static Set<String> getBagOfWords(List<DataRow> dataTraining) {
        Set<String> bagOfWords = new HashSet<String>();
        for (DataRow row : dataTraining) {
            String[] words = row.content.split(" ");
            for (String word : words) {
                if (!bagOfWords.contains(word)) {
                    bagOfWords.add(word.toLowerCase());
                }
            }
        }
        return bagOfWords;
    }
}
