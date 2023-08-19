/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.trie;

import TDA.Tree;
import TDA.TreeNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author Kenny
 */
public class FXMLController implements Initializable {

    @FXML
    private AnchorPane Pane;
    @FXML
    private AnchorPane PaneGlobal;
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
    @FXML
    private ListView<String> suggestionsListView = new ListView<>();

    Tree<String> trie = new Tree<>("");

    private double xOffset = 400;
    private double yOffset = 50;
    private double xSpacing = 60;
    private double ySpacing = 100;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            suggestionsListView.setVisible(true);
            List<String> suggestions = suggestWords(newValue.toLowerCase());
            suggestionsListView.setItems(FXCollections.observableArrayList(suggestions));
        });

        suggestionsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                String selectedWord = suggestionsListView.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    textFieldSearch.setText(selectedWord);
                }
            }
        });
    }

    @FXML
    void Insert(ActionEvent event) {
        if (textFieldSearch.getText().isEmpty()) {
            showError();
        } else {
            String texto = textFieldSearch.getText();
            List<String> CharacterList = new ArrayList<>(Arrays.asList(texto.split("")));
            trie.insert(CharacterList);
            Pane.getChildren().clear();
            BuildTree(trie.getRoot(), xOffset, yOffset);

        }
        textFieldSearch.clear();
        suggestionsListView.setVisible(false);
    }

    @FXML
    void Delete(ActionEvent event) {
        if (textFieldSearch.getText().isEmpty()) {
            showError();
        } else {
            String texto = textFieldSearch.getText();
            List<String> CharacterList = new ArrayList<>(Arrays.asList(texto.split("")));
            trie.delete(CharacterList);
            Pane.getChildren().clear();
            BuildTree(trie.getRoot(), xOffset, yOffset);
        }
        textFieldSearch.clear();
        suggestionsListView.setVisible(false);
    }

    @FXML
    void Find(ActionEvent event) {

        if (textFieldSearch.getText().isEmpty()) {
            showError();
        } else {
            String texto = textFieldSearch.getText();
            List<String> CharacterList = new ArrayList<>(Arrays.asList(texto.split("")));
            boolean found = trie.find(CharacterList);
            Pane.getChildren().clear();

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);

            if (found == true) {
                alert.setContentText("Palabra encontrada con éxito");
                alert.show();
            } else {
                alert.setContentText("Palabra no existente");
                alert.show();
            }
            BuildTree(trie.getRoot(), xOffset, yOffset);
        }
        textFieldSearch.clear();
        suggestionsListView.setVisible(false);
    }

    public void showError() {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Barra de texto vacia");
        alert.show();
    }

    public void BuildTree(TreeNode<String> node, double x, double y) {
        Circle circle = new Circle(x, y, 20, Color.WHITE); // White fill color
        circle.setStroke(Color.BLACK); // Black border color
        Pane.getChildren().add(circle);

        Text text = new Text(x - 6, y + 5, node.getContent()); // Adjust text position
        text.setFont(Font.font(14)); // Adjust font size
        Pane.getChildren().add(text);

        if (!node.getChildren().isEmpty()) {
            double childX = x - xSpacing * (node.getChildren().size() - 1) / 2.0;
            double childY = y + ySpacing;
            for (Tree<String> childTree : node.getChildren()) {
                Line line = new Line(x, y + 20, childX, childY - 20);
                Pane.getChildren().add(line);

                BuildTree(childTree.getRoot(), childX, childY);
                childX += xSpacing;
            }
        }
    }

    private TreeNode<String> findPrefixNode(TreeNode<String> node, String prefix, int index) {
        if (index >= prefix.length()) {
            return node;
        }

        String currentValue = String.valueOf(prefix.charAt(index));
        TreeNode<String> childNode = null;

        for (Tree<String> childTree : node.getChildren()) {
            if (childTree.getRoot().getContent().equals(currentValue)) {
                childNode = childTree.getRoot();
                break;
            }
        }

        if (childNode != null) {
            return findPrefixNode(childNode, prefix, index + 1);
        } else {
            return null;
        }
    }

    private List<String> suggestWords(String prefix) {
        List<String> suggestions = new ArrayList<>();
        TreeNode<String> prefixNode = findPrefixNode(trie.getRoot(), prefix, 0);

        if (prefixNode != null) {
            List<String> currentPath = new ArrayList<>();
            currentPath.add(prefixNode.getContent());
            findWords(prefixNode, currentPath, suggestions);
        }

        return suggestions;
    }

    private void findWords(TreeNode<String> node, List<String> currentPath, List<String> suggestions) {
        if (!node.getContent().isEmpty()) {
            suggestions.add(String.join("", currentPath));
        }

        for (Tree<String> childTree : node.getChildren()) {
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add(childTree.getRoot().getContent());
            findWords(childTree.getRoot(), newPath, suggestions);
        }
    }

    @FXML
    void SaveWords(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Guardado con éxito!");

        saveWordsToFile(trie, "Words.txt", "src/main/resources");

        alert.show();
    }

    @FXML
    void Load(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Diccionario cargado con éxito!");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Diccionario");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt File", "*.txt"));
        fileChooser.setInitialDirectory(new File("src/main/resources"));
        File archivo = fileChooser.showOpenDialog(null);

        loadWordsFromFile(trie, archivo);
        BuildTree(trie.getRoot(), xOffset, yOffset);

        alert.show();
    }

    private void saveWordsToFile(Tree<String> tree, String fileName, String filePath) {
        String fullPath = filePath + File.separator + fileName;

        try (FileWriter fileWriter = new FileWriter(fullPath)) {
            List<String> words = getWords(tree.getRoot(), "");
            for (String word : words) {
                if (!word.isEmpty()) {
                    fileWriter.write(word + "\n");
                }
            }
            System.out.println("Words saved to file: " + fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getWords(TreeNode<String> node, String currentWord) {
        List<String> words = new ArrayList<>();
        String newWord = currentWord + node.getContent();

        if (!newWord.isEmpty() && node.getChildren().isEmpty()) {
            words.add(newWord);
        }

        for (Tree<String> childTree : node.getChildren()) {
            words.addAll(getWords(childTree.getRoot(), newWord));
        }

        return words;
    }

    private void loadWordsFromFile(Tree<String> tree, File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    List<String> letters = Arrays.asList(word.split(""));
                    tree.insert(letters);
                }
            }
            System.out.println("Words loaded from file: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
