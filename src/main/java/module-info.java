module org.openjfx.trie {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.trie to javafx.fxml;
    exports org.openjfx.trie;
}
