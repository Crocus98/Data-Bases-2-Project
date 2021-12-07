package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import entities.Servicepackage;
import entities.Validityperiod;

@Stateless
public class ServicePackageService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public ServicePackageService() {
	}

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
	
	public List<Validityperiod> findValidityperiod () {
		List<Validityperiod> validityperiods = em
				.createNamedQuery("Validityperiod.findAll", Validityperiod.class)
				.getResultList();
		return validityperiods;
	}


}