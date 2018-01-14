package models

case class GetOffersRequest(uid: String,
                            scenario: String,
                            page: String,
                            productType: String,
                            destinationName: Option[String] = None,
                            minTripStartDate: Option[String] = None,
                            maxTripStartDate: Option[String] = None,
                            lengthOfStay: Option[Int] = None,
                            minStarRating: Option[BigDecimal] = None,
                            maxStarRating: Option[BigDecimal] = None,
                            minTotalRate: Option[BigDecimal] = None,
                            maxTotalRate: Option[BigDecimal] = None,
                            minGuestRating: Option[BigDecimal] = None,
                            maxGuestRating: Option[BigDecimal] = None)
