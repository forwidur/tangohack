package com.projecttango.experiments.javapointcloud;

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
}
