/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.utils;

public class UnsupportedGeometryException extends Exception {

  public String message;

  public UnsupportedGeometryException(String message) {
    this.message = message;
  }
}
