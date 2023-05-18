package ru.feytox.dontmineit.client.command;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.text.Text;
import ru.feytox.dontmineit.client.config.ModConfig;

import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class DMICommand {
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("dmi")
                .then(literal("addBlock")
                        .then(argument("list", DMIListArgumentType.blocklists())
                                .then(argument("block", BlockStateArgumentType.blockState(registryAccess))
                                        .executes(context -> {
                                            String listName = parseInput(context, 2);
                                            String blockName = parseInput(context, 3);

                                            if (blockName.contains("[")) {
                                                blockName = blockName.substring(0, blockName.indexOf("["));
                                            }
                                            if (blockName.contains("{")) {
                                                blockName = blockName.substring(0, blockName.indexOf("{"));
                                            }
                                            if (!blockName.contains(":")) {
                                                blockName = "minecraft:" + blockName;
                                            }

                                            ModConfig config = ModConfig.get();

                                            boolean result = false;
                                            switch (listName) {
                                                case "all" -> {
                                                    if (!config.allBlockList.contains(blockName)) {
                                                        config.allBlockList.add(blockName);
                                                        result = true;
                                                    }
                                                }
                                                case "onlySilkTouch" -> {
                                                    if (!config.onlySilkList.contains(blockName)) {
                                                        config.onlySilkList.add(blockName);
                                                        result = true;
                                                    }
                                                }
                                                case "onlyFortune" -> {
                                                    if (!config.onlyFortuneList.contains(blockName)) {
                                                        config.onlyFortuneList.add(blockName);
                                                        result = true;
                                                    }
                                                }
                                                default -> {
                                                    sendTranslatableText("dontmineit.addblock.nongroup");
                                                    return 1;
                                                }
                                            }

                                            if (result) {
                                                ModConfig.save();
                                                sendTranslatableText("dontmineit.addblock.success");
                                            } else {
                                                sendTranslatableText("dontmineit.addblock.fail");
                                            }

                                            return 1;
                                        }))))
                .then(literal("delBlock")
                        .then(argument("list", DMIListArgumentType.blocklists())
                                .then(argument("block", DMIBlockArgumentType.blockInList(2))
                                        .executes(context -> {
                                            String listName = parseInput(context, 2);
                                            String blockName = parseInput(context, 3);
                                            ModConfig config = ModConfig.get();

                                            boolean result = false;
                                            switch (listName) {
                                                case "all" -> {
                                                    if (config.allBlockList.contains(blockName)) {
                                                        config.allBlockList.remove(blockName);
                                                        result = true;
                                                    }
                                                }
                                                case "onlySilkTouch" -> {
                                                    if (config.onlySilkList.contains(blockName)) {
                                                        config.onlySilkList.remove(blockName);
                                                        result = true;
                                                    }
                                                }
                                                case "onlyFortune" -> {
                                                    if (config.onlyFortuneList.contains(blockName)) {
                                                        config.onlyFortuneList.remove(blockName);
                                                        result = true;
                                                    }
                                                }
                                                default -> {
                                                    sendTranslatableText("dontmineit.addblock.nongroup");
                                                    return 1;
                                                }
                                            }

                                            if (result) {
                                                ModConfig.save();
                                                sendTranslatableText("dontmineit.delblock.success");
                                            } else {
                                                sendTranslatableText("dontmineit.delblock.fail");
                                            }

                                            return 1;
                                        }))))
                .then(literal("list")
                        .then(argument("list", DMIListArgumentType.blocklists())
                                .executes(context -> {
                                    String listName = parseLast(context);

                                    List<String> blockList = ModConfig.getListByArgument(listName);

                                    if (blockList != null) {
                                        sendFormattedText("dontmineit.list." + listName, String.join(", ", blockList));
                                    } else {
                                        sendTranslatableText("dontmineit.addgroup.fail");
                                    }

                                    return 1;
                                })))));
    }

    public static <S> String parseLast(CommandContext<S> context) {
        String[] inputSplitted = context.getInput().split(" ");
        return inputSplitted[inputSplitted.length-1];
    }

    public static <S> String parseInput(CommandContext<S> context, int argIndex) {
        String[] inputSplitted = context.getInput().split(" ");
        return inputSplitted[argIndex];
    }

    private static void sendFormattedText(String key, Object formatObj) {
        sendMessage(Text.literal(I18n.translate(key, formatObj)));
    }

    private static void sendTranslatableText(String key) {
        sendMessage(Text.translatable(key));
    }

    private static void sendMessage(Text message) {
        MinecraftClient.getInstance().player.sendMessage(message, false);
    }
}
