/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 13:51
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package example;

import net.bplaced.abzzezz.EngineCore;
import net.bplaced.abzzezz.utils.Util;

import java.awt.*;

public class ExampleMain {

    /**
     * Example main class
     * @param args
     */
    public static void main(String[] args) {
        /*
        Init Engine
        Then configure further (here: add new File Object)
        Then start
         */
        EngineCore engineCore = new EngineCore(600, 600, new ExampleScreen());
        engineCore.getFileManager().getFiles().add(new ExampleFile("EXAMPLEFILE.EXAMPLE"));
        engineCore.start();
    }
}
