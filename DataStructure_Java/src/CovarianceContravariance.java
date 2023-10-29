import java.sql.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
public class CovarianceContravariance {

    // 1. Demonstration of Invariance
    public void readMaterialList(List<Material> materialList){
        for(Material m : materialList){System.out.println(m);}
    }

    // 2. Demonstration of Covariance
    public void readSubMaterialList(List<? extends Material> subMaterialList){

        /* 1) Extraction / Retrieve
         * Correct!!
         * - By setting condition using '? extends GrandParent', List<Parent> or List<Child> can be passed as argument.
         *  - Thus, we can now understand how covariance is attained using 'Upper-bounded Wildcard' - 'extends' keyword -.
         * - Iterator(Extractor) must be Parent type of all types that satisfy given condition - ? extends Material -.
         */
        for(Material m : subMaterialList){System.out.println(m);}
        Material mA = subMaterialList.get(0);
        /* Belows are ALL Wrong!!
        *  - Codes below cause ClassCastException.
        *    - How would you cover all the extracted elements with 'child type' object when their types are unknown?
        * for(Glass g : subMaterialList){System.out.println(g);}
        * for(Wood w : subMaterialList){System.out.println(w);}
        * for(Metal me : subMaterialList){System.out.println(me);}
        * for(StandardGlass sg : subMaterialList){System.out.println(sg);}
        * for(CustomGlass cg : subMaterialList){System.out.println(cg);}
        */

        /* 2) Adding Element
         * Adding is strictly PROHIBITED.
         *  - Suppose we have List<Wood> object and invoke readSubMaterialList() with it.
         *      ```java
         *      List<Wood> wdLst = new ArrayList<>();
         *      readSubMaterialList(wdLst);
         *      .
         *      .
         *      .
         *      public void readSubMaterialList(List<? extends Material> lst){
         *          subMaterialList.add(new Material()); // Case I: Not Possible
         *          subMaterialList.add(new Metal()); // Case II: Not Possible
         *      }
         *      ```
         *  - This is the point: Java compiler have no clue of what type of List would be passed as argument.
         *      - Case I: Material is supertype of Wood. Violation of subtype-polymorphism rule.
         *      - Case II: If Metal and Wood are not compatible - like String and Integer - this also throws exception.
         *  - Conclusion: As compiler has no idea of what type actually would be given,
         *                  Adding element is strictly forbidden in Upperbound-wildcard by compiler.
         *
         */
        //subMaterialList.add(new Material());
        //subMaterialList.add(new Wood());




    }

    // 3. Demonstration of Contravariance
    public void readSupList(List<? super Glass> supList){

        /* 1) Extraction / Retrieve
         * Correct !!
         * - By setting condition using '? super Child', List<Parent> or List<GrandParent> can be passed as argument.
         *  - Thus, we can now understand how contravariance is attained using 'Lower-bounded Wildcard' - 'super' keyword - .
         * - Iterator must be Object type. - The up-most class in class hierarchy.
         *
         */
        Object ob = supList.get(0);
        for(Object o : supList){System.out.println(o);}

        /* 2) Adding Element
         * As I stated above, this is easily understandable.
         *  - Under the subtype polymorphism rule in Java, only the most 'childish' class can be inserted.
         *  - Conclusion: In lower bound wildcard - ? super Klass -, Adding is only allowed for the type of Klass or its subtypes.
         */
        // supList.add(new Material());  - Wrong!
        supList.add(new StandardGlass()); // Correct!
    }

    public static void main(String[] args){

        CovarianceContravariance covTest = new CovarianceContravariance();

        List<Material> matList = new ArrayList<>();
        List<Wood> wdList = new ArrayList<>();
        List<Metal> mtList = new ArrayList<>();

        matList.add(new Wood());
        matList.add(new Metal());

        covTest.readMaterialList(matList); // Works fine.
        covTest.readSupList(matList);



    }

}
/*
 # Class Hierarchy Design

Belows are classes need to demonstrate covariance & contravariance.
Those classes each represent construction material.

    GrandParent : Material
    Parent : Wood, Metal, Glass
    Child : StandardGlass, CustomGlass - Both inherits Glass class
 */

class Material{
    int width;
    int height;
    int depth;
    double price;
}

class Wood extends Material{
    String source;
}

class Metal extends Material{
    String name;
}

class Glass extends Material{
    int thickness;
}

class StandardGlass extends Glass{}
class CustomGlass extends Glass{}