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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        double parts = 360. / (time.getHour() != 0 ? time.getHour() * 3600 + time.getMinute() * 60 + time.getSecond() :
                time.getMinute() != 0 ? time.getMinute() * 60  + time.getSecond() :
                        time.getSecond() != 0 ? time.getSecond() : 0);
        while(true) {
            try {
                timerText.setText(time.format(dtf));
                time = time.minusSeconds(1);
                sleep(1000);
                timeLine.setLength(timeLine.getLength() - parts);
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
