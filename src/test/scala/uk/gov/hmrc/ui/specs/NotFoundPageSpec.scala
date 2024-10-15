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

package uk.gov.hmrc.ui.specs

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.NotFoundPage

class NotFoundPageSpec extends BaseSpec {

  Feature("Not Found page") {

    Scenario("The page has the correct title") {
      Given("the user does not have welsh language selected")
      deleteAllCookies()

      When("the user visits a non-existent page")
      NotFoundPage.goTo()

      Then("the title should be visible in the default language")
      NotFoundPage.getPageHeading() shouldBe "Page not found"
    }
  }

}
