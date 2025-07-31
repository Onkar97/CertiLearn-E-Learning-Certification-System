package edu.neu.csye7374.service;

import edu.neu.csye7374.model.Question;
import edu.neu.csye7374.repository.QuestionRepository;
import edu.neu.csye7374.strategy.ScoringStrategy;
import edu.neu.csye7374.strategy.SimpleScoringStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;

    public int evaluateQuiz(Long quizId, String[] userAnswers) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        String[] correctAnswers = questions.stream().map(Question::getCorrectAnswer).toArray(String[]::new);

        ScoringStrategy strategy = new SimpleScoringStrategy(); // Can be replaced with others dynamically
        return strategy.calculateScore(userAnswers, correctAnswers);
    }
}
