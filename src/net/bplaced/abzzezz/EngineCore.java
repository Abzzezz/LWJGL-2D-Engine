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
import net.bplaced.abzzezz.file.CustomFile;
import net.bplaced.abzzezz.file.FileManager;
import net.bplaced.abzzezz.ui.Screen;
import net.bplaced.abzzezz.utils.FontUtil;
import net.bplaced.abzzezz.utils.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.io.File;

public class EngineCore {

    private static EngineCore instance;
    private String gameName, fontDir;
    private float gameVersion;
    private Screen screen;
    private File mainDir;
    private int fpsSync;
    private final int width;
    private final int height;
    private OpenGLListener openGLListener;

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
        this.fontDir = "./font/";
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

        if (!mainDir.exists()) mainDir.mkdir();
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
        if (openGLListener != null) openGLListener.onGLInitialised();
        while (true) {
            update();
            Display.update();
            Display.sync(fpsSync);
            if (Display.isCloseRequested()) {
                shutdownHook();
                Display.destroy();
                System.exit(0);
            }
        }
    }

    /**
     * Init GL Method to initialise OpenGL
     *
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

        //Enable Textures and configure
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        //Clear color
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glClearDepth(1.0f);
        //Set viewport
        GL11.glViewport(0, 0, width, height);
        //Enable Blend
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    /**
     * Update Method
     */
    private void update() {
        //Screen element projection
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 0.0f, 1.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        screen.drawScreen();

        //Shader Projection
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f, ((float) width / (float) height), 100.0f, 0.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        screen.drawShader();

        /**
         * Mouse and Keyboard hook
         */
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) screen.mousePressed(Mouse.getEventButton());
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) screen.keyTyped(Keyboard.getEventKey(), Keyboard.getEventCharacter());
        }
    }

    private void shutdownHook() {
        Logger.log("Shutdown Hook", Logger.LogType.INFO);
        fileManager.save();
        Logger.log("Saving files", Logger.LogType.INFO);
        if (openGLListener != null) openGLListener.onCloseRequested();
    }

    /**
     * Getters and setters to configure
     */

    public void setOpenGLListener(OpenGLListener openGLListener) {
        this.openGLListener = openGLListener;
    }

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
        //Clear old screen
        this.screen.getUiComponents().clear();
        //Init new screen
        screen.init();
        //Set new screen
        this.screen = screen;
    }

    public void addSaveFile(CustomFile file) {
        getFileManager().getFiles().add(file);
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

    public void setFontDir(String fontDir) {
        this.fontDir = fontDir;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setTextColor(Color color) {
        Util.textColor = color;
    }

    public void setMainColor(Color color) {
        Util.mainColor = color;
    }

    public void setTextFont(String fontName) {
        Util.textFont = fontName;
    }

    public void setBackgroundColor(Color color) {
        Util.backgroundColor = color;
    }

    public interface OpenGLListener {
        /**
         * Hook for initialised OpenGL
         */
        void onGLInitialised();

        /**
         * Hook for the user to add elements that are run when close is requested
         */
        void onCloseRequested();
    }

}
