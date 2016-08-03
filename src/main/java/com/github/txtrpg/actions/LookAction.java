package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Observable;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.core.Visible;
import com.github.txtrpg.message.ColumnLayout;
import com.github.txtrpg.message.MessageBuilder;

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
        synchronized (lock) {
            Actor actor = getInitiator();
            if (actor instanceof Player) {
                Player player = (Player) actor;
                if (target != null) {
                    final MessageBuilder messageBuilder = new MessageBuilder().parse(target.getDescription());
                    if (target instanceof Observable) {
                        messageBuilder.append(" Looking inside you find:").withColumns(ColumnLayout.Single);
                        ((Observable) target).showTo(player)
                                .forEach(visible -> messageBuilder.append(visible.getName()).tab());
                        messageBuilder.end();
                    }
                    player.sendMessage(messageBuilder.toString());
                } else {
                    final MessageBuilder messageBuilder = new MessageBuilder(player.getLocation().getDescription()).br();
                    final Collection<Visible> visibles = player.getLocation().showTo(player);
                    if (!visibles.isEmpty()) {
                        visibles.stream().forEach(v -> messageBuilder.parse(v.getDescription()));
                    }
                    player.sendMessage(messageBuilder.toString());
                }
            }
            return Collections.emptyList();
        }
    }
}
