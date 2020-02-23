package asd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class s18819 {

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int execNumber;
        int index = 0;
        execNumber = Integer.parseInt(scanner.next());
        while (scanner.hasNext()) {
            avlTree.root = avlTree.insert(avlTree.root, Integer.parseInt(scanner.next()), index++);
        }
//        ADD:
//        ->dodanie elementu o wartości X-1 na pozycję P+1,
//        ->a następnie przesunięcie wskaźnika P o X elementów w prawo
//        -> gdzie X = P.value
//
//        DELETE:
//         -> X = (element który chcemy usunąć ). value
//         -> usunięcie elementu znajdującego się na pozycji P+1,
//         -> przesunięcie wskaźnika P o X elementów w prawo
//        wartosc %2 -> usuwanie
//        !wartosc %2 -> dodawanie
        int vector = 0;
        int currentValue;
        int move;
        for (int i = 0; i < execNumber; i++) {
            if (avlTree.root != null) {
                currentValue = avlTree.findElement(avlTree.root, vector).value;
                if (currentValue % 2 != 0) {
                    Node temp = avlTree.findElement(avlTree.root, vector);
                    avlTree.insert(avlTree.root, temp.value - 1, vector + 1);
                    move = temp.value - ((temp.value / avlTree.root.size) * avlTree.root.size);
                    if (vector == avlTree.root.size) {
                        if (move != 0) {
                            vector = move - 1;
                        } else
                            vector--;
                    } else if ((move + vector) > avlTree.root.size - 1) {
                        vector = (move + vector) - avlTree.root.size;
                    } else
                        vector += move;
                } else {
                    int tmpValue;
                    if (vector + 1 > avlTree.root.size - 1) {
                        tmpValue = avlTree.findElement(avlTree.root, 0).value;
                        avlTree.deleteNode(avlTree.root, 0);
                    } else {
                        tmpValue = avlTree.findElement(avlTree.root, vector + 1).value;
                        avlTree.deleteNode(avlTree.root, vector + 1);
                    }
                    if (avlTree.root == null) {
                        break;
                    } else {
                        move = tmpValue - ((tmpValue / avlTree.root.size) * avlTree.root.size);
                        if (vector == avlTree.root.size) {
                            if (move != 0) {
                                vector = move - 1;
                            } else
                                vector--;
                        } else if ((move + vector) > avlTree.root.size - 1) {
                            vector = (move + vector) - avlTree.root.size;
                        } else {
                            vector += move;
                        }
                    }
                }
            }
        }
        if (avlTree.root != null) {
            MyLinkedList list = new MyLinkedList();
            avlTree.inOrder(avlTree.root, list);
            LinkedNode start = list.head;
            for (int i = 0; i < vector; i++) {
                start = start.next;
            }
            list.printList(start);
        } else
            System.out.print("");

    }

}

class LinkedNode {
    int value;
    LinkedNode next;

    public LinkedNode(int value) {
        this.value = value;
        this.next = null;
    }

}

class MyLinkedList {
    int size;
    LinkedNode head;
    LinkedNode tail;

    public MyLinkedList() {
        size = 0;
    }

    void push(int value) {
        if (head == null) {
            head = new LinkedNode(value);
            tail = head;
        } else {
            tail.next = new LinkedNode(value);
            tail = tail.next;
        }
        size++;
    }

    void printList(LinkedNode start) {
        for (int i = 0; i < size; i++) {
            System.out.print(start.value + " ");
            if (start.equals(tail))
                start = head;
            else
                start = start.next;
        }
    }

}

class Node {
    int value,
            height,
            size;
    Node left, right;

    Node(int d) {
        value = d;
        height = 1;
        size = 1;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}

class AVLTree {

    Node root;

    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node rightRotate(Node x) {
        Node leftB = x.left;
        Node leftRight = leftB.right;
        if (x.equals(root)) {
            root = leftB;
            root.size = 1 + size(root.left) + size(root.right);
            root.height = max(height(root.left), height(root.right)) + 1;
        }
        leftB.right = x;
        x.left = leftRight;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = max(height(x.left), height(x.right)) + 1;
        leftB.size = 1 + size(leftB.left) + size(leftB.right);
        leftB.height = max(height(leftB.left), height(leftB.right)) + 1;
        return leftB;
    }

    int size(Node N) {
        if (N == null)
            return 0;

        return N.size;
    }

    int isNull(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }

//    If k = n, return the root node (since this is the zeroth node in the tree)
//    If n ≤ k, recursively look up the nth element in the left subtree.
//    Otherwise, look up the (n - k - 1)st element in the right subtree.

    Node findElement(Node node, int n) {
        Node current = node;
        if (isNull(current.left) == n) {
            return current;
        } else if (n <= isNull(current.left)) {
            return findElement(current.left, n);
        } else {
            return findElement(current.right, n - isNull(current.left) - 1);
        }


    }

    Node leftRotate(Node x) {
        Node rightB = x.right;
        Node rightLeft = rightB.left;
        if (x.equals(root)) {
            root = rightB;
            root.size = 1 + size(root.left) + size(root.right);
            root.height = max(height(root.left), height(root.right)) + 1;
        }
        rightB.left = x;
        x.right = rightLeft;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = max(height(x.left), height(x.right)) + 1;
        rightB.size = 1 + size(rightB.left) + size(rightB.right);
        rightB.height = max(height(rightB.left), height(rightB.right)) + 1;
        return rightB;

    }

    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }


    Node insert(Node node, int value, int index) {

        if (node == null)
            return (new Node(value));

        if (index <= size(node.left))
            node.left = insert(node.left, value, index);
        else if (index > size(node.left))
            node.right = insert(node.right, value, index - size(node.left) - 1);


        node.size = 1 + size(node.left) + size(node.right);
        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) < 0) {
            return leftRotate(node);
        }
        // lewo prawo
        if (balance > 1 && getBalance(node.left) <= 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // prawo lewo
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        // bez rotacji
        return node;
    }

    int isLeftNull(Node n) {
        if (n.left == null || n.right == null) {
            return 0;
        }
        return n.left.size + n.right.size + 1;
    }

    Node deleteNode(Node node, int index) {
        if (node == null)
            return null;
        if (index < size(node.left))
            node.left = deleteNode(node.left, index);
        else if (index > size(node.left))
            node.right = deleteNode(node.right, index - size(node.left) - 1);

        else {
            Node temp = null;
            if ((node.left == null) || (node.right == null)) {
                if (node.left == null)
                    temp = node.right;
                else
                    temp = node.left;

                if (temp == null) {
                    if (node.equals(root)) {
                        root = null;
                    }
                    // jedno dziecko
                    else {
                        temp = node;
                        node = null;
                    }
                } else {
                    if (node.equals(root)) {
                        node = temp;
                        root = node;
                    } else
                        node = temp;
                }
            } else {
                temp = findElement(node.right, 0);
                node.value = temp.value;
                node.right = deleteNode(node.right, 0);
            }
        }
        if (node == null)
            return null;
        
        node.size = size(node.left) + size(node.right) + 1;
        node.height = max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);


        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }


        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void inOrder(Node node, MyLinkedList list) {
        if (node.left != null) {
            inOrder(node.left, list);
        }
        list.push(node.value);
        if (node.right != null) {
            inOrder(node.right, list);
        }
    }
}


