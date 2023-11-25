import java.io.*;
import java.util.ArrayDeque;

public class SerializationTest {
    public static void main(String[] args){
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addLast("1");
        ad.addLast("2");
        ad.addFirst("0");

        // Serialization
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("ad.ser"))){
            output.writeObject(ad);
            System.out.println("Successfully Serialized!"); // ad.ser has been created.
        } catch(IOException e) {
            e.printStackTrace();
        }

        // De-serialization
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("ad.ser"))){
            ArrayDeque<?> dad = (ArrayDeque<?>) input.readObject(); // Added type inference using wildcard generic.
            System.out.println("De-serialized: " + dad.toString());
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
