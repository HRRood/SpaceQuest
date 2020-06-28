package quest;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovementTest {
    @Test
    public void moveDownTest() throws InterruptedException {
        final int[] pos_x = new int[1];
        final int[] pos_y = new int[1];
        Thread thread = new Thread(() -> {
            new JFXPanel(); // Initializes the JavaFx Platform
            Platform.runLater(new Runnable() {

                Main m;
                @Override
                public void run() {
                    try {
                        m = new Main();
                        m.start(new Stage());
                        m.gotoGame();
                        m.game.user.handleKeyPressed(KeyCode.DOWN);
                        pos_x[0] = m.game.user.getTile().getPosition_x();
                        pos_y[0] = m.game.user.getTile().getPosition_y();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
        assertEquals(pos_x[0], 0);
        assertEquals(pos_y[0], 1);
    }

    @Test
    public void moveLeftTest() throws InterruptedException {
        final int[] pos_x = new int[1];
        final int[] pos_y = new int[1];
        Thread thread = new Thread(() -> {
            new JFXPanel(); // Initializes the JavaFx Platform
            Platform.runLater(new Runnable() {

                Main m;
                @Override
                public void run() {
                    try {
                        m = new Main();
                        m.start(new Stage());
                        m.gotoGame();
                        m.game.user.handleKeyPressed(KeyCode.LEFT);
                        pos_x[0] = m.game.user.getTile().getPosition_x();
                        pos_y[0] = m.game.user.getTile().getPosition_y();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
        assertEquals(pos_x[0], 0);
        assertEquals(pos_y[0], 0);
    }

    @Test
    public void moveUpTest() throws InterruptedException {
        final int[] pos_x = new int[1];
        final int[] pos_y = new int[1];
        Thread thread = new Thread(() -> {
            new JFXPanel(); // Initializes the JavaFx Platform
            Platform.runLater(new Runnable() {

                Main m;
                @Override
                public void run() {
                    try {
                        m = new Main();
                        m.start(new Stage());
                        m.gotoGame();
                        m.game.user.handleKeyPressed(KeyCode.UP);
                        pos_x[0] = m.game.user.getTile().getPosition_x();
                        pos_y[0] = m.game.user.getTile().getPosition_y();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
        assertEquals(pos_x[0], 0);
        assertEquals(pos_y[0], 0);
    }

    @Test
    public void moveRightTest() throws InterruptedException {
        final int[] pos_x = new int[1];
        final int[] pos_y = new int[1];
        Thread thread = new Thread(() -> {
            new JFXPanel(); // Initializes the JavaFx Platform
            Platform.runLater(new Runnable() {

                Main m;
                @Override
                public void run() {
                    try {
                        m = new Main();
                        m.start(new Stage());
                        m.gotoGame();
                        m.game.user.handleKeyPressed(KeyCode.RIGHT);
                        pos_x[0] = m.game.user.getTile().getPosition_x();
                        pos_y[0] = m.game.user.getTile().getPosition_y();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
//         will be killed before you can tell.
        assertEquals(pos_x[0], 1);
        assertEquals(pos_y[0], 0);
    }

}