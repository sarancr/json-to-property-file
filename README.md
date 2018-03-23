# json-to-property-file

Using the Helper API defined below, create a utility that given a json file (i.e. 'itunes.json') will generate a properties file (i.e. itunes.properties)

sample input itunes.json file:

```{
  "config": {
    "all": {
      "itunes": {
        "applicationId": 1234,
        "globalUrl": "https://www.itunes.com"
      }
    },
    "dc1": {
      "itunes": {
        "supportedRegions": ['United States', 'Canada', 'Mexico']
      }
    },
    "dc2": {
      "itunes": {
        "supportedRegions": ['Sweden', 'Finland', 'Denmark']
      }
    }
  }
}```
