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

import org.openqa.selenium.By
import org.openqa.selenium.By.partialLinkText
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.OneLoginComplaintPage.{click, submitForm}
import uk.gov.hmrc.ui.pages.ReportProblemPage.generateRandomString
import uk.gov.hmrc.ui.pages.SurveyPage.driver
import uk.gov.hmrc.ui.pages.{OneLoginComplaintPage, OneLoginComplaintThanksPage}

class OneLoginComplaintSpec extends BaseSpec {
  info("UI tests for /contact/report-one-login-complaint")

  Feature("Report a one login complaint") {
    Scenario("successful when user provides all the mandatory and optional data") {
      OneLoginComplaintPage.goTo()

      Given("I am on the complaint form")
      userShouldSee(OneLoginComplaintPage)

      When("I submit the report")
      OneLoginComplaintPage.fillComplaintForm(
        "name"                -> "Gary Grapefruit",
        "nino"                -> "AA112233B",
        "sa-utr"              -> "1020304050",
        "date-of-birth.day"   -> "10",
        "date-of-birth.month" -> "10",
        "date-of-birth.year"  -> "1990",
        "email"               -> "platform-ui@digital.hmrc.gov.uk",
        "phone-number"        -> "07711 223344",
        "address"             -> "1 The Street, London, SW1A",
        "contact-preference"  -> "email",
        "complaint"           -> "This is an automated test complaint"
      )
      submitForm()

      Then("I see the confirmation page")
      userShouldSee(OneLoginComplaintThanksPage)
    }
  }

  Scenario("successful when user provides all the mandatory data only") {
    OneLoginComplaintPage.goTo()

    Given("I am on the complaint form")
    userShouldSee(OneLoginComplaintPage)

    When("I submit the report")
    OneLoginComplaintPage.fillComplaintForm(
      "name"                -> "Gary Grapefruit",
      "nino"                -> "AA112233B",
      "date-of-birth.day"   -> "10",
      "date-of-birth.month" -> "10",
      "date-of-birth.year"  -> "1990",
      "email"               -> "platform-ui@digital.hmrc.gov.uk",
      "address"             -> "1 The Street, London, SW1A",
      "contact-preference"  -> "email"
    )
    submitForm()

    Then("I see the confirmation page")
    userShouldSee(OneLoginComplaintThanksPage)
  }

  Scenario("fails validation with empty fields") {

    Given("I am on the complaint form")
    OneLoginComplaintPage.goTo()

    When("When I do not complete all the fields ")

    And("I submit the form")
    submitForm()

    Then("Then I see an error message citing the required fields")
    Driver.instance.getTitle shouldBe OneLoginComplaintThanksPage.errorPageTitle

    val errorMessages = List(
      "Enter your full name",
      "Enter a National Insurance number in the correct format",
      "Date of birth must include a day",
      "Date of birth must include a month",
      "Date of birth must include a year",
      "Enter your email address",
      "Enter your full address"
    )

    val bodyText = Driver.instance.findElement(By.tagName("body")).getText

    errorMessages.foreach { errorMessage =>
      bodyText should include(errorMessage)
    }
  }

  Scenario("fails validation when submitted with invalid characters in name") {

    Given("I am on the complaint form")
    OneLoginComplaintPage.goTo()

    When("I enter an invalid character in the name field")
    OneLoginComplaintPage.fillComplaintForm(
      "name"                -> "Firstname & Lastname",
      "nino"                -> "AA112233B",
      "date-of-birth.day"   -> "10",
      "date-of-birth.month" -> "10",
      "date-of-birth.year"  -> "1990",
      "email"               -> "platform-ui@digital.hmrc.gov.uk",
      "address"             -> "1 The Street, London, SW1A",
      "contact-preference"  -> "email",
      "complaint"           -> "This is an automated test complaint"
    )
    submitForm()

    Then("I see an error message with the correct format to follow")
    Driver.instance.getTitle shouldBe OneLoginComplaintThanksPage.errorPageTitle

    val bodyText = Driver.instance.findElement(By.tagName("body")).getText
    bodyText should include(
      "Full name must only include letters a to z, hyphens, full stops, commas, apostrophes and spaces"
    )
  }

  Scenario("fails validation when submitted with invalid email address") {
    Given("I am on the complaint form")
    OneLoginComplaintPage.goTo()

    When("I enter invalid data in the email field")
    OneLoginComplaintPage.fillComplaintForm(
      "name"                -> "Firstname & Lastname",
      "nino"                -> "AA112233B",
      "date-of-birth.day"   -> "10",
      "date-of-birth.month" -> "10",
      "date-of-birth.year"  -> "1990",
      "email"               -> "firstname.lastname",
      "address"             -> "1 The Street, London, SW1A",
      "contact-preference"  -> "email",
      "complaint"           -> "This is an automated test complaint"
    )
    submitForm()

    Then("I see an error message with the correct format to follow")
    Driver.instance.getTitle shouldBe OneLoginComplaintThanksPage.errorPageTitle

    val bodyText = Driver.instance.findElement(By.tagName("body")).getText
    bodyText should include(
      "Enter an email address in the correct format, like name@example.com"
    )
  }

  Scenario("fails client side validation when complaint text is too long") {
    Given("I am on the complaint form")
    OneLoginComplaintPage.goTo()

    When("When I write more than the allocated characters in a text field")
    val longComplaint = generateRandomString(2001)
    OneLoginComplaintPage.fillComplaintForm(
      "name"                -> "Firstname & Lastname",
      "nino"                -> "AA112233B",
      "date-of-birth.day"   -> "10",
      "date-of-birth.month" -> "10",
      "date-of-birth.year"  -> "1990",
      "email"               -> "firstname.lastname",
      "address"             -> "1 The Street, London, SW1A",
      "contact-preference"  -> "email",
      "complaint"           -> longComplaint
    )

    Then("I see an error message telling me that I have exceeded the character limit")
    val bodyText = Driver.instance.findElement(By.tagName("body")).getText
    bodyText should include(
      "You have 1 character too many"
    )
  }

  Scenario("language switching") {
    Given("I am on the complaint form")
    OneLoginComplaintPage.goTo()

    When("When I use the language switch toggle")
    driver().findElement(partialLinkText("Cymraeg")).click()

    Then("I see the help and contact page in Welsh")
    Driver.instance.getTitle shouldBe OneLoginComplaintPage.expectedWelshPageTitle
  }

}
