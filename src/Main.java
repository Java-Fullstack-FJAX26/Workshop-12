import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        OrderEventPublisher publisher = new OrderEventPublisher();
        publisher.subscribe(new AuditLogObserver());

        OrderEvent event = new OrderEvent("ORD-123", "CUST-456", 499.0, "CREATED", LocalDateTime.now());
        System.out.println("Publicerar orderhändelse: " + event.orderId());
        publisher.publish(event);
    }
}
