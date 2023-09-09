import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionExaminationTest {

    /**
     * I wrote code to demonstrate myself how examination of own structure and behavior at 'runtime'  can be done with Reflection API of Java.
     * I tightly focused on 'examination' side of reflection in this code.
     * I tried following things
     * <p>
     *  <p> 1. Obtain Class object for further reflection.
     *  <p> 2. Get Method[] array that has declared methods inside given class declaration as its elements.
     *  <p> 3. Create instance of given class.
     *  <p> 4. Invoke method.
     *  <p> 5. Invoke method with parameters.
     */

    public static void main(String[] args){

        /*
            1. Obtain Class object for further reflection.

                - I learned 3-ways to obtain Class object.
                    1) Using 'Class literal' - Object.class
                    2) Using getClass()
                    3) Using Class.forName()
         */

        // 1) Using 'Class literal'
        Class<?> clsLiteral = String.class;
        System.out.println(clsLiteral); // System.out.println(obj) or print(obj) is designed to call toString() method of obj class.

        // 2) Using getClass().
        Integer i = 3; // getClass() method cannot be used on primitive type. You have to use 'wrapper class' to do so.
        Class<?> clsGetKlass = i.getClass();
        System.out.println(clsGetKlass);

        // 3) Using Class.forName()
            // Class.forName()
            // - Takes fully-qualified name(String type) of class as its argument.
            // - Invokes dynamic class loading.
            // - REQUIRES exception handling.
        try {
            Class<?> clsForName = Class.forName("java.util.ArrayDeque");
            System.out.println(clsForName);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

//////////////////////////////////////////////////////////////////////////////////

        /*
           2. Get Method[] array.
         */
        Method[] clsLiteralGetMethods = clsLiteral.getMethods();
        Method[] clsLiteralGetDeclaredMethods = clsLiteral.getDeclaredMethods();

        System.out.println(clsLiteralGetMethods); // Prints object. Internally calls toString
        System.out.println(clsLiteralGetDeclaredMethods);

        System.out.println(Arrays.toString(clsLiteralGetMethods)); // Prints shape of array by wrapping object with toString() method.
        System.out.println(Arrays.toString(clsLiteralGetDeclaredMethods));

        for(Method cGM : clsLiteralGetMethods){
            System.out.println(cGM); // Prints fully-qualified name of each method with its access modifier and its return type.
        }

        for(Method cGDM : clsLiteralGetDeclaredMethods){
            System.out.println(cGDM.getName()); // Prints plain-text style name of each method.
        }
////////////////////////////////////////////////////////////////////////////////////

        /*
           3. Create instance of given Class.
         */

        try {
            String str = (String) clsLiteral.getConstructor(String.class).newInstance("This is new String instance!");
            System.out.println("Succeed! : " + str);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
////////////////////////////////////////////////////////////////////////////////////

        /*
           4. Invoke method.
         */

        try {
            String str2;
            Method lengthMethod = clsLiteral.getMethod("length");
            int length = (int) lengthMethod.invoke(str2 = (String) clsLiteral.getConstructor(String.class).newInstance("Second object!"));
            System.out.println("str2 length = " + length); // Prints 14 : A length of str2 "Second object!"
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

////////////////////////////////////////////////////////////////////////////////////

        /*
           5. Invoke method with parameters.
         */

        try {
            // Initialize class literal and assign reference to variable 'adClass'.
            Class<ArrayDeque> adClass = ArrayDeque.class;

            // Instantiate new ArrayDeque object that is generic using ClassObject.getConstructor(Class<?> param).newInstance();
            ArrayDeque<?> ad = adClass.getConstructor(int.class).newInstance(5);

            // Instantiate Method object of addFirst() method & Invoke it.
            Method AF = adClass.getMethod("addFirst", Object.class);
            AF.invoke(ad, 1);
            System.out.println(ad.toString());


            System.out.println(ad.toString());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            e.printStackTrace();
        }



    }
}
