import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ProgressCircleController implements Initializable {

    @FXML
    private Circle circle;

    @FXML 
    private Label number;

    public void setNumber(Integer number) {
        this.number.setText(number.toString());
    }

    public void setDefaultColor()
    {
        circle.setFill(Color.web("#CAD5E2"));
        number.setTextFill(Color.web("#0D0D0D"));
    }

    public void setCurrentQuestionColor()
    {
        circle.setFill(Color.web("#3944F7"));
         number.setTextFill(Color.web("#CAD5E2"));
    }

    public void setWrongAnsweredColor()
    {
        circle.setFill(Color.web("#E21717"));
         number.setTextFill(Color.web("#0D0D0D"));
    }

    
    public void setRightAnsweredColor()
    {
        circle.setFill(Color.web("#3DBE29"));
         number.setTextFill(Color.web("#0D0D0D"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }
    
    
}
