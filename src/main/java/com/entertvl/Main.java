package com.entertvl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

    public static Plugin plugin;
    public static Configuration config;
    public static GCEServerController controller;
    private static String configName = "config.yml";

    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        Main.plugin = this;
        Main.config = loadConf(configName);

        getProxy().getPluginManager().registerListener(this, new Events());

        Configuration servers = Main.config.getSection("servers");
        Map<String, GCEServer> gceServers = new HashMap<String, GCEServer>();

        for (Map.Entry<String, ServerInfo> entry : Main.plugin.getProxy().getServers().entrySet()) {
            String name = entry.getKey();

            if (servers.contains(name)) {
                System.out.print("New server entry: " + name);
                Configuration server = servers.getSection(name);
                gceServers.put(name, new GCEServer(server));

            }

        }

        Main.controller = new GCEServerController(gceServers);

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

        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(Main.plugin.getDataFolder(), Main.configName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Configuration();
    }
}