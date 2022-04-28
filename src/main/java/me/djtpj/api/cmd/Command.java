package me.djtpj.api.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Command {

    protected Command parentCommand;
    protected ArrayList<Command> childCommands;

    protected String[] triggers;

    protected CommandManager commandManager;

    protected int index = 0;

    public Command(CommandManager commandManager, String... triggers) {
        childCommands = new ArrayList<>();

        this.commandManager = commandManager;
        this.triggers = triggers;

        Arrays.asList(triggers).replaceAll(s -> s = s.toLowerCase());
    }

    public final void run(String[] args, MessageReceivedEvent event) {
        validateRecursively(args, event);
    }

    public void addSubCommand(Command subCommand) {
        childCommands.add(subCommand);
        subCommand.setParentCommand(this);
    }

    private void validate(String[] args, MessageReceivedEvent event) {
        if (!Arrays.asList(args).isEmpty()) {
            if (Arrays.asList(triggers).contains(args[index].toLowerCase())) {
                onTrigger(CommandParser.trimToIndex(getIndex() + 1, args), event);
            }
        }
    }

    public void validateRecursively(String[] args, MessageReceivedEvent event) {

        boolean sent = false;
        if (!childCommands.isEmpty()) {
            for (Command c : childCommands) {
                String[] trimmed = CommandParser.trimToIndex(c.getIndex(), args);
                if (!Arrays.asList(trimmed).isEmpty()) {
                    if (Arrays.asList(c.getTriggers()).contains(trimmed[0].toLowerCase())) {
                        c.validateRecursively(args, event);
                        sent = true;
                        break;
                    }
                }
            }
        }

        if (!sent) {
            if (args.length >= 1) {
//                String[] trimmed = CommandParser.trimToIndex(getIndex(), args);
                validate(args, event);
            }
        }
    }

    public void sendMessage(String message, MessageReceivedEvent event) {
        event.getChannel().sendMessage(message).queue();
    }

    public abstract void onTrigger(String[] args, MessageReceivedEvent event);

    // Stupid Java boiler plate >:(

    public Command getParentCommand() {
        return parentCommand;
    }

    public void setParentCommand(Command parentCommand) {
        this.parentCommand = parentCommand;
        index = parentCommand.getIndex() + 1;
    }

    public ArrayList<Command> getChildCommands() {
        return childCommands;
    }

    public void setChildCommands(ArrayList<Command> childCommands) {
        this.childCommands = childCommands;
    }

    public String[] getTriggers() {
        return triggers;
    }

    public void setTriggers(String[] triggers) {
        this.triggers = triggers;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
