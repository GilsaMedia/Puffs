package net.gravity.puffs.entity.goals;

import net.gravity.puffs.entity.custom.puff.Puff;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class WildPuffPanicGoal extends PanicGoal {
    private final Puff puff;

    public WildPuffPanicGoal(Puff puff, double pSpeedModifier) {
        super(puff, pSpeedModifier);
        this.puff = puff;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canUse() {
        // Only panic if the puff is wild (not tamed)
        if (puff.isTame()) {
            return false;
        }
        return super.canUse();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        // Stop panicking if the puff becomes tamed
        if (puff.isTame()) {
            return false;
        }
        return super.canContinueToUse();
    }
}
