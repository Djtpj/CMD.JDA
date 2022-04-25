package examples;

import me.djtpj.api.cmd.CommandManager;
import me.djtpj.api.cmd.ContainerCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;

public class SingleCommandExample {
    public static void main(String[] args) throws LoginException {
        // Instantiate a JDA instance
        JDA jda = JDABuilder.createDefault("<bot token goes here>").build();

        // Instantiate a CommandManager instance
        CommandManager manager = new CommandManager();

        // Create a ContainerCommand named exampleCommand that has a ! delimiter (make the triggers start with that sign) with the command trigger "trigger"
        ContainerCommand exampleCommand = new ContainerCommand(manager, '!', "trigger") {
            @Override
            public void onTrigger(String[] args, MessageReceivedEvent event) {
                // When the command is activated send a message in the channel that it was activated in that reads "Example message!"
                sendMessage("Example message!", event);
            }
        };

        // Register the container command to the CommandManager
        manager.registerTopCommand(exampleCommand);

        // Add the manager as an event listener
        jda.addEventListener(manager);
    }
}
