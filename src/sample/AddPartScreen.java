package sample;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartScreen implements Initializable
{
    @FXML private TextField partIdTxt;
    @FXML private TextField partNameTxt;
    @FXML private TextField partInvTxt;
    @FXML private TextField partPriceTxt;
    @FXML private TextField partMaxTxt;
    @FXML private TextField partMinTxt;
    @FXML private TextField partCompNmTxt;

    @FXML private RadioButton partInhouseRadio;
    @FXML private RadioButton partOutsourceRadio;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @FXML private Label partCompLabel;

    private boolean isOutsourced = false;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        int partId = Inventory.getAllParts().size() + 1; //IDs start at 1 instead of 0
        partIdTxt.setText(Integer.toString(partId));
        partIdTxt.setEditable(false);
    }

    public void setInhouse(ActionEvent actionEvent)
    {
        isOutsourced = false;
        partCompNmTxt.setPromptText("Machine ID");
        partCompLabel.setText("Machine ID");
    }

    public void setOutsourced(ActionEvent actionEvent)
    {
        isOutsourced = true;
        partCompNmTxt.setPromptText("Company Name");
        partCompLabel.setText("Company Name");
    }

    public void cancelAddPart(ActionEvent actionEvent) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("The Creation of this Part has been Cancelled.");
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

    public void savePart(ActionEvent actionEvent) throws IOException
    {
        int id, inv, max, min;
        String name;
        double price;

        boolean inventoryCorrect;

        id = Integer.parseInt(partIdTxt.getText());
        name = partNameTxt.getText();
        price = Double.parseDouble(partPriceTxt.getText());
        inv = Integer.parseInt(partInvTxt.getText());
        min = Integer.parseInt(partMinTxt.getText());
        max = Integer.parseInt(partMaxTxt.getText());

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

        if (inventoryCorrect)
        {
            if (partOutsourceRadio.isSelected())
            {
                String companyName = partCompNmTxt.getText();
                OutsourcedPart newPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                Inventory.addPart(newPart);
                toMainScreen(actionEvent);
            }
            else if (partInhouseRadio.isSelected())
            {
                int machineId = Integer.parseInt(partCompNmTxt.getText());
                InhousePart newPart = new InhousePart(id, name, price, inv, min, max, machineId);
                Inventory.addPart(newPart);
                toMainScreen(actionEvent);
            }
        }
    }
}
