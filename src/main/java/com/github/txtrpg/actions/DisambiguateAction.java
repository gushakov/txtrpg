package com.github.txtrpg.actions;

import com.github.txtrpg.core.Actor;
import com.github.txtrpg.core.Entity;
import com.github.txtrpg.core.Player;
import com.github.txtrpg.message.Color;
import com.github.txtrpg.message.ColumnLayout;
import com.github.txtrpg.message.MessageBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author gushakov
 */
public class DisambiguateAction extends Action {

    private List<Entity> candidates;

    public DisambiguateAction(Actor initiator, List<Entity> candidates) {
        super(ActionName.disambiguate, initiator);
        this.candidates = candidates;
    }

    public List<Entity> getCandidates() {
        return candidates;
    }

    @Override
    protected void processForPlayer(Collection<Action> actions, Player player) {
        final MessageBuilder builder = new MessageBuilder()
                .append("There are several of those here:")
                .withColumns(ColumnLayout.list);

        for (int i = 0; i < candidates.size(); i++) {
             builder.append(i + 1, Color.cyan).append(":")
                .tab()
                .append(candidates.get(i).getName())
                .tab()
             ;
        }
        builder.end();

        player.sendMessage(builder.toString(), true, true);
    }

}
