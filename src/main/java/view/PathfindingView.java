package view;

import algorithms.Astar;
import algorithms.Bfs;
import algorithms.PathfindingAlgorithm;
import algorithms.distance.ManhattanDistance;
import config.ApplicationConfiguration;
import model.AlgorithmType;
import model.Node;
import model.NodeType;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathfindingView extends JPanel implements Observer, MouseMotionListener, KeyListener, MouseListener {

    Node board[][] = new Node[ApplicationConfiguration.getInstance().getRowNumber()][ApplicationConfiguration.getInstance().getRowNumber()];
    private NodePaintComponent nodePaintComponent = new NodePaintComponent();

    private char keyPressed = (char)0;

    Node mouseHoveringNode;
    Node startNode;
    Node endNode;

    private ExecutorService executorService;
    private PathfindingAlgorithm pathfindingAlgorithm;





    public PathfindingView() {
        initBoard();
        initViewAcordingToBoard();
        initViewProperties();
        executorService = Executors.newFixedThreadPool(10);
    }


    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                board[i][j] = new Node(i, j, NodeType.EMPTY);
            }
        }
    }

    private void initViewAcordingToBoard() {
        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();
        int width = board.length * cellWidth;
        int height = board[0].length * cellWidth;

        Dimension viewSize = new Dimension(width, height);
        setMinimumSize(viewSize);
        setPreferredSize(viewSize);
        setMaximumSize(viewSize);
    }



    public void startPathfindingAlgorithm(String algorithmType,int speed){
        this.pathfindingAlgorithm = generateAlgorithm(algorithmType);
        this.pathfindingAlgorithm.addObserver(this);
        executorService.submit(new Thread(pathfindingAlgorithm));
    }

    private PathfindingAlgorithm generateAlgorithm(String algorithmType){
        if(algorithmType.equals("A*")){
            return new Astar(board,startNode,endNode,new ManhattanDistance());
        }
        if(algorithmType.equals("BFS")){
            return new Bfs(board,startNode,endNode);
        }
        return null;
    }

    private void initViewProperties() {
        setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    public void fireUpdate() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics graph = g.create();
        nodePaintComponent.paintNodes(graph, board, ApplicationConfiguration.getInstance().getNodeDefaultWidth());
        graph.dispose();

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        basicGridCommands(mouseEvent);

    }
    public boolean isAlgorithmRunning(){
        if(pathfindingAlgorithm!=null)
            return pathfindingAlgorithm.isAlgorithmRunning();
        return false;
    }

    private void basicGridCommands(MouseEvent event){
        int row = event.getX()/ApplicationConfiguration.getInstance().getNodeDefaultWidth();
        int col = event.getY()/ApplicationConfiguration.getInstance().getNodeDefaultWidth();

        Node currentNode = board[row][col];
        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();

        if(SwingUtilities.isLeftMouseButton(event)){

            if(keyPressed == 's' && startNode==null && currentNode.getNodeType()==NodeType.EMPTY){
                startNode = currentNode;
                startNode.setNodeType(NodeType.START);
                repaint(startNode.getRow()*cellWidth,startNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

            }
            else if(keyPressed == 'e' && endNode == null && currentNode.getNodeType() == NodeType.EMPTY){
                endNode = currentNode;
                endNode.setNodeType(NodeType.END);
                repaint(endNode.getRow()*cellWidth,endNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);
            }
            else if(currentNode.getNodeType() == NodeType.EMPTY){
                currentNode.setNodeType(NodeType.BLOCK);
                repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

            }
        }
        else if(SwingUtilities.isRightMouseButton(event)){
            if(currentNode.getNodeType() == NodeType.END){
                endNode=null;
            }
            else if(currentNode.getNodeType() == NodeType.START){

                startNode = null;
            }


            currentNode.setNodeType(NodeType.EMPTY);
            repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

        }

    }

    /*
        used for painting a node we are currently hovering over
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

//        if(keyPressed == 's' || keyPressed =='e')
//            return;
//        int row = mouseEvent.getX() / ApplicationConfiguration.getInstance().getNodeDefaultWidth();
//        int col = mouseEvent.getY() / ApplicationConfiguration.getInstance().getNodeDefaultWidth();
//        int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();
//
//        Node newSelectedNode = board[row][col];
//
//
//        //TODO srediti !!
//        if(newSelectedNode.getNodeType() == NodeType.BLOCK){
//            if(mouseHoveringNode!=null){
//
//                mouseHoveringNode.setNodeType(NodeType.EMPTY);
//                repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);
//            }
//
//        }
//
//        if (newSelectedNode.getNodeType() != NodeType.EMPTY) {
//            return;
//        }
//
//        newSelectedNode.setNodeType(NodeType.SELECTED);
//
//        if (mouseHoveringNode != null) {
//            mouseHoveringNode.setNodeType(NodeType.EMPTY);
//            repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);
//        }
//
//        mouseHoveringNode = newSelectedNode;
//        repaint(mouseHoveringNode.getRow()*cellWidth,mouseHoveringNode.getCol()*cellWidth,cellWidth,cellWidth);

    }

    public void clearAlgorithm(){
        for(int i=0;i< board.length;i++){
            for(int j=0;j<board[0].length;j++){
                Node n=board[i][j];
                switch (n.getNodeType()){
                    case START:
                        board[i][j] = new Node(i,j,NodeType.START);
                        startNode = board[i][j];
                        break;
                    case END:
                        board[i][j] = new Node(i,j,NodeType.END);
                        endNode = board[i][j];
                        break;
                    case BLOCK:
                        board[i][j] = new Node(i,j,NodeType.BLOCK);
                        break;
                    default:
                        board[i][j] = new Node(i,j,NodeType.EMPTY);
                        break;
                }
            }
        }
        repaint();
    }
    public void clearBoard(){
        initBoard();
        startNode = null;
        endNode = null;
        repaint();
    }

    public boolean errorChecking(){
        if(startNode == null){
            JOptionPane.showMessageDialog(this,"Start node not initialized","Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if(endNode == null){
            JOptionPane.showMessageDialog(this,"End node not initialized","Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() == 'e')
            keyPressed = keyEvent.getKeyChar();

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        basicGridCommands(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
