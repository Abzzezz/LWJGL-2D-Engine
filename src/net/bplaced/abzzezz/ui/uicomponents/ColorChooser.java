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

import ga.abzzezz.util.misc.ColorUtil;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.Util;

import java.awt.*;


public class ColorChooser implements UIComponent {


    private final int size;
    private final float xPos;
    private final float yPos;

    /*
    TODO: Make text fit
     */
    private FontUtil fontUtil;

    public ColorChooser(float xPos, float yPos, int size) {
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
        fontUtil = new FontUtil(Util.textFont, size / 5);
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawColorWheel(xPos, yPos, size, 20);

        if (RenderUtil.getCurrentColor() != null) {
            RenderUtil.drawCircle(xPos, yPos, size - 20, 4, RenderUtil.getCurrentColor());
            fontUtil.drawString(ColorUtil.convertColorToHexDecimal(RenderUtil.getCurrentColor()), xPos - size / 2, yPos - fontUtil.getHeight() / 2, textColor);
        }
    }

    public Color getSelectedColor() {
        return RenderUtil.getCurrentColor() != null ? RenderUtil.getCurrentColor() : Color.BLACK;
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
    }
}
