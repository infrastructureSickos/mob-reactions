package com.infrastructuresickos.mob_reactions;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MobReactions.MOD_ID)
public class MobReactions {
    public static final String MOD_ID = "mob_reactions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public MobReactions() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MRConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(new MobReactionsEventHandler());
        LOGGER.info("MobReactions initialized");
    }
}
