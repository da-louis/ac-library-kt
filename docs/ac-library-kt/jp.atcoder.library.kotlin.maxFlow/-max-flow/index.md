//[ac-library-kt](../../index.md)/[jp.atcoder.library.kotlin.maxFlow](../index.md)/[MaxFlow](index.md)



# MaxFlow  
 [jvm] convert from [AtCoderLibraryForJava - MaxFlow](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/MaxFlow/MaxFlow.java)  
  
class [MaxFlow](index.md)(**n**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [jvm] fun [<init>](-init-.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   <br>


## Types  
  
|  Name|  Summary| 
|---|---|
| [CapEdge](-cap-edge/index.md)| [jvm]  <br>Content  <br>inner class [CapEdge](-cap-edge/index.md)  <br><br><br>
| [Companion](-companion/index.md)| [jvm]  <br>Content  <br>object [Companion](-companion/index.md)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [addEdge](add-edge.md)| [jvm]  <br>Content  <br>fun [addEdge](add-edge.md)(from: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), to: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), cap: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [changeEdge](change-edge.md)| [jvm]  <br>Content  <br>fun [changeEdge](change-edge.md)(i: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), newCap: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), newFlow: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))  <br><br><br>
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [jvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [flow](flow.md)| [jvm]  <br>Content  <br>fun [flow](flow.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), flowLimit: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)  <br><br><br>
| [fordFulkersonFlow](ford-fulkerson-flow.md)| [jvm]  <br>Content  <br>fun [fordFulkersonFlow](ford-fulkerson-flow.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), flowLimit: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)  <br><br><br>
| [fordFulkersonMaxFlow](ford-fulkerson-max-flow.md)| [jvm]  <br>Content  <br>fun [fordFulkersonMaxFlow](ford-fulkerson-max-flow.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)  <br><br><br>
| [getEdge](get-edge.md)| [jvm]  <br>Content  <br>fun [getEdge](get-edge.md)(i: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [MaxFlow.CapEdge](-cap-edge/index.md)  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [jvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [maxFlow](max-flow.md)| [jvm]  <br>Content  <br>fun [maxFlow](max-flow.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), t: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)  <br><br><br>
| [minCut](min-cut.md)| [jvm]  <br>Content  <br>fun [minCut](min-cut.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [BooleanArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/index.html)  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [jvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>

