package sockets;

import java.io.Serializable;
import java.util.Date;

public class SendingPackage implements Serializable{
  private String nick, ip, message;
  private Date date;

  public String getNick() {
    return nick;
  }

  public String getIp() {
    return ip;
  }

  public String getMessage() {
    return message;
  }

  public Date getDate() {
    return date;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public void setIp (String ip) {
    this.ip = ip;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}