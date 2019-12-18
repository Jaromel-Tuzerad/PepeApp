package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML
    private ImageView pictureTom;

    final FileChooser fileChooser = new FileChooser();
    
    private BufferedImage savedImage;
    private BufferedImage filteredImage;

    @FXML
    public void exit() {
        System.exit(0);
    }

    public void exitAbout(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
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

    /*@FXML
    public void disableButtons() {
        buttonApplyMatrixFilter.setDisable(true);
        buttonRestoreOriginalImage.setDisable(true);
        radioButtonOriginalImage.setDisable(true);
        radioButtonModifiedImage.setDisable(true);
        for (MenuItem item : menuFilters.getItems()) {
            item.setDisable(true);
        }
    }
    */

    @FXML
    public void loadImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpeg", "*.jpg", "*.png");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());

        if(file != null) {
            try {
                Image image = new Image(new FileInputStream(file));
                picture.setImage(image);
                miniPicture.setImage(image);
                enableButtons();
                savedImage = SwingFXUtils.fromFXImage(image, null);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @FXML
    public void about(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("About.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Prasák");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        savedImage = SwingFXUtils.fromFXImage(pic, null);
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

    @FXML
    public void applyFilter() throws FilterException{
        try {
            filteredImage = new BufferedImage(savedImage.getWidth(), savedImage.getHeight(), savedImage.getType());
            for (int x = 0; x < savedImage.getWidth(); x++){
                for (int y = 0; y < savedImage.getHeight(); y++){
                    int rgbOrig = savedImage.getRGB(x, y);
                    Color c = new Color(rgbOrig);
                    int r = 255 - c.getRed();
                    int g = 255 - c.getGreen();
                    int b = 255 - c.getBlue();
                    Color nc = new Color(r,g,b);
                    filteredImage.setRGB(x,y,nc.getRGB());
                }
            }
            picture.setImage(SwingFXUtils.toFXImage(filteredImage, null ));
        } catch (Exception e){
            throw new FilterException(e.getMessage());
        }
    }

    @FXML
    public void showFilteredImage() {
        picture.setImage(SwingFXUtils.toFXImage(filteredImage, null));
    }

    @FXML
    public void showOriginalImage() {
        picture.setImage(SwingFXUtils.toFXImage(savedImage, null));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ToggleGroup tg = new ToggleGroup();
            radioButtonModifiedImage.setToggleGroup(tg);
            radioButtonOriginalImage.setToggleGroup(tg);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
