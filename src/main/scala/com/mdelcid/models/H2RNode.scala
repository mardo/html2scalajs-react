package com.mdelcid.models

import com.mdelcid.AttributeNamesMapping
import org.scalajs.dom
import org.scalajs.dom.Node

import scala.scalajs.js
import scala.scalajs.js.UndefOr

/**
  * Created by mardo on 8/6/16.
  */

case class H2RNode(xml: dom.Node, depth: Integer) {

	val children: Seq[H2RNode] = {
		xml.asInstanceOf[js.Dynamic].children.asInstanceOf[js.Array[dom.Node]].map(H2RNode(_, depth + 1))
	}

	val attributes: Seq[H2RAttribute] = {
		UndefOr.any2undefOrA(xml.attributes).toOption match {
			case Some(attrs) =>
				for {
					i <- 0 until attrs.length
				} yield {
					H2RAttribute(attrs.item(i))
				}
			case _ =>
				Seq.empty[H2RAttribute]
		}
	}

	def attributesStr: String = {
		if (attributes.nonEmpty) {
			s"(${js.Array(attributes: _*).join(", ")})"
		} else {
			""
		}
	}

	def childrenStr: String = {
		if (children.nonEmpty) {
			children.map(_.toString).mkString(",\n") match {

				case str if depth == -1 => str // root element

				case str =>
					s"""(
					    |${str}
					    |$tabs)""".stripMargin
			}
		} else {
			xml.textContent.trim match {
				case textContent if textContent.nonEmpty => s"""("${textContent}")"""
				case _ => ""
			}
		}
	}

	def tabs = "\t" * depth

	override def toString: String = {
		s"""$tabs<.${xml.nodeName}${attributesStr}${childrenStr}""".stripMargin
	}
}
