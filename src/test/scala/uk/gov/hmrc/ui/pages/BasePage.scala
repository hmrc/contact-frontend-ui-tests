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

import org.openqa.selenium.By.tagName
import org.openqa.selenium.{By, WebDriver}
import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver

import scala.util.Random

trait BasePage extends PageObject {
  val url: String
  val expectedPageTitle: String
  val expectedWelshPageTitle: String

  def goTo(): Unit = get(url)

  def driver(): WebDriver = Driver.instance

  def wrapUrl(partialUrl: String): String =
    TestEnvironment.url("contact-frontend") + partialUrl

  def errorPageTitle: String = s"Error: $expectedPageTitle"

  def getPageTitle(): String = super.getTitle

  def getPageHeading(): String    = Driver.instance.findElement(tagName("h1")).getText
  def getPageSubHeading(): String = Driver.instance.findElement(tagName("h2")).getText
  def getPageBodyText(): String   = Driver.instance.findElement(tagName("body")).getText

  def submitForm(): Unit =
    driver().findElement(By.cssSelector(".govuk-button[type=submit]")).click()

  protected val validName: String  = "Firstname Lastname"
  protected val validEmail: String = "firstname.lastname@example.com"

  protected def generateRandomString(length: Int): String =
    Random.alphanumeric.take(length).mkString
}
