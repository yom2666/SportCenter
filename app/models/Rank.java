package models;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Rank extends Model implements Cloneable{
	

	@OneToOne
	@Required
	public Team team;
	@Min(0)
	@Max(38)
	public int jr;
	@ManyToOne
	@Required
	public Season season;
	
//	public int pos;
	public double rank;
	
	public int gPts;
	public int gJoues;
	public int gP;
	public int gN;
	public int gG;
	public int gBp;
	public int gBc;
	public int gDiff;
	
	public int dPts;
	public int dJoues;
	public int dP;
	public int dN;
	public int dG;
	public int dBp;
	public int dBc;
	public int dDiff;
	
	public int xPts;
	public int xJoues;
	public int xP;
	public int xN;
	public int xG;
	public int xBp;
	public int xBc;
	public int xDiff;

	public double ptitre;
	public double pldc;
	public double pbar;
	public double peuro;
	public double prel;
	
	public Rank(Team team, Season season, int jr)
	{
		this.team = team;
		this.season = season;
		this.jr = jr;
	}
	
	public void update()
	{
		dJoues = dG + dN + dP;
		xJoues = xG + xN + xP;
		dPts = dG * 3 + dN;
		xPts = xG * 3 + dN;
		dDiff = dBp - dBc;
		xDiff = xBp - xBc;
		gPts = dPts + xPts;
		gJoues = dJoues + xJoues;
		gG = dG + xG;
		gN = dN + xN;
		gP = dP + xP;
		gBp = dBp + xBp;
		gBc = dBc + xBc;
		gDiff = dDiff + xDiff;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb  = new StringBuilder(team.name);
		sb.append("  ");
		sb.append("Pts:");
		sb.append(gPts);
		return sb.toString();
	}
	
	public Rank copy(int jr)
	{
		Rank r = new Rank(team, season, jr);
		r.jr = jr;
		
		r.rank = rank;
		
		r.gPts = gPts;
		r.gJoues = gJoues;
		r.gP = gP;
		r.gN = gN;
		r.gG = gG;
		r.gBp = gBp;
		r.gBc = gBc;
		r.gDiff = gDiff;
		
		r.dPts = dPts;
		r.dJoues = dJoues;
		r.dP = dP;
		r.dN = dN;
		r.dG = dG;
		r.dBp = dBp;
		r.dBc = dBc;
		r.dDiff = dDiff;
		
		r.xPts = xPts;
		r.xJoues = xJoues;
		r.xP = xP;
		r.xN = xN;
		r.xG = xG;
		r.xBp = xBp;
		r.xBc = xBc;
		r.xDiff = xDiff;

		r.ptitre = ptitre;
		r.pldc = pldc;
		r.pbar = pbar;
		r.peuro = peuro;
		r.prel = prel;
		
		return r;
	}
	
	private static int compare(int pts1, int pts2, int diff1, int diff2, int bp1, int bp2, String name1, String name2)
	{
		if(pts1 == pts2)
		{
			if(diff1 == diff2)
			{
				if(bp1 == bp2)
				{
					return name1.compareToIgnoreCase(name2);
				}
				if(bp1 > bp2) return 1;
				return -1;
			}
			if(diff1 > diff2) return 1;
			return -1;
		}
		if(pts1 > pts2) return 1;
		return -1;
	}
	
	public static final Comparator<Rank> GENERAL_COMPARATOR = new Comparator<Rank>() 
	{
		@Override
		public int compare(Rank r1, Rank r2) 
		{
			return -Rank.compare(r1.gPts, r2.gPts, r1.gDiff, r2.gDiff, r1.gBp, r2.gBp, r1.team.name, r2.team.name);
		}
	};

}
