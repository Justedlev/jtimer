package jtimer.service;

import javafx.scene.shape.Arc;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer extends Thread {

    LocalTime time;

    private Arc hourTimeLine;
    private Arc minTimeLine;
    private Arc secTimeLine;
    private Text timerText;

    public Timer(LocalTime time, Arc hourTimeLine, Arc minTimeLine, Arc secTimeLine, Text timerText) {
        this.time = time;
        this.timerText = timerText;
        this.hourTimeLine = hourTimeLine;
        this.minTimeLine = minTimeLine;
        this.secTimeLine = secTimeLine;
        setDaemon(true);
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        while(true) {
            try {
                timerText.setText(time.format(dtf));
                time = time.minusSeconds(1);
                sleep(1000);
                if(time.getHour() + time.getMinute() + time.getSecond() == 0) {
                    timerText.setText(time.format(dtf));
                    sleep(1000);
                    timerText.setText("TIME IS OUT!");
                    return;
                }
            } catch (InterruptedException e) {
                timerText.setText("");
                return;
            }
        }
    }

}
