//[ac-library-kt](../../index.md)/[jp.atcoder.library.kotlin.minCostFlow](../index.md)/[MinCostFlow](index.md)



# MinCostFlow  
 [jvm] convert from [AtCoderLibraryForJava - MinCostFlow](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/MinCostFlow/MinCostFlow.java)  
  
class [MinCostFlow](index.md)(**n**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [jvm] fun [<init>](-init-.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   <br>


## Types  
  
|  Name|  Summary| 
|---|---|
| [Companion](-companion/index.md)| [jvm]  <br>Content  <br>object [Companion](-companion/index.md)  <br><br><br>
| [WeightedCapEdge](-weighted-cap-edge/index.md)| [jvm]  <br>Content  <br>inner class [WeightedCapEdge](-weighted-cap-edge/index.md)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [addEdge](add-edge.md)| [jvm]  <br>Content  <br>fun [addEdge](add-edge.md)(from: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), to: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), cap: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), cost: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [clearFlow](clear-flow.md)| [jvm]  <br>Content  <br>fun [clearFlow](clear-flow.md)()  <br><br><br>
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [jvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [getEdge](get-edge.md)| [jvm]  <br>Content  <br>fun [getEdge](get-edge.md)(i: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [MinCostFlow.WeightedCapEdge](-weighted-cap-edge/index.md)  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [jvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [minCostFlow](min-cost-flow.md)| [jvm]  <br>Content  <br>fun [minCostFlow](min-cost-flow.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), flowLimit: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [LongArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html)  <br><br><br>
| [minCostMaxFlow](min-cost-max-flow.md)| [jvm]  <br>Content  <br>fun [minCostMaxFlow](min-cost-max-flow.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LongArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html)  <br><br><br>
| [minCostSlope](min-cost-slope.md)| [jvm]  <br>Content  <br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)()  <br>  <br>fun [minCostSlope](min-cost-slope.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), flowLimit: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)<[LongArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html)>  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [jvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>

