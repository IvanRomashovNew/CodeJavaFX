package org.example.searadarapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.searadar.mr231.convert.Mr231Converter;
import org.example.searadar.mr231.station.Mr231StationType;
import org.example.searadar.mr231_3.convert.Mr231_3Converter;
import org.example.searadar.mr231_3.station.Mr231_3StationType;
import ru.oogis.searadar.api.message.SearadarStationMessage;

import java.sql.Connection;
import java.util.List;

import static java.sql.DriverManager.println;

public class HelloController {
    @FXML
    private ComboBox typeOfProtocolCB;
    @FXML
    private ComboBox typeOfMessagesCB;
    DbConnect dbConnect = new DbConnect();
    @FXML
    private TextField seaMessageTF;
    @FXML
    private  VBox vBox;
    Connection connection;
    @FXML
    protected void onAddDbButtonClick() {
        try {
            if (typeOfProtocolCB.getSelectionModel().getSelectedIndex() == 0) {
                String selectedProtocol = typeOfMessagesCB.getSelectionModel().getSelectedItem().toString();
                String mr231Input = seaMessageTF.getText();
                if (mr231Input.contains("$RA"+selectedProtocol)) {
                    Mr231StationType mr231 = new Mr231StationType();
                    Mr231Converter converter = mr231.createConverter();
                    List<SearadarStationMessage> searadarMessages = converter.convert(mr231Input);
                    String mr231Output = "";
                    for (SearadarStationMessage message : searadarMessages) {
                        mr231Output = message.toString();
                    }
                    dbConnect.AddNewMessage(connection, mr231Input, mr231Output, typeOfProtocolCB.getSelectionModel().getSelectedIndex() + 1,typeOfMessagesCB.getSelectionModel().getSelectedIndex() + 1);
                    seaMessageTF.setText("");
                    lastMessageView(vBox);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Произошла ошибка!");
                    alert.setContentText("Подробности: \nНачало предложения содержит ошибку! Проверте правильность написания сообщения(знак '$' в начале предложения, правильность протокола и регистр)!" );
                    alert.showAndWait();
                }
            }
            else if (typeOfProtocolCB.getSelectionModel().getSelectedIndex() == 1) {
                String mr231_3Input = seaMessageTF.getText();
                String selectedProtocol = typeOfMessagesCB.getSelectionModel().getSelectedItem().toString();
                if (mr231_3Input.contains("$RA"+selectedProtocol)) {
                    Mr231_3StationType mr231_3 = new Mr231_3StationType();
                    Mr231_3Converter converter = mr231_3.createConverter();
                    List<SearadarStationMessage> searadarMessages = converter.convert(mr231_3Input);
                    String mr231_3Output = "";
                    for (SearadarStationMessage message : searadarMessages) {
                        mr231_3Output = message.toString();
                    }
                    dbConnect.AddNewMessage(connection, mr231_3Input, mr231_3Output,typeOfProtocolCB.getSelectionModel().getSelectedIndex() + 1, typeOfMessagesCB.getSelectionModel().getSelectedIndex() + 1);
                    seaMessageTF.setText("");
                    lastMessageView(vBox);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Произошла ошибка!");
                    alert.setContentText("Подробности: \nНачало предложения содержит ошибку! Проверте правильность написания сообщения(знак '$' в начале предложения, правильность протокола и регистр)!" );
                    alert.showAndWait();
                }
            }
        }
        catch (Exception ex)  {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка! \nПроверьте подключение к БД и правильно ли вы написали сообщение!");
            alert.setContentText("Подробности: " + ex.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    protected void lastMessageView(VBox container) {
        container.getChildren().clear();
        Label firstLabel = new Label("Последние сообщения:");
        firstLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #FFFFFF;");
        Message message231 = dbConnect.LastMessageViewMr_231(connection);
        Message message231_3 = dbConnect.LastMessageViewMr_231_3(connection);
        VBox messageBox = new VBox();
        messageBox.getChildren().addAll(firstLabel);
        if (message231 != null) {
            Label protocolLabel = new Label("Использованный протокол:");
            protocolLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            Label protocolValueLabel = new Label(message231.getType_of_protocol());
            protocolValueLabel.setStyle(" -fx-text-fill:  #FFFFFF;");
            Label inputLabel = new Label("Полученное сообщение:" );
            inputLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            Label inputValueLabel = new Label(message231.getInput_message());
            inputValueLabel.setStyle(" -fx-text-fill:  #FFFFFF;");
            Label outputLabel = new Label("Расшифрованное сообщение:");
            outputLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            Label outputValueLabel = new Label(message231.getOutput_message());
            outputValueLabel.setStyle(" -fx-text-fill:  #FFFFFF;");
            messageBox.getChildren().addAll(protocolLabel, protocolValueLabel, inputLabel, inputValueLabel, outputLabel, outputValueLabel);
        }
        else {
            Label messageLabel = new Label("Нет сообщений для протокола МР-231");
            messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            messageBox.getChildren().add(messageLabel);
        }
        if (message231_3 != null) {
            Label protocolLabel = new Label("Использованный протокол:");
            protocolLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            Label protocolValueLabel = new Label(message231_3.getType_of_protocol());
            protocolValueLabel.setStyle(" -fx-text-fill:  #FFFFFF;");
            Label inputLabel = new Label("Полученное сообщение:");
            inputLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            Label inputValueLabel = new Label(message231_3.getInput_message());
            inputValueLabel.setStyle(" -fx-text-fill:  #FFFFFF;");
            Label outputLabel = new Label("Расшифрованное сообщение:");
            outputLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            Label outputValueLabel = new Label(message231_3.getOutput_message());
            outputValueLabel.setStyle(" -fx-text-fill:  #FFFFFF;");
            messageBox.getChildren().addAll(protocolLabel, protocolValueLabel, inputLabel, inputValueLabel, outputLabel, outputValueLabel);
        } else {
            Label messageLabel = new Label("Нет сообщений для протокола МР-231-3");
            messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFFFFF;");
            messageBox.getChildren().add(messageLabel);
        }
        container.getChildren().add(messageBox);
        messageBox.setAlignment(Pos.CENTER);
    }
    @FXML
    public void initialize() {
        ObservableList<String> typesOfProtocol = FXCollections.observableArrayList("МР-231","МР-231-3");
        ObservableList<String> protocolMr_231Options = FXCollections.observableArrayList("TTM", "VHW", "RSD");
        ObservableList<String> protocolMr_231_3Options = FXCollections.observableArrayList("TTM", "RSD");
        typeOfProtocolCB.getItems().addAll(typesOfProtocol);
        typeOfProtocolCB.setValue(typesOfProtocol.get(0));
        typeOfMessagesCB.getItems().addAll(protocolMr_231Options);
        typeOfMessagesCB.setValue(protocolMr_231Options.get(0));
        typeOfProtocolCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,newValue) -> {
            typeOfMessagesCB.getItems().clear();
            if (newValue.equals("МР-231")) {
                typeOfMessagesCB.getItems().addAll(protocolMr_231Options);
                typeOfMessagesCB.setValue(protocolMr_231Options.get(0));
            } else if (newValue.equals("МР-231-3")) {
                typeOfMessagesCB.getItems().addAll(protocolMr_231_3Options);
                typeOfMessagesCB.setValue(protocolMr_231_3Options.get(0));
            }
        });
        connection = dbConnect.ConnectionToDb();
        lastMessageView(vBox);
    }
}