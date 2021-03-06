package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "service", schema="db2_savino_vinati")
@NamedQuery(name="Service.findAll", query="SELECT s FROM Service s")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

	@ManyToOne
	@JoinColumn(name="idtype")
	private Servicetype type;
	
	@OneToOne(mappedBy="service")
	private Servicemobile servicemobile;
	
	@OneToOne(mappedBy="service")
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}