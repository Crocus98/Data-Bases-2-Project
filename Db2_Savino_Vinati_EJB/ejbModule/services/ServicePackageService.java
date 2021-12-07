package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import entities.Servicepackage;




@Stateless
public class ServicePackageService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public ServicePackageService() {
	}

	// If a mission is deleted by a concurrent transaction this method retrieves it
	// from the cache. If a mission is deleted by the JPA application, the
	// persistence context evicts it, this method no longer
	// retrieves it, and relationship sorting by the client works
	public List<Servicepackage> findAllServicePackages() {
		 List<Servicepackage> packages = em
					.createNamedQuery("Servicepackage.findAll", Servicepackage.class)
					.getResultList();
		return packages;
	}
	
	public Servicepackage findServicePackageById(int serviceId) {
		Servicepackage service = em.find(Servicepackage.class, serviceId);
		return service;
	}


}