package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Game;
import models.League;
import models.Season;
import models.Sport;
import models.Team;
import play.mvc.Controller;

public class Init extends Controller
{
	
	private static final DateFormat SHORT_DF = new SimpleDateFormat("ddMMyyHHmm");

	public static void addGame(String sport, String league, String season, int jr)
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
	
	public static void addGameJr(String sport, String league, String season, int jr)
	{
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			List<Game> games = Game.find("season = ? AND jr = ?", s, jr).fetch();
			List<Team> teams = missingTeams(games, s);
			render(sp, l, s, jr, games, teams);
		}
	}
	
	public static void doAdd(String sport, String league, String season, int jr, long homeId, long awayId)
	{
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Team home = Team.findById(homeId);
			Team away = Team.findById(awayId);
			if(home != null && away != null)
			{
				Season s = (Season) ooo[2];
				Game g = new Game();
				g.home = home;
				g.away = away;
				g.jr = jr;
				g.played = false;
				g.season = s;
				g.date = new Date().getTime();
				g.save();
				Game retour = new Game();
				retour.home = away;
				retour.away = home;
				retour.jr = Season.corresp[jr];
				retour.played = false;
				retour.season = s;
				retour.save();
			}
		}
		addGame(sport, league, season, jr);
	}
	
	public static void modGame(String sport, String league, String season, int jr)
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
	
	public static void modGameJr(String sport, String league, String season, int jr)
	{
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Sport sp = (Sport) ooo[0];
			League l = (League) ooo[1];
			Season s = (Season) ooo[2];
			List<Game> games = Game.find("season = ? AND jr = ?", s, jr).fetch();
			render(sp, l, s, jr, games);
		}
	}
	
	public static void doGlobalMod(String sport, String league, String season, int jr, String date)
	{
		System.out.println(date);
		Object[] ooo = Home.controll(sport, league, season);
		if(ooo != null)
		{
			Season s = (Season) ooo[2];
			long d = -1;
			try 
			{
				d = SHORT_DF.parse(date).getTime();
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			List<Game> games = Game.find("season = ? AND jr = ?", s, jr).fetch();
			for(Game g : games)
			{
				g.date = d;
				g.save();
			}
		}
		modGame(sport, league, season, jr);
	}
	
	public static void doMod(String sport, String league, String season, int jr, long id, boolean played, String date)
	{
		System.out.println(date);
		long d = -1;
		try 
		{
			d = SHORT_DF.parse(date).getTime();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		Game g = Game.findById(id);
		g.date = d;
		g.played = played;
		g.save();
		modGame(sport, league, season, jr);
	}
	
	public static void doDelete(String sport, String league, String season, int jr, long id)
	{
		System.out.println(id);
		((Game)Game.findById(id)).delete();
		addGame(sport, league, season, jr);
	}
	
	public static void resetRanks(String sport, String league, String season, int jr)
	{
		Ranks.reset(jr);
		Home.show(sport, league, season);
	}
	
	private static List<Team> missingTeams(List<Game> games, Season season)
	{
		List<Team> teams = Team.find("? in elements(seasons) ORDER BY name", season).fetch();
		for(Game g : games)
		{
			teams.remove(g.home);
			teams.remove(g.away);
		}
		return teams;
	}
}
