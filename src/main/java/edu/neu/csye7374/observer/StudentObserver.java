package edu.neu.csye7374.observer;

public class StudentObserver implements Observer {
    public void update(String message) {
        System.out.println("[Student Notification] " + message);
    }
}
