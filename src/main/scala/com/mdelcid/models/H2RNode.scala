package com.mdelcid.models

import com.mdelcid.AttributeNamesMapping
import org.scalajs
import org.scalajs.dom
import org.scalajs.dom.Node
import org.scalajs.dom.window.console

import scala.scalajs.js
import scala.scalajs.js.UndefOr

/**
  * Created by mardo on 8/6/16.
  */


case class H2RNode(xml: dom.Node, depth: Integer) {

	val children: List[H2RNode] = {
		xml.asInstanceOf[js.Dynamic].childNodes.asInstanceOf[js.Array[dom.Node]]
			.map(H2RNode(_, depth + 1))
			.filterNot(_.toString.trim.isEmpty)
		    .toList
	}

	def textContent = xml.textContent.trim

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
		children match {
			case Nil =>
				""
			case head :: Nil if head.xml.nodeName == "#text" =>
				s"""($head)"""

			case _ =>
				val cs = children.map(child => s"${"\t" * depth}$child").mkString(",\n")

				if (depth == 0) { // root node, don't add parenthesis, tabs nor new lines
					cs
				} else {
					s"""(
					    |$cs
				        |${"\t" * (depth-1)})""".stripMargin
				}
		}

	}

	override def toString: String = {
		xml.nodeName match {
			case "#root" =>
				childrenStr

			case "#text" =>
				if (textContent.nonEmpty) {
					s""""$textContent""""
				} else {
					""
				}

			case "#comment" =>
				if (textContent.nonEmpty) {
					s"""//$textContent"""
				} else {
					""
				}

			case _ =>
				s"""<.${xml.nodeName.toLowerCase}${attributesStr}${childrenStr}""".stripMargin
		}

	}
}