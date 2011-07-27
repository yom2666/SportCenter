package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class League extends Model{

	public String name;
	@ManyToOne
	@Required
	public Sport sport;

	@Override
	public String toString() {
		return name;
	}

}
