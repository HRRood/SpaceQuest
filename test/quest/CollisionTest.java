package quest;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CollisionTest {
    @Test
    public void planetVisitCollisionTest() throws InterruptedException {
        final boolean[] planet_is_visited = {false};
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
                        Tile prev_tile = m.game.planets[0].getTile();
                        m.game.grid[1][0].emptyTile();
                        m.game.planets[0].setTile(m.game.grid[1][0]);
                        m.game.grid[1][0].setObject(m.game.planets[0]);
                        prev_tile.emptyTile();
                        m.game.update();
                        m.game.user.handleKeyPressed(KeyCode.RIGHT);
                        m.game.update();
                        m.game.timer.cancel();
                        m.game.timer.purge();
                        planet_is_visited[0] = m.game.planets[0].isVisited();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
        assertTrue(planet_is_visited[0]);
    }

    @Test
    public void cometCollisionTest() throws InterruptedException {
        final boolean[] comet_hit = {false};
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
                        m.game.timer.cancel();
                        m.game.timer.purge();
                        Tile prev_tile = m.game.comets[0].getTile();
                        m.game.grid[1][0].emptyTile();
                        m.game.comets[0].setTile(m.game.grid[1][0]);
                        m.game.grid[1][0].setObject(m.game.comets[0]);
                        prev_tile.emptyTile();
                        m.game.update();
                        m.game.user.handleKeyPressed(KeyCode.RIGHT);
                        comet_hit[0] = Game.game_over;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app, with out this, the thread
        // will be killed before you can tell.
        assertTrue(comet_hit[0]);
    }

}