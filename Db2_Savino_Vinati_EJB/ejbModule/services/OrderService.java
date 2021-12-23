package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.time.DateUtils;

import entities.Activationschedule;
import entities.Alert;
import entities.Optionalproduct;
import entities.Order;
import entities.Servicepackage;
import entities.Validityperiod;
import exceptions.BadActivationSchedule;
import exceptions.BadAlert;
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
	
	


	public void createOrder(Order order) throws BadOrder, BadActivationSchedule, BadAlert {
		
		
		
		// creare ordine
		if (order.isPaid()) {
			createActivationSchedule(order);
		}
		else {
			checkInsolventUserAndAlert(order);
		}
		try {
			em.persist(order);
		}catch(PersistenceException e) {
			throw new BadOrder("Could not create order");
		}		
	}

	private void checkInsolventUserAndAlert(Order order) throws BadAlert, BadOrder {
		try {
			order.getUser().getInsolventuser().setInsolvent(true);
			order.getUser().getInsolventuser().setFailedpaymentcount(order.getUser().getInsolventuser().getFailedpaymentcount() + 1);
			if (order.getUser().getInsolventuser().getFailedpaymentcount() >= 3) {
				Alert alert = new Alert(order.getUser(), order.getTotalvalue(), order);
				order.getUser().getInsolventuser().getAlerts().add(alert);
			}
		}
		catch (Exception e) {
			throw new BadOrder("Could not check insolvent user or alert");
		}
	}

	private void createActivationSchedule(Order order) throws BadActivationSchedule{
		try {
			Date deactivationDate = DateUtils.addMonths(order.getStartdate(), order.getValidityperiod().getValidityperiod());			
			Activationschedule activationSchedule = new Activationschedule(order.getServicepackage().getServices(), order.getOptionalproducts(), order.getStartdate(), deactivationDate, order.getUser());
			order.getUser().getActivationschedules().add(activationSchedule);
		}
		catch(Exception e) {
			throw new BadActivationSchedule("Could not create activation schedule");
		}
	}

	public Order findOrderById(Integer orderid) {
		Order order = em.find(Order.class, orderid);
		return order;
	}
}