package com.infrastructuresickos.mob_reactions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

/**
 * On-hit: Spider → cobweb, Drowned → water source, Husk → sand (configurable chance).
 * On-death: Zombie → gravel (25%), Skeleton → gravel (10%), Husk → sand (50%).
 * Registered manually on the Forge bus — no @Mod.EventBusSubscriber.
 */
public class MobReactionsEventHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;

        BlockPos pos = entity.blockPosition();

        if (entity instanceof Spider) {
            placeIfAir(level, pos, Blocks.COBWEB.defaultBlockState());
        } else if (entity instanceof Drowned) {
            placeIfAir(level, pos, Blocks.WATER.defaultBlockState());
        } else if (entity instanceof Husk) {
            if (RANDOM.nextDouble() < MRConfig.INSTANCE.huskHitSandChance.get()) {
                placeIfAir(level, pos, Blocks.SAND.defaultBlockState());
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;

        double roll = RANDOM.nextDouble();

        if (entity instanceof Husk) {
            if (roll < MRConfig.INSTANCE.huskDeathSandChance.get()) {
                dropBlock(level, entity, new ItemStack(Items.SAND));
            }
        } else if (entity instanceof Zombie) {
            // Zombie check must come after Husk (Husk extends Zombie)
            if (roll < MRConfig.INSTANCE.zombieDeathGravelChance.get()) {
                dropBlock(level, entity, new ItemStack(Items.GRAVEL));
            }
        } else if (entity instanceof Skeleton) {
            if (roll < MRConfig.INSTANCE.skeletonDeathGravelChance.get()) {
                dropBlock(level, entity, new ItemStack(Items.GRAVEL));
            }
        }
    }

    private void placeIfAir(Level level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).isAir()) {
            level.setBlockAndUpdate(pos, state);
        }
    }

    private void dropBlock(Level level, LivingEntity entity, ItemStack stack) {
        ItemEntity item = new ItemEntity(level,
                entity.getX(), entity.getY(), entity.getZ(), stack);
        level.addFreshEntity(item);
    }
}
