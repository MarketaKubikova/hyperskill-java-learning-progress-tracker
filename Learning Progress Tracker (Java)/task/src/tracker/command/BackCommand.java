package tracker.command;

public class BackCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Enter 'exit' to exit the program");
    }
}
