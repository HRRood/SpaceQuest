package quest;

import static org.junit.Assert.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;

public class EndConditionTest {

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

                            g.setWormhole();

                            Tile old_wormhole_tile = g.wormhole.getTile();
                            g.wormhole.setTile(g.grid[1][2]);
                            g.grid[1][2].setObject(g.wormhole);
                            old_wormhole_tile.emptyTile();
                            g.user.handleKeyPressed(KeyCode.DOWN);
                            g.user.handleKeyPressed(KeyCode.DOWN);

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

        assertTrue(Game.game_won);

    }



}