/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 21:27
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.ui.uicomponents;

import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.ScissorUtil;
import net.bplaced.abzzezz.utils.Util;
import org.lwjgl.input.Mouse;

import java.util.List;

public class ListView<E> implements UIComponent {

    private List<E> list;
    private float xPos, yPos;
    private int width, height;
    private String title;

    public ListView(List<E> list, float xPos, float yPos, int height, String title) {
        this.list = list;
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = textFont.getStringWidth(title);
        this.title = title;
    }

    protected int scrollY;

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, Util.mainColor);
        textFont.drawString(title, xPos, yPos - textFont.getHeight(), textColor);

        int yBuffer = 0;

        ScissorUtil.enableScissor();
        ScissorUtil.scissor(xPos, yPos, width, height);
        for (E entry : list) {
            textFont.drawString(String.valueOf(entry), xPos, yPos + yBuffer + scrollY, textColor);
            yBuffer += ((int) textFont.getHeight());
        }
        /**
         * Scroll wheel support
         */
        if (Mouse.hasWheel() && yBuffer > height) {
            int wheel = Mouse.getDWheel() / 120;
            if (wheel < 0) {
                if(!(scrollY > yBuffer))
                    scrollY += textFont.getHeight();
            } else if(wheel > 0) {
                if(!(scrollY < -yBuffer))
                    scrollY -= textFont.getHeight();
            }
        }

        ScissorUtil.disableScissor();
    }

    /**
     * Add list item. refreshes collection
     */
    public void addToList(E item) {
        this.list.add(item);
        this.width = 100;
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {

    }

    @Override
    public void mouseListener(int mouseButton) {

    }
}
