package com.qiyue.RuneSystem.rune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.qiyue.RuneSystem.Main;
import com.qiyue.StyleLore.StyleAPI;
import com.qiyue.StyleLore.SetAttribute.Materials.AttributeMaterial;
@SuppressWarnings("all")
public class RuneSystem {

	
	/**
	 * ��ȡAPI
	 */
	public static RuneSystem getInstance(){
		return new RuneSystem();
	}
	

	/**
	 * ��ȡָ��ҳ��ķ���GUI
	 */
	  public Inventory getRuneGui(Player p,String Page)
	  {
		  
	    Inventory RuneGui = Bukkit.createInventory(null, 45,Main.data.getString(p.getName()+".RunePage."+Page+".disyname"));
		FileConfiguration data = Main.data;
		    for(int i = 0 ; i < 9 ; i++){
		    	RuneGui.setItem(i, getItem1());
		    	RuneGui.setItem(i+36, getItem2());
		    }
		String[] rune = Main.data.getString(p.getName()+".RunePage."+Page+".ItemStack").split(",");
		int i = 9;
		for(String node : rune){
		if(!node.equals("air"))
		RuneGui.setItem(i, getRune(node));
		i++;
		}
		    
		    
		    
		  
		  return RuneGui;
		  
	  }
	
	 /**
	  * ��ȡ��ҳ��
	  */
	  public Inventory getMenu(Player p)
	  {
	    Inventory RuneGui = Bukkit.createInventory(null, 45, p.getName() + Main.instance.getConfig().getString("Normal.runename").replace("&", "��"));
	    FileConfiguration data = Main.data;
	    for(int i = 0 ; i < 9 ; i++){
	    	RuneGui.setItem(i, getItem1());
	    	RuneGui.setItem(i+36, getItem2());
	    }
	    for(String node : data.getConfigurationSection(p.getName()+".RunePage").getKeys(false)){
	    	if(data.getBoolean(p.getName()+".RunePage."+node+".enable")){
	    	RuneGui.setItem(Integer.parseInt(node), getItem3(p,node));
	    	}else{
		    RuneGui.setItem(Integer.parseInt(node), getItem4(p,node));	
	    	}
	    }
	    
	    
	    
	    return RuneGui;
	  }

        /**
         * ��ȡָ������ҳ����������
         */
	  
	    public List<String> getRuneAttribute(Player p,int node){
	    String[] rune = Main.data.getString(p.getName()+".RunePage."+node+".ItemStack").split(",");
	    HashMap<String, Double> hm = new HashMap<String, Double>();
	    for(AttributeMaterial att: AttributeMaterial.values()) {
	    	hm.put(att.getName(), 0.0);
	    }
	    hm.put("MaximumLife",0.0);
	    hm.put("Speed", 0.0);
	    for(String n : rune){
	    	if(!n.equals("air")) {
	    		List<String> list = Main.instance.getConfig().getStringList("RuneItem."+n+".Attribute");
	    	    for(String att : list) {
	    	    	hm.put(att.split(":")[0], hm.get(att.split(":")[0]) + Double.parseDouble(att.split(":")[1]));
	    	    }
	    	}
	    }
	    List<String> aList = new ArrayList<>();
	    for(String s : hm.keySet()) {
	    	aList.add(s + ":" + hm.get(s));
	    }
	    return aList;
	    }
	  
	  
	    /**
	     * �������Ҫ��,������ȡװ����Ʒ��
	     */
	    
		public ItemStack getItem4(Player p,String page){
			 FileConfiguration config = Main.instance.getConfig();
			 ItemStack item = get(config.getString("Gui.ID4"));
			 ItemMeta im = item.getItemMeta();
			 im.setDisplayName(Main.data.getString(p.getName()+".RunePage."+page+".disyname"));
			 List<String> lore = config.getStringList("Gui.ID4Lore");
			 for(int i = 0 ; i < lore.size() ; i++){
				 lore.set(i, lore.get(i).replace("&", "��").replace("{money}", Main.instance.getConfig().getString("PayMoney")));
			 }
			 im.setLore(lore);
			 item.setItemMeta(im);
			 return item;
		 }
		
		
		
