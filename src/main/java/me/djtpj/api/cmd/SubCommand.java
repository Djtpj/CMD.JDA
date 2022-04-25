package me.djtpj.api.cmd;

public abstract class SubCommand extends Command{

    protected String usage;

    public SubCommand(CommandManager commandManager, String... triggers) {
        super(commandManager, triggers);
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
