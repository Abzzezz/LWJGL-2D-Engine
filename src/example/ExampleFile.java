/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 15:06
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package example;

import ga.abzzezz.util.data.FileUtil;
import net.bplaced.abzzezz.file.CustomFile;

import java.util.Arrays;

/**
 * Usage of Custom Files
 */
public class ExampleFile extends CustomFile {

    /**
     * Setup File with name
     * @param fileName
     */
    public ExampleFile(String fileName) {
        super(fileName);
    }

    /**
     * Write (uses AbzzezzUtil)
     */
    @Override
    public void write() {
        FileUtil.addToFile("EXAMPLE TEXT", thisFile);
    }

    /**
     * Read file and print
     */
    @Override
    public void read() {
        System.out.println(Arrays.toString(FileUtil.getFileContentAsList(thisFile).toArray()));
    }
}
