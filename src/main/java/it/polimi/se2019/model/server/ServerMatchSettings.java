//package it.polimi.se2019.model.server;
//
//public class ServerMatchSettings {
//  private Integer mapType;
//  private Integer skulls;
//
//  ServerMatchSettings(String encoded){
//    String[] tmp = encoded.split("~");
//    skulls = Integer.parseInt(tmp[0]);
//    mapType = Integer.parseInt(tmp[1]);
//  }
//
//  ServerMatchSettings(Integer mapType, Integer skulls){
//    this.mapType = mapType;
//    this.skulls = skulls;
//  }
//
//  ServerMatchSettings(ServerMatchSettings source){
//    this.mapType = source.getMapType();
//    this.skulls = source.getSkulls();
//  }
//
//  public Integer getMapType() {
//    return mapType;
//  }
//
//  public Integer getSkulls() {
//    return skulls;
//  }
//
//  public boolean equals(ServerMatchSettings obj) {
//    return (
//            (this.skulls == obj.getSkulls()) &&
//                    (this.mapType.equals(obj.getMapType()))
//    );
//  }
//
//  @Override
//  public String toString() {
//    return skulls.toString() + "~"  + mapType.toString();
//  }
//}
