/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 08.06.20, 15:23
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.ui.uicomponents;

import ga.abzzezz.util.easing.Bounce;
import ga.abzzezz.util.easing.Quint;
import ga.abzzezz.util.easing.Sine;
import ga.abzzezz.util.math.AnimationUtil;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.Util;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;


public class ColorChooser implements UIComponent {


    private final int size, lineWidth;
    private final float xPos;
    private final float yPos;


    /*
    TODO: Fix auto opening
     */

    private FontUtil fontUtil;
    private AnimationUtil animationUtil;

    public ColorChooser(float xPos, float yPos, int size, String text, float buttonID) {
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        this.fontUtil = new FontUtil(Util.textFont, size / 5);
        this.lineWidth = size / 5;
        this.animationUtil = new AnimationUtil(Sine.class, 0, 0, size, 1, true, false);
        Button button = new Button(buttonID, text, xPos, yPos);
        button.setButtonPressed(mouseButton -> {
            clicked = !clicked;
            animationUtil.reversed = !clicked;
        });
        this.engineCoreInstance.getScreen().getUiComponents().add(button);
    }

    boolean clicked;

    @Override
    public void drawComponent() {
        animationUtil.animate();
        drawColorWheel(xPos, yPos, animationUtil.getInt(), lineWidth);

        if(animationUtil.velocity >= size - 10) RenderUtil.drawCircle(xPos + xy[0], yPos + xy[1], 5, 1, new Color(0, 0, 0, 50));
    }

    /**
     * Draw stuff
     */

    private void drawColorWheel(float xPos, float yPos, int radius, int lWidth) {
        RenderUtil.setupGL();
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(lWidth);
        glBegin(GL_LINE_LOOP);
        {
            double theta = (2 * Math.PI / 360.0);
            double tangential_factor = Math.tan(theta);//calculate the tangential factor
            double radial_factor = Math.cos(theta);//calculate the radial factor
            float x = radius;//we start at angle = 0
            float y = 0;
            for (int ii = 0; ii < 360; ii++) {
                float[] hueColor = RenderUtil.getLWJGLColor(RenderUtil.getHue(ii));
                glColor4f(hueColor[0], hueColor[1], hueColor[2], hueColor[3]);
                glVertex2f(x + xPos, y + yPos);

                //calculate the tangential vector
                //remember, the radial vector is (x, y)
                //to get the tangential vector we flip those coordinates and negate one of them

                float tx = -y;
                float ty = x;

                //add the tangential vector

                x += tx * tangential_factor;
                y += ty * tangential_factor;

                //correct using the radial factor
                x *= radial_factor;
                y *= radial_factor;
            }
        }
        glEnd();
        RenderUtil.endGL();
    }

    private ColorSelectedListener colorSelectedListener;

    private boolean colorChooserHovered(float x, float y) {
        return MouseUtil.mouseHovered(xPos + x, yPos + y, 2);
    }

    public void setColorSelectedListener(ColorChooser.ColorSelectedListener colorSelectedListener) {
        this.colorSelectedListener = colorSelectedListener;
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        double theta = (2 * Math.PI / 360.0);
        double tangential_factor = Math.tan(theta);//calculate the tangential factor
        double radial_factor = Math.cos(theta);//calculate the radial factor
        float x = size;//we start at angle = 0
        float y = 0;
        for (int ii = 0; ii < 360; ii++) {
            if (colorChooserHovered(x, y)) {
                if (colorSelectedListener != null) colorSelectedListener.onColorSelected(RenderUtil.getHue(ii));
                xy[0] = x;
                xy[1] = y;
            }
            //calculate the tangential vector
            //remember, the radial vector is (x, y)
            //to get the tangential vector we flip those coordinates and negate one of them

            float tx = -y;
            float ty = x;

            //add the tangential vector

            x += tx * tangential_factor;
            y += ty * tangential_factor;

            //correct using the radial factor
            x *= radial_factor;
            y *= radial_factor;
        }
    }

    private float xy[] = new float[2];

    public interface ColorSelectedListener {
        void onColorSelected(Color color);
    }
}
