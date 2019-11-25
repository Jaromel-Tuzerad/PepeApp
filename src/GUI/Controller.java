package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Random;
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
    @FXML
    private ImageView miniPicture;

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
        for (MenuItem item : menuFilters.getItems()) {
            item.setDisable(false);
        }
    }

    @FXML
    public void disableButtons() {
        buttonApplyMatrixFilter.setDisable(true);
        buttonRestoreOriginalImage.setDisable(true);
        radioButtonOriginalImage.setDisable(true);
        radioButtonModifiedImage.setDisable(true);
        for (MenuItem item : menuFilters.getItems()) {
            item.setDisable(true);
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
        miniPicture.setImage(image);
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

    @FXML
    private void generateImage() {
        BufferedImage bi = makeColoredImage();
        Image pic = SwingFXUtils.toFXImage(bi, null );
        picture.setImage(pic);
        miniPicture.setImage(pic);
    }

    public int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public BufferedImage makeColoredImage() {
        BufferedImage bImage = new BufferedImage(1536, 1000, BufferedImage.TYPE_3BYTE_BGR);
        //RED
        for (int x = 0; x < 256; x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(x%256, 0 , 0).getRGB()));
            }
        }
        //RED-GREEN
        for (int x = 256; x < 512; x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(255, x%256, 0).getRGB()));
            }
        }
        //GREEN-RED
        for (int x = 512; x < 768; x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(255-(x%256), 255 ,0).getRGB()));
            }
        }
        //GREEN-BLUE
        for (int x = 768; x < 1024; x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(0, 255 ,x%256).getRGB()));
            }
        }
        //BLUE-GREEN
        for (int x = 1024; x < 1280; x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(0, 255-(x%256) ,255).getRGB()));
            }
        }
        //GREEN
        for (int x = 1280; x < 1536; x++){
            for (int y = 0; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(0, 0 ,255-(x%256)).getRGB()));
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
