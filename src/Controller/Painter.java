package Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

class Painter {

    //region Private fields

    private int maxX;
    private int maxY;

    //endregion


    Painter(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    void drawHexagon(double x, double y, int w, Graphics g) {
        double sqrt3 = Math.sqrt( 3 );
        /*left line*/
        drawLine( (int) (x), (int) (y),
                (int) (x), (int) (y + w), g );
        /*right line*/
        drawLine( (int) (x + w * sqrt3), (int) (y),
                (int) (x + w * sqrt3), (int) (y + w), g );
        /*top lines*/
        drawLine( (int) (x), (int) (y),
                (int) (x + w * sqrt3 / 2), (int) (y - w / 2D), g );
        drawLine( (int) (x + w * sqrt3), (int) (y),
                (int) (x + w * sqrt3 / 2), (int) (y - w / 2D), g );
        /*bottom lines*/
        drawLine( (int) (x), (int) (y + w),
                (int) (x + w * sqrt3 / 2), (int) (y + w + w / 2D), g );
        drawLine( (int) (x + w * sqrt3), (int) (y + w),
                (int) (x + w * sqrt3 / 2), (int) (y + w + w / 2D), g );
        /*painting in another color*/
    }

    //region Paint hexagon

    void paintHexagon(int x, int y, int newColor, BufferedImage hexField) {
        Queue<Position> q = new LinkedList<>();
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        int oldColor = hexField.getRGB( x, y );
        q.add( new Position( x, y ) );
        while (!q.isEmpty()) {
            Position element = q.poll();
            x = element.getX();
            while (oldColor == hexField.getRGB( x, element.getY() ) && x >= 0) {
                hexField.setRGB( x, element.getY(), newColor );
                if (element.getY() < maxY - 1) {
                    if (oldColor == hexField.getRGB( x, element.getY() + 1 )) {
                        q.add( new Position( x, element.getY() + 1 ) );
                    }
                }
                if (element.getY() > 0) {
                    if (oldColor == hexField.getRGB( x, element.getY() - 1 )) {
                        q.add( new Position( x, element.getY() - 1 ) );
                    }
                }
                x--;
            }
            x = element.getX() + 1;
            while (oldColor == hexField.getRGB( x, element.getY() ) && x < maxX) {
                hexField.setRGB( x, element.getY(), newColor );
                if (element.getY() < maxY - 1) {
                    if (oldColor == hexField.getRGB( x, element.getY() + 1 )) {
                        q.add( new Position( x, element.getY() + 1 ) );
                    }
                }
                if (element.getY() > 0) {
                    if (oldColor == hexField.getRGB( x, element.getY() - 1 )) {
                        q.add( new Position( x, element.getY() - 1 ) );
                    }
                }
                x++;
            }
        }
    }

    void paintHexagon1(int x, int y, int newColor, BufferedImage hexField) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        int oldColor = hexField.getRGB( x, y );
        hexField.setRGB( x, y, newColor );
        flood( x + 1, y, oldColor, newColor, hexField );
        flood( x, y + 1, oldColor, newColor, hexField );
        flood( x - 1, y, oldColor, newColor, hexField );
        flood( x, y - 1, oldColor, newColor, hexField );
    }


    private void flood(int x, int y, int oldColor, int newColor, BufferedImage hexField) {
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        if (hexField.getRGB( x, y ) == oldColor) {
            hexField.setRGB( x, y, newColor );
            flood( x + 1, y, oldColor, newColor, hexField );
            flood( x, y + 1, oldColor, newColor, hexField );
            flood( x - 1, y, oldColor, newColor, hexField );
            flood( x, y - 1, oldColor, newColor, hexField );
        }
    }

    //endregion

    //region Draw line

    private void drawLine(int xstart, int ystart, int xend, int yend, Graphics g) {
        int a = yend - ystart;
        int b = xstart - xend;
        int signA = sign( a );
        int signB = sign( b );
        int f = 0;
        int x = xstart;
        int y = ystart;
        g.drawLine( x, y, x, y );
        if (Math.abs( a ) < Math.abs( b )) {
            while (x != xend | y != yend) {
                f = f + a * signA;
                if (f > 0) {
                    f = f - b * signB;
                    y = y + signA;
                }
                x = x - signB;
                g.drawLine( x, y, x, y );
            }
        } else {
            while (x != xend | y != yend) {
                f = f + b * signB;
                if (f > 0) {
                    f = f - a * signA;
                    x = x - signB;
                }
                y = y + signA;
                g.drawLine( x, y, x, y );
            }
        }

    }


    private int sign(int x) {
        return Integer.compare( x, 0 );
    }

    //endregion

}
