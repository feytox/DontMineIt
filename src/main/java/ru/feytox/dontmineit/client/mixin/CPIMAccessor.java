package ru.feytox.dontmineit.client.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface CPIMAccessor {
    @Accessor
    GameMode getGameMode();
}
