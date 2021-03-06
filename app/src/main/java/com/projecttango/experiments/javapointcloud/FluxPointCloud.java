package com.projecttango.experiments.javapointcloud;

import android.util.Log;

import java.nio.FloatBuffer;
import com.projecttango.tangoutils.Renderer;
import android.opengl.Matrix;

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

    private static float conv(float f) {
        return (float) (Math.round(f * 100) * 0.01);
    }

    /**
     * Convert the array of floats to a JSON string
     * @param fa Array of floats x y z
     * @param pointIndex The next empty index in the array
     * @return JSON string of flux point primitives
     */
    public static String arrayToString(float[] fa, int pointIndex) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < pointIndex; i+=3) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append("{\"point\":[");
            sb.append(conv(fa[i]));
            sb.append(",");
            sb.append(conv(fa[i+1]));
            sb.append(",");
            sb.append(conv(fa[i+2]));
            sb.append("], \"primitive\":\"point\"}");
        }
        sb.append("]");
//        Log.e("WHAAAA", sb.toString().substring(0, 200));

        return sb.toString();
    }

    /**
     * Add a chunk of the float buffer to the float array
     * @param fb Iterable list of x y z
     * @param fa Array of x y z
     * @param pointIndex Next available index
     * @return The new next index position
     */
    public static int bufferAppend(FloatBuffer fb, float[] fa, int pointIndex, Renderer rndr) {
        final float[] vecP = new float[4];
        vecP[0] = 0;
        vecP[1] = 0;
        vecP[2] = 0;
        vecP[3] = 1;
        final float[] vecX = new float[4];

        final float[] xform = rndr.getViewMatrix();

        while (fb.hasRemaining() ) {
            vecP[0] = fb.get();
            if (!fb.hasRemaining())
                break;
            vecP[1] = fb.get();

            if (!fb.hasRemaining())
                break;
            vecP[2] = fb.get();

            // Apply view transformation to go from
            // camera to model space 
            Matrix.multiplyMV(vecX, 0, xform, 0, vecP, 0);

            // scale by 30 for easy visualization (hack)
            fa[pointIndex++] = vecX[0] * 30 ;
            fa[pointIndex++] = vecX[2] * 30;
            fa[pointIndex++] = -1 * vecX[1] * 30;
        }
        fb.rewind();
        return pointIndex;
    }
}
