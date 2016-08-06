package com.mdelcid

import com.mdelcid.models.H2RNode

import scala.scalajs.js.annotation.JSExport

import org.scalajs.{dom, jquery}

@JSExport
object H2RMain {
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

        val scalaJSReactCode = H2RNode(html, -1).childrenStr
        $("#html-textarea").`val`(scalaJSReactCode)
        $("#html-textarea").select()
      } catch {
        case e: Throwable =>
          e.printStackTrace()
          dom.window.alert("There was an error parsing the HTML. Please check your input is valid.")
      }
    })

  }


}
