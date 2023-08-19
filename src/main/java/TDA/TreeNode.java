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

    /*
    public boolean addChild(Tree<T> child) {
        return this.children.add(child);
    }

    public boolean addChild(T content) {
        Tree<T> child = new Tree<>(content);
        return this.children.add(child);
    }*/
    public void insert(List<T> values) {
        if (values.isEmpty()) {
            return;
        }

        T firstValue = values.get(0);
        Tree<T> childTree = getChildTree(firstValue);
        if (childTree == null) {
            childTree = new Tree<>(firstValue);
            children.add(childTree);
        }

        childTree.getRoot().insert(values.subList(1, values.size()));
    }

    public void delete(List<T> values) {
        if (values.isEmpty()) {
            children.clear();
            return;
        }

        T firstValue = values.get(0);
        Tree<T> childTree = getChildTree(firstValue);
        if (childTree != null) {
            childTree.getRoot().delete(values.subList(1, values.size()));
            if (childTree.getRoot().getChildren().isEmpty()) {
                children.remove(childTree);
            }
        }
    }

    public boolean find(List<T> values) {
        if (values.isEmpty()) {
            return true;
        }

        T firstValue = values.get(0);
        Tree<T> childTree = getChildTree(firstValue);
        if (childTree != null) {
            return childTree.getRoot().find(values.subList(1, values.size()));
        }

        return false;
    }

    private Tree<T> getChildTree(T value) {
        for (Tree<T> childTree : children) {
            if (childTree.getRoot().getContent().equals(value)) {
                return childTree;
            }
        }
        return null;
    }
    

    public void clearChildren() {
        children.clear();
    }

}
