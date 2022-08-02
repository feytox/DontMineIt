package name.uwu.feytox.dontmineit.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import name.uwu.feytox.dontmineit.client.command.DMICommand;
import name.uwu.feytox.dontmineit.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class DontMineItClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        DMICommand.init();
    }

    public static boolean checkBlockInList(BlockState blockState, List<String> blockList) {
        String blockId = Registry.ITEM.getId(blockState.getBlock().asItem()).toString();
        return blockList.contains(blockId);
    }
}
