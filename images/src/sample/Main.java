package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;


public class Main extends Application {

    private double width = 1280d;
    private double height = 720d;

    private final double size = 240d;

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    private TextArea textArea;
    private Image image;
    private ImageView imageView;
    private StackPane stackPane;


    private Canvas createCanvas(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            x1 = e.getX();
            y1 = e.getY();
            gc.clearRect(0, 0, width, height);

        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
            x2 = e.getX();
            y2 = e.getY();
            double w = x2 - x1;
            double h = y2 - y1;
            if (null != image) {
                gc.clearRect(0, 0, width, height);
                gc.strokeRoundRect(x1, y1, w, h, 0, 0);
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            x2 = e.getX();
            y2 = e.getY();
            double w = x2 - x1;
            double h = y2 - y1;
            String first = (int) x1 + "-" + (int) y1 + "-" + (int) w + "-" + (int) h;
            String second = "[  " + (int) (x1 + w / 2) + "-" + (int) (y1 + h / 2) + "  ]";
            String lineEnd = "\n";
            if (null != image) {
                gc.clearRect(0, 0, width, height);
                gc.strokeRoundRect(x1, y1, w, h, 0, 0);
                textArea.appendText(first + "    " + second + lineEnd);
            }
        });
        return canvas;
    }

    public MenuBar createMenuBar(Stage primaryStage) {
        // 添加菜单栏
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("操作");
        MenuItem menuItem = new MenuItem("打开");
        menu.getItems().add(menuItem);
        menuBar.getMenus().add(menu);
        menuItem.setOnAction((ActionEvent e) -> {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
//                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
//                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("All Images", "*.*")
            );
            File file = chooser.showOpenDialog(primaryStage);
            if (null != file) {
                String url = "file:" + file.toPath().toAbsolutePath().toAbsolutePath();
                image = new Image(url);
                width = image.getWidth();
                height = image.getHeight();
                primaryStage.setWidth(width);
                primaryStage.setHeight(height + size);
                imageView.setImage(image);
                stackPane.getChildren().remove(1);
                stackPane.getChildren().add(createCanvas(width, height));
            }
        });
        return menuBar;
    }


    private HBox createHBox() {
        // 添加信息栏
        HBox hBox = new HBox();
        textArea = new TextArea();
        textArea.setPrefHeight(size);
        textArea.setWrapText(true);
        Button button = new Button("清除");
        button.setMinWidth(size);
        button.setPrefHeight(size);
        button.setOnAction((ActionEvent e) -> textArea.clear());
        HBox.setHgrow(textArea, Priority.ALWAYS);
        hBox.getChildren().addAll(textArea, button);
        return hBox;
    }

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();
        MenuBar menuBar = createMenuBar(primaryStage);
        stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, createCanvas(width, height));
        HBox hBox = createHBox();
        VBox root = new VBox();
        root.getChildren().addAll(menuBar, stackPane, hBox);
        Scene scene = new Scene(root, width, height + size);
        Image icon = new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("cat.png")));
        primaryStage.getIcons().setAll(icon);
        primaryStage.setTitle("图片位置打开器");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
