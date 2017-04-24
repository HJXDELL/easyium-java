=======
easyium
=======
easyium is an easy-to-use wrapper for selenium&appium and it can make you more focus on business not the element.

Find the latest version on github: https://github.com/KarlGong/easyium-java or central Maven repository: http://repo1.maven.org/maven2/com/iselsoft/easyium/

Advantages
----------
- easyium provides unified apis to test on browsers and devices.

- easyium adds a global implicit wait for elements and you rarely need to consider waiting a element to be visible or existing.

- easyium introduces a simple and clear way to build model objects for UI.

- easyium has a better performance, the element will lazily load WebElement reference and reuses it if necessary.

- easyium provides easy-to-use wait method for element. e.g., myElement.waitFor().not().exists()

- easyium provides a simple way to define a locator. e.g., use ``"xpath=.//mytag"`` instead of ``By.xpath(".//mytag")``

- easyium provides a mechanism to avoid StaleElementReferenceException.

Maven Information
-----------------
If you're using Maven, you will find easyium artifact in the central Maven repository here: http://repo1.maven.org/maven2/com/iselsoft/easyium/

In order to start using easyium in your Maven project, just add the following dependency to your ``pom.xml``.

.. code:: xml

  <dependency>
    <groupId>com.iselsoft</groupId>
    <artifactId>easyium</artifactId>
    <version>1.0.5</version>
  </dependency>

Glossary
--------
WebDriver
~~~~~~~~~
It is a wrapper for selenium&appium's web driver.

DynamicElement
~~~~~~~~~~~~~~
DynamicElement is one type of Element in easyium. It refers to the element which is dynamic relative to its parent.

You can get it only by calling ``WebDriver.findElement(locator)`` or ``Element.findElement(locator)`` and you can not create a new instance by yourself.

StaticElement
~~~~~~~~~~~~~
StaticElement is the other type of Element in easyium. It refers to the element which is static relative to its parent.

You can create a new instance by providing parent and locator.

Example
-------
Here, we want to get the app names in google's home page https://www.google.com/

Firstly, build the model object for google's home page.

.. code:: java

  import com.iselsoft.easyium.ChromeDriver;
  import com.iselsoft.easyium.Element;
  import com.iselsoft.easyium.support.FoundBy;
  import com.iselsoft.easyium.support.Page;
  
  // This class maps the google page in the browser.
  public class Google extends Page{
  
      // The google apps grid button is in the top-right.
      // This button is always in the page, so it is StaticElement.
      @FoundBy(locator = "class=gb_b")
      private Element googleAppsGridButton;
      
      // After clicked the google apps grid button, the google apps list will be shown.
      // This list is always in the page, although it is invisible, it is StaticElement.
      @FoundBy(locator = "class=gb_ha")
      private Element googleAppsList;
          
      public Google() {
          // Create a WebDriver instance for chrome.
          super(new ChromeDriver());
          
          // Currently the StaticElement does not refer to WebElement in Browser,
          // so open url here is fine.
          webDriver.open("https://www.google.com");
      }
      
      public GoogleAppsList clickGoogleAppsGridButton() {
          // It is StaticElement, easyium will wait it to be visible automatically.
          googleAppsGridButton.click();
          
          // Let's return the GoogleAppsList object.
          return new GoogleAppsList(googleAppsList);
      }
      
      public void quit() {
          webDriver.quit();
      }
  }

Build model object for the google apps list.

.. code:: java

  import com.iselsoft.easyium.Element;
  import com.iselsoft.easyium.support.Control;
  
  import java.util.ArrayList;
  import java.util.List;
  
  // This class maps google apps list in the browser.
  public class GoogleAppsList extends Control {
      protected GoogleAppsList(Element element) {
          super(element);
      }
      
      public void waitUntilReady() {
          // Wait for the google apps list visible.
          element.waitFor().visible();
  
          // In most cases we should wait for the mask not existing here .
          // But in this case, no mask here.
          // loadingMask.waitFor().not().exists()
      }
      
      public List<GoogleApp> getAllApps() {
          // We should wait this control until ready.
          waitUntilReady();
          
          // Find the elements under google apps list.
          // We do not know how many apps in the list, so use find_elements(locator).
          // The found elements are DynamicElements.
          List<GoogleApp> googleApps = new ArrayList<>();
          for (Element ele : element.findElements("class=gb_Z")) {
              googleApps.add(new GoogleApp(ele));
          }
          return googleApps;
      }
  }

Build model object for the google app.

.. code:: java
  
  import com.iselsoft.easyium.Element;
  import com.iselsoft.easyium.support.Control;
  import com.iselsoft.easyium.support.FoundBy;
  
  // This class maps google app in the browser.
  public class GoogleApp extends Control {
      // This locator is relative to parent.
      @FoundBy(locator = "class=gb_4")
      private Element name;
      
      protected GoogleApp(Element element) {
          super(element);
      }
      
      public String getName() {
          // get_text() doesn't work here, so use javascript
          // return name.get_text()
          return (String) name.getWebDriver().executeScript("return arguments[0].innerText", name);
      }
  }

Then we can run our code and get google app names.

.. code:: java

  public class Main {
      
      public static void main(String[] args) {
          Google google = new Google();
          GoogleAppsList googleAppsList = google.clickGoogleAppsGridButton();
          for (GoogleApp googleApp : googleAppsList.getAllApps()) {
              System.out.println(googleApp.getName());
          }
          google.quit();
      }
  }

Contact me
----------
For information and suggestions you can contact me at karl.gong@outlook.com

Change Log
----------
1.0.5 (compared to 1.0.5)

- Add RemoteWebDriver and PhantomJSDriver.

1.0.4 (compared to 1.0.3)

- Add Focus() for Element.

1.0.3 (compared to 1.0.2)

- Depend selenium 3.0.1 and appium 4.1.2.

- Supporting waiting for WebDriver.switchTo().frame().

- Support webDriver.waitFor().reloaded().

1.0.2 (compared to 1.0.1)

- Remove atLeast argument in Context.fineElements().

- Support FindElement(s)Condition in Context.findElement(s).

1.0.1 (compared to 1.0.0)

- Add example in README.

- No checked exceptions will be thrown.

1.0.0

- Baby easyium.
