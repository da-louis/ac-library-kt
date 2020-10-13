//[ac-library-kt](../../../index.md)/[jp.atcoder.library.kotlin.modInt](../../index.md)/[ModIntFactory](../index.md)/[ModInt](index.md)



# ModInt  
 [jvm] inner class [ModInt](index.md)(**rawValue**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))   


## Functions  
  
|  Name|  Summary| 
|---|---|
| [addAsg](add-asg.md)| [jvm]  <br>Brief description  <br>this を this + [mi]() に変更する  <br>Content  <br>fun [addAsg](add-asg.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [div](div.md)| [jvm]  <br>Content  <br>operator fun [div](div.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [divAsg](div-asg.md)| [jvm]  <br>Brief description  <br>this を this / [mi]() に変更する  <br>Content  <br>fun [divAsg](div-asg.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [equals](equals.md)| [jvm]  <br>Content  <br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [hashCode](hash-code.md)| [jvm]  <br>Content  <br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [inv](inv.md)| [jvm]  <br>Brief description  <br>(this * inv) % mod = 1 を満たすような inv を ModInt として生成して返す.  <br>Content  <br>fun [inv](inv.md)(): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [minus](minus.md)| [jvm]  <br>Content  <br>operator fun [minus](minus.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [mulAsg](mul-asg.md)| [jvm]  <br>Brief description  <br>this を this * [mi]() に変更する  <br>Content  <br>fun [mulAsg](mul-asg.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [plus](plus.md)| [jvm]  <br>Content  <br>operator fun [plus](plus.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [pow](pow.md)| [jvm]  <br>Brief description  <br>(this ^ [b]()) % mod の結果を ModInt として生成して返す.  <br>Content  <br>fun [pow](pow.md)(b: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [subAsg](sub-asg.md)| [jvm]  <br>Brief description  <br>this を this - [mi]() に変更する  <br>Content  <br>fun [subAsg](sub-asg.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [times](times.md)| [jvm]  <br>Content  <br>operator fun [times](times.md)(mi: [ModIntFactory.ModInt](index.md)): [ModIntFactory.ModInt](index.md)  <br><br><br>
| [toString](to-string.md)| [jvm]  <br>Content  <br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [mod](index.md#jp.atcoder.library.kotlin.modInt/ModIntFactory.ModInt/mod/#/PointingToDeclaration/)|  [jvm] val [mod](index.md#jp.atcoder.library.kotlin.modInt/ModIntFactory.ModInt/mod/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>
| [value](index.md#jp.atcoder.library.kotlin.modInt/ModIntFactory.ModInt/value/#/PointingToDeclaration/)|  [jvm] val [value](index.md#jp.atcoder.library.kotlin.modInt/ModIntFactory.ModInt/value/#/PointingToDeclaration/): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)   <br>

