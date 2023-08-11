/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.trie;

import TDA.Tree;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author Kenny
 */
public class FXMLController implements Initializable {

    @FXML
    private AnchorPane Pane;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Button ButtonInsert;
    @FXML
    private Button ButtonDelete;
    @FXML
    private Button ButtonFind;
    @FXML
    private Button ButtonSave;
    @FXML
    private Button ButtonLoad;
    @FXML
    private Button ButtonStats;

    Tree<String> trie = new Tree<>("root");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void Insert(ActionEvent event) {
        if (textFieldSearch.getText().isEmpty()) {
            showError();
        } else {
            String texto = textFieldSearch.getText();
            List<String> CharacterList = new ArrayList<>(Arrays.asList(texto.split("")));
            trie.Insert(CharacterList);
            
            CreateNodeCircle(90,250,textFieldSearch.getText());
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        if (textFieldSearch.getText().isEmpty()) {
            showError();
        } else {

        }
    }

    @FXML
    void Find(ActionEvent event) {
        if (textFieldSearch.getText().isEmpty()) {
            showError();
        } else {

        }
    }

    public void showError() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Barra de texto vacia");
        alert.show();
    }

    public void CreateNodeCircle(double x, double y, String text) {
        Circle c1 = new Circle(x, y, 35);
        c1.setFill(Color.AQUA);
        c1.setStroke(Color.BLACK);

        Text t1 = new Text((c1.getCenterX()-12), c1.getCenterY(), text);

        Pane.getChildren().addAll(c1, t1);
    }

}
