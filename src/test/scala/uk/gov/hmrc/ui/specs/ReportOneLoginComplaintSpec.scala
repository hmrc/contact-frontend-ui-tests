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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.{ReportOneLoginComplaintPage, ReportOneLoginComplaintThanksPage}

class ReportOneLoginComplaintSpec extends BaseSpec {
  info("UI tests for /contact/report-one-login-complaint")

  Feature("Report a one login complaint") {
    Scenario("successful when user provides all the required data") {
      ReportOneLoginComplaintPage.goTo()

      Given("I am on the complaint form")
      userShouldSee(ReportOneLoginComplaintPage)

      When("I submit the report")
      ReportOneLoginComplaintPage.reportComplaint(
        "name"                -> "Gary Grapefruit",
        "nino"                -> "AA112233B",
        "date-of-birth.day"   -> "10",
        "date-of-birth.month" -> "10",
        "date-of-birth.year"  -> "1990",
        "email"               -> "platform-ui@digital.hmrc.gov.uk",
        "address"             -> "1 The Street, London, SW1A",
        "contact-preference"  -> "email",
        "complaint"           -> "This is an automated test complaint"
      )

      Then("I see the thank you message")
      userShouldSee(ReportOneLoginComplaintThanksPage)
    }
  }

}
