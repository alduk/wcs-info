package controllers

import play.api._
import play.api.mvc._
import com.sysiq.common.parsers._
import com.sysiq.common.parsers.TestParsers.Page

object Application extends Controller {
  val dir = Play.current.configuration.getString("wcs.assets.dir").get
  val struts_ext_file = Play.current.configuration.getString("wcs.struts.ext.file").get

  var group1 = filteredForwards(TestParsers.parseDir(dir), forwards)
  def index = Action {
    Ok(views.html.index("WSC Information.", Iterable()))
  }

  def listJspdependencies = Action {
    Ok(views.html.index("WSC Information.", group1))
  }

  def refresh = Action {
    val group = TestParsers.parseDir(dir)
    group1 = filteredForwards(group, forwards)
    Redirect("/listJspdependencies")
  }

  private def forwards: scala.collection.immutable.Seq[(String, String)] = {
    val xml = scala.xml.XML.loadFile(struts_ext_file)
    val fw = xml \\ "forward" map { x => ("/Stores/WebContent/AuroraStorefrontAssetStore" + (x \ "@path"), (x \ "@name").text) }
    val res = fw filter { x => x._2.indexOf("10651") >= 0 }
    res
  }

  private def filteredForwards(gr: scala.collection.Map[String,Page], fw: Seq[(String, String)]): Iterable[(String, Page, Option[(String, String)])] = {
    gr map { x => (x._1, x._2, fw.find(_._1 == x._1)) }
  }

}