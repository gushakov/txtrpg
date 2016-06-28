package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;

import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gushakov
 */
public class DelayedAction extends Action {
    private Action delegate;

    public DelayedAction(Action delegate, int delay) {
        super(delegate.getName(), delegate.getInitiator(), delay);
        this.delegate = delegate;
        System.out.println(">>>>>>>>>>>>>>>>"+getTime());
    }

    @Override
    public synchronized Collection<Action> process() {
       return delegate.process();
    }
}
