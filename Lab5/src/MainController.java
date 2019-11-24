import figures.Figure;
import figures.Point;
import figures.PolyAngle;
import figures.Triangle;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

class MainController {

    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private TableView<FigureContainer> tableView;

    private GraphicsContext context;
    private boolean begin = false;
    private List<Point> points = new ArrayList<>();

    private List<FigureContainer> figureContainers = new ArrayList<>();

    public void setValues() {
        context = canvas.getGraphicsContext2D();
        context.setLineWidth(2);
        colorPicker.setValue(Color.RED);

        createTable();
    }

    private void createTable() {
        tableView.getColumns().clear();
        TableColumn<FigureContainer, Integer> idColumn = new TableColumn<>("Номер");
        TableColumn<FigureContainer, Color> colorColumn = new TableColumn<>("Цвет");
        TableColumn<FigureContainer, String> typeColumn = new TableColumn<>("Тип");
        TableColumn<FigureContainer, Integer> anglesColumn = new TableColumn<>("Вершины");
        TableColumn<FigureContainer, Double> areaColumn = new TableColumn<>("Площадь");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        anglesColumn.setCellValueFactory(new PropertyValueFactory<>("angles"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        idColumn.setSortable(false);
        colorColumn.setSortable(false);
        typeColumn.setSortable(false);
        anglesColumn.setSortable(false);
        areaColumn.setSortable(false);

        tableView.getColumns().addAll(idColumn, colorColumn, typeColumn, anglesColumn, areaColumn);
    }

    @FXML
    private void clickOnCanvas(MouseEvent e) {
        if (begin) {
            context.strokeLine(points.get(points.size() - 1).getX(),
                    points.get(points.size() - 1).getY(), e.getX(), e.getY());
            points.add(new Point(e.getX(), e.getY()));
        } else {
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            begin = true;
            context.beginPath();
            context.setStroke(colorPicker.getValue());
            context.moveTo(e.getX(), e.getY());
            points.add(new Point(e.getX(), e.getY()));
        }
    }

    private void reset() {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        points = new ArrayList<>();
        context.closePath();
        begin = false;
    }

    @FXML
    private void endFigure() {
        if (points.size() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Количество вершин должно быть не менее трёх!");
            alert.showAndWait();
            reset();
            return;
        }
        context.strokeLine(points.get(0).getX(), points.get(0).getY(),
                points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY());
        context.closePath();
        begin = false;

        Figure figure;
        try {
            if (points.size() == 3) {
                figure = new Triangle(new Point(points.get(0).getX(), points.get(0).getY()),
                        new Point(points.get(1).getX(), points.get(1).getY()),
                        new Point(points.get(2).getX(), points.get(2).getY()));
            } else {
                Point[] pointArray = new Point[1];
                pointArray = points.toArray(pointArray);
                figure = new PolyAngle(pointArray);
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Линии между вершинами фигуры не должны " +
                    "пересекаться и/или многоугольник должен быть выпуклым!");
            alert.showAndWait();
            reset();
            return;
        }
        points = new ArrayList<>();
        figureContainers.add(new FigureContainer(figure, figureContainers.size() + 1, (Color) context.getStroke()));
        tableView.setItems(new SortedList<>(FXCollections.observableList(figureContainers),
                (o1, o2) -> Double.compare(o2.getArea(), o1.getArea())));
    }

    @FXML private void drawFigures() {
        reset();
        for (FigureContainer figureContainer : figureContainers) {
            Point[] pointArray = figureContainer.getPoints();
            context.beginPath();
            context.setStroke(figureContainer.color);
            context.moveTo(pointArray[0].getX(), pointArray[0].getY());
            for (int i = 1; i < pointArray.length; i++) {
                context.strokeLine(pointArray[i - 1].getX(),
                        pointArray[i - 1].getY(),
                        pointArray[i].getX(),
                        pointArray[i].getY());
            }
            context.strokeLine(pointArray[0].getX(), pointArray[0].getY(),
                    pointArray[pointArray.length - 1].getX(),
                    pointArray[pointArray.length - 1].getY());
            context.closePath();
        }
    }


    public static class FigureContainer {
        private Figure figure;
        private int id;
        private Color color;

        FigureContainer(Figure figure, int id, Color color) {
            this.figure = figure;
            this.id = id;
            this.color = color;
        }

        public int getId() {
            return id;
        }

        public Color getColor() {
            return color;
        }

        public String getType() {
            return figure.getName();
        }

        public int getAngles() {
            return figure.getPoints().length;
        }

        double getArea() {
            return figure.getArea();
        }

        Point[] getPoints() {
            return figure.getPoints();
        }
    }
}
