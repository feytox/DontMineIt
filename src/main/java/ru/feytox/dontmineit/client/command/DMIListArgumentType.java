package ru.feytox.dontmineit.client.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class DMIListArgumentType implements IFeyArgumentType {
    public static DMIListArgumentType blocklists() {
        return new DMIListArgumentType();
    }

    private DMIListArgumentType() {}

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        new ArrayList<>(Arrays.asList("all", "onlySilkTouch", "onlyFortune")).forEach(builder::suggest);
        return builder.buildFuture();
    }
}
