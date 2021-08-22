package jtimer.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.stage.StageStyle;
import jtimer.service.Timer;
import javafx.scene.text.Text;

public class RootController {

    Timer timer;
    Thread background;

    @FXML
    private AnchorPane timerInputPanel;

    @FXML
    private Arc hourTimeLine;

    @FXML
    private Arc minTimeLine;

    @FXML
    private Arc secTimeLine;

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
        int h = Integer.parseInt(hoursTextField.getText());
        int m = Integer.parseInt(minutesTextField.getText());
        int s = Integer.parseInt(secondsTextField.getText());
        if(!hoursTextField.getText().isEmpty() && !minutesTextField.getText().isEmpty() && !secondsTextField.getText().isEmpty()) {
            timerInputPanel.setDisable(true);
            timerInputPanel.setOpacity(0);
            startButton.setDisable(true);
            resetButton.setDisable(false);
            background = new Thread(() -> Platform.runLater(() -> {
                timer = new Timer(h, m, s, hourTimeLine, minTimeLine, secTimeLine, timerText);
                timerInputPanel.setDisable(true);
                timerInputPanel.setOpacity(0);
                timer.start();
            }));
            background.setDaemon(true);
            background.start();
        } else {
            showWarningAlert("Please set time fields!");
        }
    }

    @FXML
    void resetButton() {
        background.interrupt();
        timer.interrupt();
        timerText.setText("");
        timerInputPanel.setDisable(false);
        timerInputPanel.setOpacity(1);
        startButton.setDisable(false);
        resetButton.setDisable(true);
    }

    @FXML
    void initialize() {
        resetButton.setDisable(true);
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

}
