package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Game extends Model{
	
	@OneToOne
	@Required
	public Team home;
	@OneToOne
	@Required
	public Team away;
	public long date;
	public int scoreHome;
	public int scoreAway;
	public boolean played;
	@Min(1)
	@Max(38)
	public int jr;
	@ManyToOne
	@Required
	public Season season;
	
	@Override
	public String toString() 
	{
		return home.toString() + " - " + away.toString() + " " + scoreHome + " - " + scoreAway;
	}

}
