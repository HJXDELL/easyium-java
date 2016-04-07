=======
easyium
=======
easyium is an easy-to-use wrapper for selenium&appium and it can make you more focus on business not the element.

Find the latest version on github: https://github.com/KarlGong/easyium-java

Advantages
----------
- easyium provides unified apis to test on browsers and devices.

- easyium adds a global implicit wait for elements and you rarely need to consider waiting a element to be visible or existing.

- easyium introduces a simple and clear way to build classes for UI.

- easyium has a better performance, the element stores webelement reference and reuses it if necessary.

- easyium provides easy-to-use wait method for element. e.g., myElement.waitFor().not().exists()

- easyium provides a simple way to define a locator. e.g., use ``"xpath=.//mytag"`` instead of ``By.xpath(".//mytag")``

- easyium provides a mechanism to avoid StaleElementReferenceException.

Maven Information
-----------------
TODO

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
TODO

Contact me
----------
For information and suggestions you can contact me at karl.gong@outlook.com

Change Log
----------
1.0.0

- Baby easyium.