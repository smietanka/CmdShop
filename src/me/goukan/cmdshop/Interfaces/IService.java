package me.goukan.cmdshop.Interfaces;

import me.goukan.cmdshop.Types.Item;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public interface IService {
	public String GetPreCode();
	public boolean CheckCode(String code, Item item) throws Exception;
}
