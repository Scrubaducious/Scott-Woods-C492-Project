package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import sample.MainScreen;

import java.util.List;
import java.util.ListIterator;

public class Inventory
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId)
    {
        ListIterator<Part> partIterator = allParts.listIterator();
        Part searchResult;
        while (partIterator.hasNext())
        {
            Part currPart = partIterator.next();
            if (currPart.getId() == (partId))
            {
                searchResult = partIterator.next();
                return searchResult;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId)
    {
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName)
    {
        ListIterator<Part> partIterator = allParts.listIterator();
        ObservableList<Part> searchResult = FXCollections.observableArrayList();
        while(partIterator.hasNext())
        {
            Part currPart = partIterator.next();
            if(currPart.getName().contains(partName))
            {
                searchResult.add(currPart);
            }
        }
        if (searchResult.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Result");
            alert.setHeaderText("No Matches Found");
            alert.setContentText("Your search did not have any matches.");
            alert.showAndWait();
        }
        return searchResult;
    }

    public static ObservableList<Product> lookupProduct(String productName)
    {
        ListIterator<Product> productIterator = allProducts.listIterator();
        ObservableList<Product> searchResult = FXCollections.observableArrayList();
        while(productIterator.hasNext())
        {
            Product currProduct = productIterator.next();
            if(currProduct.getName().contains(productName))
            {
                searchResult.add(currProduct);
            }
        }
        if (searchResult.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Result");
            alert.setHeaderText("No Matches Found");
            alert.setContentText("Your search did not have any matches.");
            alert.showAndWait();
        }
        return searchResult;
    }

    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart)
    {
        allParts.remove(selectedPart);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Successful Deletion");
        alert.setContentText("The Part was successfully Deleted");
        alert.showAndWait();
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct)
    {
        allProducts.remove(selectedProduct);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Successful Deletion");
        alert.setContentText("The Product was successfully Deleted");
        alert.showAndWait();
        return true;
    }

    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
