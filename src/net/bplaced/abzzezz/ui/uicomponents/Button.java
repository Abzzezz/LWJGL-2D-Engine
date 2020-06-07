/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 10.05.20, 20:40
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.ui.uicomponents;

import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.Util;

public class Button implements UIComponent {

    private String text;
    private float xPos, yPos, id;

    /**
     * Simple button. Buttons can be added just use this as a parent
     *
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

    public boolean isButtonHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, getDimensions()[0], getDimensions()[1]);
    }

    public float getId() {
        return id;
    }

    public float[] getDimensions() {
        return new float[]{textFont.getStringWidth(text), textFont.getHeight()};
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, getDimensions()[0], getDimensions()[1], Util.mainColor);
        textFont.drawString(text, xPos, yPos, isButtonHovered() ? textColor.brighter() : textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (isButtonHovered() && mouseButton == 0) engineCoreInstance.getScreen().buttonPressed(getId());
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }
}
