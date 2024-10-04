/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By

object SurveyPage extends BasePage {

  val url: String                    = wrapUrl("/survey?ticketId=ABCD-DEFG-HIJK&serviceId=contact=frontend")
  val expectedPageTitle: String      = "Survey – GOV.UK"
  val expectedWelshPageTitle: String = "Arolwg – GOV.UK"

  def answerSatisfactionRadioElement(rating: Int) =
    driver().findElement(By.cssSelector(s"input[value=\"$rating\"][id=\"helpful\"]"))
  def answerSpeedRadioElement(rating: Int)        =
    driver().findElement(By.cssSelector(s"input[value=\"$rating\"][id=\"speed\"]"))
  def commentsField                               = driver().findElement(By.name("improve"))

  def completeReportForm(
    satisfactionRating: Int = 5,
    speedRating: Int = 5,
    commentsLength: Option[Int] = Some(25)
  ) = {
    answerSatisfactionRadioElement(satisfactionRating).click()
    answerSpeedRadioElement(speedRating).click()
    if (commentsLength.isDefined) {
      commentsField.sendKeys(generateRandomString(commentsLength.get))
    }
  }
}
