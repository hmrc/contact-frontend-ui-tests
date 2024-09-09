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

import org.openqa.selenium.By.partialLinkText
import uk.gov.hmrc.ui.pages.{AccessibilityProblemReportPage, AccessibilityProblemReportThanksPage}

class AccessibilityProblemReportSpec extends BaseSpec {

  info("UI tests for /contact/accessibility")

  Feature("Successfully submit a report accessibility problem form") {

    Scenario("I am able to successfully submit a report accessibility problem form") {
      val currentPage = AccessibilityProblemReportPage

      Given("I am on the report accessibility problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      When("I complete all the fields")
      currentPage.completeReportForm()

      And("I submit the form")
      currentPage.submitForm()

      Then("I see the submission confirmation page")
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportThanksPage.expectedPageTitle

      currentPage.getPageHeading()    shouldBe AccessibilityProblemReportThanksPage.expectedHeading
      currentPage.getPageSubHeading() shouldBe AccessibilityProblemReportThanksPage.expectedSubheading
    }
  }

  Feature("Validation fails when submitting an incomplete report accessibility problem form") {
    val currentPage = AccessibilityProblemReportPage

    Scenario("I do not complete all the fields") {

      Given("I am on the report accessibility problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      When("When I do not complete all the fields ")

      And("I submit the form")
      currentPage.submitForm()

      Then("Then I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.errorPageTitle

      val errorMessages = List(
        "Enter details of the accessibility problem",
        "Enter your full name",
        "Enter your email address"
      )

      val bodyText = currentPage.getPageBodyText()
      errorMessages.foreach { errorMessage =>
        bodyText should include(errorMessage)
      }
    }
  }

  Feature("Validation fails when submitting an invalid email address") {

    Scenario("I provide an invalid email address") {
      val currentPage = AccessibilityProblemReportPage

      Given("I am on the report accessibility problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      When("When I provide an invalid email address ")
      currentPage.completeReportForm(email = "firstname.lastname")

      And("I submit the form")
      currentPage.submitForm()

      Then("I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.errorPageTitle

      currentPage.getPageBodyText() should include(
        "Enter an email address in the correct format, like name@example.com"
      )
    }
  }

  Feature("Validation fails when submitting too long comment") {

    Scenario("I put too many characters in the text field") {
      val currentPage = AccessibilityProblemReportPage

      Given("I am on the report accessibility problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      When("When I provide an invalid email address ")
      currentPage.completeReportForm(commentsLength = 2001)

      And("I submit the form")
      currentPage.submitForm()

      Then("I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.errorPageTitle

      currentPage.getPageBodyText() should include(
        "Problem description must be 2000 characters or fewer"
      )
    }
  }

  Feature("Client side validation warns for too long comment") {

    Scenario("I put too many characters in the text field") {
      val currentPage = AccessibilityProblemReportPage

      Given("I am on the report accessibility problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      When("When I provide an invalid email address ")
      currentPage.completeReportForm(commentsLength = 2001)

      And("I do not submit the form")

      Then("I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      currentPage.getPageBodyText() should include(
        "You have 1 character too many"
      )
    }
  }

  Feature("Language switching") {
    val currentPage = AccessibilityProblemReportPage

    Scenario("I switch my language to Welsh") {

      Given("I am on the report accessibility problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedPageTitle

      When("When I use the language switch toggle")
      currentPage.driver().findElement(partialLinkText("Cymraeg")).click()

      Then("I see the help and contact page in Welsh")
      currentPage.getPageTitle() shouldBe AccessibilityProblemReportPage.expectedWelshPageTitle
    }
  }
}
