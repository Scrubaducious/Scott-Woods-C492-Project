package sample;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreen implements Initializable
{
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partTableIdColumn;
    @FXML private TableColumn<Part, String> partTableNameColumn;
    @FXML private TableColumn<Part, Integer> partTableInvColumn;
    @FXML private TableColumn<Part, Double> partTableCostColumn;
    @FXML private TableView<Product> prodTableView;
    @FXML private TableColumn<Product, Integer> prodTableIdColumn;
    @FXML private TableColumn<Product, String> prodTableNameColumn;
    @FXML private TableColumn<Product, Integer> prodTableInvColumn;
    @FXML private TableColumn<Product, Double> prodTableCostColumn;

    @FXML private TextField partSearchTxt;
    @FXML private TextField prodSearchTxt;

    public Button partsSearchButton;
    public Button partsAddButton;
    public Button partsModButton;
    public Button partsDelButton;
    public Button prodsSearchButton;
    public Button prodsAddButton;
    public Button prodsModButton;
    public Button prodsDelButton;
    public Button exitButton;

    public static Part partToMod;
    public static Product prodToMod;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partTableView.setItems(Inventory.getAllParts());
        partTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partTableInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTableView.setItems(Inventory.getAllProducts());
        prodTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodTableInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodTableCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void toAddParts(ActionEvent actionEvent) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Scene addPartScene = new Scene(parent);
        Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    public void toModParts(ActionEvent actionEvent) throws IOException
    {
        partToMod = partTableView.getSelectionModel().getSelectedItem();
        Parent parent = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
        Scene modPartScene = new Scene(parent);
        Stage modPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modPartStage.setScene(modPartScene);
        modPartStage.show();
    }

    public void deletePart(ActionEvent actionEvent)
    {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
        updatePartsTable();
    }

    public void updatePartsTable()
    {
        partTableView.setItems(Inventory.getAllParts());
    }

    public void updateProductsTable()
    {
        prodTableView.setItems(Inventory.getAllProducts());
    }

    public void partSearchClick()
    {
        ObservableList<Part> partSearchResult = FXCollections.observableArrayList();
        String searchInput = partSearchTxt.getText();
        partSearchResult = Inventory.lookupPart(searchInput);
        partTableView.setItems(partSearchResult);
    }

    public void prodSearchClick()
    {
        ObservableList<Product> prodSearchResult = FXCollections.observableArrayList();
        String searchInput = prodSearchTxt.getText();
        prodSearchResult = Inventory.lookupProduct(searchInput);
        prodTableView.setItems(prodSearchResult);
    }

    public void toAddProducts(ActionEvent actionEvent) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene addProductScene = new Scene(parent);
        Stage addProductStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    public void toModProducts(ActionEvent actionEvent) throws IOException
    {
        prodToMod = prodTableView.getSelectionModel().getSelectedItem();
        Parent parent = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
        Scene modProductScene = new Scene(parent);
        Stage modProductStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modProductStage.setScene(modProductScene);
        modProductStage.show();
    }

    public void deleteProduct(ActionEvent actionEvent)
    {
        Product product = prodTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(product);
        updateProductsTable();
    }

    public void closeProgram()
    {
        System.exit(0);
    }
}
