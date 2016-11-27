package me.goukan.cmdshop.ServiceTypes;

import java.util.regex.Pattern;

import org.bukkit.configuration.file.FileConfiguration;

import me.goukan.cmdshop.Interfaces.IService;
import me.goukan.cmdshop.Types.HttpRequest;
import me.goukan.cmdshop.Types.Item;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class DotPayService implements IService {
	
	private final String CODE_URL = "http://dotpay.pl/check_code.php?&check=%s&id=%s&code=%s&type=sms&del=1";
	private final String PRE_CODE = "AP";
	private FileConfiguration myConfig = null;
	public DotPayService(FileConfiguration config)
	{
		this.myConfig = config;
	}
	
	@Override
	public boolean CheckCode(String code, Item item) throws Exception {
		String pattern = "[0-9A-Za-z]";
		Pattern pr = Pattern.compile(pattern);
		
		String clientId = myConfig.get("sms.clientId").toString();
		if(clientId != "0")
		{
			if(!pr.matcher(code).find())
			{
				return false;
			}
			
			String createdUrl = String.format(CODE_URL, code, clientId, item.getSmsCode());
			String response = HttpRequest.getHTML(createdUrl);
			if(response.charAt(0) == '1')
			{
				return true;
			}
		}
		else
		{
			throw new Exception("sms.clientId is not changed in config.yml");
		}
		
		return false;
	}

	@Override
	public String GetPreCode() {
		return PRE_CODE;
	}
}