package it.polimi.se2019.MVC;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Model {
  private ConcurrentHashMap<String, ModelItem> data;
  private List<Consumer<Map<String, ModelItem>>> listeners;

  public Model(){
    this.data = new ConcurrentHashMap<>();
    this.listeners = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * __WARN__ A copy of the object may be saved by this function.
   *          Updating this copy will NOT propagate the changes.
   *          To propagate the change, get a new copy of the object using
   *          {@see #get}, update the copy, then feed the copy again to this
   *          function
   *
   * @param key The key of the object to update in the model
   * @param data The data to update in the model
   */
  public void update(String key, ModelItem data){
    String serializedData;

    this.data.put(key, data);

    serializedData = toString(this.data);
//    this.listeners.forEach(item -> item.accept(serializedData));
    this.listeners.forEach(item -> item.accept(this.data));
  }

  /**
   * __WARN__ A copy of the object may be returned by this function.
   *          Updating this copy will NOT propagate the changes.
   *          To propagate the change, update this copy, then feed it as param
   *          data to {@see #update}
   *
   * @param key The key of the object to retrive from the model
   *
   * @return the returned object (null if it doesn't exists)
   */
  public ModelItem get(String key){
    return this.data.get(key);
  }

  public void addUpdateListener(Consumer<Map<String, ModelItem>> cb){
    this.listeners.add(cb);
  }

  public void removeUpdateListener(Consumer<Map<String, ModelItem>> cb){
    this.listeners.remove(cb);
  }

  public interface ModelItem extends Serializable {}
}
