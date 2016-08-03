<html>
<head>
    <title>供应链金融管理平台</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="static/style.css"/>

</head>
<body>

    <my-app>Loading...</my-app>


    <!-- 1. Load libraries -->
    <!-- Polyfill(s) for older browsers -->
    <script src="static/node_modules/core-js/client/shim.min.js"></script>
    <script src="static/node_modules/zone.js/dist/zone.js"></script>
    <script src="static/node_modules/reflect-metadata/Reflect.js"></script>
    <script src="static/node_modules/systemjs/dist/system.src.js"></script>

    <!-- 2. Configure SystemJS -->
    <script src="static/systemjs.config.js"></script>
    <script>
      System.import('app').catch(function(err){ console.error(err); });
    </script>

</body>
</html>
