package controllers;

import play.mvc.Controller;

public class Sport extends Controller{
	
	public static void unknown(String sport)
	{
		renderXml("<h2>Unknown sport : "+sport+"</h2>");
	}

}
