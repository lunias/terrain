package com.ethanaa.terrain;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class TerrainApplication extends Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TerrainApplication.class);

    private static final double MODEL_X_OFFSET = 0d;
    private static final double MODEL_Y_OFFSET = 0d;
    private static final double ZOOM_FACTOR = 2.5d;
    private static final double PAN_FACTOR = 100d;
    private static final int VIEWPORT_SIZE = 1024;

    private double mouseClickX;
    private double mouseClickY;

    public static void main(String[] args) {

        SpringApplication.run(TerrainApplication.class, args);
        launch(args);
    }

    @Override
    public void run(String... args) throws Exception {

        LOG.info("Welcome to terrain");
    }

    private Group createMeshGroup() {

        TerrainGenerator terrainGenerator;
        try {
            terrainGenerator = new TerrainGenerator(
                    16, 16, 16, "dark_red_gradient.png");
        } catch (FileNotFoundException fnfe) {
            LOG.error("Could not find texture resource", fnfe);
            return null;
        }

        int size = 1024;

        int zMax = 1024;
        int zMin = 0;

        Group group = new Group();
        for (int xOffset = 0; xOffset < 5; xOffset++) {

            int xMin = 1024 * xOffset;
            int xMax = 1024 * (xOffset + 1);

            for (int yOffset = 0; yOffset < 5; yOffset++) {

                int yMin = 1024 * yOffset;
                int yMax = 1024 * (yOffset + 1);

                MeshView meshView = terrainGenerator.genMeshView(
                        new float[]{xMin, xMax}, new float[]{yMin, yMax}, new float[]{zMin, zMax}, 200f, size);

                meshView.setLayoutX(xOffset * size + size / 2);
                meshView.setLayoutY(yOffset * size + size / 2);

                AmbientLight meshLight = new AmbientLight(Color.WHITE);
                meshLight.getScope().add(meshView);

                group.getChildren().addAll(meshView, meshLight);
            }
        }

        return group;
    }

    private PointLight createLight() {

        Light.Distant distant = new Light.Distant();
        distant.setAzimuth(-135.0f);
        distant.setElevation(100d);

        Lighting l = new Lighting();
        l.setLight(distant);
        l.setSurfaceScale(10.0f);

        PointLight light = new PointLight(Color.LIGHTSKYBLUE);
        light.setEffect(l);
        light.setTranslateX(0);
        light.setTranslateY(0);
        light.setTranslateZ(-200);

        return light;
    }

    private Group createAxes() {

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        return new Group(xAxis, yAxis, zAxis);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Terrain");

        Group sceneGroup = new Group(createMeshGroup(), createLight(), createAxes());

        SubScene subScene = new SubScene(sceneGroup,
                VIEWPORT_SIZE, VIEWPORT_SIZE,
                true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.INDIANRED);

        Translate mapCameraPan = new Translate(0, 0, 0);

        Rotate mapCameraRx = new Rotate(0, Rotate.X_AXIS);
        Rotate mapCameraRy = new Rotate(0, Rotate.Y_AXIS);
        Rotate mapCameraRz = new Rotate(0, Rotate.Z_AXIS);

        mapCameraRx.setPivotX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        mapCameraRx.setPivotY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET);
        mapCameraRx.setPivotZ(VIEWPORT_SIZE / 2);

        mapCameraRy.setPivotX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        mapCameraRy.setPivotY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET);
        mapCameraRy.setPivotZ(VIEWPORT_SIZE / 2);

        mapCameraRz.setPivotX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        mapCameraRz.setPivotY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET);
        mapCameraRz.setPivotZ(VIEWPORT_SIZE / 2);

        PerspectiveCamera mapCamera = new PerspectiveCamera(false);
        mapCamera.getTransforms().addAll(mapCameraRx, mapCameraRy, mapCameraRz, mapCameraPan);

        subScene.setCamera(mapCamera);

        BorderPane root = new BorderPane(subScene);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    mapCameraPan.yProperty().setValue(mapCameraPan.getY() + PAN_FACTOR);
                    break;
                case S:
                    mapCameraPan.yProperty().setValue(mapCameraPan.getY() - PAN_FACTOR);
                    break;
                case A:
                    mapCameraPan.xProperty().setValue(mapCameraPan.getX() + PAN_FACTOR);
                    break;
                case D:
                    mapCameraPan.xProperty().setValue(mapCameraPan.getX() - PAN_FACTOR);
                    break;
            }
        });

        scene.setOnScroll(e -> mapCameraPan.zProperty().setValue(mapCameraPan.getZ() + e.getDeltaY() * ZOOM_FACTOR));

        scene.setOnMouseClicked(e -> {
            mouseClickX = e.getSceneX();
            mouseClickY = e.getSceneX();
        });

        scene.setOnMouseDragged(e -> {
            mapCameraRx.setAngle(mapCameraRx.getAngle() - (e.getSceneY() - mouseClickY));
            mapCameraRy.setAngle(mapCameraRy.getAngle() + (e.getSceneX() - mouseClickX));
            mouseClickX = e.getSceneX();
            mouseClickY = e.getSceneY();
        });

        stage.setScene(scene);
        stage.show();
    }
}
