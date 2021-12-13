package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

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
		Order order = new Order();
		Float totalprice1 = null;
		Float totalprice2 = null;
		Float totalpriceoptionalproducts = null;
		Float totalvalue = null;
		
		try {
			Servicepackage servicepackage = em.find(Servicepackage.class, idservicepackage);
			order.setServicepackage(servicepackage);
			System.out.println(order.getServicepackage().getName());
			
			for (int i = 0; i < idoptionalproducts.size() ; i++) {
				Optionalproduct optionalproduct = em.find(Optionalproduct.class, idoptionalproducts.get(i));
				order.addOptionalProduct(optionalproduct);
				totalpriceoptionalproducts = totalpriceoptionalproducts + ( optionalproduct.getMonthlyprice());
			}
		
			Validityperiod validityperiod = em.find(Validityperiod.class, idvalidityperiod);
			order.setValidityperiod(validityperiod);
			
			order.setStartdate(date);
			
			// calc totvalue to be paid
			Map<Validityperiod,Float> valperiodmonthlycost = servicepackage.getValidityperiods();
			Float cost = valperiodmonthlycost.get(validityperiod);
			
			totalprice1 = (validityperiod.getValidityperiod())*(cost);
			totalprice2 = (validityperiod.getValidityperiod())*(totalpriceoptionalproducts);
			totalvalue = totalprice1 + totalprice2;
			
			order.setTotalvalue(totalvalue);
			
			
			//TODO
			//Fare il find singolo per ogni idoptionalproduct e aggiungerli uno ad uno all'order
			//Fare il find singolo del validityperiod e aggiungerlo all'order
			//Calcolare il total value
			//Inserire tutto dentro all'ordine
			//Inserire le date necessarie
			// order.setServicepackage(servicepackage); //Controllare se ho aggiornato entrambi i lati se servisse (non dovrebbe serivire)
			return order;
		}
		catch(PersistenceException e) {
			throw new BadOrder("Data for the creation of the order were wrong");
		}
	}
}