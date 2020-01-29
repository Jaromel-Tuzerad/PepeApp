package GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class SliderController implements Initializable {

    @FXML
    private Slider sliderPercentage;

    @FXML
    private ImageView imageViewPicture;

    @FXML
    public void applyBandWFilter() {
        double percentage = sliderPercentage.getValue();
        BufferedImage newImage = new BufferedImage(Controller.currentImage.getWidth(), Controller.currentImage.getHeight(), Controller.currentImage.getType());
        for(int x = 0; x < newImage.getWidth(); x++) {
            for(int y = 0; y < newImage.getHeight(); y++) {
                Color pixelColor = new Color(Controller.currentImage.getRGB(x, y));
                if(((double)(pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue())/3)/(255)*100 > percentage) {
                    newImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    newImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        imageViewPicture.setImage(SwingFXUtils.toFXImage(newImage, null));
    }

    @FXML
    public void closeWindow() {
        Controller.previousImage = Controller.currentImage;
        Controller.currentImage = SwingFXUtils.fromFXImage(imageViewPicture.getImage(), null);
        sliderPercentage.getScene().getWindow().hide();

    }

    public void initialize(URL url, ResourceBundle rb) {

    }

}
