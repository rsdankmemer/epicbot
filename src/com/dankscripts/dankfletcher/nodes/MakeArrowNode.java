package com.dankscripts.dankfletcher.nodes;

import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankfletcher.AutoFletcherPro;
import com.dankscripts.dankfletcher.settings.UserSettings;

public class MakeArrowNode extends Node {

    private AutoFletcherPro script;
    private UserSettings userSettings;
    private static final int ARROW_PARENT_ID = 270;
    private static final int ARROW_CHILD_ID = 14;

    public MakeArrowNode(AutoFletcherPro script, UserSettings userSettings) {
        this.script = script;
        this.userSettings = userSettings;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().inventory().contains(userSettings.getArrowType().getArrowheadName()) && script.getAPIContext().inventory().contains(userSettings.getArrowType().getCombinationItemName());
    }

    @Override
    public void execute() {
        if(script.getAPIContext().bank().isOpen() && script.getAPIContext().bank().close()) {
            Time.sleep(5000, () -> !script.getAPIContext().bank().isOpen());
        }
        if(script.getAPIContext().inventory().isItemSelected()) {
            script.getAPIContext().inventory().deselectItem();
        }
        WidgetChild widgetChild = script.getAPIContext().widgets().get(ARROW_PARENT_ID, ARROW_CHILD_ID);
        if(!widgetChild.isValid() || !widgetChild.isVisible()) {
            if(script.getAPIContext().inventory().interactItem("Use", userSettings.getArrowType().getArrowheadName()) && Time.sleep(5000, () -> script.getAPIContext().inventory().isItemSelected())) {
                if(script.getAPIContext().inventory().interactItem("Use", userSettings.getArrowType().getCombinationItemName())) {
                    Time.sleep(5000, () -> script.getAPIContext().widgets().get(ARROW_PARENT_ID, ARROW_CHILD_ID).isVisible());
                }
            }
        } else {
            if(widgetChild.isVisible() && widgetChild.interact("Make sets:")) {
                final long arrowheadNum = script.getAPIContext().inventory().getCount(userSettings.getArrowType().getArrowheadName());
                Time.sleep(20000, () -> script.getAPIContext().inventory().getCount(userSettings.getArrowType().getArrowheadName()) <= arrowheadNum - 150);
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.MINIMUM;
    }

    @Override
    public String getName() {
        return "Making Arrows...";
    }


}
