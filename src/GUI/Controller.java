package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView picture;

    final FileChooser fileChooser = new FileChooser();

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void loadImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpeg", "*.jpg", "*.png");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(new FileInputStream(file));
        picture.setImage(image);
    }

    @FXML
    public void saveImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        Image image = picture.getImage();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
