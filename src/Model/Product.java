package Model;

import javafx.collections.*;



public class Product 
{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;  
    private String productName;
    private double productPrice = 0.0;
    private int productStock;
    private int productMin;
    private int productMax;

    
    public Product(int productID, String productName, double productPrice, int productStock, int productMin, int productMax)
    {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productMin = productMin;
        this.productMax = productMax;
        
    }


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }
    

    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }
    
    public boolean deleteAssociatedPart(Part selectASPart)
    {
        return associatedParts.remove(selectASPart);
    }
    
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
    
}
    
