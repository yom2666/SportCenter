package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Season extends Model{
	
	public static int[] corresp = {0,38,37,36,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
	
	public String name;
	@ManyToOne
	@Required
	public League league;
	public boolean started;
	public boolean ended;
	public int nbClubs = 20;
	
	@Override
	public String toString() {
		return name;
	}

}
