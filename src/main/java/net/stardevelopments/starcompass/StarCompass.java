package net.stardevelopments.starcompass;

        import org.bukkit.Bukkit;
        import org.bukkit.Location;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandSender;
        import org.bukkit.entity.Player;
        import org.bukkit.event.Listener;
        import org.bukkit.event.player.PlayerPortalEvent;
        import org.bukkit.plugin.java.JavaPlugin;
        import org.bukkit.plugin.Plugin;

        import java.util.Timer;
        import java.util.TimerTask;

public final class StarCompass extends JavaPlugin implements Listener {
    public static Plugin plugin;
    public static Boolean isOtherDimension;
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
        Player target = Bukkit.getPlayerExact(args[0]);
        Player gamer = (Player) sender;
        Timer t = new Timer();
        isOtherDimension = false;
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                gamer.setCompassTarget(target.getLocation());
            }
        }, 0, 100);
        gamer.sendMessage(target.getDisplayName() + " hears the howls and knows fear");
        target.sendMessage(gamer.getDisplayName() + "'s Hyperspace Rupture detected");
        return true;
    }

    public void onPlayerPortal(PlayerPortalEvent event, Player player, Location from){
        if (isOtherDimension = false){
            isOtherDimension = true;
        }
        if (isOtherDimension = true){
            isOtherDimension = false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
