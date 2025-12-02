package manager.models;

import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String FILEPATH = "data/high_scores.txt";
    private final Map<String, int[]> highScores = new HashMap<>();

    public HighScoreManager() {
        loadScore();
    }

    // if user does NOT exist, assign default scores
    public void createDefaultScores(String username) {
        if (!highScores.containsKey(username)) {
            highScores.put(username, new int[] { 1000, 1000 });
            saveScores();
        }
    }

    private void loadScore() {
        File file = new File(FILEPATH);
        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        try (Scanner scnr = new Scanner(file)) {
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String userName = parts[0];
                    int blackjack = Integer.parseInt(parts[1]);
                    int snakeGame = Integer.parseInt(parts[2]);
                    highScores.put(userName, new int[] { blackjack, snakeGame });
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateScore(String username, Integer blackjackScore, Integer snakeScore) {
        int[] current = highScores.getOrDefault(username, new int[] { 1000, 1000 });

        int newBlackjack = (blackjackScore != null)
                ? blackjackScore // always use the real time balance not max unlike snake
                : current[0];

        int newSnake = (snakeScore != null)
                ? Math.max(current[1], snakeScore)
                : current[1];

        highScores.put(username, new int[] { newBlackjack, newSnake });
        saveScores();
    }

    private void saveScores() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILEPATH))) {
            for (Map.Entry<String, int[]> map : highScores.entrySet()) {
                String userName = map.getKey();
                int[] scores = map.getValue();
                pw.println(userName + "," + scores[0] + "," + scores[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map.Entry<String, int[]>> getTop5Blackjack() {
        List<Map.Entry<String, int[]>> result = new ArrayList<>(highScores.entrySet());
        result.sort((a, b) -> b.getValue()[0] - a.getValue()[0]);
        if (result.size() > 5) {
            return result.subList(0, 5);
        }
        return result;
    }

    public List<Map.Entry<String, int[]>> getTop5SnakeGame() {
        List<Map.Entry<String, int[]>> result = new ArrayList<>(highScores.entrySet());
        result.sort((a, b) -> b.getValue()[1] - a.getValue()[1]);
        if (result.size() > 5) {
            return result.subList(0, 5);
        }
        return result;
    }

    public Map<String, int[]> getAllScores() {
        return highScores;
    }

    public int getBlackjackScore(String username) {
        return highScores.getOrDefault(username, new int[] { 1000, 1000 })[0];
    }

    public int getSnakeScore(String username) {
        return highScores.getOrDefault(username, new int[] { 1000, 1000 })[1];
    }

}
