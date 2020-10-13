//[ac-library-kt](../../index.md)/[jp.atcoder.library.kotlin.modInt](../index.md)/[ModIntFactory](index.md)



# ModIntFactory  
 [jvm] ModInt を生成するためのファクトリ.  
  
class [ModIntFactory](index.md)(**mod**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [<init>](-init-.md)|  [jvm] fun [<init>](-init-.md)(mod: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   <br>


## Types  
  
|  Name|  Summary| 
|---|---|
| [ModInt](-mod-int/index.md)| [jvm]  <br>Content  <br>inner class [ModInt](-mod-int/index.md)(**rawValue**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [create](create.md)| [jvm]  <br>Brief description  <br>ModInt を生成する. 生成された ModInt は、[value]() をファクトリ生成時に指定した mod で割った余りを持つ.  <br>Content  <br>fun [create](create.md)(value: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [ModIntFactory.ModInt](-mod-int/index.md)  <br><br><br>
| [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)| [jvm]  <br>Content  <br>open operator override fun [equals](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)| [jvm]  <br>Content  <br>open override fun [hashCode](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)| [jvm]  <br>Content  <br>open override fun [toString](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>

