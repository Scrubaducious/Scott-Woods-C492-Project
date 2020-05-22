package sample;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
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

public class ModifyPartScreen implements Initializable
{
    @FXML private TextField partIdTxt;
    @FXML private TextField partNameTxt;
    @FXML private TextField partInvTxt;
    @FXML private TextField partPriceTxt;
    @FXML private TextField partMaxTxt;
    @FXML private TextField partMinTxt;
    @FXML private TextField partCompNmTxt;

    @FXML private Label partCompNmLabel;

    @FXML private RadioButton partInhouseRadio;
    @FXML private RadioButton partOutsourceRadio;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private boolean isOutsourced;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partIdTxt.setEditable(false);
        partIdTxt.setDisable(true);

        int id = MainScreen.partToMod.getId();
        String name = MainScreen.partToMod.getName();
        int inv = MainScreen.partToMod.getStock();
        double price = MainScreen.partToMod.getPrice();
        int max = MainScreen.partToMod.getMax();
        int min = MainScreen.partToMod.getMin();

        partIdTxt.setText(Integer.toString(id));
        partNameTxt.setText(name);
        partInvTxt.setText(Integer.toString(inv));
        partPriceTxt.setText(Double.toString(price));
        partMaxTxt.setText(Integer.toString(max));
        partMinTxt.setText(Integer.toString(min));

        if (MainScreen.partToMod instanceof OutsourcedPart)
        {
            isOutsourced = true;
            partOutsourceRadio.setSelected(true);
            String compNm = ((OutsourcedPart) MainScreen.partToMod).getCompanyName();
            partCompNmLabel.setText("Company Name");
            partCompNmTxt.setText(compNm);
        }
        else if (MainScreen.partToMod instanceof InhousePart)
        {
            isOutsourced = false;
            partInhouseRadio.setSelected(true);
            int machineId = ((InhousePart) MainScreen.partToMod).getMachineId();
            partCompNmLabel.setText("Machine ID");
            partCompNmTxt.setText(Integer.toString(machineId));
        }
    }

    public void setInhouse(ActionEvent actionEvent)
    {
        isOutsourced = false;
        partCompNmTxt.setPromptText("Machine ID");
        partCompNmLabel.setText("Machine ID");
    }

    public void setOutsourced(ActionEvent actionEvent)
    {
        isOutsourced = true;
        partCompNmTxt.setPromptText("Company Name");
        partCompNmLabel.setText("Company Name");
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
                OutsourcedPart updatedPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                Inventory.updatePart(id-1, updatedPart); //-1 because IDs start at 1, not 0
                toMainScreen(actionEvent);
            }
            else if (partInhouseRadio.isSelected())
            {
                int machineId = Integer.parseInt(partCompNmTxt.getText());
                InhousePart updatedPart = new InhousePart(id, name, price, inv, min, max, machineId);
                Inventory.updatePart(id-1, updatedPart); //-1 because IDs start at 1, not 0
                toMainScreen(actionEvent);
            }
        }
    }

    public void cancelModPart(ActionEvent actionEvent) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("The Update to this Part has been Cancelled.");
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
