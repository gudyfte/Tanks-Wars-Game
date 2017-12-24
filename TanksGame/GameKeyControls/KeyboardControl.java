package TanksGame.GameKeyControls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardControl extends KeyAdapter {

    KeyboardEvents event;

    public KeyboardControl(KeyboardEvents Event) {
        event = Event;
    }

   public void keyReleased(KeyEvent kr) {
        event.SetKeyEvents(0,kr);
    }

    public void keyPressed(KeyEvent kp) {
        event.SetKeyEvents(1,kp);
    }

}
