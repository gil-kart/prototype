package il.cshaifasweng.OCSFMediatorExample.client;

//import il.cshaifasweng.OCSFMediatorExample.entities.Catalog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.stage.WindowEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
//todo: find somewhere to reinstate connection if down. (retries? maybe through connectionexception hook?)
/**
 * JavaFX App
 */


public class App extends Application {

    private static String[] arguments;

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
        scene = new Scene(loadFXML("main"));
        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
		super.stop();
	}
    
    @Subscribe
    public void onWarningEvent(WarningEvent event) {
    	Platform.runLater(() -> {
    		Alert alert = new Alert(AlertType.WARNING,
        			String.format("Message: %s\nTimestamp: %s\n",
        					event.getWarning().getMessage(),
        					event.getWarning().getTime().toString())
        	);
        	alert.show();
    	});
    }

	public static void main(String[] args) {
        arguments = args;
        launch();
    }

    private void closeWindowEvent(WindowEvent event) {
        try{
            stop();
        }catch (Exception ignored){}
        System.out.println("Graceful termination, goodbye ;)");
        System.exit(0);
    }
}