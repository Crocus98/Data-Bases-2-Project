package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ValidityperiodService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public ValidityperiodService() {
	}
	
}
