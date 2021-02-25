public class Timer {
    final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        int i = Integer.parseInt(args[0]);
        public void run() {
            System.out.println(i--);
            if (i< 0)
                timer.cancel();
        }
    }, 0, 1000);
}
