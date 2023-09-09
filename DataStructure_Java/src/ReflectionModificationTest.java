import java.lang.reflect.Field;

public class ReflectionModificationTest {

    public static void main(String[] args) {
        Person person = new Person("hoyeon", 29);

        // Printing the original details of the person
        System.out.println("Before Reflection: " + person.name);

        /* Modification Test using Reflection
            - You(future me) might wonder why this complicated reflection-stuff for modifying private variable,
              thinking that just using 'person.name = "ModifiedName"'. It might work, only if it happens in same program(module / class).
            - If you want to perform modification of 'other program' in runtime, You should use Reflection like this.
         */

        try {
            // Accessing private field 'name' using reflection
            // Without try{} catch{} handling, this will not be able to be compiled. (NoSuchField Exception.)
            Field nameField = Person.class.getDeclaredField("name");
            nameField.setAccessible(true);  // Making the private field accessible

            // Modifying the value of 'name' field to 'mingyu'
            nameField.set(person, "mingyu");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Printing the fields and corresponding value of the 'person' after using reflection.
        System.out.println("After Reflection: " + person.name);
    }

    static class Person {
        private String name;
        private int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }
}
