package com.entertvl;

import com.entertvl.type.RunningState;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {

  @EventHandler
  public void onServerConnect(ServerConnectEvent e) {

    ProxiedPlayer player = e.getPlayer();
    String name = e.getTarget().getName();
    RunningState state = Main.controller.getRunningStateByName(name);
    TextComponent message = new TextComponent();
    String text = String.format("Server is %s.", state.toString().toLowerCase());

    System.out.print("Target Server: " + name + " (" + e.getTarget().getSocketAddress() + ")");

    if (state == RunningState.SUSPENDED || state == RunningState.TERMINATED) {
      Main.controller.startServerByName(name);
      message.setText(String.format("%s Please wait and reconnect.", text));
      player.disconnect(message);
      e.setCancelled(true);
    } else if (state != RunningState.RUNNING) {
      message.setText(text);
      e.setCancelled(true);
    }

  }

  @EventHandler
  public void onServerConnected(ServerConnectedEvent e) {
  }

  @EventHandler
  public void onServerDisconnect(ServerDisconnectEvent e) {
  }

}
