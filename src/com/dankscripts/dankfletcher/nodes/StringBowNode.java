package com.dankscripts.dankfletcher.nodes;

import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankfletcher.AutoFletcherPro;
import com.dankscripts.dankfletcher.settings.UserSettings;

public class StringBowNode extends Node {

    private AutoFletcherPro script;
    private UserSettings userSettings;
    private static final int STRING_PARENT_ID = 270;
    private static final int STRING_CHILD_ID = 14;

    public StringBowNode(AutoFletcherPro script, UserSettings userSettings) {
        this.script = script;
        this.userSettings = userSettings;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().inventory().contains(AutoFletcherPro.BOWSTRING_STRING) && script.getAPIContext().inventory().contains(userSettings.getBowType().getUnstrungName());
    }

    @Override
    public void execute() {
        if(script.getAPIContext().bank().isOpen() && script.getAPIContext().bank().close()) {
            Time.sleep(5000, () -> !script.getAPIContext().bank().isOpen());
        }
        if(script.getAPIContext().inventory().isItemSelected()) {
            script.getAPIContext().inventory().deselectItem();
        }
        WidgetChild widgetChild = script.getAPIContext().widgets().get(STRING_PARENT_ID, STRING_CHILD_ID);
        if(!widgetChild.isValid() || !widgetChild.isVisible()) {
            if(script.getAPIContext().inventory().interactItem("Use", AutoFletcherPro.BOWSTRING_STRING) && Time.sleep(4000, () -> script.getAPIContext().inventory().isItemSelected())) {
                if(script.getAPIContext().inventory().interactItem("Use", userSettings.getBowType().getUnstrungName())) {
                    Time.sleep(5000, () -> script.getAPIContext().widgets().get(STRING_PARENT_ID, STRING_CHILD_ID).isVisible());
                }
            }
        } else {
            if(widgetChild.isVisible() && widgetChild.interact("String")) {
                Time.sleep(25000, () -> !script.getAPIContext().inventory().contains(userSettings.getBowType().getUnstrungName()) || !script.getAPIContext().inventory().contains(AutoFletcherPro.BOWSTRING_STRING));
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.MINIMUM;
    }

    @Override
    public String getName() {
        return "Stringing Bows...";
    }
}
