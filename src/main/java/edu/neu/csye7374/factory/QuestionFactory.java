package edu.neu.csye7374.factory;

import edu.neu.csye7374.model.Question;

public class QuestionFactory {
    public static Question createQuestion(String type) {
        Question q = new Question();
        q.setType(type);
        switch (type) {
            case "MCQ":
                q.setOptions("A,B,C,D");
                break;
            case "TRUE_FALSE":
                q.setOptions("TRUE,FALSE");
                break;
            case "SHORT_ANSWER":
                q.setOptions(null);
                break;
            default:
                q.setOptions("N/A");
        }
        return q;
    }
}
