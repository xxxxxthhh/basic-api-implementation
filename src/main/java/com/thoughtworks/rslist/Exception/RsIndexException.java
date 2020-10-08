package com.thoughtworks.rslist.Exception;

public class RsIndexException extends Exception{
  private String message;

  public RsIndexException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
