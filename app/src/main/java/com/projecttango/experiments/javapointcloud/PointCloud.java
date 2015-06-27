package com.projecttango.experiments.javapointcloud;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.projecttango.tangoutils.renderables.RenderUtils;
import com.projecttango.tangoutils.renderables.Renderable;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class PointCloud extends Renderable {
    private static final int COORDS_PER_VERTEX = 3;
    private static final String sVertexShaderCode = "uniform mat4 uMVPMatrix;attribute vec4 vPosition;varying vec4 vColor;void main() {gl_PointSize = 5.0;  gl_Position = uMVPMatrix * vPosition;  vColor = vPosition;}";
    private static final String sFragmentShaderCode = "precision mediump float;varying vec4 vColor;void main() {  gl_FragColor = vec4(vColor);}";
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POINT_TO_XYZ = 3;
    int mVertexVBO;
    private AtomicBoolean mUpdateVBO = new AtomicBoolean();
    private volatile FloatBuffer mPointCloudBuffer;
    private final int mProgram;
    private int mPosHandle;
    private int mMVPMatrixHandle;
    private int mPointCount;
    private float mAverageZ = 0.0F;

    public PointCloud(int maxDepthPoints) {
        int vertexShader = RenderUtils.loadShader('謱', "uniform mat4 uMVPMatrix;attribute vec4 vPosition;varying vec4 vColor;void main() {gl_PointSize = 5.0;  gl_Position = uMVPMatrix * vPosition;  vColor = vPosition;}");
        int fragShader = RenderUtils.loadShader('謰', "precision mediump float;varying vec4 vColor;void main() {  gl_FragColor = vec4(vColor);}");
        this.mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(this.mProgram, vertexShader);
        GLES20.glAttachShader(this.mProgram, fragShader);
        GLES20.glLinkProgram(this.mProgram);
        Matrix.setIdentityM(this.getModelMatrix(), 0);
        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        this.mVertexVBO = buffers[0];
    }

    public synchronized void UpdatePoints(FloatBuffer pointCloudFloatBuffer, int count) {
        int pointCount = mPointCount + count;
        FloatBuffer fb = FloatBuffer.allocate(pointCount * 3);
        if (mPointCloudBuffer != null) {
            mPointCloudBuffer.position(0);
            fb.put(mPointCloudBuffer);

        }
        fb.put(pointCloudFloatBuffer);
        mPointCount = pointCount;
        mPointCloudBuffer = fb;
        mUpdateVBO.set(true);
    }

    public synchronized void draw(float[] viewMatrix, float[] projectionMatrix) {
        GLES20.glBindBuffer('袒', this.mVertexVBO);
        if(this.mUpdateVBO.getAndSet(false)) {
            if(this.mPointCloudBuffer != null) {
                this.mPointCloudBuffer.position(0);
                GLES20.glBufferData('袒', this.mPointCloudBuffer.capacity() * 4, this.mPointCloudBuffer, '裤');
                this.mPointCount = this.mPointCloudBuffer.capacity() / 3;
                float totalZ = 0.0F;

                for(int i = 0; i < this.mPointCloudBuffer.capacity() - 3; i += 3) {
                    totalZ += this.mPointCloudBuffer.get(i + 2);
                }

                if(this.mPointCount != 0) {
                    this.mAverageZ = totalZ / (float)this.mPointCount;
                }

                this.mUpdateVBO.set(true);
            }

            this.mPointCloudBuffer = null;
        }

        if(this.mPointCount > 0) {
            GLES20.glUseProgram(this.mProgram);
            this.updateMvpMatrix(viewMatrix, projectionMatrix);
            GLES20.glVertexAttribPointer(this.mPosHandle, 3, 5126, false, 0, 0);
            GLES20.glEnableVertexAttribArray(this.mPosHandle);
            GLES20.glUniformMatrix4fv(this.mMVPMatrixHandle, 1, false, this.getMvpMatrix(), 0);
            GLES20.glDrawArrays(0, 0, this.mPointCount);
        }

        GLES20.glBindBuffer('袒', 0);
    }

    public float getAverageZ() {
        return this.mAverageZ;
    }

    public int getPointCount() {
        return this.mPointCount;
    }
}
