package View;

import Controller.Field;
import Util.IllegalParameterException;

import javax.swing.*;
import java.awt.*;

public class ModelSettings extends JFrame{
    private JTextField SNDImpactField;
    private JTextField FSTImpactField;
    private JTextField birthEndField;
    private JTextField birthBeginField;
    private JTextField liveEndField;
    private JTextField liveBeginField;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    private Field field;
    public ModelSettings(Field field) {
        this.field = field;
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        add( rootPanel );
        setPreferredSize( new Dimension( 200, 200 ) );
        setListeners();
        pack();
        setVisible( true );
    }

    private void setListeners(){
        liveBeginField.setText( String.valueOf(field.getLiveBegin()) );
        liveEndField.setText( String.valueOf( field.getLiveEnd() ) );
        birthBeginField.setText( String.valueOf( field.getBirthBegin() ) );
        birthEndField.setText( String.valueOf( field.getBirthEnd() ) );
        FSTImpactField.setText( String.valueOf( field.getFstImpact() ) );
        SNDImpactField.setText( String.valueOf( field.getSndImpact() ) );
        OKButton.addActionListener( actionEvent -> {
            try {
                double liveBegin = Double.parseDouble(liveBeginField.getText());
                double liveEnd = Double.parseDouble(liveEndField.getText());
                double birthBegin = Double.parseDouble(birthBeginField.getText());
                double birthEnd = Double.parseDouble(birthEndField.getText());
                double fstImpact =Double.parseDouble(FSTImpactField.getText());
                double sndImpact = Double.parseDouble(SNDImpactField.getText());
                field.setModelParameters( liveBegin,liveEnd,birthBegin,birthEnd,fstImpact,sndImpact );
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

}
