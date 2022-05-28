package DatabaseRelated;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenericHashMap <K,V>{
    private K key;
    private V value;


    private final HashMap<K,V> hashMap;

    public GenericHashMap() {
        hashMap = new HashMap<>();
    }


    public void addElement(K key, V value){
        getHashMap().put(key,value);
    }

    public boolean keyExists(K key){
        return getHashMap().containsKey(key);
    }

    public boolean deleteElement(K key){
        for (Map.Entry<K,V> o : getHashMap().entrySet()){
            getHashMap().remove(key);
            return true;
        }
        return false;
    }

    public int hashmapSize(){
        return getHashMap().size();
    }

    public void hashmapClear(){
        getHashMap().clear();
    }

    protected HashMap<K, V> getHashMap() {
        return hashMap;
    }

    public V getElementByKey(K key){
        for (Map.Entry<K,V> o : getHashMap().entrySet()){
            if (getHashMap().containsKey(key)){
                return getHashMap().get(key);
            }
        }
        return null;
    }

    public String allContentsMap(){
        StringBuilder aux = new StringBuilder();
        for (Map.Entry<K,V> o : getHashMap().entrySet()){
            aux.append(o.getKey().toString());
            aux.append(" || ");
            aux.append(o.getValue().toString());
            aux.append("\n");
        }
        return aux.toString();
    }










}
