package com.entertvl;

import java.io.IOException;

import com.entertvl.type.RunningState;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.compute.v1.GetInstanceRequest;
import com.google.cloud.compute.v1.Instance;
import com.google.cloud.compute.v1.InstancesClient;
import com.google.cloud.compute.v1.Operation;
import com.google.cloud.compute.v1.StartInstanceRequest;

import net.md_5.bungee.config.Configuration;

public class GCEServer {

  private String _projectId;
  private String _zone;
  private String _instance;
  private InstancesClient _client;

  public GCEServer(Configuration config) {
    this._projectId = config.getString("project_id");
    this._zone = config.getString("zone");
    this._instance = config.getString("instance");
    try {
      this._client = InstancesClient.create();
    } catch (IOException | ApiException e) {
      e.printStackTrace();
    }

  }

  public RunningState getRunningState() {
    Instance instance = this.retriveInstance();
    return RunningState.valueOf(instance.getStatus());
  }

  public OperationFuture<Operation, Operation> start() {
    try {
      StartInstanceRequest req = StartInstanceRequest.newBuilder()
          .setInstance(this._instance)
          .setProject(this._projectId)
          .setZone(this._zone)
          .build();
      return this._client.startAsync(req);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private Instance retriveInstance() {
    GetInstanceRequest req = GetInstanceRequest.newBuilder()
        .setProject(this._projectId)
        .setZone(this._zone)
        .setInstance(this._instance)
        .build();
    try {
      return this._client.get(req);

    } catch (ApiException e) {
      e.printStackTrace();
    }

    return null;
  }

}
