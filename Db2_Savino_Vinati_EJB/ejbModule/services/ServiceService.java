package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ServiceService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public ServiceService() {
	}
	
}
