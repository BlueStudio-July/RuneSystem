package com.qiyue.RuneSystem.Command;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.qiyue.RuneSystem.Main;
import com.qiyue.RuneSystem.Utils.playerdata;
import com.qiyue.RuneSystem.Utils.utils;
import com.qiyue.RuneSystem.rune.RuneSystem;
@SuppressWarnings("all")
public class RuneCommand implements CommandExecutor {

	String Prefix = "";
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String lable,String[] args) {
		   if(lable.equalsIgnoreCase("rune")){
			   Prefix = Main.instance.getConfig().getString("Prefix").replace("&", "§");
			   FileConfiguration config = Main.instance.getConfig();
				   if (args.length == 0)
				    {
				      sender.sendMessage("§b§m§l---------§8§l[ §d§l符文系统§8§l]§b§m§l---------");
				      sender.sendMessage("§6/rune open                    §f——§b打开你的符文页");
				      if(sender.isOp())
				      {
					
				      sender.sendMessage("§6/rune <player> open           §f——§b强制玩家打开符文页");
				      sender.sendMessage("§6/rune get <符文> <数量>       §f——§b获取符文  ");
				      sender.sendMessage("§6/rune list                    §f——§b列出所有符文");
				      sender.sendMessage("§6/rune give <player> <符文> <数量>  §f——§b给予玩家符文");
				      sender.sendMessage("§6/rune reload                  §f——§b重载插件");
				      }
				      return true;
				    
			   } 
				  if(args.length == 2 && args[1].equalsIgnoreCase("open")){
					  if(sender.isOp()){
					  if(Bukkit.getPlayer(args[0]) == null){
							sender.sendMessage(Prefix+"§c该玩家并不在线");
							return true;
					  }
					    Bukkit.dispatchCommand(sender, "rune open");
						sender.sendMessage(Prefix+"§a已强制玩家打开符文页."); 
					  }else{
				    	  sender.sendMessage(Prefix+"§c你没有权限使用这个命令!");	
				    	  return true;
				    	  }
				  }
				   
				   
				 if(args[0].equalsIgnoreCase("open")){
					 if(!(sender instanceof Player)){
						sender.sendMessage(Prefix+"§c非玩家无法使用此命令");
						return true;
					 }	
					 Player p = (Player) sender;
					 if(!playerdata.have(p.getName())){
						 playerdata.addDefault(p.getName());
					 }
					 p.closeInventory();
					 p.openInventory(RuneSystem.getInstance().getMenu(p));
					 return true;
				 }
				 
				  if(args[0].equalsIgnoreCase("give")){
				    	 
				    	 if(sender.isOp()){
				    	 
						 
						 Player p = (Player) sender;
				    	 if(args.length != 4){
				    		 sender.sendMessage(Prefix+"§f/rune give <player> <符文> <数量> ");
				    		 return true;
				    	 }
				    	  if(Bukkit.getPlayer(args[1]) == null){
				    		  sender.sendMessage(Prefix+"§c该玩家并不在线");
							  return true;
				    	  }
				    	 
				    	  if(config.getString("RuneItem."+args[2]) == null){
				    		  sender.sendMessage(Prefix+"§c此符文并不存在!");
					    	  return true;
				    	  }
				    	  if(!utils.isNumber(args[3])){
				    		  sender.sendMessage(Prefix+"§c数量请输入整数");
					    	  return true;
				    	  }
				    	  ItemStack item = RuneSystem.getInstance().getRune(args[2]);
				    	  item.setAmount(Integer.parseInt(args[3]));
				    	  Bukkit.getPlayer(args[1]).getInventory().addItem(item);
				    	  sender.sendMessage(Prefix+"§a已发送符文到§f"+ args[1] + "§a的背包");
				    	  return true;
				    	 }else{
				    	  sender.sendMessage(Prefix+"§c你没有权限使用这个命令!");	 
				    	  return true; 
				    		 
				    	 }
				     }
				 
				 
			     if(args[0].equalsIgnoreCase("get")){
			    	 
			    	 if(sender.isOp()){
			    	 
					 if(!(sender instanceof Player)){
							sender.sendMessage(Prefix+"§c非玩家无法使用此命令");
							return true;
					 }	
					 
					 Player p = (Player) sender;
			    	 if(args.length != 3){
			    		 sender.sendMessage(Prefix+"§f/rune get <符文> <数量> ");
			    		 return true;
			    	 }
			    	  if(config.getString("RuneItem."+args[1]) == null){
			    		  sender.sendMessage(Prefix+"§c此符文并不存在!");
				    	  return true;
			    	  }
			    	  if(!utils.isNumber(args[2])){
			    		  sender.sendMessage(Prefix+"§c数量请输入整数");
				    	  return true;
			    	  }
			    	  ItemStack item = RuneSystem.getInstance().getRune(args[1]);
			    	  item.setAmount(Integer.parseInt(args[2]));
			    	  p.getInventory().addItem(item);
			    	 
			    	  return true;
			    	 }else{
			    	  sender.sendMessage(Prefix+"§c你没有权限使用这个命令!");	 
			    	  return true; 
			    		 
			    	 }
			     }
				  if(args[0].equalsIgnoreCase("reload")){
					  if(sender.isOp()){
						  Main.instance.reloadConfig();
						  Main.data = playerdata.getDataFile();
						  sender.sendMessage(Prefix+"§a配置文件已重载.");
					  }else{
				      sender.sendMessage(Prefix+"§c你没有权限使用这个命令!");	
				      return true;
					  }
				  }
				  if(args[0].equalsIgnoreCase("list")){
					  if(sender.isOp()){
						sender.sendMessage("§a§m§l————————————————————————————————————");
						if(config.getConfigurationSection("RuneItem").getKeys(false).size() == 0){
						sender.sendMessage("§c无");
						}else{
						for(String s : config.getConfigurationSection("RuneItem").getKeys(false)){
							sender.sendMessage(Prefix+"§f"+s+"  "+RuneSystem.getInstance().getRune(s).getItemMeta().getDisplayName());
							}
						}
						sender.sendMessage("§a§m§l————————————————————————————————————");
						return true;
					  }else{
				      sender.sendMessage(Prefix+"§c你没有权限使用这个命令!");	
				      return true;
					  }
				  }
			   
			   
			   
			   
			   
			   
			   
			   return true;
		   }
		
		
		
		
		
		
		
		return false;
	}



}
