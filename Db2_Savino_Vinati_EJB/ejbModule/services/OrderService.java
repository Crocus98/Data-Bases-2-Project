package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.time.DateUtils;

import entities.Activationschedule;
import entities.Alert;
import entities.MvBestproduct;
import entities.Optionalproduct;
import entities.Order;
import entities.Servicepackage;
import entities.User;
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
			Servicepackage servicepackage = em.find(Servicepackage.class, idservicepackage); //id exists?
			order.setServicepackage(servicepackage);
			Validityperiod validityperiod = em.find(Validityperiod.class, idvalidityperiod); //id exist?
			order.setValidityperiod(validityperiod);
			order.setStartdate(date);
			order.setOptionalproducts(new ArrayList<>());
			for (int i = 0; i < idoptionalproducts.size() ; i++) {
				Optionalproduct optionalproduct = em.find(Optionalproduct.class, idoptionalproducts.get(i)); //id exists?
				order.addOptionalProduct(optionalproduct);
				totalprice = totalprice + (optionalproduct.getMonthlyprice());
			}
			
			Map<Validityperiod,Float> valperiodmonthlycost = servicepackage.getValidityperiods();
			float cost = order.getServicepackage().getValidityperiods().get(validityperiod);
					valperiodmonthlycost.get(validityperiod);
			totalprice = totalprice + cost;
			totalprice = (totalprice*(order.getValidityperiod().getValidityperiod()));
			BigDecimal temp = new BigDecimal(totalprice);
			temp = temp.setScale(2, RoundingMode.HALF_UP);
			totalprice = Float.parseFloat(temp.toString());
			order.setTotalvalue(totalprice);
			return order;
		}
		catch(Exception e) {
			throw new BadOrder("Data for the creation of the order were wrong");
		}
	}
	
	


	public void createOrder(Order order) throws BadOrder, BadActivationSchedule, BadAlert {
		if (order.isPaid()) {
			createActivationSchedule(order);
		}
		else {
			checkInsolventUserAndAlert(order);
		}
		try {
			em.persist(order);
			em.flush();
		}catch(PersistenceException e) {
			if(order.getId() != 0) {
				throw new BadOrder("Could not create order");
			}
			else {
				throw new BadOrder("Could not update order");
			}

		}		
	}

	private void checkInsolventUserAndAlert(Order order) throws BadAlert, BadOrder {
		try {
			order.getUser().getInsolventuser().setInsolvent(true);
			order.getUser().getInsolventuser().setFailedpaymentcount(order.getUser().getInsolventuser().getFailedpaymentcount() + 1);
			if (order.getUser().getInsolventuser().getFailedpaymentcount() >= 3) {
				Alert alert = new Alert(order.getUser().getInsolventuser(),order.getTotalvalue(), order);
				order.getUser().getInsolventuser().getAlerts().add(alert);
			}
		}
		catch (Exception e) {
			throw new BadOrder("Could not check insolvent user or alert");
		}
	}

	private void createActivationSchedule(Order order) throws BadActivationSchedule{
		try {
			order.getUser().getInsolventuser().setFailedpaymentcount(0);
			order.getUser().getInsolventuser().setInsolvent(false);
			Date deactivationDate = DateUtils.addMonths(order.getStartdate(), order.getValidityperiod().getValidityperiod());	
			Activationschedule activationSchedule = new Activationschedule(order.getServicepackage().getServices(), order.getOptionalproducts(), order.getStartdate(), deactivationDate, order.getUser());
			if(order.getUser().getActivationschedules() == null) {
				order.getUser().setActivationschedules(new ArrayList<>());
			}
			order.getUser().addActivationschedule(activationSchedule);
			if(order.getId() != 0) {
				for(int i = 0; i< order.getUser().getInsolventuser().getAlerts().size(); i++) {
					if(order.getUser().getInsolventuser().getAlerts().get(i).isActive() &&
							order.getUser().getInsolventuser().getAlerts().get(i).getOrder().getId()==order.getId()) {
						order.getUser().getInsolventuser().getAlerts().get(i).setActive(false);
					}
				}
			}
		}
		catch(Exception e) {
			throw new BadActivationSchedule("Could not create activation schedule");
		}
	}

	public Order findOrderById(Integer orderid) {
		Order order = em.find(Order.class, orderid);
		return order;
	}
	

	public List<Order> findAllRejectedOrders(User user) {
		List<Order> rejectedOrders = em
				.createNamedQuery("Order.findRejectedOrders", Order.class)
				.setParameter(1, user)
				.getResultList();
		return rejectedOrders;
	}
}