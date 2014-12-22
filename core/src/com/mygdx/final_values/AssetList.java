
package com.mygdx.final_values;

public class AssetList {

    public enum Assets {

        ATLAS_GAMESCREEN("gamescreen.pack"),
        ATLAS_ETC("etc.pack"),

        ASSET_BATTLE_ICON("battleIcon"),
        ASSET_FIGHTER("fighter"),
        ASSET_WHITEMAGE("whitemage"),
        ASSET_REDMAGE("redmage"),
        ASSET_VIKING("viking"),
        ASSET_WARRIOR("warrior"),
        ASSET_BG_FIELD("battleback"),
        ASSET_TARGET("target"),
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
