import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button saveButton, loadButton;

    public ImageView imageView;
    public VBox mainWindow;
    public ScrollPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainWindow.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();

            if (db.hasImage() || db.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        mainWindow.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

//            System.out.println(db.getImage());
            if (db.hasImage()) {
                Image image = db.getImage();
                imageView.setImage(image);
                imageView.setFitHeight(image.getHeight());
                imageView.setFitWidth(image.getWidth());
                event.setDropCompleted(true);
            } else if (db.hasFiles()) {
                db.getFiles().forEach(file -> {
                    try {
                        Image image = new Image(file.toURI().toURL().toExternalForm());
                        ImageView imageView = new ImageView(image);
                        pane.getContent().add(imageView);
                    } catch (Exception exc) {
                        alertBox.showAndWait();
                    }
                });
                event.setDropCompleted(true);
            } else {
                alertBox.showAndWait();
            }
        });

    }

    private final Alert alertBox = new Alert(Alert.AlertType.ERROR);

    {
        alertBox.setTitle("Error: Invalid file");
        alertBox.setContentText("The file you dragged was not a valid image.");
    }
}
