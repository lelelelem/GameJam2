
package com.mygdx.final_values;

public class AssetList {

    public enum Assets {

        ATLAS_GAMESCREEN("prebattle.pack"),
        ATLAS_ETC("etc.pack"),

        ASSET_BATTLE_ICON("battle_button"),
        ASSET_FIGHTER("fighter"),
        ASSET_WHITEMAGE("whitemage"),
        ASSET_REDMAGE("redmage"),
        ASSET_VIKING("viking"),
        ASSET_WARRIOR("warrior"),
        ASSET_BG_FIELD("back"),
        ASSET_TARGET("square"),
        ASSET_THUMB_BG("thumb-bg"),
        ASSET_THUMB_FIGHTER("thumb-fighter");
        
        private String filepath;

        private Assets(String filepath) {
            this.filepath = filepath;
        }

        public String getPath() {
            return this.filepath;
        }

    }

}
