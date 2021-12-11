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
			List<MvSuspendedorder> mvsuspendedorders, List<MvAlert> mvalerts, MvBestproduct mvbestproduct) throws BadSalesReportDataRetrieval {
		try {
			mvpackages = findAllmvpackages();
			mvpackageperiods = findAllmvpackageperiods();
			mvinsolventusers = findAllmvinsolventusers();
			mvsuspendedorders = findAllmvsuspendedorders();
			mvalerts = findAllmvalers();
			mvbestproduct = findAllbestproduct();
		}
		catch(PersistenceException e){
			throw new BadSalesReportDataRetrieval("Could not retrieve sales report page data");
		}
	}

	private MvBestproduct findAllbestproduct(){
		List<MvBestproduct> mvBestproduct = em
				.createNamedQuery("MvBestproduct.findAll", MvBestproduct.class)
				.getResultList();
		return mvBestproduct.get(0);
	}


	private List<MvAlert> findAllmvalers() {
		List<MvAlert> mvalerts = em
				.createNamedQuery("MvAlert.findAll", MvAlert.class)
				.getResultList();
		return mvalerts;
	}


	private List<MvSuspendedorder> findAllmvsuspendedorders() {
		List<MvSuspendedorder> mvsuspendedorders = em
		.createNamedQuery("MvSuspendedorder.findAll", MvSuspendedorder.class)
		.getResultList(); 
		return mvsuspendedorders;
	}


	private List<MvInsolventUser> findAllmvinsolventusers() {
		List<MvInsolventUser> mvinsolventuser = em
				.createNamedQuery("MvInsolventUser.findAll", MvInsolventUser.class)
				.getResultList();
		return mvinsolventuser;
	}


	private List<MvPackageperiod> findAllmvpackageperiods() {
		List<MvPackageperiod> mvpackageperiod = em
				.createNamedQuery("MvPackageperiod.findAll", MvPackageperiod.class)
				.getResultList();
		return mvpackageperiod;
	}


	private List<MvPackage> findAllmvpackages() {
		List<MvPackage> mvpackage = em
				.createNamedQuery("MvPackage.findAll", MvPackage.class)
				.getResultList();
		return mvpackage;
	}
	
}
