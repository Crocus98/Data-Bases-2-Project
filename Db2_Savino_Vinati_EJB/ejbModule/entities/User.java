package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "user", schema="db2_savino_vinati")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
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
	private Usertype userType;

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
}