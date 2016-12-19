(function(window, undefined) {
  var dictionary = {
    "5c360eb5-ed43-4f90-9404-ad681fbc62af": "UserMaster",
    "d184a509-6daf-4012-a9bb-45ca91f5924a": "Graphics",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "Log in",
    "07fce9c7-f4a0-4d9d-9cf5-e691d3967c8f": "Preferences",
    "4a73b085-f06f-46f1-bfdd-968cb27f8afb": "Table Admin",
    "734e42fd-475c-450b-8e1b-182a52e662ff": "Table User",
    "6630a78e-207b-4f3d-a32c-ab568d998399": "Table Master",
    "5de25745-0110-48f9-afcc-9cd494317ce6": "UserAdmin",
    "87db3cf7-6bd4-40c3-b29c-45680fb11462": "960 grid - 16 columns",
    "e5f958a4-53ae-426e-8c05-2f7d8e00b762": "960 grid - 12 columns",
    "3844bc7d-ba90-40ca-900b-c7ebffaf7733": "more size",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);