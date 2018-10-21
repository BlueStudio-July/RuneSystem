package com.qiyue.RuneSystem.Utils;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.qiyue.RuneSystem.Main;

public class config {

	
	public static void create(){
		JavaPlugin instance = Main.instance;
		if (!instance.getDataFolder().exists()) {
			instance.getDataFolder().mkdir();
			instance.reloadConfig();
			   }
		    File file = Main.cfg;
			if (!file.exists())instance.saveDefaultConfig();
			instance.reloadConfig();
	}
	
	

}
