package me.djtpj.api.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class CommandManager extends ListenerAdapter {
   
    private ArrayList<Command> commands;    

    public CommandManager() {
        commands = new ArrayList<>();
    }

    public void registerTopCommand(Command newCommand) {
        commands.add(newCommand);
        if (!newCommand.getChildCommands().isEmpty()) {
            for (Command c : newCommand.getChildCommands()) {
                registerTopCommand(c);
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Run through each command and check if the text matches and send the command through

        if (event.getAuthor().isBot()) {
            return;
        }

        String[] parsed = CommandParser.parseArgs(event.getMessage().getContentRaw());

        for (Command c : commands) {
            if (c.getIndex() == 0) {
                c.run(parsed, event);
            }
        }

    }
}
