package me.goukan.cmdshop;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.goukan.cmdshop.Interfaces.IInv;
import me.goukan.cmdshop.Interfaces.ILogger;
import me.goukan.cmdshop.Interfaces.IReader;
import me.goukan.cmdshop.Interfaces.IService;
import me.goukan.cmdshop.ServiceTypes.DotPayService;
import me.goukan.cmdshop.ServiceTypes.MicroSmsService;
import me.goukan.cmdshop.Types.Constans;
import me.goukan.cmdshop.Types.Commands.CommandShop;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class Core extends JavaPlugin {
	
	IReader myReader = null;
	ILogger myLogger = null;
	IService myService = null;
	FileConfiguration myConfig = getConfig();
	IInv myInv = null;
	
	private void initCore() throws Exception
	{
		myConfig.addDefault("default.logFileName", "logs.txt");
		myConfig.addDefault("default.shopItemsFileName", "shop_items.json");
		myConfig.addDefault("default.useInventoryDesign", false);
		
		myConfig.addDefault("sms.currentService", "dotpay");
		myConfig.addDefault("sms.clientId", 0);
		myConfig.addDefault("sms.serviceId", 0);
		myConfig.options().copyDefaults(true);
		saveConfig();
		
		this.saveDefaultConfig();
		String folderName = "plugins/" + Constans.PLUGIN_NAME;
		File file = new File(folderName);
		if(!file.exists())
		{
			file.mkdir();
		}
		myLogger = new PluginLogger(myConfig.get("default.logFileName").toString(), getLogger());
		
		// sprawdzamy czy istnieje shop_items.json/ jeœli nie 
		// to go tworzy z pustym obiektem
		myReader = new JsonReader(myLogger);
		myReader.Init(myConfig.get("default.shopItemsFileName").toString());
		
		
		//wybór serwisu
		switch(myConfig.get("sms.currentService").toString())
		{
		case "dotpay":
			myService = new DotPayService(myConfig);
			break;
		case "microsms":
			myService = new MicroSmsService(myConfig);
			break;
			default:
				myLogger.WriteLog("Nie ma takiego typu serwisu. Zmien w config.yml");
				throw new Exception("Nie ma takiego typu serwisu. Zmien w config.yml");
		}
		myInv = new MyInventory(myReader, myService);
	}
	
	private void initCommands()
	{
		this.getCommand("shop").setExecutor(new CommandShop(myReader, myService, myLogger, myConfig, myInv));
		
		this.getServer().getPluginManager().registerEvents(new MyInventory(myReader, myService), this);
	}
	
	public void onEnable(){
		try {
			initCore();
		} catch (Exception e) {
			e.printStackTrace();
			myLogger.WriteLog(e.getMessage());
		}
		
		initCommands();
	}
}