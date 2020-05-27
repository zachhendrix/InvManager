/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Z
 */
public class AddProductController implements Initializable 
{
    Stage stage;
    Parent scene;
    private ObservableList<Part> addParts = FXCollections.observableArrayList();
    
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
    private TextField searchTextField;

    @FXML
    private Label productIDLabel;

    @FXML
    private Button searchButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;
    
    @FXML
    private TableView<Part> addPartTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIDCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> addPartPriceCol;
    
    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Double> associatedPriceCol;

    @FXML
    private TableColumn<Part, Integer> associatedInventoryCol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        int productID = Inventory.getProductIDCount() + 1;
        productIDLabel.setText(String.valueOf(productID));
        
        addPartTableView.setItems(Inventory.getAllParts());
        
        addPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));  
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice")); 
        addPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock")); 
        
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));  
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice")); 
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock")); 
        
        
        
        associatedPartTableView.setItems(addParts);
        
    }    
    
    //Adds part to the associated part table.
    @FXML
    void addClicked(ActionEvent event) 
    {
        //Gets selected part from GUI table.
        Part part = addPartTableView.getSelectionModel().getSelectedItem();
        
        //Adds the selected part to the 
        addParts.add(part);
        System.out.println("Part Added");

       
    }

    //Sends the user back to the Main Menu screen.
    @FXML
    void cancelClicked(ActionEvent event) throws IOException 
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

    //Deletes part from the associated part table.
    @FXML
    void deleteClicked(ActionEvent event) 
    {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Part associated with product will be deleted");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
            addParts.remove(part);
        } 
        
        else 
        {

        }

    }

    //Adds the product to the product table
    @FXML
    void saveClicked(ActionEvent event) throws IOException 
    {
        //Makes sure the product has at least one part
        if(addParts.size() > 0)
        {
            int productID = Integer.parseInt(productIDLabel.getText());
            String productName = nameTextField.getText();
            double productPrice = Double.parseDouble(priceTextField.getText());
            int productStock = Integer.parseInt(inventoryTextField.getText());
            int productMin = Integer.parseInt(minTextField.getText());
            int productMax = Integer.parseInt(maxTextField.getText());
        
            Product product = new Product(productID, productName, productPrice, productStock, productMin, productMax);
            product.getAllAssociatedParts().addAll(addParts);
            Inventory.addProduct(product);
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning!");
            alert.setContentText("Product must have at least 1 part!");

            alert.showAndWait();
        }

    }

    @FXML
    void searchClicked(ActionEvent event) 
    {
        String searchString = searchTextField.getText();
        ObservableList searchedPartList = Inventory.lookupPart(searchString);
        addPartTableView.setItems(searchedPartList);

    }
    
    

    
}
