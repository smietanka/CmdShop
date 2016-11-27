package me.goukan.cmdshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.goukan.cmdshop.Interfaces.IInv;
import me.goukan.cmdshop.Interfaces.IReader;
import me.goukan.cmdshop.Interfaces.IService;
import me.goukan.cmdshop.Types.Item;
import me.goukan.cmdshop.Types.MsgUtils;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class MyInventory implements IInv, Listener{

	private Inventory myInv = Bukkit.createInventory((InventoryHolder)null, InventoryType.CHEST, (String)"\u00a71\u00a7lCmd Shop");
	
	public MyInventory(IReader myReader, IService myService)
	{
		List<Item> items = myReader.Read();
		for(Item eachItem : items)
		{
			ItemStack stack = eachItem.getItemStack();
			
			ItemMeta resultMeta = stack.getItemMeta();
	        resultMeta.setDisplayName("\u00a76\u00a7l" + eachItem.getId() + ". "+ eachItem.getName());
	        
	        ArrayList<String> shortDesc = new ArrayList<String>();
	        for(String eachDesc : eachItem.getDescription())
	        {
	        	shortDesc.add(MsgUtils.ConvertToMc(eachDesc));
	        }
	        shortDesc.add((Object)ChatColor.GRAY + "Cena: " + eachItem.getSmsPrice());
	        shortDesc.add(MsgUtils.ConvertToMc("&dAby zakupic te usluge nalezy"));
	        shortDesc.add(MsgUtils.ConvertToMc("&dwyslac smsa o tresci:"));
	        shortDesc.add(MsgUtils.ConvertToMc("&6%s.%s &dna numer &6%s. ", 
	        		myService.GetPreCode(), 
	        		eachItem.getSmsCode(), 
	        		eachItem.getSmsNumber()));
	        
	        shortDesc.add(MsgUtils.ConvertToMc("&dOtrzymasz kod zwrotny, ktory"));
	        shortDesc.add(MsgUtils.ConvertToMc("&dnalezy wpisac przy komendzie:"));
	        shortDesc.add(MsgUtils.ConvertToMc("&6/shop <%s|%s> <kod_zwrotny>", eachItem.getName(), eachItem.getId()));
	        resultMeta.setLore(shortDesc);
	        stack.setItemMeta(resultMeta);
	        
			myInv.setItem(eachItem.getId() - 1, stack);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(this.getInv().getName())) {
			event.setCancelled(true);
		}
	}
	
	@Override
	public Inventory getInv() {
		return myInv;
	}
}
