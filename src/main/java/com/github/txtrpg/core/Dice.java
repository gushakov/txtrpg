package com.github.txtrpg.core;

import org.springframework.jca.cci.core.InteractionCallback;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author gushakov
 */
public class Dice {

    private static final Pattern pattern = Pattern.compile("^((\\d+,)*\\d+)/(\\d+)$");

    private int[] success;

    private int sides;

    private Random random;

    public Dice(int sides){
        Assert.isTrue(sides > 0);
        this.sides = sides;
        this.random = new Random(System.currentTimeMillis());
    }

    public Dice(String chance){
        Assert.notNull(chance);
        Matcher matcher = pattern.matcher(chance);
        Assert.state(matcher.matches());

       this.success = Arrays.stream(matcher.group(1).split(","))
                .mapToInt(Integer::parseInt).toArray();

        this.sides = Integer.parseInt(matcher.group(3));
        this.random = new Random(System.currentTimeMillis());

    }

    public boolean success(){
        Assert.state(success != null);
        int outcome = 1 + random.nextInt(sides);
        return Arrays.stream(success).filter(s -> s == outcome).findFirst().isPresent();
    }

    public int roll(){
        return 1 + random.nextInt(sides);
    }

    public int index(){
        return random.nextInt(sides);
    }
}
