package me.djtpj.api.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContainerCommand extends Command {

    protected Character delimiter;

    public ContainerCommand(CommandManager commandManager, String... triggers) {
        super(commandManager, triggers);
    }

    public ContainerCommand(CommandManager commandManager, Character delimiter, String... triggers) {
        super(commandManager, triggers);
        this.delimiter = delimiter;

        delimitTriggers();
    }

    @Override
    public void onTrigger(String[] args, MessageReceivedEvent event) {
        // print usages of sub commands

        for (Command c : childCommands) {
            if (c instanceof SubCommand sc) {
                if (sc.getUsage() != null && !sc.getUsage().isEmpty()) {
                    sendMessage(sc.getUsage(), event);
                }
            }
        }

    }

    private void delimitTriggers() {
        Arrays.asList(triggers).replaceAll(s -> delimiter + s);

    }

    public Character getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(Character delimiter) {
        this.delimiter = delimiter;
        delimitTriggers();
    }
}
