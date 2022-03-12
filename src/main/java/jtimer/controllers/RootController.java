package jtimer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.stage.StageStyle;
import jtimer.service.Timer;
import javafx.scene.text.Text;

import java.time.DateTimeException;
import java.time.LocalTime;

public class RootController {

    Timer timer;
    Thread background;

    @FXML
    private AnchorPane timerInputPanel;

    @FXML
    private Arc timeLine;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Text timerText;

    @FXML
    private TextField hoursTextField;

    @FXML
    private TextField minutesTextField;

    @FXML
    private TextField secondsTextField;

    @FXML
    void startButton() {
        try {
            fixTimeFieldsIfEmpty();
            LocalTime lt = getTimeFromInput();
            timerInputPanel.setDisable(true);
            reverseTheButtons();
            background = new Thread(() -> {
                timer = new Timer(lt, timeLine, timerText);
                timer.start();
            });
            background.setDaemon(true);
            background.start();
        } catch (Exception e) {
            showWarningAlert(e.getMessage());
        }
    }

    @FXML
    void resetButton() {
        timer.interrupt();
        timerText.setText("");
        timerText.setFill(Color.rgb(31, 147, 255));
        timeLine.setStroke(Color.rgb(31, 147, 255));
        timeLine.setLength(0);
        timerInputPanel.setDisable(false);
        if(timer.isTimeInterrupted()) {
            setTime();
        }
        reverseTheButtons();
    }

    @FXML
    void initialize() {
        hoursTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                hoursTextField.setText(isNumber(newVal));
            }
        });
        minutesTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                minutesTextField.setText(isNumber(newVal));
            }
        });
        secondsTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                secondsTextField.setText(isNumber(newVal));
            }
        });
    }

    private void setTime() {
        LocalTime time = timer.getTime();
        hoursTextField.setText(String.valueOf(time.getHour()));
        minutesTextField.setText(String.valueOf(time.getMinute()));
        secondsTextField.setText(String.valueOf(time.getSecond()));
    }

    private void fixTimeFieldsIfEmpty() {
        if(hoursTextField.getText().isEmpty()) {
            hoursTextField.setText("0");
        }
        if(minutesTextField.getText().isEmpty()) {
            minutesTextField.setText("0");
        }
        if(secondsTextField.getText().isEmpty()) {
            secondsTextField.setText("0");
        }
    }

    private String isNumber(String value) {
        return value.replaceAll("[^\\d]", "");
    }

    private void reverseTheButtons() {
        startButton.setDisable(!startButton.isDisable());
        resetButton.setDisable(!resetButton.isDisable());
    }

    private void showWarningAlert(String explanation) {
        getAlert(Alert.AlertType.WARNING, "Warning!",
                explanation).showAndWait();
    }

    private Alert getAlert(Alert.AlertType type, String title, String explanation) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(explanation);
        return alert;
    }

    private LocalTime getTimeFromInput() throws NumberFormatException, DateTimeException {
        int h = Integer.parseInt(hoursTextField.getText());
        int m = Integer.parseInt(minutesTextField.getText());
        int s = Integer.parseInt(secondsTextField.getText());
        return LocalTime.of(h, m, s);
    }

}
