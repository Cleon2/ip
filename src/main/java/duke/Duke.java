package duke;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasks.DukeList;
import duke.ui.Ui;

/**
 * Represents the main application class for Duke.
 */
public class Duke {

    private DukeList itemList;
    private Storage storage;
    private Ui ui;

    /**
     * Constructs a Duke instance with the given file path.
     *
     * @param filePath The path to the data file.
     */
    public Duke(String filePath) {
        ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.itemList = new DukeList(this.storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            this.itemList = new DukeList();
        }
    }

    /**
     * The main method that initializes and runs the Duke application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }

    /**
     * Runs the Duke application.
     */
    public void run() {
        // Initializing Scanner
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(itemList, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
}



