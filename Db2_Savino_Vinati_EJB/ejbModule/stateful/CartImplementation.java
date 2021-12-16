package stateful;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import entities.Order;

@Stateful
public class CartImplementation implements CartService {
	private Order order;

	@PostConstruct
	public void initialize() {
		order = null;
	}

	@Override
	public void setOrder(Order order) {
		this.order = order;
		
	}

	@Override
	public Order getOrder() {
		return this.order;
		
	}

	@Remove
	@Override
	public void remove() {
		order = null;	
	}
}
