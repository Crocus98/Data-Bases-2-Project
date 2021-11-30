package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "servicetype", schema="db2_savino_vinati")
@NamedQuery(name="Servicetype.findAll", query="SELECT st FROM Servicetype st")
public class Servicetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String type;
	
	public Servicetype() {
		
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}