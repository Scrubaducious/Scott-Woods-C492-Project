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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class ModifyProductScreen implements Initializable
{
    @FXML private TableView<Part> prodAddTableView;
    @FXML private TableColumn<Part, Integer> prodAddTableIdColumn;
    @FXML private TableColumn<Part, String> prodAddTableNameColumn;
    @FXML private TableColumn<Part, Integer> prodAddTableInvColumn;
    @FXML private TableColumn<Part, Double> prodAddTableCostColumn;
    @FXML private TableView<Part> prodDelTableView;
    @FXML private TableColumn<Part, Integer> prodDelTableIdColumn;
    @FXML private TableColumn<Part, String> prodDelTableNameColumn;
    @FXML private TableColumn<Part, Integer> prodDelTableInvColumn;
    @FXML private TableColumn<Part, Double> prodDelTableCostColumn;

    @FXML private TextField prodIdTxt;
    @FXML private TextField prodNameTxt;
    @FXML private TextField prodInvTxt;
    @FXML private TextField prodPriceTxt;
    @FXML private TextField prodMaxTxt;
    @FXML private TextField prodMinTxt;
    @FXML private TextField modProdSearchTxt;

    @FXML private Button prodSearchButton;
    @FXML private Button prodAddButton;
    @FXML private Button prodDeleteButton;
    @FXML private Button prodSaveButton;
    @FXML private Button prodCancelButton;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        prodIdTxt.setEditable(false);
        prodIdTxt.setDisable(true);

        int id = MainScreen.prodToMod.getId();
        String name = MainScreen.prodToMod.getName();
        int inv = MainScreen.prodToMod.getStock();
        double price = MainScreen.prodToMod.getPrice();
        int max = MainScreen.prodToMod.getMax();
        int min = MainScreen.prodToMod.getMin();

        associatedParts = MainScreen.prodToMod.getAllAssociatedParts();

        prodIdTxt.setText(Integer.toString(id));
        prodNameTxt.setText(name);
        prodInvTxt.setText(Integer.toString(inv));
        prodPriceTxt.setText(Double.toString(price));
        prodMaxTxt.setText(Integer.toString(max));
        prodMinTxt.setText(Integer.toString(min));

        prodAddTableView.setItems(Inventory.getAllParts());
        prodAddTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodAddTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodAddTableInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodAddTableCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodDelTableView.setItems(MainScreen.prodToMod.getAllAssociatedParts());
        prodDelTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodDelTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodDelTableInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodDelTableCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void partSearchClick()
    {
        ObservableList<Part> partSearchResult = FXCollections.observableArrayList();
        String searchInput = modProdSearchTxt.getText();
        partSearchResult = Inventory.lookupPart(searchInput);
        prodAddTableView.setItems(partSearchResult);
    }

    public void addPartToProd()
    {
        Part partToAdd = prodAddTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(partToAdd);
    }

    public void removePartFromProd()
    {
        Part part = prodDelTableView.getSelectionModel().getSelectedItem();
        associatedParts.remove(part);
    }

    public void saveProd(ActionEvent actionEvent) throws IOException
    {
        int id, inv, max, min;
        String name;
        double price;

        boolean inventoryCorrect;

        id = Integer.parseInt(prodIdTxt.getText());
        name = prodNameTxt.getText();
        price = Double.parseDouble(prodPriceTxt.getText());
        inv = Integer.parseInt(prodInvTxt.getText());
        min = Integer.parseInt(prodMinTxt.getText());
        max = Integer.parseInt(prodMaxTxt.getText());

        if ((inv < min) || (inv > max) || (max < min))
        {
            inventoryCorrect = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Conflict");
            alert.setContentText("Inventory value must be greater than Min and less than Max. Max must be greater than Min.");
            alert.showAndWait();
        }
        else { inventoryCorrect = true; }

        if ((associatedParts.size() == 0) || (MainScreen.prodToMod.getAllAssociatedParts().size() == 0))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Parts Selected");
            alert.setContentText("Product must contain at least one Part");
            alert.showAndWait();
        }

        if ((inventoryCorrect) && ((associatedParts.size() != 0) || (MainScreen.prodToMod.getAllAssociatedParts().size() != 0)))
        {
            Product updatedProduct = new Product(id, name, price, inv, min, max);
            Inventory.updateProduct(id-1, updatedProduct);

            ListIterator<Part> partIterator = associatedParts.listIterator();
            while(partIterator.hasNext())
            {
                Part currPart = partIterator.next();
                updatedProduct.addAssociatedPart(currPart);
            }

            toMainScreen(actionEvent);
        }
    }

    public void cancelPartMod(ActionEvent actionEvent) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("The Update to this Product has been Cancelled.");
        alert.showAndWait();
        toMainScreen(actionEvent);
    }

    public void toMainScreen(ActionEvent actionEvent) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene mainScene = new Scene(parent);
        Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
