package net.gravity.puffs.entity.custom.puff;

import net.gravity.puffs.entity.goals.PuffFollowOwnerGoal;
import net.gravity.puffs.entity.goals.WildPuffPanicGoal;
import net.gravity.puffs.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Obsidianpuff extends Puff {
    
    public Obsidianpuff(EntityType<? extends Obsidianpuff> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.08F) // Slower than normal puffs (0.15F)
                .build();
    }

    @Override
    public ItemStack initializeShearItem() {
        return ModItems.OBSIDIANPUFF_ROOT.get().getDefaultInstance();
    }

    @Override
    public ItemStack initializeTameItem() {
        return new ItemStack(Items.OBSIDIAN);
    }

    @Override
    protected void registerGoals() {
        // Don't add FloatGoal - we want it to sink instead
        this.goalSelector.addGoal(1, new WildPuffPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new PuffGrowRootGoal(this));
        this.goalSelector.addGoal(3, new PuffFollowOwnerGoal(this, 1.55D, 0.5F, 0.5F, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater() || this.isInLava()) {
                // Make it sink in fluids instead of floating
                Vec3 vec3 = this.getDeltaMovement();
                this.moveRelative(0.02F, pTravelVector);
                this.move(net.minecraft.world.entity.MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8D));
                // Apply downward force to make it sink
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.05D, 0.0D));
                }
            } else {
                super.travel(pTravelVector);
            }
        } else {
            super.travel(pTravelVector);
        }
    }
}
