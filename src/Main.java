import Controller.Field;
import Controller.ToolBar;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Field field = new Field( 15, 15, 1, 20 );
        JFrame f = new JFrame();
        f.setLayout( new BorderLayout(  ));

        JPanel statusPanel = new JPanel();

        statusPanel.setBorder(new BevelBorder( BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(f.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel("status");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        f.add( statusLabel, BorderLayout.SOUTH );

        JScrollPane sp = new JScrollPane(field);

        f.getContentPane().add( new ToolBar( field, statusLabel ),BorderLayout.NORTH );
        f.getContentPane().add( sp, BorderLayout.CENTER );
        f.pack();
        f.setSize( 800, 800 );
        //f.setLayout(null);
        f.setVisible( true );

    }
}

