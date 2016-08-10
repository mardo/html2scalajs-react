package com.mdelcid

import com.mdelcid.models.H2RNode

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js
import org.scalajs.{dom, jquery}
import org.scalajs.dom.window.console

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

				val htmlArray = $.parseHTML(htmlStr).asInstanceOf[js.Array[dom.Node]]

				js.Dynamic.global.vvv = htmlArray

				val rootNode = new js.Object {
					val nodeName = "#root"
					val childNodes = htmlArray
					val textContent = ""
				}.asInstanceOf[dom.Node]

				console.log(rootNode)

				val scalaJSReactCode = H2RNode(rootNode, 0).childrenStr

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
