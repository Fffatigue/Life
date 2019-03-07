package View;

import Controller.Field;
import Util.IllegalParameterException;

import javax.swing.*;
import java.awt.*;

public class NewFieldSettings extends JFrame {
    private JTextField nTextField;
    private JTextField mTextField;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    private Field field;

    public NewFieldSettings(Field field) {
        this.field = field;
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        add( rootPanel );
        setPreferredSize( new Dimension( 200, 200 ) );
        setListeners();
        pack();
        setVisible( true );
    }

    private void setListeners() {
        nTextField.setText( String.valueOf( field.getN() ) );
        mTextField.setText( String.valueOf( field.getM() ) );
        OKButton.addActionListener( actionEvent -> {
            try {
                int n = Integer.parseInt( nTextField.getText() );
                int m = Integer.parseInt( mTextField.getText() );
                field.setSettings( m, n, field.getK(), field.getW(), false );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog( null, "Wrong number: " + e.getMessage() );
                return;
            } catch (IllegalParameterException e) {
                JOptionPane.showMessageDialog( null, "Wrong parameter: " + e.getMessage() );
                return;
            }
            dispose();
        } );
        cancelButton.addActionListener( actionEvent ->

        {
            dispose();
        } );

    }
}
