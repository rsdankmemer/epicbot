package com.dankscripts.dankenchanter.nodes;

import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankenchanter.DankEnchanter;

public class WorldHopNode extends Node {

    private DankEnchanter script;

    public WorldHopNode(DankEnchanter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().players().getAll().size() > 1;
    }

    @Override
    public void execute() {

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public String getName() {
        return "Hopping Worlds";
    }

}
