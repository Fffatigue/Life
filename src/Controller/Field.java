package Controller;

import Model.Life;
import Util.Position;
import Util.IllegalParameterException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static java.lang.Thread.sleep;

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
    private BufferedImage hexField;
    private BufferedImage impactImage;
    private Map<Position, Position> posToCoord;

    private boolean showImpact = false;
    private boolean running = false;
    private Timer timer;
    private boolean mousePressed = false;
    private boolean xorMode = false;
    private Position currentHexagon = null;

    //endregion

    //region Field parameters getters

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public int getW() {
        return w;
    }

    //endregion

    //region Model parameters

    public double getLiveBegin() {
        return liveBegin;
    }

    public double getLiveEnd() {
        return liveEnd;
    }

    public double getBirthBegin() {
        return birthBegin;
    }

    public double getBirthEnd() {
        return birthEnd;
    }

    public double getFstImpact() {
        return fstImpact;
    }

    public double getSndImpact() {
        return sndImpact;
    }

    private double liveBegin = 2.0;
    private double liveEnd = 3.3;
    private double birthBegin = 2.3;
    private double birthEnd = 2.9;
    private double fstImpact = 1.0;
    private double sndImpact = 0.3;

    //endregion

    //region Defines

    private static final int BORDER_COLOR = Color.black.getRGB();
    private static final int DEAD_CELL_COLOR = Color.lightGray.getRGB();
    private static final int LIVE_CELL_COLOR = Color.cyan.getRGB();

    //endregion

    //region Listeners

    public Field() {
        setListeners();
    }

    private void setListeners() {
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (running) {
                    JOptionPane.showMessageDialog( null, "Can't do this while run" );
                    return;
                }
                mousePressed = true;
                onHexagonPressing( e.getX(), e.getY() );
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
                currentHexagon = null;
            }
        } );

        addMouseMotionListener( new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                moveAndDragAction( e );
            }

            public void mouseDragged(MouseEvent e) {
                moveAndDragAction( e );
            }
        } );
    }

    private void moveAndDragAction(MouseEvent e) {
        if (mousePressed) {
            onHexagonPressing( e.getX(), e.getY() );
        }
    }

    //endregion

    public void paintComponent(Graphics g) {
        super.paintComponent( g );
        g.drawImage( hexField, 0, 0, null );
        if (showImpact) {
            paintImpacts();
            g.drawImage( impactImage, 0, 0, null );
        }
    }

    //region Field painting

    private void paintPanel(Graphics g) {
        g.setColor( new Color( BORDER_COLOR ) );
        double offsetX;
        double offsetY = k / (double) 2;
        double stepX = k * Math.sqrt( 3 );
        double stepY = k + k / (double) 2;
        for (int y = 0; y < n; y++) {
            if (y % 2 != 0) {
                offsetX = stepX / 2;
            } else {
                offsetX = 0;
            }
            for (int x = 0; x < m - (y % 2); x++) {
                painter.drawHexagon( x * stepX + offsetX, y * stepY + offsetY, k, g );
                int hexagonCenterX = (int) (x * stepX + offsetX + stepX / 2);
                int hexagonCenterY = (int) (y * stepY + offsetY + offsetY);
                painter.paintHexagon( hexagonCenterX, hexagonCenterY, DEAD_CELL_COLOR, hexField );
                posToCoord.put( new Position( x, y ), new Position( hexagonCenterX, hexagonCenterY ) );
            }
        }
    }

    private void paintImpacts() {
        Graphics graphics = impactImage.getGraphics();
        ((Graphics2D) graphics).setComposite( AlphaComposite.Clear );
        graphics.fillRect( 0, 0, impactImage.getWidth(), impactImage.getHeight() );
        ((Graphics2D) graphics).setComposite( AlphaComposite.Src );
        for (Map.Entry<Position, Position> pos : posToCoord.entrySet()) {
            double impact = life.getCellImpact( pos.getKey().getX(), pos.getKey().getY() );
            painter.drawImpact( pos.getValue().getX() - (int) (k * Math.sqrt( 3 ) / 4), pos.getValue().getY() + (int) (k + k / (double) 2) / 8, impact, graphics, (int) (k * Math.sqrt( 3 ) / 3) );
        }
    }

    //endregion


    private void onHexagonPressing(int x, int y) {
        if (running) {
            JOptionPane.showMessageDialog( null, "Can't do this while run" );
            return;
        }
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
        repaint();
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

    //region Interaction

    public void run() {
        if (running) {
            timer.cancel();
            running = false;
        } else {
            running = true;
            timer = new Timer();
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    utilCalculateNextState();
                }
            }, 0, 1000 );
        }
    }

    private void stop() {
        running = false;
        timer.cancel();
    }

    public void calculateNextState() {
        if (running) {
            JOptionPane.showMessageDialog( null, "Can't do this while run" );
            return;
        }
        utilCalculateNextState();
    }

    private void utilCalculateNextState() {
        life.calculateNextState();
        posToCoord.forEach( (key, value) -> {
            if (life.getCellState( key.getX(), key.getY() )) {
                painter.paintHexagon( value.getX(), value.getY(), LIVE_CELL_COLOR, hexField );
            } else {
                painter.paintHexagon( value.getX(), value.getY(), DEAD_CELL_COLOR, hexField );
            }
        } );
        repaint();
    }

    public void switchXORMode() {
        xorMode = !xorMode;
    }

    public void switchImpact() {
        showImpact = !showImpact;
        repaint();
    }

    public void clear() {
        if (running) {
            JOptionPane.showMessageDialog( null, "Can't do this while run" );
            return;
        }
        life.clear();
        posToCoord.forEach( (key, value) -> painter.paintHexagon( value.getX(), value.getY(), DEAD_CELL_COLOR, hexField ) );
        repaint();
    }

    public void setSettings(int m, int n, int k, int w, boolean xorMode) throws IllegalParameterException {
        if (m < 1 || m > 100) {
            throw new IllegalParameterException( "m must be between 1 and 100" );
        }
        if (n < 1 || n > 100) {
            throw new IllegalParameterException( "n must be between 1 and 100" );
        }
        if (k < 5 || k > 100) {
            throw new IllegalParameterException( "k must be between 5 and 100" );
        }
        if (w < 1 || w > 20) {
            throw new IllegalParameterException( "m must be between 1 and 20" );
        }

        if (running) {
            stop();
        }

        if (hexField != null) {
            clear();
        }
        this.xorMode = xorMode;
        this.m = m;
        this.n = n;
        this.k = k;
        this.w = w;
        life = new Life( m, n );
        life.setModelParameters( liveBegin, liveEnd, birthBegin, birthEnd, fstImpact, sndImpact );
        maxX = (int) ((m + 1) * w + m * Math.sqrt( 3 ) * k);
        maxY = n * k * 2;

        setPreferredSize( new Dimension( maxX, maxY ) );

        painter = new Painter( maxX, maxY );
        hexField = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_ARGB );
        impactImage = new BufferedImage( maxX, maxY, BufferedImage.TYPE_INT_ARGB );
        posToCoord = new HashMap<>();
        Graphics fieldGraphics = hexField.getGraphics();
        ((Graphics2D) fieldGraphics).setStroke( new BasicStroke( w ) );
        paintPanel( fieldGraphics );
    }

    public void loadGame(File saveFile) throws IllegalParameterException {
        try (BufferedReader reader = new BufferedReader( new FileReader( saveFile ) )) {
            String str = reader.readLine();
            while (str.trim().startsWith( "//" )) {
                str = reader.readLine();
            }
            String[] param = str.trim().split( "\\s+" );
            int m = Integer.parseInt( param[0].trim().split( "//" )[0] );
            int n = Integer.parseInt( param[1].trim().split( "//" )[0] );
            str = reader.readLine();
            while (str.trim().startsWith( "//" )) {
                str = reader.readLine();
            }
            param = str.trim().split( "\\s+" );
            int w = Integer.parseInt( param[0].trim().split( "//" )[0] );
            str = reader.readLine();
            while (str.trim().startsWith( "//" )) {
                str = reader.readLine();
            }
            param = str.trim().split( "\\s+" );
            int k = Integer.parseInt( param[0].trim().split( "//" )[0] );
            str = reader.readLine();
            while (str.trim().startsWith( "//" )) {
                str = reader.readLine();
            }
            if (running) {
                stop();
            }
            setSettings( m, n, k, w, false );
            param = str.trim().split( "\\s+" );
            int q = Integer.parseInt( param[0].trim().split( "//" )[0] );
            for (int i = 0; i < q; i++) {
                str = reader.readLine();
                while (str.trim().startsWith( "//" )) {
                    str = reader.readLine();
                }
                param = str.trim().split( "\\s+" );
                life.changeCellState( Integer.parseInt( param[0].trim().split( "//" )[0] ),
                        Integer.parseInt( param[1].trim().split( "//" )[0] ), true );
                Position coord = posToCoord.get( new Position( Integer.parseInt( param[0].trim().split( "//" )[0] ),
                        Integer.parseInt( param[1].trim().split( "//" )[0] ) ) );
                painter.paintHexagon( coord.getX(), coord.getY(), LIVE_CELL_COLOR, hexField );

            }
            repaint();
        } catch (IOException e) {
                JOptionPane.showMessageDialog( null, "Incorrect file" );
                return;
        }
    }

    public void saveGame(File saveFile) {
        try (BufferedWriter writer = new BufferedWriter( new FileWriter( saveFile ) )) {
            writer.write( m + " " + n );
            writer.newLine();
            writer.write( String.valueOf( w ) );
            writer.newLine();
            writer.write( String.valueOf( k ) );
            writer.newLine();
            List<Position> cells = life.getAllCellsState();
            writer.write( String.valueOf( cells.size() ) );
            writer.newLine();
            for (Position cell : cells) {
                writer.write( cell.getX() + " " + cell.getY() );
                writer.newLine();
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setModelParameters(double liveBegin, double liveEnd, double birthBegin, double birthEnd, double fstImpact, double sndImpact) throws IllegalParameterException {

        this.liveBegin = liveBegin;
        this.liveEnd = liveEnd;
        this.birthBegin = birthBegin;
        this.birthEnd = birthEnd;
        this.fstImpact = fstImpact;
        this.sndImpact = sndImpact;
        if (liveBegin < 0 || liveEnd < 0 || birthBegin < 0 || birthEnd < 0 || fstImpact < 0 || sndImpact < 0) {
            throw new IllegalParameterException( "All arguments must be no negative" );
        }
        if (liveBegin > birthBegin || birthBegin > birthEnd || birthEnd > liveEnd) {
            throw new IllegalParameterException( "Must LIVE_BEGIN≤BIRTH_BEGIN≤BIRTH_END≤LIVE_END" );
        }
        if (running) {
            stop();
        }
        life.setModelParameters( liveBegin, liveEnd, birthBegin, birthEnd, fstImpact, sndImpact );
    }



    //endregion
}