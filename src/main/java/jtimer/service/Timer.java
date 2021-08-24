package jtimer.service;

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
            for (int i = (int) getTimeInSecond(); i >= 0; i--) {
                timerText.setText(time.format(dtf));
                sleep(1000);
                if (i != 0) {
                    time = time.minusSeconds(1);
                    timeLine.setLength(timeLine.getLength() - pieces);
                }
            }
            timerText.setText("TIME IS OUT!");
        } catch (InterruptedException e) {
            timerText.setText("");
        }
    }

    private double getTimeInSecond() {
        return time.getHour() != 0 ? time.getHour() * 3600 + time.getMinute() * 60 + time.getSecond() :
                time.getMinute() != 0 ? time.getMinute() * 60 + time.getSecond() :
                        time.getSecond() != 0 ? time.getSecond() : 0;
    }

}
