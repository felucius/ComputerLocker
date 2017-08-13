/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Maxime de Lange
 */
public class ScreenLockerController implements Initializable {

    // Fields
    private List<Stage> stages = null;
    private List<Screen> screens = null;
    private Stage stage = null;
    private Stage screen1 = null;
    private Stage screen2 = null;
    private Scene scene = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void startLockScreen() {
        try {
            stages = new ArrayList<Stage>();
            screen1 = new Stage();
            screen2 = new Stage();
            stages.add(screen1);
            stages.add(screen2);

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ScreenLocker.fxml"));
            scene = new Scene(root, 1400, 800);

            stages = checkForMultipleScreens(stages);

            if (screens.size() > 1) {
                stages.get(0).setTitle("Computer locker");
                // Makes the screen display always on top.
                stages.get(0).setAlwaysOnTop(true);
                // Removes the minimize, maximize and remove button.
                stages.get(0).initStyle(StageStyle.UNDECORATED);
                stages.get(0).setScene(scene);
                stages.get(0).show();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stages.get(1).setTitle("Computer locker");
                        // Makes the screen display always on top.
                        stages.get(1).setAlwaysOnTop(true);
                        // Removes the minimize, maximize and remove button.
                        stages.get(1).initStyle(StageStyle.UNDECORATED);
                        stages.get(1).setScene(scene);
                        stages.get(1).show();
                    }
                });
            } else {
                screen1.setTitle("Computer locker");
                // Makes the screen display always on top.
                screen1.setAlwaysOnTop(true);
                // Removes the minimize, maximize and remove button.
                screen1.initStyle(StageStyle.UNDECORATED);
                screen1.setScene(scene);
                screen1.show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void cancelScreenExit() {
        Platform.setImplicitExit(false);

        if (stages != null) {
            for (Stage stage : stages) {
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        event.consume();
                    }
                });

                scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
                    @Override
                    public void handle(javafx.scene.input.KeyEvent event) {
                        if (event.getCode().equals(KeyCode.F4)) {
                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    event.consume();
                                }
                            });
                        }
                    }
                });
            }
        } else {
            screen1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                }
            });

            scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
                @Override
                public void handle(javafx.scene.input.KeyEvent event) {
                    if (event.getCode().equals(KeyCode.F4)) {
                        screen1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                event.consume();
                            }
                        });
                    }
                }
            });

        }
    }

    public List<Stage> checkForMultipleScreens(List<Stage> stage) {
        screens = Screen.getScreens();
        if (screens.size() > 1) {
            Screen firstScreen = screens.get(0);
            Screen secondScreen = screens.get(1);
            Rectangle2D boundsSecondScreen = null;
            Rectangle2D boundsFirstScreen = null;

            boundsFirstScreen = firstScreen.getVisualBounds();
            stage.get(0).setX(boundsFirstScreen.getMinX());
            stage.get(0).setY(boundsFirstScreen.getMinY());
            stage.get(0).setWidth(boundsFirstScreen.getWidth());
            stage.get(0).setHeight(boundsFirstScreen.getHeight() + 100);

            boundsSecondScreen = secondScreen.getVisualBounds();
            stage.get(1).setX(boundsSecondScreen.getMinX());
            stage.get(1).setY(boundsSecondScreen.getMinY());
            stage.get(1).setWidth(boundsSecondScreen.getWidth());
            stage.get(1).setHeight(boundsSecondScreen.getHeight() + 100);

            return stage;
        } else {
            System.out.println("No extra screens connected.");
            return null;
        }
    }
}
