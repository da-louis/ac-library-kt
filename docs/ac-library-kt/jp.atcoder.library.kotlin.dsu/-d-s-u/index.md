//[ac-library-kt](../../index.md)/[jp.atcoder.library.kotlin.dsu](../index.md)/[DSU](index.md)



# DSU  
 [jvm] Disjoint set union.convert from [AtCoderLibraryForJava - DSU](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/DSU/DSU.java)  
  
class [DSU](index.md)(**n**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [jvm] fun [<init>](-init-.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   <br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [jvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [groups](groups.md)| [jvm]  <br>Brief description  <br>Group by leader.  <br>Content  <br>fun [groups](groups.md)(): [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)<[ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)<[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)>>  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [jvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [leader](leader.md)| [jvm]  <br>Brief description  <br>Get its leader node.  <br>Content  <br>fun [leader](leader.md)(a: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [merge](merge.md)| [jvm]  <br>Brief description  <br>Merge nodes.  <br>Content  <br>fun [merge](merge.md)(a: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), b: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [same](same.md)| [jvm]  <br>Brief description  <br>True if two nodes are connected.  <br>Content  <br>fun [same](same.md)(a: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), b: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [size](size.md)| [jvm]  <br>Brief description  <br>A group's size.  <br>Content  <br>fun [size](size.md)(a: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [jvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>

