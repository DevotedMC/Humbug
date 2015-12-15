package com.untamedears.humbug;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.entity.Entity;

public class Versioned {
  // Static class
  private Versioned() {}

  public static double getHorseSpeed(Entity entity) {
    if (!(entity instanceof CraftHorse)) {
      return -1.0000001;
    }
    EntityHorse mcHorseAlot = ((CraftHorse)entity).getHandle();
    // GenericAttributes.d == generic.movementSpeed
    return mcHorseAlot.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b();
  }

  public static void setHorseSpeed(Entity entity, double speedModifier) {
    if (!(entity instanceof CraftHorse)) {
      return;
    }
    EntityHorse mcHorseAlot = ((CraftHorse)entity).getHandle();
    mcHorseAlot.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speedModifier);
  }
}
