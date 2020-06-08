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
import net.bplaced.abzzezz.utils.RenderUtil;

public class ColorChooser implements UIComponent {


    private int size;
    private float xPos, yPos;
    private String dialog;


    public ColorChooser(float xPos, float yPos, int size) {
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawColorWheel(xPos, yPos, size, 20);

        if(RenderUtil.getCurrentColor() != null) {
            RenderUtil.drawCircle(xPos, yPos, size - 20, RenderUtil.getCurrentColor());
            textFont.drawString(ColorUtil.convertColorToHexDecimal(RenderUtil.getCurrentColor()), xPos - size / 2, yPos - textFont.getHeight() / 2, textColor);
        }
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {}

    @Override
    public void mouseListener(int mouseButton) {
    }
}
