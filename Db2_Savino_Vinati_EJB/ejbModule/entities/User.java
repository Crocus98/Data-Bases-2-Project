package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user", schema="db2_savino_vinati")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2"),
	@NamedQuery(name = "User.findFromUsername", query = "SELECT u FROM User u  WHERE u.username = ?1"),
	@NamedQuery(name = "User.findFromMail", query = "SELECT u FROM User u  WHERE u.mail = ?1")
	})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String password;
	private String mail;
	
	@ManyToOne
	@JoinColumn(name="idusertype")
	private Usertype usertype;
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Insolventuser insolventuser;
	
	@OneToMany(mappedBy="user")
	private List<Activationschedule> activationschedules;
	
	@OneToMany(mappedBy="user")
	private List<Alert> alerts;
	
	public User() {
		
	}
	
	public User(String username, String password, Usertype usertype, String mail) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.mail = mail;
		if(usertype.getUsertype().equals("Customer")) {
			this.setInsolventuser(new Insolventuser());
		}
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Insolventuser getInsolventuser() {
		return insolventuser;
	}

	public void setInsolventuser(Insolventuser insolventuser) {
		this.insolventuser = insolventuser;
		insolventuser.setUser(this); //updates the inverse relationship too.
	}

	public List<Activationschedule> getActivationschedules() {
		return activationschedules;
	}

	public void setActivationschedules(List<Activationschedule> activationschedules) {
		this.activationschedules = activationschedules;
	}

	public Usertype getUsertype() {
		return usertype;
	}

	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}