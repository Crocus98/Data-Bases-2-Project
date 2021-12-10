package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.openjpa.lib.util.Services;

import entities.Optionalproduct;
import entities.Packageperiod;
import entities.Service;
import entities.Servicepackage;
import entities.User;
import entities.Usertype;
import entities.Validityperiod;
import exceptions.BadServicePackage;
import exceptions.DuplicatedMail;
import exceptions.DuplicatedPackagename;
import exceptions.InvalidRegistrationParams;
import exceptions.InvalidUsertype;

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
		List<Service> services = null;
		List<Optionalproduct> optionalproducts = null;
		List<Packageperiod> packageperiods = new ArrayList<Packageperiod>();
		try {
			checkDuplicatedName(servicepackagename);
			//get services from list id
			//get optional products from list id
			//foreach package packageperiods.add(new Packageperiod());
		} 
		catch (Exception e) {
			throw new BadServicePackage(e.getMessage());
		}
		try {
			Servicepackage servicepackage = new Servicepackage(servicepackagename, services, optionalproducts, packageperiods);
			em.persist(servicepackage);
		}
		catch(PersistenceException e) {
			throw new BadServicePackage("Could not create service package");
		}
		
	}

}