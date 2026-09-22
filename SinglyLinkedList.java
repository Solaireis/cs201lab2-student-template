import java.util.*;
 
public class SinglyLinkedList<E extends Comparable<E>> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;
 
    private static class Node<E> {
        private E element;
        private Node<E> next;
    
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }
    
        public E getElement(){
            return element;
        }
    
        public Node<E> getNext(){
            return next;
        }
    
        public void setNext(Node<E> n){
            next = n;
        }
    }
 
    public SinglyLinkedList(){
 
    }
 
    public int size(){
        return size;
    }
 
    public boolean isEmpty(){
        return size == 0;
    }
 
    public E first(){
        if (isEmpty()){
            return null;
        } 
        return head.getElement();
    }
 
    public E last(){
        if (isEmpty()){
            return null;
        }
        return tail.getElement();
    }
 
    public void addFirst(E e){
        head = new Node<>(e, head);
 
        if (isEmpty()){
            tail = head;
        }
        size++;
    }
 
    public void addLast(E e){
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()){
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }
 
    public E removeFirst(){
        if (isEmpty()){
            return null;
        }
 
        E answer = head.getElement();
        head = head.getNext();
        size--;
 
        if (isEmpty()){
            tail = null;
        }
        return answer;
    }
 
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }
 
    // write your codes here
    public void swap(){
        if (size < 2) {
            return;
        }
 
        // Step 1: collect every node in list order (position 0..size-1),
        // without touching Node's element field or adding to Node itself.
        @SuppressWarnings("unchecked")
        Node<E>[] nodesInOrder = new Node[size];
        Node<E> current = head;
        int i = 0;
        while (current != null) {
            nodesInOrder[i] = current;
            i++;
            current = current.getNext();
        }
 
        // Step 2: figure out, by VALUE, which original position holds the
        // smallest, 2nd smallest, ... largest element.
        Integer[] positionsByValue = new Integer[size];
        for (int p = 0; p < size; p++) {
            positionsByValue[p] = p;
        }
        Arrays.sort(positionsByValue, (a, b) ->
                nodesInOrder[a].getElement().compareTo(nodesInOrder[b].getElement()));
 
        // Step 3: pair up (smallest, largest), (2nd smallest, 2nd largest), ...
        // and swap the NODES that currently sit at those two original
        // positions (this reproduces "swap the values" without ever writing
        // to Node.element directly).
        Node<E>[] finalOrder = Arrays.copyOf(nodesInOrder, size);
        for (int k = 0; k < size / 2; k++) {
            int lowPos = positionsByValue[k];
            int highPos = positionsByValue[size - 1 - k];
            Node<E> temp = finalOrder[lowPos];
            finalOrder[lowPos] = finalOrder[highPos];
            finalOrder[highPos] = temp;
        }
        // If size is odd, the middle-ranked element's position is untouched.
 
        // Step 4: relink the list (and fix head/tail) to reflect the new order.
        head = finalOrder[0];
        Node<E> prev = head;
        for (int j = 1; j < size; j++) {
            prev.setNext(finalOrder[j]);
            prev = finalOrder[j];
        }
        prev.setNext(null);
        tail = prev;
    }
   
}