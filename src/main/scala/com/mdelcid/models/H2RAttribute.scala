package com.mdelcid.models

import com.mdelcid.AttributeNamesMapping
import org.scalajs.dom
import org.scalajs.dom.raw.Attr

import scala.scalajs.js
import scala.scalajs.js.UndefOr

/**
  * Created by mardo on 8/6/16.
  */
case class H2RAttribute(attr: Attr) {

	val exceptions = Map(
		"class" -> "className",
		"type" -> "`type`"
	)

	override def toString: String = {
		val attrName = exceptions.getOrElse(attr.name, attr.name)

		attrName match {
			case _ if attrName.contains("-") =>
				s""""$attrName".reactAttr := "${attr.value}""""

			case name =>
				s"""^.${attrName} := "${attr.value}""""
		}
	}


}
