package bounceThread;

public class ScoreCounter { //счетчик очков
    private static int count = 0;

    private static final java.util.List<ScoreListener> listeners = new java.util.ArrayList<ScoreListener>();

    public static void incrementCount() {
        count++;
        for (ScoreListener hl : listeners) 
            hl.actionPerformed();
    }

    public static int getCount() {
        return count;
    }

    public static void addListener(ScoreListener listener) {
        listeners.add(listener);
    }
}