	public double gets(double d){
		if(d > 100){
			return 100;
		}
		    return d;
	}
		
	
	 /**
     * �������Ҫ��,������ȡװ����Ʒ��
     */
	public ItemStack getItem3(Player p,String page){
		 FileConfiguration config = Main.instance.getConfig();
		 ItemStack item = get(config.getString("Gui.ID3"));
		 ItemMeta im = item.getItemMeta();
		 im.setDisplayName(Main.data.getString(p.getName()+".RunePage."+page+".disyname"));
		 im.addEnchant(Enchantment.ARROW_DAMAGE, 10, true);
		 List<String> lore = config.getStringList("Gui.ID3Lore");
		 List<String> AttLore = this.getRuneAttribute(p, Integer.parseInt(page));
		 List<String> AttLores = new ArrayList<>();
		 List<String> lores = new ArrayList<>();
		 for(int i = 0 ; i < lore.size(); i++){
			if(lore.get(i).equals("{Rune_Attribute}")){
			   boolean b = false;
			  for(String node : AttLore){
				  String test = "";
				  if(node.split(":")[0].equals("Speed")) {
					   test = "����";
				   }
				   if(node.split(":")[0].equals("MaximumLife")) {
					   test = "�������";
				   }
			       if(test.equals("")) {
			    	  test = StyleAPI.getByName(AttributeMaterial.valueOf(node.split(":")[0]));
			       }
			  if(Main.instance.getConfig().getBoolean("Vau.enable")){
				  
				  if(!node.split(":")[1].equals("0") && !node.split(":")[1].equals("0.0"))
				  {
					 lores.add(Main.instance.getConfig().getString("Vau.lore").replace("&", "��").replace("%num%",node.split(":")[1]).replace("%Attribute%",test));	 
					 b = true;
				  }
				 }else{
				    lores.add(Main.instance.getConfig().getString("Vau.lore").replace("&", "��").replace("%num%",node.split(":")[1]).replace("%Attribute%",test));	 
					b = true;
				 }
			  }
			
			  if(!b){
				  lores.add(Main.instance.getConfig().getString("NoAttribute").replace("&","��"));
			  }
			  
			 continue;
			}
		 lores.add(lore.get(i).replace("&", "��").replace("{level}", this.getlevel(p,page)+""));
		 }
		 im.setLore(lores);
		 item.setItemMeta(im);
		 return item;
	}
	
	 /**
     * �������Ҫ��
     */
	public ItemStack get(String s){
		 if(s.contains(":")){
			 return new ItemStack(Integer.parseInt(s.split(":")[0]),1,Short.valueOf(s.split(":")[1]));
		 }else{
			 return new ItemStack(Integer.parseInt(s),1);
		 }
	 }
	
	 /**
     * ��ȡ�������ʹ�õķ���ҳ����
     */
	public String getUseRune(Player p){
		
		for(String node : Main.data.getConfigurationSection(p.getName()+".RunePage").getKeys(false)){
			
			if(Main.data.getBoolean(p.getName() + ".RunePage."+node+".use")){
				return node;
			}
			
			
		}
		
		return null;
	}
	
	 /**
     * ��ȡ��ҵ�ָ������ҳ�ĵȼ�
     */
	public int getlevel(Player p,String node){
	
	String[] rune = Main.data.getString(p.getName()+".RunePage."+node+".ItemStack").split(",");
	
	int level = 0;
	
	for(String n : rune){
		if(!n.equals("air")){
		level = level + Main.instance.getConfig().getInt("RuneItem."+n+".Level");
		}
	}

	return level;
	}
	
	 /**
     * �������Ҫ��,������ȡװ����Ʒ��
     */
	 public ItemStack getItem1(){
		 FileConfiguration config = Main.instance.getConfig();
		 ItemStack item = get(config.getString("Gui.ID1"));
		 ItemMeta im = item.getItemMeta();
		 im.setDisplayName(" ");
		 item.setItemMeta(im);
		 return item;
	 }
	 /**
	     * �������Ҫ��,������ȡװ����Ʒ��
	     */
	 public ItemStack getItem2(){
		 FileConfiguration config = Main.instance.getConfig();
		 ItemStack item = get(config.getString("Gui.ID2"));
		 ItemMeta im = item.getItemMeta();
		 im.setDisplayName(" ");
		 item.setItemMeta(im);
		 return item;
	 }
	
	 
	 /**
	     * ��ȡ������Ʒ
	     */
	 public ItemStack getRune(String node){
		 FileConfiguration config = Main.instance.getConfig();
		 ItemStack item = get(config.getString("RuneItem."+node+".Id"));
		 ItemMeta im = item.getItemMeta();
		 im.setDisplayName(config.getString("RuneLogo") + config.getString("RuneItem."+node+".Name").replace("&","��"));
		 List<String> lore = config.getStringList("RuneItem."+node+".Lore");
		 for(int i = 0 ; i < lore.size() ; i++){
			 lore.set(i, lore.get(i).replace("&", "��"));
		 }
		 im.setLore(lore);
		 item.setItemMeta(im);
		 return item;
	 }
	 
	 
	 
	 
	
	
	

}
