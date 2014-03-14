package com.github.txtrpg.core;

import java.util.Collection;

/**
 * @author gushakov
 */
public interface Observable {

    public Collection<Visible> showTo(Actor actor);

}
