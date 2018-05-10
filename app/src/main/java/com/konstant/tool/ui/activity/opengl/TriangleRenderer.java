package com.konstant.tool.ui.activity.opengl;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 描述:OpenGL的渲染器，三角形渲染器
 * 创建人:菜籽
 * 创建时间:2018/2/2 下午5:08
 * 备注:
 */

public class TriangleRenderer implements GLSurfaceView.Renderer {

    // 三角形数组
    private float[] mTriangleArray = {
            0f, 1f, 0f,
            -1f, -1f, 0f,
            1f, -1f, 0f
    };

    // 三角形各个顶点的颜色（三个顶点）
    private float[] mColor = new float[]{
            1, 1, 0, 1,     // R,G,B,A
            0, 1, 1, 1,
            1, 0, 1, 1,
    };

    private FloatBuffer mTriangleBuffer;
    private FloatBuffer mColorBuffer;

    public TriangleRenderer() {
        // 先初始化buffer，数组的长度*4，因为一个float占用4个字节
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(mTriangleArray.length * 4);

        // 大小端的问题，以本机的字节顺序来修改此缓冲区的字节顺序
        byteBuffer.order(ByteOrder.nativeOrder());
        mTriangleBuffer = byteBuffer.asFloatBuffer();

        // 将给定的float[]数据从当前位置开始，依次写入此缓冲区
        mTriangleBuffer.put(mTriangleArray);

        // 设置此缓冲区的位置，如果标记已定义并且大于新的位置，则需要丢弃该标记
        mTriangleBuffer.position(0);

        // 颜色相关设置
        ByteBuffer buffer = ByteBuffer.allocateDirect(mColor.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        mColorBuffer = buffer.asFloatBuffer();
        mColorBuffer.put(mColor);
        mColorBuffer.position(0);

    }

    // surfaceView创建时调用
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置白色为清屏，范围0~1
        gl.glClearColor(1, 1, 1, 1);
    }

    // surfaceView视图大小发生变化时调用
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 重置投影矩阵
        gl.glLoadIdentity();
        // 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        // 以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    // surfaceView绘制图形时调用
    @Override
    public void onDrawFrame(GL10 gl) {

        // 清除屏幕和缓存深度
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();

        // 允许设置顶点
        // GL10.GL_VERTEX_ARRAY 顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // 允许设置颜色
        // GL10.GL_COLOR_ARRAY颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        // 将三角形在Z轴上移动
        gl.glTranslatef(0f, 0.0f, -2.0f);

        // 设置三角形
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);
        // 设置三角形颜色
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
        // 绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // 取消颜色设置
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //绘制结束
        gl.glFinish();
    }
}
