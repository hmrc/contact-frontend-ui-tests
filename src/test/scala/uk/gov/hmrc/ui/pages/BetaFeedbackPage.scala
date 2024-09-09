/*
 * Copyright 2023 HM Revenue & Customs
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

object BetaFeedbackPage extends BasePage {

  override val url: String                    = wrapUrl("/beta-feedback?service=pay")
  override val expectedPageTitle: String      = "Send your feedback – GOV.UK"
  override val expectedWelshPageTitle: String = "Anfon eich adborth – GOV.UK"

  def ratingRadioElement(rating: Int) =
    driver().findElement(By.cssSelector(s"input[value=\"$rating\"]"))
  def nameField                       = driver().findElement(By.name("feedback-name"))
  def emailField                      = driver().findElement(By.name("feedback-email"))
  def commentsField                   = driver().findElement(By.name("feedback-comments"))

  def completeReportForm(
    name: String = validName,
    email: String = validEmail,
    rating: Int = 5,
    commentsLength: Int = 25
  ) = {
    nameField.sendKeys(name)
    emailField.sendKeys(email)
    ratingRadioElement(rating).click()
    commentsField.sendKeys(generateRandomString(commentsLength))
  }
}
