package net.stardevelopments.starcompass;

        import org.bukkit.Bukkit;
        import org.bukkit.Location;
        import org.bukkit.Material;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandSender;
        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.entity.PlayerDeathEvent;
        import org.bukkit.event.player.PlayerInteractEvent;
        import org.bukkit.event.player.PlayerItemBreakEvent;
        import org.bukkit.event.player.PlayerPortalEvent;
        import org.bukkit.event.player.PlayerRespawnEvent;
        import org.bukkit.inventory.ItemStack;
        import org.bukkit.inventory.meta.CompassMeta;
        import org.bukkit.inventory.meta.ItemMeta;
        import org.bukkit.plugin.java.JavaPlugin;
        import org.bukkit.plugin.Plugin;

        import javax.annotation.Nonnull;
        import java.util.Objects;
        import java.util.Timer;
        import java.util.TimerTask;

public final class StarCompass extends JavaPlugin implements Listener {
    public static Plugin plugin;
    //public static Boolean isOtherDimension;
    static String target;
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
        target = Bukkit.getPlayerExact(args[0]).getName();
        Player player = Bukkit.getPlayerExact(target);
        sender.sendMessage(target + " is now being tracked!");
        player.sendMessage(sender.getName() + " is tracking you!");

        ItemStack newCompass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = newCompass.getItemMeta();
        compassMeta.setDisplayName(target + " tracker");
        newCompass.setItemMeta(compassMeta);
        Player pSender = (Player) sender;
        pSender.getInventory().addItem(newCompass);
        return true;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack compass = event.getItem();
        try{
            compass.getType();
        }catch(NullPointerException e){
            return;
        }
            if (compass.getType() == Material.getMaterial("COMPASS")) {
                if (Objects.requireNonNull(event.getItem()).getType() == Material.getMaterial("COMPASS")) {
                    if (target == null) {
                        player.sendMessage("No target to track!");
                        return;
                    }
                    Player targetPlayer = Bukkit.getPlayerExact(target);
                    if (Bukkit.getPlayerExact(target).getWorld().getName().equals(player.getWorld().getName())) {
                        //player.setCompassTarget(targetPlayer.getLocation());
                        CompassMeta meta = (CompassMeta) compass.getItemMeta();
                        meta.setLodestoneTracked(false);
                        meta.setLodestone(targetPlayer.getLocation());
                        compass.setItemMeta(meta);
                        player.sendMessage("Tracking " + target);
                    } else {
                        player.sendMessage("Tracking " + target + "'s last known position!");
                    }
                }
            }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        if (target != null){
            Player player = e.getPlayer();
            ItemStack newCompass = new ItemStack(Material.COMPASS);
            ItemMeta compassMeta = newCompass.getItemMeta();
            compassMeta.setDisplayName(target + " tracker");
            newCompass.setItemMeta(compassMeta);
            player.getInventory().addItem(newCompass);
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
