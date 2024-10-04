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
import uk.gov.hmrc.ui.pages.{SurveyPage, SurveyThanksPage}

class SurveySpec extends BaseSpec {

  info("UI tests for /contact/survey")

  Feature("Successfully submit a survey form with valid ID") {

    Scenario("I am able to successfully submit a survey form with the optional comments") {
      val currentPage = SurveyPage

      Given("I am on the survey page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle

      When("I complete all the fields")
      currentPage.completeReportForm(commentsLength = Some(250))

      And("I submit the form")
      currentPage.submitForm()

      Then("I see the submission confirmation page")
      currentPage.getPageTitle()   shouldBe SurveyThanksPage.expectedPageTitle
      currentPage.getPageHeading() shouldBe SurveyThanksPage.expectedHeading
    }

    Scenario("I am able to successfully submit a survey form without the optional comments") {
      val currentPage = SurveyPage

      Given("I am on the survey page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle

      When("I complete all the fields")
      currentPage.completeReportForm(commentsLength = None)

      And("I submit the form")
      currentPage.submitForm()

      Then("I see the submission confirmation page")
      currentPage.getPageTitle()   shouldBe SurveyThanksPage.expectedPageTitle
      currentPage.getPageHeading() shouldBe SurveyThanksPage.expectedHeading
    }
  }

  Feature("Error page returned for ticket id using incorrect format") {
    Scenario("I use an invalid ticket id on the URL") {
      // TODO: Should return error page, currently not behaving as expected
    }
  }

  Feature("Validation fails when submitting an incomplete survey form") {

    Scenario("I do not complete all the fields") {
      val currentPage = SurveyPage

      Given("I am on the survey page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle

      When("When I do not complete all the fields ")

      And("I submit the form")
      currentPage.submitForm()

      Then("Then I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe SurveyPage.errorPageTitle

      val errorMessages = List(
        "Tell us how satisfied you are with the answer we gave you",
        "Tell us how satisfied you are with the speed of our reply"
      )

      val bodyText = currentPage.getPageBodyText()
      errorMessages.foreach { errorMessage =>
        bodyText should include(errorMessage)
      }
    }
  }

  Feature("Validation fails when submitting too long comment") {

    Scenario("I put too many characters in the text field") {
      val currentPage = SurveyPage

      Given("I am on the survey page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle

      When("When I write more than the allocated characters in a text field")
      currentPage.completeReportForm(commentsLength = Some(2501))

      And("I submit the form")
      currentPage.submitForm()

      Then("Then I see an error message citing the required fields")
      currentPage.getPageTitle() shouldBe SurveyPage.errorPageTitle

      val bodyText = currentPage.getPageBodyText()
      bodyText should include(
        "Improvement suggestions must be 2500 characters or fewer"
      )
    }
  }

  Feature("Client side validation warns for too long comment") {

    Scenario("I put too many characters in the text field") {
      val currentPage = SurveyPage

      Given("I am on the survey page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle

      When("When I write more than the allocated characters in a text field")
      currentPage.completeReportForm(commentsLength = Some(2501))

      And("I do not submit the form")

      Then("I see an error message telling me that I have exceeded the character limit")
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle
      val bodyText = currentPage.getPageBodyText()
      bodyText should include(
        "You have 1 character too many"
      )
    }
  }

  Feature("Language switching") {

    Scenario("I switch my language to Welsh") {
      val currentPage = SurveyPage

      Given("I am on the the survey page")
      currentPage.goTo()
      currentPage.getPageTitle() shouldBe SurveyPage.expectedPageTitle

      When("When I use the language switch toggle")
      currentPage.driver().findElement(partialLinkText("Cymraeg")).click()

      Then("I see the help and contact page in Welsh")
      currentPage.getPageTitle() shouldBe SurveyPage.expectedWelshPageTitle
    }
  }
}
