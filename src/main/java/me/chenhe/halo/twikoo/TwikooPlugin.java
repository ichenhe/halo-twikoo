package me.chenhe.halo.twikoo;

import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

@Component
public class TwikooPlugin extends BasePlugin {

    public TwikooPlugin(PluginContext pluginContext) {
        super(pluginContext);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
