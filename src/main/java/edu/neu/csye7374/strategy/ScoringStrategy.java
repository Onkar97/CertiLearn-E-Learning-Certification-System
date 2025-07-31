package edu.neu.csye7374.strategy;

public interface ScoringStrategy {
    int calculateScore(String[] userAnswers, String[] correctAnswers);
}
