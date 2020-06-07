/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 15:15
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.ui.uicomponents;

import net.bplaced.abzzezz.EngineCore;
import net.bplaced.abzzezz.utils.FontUtil;

import java.awt.*;

public interface UIComponent {

    EngineCore engineCoreInstance = EngineCore.getInstance();
    FontUtil textFont = new FontUtil("Roboto-Light", 20);
    Color textColor = Color.BLACK;

    void drawComponent();
    void keyListener(int keyCode, char keyTyped);
    void mouseListener(int mouseButton);
}
