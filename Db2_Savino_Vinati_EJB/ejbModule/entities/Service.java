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

	@ManyToOne
	@JoinColumn(name="idtype")
	private Servicetype type;

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

}