package me.goukan.cmdshop.Interfaces;

import java.util.List;

import me.goukan.cmdshop.Types.Item;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public interface IReader {
	public void Init(String fileName);
	public List<Item> Read();
}