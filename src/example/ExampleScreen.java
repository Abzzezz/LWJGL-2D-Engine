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
import net.bplaced.abzzezz.ui.uicomponents.*;
import net.bplaced.abzzezz.ui.Screen;
import net.bplaced.abzzezz.ui.uicomponents.Button;
import net.bplaced.abzzezz.ui.uicomponents.TextField;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

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

    int xPos = 100;

    private TextField textField;
    @Override
    public void init() {
        Logger.log("Refreshed", Logger.LogType.INFO);

        //Set arraylist
        ArrayList arrayList= new ArrayList();
        arrayList.add("Example element");
        arrayList.add("Example element 2");
        //Create listview object
        ListView listView = new ListView(arrayList, xPos, 400, 100, "Example ListView");
        //Add listener
        listView.setClickListener((index, item) -> {
            //Print index of clicked element
            System.out.println("Index clicked " + index);
        });

        //init textfield
        this.textField = new TextField(xPos, 100, 200, 40, "Example Text box");
        this.fontUtil = new FontUtil("Roboto-Light", 20);

        //Add new button
        getUiComponents().add(new Button(1, "This is a example button", xPos, 200));
        //Add textfield
        getUiComponents().add(textField);
        //Create checkbox
        getUiComponents().add(new CheckBox(false, xPos, 300, 10, "This is a example checkbox"));
        //Add listview
        getUiComponents().add(listView);
        //Add colorchooser

        ColorChooser colorChooser = new ColorChooser(xPos + 200, 100, 70);
        colorChooser.setColorSelectedListener(color -> {
            System.out.println("Color selected:" + color);
        });
        getUiComponents().add(colorChooser);

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
            //Print textfield text
            System.out.println(textField.toString());;
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
        fontUtil.drawString("This is a example string at 10, 10", xPos, 10, Color.BLACK);
        RenderUtil.drawQuad(xPos, 500, 40, 40, Color.BLUE);
        super.drawScreen();
    }
}
