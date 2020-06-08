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

import net.bplaced.abzzezz.ui.uicomponents.*;
import net.bplaced.abzzezz.ui.Screen;
import net.bplaced.abzzezz.ui.uicomponents.Button;
import net.bplaced.abzzezz.ui.uicomponents.TextField;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;

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

    private TextField textField;
    @Override
    public void init() {
        ArrayList arrayList= new ArrayList();
        arrayList.add("Test");
        arrayList.add("Test 1");

        this.textField = new TextField(10, 100, "Example Text box");
        this.fontUtil = new FontUtil("Roboto-Light", 20);
        getUiComponents().add(new Button(1, "This is a example button", 10, 200));
        getUiComponents().add(textField);
        getUiComponents().add(new CheckBox(10, 300, 10, "This is a checkbox"));
        getUiComponents().add(new ListView<>(arrayList, 100, 400, 100, "Example ListView"));
        getUiComponents().add(new ColorChooser(500, 100, 100));
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
            System.out.println(textField.toString());;
        }
        super.buttonPressed(buttonID);
    }

    /**
     * Draw screen and render Font + a Quad
     */
    @Override
    public void drawScreen() {
        fontUtil.drawString("This is a example string at 10, 10", 10, 10, Color.BLACK);
        RenderUtil.drawQuad(10, 500, 40, 40, Color.BLUE);
        super.drawScreen();
    }
}
