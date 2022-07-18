package com.github.hf02.scrollForWorldEdit.config;

import com.github.hf02.scrollForWorldEdit.client.StatusTextType;
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

	public boolean useKeyToggles = false;
	public boolean modeKeyToggles = false;

	public StatusTextType statusTextType = StatusTextType.TwoLinedWheel;

	@ConfigEntry.ColorPicker
	public int wheelSelectedColor = 0xffff00;

	@ConfigEntry.ColorPicker
	public int wheelUnselectedColor = 0xaaaaaa;

	@ConfigEntry.ColorPicker(allowAlpha = true)
	public int useColor = 0x77ffffff;

	@ConfigEntry.ColorPicker(allowAlpha = true)
	public int modeColor = 0xffffff00;

	@Override
	public void validatePostLoad() {
		if (scrollThresholdY <= 0) scrollThresholdY = 1;
		if (scrollThresholdX <= 0) scrollThresholdX = 1;
	}
}
