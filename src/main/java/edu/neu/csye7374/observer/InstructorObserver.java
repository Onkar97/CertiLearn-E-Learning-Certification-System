package edu.neu.csye7374.observer;

public class InstructorObserver implements Observer {
    public void update(String message) {
        System.out.println("[Instructor Notification] " + message);
    }
}
