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

import ga.abzzezz.util.logging.Logger;
import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.ScissorUtil;
import net.bplaced.abzzezz.utils.Util;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListView implements UIComponent {

    /*
    TODO: More work
     */
    protected int scrollY;
    private final List<ListViewElement> list;
    private final float xPos;
    private final float yPos;
    private final int width;
    private final int height;
    private final String title;

    public ListView(List<Object> list, float xPos, float yPos, int height, String title) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = textFont.getStringWidth(title);
        this.title = title;
        this.list = new CopyOnWriteArrayList<>();
        list.forEach(o -> this.list.add(new ListViewElement(o)));
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void drawComponent() {
        int yBuffer = 0;
        RenderUtil.drawQuad(xPos, yPos, width, height, Util.mainColor);
        textFont.drawString(title, xPos, yPos - textFont.getHeight(), textColor);

        ScissorUtil.enableScissor();
        ScissorUtil.scissor(xPos, yPos, width, height);

        for (ListViewElement entry : list) {
            float entryY = yBuffer + yPos + scrollY;

            if (entry.isHovered()) RenderUtil.drawQuad(xPos, entryY, width, textFont.getHeight(), Color.LIGHT_GRAY);
            textFont.drawString(entry.getObjectString(), xPos, entryY, textColor);


            entry.setyPos(entryY);
            yBuffer += ((int) textFont.getHeight());
        }
        /**
         * Scroll wheel support
         */
        if (Mouse.hasWheel() && yBuffer > height) {
            int wheel = Mouse.getDWheel() / 120;
            if (wheel < 0) {
                if (!(scrollY > yBuffer))
                    scrollY += textFont.getHeight();
            } else if (wheel > 0) {
                if (!(scrollY < -yBuffer))
                    scrollY -= textFont.getHeight();
            }
        }

        ScissorUtil.disableScissor();
    }

    /**
     * Add list item. refreshes collection
     */
    public void addToList(Object item) {
        this.list.add(new ListViewElement(item));
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    private onListViewElementClicked clickListener;

    public void setClickListener(onListViewElementClicked clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void mouseListener(int mouseButton) {
        list.forEach(listViewElement -> {
            if (listViewElement.isHovered() && mouseButton == 0) {
                if (clickListener != null) {
                    clickListener.onItemClicked(list.indexOf(listViewElement), listViewElement);
                } else {
                    Logger.log("On item click listener for class " + getClass() + "not initialised", Logger.LogType.WARNING);
                }
            }
        });
    }

    /**
     * Used for drawing shaders on objects
     */
    @Override
    public void drawShader() {

    }

    public interface onListViewElementClicked {
        void onItemClicked(int index, ListViewElement item);
    }

    public class ListViewElement {

        private final Object object;
        private float yPos;

        public ListViewElement(Object o) {
            this.object = o;
        }

        public Object getObject() {
            return object;
        }

        public String getObjectString() {
            return object.toString();
        }

        public void setyPos(float yPos) {
            this.yPos = yPos;
        }

        public float getyPos() {
            return yPos;
        }

        public boolean isHovered() {
            return MouseUtil.mouseHovered(xPos, yPos, width, textFont.getHeight());
        }
    }
}
