package il.cshaifasweng.OCSFMediatorExample.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class complaintWorkerResponseController{
    @FXML
    private Label complaintNumber;

    @FXML
    private Label complaintStatus;

    @FXML
    private Label lastDateToHandle;

    @FXML
    private TextArea response;

    @FXML
    private TextArea content;

    @FXML
    private Button SendBtn;

    customerServiceViewController controllerInstance;

    public void setComplaintData(String _complaintNumber, String _complaintStatus, String _lastDateToHandle,
                                 String _content,customerServiceViewController _controllerInstance){
        complaintNumber.setText(_complaintNumber);
        complaintStatus.setText(_complaintStatus);
        lastDateToHandle.setText(_lastDateToHandle);
        content.setText(_content);
        controllerInstance = _controllerInstance;
    }
    @FXML private javafx.scene.control.Button closeButton;

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    void onSendBtn(ActionEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setHeaderText("תגובתך לתלונה נשלחה ללקוח, התלונה נסגרה");
        a.setTitle("תגובה לתלונת לקוח");
        a.setContentText("");
        a.show();

        controllerInstance.closeComplaint(complaintNumber.getText());
        Stage stage = (Stage) SendBtn.getScene().getWindow();
        stage.close();
    }
}
