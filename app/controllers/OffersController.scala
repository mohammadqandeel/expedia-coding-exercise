package controllers

import javax.inject.{Inject, _}

import akka.actor.ActorSystem
import models.Offers._
import models._
import play.api.Logger
import play.api.data.Forms._
import play.api.data._
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws._
import play.api.mvc._
import play.api.Configuration

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OffersController @Inject()(cc: MessagesControllerComponents, actorSystem: ActorSystem,
                                 ws: WSClient, config: Configuration)(implicit exec: ExecutionContext) extends MessagesAbstractController(cc) {

  val getOffersForm = Form(
    mapping("uid" -> text,
      "scenario" -> text,
      "page" -> text,
      "productType" -> text,
      "destinationName" -> optional(text),
      "minTripStartDate" -> optional(text),
      "maxTripStartDate" -> optional(text),
      "lengthOfStay" -> optional(number),
      "minStarRating" -> optional(bigDecimal),
      "maxStarRating" -> optional(bigDecimal),
      "minTotalRate" -> optional(bigDecimal),
      "maxTotalRate" -> optional(bigDecimal),
      "minGuestRating" -> optional(bigDecimal),
      "maxGuestRating" -> optional(bigDecimal))(GetOffersRequest.apply)(GetOffersRequest.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.offers.getOffers(getOffersForm.fill(GetOffersRequest("foo", "deal-finder", "foo", ""))))
  }

  def test = Action {
    Ok("success")
  }

  def getOffers = Action.async { implicit request =>
    getOffersForm.bindFromRequest().fold(
      errorForm => {
        Future.successful(BadRequest(views.html.offers.getOffers(errorForm)))
      },
      requestForm => {
        ws.url(config.get[String]("app.service.offers.url") + buildQueryParams(requestForm)).get().map(reponse =>
          Json.fromJson[OffersModelV2](Json.parse(reponse.body)) match {
            case JsSuccess(offer, path) =>
              Logger.info(offer.toString())
              Ok(views.html.offers.offersList(offer, getOffersForm.fill(requestForm)))
            case e: JsError => Ok(e.errors.map(error => error._1.toString + " " + error._2.toString).mkString("|")) // JsError.toJson(e)
          })
      }
    )
  }

  private def buildQueryParams(requestForm: GetOffersRequest) = {
    val queryParams: StringBuilder = new StringBuilder()
    queryParams.append(s"?scenario=${requestForm.scenario}")
    queryParams.append(s"&page=${requestForm.page}")
    queryParams.append(s"&uid=${requestForm.uid}")
    queryParams.append(s"&productType=${requestForm.productType}")
    //    if (requestForm.destinationName.isDefined) queryParams.append(s"&destinationName=${requestForm.destinationName.get}")
    //    if (requestForm.minTripStartDate.isDefined) queryParams.append(s"&minTripStartDate=${requestForm.minTripStartDate.get}")
    //    if (requestForm.maxTripStartDate.isDefined) queryParams.append(s"&maxTripStartDate=${requestForm.maxTripStartDate.get}")
    //    if (requestForm.lengthOfStay.isDefined) queryParams.append(s"&lengthOfStay=${requestForm.lengthOfStay.get}")
    //    if (requestForm.minStarRating.isDefined) queryParams.append(s"&minStarRating=${requestForm.minStarRating.get}")
    //    if (requestForm.maxStarRating.isDefined) queryParams.append(s"&maxStarRating=${requestForm.maxStarRating.get}")
    //    if (requestForm.minTotalRate.isDefined) queryParams.append(s"&minTotalRate=${requestForm.minTotalRate.get}")
    //    if (requestForm.maxTotalRate.isDefined) queryParams.append(s"&maxTotalRate=${requestForm.maxTotalRate.get}")
    //    if (requestForm.minGuestRating.isDefined) queryParams.append(s"&minGuestRating=${requestForm.minGuestRating.get}")
    //    if (requestForm.maxGuestRating.isDefined) queryParams.append(s"&maxGuestRating=${requestForm.maxGuestRating.get}")

    queryParams.result()
  }
}
