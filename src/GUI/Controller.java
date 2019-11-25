package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView picture;
    @FXML
    private Button buttonSelectImageFile;
    @FXML
    private Button buttonEditMatrix;
    @FXML
    private Button buttonApplyMatrixFilter;
    @FXML
    private Button buttonGenerateImage;
    @FXML
    private Button buttonRestoreOriginalImage;
    @FXML
    private RadioButton radioButtonOriginalImage;
    @FXML
    private RadioButton radioButtonModifiedImage;
    @FXML
    private Menu menuFilters;

    final FileChooser fileChooser = new FileChooser();

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void enableButtons() {
        buttonApplyMatrixFilter.setDisable(false);
        buttonRestoreOriginalImage.setDisable(false);
        radioButtonOriginalImage.setDisable(false);
        radioButtonModifiedImage.setDisable(false);
        for (int i = 0 ;i < menuFilters.getItemCount(); i++) {
            menuFilters.getItem(i).setEnabled(true);
        }
    }

    @FXML
    public void disableButtons() {
        buttonApplyMatrixFilter.setDisable(true);
        buttonRestoreOriginalImage.setDisable(true);
        radioButtonOriginalImage.setDisable(true);
        radioButtonModifiedImage.setDisable(true);
        for (int i = 0 ;i < menuFilters.getItemCount(); i++) {
            menuFilters.getItem(i).setEnabled(false);
        }
    }

    @FXML
    public void loadImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpeg", "*.jpg", "*.png");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(new FileInputStream(file));
        picture.setImage(image);
        enableButtons();
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

    /*@FXML
    private void generateImage() {
        BufferedImage img = makeColoredImage();
        printIntoLog("Image generated");
        for (int a = 0; a < menuFilters.getItemCount(); a++) {
            menuFilters.getItem(a).setEnabled(true);
        }
        ;
    }*/

    public BufferedImage makeColoredImage() {
        BufferedImage bImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < bImage.getWidth(); x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(x%255,y%255,(x+y)%255).getRGB()));
            }
        }
        return bImage;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup tg = new ToggleGroup();
        radioButtonModifiedImage.setToggleGroup(tg);
        radioButtonOriginalImage.setToggleGroup(tg);
    }


}
