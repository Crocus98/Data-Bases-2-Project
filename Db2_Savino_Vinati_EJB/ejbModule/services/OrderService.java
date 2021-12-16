package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Optionalproduct;
import entities.Order;
import entities.Servicepackage;
import entities.Validityperiod;
import exceptions.BadOrder;


@Stateless
public class OrderService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public OrderService() {
	}

	public Order createOrderNoPersist(Integer idservicepackage, Integer idvalidityperiod,
			List<Integer> idoptionalproducts, Date date) throws BadOrder {

		Order order = null;

		try {
			//Controlls missing
			float totalprice = 0;
			order = new Order();
			Servicepackage servicepackage = em.find(Servicepackage.class, idservicepackage);
			order.setServicepackage(servicepackage);
			Validityperiod validityperiod = em.find(Validityperiod.class, idvalidityperiod);
			order.setValidityperiod(validityperiod);
			order.setStartdate(date);
			order.setOptionalproducts(new ArrayList<>());
			for (int i = 0; i < idoptionalproducts.size() ; i++) {
				Optionalproduct optionalproduct = em.find(Optionalproduct.class, idoptionalproducts.get(i));
				order.addOptionalProduct(optionalproduct);
				totalprice = totalprice + (optionalproduct.getMonthlyprice());
			}
			
			Map<Validityperiod,Float> valperiodmonthlycost = servicepackage.getValidityperiods();
			Float cost = order.getServicepackage().getValidityperiods().get(validityperiod);
					valperiodmonthlycost.get(validityperiod);
			totalprice = totalprice + cost;
			order.setTotalvalue((totalprice*(order.getValidityperiod().getValidityperiod())));
			
			return order;
		}
		catch(Exception e) {
			throw new BadOrder("Data for the creation of the order were wrong");
		}
	}
}