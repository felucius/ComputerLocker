/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerlockscreenfx;

import Controller.ScreenLockerController;
import java.awt.AWTException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Maxime de Lange
 */
public class ComputerLockScreenFX extends Application {
    
    // Fields
    private ScreenLockerController screenLocker = null;
    
    @Override
    public void start(Stage primaryStage) throws AWTException {  
        screenLocker = new ScreenLockerController();
        screenLocker.startLockScreen();
        screenLocker.cancelScreenExit();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
