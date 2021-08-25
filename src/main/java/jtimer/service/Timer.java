package jtimer.service;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer extends Thread {

    LocalTime time;

    private Arc timeLine;
    private Text timerText;

    public Timer(LocalTime time, Arc timeLine, Text timerText) {
        this.time = time;
        this.timerText = timerText;
        this.timeLine = timeLine;
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            double pieces = 360. / getTimeInSecond();
            for (int i = getTimeInSecond(); i >= 0; i--) {
                timerText.setText(time.format(dtf));
                sleep(1000);
                if (i != 0) {
                    time = time.minusSeconds(1);
                    timeLine.setLength(timeLine.getLength() + pieces);
                    if(timeLine.getLength() / 360 * 100 >= 75) {
                        timeLine.setStroke(Color.rgb(218, 64, 8));
                        timerText.setFill(Color.rgb(218, 64, 8));
                    }
                }
            }
            timerText.setText("TIME IS OUT!");
        } catch (InterruptedException e) {
            timerText.setText("");
        }
    }

    private int getTimeInSecond() {
        return (time.getHour() * 3600) + (time.getMinute() * 60) + time.getSecond();
    }

}
