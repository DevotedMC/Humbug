package com.untamedears.humbug;

import java.io.File;

import org.bukkit.Bukkit;

public class DiskMonitor implements Runnable {
	private File serverFolder;
	private Config config;

	public DiskMonitor(Humbug plugin) {
		serverFolder = plugin.getDataFolder().getParentFile();
		config = plugin.getHumbugConfig();
	}

	public void run() {
		Double criticalValue = config.get("disk_space_shutdown").getDouble();
		if (((double)serverFolder.getFreeSpace()/(double)serverFolder.getTotalSpace()) < criticalValue) {
			Humbug.severe("Available disk space below critical value, shutting the server down to prevent damage");
			Bukkit.shutdown();
		}
	}

}
