package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user", schema="db2_savino_vinati")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2")
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
	
	@OneToOne(mappedBy="user")
	private Insolventuser insolventuser;
	
	@OneToMany(mappedBy="user")
	private List<Activationschedule> activationschedules;
	
	@OneToMany(mappedBy="user")
	private List<Alert> alerts;

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