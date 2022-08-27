package duke;
import java.util.Scanner;

/**
 * The Parser class deals with making sense of the user command.
 */
public class Parser {
    /** A user interface to interact with the user */
    private Ui ui;
    /** The command input by the user */
    private String command;
    private static final String LINE = "____________________________________________________________\n";

    /**
     * Instantiates the Parser object to deal with user input.
     *
     * @param command The command input by the user.
     * @param ui
     */
    public Parser(String command, Ui ui) {
        this.command = command;
        this.ui = ui;
    }

    /**
     * Reads the input given by the user and determines the commands to be carried out.
     *
     * @param taskList A collection of tasks.
     * @return false if command closes the Parser, true otherwise.
     */
    public boolean readCommand(TaskList taskList) {
        Scanner sc = new Scanner(command);
        String input = sc.nextLine();
        String[] inputArray = input.split(" ");
        String first = inputArray[0];

        try {
            if (input.equals("bye")) {
                return false;
            } else if (input.equals("list")) {
                listTasks(taskList);
            } else if (first.equals("unmark")) {
                unmarkTask(input, taskList);
            } else if (first.equals("mark")) {
                markTask(input, taskList);
            } else if (first.equals("todo")) {
                addTask(input, taskList);
            } else if (first.equals("deadline")) {
                addTask(input, taskList);
            } else if (first.equals("event")) {
                addTask(input, taskList);
            } else if (first.equals("delete")) {
                deleteTask(input, taskList);
            } else {
                throw new DukeException(LINE + "☹ OOPS!!! I'm sorry, but I don't know what that means :(\n"
                        + LINE);
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(LINE + "☹ OOPS!!! Please make sure you include the proper index.\n" + LINE);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(LINE + "☹ OOPS!!! Please make sure you include the proper index.\n" + LINE);
        }
        return true;
    }

    /**
     * Returns the lists of tasks onto the console.
     *
     * @param taskList A collection of Task objects.
     */
    public static void listTasks(TaskList taskList) {
        int counter = 1;
        System.out.println(LINE + "Here are the tasks in your list:");

        for (Task t : taskList.getTaskList()) {
            System.out.println(counter + "." + t.toString());
            counter++;
        }

        System.out.println(LINE);
    }

    /**
     * Unmarks the task given by the input user command.
     *
     * @param input The input command given by the user.
     * @param taskList A collection of Task objects.
     * @throws DukeException If input given is invalid.
     */
    public static void unmarkTask(String input, TaskList taskList) throws DukeException {
        String taskNumber = input.substring(7);
        int number = Integer.parseInt(taskNumber);

        if (number <= 0 || number > taskList.size()) {
            throw new DukeException(LINE + "☹ OOPS!!! Sorry, I can't mark this as"
                    + " undone if it does not exist\n" + LINE);
        }

        Task task = taskList.get(number - 1);
        task.markAsUndone();
        System.out.println(LINE + "OK, I've marked this task as not done yet:");
        System.out.println(task + "\n" + LINE);
    }

    /**
     * Marks the task given by the input user command.
     *
     * @param input The input command given by the user.
     * @param taskList A collection of Task objects.
     * @throws DukeException If input given is invalid.
     */
    public static void markTask(String input, TaskList taskList) throws DukeException {
        String taskNumber = input.substring(5);
        int number = Integer.parseInt(taskNumber);

        if (number <= 0 || number > taskList.size()) {
            throw new DukeException(LINE + "☹ OOPS!!! Sorry,"
                    + " I can't mark this as done if it does not exist :(\n" + LINE);
        }

        Task task = taskList.get(number - 1);
        task.markAsDone();
        System.out.println(LINE + "Nice! I've marked this task as done:");
        System.out.println(task + "\n" + LINE);
    }

    /**
     * Adds the task given by the input user command into the list.
     *
     * @param input The input command given by the user.
     * @param taskList A collection of Task objects.
     * @throws DukeException If input given is invalid.
     */
    public static void addTask(String input, TaskList taskList) throws DukeException {
        String[] taskArray = input.split(" ", 2);
        String task = taskArray[0];

        switch(task) {
        case "todo":
            if (taskArray[1].isEmpty()) {
                throw new DukeException(LINE + "☹ OOPS!!! The description of a todo cannot be empty.\n"
                        + LINE);
            } else {
                Task todo = new ToDo(taskArray[1]);
                int index = taskList.size() + 1;
                taskList.add(todo);
                System.out.println(LINE + "Got it. I've added this task:\n" + todo);
                System.out.println("Now you have " + index + " tasks in this list.\n" + LINE);
                break;
            }
        case "deadline":
            String[] deadlineArray = input.split("deadline", 2);
            String taskBy = deadlineArray[1];
            String[] taskDeadline = taskBy.split("/by", 2);

            if (deadlineArray[1].isEmpty()) {
                throw new DukeException(LINE + "☹ OOPS!!! The description of a deadline cannot be empty.\n"
                        + LINE);
            }

            if (taskDeadline.length < 2 || taskDeadline[1].isEmpty()) {
                throw new DukeException(LINE + "☹ OOPS!!! Please include a /by for your deadline. "
                        + "E.g /by 2019-10-15T10:15:00.\n" + LINE);
            } else {
                Deadline d = new Deadline(taskDeadline[0].substring(1), taskDeadline[1]);
                int index = taskList.size() + 1;
                taskList.add(d);
                System.out.println(LINE + "Got it. I've added this task:\n" + d);
                System.out.println("Now you have " + index + " tasks in this list.\n" + LINE);
                break;
            }
        case "event":
            String[] eventArray = input.split("event", 2);
            String taskAt = eventArray[1];
            String[] taskEvent = taskAt.split("/at", 2);

            if (eventArray[1].isEmpty()) {
                throw new DukeException(LINE + "☹ OOPS!!! The description of a event cannot be empty.\n" + LINE);
            }

            if (taskEvent.length < 2 || taskEvent[1].isEmpty()) {
                throw new DukeException(LINE + "☹ OOPS!!! Please include a /at for your deadline. "
                        + "E.g /at 2019-10-15T10:15:00.\n" + LINE);
            } else {
                Event e = new Event(taskEvent[0].substring(1), taskEvent[1]);
                int index = taskList.size() + 1;
                taskList.add(e);
                System.out.println(LINE + "Got it. I've added this task:\n" + e);
                System.out.println("Now you have " + index + " tasks in this list.\n" + LINE);
                break;
            }
        default:
            throw new DukeException("Sorry, I cannot add this type of task into the list!");
        }
    }

    /**
     * Deletes the task given by the input user command.
     *
     * @param input The input command given by the user.
     * @param taskList A collection of Task objects.
     * @throws DukeException If input given is invalid.
     */
    public static void deleteTask(String input, TaskList taskList) throws DukeException {
        String deletionIndex = input.substring(7);
        int index = Integer.parseInt(deletionIndex);

        if (index <= 0 || index > taskList.size()) {
            throw new DukeException(LINE + "☹ OOPS!!! I can't remove this if it does not exist\n"
                    + LINE);
        }

        Task task = taskList.remove(index - 1);
        System.out.println(LINE + "Noted. I've removed this task:");
        System.out.println(task.toString() + "\n" + "Now you have " + taskList.size()
                + " tasks in the list.\n" + LINE);
    }
}
