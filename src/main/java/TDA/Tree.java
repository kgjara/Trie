/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDA;

import java.util.List;

/**
 *
 * @author Kenny
 */
public class Tree<T> {

    private TreeNode<T> root;

    public Tree() {
        this.root = null;
    }

    public Tree(T content) {
        this.root = new TreeNode<>(content);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    /*
    public T getRoot() {
        if (!isEmpty()) {
            return root.getContent();
        }
        return null;
    }*/
    public TreeNode<T> getRoot() {
        if (!isEmpty()) {
            return root;
        }
        return null;
    }

    public void setRoot(T content) {
        if (!isEmpty()) {
            this.root.setContent(content);
        }
    }

    /*
    public void Insert(List<T> elements) {

        TreeNode<T> currentNode = root;

        for (T content : elements) {
            boolean found = false;

            for (Tree<T> childTree : currentNode.getChildren()) {
                if (childTree.getRoot().getContent().equals(content)) {
                    currentNode = childTree.getRoot();
                    found = true;
                    break;
                }
            }
            if (!found) {
                Tree<T> newChildTree = new Tree<>(content);
                currentNode.addChild(newChildTree);
                currentNode = newChildTree.getRoot();
            } else {
                currentNode = currentNode.getChildren().get(0).getRoot(); // Use the existing child
            }
        }

    }*/

    public void Insert(List<T> contentList) {
        TreeNode<T> currentNode = root;

        for (T content : contentList) {
            boolean found = false;

            for (Tree<T> childTree : currentNode.getChildren()) {
                if (childTree.getRoot().getContent().equals(content)) {
                    currentNode = childTree.getRoot();
                    found = true;
                    break;
                }
            }

            if (!found) {
                currentNode.addChild(content);
                currentNode = currentNode.getChildren().get(currentNode.getChildren().size() - 1).getRoot();
            }
        }
    }

    public boolean find(List<T> contentList) {
        TreeNode<T> currentNode = root;

        for (T content : contentList) {
            boolean found = false;

            for (Tree<T> childTree : currentNode.getChildren()) {
                if (childTree.getRoot().getContent().equals(content)) {
                    currentNode = childTree.getRoot();
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    public boolean delete(List<T> contentList) {
        return deleteRecursive(root, contentList, 0);
    }

    private boolean deleteRecursive(TreeNode<T> currentNode, List<T> contentList, int index) {
        if (index == contentList.size()) {
            return true; // All elements of the contentList are matched, this is the node to delete
        }

        for (Tree<T> childTree : currentNode.getChildren()) {
            if (childTree.getRoot().getContent().equals(contentList.get(index))) {
                boolean deleted = deleteRecursive(childTree.getRoot(), contentList, index + 1);
                if (deleted && index == contentList.size() - 1) {
                    if (childTree.getRoot().getChildren().size() > 0) {
                        childTree.getRoot().getChildren().clear(); // Remove all children
                    } else {
                        currentNode.getChildren().remove(childTree); // Remove the child itself
                    }
                }
                return deleted;
            }
        }

        return false; // Element not found
    }

    public void printTree(TreeNode<T> node, String prefix) {
        if (node == null) {
            return;
        }

        System.out.println(prefix + node.getContent());
        List<Tree<T>> childTrees = node.getChildren();
        for (Tree<T> childTree : childTrees) {
            printTree(childTree.getRoot(), prefix + "  ");
        }
    }

}
