package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolBar extends JToolBar {
    private Field field;
    private JLabel statusLabel;

    public ToolBar(Field field, JLabel statusLabel) {
        this.statusLabel = statusLabel;
        this.field = field;
        setFloatable( false );
        add( new OpenFileButton() );
        add( new SaveFileButton() );
        add( new NewFileButton() );
        add( new XorButton() );
        add( new ClearButton() );
        add( new ImpactButton() );
        add( new NextButton() );
        add( new RunButton() );

    }

    class OpenFileButton extends JButton implements MouseListener {
        public OpenFileButton() {
            setToolTipText( "Open file" );
            setIcon( new ImageIcon( "src/images/open.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Open file" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class SaveFileButton extends JButton implements MouseListener {
        public SaveFileButton() {
            setToolTipText( "Save file" );
            setIcon( new ImageIcon( "src/images/save.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Save file" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class NewFileButton extends JButton implements MouseListener {
        public NewFileButton() {
            setToolTipText( "Create new file" );
            setIcon( new ImageIcon( "src/images/new.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Create new file" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class XorButton extends JButton implements MouseListener {
        public XorButton() {
            setToolTipText( "Enable/Disable XOR mode" );
            setIcon( new ImageIcon( "src/images/xor.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Enable/Disable XOR mode" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class ClearButton extends JButton implements MouseListener{
        public ClearButton() {
            setToolTipText( "Clear field" );
            setIcon( new ImageIcon( "src/images/clear.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Clear field" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class ImpactButton extends JButton implements MouseListener{
        public ImpactButton() {
            setToolTipText( "Show/Hide impact" );
            setIcon( new ImageIcon( "src/images/impact.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Show/hide impact" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class NextButton extends JButton implements MouseListener{
        public NextButton() {
            setToolTipText( "Next step" );
            setIcon( new ImageIcon( "src/images/next.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Next step" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }

    class RunButton extends JButton implements MouseListener{
        public RunButton() {
            setToolTipText( "Run/Stop" );
            setIcon( new ImageIcon( "src/images/run.png" ) );
            addMouseListener( this );
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            statusLabel.setText( "Run/Stop" );
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            statusLabel.setText( "" );
        }
    }
}
