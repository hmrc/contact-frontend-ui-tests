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

object OneLoginComplaintPage extends BasePage {
  override val url: String                    = wrapUrl("/report-one-login-complaint")
  override val expectedPageTitle: String      = "One Login for Government complaint – Contact HMRC – GOV.UK"
  override val expectedWelshPageTitle: String = "Cwyn One Login for Government – Cysylltu â CThEF – GOV.UK"

  private val clickableFormElements = List("contact-preference")

  def fillComplaintForm(complaint: (String, String)*): Unit =
    for ((field, value) <- complaint)
      if (clickableFormElements.contains(field)) {
        click(By.xpath(s"//input[@name='$field' and @value='$value']"))
      } else {
        sendKeys(By.name(field), value)
      }
}
