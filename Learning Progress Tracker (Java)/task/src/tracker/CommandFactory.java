package tracker;

import tracker.command.*;
import tracker.model.Course;
import tracker.model.Student;

import java.util.*;

public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final Command defaultCommand = new UnknownCommand();

    public CommandFactory(List<Student> students, List<Course> courses) {
        commands.put("exit", new ExitCommand());
        commands.put("back", new BackCommand());
        commands.put("add students", new AddStudentsCommand(students));
        commands.put("list", new ListCommand(students));
        commands.put("add points", new AddPointsCommand(students, courses));
        commands.put("find", new FindCommand(students));
        commands.put("statistics", new StatisticsCommand(students, courses));
        commands.put("notify", new NotifyCommand(students));
    }

    public Command getCommand(String input) {
        return commands.getOrDefault(input.toLowerCase(), defaultCommand);
    }
}
