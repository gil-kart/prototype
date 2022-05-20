package il.cshaifasweng.OCSFMediatorExample.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class complaintController {
    @FXML
    private Button onSendComplaintClick;

    @FXML
    private TextArea complaintText;

    @FXML
    private Label orderNumber;

    @FXML
    void SendComplaintClicked(ActionEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setHeaderText("תלונתך נשלחה למערכת");
        a.setTitle("הגשת תלונה");
        a.setContentText("");
        a.show();
        Complaint complaint = new Complaint("creationDate", "open","1234", complaintText.getText());
        System.out.println(complaint.getContent());
        // this is the part where we send the data to the server
        try {
            App.setRoot("main");
        }
        catch (Exception e){
            System.out.println("cant go to main screen");
            e.printStackTrace();
        }

    }
}
