# Molabs Progresive Web APP

##Production

URL: http://54.144.112.150

## Getting Started

Install Bower

```sh
npm install -g bower
```

Install Polymer

```sh
npm install -g polymer-CLI
```

`cd` into this directory and install any missing bower dependencies with

```sh
bower install
```
finally you can serve locally with

```sh
polymer serve
```


**NOTE**: App will not be able to access api when running locally due Browser Origin control. While developing use this plugin to get around this https://chrome.google.com/webstore/detail/allow-control-allow-origi/nlfbmbojpeacfghkpbjhddihlkkiljbi?hl=en . FYI, while running this plugin has a tendancy to break production sites, such as Github!
