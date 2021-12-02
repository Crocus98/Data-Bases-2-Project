package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mv_packageperiod database table.
 * 
 */
@Entity
@Table(name="mv_packageperiod")
@NamedQuery(name="MvPackageperiod.findAll", query="SELECT mpp FROM MvPackageperiod mpp")
public class MvPackageperiod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String packagename;

	private int period;

	private int sales;

	public MvPackageperiod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPackagename() {
		return this.packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public int getPeriod() {
		return this.period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getSales() {
		return this.sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

}