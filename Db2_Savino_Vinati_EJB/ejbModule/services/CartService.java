package services;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import entities.Order;

@Stateful
public class CartService {
	private Order order;
	
	public void initialize () {
		setOrder(null);	
	}
	
	public void initialize (Order order) {
		setOrder(order);	
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Remove
	public void remove() {
		setOrder(null);
	}
}
