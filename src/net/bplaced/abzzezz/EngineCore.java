/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:04
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz;

import ga.abzzezz.util.logging.Logger;
import net.bplaced.abzzezz.file.FileManager;
import net.bplaced.abzzezz.screens.Screen;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class EngineCore {

    private static EngineCore instance;
    private String gameName, fontDir;
    private float gameVersion;
    private Screen screen;
    private File mainDir;
    private int fpsSync, width, height;

    /*
    Handlers
     */

    private FileManager fileManager;

    /**
     * Engine Core init. If left empty it gets auto set.
     *
     * @param gameName
     * @param gameVersion
     * @param width
     * @param height
     * @param startScreen
     * @param fpsSync
     */
    public EngineCore(String gameName, float gameVersion, int width, int height, Screen startScreen, int fpsSync, File outDir, String fontDir) {
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.fontDir = fontDir;
        this.screen = startScreen;
        this.fpsSync = fpsSync;
        this.mainDir = outDir;
        this.width = width;
        this.height = height;
        this.initHeaders();
    }

    /**
     * Auto Config
     *
     * @param width
     * @param height
     * @param startScreen
     */
    public EngineCore(int width, int height, Screen startScreen) {
        this.gameName = "Test Game";
        this.gameVersion = 1.0F;
        this.fontDir = FontUtil.class.getResource("font/").getPath();
        this.screen = startScreen;
        this.fpsSync = 60;
        this.mainDir = new File(System.getProperty("user.home"), gameName);
        this.width = width;
        this.height = height;
        this.initHeaders();
    }

    public static EngineCore getInstance() {
        return instance;
    }

    /**
     * Method to initialise headers before the engine gets configured further
     */
    private void initHeaders() {
        instance = this;
        Logger.log("Initialising headers", Logger.LogType.INFO);
        /*
        Create directory if it does not exists
         */
        if (!mainDir.exists()) mainDir.mkdir();

        this.fileManager = new FileManager();
    }

    /**
     * Start method. Must be called after configuring
     */
    public void start() {
        /*
        For Debug purposes
         */
        Logger.log("Game Engine starting", Logger.LogType.INFO);
        Logger.log("Game name:" + gameName, Logger.LogType.INFO);
        Logger.log("Game version: " + gameVersion, Logger.LogType.INFO);
        Logger.log("fps Sync: " + fpsSync, Logger.LogType.INFO);
        Logger.log("Font Path: " + fontDir, Logger.LogType.INFO);
        Logger.log("Loading files", Logger.LogType.INFO);

        /*
        Loads files
        TODO: Add more handlers
         */
        fileManager.load();
        Logger.log("Game starting", Logger.LogType.INFO);

        /*
        Call run
         */
        run(width, height);
    }

    /**
     * @param width
     * @param height
     */
    private void run(int width, int height) {
        initGL(width, height);
        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            update();
            Display.update();
            Display.sync(fpsSync);
            if (Display.isCloseRequested()) {
                Display.destroy();
                System.exit(0);
            }
        }
    }

    /**
     * Init GL Method to initialise OpenGL
     * @param width
     * @param height
     */
    private void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setTitle(gameName + gameVersion);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        /*
        Init first screen
         */
        screen.init();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearColor(Util.backgroundColor.getRed() / 255.0F, Util.backgroundColor.getGreen() / 255.0F, Util.backgroundColor.getBlue() / 255.0F, Util.backgroundColor.getAlpha() / 255.0F);
        GL11.glClearDepth(1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     * Update Method
     */
    private void update() {
        screen.drawScreen();
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) screen.mousePressed(Mouse.getEventButton());
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) screen.keyTyped(Keyboard.getEventKey(), Keyboard.getEventCharacter());
        }
    }

    /**
     * Getters and setters to configure
     *
     */

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public float getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(float gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        screen.init();
        this.screen = screen;
    }

    public File getMainDir() {
        return mainDir;
    }

    public void setMainDir(File mainDir) {
        this.mainDir = mainDir;
    }

    public int getFpsSync() {
        return fpsSync;
    }

    public void setFpsSync(int fpsSync) {
        this.fpsSync = fpsSync;
    }

    public String getFontDir() {
        return fontDir;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setFontDir(String fontDir) {
        this.fontDir = fontDir;
    }

}
