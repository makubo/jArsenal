/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jarsenal;

import com.sun.deploy.uitoolkit.Applet2Adapter;
import com.sun.deploy.uitoolkit.DragContext;
import com.sun.deploy.uitoolkit.Window;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jarsenal.MapFilter;

/**
 *
 * @author maksim.bobylev
 */
public class FXMLDocumentController implements Initializable {

    final DragContext dragContext = new DragContext();

    @FXML
    private Label label;
    
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private Canvas mapCanvas;

    @FXML
    private TextField seedField;

    @FXML
    private TextField sizeField;

    @FXML
    public GraphicsContext gc;

    @FXML
    public GraphicsContext gp;

    @FXML
    private Pane mapPanel;
    
    @FXML
    private ChoiceBox generatorSelector;
    
    FrameCreator fc;
    HillMap hmap;
    Map map;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Exit application");
        Platform.exit();
    }

    @FXML
    private void generateMap() {
        fc = new FrameCreator(gc);
        int size = Integer.valueOf(sizeField.getText());
        int seed = Integer.valueOf(seedField.getText());
        hmap = new HillMap(size);
        hmap.setSeed(seed);
        MapFilter.setSeed(seed);
		
        DiamondSquareMap m = new DiamondSquareMap(size);
        m.setSeed(seed);
		int[][] landscape = m.generateMap();
		
		MapFilter.removeSingleHill(landscape);
		MapFilter.removeHighElevation(landscape);
        
		map = new Map(landscape, size, seed);
        //map = new Map(hmap.generateMap(), size, seed);
        //int[][] matr = hmap.generateMap();

        
        mapPanel.setPrefHeight(size + 10);
        mapPanel.setPrefWidth(size + 10);
        mapCanvas.setHeight(size);
        mapCanvas.setWidth(size);
        
        
        
        Color color;
        //color = Color.web(colorString);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                switch (map.getLandscape()[x][y]) {
                    case 0: {
                        //g.setColor(new Color(0x072059));
                        color = Color.rgb(44, 73, 146);
                        break;
                    }
                    case 1: {
                        //g.setColor(new Color(0x072059));
                        color = Color.rgb(56, 89, 166);
                        break;
                    }
                    case 2: {
                        //g.setColor(new Color(0x1E449B));
                        color = Color.rgb(69, 105, 186);
                        break;
                    }
                    case 3: {
                        //g.setColor(new Color(0x5670AB));
                        color = Color.rgb(89, 121, 195);
                        break;
                    }
                    case 4: {
                        //g.setColor(new Color(0x00CC00));
                        color = Color.rgb(154, 162, 60);
                        break;
                    }
                    case 5: {
                        //g.setColor(new Color(0x269926));
                        color = Color.rgb(138, 146, 48);
                        break;
                    }
                    case 6: {
                        //g.setColor(new Color(0x015F01));
                        color = Color.rgb(117, 130, 40);
                        break;
                    }
                    case 7: {
                        //g.setColor(new Color(0x9ADEC4));
                        color = Color.rgb(186, 174, 158);
                        break;
                    }
                    default: {
                        //g.setColor(Color.RED);
                        color = Color.RED;
                        break;
                    }
                }
                gp.getPixelWriter().setColor(x, y, color);
            }
        }
        //fc.setLandscape(map.getLandscape());
        fc.setLandscape(map.getTileMap());
        fc.getFrame();
    }

    public void initGraphics() {
        gc = mainCanvas.getGraphicsContext2D();
        gp = mapCanvas.getGraphicsContext2D();
        
    }

    @FXML
    public void drawClicked() {
        //gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        //Random rnd = new Random();
        //gc.setFill(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        //gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        //gc.setFill(Color.BLUE);
        //gc.fillOval(60, 60, 30, 10);
        
        //Image grass = new Image("file:graphics/0001.bmp");
        //gc.drawImage(grass, -5, -2);
        //if (fc != null ) {
            //fc.setLandscape(hmap.generateMap());
            //fc.getFrame();
        //}
    }

    @FXML
    private void dragMapPanel() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initGraphics();

        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            mainCanvas.setWidth(newValue.doubleValue());
            //drawClicked();
        });

        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            mainCanvas.setHeight(newValue.doubleValue());
            //drawClicked();
        });

//        mapPanel.addEventFilter(
//                MouseEvent.ANY,
//                new EventHandler<MouseEvent>() {
//            public void handle(final MouseEvent mouseEvent) {
//                mouseEvent.consume();
//            }
//        });
        
        mapPanel.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                // remember initial mouse cursor coordinates
                // and node position
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    dragContext.mouseAnchorX = mouseEvent.getSceneX();
                    dragContext.mouseAnchorY = mouseEvent.getSceneY();
                    dragContext.initialTranslateX = mapPanel.getTranslateX();
                    dragContext.initialTranslateY = mapPanel.getTranslateY();
                }

            }
        });

        mapPanel.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                // shift node from its initial position by delta
                // calculated from mouse cursor movement
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                mapPanel.setTranslateX(
                        dragContext.initialTranslateX
                        + mouseEvent.getSceneX()
                        - dragContext.mouseAnchorX);
                mapPanel.setTranslateY(
                        dragContext.initialTranslateY
                        + mouseEvent.getSceneY()
                        - dragContext.mouseAnchorY);
                label.setText("" + mouseEvent.getX() + " " + mouseEvent.getY());
                }
            }
        });
        
        anchorPane.addEventFilter(
                KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent){
                switch (keyEvent.getCode()) {
                    case UP:    fc.setyPosition(fc.getyPosition() - 12); break;
                    case DOWN:  fc.setyPosition(fc.getyPosition() + 12); break;
                    case LEFT:  fc.setxPosition(fc.getxPosition() - 12); break;
                    case RIGHT: fc.setxPosition(fc.getxPosition() + 12); break;
                    //case SHIFT: running = true; break;
                }
                fc.getFrame();
            }
        });
        
        anchorPane.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    dragContext.mouseAnchorX = mouseEvent.getSceneX();
                    dragContext.mouseAnchorY = mouseEvent.getSceneY();
                    dragContext.initialTranslateX = fc.getxPosition();
                    dragContext.initialTranslateY = fc.getyPosition();
                }
            }
        });
        
        anchorPane.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    fc.setxPosition(
                            (int)( dragContext.initialTranslateX
                            - ((int) (mouseEvent.getSceneX()
                            - dragContext.mouseAnchorX) / 12 * 12 )));
                    fc.setyPosition( (int)(
                            dragContext.initialTranslateY
                            - ((int) (mouseEvent.getSceneY()
                            - dragContext.mouseAnchorY) / 12 * 12)));
                    fc.getFrame();

                }
            }
        });
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

}
