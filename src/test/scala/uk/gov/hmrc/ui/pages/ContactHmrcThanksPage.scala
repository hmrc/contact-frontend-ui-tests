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

object ContactHmrcThanksPage extends BasePage {

  override val url: String                    = wrapUrl("/contact-hmrc/thanks")
  override val expectedPageTitle: String      = "Help and contact – GOV.UK"
  override val expectedWelshPageTitle: String = "Cymorth a chysylltiadau – GOV.UK"

  val expectedHeading: String    = "We have received your request for help"
  val expectedSubheading: String = "What happens next"

}
