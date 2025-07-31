package edu.neu.csye7374.strategy;

public class SimpleScoringStrategy implements ScoringStrategy {
    public int calculateScore(String[] userAnswers, String[] correctAnswers) {
        int score = 0;
        for (int i = 0; i < userAnswers.length; i++) {
            if (userAnswers[i].equalsIgnoreCase(correctAnswers[i])) {
                score++;
            }
        }
        return score;
    }
}
