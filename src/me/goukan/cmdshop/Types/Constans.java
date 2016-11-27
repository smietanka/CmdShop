package me.goukan.cmdshop.Types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class Constans {
	public static final String PLUGIN_NAME = "CmdShop";
	public static final List<String> ALL_USAGE_SHOP = new ArrayList<String>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(ChatColor.DARK_AQUA + "/shop list, aby wyswietlic liste uslug.");
			add(ChatColor.DARK_AQUA + "/shop <numer_uslugi|nazwa_uslugi>, aby wyswietlic informacje o konkretnej usludze.");
			add(ChatColor.DARK_AQUA + "/shop <numer_uslugi|nazwa_uslugi> <kod_zwrotny>, aby sfinalizowac zakup.");
		}
	};
}