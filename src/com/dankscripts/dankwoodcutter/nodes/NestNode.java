package com.dankscripts.dankwoodcutter.nodes;


import com.epicbot.api.shared.entity.GroundItem;
import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankwoodcutter.DankWoodcutter;

public class NestNode extends Node {

    private DankWoodcutter script;
    private static final String BIRD_NEST = "Bird nest";

    public NestNode(DankWoodcutter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        GroundItem birdsNest = script.getAPIContext().groundItems().query().named(BIRD_NEST).results().first();
        return !script.getAPIContext().inventory().isFull() && birdsNest != null;
    }

    @Override
    public void execute() {
        GroundItem birdsNest = script.getAPIContext().groundItems().query().named(BIRD_NEST).results().first();
        if(birdsNest != null && birdsNest.interact("Take")) {
            Time.sleep(3000, 4000, () -> script.getAPIContext().groundItems().query().named(BIRD_NEST).results().first() == null);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public String getName() {
        return "Taking birds nest...";
    }
}
