package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.MvAlert;
import entities.MvBestproduct;
import entities.MvInsolventUser;
import entities.MvPackage;
import entities.MvPackageperiod;
import entities.MvSuspendedorder;
import exceptions.BadSalesReportDataRetrieval;

@Stateless
public class SalesReportService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public SalesReportService() {
		
	}

	public void getSalesReportPageData(List<MvPackage> mvpackages, List<MvPackageperiod> mvpackageperiods, List<MvInsolventUser> mvinsolventusers, 
			List<MvSuspendedorder> mvsuspendedorders, List<MvAlert> mvalerts, List<MvBestproduct> mvbestproducts) throws BadSalesReportDataRetrieval {
		try {
			mvpackages = findAllmvpackages();
			mvpackageperiods = findAllmvpackageperiods();
			mvinsolventusers = findAllmvinsolventusers();
			mvsuspendedorders = findAllmvsuspendedorders();
			mvalerts = findAllmvalers();
			mvbestproducts = findAllbestproducts();
		}
		catch(PersistenceException e){
			throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
	}

	public List<MvBestproduct> findAllbestproducts () throws BadSalesReportDataRetrieval{
		List<MvBestproduct> mvBestproduct = null;
		try {
			mvBestproduct = em
					.createNamedQuery("MvBestproduct.findAll", MvBestproduct.class)
					.getResultList();
		}
		 catch (PersistenceException e) {
			 throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		 }
		return mvBestproduct;
	}


	public List<MvAlert> findAllmvalers() throws BadSalesReportDataRetrieval{
		List<MvAlert> mvalerts = null;
		try {
			mvalerts = em
					.createNamedQuery("MvAlert.findAll", MvAlert.class)
					.getResultList();
		}
		catch(PersistenceException e) {
			 throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
		
		return mvalerts;
	}


	public List<MvSuspendedorder> findAllmvsuspendedorders() throws BadSalesReportDataRetrieval{
		List<MvSuspendedorder> mvsuspendedorders = null;
		try {
			mvsuspendedorders = em
					.createNamedQuery("MvSuspendedorder.findAll", MvSuspendedorder.class)
					.getResultList(); 
		}
		catch(PersistenceException e){
			 throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
		
		return mvsuspendedorders;
	}


	public List<MvInsolventUser> findAllmvinsolventusers() throws BadSalesReportDataRetrieval{
		List<MvInsolventUser> mvinsolventuser = null;
		try {
			mvinsolventuser = em
				.createNamedQuery("MvInsolventUser.findAll", MvInsolventUser.class)
				.getResultList();
		}
		catch(PersistenceException e){
			 throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
		
		return mvinsolventuser;
	}


	public List<MvPackageperiod> findAllmvpackageperiods() throws BadSalesReportDataRetrieval{
		List<MvPackageperiod> mvpackageperiod = null;
		try {
			mvpackageperiod = em
				.createNamedQuery("MvPackageperiod.findAll", MvPackageperiod.class)
				.getResultList();
		}
		catch(PersistenceException e){
			 throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
		
		return mvpackageperiod;
	}


	public List<MvPackage> findAllmvpackages() throws BadSalesReportDataRetrieval{
		List<MvPackage> mvpackage = null;
		try {
			mvpackage = em
				.createNamedQuery("MvPackage.findAll", MvPackage.class)
				.getResultList();
		}
		catch(PersistenceException e){
			 throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
		
		return mvpackage;
	}
	
}
