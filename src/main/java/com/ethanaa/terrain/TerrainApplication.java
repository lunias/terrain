package com.ethanaa.terrain;

import com.ethanaa.terrain.cube.CallbackMC;
import com.ethanaa.terrain.cube.MarchingCubes;
import com.ethanaa.terrain.noise.SimplexNoise;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.*;
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

    private IntegerProperty xp = new SimpleIntegerProperty(16);
    private IntegerProperty yp = new SimpleIntegerProperty(16);
    private IntegerProperty zp = new SimpleIntegerProperty(16);

    private IntegerProperty zFullP = new SimpleIntegerProperty(1);

    private FloatProperty vxp = new SimpleFloatProperty(1f);
    private FloatProperty vyp = new SimpleFloatProperty(1f);
    private FloatProperty vzp = new SimpleFloatProperty(1f);

    private FloatProperty isop = new SimpleFloatProperty(10f);

    private IntegerProperty offsetp = new SimpleIntegerProperty(0);

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

        Label lx = new Label("volDim x");
        TextField x = new TextField();
        x.textProperty().bindBidirectional(xp, new NumberStringConverter());

        Label ly = new Label("volDim y");
        TextField y = new TextField();
        y.textProperty().bindBidirectional(yp, new NumberStringConverter());

        Label lz = new Label("volDim z");
        TextField z = new TextField();
        z.textProperty().bindBidirectional(zp, new NumberStringConverter());

        Label lzFull = new Label("volZFull");
        TextField zFull = new TextField();
        zFull.textProperty().bindBidirectional(zFullP, new NumberStringConverter());

        Label lvx = new Label("voxDim x");
        TextField vx = new TextField();
        vx.textProperty().bindBidirectional(vxp, new NumberStringConverter());

        Label lvy = new Label("voxDim y");
        TextField vy = new TextField();
        vy.textProperty().bindBidirectional(vyp, new NumberStringConverter());

        Label lvz = new Label("voxDim z");
        TextField vz = new TextField();
        vz.textProperty().bindBidirectional(vzp, new NumberStringConverter());

        Label lIso = new Label("iso");
        TextField iso = new TextField();
        iso.textProperty().bindBidirectional(isop, new NumberStringConverter());

        Label lOffset = new Label("offset");
        TextField offset = new TextField();
        offset.textProperty().bindBidirectional(offsetp, new NumberStringConverter());

        Button update = new Button("[Update]");

        Group sceneGroup = new Group(createBoxGroup(update), light);

        SubScene subScene = new SubScene(sceneGroup, VIEWPORT_SIZE,VIEWPORT_SIZE,
                true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(mapCamera);

        BorderPane root = new BorderPane(subScene);

        HBox controls = new HBox(5);
        controls.getChildren().addAll(lx, x, ly, y, lz, z, lzFull, zFull,
                lvx, vx, lvy, vy, lvz, vz, lIso, iso, lOffset, offset, update);
        root.setBottom(controls);

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

    public Group createBoxGroup(Button update) {

        Group boxGroup = new Group();

        /*
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
        */

        boxGroup.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        boxGroup.setTranslateY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET);
        boxGroup.setTranslateZ(VIEWPORT_SIZE / 2);
        boxGroup.setScaleX(MODEL_SCALE_FACTOR);
        boxGroup.setScaleY(MODEL_SCALE_FACTOR);
        boxGroup.setScaleZ(MODEL_SCALE_FACTOR);

        CallbackMC callback = new CallbackMC() {
            @Override
            public void run() {

                boxGroup.getChildren().clear();

                List<float[]> vertices = this.getVertices();
                LOG.info("Total Vertices: {}", vertices.size());

                TriangleMesh marchedMesh = new TriangleMesh();

                marchedMesh.getTexCoords().addAll(
                        .1f, .1f,
                        .1f, .3f,
                        .1f, .7f,
                        .1f, .9f);

                PhongMaterial material = new PhongMaterial(Color.WHITE);

                Image image = null;
                try {
                    image = new Image(ResourceUtils.getFile("classpath:red_gradient.png").toURI().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                material.setDiffuseMap(image);
                material.setSpecularColor(Color.RED);

                MeshView meshView = new MeshView(marchedMesh);
                meshView.setDrawMode(DrawMode.FILL);
                meshView.setMaterial(material);
                meshView.setCullFace(CullFace.BACK);

                AmbientLight light = new AmbientLight(Color.LIGHTYELLOW);
                light.getScope().add(meshView);

                Map<String, Integer> vertexIndices = new HashMap<>();

                int p = 0;
                for (int i = 0; i < vertices.size(); i += 3) {

                    float[] v1 = vertices.get(i);
                    String v1Name = Arrays.toString(v1);

                    float[] v2 = vertices.get(i + 1);
                    String v2Name = Arrays.toString(v2);

                    float[] v3 = vertices.get(i + 2);
                    String v3Name = Arrays.toString(v3);

                    float maxZ = Math.max(Math.max(v1[2], v2[2]), v3[2]);
                    LOG.info("maxZ = {}", maxZ);
                    int texture = maxZ < -.5 ? 0 : maxZ < 0 ? 1 : maxZ < .5 ? 2 : 3;

                    Integer v1Index = vertexIndices.get(v1Name);
                    if (v1Index == null) {
                        vertexIndices.put(v1Name, p);
                        v1Index = vertexIndices.get(v1Name);
                        marchedMesh.getPoints().addAll(multiply(v1, 1024));
                        p++;
                    }

                    Integer v2Index = vertexIndices.get(v2Name);
                    if (v2Index == null) {
                        vertexIndices.put(v2Name, p++);
                        v2Index = vertexIndices.get(v2Name);
                        marchedMesh.getPoints().addAll(multiply(v2, 1024));
                    }

                    Integer v3Index = vertexIndices.get(v3Name);
                    if (v3Index == null) {
                        vertexIndices.put(v3Name, p++);
                        v3Index = vertexIndices.get(v3Name);
                        marchedMesh.getPoints().addAll(multiply(v3, 1024));
                    }

                    LOG.info("face: {}, {}, {}", v1Index, v2Index, v3Index);

                    marchedMesh.getFaces().addAll(v1Index, texture, v2Index, texture, v3Index, texture);
                }

                for (Map.Entry<String, Integer> entry : sortByValue(vertexIndices).entrySet()) {
                    LOG.info("{}: {}", entry.getValue(), entry.getKey());
                }

                LOG.info("Unique Vertices: {}", vertexIndices.size());

                Platform.runLater(() -> boxGroup.getChildren().add(new Group(light, meshView)));
            }
        };

        float[] myCubeValues = genSimplex3d(new int[]{xp.get(), yp.get(), zp.get()});

        LOG.info(Arrays.toString(myCubeValues));

        MarchingCubes.marchingCubesFloat(myCubeValues,
                new int[] {xp.get(), yp.get(), zp.get()}, zFullP.get(),
                new float[] {vxp.get(), vyp.get(), vzp.get()}, isop.get(), offsetp.get(), callback);

        update.setOnAction(e -> {
            MarchingCubes.marchingCubesFloat(myCubeValues,
                    new int[] {xp.get(), yp.get(), zp.get()}, zFullP.get(),
                    new float[] {vxp.get(), vyp.get(), vzp.get()}, isop.get(), offsetp.get(), callback);
            LOG.info("Updated!");
        });

        return boxGroup;
    }

    public static float[] genSimplex3d(int[] size) {

        final float[] scalarField = new float[size[0] * size[1] * size[2]];

        float axisMin = -1024;
        float axisMax = 1024;
        float axisRange = axisMax - axisMin;

        for (int k = 0; k < size[0]; k++) {
            for (int j = 0; j < size[1]; j++) {
                for (int i = 0; i < size[2]; i++) {
                    // actual values
                    double x = axisMin + axisRange * i / (size[0] - 1);
                    double y = axisMin + axisRange * j / (size[1] - 1);
                    double z = axisMin + axisRange * k / (size[2] - 1);

                    double factor = 1;
                    double nx = (x/axisRange - .5) * factor;
                    double ny = (y/axisRange - .5) * factor;
                    double nz = (z/axisRange - .5) * factor;

                    double e = Math.pow(
                            SimplexNoise.noise(nx, ny, nz) +
                                    .5 * SimplexNoise.noise(2 * nx, 2 * ny, 2 * nz) +
                                    .25 * SimplexNoise.noise(4 * nx, 4 * ny, 4 * nz)
                            , 2);

                    double e2 = Math.pow(
                            SimplexNoise.noise(nx, ny) +
                                    .5 * SimplexNoise.noise(2 * nx, 2 * ny, 2) +
                                    .25 * SimplexNoise.noise(4 * nx, 4 * ny, 4)
                            , 2);

                    scalarField[k + size[1] * (j + size[2] * i)]
                            = (float) ((x * Math.cos(1)) + (y * Math.cos(1) * e2) + ((z * Math.cos(1) * e)));
                }
            }
        }

        return scalarField;
    }

    public static float[] multiply(float[] children, float number) {
        for(int i = 0; i < children.length; i++) {
            children[i] = children[i] * number;
        }
        return children;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    // hyperboloid equation: x^2 + y^2 - z^2 - 25
    public static float[] generateScalarFieldFloat(int[] size) {

        final float[] scalarField = new float[size[0] * size[1] * size[2]];
        float axisMin = -10;
        float axisMax = 10;
        float axisRange = axisMax - axisMin;

        for (int k = 0; k < size[0]; k++) {
            for (int j = 0; j < size[1]; j++) {
                for (int i = 0; i < size[2]; i++) {
                    // actual values
                    float x = axisMin + axisRange * i / (size[0] - 1);
                    float y = axisMin + axisRange * j / (size[1] - 1);
                    float z = axisMin + axisRange * k / (size[2] - 1);
                    scalarField[k + size[1] * (j + size[2] * i)] = x * x + y * y - z * z - 25;
                }
            }
        }

        return scalarField;
    }

    // spheroid equation: x^2 + y^2 + z^2
    public static float[] generateScalarFieldFloat2(int[] size) {

        final float[] scalarField = new float[size[0] * size[1] * size[2]];
        float axisMin = -10;
        float axisMax = 10;
        float axisRange = axisMax - axisMin;

        for (int k = 0; k < size[0]; k++) {
            for (int j = 0; j < size[1]; j++) {
                for (int i = 0; i < size[2]; i++) {
                    // actual values
                    float x = axisMin + axisRange * i / (size[0] - 1);
                    float y = axisMin + axisRange * j / (size[1] - 1);
                    float z = axisMin + axisRange * k / (size[2] - 1);
                    scalarField[k + size[1] * (j + size[2] * i)] = x * x + y * y + z * z - 1;
                }
            }
        }

        return scalarField;
    }
}
