/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TDA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kenny
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Tree<String> trie = new Tree<>("root");

        String texto1 = "hola";
        String texto2 = "holi";
        String texto3 = "bola";
        String texto4 = "coco";
        List<String> CharacterList1 = new ArrayList<>(Arrays.asList(texto1.split("")));
        List<String> CharacterList2 = new ArrayList<>(Arrays.asList(texto2.split("")));
        List<String> CharacterList3 = new ArrayList<>(Arrays.asList(texto3.split("")));
        List<String> CharacterList4 = new ArrayList<>(Arrays.asList(texto4.split("")));
/*
        System.out.println(CharacterList1);
        System.out.println(CharacterList2);
        System.out.println(CharacterList3);*/

        trie.insert(CharacterList1);
        trie.insert(CharacterList2);
        trie.insert(CharacterList3);
        
        trie.printTree(trie.getRoot(), "");
        System.out.println("FIND holi y bola ");
        System.out.println(trie.find(CharacterList2));
        System.out.println(trie.find(CharacterList3));
        System.out.println("DELETE holi y bola");
        System.out.println(trie.delete(CharacterList2));
        System.out.println(trie.delete(CharacterList3));
        System.out.println("FIND holi y bola");
        System.out.println(trie.find(CharacterList2));
        System.out.println(trie.find(CharacterList3));
        System.out.println("INSERT coco y holi");
        trie.insert(CharacterList4);
        trie.insert(CharacterList2);
        System.out.println("FIND holi y coco");
        System.out.println(trie.find(CharacterList2));
        System.out.println(trie.find(CharacterList4));
        
        trie.printTree(trie.getRoot(),"");
        /*
        List<List<String>> contentLists = new ArrayList<>();
        contentLists.add(Arrays.asList("h", "o", "l", "a"));
        contentLists.add(Arrays.asList("h", "o", "l", "i"));
        contentLists.add(Arrays.asList("h", "a", "b", "a"));
        contentLists.add(Arrays.asList("b", "o", "l", "a"));

        trie.insert(contentLists.get(0));
        trie.insert(contentLists.get(1));
        trie.insert(contentLists.get(2));
        trie.insert(contentLists.get(3));

        System.out.println(trie.find(contentLists.get(3)));
        System.out.println(trie.delete(contentLists.get(3)));
        System.out.println(trie.find(contentLists.get(3)));
        
        trie.printTree(trie.getRoot(), "");*/
    }

}
