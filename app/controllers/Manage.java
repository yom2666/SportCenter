package controllers;

import java.util.List;

import play.mvc.Controller;
import models.Game;
import models.League;
import models.Rank;
import models.Season;
import models.Sport;
import models.Team;

public class Manage extends Controller
{

	public static void played(String sport, String league, String season, int jr)
	{
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			if(jr <= 0) jr = 1;
			render(sp, l, s, jr);
		}
	}
	
	public static void playedJr(String sport, String league, String season, int jr)
	{
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			List<Game> games = Game.find("season = ? AND jr = ? AND played=false ORDER BY date", s, jr).fetch();
			render(sp, l, s, jr, games);
		}
	}
	
	public static void doPlayed(String sport, String league, String season, int jr, long id, int scoreHome, int scoreAway)
	{
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			Game g = Game.findById(id);
			g.scoreHome = scoreHome;
			g.scoreAway = scoreAway;
			List<Rank> ranks = Ranks.getRanks(sp, l, s, jr);
			Ranks.manageGame(ranks, g, true);
			Ranks.reset(jr);
			g.played = true;
			g.save();
		}
		played(sport, league, season, jr);
	}
	
}
