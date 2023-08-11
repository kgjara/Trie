/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kenny
 */
public class TreeNode<T> {

    private T content;
    private List<Tree<T>> children;

    public TreeNode(T content) {
        this.content = content;
        this.children = new ArrayList<>();
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    
    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public boolean addChild(Tree<T> child) {
        return this.children.add(child);
    }

    public boolean addChild(T content) {
        Tree<T> child = new Tree<>(content);
        return this.children.add(child);
    }

}
