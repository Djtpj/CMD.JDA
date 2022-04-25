package examples;

import me.djtpj.api.cmd.CommandManager;
import me.djtpj.api.cmd.ContainerCommand;
import me.djtpj.api.cmd.SubCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;

public class StackedSubCommandExample {
    public static void main(String[] args) throws LoginException {
        // Instantiate a JDA instance
        JDA jda = JDABuilder.createDefault("<bot token goes here>").build();

        // Instantiate a CommandManager instance
        CommandManager manager = new CommandManager();

        // Instantiate a ContainerCommand with a "!" delimiter and the trigger "trigger"
        ContainerCommand containerCommand = new ContainerCommand(manager, '!', "trigger");

        // Create a ChildContainerCommand that sends the message "This feature is the coolest!" with the trigger "sub"
        ContainerCommand subContainerCommand = new ContainerCommand(manager, "sub") {
            @Override
            public void onTrigger(String[] args, MessageReceivedEvent event) {
                sendMessage("This feature is the coolest!", event);
            }
        };

        // Create a SubCommand of that ChildContainerCommand that sends the message "This part is even cooler!" with the trigger "subsub"
        SubCommand finalCommand = new SubCommand(manager, "subsub") {
            @Override
            public void onTrigger(String[] args, MessageReceivedEvent event) {
                sendMessage("This part is even cooler!", event);
            }
        };

        manager.registerTopCommand(containerCommand);

        containerCommand.addSubCommand(subContainerCommand);

        subContainerCommand.addSubCommand(finalCommand);

        jda.addEventListener(manager);
    }
}
