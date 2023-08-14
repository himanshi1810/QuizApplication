import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Exceptions.LoginException;
import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.QuizResultDetails;
import Models.Student;
import Utils.DataCollector;
import javafx.scene.paint.Color;

public class test {

    public static void main(String args[]) throws Exception
    {
        // totalSec = 10;
        // TimerTask timerTask = new TimerTask() {
        //     @Override
        //     public void run()
        //     {
        //         System.out.println("After 1 sec....");
        //         convertTime();
        //         if(totalSec<=0)
        //         {
        //             System.exit(0);
        //         }
        //     }
        // };
        // Timer timer = new Timer();
        // timer.schedule(timerTask, 0 ,1000);
        Quiz.createTable();
        Question.createTable();
        Student.createTable();
        QuizResult.createTable();
        QuizResultDetails.createTable();
        DataCollector.readAndSaveQuizzesData();
        DataCollector.readAndSaveUserData();


    }
}
