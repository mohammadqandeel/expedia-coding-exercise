package models

import play.api.libs.json._

case class OfferInfo(siteID: String, language: String, currency: String, userSelectedCurrency: String)

case class Persona(personaType: String)

case class UserInfo(persona: Persona, userId: String)

case class Offer(Hotel: List[HotelOffer])

case class HotelOffer(offerDateRange: OfferDateRange, destination: Destination, hotelInfo: HotelInfo,
                      hotelUrgencyInfo: HotelUrgencyInfo, hotelPricingInfo: HotelPricingInfo, hotelUrls: HotelUrls)


case class OfferDateRange(travelStartDate: List[Int], travelEndDate: List[Int])

case class Destination(regionID: String, associatedMultiCityRegionId: String, longName: String, shortName: String, country: String,
                       province: String, city: String, tla: String, nonLocalizedCity: String)

case class HotelInfo(hotelId: String, hotelName: String, localizedHotelName: String, hotelDestination: String, hotelDestinationRegionID: String, hotelLongDestination: String,
                     hotelStreetAddress: String, hotelCity: String, hotelProvince: String, hotelCountryCode: String,
                     hotelLatitude: Float, hotelLongitude: Float, hotelStarRating: String, hotelGuestReviewRating: Float, hotelReviewTotal: Int, hotelImageUrl: String,
                     vipAccess: Boolean, isOfficialRating: Boolean)

case class HotelUrgencyInfo(airAttachRemainingTime: Int, numberOfPeopleViewing: Int, numberOfPeopleBooked: Int, numberOfRoomsLeft: Int, lastBookedTime: Long,
                            almostSoldStatus: String, link: String, airAttachEnabled: Boolean)

case class HotelPricingInfo(averagePriceValue: Float, totalPriceValue: Float, crossOutPriceValue: Float, originalPricePerNight: Float,
                            currency: String, percentSavings: Float, drr: Boolean)

case class HotelUrls(hotelInfositeUrl: String, hotelSearchResultUrl: String)

case class OffersModelV2(offerInfo: OfferInfo, userInfo: UserInfo, offers: Offer)

object Offers {
  implicit val offerInfoFormat = Json.format[OfferInfo]
  implicit val personaFormat = Json.format[Persona]
  implicit val userInfoFormat = Json.format[UserInfo]
  implicit val OfferDateRangeFormat = Json.format[OfferDateRange]
  implicit val destinationFormat = Json.format[Destination]
  implicit val hotelInfoFormat = Json.format[HotelInfo]
  implicit val hotelUrgencyInfoFormat = Json.format[HotelUrgencyInfo]
  implicit val hotelPricingInfoFormat = Json.format[HotelPricingInfo]
  implicit val hotelUrlsFormat = Json.format[HotelUrls]

  implicit val hotelOfferFormat = Json.format[HotelOffer]
  implicit val offerFormat = Json.format[Offer]
  implicit val offerModelV2Format = Json.format[OffersModelV2]
}