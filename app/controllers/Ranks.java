package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
				// TODO : handle day games
			}
		}
		Collections.sort(ranks, Rank.GENERAL_COMPARATOR);
		return ranks;
	}

}
