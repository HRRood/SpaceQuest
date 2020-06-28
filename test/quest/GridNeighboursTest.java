package quest;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import org.junit.Assert;
import org.junit.Test;

public class GridNeighboursTest {

    @Test
    public void leftNeighbourValid() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.start(new Stage());
                            main.gotoGame();

                            Tile tile = main.game.getGrid()[1][1].getNeighbours().get(1);

                            Assert.assertEquals(0, tile.getPosition_x());
                            Assert.assertEquals(1, tile.getPosition_y());

                            main.stop();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
        Thread.sleep(500);
    }

    @Test
    public void rightNeighbourValid() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.start(new Stage());
                            main.gotoGame();

                            Tile tile = main.game.getGrid()[1][1].getNeighbours().get(2);

                            Assert.assertEquals(2, tile.getPosition_x());
                            Assert.assertEquals(1, tile.getPosition_y());

                            main.stop();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
        Thread.sleep(500);
    }

    @Test
    public void bottomNeighbourValid() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.start(new Stage());
                            main.gotoGame();

                            Tile tile = main.game.getGrid()[1][1].getNeighbours().get(3);

                            Assert.assertEquals(1, tile.getPosition_x());
                            Assert.assertEquals(2, tile.getPosition_y());

                            main.stop();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
        Thread.sleep(500);
    }

    @Test
    public void upperNeighbourValid() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.start(new Stage());
                            main.gotoGame();

                            Tile tile = main.game.getGrid()[1][1].getNeighbours().get(0);

                            Assert.assertEquals(1, tile.getPosition_x());
                            Assert.assertEquals(0, tile.getPosition_y());

                            main.stop();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
        Thread.sleep(500);
    }

}
