package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Game;
import models.League;
import models.Rank;
import models.Season;
import models.Sport;
import models.Team;

public class Ranks 
{

	public static List<Rank> getRanks(Sport sport, League league, Season season, int jr)
	{
		List<Rank> ranks;
		if(jr < 0)
		{
			ranks = new ArrayList<Rank>();
			List<Team> teams = Team.find("? in elements(seasons)", season).fetch();
			for(Team t : teams)
			{
				Rank r = new Rank(t, season, 0);
				r.save();
				ranks.add(r);
			}
		}
		else
		{
			ranks = Rank.find("season = ? AND jr = ?", season, jr).fetch();
			if(ranks.size() < season.nbClubs)
			{
				for(Rank r : ranks)
				{
					r.delete();
				}
				ranks.clear();
			}
			if(ranks == null || ranks.isEmpty())
			{
				ranks = getRanks(sport, league, season, jr - 1);
				for(Rank r : ranks)
				{
					r.copy(jr).save();
				}
			}
		}
		Collections.sort(ranks, Rank.GENERAL_COMPARATOR);
		return ranks;
	}
	
	public static Rank find(List<Rank> ranks, Team t)
	{
		for(Rank r : ranks)
		{
			if(r.team.equals(t))
			{
				return r;
			}
		}
		return null;
	}
	
	public static void reset(int jr)
	{
		Rank.delete("jr > ?", jr);
	}
	
	public static void manageGame(List<Rank> ranks, Game g, boolean save)
	{
		Rank homeRank = Ranks.find(ranks, g.home);
		Rank awayRank = Ranks.find(ranks, g.away);
		if(g.scoreHome > g.scoreAway)
		{
			double pts = 0;
			double rg = homeRank.rank - awayRank.rank;
			rg += 3;
			if(rg > 10)
			{
				pts = 0;
			}
			else if(rg < -10)
			{
				pts = 2;
			}
			else
			{
				int diff = g.scoreHome - g.scoreAway;
				double k = 2 - 1 / diff;
				pts = k * (-0.1 * rg + 1);
				pts = Math.ceil(100 * pts) / 100;
			}
			homeRank.rank += pts;
			awayRank.rank -= pts;
			homeRank.dG += 1;
			awayRank.xP += 1;
		}
		else if(g.scoreHome < g.scoreAway)
		{
			double pts = 0;
			double rg = awayRank.rank - homeRank.rank;
			rg -= 3;
			if(rg > 10)
			{
				pts = 0;
			}
			else if(rg < -10)
			{
				pts = 2;
			}
			else
			{
				int diff = g.scoreAway - g.scoreHome;
				double k = 2 - 1 / diff;
				pts = k * (-0.1 * rg + 1);
				pts = Math.ceil(100 * pts) / 100;
			}
			homeRank.rank -= pts;
			awayRank.rank += pts;
			homeRank.dP += 1;
			awayRank.xG += 1;
		}
		else
		{
			double pts = 0;
			double rg = homeRank.rank - awayRank.rank;
			rg += 3;
			if(rg > 10)
			{
				pts = -1;
			}
			else if(rg < -10)
			{
				pts = 1;
			}
			else
			{
				pts = -0.1 * rg;
				pts = Math.ceil(100 * pts) / 100;
			}
			homeRank.rank += pts;
			awayRank.rank -= pts;
			homeRank.dN += 1;
			awayRank.xN += 1;
		}
		homeRank.dBp += g.scoreHome;
		homeRank.dBc += g.scoreAway;
		awayRank.dBp += g.scoreAway;
		awayRank.dBc += g.scoreHome;
		homeRank.update();
		awayRank.update();
		if(save)
		{
			homeRank.save();
			awayRank.save();
		}
	}

}
