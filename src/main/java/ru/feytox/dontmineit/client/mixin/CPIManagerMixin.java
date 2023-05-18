package ru.feytox.dontmineit.client.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.feytox.dontmineit.client.config.ModConfig;

import static ru.feytox.dontmineit.client.DontMineItClient.checkBlockInList;

@Mixin(ClientPlayerInteractionManager.class)
public class CPIManagerMixin {

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    public void injected(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        ModConfig config = ModConfig.get();
        if (client.world != null && client.player != null && config.toggleMod &&
                !((CPIMAccessor) this).getGameMode().isCreative()) {
            BlockState blockState = client.world.getBlockState(pos);
            ItemStack handItem = client.player.getMainHandStack();
            if (config.toggleAll && checkBlockInList(blockState, config.allBlockList)) {
                cir.setReturnValue(config.enableParticles);
            } else {
                String enchantments = handItem.getEnchantments().toString();
                if (config.toggleOnlyFortune && !enchantments.contains("fortune") && checkBlockInList(blockState, config.onlyFortuneList)) {
                    cir.setReturnValue(config.enableParticles);
                } else if (config.toggleOnlySilk && !enchantments.contains("silk_touch") && checkBlockInList(blockState, config.onlySilkList)) {
                    cir.setReturnValue(config.enableParticles);
                }
            }
        }
    }
}
