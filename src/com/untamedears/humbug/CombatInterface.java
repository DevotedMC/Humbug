package com.untamedears.humbug;

import org.bukkit.entity.Player;

public interface CombatInterface {

	public boolean tagPlayer(Player player);
	public boolean tagPlayer(String playerName);
	public Integer remainingSeconds(Player player);
}
