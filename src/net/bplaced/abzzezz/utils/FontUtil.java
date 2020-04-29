/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 13:59
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.utils;


import ga.abzzezz.util.logging.Logger;
import net.bplaced.abzzezz.EngineCore;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class FontUtil {
    private UnicodeFont unicodeFont;

    public FontUtil(String fontName, int size) {
        try {
            InputStream inputStream = new URL("file:" + EngineCore.getInstance().getFontDir() + fontName + ".ttf").openStream();
            Font awtFont = Font.createFont(Font.PLAIN, inputStream);
            this.unicodeFont = new UnicodeFont(awtFont, size, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Font rendering using Slick
     * @param text
     * @param xPos
     * @param yPos
     * @param color
     */
    public void drawString(String text, float xPos, float yPos, Color color) {
        try {
            GL11.glEnable(GL11.GL_BLEND);
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(color));
            unicodeFont.loadGlyphs();
            unicodeFont.drawString(xPos, yPos, text, new org.newdawn.slick.Color(color.getRGB()));
            GL11.glDisable(GL11.GL_BLEND);
        } catch (SlickException e) {
            Logger.log("Error rendering font", Logger.LogType.ERROR);
        }
    }

    public int getStringWidth(String text) {
        return this.unicodeFont.getWidth(text);
    }

    public float getHeight() {
        return this.unicodeFont.getFont().getSize();
    }

}