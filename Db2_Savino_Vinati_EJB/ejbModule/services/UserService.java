package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;

import entities.Insolventuser;
import entities.User;
import entities.Usertype;
import exceptions.*;
import java.util.List;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;

	public UserService() {
		
	}

	public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class)
					.setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} 
		catch (PersistenceException e) 
		{
			throw new CredentialsException("Could not verify credentials");
		}
		if (uList.isEmpty()) 
		{
			return null;
		}
		else if (uList.size() == 1) {
			return uList.get(0);
		}
		throw new NonUniqueResultException("More than one user registered with same credentials");
		
	}
	
	public void registerUser (String username, String password, String type, String mail) throws InvalidRegistrationParams{
		List<Usertype> utList = null;
		Usertype usertype = null;
		try {
			
			utList = em.createNamedQuery("Usertype.findFromType", Usertype.class)
					.setParameter(1, type)
					.getResultList();
		}
		catch(PersistenceException e) {
			throw new InvalidRegistrationParams("Invalid user type");
		}
		if (utList.isEmpty()) 
		{
			return;
		}
		else if (utList.size() == 1) {
			usertype = utList.get(0);
		}
		
		try {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setMail(mail);
			if(type == "Customer") {
				user.setInsolventuser(new Insolventuser());
			}
			user.setUsertype(usertype);
			em.persist(user);
			
		}
		catch(PersistenceException e){
			throw new InvalidRegistrationParams("Could not verify registration values");
		}
		
	}
}
