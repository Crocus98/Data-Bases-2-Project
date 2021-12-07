package entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="mv_insolventuser")
@NamedQuery(name="MvInsolventUser.findAll", query="SELECT miu FROM MvInsolventUser miu")
public class MvInsolventUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "idinsolventuser")
	private Insolventuser insolventuser;

	public MvInsolventUser() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Insolventuser getInsolventuser() {
		return insolventuser;
	}

	public void setInsolventuser(Insolventuser insolventuser) {
		this.insolventuser = insolventuser;
	}

}