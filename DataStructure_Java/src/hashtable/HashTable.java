package hashtable;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * HashTable Implementation
 * <p></p>
 * - This implementation is designed in <i><strong>separate chaining</strong></i> manner.
 * <p></p>
 * - Unordered.
 * <p></p>
 * - Table size  = power of 2 : Motivated by the idea adopted from java.util.HashMap.
 * <p></p>
 * - Operations<pre>
 *     1) search(K key) : Search entry with given key.
 *     2) insert(Entry<K, V> entry) : Insert given entry into proper location.
 *     3) delete(K key) : Search the entry with given key and delete it.
 *     4) resize() : Resize the array(table) of hash table.</pre>
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTable<K, V> {


    /* --------------------------------- Entry<K, V> Implementation -------------------------------- */
    static class Entry<K, V> implements Map.Entry<K, V> {

        int hash; // Cached hash code. Computed from Object.hashCode().
        final K key;
        V value;
        Entry<K, V> next;

        // Entry<K, V> constructor /w key and value given.
        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /* -------- Map.Entry<K,V> Interface Implementation------------ */
        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public V setValue(V value) {
            if (value != null) {
                V temp = this.value;
                this.value = value;
                System.out.printf("Value Updated. From : %s / To : %s", temp, this.value);
                return temp;
            } else {
                this.value = value;
                System.out.printf("Value has been initialized. From : null / To : %s", this.value);
                return value;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append(key.toString());
            sb.append(" : ");
            sb.append(value.toString());
            sb.append("}");
            return sb.toString();
        }
    }

    /*---------------------------------HashTable Fields---------------------------------*/
    private Entry<K, V>[] table;

    private int currentCap;
    private final int MAX_CAPACITY = Integer.MAX_VALUE - 8; // Saving 8 for 'object header'
    private int entryCount; // The number of total entries in table.
    private double loadfactor; // An occupied buckets / Total buckets(array length)

    private int threshold;
    private final double DEFAULT_LOAD_FACTOR = 0.75f; // Default load factor
    private final int DEFAULT_CAPACITY = 16; // Default Table Capacity

    /*-----------------------------Constructors For Scenarios--------------------------------*/

    // 1. Default Constructor
    @SuppressWarnings("unchecked")
    public HashTable() {
        // For initializing array with generic item, it has to be either initialized Object[] or cast.
        table = (Entry<K, V>[]) new Entry[currentCap = DEFAULT_CAPACITY];
        loadfactor = DEFAULT_LOAD_FACTOR;
        threshold = (int) (currentCap * loadfactor);
    }

    // 2. Constructor /w capacity & load factor given. Initial capacity required to be greater than 2.
    @SuppressWarnings("unchecked")
    public HashTable(int capacity, double givenLoadFactor) {
        if(capacity < 2){throw new IllegalArgumentException("Please set initial capacity to be greater than at least 2.");}
        loadfactor = givenLoadFactor;
        currentCap = capacity;
        table = (Entry<K, V>[]) new Entry[currentCap];
        threshold = (int) (capacity * loadfactor);

    }

    /*-----------------------------public Methods--------------------------------*/
    public Entry<K, V> newEntry(int hash, K key, V value, Entry<K, V> next){return new Entry<>(hash, key, value, next);}

    /**
     * Search entry using given key and returns it. If there's no entry having the key, returns null;
     * @param key
     * @return Entry
     */
    public Entry<K, V> search(K key){
        int hashed = hash(key);
        int index = (currentCap - 1) & hashed;
        if(table[index] == null){return null;}
        else{
            Entry<K, V> temp = table[index];
            while(temp != null && temp.getKey() != key && !key.equals(temp.getKey())){
                temp = temp.next;
            }
            return temp;
        }
    }

    /**
     * It allows insert of entry iff neither of key or value are null. <br></br>
     * If both or either key or value is null, throws IllegalArgumentException(). <br></br>
     * It updates value if there's entry that has same key.<br></br>
     * It checks if rehash() need to be invoked after every insertion success. If it does, invoke rehash().
     * @param key
     * @param value
     * @return Entry
     */
    public Entry<K, V> insert(K key, V value){
        if(key != null && value != null){
            int hashed = hash(key);
            int index = (currentCap - 1) & (hashed);
            Entry<K, V> alreadyExists;

            if((alreadyExists = search(key)) == null){
                Entry<K, V> inserted;
                if(table[index] == null){
                    table[index] = (inserted = newEntry(hash(key), key, value, null));
                } else {
                    Entry<K, V> temp = table[index];
                    inserted = newEntry(hash(key), key, value, temp);
                    table[index] = inserted;
                }
                entryCount++;
                if(entryCount > threshold){rehash();}

                return inserted;
            } else {
                alreadyExists.setValue(value);
                return alreadyExists;
            }
        } else {
            throw new IllegalArgumentException("It seems like one or more arguments is null.");
        }
    }

    /**
     * Delete entry whose key is identical to given key.<br></br>
     * If there's no such entry, throws NoSuchElementException.
     *
     * @param key
     * @return Entry
     */
    public Entry<K, V> delete(K key){
        Entry<K, V> target = search(key);
        if(target == null){throw new NoSuchElementException("No Such Element.");}
        else{
            int index = (currentCap - 1) & hash(key);
            Entry<K, V> temp = table[index];
            if(temp == target){
                table[index] = temp.next;
            }
            else {
                while(temp.next != null && !temp.next.equals(target)){
                    temp = temp.next;
                }
                temp.next = target.next;
                target.next = null;
            }
            entryCount--;

            return target;
        }
    }


    /**
     * Main hash function. Takes key and returns hashcode.<br></br>
     * It performs bitwise XOR operation with primary hash code for given key computed by Object.hashCode() and bit-spread version of it.
     * @param key
     * @return int-type hashcode for given key.
     */
    public int hash(Object key){
        int hash = key.hashCode();
        int h = hash ^ (hash >>> 16);
        return h;
    }

    /**
     * <p> Resize the table capacity and rehash all its entries.
     * <p> Updated capacity of table will be the smallest power of 2 number and larger than the old capacity.
     * <p> And updates currentCapacity, threshold.
     * <p> Bitwise operation logic for getting new capacity is referenced from that of java.util.HashMap.tableForSize() .
     */
    @SuppressWarnings("unchecked")
    public void rehash(){
        int newCapacity = newSize(currentCap);
        Entry<K, V>[] newTab = (Entry<K, V>[]) new Entry[newCapacity];
        Entry<K, V> iter;

        // Rehashing / Copying all entries into newTab from oldTab;
        for(int i = 0; i < table.length; i++){
            iter = table[i];
            while(iter != null){
                int newIdx = (newCapacity - 1) & iter.hash;
                if(newTab[newIdx] == null){newTab[newIdx] = newEntry(iter.hash, iter.getKey(), iter.getValue(), null);}
                else {newTab[newIdx] = newEntry(iter.hash, iter.getKey(), iter.getValue(), newTab[newIdx]);}
                iter = iter.next;
            }
        }

        // Updating currentCap & threshold & table.
        table = newTab;
        currentCap = newCapacity;
        threshold = (int) (newCapacity * loadfactor);


    }

    /**
     * Calculate new capacity for rehash().
     * I separated this method from consideration of SRP(Single Responsibility Principle).
     * <p> It calculates the smallest power of 2 number that is greater than the old capacity.
     * <p> If the old capacity is smaller than 2, than new capacity is set to be 2.
     * @param currentCap
     * @return
     */
    private int newSize(int currentCap){
        int oldCap = currentCap;
        int newCap = 0;
        if(oldCap > 1){
            newCap = oldCap - 1; // For the case where oldCap is already power of 2.
            newCap |= newCap >>> 1;
            newCap |= newCap >>> 2;
            newCap |= newCap >>> 4;
            newCap |= newCap >>> 8;
            newCap |= newCap >>> 16; // Right-shifted count : 31 bit.

            // Line below can be replaced with Math.min(MAX_CAPACITY, newCap).
            // But I didn't since I thought this will be more illustrative for its intention.
            return (newCap > MAX_CAPACITY) ? MAX_CAPACITY : (newCap + 1) << 1;
        }
        else{
            return 2;
        }
    }

    /**
     *
     * @return Hashtable represented as String
     */
    public String toString(){
        if(entryCount == 0){
            String s = "Empty.";
            return s;
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < table.length; i++){
                sb.append("|-- ");
                sb.append(i);
                sb.append(" --|");
                Entry<K, V> iter = table[i];
                if(iter == null){
                    sb.append(" _null_ \n");
                } else {
                    sb.append("  ");
                    sb.append(iter.toString());
                    sb.append(" ---> ");
                    iter = iter.next;
                    while(iter != null){
                        sb.append(iter.toString());
                        sb.append(" -> ");
                        iter = iter.next;
                    }
                    sb.append("End Of List.\n");
                }
            }
            sb.append("EoF\n");
            return sb.toString();
        }
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        Entry<K, V>[] newTab = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        table = newTab;
    }

    /*----------------------------Validation Methods -------------------------- */
    public int printCap(){return currentCap;}
    public int printEntryCount(){return entryCount;}
    public int printThreshold(){return threshold;}


    /*---------------------------- Test Program -------------------------- */
    public static void main(String[] args){
        HashTable<Integer, String> ht = new HashTable<>();
        System.out.println(ht);

        // Validation Methods Test I : Capacity, Entry count, Threshold Check : PASS
        System.out.println(ht.printCap());
        System.out.println(ht.printEntryCount());
        System.out.println(ht.printThreshold());

        // insert() Test : PASS
        ht.insert(19970919, "Jane Doe");
        ht.insert(19600909, "John Doe");
        ht.insert(19950218, "Nathan Ake");
        ht.insert(19910628, "Kevin De Bruyne");
        ht.insert(20000721, "Erling Haaland");
        ht.insert(19930817, "Ederson");
        ht.insert(19940810, "Bernardo Silva");
        ht.insert(19950910, "Jack Grealish");
        ht.insert(19900528, "Kyle Walker");
        ht.insert(19950719, "Manuel Akanji");
        ht.insert(19960622, "Rodri");


        // toString() Test : PASS
        System.out.println(ht);

        // Validation Methods Test I : Capacity, Entry count, Threshold Check : PASS
        System.out.println(ht.printCap());
        System.out.println(ht.printEntryCount());
        System.out.println(ht.printThreshold());

        // search(key) Test : Fixed & Passed!
        // Cause of the problem : '!=' cannot handle proper comparison.
        // Solution : Add another comparison filter. I added '!key.equals(Entry.getKey())'
        System.out.println(ht.search(19970919));
        System.out.println(ht.search(19600909));

        // delete() Test I _If target is first entry of list : PASS
        ht.delete(19970919);
        ht.delete(19600909);
        System.out.println(ht);
        System.out.println(ht.printCap());
        System.out.println(ht.printEntryCount());
        System.out.println(ht.printThreshold());

        // delete() Test II _ If target is in the middle of the list : PASS
        ht.delete(19950719);
        System.out.println(ht);
        System.out.println(ht.printCap());
        System.out.println(ht.printEntryCount());
        System.out.println(ht.printThreshold());

        // rehash() Test : Pass!
        ht.insert(19950719, "Manuel Akanji");
        ht.insert(19970514, "Ruben Dias");
        ht.insert(20000528, "Phil Foden");
        ht.insert(20041121, "Rico Lewis");
        ht.insert(20030612, "Oscar Bobb"); // rehash() invocation point
        System.out.println(ht);
        System.out.println(ht.printCap());
        System.out.println(ht.printEntryCount());
        System.out.println(ht.printThreshold());


    }
}
