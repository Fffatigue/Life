package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ToolBar extends JToolBar {
    private Field field;

    public ToolBar(Field field) {
        this.field = field;
        setFloatable( false );
        add(new OpenAction());
        add(new SaveAction());
        add(new NewAction());
        add(new JButton( "Xor" ));
        add(new JButton( "Clear" ));
        add(new JButton( "Impact" ));
        add(new JButton( "Next" ));
        add(new JButton( "Run" ));

    }

    class OpenAction extends AbstractAction {
        public OpenAction() {
            // Настройка иконок
            putValue(AbstractAction.SMALL_ICON, new ImageIcon("images/open.png"));
        }
        // Обработка действия
        public void actionPerformed(ActionEvent e) {
            // ничего не делаем
        }
    }

    class SaveAction extends AbstractAction {
        public SaveAction() {
            // Настройка иконок
            putValue(AbstractAction.SMALL_ICON, new ImageIcon("images/save.png"));
        }
        // Обработка действия
        public void actionPerformed(ActionEvent e) {
            // ничего не делаем
        }
    }

    class NewAction extends AbstractAction {
        public NewAction() {
            // Настройка иконок
            putValue(AbstractAction.SMALL_ICON, new ImageIcon("src/images/new.png"));
        }
        // Обработка действия
        public void actionPerformed(ActionEvent e) {
            // ничего не делаем
        }
    }

}
