import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.LoginException;
import Models.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class LoginController implements Initializable{

 @FXML
 private TextField adminEmail;

 @FXML
 private PasswordField adminPassword;

 @FXML
 private Button adminLoginButton;

 @FXML
 private TextField studentEmail;

 @FXML
 private PasswordField studentPassword;

 @FXML
 private Button studentLoginButton;



 
 @Override
 public void initialize(URL arg0, ResourceBundle arg1) {
  
 
 } 

 public void loginA(ActionEvent e) throws IOException
 {
    System.out.println("Admin Login");
    String email = adminEmail.getText();
    String password = adminPassword.getText();

    
    
    if(email.trim().equals(AdminEmailPassword.email) && password.trim().equals(AdminEmailPassword.password))
    {
        System.out.println("Admin Logged In Succesfully");
        Parent root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
       Stage stage =(Stage) studentPassword.getScene().getWindow();
       Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        
     
       
    }
    else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Enter Valid Email Or Password");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();

    }
    System.out.println("Admin email ---->" + email +"    Password ---->" +password);

 }

 public void loginS(ActionEvent e)
 {
    System.out.println("Student Login");
    
    Student s = new Student(this.studentEmail.getText(),this.studentPassword.getText());

    try{
        s.login();
        // System.out.println(s);
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentMainScreen.fxml"));
             Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("You Logged In Successfully");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();

            
            Parent root = fxmlLoader.load();
            StudentMainScreen studentMainScreen =fxmlLoader.getController();
            studentMainScreen.setStudent(s);
           Stage stage =(Stage) studentPassword.getScene().getWindow();
           Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }
        catch(Exception ex)
        {
            if(ex instanceof LoginException)
            {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Enter Valid Email Or Password");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();

            }
        }
            
    
            
         
           
        
    }catch(Exception ex)
    {
     if(ex instanceof LoginException)
     {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Email or Password Incorrect");
        alert.setHeight(200);
        alert.setWidth(200);
        alert.show();
     }
    }
 }

 
} 