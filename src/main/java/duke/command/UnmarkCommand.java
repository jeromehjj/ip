package duke.command;

import duke.exception.DukeException;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;

/**
 * The UnmarkCommand class executes the unmark command.
 */
public class UnmarkCommand extends Command {
    /** The input given by the user. */
    private String input;

    /**
     * Instantiates the UnmarkCommand object.
     *
     * @param input The input given by the user.
     */
    public UnmarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws DukeException {
        String taskNumber = input.substring(7);
        int number = Integer.parseInt(taskNumber);
        assert number > 0 : "index must be > 0";
        if (number <= 0 || number > taskList.size()) {
            throw new DukeException("OOPS!!! Sorry, I can't mark this as"
                    + " undone if it does not exist\n");
        }

        Task task = taskList.get(number - 1);
        task.markAsUndone();
        this.response = "OK, I've marked this task as not done yet:\n" + task.toString() + "\n";
    }
}
