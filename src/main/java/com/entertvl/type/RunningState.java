package com.entertvl.type;

public enum RunningState {

  PROVISIONING(1),
  STAGING(2),
  RUNNING(3),
  STOPPING(4),
  SUSPENDING(5),
  SUSPENDED(6),
  REPAIRING(7),
  TERMINATED(8);

  private final int _number;

  RunningState(int number) {
    this._number = number;
  }

  public int getNumber() {
    return this._number;
  }

  public static RunningState getByNumber(int number) {
    for (RunningState value : RunningState.values()) {
      if (value.getNumber() == number)
        return value;
    }
    return null;
  }
}
