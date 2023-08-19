package org.diableAvionics.hullmods;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import static org.diableAvionics.util.StringManager.txt;

public class FireSelectorAuto extends BaseHullMod {
    @Override
    public int getDisplaySortOrder() {
        return 2000;
    }

    @Override
    public int getDisplayCategoryIndex() {
        return 3;
    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return txt("hm_selector_0");
        if (index == 1) return txt("hm_selector");        
        return null;
    }
}