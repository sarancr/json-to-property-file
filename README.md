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

sample output itunes.properties file:

config.all.itunes.applicationId=1234
config.all.itunes.globalUrl=https://www.itunes.com
config.dc1.itunes.supportedRegions[0]=United States
config.dc1.itunes.supportedRegions[1]=Canada
config.dc1.itunes.supportedRegions[2]=Mexico
config.dc2.itunes.supportedRegions[0]=Sweden
config.dc2.itunes.supportedRegions[1]=Finland
config.dc2.itunes.supportedRegions[2]=Denmark


NOTE: Your solution needs to work for any JSON file, not just the example above.
