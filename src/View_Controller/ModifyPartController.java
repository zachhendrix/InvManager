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
public class ModifyPartController implements Initializable 
{
    Stage stage;
    Parent scene;
    private Part partRef;
    private boolean isOutsourced;

    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private RadioButton inhouseRadioButton;      
    @FXML
    private ToggleGroup partOrigin;
    @FXML
    private Label partIDLabel;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    }    

    //Sends user back to the Main Menu Screen.
    @FXML
    private void cancelClicked(ActionEvent event) throws IOException 
    {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Changes will be reset & User will be sent back to the Main Menu");
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

    //Takes all data entered and deletes the old part and adds a new one in its place.
    @FXML
    private void saveClicked(ActionEvent event) throws IOException 
    {
        //deletes part so that it can be replaced
        Inventory.deletePart(partRef);
        
        //sets data for the part based on the inputs from the textfields 
        int partID = Integer.parseInt(partIDLabel.getText());
        String partName = nameTextField.getText();
        double partPrice = Double.parseDouble(priceTextField.getText());
        int partStock = Integer.parseInt(inventoryTextField.getText());
        int partMin = Integer.parseInt(minTextField.getText());
        int partMax = Integer.parseInt(maxTextField.getText());
        
        //detects whether the part is going to be outsourced or not, then builds using the respective constructor.
        if(isOutsourced == false)
        {
            int machineID = Integer.parseInt(originTextField.getText());
            Inventory.addPart(new InHouse(partID, partName,partPrice,partStock,partMin,partMax,machineID));
        }
        
        else if (isOutsourced == true)
        {
            String companyName = originTextField.getText();
            Inventory.addPart(new Outsourced(partID, partName,partPrice,partStock,partMin,partMax,companyName));
        }
        
        //Loads the Main Menu screen.
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    
    @FXML
    private void outsourcedRadio(ActionEvent event) 
    {
        isOutsourced = true;
        originLabel.setText("Company Name");
        outsourcedRadioButton.setSelected(true);
    }

    @FXML
    private void inhouseRadio(ActionEvent event) 
    {
        isOutsourced = false;
        originLabel.setText("Machine ID");
        inhouseRadioButton.setSelected(true);
    }
    
    
    
    public void sendModData(Part part)
    {
        //Sets the sent data to a reference of the part
        partRef = part;
        
        //Fills the text boxes with the previous information of the part
        partIDLabel.setText(String.valueOf(part.getPartID()));
        nameTextField.setText(String.valueOf((part).getPartName()));
        inventoryTextField.setText(String.valueOf((part).getPartStock()));
        priceTextField.setText(String.valueOf((part).getPartPrice()));
        minTextField.setText(String.valueOf((part).getPartMin()));
        maxTextField.setText(String.valueOf((part).getPartMax()));
        
        //Detects whether or not the part is Inhouse or Outsorced and sets up the final part of the constructor appropriately when loaded.
        if (part instanceof Outsourced)
        {
            isOutsourced = true;
            outsourcedRadioButton.setSelected(true);
            originLabel.setText("Company Name");
            originTextField.setText((((Outsourced) part).getCompanyName()));

        }
        else if (part instanceof InHouse)
        {
            isOutsourced = false;
            inhouseRadioButton.setSelected(true);
            originLabel.setText("Machine ID");
            originTextField.setText(Integer.toString(((InHouse) part).getMachineID()));
            
        }
    }

    
}
