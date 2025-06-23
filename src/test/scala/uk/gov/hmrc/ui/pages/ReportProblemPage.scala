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

import org.openqa.selenium.By.name

object ReportProblemPage extends BasePage {
  override val url: String                    = wrapUrl("/report-technical-problem?service=pay")
  override val expectedPageTitle: String      = "Get help with a technical problem – Contact HMRC – GOV.UK"
  override val expectedWelshPageTitle: String = "Cael help gyda phroblem dechnegol – Cysylltu â CThEF – GOV.UK"

  val deprecatedUrl       = wrapUrl("/problem_reports_nonjs?service=pay")
  def goToDeprecatedUrl() = get(deprecatedUrl)

  def nameField   = driver().findElement(name("report-name"))
  def emailField  = driver().findElement(name("report-email"))
  def actionField = driver().findElement(name("report-action"))
  def errorField  = driver().findElement(name("report-error"))

  def completeReportForm(
    name: String = validName,
    email: String = validEmail,
    actionLength: Int = 25,
    errorLength: Int = 25
  ) = {
    nameField.sendKeys(name)
    emailField.sendKeys(email)
    actionField.sendKeys(generateRandomString(actionLength))
    errorField.sendKeys(generateRandomString(errorLength))
  }
}
