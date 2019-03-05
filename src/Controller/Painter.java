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

    void paintHexagon(int x, int y, int newColor, BufferedImage hexField) {
        Queue<Position> q = new LinkedList<>();
        if (x < 0 || x >= maxX || y < 0 || y >= maxY) {
            return;
        }
        int oldColor = hexField.getRGB( x, y );
        if(oldColor == newColor){
            return;
        }
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

    //region Draw line



     private void drawLine(int xstart, int ystart, int xend, int yend, Graphics g) {

         int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

         dx = xend - xstart;//проекция на ось икс
         dy = yend - ystart;//проекция на ось игрек

         incx = sign(dx);
         /*
          * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
          * справа налево по иксу, то incx будет равен -1.
          * Это будет использоваться в цикле постороения.
          */
         incy = sign(dy);
         /*
          * Аналогично. Если рисуем отрезок снизу вверх -
          * это будет отрицательный сдвиг для y (иначе - положительный).
          */

         if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
         if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
         //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

         if (dx > dy)
         //определяем наклон отрезка:
         {
             /*
              * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
              * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
              * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
              * по y сдвиг такой отсутствует.
              */
             pdx = incx;	pdy = 0;
             es = dy;	el = dx;
         }
         else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
         {
             pdx = 0;	pdy = incy;
             es = dx;	el = dy;//тогда в цикле будем двигаться по y
         }

         x = xstart;
         y = ystart;
         err = el/2;
         g.drawLine (x, y, x, y);//ставим первую точку
         //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

         for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
         {
             err -= es;
             if (err < 0)
             {
                 err += el;
                 x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                 y += incy;//или сместить влево-вправо, если цикл проходит по y
             }
             else
             {
                 x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                 y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
             }

             g.drawLine (x, y, x, y);
         }

    }


    private int sign(int x) {
        return Integer.compare( x, 0 );
    }

    //endregion

}
