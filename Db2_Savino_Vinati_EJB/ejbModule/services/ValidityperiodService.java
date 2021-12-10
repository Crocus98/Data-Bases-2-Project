package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.EntitiesTest;

@Stateless
public class ValidityperiodService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public ValidityperiodService() {
	}
	
}
