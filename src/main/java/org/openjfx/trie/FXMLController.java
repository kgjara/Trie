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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private double ySpacing = 80;

    private int CountTotalWords = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            suggestionsListView.setVisible(true);
            List<String> suggestions = suggestWords(newValue.toLowerCase());
            suggestionsListView.setItems(FXCollections.observableArrayList(suggestions));
        });
*/
        suggestionsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                String selectedWord = suggestionsListView.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    textFieldSearch.setText(selectedWord);
                }
            }
        });

        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            suggestionsListView.setVisible(true);

            if (newValue.endsWith(" ")) {
                String ending = newValue.trim();
                List<String> suggestions = suggestWordsEndingWith(ending.toLowerCase());
                suggestionsListView.setItems(FXCollections.observableArrayList(suggestions));
            } else {
                List<String> suggestions = suggestWords(newValue.toLowerCase());
                suggestionsListView.setItems(FXCollections.observableArrayList(suggestions));
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
        CountTotalWords++;
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

    private List<String> suggestWordsEndingWith(String ending) {
        List<String> suggestions = new ArrayList<>();
        if (!ending.isEmpty()) {
            TreeNode<String> endingNode = findEndingNode(trie.getRoot(), ending);
            if (endingNode != null) {
                List<String> currentPath = new ArrayList<>();
                findWordsEndingWith(endingNode, currentPath, suggestions, ending);
            }
        }
        return suggestions;
    }

    private TreeNode<String> findEndingNode(TreeNode<String> node, String ending) {
        if (ending.isEmpty()) {
            return node;
        }

        String lastChar = ending.substring(ending.length() - 1);
        for (Tree<String> childTree : node.getChildren()) {
            if (childTree.getRoot().getContent().equals(lastChar)) {
                return findEndingNode(childTree.getRoot(), ending.substring(0, ending.length() - 1));
            }
        }
        return null;
    }

    private void findWordsEndingWith(TreeNode<String> node, List<String> currentPath, List<String> suggestions, String ending) {
        String currentWord = String.join("", currentPath);

        if (!node.getContent().isEmpty()) {
            currentWord += node.getContent();
            if (currentWord.endsWith(ending)) {
                suggestions.add(currentWord);
            }
        }

        for (Tree<String> childTree : node.getChildren()) {
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add(childTree.getRoot().getContent());
            findWordsEndingWith(childTree.getRoot(), newPath, suggestions, ending);
        }
    }

    public void showStatisticsPanel() {
        // Crear un nuevo Pane para mostrar las estadísticas
        Pane statisticsPane = new Pane();

        // Obtener y mostrar las estadísticas
        Text totalWordsText = new Text("Total de palabras: " + CountTotalWords);
        totalWordsText.setLayoutY(20);
        statisticsPane.getChildren().add(totalWordsText);

        // Agregar más Text o elementos para mostrar otras estadísticas
        int yOffset = 50;
        Text lettersCountTitle = new Text("Palabras por cada letra del abecedario:");
        lettersCountTitle.setLayoutY(yOffset);
        statisticsPane.getChildren().add(lettersCountTitle);
        yOffset += 20;

        for (Tree<String> childTree : trie.getRoot().getChildren()) {
            String letter = childTree.getRoot().getContent();
            int wordsStartingWithLetter = countWordsStartingWithLetter(childTree.getRoot());
            if (wordsStartingWithLetter > 0) {
                Text letterCountText = new Text(letter + ": " + wordsStartingWithLetter + " palabra" + (wordsStartingWithLetter > 1 ? "s" : ""));
                letterCountText.setLayoutY(yOffset);
                statisticsPane.getChildren().add(letterCountText);

                yOffset += 20;
            }
        }
        // Crear una nueva ventana para mostrar el panel de estadísticas
        Stage statisticsStage = new Stage();
        Scene statisticsScene = new Scene(statisticsPane, 300, 200);
        statisticsStage.setScene(statisticsScene);
        statisticsStage.setTitle("Estadísticas");
        statisticsStage.show();
    }

    private int countWordsStartingWithLetter(TreeNode<String> node) {
        int count = node.getContent().isEmpty() ? 0 : 1;

        for (Tree<String> childTree : node.getChildren()) {
            count += countWordsStartingWithLetter(childTree.getRoot());
        }

        return count;
    }

}
