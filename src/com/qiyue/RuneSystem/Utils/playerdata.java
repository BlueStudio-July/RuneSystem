package com.qiyue.RuneSystem.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.qiyue.RuneSystem.Main;
@SuppressWarnings("all")
public class playerdata {

	public static void create(){
		JavaPlugin instance = Main.instance;
	    File file = Main.player;
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static FileConfiguration getDataFile()
	{
	return Main.player.exists() ? YamlConfiguration.loadConfiguration(Main.player) : new YamlConfiguration();
	}
	
	public static boolean have(String PlayerName){
		return playerdata.getDataFile().getString(PlayerName) != null;
	}
	
	public static void save(){
		try {
			Main.data.save(Main.player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addDefault(String PlayerName){
		
	FileConfiguration data = Main.data;
	FileConfiguration config = Main.instance.getConfig();
	
	for(int i = 0 ; i < 27 ; i++){
	data.set(PlayerName+".RunePage."+(i+9)+".disyname", config.getString("Normal.disyname").replace("&", "¡ì").replace("{page}", (i+1)+""));
	data.set(PlayerName+".RunePage."+(i+9)+".enable",config.getInt("Normal.page") >= i + 1);
	data.set(PlayerName+".RunePage."+(i+9)+".use",config.getInt("Normal.use") == i + 1);
	String Item = "";
	for(int a = 0 ; a < 27 ; a++){
	Item = Item + "air,";
	}
	Item = Item.substring(0,Item.length() - 1);
	data.set(PlayerName+".RunePage."+(i+9)+".ItemStack", Item);
	}
	Main.data = data;
	playerdata.save();
	
	}
	
	
	
	
	
	

}
