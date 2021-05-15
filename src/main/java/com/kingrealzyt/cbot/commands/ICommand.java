package com.kingrealzyt.cbot.commands;

import java.util.Arrays;
import java.util.List;

public interface ICommand {

    void handle(CommandContext event);

    String getName();

    default List<String> getAliases() {
        return Arrays.asList();
    }

}
