package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Team extends Model{
	
	public String name;
	@ManyToMany
	public List<Season> seasons;
	
	@Override
	public String toString() {
		return name;
	}

}
