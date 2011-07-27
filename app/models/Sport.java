package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Sport extends Model{
	
	public String name;
	
	@Override
	public String toString() {
		return name;
	}

}
