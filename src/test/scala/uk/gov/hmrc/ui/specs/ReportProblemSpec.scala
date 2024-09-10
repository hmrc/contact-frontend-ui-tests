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
import uk.gov.hmrc.ui.pages.{ReportProblemPage, ReportProblemThanksPage}

class ReportProblemSpec extends BaseSpec {

  info("UI tests for /contact/problem_reports_nonjs")

  Feature("Successfully submit a problem report form") {

    Scenario("I am able to successfully submit a problem report form") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("I submit the report")
      currentPage.completeReportForm()
      currentPage.submitForm()

      Then("I see the thank you page")
      currentPage.getPageTitle() shouldBe ReportProblemThanksPage.expectedPageTitle

      currentPage.getPageHeading()    shouldBe ReportProblemThanksPage.expectedHeading
      currentPage.getPageSubHeading() shouldBe ReportProblemThanksPage.expectedSubHeading
    }
  }

  Feature("Successfully submit a problem report form via deprecated URL") {

    Scenario("I am able to successfully submit a problem report form on the deprecated URL") {
      val currentPage = ReportProblemPage

      When("I go to thw report a technical problem page deprecated URL")
      currentPage.goToDeprecatedUrl()

      Then("I am redirected to the updated URL")
      currentPage.driver().getCurrentUrl should include("report-technical-problem")
      currentPage.getPageTitle()       shouldBe ReportProblemPage.expectedPageTitle

      When("I submit the report")
      currentPage.completeReportForm()
      currentPage.submitForm()

      Then("I see the thank you page")
      currentPage.getPageTitle() shouldBe ReportProblemThanksPage.expectedPageTitle

      currentPage.getPageHeading()    shouldBe ReportProblemThanksPage.expectedHeading
      currentPage.getPageSubHeading() shouldBe ReportProblemThanksPage.expectedSubHeading
    }
  }

  Feature("Validation fails when submitting a report technical problem form with invalid name") {

    Scenario("I submit a problem report form with invalid characters in name") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("I enter an invalid character in the name field")
      currentPage.completeReportForm(name = "Firstname & Lastname")

      And("I submit the report")
      currentPage.submitForm()

      Then("I see an error message with the correct format to follow")
      currentPage.getPageTitle() shouldBe ReportProblemPage.errorPageTitle

      val bodyText = currentPage.getPageBodyText()
      bodyText should include(
        "Full name must only include letters a to z, hyphens, full stops, commas, apostrophes and spaces"
      )
    }
  }

  Feature("Validation fails when submitting an incomplete report technical problem form") {

    Scenario("I do not complete all the fields") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("When I do not complete all the fields ")

      And("I submit the form")
      currentPage.submitForm()

      Then("Then I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe ReportProblemPage.errorPageTitle

      val errorMessages = List(
        "Enter your full name",
        "Enter your email address",
        "Enter details of what you were doing",
        "Enter details of what went wrong"
      )

      val bodyText = currentPage.getPageBodyText()
      errorMessages.foreach { errorMessage =>
        bodyText should include(errorMessage)
      }
    }
  }

  Feature("Validation fails when submitting an invalid email address") {

    Scenario("I provide an invalid email address") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("When I provide an invalid email address ")
      currentPage.completeReportForm(email = "firstname.lastname")

      And("I submit the form")
      ReportProblemPage.submitForm()

      Then("I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe ReportProblemPage.errorPageTitle

      val bodyText = currentPage.getPageBodyText()
      bodyText should include(
        "Enter an email address in the correct format, like name@example.com"
      )
    }
  }

  Feature("Validation fails when submitting too long comment") {

    Scenario("I put too many characters in the text field") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("When I write more than the allocated characters in a text field")
      currentPage.completeReportForm(actionLength = 1001)

      And("I submit the form")
      ReportProblemPage.submitForm()

      Then("I see an error message telling me that I have exceeded the character limit")
      currentPage.getPageTitle() shouldBe ReportProblemPage.errorPageTitle

      val bodyText = currentPage.getPageBodyText()
      bodyText should include(
        "Comment must be 1000 characters or less"
      )
    }
  }

  Feature("Client side validation warns for too long action comment") {

    Scenario("I put too many characters in the action field") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("When I write more than the allocated characters in a text field")
      currentPage.completeReportForm(actionLength = 1001)

      Then("I see an error message telling me that I have exceeded the character limit")
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      val bodyText = currentPage.getPageBodyText()
      bodyText should include(
        "You have 1 character too many"
      )
    }
  }

  Feature("Language switching") {

    Scenario("I switch my language to Welsh") {
      val currentPage = ReportProblemPage

      Given("I am on the report a technical problem page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedPageTitle

      When("When I use the language switch toggle")
      currentPage.driver().findElement(partialLinkText("Cymraeg")).click()

      Then("I see the help and contact page in Welsh")
      currentPage.getPageTitle() shouldBe ReportProblemPage.expectedWelshPageTitle
    }
  }

}
