package io.opencubes.stuff.config;

import io.opencubes.stuff.Stuff;
import net.minecraftforge.common.config.Config;

@Config(modid = Stuff.ID, category = "solarGenerator")
public class SolarGeneratorConfig {

  @Config.Comment("The maximum power the solar generator can have in its buffer")
  public static int MAX_POWER = 100_000;

  @Config.Comment("The maximum power transfer speed the solar generator output (power/tick)")
  @Config.LangKey("sgc.transfer_speed")
  public static int TRANSFER_SPEED = 100;

}