package com.projecttango.experiments.javapointcloud;

import java.lang.reflect.Array;
import java.util.ArrayList;
import static java.lang.System.out;
import java.nio.FloatBuffer;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by kylegmaxwell on 6/27/15.
 */
public class FluxPointCloud {

    /**
     * Convert a float buffer list of x y z float values into a JSON string
     * that matches flux format for points. { 'point': [x, y, z], 'primitive':'point' }
     * @param fb The buffer of float x y z positions
     * @return String json for flux
     */
    public static String bufferToString(FloatBuffer fb) {
        int count = 0;
        String result = "";
        while (fb.hasRemaining() && (count < 30) ) {
            float x = fb.get();
            if (!fb.hasRemaining())
                break;
            float y = fb.get();

            if (!fb.hasRemaining())
                break;
            float z = fb.get();
            x *= 30;
            y *= 30;
            z *= 30;
            result += "{ 'point': [";
            result += x+","+y+","+z;
            result += "], 'primitive':'point' },";
            count++;
        }
        fb.rewind();
        return result;
    }

    /**
     * Convert the array of floats to a JSON string
     * @param fa Array of floats x y z
     * @param pointIndex The next empty index in the array
     * @return JSON string of flux point primitives
     */
    public static String arrayToString(float[] fa, int pointIndex) {

        String result = "[";
        for (int i = 0; i < pointIndex; i+=3) {
            float x = fa[i];
            float y = fa[i+1];
            float z = fa[i+2];
            result += "{ 'point': [";
            result += x+","+y+","+z;
            result += "], 'primitive':'point' },";
        }
        result += "]";
        return result;
    }

    /**
     * Add a chunk of the float buffer to the float array
     * @param fb Iterable list of x y z
     * @param fa Array of x y z
     * @param pointIndex Next available index
     * @return The new next index position
     */
    public static int bufferAppend(FloatBuffer fb, float[] fa, int pointIndex) {
        int count = 0;
        while (fb.hasRemaining() && (count < 30) ) {
            float x = fb.get();
            if (!fb.hasRemaining())
                break;
            float y = fb.get();

            if (!fb.hasRemaining())
                break;
            float z = fb.get();
            // scale for easy visualization (hack)
            x *= 30;
            y *= 30;
            z *= 30;
            fa[pointIndex++] = x;
            fa[pointIndex++] = y;
            fa[pointIndex++] = z;
            count++;
        }
        fb.rewind();
        return pointIndex;
    }
}
