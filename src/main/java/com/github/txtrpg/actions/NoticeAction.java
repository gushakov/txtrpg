package com.github.txtrpg.actions;

import com.github.txtrpg.actions.Action;
import com.github.txtrpg.actions.ActionName;
import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Visible;

/**
 * @author gushakov
 */
public class NoticeAction extends Action {

    private Visible visible;

    public NoticeAction(Actor initiator, Visible visible) {
        super(ActionName.notice, initiator);
        this.visible = visible;
    }

    public Visible getVisible() {
        return visible;
    }
}
