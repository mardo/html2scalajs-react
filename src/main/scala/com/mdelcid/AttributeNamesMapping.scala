package com.mdelcid

/**
  * Created by mardo on 8/5/16.
  */
object AttributeNamesMapping {
	val exceptions = Map(
		"class" -> "className"
	)

	def apply(key: String): String = {
		exceptions.get(key).getOrElse(key)
	}

}
