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

object AccessibilityProblemReportPage extends BasePage {

  val url: String                    = wrapUrl("/accessibility")
  val expectedPageTitle: String      = "Report an accessibility problem – Contact HMRC – GOV.UK"
  val expectedWelshPageTitle: String = "Rhoi gwybod am broblem hygyrchedd – Cysylltu â CThEF – GOV.UK"

  def problemDescriptionField = driver().findElement(By.name("problemDescription"))
  def nameField               = driver().findElement(By.name("name"))
  def emailField              = driver().findElement(By.name("email"))

  def completeReportForm(
    name: String = validName,
    email: String = validEmail,
    commentsLength: Int = 25
  ) = {
    nameField.sendKeys(name)
    emailField.sendKeys(email)
    problemDescriptionField.sendKeys(generateRandomString(commentsLength))
  }
}
