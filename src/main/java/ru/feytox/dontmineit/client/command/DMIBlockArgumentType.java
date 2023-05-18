package ru.feytox.dontmineit.client.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import ru.feytox.dontmineit.client.config.ModConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static ru.feytox.dontmineit.client.command.DMICommand.parseInput;

public class DMIBlockArgumentType implements IFeyArgumentType {
    int listNameArgIndex;

    private DMIBlockArgumentType(int listNameArgIndex) {
        this.listNameArgIndex = listNameArgIndex;
    }

    public static DMIBlockArgumentType blockInList(int listNameArgIndex) {
        return new DMIBlockArgumentType(listNameArgIndex);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String listName = parseInput(context, listNameArgIndex);
        List<String> blockList = ModConfig.getListByArgument(listName);

        if (blockList != null) {
            blockList.forEach(builder::suggest);
            return builder.buildFuture();
        }

        return Suggestions.empty();
    }
}
