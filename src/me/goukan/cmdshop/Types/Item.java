package me.goukan.cmdshop.Types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class Item {
	private static Integer objCounter = 1;
	
	private Integer Id;
	private String Name = "";
	private List<String> Description = new ArrayList<String>();
	
	private String SmsPrice = "";
	private String SmsNumber = "";
	private String SmsCode = "";
	private List<String> Commands = new ArrayList<String>();
	private List<String> SuccessMessages = new ArrayList<String>();
	private String ItemMaterial = "";
	
	public Item(String name, List<String> desc, String smsPrice, String smsNumber, String smsCode, List<String> commands, List<String> successMsgs, String material)
	{
		this.Id = objCounter++;
		this.Name = name;
		this.Description = desc;
		this.SmsPrice = smsPrice;
		this.SmsCode = smsCode;
		this.SmsNumber = smsNumber;
		this.Commands = commands;
		this.SuccessMessages = successMsgs;
		this.ItemMaterial = material;
	}
	public String getMaterial()
	{
		return this.ItemMaterial;
	}
	public Integer getId()
	{
		return this.Id;
	}
	public String getName()
	{
		return this.Name;
	}
	public List<String> getDescription()
	{
		return this.Description;
	}
	
	public List<String> getSuccessMsg()
	{
		return this.SuccessMessages;
	}
	
	public String getSmsPrice()
	{
		return this.SmsPrice;
	}
	public String getSmsNumber()
	{
		return this.SmsNumber;
	}
	public String getSmsCode() {
		return this.SmsCode;
	}
	public List<String> getCommands()
	{
		return this.Commands;
	}
	
	public ItemStack getItemStack()
	{
		String material = "";
		if(this.getMaterial().isEmpty())
		{
			material = "DIRT";
		}
		else
		{
			material = this.getMaterial();
		}
		ItemStack result =  new ItemStack(Material.getMaterial(material));
		
		return result;
	}
}
