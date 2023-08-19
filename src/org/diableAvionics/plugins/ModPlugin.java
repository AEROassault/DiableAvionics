package org.diableAvionics.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import org.diableAvionics.ai.AntiMissileAI;
import org.diableAvionics.ai.BanishAI;
import org.diableAvionics.ai.ThrushAI;
import org.diableAvionics.ai.ScatterMissileAI;
import org.diableAvionics.ai.SrabAI;
import org.diableAvionics.ai.ThunderboltMissileAI;
import org.diableAvionics.ai.CicadaAI;
import org.diableAvionics.ai.DeepStrikeAI;
import org.diableAvionics.ai.ItanoMissileAI;
import org.diableAvionics.ai.PloverMissileAI;
import org.magiclib.util.MagicSettings;
import org.magiclib.util.MagicVariables;
import org.diableAvionics.world.DiableSystemGen;
import exerelin.campaign.SectorManager;
import java.util.ArrayList;
import java.util.List;
import org.dark.shaders.light.LightData;
import org.dark.shaders.util.ShaderLib;
import org.dark.shaders.util.TextureData;

public class ModPlugin extends BaseModPlugin {

    public static final String SCATTER_MISSILE_ID = "diableavionics_micromissile";    
    public static final String PD_MISSILE_ID = "diableavionics_magicmissile";
    public static final String THUNDERBOLT_ID = "diableavionics_thunderbolt";
    public static final String PLOVER_ID = "diableavionics_plover";
    public static final String BANISH_ID = "diableavionics_banishmirv";
    public static final String THRUSH_ID = "diableavionics_thrushmirv";
    public static final String SRAB_ID = "diableavionics_srab_shot";	
    public static final String CICADA_ID = "diableavionics_cicada_shot";
    public static final String VIRTUOUS_GRENADE_ID = "diableavionics_virtuousGrenade_shot";
    public static final String VIRTUOUS_MISSILE_ID = "diableavionics_virtuousmissile";    
    public static final String DEEPSTRIKE_ID = "diableavionics_deepStrikeM";
    
    public static List<String> DERECHO_RESIST = new ArrayList<>();
    public static List<String> DERECHO_IMMUNE = new ArrayList<>();
    public static List<String> WANZERS = new ArrayList<>();
    public static float GANTRY_TIME_MULT = 1, GANTRY_DEPLETION_PERCENT = 0, GANTRY_OP_MODIFIER=0;
    
    @Override
    public void onApplicationLoad() throws ClassNotFoundException {  
            
        
        try {  
                Global.getSettings().getScriptClassLoader().loadClass("org.dark.shaders.util.ShaderLib");  
                ShaderLib.init();  
                LightData.readLightDataCSV("data/config/modFiles/diableavionics_lights.csv"); 
                TextureData.readTextureDataCSV("data/config/modFiles/diableavionics_maps.csv"); 
            } catch (ClassNotFoundException ex) {
        }
        
        //modSettings loading:
        DERECHO_RESIST = MagicSettings.getList("diableavionics", "missile_resist_derecho");        
        DERECHO_IMMUNE = MagicSettings.getList("diableavionics", "missile_immune_derecho");                
        WANZERS = MagicSettings.getList("diableavionics", "wanzers");
        GANTRY_TIME_MULT = MagicSettings.getFloat("diableavionics", "gantry_refitMult");
        GANTRY_DEPLETION_PERCENT = MagicSettings.getFloat("diableavionics", "gantry_depletionPercent");
    }	
	
    @Override
    public void onNewGame() {
        boolean nexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");
        if (!nexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
            new DiableSystemGen().generate(Global.getSector());
        }
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        //add the special fleets
        if(!MagicVariables.getIBB()){
            //no IBB system? it would be a shame to miss on the Gulf
            DiableSystemGen.spawnGulf();
        }
        DiableSystemGen.spawnVirtuous();
        String SPECIAL_FLEETS = "$diableavionicsSpecial";
        Global.getSector().getMemoryWithoutUpdate().set(SPECIAL_FLEETS, true);
    }

    @Override
    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case SCATTER_MISSILE_ID:
                return new PluginPick<MissileAIPlugin>(new ScatterMissileAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case PD_MISSILE_ID:
                return new PluginPick<MissileAIPlugin>(new AntiMissileAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case THUNDERBOLT_ID:
                return new PluginPick<MissileAIPlugin>(new ThunderboltMissileAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case PLOVER_ID:
                return new PluginPick<MissileAIPlugin>(new PloverMissileAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case BANISH_ID:
                return new PluginPick<MissileAIPlugin>(new BanishAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case THRUSH_ID:
                return new PluginPick<MissileAIPlugin>(new ThrushAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case SRAB_ID:
                return new PluginPick<MissileAIPlugin>(new SrabAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case CICADA_ID:
            case VIRTUOUS_GRENADE_ID:
                return new PluginPick<MissileAIPlugin>(new CicadaAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case VIRTUOUS_MISSILE_ID:
                return new PluginPick<MissileAIPlugin>(new ItanoMissileAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case DEEPSTRIKE_ID:
                return new PluginPick<MissileAIPlugin>(new DeepStrikeAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            default:        
        }
        return null;
    }		
}