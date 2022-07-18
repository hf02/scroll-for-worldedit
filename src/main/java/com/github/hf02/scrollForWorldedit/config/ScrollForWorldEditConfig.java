package com.github.hf02.scrollForWorldEdit.config;

import com.github.hf02.scrollForWorldEdit.client.TextDirectionType;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "scroll-for-worldedit")
public class ScrollForWorldEditConfig implements ConfigData {

	public float scrollThresholdY = 1;

	public float scrollThresholdX = 1;

	@ConfigEntry.Gui.PrefixText
	public TextDirectionType primaryDirection = TextDirectionType.Normal;

	public TextDirectionType primaryDirectionModifier =
		TextDirectionType.Vertical;

	public TextDirectionType secondaryDirection = TextDirectionType.Sideways;

	public TextDirectionType secondaryDirectionModifier =
		TextDirectionType.Vertical;

	@ConfigEntry.Gui.Tooltip
	public boolean mustHoldUseKeyForModeKey = false;

	@Override
	public void validatePostLoad() {
		if (scrollThresholdY <= 0) scrollThresholdY = 1;
		if (scrollThresholdX <= 0) scrollThresholdX = 1;
	}
}
