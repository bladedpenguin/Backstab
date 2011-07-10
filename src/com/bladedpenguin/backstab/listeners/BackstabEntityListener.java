package com.bladedpenguin.backstab.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import com.bladedpenguin.backstab.*;

/**
 * Handle events for all Player related events
 * @author locutus/bladedpenguin
 */
public class BackstabEntityListener extends EntityListener {
    //private final Backstab plugin;

    public BackstabEntityListener(Backstab instance) {
        //plugin = instance;
        
    }
    public void onEntityDamage(EntityDamageEvent e){ //if this doesn't work try EntityDamageEvent and uncomment below
    	if (!(e instanceof EntityDamageByEntityEvent )){
    		return;
    	}
    	EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e; 
    	Entity rogue = event.getDamager();
    	if (rogue instanceof Player) {
    		rogue = (Player) rogue;
    		  if (!Backstab.permissionHandler.has((Player) rogue, "backstab.stab")) { 
    		      return;
    		  }
    	}
    	else {
    		return;
    	}
    	int base = event.getDamage();
    	int  bonus = (int) (event.getEntity().getLocation().getDirection().dot(rogue.getLocation().getDirection())*base*2);
    	if (bonus > 0){
    		((Player)  rogue).sendMessage("Backstab: " + bonus*.5 + " extra hearts");
    		if (event.getEntity() instanceof Player){
    			((Player) event.getEntity()).sendMessage("You've been stabbed in the back!");
    		}
    		event.setDamage(base + bonus);	
    	}
    }
    //Insert Player related code here
}
