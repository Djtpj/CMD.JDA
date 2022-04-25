package examples;

import me.djtpj.api.cmd.CommandManager;
import me.djtpj.api.cmd.ContainerCommand;
import me.djtpj.api.cmd.SubCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;

public class SubCommandExample {

    public static void main(String[] args) throws LoginException {
        // Instantiate a JDA instance
        JDA jda = JDABuilder.createDefault("<bot token goes here>").build();

        // Instantiate a CommandManager instance
        CommandManager manager = new CommandManager();

        // Instantiate a ContainerCommand with a  "!" delimiter and the trigger "trigger"
        ContainerCommand containerCommand = new ContainerCommand(manager, '!', "trigger");

        // Create a SubCommand that sends a message with the trigger "sub"
        SubCommand subCommand = new SubCommand(manager, "sub") {
            @Override
            public void onTrigger(String[] args, MessageReceivedEvent event) {
                sendMessage("Sub Command Example!", event);
            }
        };

        // Set usages of the subCommand (This is optional)
        subCommand.setUsage("An example command to demonstrate how to utilize SubCommands and ContainerCommands");

        // Register the subCommand to the containerCommand
        containerCommand.addSubCommand(subCommand);

        // Register the containerCommand to the CommandManager
        manager.registerTopCommand(containerCommand);

        // Register the commandManager as an eventListener to the JDA instance
        jda.addEventListener(manager);
    }

}
