package TanksGame.GameKeyControls;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class KeyboardEvents extends Observable {
    public KeyEvent events;
    public int KeyEventType; 
  
    public void SetKeyEvents(int EventType,KeyEvent ke) {
        KeyEventType = EventType;
        events = ke;

        setChanged();
        notifyObservers(this);
    }

}
