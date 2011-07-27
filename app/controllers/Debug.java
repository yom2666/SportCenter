package controllers;

import models.Game;
import play.mvc.Controller;

public class Debug extends Controller
{

	public static void resetGames()
	{
		Game.deleteAll();
		Home.index();
	}
	
}
