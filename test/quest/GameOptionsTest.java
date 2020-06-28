package quest;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.JUnit4;

public class GameOptionsTest {

    @Test
    public void xCountEqualsGameOption() throws InterruptedException {
        int game_x, game_options_x;

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
                            main.game_options

                            System.out.println("xCountEqualsGameOption");
                            System.out.println(GameOptionsTest.game_options.getXTileCount());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
        Thread.sleep(3000);

        Assert.assertTrue();
    }

    @Test
    public void yCountEqualsGameOption() throws InterruptedException {
        System.out.println("yCountEqualsGameOption");
        System.out.println(GameOptionsTest.game_options.getYTileCount());
    }

    @Test
    public void planetCountEqualsGameOption() throws InterruptedException {
        System.out.println("planetCountEqualsGameOption");
        System.out.println(GameOptionsTest.game_options.getPlanetCount());
    }

    @Test
    public void cometCountEqualsGameOption() throws InterruptedException {
        System.out.println("cometCountEqualsGameOption");
        System.out.println(GameOptionsTest.game_options.getCometCount());
    }

}
