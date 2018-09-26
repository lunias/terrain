package com.ethanaa.terrain;

import com.ethanaa.terrain.cube.CallbackMC;
import com.ethanaa.terrain.cube.MarchingCubes;
import com.ethanaa.terrain.noise.SimplexNoise;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableFloatArray;
import javafx.scene.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.fxyz3d.shapes.primitives.CubeMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class TerrainApplication extends Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TerrainApplication.class);

    private static final double MODEL_SCALE_FACTOR = 1d;
    private static final double MODEL_X_OFFSET = 0d;
    private static final double MODEL_Y_OFFSET = 0d;
    private static final double ZOOM_FACTOR = 2.5d;
    private static final double PAN_FACTOR = 10d;
    private static final int VIEWPORT_SIZE = 1000;

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

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Noise");

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

        Translate mapCameraPan = new Translate(0, 0, 0);

        PerspectiveCamera mapCamera = new PerspectiveCamera(false);
        mapCamera.getTransforms().addAll(mapCameraRx, mapCameraRy, mapCameraRz, mapCameraPan);

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

        Group sceneGroup = new Group(createBoxGroup(), light);

        SubScene subScene = new SubScene(sceneGroup, VIEWPORT_SIZE,VIEWPORT_SIZE,
                true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(mapCamera);

        Group root = new Group(subScene);

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

    public static Group createBoxGroup() {

        Group boxGroup = new Group();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = 0; z < 10; z++) {

                    double noise = SimplexNoise.noise(x, y, z);

                    if (noise > .5) {

                        CubeMesh cubeMesh = new CubeMesh(10d);

                        cubeMesh.setTranslateX(10 * x);
                        cubeMesh.setTranslateY(10 * y);
                        cubeMesh.setTranslateZ(10 * z);

                        boxGroup.getChildren().add(cubeMesh);
                    }
                }
            }
        }

        boxGroup.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        boxGroup.setTranslateY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET);
        boxGroup.setTranslateZ(VIEWPORT_SIZE / 2);
        boxGroup.setScaleX(MODEL_SCALE_FACTOR);
        boxGroup.setScaleY(MODEL_SCALE_FACTOR);
        boxGroup.setScaleZ(MODEL_SCALE_FACTOR);

        CallbackMC callback = new CallbackMC() {
            @Override
            public void run() {

                List<float[]> vertices = this.getVertices();

                TriangleMesh marchedMesh = new TriangleMesh();

                PhongMaterial material = new PhongMaterial(Color.RED);
                material.setDiffuseColor(Color.RED);
                material.setSpecularColor(Color.RED);

                MeshView meshView = new MeshView(marchedMesh);
                meshView.setDrawMode(DrawMode.FILL);
                meshView.setMaterial(material);
                meshView.setCullFace(CullFace.NONE);

                AmbientLight light = new AmbientLight(Color.PERU);
                light.getScope().add(meshView);

                Map<Integer, String> indexMap = new TreeMap<>();
                Map<String, List<Integer>> seen = new TreeMap<>();

                int i = 0;
                for (float[] vertex : vertices) {

                    LOG.info(Arrays.toString(vertex));

                    for (float coord : vertex) {
                        marchedMesh.getPoints().addAll(coord * 50);
                    }

                    seen.computeIfAbsent(Arrays.toString(vertex), k -> new ArrayList<>()).add(i);
                    indexMap.put(i, Arrays.toString(vertex));

                    marchedMesh.getTexCoords().addAll(0, 0, 0, 1, 1, 0, 1, 1);
                    i++;
                }

                Map<String, Boolean> check = new HashMap<>();
                for (Map.Entry<Integer, String> entry : indexMap.entrySet()) {

                    String vName = entry.getValue();
                    check.computeIfAbsent(vName, k -> {

                        for (Integer loc : seen.get(entry.getValue())) {
                            marchedMesh.getFaces().addAll((loc == 0 ? loc : loc - 1) % vertices.size(), loc, (loc + 1) % vertices.size());
                        }

                        return true;
                    });
                }

                Platform.runLater(() -> boxGroup.getChildren().add(new Group(light, meshView)));
            }
        };

        CubeMesh cubeMesh = new CubeMesh(10d);
        TriangleMesh triangleMesh = (TriangleMesh) cubeMesh.getMesh();

        ObservableFloatArray observableFloatArray = triangleMesh.getPoints();
        float[] values = observableFloatArray.toArray(new float[observableFloatArray.size()]);

        LOG.info(Arrays.toString(values));

        MarchingCubes.marchingCubesFloat(values, new int[] {3, 2, 4}, 1, new float[] {1f, 1f, 1f}, 1f, 0, callback);

        return boxGroup;
    }
}
