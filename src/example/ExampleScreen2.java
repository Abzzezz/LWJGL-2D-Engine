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

import ga.abzzezz.util.logging.Logger;
import net.bplaced.abzzezz.EngineCore;
import net.bplaced.abzzezz.ui.Screen;
import net.bplaced.abzzezz.ui.uicomponents.Button;
import net.bplaced.abzzezz.ui.uicomponents.TextField;
import net.bplaced.abzzezz.ui.uicomponents.*;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

/**
 * Example Class to understand how to make screens
 */
public class ExampleScreen2 extends Screen {

    /*
    Create new fontutil object
     */

    /*
    Initialise FontUtil
    add Button (buttonID, Text, xPos, yPos)
     */

    int xPos = 100;

    private TextField textField;
    @Override
    public void init() {
        Logger.log("Refreshed", Logger.LogType.INFO);

        //Add new button
        getUiComponents().add(new Button(1, "This is a example button", xPos, 200));
        super.init();
    }

    /**
     * Check if button ID == Button you denier then run action
     * @param buttonID
     */
    @Override
    public void buttonPressed(float buttonID) {
        if(buttonID == 1) {
            System.out.println("New button");
        }
        super.buttonPressed(buttonID);
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        /**
         * Debug refresh
         */
        if(keyCode == Keyboard.KEY_F5) {
            EngineCore.getInstance().setScreen(this);
        }
        super.keyTyped(keyCode, keyTyped);
    }

    /**
     * Draw screen and render Font + a Quad
     */
    @Override
    public void drawScreen() {
        super.drawScreen();
    }
}