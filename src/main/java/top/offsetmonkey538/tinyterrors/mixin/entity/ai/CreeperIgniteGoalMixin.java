package top.offsetmonkey538.tinyterrors.mixin.entity.ai;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static top.offsetmonkey538.tinyterrors.TinyTerrors.config;

@Mixin(CreeperIgniteGoal.class)
public abstract class CreeperIgniteGoalMixin extends Goal {

    @Shadow
    @Final
    private CreeperEntity creeper;

    @ModifyExpressionValue(
            method = "canStart",
            at = @At(
                    value = "CONSTANT",
                    args = "doubleValue=9.0"
            )
    )
    private double tiny_terrors$changeBabyCreeperIgniteRadius(double constant) {
        if (this.creeper.isBaby()) constant = constant * config.get().creeperConfig.igniteRadiusMultiplier;
        return constant;
    }
}
