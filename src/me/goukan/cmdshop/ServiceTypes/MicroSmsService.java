package me.goukan.cmdshop.ServiceTypes;

import java.util.regex.Pattern;

import org.bukkit.configuration.file.FileConfiguration;

import me.goukan.cmdshop.Interfaces.IService;
import me.goukan.cmdshop.Types.HttpRequest;
import me.goukan.cmdshop.Types.Item;

public class MicroSmsService implements IService {
	
	private final String CODE_URL = "http://microsms.pl/api/check.php?userid=%s&number=%s&code=%s&serviceid=%s";
	private final String PRE_CODE = "MSMS";
	private FileConfiguration myConfig = null;
	
	public MicroSmsService(FileConfiguration config)
	{
		this.myConfig = config;
	}
	
	@Override
	public String GetPreCode() {
		return PRE_CODE;
	}

	@Override
	public boolean CheckCode(String code, Item item) throws Exception {
		String pattern = "[0-9A-Za-z]{8}";
		Pattern pr = Pattern.compile(pattern);
		
		String clientId = myConfig.get("sms.clientId").toString();
		String serviceId = myConfig.get("sms.serviceId").toString();
		
		if(clientId != "0" || serviceId != "0")
		{
			if(!pr.matcher(code).find())
			{
				return false;
			}
			
			String createdUrl = String.format(CODE_URL, clientId, item.getSmsNumber(), code, serviceId);
			String response = HttpRequest.getHTML(createdUrl);
			if(response != null && !response.isEmpty())
			{
				return response.substring(0, 1).equals("1") ? true : false;
			}
		}
		else
		{
			throw new Exception("sms.clientId or sms.serviceId is not changed in config.yml");
		}
		return false;
	}
}
