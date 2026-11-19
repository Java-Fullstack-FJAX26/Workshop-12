@FunctionalInterface
public interface OrderObserver {
    void onOrderEvent(OrderEvent event);
}

