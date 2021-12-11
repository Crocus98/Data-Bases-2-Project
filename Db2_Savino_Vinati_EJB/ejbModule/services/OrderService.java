package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.Order;
import entities.Servicepackage;
import exceptions.BadOrder;


@Stateless
public class OrderService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public OrderService() {
	}

	public Order createOrderNoPersist(Integer idservicepackage, Integer idvalidityperiod,
			List<Integer> idoptionalproducts, Date date) throws BadOrder {
		Order order = new Order();
		try {
			Servicepackage servicepackage = em.find(Servicepackage.class, idservicepackage);
			//TODO
			//Fare il find singolo per ogni idoptionalproduct e aggiungerli uno ad uno all'order
			//Fare il find singolo del validityperiod e aggiungerlo all'order
			//Calcolare il total value
			//Inserire tutto dentro all'ordine
			//Inserire le date necessarie
			order.setServicepackage(servicepackage); //Controllare se ho aggiornato entrambi i lati se servisse (non dovrebbe serivire)
			return order;
		}
		catch(PersistenceException e) {
			throw new BadOrder("Data for the creation of the order were wrong");
		}
	}
}