package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the service database table.
 * 
 */
@Entity
@Table(name = "service", schema="db2_savino_vinati")
@NamedQuery(name="Service.findAll", query="SELECT s FROM Service s")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private Serviceinternet serviceinternet;

	@ManyToOne
	@JoinColumn(name="idtype")
	private Servicetype type;
	
	@OneToOne
	private Servicemobile servicemobile;
	
	@OneToOne
	private Serviceinternet serviceinternet;

	public Service() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Servicetype getType() {
		return type;
	}

	public void setType(Servicetype type) {
		this.type = type;
	}

	public Servicemobile getServicemobile() {
		return servicemobile;
	}

	public void setServicemobile(Servicemobile servicemobile) {
		this.servicemobile = servicemobile;
	}
	
	public Serviceinternet getServiceinternet() {
		return serviceinternet;
	}

	public void setServiceinternet(Serviceinternet serviceinternet) {
		this.serviceinternet = serviceinternet;
	}

}