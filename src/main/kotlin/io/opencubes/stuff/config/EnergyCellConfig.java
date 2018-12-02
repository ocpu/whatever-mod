package io.opencubes.stuff.config;

import io.opencubes.stuff.Stuff;
import net.minecraftforge.common.config.Config;

@Config(modid = Stuff.ID, category = "energycell")
public class EnergyCellConfig {

    @Config.Comment("The maximum power the energy furnace can have in its buffer")
    public static int MAX_POWER = 5_000;

    @Config.Comment("The maximum power receiving speed the energy furnace input (power/tick)")
    @Config.LangKey("sgc.receiving_speed")
    public static int RECEIVING_SPEED = 100;

    @Config.Comment("The energy furnace operational power per tick (power/tick)")
    public static int RF_PER_TICK = 5;
}