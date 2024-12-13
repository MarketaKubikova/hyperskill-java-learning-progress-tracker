package tracker.command;

public class UnknownCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Error: unknown command!");
    }
}
