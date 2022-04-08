package bounceThread; 

import java.awt.geom.*;
import java.awt.Color;


public class Ball
{
   private static final int XSIZE = 15;
   private static final int YSIZE = 15;
   private double x = Pool.getRadius()+1; //нач корд
   private double y = Pool.getRadius()+1;
   private double dx = 1; //скорость
   private double dy = 1;
   private Color color;

   public Ball(Color color) {
      this.color = color;
   } 

   public boolean isInPool(java.util.List<Pool> pools) { 
      //проверка чи попав шарик в лунку
      for (Pool p: pools) {
         if(Math.sqrt((x-p.getCenterX())*(x-p.getCenterX())+(y-p.getCenterY())*(y-p.getCenterY())) < Pool.getRadius()) {
            return true;
         }
      }
      return false;
   }

   public void move(Rectangle2D bounds) //шв та напрям руху
   {
      x += dx;
      y += dy;
      if (x < 0)
      { 
         x = 0;
         dx = -dx;
      }
      if (x + XSIZE >= bounds.getWidth())
      {
         x = bounds.getWidth() - XSIZE; 
         dx = -dx; 
      }
      if (y < 0)
      {
         y = 0; 
         dy = -dy;
      }
      if (y + YSIZE >= bounds.getHeight())
      {
         y = bounds.getHeight() - YSIZE;
         dy = -dy; 
      }


   }

   public Color getColor() {return color;}

   public Ellipse2D getShape() //верт текстуру шара
   {
      Ellipse2D shape = new Ellipse2D.Double(x, y, XSIZE, YSIZE);
      return shape;
   }
}
