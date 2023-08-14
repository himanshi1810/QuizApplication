package Listeners;

import java.util.EventListener;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

public interface NewScreenListener extends EventHandler {

    public void ChangeScreen(Node node);
    public void removeTopScreen();
    
}
