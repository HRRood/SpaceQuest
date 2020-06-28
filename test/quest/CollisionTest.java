package quest;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionTest {
    @Test
    public void planetVisitCollisionTest() throws InterruptedException {
        final boolean[] planet_is_visited = {false};
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
                            m.game_scene = new Scene(m.createGame());
                            m.addHandlers();
                            m.stage.setScene(m.game_scene);
                            Tile prev_tile = m.planets[0].getTile();
                            m.planets[0].setTile(m.grid[1][0]);
                            m.grid[1][0].setObject(m.planets[0]);
                            prev_tile.emptyTile();
                            m.updateGame();
                            m.user.handleKeyPressed(KeyCode.RIGHT);
                            planet_is_visited[0] = m.planets[0].isVisited();
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
        assertTrue(planet_is_visited[0]);
    }

    @Test
    public void cometCollisionTest() throws InterruptedException {
        final boolean[] comet_hit = {false};
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
                            m.game_scene = new Scene(m.createGame());
                            m.addHandlers();
                            m.stage.setScene(m.game_scene);
                            Tile prev_tile = m.comets[0].getTile();
                            m.comets[0].setTile(m.grid[1][0]);
                            m.grid[1][0].setObject(m.comets[0]);
                            prev_tile.emptyTile();
                            m.updateGame();
                            m.user.handleKeyPressed(KeyCode.RIGHT);
                            m.updateGame();
                            comet_hit[0] = Main.game_over;
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
        assertTrue(comet_hit[0]);
    }

}