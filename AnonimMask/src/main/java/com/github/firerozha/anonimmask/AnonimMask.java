package com.github.firerozha.anonimmask;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnonimMask extends JavaPlugin {
    private static Chat chat = null;

    @Override
    public void onEnable() {
        // listeners
        Bukkit.getServer().getPluginManager().registerEvents(new AnoninMaskListener(this), this);

        //config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //api
        setupChat();

        //other
        getLogger().info("AnonimMask ||| Made by FireRoz\nIf you're facing issues DM me on Bukkit");

    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public static Chat getChat() {
        return chat;
    }


}
