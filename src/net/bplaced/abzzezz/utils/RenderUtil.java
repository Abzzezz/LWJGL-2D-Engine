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
        glBegin(GL_TRIANGLES);
        {
            drawRectBasis(xPos, yPos, width, height);
        }
        glEnd();
        endGL();
    }

    private static void drawRectBasis(float xPos, float yPos, float width, float height) {
        glVertex2f(xPos, yPos);
        glVertex2f(xPos, yPos + height);
        glVertex2f(xPos + width, yPos + height);

        glVertex2f(xPos + width, yPos + height);
        glVertex2f(xPos + width, yPos);
        glVertex2f(xPos, yPos);

    }
}