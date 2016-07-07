package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Observable;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;

import java.util.Collection;
import java.util.Collections;

/**
 * @author gushakov
 */
public class LookAction extends Action {

    private Visible target;

    public LookAction(Actor initiator) {
        super(ActionName.look, initiator);
    }

    public LookAction(Actor initiator, Visible target) {
        super(ActionName.look, initiator);
        this.target = target;
    }

    public Visible getTarget() {
        return target;
    }

    @Override
    public Collection<Action> process() {
        synchronized (lock){
            Actor actor = getInitiator();
            if (actor instanceof Player) {
                Player player = (Player) actor;
                if (target != null) {
                    final StringBuilder builder = new StringBuilder(target.getDescription());
                    if (target instanceof Observable){
                        builder.append(" Looking inside you find:");
                        ((Observable)target).showTo(player)
                                .forEach(visible -> {
                                    builder.appendCodePoint(10)
                                            .appendCodePoint(9)
                                            .append("#")
                                            .append(visible.getName())
                                    .append("#")
                                    ;
                                });

                    }

                    player.sendMessage(builder.toString());

                } else {
                    Collection<Visible> visibles = player.getLocation().showTo(player);
                    if (visibles.isEmpty()){
                        player.sendMessage(player.getLocation().getDescription());
                    }
                    else {
                        player.sendMessage(player.getLocation().getDescription(), true, false);
                        visibles.stream()
                                .forEach(v -> player.sendMessage(v.getDescription()));
                    }
                }
            }
            return Collections.emptyList();
        }
    }
}
