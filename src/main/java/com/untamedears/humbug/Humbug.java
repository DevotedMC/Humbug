package com.untamedears.humbug;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R3.EntityEnderPearl;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemEnderPearl;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.RegistryID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.ContainerBlock;
import org.bukkit.block.Sign;
import org.bukkit.block.Hopper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.untamedears.humbug.annotations.BahHumbug;
import com.untamedears.humbug.annotations.BahHumbugs;
import com.untamedears.humbug.annotations.ConfigOption;
import com.untamedears.humbug.annotations.OptType;

public class Humbug extends JavaPlugin implements Listener {
  public static void severe(String message) {
    log_.severe("[Humbug] " + message);
  }

  public static void warning(String message) {
    log_.warning("[Humbug] " + message);
  }

  public static void info(String message) {
    log_.info("[Humbug] " + message);
  }

  public static void debug(String message) {
    if (config_.getDebug()) {
      log_.info("[Humbug] " + message);
    }
  }

  public static Humbug getPlugin() {
    return global_instance_;
  }

  private static final Logger log_ = Logger.getLogger("Humbug");
  private static Humbug global_instance_ = null;
  private static Config config_ = null;
  private static int max_golden_apple_stack_ = 1;

  static {
    max_golden_apple_stack_ = Material.GOLDEN_APPLE.getMaxStackSize();
    if (max_golden_apple_stack_ > 64) {
      max_golden_apple_stack_ = 64;
    }
  }

  private Random prng_ = new Random();
  private CombatInterface combatTag_;

  public Humbug() {}

  // ================================================
  // Reduce registered PlayerInteractEvent count. onPlayerInteractAll handles
  //  cancelled events.

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onPlayerInteract(PlayerInteractEvent event) {
    onAnvilOrEnderChestUse(event);
    if (!event.isCancelled()) {
      onCauldronInteract(event);
    }
    if (!event.isCancelled()) {
      onRecordInJukebox(event);
    }
    if (!event.isCancelled()) {
      onEnchantingTableUse(event);
    }
    if (!event.isCancelled()) {
        onChangingSpawners(event);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST) // ignoreCancelled=false
  public void onPlayerInteractAll(PlayerInteractEvent event) {
    onPlayerEatGoldenApple(event);
    throttlePearlTeleport(event);
    onEquippingBanners(event);
  }

  // ================================================
  // Stops people from dying sheep

  @BahHumbug(opt="allow_dye_sheep", def="true")
  @EventHandler
  public void onDyeWool(SheepDyeWoolEvent event) {
    if (!config_.get("allow_dye_sheep").getBool()) {
      event.setCancelled(true);
    }
  }

  // ================================================
  // Configurable bow buff

  @EventHandler
  public void onEntityShootBowEventAlreadyIntializedSoIMadeThisUniqueName(EntityShootBowEvent event) {
    Integer power = event.getBow().getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
    MetadataValue metadata = new FixedMetadataValue(this, power);
    event.getProjectile().setMetadata("power", metadata);
  }

  @BahHumbug(opt="bow_buff", type=OptType.Double, def="1.000000")
  @EventHandler
  public void onArrowHitEntity(EntityDamageByEntityEvent event) {
    Double multiplier = config_.get("bow_buff").getDouble();
    if(multiplier <= 1.000001 && multiplier >= 0.999999) {
      return;
    }

    if (event.getEntity() instanceof LivingEntity) {
      Entity damager = event.getDamager();
      if (damager instanceof Arrow) {
        Arrow arrow = (Arrow) event.getDamager();
        Double damage = event.getDamage() * config_.get("bow_buff").getDouble();
        Integer power = 0;
        if(arrow.hasMetadata("power")) {
          power = arrow.getMetadata("power").get(0).asInt();
        }
        damage *= Math.pow(1.25, power - 5); // f(x) = 1.25^(x - 5)
        event.setDamage(damage);
      }
    }
  }

  // ================================================
  // Fixes Teleporting through walls and doors
  // ** and **
  // Ender Pearl Teleportation disabling
  // ** and **
  // Ender pearl cooldown timer

  private class PearlTeleportInfo {
    public long last_teleport;
    public long last_notification;
  }
  private Map<String, PearlTeleportInfo> pearl_teleport_info_
      = new TreeMap<String, PearlTeleportInfo>();
  private final static int PEARL_THROTTLE_WINDOW = 10000;  // 10 sec
  private final static int PEARL_NOTIFICATION_WINDOW = 1000;  // 1 sec

  // EventHandler registered in onPlayerInteractAll
  @BahHumbug(opt="ender_pearl_teleportation_throttled", def="true")
  public void throttlePearlTeleport(PlayerInteractEvent event) {
    if (!config_.get("ender_pearl_teleportation_throttled").getBool()) {
      return;
    }
    if (event.getItem() == null || !event.getItem().getType().equals(Material.ENDER_PEARL)) {
      return;
    }
    final Action action = event.getAction();
    if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    final Block clickedBlock = event.getClickedBlock();
    BlockState clickedState = null;
    Material clickedMaterial = null;
    if (clickedBlock != null) {
      clickedState = clickedBlock.getState();
      clickedMaterial = clickedState.getType();
    }
    if (clickedState != null && (
          clickedState instanceof InventoryHolder
          || clickedMaterial.equals(Material.ANVIL)
          || clickedMaterial.equals(Material.ENCHANTMENT_TABLE)
          || clickedMaterial.equals(Material.ENDER_CHEST)
          || clickedMaterial.equals(Material.WORKBENCH))) {
      // Prevent Combat Tag/Pearl cooldown on inventory access
      return;
    }
    final long current_time = System.currentTimeMillis();
    final Player player = event.getPlayer();
    final String player_name = player.getName();
    PearlTeleportInfo teleport_info = pearl_teleport_info_.get(player_name);
    long time_diff = 0;
    if (teleport_info == null) {
      // New pearl thrown outside of throttle window
      teleport_info = new PearlTeleportInfo();
      teleport_info.last_teleport = current_time;
      teleport_info.last_notification =
          current_time - (PEARL_NOTIFICATION_WINDOW + 100);  // Force notify
      combatTag_.tagPlayer(player);
    } else {
      time_diff = current_time - teleport_info.last_teleport;
      if (PEARL_THROTTLE_WINDOW > time_diff) {
        // Pearl throw throttled
        event.setCancelled(true);
      } else {
        // New pearl thrown outside of throttle window
        combatTag_.tagPlayer(player);
        teleport_info.last_teleport = current_time;
        teleport_info.last_notification =
            current_time - (PEARL_NOTIFICATION_WINDOW + 100);  // Force notify
        time_diff = 0;
      }
    }
    final long notify_diff = current_time - teleport_info.last_notification;
    if (notify_diff > PEARL_NOTIFICATION_WINDOW) {
      teleport_info.last_notification = current_time;
      Integer tagCooldown = combatTag_.remainingSeconds(player);
      if (tagCooldown != null) {
        player.sendMessage(String.format(
            "Pearl in %d seconds. Combat Tag in %d seconds.",
            (PEARL_THROTTLE_WINDOW - time_diff + 500) / 1000,
            tagCooldown));
      } else {
        player.sendMessage(String.format(
            "Pearl Teleport Cooldown: %d seconds",
            (PEARL_THROTTLE_WINDOW - time_diff + 500) / 1000));
      }
    }
    pearl_teleport_info_.put(player_name, teleport_info);
    return;
  }

  @BahHumbugs({
    @BahHumbug(opt="ender_pearl_teleportation", def="true"),
    @BahHumbug(opt="fix_teleport_glitch", def="true")
  })
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onTeleport(PlayerTeleportEvent event) {
    TeleportCause cause = event.getCause();
    if (cause != TeleportCause.ENDER_PEARL) {
      return;
    } else if (!config_.get("ender_pearl_teleportation").getBool()) {
      event.setCancelled(true);
      return;
    }
    if (!config_.get("fix_teleport_glitch").getBool()) {
      return;
    }
    Location to = event.getTo();
    World world = to.getWorld();

    // From and To are feet positions.  Check and make sure we can teleport to a location with air
    // above the To location.
    Block toBlock = world.getBlockAt(to);
    Block aboveBlock = world.getBlockAt(to.getBlockX(), to.getBlockY()+1, to.getBlockZ());
    Block belowBlock = world.getBlockAt(to.getBlockX(), to.getBlockY()-1, to.getBlockZ());
    boolean lowerBlockBypass = false;
    double height = 0.0;
    switch( toBlock.getType() ) {
    case CHEST: // Probably never will get hit directly
    case ENDER_CHEST: // Probably never will get hit directly
      height = 0.875;
      break;
    case STEP:
      lowerBlockBypass = true;
      height = 0.5;
      break;
    case WATER_LILY:
      height = 0.016;
      break;
    case ENCHANTMENT_TABLE:
      lowerBlockBypass = true;
      height = 0.75;
      break;
    case BED:
    case BED_BLOCK:
      // This one is tricky, since even with a height offset of 2.5, it still glitches.
      //lowerBlockBypass = true;
      //height = 0.563;
      // Disabling teleporting on top of beds for now by leaving lowerBlockBypass false.
      break;
    case FLOWER_POT:
    case FLOWER_POT_ITEM:
      height = 0.375;
      break;
    case SKULL: // Probably never will get hit directly
      height = 0.5;
      break;
    default:
      break;
    }
    // Check if the below block is difficult
    // This is added because if you face downward directly on a gate, it will
    // teleport your feet INTO the gate, thus bypassing the gate until you leave that block.
    switch( belowBlock.getType() ) {
    case FENCE:
    case FENCE_GATE:
    case NETHER_FENCE:
    case COBBLE_WALL:
      height = 0.5;
      break;
    default:
      break;
    }

    boolean upperBlockBypass = false;
    if( height >= 0.5 ) {
      Block aboveHeadBlock = world.getBlockAt(aboveBlock.getX(), aboveBlock.getY()+1, aboveBlock.getZ());
      if( false == aboveHeadBlock.getType().isSolid() ) {
        height = 0.5;
      } else {
        upperBlockBypass = true; // Cancel this event.  What's happening is the user is going to get stuck due to the height.
      }
    }

    // Normalize teleport to the center of the block.  Feet ON the ground, plz.
    // Leave Yaw and Pitch alone
    to.setX(Math.floor(to.getX()) + 0.5000);
    to.setY(Math.floor(to.getY()) + height);
    to.setZ(Math.floor(to.getZ()) + 0.5000);

    if(aboveBlock.getType().isSolid() ||
       (toBlock.getType().isSolid() && !lowerBlockBypass) ||
       upperBlockBypass ) {
      // One last check because I care about Top Nether.  (someone build me a shrine up there)
      boolean bypass = false;
      if ((world.getEnvironment() == Environment.NETHER) &&
          (to.getBlockY() > 124) && (to.getBlockY() < 129)) {
        bypass = true;
      }
      if (!bypass) {
        event.setCancelled(true);
      }
    }
  }

  // ================================================
  // Villager Trading

  @BahHumbug(opt="villager_trades")
  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if (config_.get("villager_trades").getBool()) {
      return;
    }
    Entity npc = event.getRightClicked();
    if (npc == null) {
        return;
    }
    if (npc.getType() == EntityType.VILLAGER) {
      event.setCancelled(true);
    }
  }

