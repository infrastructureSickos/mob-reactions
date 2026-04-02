package com.infrastructuresickos.mob_reactions;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MRConfig {
    public static final ForgeConfigSpec SPEC;
    public static final MRConfig INSTANCE;

    static {
        Pair<MRConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MRConfig::new);
        INSTANCE = specPair.getLeft();
        SPEC = specPair.getRight();
    }

    /** Chance (0.0–1.0) that a Husk places sand when hit. */
    public final ForgeConfigSpec.DoubleValue huskHitSandChance;
    /** Chance (0.0–1.0) that a Zombie drops a gravel block on death. */
    public final ForgeConfigSpec.DoubleValue zombieDeathGravelChance;
    /** Chance (0.0–1.0) that a Skeleton drops a gravel block on death. */
    public final ForgeConfigSpec.DoubleValue skeletonDeathGravelChance;
    /** Chance (0.0–1.0) that a Husk drops a sand block on death. */
    public final ForgeConfigSpec.DoubleValue huskDeathSandChance;

    private MRConfig(ForgeConfigSpec.Builder builder) {
        builder.push("on_hit");
        huskHitSandChance = builder
                .comment("Chance a Husk places sand when hit (0.0–1.0, default 0.1)")
                .defineInRange("huskHitSandChance", 0.1, 0.0, 1.0);
        builder.pop();

        builder.push("on_death");
        zombieDeathGravelChance = builder
                .comment("Chance a Zombie drops gravel on death (0.0–1.0, default 0.25)")
                .defineInRange("zombieDeathGravelChance", 0.25, 0.0, 1.0);
        skeletonDeathGravelChance = builder
                .comment("Chance a Skeleton drops gravel on death (0.0–1.0, default 0.10)")
                .defineInRange("skeletonDeathGravelChance", 0.10, 0.0, 1.0);
        huskDeathSandChance = builder
                .comment("Chance a Husk drops sand on death (0.0–1.0, default 0.50)")
                .defineInRange("huskDeathSandChance", 0.50, 0.0, 1.0);
        builder.pop();
    }
}
