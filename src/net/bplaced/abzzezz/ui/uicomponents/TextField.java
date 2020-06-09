/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 07.06.20, 15:09
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.ui.uicomponents;

import ga.abzzezz.util.data.Clipboard;
import net.bplaced.abzzezz.utils.*;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class TextField implements UIComponent {

    private final List<String> backupText = new CopyOnWriteArrayList();
    private final StringBuilder displayText = new StringBuilder();
    private final float xPos;
    private final float yPos;
    private final int width;
    private final int height;
    private boolean clicked, selectedAll;
    private final String name;
    private final FontUtil fontUtil;

    /*
    TODO: More work, Adding to clipboard etc. Text moving, selecting. Make box able to keep up
     */
    public TextField(float xPos, float yPos, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        //Auto set
        this.width = 100;
        this.height = 20;
        this.name = name;
        this.fontUtil = new FontUtil(Util.textFont, width / 7);
    }

    public TextField(float xPos, float yPos, int width, int height, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.name = name;
        this.fontUtil = new FontUtil(Util.textFont, width / 7);
    }

    @Override
    public void drawComponent() {
        ScissorUtil.enableScissor();
        ScissorUtil.scissor(xPos, yPos, width, height);
        RenderUtil.drawQuad(xPos, yPos, width, height, clicked ? Util.mainColor.darker() : Util.mainColor);
        fontUtil.drawString(displayText.toString(), xPos, yPos + height / 2 - fontUtil.getHeight() / 2, selectedAll ? Color.LIGHT_GRAY : textColor);
        ScissorUtil.disableScissor();
        textFont.drawString(name, xPos, yPos - height, textColor);
    }

    /**
     * @param keyCode
     * @param keyTyped
     */
    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (isClicked()) {
            /**
             * Keyboard combos
             */
            if (isControlA()) {
                selectedAll = true;
                return;
            }

            if (isDeleteAll()) {
                deleteAllText();
                return;
            }
            //TODO: Fix clipboard spamming. - Overflow
            if (isControlV()) {
                displayText.append(Clipboard.getClipboard());
                return;
            }

            if (keyCode == Keyboard.KEY_BACK) {
                //Control A Delete
                if (selectedAll) {
                    selectedAll = false;
                    deleteAllText();
                    return;
                }
                if (!(displayText.length() == 0)) {
                    /*
                    If backups lengh is 0 then stop restoring old data.
                     */
                    displayText.delete(getLastDisplayChar()[0], getLastDisplayChar()[1]);
                    if (!(backupText.size() == 0)) {
                        displayText.insert(0, backupText.get(backupText.size() - 1));
                        backupText.remove(backupText.size() - 1);
                    }
                }
            } else {
                //If text out of bounds append old characters to backuptext and delete from displayed string
                if (fontUtil.getStringWidth(displayText.toString()) >= (width - width / 5)) {
                    backupText.add(displayText.substring(0, 1));
                    displayText.delete(0, 1);
                }

                //Append typed char
                if (!(keyCode == Keyboard.KEY_LSHIFT) && !(keyCode == Keyboard.KEY_RSHIFT) && !(keyCode == Keyboard.KEY_RCONTROL) && !(keyCode == Keyboard.KEY_LCONTROL))
                    displayText.append(keyTyped);
            }
        }
    }

    private void deleteAllText() {
        displayText.delete(0, displayText.length());
        backupText.clear();
    }

    private int[] getLastDisplayChar() {
        return new int[]{displayText.length() - 1, displayText.length()};
    }

    /**
     * TODO: Move to separate class
     */
    private boolean isControlA() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && Keyboard.isKeyDown(Keyboard.KEY_A);
    }

    private boolean isDeleteAll() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) && Keyboard.isKeyDown(Keyboard.KEY_BACK);
    }

    private boolean isControlV() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && Keyboard.isKeyDown(Keyboard.KEY_V);
    }

    @Override
    public void mouseListener(int mouseButton) {
        if (isTextFieldHovered() && mouseButton == 0) clicked = !clicked;
    }

    private boolean isTextFieldHovered() {
        return MouseUtil.mouseHovered(xPos, yPos, width, height);
    }

    /**
     * Return text as string by combining backup and displayed text
     *
     * @return
     */
    public String toString() {
        StringBuilder backupOut = new StringBuilder();
        backupText.forEach(s -> {
            backupOut.append(s);
        });

        return backupOut.toString() + displayText.toString();
    }

    public boolean isClicked() {
        return clicked;
    }

    public float getYPos() {
        return yPos;
    }

    public float getXPos() {
        return xPos;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
