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

import ga.abzzezz.util.math.MathUtil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {


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
            drawCircle(xPos, yPos, radius);
        }
        glEnd();
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(lWidth);
        glBegin(GL_LINE_LOOP);
        {
            drawCircle(xPos, yPos, radius);
        }
        glEnd();
        endGL();
    }

    /*
    TODO: Cleanup
     */
    private static void drawCircle(float xPos, float yPos, int radius) {
        double theta = (2 * Math.PI / 360.0);
        double tangetial_factor = Math.tan(theta);//calculate the tangential factor
        double radial_factor = Math.cos(theta);//calculate the radial factor
        float x = radius;//we start at angle = 0
        float y = 0;
        for (int ii = 0; ii < 360; ii++) {
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

    public static Color getHue(float percent) {
        return Color.getHSBColor((percent / 360.0F), 1F, 1F);
    }

    private static void drawTriangle(float xPos, float yPos, float width, float height) {
        glVertex2f(xPos, yPos);
        glVertex2f(xPos, yPos + height);
        glVertex2f(xPos + width, yPos + height);
    }

    public static void drawRoundedQuad(float xPos, float yPos, float width, float height, int radius, Color quadColor) {
        setupGL();
        glColor4f(quadColor.getRed() / 255.0F, quadColor.getGreen() / 255.0F, quadColor.getBlue() / 255.0F, quadColor.getAlpha() / 255.0F);
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(3);
        glBegin(GL_LINE_LOOP);
        {
            int deg = 45;
            for (int i = 0; i < deg; i++) {
                glVertex2d(xPos + radius - MathUtil.sinForCircle(i, radius), yPos + MathUtil.cosForCircle(i, radius));
            }

            for (int i = deg; i > 0; i--) {
                glVertex2d(xPos + radius - MathUtil.sinForCircle(i, radius), yPos + height + MathUtil.cosForCircle(i, radius));
            }

            for (int i = 0; i < deg; i++) {
                glVertex2d(xPos - radius + width + MathUtil.sinForCircle(i, radius), yPos + height + MathUtil.cosForCircle(i, radius));
            }

            for (int i = deg; i > 0; i--) {
                glVertex2d(xPos - radius + width + MathUtil.sinForCircle(i, radius), yPos + MathUtil.cosForCircle(i, radius));
            }

        }
        glEnd();


        endGL();
    }
}