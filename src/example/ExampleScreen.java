/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:02
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package example;

import net.bplaced.abzzezz.screens.Button;
import net.bplaced.abzzezz.screens.Screen;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.RenderUtil;

import java.awt.*;

/**
 * Example Class to understand how to make screens
 */
public class ExampleScreen extends Screen {

    /*
    Create new fontutil object
     */
    FontUtil fontUtil;

    /*
    Initialise FontUtil
    add Button (buttonID, Text, xPos, yPos)
     */
    @Override
    public void init() {
        fontUtil = new FontUtil("BigNoodleTitling", 40);
        getButtons().add(new Button(1, "test button", 200, 200));
        super.init();
    }

    /**
     * Check if button ID == Button you denier then run action
     * @param buttonID
     */
    @Override
    public void buttonPressed(float buttonID) {
        if(buttonID == 1) {
            System.out.println("example button down");
        }
        super.buttonPressed(buttonID);
    }

    /**
     * Draw screen and render Font + a Quad
     */
    @Override
    public void drawScreen() {
        fontUtil.drawString("Example String", 100, 100, Color.BLACK);

        RenderUtil.drawQuad(100, 100, 40, 40, Color.BLUE);
        super.drawScreen();
    }
}
