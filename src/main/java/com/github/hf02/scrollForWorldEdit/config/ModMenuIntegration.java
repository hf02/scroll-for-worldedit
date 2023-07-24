package com.github.hf02.scrollForWorldEdit.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent ->
			AutoConfig
				.getConfigScreen(ScrollForWorldEditConfig.class, parent)
				.get();
	}
}
