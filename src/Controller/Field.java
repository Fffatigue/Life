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
    private Life life;
    private Painter painter;
    private Field field = this;
    private BufferedImage hexField;
    private Map<Position, Position> posToCoord = new ConcurrentHashMap<>();

    private boolean mousePressed = false;
    private boolean xorMode = false;
    private Position currentHexagon = null;

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
        life = new Life( m, n );
        maxX = (int) ((m + 1) * k + m * Math.sqrt( 3 ) * w);
        maxY = n * w * 2 + 1000;

        setPreferredSize( new Dimension( maxX, maxY ) );

        painter = new Painter( maxX, maxY );
        hexField = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_ARGB );
        Graphics fieldGraphics = hexField.getGraphics();
        ((Graphics2D) fieldGraphics).setStroke( new BasicStroke( k ) );
        paintPanel( fieldGraphics );
    }


    public void paintComponent(Graphics g) {
        super.paintComponent( g );
        g.drawImage( hexField, 0, 0, null );
    }

    private void setListeners() {
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
                onHexagonPressing( e.getX(), e.getY() );
                field.getGraphics().drawImage( hexField, 0, 0, field );
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
                currentHexagon = null;
            }
        } );

        addMouseMotionListener( new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (mousePressed) {
                    onHexagonPressing( e.getX(), e.getY() );
                    field.getGraphics().drawImage( hexField, 0, 0, field );
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (mousePressed) {
                    onHexagonPressing( e.getX(), e.getY() );
                    field.getGraphics().drawImage( hexField, 0, 0, field );
                }
            }
        } );
    }

    //endregion

    //region Field painting

    private void paintPanel(Graphics g) {
        g.setColor( new Color( BORDER_COLOR ) );
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


    private void onHexagonPressing(int x, int y) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        int oldColor = hexField.getRGB( x, y );
        if (oldColor == BORDER_COLOR || (oldColor != DEAD_CELL_COLOR && oldColor != LIVE_CELL_COLOR)) {
            return;
        }
        Position hexagon = getHexagonByCoord( x, y );
        if (currentHexagon == null) {
            currentHexagon = hexagon;
        } else if (currentHexagon == hexagon) {
            return;
        }
        currentHexagon = hexagon;
        if (xorMode) {
            if (oldColor == DEAD_CELL_COLOR) {
                painter.paintHexagon( x, y, LIVE_CELL_COLOR, hexField );
                life.changeCellState( hexagon.getX(), hexagon.getY(), true );
            } else {
                painter.paintHexagon( x, y, DEAD_CELL_COLOR, hexField );
                life.changeCellState( hexagon.getX(), hexagon.getY(), false );
            }
        } else {
            painter.paintHexagon( x, y, LIVE_CELL_COLOR, hexField );
            life.changeCellState( hexagon.getX(), hexagon.getY(), true );
        }
    }

    private Position getHexagonByCoord(int x, int y) {
        int min = Integer.MAX_VALUE;
        Position hexagon = null;
        for (Map.Entry<Position, Position> pos : posToCoord.entrySet()) {
            int distance = (pos.getValue().getX() - x) * (pos.getValue().getX() - x) + (pos.getValue().getY() - y) * (pos.getValue().getY() - y);
            if (distance <= min) {
                hexagon = pos.getKey();
                min = distance;
            }
        }
        return hexagon;
    }

    //region interaction

    public void calculateNextState() {
        life.calculateNextState();
        posToCoord.forEach( (key, value) -> {
            if (life.getCellState( key.getX(), key.getY() )) {
                painter.paintHexagon( value.getX(), value.getY(), LIVE_CELL_COLOR, hexField );
            } else {
                painter.paintHexagon( value.getX(), value.getY(), DEAD_CELL_COLOR, hexField );
            }
        } );
        field.getGraphics().drawImage( hexField, 0, 0, field );
    }

    public void switchXORMode() {
        xorMode = !xorMode;
    }

    public void clear() {
        life.clear();
        posToCoord.forEach( (key, value) -> painter.paintHexagon( value.getX(), value.getY(), DEAD_CELL_COLOR, hexField ) );
        field.getGraphics().drawImage( hexField,0,0,field );
    }

    //endregion
}