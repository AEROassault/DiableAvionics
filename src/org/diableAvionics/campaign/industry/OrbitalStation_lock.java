package org.diableAvionics.campaign.industry;

import com.fs.starfarer.api.impl.campaign.econ.impl.OrbitalStation;

public class OrbitalStation_lock extends OrbitalStation {
    
    @Override
    public boolean isAvailableToBuild() {
        return false;
    }

    @Override
    public String getUnavailableReason() {
        return "Unavailable technology";
    }

    @Override
    public boolean showWhenUnavailable() {
        return false;
    }
}