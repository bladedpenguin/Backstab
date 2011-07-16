package com.bladedpenguin.backstab.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
    private final Backstab plugin;

    public BackstabEntityListener(Backstab instance) {
        plugin = instance;
        
    }
    public void onEntityDamage(EntityDamageEvent e){ //if this doesn't work try EntityDamageEvent and uncomment below
    	if (!(e instanceof EntityDamageByEntityEvent )){
    		return;
    	}
    	EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e; 
    	Entity rogue = event.getDamager();
    	if (event.isCancelled())
    		return;
    	if (!(rogue instanceof Player)) 
    		return;
    	rogue = (Player) rogue;
    	if (!Backstab.permissionHandler.permission((Player) rogue, "backstab.stab"))
    		return;
    	if (!(event.getEntity() instanceof LivingEntity))
    		return;
    	LivingEntity victim = (LivingEntity) event.getEntity();
    	int base = event.getDamage();
    	
    	double q = victim.getLocation().getDirection().dot(rogue.getLocation().getDirection());
    	if (q > 0){
    		if (plugin.isNarrowTarget())
        		q = q*q*q; 
    		int bonus = (int) (q * plugin.getBonusMultiplier() * base);
    		if (bonus == 0)
    			return;
    		((Player)  rogue).sendMessage("Backstab: " + (bonus * .5) + " extra hearts");
    		if (victim instanceof Player){
    			((Player) event.getEntity()).sendMessage("You've been stabbed in the back!");
    		}
    		if (plugin.isIgnoreArmor()){
    			int health = victim.getHealth() - (base + bonus);
    			if (health > 0){
    				victim.setHealth(health);
    				event.setDamage(1);
    			}else{
    				victim.setHealth(1);
    				event.setDamage(base+bonus);
    			}
    		} else
    			event.setDamage(base + bonus);	
    	}
    }
    //Insert Player related code here
}
