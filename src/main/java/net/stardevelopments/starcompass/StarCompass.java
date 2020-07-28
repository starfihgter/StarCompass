package net.stardevelopments.starcompass;

        import org.bukkit.Bukkit;
        import org.bukkit.Location;
        import org.bukkit.Material;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandSender;
        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.player.PlayerInteractEvent;
        import org.bukkit.event.player.PlayerItemBreakEvent;
        import org.bukkit.event.player.PlayerPortalEvent;
        import org.bukkit.plugin.java.JavaPlugin;
        import org.bukkit.plugin.Plugin;

        import java.util.Objects;
        import java.util.Timer;
        import java.util.TimerTask;

public final class StarCompass extends JavaPlugin implements Listener {
    public static Plugin plugin;
    //public static Boolean isOtherDimension;
    static Player target;
    static Player gamer;
   // static Location overworldPortal;
    @Override
    public void onEnable() {
        System.out.println("Star's compass is now running!");
        Bukkit.getPluginManager().registerEvents(this, this);
        plugin = this;
        getCommand("hunt").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (args.length == 0) {
            sender.sendMessage("Enter a player name");
            return false;
        }
        target = Bukkit.getPlayerExact(args[0]);
        gamer = (Player) sender;
        gamer.sendMessage(target.getDisplayName() + " hears the howls and knows fear");
        target.sendMessage(gamer.getDisplayName() + "'s Hyperspace Rupture detected");
        return true;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (Objects.requireNonNull(event.getItem()).getType() == Material.getMaterial("COMPASS")){
            if (gamer == null || target == null) {
                player.sendMessage("No target to track!");
                return;
            }
            if (player.getDisplayName().equals(gamer.getDisplayName())){
                if (target.getWorld().getName().equals("world")) {
                    gamer.setCompassTarget(target.getLocation());
                    gamer.sendMessage("Tracking " + target.getDisplayName());
                } else{
                    gamer.sendMessage("Tracking " + target.getDisplayName() + "'s last known position!");
                }
            }
        }
    }

   // @EventHandler
    //public void onPortal(PlayerPortalEvent event, Player player, Location from){
     //   if (event.getPlayer().getDisplayName().equals(target.getDisplayName())){
      //          gamer.sendMessage("Portal transition detected");
       //         overworldPortal = event.getFrom();
        //}
   // }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
