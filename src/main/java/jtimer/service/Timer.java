package jtimer.service;

import javafx.scene.shape.Arc;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer extends Thread {

    LocalTime lt;

    private int h;
    private int m;
    private int s;

    private Arc hourTimeLine;
    private Arc minTimeLine;
    private Arc secTimeLine;
    private Text timerText;

    public Timer(int h, int m, int s, Arc hourTimeLine, Arc minTimeLine, Arc secTimeLine, Text timerText) {
        this.h = h;
        this.m = m;
        this.s = s;
        this.timerText = timerText;
        this.hourTimeLine = hourTimeLine;
        this.minTimeLine = minTimeLine;
        this.secTimeLine = secTimeLine;
        setDaemon(true);
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        lt = LocalTime.of(h, m, s);
        while(true) {
            try {
                timerText.setText(lt.format(dtf));
                lt = lt.minusSeconds(1);
                sleep(1000);
                if(lt.getHour() + lt.getMinute() + lt.getSecond() == 0) {
                    timerText.setText(lt.format(dtf));
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
