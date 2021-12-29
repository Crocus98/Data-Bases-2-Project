package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.Optionalproduct;
import exceptions.InvalidProductParams;

@Stateless
public class OptionalProductService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public OptionalProductService() {
	}
	
	public void createOptionalProduct(String name, float monthlyprice) throws InvalidProductParams {
		try {
			Optionalproduct product = new Optionalproduct(name, monthlyprice);
			em.persist(product);
		}
		catch(PersistenceException e) {
			throw new InvalidProductParams("Could not create optional product");
		}
	}
	
}
