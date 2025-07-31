package edu.neu.csye7374.service;

import edu.neu.csye7374.observer.NotificationService;
import edu.neu.csye7374.observer.Observer;
import org.springframework.stereotype.Service;

@Service
public class NotificationDispatchService {
    private final NotificationService notificationService = new NotificationService();

    public void register(Observer observer) {
        notificationService.registerObserver(observer);
    }

    public void send(String message) {
        notificationService.notifyObservers(message);
    }
}
