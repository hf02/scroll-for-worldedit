package com.github.hf02.scrollForWorldEdit.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "scroll_for_worldedit")
public class ScrollForWorldEditConfig implements ConfigData {

	public float scrollThresholdY = 1;

	public float scrollThresholdX = 1;

	@ConfigEntry.Gui.Tooltip
	public boolean verticalLook = true;

	@Override
	public void validatePostLoad() {
		if (scrollThresholdY <= 0) scrollThresholdY = 1;
		if (scrollThresholdX <= 0) scrollThresholdX = 1;
	}
}
