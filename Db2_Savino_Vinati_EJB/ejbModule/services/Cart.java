package services;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import entities.Order;
import exceptions.OrderException;

@Stateful
public class Cart {
	private Order order;
	
	public void initialize () throws OrderException{
		setOrder(null);	
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
