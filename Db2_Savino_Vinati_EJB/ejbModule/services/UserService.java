package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;


import entities.User;
import entities.Usertype;
import exceptions.*;
import java.util.List;

@Stateless
public class UserService {
	//Container managed entity manager
	//NB1: Using @PersistenceUnit or EntityManagerFactory 
	//NB continues: would have created an em ApplicationManaged (managed by developer)
	@PersistenceContext(unitName = "SavinoVinatiProject")
	private EntityManager em;
	//Being container managed we don't need any transaction demarcation code.
	//Everything is managed by the container. Transaction is propagated between methods.

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
	
	public Usertype getUsertype(String type) throws InvalidUsertype {
		List<Usertype> utList = null;
		try {
			utList = em.createNamedQuery("Usertype.findFromType", Usertype.class)
					.setParameter(1, type)
					.getResultList();
		}
		catch(PersistenceException e) {
			throw new InvalidUsertype("Could not verify user type");
		}
		if (utList.isEmpty()) 
		{
			return null;
		}
		else if (utList.size() == 1) {
			return utList.get(0);
		}
		throw new InvalidUsertype("System error: there is more than one usertype with this name");
	}
	
	public boolean checkDuplicatedMail(String username) throws DuplicatedMail, InvalidRegistrationParams{
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.findFromMail", User.class)
					.setParameter(1, username)
					.getResultList();
		}
		catch(PersistenceException e) {
			throw new InvalidRegistrationParams("Could not verify mail");
		}
		if (uList.isEmpty()) 
		{
			return true;
		}
		else if (uList.size() == 1) {
			throw new DuplicatedMail("Mail already exists");
		}
		throw new DuplicatedMail("System error: more than one tuple with this mail already exist.");
	}
	
	public boolean checkDuplicatedUsername(String username) throws DuplicatedUsername, InvalidRegistrationParams{
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.findFromUsername", User.class)
					.setParameter(1, username)
					.getResultList();
		}
		catch(PersistenceException e) {
			throw new InvalidRegistrationParams("Could not verify usertype");
		}
		if (uList.isEmpty()) 
		{
			return true;
		}
		else if (uList.size() == 1) {
			throw new DuplicatedUsername("Username already exists");
		}
		else {
			throw new DuplicatedUsername("System error: more than one tuple with this username already exist.");
		}
	}
	
	public void registerUser (String username, String password, String type, String mail) throws InvalidRegistrationParams{
		Usertype usertype= null;
		
		try {
			checkDuplicatedUsername(username);
			checkDuplicatedMail(mail);
			usertype = getUsertype(type);
		} 
		catch (Exception e) {
			System.out.println("Ciao");
			throw new InvalidRegistrationParams(e.getMessage());
		}
		try {
			User user = new User(username, password, usertype, mail);
			em.persist(user);
		}
		catch(PersistenceException e){
			throw new InvalidRegistrationParams("Could not register user");
		}
		
	}
}
