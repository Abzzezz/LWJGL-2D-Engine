/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 16:58
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.ui.uicomponents;

import ga.abzzezz.util.easing.Quint;
import ga.abzzezz.util.math.AnimationUtil;
import ga.abzzezz.util.misc.ColorUtil;
import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.Util;

import java.awt.*;

public class CheckBox implements UIComponent {

    private boolean checked;
    private float xPos, yPos;
    private int size;
    private String text;

    public CheckBox(float xPos, float yPos, int size, String text) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.text = text;
        this.animationUtil = new AnimationUtil(Quint.class, 0, 0,  size / 2, 1, true, false);
    }

    public CheckBox(boolean checked, float xPos, float yPos, int size, String text) {
        this.checked = checked;
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.text = text;
        this.animationUtil = new AnimationUtil(Quint.class, 0, 0,  size / 2, 1, true, false);
    }

    public CheckBox(boolean checked, float xPos, float yPos, String text) {
        this.checked = checked;
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        //Auto set
        this.size = (int) textFont.getHeight();
        this.animationUtil = new AnimationUtil(Quint.class, 0, 0,  size / 2, 1, true, false);
    }

    public CheckBox(float xPos, float yPos, String text) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        //Auto set
        this.size = (int) textFont.getHeight();
        this.animationUtil = new AnimationUtil(Quint.class, 0, 0,  size / 2, 1, true, false);
    }

    private AnimationUtil animationUtil;

    /**
     * Check box now animated
     */
    @Override
    public void drawComponent() {
        animationUtil.animate();
        RenderUtil.drawCircle(xPos, yPos, size, Util.mainColor);

        RenderUtil.drawCircle(xPos, yPos, animationUtil.getInt(), ColorUtil.darker(Util.mainColor, 3));
        textFont.drawString(text, xPos + size, yPos - textFont.getHeight() / 1.5F, Color.BLACK);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {}

    @Override
    public void mouseListener(int mouseButton) {
        if(checkBoxHovered() && mouseButton == 0) {
            checked = !checked;
            animationUtil.reversed = !checked;
        }
    }

    private boolean checkBoxHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, size);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public float getXPos() {
        return xPos;
    }

    public void setYPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYos() {
        return yPos;
    }

    public void setYos(float yPos) {
        this.yPos = yPos;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
