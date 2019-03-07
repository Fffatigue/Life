package View;

import Controller.Field;
import Util.IllegalParameterException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FieldSettings extends JFrame {
    private Field field;

    public FieldSettings(Field field) {
        this.field = field;
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        add( rootPanel );
        setPreferredSize( new Dimension( 450, 250 ) );
        setListeners();
        pack();
        setVisible( true );
    }

    private void setListeners() {
        ButtonGroup group = new ButtonGroup();
        group.add( XORRadioButton );
        group.add( replaceRadioButton );
        cellSizeTextField.setText( String.valueOf( 50 ) );
        cellSizeSlider.setMinimum( 1 );
        cellSizeSlider.setMaximum( 80 );
        cellSizeSlider.setValue( field.getK() );
        mTextField.setText( String.valueOf(field.getM()) );
        nTextField.setText( String.valueOf( field.getN() ) );
        lineWidthTextField.setText( String.valueOf( field.getK() ) );
        cellSizeSlider.addChangeListener( e -> cellSizeTextField.setText( String.valueOf( cellSizeSlider.getValue() ) ) );
        cellSizeTextField.addKeyListener( new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String number = cellSizeTextField.getText();
                if (!"".equals( number )) {
                    cellSizeSlider.setValue( Integer.parseInt( number ) );
                }
            }
        } );
        okButton.addActionListener( actionEvent -> {
            try {
                boolean isxor = XORRadioButton.isSelected();
                int cellSize = Integer.parseInt( cellSizeTextField.getText() );
                int lineWidth = Integer.parseInt( lineWidthTextField.getText() );
                int m = Integer.parseInt( mTextField.getText() );
                int n = Integer.parseInt( nTextField.getText() );
                field.setSettings( m, n, cellSize, lineWidth, isxor );
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog( null,"Wrong number: "+e.getMessage());
                return;
            }catch (IllegalParameterException e){
                JOptionPane.showMessageDialog( null,"Wrong parameter: "+e.getMessage());
                return;
            }
            dispose();
        } );
        cancelButton.addActionListener( actionEvent -> {
            dispose();
        } );
    }

    private JPanel rootPanel;
    private JRadioButton XORRadioButton;
    private JRadioButton replaceRadioButton;
    private JTextField mTextField;
    private JTextField nTextField;
    private JTextField lineWidthTextField;
    private JSlider cellSizeSlider;
    private JTextField cellSizeTextField;
    private JButton okButton;
    private JButton cancelButton;
}
