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
            int timeInSeconds = getTimeInSecond();
            double pieces = 360. / timeInSeconds;
            double quarter = 360 * 75 / 100.;
            boolean isColorChanged = false;
            for (int i = getTimeInSecond(); i >= 0; i--) {
                timerText.setText(time.format(dtf));
                sleep(1000);
                time = time.minusSeconds(1);
                timeLine.setLength(timeLine.getLength() + pieces);
                if(!isColorChanged && timeLine.getLength() >= quarter) {
                    isColorChanged = true;
                    timeLine.setStroke(Color.rgb(218, 64, 8));
                    timerText.setFill(Color.rgb(218, 64, 8));
                }
            }
            timerText.setText("Time is over!");
        } catch (InterruptedException e) {
            timerText.setText("");
        }
    }

    private int getTimeInSecond() {
        return (time.getHour() * 3600) + (time.getMinute() * 60) + time.getSecond();
    }

}
