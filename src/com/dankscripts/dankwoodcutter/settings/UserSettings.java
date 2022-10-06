package com.dankscripts.dankwoodcutter.settings;

import com.dankscripts.api.utils.Location;
import com.dankscripts.dankwoodcutter.data.TreeType;

public class UserSettings {

    private TreeType treeType;
    private Location cutLocation;

    public void setTreeType(TreeType treeType) {
        this.treeType = treeType;
    }

    public void setCutLocation(Location cutLocation) {
        this.cutLocation = cutLocation;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public Location getCutLocation() {
        return cutLocation;
    }

}
