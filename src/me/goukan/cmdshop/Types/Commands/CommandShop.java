package me.goukan.cmdshop.Types.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.goukan.cmdshop.Interfaces.IInv;
import me.goukan.cmdshop.Interfaces.ILogger;
import me.goukan.cmdshop.Interfaces.IReader;
import me.goukan.cmdshop.Interfaces.IService;
import me.goukan.cmdshop.Types.Constans;
import me.goukan.cmdshop.Types.Item;
import me.goukan.cmdshop.Types.MsgUtils;

/**
 * @author Kacper
 * 
 *         www.linkedin.com/in/kacperdrapala
 *         http://www.mpcforum.pl/user/887095-goukan/
 */
public class CommandShop implements CommandExecutor {

	private IReader myReader = null;
	private IService myService = null;
	private ILogger myLogger = null;
	private FileConfiguration myConfig = null;
	private IInv smsShopInventory = null;

	public CommandShop(IReader myReader, IService myService, ILogger myLogger, FileConfiguration myConfig,
			IInv myInventory) {
		this.myReader = myReader;
		this.myService = myService;
		this.myLogger = myLogger;
		this.myConfig = myConfig;
		this.smsShopInventory = myInventory;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			List<Item> allItems = myReader.Read();
			if (allItems.isEmpty()) {
				player.sendMessage("Aktualnie nie ma zadnych uslug w sklepie.");
				myLogger.WriteLog("Gracz " + sender.getName()
						+ " chcial wyswietlic liste produktow w sklepie, jednak tych produktow nie ma. Sprawdz czy w pliku z produktami sa jakies uslugi wpisane.");
				return true;
			}
			switch (args.length) {
			case 1:
				// /shop list
				if (args[0].equalsIgnoreCase("list")) {
					if (!myConfig.getBoolean("default.useInventoryDesign")) {
						MsgUtils.sendCenteredMessage(player, "&8&m-----&6Lista uslug&8&m-----");
						for (Item eachItem : allItems) {
							player.sendMessage(MsgUtils.ConvertToMc("&9Nazwa uslugi: &6%s (%s zl)", eachItem.getName(),
									eachItem.getSmsPrice()));
							player.sendMessage(MsgUtils.ConvertToMc("&9Numer uslugi: &6%s", eachItem.getId()));
							if (!eachItem.getDescription().isEmpty()) {
								player.sendMessage(MsgUtils.ConvertToMc("&9&oOpis: "));
								for (String eachDesc : eachItem.getDescription()) {
									player.sendMessage(MsgUtils.ConvertToMc(eachDesc));
								}
							}
							player.sendMessage(ChatColor.translateAlternateColorCodes('&',
									"&8&m-------------------------------------------------"));
						}
					} else {
						player.openInventory(smsShopInventory.getInv());
					}

					return true;
				}
				// /shop <nazwa_uslugi|numer_uslugi>
				for (Item eachItem : allItems) {
					if ((eachItem.getId().toString().equalsIgnoreCase(args[0]))
							|| (eachItem.getName().equalsIgnoreCase(args[0]))) {
						MsgUtils.sendCenteredMessage(player, String.format("&8&m-----&6%s (%s zl)&8&m-----",
								eachItem.getName(), eachItem.getSmsPrice()));
						if (!eachItem.getDescription().isEmpty()) {
							player.sendMessage(MsgUtils.ConvertToMcCenter("&9&oOpis: "));
							for (String eachDesc : eachItem.getDescription()) {
								player.sendMessage(MsgUtils.ConvertToMcCenter(eachDesc));
							}
						}
						MsgUtils.sendCenteredMessage(player,
								String.format(
										"&dAby zakupic te usluge nalezy wyslac smsa o tresci: &6%s.%s &dna numer &6%s. ",
										myService.GetPreCode(), eachItem.getSmsCode(), eachItem.getSmsNumber()));
						MsgUtils.sendCenteredMessage(player,
								String.format(
										"&dOtrzymasz kod zwrotny, ktory nalezy wpisac przy komendzie: &6/shop <%s|%s> <kod_zwrotny>",
										eachItem.getName(), eachItem.getId()));
						return true;
					}
				}
				MsgUtils.sendCenteredMessage(player, "Nie znaleziono takiej uslugi.");
				break;
			case 2:
				// /shop <nazwa_uslugi|numer_uslugi> <kod_zwrotny>
				boolean foundItem = false;
				Item myItem = null;
				for (Item eachItem : allItems) {
					if ((eachItem.getId().toString().equalsIgnoreCase(args[0]))
							|| (eachItem.getName().equalsIgnoreCase(args[0]))) {
						foundItem = true;
						myItem = eachItem;
						break;
					}
				}
				if (foundItem) {
					try {
						if (myService.CheckCode(args[1].toUpperCase(), myItem)) {
							myLogger.WriteLog("Gracz " + sender.getName() + " kupil usluge " + myItem.getName());
							if (myItem.getSuccessMsg().isEmpty()) {
								player.sendMessage(ChatColor.GREEN + "Gratuluje! Zakupiles usluge " + myItem.getName());
							} else {
								for (String eachSucc : myItem.getSuccessMsg()) {
									player.sendMessage(MsgUtils.ConvertToMc(eachSucc));
								}
							}

							for (String eachCmd : myItem.getCommands()) {
								Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
										(String) eachCmd.replace("{PLAYER}", sender.getName()));
							}
							return true;
						} else {
							myLogger.WriteLog(
									"Gracz " + sender.getName() + " wpisal zly kod dla uslugi " + myItem.getName());
							MsgUtils.sendCenteredMessage(player,
									"&4Kod jest niepoprawny dla danej uslugi. Sprawdz jeszcze raz.");
							return true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						myLogger.WriteLog(e.getMessage());
					}
				} else {
					MsgUtils.sendCenteredMessage(player, "Nie znaleziono takiej uslugi.");
					return true;
				}
				break;

			default:
				MsgUtils.sendCenteredMessage(player, "&8&m-----&6CmdShop&8&m-----");
				Integer counter = 1;
				for (String eachMsg : Constans.ALL_USAGE_SHOP) {
					player.sendMessage(MsgUtils.ConvertToMc("&9%s. &3%s", counter.toString(), eachMsg));
					counter++;
				}
				player.sendMessage(MsgUtils.ConvertToMc("&8&m-----------------------------------------------"));
				return true;
			}
		}
		return true;
	}
}