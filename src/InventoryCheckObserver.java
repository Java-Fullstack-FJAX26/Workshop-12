public class InventoryCheckObserver implements OrderObserver{

    @Override
    public void onOrderEvent(OrderEvent event) {
        IO.println("Decreasing inventory of items in: "+event.orderId());
        // Find the order in order database on ID. 
        // Decrease amount in inventory by number of items ordered.
    }
    
}
