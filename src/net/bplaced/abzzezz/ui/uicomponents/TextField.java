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
import net.bplaced.abzzezz.utils.MouseUtil;
import net.bplaced.abzzezz.utils.RenderUtil;
import net.bplaced.abzzezz.utils.Util;
import org.lwjgl.input.Keyboard;


public class TextField implements UIComponent {

    private StringBuilder backupText = new StringBuilder(), displayText = new StringBuilder();
    private float xPos, yPos;
    private int width, height;
    private boolean clicked;
    private String name;

    public TextField(float xPos, float yPos, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        //Auto set
        this.width = 100;
        this.height = 20;
        this.name = name;
    }

    public TextField(float xPos, float yPos, int width, int height, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    @Override
    public void drawComponent() {
        RenderUtil.drawQuad(xPos, yPos, width, height, Util.mainColor);
        textFont.drawString(displayText.toString(), xPos, yPos, textColor);
        textFont.drawString(name, xPos, yPos - height, textColor);
    }


    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (isClicked()) {
            if (isDeleteAll()) {
                displayText.delete(0, displayText.length());
                backupText.delete(0, backupText.length());
            }
            //TODO: Fix clipboard spamming. - Overflow
            if (isControlV()) {
                displayText.append(Clipboard.getClipboard());
            }
            if (keyCode == Keyboard.KEY_BACK) {
                if (!(displayText.length() == 0)) {
                    /*
                    If backups lengh is 0 then stop restoring old data.
                     */
                    displayText.delete(getLastDisplayChar()[0], getLastDisplayChar()[1]);
                    if (!(backupText.length() == 0)) {
                        int[] bounds = {backupText.length() - 1, backupText.length()};
                        displayText.insert(0, backupText.substring(bounds[0], bounds[1]));
                        backupText.delete(bounds[0], bounds[1]);
                    }
                }
            } else {
                //If text out of bounds append old characters to backuptext and delete from displayed string
                if (textFont.getStringWidth(displayText.toString()) >= width - textFont.getStringWidth(String.valueOf(keyTyped))) {
                    backupText.append(displayText.substring(0, 1));
                    displayText.delete(0, 1);
                }

                //Append typed char
                if (!(keyCode == Keyboard.KEY_LSHIFT) && !(keyCode == Keyboard.KEY_RSHIFT))
                    displayText.append(keyTyped);
            }
        }
    }

    private int[] getLastDisplayChar() {
        return new int[] {displayText.length() - 1, displayText.length()};
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
        return backupText.toString() + displayText.toString();
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
