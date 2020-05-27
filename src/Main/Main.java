package Main;


import Model.*;
import View_Controller.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application 
{
   
    public static void main(String[] args) 
    {
        Part a1 = new InHouse(1,"Part A1", 2.99, 10,5,100,101);
        Part a2 = new Outsourced(2,"Part A2", 5.99, 15,10,100,"Crunkco");
        Product b1 = new Product(1, "Product", 6.99, 3, 1, 10);
        
        Inventory.addPart(a1);
        Inventory.addPart(a2);
        Inventory.addProduct(b1);
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    

   
    
}
