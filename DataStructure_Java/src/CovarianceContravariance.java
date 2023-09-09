import java.util.List;
import java.util.ArrayList;
public class CovarianceContravariance {

    // 1. Demonstration of Invariance
    public void readMaterialList(List<Material> materialList){for(Material m : materialList){System.out.println(m);}}

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

    public void readSupList(List<? super StandardGlass> supList){

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
         *  - Under the subtype polymorphism rule in Java, only most 'childish' class can be accepted
         *      in every possiblly instantiated type of given condition - ? super StandardGlass -.
         *  - Conclusion: In lowerbound wildcard - ? super Klass -, Adding is only allowed for the type of Klass or its subtypes.
         */
        // supList.add(new Material());  - Wrong!
        supList.add(new StandardGlass()); // Correct!
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