package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gushakov
 */
public class DelayedAction extends Action {
    private Action delegate;

    private int delay;

    private AtomicInteger counter;

    public DelayedAction(Action delegate, int delay) {
        super(delegate.getName(), delegate.getInitiator());
        this.delegate = delegate;
        this.delay = delay;
        this.counter.set(0);
    }

    @Override
    public synchronized Collection<Action> process() {
        if (counter.incrementAndGet() > delay){
            System.out.println("iiiiiiiiiii");
            System.out.println("iiiiiiiiiii");
            System.out.println("iiiiiiiiiii");
            System.out.println("iiiiiiiiiii");
            System.out.println("iiiiiiiiiii");
            return delegate.process();
        }
        else {
            System.out.println("+");
            System.out.println("+");
            System.out.println("+");
            System.out.println("+");
            System.out.println("+");
           return Collections.singletonList(this);
        }
    }
}
