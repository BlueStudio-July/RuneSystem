package com.qiyue.RuneSystem.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.qiyue.RuneSystem.Utils.playerdata;
@SuppressWarnings("all")
public class PlayerFirstJoin implements Listener {

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(!playerdata.have(p.getName())){
			playerdata.addDefault(p.getName());
		}
	}
	
	

}
