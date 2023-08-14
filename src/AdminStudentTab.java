import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXRadioButton;

import Models.Student;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AdminStudentTab implements Initializable{
    @FXML
    private VBox formContainer;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField mobileNumber;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private JFXRadioButton female;

    @FXML
    private JFXRadioButton male;

    @FXML
    private JFXButton saveButton;

    @FXML
   private TableView<Student> studentTable;

   @FXML
   private TableColumn<Student, String> studentIdColumn;

   @FXML
   private TableColumn<Student, String> firstNameColumn;

   @FXML
   private TableColumn<Student, String>  lastNameColumn;

   @FXML
   private TableColumn<Student, String>  mobileNumberColumn;

   @FXML
   private TableColumn<Student, Character>  genderColumn;

   @FXML
   private TableColumn<Student, String>  emailColumn;

   @FXML
   private TableColumn<Student, String>  passwordColumn;

   private ToggleGroup toggleGroup;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      initAll();
      radioButtonSetup();
      renderTable();
   }

   private void renderTable()
   {
    List<Student> students = Student.getAll();
    studentTable.getItems().clear();

    this.studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    this.mobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    this.passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    this.genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

    studentTable.getItems().addAll(students);
   }


 public void radioButtonSetup()
   {
    this.male.setSelected(true);
    this.male.setToggleGroup(toggleGroup);
    this.female.setToggleGroup(toggleGroup);
   }

   public void initAll()
   {
    toggleGroup = new ToggleGroup();
   }

   private void resetForm()
   {
    this.password.clear();
    this.email.clear();
    this.firstName.clear();
    this.lastName.clear();
    this.mobileNumber.clear();
   }
   
   public void saveStudent(ActionEvent e)
    {
        System.out.println("Save Student Button Clicked");

        final String EMAIL_PATTERN =
        "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        Pattern p;
     

        p = Pattern.compile(EMAIL_PATTERN);
        
        String firstName = this.firstName.getText().trim();
        String lastName = this.lastName.getText().trim();
        String mobile = this.mobileNumber.getText().trim();
        Character gen = 'M';
        String email = this.email.getText().trim();
        String password = this.password.getText().trim();

        JFXRadioButton gender = (JFXRadioButton) toggleGroup.getSelectedToggle();
        if(gender!=null)
        {
            if(gender == female)
            {
                gen = 'F';
            }
        }

        String message = null;

        if(firstName.length() == 0)
        {
            message ="Enter Your First Name";
        }
        else if(lastName.length() == 0)
        {
            message ="Enter Your Last Name";
        }
        else if(mobile.length() < 10 || mobile.length() > 10)
        {
            message ="Enter Valid Contact Number";
        }
        else if(!p.matcher(email).matches())
        {
            message = "Please Enter Valid Email";
        }
        else if(password.length() < 6)
        {
            message = "Password must contain minimum 6 characters";
        }
        if(message != null)
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Student");
            alert.setHeaderText("Fill Student Information Correctly");
            alert.setContentText(message);
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();
          
            return;
        }

        
        //Save code

       Student s = new Student( firstName, lastName, mobile, gen, email, password);
       System.out.println(s);

      if(s.isExists())
      {
        Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Student Already Registered");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();
        return;
      }

       s=s.save();
       System.out.println(s);
        if(s != null)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Student Registered");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();
            this.resetForm();
           studentTable.getItems().add(s);
         
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("FAIL");
            alert.setHeaderText("Student Registered Failed");
            alert.setHeight(100);
            alert.setWidth(200);
            alert.show();   
        }
    }

   public void resetStudent(ActionEvent e)
    {
        resetForm();
    }

  
}
