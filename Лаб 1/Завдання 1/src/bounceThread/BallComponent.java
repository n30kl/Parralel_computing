package bounceThread;

import java.awt.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

public class BallComponent extends JComponent
 //класс окна
{
   private static final int DEFAULT_WIDTH = 640;
   private static final int DEFAULT_HEIGHT = 480;

   private java.util.List<Ball> balls = new CopyOnWriteArrayList<>();
   private java.util.List<Pool> pools = new ArrayList<>();

   public void add(Ball b)
   {
      balls.add(b);
   }

   public boolean removeInactive() { //удаляє ті які в лузу попали
      return balls.removeIf(element -> element.isInPool(pools));
   }
   public boolean remove(Ball b) { //удаляє конкр шарік
      return balls.remove(b);
   }

   public void add(Pool p) { //добавл лузу
      pools.add(p);
   }

   public java.util.List<Pool> getPools() { //вертає лузи
      return pools;
   }

   public void paintComponent(Graphics g) //отрисовка компонентов
   {
      Graphics2D g2 = (Graphics2D) g;
      for (Ball b : balls)
      {
         g2.setColor(b.getColor());;
         g2.fill(b.getShape());
      }

      for (Pool p: pools) {
         g2.setColor(Color.GRAY);
         g2.fill(p.getShape());
      }
   }
   
   public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); } //получить размер окна
}