  // ================================================
  // Anvil and Ender Chest usage

  // EventHandler registered in onPlayerInteract
  @BahHumbugs({
    @BahHumbug(opt="anvil"),
    @BahHumbug(opt="ender_chest")
  })
  public void onAnvilOrEnderChestUse(PlayerInteractEvent event) {
    if (config_.get("anvil").getBool() && config_.get("ender_chest").getBool()) {
      return;
    }
    Action action = event.getAction();
    Material material = event.getClickedBlock().getType();
    boolean anvil = !config_.get("anvil").getBool() &&
                    action == Action.RIGHT_CLICK_BLOCK &&
                    material.equals(Material.ANVIL);
    boolean ender_chest = !config_.get("ender_chest").getBool() &&
                          action == Action.RIGHT_CLICK_BLOCK &&
                          material.equals(Material.ENDER_CHEST);
    if (anvil || ender_chest) {
      event.setCancelled(true);
    }
  }

  @BahHumbug(opt="enchanting_table", def = "false")
  public void onEnchantingTableUse(PlayerInteractEvent event) {
    if(!config_.get("enchanting_table").getBool()) {
      return;
    }
    Action action = event.getAction();
    Material material = event.getClickedBlock().getType();
    boolean enchanting_table = action == Action.RIGHT_CLICK_BLOCK &&
                   material.equals(Material.ENCHANTMENT_TABLE);
    if(enchanting_table) {
      event.setCancelled(true);
    }
  }
  
  @BahHumbug(opt="ender_chests_placeable", def="true")
  @EventHandler(ignoreCancelled=true)
  public void onEnderChestPlace(BlockPlaceEvent e) {
    Material material = e.getBlock().getType();
    if (!config_.get("ender_chests_placeable").getBool() && material == Material.ENDER_CHEST) {
      e.setCancelled(true);
    }
  }

  public void EmptyEnderChest(HumanEntity human) {
    if (config_.get("ender_backpacks").getBool()) {
      dropInventory(human.getLocation(), human.getEnderChest());
    }
  }

  public void dropInventory(Location loc, Inventory inv) {
    final World world = loc.getWorld();
    final int end = inv.getSize();
    for (int i = 0; i < end; ++i) {
      try {
        final ItemStack item = inv.getItem(i);
        if (item != null) {
          world.dropItemNaturally(loc, item);
          inv.clear(i);
        }
      } catch (Exception ex) {}
    }
  }

  // ================================================
  // Unlimited Cauldron water

  // EventHandler registered in onPlayerInteract
  @BahHumbug(opt="unlimitedcauldron")
  public void onCauldronInteract(PlayerInteractEvent e) {
    if (!config_.get("unlimitedcauldron").getBool()) {
      return;
    }

    // block water going down on cauldrons
    if(e.getClickedBlock().getType() == Material.CAULDRON && e.getMaterial() == Material.GLASS_BOTTLE && e.getAction() == Action.RIGHT_CLICK_BLOCK)
    {
      Block block = e.getClickedBlock();
      if(block.getData() > 0)
      {
        block.setData((byte)(block.getData()+1));
      }
    }
  }

  // ================================================
  // Quartz from Gravel

