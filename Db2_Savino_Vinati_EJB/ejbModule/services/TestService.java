package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.EntitiesTest;

@Stateless
public class TestService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public TestService() {
	}
	
	public void addToDb (String a) {
		try {
			EntitiesTest test = new EntitiesTest();
			test.setTestValue("TestSuccessful " + a);
			em.persist(test);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
