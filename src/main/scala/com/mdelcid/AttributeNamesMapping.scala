package com.mdelcid

import scala.util.matching.Regex

/**
  * Created by mardo on 8/5/16.
  */
object AttributeNamesMapping {

	val DataPattern = "(data-.*)".r

	val exceptions = Map(
		"class" -> "className",
		"type" -> "`type`"
	)

	def apply(key: String): String = {
		key match {
			case DataPattern(attrName) =>
				s""""$attrName".reactAttr"""

			case _ =>
				val attrName = exceptions.get(key).getOrElse(key)
				s"""^.${attrName}"""
		}
	}

}
