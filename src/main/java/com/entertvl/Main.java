package com.entertvl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

    public static Plugin plugin;
    public static Configuration config;
    private static String configName = "config.yml";

    @Override
    public void onEnable() {
        Main.plugin = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        Main.config = loadConf(configName);

        getProxy().getPluginManager().registerListener(this, new Events());

    }

    public static Configuration loadConf(String fileName) {

        File conf = new File(Main.plugin.getDataFolder(), Main.configName);

        if (!conf.exists()) {
            try (
                    InputStream in = Main.plugin.getResourceAsStream(configName);
                    OutputStream ou = new FileOutputStream(conf.toPath().toFile())) {
                in.transferTo(ou);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(conf.toPath());

        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(Main.plugin.getDataFolder(), Main.configName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Configuration();
    }
}