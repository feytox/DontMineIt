package ru.feytox.dontmineit.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import ru.feytox.dontmineit.client.command.DMICommand;
import ru.feytox.dontmineit.client.config.ModConfig;

import java.util.List;

@Environment(EnvType.CLIENT)
public class DontMineItClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        DMICommand.init();
    }

    public static boolean checkBlockInList(BlockState blockState, List<String> blockList) {
        String blockId = Registries.ITEM.getId(blockState.getBlock().asItem()).toString();
        return blockList.contains(blockId);
    }
}
