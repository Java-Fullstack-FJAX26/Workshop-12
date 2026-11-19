public class AuditLogObserver implements OrderObserver {
    @Override
    public void onOrderEvent(OrderEvent event) {
        System.out.println("[AUDIT LOGG] Order händelse registrerad: " + event.orderId() + " Status: " + event.status());
    }
}
