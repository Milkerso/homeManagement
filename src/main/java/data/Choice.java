package data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "choice")
public class Choice implements Serializable {

	
	@Id
	@GeneratedValue
	private int id;
	private String nagroda;
	private String nameuser;
	private String pathn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNagroda() {
		return nagroda;
	}
	public void setNagroda(String nagroda) {
		this.nagroda = nagroda;
	}
	public String getNameuser() {
		return nameuser;
	}
	public void setNameuser(String nameuser) {
		this.nameuser = nameuser;
	}
	public String getPathn() {
		return pathn;
	}
	public void setPathn(String pathn) {
		this.pathn = pathn;
	}
	@Override
	public String toString() {
		return "Choice [id=" + id + ", nagroda=" + nagroda + ", nameuser=" + nameuser + ", pathn=" + pathn + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nagroda == null) ? 0 : nagroda.hashCode());
		result = prime * result + ((nameuser == null) ? 0 : nameuser.hashCode());
		result = prime * result + ((pathn == null) ? 0 : pathn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Choice other = (Choice) obj;
		if (id != other.id)
			return false;
		if (nagroda == null) {
			if (other.nagroda != null)
				return false;
		} else if (!nagroda.equals(other.nagroda))
			return false;
		if (nameuser == null) {
			if (other.nameuser != null)
				return false;
		} else if (!nameuser.equals(other.nameuser))
			return false;
		if (pathn == null) {
			if (other.pathn != null)
				return false;
		} else if (!pathn.equals(other.pathn))
			return false;
		return true;
	}
	
	
}