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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class MainMenuController implements Initializable 
{
    Stage stage;
    Parent scene;

    @FXML
    private Button searchPartButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button searchProductButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField searchPartTextField;
    @FXML
    private TextField searchProductTextField;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Integer> partStockCol;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productStockCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        partTableView.setItems(Inventory.getAllParts());
        
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));  
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice")); 
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));   
        
        
        productTableView.setItems(Inventory.getAllProducts());
        
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));  
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice")); 
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));  
        
        
  
    }    

    @FXML
    private void searchPartClicked(ActionEvent event)
    {
        String searchString = searchPartTextField.getText();
        ObservableList searchedPartList = Inventory.lookupPart(searchString);
        partTableView.setItems(searchedPartList);

    }
    
    @FXML
    private void searchProductClicked(ActionEvent event) 
    {
        String searchString = searchProductTextField.getText();
        ObservableList searchedProductList = Inventory.lookupProduct(searchString);
        productTableView.setItems(searchedProductList);
    }

    
    //Sends User to Add Part screen.
    @FXML
    private void addPartClicked(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddPart.fxml"));
        loader.load();
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
    //Sends User to Add Product screen.
    @FXML
    private void addProductClicked(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddProduct.fxml"));
        loader.load();
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //Sends data and user to the Modify Part screen.
    @FXML
    private void modifyPartClicked(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();
        
        ModifyPartController MPController = loader.getController();
        MPController.sendModData(partTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
    //Sends data and user to the Modify Product screen.
    @FXML
    private void modifyProductClicked(ActionEvent event) throws IOException 
    {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();
        
        ModifyProductController MPRController = loader.getController();
        MPRController.sendModData(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
       
    }

    //Deletes a part from the table.
    @FXML
    private void deletePartClicked(ActionEvent event) 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Selected Part Will Be Deleted");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            Part part = partTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(part);
        } 
        
        else 
        {

        }
        
    }


    //Deletes a product from the table 
    @FXML
    private void deleteProductClicked(ActionEvent event) 
    {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Selected Product Will Be Deleted");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            Product product = productTableView.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(product);
        } 
        
        else 
        {

        }
        
    }

    
    //Exits the Program
    @FXML
    private void exitClicked(ActionEvent event) 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("User will exit program");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            System.exit(0);
        } 
        
        else 
        {

        }
    }
    

    
}
