package com.dankscripts.dankwoodcutter.nodes;


import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankwoodcutter.DankWoodcutter;
import com.dankscripts.dankwoodcutter.data.AxeType;

public class DropNode extends Node {

    private DankWoodcutter script;

    public DropNode(DankWoodcutter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().inventory().isFull();
    }

    @Override
    public void execute() {
        Time.sleep(50, 10000);
        if(script.getAPIContext().inventory().dropAllExcept(AxeType.names())) {
            script.setJustDropped(true);
            Time.sleep(50);
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public String getName() {
        return "Dropping...";
    }

}
