/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:14
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.utils;

import org.lwjgl.input.Mouse;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {


    /**
     * Draws circle. If hovered returns the color (if color wheel mode enabled)
     *
     * @param xPos
     * @param yPos
     * @param radius
     * @param color
     * @return
     */
    static Color currentColor;

    public static void setupGL() {
        glPushMatrix();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glEnable(GL_BLEND);
        glDisable(GL_CULL_FACE);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
    }

    public static void endGL() {
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_CULL_FACE);
        glDisable(GL_BLEND);
        glPopAttrib();
        glPopMatrix();
    }

    public static void drawQuad(float xPos, float yPos, float width, float height, Color quadColor) {
        setupGL();
        glColor4f(quadColor.getRed() / 255.0F, quadColor.getGreen() / 255.0F, quadColor.getBlue() / 255.0F, quadColor.getAlpha() / 255.0F);
        glBegin(GL_TRIANGLE_STRIP);
        {
            drawTriangle(xPos, yPos, width, height);
            drawTriangle(xPos + width, yPos + height, -width, -height);
        }
        glEnd();
        endGL();
    }

    /**
     * Copied and modified from http://slabode.exofire.net/circle_draw.shtml
     * Credit where credit is dou
     *
     * @param xPos
     * @param yPos
     * @param radius
     * @param circleColor
     */
    public static void drawCircle(float xPos, float yPos, int radius, int lWidth, Color circleColor) {
        setupGL();
        glColor4f(circleColor.getRed() / 255.0F, circleColor.getGreen() / 255.0F, circleColor.getBlue() / 255.0F, circleColor.getAlpha() / 255.0F);
        glBegin(GL_POLYGON);
        {
            drawCircle(xPos, yPos, radius, lWidth, false);
        }
        glEnd();
        glEnable(GL_LINE_SMOOTH);
        glBegin(GL_LINE_LOOP);
        {
            drawCircle(xPos, yPos, radius, lWidth, false);
        }
        glEnd();
        endGL();
    }

    /*
    TODO: Cleanup
     */
    private static void drawCircle(float xPos, float yPos, int radius, int lWidth, boolean color) {
        double theta = (2 * Math.PI / 360.0);
        double tangetial_factor = Math.tan(theta);//calculate the tangential factor
        double radial_factor = Math.cos(theta);//calculate the radial factor
        float x = radius;//we start at angle = 0
        float y = 0;
        for (int ii = 0; ii < 360; ii++) {
            if (color) {
                float[] hueColor = getLWJGLColor(getHue(ii));
                glColor4f(hueColor[0], hueColor[1], hueColor[2], hueColor[3]);
                if (MouseUtil.mouseHovered(xPos + x, yPos + y, lWidth) && Mouse.isButtonDown(0))
                    currentColor = getHue(ii);
            }
            glVertex2f(x + xPos, y + yPos);

            //calculate the tangential vector
            //remember, the radial vector is (x, y)
            //to get the tangential vector we flip those coordinates and negate one of them

            float tx = -y;
            float ty = x;

            //add the tangential vector

            x += tx * tangetial_factor;
            y += ty * tangetial_factor;

            //correct using the radial factor
            x *= radial_factor;
            y *= radial_factor;
        }
    }

    public static float[] getLWJGLColor(Color color) {
        return new float[]{color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F};
    }

    public static void drawColorWheel(float xPos, float yPos, int radius, int lWidth) {
        setupGL();
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(lWidth);
        glBegin(GL_LINE_LOOP);
        {
            drawCircle(xPos, yPos, radius, lWidth, true);
        }
        glEnd();
        endGL();
    }

    public static Color getCurrentColor() {
        return currentColor;
    }

    private static Color getHue(float percent) {
        return Color.getHSBColor((percent / 360.0F), 1F, 1F);
    }

    private static void drawTriangle(float xPos, float yPos, float width, float height) {
        glVertex2f(xPos, yPos);
        glVertex2f(xPos, yPos + height);
        glVertex2f(xPos + width, yPos + height);
    }
}