import Controller.Field;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Field m = new Field( 15, 15, 3, 20 );
        JFrame f = new JFrame();
        f.add( m );
        f.setSize( 800, 800 );
        f.setVisible( true );
    }
}

