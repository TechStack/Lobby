package com.projectreddog.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Lobby extends JavaPlugin implements Listener {

	public static final String sorryVIPOnlyMessage ="Sorry you must be a VIP to access this command." ;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ( sender instanceof Player){
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("SPEED")){
				if (p.hasPermission("LOBBY.VIP.SPEED") ){
					if (args.length==0 || args[0].equalsIgnoreCase("ON")){
						// no value or ON
						p.addPotionEffect( new PotionEffect(PotionEffectType.SPEED, 999999, 25));
					}else	if (args[0].equalsIgnoreCase("OFF")){

						p.removePotionEffect( PotionEffectType.SPEED);
					}else
						return true;
				}else{
					//no access to this area
					p.sendMessage(sorryVIPOnlyMessage);
					return false;
				}
			}
			if (label.equalsIgnoreCase("VIP")){
				if (p.hasPermission("LOBBY.VIP.AREA") ){
						p.teleport(new Location(Bukkit.getWorld("world"),  -69, 131, 268), TeleportCause.PLUGIN);
						return true;
				}else{
					//no access to this are
					p.sendMessage(sorryVIPOnlyMessage);
					return false;
				}
			}
			if (label.equalsIgnoreCase("FW")){
				if (p.hasPermission("LOBBY.VIP.FIREWORK") ){
					    launchFirework (p, 10);
						return true;
				}else{
					//no access to this are
					p.sendMessage(sorryVIPOnlyMessage);
					return false;
				}
			}
			
		}

		return false;	
	}
	
	
	
	
	public void launchFirework(Player p, int speed) {
	    Firework fw = (Firework) p.getWorld().spawn(p.getEyeLocation(), Firework.class);
	    FireworkMeta meta = fw.getFireworkMeta();
	    meta.addEffect(    FireworkEffect.builder().withTrail().withFlicker().withColor(Color.BLUE).withFade(Color.RED).build() );
	    fw.setFireworkMeta(meta);
	    //use meta to customize the firework or add parameters to the method
	    //fw.setVelocity(p.getLocation().getDirection().multiply(speed));
	    //speed is how fast the firework flies
	}

	
	
	
	@EventHandler
	public void weatherChange (WeatherChangeEvent e){
		
			// if it is about to start raining cancel the event 
			e.setCancelled(e.toWeatherState());
		
	}
}
