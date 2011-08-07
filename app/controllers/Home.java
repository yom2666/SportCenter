package controllers;

import java.util.Collections;
import java.util.List;

import models.Game;
import models.League;
import models.Rank;
import models.Season;
import models.Sport;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class Home extends Controller{
	
	public static void index()
	{
		List<Object> sports = Sport.all().fetch();
		render(sports);
	}
	
	public static void sport(String sport)
	{
		Sport sp = (Sport) controll(sport);
		if(sp != null)
		{
			List<League> leagues = League.find("sport = ?", sp).fetch();
			render(sp, leagues);
		}
	}
	
	public static void league(String sport, String league)
	{
		Object[] oo = controll(sport, league);
		if(oo != null)
		{
			Sport sp = (Sport) oo[0];
			League l = (League) oo[1];
			List<Season> seasons = Season.find("league = ?", l).fetch();
			render(sp, l, seasons);
		}
	}
	
	public static void season(String sport, String league, String season)
	{
		show(sport, league, season);
	}
	
	public static void show(String sport, String league, String season)
	{
		Object[] ooo = controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			// TODO ordered by date
			List<Game> games = Game.find("season = ? ORDER BY date, jr", s).fetch();
//			System.out.println(games.size());
//			for(Game g : games)
//			{
//				System.out.println(g.home.name + " - " + g.away.name);
//			}
			render(sp, l, s, games);
		}
	}
		
	public static void getTable(String sport, String league, String season, int jr, boolean escape, String q, long untildate)
	{
		System.out.println(q + " : "+escape+" untildate:"+untildate);
		Object[] ooo = controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			List<Rank> ranks = Ranks.getRanks(sp, l, s, jr);
			if(!escape)
			{
				List<Game> games = null;
				if(untildate > 0)
				{
					games = Game.find("season = ? AND played = false AND date <= ?", s, untildate).fetch();
				}
				else
				{
					games = Game.find("season = ? AND played = false", s).fetch();
				}
				for(Game g : games)
				{
					Ranks.manageGame(ranks, g, false);
				}
				Collections.sort(ranks, Rank.GENERAL_COMPARATOR);
			}
			render(sp, l, s, ranks);
		}
	}
	
	public static Object[] controll(String sport, String league, String season)
	{
		Object[] oo = controll(sport, league);
		if(oo != null)
		{
			String xSeason = season.replace("+", " ");
			Season s = Season.find("name = ?", xSeason).first();
			if(s == null){
				league(sport, league);
				return null;
			}
			Object[] objects = new Object[3];
			objects[0] = oo[0];
			objects[1] = oo[1];
			objects[2] = s;
			return objects;
		}
		return null;
	}

	
	public static Object[] controll(String sport, String league)
	{
		Object o = controll(sport);
		if(o != null)
		{
			String xLeague = league.replace("+", " ");
			League l = League.find("name = ?", xLeague).first();
			if(l == null)
			{
				sport(sport);
				return null;
			}

			Object[] objects = new Object[2];
			objects[0] = o;
			objects[1] = l;
			return objects;
		}
		return null;
	}

	
	public static Object controll(String sport)
	{
		String xSport = sport.replace("+", " ");
		Sport sp = Sport.find("name=?", xSport).first();
		if(sp == null) index();
		return sp;
	}

}
