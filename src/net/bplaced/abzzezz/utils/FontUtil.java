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


import net.bplaced.abzzezz.EngineCore;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class FontUtil {
    private UnicodeFont unicodeFont;

    public FontUtil(String fontName, int size) {
        try {
            String fontDir = EngineCore.getInstance().getFontDir() + fontName + ".ttf";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fontDir);
            Font awtFont = Font.createFont(Font.PLAIN, inputStream);
            this.unicodeFont = new UnicodeFont(awtFont, size, false, false);
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Font rendering using Slick
     *
     * @param text
     * @param xPos
     * @param yPos
     * @param color
     */
    public void drawString(String text, float xPos, float yPos, Color color) {
        GL11.glEnable(GL11.GL_BLEND);
        unicodeFont.drawString(xPos, yPos, text, new org.newdawn.slick.Color(color.getRGB()));
        GL11.glDisable(GL11.GL_BLEND);
    }

    public int getStringWidth(String text) {
        return this.unicodeFont.getWidth(text);
    }

    public float getHeight() {
        return this.unicodeFont.getFont().getSize();
    }

}