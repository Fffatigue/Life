package View;

import Controller.Field;
import Util.IllegalParameterException;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class InitMainWindow extends MainFrame {
    private Field field = new Field();

    /**
     * Default constructor to create main window
     */
    private InitMainWindow() throws IllegalParameterException {
        super( 600, 400, "Life" );
        try {
            addSubMenu( "File", KeyEvent.VK_F );
            addMenuItem( "File/New", "Create new file", KeyEvent.VK_C, "new.png", "onNew" );
            addMenuItem( "File/Open", "Open file", KeyEvent.VK_O, "open.png", "onOpen" );
            addMenuItem( "File/Save", "Save file", KeyEvent.VK_S, "save.png", "onSave" );
            addMenuItem( "File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit" );
            addSubMenu( "Tools", KeyEvent.VK_T );
            addMenuItem( "Tools/Impact", "Show Impact", KeyEvent.VK_I, "impact.png", "onImpact" );
            addMenuItem( "Tools/Xor", "Enable\\Disable xor", KeyEvent.VK_M, "xor.png", "onXor" );
            addMenuItem( "Tools/Clear", "Clear field", KeyEvent.VK_BACK_SPACE, "trash.png", "onClear" );
            addSubMenu( "Run", KeyEvent.VK_R );
            addMenuItem( "Run/Run\\Stop", "Run\\Stop life", KeyEvent.VK_R, "run.png", "onRun" );
            addMenuItem( "Run/Next", "Make one step", KeyEvent.VK_N, "next.png", "onNext" );
            addSubMenu( "Settings", KeyEvent.VK_S );
            addMenuItem( "Settings/Field settings", "Open field settings", KeyEvent.VK_F, "onFieldSettings" );
            addMenuItem( "Settings/Model settings", "Open model settings", KeyEvent.VK_M, "onModelSettings" );
            addSubMenu( "Help", KeyEvent.VK_H );
            addMenuItem( "Help/About", "Shows program version and copyright information", KeyEvent.VK_A, "about.png", "onAbout" );
            addToolBarButton( "File/New" );
            addToolBarButton( "File/Open" );
            addToolBarButton( "File/Save" );
            addToolBarButton( "File/Exit" );
            addToolBarSeparator();
            addToolBarButton( "Tools/Impact" );
            addToolBarButton( "Tools/Xor" );
            addToolBarButton( "Tools/Clear" );
            addToolBarSeparator();
            addToolBarButton( "Run/Run\\Stop" );
            addToolBarButton( "Run/Next" );
            addToolBarSeparator();
            addToolBarButton( "Help/About" );


            add( new JScrollPane( field ) );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
        field.setSettings( 15,15,30,2,false );
    }

    /**
     * Help/About... - shows program version and copyright information
     */
    public void onAbout() {
        JOptionPane.showMessageDialog( this, "Life, version 1.0\nCopyright � 2019 Aleksandr Chmil, FIT, group 16203", "About Life", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * File/Exit - exits application
     */
    public void onExit() {
        System.exit( 0 );
    }

    public void onClear(){
        field.clear();
    }

    public void onImpact() {
        field.switchImpact();
    }

    public void onNew() {
        new NewFieldSettings( field );
    }

    public void onNext() {
        field.calculateNextState();
    }

    public void onOpen() {
        File f = getOpenFileName( "txt","text files");
        if(f != null) {
            try {
                field.loadGame( f );
            } catch (IllegalParameterException e) {
                JOptionPane.showMessageDialog( null, "Wrong arguments " + e.getMessage() );
            }
        }
    }

    public void onRun() {
        field.run();
    }

    public void onSave() {
        File f = getSaveFileName( "txt","text files");
        if(f!=null) {
            field.saveGame( f );
        }
        }

        public void onXor() {
            field.switchXORMode();
        }

    public void onFieldSettings(){
        new FieldSettings(field);
    }

    public void onModelSettings(){
        new ModelSettings( field );
    }

    /**
     * Application main entry point
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) throws IllegalParameterException {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible( true );
    }
}
