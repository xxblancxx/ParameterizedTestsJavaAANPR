# ParameterizedTestsJavaAANPR
Parameterized test class for javaaanpr exercise

E)
Personal Note: I'm a .Net developer, so my general overview of the java unit test is that everything looks weird to me ;)

Based upon the @Test containing the use of Assert methods, it feels like a true unit test. Only, the test is run multiple times (Parameterized) from a dynamic set.
The dynamic set is based upon a location on the local drive - this is known to cause errors if things change. But i see how this could be useful when importing test subjects from an external source, like an API.

A less-than-dynamic property was the expectedLicencePlate property. These are read from a static file containing the expected licenseplate number for the car in the image.
 - I don't have a better solution, but this generally would involve manual entry in this property file, which is less automated.

