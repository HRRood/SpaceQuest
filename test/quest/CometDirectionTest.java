package quest;

import static org.junit.Assert.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;

public class CometDirectionTest {

    boolean should = false;
    boolean shouldNot = false;

    @Test
    public void CometDirectionTest() throws InterruptedException {
        final boolean[] available_pos = {false};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    Main m;

                    @Override
                    public void run() {
                        try {
                            m = new Main();
                            m.start(new Stage());
                            m.gotoGame();
                            m.game.timer.cancel();
                            m.game.timer.purge();

                            Tile prev_tile = m.game.comets[0].getTile();
                            m.game.grid[0][5].emptyTile();
                            m.game.comets[0].setTile(m.game.grid[0][5]);
                            m.game.grid[0][5].setObject(m.game.comets[0]);
                            prev_tile.emptyTile();


                            m.game.comets[0].newPos = 1;
                            shouldNot = m.game.comets[0].checkDirectionAvailable(m.game.comets[0].newPos);

                            m.game.comets[0].update();


                            if (m.game.comets[0].newPos != 1) {
                                should = !m.game.comets[0].checkDirectionAvailable(m.game.comets[0].newPos);
                            } else {
                                m.game.comets[0].update();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
        assertTrue(should);
    }

    @Test
    public void isNotAvailable() {
        assertFalse(shouldNot);
    }
}