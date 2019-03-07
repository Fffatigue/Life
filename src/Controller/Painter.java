package Controller;

import Util.Position;

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

    void drawImpact(int x, int y, double impact, Graphics g, int fontSize){
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        g.setColor(Color.BLACK);
        g.drawString(String.format("%.1f", impact), x, y);
    }

    void paintHexagon(int x, int y, int newColor, BufferedImage hexField) {
        Queue<Position> q = new LinkedList<>();
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        int oldColor = hexField.getRGB( x, y );
        if (oldColor == newColor) {
            return;
        }
        q.add( new Position( x, y ) );
        while (!q.isEmpty()) {
            boolean upTrigger = false;
            boolean downTrigger = false;
            Position element = q.poll();
            x = element.getX();
            while (oldColor == hexField.getRGB( x, element.getY() ) && x >= 0) {
                hexField.setRGB( x, element.getY(), newColor );
                if (element.getY() < maxY - 1) {
                    if (oldColor == hexField.getRGB( x, element.getY() + 1 )) {
                        if (!upTrigger) {
                            q.add( new Position( x, element.getY() + 1 ) );
                            upTrigger = true;
                        }
                    } else {
                        upTrigger = false;
                    }
                }
                if (element.getY() > 0) {
                    if (oldColor == hexField.getRGB( x, element.getY() - 1 )) {
                        if (!downTrigger) {
                            q.add( new Position( x, element.getY() - 1 ) );
                            downTrigger = true;
                        }
                    } else {
                        downTrigger = false;
                    }
                }
                x--;
            }
            x = element.getX() + 1;
            upTrigger = false;
            downTrigger = false;

            while (oldColor == hexField.getRGB( x, element.getY() ) && x < maxX) {
                hexField.setRGB( x, element.getY(), newColor );
                if (element.getY() < maxY - 1) {
                    if (oldColor == hexField.getRGB( x, element.getY() + 1 )) {
                        if (!upTrigger) {
                            q.add( new Position( x, element.getY() + 1 ) );
                            upTrigger = true;
                        }
                    } else {
                        upTrigger = false;
                    }
                }
                if (element.getY() > 0) {
                    if (oldColor == hexField.getRGB( x, element.getY() - 1 )) {
                        if (!downTrigger) {
                            q.add( new Position( x, element.getY() - 1 ) );
                            downTrigger = true;
                        }
                    } else {
                        downTrigger = false;
                    }
                }
                x++;
            }
        }
    }

    //region Draw line

    private void drawLine(int xstart, int ystart, int xend, int yend, Graphics g) {
        ;
        int dx = Math.abs( xend - xstart );
        int dy = Math.abs( yend - ystart );
        int directionx = sign( xend - xstart );
        int directiony = sign( yend - ystart );
        if (dy < dx) {
            int error = 2 * dy - dx;
            int d1 = 2 * dy;
            int d2 = (dy - dx) * 2;
            g.drawLine( xstart, ystart, xstart, ystart );
            int x = xstart + directionx;
            int y = ystart;
            for (int i = 1; i <= dx; i++) {
                if (error > 0) {
                    error += d2;
                    y += directiony;
                } else {
                    error += d1;
                }
                g.drawLine( x, y, x, y );
                x += directionx;
            }
        } else {
            int d = 2 * dx - dy;
            int d1 = 2 * dx;
            int d2 = (dx - dy) * 2;
            g.drawLine( xstart, ystart, xstart, ystart );
            int x = xstart;
            int y = ystart + directiony;
            for (int i = 1; i <= dy; i++) {
                if (d > 0) {
                    d += d2;
                    x += directionx;
                } else {
                    d += d1;
                }
                g.drawLine( x, y, x, y );
                y += directiony;
            }
        }
    }

    private int sign(int x) {
        return Integer.compare( x, 0 );
    }

    //endregion

}
