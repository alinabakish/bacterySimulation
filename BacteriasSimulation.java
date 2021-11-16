import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BacteriasSimulation extends Application{

    Pane root = new Pane();
    List<Circle> foodCont = new ArrayList<>();
    List<Circle> weakBacteriaCont = new ArrayList<>();
    List<Integer> minIndex = new ArrayList<>();
    AnimationTimer timer;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        for (int i = 0; i < 100; i++) {
            minIndex.add(0);
            foodCont.add(food());
            root.getChildren().add(foodCont.get(i));
        }


        weakBacteriaCont.add(weakBacteria(randNum(0, 1000), randNum(0, 800)));
        root.getChildren().add(weakBacteriaCont.get(0));

        timer = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                gameUpdate();
                primaryStage.setTitle("Bacterias : " + weakBacteriaCont.size());
            }

        };
        timer.start();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bacterias Simulation");
        primaryStage.show();

    }

    public Circle food() {
        Circle c = new Circle();
        c.setLayoutX(randNum(0, 800));
        c.setLayoutY(randNum(0, 600));
        c.setRadius(2);
        c.setFill(Color.rgb(randNum(0, 256), randNum(0, 256), randNum(0, 256)));
        return c;
    }
    public Circle weakBacteria(double d, double e) {
        Circle c = new Circle();
        c.setLayoutX(d);
        c.setLayoutY(e);
        c.setRadius(5);
        c.setFill(Color.BLUE);
        return c;
    }
    public void gameUpdate() {



        for(int i = 0; i < weakBacteriaCont.size(); i ++) {

            int[] index = new int[weakBacteriaCont.size()];

            if((weakBacteriaCont.get(i)).getRadius() >= 10){
                (weakBacteriaCont.get(i)).setRadius(5);
                weakBacteriaCont.add(weakBacteria((weakBacteriaCont.get(i)).getLayoutX() + 10, (weakBacteriaCont.get(i)).getLayoutY() + 10));
                root.getChildren().add((weakBacteriaCont).get(weakBacteriaCont.size() - 1));
            }

            (weakBacteriaCont.get(i)).setRadius((weakBacteriaCont.get(i)).getRadius() - 0.005);
            if((weakBacteriaCont.get(i)).getRadius() <= 2){
                root.getChildren().remove((weakBacteriaCont.get(i)));
                weakBacteriaCont.remove((weakBacteriaCont.get(i)));

            }

            double minDistance = Math.sqrt(Math.pow(((weakBacteriaCont.get(0)).getLayoutX() - (foodCont.get(0)).getLayoutX()), 2) + Math.pow(((weakBacteriaCont.get(0)).getLayoutY() - (foodCont.get(0)).getLayoutY()), 2));


            for(int k= 0; k < foodCont.size(); k++) {

                if (minDistance > Math.sqrt(Math.pow(((weakBacteriaCont.get(i)).getLayoutX() - (foodCont.get(k)).getLayoutX()), 2) + Math.pow(((weakBacteriaCont.get(i)).getLayoutY() - (foodCont.get(k)).getLayoutY()), 2))){
                    minDistance = Math.min(minDistance, Math.sqrt(Math.pow(((weakBacteriaCont.get(i)).getLayoutX() - (foodCont.get(k)).getLayoutX()), 2) + Math.pow(((weakBacteriaCont.get(i)).getLayoutY() - (foodCont.get(k)).getLayoutY()), 2)));
                    index[i] = k;
                }

                if((((weakBacteriaCont.get(i)).getLayoutX() > (foodCont.get(k)).getLayoutX() - (weakBacteriaCont.get(i)).getRadius())
                        &&  ((weakBacteriaCont.get(i)).getLayoutX() < (foodCont.get(k)).getLayoutX() + (weakBacteriaCont.get(i)).getRadius()))
                        && (((weakBacteriaCont.get(i)).getLayoutY() > (foodCont.get(k)).getLayoutY() - (weakBacteriaCont.get(i)).getRadius())
                        &&  ((weakBacteriaCont.get(i)).getLayoutY() < (foodCont.get(k)).getLayoutY() + (weakBacteriaCont.get(i)).getRadius()))) {
                    root.getChildren().remove(foodCont.get(k));
                    foodCont.remove(k);
                    foodCont.add(food());
                    root.getChildren().add(foodCont.get(foodCont.size() - 1));

                    (weakBacteriaCont.get(i)).setRadius((weakBacteriaCont.get(i)).getRadius() + 1);
                }
            }

            if((weakBacteriaCont.get(i)).getLayoutX() > (foodCont.get(index[i])).getLayoutX()) {
                if(movable((weakBacteriaCont.get(i)).getLayoutX(), (weakBacteriaCont.get(i)).getLayoutY(), i)) {
                    (weakBacteriaCont.get(i)).setLayoutX((weakBacteriaCont.get(i)).getLayoutX() - 1);
                }
                else {
                    (weakBacteriaCont.get(i)).setLayoutX((weakBacteriaCont.get(i)).getLayoutX() + 1);
                }
            }

            if((weakBacteriaCont.get(i)).getLayoutX() < (foodCont.get(index[i])).getLayoutX()) {
                if(movable((weakBacteriaCont.get(i)).getLayoutX(), (weakBacteriaCont.get(i)).getLayoutY(), i)) {
                    (weakBacteriaCont.get(i)).setLayoutX((weakBacteriaCont.get(i)).getLayoutX() + 1);
                }
                else {
                    (weakBacteriaCont.get(i)).setLayoutX((weakBacteriaCont.get(i)).getLayoutX() - 1);
                }
            }

            if((weakBacteriaCont.get(i)).getLayoutY() > (foodCont.get(index[i])).getLayoutY()) {
                if(movable((weakBacteriaCont.get(i)).getLayoutX(), (weakBacteriaCont.get(i)).getLayoutY(), i)) {
                    (weakBacteriaCont.get(i)).setLayoutY((weakBacteriaCont.get(i)).getLayoutY() - 1);
                }
                else {
                    (weakBacteriaCont.get(i)).setLayoutX((weakBacteriaCont.get(i)).getLayoutX() + 1);
                }
            }

            if((weakBacteriaCont.get(i)).getLayoutY() < (foodCont.get(index[i])).getLayoutY()) {
                if(movable((weakBacteriaCont.get(i)).getLayoutX(), (weakBacteriaCont.get(i)).getLayoutY(), i)) {
                    (weakBacteriaCont.get(i)).setLayoutY((weakBacteriaCont.get(i)).getLayoutY() + 1);
                }
                else {
                    (weakBacteriaCont.get(i)).setLayoutX((weakBacteriaCont.get(i)).getLayoutX() - 1);
                }
            }



        }


    }

    public int randNum(int min, int max) {
        return (int)(Math.random() * max + min);
    }

    public boolean movable(double x, double y, int ind){
        for(int i = 0; i < weakBacteriaCont.size(); i++) {
            if(ind == i) {
                break;
            }
            else if((((weakBacteriaCont.get(i)).getLayoutX() > x - (weakBacteriaCont.get(i)).getRadius())
                    &&  ((weakBacteriaCont.get(i)).getLayoutX() < x + (weakBacteriaCont.get(i)).getRadius()))
                    && (((weakBacteriaCont.get(i)).getLayoutY() > y - (weakBacteriaCont.get(i)).getRadius())
                    &&  ((weakBacteriaCont.get(i)).getLayoutY() < y + (weakBacteriaCont.get(i)).getRadius()))) {
                return false;
            }

        }
        return true;
    }

}