package entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "activationschedule", schema="db2_savino_vinati")
@NamedQuery(name="Activationschedule.findAll", query="SELECT s FROM Servicepackage s")
public class Activationschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int iduser;
	
	@Temporal(TemporalType.DATE)
	private Date activationdate;
	
	@Temporal(TemporalType.DATE)
	private Date deactivationdate;
	
	@ManyToOne
	@JoinColumn(name="iduser")
	private User user;

	public Activationschedule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public Date getActivationdate() {
		return activationdate;
	}

	public void setActivationdate(Date activationdate) {
		this.activationdate = activationdate;
	}

	public Date getDeactivationdate() {
		return deactivationdate;
	}

	public void setDeactivationdate(Date deactivationdate) {
		this.deactivationdate = deactivationdate;
	}

	

}