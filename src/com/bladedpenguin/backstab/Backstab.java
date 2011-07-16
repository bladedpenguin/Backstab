package com.bladedpenguin.backstab;

import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.bladedpenguin.backstab.listeners.BackstabEntityListener;

public class Backstab extends JavaPlugin {

		private double bonusMultiplier;
		private boolean narrowTarget;
		private boolean ignoreArmor;
	    private final BackstabEntityListener entityListener = new BackstabEntityListener(this);
	    public static PermissionHandler permissionHandler;
	    
	    // NOTE: There should be no need to define a constructor any more for more info on moving from
	    // the old constructor see:
	    // http://forums.bukkit.org/threads/too-long-constructor.5032/

	    public void onDisable() {
	        // TODO: Place any custom disable code here

	        // NOTE: All registered events are automatically unregistered when a plugin is disabled

	        // EXAMPLE: Custom code, here we just output some info so we can check all is well
	        System.out.println("No more Backstabbery");
	    }

	    public void onEnable() {
	        // TODO: Place any custom enable code here including the registration of any events

	    	// Enable permissions
	    	setupPermissions();
	        // Register our events
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Normal, this);
	        
	        //get configs
	        getConfiguration().load();
	        setBonusMultiplier(getConfiguration().getDouble("bonusMultiplier", 2.0));
	        setNarrowTarget(getConfiguration().getBoolean("narrowTarget",false));
	        setIgnoreArmor(getConfiguration().getBoolean("ignoreArmor",false));
	        getConfiguration().save();
	        
	        // EXAMPLE: Custom code, here we just output some info so we can check all is well
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	    }
	    private void setupPermissions() {
	        if (permissionHandler != null) {
	            return;
	        }
	        
	        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	        
	        if (permissionsPlugin == null) {
	            log("Permission system not detected, defaulting to OP");
	            return;
	        }
	        
	        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	        log("Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
	    }
	    
	    private void log(String string) {
			System.out.println(string);
		}
	    protected static Logger logger() {
	        return Logger.getLogger("Minecraft");
	    }

		public void setBonusMultiplier(double bonusMultiplier) {
			this.bonusMultiplier = bonusMultiplier;
		}

		public double getBonusMultiplier() {
			return bonusMultiplier;
		}

		public void setNarrowTarget(boolean narrowTarget) {
			this.narrowTarget = narrowTarget;
		}

		public boolean isNarrowTarget() {
			return narrowTarget;
		}

		public void setIgnoreArmor(boolean ignoreArmor) {
			this.ignoreArmor = ignoreArmor;
		}

		public boolean isIgnoreArmor() {
			return ignoreArmor;
		}


	    
/*		public boolean isDebugging(final Player player) {
	        if (debugees.containsKey(player)) {
	            return debugees.get(player);
	        } else {
	            return false;
	        }
	    }

	    public void setDebugging(final Player player, final boolean value) {
	        debugees.put(player, value);
	    }*/
}
