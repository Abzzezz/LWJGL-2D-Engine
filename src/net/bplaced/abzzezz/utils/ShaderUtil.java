/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 16.06.20, 15:59
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.utils;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.lwjgl.opengl.GL11.*;

public abstract class ShaderUtil {

    private final int program;
    private boolean shader;

    //TODO: ShaderUtil rework
    
    public ShaderUtil(String vertexShader, String fragmentShader) {
        int vertexShader1 = 0, fragmentShader1 = 0;
        this.program = ARBShaderObjects.glCreateProgramObjectARB();

        try {
            vertexShader1 = createShader(vertexShader, ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragmentShader1 = createShader(fragmentShader, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
        setup(vertexShader1, fragmentShader1);
    }

    public ShaderUtil(URL vertexShader, URL fragmentShader) {
        int vertexShader1 = 0, fragmentShader1 = 0;
        this.program = ARBShaderObjects.glCreateProgramObjectARB();

        try {
            vertexShader1 = createShader(getShaderByUrl(vertexShader), ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragmentShader1 = createShader(getShaderByUrl(fragmentShader), ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
        setup(vertexShader1, fragmentShader1);
    }

    private void setup(int vertexShader, int fragmentShader) {
        if (program == 0)
            return;

        ARBShaderObjects.glAttachObjectARB(program, vertexShader);
        ARBShaderObjects.glAttachObjectARB(program, fragmentShader);
        ARBShaderObjects.glLinkProgramARB(program);
        ARBShaderObjects.glValidateProgramARB(program);

        if (ARBShaderObjects.glGetObjectParameteriARB(program,
                ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

        if (ARBShaderObjects.glGetObjectParameteriARB(program,
                ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }
        shader = true;
    }


    protected String getShaderByUrl(URL shaderURL) {
        //TODO: rewrite
        StringBuilder stringBuilder = new StringBuilder();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(shaderURL.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n\r");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void draw() {
        glPushMatrix();
        if (shader) ARBShaderObjects.glUseProgramObjectARB(program);
        glLoadIdentity();
        /*
        Load texture and pass to shader

          if(texture != -1) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
            GL20.glUniform1f(GL20.glGetUniformLocation(program, "video"), 0);
        }
         */
        glTranslatef(0.0f, 0.0f, -10.0f);
        glColor3f(1.0f, 1.0f, 1.0f);
        glBegin(GL_QUADS);
        {
            glVertex3f(-1.0f, 1.0f, 0.0f);
            glVertex3f(1.0f, 1.0f, 0.0f);
            glVertex3f(1.0f, -1.0f, 0.0f);
            glVertex3f(-1.0f, -1.0f, 0.0f);
        }
        glEnd();

        if (shader) ARBShaderObjects.glUseProgramObjectARB(0);
        glPopMatrix();
    }

    protected int createShader(String shaderSource, int shaderType) {
        try {
            int shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if (shader == 0) return 0;
            ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
            ARBShaderObjects.glCompileShaderARB(shader);
            return shader;
        } catch (Exception exc) {
            throw exc;
        }
    }

    protected String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
}
