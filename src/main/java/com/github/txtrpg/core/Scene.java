package com.github.txtrpg.core;

import com.github.txtrpg.message.Color;
import com.github.txtrpg.message.MessageBuilder;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.*;

/**
 * A location in the world.
 *
 * @author gushakov
 */
public class Scene extends Entity implements Observable {

    @RelatedToVia(type = "EXIT_TO", direction = Direction.OUTGOING, elementClass = Exit.class)
    @Fetch
    private Set<Exit> exits;

    private Ground ground;

    private Room<Actor> room;

    public Scene() {
        this.ground = new Ground();
        this.room = new Room<>();
    }

    public Scene(String name, String description) {
        super(name, description);
        this.exits = new HashSet<>();
        this.ground = new Ground();
        this.room = new Room<>();
    }

    public Set<Exit> getExits() {
        return exits;
    }

    public void setExits(Set<Exit> exits) {
        this.exits = exits;
    }

    public Container<Item> getGround() {
        return ground;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public Room<Actor> getRoom() {
        return room;
    }

    public void setRoom(Room<Actor> room) {
        this.room = room;
    }

    public void addExit(Dir dir, Scene to) {
        exits.add(new Exit(dir, this, to));
    }

    public Optional<Exit> getExit(Dir dir) {
        return exits.stream().filter(e -> e.getDir() == dir).findFirst();
    }

    public Dir getRandomExitDirection(){
        List<Exit> exits = new ArrayList<>(getExits());
        Collections.shuffle(exits);
        return exits.stream().findAny().get().getDir();
    }


    public Optional<Scene> getExitTo(Dir dir) {
        Optional<Exit> exit = exits.stream().filter(e -> e.getDir() == dir).findFirst();
        return exit.isPresent() ? Optional.of(exit.get().getTo()) : Optional.empty();
    }

    @Override
    public String getDescription() {
        final MessageBuilder messageBuilder = new MessageBuilder().parse(super.getDescription());
        ground.stream().forEach(it -> {
            messageBuilder.br()
                    .parse(it.getDescription());
        });
        exits.stream().forEach(e -> {
            messageBuilder.br()
                    .append(e.getDir().getDirection(), Color.blue)
                    .append(": to ")
                    .append(e.getTo().getName());
        });
        return messageBuilder.toString();
    }

    @Override
    public Collection<Visible> showTo(Actor actor) {
        final List<Visible> visibles = new ArrayList<>();
        room.stream().filter(a -> !a.getName().equals(actor.getName())).forEach(visibles::add);
        return visibles;
    }

}
