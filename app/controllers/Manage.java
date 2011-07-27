package controllers;

import play.mvc.Controller;
import models.League;
import models.Season;
import models.Sport;

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
	
}
