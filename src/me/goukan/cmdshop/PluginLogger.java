package me.goukan.cmdshop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import me.goukan.cmdshop.Interfaces.ILogger;
import me.goukan.cmdshop.Types.Constans;

/**
 * @author Kacper
 * 
 * www.linkedin.com/in/kacperdrapala
 * http://www.mpcforum.pl/user/887095-goukan/
 */
public class PluginLogger implements ILogger {

	private File saveTo = null;
	private Logger consoleLogger = null;
	
	public PluginLogger(String fileName, Logger consoleLogger)
	{
		this.consoleLogger = consoleLogger;
		try
        {
            saveTo = new File("plugins/" + Constans.PLUGIN_NAME, fileName);
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }
 
        } catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	@Override
	public void WriteLog(String msg) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		
		consoleLogger.info("["+timeStamp+"] - " + msg);
        FileWriter fw = null;
		try {
			fw = new FileWriter(saveTo, true);
	        PrintWriter pw = new PrintWriter(fw);

	        pw.println("["+timeStamp+"] - " + msg);
	        pw.flush();
	        pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}