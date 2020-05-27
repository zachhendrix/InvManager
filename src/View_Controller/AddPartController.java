/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;
import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Z
 */
public class AddPartController implements Initializable 
{
    Stage stage;
    Parent scene;
    private boolean isOutsourced;

    
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private RadioButton inhouseRadioButton;
    @FXML
    private ToggleGroup partOrigin;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField inventoryTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField originTextField;
    @FXML
    private Label originLabel;
    @FXML
    private Label partIDLabel;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        int partID = Inventory.getPartIDCount() + 1;
        partIDLabel.setText(String.valueOf(partID));
    }    

    //Sends user back to the Main Menu Screen.
    @FXML
    private void cancelClicked(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Entered information will be deleted & User will be sent back to the Main Menu");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } 
        
        else 
        {

        }

    }

    @FXML
    private void saveClicked(ActionEvent event) throws IOException 
    {
        
        int partID = Integer.parseInt(partIDLabel.getText());
        String partName = nameTextField.getText();
        double partPrice = Double.parseDouble(priceTextField.getText());
        int partStock = Integer.parseInt(inventoryTextField.getText());
        int partMin = Integer.parseInt(minTextField.getText());
        int partMax = Integer.parseInt(maxTextField.getText());
        
        if(isOutsourced == false)
        {
            int machineID = Integer.parseInt(originTextField.getText());
            Inventory.addPart(new InHouse(partID, partName,partPrice,partStock,partMin,partMax,machineID));
        }
        
        else
        {
            String companyName = originTextField.getText();
            Inventory.addPart(new Outsourced(partID, partName, partPrice, partStock, partMin, partMax, companyName));
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
         
    }

    @FXML
    private void outsourcedRadio(ActionEvent event) 
    {
        originLabel.setText("Company Name");
        isOutsourced = true;
        outsourcedRadioButton.setSelected(true);
    }

    @FXML
    private void inhouseRadio(ActionEvent event) 
    {
         originLabel.setText("Machine ID");
         isOutsourced = false;
         inhouseRadioButton.setSelected(true);
    }
    
}
