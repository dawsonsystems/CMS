<html>
<head>
  <!-- Integrate with Sitemesh layouts           -->
  <meta name="layout" content="gwt" />

  <!--                                           -->
  <!-- Any title is fine                         -->
  <!--                                           -->
  <title>de Vooght Stock Management</title>

  <!--                                           -->
  <!-- This script loads your compiled module.   -->
  <!-- If you add any GWT meta tags, they must   -->
  <!-- be added before this line.                -->
  <!--                                   -->
  <link rel='stylesheet' type='text/css' href='${createLinkTo(dir:"resources/css", file:"gxt-all.css")}' />
  <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:"resources/themes/slate/css", file:"xtheme-slate.css")}" />
  <script language='javascript' src='${createLinkTo(dir:"resources/flash", file:"swfobject.js")}'></script>
  <script type="text/javascript" src="${resource(dir: 'gwt/stock', file: 'stock.nocache.js')}"></script>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic ui         -->
<!--                                           -->
<body>
  <!-- OPTIONAL: include this if you want history support -->
  <iframe id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>

  <!-- Add the rest of the page here, or leave it -->
  <!-- blank for a completely dynamic interface.  -->
</body>
</html>
