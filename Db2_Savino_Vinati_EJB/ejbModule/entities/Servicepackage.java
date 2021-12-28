package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.*;


/**
 * The persistent class for the servicepackage database table.
 * 
 */
@Entity
@Table(name = "servicepackage", schema="db2_savino_vinati")
@NamedQueries({
	@NamedQuery(name="Servicepackage.findAll", query="SELECT sp FROM Servicepackage sp"),
	@NamedQuery(name="Servicepackage.findFromName", query="SELECT sp FROM Servicepackage sp WHERE sp.name = ?1")
})
public class Servicepackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="packageproduct",
			joinColumns= { @JoinColumn(name="idservicepackage")},
			inverseJoinColumns= { @JoinColumn(name="idoptionalproduct")}
	)
	private List<Optionalproduct> optionalproducts;

	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "packageperiod", schema = "db2_savino_vinati", joinColumns = @JoinColumn(name = "idservicepackage"))
	@MapKeyJoinColumn(name = "idvalidityperiod")
	@Column(name = "monthlycost")
	private Map<Validityperiod, Float> validityperiods;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="servicepackageservice",
			joinColumns= { @JoinColumn(name="idservicepackage")},
			inverseJoinColumns= { @JoinColumn(name="idservice")}
	)
	private List<Service> services;

	public Servicepackage() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Optionalproduct> getOptionalproducts() {
		return optionalproducts;
	}

	public void setOptionalproducts(List<Optionalproduct> optionalproducts) {
		this.optionalproducts = optionalproducts;
	}
	
	public void addOptionalproduct(Optionalproduct optionalproduct) {
		getOptionalproducts().add(optionalproduct);
	}
	
	public void addService(Service service) {
		getServices().add(service);
	}
	
	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public Map<Validityperiod, Float> getValidityperiods() {
		return validityperiods;
	}
	
	public void setValidityperiods(Map<Validityperiod, Float> validityperiods) {
		this.validityperiods = validityperiods;
	}
	
	public void setValidityperiodCost(Validityperiod validityperiod, float monthlycost) {
		validityperiods.put(validityperiod, monthlycost);
	}
	

	
	

}