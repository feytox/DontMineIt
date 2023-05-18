package ru.feytox.dontmineit.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Config(name = "dontmineit")
@Config.Gui.Background("minecraft:textures/block/oak_planks.png")
public class ModConfig implements ConfigData {
    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    @Nullable
    public static List<String> getListByArgument(String listName) {
        ModConfig config = get();

        List<String> result = null;
        switch (listName) {
            case "all" -> result = config.allBlockList;
            case "onlySilkTouch" -> result = config.onlySilkList;
            case "onlyFortune" -> result = config.onlyFortuneList;
        }

        return result;
    }

    public static void save() {
        AutoConfig.getConfigHolder(ModConfig.class).save();
    }


    @ConfigEntry.Gui.PrefixText
    public boolean toggleMod = true;

    public boolean enableParticles = false;

    public boolean toggleAll = true;
    public boolean toggleOnlySilk = true;
    public boolean toggleOnlyFortune = true;

    @ConfigEntry.Gui.PrefixText
    public List<String> allBlockList = new ArrayList<>();
    public List<String> onlySilkList = new ArrayList<>();
    public List<String> onlyFortuneList = new ArrayList<>();
}
