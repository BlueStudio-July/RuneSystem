package com.qiyue.RuneSystem.Listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import com.qiyue.RuneSystem.Main;
import com.qiyue.RuneSystem.Command.RuneCommand;
import com.qiyue.RuneSystem.Utils.playerdata;
import com.qiyue.RuneSystem.rune.RuneSystem;
@SuppressWarnings("all")
public class PlayerRuneMain implements Listener {

    HashMap<String, String> OpenMap = new HashMap();
	  
	@EventHandler
	public void PlayerClickRuneMenu(InventoryClickEvent e){
		
		if(!(e.getWhoClicked() instanceof Player)) { return; }	
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getInventory().getTitle().equals(p.getName() +  Main.instance.getConfig().getString("Normal.runename").replace("&", "§"))){
			
		String Prefix;
		Prefix = Main.instance.getConfig().getString("Prefix").replace("&", "§");
			
		if(e.getSlot() < 0 ){ return; }
				
		if(e.getRawSlot() > 44){ return; }
			
	    if(e.getSlot() > 8 && e.getSlot() < 35 ){
	    
	    	
	    if(e.isShiftClick()){
	    	
	    	 if(Main.data.getBoolean(p.getName()+".RunePage."+e.getSlot()+".enable"))
	 	    {
		 	  if(Main.data.getBoolean(p.getName()+".RunePage."+e.getSlot()+".use")){
		 			e.setCancelled(true);
		 			p.updateInventory();
		 			p.closeInventory();  	
		 			p.sendMessage(Prefix+"§c该符文页正在使用中!");
		 			return; 
		 	  }else{
					e.setCancelled(true);
		 			p.updateInventory();
		 			p.closeInventory();  	
		 	        for (String s : Main.data.getConfigurationSection(p.getName() + ".RunePage").getKeys(false))
		 	        {
		 	          if (!Main.data.getBoolean(p.getName() + ".RunePage." + s + ".use"))
		 	          continue;
		 	          Main.data.set(p.getName() + ".RunePage." + s + ".use",false);
		 	        }
		 			Main.data.set(p.getName()+".RunePage."+e.getSlot()+".use", true);
		 			playerdata.save();
		 			p.sendMessage(Prefix+"§a已使用该符文页!");
		 			return; 
		 	  }
	    		 
	    		 
	    		 
	 	    }else{
	 			e.setCancelled(true);
	 			p.updateInventory();
	 			p.closeInventory();  	
	 			p.sendMessage(Prefix+"§c该符文页并没有解锁");
	 			return;
	 	    }
	    	
	    	
	    }
	    	
	    	
	    	
	    if(e.isLeftClick()){
	    	
	    if(Main.data.getBoolean(p.getName()+".RunePage."+e.getSlot()+".enable"))
	    {
		e.setCancelled(true);
		p.updateInventory();
		p.closeInventory();  	
		p.openInventory(RuneSystem.getInstance().getRuneGui(p, e.getSlot()+""));
		this.OpenMap.put(p.getName(), e.getSlot()+"");
		return;
	    }
	    
	    else{
	       if (!Main.economy.has(p.getName(), Double.parseDouble(Main.instance.getConfig().getString("PayMoney"))))
	       {
	    	e.setCancelled(true);
	    	p.updateInventory();
	    	p.closeInventory();  	
	        p.sendMessage(Prefix+"§c你没有足够的金钱来解锁此符文页!");
	        return;
	       }
	       int n = 0;
	       int i = Main.instance.getConfig().getInt("RanePage.Normal");
	       
	          for (String s : Main.data.getConfigurationSection(p.getName() + ".RunePage").getKeys(false))
	          {
	            if (!Main.data.getBoolean(p.getName() + ".RunePage." + s + ".enable"))
	            {
	              continue;
	            }
	            n++;
	          }

	          for (String s : Main.instance.getConfig().getConfigurationSection("RanePage.authority").getKeys(false))
	          {
	            if (!p.hasPermission("rune.por." + s)){
	              continue;}
	            i = i + Main.instance.getConfig().getInt("RanePage.authority." + s);
	          }

	          if (i > 27)
	          {
	            i = 27;
	          }
	    
	
	          
	          if (n >= i)
	          {
			    e.setCancelled(true);
			    p.updateInventory();
	            p.closeInventory();
	            p.sendMessage(Prefix + "§c你没有权限解锁过多的符文页!");
	            return;
	          }   
	       
	       
	       
		e.setCancelled(true);
		p.updateInventory();
		p.closeInventory();  	
		Main.data.set(p.getName()+".RunePage."+e.getSlot()+".enable", true);
		playerdata.save();
	    Main.economy.withdrawPlayer(p.getName(), Double.parseDouble(Main.instance.getConfig().getString("PayMoney")));
		p.sendMessage(Prefix+"§a已花费金币解锁符文页");
		return;
	    }
	    
	    }
	    }
			
			
			
			
			
		e.setCancelled(true);
		p.updateInventory();
		p.closeInventory();
			
		}
		
		
		
	}
	
	
	@EventHandler
	public void PlayerClickRuneGui(InventoryClickEvent e){

	if(!(e.getWhoClicked() instanceof Player)) { return; }	
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getSlot() < 0 ){ return; }
		
		if(!this.OpenMap.containsKey(p.getName())){ 
			return; 
		}
		
		if(! Main.data.getString(p.getName()+".RunePage."+this.OpenMap.get(p.getName())+".disyname").equals(e.getInventory().getTitle())){
			return;
		}
		
		
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
			return;
		}
		
		for(String node : Main.instance.getConfig().getConfigurationSection("RuneItem").getKeys(false)){
			if(e.getCurrentItem().isSimilar(RuneSystem.getInstance().getRune(node))){
			  return;
			}
		}
		
		e.setCancelled(true);
		p.updateInventory();
		
		
		
	
		
	}
	
	
	@EventHandler
	public void PlayerCloseRuneGui(InventoryCloseEvent e){
		
	Player p = (Player) e.getPlayer();
	String Prefix = Main.instance.getConfig().getString("Prefix").replace("&", "§");
	if(this.OpenMap.containsKey(p.getName()) && Main.data.getString(p.getName()+".RunePage."+this.OpenMap.get(p.getName())+".disyname").equals(e.getInventory().getTitle())){
		
		ItemStack[] items = e.getInventory().getContents();
		
		String runes = "";
		
		Boolean b = false;
		
		for(int i = 9 ; i < 36 ; i++){
			
		ItemStack item = e.getInventory().getItem(i);
					
		if(item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains(Main.instance.getConfig().getString("RuneLogo")))
		  {
			 if(item.getAmount() > 1){
             b = true;
			 item.setAmount(item.getAmount() - 1);
             p.getInventory().addItem(item);
			 }
		     for(String node : Main.instance.getConfig().getConfigurationSection("RuneItem").getKeys(false))
		     {
		    	if(RuneSystem.getInstance().getRune(node).isSimilar(item)){
		    	 runes = runes + node + ",";
		    	}  
		      }
		    		
		}else{
	    	 runes = runes + "air" + ",";	
		}
		}
		if(b){
			 p.sendMessage(Prefix+"已退回叠加的符文数量");
		}
		runes = runes.substring(0,runes.length() - 1);
		Main.data.set(p.getName()+".RunePage."+ OpenMap.get(p.getName()) + ".ItemStack", runes); 
		playerdata.save();
		Main.data = playerdata.getDataFile();
		this.OpenMap.remove(p.getName());
		
	}
		
		
		
		
		
		
	}
	
	
	
	
}
