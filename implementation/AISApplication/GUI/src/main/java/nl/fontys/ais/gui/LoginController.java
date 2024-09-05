/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package nl.fontys.ais.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * FXML Controller class
 *
 * @author Danylo
 */
public class LoginController implements Initializable {

    @FXML
    private Text noImplementation;

    /**
     * Initializes the controller class.
     */
    private final Supplier<SceneManager> sceneManagerSupplier;

    public LoginController(Supplier<SceneManager> sceneManagerSupplier) {
        this.sceneManagerSupplier = sceneManagerSupplier;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    //Changes the view to create flight fxml file
    @FXML
    private void salesofficerLogin(){
        this.sceneManagerSupplier.get().changeScene("searchFlight");
    }
    
    @FXML
    private void salesManager(){
        this.sceneManagerSupplier.get().changeScene("ManagementDashboard");
    }

    @FXML
    private void salesemployeeLogin(){
        this.sceneManagerSupplier.get().changeScene("searchFlightEmployeeView");
    }
    @FXML
    private void notImplemented()  {
        noImplementation.setText("Error not implemented:");
        noImplementation.setVisible(true);
    }

}


