package edu.neu.csye7374.command;

public class SubmitQuizCommand implements Command {
    public void execute() {
        System.out.println("Quiz submitted");
    }

    public void undo() {
        System.out.println("Quiz submission undone");
    }
}
