package com.dankscripts.dankwoodcutter.nodes;

import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.util.Random;
import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankwoodcutter.DankWoodcutter;
import com.dankscripts.dankwoodcutter.settings.UserSettings;

public class ChopNode extends Node {

    private DankWoodcutter script;
    private UserSettings userSettings;

    public ChopNode(DankWoodcutter script, UserSettings userSettings) {
        this.script = script;
        this.userSettings = userSettings;
    }

    @Override
    public boolean validate() {
        return !script.getAPIContext().inventory().isFull() && !script.getAPIContext().localPlayer().isAnimating();
    }

    @Override
    public void execute() {
        if(script.isJustDropped()) {
            script.setJustDropped(false);
        } else {
            Time.sleep(50, 10000);
        }
        if (userSettings.getCutLocation().getArea().contains(script.getAPIContext().localPlayer().getLocation())) {
            if (!script.getAPIContext().localPlayer().isAnimating()) {
                SceneObject tree = script.getAPIContext().objects().query().nameContains(userSettings.getTreeType().toString()).within(userSettings.getCutLocation().getArea()).actions("Chop", "Chop down").results().nearest();
                if (tree != null && userSettings.getCutLocation().getArea().contains(tree)) {
                    if(!tree.isVisible()) {
                        script.getAPIContext().camera().turnTo(tree);
                    }
                    if(tree.click()) {
                        script.getAPIContext().mouse().moveOffScreen();
                        Time.sleep(8000, 12000, () -> script.getAPIContext().localPlayer().isAnimating() || script.getAPIContext().dialogues().canContinue());
                    }
                }
            }
        } else {
            script.getAPIContext().walking().setRun(true);
            script.getAPIContext().webWalking().walkTo(userSettings.getCutLocation().getArea().getRandomTile(), () -> userSettings.getCutLocation().getArea().contains(script.getAPIContext().localPlayer().getLocation()));
        }
    }

    @Override
    public Priority priority() {
        return Priority.MINIMUM;
    }

    @Override
    public String getName() {
        return "Chopping...";
    }

}