  @BahHumbug(opt="quartz_gravel_percentage", type=OptType.Int)
  @EventHandler(ignoreCancelled=true, priority = EventPriority.HIGHEST)
  public void onGravelBreak(BlockBreakEvent e) {
    if(e.getBlock().getType() != Material.GRAVEL
        || config_.get("quartz_gravel_percentage").getInt() <= 0) {
      return;
    }

    if(prng_.nextInt(100) < config_.get("quartz_gravel_percentage").getInt())
    {
      e.setCancelled(true);
      e.getBlock().setType(Material.AIR);
      e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 1));
    }
  }

  // ================================================
  // Portals

  @BahHumbug(opt="portalcreate", def="true")
  @EventHandler(ignoreCancelled=true)
  public void onPortalCreate(PortalCreateEvent e) {
    if (!config_.get("portalcreate").getBool()) {
      e.setCancelled(true);
    }
  }

  @EventHandler(ignoreCancelled=true)
  public void onEntityPortalCreate(EntityCreatePortalEvent e) {
    if (!config_.get("portalcreate").getBool()) {
      e.setCancelled(true);
    }
  }

  // ================================================
  // EnderDragon

  @BahHumbug(opt="enderdragon", def="true")
  @EventHandler(ignoreCancelled=true)
  public void onDragonSpawn(CreatureSpawnEvent e) {
    if (e.getEntityType() == EntityType.ENDER_DRAGON
        && !config_.get("enderdragon").getBool()) {
      e.setCancelled(true);
    }
  }

  // ================================================
  // Join/Quit/Kick messages

  @BahHumbug(opt="joinquitkick", def="true")
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onJoin(PlayerJoinEvent e) {
    if (!config_.get("joinquitkick").getBool()) {
      e.setJoinMessage(null);
    }
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onQuit(PlayerQuitEvent e) {
    EmptyEnderChest(e.getPlayer());
    if (!config_.get("joinquitkick").getBool()) {
      e.setQuitMessage(null);
    }
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onKick(PlayerKickEvent e) {
    EmptyEnderChest(e.getPlayer());
    if (!config_.get("joinquitkick").getBool()) {
      e.setLeaveMessage(null);
    }
  }

  // ================================================
  // Death Messages
  @BahHumbugs({
    @BahHumbug(opt="deathannounce", def="true"),
    @BahHumbug(opt="deathlog"),
    @BahHumbug(opt="deathpersonal"),
    @BahHumbug(opt="deathred"),
    @BahHumbug(opt="ender_backpacks")
  })
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onDeath(PlayerDeathEvent e) {
    final boolean logMsg = config_.get("deathlog").getBool();
    final boolean sendPersonal = config_.get("deathpersonal").getBool();
    final Player player = e.getEntity();
    EmptyEnderChest(player);
    if (logMsg || sendPersonal) {
      Location location = player.getLocation();
      String msg = String.format(
          "%s ([%s] %d, %d, %d)", e.getDeathMessage(), location.getWorld().getName(),
          location.getBlockX(), location.getBlockY(), location.getBlockZ());
      if (logMsg) {
        info(msg);
      }
      if (sendPersonal) {
        e.getEntity().sendMessage(ChatColor.RED + msg);
      }
    }
    if (!config_.get("deathannounce").getBool()) {
      e.setDeathMessage(null);
    } else if (config_.get("deathred").getBool()) {
      e.setDeathMessage(ChatColor.RED + e.getDeathMessage());
    }
  }

  // ================================================
  // Endermen Griefing

  @BahHumbug(opt="endergrief", def="true")
  @EventHandler(ignoreCancelled=true)
  public void onEndermanGrief(EntityChangeBlockEvent e)
  {
    if (!config_.get("endergrief").getBool() && e.getEntity() instanceof Enderman) {
      e.setCancelled(true);
    }
  }

  // ================================================
  // Wither Insta-breaking and Explosions

  @BahHumbug(opt="wither_insta_break")
  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onEntityChangeBlock(EntityChangeBlockEvent event) {
    if (config_.get("wither_insta_break").getBool()) {
      return;
    }
    Entity npc = event.getEntity();
    if (npc == null) {
        return;
    }
    EntityType npc_type = npc.getType();
    if (npc_type.equals(EntityType.WITHER)) {
      event.setCancelled(true);
    }
  }

  @BahHumbug(opt="wither_explosions")
  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onEntityExplode(EntityExplodeEvent event) {
    if (config_.get("wither_explosions").getBool()) {
      return;
    }
    Entity npc = event.getEntity();
    if (npc == null) {
        return;
    }
    EntityType npc_type = npc.getType();
    if ((npc_type.equals(EntityType.WITHER) ||
         npc_type.equals(EntityType.WITHER_SKULL))) {
      event.blockList().clear();
    }
  }

  @BahHumbug(opt="wither", def="true")
  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onWitherSpawn(CreatureSpawnEvent event) {
    if (config_.get("wither").getBool()) {
      return;
    }
    if (!event.getEntityType().equals(EntityType.WITHER)) {
      return;
    }
    event.setCancelled(true);
  }

  // ================================================
  // Prevent specified items from dropping off mobs

  public void removeItemDrops(EntityDeathEvent event) {
    if (!config_.doRemoveItemDrops()) {
      return;
    }
    if (event.getEntity() instanceof Player) {
      return;
    }
    Set<Integer> remove_ids = config_.getRemoveItemDrops();
    List<ItemStack> drops = event.getDrops();
    ItemStack item;
    int i = drops.size() - 1;
    while (i >= 0) {
      item = drops.get(i);
      if (remove_ids.contains(item.getTypeId())) {
        drops.remove(i);
      }
      --i;
    }
  }

  // ================================================
  // Spawn more Wither Skeletons and Ghasts

  @BahHumbugs ({
    @BahHumbug(opt="extra_ghast_spawn_rate", type=OptType.Int),
    @BahHumbug(opt="extra_wither_skele_spawn_rate", type=OptType.Int),
    @BahHumbug(opt="portal_extra_ghast_spawn_rate", type=OptType.Int),
    @BahHumbug(opt="portal_extra_wither_skele_spawn_rate", type=OptType.Int),
    @BahHumbug(opt="portal_pig_spawn_multiplier", type=OptType.Int)
  })
  @EventHandler(priority = EventPriority.LOW, ignoreCancelled=true)
  public void spawnMoreHellMonsters(CreatureSpawnEvent e) {
    final Location loc = e.getLocation();
    final World world = loc.getWorld();
    boolean portalSpawn = false;
    final int blockType = world.getBlockTypeIdAt(loc);
    if (blockType == 90 || blockType == 49) {
      // >= because we are preventing instead of spawning
      if(prng_.nextInt(1000000) >= config_.get("portal_pig_spawn_multiplier").getInt()) {
        e.setCancelled(true);
        return;
      }
      portalSpawn = true;
    }
    if (config_.get("extra_wither_skele_spawn_rate").getInt() <= 0
        && config_.get("extra_ghast_spawn_rate").getInt() <= 0) {
      return;
    }
    if (e.getEntityType() == EntityType.PIG_ZOMBIE) {
      int adjustedwither;
      int adjustedghast;
      if (portalSpawn) {
        adjustedwither = config_.get("portal_extra_wither_skele_spawn_rate").getInt();
        adjustedghast = config_.get("portal_extra_ghast_spawn_rate").getInt();
      } else {
        adjustedwither = config_.get("extra_wither_skele_spawn_rate").getInt();
        adjustedghast = config_.get("extra_ghast_spawn_rate").getInt();
      }
      if(prng_.nextInt(1000000) < adjustedwither) {
        e.setCancelled(true);
        world.spawnEntity(loc, EntityType.SKELETON);
      } else if(prng_.nextInt(1000000) < adjustedghast) {
        e.setCancelled(true);
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        List<Integer> heights = new ArrayList<Integer>(16);
        int lastBlockHeight = 2;
        int emptyCount = 0;
        int maxHeight = world.getMaxHeight();
        for (int y = 2; y < maxHeight; ++y) {
          Block block = world.getBlockAt(x, y, z);
          if (block.isEmpty()) {
            ++emptyCount;
            if (emptyCount == 11) {
              heights.add(lastBlockHeight + 2);
            }
          } else {
            lastBlockHeight = y;
            emptyCount = 0;
          }
        }
        if (heights.size() <= 0) {
          return;
        }
        loc.setY(heights.get(prng_.nextInt(heights.size())));
        world.spawnEntity(loc, EntityType.GHAST);
      }
    } else if (e.getEntityType() == EntityType.SKELETON
        && e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
      Entity entity = e.getEntity();
      if (entity instanceof Skeleton) {
        Skeleton skele = (Skeleton)entity;
        skele.setSkeletonType(SkeletonType.WITHER);
        EntityEquipment entity_equip = skele.getEquipment();
        entity_equip.setItemInHand(new ItemStack(Material.STONE_SWORD));
        entity_equip.setItemInHandDropChance(0.0F);
      }
    }
  }

  // ================================================
  // Wither Skull drop rate

  public static final int skull_id_ = Material.SKULL_ITEM.getId();
  public static final byte wither_skull_data_ = 1;

  @BahHumbug(opt="wither_skull_drop_rate", type=OptType.Int)
  public void adjustWitherSkulls(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof Skeleton)) {
      return;
    }
    int rate = config_.get("wither_skull_drop_rate").getInt();
    if (rate < 0 || rate > 1000000) {
      return;
    }
    Skeleton skele = (Skeleton)entity;
    if (skele.getSkeletonType() != SkeletonType.WITHER) {
      return;
    }
    List<ItemStack> drops = event.getDrops();
    ItemStack item;
    int i = drops.size() - 1;
    while (i >= 0) {
      item = drops.get(i);
      if (item.getTypeId() == skull_id_
          && item.getData().getData() == wither_skull_data_) {
        drops.remove(i);
      }
      --i;
    }
    if (rate - prng_.nextInt(1000000) <= 0) {
      return;
    }
    item = new ItemStack(Material.SKULL_ITEM);
    item.setAmount(1);
    item.setDurability((short)wither_skull_data_);
    drops.add(item);
  }

  // ================================================
  // Fix a few issues with pearls, specifically pearls in unloaded chunks and slime blocks.
  
  @BahHumbug(opt="remove_pearls_chunks", type=OptType.Bool, def = "true")
  @EventHandler()
  public void onChunkUnloadEvent(ChunkUnloadEvent event){
	  Entity[] entities = event.getChunk().getEntities();
	  for (Entity ent: entities)
		  if (ent.getType() == EntityType.ENDER_PEARL)
			  ent.remove();
  }
  
  private void registerTimerForPearlCheck(){
	  Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

		@Override
		public void run() {
			for (Entity ent: pearlTime.keySet()){
				if (pearlTime.get(ent).booleanValue()){
					ent.remove();
				}
				else 
					pearlTime.put(ent, true);
			}
		}
		  
	  }, 100, 200);
  }
  
  private Map<Entity, Boolean> pearlTime = new HashMap<Entity, Boolean>();
  public void onPearlLastingTooLong(EntityInteractEvent event){
	  if (event.getEntityType() != EntityType.ENDER_PEARL)
		  return;
	  Entity ent = event.getEntity();
	  pearlTime.put(ent, false);
  }
  // ================================================
  // Generic mob drop rate adjustment
  
  @BahHumbug(opt="disable_xp_orbs", type=OptType.Bool, def = "true")
  public void adjustMobItemDrops(EntityDeathEvent event){
    Entity mob = event.getEntity();
    if (mob instanceof Player){
      return;
    }
    
    // Try specific multiplier, if that doesn't exist use generic
    EntityType mob_type = mob.getType();
    int multiplier = config_.getLootMultiplier(mob_type.toString());
    if (multiplier < 0) {
      multiplier = config_.getLootMultiplier("generic");
    }
    //set entity death xp to zero so they don't drop orbs
    if(config_.get("disable_xp_orbs").getBool()){
      event.setDroppedExp(0);
    }
    //if a dropped item was in the mob's inventory, drop only one, otherwise drop the amount * the multiplier
    LivingEntity liveMob = (LivingEntity) mob;
    EntityEquipment mobEquipment = liveMob.getEquipment();
    ItemStack[] eeItem = mobEquipment.getArmorContents();
    for (ItemStack item : event.getDrops()) {
      boolean armor = false;
      boolean hand = false;
      for(ItemStack i : eeItem){
        if(i.isSimilar(item)){
          armor = true;
          item.setAmount(1);
        }
      }
      if(item.isSimilar(mobEquipment.getItemInHand())){
        hand = true;
        item.setAmount(1);
      }
      if(!hand && !armor){
        int amount = item.getAmount() * multiplier;
          item.setAmount(amount);
      }
    }
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onEntityDeathEvent(EntityDeathEvent event) {
    removeItemDrops(event);
    adjustWitherSkulls(event);
    adjustMobItemDrops(event);
  }

  // ================================================
  // Enchanted Golden Apple

  public boolean isEnchantedGoldenApple(ItemStack item) {
    // Golden Apples are GOLDEN_APPLE with 0 durability
    // Enchanted Golden Apples are GOLDEN_APPLE with 1 durability
    if (item == null) {
      return false;
    }
    if (item.getDurability() != 1) {
      return false;
    }
    Material material = item.getType();
    return material.equals(Material.GOLDEN_APPLE);
  }

  public boolean isCrackedStoneBrick(ItemStack item) {
	// Cracked stone bricks are stone bricks with 2 durability
	if (item == null) {
		return false;
	}
	if (item.getDurability() != 2) {
		return false;
	}
	Material material = item.getType();
	return material.equals(Material.SMOOTH_BRICK);
  }

  public void replaceEnchantedGoldenApple(
      String player_name, ItemStack item, int inventory_max_stack_size) {
    if (!isEnchantedGoldenApple(item)) {
      return;
    }
    int stack_size = max_golden_apple_stack_;
    if (inventory_max_stack_size < max_golden_apple_stack_) {
      stack_size = inventory_max_stack_size;
    }
    info(String.format(
          "Replaced %d Enchanted with %d Normal Golden Apples for %s",
          item.getAmount(), stack_size, player_name));
    item.setDurability((short)0);
    item.setAmount(stack_size);
  }
  
  @BahHumbug(opt="ench_gold_app_craftable", def = "false")
  public void removeRecipies() {
    if (config_.get("ench_gold_app_craftable").getBool()&&config_.get("moss_stone_craftable").getBool()&&config_.get("cracked_stone_craftable").getBool()) {
      return;
    }
    Iterator<Recipe> it = getServer().recipeIterator();
    while (it.hasNext()) {
      Recipe recipe = it.next();
      ItemStack resulting_item = recipe.getResult();
      if (!config_.get("ench_gold_app_craftable").getBool() &&
          isEnchantedGoldenApple(resulting_item)) {
        it.remove();
        info("Enchanted Golden Apple Recipe disabled");
      }
      if (recipe instanceof FurnaceRecipe) {
          FurnaceRecipe fre = (FurnaceRecipe) recipe;
          if (isCrackedStone(fre.getResult())) {
                  it.remove();
                  info("Cracked Stone Furnace Recipe disabled");
          }
		}
    }
  }
  
  public boolean isCrackedStone(ItemStack item) {
	  if (item == null)
		  return false;
	  if (item.getDurability() != 2){
		  return false;
	  }
	  
	  Material material = item.getType();
	  return material.equals(Material.SMOOTH_BRICK);
  }

  // EventHandler registered in onPlayerInteractAll
  @BahHumbug(opt="ench_gold_app_edible")
  public void onPlayerEatGoldenApple(PlayerInteractEvent event) {
    // The event when eating is cancelled before even LOWEST fires when the
    //  player clicks on AIR.
    if (config_.get("ench_gold_app_edible").getBool()) {
      return;
    }
    Player player = event.getPlayer();
    Inventory inventory = player.getInventory();
    ItemStack item = event.getItem();
    replaceEnchantedGoldenApple(
        player.getName(), item, inventory.getMaxStackSize());
  }

  // ================================================
  // Fix entities going through portals
  
  // This needs to be removed when updated to citadel 3.0
  @BahHumbug(opt="disable_entities_portal", type = OptType.Bool, def = "true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void entityPortalEvent(EntityPortalEvent event){
    event.setCancelled(config_.get("disable_entities_portal").getBool());
  }
  //=================================================
  // Enchanted Book

  public boolean isNormalBook(ItemStack item) {
    if (item == null) {
      return false;
    }
    Material material = item.getType();
    return material.equals(Material.BOOK);
  }

  @BahHumbug(opt="ench_book_craftable")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
    if (config_.get("ench_book_craftable").getBool()) {
        return;
    }
    ItemStack item = event.getItem();
    if (isNormalBook(item)) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void onEnchantItemEvent(EnchantItemEvent event) {
    if (config_.get("ench_book_craftable").getBool()) {
        return;
    }
    ItemStack item = event.getItem();
    if (isNormalBook(item)) {
      event.setCancelled(true);
      Player player = event.getEnchanter();
      warning(
          "Prevented book enchant. This should not trigger. Watch player " +
          player.getName());
    }
  }

  // ================================================
  // Stop Cobble generation from lava+water

  private static final BlockFace[] faces_ = new BlockFace[] {
      BlockFace.NORTH,
      BlockFace.SOUTH,
      BlockFace.EAST,
      BlockFace.WEST,
      BlockFace.UP,
      BlockFace.DOWN
    };


  private BlockFace WaterAdjacentLava(Block lava_block) {
    for (BlockFace face : faces_) {
      Block block = lava_block.getRelative(face);
      Material material = block.getType();
      if (material.equals(Material.WATER) ||
          material.equals(Material.STATIONARY_WATER)) {
        return face;
      }
    }
    return BlockFace.SELF;
  }

  public void ConvertLava(final Block block) {
    int data = (int)block.getData();
    if (data == 0) {
      return;
    }
    Material material = block.getType();
    if (!material.equals(Material.LAVA) &&
        !material.equals(Material.STATIONARY_LAVA)) {
      return;
    }
    if (isLavaSourceNear(block, 3)) {
      return;
    }
    BlockFace face = WaterAdjacentLava(block);
    if (face == BlockFace.SELF) {
      return;
    }
    Bukkit.getScheduler().runTask(this, new Runnable() {
      @Override
      public void run() {
        block.setType(Material.AIR);
      }
    });
  }

  public boolean isLavaSourceNear(Block block, int ttl) {
    int data = (int)block.getData();
    if (data == 0) {
      Material material = block.getType();
      if (material.equals(Material.LAVA) ||
          material.equals(Material.STATIONARY_LAVA)) {
        return true;
      }
    }
    if (ttl <= 0) {
      return false;
    }
    for (BlockFace face : faces_) {
      Block child = block.getRelative(face);
      if (isLavaSourceNear(child, ttl - 1)) {
        return true;
      }
    }
    return false;
  }

  public void LavaAreaCheck(Block block, int ttl) {
    ConvertLava(block);
    if (ttl <= 0) {
      return;
    }
    for (BlockFace face : faces_) {
      Block child = block.getRelative(face);
      LavaAreaCheck(child, ttl - 1);
    }
  }

  @BahHumbugs ({
    @BahHumbug(opt="cobble_from_lava"),
    @BahHumbug(opt="cobble_from_lava_scan_radius", type=OptType.Int, def="0")
  })
  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
    if (config_.get("cobble_from_lava").getBool()) {
      return;
    }
    Block block = event.getBlock();
    Material material = block.getType();
    if (!material.equals(Material.LAVA) &&
        !material.equals(Material.STATIONARY_LAVA)) {
      return;
    }
    LavaAreaCheck(block, config_.get("cobble_from_lava_scan_radius").getInt());
  }

  // ================================================
  // Counteract 1.4.6 protection enchant nerf

  @BahHumbug(opt="scale_protection_enchant", def="true")
  @EventHandler(priority = EventPriority.LOWEST) // ignoreCancelled=false
  public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
    if (!config_.get("scale_protection_enchant").getBool()) {
        return;
    }
    double damage = event.getDamage();
    if (damage <= 0.0000001D) {
      return;
    }
    DamageCause cause = event.getCause();
    if (!cause.equals(DamageCause.ENTITY_ATTACK) &&
            !cause.equals(DamageCause.PROJECTILE)) {
        return;
    }
    Entity entity = event.getEntity();
    if (!(entity instanceof Player)) {
      return;
    }
    Player defender = (Player)entity;
    PlayerInventory inventory = defender.getInventory();
    int enchant_level = 0;
    for (ItemStack armor : inventory.getArmorContents()) {
      enchant_level += armor.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
    }
    int damage_adjustment = 0;
    if (enchant_level >= 3 && enchant_level <= 6) {
      // 0 to 2
      damage_adjustment = prng_.nextInt(3);
    } else if (enchant_level >= 7 && enchant_level <= 10) {
      // 0 to 3
      damage_adjustment = prng_.nextInt(4);
    } else if (enchant_level >= 11 && enchant_level <= 14) {
      // 1 to 4
      damage_adjustment = prng_.nextInt(4) + 1;
    } else if (enchant_level >= 15) {
      // 2 to 4
      damage_adjustment = prng_.nextInt(3) + 2;
    }
    damage = Math.max(damage - (double)damage_adjustment, 0.0D);
    event.setDamage(damage);
  }

  @BahHumbug(opt="player_max_health", type=OptType.Int, def="20")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void onPlayerJoinEvent(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.setMaxHealth((double)config_.get("player_max_health").getInt());
  }

  // ================================================
  // Fix dupe bug with chests and other containers
  
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void blockExplodeEvent(EntityExplodeEvent event) {
    List<HumanEntity> humans = new ArrayList<HumanEntity>();
    for (Block block: event.blockList()) {
      if (block.getState() instanceof InventoryHolder) {
        InventoryHolder holder = (InventoryHolder) block.getState();
        for (HumanEntity ent: holder.getInventory().getViewers()) {
          humans.add(ent);
        }
      }
    }
    for (HumanEntity human: humans) {
      human.closeInventory();
    }
  }
  
  // ==================================================
  // Prevent entity dup bug
  // From https://github.com/intangir/EventBlocker

  @BahHumbug(opt="fix_rail_dup_bug", def="true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void onPistonPushRail(BlockPistonExtendEvent e) {
    if (!config_.get("fix_rail_dup_bug").getBool()) {
      return;
    }
    for (Block b : e.getBlocks()) {
      Material t = b.getType();
      if (t == Material.RAILS ||
          t == Material.POWERED_RAIL ||
          t == Material.DETECTOR_RAIL) {
        e.setCancelled(true);
        return;
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void onRailPlace(BlockPlaceEvent e) {
    if (!config_.get("fix_rail_dup_bug").getBool()) {
      return;
    }
    Block b = e.getBlock();
    Material t = b.getType();
    if (t == Material.RAILS ||
        t == Material.POWERED_RAIL ||
        t == Material.DETECTOR_RAIL) {
      for (BlockFace face : faces_) {
        t = b.getRelative(face).getType();
        if (t == Material.PISTON_STICKY_BASE ||
            t == Material.PISTON_EXTENSION ||
            t == Material.PISTON_MOVING_PIECE ||
            t == Material.PISTON_BASE) {
          e.setCancelled(true);
          return;
        }
      }
    }
  }

  //=================================================
  // Combat Tag players on server join

  @BahHumbug(opt="tag_on_join", def="true")
  @EventHandler
  public void tagOnJoin(PlayerJoinEvent event){
    if(!config_.get("tag_on_join").getBool()) {
      return;
    }
    // Delay two ticks to tag after secure login has been denied.
    // This opens a 1 tick window for a cheater to login and grab
    // server info, which should be detectable and bannable.
    final Player loginPlayer = event.getPlayer();
    Bukkit.getScheduler().runTaskLater(this, new Runnable() {
      @Override
      public void run() {
    	  if (loginPlayer == null)
    		  return;
    	  combatTag_.tagPlayer(loginPlayer.getName());
    	  loginPlayer.sendMessage("You have been Combat Tagged on Login");
      }
    }, 2L);
  }

  //================================================
  // Give introduction book to n00bs

  private Set<String> playersWithN00bBooks_ = new TreeSet<String>();

  @BahHumbug(opt="drop_newbie_book", def="true")
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerDeathBookDrop(PlayerDeathEvent e) {
    if (!config_.get("drop_newbie_book").getBool()) {
      return;
    }
    final String playerName = e.getEntity().getName();
    List<ItemStack> dropList = e.getDrops();
    for (int i = 0; i < dropList.size(); ++i) {
      final ItemStack item = dropList.get(i);
      if (item.getType().equals(Material.WRITTEN_BOOK)) {
        final BookMeta bookMeta = (BookMeta)item.getItemMeta();
        if (bookMeta.getTitle().equals(config_.getTitle()) && bookMeta.getAuthor().equals(config_.getAuthor())) {
          playersWithN00bBooks_.add(playerName);
          dropList.remove(i);
          return;
        }
      }
    }
    playersWithN00bBooks_.remove(playerName);
  }

  @EventHandler
  public void onGiveBookOnRespawn(PlayerRespawnEvent event) {
    if (!config_.get("drop_newbie_book").getBool()) {
      return;
    }
    final Player player = event.getPlayer();
    final String playerName = player.getName();
    if (!playersWithN00bBooks_.contains(playerName)) {
      return;
    }
    playersWithN00bBooks_.remove(playerName);
    giveN00bBook(player);
  }

  @EventHandler
  public void onGiveBookOnJoin(PlayerJoinEvent event) {
    if (!config_.get("drop_newbie_book").getBool()) {
      return;
    }
    final Player player = event.getPlayer();
    final String playerName = player.getName();
    if (player.hasPlayedBefore() && !playersWithN00bBooks_.contains(playerName)) {
      return;
    }
    playersWithN00bBooks_.remove(playerName);
    giveN00bBook(player);
  }

  public void giveN00bBook(Player player) {
    Inventory inv = player.getInventory();
    inv.addItem(createN00bBook());
  }

  public ItemStack createN00bBook() {
    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta sbook = (BookMeta)book.getItemMeta();
    sbook.setTitle(config_.getTitle());
    sbook.setAuthor(config_.getAuthor());
    sbook.setPages(config_.getPages());
    book.setItemMeta(sbook);
    return book;
  }

  public boolean checkForInventorySpace(Inventory inv, int emptySlots) {
    int foundEmpty = 0;
    final int end = inv.getSize();
    for (int slot = 0; slot < end; ++slot) {
      ItemStack item = inv.getItem(slot);
      if (item == null) {
        ++foundEmpty;
      } else if (item.getType().equals(Material.AIR)) {
        ++foundEmpty;
      }
    }
    return foundEmpty >= emptySlots;
  }

  public void giveHolidayPackage(Player player) {
    int count = 0;
    Inventory inv = player.getInventory();
    while (checkForInventorySpace(inv, 4)) {
        inv.addItem(createHolidayBook());
        inv.addItem(createFruitcake());
        inv.addItem(createTurkey());
        inv.addItem(createCoal());
        ++count;
    }
    info(String.format("%s generated %d packs of holiday cheer.",
          player.getName(), count));
  }

  public ItemStack createHolidayBook() {
    ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta sbook = (BookMeta)book.getItemMeta();
    sbook.setTitle(config_.getHolidayTitle());
    sbook.setAuthor(config_.getHolidayAuthor());
    sbook.setPages(config_.getHolidayPages());
    List<String> lore = new ArrayList<String>(1);
    lore.add("December 25th, 2013");
    sbook.setLore(lore);
    book.setItemMeta(sbook);
    return book;
  }

  public ItemStack createFruitcake() {
    ItemStack cake = new ItemStack(Material.CAKE);
    ItemMeta meta = cake.getItemMeta();
    meta.setDisplayName("Fruitcake");
    List<String> lore = new ArrayList<String>(1);
    lore.add("Deliciously stale");
    meta.setLore(lore);
    cake.setItemMeta(meta);
    return cake;
  }

  private String[] turkey_names_ = new String[] {
    "Turkey",
    "Turkey",
    "Turkey",
    "Turducken",
    "Tofurkey",
    "Cearc Frangach",
    "Dinde",
    "Kalkoen",
    "Indeyka",
    "Pollo d'India",
    "Pelehu",
    "Chilmyeonjo"
  };

  public ItemStack createTurkey() {
    String turkey_name = turkey_names_[prng_.nextInt(turkey_names_.length)];
    ItemStack turkey = new ItemStack(Material.COOKED_CHICKEN);
    ItemMeta meta = turkey.getItemMeta();
    meta.setDisplayName(turkey_name);
    List<String> lore = new ArrayList<String>(1);
    lore.add("Tastes like chicken");
    meta.setLore(lore);
    turkey.setItemMeta(meta);
    return turkey;
  }

  public ItemStack createCoal() {
    ItemStack coal = new ItemStack(Material.COAL);
    ItemMeta meta = coal.getItemMeta();
    List<String> lore = new ArrayList<String>(1);
    lore.add("You've been naughty");
    meta.setLore(lore);
    coal.setItemMeta(meta);
    return coal;
  }


  // ================================================
  // Playing records in jukeboxen? Gone

  // EventHandler registered in onPlayerInteract
  @BahHumbug(opt="disallow_record_playing", def="true")
  public void onRecordInJukebox(PlayerInteractEvent event) {
    if (!config_.get("disallow_record_playing").getBool()) {
      return;
    }
    Block cb = event.getClickedBlock();
    if (cb == null || cb.getType() != Material.JUKEBOX) {
      return;
    }
    ItemStack his = event.getItem();
    if(his != null && his.getType().isRecord()) {
      event.setCancelled(true);
    }
  }

  //=================================================
  // Water in the nether? Nope.

  @BahHumbugs ({
    @BahHumbug(opt="allow_water_in_nether"),
    @BahHumbug(opt="indestructible_end_portals", def="true")
  })
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent e) {
    if(!config_.get("allow_water_in_nether").getBool()) {
      if( ( e.getBlockClicked().getBiome() == Biome.HELL )
          && ( e.getBucket() == Material.WATER_BUCKET ) ) {
        e.setCancelled(true);
        e.getItemStack().setType(Material.BUCKET);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 5, 1));
      }
    }
    if (config_.get("indestructible_end_portals").getBool()) {
      Block baseBlock = e.getBlockClicked();
      BlockFace face = e.getBlockFace();
      Block block = baseBlock.getRelative(face);
      if (block.getType() == Material.ENDER_PORTAL) {
          e.setCancelled(true);
      }
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockFromToEvent(BlockFromToEvent e) {
    if(!config_.get("allow_water_in_nether").getBool()) {
      if( e.getToBlock().getBiome() == Biome.HELL ) {
        if( ( e.getBlock().getType() == Material.WATER )
            || ( e.getBlock().getType() == Material.STATIONARY_WATER ) ) {
          e.setCancelled(true);
        }
      }
    }
    if (config_.get("indestructible_end_portals").getBool()) {
      if (e.getToBlock().getType() == Material.ENDER_PORTAL) {
          e.setCancelled(true);
      }
    }
    if(!e.isCancelled() && config_.get("obsidian_generator").getBool()) {
      generateObsidian(e);
    }
  }

  // Generates obsidian like it did in 1.7.
  // Note that this does not change anything in versions where obsidian generation exists.
  @BahHumbug(opt="obsidian_generator", def="false")
  public void generateObsidian(BlockFromToEvent event) {
    if(!event.getBlock().getType().equals(Material.STATIONARY_LAVA)) {
      return;
    }
    if(!event.getToBlock().getType().equals(Material.TRIPWIRE)) {
      return;
    }
    Block string = event.getToBlock();
    if(!(string.getRelative(BlockFace.NORTH).getType().equals(Material.STATIONARY_WATER)
          || string.getRelative(BlockFace.EAST).getType().equals(Material.STATIONARY_WATER)
          || string.getRelative(BlockFace.WEST).getType().equals(Material.STATIONARY_WATER)
          ||  string.getRelative(BlockFace.SOUTH).getType().equals(Material.STATIONARY_WATER))) {
      return;
    }
    string.setType(Material.OBSIDIAN);
  }

  //=================================================
  // Stops perculators
  private Map<Chunk, Integer> waterChunks = new HashMap<Chunk, Integer>();
  BukkitTask waterSchedule = null;
  @BahHumbugs ({
  @BahHumbug(opt="max_water_lava_height", def="100", type=OptType.Int),
  @BahHumbug(opt="max_water_lava_amount", def = "400", type=OptType.Int),
  @BahHumbug(opt="max_water_lava_timer", def = "1200", type=OptType.Int)
  })
  @EventHandler(priority = EventPriority.LOWEST)
  public void stopLiquidMoving(BlockFromToEvent event){
	  try {
    Block to = event.getToBlock();
    Block from = event.getBlock();
    if (to.getLocation().getBlockY() < config_.get("max_water_lava_height").getInt()) {
      return;
    }
    Material mat = from.getType();
    if (!(mat.equals(Material.WATER) || mat.equals(Material.STATIONARY_WATER) ||
          mat.equals(Material.LAVA) || mat.equals(Material.STATIONARY_LAVA))) {
      return;
    }
    Chunk c = to.getChunk();
    if (!waterChunks.containsKey(c)){
      waterChunks.put(c, 0);
    }
    Integer i = waterChunks.get(c);
    i = i + 1;
    waterChunks.put(c, i);
    int amount = getWaterInNearbyChunks(c);
    if (amount > config_.get("max_water_lava_amount").getInt()) {
      event.setCancelled(true);
    }
    if (waterSchedule != null) {
      return;
    }
    waterSchedule = Bukkit.getScheduler().runTaskLater(this, new Runnable(){
      @Override
      public void run() {
        waterChunks.clear();
        waterSchedule = null;
      }
    }, config_.get("max_water_lava_timer").getInt());
	  } catch (Exception e){
		  getLogger().log(Level.INFO, "Tried getting info from a chunk before it generated, skipping.");
		  return;
	  }
  }

  public int getWaterInNearbyChunks(Chunk chunk){
    World world = chunk.getWorld();
    Chunk[] chunks = {
        world.getChunkAt(chunk.getX(), chunk.getZ()), world.getChunkAt(chunk.getX()-1, chunk.getZ()),
        world.getChunkAt(chunk.getX(), chunk.getZ()-1), world.getChunkAt(chunk.getX()-1, chunk.getZ()-1),
        world.getChunkAt(chunk.getX()+1, chunk.getZ()), world.getChunkAt(chunk.getX(), chunk.getZ()+1),
        world.getChunkAt(chunk.getX()+1, chunk.getZ()+1), world.getChunkAt(chunk.getX()-1, chunk.getZ()+1),
        world.getChunkAt(chunk.getX()+1, chunk.getZ()-1)
    };
    int count = 0;
    for (Chunk c: chunks){
      Integer amount = waterChunks.get(c);
      if (amount == null)
        continue;
      count += amount;
    }
    return count;
  }
  // ================================================
  // Changes Strength Potions, strength_multiplier 3 is roughly Pre-1.6 Level

  @BahHumbugs ({
    @BahHumbug(opt="nerf_strength", def="true"),
    @BahHumbug(opt="strength_multiplier", type=OptType.Int, def="3")
  })
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPlayerDamage(EntityDamageByEntityEvent event) {
    if (!config_.get("nerf_strength").getBool()) {
      return;
    }
    if (!(event.getDamager() instanceof Player)) {
      return;
    }
    Player player = (Player)event.getDamager();
    final int strengthMultiplier = config_.get("strength_multiplier").getInt();
    if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
      for (PotionEffect effect : player.getActivePotionEffects()) {
        if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
          final int potionLevel = effect.getAmplifier() + 1;
          final double unbuffedDamage = event.getDamage() / (1.3 * potionLevel + 1);
          final double newDamage = unbuffedDamage + (potionLevel * strengthMultiplier);
          event.setDamage(newDamage);
          break;
        }
      }
    }
  }

  //=================================================
  // Buffs health splash to pre-1.6 levels

  @BahHumbug(opt="buff_health_pots", def="true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPotionSplash(PotionSplashEvent event) {
    if (!config_.get("buff_health_pots").getBool()) {
      return;
    }
    for (PotionEffect effect : event.getEntity().getEffects()) {
      if (!(effect.getType().getName().equalsIgnoreCase("heal"))) { // Splash potion of poison
        return;
      }
    }
    for (LivingEntity entity : event.getAffectedEntities()) {
      if (entity instanceof Player) {
        if(((Damageable)entity).getHealth() > 0d) {
          final double newHealth = Math.min(
            ((Damageable)entity).getHealth() + 4.0D,
            ((Damageable)entity).getMaxHealth());
          entity.setHealth(newHealth);
        }
      }
    }
  }
  
  @BahHumbugs({
	  @BahHumbug(opt="disable_bed_nether_end", def="true")
  })
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerEnterBed(BlockPlaceEvent event) {
	  Block b = event.getBlock();
	  if (!(b.getType() == Material.BED || b.getType() == Material.BED_BLOCK))
		  return;
	  Environment env = b.getLocation().getWorld().getEnvironment();
	  if (config_.get("disable_bed_nether_end").getBool() && (env == Environment.NETHER || env == Environment.THE_END))
		  event.setCancelled(true);
  }

  //=================================================
  // Bow shots cause slow debuff

  @BahHumbugs ({
    @BahHumbug(opt="projectile_slow_chance", type=OptType.Int, def="30"),
    @BahHumbug(opt="projectile_slow_ticks", type=OptType.Int, def="100")
  })
  @EventHandler
  public void onEDBE(EntityDamageByEntityEvent event) {
    int rate = config_.get("projectile_slow_chance").getInt();
    if (rate <= 0 || rate > 100) {
      return;
    }
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    boolean damager_is_player_arrow = false;
    int chance_scaling = 0;
    Entity damager_entity = event.getDamager();
    if (damager_entity != null) {
      // public LivingEntity CraftArrow.getShooter()
      // Playing this game to not have to take a hard dependency on
      //  craftbukkit internals.
      try {
        Class<?> damager_class = damager_entity.getClass();
        if (damager_class.getName().endsWith(".CraftArrow")) {
          Method getShooter = damager_class.getMethod("getShooter");
          Object result = getShooter.invoke(damager_entity);
          if (result instanceof Player) {
            damager_is_player_arrow = true;
            String player_name = ((Player)result).getName();
            if (bow_level_.containsKey(player_name)) {
              chance_scaling = bow_level_.get(player_name);
            }
          }
        }
      } catch(Exception ex) {}
    }
    if (!damager_is_player_arrow) {
      return;
    }
    rate += chance_scaling * 5;
    int percent = prng_.nextInt(100);
    if (percent < rate){
      int ticks = config_.get("projectile_slow_ticks").getInt();
      Player player = (Player)event.getEntity();
      player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticks, 1, false));
    }
  }

  // Used to track bow enchantment levels per player
  private Map<String, Integer> bow_level_ = new TreeMap<String, Integer>();

  @EventHandler
  public void onEntityShootBow(EntityShootBowEvent event) {
    if (!(event.getEntity() instanceof Player)) {
         return;
    }
    int ench_level = 0;
    ItemStack bow = event.getBow();
    Map<Enchantment, Integer> enchants = bow.getEnchantments();
    for (Enchantment ench : enchants.keySet()) {
      int tmp_ench_level = 0;
      if (ench.equals(Enchantment.KNOCKBACK) || ench.equals(Enchantment.ARROW_KNOCKBACK)) {
        tmp_ench_level = enchants.get(ench) * 2;
      } else if (ench.equals(Enchantment.ARROW_DAMAGE)) {
        tmp_ench_level = enchants.get(ench);
      }
      if (tmp_ench_level > ench_level) {
        ench_level = tmp_ench_level;
      }
    }
    bow_level_.put(
        ((Player)event.getEntity()).getName(),
        ench_level);
  }

  // ================================================
  // BottleO refugees

  // Changes the yield from an XP bottle
  @BahHumbugs ({
    @BahHumbug(opt="ignore_experience", def="false"),
    @BahHumbug(opt="disable_experience", def="true"),
    @BahHumbug(opt="xp_per_bottle", type=OptType.Int, def="10")
  })
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onExpBottleEvent(ExpBottleEvent event) {
    if (config_.get("ignore_experience").getBool()) return;
    final int bottle_xp = config_.get("xp_per_bottle").getInt();
    if (config_.get("disable_experience").getBool()) {
      ((Player) event.getEntity().getShooter()).giveExp(bottle_xp);
      event.setExperience(0);
    } else {
      event.setExperience(bottle_xp);
    }
  }

  // Diables all XP gain except when manually changed via code.
  @EventHandler
  public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
    if (config_.get("ignore_experience").getBool()) return;
    if (config_.get("disable_experience").getBool()) {
      event.setAmount(0);
    }
  }

  // ================================================
  // Find the end portals

  public static final int ender_portal_id_ = Material.ENDER_PORTAL.getId();
  public static final int ender_portal_frame_id_ = Material.ENDER_PORTAL_FRAME.getId();
  private Set<Long> end_portal_scanned_chunks_ = new TreeSet<Long>();

  @BahHumbug(opt="find_end_portals", type=OptType.String)
  @EventHandler
  public void onFindEndPortals(ChunkLoadEvent event) {
    String scanWorld = config_.get("find_end_portals").getString();
    if (scanWorld.isEmpty()) {
      return;
    }
    World world = event.getWorld();
    if (!world.getName().equalsIgnoreCase(scanWorld)) {
      return;
    }
    Chunk chunk = event.getChunk();
    long chunk_id = ((long)chunk.getX() << 32L) + (long)chunk.getZ();
    if (end_portal_scanned_chunks_.contains(chunk_id)) {
      return;
    }
    end_portal_scanned_chunks_.add(chunk_id);
    int chunk_x = chunk.getX() * 16;
    int chunk_end_x = chunk_x + 16;
    int chunk_z = chunk.getZ() * 16;
    int chunk_end_z = chunk_z + 16;
    int max_height = 0;
    for (int x = chunk_x; x < chunk_end_x; x += 3) {
      for (int z = chunk_z; z < chunk_end_z; ++z) {
        int height = world.getMaxHeight();
        if (height > max_height) {
          max_height = height;
        }
      }
    }
    for (int y = 1; y <= max_height; ++y) {
      int z_adj = 0;
      for (int x = chunk_x; x < chunk_end_x; ++x) {
        for (int z = chunk_z + z_adj; z < chunk_end_z; z += 3) {
          int block_type = world.getBlockTypeIdAt(x, y, z);
          if (block_type == ender_portal_id_ || block_type == ender_portal_frame_id_) {
            info(String.format("End portal found at %d,%d", x, z));
            return;
          }
        }
        // This funkiness results in only searching 48 of the 256 blocks on
        //  each y-level. 81.25% fewer blocks checked.
        ++z_adj;
        if (z_adj >= 3) {
          z_adj = 0;
          x += 2;
        }
      }
    }
  }

  // ================================================
  // Prevent inventory access while in a vehicle, unless it's the Player's

  @BahHumbugs ({
    @BahHumbug(opt="prevent_opening_container_carts", def="true"),
    @BahHumbug(opt="prevent_vehicle_inventory_open", def="true")
  })
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPreventVehicleInvOpen(InventoryOpenEvent event) {
    // Cheap break-able conditional statement
    while (config_.get("prevent_vehicle_inventory_open").getBool()) {
      HumanEntity human = event.getPlayer();
      if (!(human instanceof Player)) {
        break;
      }
      if (!human.isInsideVehicle()) {
        break;
      }
      InventoryHolder holder = event.getInventory().getHolder();
      if (holder == human) {
        break;
      }
      event.setCancelled(true);
      break;
    }
    if (config_.get("prevent_opening_container_carts").getBool() && !event.isCancelled()) {
      InventoryHolder holder = event.getInventory().getHolder();
      if (holder instanceof StorageMinecart || holder instanceof HopperMinecart) {
        event.setCancelled(true);
      }
    }
  }

  // ================================================
  // Disable outbound hopper transfers

  @BahHumbug(opt="disable_hopper_out_transfers", def="false")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onInventoryMoveItem(InventoryMoveItemEvent event) {
    if (!config_.get("disable_hopper_out_transfers").getBool()) {
      return;
    }
    final Inventory src = event.getSource();
    final InventoryHolder srcHolder = src.getHolder();
    if (srcHolder instanceof Hopper) {
      event.setCancelled(true);
      return;
    }
  }

  // ================================================
  // Adjust horse speeds

  @BahHumbug(opt="horse_speed", type=OptType.Double, def="0.170000")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onVehicleEnter(VehicleEnterEvent event) {
    // 0.17 is just a tad slower than minecarts
    Vehicle vehicle = event.getVehicle();
    if (!(vehicle instanceof Horse)) {
      return;
    }
    Versioned.setHorseSpeed((Entity)vehicle, config_.get("horse_speed").getDouble());
  }

  // ================================================
  // Admins can view player inventories

  @SuppressWarnings("deprecation")
  public void onInvseeCommand(Player admin, String playerName) {
    final Player player = Bukkit.getPlayerExact(playerName);
    if (player == null) {
      admin.sendMessage("Player not found");
      return;
    }
    final Inventory pl_inv = player.getInventory();
    final Inventory inv = Bukkit.createInventory(
        admin, 36, playerName + "'s Inventory");
    for (int slot = 0; slot < 36; slot++) {
      final ItemStack it = pl_inv.getItem(slot);
      inv.setItem(slot, it);
    }
    admin.openInventory(inv);
    admin.updateInventory();
  }

  // ================================================
  // Fix boats

  @BahHumbug(opt="prevent_self_boat_break", def="true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPreventLandBoats(VehicleDestroyEvent event) {
    if (!config_.get("prevent_land_boats").getBool()) {
      return;
    }
    final Vehicle vehicle = event.getVehicle();
    if (vehicle == null || !(vehicle instanceof Boat)) {
      return;
    }
    final Entity passenger = vehicle.getPassenger();
    if (passenger == null || !(passenger instanceof Player)) {
      return;
    }
    final Entity attacker = event.getAttacker();
    if (attacker == null) {
      return;
    }
    if (!attacker.getUniqueId().equals(passenger.getUniqueId())) {
      return;
    }
    final Player player = (Player)passenger;
    Humbug.info(String.format(
        "Player '%s' kicked for self damaging boat at %s",
        player.getName(), vehicle.getLocation().toString()));
    vehicle.eject();
    vehicle.getWorld().dropItem(vehicle.getLocation(), new ItemStack(Material.BOAT));
    vehicle.remove();
    ((Player)passenger).kickPlayer("Nope");
  }

  @BahHumbug(opt="prevent_land_boats", def="true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPreventLandBoats(VehicleMoveEvent event) {
    if (!config_.get("prevent_land_boats").getBool()) {
      return;
    }
    final Vehicle vehicle = event.getVehicle();
    if (vehicle == null || !(vehicle instanceof Boat)) {
      return;
    }
    final Entity passenger = vehicle.getPassenger();
    if (passenger == null || !(passenger instanceof Player)) {
      return;
    }
    final Location to = event.getTo();
    final Material boatOn = to.getBlock().getType();
    if (boatOn.equals(Material.STATIONARY_WATER) || boatOn.equals(Material.WATER)) {
        return;
    }
    Humbug.info(String.format(
        "Player '%s' removed from land-boat at %s",
        ((Player)passenger).getName(), to.toString()));
    vehicle.eject();
    vehicle.getWorld().dropItem(vehicle.getLocation(), new ItemStack(Material.BOAT));
    vehicle.remove();
  }

  // ================================================
  // Fix minecarts

  public boolean checkForTeleportSpace(Location loc) {
    final Block block = loc.getBlock();
    final Material mat = block.getType();
    if (mat.isSolid()) {
      return false;
    }
    final Block above = block.getRelative(BlockFace.UP);
    if (above.getType().isSolid()) {
      return false;
    }
    return true;
  }

  public boolean tryToTeleport(Player player, Location location, String reason) {
    Location loc = location.clone();
    loc.setX(Math.floor(loc.getX()) + 0.500000D);
    loc.setY(Math.floor(loc.getY()) + 0.02D);
    loc.setZ(Math.floor(loc.getZ()) + 0.500000D);
    final Location baseLoc = loc.clone();
    final World world = baseLoc.getWorld();
    // Check if teleportation here is viable
    boolean performTeleport = checkForTeleportSpace(loc);
    if (!performTeleport) {
      loc.setY(loc.getY() + 1.000000D);
      performTeleport = checkForTeleportSpace(loc);
    }
    if (performTeleport) {
      player.setVelocity(new Vector());
      player.teleport(loc);
      Humbug.info(String.format(
          "Player '%s' %s: Teleported to %s",
          player.getName(), reason, loc.toString()));
      return true;
    }
    loc = baseLoc.clone();
    // Create a sliding window of block types and track how many of those
    //  are solid. Keep fetching the block below the current block to move down.
    int air_count = 0;
    LinkedList<Material> air_window = new LinkedList<Material>();
    loc.setY((float)world.getMaxHeight() - 2);
    Block block = world.getBlockAt(loc);
    for (int i = 0; i < 4; ++i) {
      Material block_mat = block.getType();
      if (!block_mat.isSolid()) {
        ++air_count;
      }
      air_window.addLast(block_mat);
      block = block.getRelative(BlockFace.DOWN);
    }
    // Now that the window is prepared, scan down the Y-axis.
    while (block.getY() >= 1) {
      Material block_mat = block.getType();
      if (block_mat.isSolid()) {
        if (air_count == 4) {
          player.setVelocity(new Vector());
          loc = block.getLocation();
          loc.setX(Math.floor(loc.getX()) + 0.500000D);
          loc.setY(loc.getY() + 1.02D);
          loc.setZ(Math.floor(loc.getZ()) + 0.500000D);
          player.teleport(loc);
          Humbug.info(String.format(
              "Player '%s' %s: Teleported to %s",
              player.getName(), reason, loc.toString()));
          return true;
        }
      } else { // !block_mat.isSolid()
        ++air_count;
      }
      air_window.addLast(block_mat);
      if (!air_window.removeFirst().isSolid()) {
        --air_count;
      }
      block = block.getRelative(BlockFace.DOWN);
    }
    return false;
  }

  @BahHumbug(opt="prevent_ender_pearl_save", def="true")
  @EventHandler
  public void enderPearlSave(EnderPearlUnloadEvent event) {
    if(!config_.get("prevent_ender_pearl_save").getBool())
      return;
    event.setCancelled(true);
  }

  @BahHumbug(opt="fix_vehicle_logout_bug", def="true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled=true)
  public void onDisallowVehicleLogout(PlayerQuitEvent event) {
    if (!config_.get("fix_vehicle_logout_bug").getBool()) {
      return;
    }
    kickPlayerFromVehicle(event.getPlayer());
  }

  public void kickPlayerFromVehicle(Player player) {
    Entity vehicle = player.getVehicle();
    if (vehicle == null
        || !(vehicle instanceof Minecart || vehicle instanceof Horse || vehicle instanceof Arrow)) {
      return;
    }
    Location vehicleLoc = vehicle.getLocation();
    // Vehicle data has been cached, now safe to kick the player out
    player.leaveVehicle();
    if (!tryToTeleport(player, vehicleLoc, "logged out")) {
      player.setHealth(0.000000D);
      Humbug.info(String.format(
          "Player '%s' logged out in vehicle: Killed", player.getName()));
    }
  }

  @BahHumbug(opt="fix_minecart_reenter_bug", def="true")
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onFixMinecartReenterBug(VehicleExitEvent event) {
    if (!config_.get("fix_minecart_reenter_bug").getBool()) {
      return;
    }
    final Vehicle vehicle = event.getVehicle();
    if (vehicle == null || !(vehicle instanceof Minecart)) {
      return;
    }
    final Entity passengerEntity = event.getExited();
    if (passengerEntity == null || !(passengerEntity instanceof Player)) {
      return;
    }
    // Must delay the teleport 2 ticks or else the player's mis-managed
    //  movement still occurs. With 1 tick it could still occur.
    final Player player = (Player)passengerEntity;
    final Location vehicleLoc = vehicle.getLocation();
    Bukkit.getScheduler().runTaskLater(this, new Runnable() {
      @Override
      public void run() {
        if (!tryToTeleport(player, vehicleLoc, "exiting vehicle")) {
          player.setHealth(0.000000D);
          Humbug.info(String.format(
              "Player '%s' exiting vehicle: Killed", player.getName()));
        }
      }
    }, 2L);
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onFixMinecartReenterBug(VehicleDestroyEvent event) {
    if (!config_.get("fix_minecart_reenter_bug").getBool()) {
      return;
    }
    final Vehicle vehicle = event.getVehicle();
    if (vehicle == null || !(vehicle instanceof Minecart || vehicle instanceof Horse)) {
      return;
    }
    final Entity passengerEntity = vehicle.getPassenger();
    if (passengerEntity == null || !(passengerEntity instanceof Player)) {
      return;
    }
    // Must delay the teleport 2 ticks or else the player's mis-managed
    //  movement still occurs. With 1 tick it could still occur.
    final Player player = (Player)passengerEntity;
    final Location vehicleLoc = vehicle.getLocation();
    Bukkit.getScheduler().runTaskLater(this, new Runnable() {
      @Override
      public void run() {
        if (!tryToTeleport(player, vehicleLoc, "in destroyed vehicle")) {
          player.setHealth(0.000000D);
          Humbug.info(String.format(
              "Player '%s' in destroyed vehicle: Killed", player.getName()));
        }
      }
    }, 2L);
  }

  // ================================================
  // Adjust ender pearl gravity

  public final static int pearlId = 368;
  public final static MinecraftKey pearlKey = new MinecraftKey("ender_pearl");

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @BahHumbug(opt="ender_pearl_gravity", type=OptType.Double, def="0.060000")
  private void hookEnderPearls() {
    try {
      // They thought they could stop us by preventing us from registering an
      // item. We'll show them
      Field idRegistryField = Item.REGISTRY.getClass().getDeclaredField("a");
      idRegistryField.setAccessible(true);
      Object idRegistry = idRegistryField.get(Item.REGISTRY);

      Field idRegistryMapField = idRegistry.getClass().getDeclaredField("a");
      idRegistryMapField.setAccessible(true);
      Object idRegistryMap = idRegistryMapField.get(idRegistry);

      Field idRegistryItemsField = idRegistry.getClass().getDeclaredField("b");
      idRegistryItemsField.setAccessible(true);
      Object idRegistryItemList = idRegistryItemsField.get(idRegistry);

      // Remove ItemEnderPearl from the ID Registry
      Item idItem = null;
      Iterator<Item> itemListIter = ((List<Item>)idRegistryItemList).iterator();
      while (itemListIter.hasNext()) {
        idItem = itemListIter.next();
        if (idItem == null) {
          continue;
        }
        if (!(idItem instanceof ItemEnderPearl)) {
          continue;
        }
        itemListIter.remove();
        break;
      }
      if (idItem != null) {
        ((Map<Item, Integer>)idRegistryMap).remove(idItem);
      }
      // Register our custom pearl Item.
      Item.REGISTRY.a(pearlId, pearlKey, new CustomNMSItemEnderPearl(config_));

      // Setup the custom entity
      Field fieldStringToClass = EntityTypes.class.getDeclaredField("c");
      Field fieldClassToString = EntityTypes.class.getDeclaredField("d");
      fieldStringToClass.setAccessible(true);
      fieldClassToString.setAccessible(true);
      
      Field fieldClassToId = EntityTypes.class.getDeclaredField("f");
      Field fieldStringToId = EntityTypes.class.getDeclaredField("g");
      fieldClassToId.setAccessible(true);
      fieldStringToId.setAccessible(true);
      
      Map mapStringToClass = (Map)fieldStringToClass.get(null);
      Map mapClassToString = (Map)fieldClassToString.get(null);
      
      Map mapClassToId = (Map)fieldClassToId.get(null);
      Map mapStringToId = (Map)fieldStringToId.get(null);
      
      mapStringToClass.put("ThrownEnderpearl",CustomNMSEntityEnderPearl.class);
      mapStringToId.put("ThrownEnderpearl", Integer.valueOf(14));
      
      mapClassToString.put(CustomNMSEntityEnderPearl.class, "ThrownEnderpearl");
      mapClassToId.put(CustomNMSEntityEnderPearl.class, Integer.valueOf(14));
    } catch (Exception e) {
      Humbug.severe("Exception while overriding MC's ender pearl class");
      e.printStackTrace();
    }
  }

  // ================================================
  // Hunger Changes

  // Keep track if the player just ate.
  private Map<Player, Double> playerLastEat_ = new HashMap<Player, Double>();

  @BahHumbug(opt="saturation_multiplier", type=OptType.Double, def="0.0")
  @EventHandler
  public void setSaturationOnFoodEat(PlayerItemConsumeEvent event) {
    // Each food sets a different saturation.
    final Player player = event.getPlayer();
    ItemStack item = event.getItem();
    Material mat = item.getType();
    double multiplier = config_.get("saturation_multiplier").getDouble();
    if (multiplier <= 0.000001 && multiplier >= -0.000001) {
      return;
    }
    switch(mat) {
      case APPLE:
        playerLastEat_.put(player, multiplier*2.4);
      case BAKED_POTATO:
        playerLastEat_.put(player, multiplier*7.2);
      case BREAD:
        playerLastEat_.put(player, multiplier*6);
      case CAKE:
        playerLastEat_.put(player, multiplier*0.4);
      case CARROT_ITEM:
        playerLastEat_.put(player, multiplier*4.8);
      case COOKED_FISH:
        playerLastEat_.put(player, multiplier*6);
      case GRILLED_PORK:
        playerLastEat_.put(player, multiplier*12.8);
      case COOKIE:
        playerLastEat_.put(player, multiplier*0.4);
      case GOLDEN_APPLE:
        playerLastEat_.put(player, multiplier*9.6);
      case GOLDEN_CARROT:
        playerLastEat_.put(player, multiplier*14.4);
      case MELON:
        playerLastEat_.put(player, multiplier*1.2);
      case MUSHROOM_SOUP:
        playerLastEat_.put(player, multiplier*7.2);
      case POISONOUS_POTATO:
        playerLastEat_.put(player, multiplier*1.2);
      case POTATO:
        playerLastEat_.put(player, multiplier*0.6);
      case RAW_FISH:
        playerLastEat_.put(player, multiplier*1);
      case PUMPKIN_PIE:
        playerLastEat_.put(player, multiplier*4.8);
      case RAW_BEEF:
        playerLastEat_.put(player,  multiplier*1.8);
      case RAW_CHICKEN:
        playerLastEat_.put(player, multiplier*1.2);
      case PORK:
        playerLastEat_.put(player,  multiplier*1.8);
      case ROTTEN_FLESH:
        playerLastEat_.put(player, multiplier*0.8);
      case SPIDER_EYE:
        playerLastEat_.put(player, multiplier*3.2);
      case COOKED_BEEF:
        playerLastEat_.put(player, multiplier*12.8);
      default:
        playerLastEat_.put(player, multiplier);
        Bukkit.getServer().getScheduler().runTaskLater(this, new Runnable() {
          // In case the player ingested a potion, this removes the
          // saturation from the list. Unsure if I have every item
          // listed. There is always the other cases of like food
          // that shares same id
          @Override
          public void run() {
            playerLastEat_.remove(player);
          }
        }, 80);
    }
  }

  @BahHumbug(opt="hunger_slowdown", type=OptType.Double, def="0.0")
  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
    final Player player = (Player) event.getEntity();
    final double mod = config_.get("hunger_slowdown").getDouble();
    Double saturation;
    if (playerLastEat_.containsKey(player)) { // if the player just ate
      saturation = playerLastEat_.get(player);
      if (saturation == null) {
        saturation = ((Float)player.getSaturation()).doubleValue();
      }
    } else {
      saturation = Math.min(
          player.getSaturation() + mod,
          20.0D + (mod * 2.0D));
    }
    player.setSaturation(saturation.floatValue());
  }

  //=================================================
  //Remove Book Copying
  @BahHumbug(opt="copy_book_enable", def= "false")
  public void removeBooks() {
    if (config_.get("copy_book_enable").getBool()) {
      return;
    }
    Iterator<Recipe> it = getServer().recipeIterator();
    while (it.hasNext()) {
      Recipe recipe = it.next();
      ItemStack resulting_item = recipe.getResult();
      if ( // !copy_book_enable_ &&
          isWrittenBook(resulting_item)) {
        it.remove();
        info("Copying Books disabled");
      }
    }
  }
  
  public boolean isWrittenBook(ItemStack item) {
    if (item == null) {
      return false;
    }
    Material material = item.getType();
    return material.equals(Material.WRITTEN_BOOK);
  }

  // ================================================
  // Prevent tree growth wrap-around

  @BahHumbug(opt="prevent_tree_wraparound", def="true")
  @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = true)
  public void onStructureGrowEvent(StructureGrowEvent event) {
    if (!config_.get("prevent_tree_wraparound").getBool()) {
      return;
    }
    int maxY = 0, minY = 257;
    for (BlockState bs : event.getBlocks()) {
      final int y = bs.getLocation().getBlockY();
      maxY = Math.max(maxY, y);
      minY = Math.min(minY, y);
    }
    if (maxY - minY > 240) {
      event.setCancelled(true);
      final Location loc = event.getLocation();
      info(String.format("Prevented structure wrap-around at %d, %d, %d",
          loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }
  }
  
  // ================================================
  // Equipping banners
  @BahHumbug(opt="equipping_banners", def="true")
  public void onEquippingBanners(PlayerInteractEvent event){
	  if(!config_.get("equipping_banners").getBool()) {
	      return;
	  }
	  if (event.getItem() == null
				|| !event.getItem().getType().equals(Material.BANNER)
				|| (event.getAction() != Action.LEFT_CLICK_AIR&&event.getAction() != Action.LEFT_CLICK_BLOCK)) {
		  return;
	  }
	  Player player = event.getPlayer();
	  ItemStack banner = new ItemStack(event.getItem());
	  banner.setAmount(1);
	  player.getInventory().removeItem(banner);
	  if (player.getEquipment().getHelmet() != null) {
		  if(player.getInventory().addItem(player.getEquipment().getHelmet()).size() != 0) {
			  player.getWorld().dropItem(player.getLocation(), player.getEquipment().getHelmet());
		  }
	  }
	  player.getEquipment().setHelmet(banner);
  }
  
  // ================================================
  // Disable changing spawners with eggs
  
  @BahHumbug(opt="changing_spawners_with_eggs", def="true")
  public void onChangingSpawners(PlayerInteractEvent event)
  {
	if (!config_.get("changing_spawners_with_eggs").getBool()) {
		return;
	}
    if ((event.getClickedBlock() != null) && (event.getItem() != null) && 
      (event.getClickedBlock().getType()==Material.MOB_SPAWNER) && (event.getItem().getType() == Material.MONSTER_EGG)) {
      event.setCancelled(true);
    }
  }
  
  // ================================================
  // Enforce good sign data length

  @BahHumbugs( {
    @BahHumbug(opt="prevent_long_signs", def="true"),
    @BahHumbug(opt="prevent_long_signs_limit", type=OptType.Int, def="100"),
	@BahHumbug(opt="prevent_long_signs_allornothing", def="true"),
	@BahHumbug(opt="prevent_long_signs_cancelevent", def="false")
  })
  @EventHandler(ignoreCancelled=true)
  public void onSignFinalize(SignChangeEvent e) {
    if (!config_.get("prevent_long_signs").getBool()) {
      return;
    }
	String[] signdata = e.getLines();
	for (int i = 0; i < signdata.length; i++) {
      if (signdata[i] != null && signdata[i].length() > config_.get("prevent_long_signs_limit").getInt()) {
        Player p = e.getPlayer();
        Location location = e.getBlock().getLocation();
        warning(String.format(
            "Player '%s' [%s] attempted to place sign at ([%s] %d, %d, %d) with line %d having length %d > %d. Preventing.", 
            p.getPlayerListName(), p.getUniqueId(), location.getWorld().getName(), 
            location.getBlockX(), location.getBlockY(), location.getBlockZ(),
            i, signdata[i].length(), config_.get("prevent_long_signs_limit").getInt()));

        if (config_.get("prevent_long_signs_cancelevent").getBool()) {
          e.setCancelled(true);
          return;
        }
        if (config_.get("prevent_long_signs_allornothing").getBool()) {
          e.setLine(i, "");
        } else {
          e.setLine(i, signdata[i].substring(0, config_.get("prevent_long_signs_limit").getInt()));
        }
      }
    }
  }

  private HashMap<String, Set<Long>> signs_scanned_chunks_ = new HashMap<String, Set<Long>>();

  @BahHumbug(opt="prevent_long_signs_in_chunks", def="true")
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
  public void onSignLoads(ChunkLoadEvent event) {
    if (!config_.get("prevent_long_signs_in_chunks").getBool()) {
      return;
    }
    Chunk chunk = event.getChunk();
    String world = chunk.getWorld().getName();
    long chunk_id = ((long)chunk.getX() << 32L) + (long)chunk.getZ();
    if (signs_scanned_chunks_.containsKey(world)) {
      if (signs_scanned_chunks_.get(world).contains(chunk_id)) {
        return;
      }
    } else {
      signs_scanned_chunks_.put(world, new TreeSet<Long>()); 
    }
    signs_scanned_chunks_.get(world).add(chunk_id);

    BlockState[] allTiles = chunk.getTileEntities();

    for(BlockState tile: allTiles) {
      if (tile instanceof Sign) {
        Sign sign = (Sign) tile;
        String[] signdata = sign.getLines();
        for (int i = 0; i < signdata.length; i++) {
          if (signdata[i] != null && signdata[i].length() > config_.get("prevent_long_signs_limit").getInt()) {
            Location location = sign.getLocation();
            warning(String.format(
                "Line %d in sign at ([%s] %d, %d, %d) is length %d > %d. Curating.", i,
                world, location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                signdata[i].length(), config_.get("prevent_long_signs_limit").getInt()));

            if (config_.get("prevent_long_signs_allornothing").getBool()) {
              sign.setLine(i, "");
            } else {
              sign.setLine(i, signdata[i].substring(0, config_.get("prevent_long_signs_limit").getInt()));
            }

            sign.update(true);
          }
        }
      }
    }
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void adminAccessBlockedChest(PlayerInteractEvent e) {
	  if (!e.getPlayer().hasPermission("humbug.admin") && !e.getPlayer().isOp()) {
		  return;
	  }
	  if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		  Player p = e.getPlayer();
		  Set <Material> s = new TreeSet<Material>();
		  s.add(Material.AIR);
		  s.add(Material.OBSIDIAN); //probably in a vault
		  List <Block> blocks = p.getLineOfSight(s, 8);
		  for(Block b:blocks) {
			  Material m = b.getType();
			  if(m == Material.CHEST || m == Material.TRAPPED_CHEST) {
				  if(b.getRelative(BlockFace.UP).getType().isOccluding()) {
					  //dont show inventory twice if a normal chest is opened
					  final Inventory che_inv = ((InventoryHolder)b.getState()).getInventory();
						    p.openInventory(che_inv);
						    p.updateInventory();	  
				  }
				  break;
			  }
		  }
	  }
  }
  
  //there is a bug in minecraft 1.8, which allows fire and vines to spread into unloaded chunks
  //where they can replace any existing block
  @EventHandler(priority = EventPriority.LOWEST)
  public void fixSpreadInUnloadedChunks(BlockSpreadEvent e) {
	  if (!e.getBlock().getChunk().isLoaded()) {
		  e.setCancelled(true);
	  }
  }

  // ================================================
  // General

  public void onLoad()
  {
    loadConfiguration();
    //hookEnderPearls();
    info("Loaded");
  }

  public void onEnable() {
    registerEvents();
    registerCommands();
    removeRecipies();
    removeBooks();
    registerTimerForPearlCheck();
    global_instance_ = this;
    info("Enabled");
    if (Bukkit.getPluginManager().getPlugin("CombatTag") != null)
    	combatTag_ = new CombatTagManager();
    else if (Bukkit.getPluginManager().getPlugin("CombatTagPlus") != null)
    	combatTag_ = new CombatTagPlusManager();
  }
  
  public void onDisable() {
    if (config_.get("fix_vehicle_logout_bug").getBool()) {
      for (World world: getServer().getWorlds()) {
        for (Player player: world.getPlayers()) {
          kickPlayerFromVehicle(player);
        }
      }
    }
  }

  public boolean isInitiaized() {
    return global_instance_ != null;
  }

  public boolean toBool(String value) {
    if (value.equals("1") || value.equalsIgnoreCase("true")) {
      return true;
    }
    return false;
  }

  public int toInt(String value, int default_value) {
    try {
      return Integer.parseInt(value);
    } catch(Exception e) {
      return default_value;
    }
  }

  public double toDouble(String value, double default_value) {
    try {
      return Double.parseDouble(value);
    } catch(Exception e) {
      return default_value;
    }
  }

  public int toMaterialId(String value, int default_value) {
    try {
      return Integer.parseInt(value);
    } catch(Exception e) {
      Material mat = Material.matchMaterial(value);
      if (mat != null) {
        return mat.getId();
      }
    }
    return default_value;
  }

  public boolean onCommand(
      CommandSender sender,
      Command command,
      String label,
      String[] args) {
    if (sender instanceof Player && command.getName().equals("invsee")) {
      if (args.length < 1) {
        sender.sendMessage("Provide a name");
        return false;
      }
      onInvseeCommand((Player)sender, args[0]);
      return true;
    }
    if (sender instanceof Player
        && command.getName().equals("introbook")) {
      if (!config_.get("drop_newbie_book").getBool()) {
        return true;
      }
      Player sendBookTo = (Player)sender;
      if (args.length >= 1) {
        Player possible = Bukkit.getPlayerExact(args[0]);
        if (possible != null) {
          sendBookTo = possible;
        }
      }
      giveN00bBook(sendBookTo);
      return true;
    }
    if (sender instanceof Player
        && command.getName().equals("bahhumbug")) {
      giveHolidayPackage((Player)sender);
      return true;
    }
    if (!(sender instanceof ConsoleCommandSender) ||
        !command.getName().equals("humbug") ||
        args.length < 1) {
      return false;
    }
    String option = args[0];
    String value = null;
    String subvalue = null;
    boolean set = false;
    boolean subvalue_set = false;
    String msg = "";
    if (args.length > 1) {
      value = args[1];
      set = true;
    }
    if (args.length > 2) {
      subvalue = args[2];
      subvalue_set = true;
    }
    ConfigOption opt = config_.get(option);
    if (opt != null) {
      if (set) {
        opt.set(value);
      }
      msg = String.format("%s = %s", option, opt.getString());
    } else if (option.equals("debug")) {
      if (set) {
        config_.setDebug(toBool(value));
      }
      msg = String.format("debug = %s", config_.getDebug());
    } else if (option.equals("loot_multiplier")) {
      String entity_type = "generic";
      if (set && subvalue_set) {
        entity_type = value;
        value = subvalue;
      }
      if (set) {
        config_.setLootMultiplier(
                entity_type, toInt(value, config_.getLootMultiplier(entity_type)));
      }
      msg = String.format(
          "loot_multiplier(%s) = %d", entity_type, config_.getLootMultiplier(entity_type));
    } else if (option.equals("remove_mob_drops")) {
      if (set && subvalue_set) {
        if (value.equals("add")) {
          config_.addRemoveItemDrop(toMaterialId(subvalue, -1));
        } else if (value.equals("del")) {
          config_.removeRemoveItemDrop(toMaterialId(subvalue, -1));
        }
      }
      msg = String.format("remove_mob_drops = %s", config_.toDisplayRemoveItemDrops());
    } else if (option.equals("save")) {
      config_.save();
      msg = "Configuration saved";
    } else if (option.equals("reload")) {
      config_.reload();
      msg = "Configuration loaded";
    } else {
      msg = String.format("Unknown option %s", option);
    }
    sender.sendMessage(msg);
    return true;
  }

  public void registerCommands() {
    ConsoleCommandSender console = getServer().getConsoleSender();
    console.addAttachment(this, "humbug.console", true);
  }

  private void registerEvents() {
    getServer().getPluginManager().registerEvents(this, this);
    getServer().getScheduler().scheduleSyncRepeatingTask(this, new DiskMonitor(this), 0L, 20L*60L); //once a minute
  }

  private void loadConfiguration() {
    config_ = Config.initialize(this);
  }
  
  @BahHumbug(opt="disk_space_shutdown", type = OptType.Double, def = "0.02")
  public Config getHumbugConfig() {
	  return config_;
  }
}
