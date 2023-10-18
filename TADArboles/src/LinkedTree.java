import material.Position;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class represents a tree data structure using a linked implementation.
 * It implements the NAryTree interface.
 *
 * @param <E> the type of element stored in the tree
 */
public class LinkedTree<E> implements NAryTree<E> {

    /**
     * This class represents a node in a tree data structure.
     * It implements the Position interface.
     *
     * @param <T> the type of element stored in the node
     */
    private TreeNode<E> root;
    private int size;
    private class TreeNode<T> implements Position<T> {


        private List<TreeNode<T>> children = new ArrayList<>();
        private T element;
        private TreeNode<T> parent;

        public TreeNode(T element) {
            this.element = element;
        }

        public TreeNode(T element, TreeNode<T> parent) {
            this.element = element;
            this.parent = parent;
        }

        public TreeNode<T> getParent() {
            return parent;
        }

        @Override
        public T getElement() {
            return element;
        }
        public List<TreeNode<T>> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode<T>> children) {
            this.children = children;
        }
    }




    @Override
    public Position<E> addRoot(E e) {
        if(root == null){
            TreeNode<E> node = new TreeNode<>(e);
            root = node;
            size++;
            return  root;
        }else{throw new RuntimeException("El arbol no es vacio");}

    }
    private TreeNode<E> checkPosition(Position<E> p){
        if(!(p instanceof TreeNode)){
            throw new RuntimeException("The position is invalid");
        }
        return (TreeNode<E>) p;
    }
    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode parent = checkPosition(p);
        TreeNode newNode = new TreeNode(element,parent);
        parent.getChildren().add(newNode);
        size++;
        return newNode;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node = checkPosition(p);
        E old = node.getElement();
        node.element = e;
        return old;

    }

    @Override
    public void remove(Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        if (node == root){
            root = null;

        }else {
            TreeNode<E> parent = node.getParent();
            parent.getChildren().remove(node);
            size -= computeSize(node);
        }
    }

    private int computeSize(TreeNode<E> node) {
        int size = 1;
        for (TreeNode<E> child : node.getChildren()){
            size += computeSize(child);
        }return size;
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        LinkedTree<E> tree = new LinkedTree<>();
        tree.root = node;
        tree.size = computeSize(node);
        return tree;
    }
    private LinkedTree<E> checkTree(NAryTree<E> t){
        if (!(t instanceof LinkedTree)){
            throw new RuntimeException("The tree is invalid");
        }return (LinkedTree<E>) t;
    }
    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        TreeNode<E> node = checkPosition(p);
        LinkedTree<E> tree = checkTree(t);
        node.getChildren().addAll(tree.root.getChildren());
        size+= tree.size();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        TreeNode<E> node =checkPosition(v);
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
       TreeNode<E> node = checkPosition(v);
       return node.getChildren();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return (node.getParent() != null && node.getChildren() != null);
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getChildren().isEmpty();
    }

    @Override
    public boolean isRoot(Position<E> v) {
        return v.equals(this.root);
    }

    @Override
    public Iterator<Position<E>> iterator() {
        List<Position<E>> positions = new ArrayList<>();
        breathOrder(root, positions);

        return positions.iterator();
    }
    private Iterator<Position<E>> iteratorPreorder(){
        if (isEmpty()){
            return new ArrayList<Position<E>>().iterator();
        }
        List<Position<E>> positions = new ArrayList<>();
        preOrderTransveral(root,positions);
        return positions.iterator();
    }
    private void preOrderTransveral(TreeNode<E> node,List<Position<E>> positions){
        if(node != null){
            positions.add(node);
            for(TreeNode<E> child : node.getChildren()){
                preOrderTransveral(child,positions);
            }

        }
    }
    private Iterator<Position<E>> iteratorPostorder(){
        if (isEmpty()){
            return new ArrayList<Position<E>>().iterator();
        }
        List<Position<E>> positions = new ArrayList<>();
        postOrderTransveral(root,positions);
        return positions.iterator();
    }
    private void postOrderTransveral(TreeNode<E> node,List<Position<E>> positions){
        if(node != null){
            for(TreeNode<E> child : node.getChildren()){
                postOrderTransveral(child,positions);
            }
            positions.add(node);
        }
    }
    private void breathOrder(TreeNode<E> node, List<Position<E>> positions) {
        if(node != null){
            List<TreeNode<E>> queue = new ArrayList<>();
            queue.add(node);
            while (!queue.isEmpty()){
                TreeNode<E> toExplore = queue.remove(0);
                positions.add(toExplore);
                queue.addAll(node.getChildren());
            }
        }
    }

    public int size() {
        return size;
    }
}
