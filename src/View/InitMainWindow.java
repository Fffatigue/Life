package View;

import Controller.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class InitMainWindow extends MainFrame {
    private Field field = new Field( 10, 10, 2, 20 );

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super( 600, 400, "Init application" );

        try {
            addSubMenu( "File", KeyEvent.VK_F );
            addMenuItem( "File/New", "Create new file", KeyEvent.VK_C, "new.png", "onNew" );
            addMenuItem( "File/Open", "Open file", KeyEvent.VK_O, "open.png", "onOpen" );
            addMenuItem( "File/Save", "Save file", KeyEvent.VK_S, "save.png", "onSave" );
            addMenuItem( "File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", "onExit" );
            addSubMenu( "Tools", KeyEvent.VK_T );
            addMenuItem( "Tools/Impact", "Show Impact", KeyEvent.VK_I, "impact.png", "onImpact" );
            addMenuItem( "Tools/Xor", "Enable\\Disable xor", KeyEvent.VK_M, "xor.png", "onXor" );
            addSubMenu( "Run", KeyEvent.VK_R );
            addMenuItem( "Run/Run\\Stop", "Run\\Stop life", KeyEvent.VK_R, "run.png", "onRun" );
            addMenuItem( "Run/Next", "Make one step", KeyEvent.VK_N, "next.png", "onNext" );
            addSubMenu( "Help", KeyEvent.VK_H );
            addMenuItem( "Help/About", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", "onAbout" );
            addToolBarButton( "File/New" );
            addToolBarButton( "File/Open" );
            addToolBarButton( "File/Save" );
            addToolBarButton( "File/Exit" );
            addToolBarSeparator();
            addToolBarButton( "Tools/Impact" );
            addToolBarButton( "Tools/Xor" );
            addToolBarSeparator();
            addToolBarButton( "Run/Run\\Stop" );
            addToolBarButton( "Run/Next" );
            addToolBarSeparator();
            addToolBarButton( "Help/About" );


            add( new JScrollPane( field ) );
        } catch (Exception e) {
            throw new RuntimeException( e );
        }
    }

    /**
     * Help/About... - shows program version and copyright information
     */
    public void onAbout() {
        JOptionPane.showMessageDialog( this, "Init, version 1.0\nCopyright � 2010 Vasya Pupkin, FF, group 1234", "About Init", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * File/Exit - exits application
     */
    public void onExit() {
        System.exit( 0 );
    }


    public void onImpact() {
    }

    public void onNew() {
        field.clear();
    }

    public void onNext() {
        field.calculateNextState();
    }

    public void onOpen() {
    }

    public void onRun() {
    }

    public void onSave() {
    }

    public void onXor() {
        field.switchXORMode();
    }

    /**
     * Application main entry point
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible( true );
    }
}
