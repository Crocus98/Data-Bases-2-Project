package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OrderService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public OrderService() {
	}
	
	/*public void createOrder (Servicepackage servicepackage, User iduser, Validityperiod validitypeirod, 
			Float totvalue, Date startdate, boolean paid) throws BadServicePackage {
		Order order = null;
		try {
			order = new Order();
			order.setServicepackage(servicepackage);
			order.setUser(iduser);
			order.setValidityperiod(validitypeirod);
			order.setStartdate(startdate);
			order.setTotalvalue(0);
			
			int idvalidityperiod = validitypeirod.getId();
		
			Validityperiod validityperiod = em.find(Validityperiod.class, idvalidityperiod);
			servicepackage.setValidityperiodCost(validityperiod, monthlycosts.get(i));
			
			if(idoptionalproductspossible != null) {
				for(int i = 0; i < idoptionalproductspossible.size(); i++) {
					Optionalproduct optionalproduct = em.find(Optionalproduct.class, idoptionalproductspossible.get(i));
					servicepackage.addOptionalproduct(optionalproduct);
				}
			}
			for(int i = 0; i < idservicesincluded.size(); i++) {
				Service service = em.find(Service.class, idservicesincluded.get(i));
				servicepackage.addService(service);
			}
		}
		catch (Exception e) {
			throw new BadServicePackage(e.getMessage());
		}
		try {
			em.persist(servicepackage);
		}
		catch(PersistenceException e) {
			throw new BadServicePackage("Could not create service package");
		}
		
	}*/
	
	
	
	

}