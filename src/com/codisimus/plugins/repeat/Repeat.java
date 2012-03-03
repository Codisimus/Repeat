package com.codisimus.plugins.repeat;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Loads Plugin and registers events
 *
 * @author Codisimus
 */
public class Repeat extends JavaPlugin {
    static Server server;
    
    /**
     * Informs the Server Admin that memory is lost when disabling
     * 
     */
    @Override
    public void onDisable () {
        System.out.println("[Repeat] Memory of commands cleared");
    }

    /**
     * Registers the CommandListener
     * 
     */
    @Override
    public void onEnable () {
        server = getServer();
        server.getPluginManager().registerEvents(new RepeatListener(), this);
        System.out.println("Repeat "+this.getDescription().getVersion()+" is enabled! ("+RepeatListener.cmd+" to repeat commands)");
    }
}