package com.qiyue.RuneSystem;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.qiyue.RuneSystem.Utils.playerdata;
import com.qiyue.RuneSystem.rune.RuneSystem;

import me.clip.placeholderapi.external.EZPlaceholderHook;

@SuppressWarnings("all")
public class Placeholder extends EZPlaceholderHook{

	 public Placeholder (Main main) {
           super(main,"RuneSystem");
     }

     @Override
     public String onPlaceholderRequest(Player p, String str) {
    	 if(str.equals("level")){
    		  if(playerdata.getDataFile().getString(p.getName()) == null){
    			  return "0";
    		  }
    		  return String.valueOf(RuneSystem.getInstance().getlevel(p, RuneSystem.getInstance().getUseRune(p)));
    	 }
    	 if(str.equals("number")){
   		  if(playerdata.getDataFile().getString(p.getName()) == null){
   			  return "0";
   		  }
   		  int i = 0;
   		  for(String s : playerdata.getDataFile().getConfigurationSection(p.getName()+".RunePage").getKeys(false)){
   			 if(playerdata.getDataFile().getBoolean(p.getName()+".RunePage."+s+".enable")){
   				 i++;
   			 }
   		  }
   		  return String.valueOf(i);
   	 }
    	 if(str.equals("use")){
      		  if(playerdata.getDataFile().getString(p.getName()) == null){
      			  return "";
      		  }
      		  for(String s : playerdata.getDataFile().getConfigurationSection(p.getName()+".RunePage").getKeys(false)){
      			 if(playerdata.getDataFile().getBoolean(p.getName()+".RunePage."+s+".use")){
      		   		  return String.valueOf(playerdata.getDataFile().getString(p.getName()+".RunePage."+s+".disyname"));
      			 }
      		  }
      	 }
    	 
    	 
    	 
    	 
         return null;
     }
	 
	

}
