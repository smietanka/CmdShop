package me.goukan.cmdshop;

import me.goukan.cmdshop.Interfaces.ILogger;
import me.goukan.cmdshop.Interfaces.IReader;
import me.goukan.cmdshop.Types.Constans;
import me.goukan.cmdshop.Types.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class JsonReader implements IReader {

	private ILogger myLogger = null;
	private List<Item> allItems = new ArrayList<Item>();
	public JsonReader(ILogger myLogger)
	{
		this.myLogger = myLogger;
	}
	
	@Override
	public void Init(String fileName) {
		JSONParser parser = new JSONParser();
		try {
			File saveTo = new File("plugins/" + Constans.PLUGIN_NAME, fileName);
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
                // wpisywanie pustego obiektu json
                FileWriter fw = null;
        		try {
        			fw = new FileWriter(saveTo, true);
        	        PrintWriter pw = new PrintWriter(fw);

        	        pw.println("{\"Items\": [{\"Name\":\"test\", \"Description\": [\"jakis opis\"], \"SmsServiceId\": \"id uslugi (zostaw puste jesli niepotrzebne)\", \"SmsPrice\": \"cena\", \"SmsNumber\": \"numer na jaki wyslac sms\", \"SmsCode\": \"tresc smsa\", \"Commands\": [], \"SuccessMessages\": [\"kupiles usluge\"], \"ItemMaterial\":\"DIAMOND_AXE\"}]}");
        	        pw.flush();
        	        pw.close();
        	        this.myLogger.WriteLog("Zapisalem pusty obiekt JSON'a do pliku. Nalezy go skonfigurowac.");
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
            JSONObject jsonObject =(JSONObject) parser.parse(new FileReader("plugins/" + Constans.PLUGIN_NAME + "/" + fileName));
            
    		JSONArray itemList = (JSONArray) jsonObject.get("Items");
    		for(Object o : itemList)
    		{
    			JSONObject item = (JSONObject) o;
    			String name = (String) item.get("Name");
    			List<String> desc = getListStringFromJsonObj(item, "Description");
    			
    			String price = (String) item.get("SmsPrice");
    			String number = (String) item.get("SmsNumber");
    			String code = (String) item.get("SmsCode");
    			String material = (String) item.get("ItemMaterial");
    			String serviceId = (String) item.get("SmsServiceId");
    			
    			List<String> createdCommands = getListStringFromJsonObj(item, "Commands");
    			List<String> successMsg = getListStringFromJsonObj(item, "SuccessMessages");
    			
    			if(createdCommands.isEmpty())
    			{
    				myLogger.WriteLog("Usluga " + name + " nie posiada zadnych komend, nie dodaje jej do listy.");
    				continue;
    			}
    			else
    			{
    				Item myItem = new Item(name, desc, price, number, code, createdCommands, successMsg, material, serviceId);
        			allItems.add(myItem);
    			}
    		}
		}
		catch (ParseException e) {
			e.printStackTrace();
			myLogger.WriteLog(e.getLocalizedMessage() + e.getMessage());
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
            myLogger.WriteLog(e.getLocalizedMessage() + e.getMessage());
        }
		catch(IOException e) {
			e.printStackTrace();
			myLogger.WriteLog(e.getLocalizedMessage() + e.getMessage());
		}
	}
	private List<String> getListStringFromJsonObj(JSONObject obj, String key)
	{
		List<String> result = new ArrayList<String>();
		JSONArray temp = (JSONArray) obj.get(key);
		for(Object eachCmd : temp)
		{
			result.add(eachCmd.toString());
		}
		return result;
	}
	@Override
	public List<Item> Read() {
		return allItems;
	}
}