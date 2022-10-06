package com.dankscripts.dankfletcher.nodes;

import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.util.time.Time;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankfletcher.AutoFletcherPro;
import com.dankscripts.dankfletcher.settings.UserSettings;

public class CutNode extends Node {

    private AutoFletcherPro script;
    private UserSettings userSettings;

    public CutNode(AutoFletcherPro script, UserSettings userSettings) {
        this.script = script;
        this.userSettings = userSettings;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().inventory().contains(userSettings.getBowType().getLogName()) && script.getAPIContext().inventory().contains(AutoFletcherPro.KNIFE_STRING) && !script.getAPIContext().localPlayer().isAnimating();
    }

    @Override
    public void execute() {
        if(script.getAPIContext().bank().isOpen() && script.getAPIContext().bank().close()) {
            Time.sleep(5000, () -> !script.getAPIContext().bank().isOpen());
        } else {
            if(script.getAPIContext().inventory().isItemSelected()) {
                script.getAPIContext().inventory().deselectItem();
            }
            WidgetChild widgetChild = script.getAPIContext().widgets().get(userSettings.getBowType().getParentInterfaceId(), userSettings.getBowType().getChildInterfaceId());
            if(!widgetChild.isValid() || !widgetChild.isVisible()) {
                if(script.getAPIContext().inventory().interactItem("Use", AutoFletcherPro.KNIFE_STRING)) {
                    if (Time.sleep(5000, () -> script.getAPIContext().inventory().isItemSelected())) {
                        if(script.getAPIContext().inventory().interactItem("Use", userSettings.getBowType().getLogName())) {
                            Time.sleep(5000, () -> script.getAPIContext().widgets().get(userSettings.getBowType().getParentInterfaceId()).isVisible());
                        }
                    }
                }
            } else {
                if(widgetChild.isVisible() && widgetChild.interact("Make")) {
                    Time.sleep(5000, () -> script.getAPIContext().localPlayer().isAnimating());
                }
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.MINIMUM;
    }

    @Override
    public String getName() {
        return "Cutting Logs...";
    }
}
