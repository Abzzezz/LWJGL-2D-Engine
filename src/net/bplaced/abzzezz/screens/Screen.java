/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:02
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.screens;

import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class Screen {

    private List<Button> buttons = new ArrayList<>();

    /**
     * Int method to add things like buttons etc.
     */
    public void init() {
    }

    /**
     * Gets called when button is pressed then looks for action event
     * @param buttonID
     */
    public void buttonPressed(float buttonID) {
    }

    /*
    Screen drawing - simple
     */
    public void drawScreen() {
        buttons.forEach(button -> {
                    button.drawButton();
                }
        );
    }

    /**
     * Gets called if mouse button down
     * @param mouseButton
     */
    public void mousePressed(int mouseButton) {
        buttons.forEach(menuButton -> {
            if (menuButton.isButtonHovered() && mouseButton == 0) {
                buttonPressed(menuButton.getId());
            }
        });
    }

    /**
     * Gets called when keyboard key is down
     * @param keyCode
     * @param keyTyped
     */
    public void keyTyped(int keyCode, char keyTyped) {
    }

    protected int getWidth() {
        return Display.getWidth();
    }

    protected int getHeight() {
        return Display.getHeight();
    }

    public List<Button> getButtons() {
        return buttons;
    }
}
