package view;

import algorithms.PathfindingAlgorithm;
import algorithms.factory.PathFindingAlgorithmFactory;
import config.ApplicationConfiguration;
import model.Node;
import model.NodeType;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathfindingView extends JPanel implements Observer, MouseMotionListener, KeyListener, MouseListener,MouseWheelListener {

    Node[][] board = new Node[ApplicationConfiguration.getInstance().getRowNumber()][ApplicationConfiguration.getInstance().getRowNumber()];
    private NodePaintComponent nodePaintComponent = new NodePaintComponent();

    private char keyPressed = (char)0;


    Node startNode;
    Node endNode;

    private String algorithmType;
    private ExecutorService executorService;
    private PathfindingAlgorithm pathfindingAlgorithm;
    private boolean algorithmErasedFromBoard = true;






    public PathfindingView() {
        initBoard();
        initViewAcordingToBoard();
        initListeners();
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



    public void startPathfindingAlgorithm(String algorithmType,int pathfindingSpeed){
        this.pathfindingAlgorithm = PathFindingAlgorithmFactory.getPathFindingAlgorithm(algorithmType,pathfindingSpeed,board,startNode,endNode);
        this.pathfindingAlgorithm.addObserver(this);
        this.algorithmType = algorithmType;
        executorService.submit(new Thread(pathfindingAlgorithm));
        algorithmErasedFromBoard=false;
    }



    private void initListeners() {
        setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
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
        if(keyPressed=='q')
            return;
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

            mouseDrawNodes(currentNode,cellWidth);
            keyPressed = '0';
        }
        else if(SwingUtilities.isRightMouseButton(event) && !isAlgorithmRunning()){
           mouseEraseNodes(currentNode,cellWidth);
        }

    }
    private void mouseDrawNodes(Node currentNode,int cellWidth){
        if(keyPressed == 's' && startNode==null && (currentNode.getNodeType()==NodeType.EMPTY
                || currentNode.getNodeType() == NodeType.SELECTED)){
            startNode = currentNode;
            startNode.setNodeType(NodeType.START);
            repaint(startNode.getRow()*cellWidth,startNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

        }
        else if(keyPressed == 'e' && endNode == null && (currentNode.getNodeType() == NodeType.EMPTY
                || currentNode.getNodeType() == NodeType.SELECTED)){
            endNode = currentNode;
            endNode.setNodeType(NodeType.END);
            repaint(endNode.getRow()*cellWidth,endNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);
        }
        else if(currentNode.getNodeType() == NodeType.EMPTY || currentNode.getNodeType() == NodeType.SELECTED){
            currentNode.setNodeType(NodeType.BLOCK);
            repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

        }
    }

    private void mouseEraseNodes(Node currentNode,int cellWidth){
        if(currentNode.getNodeType() == NodeType.END){
            endNode=null;
            currentNode.setNodeType(NodeType.EMPTY);
            repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);
        }
        else if(currentNode.getNodeType() == NodeType.START){
            startNode = null;
            currentNode.setNodeType(NodeType.EMPTY);
            repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);
        }
        else if(currentNode.getNodeType() == NodeType.BLOCK){
            currentNode.setNodeType(NodeType.EMPTY);
            repaint(currentNode.getRow()*cellWidth,currentNode.getCol()*cellWidth,cellWidth-1,cellWidth-1);

        }
    }


    public void clearAlgorithm(){
        algorithmErasedFromBoard=true;

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
        algorithmErasedFromBoard=true;
        repaint();
    }
    public boolean errorChecking(){
        if(startNode == null){
            JOptionPane.showMessageDialog(null,ApplicationConfiguration.getInstance().getStartNodeNotDefinedError(),"Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if(endNode == null){
            JOptionPane.showMessageDialog(null,ApplicationConfiguration.getInstance().getEndNodeNotDefinedError(),"Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if(!algorithmErasedFromBoard){
            JOptionPane.showMessageDialog(null,ApplicationConfiguration.getInstance().getAlgorithmNotClearedError(),"Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }

        return false;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() == 'e' || keyEvent.getKeyChar() == 'q')
            keyPressed = keyEvent.getKeyChar();

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int currentCellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();

        if(mouseWheelEvent.getWheelRotation()<0 && currentCellWidth<ApplicationConfiguration.getInstance().getNodeMaxWidth()){
            int newSize = Math.min(currentCellWidth+20,ApplicationConfiguration.getInstance().getNodeMaxWidth());
            ApplicationConfiguration.getInstance().setNodeDefaultWidth(newSize);
            initViewAcordingToBoard();
            repaint();

        }
        else if(mouseWheelEvent.getWheelRotation()>0 && currentCellWidth>ApplicationConfiguration.getInstance().getNodeMinWidth()){
            int newSize = Math.max(currentCellWidth-20,ApplicationConfiguration.getInstance().getNodeMinWidth());
            ApplicationConfiguration.getInstance().setNodeDefaultWidth(newSize);
            initViewAcordingToBoard();
            repaint();


        }

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(keyPressed == 'q' && startNode!=null && endNode!=null){
            keyPressed='0';
            int cellWidth = ApplicationConfiguration.getInstance().getNodeDefaultWidth();
            int row = mouseEvent.getX()/cellWidth;
            int col = mouseEvent.getY()/cellWidth;

            Node n = board[row][col];
            startNode.setNodeType(NodeType.EMPTY);
            startNode = n;
            startNode.setNodeType(NodeType.START);

            this.clearAlgorithm();
            this.startPathfindingAlgorithm(algorithmType,0);


        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        basicGridCommands(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {



    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }



    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
