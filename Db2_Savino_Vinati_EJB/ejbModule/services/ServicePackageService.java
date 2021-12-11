package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.Optionalproduct;
import entities.Service;
import entities.Servicepackage;
import entities.Validityperiod;
import exceptions.BadServicePackage;
import exceptions.DuplicatedPackagename;


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
	
	public List<Validityperiod> findAllValidityperiods () {
		List<Validityperiod> validityperiods = em
				.createNamedQuery("Validityperiod.findAll", Validityperiod.class)
				.getResultList();
		return validityperiods;
	}
	
	public List<Service> findAllServices(){
		List<Service> services = em
				.createNamedQuery("Service.findAll", Service.class)
				.getResultList();
		return services;
	}
	
	public List<Optionalproduct> findAllOptionalproducts(){
		List<Optionalproduct> optionalproducts = em
				.createNamedQuery("Optionalproduct.findAll", Optionalproduct.class)
				.getResultList();
		return optionalproducts;
	}
	
	public void checkDuplicatedName(String name) throws DuplicatedPackagename, BadServicePackage{
		List<Servicepackage> spList = null;
		try {
			spList = em.createNamedQuery("Servicepackage.findFromName", Servicepackage.class)
					.setParameter(1, name)
					.getResultList();
		}
		catch(PersistenceException e) {
			throw new BadServicePackage("Could not verify name");
		}
		if (spList.isEmpty()) 
		{
			return;
		}
		else if (spList.size() == 1) {
			throw new DuplicatedPackagename("Service package name already exists");
		}
		throw new DuplicatedPackagename("System error: more than one tuple with this service package name already exist.");
	}
	
	
	public void createNewServicePackage (String servicepackagename, List<Integer> idservicesincluded, List<Integer> idoptionalproductspossible, 
			List<Integer> idvalidityperiods, List<Float> monthlycosts) throws BadServicePackage {
		Servicepackage servicepackage = null;
		try {
			servicepackage = new Servicepackage();
			checkDuplicatedName(servicepackagename);
			servicepackage.setName(servicepackagename);
			servicepackage.setValidityperiods(new HashMap<>());
			servicepackage.setOptionalproducts(new ArrayList<>());
			servicepackage.setServices(new ArrayList<>());
			for(int i = 0; i < idvalidityperiods.size(); i++) {
				Validityperiod validityperiod = em.find(Validityperiod.class, idvalidityperiods.get(i));
				servicepackage.setValidityperiodCost(validityperiod, monthlycosts.get(i));
			}
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
		
	}

}