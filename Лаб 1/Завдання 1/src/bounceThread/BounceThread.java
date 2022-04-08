package bounceThread;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BounceThread
{
   public static void main(String[] args)
   {
      
      EventQueue.invokeLater(() -> { //запуск очереді собитій які обраб
         JFrame frame = new BounceFrame();
         frame.setTitle("BounceThread");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      });
   }
}


class BounceFrame extends JFrame //окно з рамками і кнопками
{
   private BallComponent comp;
   public static final int STEPS = 100000;
   public static final int DELAY = 5;

   public BounceFrame() //иніц окна, созд лунки і кнопки
   {
      comp = new BallComponent();
      add(comp, BorderLayout.CENTER);
      initButtons();
      initLabels();
      initPools();
      pack();
   }

   private void initLabels() { //созданіє текстових полей (счет)
      JPanel labelPanel = new JPanel();
      JLabel scoreLabel = new JLabel("Score: " + ScoreCounter.getCount());
      labelPanel.add(scoreLabel);
      labelPanel.setBackground(Color.lightGray);
      ScoreCounter.addListener(
         new ScoreListener() {
            @Override
            public void actionPerformed() {
               scoreLabel.setText("Score: " + ScoreCounter.getCount());
               scoreLabel.repaint();
            }
         }
      );
      add(labelPanel, BorderLayout.NORTH);
   }

   private void initButtons() {
      JPanel buttonPanel = new JPanel();
      addButton(buttonPanel, "Start(Blue)", event -> addBlueBall());
      addButton(buttonPanel, "Start(Red)", event -> addRedBall());
      addButton(buttonPanel, "Start(Check priority)", event -> addManyBalls());
      addButton(buttonPanel, "Start(Check join)", event -> joinManyBalls());
      addButton(buttonPanel, "Close", event -> System.exit(0));
      add(buttonPanel, BorderLayout.SOUTH);
   }

   private void initPools() {
      Dimension size = comp.getPreferredSize();
      comp.add(new Pool(0 - Pool.getRadius(), 0-Pool.getRadius()));
      comp.add(new Pool(0 - Pool.getRadius(), (int)size.getHeight() - Pool.getRadius()));
      comp.add(new Pool((int)size.getWidth() - Pool.getRadius(), 0 - Pool.getRadius()));
      comp.add(new Pool((int)size.getWidth() - Pool.getRadius(), (int)size.getHeight() - Pool.getRadius()));
   }

   public void addButton(Container c, String title, ActionListener listener) //созд кнопки
   {
      JButton button = new JButton(title);
      c.add(button);
      button.addActionListener(listener);
   }

   private void addBall(Color color, int p) {
      Ball ball = new Ball(color);
      comp.add(ball);
      Runnable r = () -> { 
         try
         {  
            for (int i = 1; i <= STEPS; i++)
            {
               ball.move(comp.getBounds());
               if(ball.isInPool(comp.getPools())) {
                  ScoreCounter.incrementCount();
                  comp.removeInactive();
                  Thread.currentThread().interrupt();
               }
               comp.repaint();
               Thread.sleep(DELAY);
            }
         }
         catch (InterruptedException e)
         {
         }
      };
      Thread t = new Thread(r); //задаєм те що в потокі
      t.setPriority(p); //пріорітет кольорів
      t.start();
      System.out.println("Поток " + t.getName() + " запущен с приоритетом " + t.getPriority());
   }
   
   /**
    * Adds a bouncing ball to the canvas and starts a thread to make it bounce
    */
   public void addBlueBall()
   {
      addBall(Color.blue, Thread.MIN_PRIORITY);
   }

   public void addRedBall()
   {
      addBall(Color.red, Thread.MAX_PRIORITY);
   }

   public void addManyBalls() { //в циклі создаєм 100шаріков в отд потокі
      Runnable r = () -> {
         for (int i = 0; i < 99; i++) {
            addBlueBall();
            try{
               Thread.currentThread().sleep(50);
            } catch(InterruptedException e) {
               e.printStackTrace();
            }
         }
         addRedBall();
      };
      Thread t = new Thread(r);
      t.setPriority(Thread.NORM_PRIORITY);
      t.start();
   }

   private void joinBall(Color color) { //создаєм шаріки в потокі з методом джоін
      Ball ball = new Ball(color);
      comp.add(ball);
      Runnable r = () -> { 
         try
         {  
            for (int i = 1; i <= 400; i++)
            {
               ball.move(comp.getBounds());
               if(ball.isInPool(comp.getPools())) {
                  ScoreCounter.incrementCount();
                  comp.removeInactive();
                  Thread.currentThread().interrupt();
               }
               comp.repaint();
               Thread.sleep(DELAY);
            }
            comp.remove(ball);
         }
         catch (InterruptedException e)
         {
         }
      };
      Thread t = new Thread(r);
      t.setPriority(Thread.MIN_PRIORITY);
      t.start();
      try{
         t.join();
      } catch(InterruptedException e) {
         e.printStackTrace();
      }
      System.out.println("Поток " + t.getName() + " запущен с приоритетом " + t.getPriority());
   }

   public void joinManyBalls() { //в циклі визиваєм джоін бол
      Runnable r = () -> {
         for (int i = 0; i < 10; i++) {
            joinBall(i%2 == 0 ? Color.red: Color.blue);
            try{
               Thread.currentThread().sleep(50);
            } catch(InterruptedException e) {
               e.printStackTrace();
            }
         }
      };
      Thread t = new Thread(r);
      t.setPriority(Thread.NORM_PRIORITY);
      t.start();
   }
}
