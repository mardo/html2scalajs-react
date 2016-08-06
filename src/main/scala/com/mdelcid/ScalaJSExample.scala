package com.mdelcid

import scala.scalajs.js.annotation.JSExport
import org.scalajs.{dom, jquery}
import org.scalajs.dom.console

import scala.util.Random
import scalajs.js
import org.scalajs

import scala.scalajs.js.UndefOr


@JSExport
object ScalaJSExample {
  val $ = jquery.jQuery

  @JSExport
  def main(): Unit = {

    $("#html-textarea").click((e: dom.Event) => {
      $("#html-textarea").select()
    })
    $("#convert-button").click((e: dom.Event) => {
      val htmlStr = $("#html-textarea").`val`().toString

      try {
        val html = $.parseXML(htmlStr).asInstanceOf[dom.Node]
        js.Dynamic.global.window.vvv = html

        val scalaJSReactCode = getChildrenStr(html, 0)
        $("#html-textarea").`val`(scalaJSReactCode)
        $("#html-textarea").select()
      } catch {
        case e: Throwable =>
          dom.window.alert("There was an error parsing the HTML. Please check your input is valid.")
      }
    })

  }


  def getAttributesStr(xml: dom.Node): String = {

    UndefOr.any2undefOrA(xml.attributes).toOption match {
      case Some(attributes) if attributes.length > 0 =>
        val res: js.Array[String] = js.Array()
        for( i <- 0 to attributes.length-1) {
          val attr = attributes.item(i)
          res.push(s"""^.${AttributeNamesMapping(attr.name)} := "${attr.value}"""")
        }

        s"(${res.join(", ")})"

      case _ => ""
    }

  }

  def getChildrenStr(xml: dom.Node, depth: Integer): String = {
    val children: js.Array[dom.Node] = xml.asInstanceOf[js.Dynamic].children.asInstanceOf[js.Array[dom.Node]]
    val tabs = "\t" * depth

    if (children.length > 0) {
      s"""${children.map(getNodeStr(_, depth)).mkString(",\n")}""".stripMargin
    } else {
      s"""$tabs"${xml.textContent.trim}""""
    }

  }

  def getNodeStr(xml: dom.Node, depth: Integer): String = {
    val tabs = "\t" * depth

    s"""$tabs<.${xml.nodeName}${getAttributesStr(xml)}(
       |${getChildrenStr(xml, depth+1)}
       |$tabs)""".stripMargin
  }


}
