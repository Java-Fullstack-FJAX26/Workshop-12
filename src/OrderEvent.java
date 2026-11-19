import java.time.LocalDateTime;

public record OrderEvent(String orderId, String customerId, double totalAmount, String status, LocalDateTime timestamp) {}