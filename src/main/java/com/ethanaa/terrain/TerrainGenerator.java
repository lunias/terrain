package com.ethanaa.terrain;

import com.ethanaa.terrain.cube.CallbackMC;
import com.ethanaa.terrain.cube.MarchingCubes;
import com.ethanaa.terrain.noise.SimplexNoise;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.*;

public class TerrainGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(TerrainGenerator.class);

    private int xResolution;
    private int yResolution;
    private int zResolution;

    private float[] scalarField;

    private Image texture;

    public TerrainGenerator(int xResolution, int yResolution, int zResolution, String texture) throws FileNotFoundException {

        this.xResolution = xResolution;
        this.yResolution = yResolution;
        this.zResolution = zResolution;

        this.scalarField = new float[xResolution * yResolution * zResolution];

        this.texture = new Image(ResourceUtils.getFile("classpath:" + texture).toURI().toString());
    }

    // rescale noise from [-1, 1] to [0, 1]
    private static double genElevationNoise(double x, double y) {
        return SimplexNoise.noise(x, y) / 2 + 0.5d;
    }

    private static float[] multiply(float[] values, float number) {
        for(int i = 0; i < values.length; i++) {
            values[i] = values[i] * number;
        }
        return values;
    }

    private float[] genScalarField(float[] xRange, float[] yRange, float[] zRange) {

        List<Double> elevations = new ArrayList<>(xResolution * yResolution * zResolution);

        for (int k = 0; k < xResolution; k++) {
            for (int j = 0; j < yResolution; j++) {
                for (int i = 0; i < zResolution; i++) {

                    double xDelta = xRange[1] - xRange[0];
                    double yDelta = yRange[1] - yRange[0];
                    double zDelta = zRange[1] - zRange[0];

                    double x = xRange[0] + xDelta * i / (xResolution - 1);
                    double y = yRange[0] + yDelta * j / (yResolution - 1);
                    double z = zRange[0] + zDelta * k / (zResolution - 1);

                    double nx = x / xDelta - 0.5d;
                    double ny = y / yDelta - 0.5d;

                    double elevation = (1.00 * genElevationNoise( 1 * nx,  1 * ny)
                            + 0.50 * genElevationNoise( 2 * nx,  2 * ny)
                            + 0.25 * genElevationNoise( 4 * nx,  4 * ny)
                            + 0.13 * genElevationNoise( 8 * nx,  8 * ny)
                            + 0.06 * genElevationNoise(16 * nx, 16 * ny)
                            + 0.03 * genElevationNoise(32 * nx, 32 * ny));

                    elevation /= (1.00 + 0.50 + 0.25 + 0.13 + 0.06 + 0.03);
                    elevation = Math.pow(elevation, 2d);
                    elevation = Math.round(elevation * 4) / 4.0d;

                    elevations.add(elevation);

                    scalarField[k + yResolution * (j + zResolution * i)]
                            = (float) (z * elevation);
                }
            }
        }

        LOG.info("Minimum elevation: {}", Collections.min(elevations));
        LOG.info("Maximum elevation: {}", Collections.max(elevations));

        return scalarField;
    }

    private void genVertices(float[] xRange, float[] yRange, float[] zRange, float iso, CallbackMC callbackMC) {

        float[] scalarField = genScalarField(xRange, yRange, zRange);

        MarchingCubes.marchingCubesFloat(scalarField,
                new int[] {xResolution, yResolution, zResolution}, 1,
                new float[] {1, 1, 1}, iso, 0, callbackMC);
    }

    private TriangleMesh genMesh(float[] xRange, float[] yRange, float[] zRange, float iso, float scale) {

        final TriangleMesh mesh = new TriangleMesh();
        mesh.getTexCoords().addAll(
                .5f, .1f,
                .5f, .3f,
                .5f, .7f,
                .5f, .9f);

        genVertices(xRange, yRange, zRange, iso, new CallbackMC() {
            @Override
            public void run() {

                List<float[]> vertices = getVertices();

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
                    int texture = maxZ < -.5 ? 0 : maxZ < 0 ? 1 : maxZ < .5 ? 2 : 3;

                    Integer v1Index = vertexIndices.get(v1Name);
                    if (v1Index == null) {
                        vertexIndices.put(v1Name, p);
                        v1Index = vertexIndices.get(v1Name);
                        mesh.getPoints().addAll(multiply(v1, scale));
                        p++;
                    }

                    Integer v2Index = vertexIndices.get(v2Name);
                    if (v2Index == null) {
                        vertexIndices.put(v2Name, p++);
                        v2Index = vertexIndices.get(v2Name);
                        mesh.getPoints().addAll(multiply(v2, scale));
                    }

                    Integer v3Index = vertexIndices.get(v3Name);
                    if (v3Index == null) {
                        vertexIndices.put(v3Name, p++);
                        v3Index = vertexIndices.get(v3Name);
                        mesh.getPoints().addAll(multiply(v3, scale));
                    }

                    mesh.getFaces().addAll(v1Index, texture, v2Index, texture, v3Index, texture);
                }
            }
        });

        return mesh;
    }

    public MeshView genMeshView(float[] xRange, float[] yRange, float[] zRange, float iso, float scale) {

        MeshView meshView = new MeshView(genMesh(xRange, yRange, zRange, iso, scale));

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(texture);
        material.setSpecularColor(Color.RED);

        meshView.setMaterial(material);
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setCullFace(CullFace.FRONT);

        return meshView;
    }
}
