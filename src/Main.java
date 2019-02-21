import Controller.Field;
import Controller.ToolBar;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Field m = new Field( 15, 15, 3, 20 );
        JFrame f = new JFrame();
        f.setLayout( new BorderLayout(  ));
        f.getContentPane().add( new ToolBar( m ),"North" );
        f.getContentPane().add( m, "Center" );
        //f.pack();
        f.setSize( 800, 800 );
        //f.setLayout(null);
        f.setVisible( true );
    }
}

