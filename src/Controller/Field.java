package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Field extends JPanel {

    //region Private fields
    private int m;
    private int n;
    private int k;
    private int w;
    private int maxX;
    private int maxY;
    private boolean mousePressed = false;

    private Painter painter;
    private Field field = this;
    private BufferedImage hexField;
    private Map<Position, Position> posToCoord = new ConcurrentHashMap<>();

    //endregion

    //region Defines

    public static final int BORDER_COLOR = Color.black.getRGB();
    public static final int DEAD_CELL_COLOR = Color.yellow.getRGB();
    public static final int LIVE_CELL_COLOR = Color.green.getRGB();

    //endregion

    //region Initialization

    public Field(int m, int n, int k, int w) {
        setListeners();

        this.m = m;
        this.n = n;
        this.k = k;
        this.w = w;

        maxX = (int) ((m + 1) * k + m * Math.sqrt( 3 ) * w);
        maxY = n * 3 * w;

        painter = new Painter( maxX, maxY );
        hexField = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_ARGB );
        Graphics fieldGraphics = hexField.getGraphics();
        ((Graphics2D) fieldGraphics).setStroke( new BasicStroke( k ) );
        paintPanel( fieldGraphics );
    }


    public void paint(Graphics g) {
        g.drawImage( hexField, 0, 0, this );
    }

    //endregion

    //region Field painting

    private void paintPanel(Graphics g) {
        g.setColor( Color.black );
        double offsetX;
        double offsetY = w / (double) 2;
        double stepX = w * Math.sqrt( 3 );
        double stepY = w + w / (double) 2;
        for (int y = 0; y < n; y++) {
            if (y % 2 != 0) {
                offsetX = stepX / 2;
            } else {
                offsetX = 0;
            }
            for (int x = 0; x < m - (y % 2); x++) {
                painter.drawHexagon( x * stepX + offsetX, y * stepY + offsetY, w, g );
                int hexagonCenterX = (int) (x * stepX + offsetX + stepX / 2);
                int hexagonCenterY = (int) (y * stepY + offsetY + offsetY);
                painter.paintHexagon( hexagonCenterX, hexagonCenterY, DEAD_CELL_COLOR, hexField );
                posToCoord.put( new Position( x, y ), new Position( hexagonCenterX, hexagonCenterY ) );
            }
        }
    }

    //endregion

    private void setListeners() {
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
                changeHexagonColor( e.getX(), e.getY(), LIVE_CELL_COLOR );
                field.getGraphics().drawImage( hexField, 0, 0, field );
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }
        } );

        addMouseMotionListener( new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (mousePressed) {
                    changeHexagonColor( e.getX(), e.getY(), LIVE_CELL_COLOR );
                    field.getGraphics().drawImage( hexField, 0, 0, field );
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (mousePressed) {
                    changeHexagonColor( e.getX(), e.getY(), LIVE_CELL_COLOR );
                    field.getGraphics().drawImage( hexField, 0, 0, field );
                }
            }
        } );
    }

    private void changeHexagonColor(int x, int y, int newColor) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        int oldColor = hexField.getRGB( x, y );
        if ((oldColor == DEAD_CELL_COLOR || oldColor == LIVE_CELL_COLOR) && oldColor != newColor) {
            painter.paintHexagon( x, y, newColor, hexField );
        }
    }

}
