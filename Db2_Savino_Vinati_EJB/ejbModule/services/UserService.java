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
		else {
			throw new InvalidUsertype("System error");
		}
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
		else {
			throw new DuplicatedMail("System error: more than one tuple with this mail already exist.");
		}
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
			throw new InvalidRegistrationParams(e.getMessage());
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
