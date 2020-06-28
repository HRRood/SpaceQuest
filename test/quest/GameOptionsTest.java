package quest;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import org.junit.Assert;
import org.junit.Test;

public class GameOptionsTest {

    @Test
    public void xCountEqualsGameOption() throws InterruptedException {
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

                            Assert.assertEquals(main.game_options.getXTileCount(), main.game.getGrid().length);

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
    public void yCountEqualsGameOption() throws InterruptedException {
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

                            Assert.assertEquals(main.game_options.getYTileCount(), main.game.getGrid()[0].length);

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
    public void planetCountEqualsGameOption() throws InterruptedException {
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

                            Assert.assertEquals(main.game_options.getPlanetCount(), main.game.getPlanets().length);

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
    public void cometCountEqualsGameOption() throws InterruptedException {
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

                            Assert.assertEquals(main.game_options.getCometCount(), main.game.getComets().length);

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
