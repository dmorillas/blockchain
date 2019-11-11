import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String keywords = bufferedReader.readLine();

        int hotel_idsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> hotel_idsTemp = new ArrayList<>();

        IntStream.range(0, hotel_idsCount).forEach(i -> {
            try {
                hotel_idsTemp.add(bufferedReader.readLine().replaceAll("\\s+$", ""));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> hotel_ids = hotel_idsTemp.stream()
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        int reviewsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> reviews = new ArrayList<>();

        IntStream.range(0, reviewsCount).forEach(i -> {
            try {
                reviews.add(bufferedReader.readLine());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> res = sort_hotels(keywords, hotel_ids, reviews);

        bufferedWriter.write(
                res.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }

    // Complete the howManyAgentsToAdd function below.
    // Complete the sort_hotels function below.
    static List<Integer> sort_hotels(String keywords, List<Integer> hotel_ids, List<String> reviews) {
        String[] kw = keywords.split(" ");
        ArrayList<Integer> list = new ArrayList<>();

        for(String review : reviews){
            int occurrences = 0;

            for(String k : kw){
                if(review.contains(k))
                    occurrences++;
            }

            list.add(occurrences);
        }

        return list.stream().distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    }
}
