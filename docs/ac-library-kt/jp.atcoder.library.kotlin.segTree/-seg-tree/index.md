//[ac-library-kt](../../index.md)/[jp.atcoder.library.kotlin.segTree](../index.md)/[SegTree](index.md)



# SegTree  
 [jvm] Segment tree(0-indexed)convert from [AtCoderLibraryForJava - SegTree](https://github.com/NASU41/AtCoderLibraryForJava/blob/24160d880a5fc6d1caf9b95baa875e47fb568ef3/SegTree/SegTree.java)  
  
class [SegTree](index.md)<[S](index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> (**n**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html),**op**: [BinaryOperator](https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html)<[S](index.md)>,**e**: [S](index.md))   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [jvm] fun <[S](index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> [<init>](-init-.md)(dat: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)<[S](index.md)>, op: [BinaryOperator](https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html)<[S](index.md)>, e: [S](index.md))   <br>
| [<init>](-init-.md)|  [jvm] fun <[S](index.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> [<init>](-init-.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), op: [BinaryOperator](https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html)<[S](index.md)>, e: [S](index.md))   <br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [allProd](all-prod.md)| [jvm]  <br>Content  <br>fun [allProd](all-prod.md)(): [S](index.md)  <br><br><br>
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [jvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [get](get.md)| [jvm]  <br>Content  <br>operator fun [get](get.md)(p: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [S](index.md)  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [jvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [maxRight](max-right.md)| [jvm]  <br>Content  <br>fun [maxRight](max-right.md)(l: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), f: [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)<[S](index.md)>): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [minLeft](min-left.md)| [jvm]  <br>Content  <br>fun [minLeft](min-left.md)(r: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), f: [Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)<[S](index.md)>): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [prod](prod.md)| [jvm]  <br>Content  <br>fun [prod](prod.md)(l: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), r: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [S](index.md)  <br><br><br>
| [set](set.md)| [jvm]  <br>Content  <br>operator fun [set](set.md)(p: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), x: [S](index.md))  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [jvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>

