package it.beergash.tennis.data.scanner.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchUtils {

    private static final List<String> GAME_SCORES = Arrays.asList("0-15", "0-30", "0-40", "15-0", "15-15", "15-30", "15-40", "30-0",
                                                    "30-15", "30-30", "30-40", "40-0", "40-15", "40-30", "40-40");

    private static final List<String> WRONG_SCORES = Arrays.asList("Mar", "Feb", "Jun", "Jul", "Jan", "Apr", "May", "RET", "W/O", "ABD", "NA", "DEF", "[");

    public static int getGamesNumber(String score) {
        if (StringUtils.isEmpty(score) ) {
            return 0;
        }
            return Arrays.asList(score.split(" "))
                    .stream()
                    .filter(s -> s.matches(".*\\d.*") && !WRONG_SCORES.stream().anyMatch(w -> StringUtils.containsIgnoreCase(score, w)))
                    .map(s -> s.contains("(") ? s.substring(0, s.indexOf("(")) : s)
                    .map(s -> s.contains("[") ? 0 : Integer.valueOf(s.split("-")[0]) + Integer.valueOf(s.split("-")[1]))
                    .reduce(0, Integer::sum);
    }


    /**
     * return list of games number per set and player (Example 6-4 6-4 returns {6,4,6,4})
     * @param score
     * @return
     */
    public static List<Integer> getGamesList(String score) {
        List<Integer> result = new ArrayList<>();
        if (StringUtils.isEmpty(score)) {
            return result;
        }
        Arrays.asList(score.split(" "))
                .stream()
                .filter(s -> s.matches(".*\\d.*") && !WRONG_SCORES.stream().anyMatch(w -> StringUtils.containsIgnoreCase(score, w)))
                .map(s -> s.contains("(") ? s.substring(0, s.indexOf("(")) : s)
                .forEach(s -> {
                    String[] splt = s.split("-");
                    for (String spl : splt) {
                        spl = cleanData(spl);
                            result.add(Integer.valueOf(spl));
                    }
                });
        return result;
    }

    private static String cleanData(String input) {
        return input.replace("?", "");
    }
}
