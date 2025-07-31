package edu.neu.csye7374.strategy;

public class KeywordScoringStrategy implements ScoringStrategy {
    public int calculateScore(String[] userAnswers, String[] correctAnswers) {
        int score = 0;
        for (int i = 0; i < userAnswers.length; i++) {
            if (correctAnswers[i].contains(userAnswers[i])) {
                score++;
            }
        }
        return score;
    }
}
