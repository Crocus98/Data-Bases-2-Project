package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import entities.User;
import entities.Validityperiod;
import exceptions.BadActivationSchedule;
import exceptions.BadAlert;
import exceptions.BadOrder;
import exceptions.BadOrderParams;


@Stateless
public class OrderService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public OrderService() {
	}

	public Order createOrderNoPersist(Integer idservicepackage, Integer idvalidityperiod,
			List<Integer> idoptionalproducts, Date date) throws BadOrder, BadOrderParams {

		Order order = null;

		try {
			
			float totalprice = 0;
			order = new Order();
			
			Servicepackage servicepackage = checkServicePackage(idservicepackage);
			Validityperiod validityperiod = checkValidityPeriod (idvalidityperiod);
			order.setServicepackage(servicepackage);
			order.setValidityperiod(validityperiod);
			order.setStartdate(date);
			if(idoptionalproducts != null) {
				order.setOptionalproducts(new ArrayList<>());
				totalprice += checkOptionalProducts(idoptionalproducts, order, totalprice);
			}
			totalprice += order.getServicepackage().getValidityperiods().get(validityperiod);
			totalprice = (totalprice*(order.getValidityperiod().getValidityperiod()));
			BigDecimal temp = new BigDecimal(totalprice);
			temp = temp.setScale(2, RoundingMode.HALF_UP);
			totalprice = Float.parseFloat(temp.toString());
			order.setTotalvalue(totalprice);
			return order;
		}
		catch(Exception e) {
			throw new BadOrder("Data for the creation of the order is wrong");
		}
	}
	
	private Validityperiod checkValidityPeriod(Integer idvalidityperiod) throws BadOrderParams{
		try {
			Validityperiod validityperiod = em.find(Validityperiod.class, idvalidityperiod); //id exists?
			 if(validityperiod != null) {
				 return validityperiod;
			 }else {
				 return null;
			 }
		}catch(Exception e) {
			throw new BadOrderParams("Could not find the vailidty period");
		}
	}

	public Servicepackage checkServicePackage(Integer idservicepackage) throws BadOrderParams{
		try {
			 Servicepackage servicepackage = em.find(Servicepackage.class, idservicepackage); //id exists?
			 if(servicepackage != null) {
				 return servicepackage;
			 }else {
				 return null;
			 }
		}catch(Exception e) {
			throw new BadOrderParams("Could not find the service package");
		}
		
	}

	public float checkOptionalProducts(List<Integer> idoptionalproducts, Order order, float totalprice) throws BadOrderParams{
		try {
			for (int i = 0; i < idoptionalproducts.size() ; i++) {
				Optionalproduct optionalproduct = em.find(Optionalproduct.class, idoptionalproducts.get(i)); //id exists?
				order.addOptionalProduct(optionalproduct);
				totalprice = totalprice + (optionalproduct.getMonthlyprice());
			}
			 
		}catch(Exception e) {
			throw new BadOrderParams("Could not add the optional product/s");
		}
		
		return totalprice;
		
	}

	public void createOrder(Order order) throws BadOrder, BadActivationSchedule, BadAlert {
		User user = em.find(User.class, order.getUser().getId());
		if(user.getOrders() == null) {
			user.setOrders(new ArrayList<>());
		}
		if(order.getId() == 0) {
			user.addOrder(order);
		}
		else{
			user.updateOrder(order);
		}
		if (order.isPaid()) {
			createActivationSchedule(order, user);
		}
		else {
			checkInsolventUserAndAlert(order);
		}
		try {
			if(order.getId() == 0) {
				em.persist(user);
			}
			else {
				em.merge(user);
				
			}
			em.flush();
			em.refresh(user);
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
				if(order.getUser().getInsolventuser().getAlerts()== null){
					order.getUser().getInsolventuser().setAlerts(new ArrayList<>());
				}
				order.getUser().getInsolventuser().addAlert(alert);
			}
		}
		catch (Exception e) {
			throw new BadOrder("Could not check insolvent user or alert");
		}
	}

	private void createActivationSchedule(Order order, User user) throws BadActivationSchedule{
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
				user.getInsolventuser().updateToInactiveAlert(order);
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