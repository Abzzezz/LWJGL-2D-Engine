/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:52
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.screens;

import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;

import java.awt.*;

public class Button {

    private String text;
    private float xPos, yPos, id;

    private FontUtil smallFont = new FontUtil("SIMPLIFICA", 20);

    /**
     * Simple button. Buttons can be added just use this as a parent
     * @param id
     * @param text
     * @param xPos
     * @param yPos
     */
    public Button(float id, String text, float xPos, float yPos) {
        this.id = id;
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void drawButton() {
        RenderUtil.drawQuad(xPos - 20, yPos, 100, getDimensions()[1], new Color(0xAB89EE));
        smallFont.drawString(text, xPos, yPos, isButtonHovered() ? new Color(0xAB89EE): Color.GRAY);
    }

    public boolean isButtonHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, getDimensions()[0], getDimensions()[1]);
    }

    public float getId() {
        return id;
    }

    public float[] getDimensions() {
        return new float[]{smallFont.getStringWidth(text), smallFont.getHeight()};
    }
}
