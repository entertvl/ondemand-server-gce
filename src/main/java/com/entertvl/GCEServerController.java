package com.entertvl;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.entertvl.type.RunningState;
import com.google.cloud.compute.v1.Operation;

public class GCEServerController {

  private Map<String, GCEServer> _servers;

  public GCEServerController(Map<String, GCEServer> servers) {
    this._servers = servers;
  }

  public void startServerByName(String name) {
    try {
      Operation res = this._servers.get(name).start().get();
      System.out.print("Operation: " + res.getId());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  public RunningState getRunningStateByName(String name) {
    return this._servers.get(name).getRunningState();
  }

}
