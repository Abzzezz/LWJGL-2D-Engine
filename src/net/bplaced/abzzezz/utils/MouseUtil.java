/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 13:36
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.utils;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseUtil {

    /**
     * Checks if mouse in a quad radius
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     * @return
     */
    public static boolean mouseHovered(float xPos, float yPos, float width, float height) {
        int[] mouse = getMousePositions();
        return mouse[0] >= xPos && mouse[0] <= xPos + width && mouse[1] >= yPos && mouse[1] <= yPos + height;
    }

    /**
     * Mouse positions
     * @return
     */
    private static int[] getMousePositions() {
        int x = Mouse.getX();
        int y = Display.getHeight() - Mouse.getY();
        return new int[]{x, y};
    }
}
