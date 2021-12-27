package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.MvAlert;
import entities.MvBestproduct;
import entities.MvInsolventUser;
import entities.MvPackage;
import entities.MvPackageperiod;
import entities.MvSuspendedorder;

@Stateless
public class SalesReportService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	
	public SalesReportService() {
		
	}

	public List<MvBestproduct> findAllbestproducts (){
		List<MvBestproduct> mvBestproducts = em
					.createNamedQuery("MvBestproduct.findAll", MvBestproduct.class)
					.setHint("javax.persistence.cache.storeMode", "REFRESH")
					.getResultList();
		return mvBestproducts;
	}


	public List<MvAlert> findAllmvalerts(){
		List<MvAlert> mvalerts = em
					.createNamedQuery("MvAlert.findAll", MvAlert.class)
					.getResultList();
		return mvalerts;
	}


	public List<MvSuspendedorder> findAllmvsuspendedorders(){
		List<MvSuspendedorder> mvsuspendedorders = em
					.createNamedQuery("MvSuspendedorder.findAll", MvSuspendedorder.class)
					.getResultList(); 
		return mvsuspendedorders;
	}


	public List<MvInsolventUser> findAllmvinsolventusers(){
		List<MvInsolventUser> mvinsolventuser = em
				.createNamedQuery("MvInsolventUser.findAll", MvInsolventUser.class)
				.getResultList();
		return mvinsolventuser;
	}


	public List<MvPackageperiod> findAllmvpackageperiods(){
		List<MvPackageperiod> mvpackageperiod = em
				.createNamedQuery("MvPackageperiod.findAll", MvPackageperiod.class)
				.getResultList();
		return mvpackageperiod;
	}


	public List<MvPackage> findAllmvpackages(){
		List<MvPackage> mvpackage = em
			.createNamedQuery("MvPackage.findAll", MvPackage.class)
			.getResultList();
		return mvpackage;
	}
	
}
