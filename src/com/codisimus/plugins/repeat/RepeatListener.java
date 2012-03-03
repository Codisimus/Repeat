package com.codisimus.plugins.repeat;

import java.util.Properties;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Listens for repeat command and logs every other command
 *
 * @author Codisimus
 */
public class RepeatListener implements Listener {
    static String cmd = "/r";
    public static Properties memory = new Properties();

    /**
     * Executes repeat command with replacement and logs every other command
     * 
     * @param event the PlayerCommandPreprocessEvent that occurred
     */
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        String msg = event.getMessage();
        String[] args = msg.split(" ");
        
        if (args[0].equals(cmd)) {
            //Cancel command event so an error is not displayed by Bukkit
            event.setCancelled(true);
            
            //Find the previous command
            msg = memory.getProperty(name);
            if (msg == null) {
                event.getPlayer().sendMessage("Previous command cannot be repeated because no previous command was found");
                return;
            }

            //Check if there will be replacement
            if (args.length >= 3) {
                String newMsg = null;
                
                //Dispatch commands replacing the 2nd word with every word that follows
                for (int i = 2; i < args.length; i++) {
                    newMsg = msg.replaceFirst("/"+args[1]+" ", "/"+args[i]+" ");
                    newMsg = newMsg.replaceAll(" "+args[1]+" ", " "+args[i]+" ");
                    if (newMsg.endsWith(" "+args[1]))
                        newMsg = newMsg.substring(0, newMsg.length() - args[1].length()).concat(args[i]);
                    
                    Repeat.server.dispatchCommand(player, newMsg);
                }
                
                //Update the memory with the last command that was dispatched
                memory.setProperty(name, newMsg);
            }
            else
                Repeat.server.dispatchCommand(player, msg);
        }
        else
            //Update the memory with the new command
            memory.setProperty(name, msg.substring(1));
    }
}