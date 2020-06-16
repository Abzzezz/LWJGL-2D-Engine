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

import ga.abzzezz.util.logging.Logger;
import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.Util;

public class Button implements UIComponent {

    private final String text;
    private final float xPos;
    private final float yPos;
    private final float id;
    private final int width, height;

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
        this.width = textFont.getStringWidth(text);
        this.height = (int) textFont.getHeight();
    }

    public Button(float id, String text, float xPos, float yPos, int width, int height) {
        this.id = id;
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public boolean buttonHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, getDimensions()[0], getDimensions()[1]);
    }

    public float getId() {
        return id;
    }

    public int[] getDimensions() {
        return new int[]{width, height};
    }

    @Override
    public void initComponent() {
    }

    /**
     * Size is dependent on text length
     */
    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, getDimensions()[0], getDimensions()[1], buttonHovered() ? Util.mainColor.darker() : Util.mainColor);
        textFont.drawString(text, xPos + width / 2 - textFont.getStringWidth(getText()) / 2, yPos, buttonHovered() ? textColor.brighter() : textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    /**
     * Button pressed
     *
     * @param mouseButton
     */
    private ButtonPressed buttonPressed;

    public void setButtonPressed(Button.ButtonPressed buttonPressed) {
        this.buttonPressed = buttonPressed;
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (buttonHovered() && mouseButton == 0) engineCoreInstance.getScreen().buttonPressed(getId());

        if (buttonHovered()) {
            if (buttonPressed != null)
                buttonPressed.onButtonPressed(mouseButton, this);
            else
                Logger.log("Button pressed handler not initialised", Logger.LogType.INFO);
        }
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public String getText() {
        return text;
    }


    public interface ButtonPressed {
        void onButtonPressed(int mouseButton, Button button);
    }
}
