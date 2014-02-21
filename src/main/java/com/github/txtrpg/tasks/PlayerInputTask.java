package com.github.txtrpg.tasks;

import com.github.txtrpg.core.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author gushakov
 */
public class PlayerInputTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlayerInputTask.class);
    private World world;
    private String rawInput;

    public PlayerInputTask(World world, String rawInput) {
        this.world = world;
        this.rawInput = rawInput;
    }

    @Override
    public void run() {
        try {
            // convert input to UTF-8
            String line = new String(rawInput.getBytes("ISO-8859-1"), "UTF-8");
            // get rid of all control characters
            line = line.replaceAll("[\u0000-\u001f]", "");
            logger.debug("Read line line {}", line);
            if (line.equalsIgnoreCase("bye")) {
                System.exit(1);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
