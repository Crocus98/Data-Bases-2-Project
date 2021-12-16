package stateful;

import javax.ejb.Local;

import entities.Order;

@Local
public interface CartService {
	public void setOrder(Order order);
	public Order getOrder();
	public void remove();
}
