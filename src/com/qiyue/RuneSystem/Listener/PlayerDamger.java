package com.qiyue.RuneSystem.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.qiyue.RuneSystem.Utils.playerdata;
import com.qiyue.RuneSystem.rune.RuneSystem;
import com.qiyue.StyleLore.PlayerAttribute.TickAttributeEvent;
import com.qiyue.StyleLore.SetAttribute.AttributeEvent;
import com.qiyue.StyleLore.SetAttribute.Materials.AttributeMaterial;

@SuppressWarnings("all")
public class PlayerDamger implements Listener{

  @EventHandler
  public void AttributeEntity(AttributeEvent event) {
	  Player p = (Player) event.getEntity();
	  if(!playerdata.have(p.getName())) {
		  return;
	  }
	  for(String att : RuneSystem.getInstance().getRuneAttribute(p,Integer.parseInt(RuneSystem.getInstance().getUseRune(p)))) {
		  if(att.split(":")[0].equals("Armor") || att.split(":")[0].equals("DodgeJudge") || att.split(":")[0].equals("Thorns")) {
			  event.setAttribute(AttributeMaterial.valueOf(att.split(":")[0]), Double.parseDouble(att.split(":")[1]));
		  }
	  }
  }
  
  @EventHandler
  public void AttributeDamage(AttributeEvent event) {
	  Player p = (Player) event.getDamger();
	  if(!playerdata.have(p.getName())) {
		  return;
	  }
	  for(String att : RuneSystem.getInstance().getRuneAttribute(p,Integer.parseInt(RuneSystem.getInstance().getUseRune(p)))) {
		  if(att.split(":")[0].equals("Armor") || att.split(":")[0].equals("DodgeJudge") || att.split(":")[0].equals("Thorns")) {
			  event.setAttribute(AttributeMaterial.valueOf(att.split(":")[0]), Double.parseDouble(att.split(":")[1]));
		  }
	  }
  }

  @EventHandler
  public void TickAttribute(TickAttributeEvent event) {
	  Player p = event.getPlayer();
	  if(!playerdata.have(p.getName())) {
		  return;
	  }
	  for(String att : RuneSystem.getInstance().getRuneAttribute(p,Integer.parseInt(RuneSystem.getInstance().getUseRune(p)))) {
		  if(att.split(":")[0].equals("Speed")) {
	         event.setSpeed(event.getSpeed() + com.qiyue.StyleLore.PlayerAttribute.TickAttribute.ofs(Double.parseDouble(att.split(":")[1])));
		  }
		  if(att.split(":")[0].equals("MaximumLife")) {
			  event.setMaxHealth(event.getMaxHelath() + Double.parseDouble(att.split(":")[1]));
		  }
		  
	  }
  }
  
}
