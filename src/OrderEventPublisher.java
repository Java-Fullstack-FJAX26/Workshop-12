import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {
    private final List<OrderObserver> observers = new ArrayList<>();

    public void subscribe(OrderObserver observer) {
        observers.add(observer);
    }

    public void publish(OrderEvent event) {
        for (OrderObserver observer : observers) {
            try {
                observer.onOrderEvent(event);
            } catch (Exception e) {
                System.err.println("Fel vid notifiering av observatör: " + e.getMessage());
            }
        }
    }
}